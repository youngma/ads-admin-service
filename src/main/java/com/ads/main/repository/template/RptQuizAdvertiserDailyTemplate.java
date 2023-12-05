package com.ads.main.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RptQuizAdvertiserDailyTemplate {

    private final JdbcTemplate jdbcTemplate;

    public void sinkReq(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADVERTISER_DAILY
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                RAW.CAMPAIGN_CODE as CAMPAIGN_CODE,
                                sum(RAW.REQ_CNT) AS REQ_CNT,
                                0 as IMPRESSION_CNT,
                                0 as ANSWER_CNT,
                                0 as HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                current_timestamp as INSERT_AT,
                                current_timestamp  as UPDATE_AT
                            from RPT_QUIZ_RAW RAW
                            inner join AD_CAMPAIGN_MASTER AD_CAMPAIGN
                                on RAW.CAMPAIGN_CODE = AD_CAMPAIGN.CAMPAIGN_CODE
                            where RAW.REQUEST_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,CAMPAIGN_CODE
                        ) as report
                        ON DUPLICATE KEY UPDATE  REQ_CNT = RPT_QUIZ_ADVERTISER_DAILY.REQ_CNT + report.REQ_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }


    public void sinkImpressionAdvertiser(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADVERTISER_DAILY
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                RAW.CAMPAIGN_CODE as CAMPAIGN_CODE,
                                0 AS REQ_CNT,
                                sum(DETAIL_CNT) as IMPRESSION_CNT,
                                0 as ANSWER_CNT,
                                0 as HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                current_timestamp as INSERT_AT,
                                current_timestamp  as UPDATE_AT
                            from RPT_QUIZ_RAW RAW
                            inner join AD_CAMPAIGN_MASTER AD_CAMPAIGN
                                on RAW.CAMPAIGN_CODE = AD_CAMPAIGN.CAMPAIGN_CODE
                            where RAW.DETAIL_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,CAMPAIGN_CODE
                        ) as report
                        ON DUPLICATE KEY UPDATE  IMPRESSION_CNT = RPT_QUIZ_ADVERTISER_DAILY.IMPRESSION_CNT + report.IMPRESSION_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkHint(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADVERTISER_DAILY
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                RAW.CAMPAIGN_CODE as CAMPAIGN_CODE,
                                0 AS REQ_CNT,
                                0 as IMPRESSION_CNT,
                                0 as ANSWER_CNT,
                                sum(HINT_CNT) as HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                current_timestamp as INSERT_AT,
                                current_timestamp  as UPDATE_AT
                            from RPT_QUIZ_RAW RAW
                            inner join AD_CAMPAIGN_MASTER AD_CAMPAIGN
                                on RAW.CAMPAIGN_CODE = AD_CAMPAIGN.CAMPAIGN_CODE
                            where RAW.HINT_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,CAMPAIGN_CODE
                        ) as report
                        ON DUPLICATE KEY UPDATE  HINT_CNT = RPT_QUIZ_ADVERTISER_DAILY.HINT_CNT + report.HINT_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }


    public void sinkClick(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADVERTISER_DAILY
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                RAW.CAMPAIGN_CODE as CAMPAIGN_CODE,
                                0 AS REQ_CNT,
                                0 as IMPRESSION_CNT,
                                0 as ANSWER_CNT,
                                0 as HINT_CNT,
                                sum(AD_COST) as AD_COST,
                                sum(CLICK_CNT) AS CLICK_CNT,
                                current_timestamp as INSERT_AT,
                                current_timestamp  as UPDATE_AT
                            from RPT_QUIZ_RAW RAW
                            inner join AD_CAMPAIGN_MASTER AD_CAMPAIGN
                                on RAW.CAMPAIGN_CODE = AD_CAMPAIGN.CAMPAIGN_CODE
                            where RAW.CLICK_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,CAMPAIGN_CODE
                        ) as report
                        ON DUPLICATE KEY UPDATE  CLICK_CNT = RPT_QUIZ_ADVERTISER_DAILY.CLICK_CNT + report.CLICK_CNT
                        , AD_COST = RPT_QUIZ_ADVERTISER_DAILY.AD_COST + report.AD_COST
                        ;
                        """
                ,startDate, endDate
        );
    }
}
