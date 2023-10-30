package com.ads.main.schedule.quiz;


import com.ads.main.repository.template.RptQuizPartnerDailyTemplate;
import com.ads.main.repository.template.RptQuizRawTemplate;
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

    @Scheduled(cron = "0 */60 * * * *")
    public void rawSink() {

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime data = LocalDateTime.now().minusHours(1);

        String startDateH = data.format(formatter) + ":00:00";
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

    }

}
