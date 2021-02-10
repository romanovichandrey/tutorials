package by.bytechs.security.basic;

import by.bytechs.security.model.BasicAppUserDetails;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

/**
 * @author Romanovich Andrei 10.02.2021 - 13:45
 */

public class BasicAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String login = auth.getName();
        String password = auth.getCredentials().toString();
        if ("test".equals(login) && "test".equals(password)) {
            BasicAppUserDetails userDetails = new BasicAppUserDetails("test", "test", "TEST",
                    "", Collections.singleton(new SimpleGrantedAuthority("ROLE_ALLUSERS")));
            UsernamePasswordAuthenticationToken output = new UsernamePasswordAuthenticationToken(userDetails,
                    auth.getCredentials(), userDetails.getAuthorities());
            output.setDetails(auth.getDetails());
            return output;
        }
        throw new BadCredentialsException("Bad credential");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
