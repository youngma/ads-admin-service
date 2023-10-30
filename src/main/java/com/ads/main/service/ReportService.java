package com.ads.main.service;

import com.ads.main.core.security.config.dto.Role;
import com.ads.main.repository.querydsl.QRptQuizRepository;
import com.ads.main.vo.report.req.RptSearchVo;
import com.ads.main.vo.report.resp.RptDashboard;
import com.ads.main.vo.report.resp.RptQuizAdvertiserDailyVo;
import com.ads.main.vo.report.resp.RptQuizDailyVo;
import com.ads.main.vo.report.resp.RptQuizPartnerDailyVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {


    private final ReportCacheService reportCacheService;

    private final QRptQuizRepository qRptQuizRepository;


    public RptDashboard getDashboard(String start, String end) {

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime data = LocalDateTime.now();

        RptDashboard dashboard = reportCacheService.findDashboard(data.format(formatter));
        List<RptQuizDailyVo> dailyList = reportCacheService.findDashboardList(start, end);
        dashboard.setRptQuizDailyList(dailyList);

        return dashboard;
    }


    public Page<RptQuizAdvertiserDailyVo> searchAdvertiserRpt(RptSearchVo rptSearchVo, Pageable pageable) {
        return switch (rptSearchVo.getRole()) {
            case ADMIN -> null;
            case PARTNER -> null;
            case ADVERTISER -> qRptQuizRepository.searchRptQuizAdvertiserDaily(rptSearchVo, pageable);
        };
    }

    public Page<RptQuizPartnerDailyVo> searchPartnerRpt(RptSearchVo rptSearchVo, Pageable pageable) {
        return switch (rptSearchVo.getRole()) {
            case ADMIN -> null;
            case PARTNER -> qRptQuizRepository.searchRptQuizPartnerDaily(rptSearchVo, pageable);
            case ADVERTISER -> null;
        };
    }
}

