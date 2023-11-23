package com.ads.main.core.security.handle;


import com.ads.main.core.security.config.dto.AppUser;
import com.ads.main.core.security.config.dto.LoginInfo;
import com.ads.main.core.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashSet;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider  {

    private final CustomUserDetailsService userDetailsService;

    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("### 인증 시작 ###");

        String userId = authentication.getPrincipal().toString();
        String userPw = authentication.getCredentials().toString();

        try {
            AppUser appUser = userDetailsService.loadUserByUserId(userId);
            if ( compare(appUser, userId, userPw) ) {
                return getUsernamePasswordAuthenticationToken(appUser);
            }
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }

        throw new BadCredentialsException("로그인 정보를 다시 확인 해주세요.");
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(AppUser appUser) {
        LoginInfo loginInfo = new LoginInfo(
                appUser.getUserVo().getUserSeq(),
                appUser.getUserVo().getUserId(),
                null,
                appUser.getUserVo().getPhoneNumber(),
                appUser.getUserVo().getUserName()
        );

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(appUser.getUsername(), null, appUser.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(loginInfo);
        return usernamePasswordAuthenticationToken;
    }

    private boolean compare(UserDetails userVo, String userId , String userPw){
        return userVo.getUsername().equals(userId) && sCryptPasswordEncoder.matches(userPw, userVo.getPassword());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }
}



