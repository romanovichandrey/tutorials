package by.bytechs.security.kerberos;

import by.bytechs.utils.FilePathHelper;

/**
 * @author Romanovich Andrei
 */
public class kerberosCredential {
    private String principal;
    private String keytabLocation;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getKeytabLocation() {
        return FilePathHelper.findPathWithResource(keytabLocation);
    }

    public void setKeytabLocation(String keytabLocation) {
        this.keytabLocation = keytabLocation;
    }
}