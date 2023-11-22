package com.ads.main.schedule.quiz;


import com.ads.main.core.enums.common.SinkSchedule;
import com.ads.main.repository.template.RptQuizPartnerDailyTemplate;
import com.ads.main.service.ReportService;
import com.ads.main.vo.report.RptSinkTimeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuizPartnerDailySink {

    private final RptQuizPartnerDailyTemplate rptQuizPartnerDailyTemplate;

    private final ReportService reportService;

    @Scheduled(cron = "0 */60 * * * *")
    public void rawSink() {

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime data = LocalDateTime.now().minusHours(1);

        RptSinkTimeVo rptSinkTimeVo =  reportService.loadSinkSchedule(SinkSchedule.PARTNER_DAILY, data.format(formatter));

        LocalDateTime last = LocalDateTime.parse(rptSinkTimeVo.getLastSink(), formatter);

        String startDateH = last.format(formatter) + ":00:00";
        String endDateH = data.format(formatter)+ ":59:59";
        log.info("# Quiz Partner Daily Sink {} ~ {}", startDateH, endDateH);

        log.info("# Start Partner Daily Sink Quiz Request");
        rptQuizPartnerDailyTemplate.sinkReq(startDateH, endDateH);

        log.info("# Start Partner Daily Sink Quiz Impression(Partner");
        rptQuizPartnerDailyTemplate.sinkImpressionPartner(startDateH, endDateH);

        log.info("# Start Partner Daily Sink Quiz Hint");
        rptQuizPartnerDailyTemplate.sinkHint(startDateH, endDateH);

        log.info("# Start Partner Daily Sink Quiz Answer");
        rptQuizPartnerDailyTemplate.sinkAnswer(startDateH, endDateH);

        rptSinkTimeVo.setLastSink(data.plusHours(1).format(formatter));
        reportService.saveSinkSchedule(rptSinkTimeVo);
    }

}
