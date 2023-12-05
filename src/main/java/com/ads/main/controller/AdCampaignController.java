package com.ads.main.controller;

import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.core.enums.campaign.PaymentTerms;
import com.ads.main.core.vo.RespVo;
import com.ads.main.service.AdCampaignService;
import com.ads.main.vo.advertiser.campaign.req.*;
import com.ads.main.vo.advertiser.campaign.resp.AdCampaignMasterVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/admin/v1/campaign")
@RestController
@Slf4j
@Validated
public class AdCampaignController {

    private final AdCampaignService adCampaignService;

    @GetMapping("/search")
    public RespVo<Page<AdCampaignMasterVo>> selectAdvertisers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "advertiserSeq", required = false) Set<Long> advertiserSeq,
            @RequestParam(value = "campaignType", required = false) String campaignType,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            @RequestParam(value = "exposureStatus", required = false) String exposureStatus,
            @RequestParam(value = "paymentTerms", required = false) String paymentTerms
    ) {

        AdCampaignSearchVo adCampaignSearchVo = AdCampaignSearchVo.builder()
                .advertiserSeqList(advertiserSeq)
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .campaignType(CampaignType.of(campaignType))
                .exposureStatus(exposureStatus)
//                .targetUrl(targetUrl)
//                .goodsCode(goodsCode)
                .paymentTerms(PaymentTerms.of(paymentTerms))
                .build();

        log.info("# adCampaignSearchVo: {}", adCampaignSearchVo);

        Page<AdCampaignMasterVo> adCampaigns = adCampaignService.findAdCampaigns(adCampaignSearchVo, PageRequest.of(page - 1, size));
        return new RespVo<>(adCampaigns);
    }

    @PostMapping("/register")
    public RespVo<AdCampaignMasterVo> register(@RequestBody @Validated @NotNull AdCampaignMasterRegisterVo adCampaignVo) {
        AdCampaignMasterVo registered = adCampaignService.register(adCampaignVo);
        return new RespVo<>(registered);
    }

    @GetMapping("/mobi/already_registered")
    public RespVo<HashSet<String>> alreadyRegisteredCheckByMObi(
            @RequestParam(value = "ifAdCode") String ifAdCode,
            @RequestParam(value = "keys") HashSet<String> keys
    ) {
        return new RespVo<>(adCampaignService.mobiAdsAlreadyRegisteredCheck(ifAdCode, keys));
    }


    @PutMapping("/modify")
    public RespVo<AdCampaignMasterVo> modify(@RequestBody @Validated @NotNull AdCampaignMasterModifyVo adCampaignVo) {
        AdCampaignMasterVo registered = adCampaignService.modify(adCampaignVo);
        return new RespVo<>(registered);
    }

    @PutMapping("/approval")
    public RespVo<AdCampaignMasterVo> approval(@RequestBody @Validated @NotNull AdCampaignStatusVo adCampaignStatusVo) {
        AdCampaignMasterVo registered = adCampaignService.status(CampaignStatus.Approval, adCampaignStatusVo);
        return new RespVo<>(registered);
    }

    @PutMapping("/reject")
    public RespVo<AdCampaignMasterVo> reject(@RequestBody @Validated @NotNull AdCampaignStatusVo adCampaignStatusVo) {
        AdCampaignMasterVo registered = adCampaignService.status(CampaignStatus.Reject, adCampaignStatusVo);
        return new RespVo<>(registered);
    }

    @PutMapping("/hold")
    public RespVo<AdCampaignMasterVo> hold(@RequestBody @Validated @NotNull AdCampaignStatusVo adCampaignStatusVo) {
        AdCampaignMasterVo registered = adCampaignService.status(CampaignStatus.Hold, adCampaignStatusVo);
        return new RespVo<>(registered);
    }

    @PutMapping("/exposure")
    public RespVo<AdCampaignMasterVo> exposure(@RequestBody @Validated @NotNull AdCampaignStatusVo adCampaignStatusVo) {
        AdCampaignMasterVo registered = adCampaignService.status(CampaignStatus.Exposure, adCampaignStatusVo);
        return new RespVo<>(registered);
    }

    @PutMapping("/non-exposure")
    public RespVo<AdCampaignMasterVo> nonExposure(@RequestBody @Validated @NotNull AdCampaignStatusVo adCampaignStatusVo) {
        AdCampaignMasterVo registered = adCampaignService.status(CampaignStatus.NonExposure, adCampaignStatusVo);
        return new RespVo<>(registered);
    }
}
