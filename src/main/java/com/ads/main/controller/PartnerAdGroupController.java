package com.ads.main.controller;

import com.ads.main.core.enums.advertiser.AdGroupStatus;
import com.ads.main.core.vo.RespVo;
import com.ads.main.service.PartnerAdGroupService;
import com.ads.main.service.PartnerService;
import com.ads.main.vo.partner.adGroup.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/admin/v1/partner/ad_groups")
@RestController
@Slf4j
@Validated
public class PartnerAdGroupController {

    private final PartnerService partnerService;
    private final PartnerAdGroupService partnerAdGroupService;

    // 매체사 광고 그룹 조회
    @GetMapping("/search")
    public RespVo<Page<PartnerAdGroupVo>> selectAdGroups(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "partnerSeq", required = false) Set<Long> partnerSeq,
            @RequestParam(value = "groupSeq", required = false) Long groupSeq,
            @RequestParam(value = "adType", required = false) String adType,
            @RequestParam(value = "groupCode", required = false) String groupCode,
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "groupStatus", required = false) String groupStatus
    ) {

        PartnerAdGroupSearchVo adGroupSearchVo = PartnerAdGroupSearchVo.builder()
                .partnerSeq(partnerSeq)
                .groupSeq(groupSeq)
                .adType(adType)
                .groupCode(groupCode)
                .groupName(groupName)
                .groupStatus(groupStatus)
                .build();

        Page<PartnerAdGroupVo> adGroups = partnerAdGroupService.findAdGroups(adGroupSearchVo, PageRequest.of(page - 1, size));
        return new RespVo<>(adGroups);
    }


    @PostMapping("/register")
    public RespVo<PartnerAdGroupVo> register(@RequestBody @Validated @NotNull PartnerAdGroupRegisterVo partnerAdGroupVo) {
        PartnerAdGroupVo registered = partnerAdGroupService.register(partnerAdGroupVo);
        return new RespVo<>(registered);
    }


    @PutMapping("/modify")
    public RespVo<PartnerAdGroupVo> modify(@RequestBody @Validated @NotNull PartnerAdGroupModifyVo modifyVo) {
        PartnerAdGroupVo registered = partnerAdGroupService.modify(modifyVo);
        return new RespVo<>(registered);
    }


    @PutMapping("/approval")
    public RespVo<PartnerAdGroupVo> approval(@RequestBody @Validated @NotNull PartnerAdGroupStatusVo partnerAdGroupStatusVo) {
        PartnerAdGroupVo registered = partnerAdGroupService.status(AdGroupStatus.Approval, partnerAdGroupStatusVo);
        return new RespVo<>(registered);
    }

    @PutMapping("/reject")
    public RespVo<PartnerAdGroupVo> reject(@RequestBody @Validated @NotNull PartnerAdGroupStatusVo partnerAdGroupStatusVo) {
        PartnerAdGroupVo registered = partnerAdGroupService.status(AdGroupStatus.Reject, partnerAdGroupStatusVo);
        return new RespVo<>(registered);
    }

    @PutMapping("/hold")
    public RespVo<PartnerAdGroupVo> hold(@RequestBody @Validated @NotNull PartnerAdGroupStatusVo partnerAdGroupStatusVo) {
        PartnerAdGroupVo registered = partnerAdGroupService.status(AdGroupStatus.Hold, partnerAdGroupStatusVo);
        return new RespVo<>(registered);
    }
}


