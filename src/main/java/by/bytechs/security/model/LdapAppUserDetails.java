package by.bytechs.security.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Romanovich Andrei 05.02.2021 - 10:25
 */
public class LdapAppUserDetails implements AppUserDetails {
    private String dn;
    private String username;
    private String password;
    private String fullName;
    private String ipAddress;
    private Collection<GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    public LdapAppUserDetails(String dn, String username, String password, String fullName, String ipAddress,
                              Collection<GrantedAuthority> authorities) {
        this.dn = dn;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.ipAddress = ipAddress;
        this.authorities = authorities;
    }

    public String getDn() {
        return dn;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getUserType() {
        return LDAP_USER_TYPE;
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
