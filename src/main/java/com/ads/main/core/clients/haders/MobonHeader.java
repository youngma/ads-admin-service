package com.ads.main.core.clients.haders;


import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@ToString
@ConfigurationProperties(prefix = "mobon")
@Configuration
@Slf4j
public class MobonHeader extends AbstractHeaders {
    @Override
    public void init() {

        headers.put(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Mobile Safari/537.36");
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.put(HttpHeaders.ACCEPT_CHARSET, "UTF-8");

    }
}
