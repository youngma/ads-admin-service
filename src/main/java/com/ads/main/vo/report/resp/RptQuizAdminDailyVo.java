package com.ads.main.vo.report.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RptQuizAdminDailyVo extends DailyReportVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;



    /**
     * 광고주 수
     */
    private Long advertiserCnt;


    /**
     * 광고 캠페인 수(진행)
     */
    private Long campaignCnt;


    /**
     * 매체사 수
     */
    private Long partnerCnt;


    /**
     * 매체사 지면 수
     */
    private Long adGroupCnt;


    /**
     * 광고 요청 수
     */
    private Long reqCnt;


    /**
     * 광고 노출 수
     */
    private Long impressionCnt;


    /**
     * 정답 사용자 수
     */
    private Long answerCnt;


    /**
     * 광고 힌트 랜딩 수
     */
    private Long hintCnt;


    /**
     * 광고 클릭 랜딩 수
     */
    private Long clickCnt;



    private Long adCost;


    /**
     * 수수료(매체사)
     */
    private Long partnerCommission;


    /**
     * 수수료(고객)
     */
    private Long userCommission;


    /**
     * 등록 일자
     */
    @NotNull(message = "insertedAt can not null")
    private Date insertedAt;


    /**
     * 수정 일자
     */
    @NotNull(message = "updatedAt can not null")
    private Date updatedAt;

}
