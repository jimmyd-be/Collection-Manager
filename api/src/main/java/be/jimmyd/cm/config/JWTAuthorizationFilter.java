package be.jimmyd.cm.config;

import be.jimmyd.cm.domain.exceptions.UserPermissionException;
import be.jimmyd.cm.entities.InvalidToken;
import be.jimmyd.cm.repositories.InvalidTokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final String secret;

    private final String tokenPrefix = "Bearer ";
    private final String headerString = "Authorization";
    private final UserDetailsServiceImpl userDetailsService;
    private final InvalidTokenRepository invalidTokenRepository;

    public JWTAuthorizationFilter(AuthenticationManager authManager,
                                  UserDetailsServiceImpl userDetailsService,
                                  @Value("${cm.secretKey}") String secret,
                                  InvalidTokenRepository invalidTokenRepository) {
        super(authManager);
        this.secret = secret;
        this.userDetailsService = userDetailsService;
        this.invalidTokenRepository = invalidTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        List<String> uriWithoutAuthentication = List.of("/api/auth/request-pass", "/api/auth/register");

        if (!uriWithoutAuthentication.contains(req.getRequestURI())) {

            String header = req.getHeader(headerString);

            if (header == null || !header.startsWith(tokenPrefix)) {
                chain.doFilter(req, res);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(headerString);

        if (invalidTokenRepository.findById(token.replace(tokenPrefix, "")).isPresent()) {
            return null;
        }

        if (token != null) {
            // parse the token.
            String userMail = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getSubject();

            if (userMail != null) {

                final UserDetails userDetails = userDetailsService.loadUserByUsername(userMail);

                return new UsernamePasswordAuthenticationToken(userMail, null, userDetails.getAuthorities());
            }

            return null;
        }

        return null;
    }
}