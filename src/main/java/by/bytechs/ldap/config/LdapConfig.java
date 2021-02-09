package by.bytechs.ldap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.kerberos.client.config.SunJaasKrb5LoginConfig;
import org.springframework.security.kerberos.client.ldap.KerberosLdapContextSource;

/**
 * @author Romanovich Andrei 02.02.2021 - 10:31
 */
@Configuration
@ComponentScan(basePackages = {"by.bytechs.ldap"})
@Profile({"LDAP", "SECURITY"})
@EnableLdapRepositories(basePackages = {"by.bytechs.ldap.**"})
public class LdapConfig {

    @Bean
    @ConfigurationProperties(prefix = "server.ldap-connection-settings")
    public LdapConnectionSettings ldapConnectionSettings() {
        return new LdapConnectionSettings();
    }

    @Bean
    @Autowired
    public KerberosLdapContextSource kerberosLdapContextSource(LdapConnectionSettings ldapConnectionSettings) throws Exception {
        SunJaasKrb5LoginConfig loginConfig = new SunJaasKrb5LoginConfig();
        loginConfig.setServicePrincipal(ldapConnectionSettings.getPrincipal());
        loginConfig.setKeyTabLocation(new FileSystemResource(ldapConnectionSettings.getKeytabLocation()));
        loginConfig.setDebug(true);
        loginConfig.setIsInitiator(true);
        loginConfig.afterPropertiesSet();
        KerberosLdapContextSource contextSource = new KerberosLdapContextSource(ldapConnectionSettings.getUrl(),
                ldapConnectionSettings.getBasePartitionSuffix());
        contextSource.setLoginConfig(loginConfig);
        return contextSource;
    }

    @Bean
    @Autowired
    public LdapTemplate ldapTemplate(KerberosLdapContextSource kerberosLdapContextSource) {
        return new LdapTemplate(kerberosLdapContextSource);
    }
}
