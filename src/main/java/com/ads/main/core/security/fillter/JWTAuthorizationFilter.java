package com.ads.main.core.security.fillter;

import com.ads.main.core.security.config.SecurityProperties;
import com.ads.main.core.security.config.dto.LoginInfo;
import com.ads.main.core.security.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final SecurityProperties securityProperties;

    private final CustomUserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,  SecurityProperties securityProperties, CustomUserDetailsService userDetailsService, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.securityProperties = securityProperties;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, SecurityProperties securityProperties, CustomUserDetailsService userDetailsService, ObjectMapper objectMapper) {
        super(authenticationManager, authenticationEntryPoint);
        this.securityProperties = securityProperties;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws AuthenticationException, ServletException, IOException {

        String requestURI = request.getRequestURI();

        log.info("### JWTAuthorizationFilter");
        String token = request.getHeader(securityProperties.getHeaderString());
        String jwt = resolveToken(request);

        if (StringUtils.hasText(jwt) && validateToken(jwt)) {

            Authentication authentication = loadAuthentication(token);
            log.debug("Security Context 에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getPrincipal(), requestURI);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);

        } else {
            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
            throw new AuthenticationCredentialsNotFoundException("UNAUTHORIZED");
        }
    }


    private UsernamePasswordAuthenticationToken loadAuthentication(String token) {
        try {

            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(securityProperties.getSecret().getBytes()))
                    .build()
                    .parseClaimsJws(token.replace(securityProperties.getTokenPrefix(), ""))
                    .getBody();

            LoginInfo loginInfo = objectMapper.readValue(claims.get("info", String.class), LoginInfo.class) ;
            List<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("role").toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            return new UsernamePasswordAuthenticationToken(loginInfo.getUserSeq(), loginInfo, authorities);
        } catch (Exception e) {
            throw new AuthorizationServiceException("인증 토큰이 잘못 되었습니다..");
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(securityProperties.getHeaderString());

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(securityProperties.getSecret().getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
            throw new JwtException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
            throw new JwtException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
            throw new JwtException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
            throw new JwtException("JWT 토큰이 잘못되었습니다.");
        }

    }

}
