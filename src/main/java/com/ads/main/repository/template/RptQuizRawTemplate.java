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
                    select
                        REQ.REQUEST_ID,
                        REQ.GROUP_CODE,
                        REQ.CAMPAIGN_CODE,
                        1 AS REQ_CNT,
                        0 AS IMPRESSION_CNT,
                        0 AS DETAIL_CNT,
                        0 AS ANSWER_CNT,
                        0 AS HINT_CNT,
                        0 AS CLICK_CNT,
                        current_timestamp, /* request at */
                        null,              /* impression at */
                        null,              /* hint at */
                        null,              /* detail at */
                        null,              /* answer at */
                        null               /* click at */
                    from RPT_AD_REQUEST REQ
                    join PARTNER_AD_GROUP AD_GROUP\s
                     on REQ.GROUP_CODE = AD_GROUP.GROUP_CODE
                     and AD_GROUP.AD_TYPE = 'QUIZ01'
                    where REQ.REQUEST_AT between ? and ?
                    ON DUPLICATE KEY UPDATE  REQ_CNT = values(REQ_CNT)
                    ;
                    """
                ,startDate, endDate
        );
    }

    public void sinkImpressionAdvertiser(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                    insert into RPT_QUIZ_RAW
                    select
                        RAW.REQUEST_ID,
                        RAW.GROUP_CODE,
                        RAW.CAMPAIGN_CODE,
                        REQ_CNT,
                        1 as IMPRESSION_CNT,
                        DETAIL_CNT,
                        ANSWER_CNT,
                        HINT_CNT,
                        CLICK_CNT,
                        current_timestamp, /* request at */
                        current_timestamp as IMPRESSION_AT, /* impression at */
                        null,              /* hint at */
                        null, /* detail at */
                        null,              /* answer at */
                        null               /* click at */
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
                    ON DUPLICATE KEY UPDATE
                    IMPRESSION_CNT = values(IMPRESSION_CNT),
                    IMPRESSION_AT = values(IMPRESSION_AT)
                    ;
                    """
                ,startDate, endDate
        );
    }


    public void sinkImpressionPartner(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                    insert into RPT_QUIZ_RAW
                    select
                        RAW.REQUEST_ID,
                        RAW.GROUP_CODE,
                        RAW.CAMPAIGN_CODE,
                        REQ_CNT,
                        IMPRESSION_CNT,
                        1 as DETAIL_CNT,
                        ANSWER_CNT,
                        HINT_CNT,
                        CLICK_CNT,
                        current_timestamp, /* request at */
                        null,              /* impression at */
                        null,              /* hint at */
                        current_timestamp as DETAIL_AT, /* detail at */
                        null,              /* answer at */
                        null               /* click at */
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
                    ON DUPLICATE KEY UPDATE  DETAIL_CNT = values(DETAIL_CNT)
                    , DETAIL_AT = values(DETAIL_AT)
                    ;
                    """
                ,startDate, endDate
        );
    }

    public void sinkHint(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                    insert into RPT_QUIZ_RAW
                    select
                        RAW.REQUEST_ID,
                        RAW.GROUP_CODE,
                        RAW.CAMPAIGN_CODE,
                        REQ_CNT,
                        IMPRESSION_CNT,
                        DETAIL_CNT,
                        ANSWER_CNT,
                        1 AS HINT_CNT,
                        CLICK_CNT,
                        current_timestamp, /* request at */
                        null,              /* impression at */
                        current_timestamp as HINT_AT, /* hint at */
                        null,              /* detail at */
                        null,              /* answer at */
                        null               /* click at */
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
                    ON DUPLICATE KEY UPDATE  HINT_CNT = values(HINT_CNT)
                    , HINT_AT = values(HINT_AT)
                    ;
                    """
                ,startDate, endDate
        );
    }


    public void sinkClick(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                    insert into RPT_QUIZ_RAW
                    select
                        RAW.REQUEST_ID,
                        RAW.GROUP_CODE,
                        RAW.CAMPAIGN_CODE,
                        REQ_CNT,
                        IMPRESSION_CNT,
                        DETAIL_CNT,
                        ANSWER_CNT,
                        HINT_CNT,
                        1 AS CLICK_CNT,
                        current_timestamp, /* request at */
                        null,              /* impression at */
                        null,              /* hint at */
                        null,              /* detail at */
                        null,              /* answer at */
                        current_timestamp as CLICK_AT  /* click at */
                    from RPT_QUIZ_RAW RAW
                    inner  join (
                        select
                            REQUEST_ID as CLICK_ID,
                            CLICK_AT as CLICK_AT
                        from RPT_AD_CLICK
                        where TARGET = 'answer'
                    ) CLICK
                    on RAW.REQUEST_ID = CLICK_ID
                    where CLICK.CLICK_AT between ? and ?
                    ON DUPLICATE KEY UPDATE  CLICK_CNT = values(CLICK_CNT)
                    , CLICK_AT = values(CLICK_AT)
                    ;
                    """
                ,startDate, endDate
        );
    }

    public void sinkAnswer(String startDate, String endDate) {

        jdbcTemplate.update(
                """
                    insert into RPT_QUIZ_RAW
                    select
                        RAW.REQUEST_ID,
                        RAW.GROUP_CODE,
                        RAW.CAMPAIGN_CODE,
                        REQ_CNT,
                        IMPRESSION_CNT,
                        DETAIL_CNT,
                        1 AS ANSWER_CNT,
                        HINT_CNT,
                        CLICK_CNT,
                        current_timestamp, /* request at */
                        null,              /* impression at */
                        null,              /* hint at */
                        null,              /* detail at */
                        current_timestamp as ANSWER_AT,  /* answer at */
                        null                 /* click at */
                    from RPT_QUIZ_RAW RAW
                    inner join (
                        select
                            REQUEST_ID as ANSWER_ID,
                            ANSWER_AT
                        from RPT_AD_ANSWER
                    ) ANSWER
                       on RAW.REQUEST_ID = ANSWER_ID
                    where ANSWER.ANSWER_AT between ? and ?
                    ON DUPLICATE KEY UPDATE  ANSWER_CNT = values(ANSWER_CNT)
                    , ANSWER_AT = values(ANSWER_AT)
                    ;
                    """
                ,startDate, endDate
        );
    }
}
