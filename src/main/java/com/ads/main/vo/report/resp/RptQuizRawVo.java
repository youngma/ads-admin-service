package com.ads.main.vo.report.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
public class RptQuizRawVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 광고 요청 코드
     */
    private String requestId;


    /**
     * 그룹 순번
     */
    private String groupCode;


    /**
     * 광고 코드
     */
    private String campaignCode;


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
    private Long detailCnt;


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
     * 요청 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestAt;


    /**
     * 노출 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime impressionAt;


    /**
     * 힌트 클릭 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hintAt;


    /**
     * 상세 노출 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime detailAt;


    /**
     * 정답 등록 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime answerAt;


    /**
     * 클릭 처리 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime clickAt;

}
