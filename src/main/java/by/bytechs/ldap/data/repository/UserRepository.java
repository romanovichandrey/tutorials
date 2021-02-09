package by.bytechs.ldap.data.repository;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Romanovich Andrei 02.02.2021 - 10:45
 */
@Repository
public interface UserRepository extends LdapRepository<User> {

    User findByFullName(String username);

    User findByAccountNameAndPassword(String accountName, String password);

    User findByAccountName(String accountName);

    List<User> findByFullNameLikeIgnoreCase(String username);

    List<User> findByAccountNameLikeIgnoreCase(String accountName);

    User findByUserPrincipalName(String principalName);
}
