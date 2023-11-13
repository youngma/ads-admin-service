package com.ads.main.schedule.quiz;


import com.ads.main.core.enums.common.SinkSchedule;
import com.ads.main.repository.template.RptQuizAdminDailyTemplate;
import com.ads.main.repository.template.RptQuizPartnerDailyTemplate;
import com.ads.main.service.ReportService;
import com.ads.main.vo.report.RptSinkTimeVo;
import com.ads.main.vo.report.resp.RptDashboard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuizAdminDailySink {

    private final RptQuizAdminDailyTemplate rptQuizAdminDailyTemplate;

    private final ReportService reportService;

    @Scheduled(cron = "0 */60 * * * *")
    public void rawSink() {

        RptDashboard dashboard = reportService.getDashboardToday();

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime data = LocalDateTime.now().minusHours(1);

        RptSinkTimeVo rptSinkTimeVo =  reportService.loadSinkSchedule(SinkSchedule.ADMIN_DAILY, data.format(formatter));

        LocalDateTime last = LocalDateTime.parse(rptSinkTimeVo.getLastSink(), formatter);

        String startDateH = last.format(formatter) + ":00:00";
        String endDateH = data.format(formatter)+ ":59:59";

        log.info("# Quiz Admin Daily Sink {} ~ {}", startDateH, endDateH);

        log.info("# Start Admin Daily Sink Count(Partner/Advertiser)");
        rptQuizAdminDailyTemplate.sinkCount(dashboard);

        log.info("# Start Admin Daily Sink Quiz Request");
        rptQuizAdminDailyTemplate.sinkReq(startDateH, endDateH);

        log.info("# Start Admin Daily Sink Quiz Impression(Partner");
        rptQuizAdminDailyTemplate.sinkImpressionPartner(startDateH, endDateH);

        log.info("# Start Admin Daily Sink Quiz Hint");
        rptQuizAdminDailyTemplate.sinkHint(startDateH, endDateH);

        log.info("# Start Admin Daily Sink Quiz Answer");
        rptQuizAdminDailyTemplate.sinkAnswer(startDateH, endDateH);

        rptSinkTimeVo.setLastSink(data.plusHours(1).format(formatter));
        reportService.saveSinkSchedule(rptSinkTimeVo);
    }

}
