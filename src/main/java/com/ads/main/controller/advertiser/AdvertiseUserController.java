package com.ads.main.controller.advertiser;


import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.security.config.dto.Role;
import com.ads.main.core.vo.RespVo;
import com.ads.main.service.admin.UserService;
import com.ads.main.vo.admin.user.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/advertiser/v1/users")
@RestController
@Slf4j
@Validated
public class AdvertiseUserController {

    private final UserService userService;

    @GetMapping("/search")
    public RespVo<Page<UserVo>> selectUsers(
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "20") int size,
        @RequestParam(value = "userId", required = false) String userId,
        @RequestParam(value = "userName", required = false) String userName,
        @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
        @RequestParam(value = "userStatus", required = false) String userStatus
    ) {

        UserSearchVo userSearchVo = UserSearchVo.builder()
                .userId(userId)
                .userName(userName)
                .phoneNumber(phoneNumber != null ? phoneNumber.replaceAll("\\D", "") : null)
                .userStatus(UserStatus.of(userStatus))
                .build();

        Page<UserVo> users = userService.findUsers(userSearchVo, PageRequest.of(page - 1, size));
        return new RespVo<>(users);
    }

    @GetMapping("/info")
    public RespVo<UserVo> check() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdvertiserUserVo user = userService.findAdvertiserUser(Long.valueOf(authentication.getName()), Role.ADVERTISER);
        return new RespVo<>(user);
    }

    @PostMapping("/register")
    public RespVo<UserVo> register(@RequestBody @Validated @NotNull UserVo userVo) {

        userVo.setUserRole(Role.ADVERTISER);

        UserVo registeredUser = userService.register(userVo);
        return new RespVo<>(registeredUser);
    }

    @GetMapping("/check")
    public RespVo<Boolean> duplicateCheckByUserId(
            @RequestParam(value = "userId") String userId
    ) {
        boolean userCheck = userService.adminUserCheck(userId);
        return new RespVo<>(userCheck);
    }

    @PutMapping("/disabled")
    public RespVo<List<UserVo>> disabled(@RequestBody @Validated @NotNull UserStatusVo userStatusVo) {
        if (userStatusVo.getUserSeqList().size() == 1) {
            UserVo disableUser =  userService.userDisable(userStatusVo.getUserSeqList().get(0));
            return new RespVo<>(List.of(disableUser));
        } else {
            List<UserVo> userVos = userService.bulkUserDisable(userStatusVo.getUserSeqList());
            return new RespVo<>(userVos);
        }
    }

    @PutMapping("/enabled")
    public RespVo<List<UserVo>> enabled(@RequestBody @Validated @NotNull UserStatusVo userStatusVo) {
        if (userStatusVo.getUserSeqList().size() == 1) {
            UserVo disableUser =  userService.userEnable(userStatusVo.getUserSeqList().get(0));
            return new RespVo<>(List.of(disableUser));
        } else {
            List<UserVo> userVos = userService.bulkUserEnable(userStatusVo.getUserSeqList());
            return new RespVo<>(userVos);
        }
    }

    @PutMapping("/password")
    public RespVo<UserVo> password(@RequestBody @Validated @NotNull UserPasswordVo userDisabledVo) {
        UserVo user =  userService.changePassword(userDisabledVo);
        return new RespVo<>(user);
    }

    @PutMapping("/info")
    public RespVo<UserVo> modify(@RequestBody @Validated @NotNull UserModifyVo userVo) {
        UserVo user = userService.modify(userVo);
        return new RespVo<>(user);
    }

}
