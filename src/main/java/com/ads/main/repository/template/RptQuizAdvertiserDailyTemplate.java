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
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            RAW.CAMPAIGN_CODE,
                            sum(RAW.REQ_CNT) AS REQ_CNT,
                            0,
                            0,
                            0,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        inner join AD_CAMPAIGN_MASTER AD_CAMPAIGN
                            on RAW.CAMPAIGN_CODE = AD_CAMPAIGN.CAMPAIGN_CODE
                        where RAW.REQUEST_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,CAMPAIGN_CODE
                        ON DUPLICATE KEY UPDATE  REQ_CNT = REQ_CNT + values(REQ_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }


    public void sinkImpressionAdvertiser(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADVERTISER_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            RAW.CAMPAIGN_CODE,
                            0,
                            sum(IMPRESSION_CNT) AS IMPRESSION_CNT,
                            0,
                            0,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        inner join AD_CAMPAIGN_MASTER AD_CAMPAIGN
                            on RAW.CAMPAIGN_CODE = AD_CAMPAIGN.CAMPAIGN_CODE
                        where RAW.DETAIL_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,CAMPAIGN_CODE
                        
                        ON DUPLICATE KEY UPDATE  IMPRESSION_CNT = IMPRESSION_CNT + values(IMPRESSION_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkHint(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADVERTISER_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            RAW.CAMPAIGN_CODE,
                            0,
                            0,
                            0,
                            sum(HINT_CNT) AS HINT_CNT,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        inner join AD_CAMPAIGN_MASTER AD_CAMPAIGN
                            on RAW.CAMPAIGN_CODE = AD_CAMPAIGN.CAMPAIGN_CODE
                        where RAW.HINT_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,CAMPAIGN_CODE
                        
                        ON DUPLICATE KEY UPDATE  HINT_CNT = HINT_CNT + values(HINT_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }


    public void sinkClick(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADVERTISER_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            RAW.CAMPAIGN_CODE,
                            0,
                            0,
                            0,
                            0,
                            sum(CLICK_CNT) AS CLICK_CNT,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        inner join AD_CAMPAIGN_MASTER AD_CAMPAIGN
                            on RAW.CAMPAIGN_CODE = AD_CAMPAIGN.CAMPAIGN_CODE
                        where RAW.ANSWER_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') ,CAMPAIGN_CODE
                        ON DUPLICATE KEY UPDATE  CLICK_CNT = CLICK_CNT + values(CLICK_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }
}
