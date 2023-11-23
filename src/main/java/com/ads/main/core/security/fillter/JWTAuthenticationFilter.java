package com.ads.main.core.security.fillter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ads.main.core.security.config.dto.LoginInfo;
import com.ads.main.core.security.config.SecurityProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;

import static java.util.Date.from;


@AllArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final SecurityProperties securityProperties;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginInfo user = objectMapper.readValue(request.getReader(), LoginInfo.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword()));
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {

        HashSet<String> roles = new HashSet<>();

        if (!auth.getAuthorities().isEmpty()) {
            auth.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));
        }




        String token = Jwts.builder()
                .setSubject((auth.getName()))
                .claim("info", objectMapper.writeValueAsString(auth.getDetails()))
                .claim("role", roles)
                .setExpiration(from(Instant.now().plus(Duration.ofDays(securityProperties.getExpirationTime()))))
                .signWith(Keys.hmacShaKeyFor(securityProperties.getSecret().getBytes()), SignatureAlgorithm.HS512)
                .compact();

        log.info("# Token => {}", token);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        response.addHeader(securityProperties.getHeaderString(), securityProperties.getTokenPrefix() + token);

//        super.successfulAuthentication(request, response, chain, auth);
    }
}
