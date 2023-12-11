package com.ads.main.core.security.fillter;

import com.ads.main.core.config.exception.ServiceException;
import com.ads.main.core.enums.exception.AuthException;
import com.ads.main.core.security.config.SecurityProperties;
import com.ads.main.core.security.config.dto.LoginInfo;
import com.ads.main.core.security.config.dto.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;

import static java.util.Date.from;


@AllArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final SecurityProperties securityProperties;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
            LoginInfo user = objectMapper.readValue(request.getReader(), LoginInfo.class);

            SimpleGrantedAuthority grantedAuthority;

            if (request.getRequestURI().contains("/partner/login")) {
                grantedAuthority = new SimpleGrantedAuthority(Role.PARTNER.getRoleName());
            } else if (request.getRequestURI().contains("/advertiser/login")) {
                grantedAuthority = new SimpleGrantedAuthority(Role.ADVERTISER.getRoleName());
            }else if (request.getRequestURI().contains("/admin/login")) {
                grantedAuthority = new SimpleGrantedAuthority(Role.ADMIN.getRoleName());
            } else {
                throw AuthException.PATH_NOT_FOUND.throwErrors();
            }

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword(), Collections.singleton(grantedAuthority)));
        } catch (Exception e) {
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
