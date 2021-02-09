package by.bytechs.security.model;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Kaminsky_VS
 */
public interface AppUserDetails extends UserDetails, CredentialsContainer {
    String LDAP_USER_TYPE = "LDAP";
    String BASIC_USER_TYPE = "BASIC";

    String getFullName();

    String getUserType();

    String getIpAddress();
}
