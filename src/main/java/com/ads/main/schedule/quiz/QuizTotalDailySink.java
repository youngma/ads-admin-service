package com.ads.main.schedule.quiz;


import com.ads.main.repository.template.RptQuizDailyTemplate;
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
public class QuizTotalDailySink {

    private final RptQuizDailyTemplate rptQuizDailyTemplate;

    @Scheduled(cron = "0 10 0 * * ?")
    public void rawSink() {

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        LocalDateTime data = LocalDateTime.now().minusDays(1);

        String startDate = data.format(formatter) + "00:00:00";
        String endDate = data.format(formatter)+ "23:59:59";
        log.info("# Quiz Daily Sink {} ~ {}", startDate, endDate);

        rptQuizDailyTemplate.sinkDaily(startDate, endDate);

    }

}
