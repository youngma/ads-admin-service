package com.ads.main.controller;

import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.vo.RespVo;
import com.ads.main.service.AdvertiserAccountService;
import com.ads.main.service.AdvertiserService;
import com.ads.main.service.UserService;
import com.ads.main.vo.advertiser.AdvertiserModifyVo;
import com.ads.main.vo.advertiser.AdvertiserSearchVo;
import com.ads.main.vo.advertiser.AdvertiserVo;
import com.ads.main.vo.advertiser.AdvertiserBusinessModifyVo;
import com.ads.main.vo.advertiser.account.*;
import com.ads.main.vo.advertiser.user.*;
import com.ads.main.vo.admin.UserVo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/v1/advertiser")
@RestController
@Slf4j
@Validated
public class AdvertiserController {

    private final AdvertiserService advertiserService;
    private final UserService userService;
    private final AdvertiserAccountService advertiserAccountService;

    // 광고주 조회
    @GetMapping("/search")
    public RespVo<Page<AdvertiserVo>> selectAdvertisers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "businessName", required = false) String businessName,
            @RequestParam(value = "businessNumber", required = false) String businessNumber,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "email", required = false) String email
    ) {

        AdvertiserSearchVo advertiserSearchVo = AdvertiserSearchVo.builder()
                .businessName(businessName)
                .phoneNumber(phoneNumber != null ? phoneNumber.replaceAll("\\D", "") : null)
                .businessNumber(businessNumber != null ? businessNumber.replaceAll("\\D", ""): null)
                .email(email)
                .build();

        Page<AdvertiserVo> advertisers = advertiserService.findAdvertisers(advertiserSearchVo, PageRequest.of(page - 1, size));
        return new RespVo<>(advertisers);
    }

    @GetMapping("/businessNumber/check")
    public RespVo<Boolean> duplicateCheckByBusinessNumber(
            @RequestParam(value = "businessNumber")
            @Pattern(regexp = "([0-2])([0-9])([0-9])([0-9])([0-9])([0-9])([1-4])([0-9])([0-9])([0-9])", message = "사업자 번호를 다시 확인 해주세요.")
            String businessNumber
    ) {
        boolean userCheck = advertiserService.checkBusinessNumber(businessNumber);
        return new RespVo<>(userCheck);
    }

    @PostMapping("/register")
    public RespVo<AdvertiserVo> register(@RequestBody @Validated @NotNull AdvertiserVo advertiserVo) {
        AdvertiserVo advertiser = advertiserService.register(advertiserVo);
        return new RespVo<>(advertiser);
    }

    @PutMapping("/modify")
    public RespVo<AdvertiserVo> modify(@RequestBody @Validated @NotNull AdvertiserModifyVo advertiserVo) {
        AdvertiserVo advertiser = advertiserService.modify(advertiserVo);
        return new RespVo<>(advertiser);
    }

    @PutMapping("/businessNumber")
    public RespVo<AdvertiserVo> businessNumberModify(@RequestBody @Validated @NotNull AdvertiserBusinessModifyVo advertiserVo) {
        AdvertiserVo advertiser = advertiserService.businessNumberModify(advertiserVo);
        return new RespVo<>(advertiser);
    }

    //광고주 사용자 조회
    @GetMapping("/users")
    public RespVo<Page<UserVo>> selectAdvertiserUser(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "advertiserSeq") long advertiserSeq,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "userStatus", required = false) String userStatus
    ) {
        AdvertiserUserSearchVo userSearchVo = AdvertiserUserSearchVo.builder()
                .advertiserSeq(advertiserSeq)
                .userId(userId)
                .userName(userName)
                .userStatus(UserStatus.of(userStatus))
                .build();
        return new RespVo<>(advertiserService.findAdvertiserUsers(userSearchVo, PageRequest.of(page - 1, size) ));
    }

    // 광고주 사용자 아이디 등록 여부 체크
    @GetMapping("/users/check")
    public RespVo<Boolean> duplicateCheckByUserId(
            @RequestParam(value = "advertiserSeq") int advertiserSeq,
            @RequestParam(value = "userId") String userId
    ) {
        boolean userCheck = advertiserService.userCheck(advertiserSeq, userId);
        return new RespVo<>(userCheck);
    }

    // 광고주 사용자 등록

    @PostMapping("/user/register")
    public RespVo<AdvertiserUserVo> userResister(@RequestBody @Validated @NotNull AdvertiserUserRegisterVo registerVo) {
        log.info("# registerVo = {}", registerVo);
        AdvertiserUserVo registered = advertiserService.registerAdvertiserUser(registerVo);
        return new RespVo<>(registered);
    }

    // 광고주 사용자 수정
    @PutMapping("/user/modify")
    public RespVo<UserVo> userModify(@RequestBody @Validated @NotNull AdvertiserUserModifyVo modifyVo) {
        UserVo user = advertiserService.modifyAdvertiserUser(modifyVo);
        return new RespVo<>(user);
    }

    @PutMapping("/user/disable")
    public RespVo<List<UserVo>> userDisabled(@RequestBody @Validated @NotNull AdvertiserUserStatusVo userDisabledVo) {
        UserVo user = advertiserService.advertiserUserDisable(userDisabledVo);

        return new RespVo<>(List.of(user));
    }

    @PutMapping("/user/enable")
    public RespVo<List<UserVo>> userEnabled(@RequestBody @Validated @NotNull AdvertiserUserStatusVo userDisabledVo) {
        UserVo user = advertiserService.advertiserUserEnable(userDisabledVo);

        return new RespVo<>(List.of(user));
    }


    // 광고주 계좌 정보
    @GetMapping("/accounts")
    public RespVo<Page<AdvertiserAccountVo>> selectAdvertiserAccount(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "advertiserSeq") long advertiserSeq,
            @RequestParam(value = "bankCode", required = false) String bankCode,
            @RequestParam(value = "bankAccount", required = false) String bankAccount,
            @RequestParam(value = "accountHolder", required = false) String accountHolder,
            @RequestParam(value = "accountUse", required = false) String accountUse

    ) {

        AdvertiserAccountSearchVo searchVo = AdvertiserAccountSearchVo.builder()
                .advertiserSeq(advertiserSeq)
                .bankCode(bankCode)
                .bankAccount(bankAccount)
                .accountHolder(accountHolder)
                .accountUse(accountUse)
                .build();

        Page<AdvertiserAccountVo> accounts = advertiserAccountService.findAccounts(searchVo, PageRequest.of(page -1 , size));
        return new RespVo<>(accounts);
    }


    // 광고주 계좌 정보 등록
    @GetMapping("/account/check")
    public RespVo<Boolean> accountCheck(
            @RequestParam(value = "advertiserSeq") long advertiserSeq,
            @RequestParam(value = "bankCode") String bankCode,
            @RequestParam(value = "bankAccount") String bankAccount
    ) {

        Boolean isRegistered = advertiserAccountService.checkAccount(advertiserSeq, bankCode, bankAccount);
        return new RespVo<>(isRegistered);
    }

    // 광고주 계좌 정보 등록
    @PostMapping("/account/register")
    public RespVo<AdvertiserAccountVo> accountRegister(
            @RequestBody @Validated @NotNull AdvertiserAccountVo registerVo
    ) {
        AdvertiserAccountVo account = advertiserAccountService.register(registerVo);
        return new RespVo<>(account);
    }

    // 광고주 계좌 정보 삭제
    @DeleteMapping("/account/delete")
    public RespVo<String> accountDelete(
            @RequestBody @Validated @NotNull AdvertiserAccountDeleteVo deleteVo
    ) {
        advertiserAccountService.delete(deleteVo);
        return new RespVo<>("success");
    }

    // 광고주 계좌 정보 수정
    @PutMapping("/account/modify")
    @Deprecated
    public RespVo<AdvertiserAccountVo> accountModify(
            @RequestBody @Validated @NotNull AdvertiserAccountModifyVo modifyVo
    ) {
        AdvertiserAccountVo account = advertiserAccountService.modify(modifyVo);
        return new RespVo<>(account);
    }


    // 광고주 계좌 정보 수정 (계좌 활성화)
    @PutMapping("/account/used")
    public RespVo<AdvertiserAccountVo> accountUsed(
            @RequestBody @Validated @NotNull AdvertiserAccountUseVo modifyVo
    ) {
        AdvertiserAccountVo account = advertiserAccountService.procUsed(modifyVo, true);
        return new RespVo<>(account);
    }

    // 광고주 계좌 정보 수정 (계좌 비 활성화)
    @PutMapping("/account/unused")
    public RespVo<AdvertiserAccountVo> accountUnUsed(
            @RequestBody @Validated @NotNull AdvertiserAccountUseVo modifyVo
    ) {
        AdvertiserAccountVo account = advertiserAccountService.procUsed(modifyVo, false);
        return new RespVo<>(account);
    }
}

