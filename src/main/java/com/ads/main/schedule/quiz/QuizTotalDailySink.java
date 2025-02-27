package com.ads.main.schedule.quiz;


import com.ads.main.core.enums.common.SinkSchedule;
import com.ads.main.repository.template.RptQuizDailyTemplate;
import com.ads.main.service.admin.ReportService;
import com.ads.main.vo.admin.report.RptSinkTimeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuizTotalDailySink {

    private final RptQuizDailyTemplate rptQuizDailyTemplate;

    private final ReportService reportService;

    @Scheduled(cron = "0 10 0 * * ?")
    public void rawSink() {

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate data = LocalDate.now().minusDays(1);

        RptSinkTimeVo rptSinkTimeVo =  reportService.loadSinkSchedule(SinkSchedule.QUIZ_DAILY, data.format(formatter));

        LocalDate last = LocalDate.parse(rptSinkTimeVo.getLastSink(), formatter);

        String startDate = last.format(formatter) + " 00:00:00";
        String endDate = data.format(formatter)+ " 23:59:59";
        log.info("# Quiz Daily Sink {} ~ {}", startDate, endDate);

        rptQuizDailyTemplate.sinkDaily(startDate, endDate);

        rptSinkTimeVo.setLastSink(data.plusDays(1).format(formatter));
        reportService.saveSinkSchedule(rptSinkTimeVo);
    }
}
