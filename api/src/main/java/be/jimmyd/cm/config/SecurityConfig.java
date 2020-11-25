package be.jimmyd.cm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private long expirationtime;
    private UserDetailsServiceImpl userDetailsService;
    private String secret;

    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsServiceImpl userDetailsService, @Value("${cm.secretKey}") String secret,
                          @Value("${cm.secret.expiration}") long expirationTime) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.secret = secret;
        this.expirationtime = expirationTime;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), secret, expirationtime))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), secret))
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/auth/**").anonymous()
                .anyRequest().authenticated()
                .and()

                //.anonymous().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
