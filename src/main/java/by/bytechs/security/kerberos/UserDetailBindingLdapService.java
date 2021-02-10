package by.bytechs.security.kerberos;

import by.bytechs.ldap.config.LdapConnectionSettings;
import by.bytechs.ldap.data.repository.User;
import by.bytechs.ldap.data.service.LdapUserService;
import by.bytechs.security.model.LdapAppUserDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Romanovich Andrei
 */
@Service
public class UserDetailBindingLdapService implements UserDetailsService {
    private final LdapUserService ldapUserService;
    private final LdapConnectionSettings ldapConnectionSettings;
    private final static String ROLE_PREFIX = "ROLE_";
    private final static Logger LOGGER = LogManager.getLogger("WebLogger");

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    public UserDetailBindingLdapService(LdapUserService ldapUserService, LdapConnectionSettings ldapConnectionSettings) {
        this.ldapUserService = ldapUserService;
        this.ldapConnectionSettings = ldapConnectionSettings;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pattern pattern = Pattern.compile("(.+)@(.+)");
        Matcher matcher = pattern.matcher(username);
        if (matcher.matches()) {
            String domain = matcher.group(2);
            if (ldapConnectionSettings.getBasePartition().equalsIgnoreCase(domain)) {
                User user = ldapUserService.searchByPrincipalName(username);
                if (user != null) {
                    return new LdapAppUserDetails(getNameInNamespace(user.getId()), user.getAccountName(), user.getPassword(),
                            user.getFullName(), httpServletRequest.getRemoteAddr(), getAuthorities(user.getMemberOfs(), true));
                }
            }
        }
        return null;
    }

    private String getNameInNamespace(LdapName name) {
        String basePartitionSuffix = ldapConnectionSettings.getBasePartitionSuffix();
        if (basePartitionSuffix == null || basePartitionSuffix.isEmpty()) {
            return name.toString();
        }

        LdapName base = LdapUtils.newLdapName(basePartitionSuffix);
        try {
            LdapName result = (LdapName) name.clone();
            result.addAll(0, base);
            String db = result.toString();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("DN: ".concat(db));
            }
            return db;
        } catch (InvalidNameException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private Collection<GrantedAuthority> getAuthorities(List<String> memberOfs, boolean addPrefix) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (memberOfs == null) {
            return grantedAuthorities;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("User roles: ");
        for (String memberOf : memberOfs) {
            String[] mas = memberOf.split(",");
            if (mas.length > 0) {
                String[] groupNameMas = mas[0].split("=");
                if (groupNameMas.length == 2) {
                    String role = groupNameMas[1].toUpperCase();
                    if (role.isEmpty()) {
                        continue;
                    }
                    if (addPrefix) {
                        role = addPrefixToRole(role);
                    }
                    builder.append(role).append("\n");
                    grantedAuthorities.add(new SimpleGrantedAuthority(role));
                }
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(builder.toString());
        }
        return grantedAuthorities;
    }

    private String addPrefixToRole(String role) {
        return ROLE_PREFIX.concat(role);
    }
}