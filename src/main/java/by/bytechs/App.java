package by.bytechs;

import by.bytechs.ldap.config.LdapConfig;
import by.bytechs.security.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"by.bytechs.service", "by.bytechs.excel", "by.bytechs.controller"},
        exclude = {SecurityAutoConfiguration.class, LdapAutoConfiguration.class})
@Import({LdapConfig.class, WebSecurityConfig.class})
public class App implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(new Class[]{App.class, LdapConfig.class}, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login.html");
    }
}
