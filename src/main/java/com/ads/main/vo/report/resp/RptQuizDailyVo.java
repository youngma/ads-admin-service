package com.ads.main.vo.report.resp;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class RptQuizDailyVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 리포팅 일자
     */
    @NotNull(message = "rptDate can not null")
    private String rptDate;


    /**
     * 그룹 순번
     */
    @NotNull(message = "groupCnt can not null")
    private Long groupCnt;


    /**
     * 광고 코드
     */
    @NotNull(message = "campaignCnt can not null")
    private Long campaignCnt;


    /**
     * 광고 요청 수
     */
    private Long reqCnt;


    /**
     * 광고 노출 수
     */
    private Long impressionCnt;


    /**
     * 상세 노출 수
     */
    private Integer detailCnt;


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


    /**
     * 광고 단가
     */
    private Long adCost;


    /**
     * 파트너 지급 금액
     */
    private Long partnerCommission;



    /**
     * 사용자 지급 금액
     */
    private Long userCommission;
}
