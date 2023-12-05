package com.ads.main.repository.template;

import com.ads.main.vo.report.resp.RptDashboard;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RptQuizXCodeDailyTemplate {

    private final JdbcTemplate jdbcTemplate;

    public void sinkReq(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_X_CODE
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                GROUP_CODE as GROUP_CODE,
                                CAMPAIGN_CODE as CAMPAIGN_CODE,
                                sum(RAW.REQ_CNT) as REQ_CNT,
                                0 AS IMPRESSION_CNT,
                                0 AS DETAIL_CNT,
                                0 AS ANSWER_CNT,
                                0 AS HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                0 as PARTNER_COMMISSION,
                                0 as USER_COMMISSION,
                                0 as AD_REWORD
                            from RPT_QUIZ_RAW RAW
                            where RAW.REQUEST_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d'), GROUP_CODE, CAMPAIGN_CODE
                        ) as raw
                        ON DUPLICATE KEY UPDATE  REQ_CNT = RPT_QUIZ_X_CODE.REQ_CNT + raw.REQ_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkImpressionAdvertiser(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_X_CODE
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                GROUP_CODE as GROUP_CODE,
                                CAMPAIGN_CODE as CAMPAIGN_CODE,
                                0 as REQ_CNT,
                                sum(IMPRESSION_CNT) AS IMPRESSION_CNT,
                                0 AS DETAIL_CNT,
                                0 AS ANSWER_CNT,
                                0 AS HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                0 as PARTNER_COMMISSION,
                                0 as USER_COMMISSION,
                                0 as AD_REWORD
                            from RPT_QUIZ_RAW RAW
                            where RAW.IMPRESSION_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d'), GROUP_CODE, CAMPAIGN_CODE
                        ) as raw
                        ON DUPLICATE KEY UPDATE  IMPRESSION_CNT = RPT_QUIZ_X_CODE.IMPRESSION_CNT + raw.IMPRESSION_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkImpressionPartner(String startDate, String endDate) {
        jdbcTemplate.update(
                """
                        
                        insert into RPT_QUIZ_X_CODE
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                GROUP_CODE as GROUP_CODE,
                                CAMPAIGN_CODE as CAMPAIGN_CODE,
                                0 as REQ_CNT,
                                0 AS IMPRESSION_CNT,
                                sum(DETAIL_CNT) AS DETAIL_CNT,
                                0 AS ANSWER_CNT,
                                0 AS HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                0 as PARTNER_COMMISSION,
                                0 as USER_COMMISSION,
                                0 as AD_REWORD
                            from RPT_QUIZ_RAW RAW
                            where RAW.DETAIL_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d'), GROUP_CODE, CAMPAIGN_CODE
                        ) as raw
                        ON DUPLICATE KEY UPDATE  DETAIL_CNT = RPT_QUIZ_X_CODE.DETAIL_CNT + raw.DETAIL_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkHint(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                       insert into RPT_QUIZ_X_CODE
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                GROUP_CODE as GROUP_CODE,
                                CAMPAIGN_CODE as CAMPAIGN_CODE,
                                0 as REQ_CNT,
                                0 AS IMPRESSION_CNT,
                                0 AS DETAIL_CNT,
                                0 AS ANSWER_CNT,
                                sum(HINT_CNT) AS HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                0 as PARTNER_COMMISSION,
                                0 as USER_COMMISSION,
                                0 as AD_REWORD
                            from RPT_QUIZ_RAW RAW
                            where RAW.HINT_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d'), GROUP_CODE, CAMPAIGN_CODE
                        ) as raw
                        ON DUPLICATE KEY UPDATE  HINT_CNT = RPT_QUIZ_X_CODE.HINT_CNT + raw.HINT_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }



    public void sinkAnswer(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                      insert into RPT_QUIZ_X_CODE
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                GROUP_CODE as GROUP_CODE,
                                CAMPAIGN_CODE as CAMPAIGN_CODE,
                                0 as REQ_CNT,
                                0 AS IMPRESSION_CNT,
                                0 AS DETAIL_CNT,
                                sum(ANSWER_CNT) AS ANSWER_CNT,
                                0 AS HINT_CNT,
                                0 as CLICK_CNT,
                                0 as AD_COST,
                                0 as PARTNER_COMMISSION,
                                0 as USER_COMMISSION,
                                0 as AD_REWORD
                            from RPT_QUIZ_RAW RAW
                            where RAW.ANSWER_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d'), GROUP_CODE, CAMPAIGN_CODE
                        ) as raw
                        ON DUPLICATE KEY UPDATE  ANSWER_CNT = RPT_QUIZ_X_CODE.ANSWER_CNT + raw.ANSWER_CNT
                        ;
                        """
                ,startDate, endDate
        );
    }

    public void sinkClick(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                        insert into RPT_QUIZ_X_CODE
                        select * from (
                            select
                                DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d') as RPT_DATE,
                                GROUP_CODE as GROUP_CODE,
                                CAMPAIGN_CODE as CAMPAIGN_CODE,
                                0 as REQ_CNT,
                                0 AS IMPRESSION_CNT,
                                0 AS DETAIL_CNT,
                                0 AS ANSWER_CNT,
                                0 AS HINT_CNT,
                                sum(CLICK_CNT) as CLICK_CNT,
                                sum(AD_COST) as AD_COST,
                                sum(PARTNER_COMMISSION) as PARTNER_COMMISSION,
                                sum(USER_COMMISSION) as USER_COMMISSION,
                                sum(AD_REWORD) as AD_REWORD
                            from RPT_QUIZ_RAW RAW
                            where RAW.CLICK_AT between ? and ?
                            group by DATE_FORMAT(RAW.REQUEST_AT, '%Y%m%d'), GROUP_CODE, CAMPAIGN_CODE
                        ) as raw
                        ON DUPLICATE KEY UPDATE  CLICK_CNT = RPT_QUIZ_X_CODE.CLICK_CNT + raw.CLICK_CNT
                        ,AD_COST = RPT_QUIZ_X_CODE.AD_COST + raw.AD_COST
                        ,PARTNER_COMMISSION = RPT_QUIZ_X_CODE.PARTNER_COMMISSION + raw.PARTNER_COMMISSION
                        ,USER_COMMISSION = RPT_QUIZ_X_CODE.USER_COMMISSION + raw.USER_COMMISSION
                        ,AD_REWORD = RPT_QUIZ_X_CODE.AD_REWORD + raw.AD_REWORD
                        ;
                        """
                ,startDate, endDate
        );
    }
}
