package by.bytechs.ldap.data.service;

import by.bytechs.ldap.data.repository.User;
import by.bytechs.ldap.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Romanovich Andrei 02.02.2021 - 10:48
 */
@Service
public class LdapUserService {
    private final UserRepository userRepository;
    private final LdapTemplate ldapTemplate;

    @Autowired
    public LdapUserService(UserRepository userRepository, LdapTemplate ldapTemplate) {
        this.userRepository = userRepository;
        this.ldapTemplate = ldapTemplate;
    }

    public User searchByAccountName(final String accountName) {
        User user =  userRepository.findByAccountName(accountName);
        return user;
    }

    public List<User> searchByUserNameLikeIgnoreCase(final String userName) {
        return userRepository.findByFullNameLikeIgnoreCase(userName);
    }

    public List<User> searchByAccountNameLikeIgnoreCase(final String accountName) {
        return userRepository.findByAccountNameLikeIgnoreCase(accountName);
    }

    public User searchByPrincipalName(String principalName) {
        return userRepository.findByUserPrincipalName(principalName);
    }
}
