package com.ads.main.core.security.fillter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try{
            filterChain.doFilter(request, response);
        } catch (AuthenticationException | JwtException e){
            setErrorResponse(response, HttpStatus.UNAUTHORIZED, e);
        } catch (AccessDeniedException e) {
            setErrorResponse(response, HttpStatus.FORBIDDEN, e);
        }
    }
    private void setErrorResponse(
            HttpServletResponse response,
            HttpStatusCode httpStatusCode,
            Exception e
    ) throws IOException {
        log.debug("# Filter Exception {}", e.getMessage());
        response.sendError(httpStatusCode.value(), e.getMessage());
    }

}
