package com.ads.main.controller.advertiser;


import com.ads.main.core.security.config.dto.LoginInfo;
import com.ads.main.core.security.config.dto.Role;
import com.ads.main.core.utils.ExcelUtilsV1;
import com.ads.main.core.vo.RespVo;
import com.ads.main.service.admin.ReportService;
import com.ads.main.vo.admin.report.excel.RptQuizAdvertiserExcelVo;
import com.ads.main.vo.admin.report.excel.RptQuizPartnerExcelVo;
import com.ads.main.vo.admin.report.excel.RptQuizUserRawExcelVo;
import com.ads.main.vo.admin.report.excel.RptQuizXCodeExcelVo;
import com.ads.main.vo.admin.report.req.RptSearchVo;
import com.ads.main.vo.admin.report.resp.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/advertiser/v1/report")
@RestController
@Slf4j
@Validated
public class AdvertiserReportController {


    private final ReportService reportService;
    private final ExcelUtilsV1 excelUtils;

    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

    @GetMapping("/quiz/advertiser/daily")
    public RespVo<Page<? extends DailyReportVo>> rptQuizAdvertiser(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            Authentication authentication,
//            @RequestParam(value = "advertiserSeq", required = false) HashSet<Long> advertiserSeq,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.ADVERTISER)
                .searchStartDt(start)
                .searchEndDt(end)
                .advertiserSeq(convetPrincipalHashSet(authentication))
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .build();

        Page<? extends DailyReportVo> list = reportService.searchReportByDaily(rptSearchVo, PageRequest.of(page - 1, size));

        return new RespVo<>(list);
    }

    @GetMapping("/quiz/advertiser/daily/summary")
    public RespVo<? extends DailyReportVo> rptQuizAdvertiserSummary(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            Authentication authentication,

//            @RequestParam(value = "advertiserSeq", required = false) HashSet<Long> advertiserSeq,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.ADVERTISER)
                .searchStartDt(start)
                .searchEndDt(end)
                .advertiserSeq(convetPrincipalHashSet(authentication))
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .build();

        DailyReportVo summary = reportService.searchReportByDailySummary(rptSearchVo);

        return new RespVo<>(summary);
    }

    @GetMapping("/quiz/advertiser/daily/excel")
    public void rptQuizAdvertiserExcel(
            Authentication authentication,

            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "advertiserSeq", required = false) HashSet<Long> advertiserSeq,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            HttpServletResponse response
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.ADVERTISER)
                .searchStartDt(start)
                .searchEndDt(end)
                .advertiserSeq(convetPrincipalHashSet(authentication))
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .build();

        List<RptQuizAdvertiserExcelVo> list = reportService.searchReportByDailyExcel(rptSearchVo)
                .stream()
                .map(t -> {
                            RptQuizAdvertiserDailyVo rptQuizAdvertiserDailyVo = (RptQuizAdvertiserDailyVo) t;
                            return new RptQuizAdvertiserExcelVo(
                                    rptQuizAdvertiserDailyVo.getRptDate(),
                                    rptQuizAdvertiserDailyVo.getBusinessName(),
                                    rptQuizAdvertiserDailyVo.getCampaignCode(),
                                    rptQuizAdvertiserDailyVo.getCampaignName(),
                                    rptQuizAdvertiserDailyVo.getImpressionCnt(),
                                    rptQuizAdvertiserDailyVo.getHintCnt(),
                                    rptQuizAdvertiserDailyVo.getClickCnt(),
                                    rptQuizAdvertiserDailyVo.getOriginAdCost(),
                                    rptQuizAdvertiserDailyVo.getAdCost()
                            );
                        }
                ).toList();

        excelUtils.download(RptQuizAdvertiserExcelVo.class, list, LocalDateTime.now().format(formatter), response);
    }
    private HashSet<Long> convetPrincipalHashSet(Authentication authentication) {
        try {

            LoginInfo credentials = (LoginInfo) authentication.getCredentials();

            HashSet<Long> hashSet = new HashSet<>();
            hashSet.add(credentials.getAdvertiserSeq());
            return hashSet;

        } catch (Exception e) {
            throw new AuthorizationServiceException("광고주 로그인 정보가 없습니다.");
        }
    }
}
