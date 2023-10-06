package com.ads.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdsAdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdsAdminServiceApplication.class, args);
    }

}
