package com.ads.main.core.security.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ads.main.core.vo.RespVo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String failMsg = "";

        if (exception instanceof AuthenticationServiceException) {
            failMsg = "로그인 정보가 일치 하지 않습니다.";
        } else if (exception instanceof BadCredentialsException) {
            failMsg = "로그인 정보가 일치 하지 않습니다.";
        } else if (exception instanceof LockedException) {
            failMsg = "로그인 정보가 일치 하지 않습니다.";
        } else if (exception instanceof DisabledException) {
            failMsg = "로그인 정보가 일치 하지 않습니다.";
        } else if (exception instanceof AccountExpiredException) {
            failMsg = "로그인 정보가 일치 하지 않습니다.";
        } else if (exception instanceof CredentialsExpiredException) {
            failMsg = "로그인 정보가 일치 하지 않습니다.";
        }


        log.error("# onAuthenticationFailure =>", exception);

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");
        String resp = objectMapper.writeValueAsString(new RespVo<>(failMsg));

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, resp);
    }
}
