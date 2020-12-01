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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder bCryptPasswordEncoder;
    private long expirationtime;
    private UserDetailsServiceImpl userDetailsService;
    private String secret;

    public SecurityConfig(PasswordEncoder bCryptPasswordEncoder, UserDetailsServiceImpl userDetailsService, @Value("${cm.secretKey}") String secret,
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
    CorsConfigurationSource corsConfigurationSource() {

        final CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
        corsConfiguration.addAllowedMethod(HttpMethod.GET);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and()
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
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
