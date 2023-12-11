package com.ads.main.service.partner;

import com.ads.main.core.security.config.dto.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class PartnerSupport {
    public void load() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginInfo loginInfo = (LoginInfo) authentication.getCredentials();
        log.info(" loginInfo => {}", loginInfo);
    }
}
