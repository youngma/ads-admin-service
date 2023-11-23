package com.ads.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
public class AdsAdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdsAdminServiceApplication.class, args);
    }

}
