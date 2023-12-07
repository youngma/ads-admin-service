package com.ads.main.vo.admin.advertiser.campaign.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ads.main.vo.admin.FilesVo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class AdSmartStoreVo implements Serializable {
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
     * 이미지
     */
    private FilesVo image;


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
     * 지급 조건
     */
    private String paymentTerms;

    /**
     * 지급 조건 시간
     */
    private Integer holdingTime;


    /**
     * 총 예산
     */
    private BigDecimal totalBudget;


    /**
     * 광고 단가
     */
    private BigDecimal adPrice;


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
    @NotNull(message = "updatedAt can not null")
    private LocalDateTime updatedAt;


    /**
     * 수정자
     */
    private String updatedId;

}
