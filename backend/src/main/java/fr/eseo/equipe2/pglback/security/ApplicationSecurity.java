package fr.eseo.equipe2.pglback.security;

import fr.eseo.equipe2.pglback.dao.UserDao;
import fr.eseo.equipe2.pglback.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //declare a class used for the configuration
public class ApplicationSecurity {
    @Autowired //permit to eliminate the need for getters and setters.
    private UserDao userDao;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    /**
     * Locates the user based on the login. If the UserDetails object that comes back may have
     * a username that is of a different case than what was actually requested
     * @return indication where the user does not exist if we have an exception
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return login -> userDao.findByLogin(login)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User " + login + " not found"));
    }

    /**
     * Permit to create an encode password
     * @return an encode password
     */
    @Bean           //declare a dependency injection
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Processes an Authentication request
     * @param authConfig Exports the authentication
     * @return a fully authenticated object including credentials
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
