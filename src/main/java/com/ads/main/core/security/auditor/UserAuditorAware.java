package com.ads.main.core.security.auditor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserAuditorAware implements AuditorAware<String> {
    public static final String ADMIN_SYSTEM = "ADMIN_SYSTEM";

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new AuthenticationServiceException("사용자 정보가 없습니다.");
            return  Optional.of(ADMIN_SYSTEM);
        }

        Long seq = (Long) authentication.getPrincipal();
        return  Optional.ofNullable(String.valueOf(seq));
    }
}
