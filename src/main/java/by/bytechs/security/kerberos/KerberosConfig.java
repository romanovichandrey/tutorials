package by.bytechs.security.kerberos;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.kerberos.authentication.KerberosAuthenticationProvider;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosClient;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosTicketValidator;
import org.springframework.security.kerberos.web.authentication.SpnegoEntryPoint;

/**
 * @author Romanovich Andrei
 */
@Configuration
@ComponentScan(basePackages = {"by.bytechs.security.kerberos"})
public class KerberosConfig {

    @Bean
    @ConfigurationProperties(prefix = "server.kerberos-credential")
    public kerberosCredential kerberosCredential() {
        return new kerberosCredential();
    }

    /**
     * This provider is required to authorize the user through the form
     * when the user's browser does not support SPNEGO
     *
     * @param userDetailBindingLdapService - loads user-specific data
     * @return new instance KerberosAuthenticationProvider
     */
    @Bean
    public KerberosAuthenticationProvider kerberosAuthenticationProvider(UserDetailBindingLdapService userDetailBindingLdapService) {
        KerberosAuthenticationProvider provider = new KerberosAuthenticationProvider();
        SunJaasKerberosClient client = new SunJaasKerberosClient();
        client.setDebug(true);
        provider.setKerberosClient(client);
        provider.setUserDetailsService(userDetailBindingLdapService);
        return provider;
    }

    @Bean
    public SpnegoEntryPoint spnegoEntryPoint() {
        return new SpnegoEntryPoint("/login");
    }

    @Bean
    public SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator(kerberosCredential kerberosCredential) {
        SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();
        ticketValidator.setServicePrincipal(kerberosCredential.getPrincipal());
        ticketValidator.setKeyTabLocation(new FileSystemResource(kerberosCredential.getKeytabLocation()));
        ticketValidator.setDebug(true);
        return ticketValidator;
    }

    @Bean
    public KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider(UserDetailBindingLdapService userDetailBindingLdapService,
                                                                                       SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator) {
        KerberosServiceAuthenticationProvider provider = new KerberosServiceAuthenticationProvider();
        provider.setTicketValidator(sunJaasKerberosTicketValidator);
        provider.setUserDetailsService(userDetailBindingLdapService);
        return provider;
    }
}