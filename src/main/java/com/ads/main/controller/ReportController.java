package com.ads.main.controller;


import com.ads.main.core.security.config.dto.Role;
import com.ads.main.core.vo.FileVo;
import com.ads.main.core.vo.RespVo;
import com.ads.main.service.ReportService;
import com.ads.main.vo.report.req.RptSearchVo;
import com.ads.main.vo.report.resp.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/admin/v1/report")
@RestController
@Slf4j
@Validated
public class ReportController {


    private final ReportService reportService;

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
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {

        RptSearchVo rptSearchVo = RptSearchVo.builder()
                .role(Role.ADVERTISER)
                .searchStartDt(start)
                .searchEndDt(end)
                .build();

        Page<? extends DailyReportVo> list = reportService.searchReportByDaily(rptSearchVo, PageRequest.of(page - 1, size));

        return new RespVo<>(list);
    }

    @GetMapping("/quiz/partner/daily")
    public RespVo<Page<? extends DailyReportVo>> rptQuizPartner(
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
}
