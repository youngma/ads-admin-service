package com.ads.main.core.security.fillter;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
public class FilterSkipMatcher implements RequestMatcher {

    private final List<String> pathToSkip;
    private final String processingPath;

    private OrRequestMatcher orRequestMatcher;
    private RequestMatcher processingMatcher;

    public FilterSkipMatcher(List<String> pathToSkip, String processingPath) {
        log.debug("# FilterSkipMatcher init =. {}", pathToSkip);

        this.pathToSkip = pathToSkip;
        this.processingPath = processingPath;

        List<RequestMatcher> requestMatchers = pathToSkip.stream().map(t -> (RequestMatcher) httpPath(t)).toList();

        log.debug("# requestMatchers {}", requestMatchers);

        orRequestMatcher = new OrRequestMatcher(requestMatchers);
        processingMatcher =  new AntPathRequestMatcher(processingPath);
    }

    private AntPathRequestMatcher httpPath(String skipPath) {

        String[] splitStr = skipPath.split(",");

        return new AntPathRequestMatcher(
                splitStr[1],
                splitStr[0]
        );
    }
    @Override
    public boolean matches(HttpServletRequest request) {
        return !orRequestMatcher.matches(request) && processingMatcher.matches(request);
    }



    @Override
    public MatchResult matcher(HttpServletRequest request) {
        return RequestMatcher.super.matcher(request);
    }

}
