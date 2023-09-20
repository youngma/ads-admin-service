package com.ads.main.core.security.service;

import com.ads.main.core.security.config.dto.AppUser;
import com.ads.main.service.UserService;
import com.ads.main.vo.admin.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String userId) throws  UsernameNotFoundException {
        UserVo user = userService.findUserById(userId);
        return new AppUser(user);
    }

    public AppUser loadUserByUserId(String userId) throws UsernameNotFoundException {
        UserVo user = userService.findUserById(userId);
        return new AppUser(user);
    }
}
