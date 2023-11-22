package com.ads.main.repository.template;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RptQuizRawTemplate {

    private final JdbcTemplate jdbcTemplate;

    public void sinkReq(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                    insert into RPT_QUIZ_RAW
                    select * from (
                        select
                            REQ.REQUEST_ID as REQUEST_ID,
                            REQ.GROUP_CODE as GROUP_CODE,
                            REQ.CAMPAIGN_CODE as CAMPAIGN_CODE,
                            REQ.USER_KEY as USER_KEY,
                            1 AS REQ_CNT ,
                            0 AS IMPRESSION_CNT,
                            0 AS DETAIL_CNT,
                            0 AS ANSWER_CNT,
                            0 AS HINT_CNT,
                            0 AS CLICK_CNT,
                            REQ.REQUEST_AT as REQUEST_AT, /* request at */
                            null as IMPRESSION_AT ,              /* impression at */
                            null as HINT_AT ,              /* hint at */
                            null as DETAIL_AT,              /* detail at */
                            null as ANSWER_AT,              /* answer at */
                            null as CLICK_AT,               /* click at */
                            0 as AD_PRICE,
                            0 as PARTNER_COMMISSION,
                            0 as USER_COMMISSION,
                            0 as AD_REWORD
                        from RPT_AD_REQUEST REQ
                        join PARTNER_AD_GROUP AD_GROUP
                         on REQ.GROUP_CODE = AD_GROUP.GROUP_CODE
                         and AD_GROUP.AD_TYPE in ('QUIZ01','QUIZ02', 'ALL')
                        where REQ.REQUEST_AT between ? and ?
                    ) as RAW
                    ON DUPLICATE KEY UPDATE  REQ_CNT = RAW.REQ_CNT
                    ;
                    """
                ,startDate, endDate
        );
    }

    public void sinkImpressionAdvertiser(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                    insert into RPT_QUIZ_RAW
                    select * from (
                        select
                        RAW.REQUEST_ID as REQUEST_ID,
                        RAW.GROUP_CODE as GROUP_CODE,
                        RAW.CAMPAIGN_CODE as CAMPAIGN_CODE,
                        RAW.USER_KEY as USER_KEY,
                        REQ_CNT as REQ_CNT ,
                        1 AS IMPRESSION_CNT,
                        0 AS DETAIL_CNT,
                        0 AS ANSWER_CNT,
                        0 AS HINT_CNT,
                        0 AS CLICK_CNT,
                        RAW.REQUEST_AT as REQUEST_AT, /* request at */
                        IMPRESSION.IMPRESSION_AT as IMPRESSION_AT ,              /* impression at */
                        null as HINT_AT ,              /* hint at */
                        null as DETAIL_AT,              /* detail at */
                        null as ANSWER_AT,              /* answer at */
                        null as CLICK_AT,               /* click at */
                        0 as AD_PRICE,
                        0 as PARTNER_COMMISSION,
                        0 as USER_COMMISSION,
                        0 as AD_REWORD
                    from RPT_QUIZ_RAW RAW
                    inner join (
                        select
                            REQUEST_ID as IMPRESSION_ID,
                            IMPRESSION_AT as IMPRESSION_AT
                        from RPT_AD_IMPRESSION
                        where TARGET = 'advertiser'
                    ) IMPRESSION
                        on RAW.REQUEST_ID = IMPRESSION_ID
                    where IMPRESSION.IMPRESSION_AT between ? and ?
                    ) as RAW
                    ON DUPLICATE KEY UPDATE
                    IMPRESSION_CNT = RAW.IMPRESSION_CNT,
                    IMPRESSION_AT = RAW.IMPRESSION_AT
                    ;
                    """
                ,startDate, endDate
        );
    }


    public void sinkImpressionPartner(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                    insert into RPT_QUIZ_RAW
                    select * from (
                        select
                        RAW.REQUEST_ID as REQUEST_ID,
                        RAW.GROUP_CODE as GROUP_CODE,
                        RAW.CAMPAIGN_CODE as CAMPAIGN_CODE,
                        RAW.USER_KEY as USER_KEY,
                        REQ_CNT as REQ_CNT ,
                        0 AS IMPRESSION_CNT,
                        1 AS DETAIL_CNT,
                        0 AS ANSWER_CNT,
                        0 AS HINT_CNT,
                        0 AS CLICK_CNT,
                        RAW.REQUEST_AT as REQUEST_AT, /* request at */
                        null as IMPRESSION_AT ,              /* impression at */
                        null as HINT_AT ,              /* hint at */
                        IMPRESSION.IMPRESSION_AT as DETAIL_AT,              /* detail at */
                        null as ANSWER_AT,              /* answer at */
                        null as CLICK_AT,               /* click at */
                        0 as AD_PRICE,
                        0 as PARTNER_COMMISSION,
                        0 as USER_COMMISSION,
                        0 as AD_REWORD
                    from RPT_QUIZ_RAW RAW
                    inner join (
                        select
                            REQUEST_ID as IMPRESSION_ID,
                            IMPRESSION_AT as IMPRESSION_AT
                        from RPT_AD_IMPRESSION
                        where TARGET = 'partner'
                    ) IMPRESSION
                        on RAW.REQUEST_ID = IMPRESSION_ID
                    where IMPRESSION.IMPRESSION_AT between ? and ?
                    ) as RAW
                    ON DUPLICATE KEY UPDATE  DETAIL_CNT = RAW.DETAIL_CNT
                    , DETAIL_AT = RAW.DETAIL_AT
                    ;
                    """
                ,startDate, endDate
        );
    }

    public void sinkHint(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                   insert into RPT_QUIZ_RAW
                   select * from (
                        select
                        RAW.REQUEST_ID as REQUEST_ID,
                        RAW.GROUP_CODE as GROUP_CODE,
                        RAW.CAMPAIGN_CODE as CAMPAIGN_CODE,
                        RAW.USER_KEY as USER_KEY,
                        REQ_CNT as REQ_CNT ,
                        IMPRESSION_AT AS IMPRESSION_CNT,
                        1 AS DETAIL_CNT,
                        0 AS ANSWER_CNT,
                        0 AS HINT_CNT,
                        0 AS CLICK_CNT,
                        RAW.REQUEST_AT as REQUEST_AT, /* request at */
                        null as IMPRESSION_AT ,              /* impression at */
                        HINT.CLICK_AT as HINT_AT ,              /* hint at */
                        null as DETAIL_AT,              /* detail at */
                        null as ANSWER_AT,              /* answer at */
                        null as CLICK_AT,               /* click at */
                        0 as AD_PRICE,
                        0 as PARTNER_COMMISSION,
                        0 as USER_COMMISSION,
                        0 as AD_REWORD
                    from RPT_QUIZ_RAW RAW
                    inner join (
                        select
                            REQUEST_ID as HINT_ID,
                            CLICK_AT as CLICK_AT
                        from RPT_AD_CLICK
                        where TARGET = 'hint'
                    ) HINT
                       on RAW.REQUEST_ID = HINT_ID
                    where HINT.CLICK_AT between ? and ?
                    ) as RAW
                    ON DUPLICATE KEY UPDATE  HINT_CNT = RAW.HINT_CNT
                    , HINT_AT = RAW.HINT_AT
                    ;
                    """
                ,startDate, endDate
        );
    }


    public void sinkClick(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                    insert into RPT_QUIZ_RAW
                    select * from (
                        select
                        RAW.REQUEST_ID as REQUEST_ID,
                        RAW.GROUP_CODE as GROUP_CODE,
                        RAW.CAMPAIGN_CODE as CAMPAIGN_CODE,
                        RAW.USER_KEY as USER_KEY,
                        REQ_CNT as REQ_CNT ,
                        IMPRESSION_AT AS IMPRESSION_CNT,
                        DETAIL_CNT AS DETAIL_CNT,
                        ANSWER_CNT AS ANSWER_CNT,
                        HINT_CNT AS HINT_CNT,
                        1 AS CLICK_CNT,
                        RAW.REQUEST_AT as REQUEST_AT, /* request at */
                        null as IMPRESSION_AT ,              /* impression at */
                        null as HINT_AT ,              /* hint at */
                        null as DETAIL_AT,              /* detail at */
                        null as ANSWER_AT,              /* answer at */
                        CLICK.CLICK_AT as CLICK_AT,               /* click at */
                        0 as AD_PRICE,
                        0 as PARTNER_COMMISSION,
                        0 as USER_COMMISSION,
                        0 as AD_REWORD
                    from RPT_QUIZ_RAW RAW
                    inner  join (
                        select
                            REQUEST_ID as CLICK_ID,
                            CLICK_AT as CLICK_AT
                        from RPT_AD_CLICK
                        where TARGET = 'answer'
                    ) CLICK
                    on RAW.REQUEST_ID = CLICK_ID
                    where
                     CLICK.CLICK_AT between ? and ?
                    ) RAW
                    ON DUPLICATE KEY UPDATE  CLICK_CNT = RAW.CLICK_CNT
                    , CLICK_AT = RAW.CLICK_AT
                    ;
                    """
                ,startDate, endDate
        );
    }

    public void sinkAnswer(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                    insert into RPT_QUIZ_RAW
                    
                    select * from (
                        select
                            RAW.REQUEST_ID as REQUEST_ID,
                            RAW.GROUP_CODE as GROUP_CODE,
                            RAW.CAMPAIGN_CODE as CAMPAIGN_CODE,
                            RAW.USER_KEY as USER_KEY,
                            REQ_CNT as REQ_CNT ,
                            IMPRESSION_AT AS IMPRESSION_CNT,
                            DETAIL_CNT AS DETAIL_CNT,
                            1 AS ANSWER_CNT,
                            HINT_CNT AS HINT_CNT,
                            CLICK_CNT AS CLICK_CNT,
                            RAW.REQUEST_AT as REQUEST_AT, /* request at */
                            null as IMPRESSION_AT ,              /* impression at */
                            null as HINT_AT ,              /* hint at */
                            null as DETAIL_AT,              /* detail at */
                            ANSWER.ANSWER_AT as ANSWER_AT,              /* answer at */
                            null as CLICK_AT,               /* click at */
                            REQUEST.AD_PRICE as AD_PRICE,
                            REQUEST.PARTNER_COMMISSION as PARTNER_COMMISSION,
                            REQUEST.USER_COMMISSION as USER_COMMISSION, 
                            REQUEST.AD_REWORD as AD_REWORD
                        from RPT_QUIZ_RAW RAW
                        inner join (
                            select
                                REQUEST_ID as ANSWER_ID,
                                ANSWER_AT
                            from RPT_AD_ANSWER
                        ) ANSWER
                           on RAW.REQUEST_ID = ANSWER_ID
                        inner join RPT_AD_REQUEST REQUEST
                           on RAW.REQUEST_ID = REQUEST.REQUEST_ID
                        where ANSWER.ANSWER_AT between ? and ?
                    ) as RAW
                    ON DUPLICATE KEY UPDATE  ANSWER_CNT = RAW.ANSWER_CNT
                    , ANSWER_AT = RAW.ANSWER_AT
                    , AD_PRICE = RAW.AD_PRICE
                    , PARTNER_COMMISSION = RAW.PARTNER_COMMISSION
                    , USER_COMMISSION = RAW.USER_COMMISSION
                    , AD_REWORD = RAW.AD_REWORD
                    ;
                    """
                ,startDate, endDate
        );
    }
}
