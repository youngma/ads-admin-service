package com.ads.main.vo.admin.report.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RptQuizXCodeVo extends DailyReportVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;



    /**
     * 그룹 순번
     */
    private String partnerName;
    private String groupCode;
    private String groupName;


    /**
     * 광고 코드
     */
    private String advertiserName;
    private String campaignCode;
    private String campaignName;


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
     * 광고 금액
     */
    private Long adCost;
    private BigDecimal originAdCost;


    /**
     * 파트너 수수료
     */
    private Long partnerCommission;


    /**
     * 사용자 수수료
     */
    private Long userCommission;


    /**
     * 사용자 지급 포인트
     */
    private Long adReword;

}
