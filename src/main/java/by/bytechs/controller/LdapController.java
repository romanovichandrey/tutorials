package by.bytechs.controller;

import by.bytechs.ldap.data.repository.User;
import by.bytechs.ldap.data.service.LdapUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Romanovich Andrei 12.01.2021 - 15:02
 */
@RestController
@RequestMapping("/ldap-info")
public class LdapController {
    private final LdapUserService ldapUserService;

    @Autowired
    public LdapController(LdapUserService ldapUserService) {
        this.ldapUserService = ldapUserService;
    }

    @GetMapping("/{accountName}")
    public User search(@PathVariable @NonNull String accountName) {
        return ldapUserService.searchByAccountName(accountName);
    }

    @GetMapping("/like/{userName}")
    public List<User> searchLike(@PathVariable @NonNull String userName) {
        return ldapUserService.searchByUserNameLikeIgnoreCase(userName);
    }

    @GetMapping("/like/accountName/{accountName}")
    public List<User> searchLikeAccountName(@PathVariable @NonNull String accountName) {
        return ldapUserService.searchByAccountNameLikeIgnoreCase(accountName);
    }
}
