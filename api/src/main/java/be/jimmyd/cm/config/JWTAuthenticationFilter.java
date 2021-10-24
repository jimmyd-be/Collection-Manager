package be.jimmyd.cm.config;

import be.jimmyd.cm.dto.TokenDto;
import be.jimmyd.cm.dto.UserLoginDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final String secret;
    private final String SIGN_UP_URL = "/api/auth/login";
    private long expirationTime;
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, @Value("${cm.secretKey}") String secret,
                                   @Value("${cm.secret.expiration}") long expirationTime) {
        this.authenticationManager = authenticationManager;
        this.secret = secret;
        this.expirationTime = expirationTime;

        setFilterProcessesUrl(SIGN_UP_URL);
    }                // new arraylist means authorities

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserLoginDto credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), UserLoginDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            credentials.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        String token = JWT.create()
                .withSubject(((UserDetailsImpl) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) //TODO change to Java 8 times when library updates
                .sign(Algorithm.HMAC512(secret.getBytes()));

        TokenDto dto = new TokenDto(token);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        res.setContentType("application/json");
        //res.getWriter().flush();
    }
}
