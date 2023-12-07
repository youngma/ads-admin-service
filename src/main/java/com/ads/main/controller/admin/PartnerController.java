package com.ads.main.controller.admin;

import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.vo.RespVo;
import com.ads.main.service.PartnerAccountService;
import com.ads.main.service.PartnerService;
import com.ads.main.service.UserService;
import com.ads.main.vo.partner.PartnerBusinessModifyVo;
import com.ads.main.vo.partner.PartnerModifyVo;
import com.ads.main.vo.partner.PartnerSearchVo;
import com.ads.main.vo.partner.PartnerVo;
import com.ads.main.vo.partner.account.*;
import com.ads.main.vo.partner.user.*;
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
@RequestMapping("/admin/v1/partner")
@RestController
@Slf4j
@Validated
public class PartnerController {

    private final PartnerService partnerService;
    private final UserService userService;
    private final PartnerAccountService partnerAccountService;

    // 광고주 조회
    @GetMapping("/search")
    public RespVo<Page<PartnerVo>> selectAdvertisers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "businessName", required = false) String businessName,
            @RequestParam(value = "businessNumber", required = false) String businessNumber,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "email", required = false) String email
    ) {

        PartnerSearchVo partnerSearchVo = PartnerSearchVo.builder()
                .businessName(businessName)
                .phoneNumber(phoneNumber != null ? phoneNumber.replaceAll("\\D", "") : null)
                .businessNumber(businessNumber != null ? businessNumber.replaceAll("\\D", ""): null)
                .email(email)
                .build();

        Page<PartnerVo> advertisers = partnerService.findAdvertisers(partnerSearchVo, PageRequest.of(page - 1, size));
        return new RespVo<>(advertisers);
    }

    @GetMapping("/businessNumber/check")
    public RespVo<Boolean> duplicateCheckByBusinessNumber(
            @RequestParam(value = "businessNumber")
            @Pattern(regexp = "([0-2])([0-9])([0-9])([0-9])([0-9])([0-9])([1-4])([0-9])([0-9])([0-9])", message = "사업자 번호를 다시 확인 해주세요.")
            String businessNumber
    ) {
        boolean userCheck = partnerService.checkBusinessNumber(businessNumber);
        return new RespVo<>(userCheck);
    }

    @PostMapping("/register")
    public RespVo<PartnerVo> register(@RequestBody @Validated @NotNull PartnerVo partnerVo) {
        PartnerVo partner = partnerService.register(partnerVo);
        return new RespVo<>(partner);
    }

    @PutMapping("/modify")
    public RespVo<PartnerVo> modify(@RequestBody @Validated @NotNull PartnerModifyVo modifyVo) {
        PartnerVo partner = partnerService.modify(modifyVo);
        return new RespVo<>(partner);
    }

    @PutMapping("/businessNumber")
    public RespVo<PartnerVo> businessNumberModify(@RequestBody @Validated @NotNull PartnerBusinessModifyVo modifyVo) {
        PartnerVo partner = partnerService.businessNumberModify(modifyVo);
        return new RespVo<>(partner);
    }

    //광고주 사용자 조회
    @GetMapping("/users")
    public RespVo<Page<UserVo>> selectAdvertiserUser(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "partnerSeq") long partnerSeq,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "userStatus", required = false) String userStatus
    ) {
        PartnerUserSearchVo userSearchVo = PartnerUserSearchVo.builder()
                .partnerSeq(partnerSeq)
                .userId(userId)
                .userName(userName)
                .userStatus(UserStatus.of(userStatus))
                .build();
        return new RespVo<>(partnerService.findPartnerUsers(userSearchVo, PageRequest.of(page - 1, size) ));
    }

    // 광고주 사용자 아이디 등록 여부 체크
    @GetMapping("/users/check")
    public RespVo<Boolean> duplicateCheckByUserId(
            @RequestParam(value = "partnerSeq") int partnerSeq,
            @RequestParam(value = "userId") String userId
    ) {
        boolean userCheck = partnerService.userCheck(partnerSeq, userId);
        return new RespVo<>(userCheck);
    }

    // 광고주 사용자 등록

    @PostMapping("/user/register")
    public RespVo<PartnerUserVo> userResister(@RequestBody @Validated @NotNull PartnerUserRegisterVo registerVo) {
        log.info("# registerVo = {}", registerVo);
        PartnerUserVo registered = partnerService.registerPartnerUser(registerVo);
        return new RespVo<>(registered);
    }

    // 광고주 사용자 수정
    @PutMapping("/user/modify")
    public RespVo<UserVo> userModify(@RequestBody @Validated @NotNull PartnerUserModifyVo modifyVo) {
        UserVo user = partnerService.modifyPartnerUser(modifyVo);
        return new RespVo<>(user);
    }

    @PutMapping("/user/disable")
    public RespVo<List<UserVo>> userDisabled(@RequestBody @Validated @NotNull PartnerUserStatusVo userDisabledVo) {
        UserVo user = partnerService.partnerUserDisable(userDisabledVo);

        return new RespVo<>(List.of(user));
    }

    @PutMapping("/user/enable")
    public RespVo<List<UserVo>> userEnabled(@RequestBody @Validated @NotNull PartnerUserStatusVo userDisabledVo) {
        UserVo user = partnerService.partnerUserEnable(userDisabledVo);

        return new RespVo<>(List.of(user));
    }
//
//
    // 매체사 계좌 정보
    @GetMapping("/accounts")
    public RespVo<Page<PartnerAccountVo>> selectPartnerAccount(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "partnerSeq") long partnerSeq,
            @RequestParam(value = "bankCode", required = false) String bankCode,
            @RequestParam(value = "bankAccount", required = false) String bankAccount,
            @RequestParam(value = "accountHolder", required = false) String accountHolder,
            @RequestParam(value = "accountUse", required = false) String accountUse

    ) {


        PartnerAccountSearchVo searchVo = PartnerAccountSearchVo.builder()
                .partnerSeq(partnerSeq)
                .bankCode(bankCode)
                .bankAccount(bankAccount)
                .accountHolder(accountHolder)
                .accountUse(accountUse)
                .build();

        Page<PartnerAccountVo> accounts = partnerAccountService.findAccounts(searchVo, PageRequest.of(page - 1 , size));
        return new RespVo<>(accounts);
    }


    // 광고주 계좌 정보 등록
    @GetMapping("/account/check")
    public RespVo<Boolean> accountCheck(
            @RequestParam(value = "partnerSeq") long partnerSeq,
            @RequestParam(value = "bankCode") String bankCode,
            @RequestParam(value = "bankAccount") String bankAccount
    ) {

        Boolean isRegistered = partnerAccountService.checkAccount(partnerSeq, bankCode, bankAccount);
        return new RespVo<>(isRegistered);
    }

    // 광고주 계좌 정보 등록
    @PostMapping("/account/register")
    public RespVo<PartnerAccountVo> accountRegister(
            @RequestBody @Validated @NotNull PartnerAccountRegisterVo registerVo
    ) {
        PartnerAccountVo account = partnerAccountService.register(registerVo);
        return new RespVo<>(account);
    }

    // 광고주 계좌 정보 삭제
    @DeleteMapping("/account/delete")
    public RespVo<String> accountDelete(
            @RequestBody @Validated @NotNull PartnerAccountDeleteVo deleteVo
    ) {
        partnerAccountService.delete(deleteVo);
        return new RespVo<>("success");
    }

    // 광고주 계좌 정보 수정
    @PutMapping("/account/modify")
    @Deprecated
    public RespVo<PartnerAccountVo> accountModify(
            @RequestBody @Validated @NotNull PartnerAccountModifyVo modifyVo
    ) {
        PartnerAccountVo account = partnerAccountService.modify(modifyVo);
        return new RespVo<>(account);
    }


    // 광고주 계좌 정보 수정 (계좌 활성화)
    @PutMapping("/account/used")
    public RespVo<PartnerAccountVo> accountUsed(
            @RequestBody @Validated @NotNull PartnerAccountUseVo modifyVo
    ) {
        PartnerAccountVo account = partnerAccountService.procUsed(modifyVo, true);
        return new RespVo<>(account);
    }

    // 광고주 계좌 정보 수정 (계좌 비 활성화)
    @PutMapping("/account/unused")
    public RespVo<PartnerAccountVo> accountUnUsed(
            @RequestBody @Validated @NotNull PartnerAccountUseVo modifyVo
    ) {
        PartnerAccountVo account = partnerAccountService.procUsed(modifyVo, false);
        return new RespVo<>(account);
    }
}

