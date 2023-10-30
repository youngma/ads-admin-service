package com.ads.main.vo.report.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RptQuizAdvertiserDailyVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 리포팅 일자
     */
    @NotNull(message = "rptDate can not null")
    private String rptDate;

    private Long advertiserSeq;
    private String advertiserName;
    private String businessName;



    /**
     * 그룹 순번
     */
    @NotNull(message = "campaignCode can not null")
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
