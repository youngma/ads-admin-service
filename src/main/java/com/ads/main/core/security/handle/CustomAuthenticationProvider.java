package com.ads.main.core.security.handle;


import com.ads.main.core.security.config.dto.AppUser;
import com.ads.main.core.security.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider  {

    private final CustomUserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("### 인증 시작 ###");

        String userId = authentication.getPrincipal().toString();
        String userPw = authentication.getCredentials().toString();

        try {
            AppUser appUser = userDetailsService.loadUserByUserId(userId);
            if ( compare(appUser, userId, userPw) ) {
                return new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getUserVo(), appUser.getAuthorities());
            }
        } catch (Exception e) {
            log.error("# 인증 오류 => ", e);
            throw new BadCredentialsException(e.getMessage());
        }

        return null;
    }

    private boolean compare(UserDetails userVo, String userId , String userPw){
        return userVo.getUsername().equals(userId) && userVo.getPassword().equals(userPw);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }
}



