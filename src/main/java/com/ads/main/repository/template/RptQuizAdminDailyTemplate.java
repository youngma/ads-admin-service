package com.ads.main.repository.template;

import com.ads.main.vo.report.resp.RptDashboard;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RptQuizAdminDailyTemplate {

    private final JdbcTemplate jdbcTemplate;

    public void sinkCount(RptDashboard rptDashboard) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADMIN_DAILY
                        select * from (
                            select
                                DATE_FORMAT(now(), '%Y%m%d') as RPT_DATE,
                                ? as ADVERTISER_CNT,
                                ? as CAMPAIGN_CNT,
                                ? as PARTNER_CNT,
                                ? as AD_GROUP_CNT,
                                0 as REQ_CNT,
                                0 AS IMPRESSION_CNT,
                                0 AS ANSWER_CNT,
                                0 AS HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                0 as PARTNER_COMMISSION,
                                0 as USER_COMMISSION,
                                current_timestamp as INSERTED_AT,
                                current_timestamp as UPDATED_AT
                            from dual
                        ) as raw
                        ON DUPLICATE KEY UPDATE  ADVERTISER_CNT = raw.ADVERTISER_CNT,
                        CAMPAIGN_CNT = raw.CAMPAIGN_CNT,
                        PARTNER_CNT = raw.PARTNER_CNT,
                        AD_GROUP_CNT = raw.AD_GROUP_CNT
                        ;
                        
                        """
                ,rptDashboard.getAdvertiserCount(), rptDashboard.getCampaignCount(), rptDashboard.getPartnerCount(), rptDashboard.getGroupCount()
        );
    }

    public void sinkReq(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADMIN_DAILY
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                0 as ADVERTISER_CNT,
                                0 as CAMPAIGN_CNT,
                                0 as PARTNER_CNT,
                                0 as AD_GROUP_CNT,
                                sum(RAW.REQ_CNT) as REQ_CNT,
                                0 AS IMPRESSION_CNT,
                                0 AS ANSWER_CNT,
                                0 AS HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                0 as PARTNER_COMMISSION,
                                0 as USER_COMMISSION,
                                current_timestamp as INSERTED_AT,
                                current_timestamp as UPDATED_AT
                            from RPT_QUIZ_RAW RAW
                            where RAW.REQUEST_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d')
                        ) as raw
                        ON DUPLICATE KEY UPDATE  REQ_CNT = raw.REQ_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }


    public void sinkImpressionPartner(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        
                        insert into RPT_QUIZ_ADMIN_DAILY
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                0 as ADVERTISER_CNT,
                                0 as CAMPAIGN_CNT,
                                0 as PARTNER_CNT,
                                0 as AD_GROUP_CNT,
                                0 as REQ_CNT,
                                sum(DETAIL_CNT) AS IMPRESSION_CNT,
                                0 AS ANSWER_CNT,
                                0 AS HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                0 as PARTNER_COMMISSION,
                                0 as USER_COMMISSION,
                                current_timestamp as INSERTED_AT,
                                current_timestamp as UPDATED_AT
                            from RPT_QUIZ_RAW RAW
                            where RAW.DETAIL_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d')
                        ) as raw
                        ON DUPLICATE KEY UPDATE  IMPRESSION_CNT = raw.IMPRESSION_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkHint(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                       insert into RPT_QUIZ_ADMIN_DAILY
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                0 as ADVERTISER_CNT,
                                0 as CAMPAIGN_CNT,
                                0 as PARTNER_CNT,
                                0 as AD_GROUP_CNT,
                                0 as REQ_CNT,
                                0 as IMPRESSION_CNT,
                                0 AS ANSWER_CNT,
                                sum(HINT_CNT) AS HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                0 as PARTNER_COMMISSION,
                                0 as USER_COMMISSION,
                                current_timestamp as INSERTED_AT,
                                current_timestamp as UPDATED_AT
                            from RPT_QUIZ_RAW RAW
                            where RAW.HINT_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d')
                        ) as raw
                        ON DUPLICATE KEY UPDATE  HINT_CNT = raw.HINT_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }



    public void sinkAnswer(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                       insert into RPT_QUIZ_ADMIN_DAILY
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                0 as ADVERTISER_CNT,
                                0 as CAMPAIGN_CNT,
                                0 as PARTNER_CNT,
                                0 as AD_GROUP_CNT,
                                0 as REQ_CNT,
                                0 as IMPRESSION_CNT,
                                sum(ANSWER_CNT) AS ANSWER_CNT,
                                0 as HINT_CNT,
                                0 as CLICK_CNT,
                                sum(AD_COST) as AD_COST,
                                sum(PARTNER_COMMISSION) as PARTNER_COMMISSION,
                                sum(USER_COMMISSION) as USER_COMMISSION,
                                current_timestamp as INSERTED_AT,
                                current_timestamp as UPDATED_AT
                            from RPT_QUIZ_RAW RAW
                            where RAW.ANSWER_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d')
                        ) as raw
                        ON DUPLICATE KEY UPDATE  ANSWER_CNT = raw.ANSWER_CNT,
                        AD_COST =  raw.AD_COST,
                        PARTNER_COMMISSION =  raw.PARTNER_COMMISSION,
                        USER_COMMISSION =  raw.USER_COMMISSION
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkClick(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADMIN_DAILY
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                0 as ADVERTISER_CNT,
                                0 as CAMPAIGN_CNT,
                                0 as PARTNER_CNT,
                                0 as AD_GROUP_CNT,
                                0 as REQ_CNT,
                                0 as IMPRESSION_CNT,
                                0 AS ANSWER_CNT,
                                0 as HINT_CNT,
                                sum(CLICK_CNT)  as CLICK_CNT,
                                0 as AD_COST,
                                0 as PARTNER_COMMISSION,
                                0 as USER_COMMISSION,
                                current_timestamp as INSERTED_AT,
                                current_timestamp as UPDATED_AT
                            from RPT_QUIZ_RAW RAW
                            where RAW.CLICK_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d')
                        ) as raw
                        ON DUPLICATE KEY UPDATE  CLICK_CNT = raw.CLICK_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }
}
