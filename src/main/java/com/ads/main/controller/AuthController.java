package com.ads.main.controller;


import com.ads.main.core.vo.RespVo;
import com.ads.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/admin/v1/auth")
@RestController
@Slf4j
@Validated
public class AuthController {

    private final UserService userService;

    @PostMapping("/logout")
    public RespVo<String> check() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return new RespVo<>("Success");
    }

}
