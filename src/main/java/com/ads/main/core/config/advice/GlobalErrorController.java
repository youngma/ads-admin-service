package com.ads.main.core.config.advice;

import com.ads.main.core.vo.RespVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Arrays;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GlobalErrorController implements ErrorController {

    private final Environment environment;

    @Value("${error.path:/error}")
    private String errorPath;

    @RequestMapping(value = "${error.path:/error}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<RespVo<String>> error(HttpServletRequest request) {
        final Throwable error = getError(request);
        int statusCode = getErrorStatusCode(request);

//        HttpStatusCode status = getHttpStatus(error);
        log.error("# Error Controller {}, {}", statusCode, error);

//        if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
//            status = HttpStatus.UNAUTHORIZED;
//        }

        String errorMessage = (isProduction() && statusCode >= 500) ? "Server error" : getErrorMessage(request);

        return new ResponseEntity<>(
                new RespVo<>(errorMessage), HttpStatusCode.valueOf(statusCode)
        );
    }

    private boolean isProduction() {
        return Arrays.asList(environment.getActiveProfiles()).contains("production");
    }

    private HttpStatusCode getHttpStatus(Throwable error) {
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (error instanceof HttpStatusCodeException) {
            HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) error;
            status = httpStatusCodeException.getStatusCode();
        }

        return status;
    }

    private Throwable getError(HttpServletRequest request) {
        return (Throwable) request.getAttribute("jakarta.servlet.error.exception");
    }

    private int getErrorStatusCode(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("jakarta.servlet.error.status_code");
        return statusCode != null ? statusCode : HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    private String getErrorMessage(HttpServletRequest request) {
        final Throwable exc = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
        return exc != null ? exc.getMessage() : getErrorMessage(request, "Unexpected error occurred");
    }

    private String getErrorMessage(HttpServletRequest request, String defaultMessage) {
        Object message = request.getAttribute("jakarta.servlet.error.message");
        return ObjectUtils.isEmpty(message) ? defaultMessage : (String)message;
    }

}

