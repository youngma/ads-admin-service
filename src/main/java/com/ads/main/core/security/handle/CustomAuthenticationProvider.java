package com.ads.main.core.security.handle;


import com.ads.main.core.security.config.dto.AppUser;
import com.ads.main.core.security.config.dto.LoginInfo;
import com.ads.main.core.security.config.dto.Role;
import com.ads.main.core.security.service.CustomUserDetailsService;
import com.ads.main.vo.admin.partner.PartnerVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider  {

    private final CustomUserDetailsService userDetailsService;

    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("### 인증 시작 ###");

        Optional<? extends GrantedAuthority> grantedAuthority = authentication.getAuthorities().stream().findFirst();

        if (grantedAuthority.isPresent()) {

            Role role = Role.findRole(grantedAuthority.get().getAuthority());
            String id = authentication.getPrincipal().toString();
            String password = authentication.getCredentials().toString();

            try {
                AppUser appUser = userDetailsService.loadUserByUserId(id, role);
                if ( compare(appUser, id, password) ) {
                    return getUsernamePasswordAuthenticationToken(appUser);
                }
            } catch (Exception e) {
                throw new BadCredentialsException(e.getMessage());
            }
        } else {
            throw new BadCredentialsException("로그인 정보를 다시 확인 해주세요.");
        }

        throw new BadCredentialsException("로그인 정보를 다시 확인 해주세요.");
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(AppUser appUser) {

        GrantedAuthority grantedAuthority = appUser.getAuthorities().stream().findFirst().get();
        Role role = Role.findRole(grantedAuthority.getAuthority());



        LoginInfo loginInfo = new LoginInfo(
                appUser.getUserVo().getUserSeq(),
                appUser.getUserVo().getUserId(),
                null,
                appUser.getUserVo().getPhoneNumber(),
                appUser.getUserVo().getUserName()
        );


        if (role == Role.PARTNER) {
            PartnerVo partnerVo = userDetailsService.loadPartnerByUserSeq(appUser.getUserVo().getUserSeq());

            loginInfo.setPartner(partnerVo);
        }

        if (role == Role.ADVERTISER) {

        }

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
