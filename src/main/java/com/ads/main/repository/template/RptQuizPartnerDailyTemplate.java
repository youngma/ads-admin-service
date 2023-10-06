package com.ads.main.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RptQuizPartnerDailyTemplate {

    private final JdbcTemplate jdbcTemplate;

    public void sinkReq(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_PARTNER_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            RAW.GROUP_CODE,
                            sum(RAW.REQ_CNT) AS REQ_CNT,
                            0,
                            0,
                            0,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        inner join PARTNER_AD_GROUP AD_GROUP
                            on RAW.GROUP_CODE = AD_GROUP.GROUP_CODE
                        where RAW.REQUEST_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,GROUP_CODE
                        ON DUPLICATE KEY UPDATE  REQ_CNT = REQ_CNT + values(REQ_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }


    public void sinkImpressionPartner(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_PARTNER_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            RAW.GROUP_CODE,
                            0,
                            sum(DETAIL_CNT) AS IMPRESSION_CNT,
                            0,
                            0,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        inner join PARTNER_AD_GROUP AD_GROUP
                            on RAW.GROUP_CODE = AD_GROUP.GROUP_CODE
                        where RAW.DETAIL_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,GROUP_CODE
                        
                        ON DUPLICATE KEY UPDATE  IMPRESSION_CNT = IMPRESSION_CNT + values(IMPRESSION_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkHint(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_PARTNER_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            RAW.GROUP_CODE,
                            0,
                            0,
                            0,
                            sum(HINT_CNT) AS HINT_CNT,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        inner join PARTNER_AD_GROUP AD_GROUP
                            on RAW.GROUP_CODE = AD_GROUP.GROUP_CODE
                        where RAW.HINT_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,GROUP_CODE
                        
                        ON DUPLICATE KEY UPDATE  HINT_CNT = HINT_CNT + values(HINT_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }


    public void sinkAnswer(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_PARTNER_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            RAW.GROUP_CODE,
                            0,
                            0,
                            sum(ANSWER_CNT) AS ANSWER_CNT,
                            0,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        inner join PARTNER_AD_GROUP AD_GROUP
                            on RAW.GROUP_CODE = AD_GROUP.GROUP_CODE
                        where RAW.ANSWER_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,GROUP_CODE
                        ON DUPLICATE KEY UPDATE  ANSWER_CNT = ANSWER_CNT + values(ANSWER_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }
}
