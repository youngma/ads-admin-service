package com.ads.main.service.admin;

import com.ads.main.core.enums.common.SinkSchedule;
import com.ads.main.entity.RptSinkTimeEntity;
import com.ads.main.entity.mapper.RptSinkTimeConvert;
import com.ads.main.repository.jpa.RptSinkTimeRepository;
import com.ads.main.repository.querydsl.QRptQuizRawRepository;
import com.ads.main.repository.querydsl.QRptQuizReportRepository;
import com.ads.main.vo.admin.report.RptSinkTimeVo;
import com.ads.main.vo.admin.report.req.RptSearchVo;
import com.ads.main.vo.admin.report.resp.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {

    private final RptSinkTimeRepository rptSinkTimeRepository;
    private final RptSinkTimeConvert rptSinkTimeConvert;

    private final ReportCacheService reportCacheService;

    private final QRptQuizReportRepository qRptQuizReportRepository;
    private final QRptQuizRawRepository qRptQuizRawRepository;

    public RptDashboard getDashboardToday() {

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime data = LocalDateTime.now();


        return reportCacheService.findDashboard(data.format(formatter));
    }

    public RptDashboard getDashboard(String start, String end) {

        RptDashboard dashboard = getDashboardToday();
        List<RptQuizDailyVo> dailyList = reportCacheService.findDashboardList(start, end);
        dashboard.setRptQuizDailyList(dailyList);

        dashboard.setLastUpdate(loadSinkSchedule(SinkSchedule.QUIZ_DAILY));

        return dashboard;
    }

    public Page<? extends DailyReportVo> searchReportByDaily(RptSearchVo rptSearchVo, Pageable pageable) {
        return switch (rptSearchVo.getRole()) {
            case ADMIN -> qRptQuizReportRepository.searchRptQuizAdminDaily(rptSearchVo, pageable);
            case PARTNER -> qRptQuizReportRepository.searchRptQuizPartnerDaily(rptSearchVo, pageable);
            case ADVERTISER -> qRptQuizReportRepository.searchRptQuizAdvertiserDaily(rptSearchVo, pageable);
            case USER -> qRptQuizRawRepository.searchRptQuizUserDaily(rptSearchVo, pageable);
            case X_CODE -> qRptQuizReportRepository.searchRptQuizXCodeDaily(rptSearchVo, pageable);
        };
    }


    public DailyReportVo searchReportByDailySummary(RptSearchVo rptSearchVo) {
        return switch (rptSearchVo.getRole()) {
            case ADMIN -> qRptQuizReportRepository.searchRptQuizAdminDailySummary(rptSearchVo);
            case PARTNER -> qRptQuizReportRepository.searchRptQuizPartnerDailySummary(rptSearchVo);
            case ADVERTISER -> qRptQuizReportRepository.searchRptQuizAdvertiserSummary(rptSearchVo);
            case USER -> qRptQuizRawRepository.searchRptQuizUserDailySummery(rptSearchVo);
            case X_CODE -> qRptQuizReportRepository.searchRptQuizXCodeDailySummary(rptSearchVo);

        };
    }

    public List<? extends DailyReportVo> searchReportByDailyExcel(RptSearchVo rptSearchVo) {
        return switch (rptSearchVo.getRole()) {
            case ADMIN -> qRptQuizReportRepository.searchRptQuizAdminDailyExcel(rptSearchVo);
            case PARTNER -> qRptQuizReportRepository.searchRptQuizPartnerDailyExcel(rptSearchVo);
            case ADVERTISER -> qRptQuizReportRepository.searchRptQuizAdvertiserDailyExcel(rptSearchVo);
            case USER -> qRptQuizRawRepository.searchRptQuizUserDailyExcel(rptSearchVo);
            case X_CODE -> qRptQuizReportRepository.searchRptQuizXCodeDailyExcel(rptSearchVo);
        };
    }

    public RptSinkTimeVo loadSinkSchedule(SinkSchedule schedule, String defaultSinkTime) {

        Optional<RptSinkTimeEntity> rptSinkTimeEntityOptional = rptSinkTimeRepository.findByScheduleName(schedule.getName());
        RptSinkTimeEntity rptSinkTimeEntity = rptSinkTimeEntityOptional.orElseGet( () -> {
            RptSinkTimeEntity newRptSinkTimeEntity = new RptSinkTimeEntity();
            newRptSinkTimeEntity.setScheduleName(schedule.getName());
            newRptSinkTimeEntity.setLastSink(defaultSinkTime);
            return newRptSinkTimeEntity;
        });

        return rptSinkTimeConvert.toDto(rptSinkTimeEntity);
    }

    public RptSinkTimeVo loadSinkSchedule(SinkSchedule schedule) {
        Optional<RptSinkTimeEntity> rptSinkTimeEntityOptional = rptSinkTimeRepository.findByScheduleName(schedule.getName());
        return rptSinkTimeEntityOptional.map(rptSinkTimeConvert::toDto).orElse(null);
    }

    public void saveSinkSchedule(RptSinkTimeVo rptSinkTimeVo) {
        RptSinkTimeEntity rptSinkTimeEntity  =  rptSinkTimeConvert.toEntity(rptSinkTimeVo);
        rptSinkTimeRepository.save(rptSinkTimeEntity);
    }

}

