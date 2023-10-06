package com.ads.main.schedule.quiz;


import com.ads.main.repository.template.RptQuizAdvertiserDailyTemplate;
import com.ads.main.repository.template.RptQuizPartnerDailyTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuizAdvertiserDailySink {

    private final RptQuizAdvertiserDailyTemplate rptQuizAdvertiserDailyTemplate;

    @Scheduled(cron = "1 * * * * ?")
    public void rawSink() {

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime data = LocalDateTime.now();

        String startDateH = data.format(formatter) + ":00:00";
        String endDateH = data.format(formatter)+ ":59:59";
        log.info("# Quiz Advertiser Daily Sink {} ~ {}", startDateH, endDateH);

        log.info("# Start Advertiser Daily Sink Quiz Request");
        rptQuizAdvertiserDailyTemplate.sinkReq(startDateH, endDateH);

        log.info("# Start Advertiser Daily Sink Quiz Impression");
        rptQuizAdvertiserDailyTemplate.sinkImpressionAdvertiser(startDateH, endDateH);

        log.info("# Start Advertiser Daily Sink Quiz Hint");
        rptQuizAdvertiserDailyTemplate.sinkHint(startDateH, endDateH);

        log.info("# Start Advertiser Daily Sink Quiz Answer");
        rptQuizAdvertiserDailyTemplate.sinkClick(startDateH, endDateH);

    }

}
