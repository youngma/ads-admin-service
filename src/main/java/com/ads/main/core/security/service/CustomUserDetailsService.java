package com.ads.main.core.security.service;

import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.security.config.dto.AppUser;
import com.ads.main.service.UserService;
import com.ads.main.vo.admin.UserStatusVo;
import com.ads.main.vo.admin.UserVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String userId) throws  UsernameNotFoundException {
        UserVo user = userService.findUserById(userId);
        if (user.getUserStatus().equals(UserStatus.Enable.getCode())) {
            return new AppUser(user);
        }
        throw new UsernameNotFoundException("로그인 정보를 다시 확인 해주세요.");
    }

    public AppUser loadUserByUserId(String userId) throws UsernameNotFoundException {
        UserVo user = userService.findUserById(userId);
        if (user.getUserStatus().equals(UserStatus.Enable.getCode())) {
            return new AppUser(user);
        }
        throw new UsernameNotFoundException("로그인 정보를 다시 확인 해주세요.");
    }
}
