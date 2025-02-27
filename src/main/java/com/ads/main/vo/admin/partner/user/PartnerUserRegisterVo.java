package com.ads.main.vo.admin.partner.user;

import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.security.config.dto.Role;
import com.ads.main.vo.admin.user.UserVo;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PartnerUserRegisterVo extends UserVo implements Serializable {

    private Long partnerSeq;

    public UserVo toUser(@NotNull Role role) {
        return toUser(role, null);
    }


    public UserVo toUser(@NotNull Role role, UserStatus userStatus) {

        UserVo userVo = new UserVo();

        userVo.setUserId(this.userId);
        userVo.setUserName(this.userName);
        userVo.setUserPassword(this.userPassword);
        userVo.setPhoneNumber(this.phoneNumber);
        userVo.setEmail(this.email);
        userVo.setUserRole(role);

        if (userStatus == null) {
            userVo.setUserStatus(UserStatus.Disable.getCode());
        } else {
            userVo.setUserStatus(userStatus.getCode());
        }

        return userVo;
    }

}
