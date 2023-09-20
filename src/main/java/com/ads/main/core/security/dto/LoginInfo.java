package com.ads.main.core.security.dto;

import org.springframework.security.core.token.Sha512DigestUtils;

public record LoginInfo(
        String userId,
        String password
) {
}
