package by.bytechs.security;

import by.bytechs.security.basic.BasicAuthenticationProvider;
import by.bytechs.security.handler.AuthenticationSuccessHandler;
import by.bytechs.security.kerberos.KerberosConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.kerberos.authentication.KerberosAuthenticationProvider;
import org.springframework.security.kerberos.authentication.KerberosServiceAuthenticationProvider;
import org.springframework.security.kerberos.web.authentication.SpnegoAuthenticationProcessingFilter;
import org.springframework.security.kerberos.web.authentication.SpnegoEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Romanovich Andrei 12.01.2021 - 9:39
 */
@Configuration
@Import(KerberosConfig.class)
@EnableWebSecurity
@Profile({"SECURITY"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SpnegoEntryPoint spnegoEntryPoint;
    @Autowired
    private KerberosAuthenticationProvider kerberosAuthenticationProvider;
    @Autowired
    private KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(spnegoEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/home", "/ldap-info").hasRole("ALLUSERS")
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .addFilterBefore(spnegoAuthenticationProcessingFilter(authenticationManagerBean(),
                        authenticationSuccessHandler()), BasicAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(kerberosAuthenticationProvider)
                .authenticationProvider(kerberosServiceAuthenticationProvider)
                .authenticationProvider(new BasicAuthenticationProvider());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler();
    }

    @Bean
    @Autowired
    public SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter(AuthenticationManager authenticationManager,
                                                                                     AuthenticationSuccessHandler authenticationSuccessHandler) {
        SpnegoAuthenticationProcessingFilter filter = new SpnegoAuthenticationProcessingFilter();
        filter.setSessionAuthenticationStrategy(new ChangeSessionIdAuthenticationStrategy());
        filter.setAuthenticationManager(authenticationManager);
        filter.setSuccessHandler(authenticationSuccessHandler);
        filter.setFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login"));
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
