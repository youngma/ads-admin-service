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
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                RAW.GROUP_CODE as GROUP_CODE,
                                sum(RAW.REQ_CNT) AS REQ_CNT,
                                0 as IMPRESSION_CNT,
                                0 as ANSWER_CNT,
                                0 as HINT_CNT,
                                0 as CLICK_CNT,
                                current_timestamp as INSERTED_AT,
                                current_timestamp as UPDATED_AT
                            from RPT_QUIZ_RAW RAW
                            inner join PARTNER_AD_GROUP AD_GROUP
                                on RAW.GROUP_CODE = AD_GROUP.GROUP_CODE
                            where RAW.REQUEST_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,GROUP_CODE
                        ) as report
                        ON DUPLICATE KEY UPDATE  REQ_CNT = RPT_QUIZ_PARTNER_DAILY.REQ_CNT + report.REQ_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }


    public void sinkImpressionPartner(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_PARTNER_DAILY
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                RAW.GROUP_CODE as GROUP_CODE,
                                0 AS REQ_CNT,
                                sum(DETAIL_CNT) AS IMPRESSION_CNT,
                                0 as ANSWER_CNT,
                                0 as HINT_CNT,
                                0 as CLICK_CNT,
                                current_timestamp as INSERTED_AT,
                                current_timestamp as UPDATED_AT
                            from RPT_QUIZ_RAW RAW
                            inner join PARTNER_AD_GROUP AD_GROUP
                                on RAW.GROUP_CODE = AD_GROUP.GROUP_CODE
                            where RAW.DETAIL_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,GROUP_CODE
                        ) as report
                        ON DUPLICATE KEY UPDATE  IMPRESSION_CNT = RPT_QUIZ_PARTNER_DAILY.IMPRESSION_CNT + report.IMPRESSION_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkHint(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_PARTNER_DAILY
                        
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                RAW.GROUP_CODE as GROUP_CODE,
                                0 AS REQ_CNT,
                                0 AS IMPRESSION_CNT,
                                0 as ANSWER_CNT,
                                sum(HINT_CNT) AS HINT_CNT,
                                0 as CLICK_CNT,
                                current_timestamp as INSERTED_AT,
                                current_timestamp as UPDATED_AT
                            from RPT_QUIZ_RAW RAW
                            inner join PARTNER_AD_GROUP AD_GROUP
                                on RAW.GROUP_CODE = AD_GROUP.GROUP_CODE
                            where RAW.HINT_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,GROUP_CODE
                        ) as report
                        ON DUPLICATE KEY UPDATE  HINT_CNT = RPT_QUIZ_PARTNER_DAILY.HINT_CNT + values(HINT_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }


    public void sinkAnswer(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_PARTNER_DAILY
                        
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                RAW.GROUP_CODE as GROUP_CODE,
                                0 AS REQ_CNT,
                                0 AS IMPRESSION_CNT,
                                sum(ANSWER_CNT) AS ANSWER_CNT,
                                0 AS HINT_CNT,
                                0 as CLICK_CNT,
                                current_timestamp as INSERTED_AT,
                                current_timestamp as UPDATED_AT
                            from RPT_QUIZ_RAW RAW
                            inner join PARTNER_AD_GROUP AD_GROUP
                                on RAW.GROUP_CODE = AD_GROUP.GROUP_CODE
                            where RAW.ANSWER_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,GROUP_CODE
                        ) as report
                        ON DUPLICATE KEY UPDATE  ANSWER_CNT = RPT_QUIZ_PARTNER_DAILY.ANSWER_CNT + report.ANSWER_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }
}
