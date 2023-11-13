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
                        insert into RPT_QUIZ_ADMIN_DAILY (
                        RPT_DATE, ADVERTISER_CNT, CAMPAIGN_CNT, PARTNER_CNT, AD_GROUP_CNT,
                        REQ_CNT, IMPRESSION_CNT, ANSWER_CNT, HINT_CNT, CLICK_CNT, PARTNER_COMMISSION, USER_COMMISSION,
                        INSERTED_AT, UPDATED_AT)
                        values (
                        DATE_FORMAT(now(), '%Y%m%d'), ?, ?, ?, ?,
                        0, 0, 0, 0, 0, 0, 0,
                        current_timestamp, current_timestamp
                        )
                        
                        ON DUPLICATE KEY UPDATE  ADVERTISER_CNT = values(ADVERTISER_CNT),
                            ADVERTISER_CNT = values(ADVERTISER_CNT),
                            CAMPAIGN_CNT =  values(CAMPAIGN_CNT),
                            PARTNER_CNT = values(PARTNER_CNT),
                            AD_GROUP_CNT = values(AD_GROUP_CNT)
                        ;
                        
                        """
                ,rptDashboard.getAdvertiserCount(), rptDashboard.getCampaignCount(), rptDashboard.getPartnerCount(), rptDashboard.getGroupCount()
        );
    }

    public void sinkReq(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADMIN_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            0,
                            0,
                            0,
                            0,
                            sum(RAW.REQ_CNT) AS REQ_CNT,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        where RAW.REQUEST_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d')
                        ON DUPLICATE KEY UPDATE  REQ_CNT = REQ_CNT + values(REQ_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }


    public void sinkImpressionPartner(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADMIN_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            0,
                            0,
                            0,
                            0,
                            sum(DETAIL_CNT) AS IMPRESSION_CNT,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        where RAW.DETAIL_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d')
                        
                        ON DUPLICATE KEY UPDATE  IMPRESSION_CNT = IMPRESSION_CNT + values(IMPRESSION_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkHint(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADMIN_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            sum(HINT_CNT) AS HINT_CNT,
                            0,
                            0,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        where RAW.HINT_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d')
                        
                        ON DUPLICATE KEY UPDATE  HINT_CNT = HINT_CNT + values(HINT_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }



    public void sinkAnswer(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADMIN_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            sum(ANSWER_CNT) AS ANSWER_CNT,
                            0,
                            0,
                            sum(PARTNER_COMMISSION) as PARTNER_COMMISSION,
                            sum(USER_COMMISSION) as USER_COMMISSION,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        where RAW.ANSWER_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d')
                        ON DUPLICATE KEY UPDATE  ANSWER_CNT = ANSWER_CNT + values(ANSWER_CNT),
                        PARTNER_COMMISSION = PARTNER_COMMISSION + values(PARTNER_COMMISSION),
                        USER_COMMISSION = USER_COMMISSION + values(USER_COMMISSION)
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkClock(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_ADMIN_DAILY
                        select
                            DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            sum(CLICK_CNT) AS CLICK_CNT,
                            0,
                            0,
                            current_timestamp,
                            current_timestamp
                        from RPT_QUIZ_RAW RAW
                        where RAW.CLICK_AT between ? and ?
                        group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d')
                        
                        ON DUPLICATE KEY UPDATE  CLICK_CNT = CLICK_CNT + values(CLICK_CNT)
                        ;
                        """
                ,startDate, endDate
        );
    }
}
