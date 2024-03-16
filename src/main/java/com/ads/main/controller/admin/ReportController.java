package com.ads.main.controller.admin;


import com.ads.main.core.security.config.dto.Role;
import com.ads.main.core.utils.ExcelUtilsV1;
import com.ads.main.core.vo.RespVo;
import com.ads.main.service.admin.ReportService;
import com.ads.main.vo.admin.report.excel.*;
import com.ads.main.vo.admin.report.req.RptSearchVo;
import com.ads.main.vo.admin.report.resp.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/admin/v1/report")
@RestController
@Slf4j
@Validated
public class ReportController {


    private final ReportService reportService;
    private final ExcelUtilsV1 excelUtils;

    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    @GetMapping("/dashboard")
    public RespVo<RptDashboard> rptDashBoard(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String start,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  String end
    ) {
        return new RespVo<>(reportService.getDashboard(start, end));
    }

    @GetMapping("/quiz/advertiser/daily")
    public RespVo<Page<? extends DailyReportVo>> rptQuizAdvertiser(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "advertiserSeq", required = false) HashSet<Long> advertiserSeq,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.ADVERTISER)
                .searchStartDt(start)
                .searchEndDt(end)
                .advertiserSeq(advertiserSeq)
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .build();

        Page<? extends DailyReportVo> list = reportService.searchReportByDaily(rptSearchVo, PageRequest.of(page - 1, size));

        return new RespVo<>(list);
    }

    @GetMapping("/quiz/partner/daily")
    public RespVo<Page<? extends DailyReportVo>> rptQuizPartner(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "partnerSeq", required = false) HashSet<Long> partnerSeq,
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "groupCode", required = false) String groupCode,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.PARTNER)
                .searchStartDt(start)
                .searchEndDt(end)
                .partnerSeq(partnerSeq)
                .groupName(groupName)
                .groupCode(groupCode)
                .build();

        Page<? extends DailyReportVo> list = reportService.searchReportByDaily(rptSearchVo, PageRequest.of(page - 1, size));


        return new RespVo<>(list);
    }

    @GetMapping("/quiz/admin/daily")
    public RespVo<Page<? extends DailyReportVo>> rptQuizAdmin(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.ADMIN)
                .searchStartDt(start)
                .searchEndDt(end)

                .build();

        Page<? extends DailyReportVo> list = reportService.searchReportByDaily(rptSearchVo, PageRequest.of(page - 1, size));


        return new RespVo<>(list);
    }

    @GetMapping("/quiz/user/raw")
    public RespVo<Page<? extends DailyReportVo>> rptQuizUser(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "userKey", required = false) String userKey,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.USER)
                .searchStartDt(start)
                .searchEndDt(end)
                .userKey(userKey)
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .build();

        Page<? extends DailyReportVo> list = reportService.searchReportByDaily(rptSearchVo, PageRequest.of(page - 1, size));


        return new RespVo<>(list);
    }

    @GetMapping("/quiz/xcode")
    public RespVo<Page<? extends DailyReportVo>> rptQuizXCode(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            @RequestParam(value = "groupCode", required = false) String groupCode,
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.X_CODE)
                .searchStartDt(start)
                .searchEndDt(end)
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .groupCode(groupCode)
                .groupName(groupName)
                .build();

        Page<? extends DailyReportVo> list = reportService.searchReportByDaily(rptSearchVo, PageRequest.of(page - 1, size));


        return new RespVo<>(list);
    }

    @GetMapping("/quiz/advertiser/daily/summary")
    public RespVo<? extends DailyReportVo> rptQuizAdvertiserSummary(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "advertiserSeq", required = false) HashSet<Long> advertiserSeq,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.ADVERTISER)
                .searchStartDt(start)
                .searchEndDt(end)
                .advertiserSeq(advertiserSeq)
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .build();

        DailyReportVo summary = reportService.searchReportByDailySummary(rptSearchVo);

        return new RespVo<>(summary);
    }
//
    @GetMapping("/quiz/partner/daily/summary")
    public RespVo<? extends DailyReportVo> rptQuizPartnerSummery(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.PARTNER)
                .searchStartDt(start)
                .searchEndDt(end)
                .build();

        DailyReportVo summary = reportService.searchReportByDailySummary(rptSearchVo);


        return new RespVo<>(summary);
    }

    @GetMapping("/quiz/admin/daily/summary")
    public RespVo<? extends DailyReportVo> rptQuizAdminSummary(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.ADMIN)
                .searchStartDt(start)
                .searchEndDt(end)
                .build();

        DailyReportVo summary = reportService.searchReportByDailySummary(rptSearchVo);

        return new RespVo<>(summary);
    }
    @GetMapping("/quiz/user/raw/summary")
    public RespVo<? extends DailyReportVo> rptQuizUserSummary(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "userKey", required = false) String userKey,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.USER)
                .searchStartDt(start)
                .searchEndDt(end)
                .userKey(userKey)
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .build();

        DailyReportVo summary = reportService.searchReportByDailySummary(rptSearchVo);


        return new RespVo<>(summary);
    }


    @GetMapping("/quiz/xcode/summary")
    public RespVo<? extends DailyReportVo> rptQuizXCodeSummary(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            @RequestParam(value = "groupCode", required = false) String groupCode,
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.X_CODE)
                .searchStartDt(start)
                .searchEndDt(end)
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .groupCode(groupCode)
                .groupName(groupName)
                .build();

        DailyReportVo summary = reportService.searchReportByDailySummary(rptSearchVo);

        return new RespVo<>(summary);
    }

    @GetMapping("/quiz/advertiser/daily/excel")
    public void rptQuizAdvertiserExcel(
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
                .advertiserSeq(advertiserSeq)
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

    @GetMapping("/quiz/partner/daily/excel")
    public void rptQuizPartnerExcel(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "partnerSeq", required = false) HashSet<Long> partnerSeq,
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "groupCode", required = false) String groupCode,
            HttpServletResponse response
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.PARTNER)
                .searchStartDt(start)
                .searchEndDt(end)
                .partnerSeq(partnerSeq)
                .groupName(groupName)
                .groupCode(groupCode)
                .build();


        List<RptQuizPartnerExcelVo> list = reportService.searchReportByDailyExcel(rptSearchVo)
                .stream()
                .map(t -> {
                    RptQuizPartnerDailyVo rptQuizAdvertiserDailyVo = (RptQuizPartnerDailyVo) t;
                            return new RptQuizPartnerExcelVo(
                                    rptQuizAdvertiserDailyVo.getRptDate(),
                                    rptQuizAdvertiserDailyVo.getBusinessName(),
                                    rptQuizAdvertiserDailyVo.getGroupCode(),
                                    rptQuizAdvertiserDailyVo.getGroupName(),
                                    rptQuizAdvertiserDailyVo.getImpressionCnt(),
                                    rptQuizAdvertiserDailyVo.getHintCnt(),
                                    rptQuizAdvertiserDailyVo.getClickCnt(),
                                    rptQuizAdvertiserDailyVo.getPartnerCommission(),
                                    rptQuizAdvertiserDailyVo.getUserCommission()
                            );
                        }
                ).toList();

        excelUtils.download(RptQuizPartnerExcelVo.class, list, LocalDateTime.now().format(formatter), response);
    }

    @GetMapping("/quiz/admin/daily/excel")
    public void rptQuizAdminExcel(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            HttpServletResponse response
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.ADMIN)
                .searchStartDt(start)
                .searchEndDt(end)
                .build();


        List<RptQuizAdminExcelVo> list = reportService.searchReportByDailyExcel(rptSearchVo)
                .stream()
                .map(t -> {
                    RptQuizAdminDailyVo rptQuizAdvertiserDailyVo = (RptQuizAdminDailyVo) t;
                            return new RptQuizAdminExcelVo(
                                    rptQuizAdvertiserDailyVo.getRptDate(),
                                    rptQuizAdvertiserDailyVo.getAdvertiserCnt(),
                                    rptQuizAdvertiserDailyVo.getCampaignCnt(),
                                    rptQuizAdvertiserDailyVo.getPartnerCnt(),
                                    rptQuizAdvertiserDailyVo.getAdGroupCnt(),
                                    rptQuizAdvertiserDailyVo.getImpressionCnt(),
                                    rptQuizAdvertiserDailyVo.getAnswerCnt(),
                                    rptQuizAdvertiserDailyVo.getHintCnt(),
                                    rptQuizAdvertiserDailyVo.getClickCnt(),
                                    rptQuizAdvertiserDailyVo.getAdCost(),
                                    rptQuizAdvertiserDailyVo.getPartnerCommission(),
                                    rptQuizAdvertiserDailyVo.getUserCommission()
                            );
                        }
                ).toList();

        excelUtils.download(RptQuizAdminExcelVo.class, list, LocalDateTime.now().format(formatter), response);

    }

    @GetMapping("/quiz/user/raw/excel")
    public void rptQuizUserExcel(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "userKey", required = false) String userKey,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            HttpServletResponse response
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.USER)
                .searchStartDt(start)
                .searchEndDt(end)
                .userKey(userKey)
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .build();

        List<RptQuizUserRawExcelVo> list = reportService.searchReportByDailyExcel(rptSearchVo)
                .stream()
                .map(t -> {
                            RptQuizRawVo rptQuizAdvertiserDailyVo = (RptQuizRawVo) t;
                            return new RptQuizUserRawExcelVo(
                                    rptQuizAdvertiserDailyVo.getRptDate(),
                                    rptQuizAdvertiserDailyVo.getUserKey(),
                                    rptQuizAdvertiserDailyVo.getAdvertiserName(),
                                    rptQuizAdvertiserDailyVo.getCampaignName(),
                                    rptQuizAdvertiserDailyVo.getGroupName(),
                                    rptQuizAdvertiserDailyVo.getCampaignCode(),
                                    rptQuizAdvertiserDailyVo.getGroupCode(),
                                    rptQuizAdvertiserDailyVo.getGroupName(),

                                    rptQuizAdvertiserDailyVo.getDetailAt(),
                                    rptQuizAdvertiserDailyVo.getHintAt(),
                                    rptQuizAdvertiserDailyVo.getClickAt(),
                                    rptQuizAdvertiserDailyVo.getAnswerAt(),

                                    rptQuizAdvertiserDailyVo.getAdCost(),
                                    rptQuizAdvertiserDailyVo.getPartnerCommission(),
                                    rptQuizAdvertiserDailyVo.getUserCommission(),
                                    rptQuizAdvertiserDailyVo.getAdReword()
                            );
                        }
                ).toList();

        excelUtils.download(RptQuizUserRawExcelVo.class, list, LocalDateTime.now().format(formatter), response);
    }
    @GetMapping("/quiz/xcode/daily/excel")
    public void rptQuizXCodeExcel(
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "campaignCode", required = false) String campaignCode,
            @RequestParam(value = "groupCode", required = false) String groupCode,
            @RequestParam(value = "groupName", required = false) String groupName,
            HttpServletResponse response
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.X_CODE)
                .searchStartDt(start)
                .searchEndDt(end)
                .campaignName(campaignName)
                .campaignCode(campaignCode)
                .groupCode(groupCode)
                .groupName(groupName)
                .build();

        List<RptQuizXCodeExcelVo> list = reportService.searchReportByDailyExcel(rptSearchVo)
                .stream()
                .map(t -> {
                    RptQuizXCodeVo rptQuizXCodeVo = (RptQuizXCodeVo) t;
                            return new RptQuizXCodeExcelVo(
                                    rptQuizXCodeVo.getRptDate(),
                                    rptQuizXCodeVo.getPartnerName(),
                                    rptQuizXCodeVo.getGroupCode(),
                                    rptQuizXCodeVo.getGroupName(),
                                    rptQuizXCodeVo.getAdvertiserName(),
                                    rptQuizXCodeVo.getCampaignCode(),
                                    rptQuizXCodeVo.getCampaignName(),

                                    rptQuizXCodeVo.getImpressionCnt(),
                                    rptQuizXCodeVo.getAnswerCnt(),
                                    rptQuizXCodeVo.getHintCnt(),
                                    rptQuizXCodeVo.getClickCnt(),

                                    rptQuizXCodeVo.getAdCost(),
                                    rptQuizXCodeVo.getPartnerCommission(),
                                    rptQuizXCodeVo.getUserCommission()
                            );
                        }
                ).toList();

        excelUtils.download(RptQuizXCodeExcelVo.class, list, LocalDateTime.now().format(formatter), response);
    }
}
