package com.ads.main.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RptQuizDailyTemplate {

    private final JdbcTemplate jdbcTemplate;

    public void sinkDaily(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_DAILY
                        select
                            date_format(REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            count(distinct GROUP_CODE) GROUP_CNT,
                            count(distinct CAMPAIGN_CODE) CAMPAIGN_CNT,
                            sum(REQ_CNT) as REQ_CNT,
                            sum(IMPRESSION_CNT) as IMPRESSION_CNT,
                            sum(DETAIL_CNT) as DETAIL_CNT,
                            sum(ANSWER_CNT) as ANSWER_CNT,
                            sum(HINT_CNT) as HINT_CNT,
                            sum(CLICK_CNT) as CLICK_CNT
                        from  RPT_QUIZ_RAW where REQUEST_AT between ? and ?
                        group by date_format(REQUEST_AT, '%Y%m%d')
                        ON DUPLICATE KEY UPDATE 
                            GROUP_CNT = values(GROUP_CNT),
                            CAMPAIGN_CNT = values(CAMPAIGN_CNT),
                            REQ_CNT = values(REQ_CNT),
                            IMPRESSION_CNT = values(IMPRESSION_CNT),
                            DETAIL_CNT = values(DETAIL_CNT),
                            ANSWER_CNT = values(ANSWER_CNT),
                            ANSWER_CNT = values(ANSWER_CNT),
                            HINT_CNT = values(HINT_CNT),
                            CLICK_CNT = values(CLICK_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }


}
