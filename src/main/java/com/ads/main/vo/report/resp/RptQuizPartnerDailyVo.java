package com.ads.main.vo.report.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RptQuizPartnerDailyVo extends DailyReportVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private Long partnerCount;
    private Long adGroupCount;

    /**
     * 그룹 순번
     */
    @NotNull(message = "groupCode can not null")
    private String groupCode;
    private String groupName;
    private Long partnerSeq;
    private String businessName;


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


    /**
     * 광고 금액
     */
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertedAt;


    /**
     * 수정 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
