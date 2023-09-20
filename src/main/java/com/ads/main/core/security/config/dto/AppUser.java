package com.ads.main.core.security.config.dto;

import com.ads.main.vo.admin.UserVo;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
@ToString(callSuper = true)
public class AppUser extends User {

    private final UserVo userVo;

    public AppUser(UserVo userVo) {
        super(userVo.getUserId(), userVo.getUserPassword(), List.of(new SimpleGrantedAuthority(userVo.getUserRole().getRoleName())));
        this.userVo = userVo;
    }
}
