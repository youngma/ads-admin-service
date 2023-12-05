package com.ads.main.schedule.quiz;


import com.ads.main.core.enums.common.SinkSchedule;
import com.ads.main.repository.template.RptQuizAdminDailyTemplate;
import com.ads.main.repository.template.RptQuizXCodeDailyTemplate;
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
public class QuizXCodeDailySink {

    private final RptQuizXCodeDailyTemplate rptQuizXCodeDailyTemplate;

    private final ReportService reportService;

    @Scheduled(cron = "0 */60 * * * *")
    public void rawSink() {

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime data = LocalDateTime.now().minusHours(1);

        RptSinkTimeVo rptSinkTimeVo =  reportService.loadSinkSchedule(SinkSchedule.X_CODE_DAILY, data.format(formatter));

        LocalDateTime last = LocalDateTime.parse(rptSinkTimeVo.getLastSink(), formatter);

        String startDateH = last.format(formatter) + ":00:00";
        String endDateH = data.format(formatter)+ ":59:59";

        log.info("# Quiz X_CODE Daily Sink {} ~ {}", startDateH, endDateH);

        log.info("# Start X_CODE Daily Sink Quiz Request");
        rptQuizXCodeDailyTemplate.sinkReq(startDateH, endDateH);

        log.info("# Start X_CODE Daily Sink Quiz Impression(Advertiser");
        rptQuizXCodeDailyTemplate.sinkImpressionAdvertiser(startDateH, endDateH);

        log.info("# Start X_CODE Daily Sink Quiz Impression(Partner");
        rptQuizXCodeDailyTemplate.sinkImpressionPartner(startDateH, endDateH);

        log.info("# Start X_CODE Daily Sink Quiz Hint");
        rptQuizXCodeDailyTemplate.sinkHint(startDateH, endDateH);

        log.info("# Start X_CODE Daily Sink Quiz Answer");
        rptQuizXCodeDailyTemplate.sinkAnswer(startDateH, endDateH);

        log.info("# Start X_CODE Daily Sink Quiz Click");
        rptQuizXCodeDailyTemplate.sinkClick(startDateH, endDateH);

        rptSinkTimeVo.setLastSink(data.plusHours(1).format(formatter));
        reportService.saveSinkSchedule(rptSinkTimeVo);
    }

}
