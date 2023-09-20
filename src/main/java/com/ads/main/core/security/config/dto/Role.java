package com.ads.main.core.security.config.dto;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("admin"),
    PARTNER("partner"),
    ADVERTISER("advertiser"),

    ;
    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    Role findRole(String roleName) {
        return Role.valueOf(roleName);
    }
}
