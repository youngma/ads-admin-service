package com.ads.main.core.security.dto;

import lombok.Getter;

@Getter
public class SecurityProperties{

    private String secret;
    private int expirationTime; // in days,
    private String tokenPrefix;
    private String headerString;
    int strength;

    public SecurityProperties() {
        this.secret = "mYq3t6w9z\\$B&E)H@McQfTjWnZr4u7x!A%D*F-JaNdRgUkXp2s5v8y/B?E(H+KbPe";
        this.expirationTime = 31;
        this.tokenPrefix = "Bearer ";
        this.headerString = "Authorization";
        this.strength = 10;
    }

}
