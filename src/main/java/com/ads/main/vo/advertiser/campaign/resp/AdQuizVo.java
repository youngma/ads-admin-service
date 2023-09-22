package com.ads.main.vo.advertiser.campaign.resp;

import com.ads.main.vo.FilesVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class AdQuizVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 순번
     */
    private Long seq;


    /**
     * 캠페인 순번
     */
    private AdCampaignMasterVo adCampaignMasterVo;


    /**
     * 참여 방법
     */
    private String useHow;


    /**
     * 퀴즈 문제
     */
    private String quizTitle;

    /**
     * 퀴즈 정답
     */
    private String quizAnswer;

    /**
     * 리스트 노출 이미지
     */
    private FilesVo mainImage;

    /**
     * 상세 노출 이미지1
     */
    private FilesVo detailImage1;

    /**
     *상세 노출 이미지2
     */
    private FilesVo detailImage2;

    /**
     * 랜딩 URL(PC)
     */
    private String targetUrlPc;

    /**
     * 랜딩 URL(MOBILE)
     */
    private String targetUrlMobile;

    /**
     * 상품 코드
     */
    private String goodsCode;

    /**
     * 등록 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertedAt;

    /**
     * 등록자
     */
    private String insertedId;


    /**
     * 수정 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    /**
     * 수정자
     */
    private String updatedId;

}
