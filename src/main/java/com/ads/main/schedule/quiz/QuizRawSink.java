package com.ads.main.schedule.quiz;


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
public class QuizRawSink {

    private final RptQuizRawTemplate rptQuizRawTemplate;

    @Scheduled(cron = "1 * * * * ?")
    public void rawSink() {

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime data = LocalDateTime.now().minusSeconds(10);

        String startDateH = data.format(formatter) + ":00";
        String endDateH = data.format(formatter)+ ":59";
        log.info("# QuizRawSink {} ~ {}", startDateH, endDateH);

        log.info("# Start Sink Quiz Request");
        rptQuizRawTemplate.sinkReq(startDateH, endDateH);

        log.info("# Start Sink Quiz Impression(Partner");
        rptQuizRawTemplate.sinkImpressionPartner(startDateH, endDateH);

        log.info("# Start Sink Quiz Impression(Advertiser");
        rptQuizRawTemplate.sinkImpressionAdvertiser(startDateH, endDateH);

        log.info("# Start Sink Quiz Hint");
        rptQuizRawTemplate.sinkHint(startDateH, endDateH);

        log.info("# Start Sink Quiz Click");
        rptQuizRawTemplate.sinkClick(startDateH, endDateH);

        log.info("# Start Sink Quiz Answer");
        rptQuizRawTemplate.sinkAnswer(startDateH, endDateH);

    }

}
