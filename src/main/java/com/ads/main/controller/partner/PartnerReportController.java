package com.ads.main.controller.partner;


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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/partner/v1/report")
@RestController
@Slf4j
@Validated
public class PartnerReportController {


    private final ReportService reportService;
    private final ExcelUtilsV1 excelUtils;

    private final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    @GetMapping("/quiz/partner/daily")
    public RespVo<Page<? extends DailyReportVo>> rptQuizPartner(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("startDate") String start,
            @RequestParam("endDate") String end,
            @RequestParam(value = "partnerSeq", required = false) HashSet<Long> partnerSeq,
            @RequestParam(value = "groupName", required = false) String groupName,
            @RequestParam(value = "groupCode", required = false) String groupCode,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        log.info("# userDetails.getUsername(), {}", userDetails);

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
