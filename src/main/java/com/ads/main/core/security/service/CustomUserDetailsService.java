package com.ads.main.core.security.service;

import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.security.config.dto.AppUser;
import com.ads.main.core.security.config.dto.Role;
import com.ads.main.service.admin.AdvertiserService;
import com.ads.main.service.admin.PartnerService;
import com.ads.main.service.admin.UserService;
import com.ads.main.vo.admin.advertiser.AdvertiserVo;
import com.ads.main.vo.admin.partner.PartnerVo;
import com.ads.main.vo.admin.user.UserVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final PartnerService partnerService;
    private final AdvertiserService advertiserService;


    @Override
    public UserDetails loadUserByUsername(String userId) throws  UsernameNotFoundException {
        UserVo user = userService.findUserById(userId);
        if (user.getUserStatus().equals(UserStatus.Enable.getCode())) {
            return new AppUser(user);
        }
        throw new UsernameNotFoundException("로그인 정보를 다시 확인 해주세요.");
    }

    public AppUser loadUserByUserId(String userId, Role role) throws UsernameNotFoundException {
        UserVo user = userService.findUserById(userId, role);
        if (user.getUserStatus().equals(UserStatus.Enable.getCode())) {
            return new AppUser(user);
        }
        throw new UsernameNotFoundException("로그인 정보를 다시 확인 해주세요.");
    }

    public PartnerVo loadPartnerByUserSeq(Long userSeq) {
       return partnerService.findPartnerByUserSeq(userSeq);
    }
    public AdvertiserVo loadAdvertiserByUserSeq(Long userSeq) {
       return advertiserService.findAdvertiserByUserSeq(userSeq);
    }

}
