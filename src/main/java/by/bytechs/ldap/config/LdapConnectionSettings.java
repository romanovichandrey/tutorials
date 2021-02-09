package by.bytechs.ldap.config;

import by.bytechs.utils.FilePathHelper;

import java.util.List;

/**
 * @author Romanovich Andrei 02.02.2021 - 10:37
 */
public class LdapConnectionSettings {
    private String principal;
    private String keytabLocation;
    private List<String> url;
    private String basePartitionSuffix;
    private String basePartition;

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

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String getBasePartitionSuffix() {
        return basePartitionSuffix;
    }

    public void setBasePartitionSuffix(String basePartitionSuffix) {
        this.basePartitionSuffix = basePartitionSuffix;
    }

    public String getBasePartition() {
        return basePartition;
    }

    public void setBasePartition(String basePartition) {
        this.basePartition = basePartition;
    }
}
