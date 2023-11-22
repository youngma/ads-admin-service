package com.ads.main.vo.advertiser.campaign.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.core.enums.campaign.PaymentTerms;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class AdCampaignRegVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 광고주 순번
     */
    private Long advertiserSeq;


    /**
     * 캠페인 명
     */
    @NotBlank(message = "캠페인 명 은 필수 입니다.")
    private String campaignName;


    /**
     * 캠페인 설명
     */
    @NotBlank(message = "캠페인 명 은 필수 입니다.")
    private String campaignDesc;


    /**
     * 참여 방법
     */
    @NotBlank(message = "참여 방법 은 필수 입니다.")
    private String useHow;


    /**
     * 이미지
     */
    @NotBlank(message = "이미지 은 필수 입니다.")
    private String image;


    /**
     * 캠페인 타입
     */
    @NotBlank(message = "캠페인 타입 은 필수 입니다.")
    private String campaignType;
    private String campaignTypeName;

    public String getCampaignTypeName() {
        return CampaignType.of(this.campaignType).getName();
    }

    /**
     * 랜딩 URL
     */
    @NotBlank(message = "랜딩 URL 은 필수 입니다.")
    private String targetUrl;


    /**
     * 상품코드
     */
    @NotBlank(message = "상품코드 은 필수 입니다.")
    private String goodsCode;


    /**
     * 지급 조건
     */
    @NotBlank(message = "지급 조건 은 필수 입니다.")
    private String paymentTerms;
    private String paymentTermsName;

    public String getPaymentTermsName() {
        return PaymentTerms.of(this.paymentTerms).getName();
    }


    /**
     * 지급 조건 시간
     */
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Integer holdingTime;

    /**
     * 광고 단가
     */
    @Min(value = 1, message = "광고 단가의 최소액은 1원 이상 입니다.")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @NotNull(message = "광고 단가 은 필수 입니다.")
    private BigDecimal adPrice;

    /**
     * 총 예산
     */
    @Min(value = 1, message = "총 예산금액의 최소액은 1원 이상 입니다.")
    @NotNull(message = "총 예산 금액 은 필수 입니다.")
    private BigDecimal totalBudget;


    /**
     * 일일 참여 제한 횟수
     */
    @Min(value = 0, message = "제한 인원이 음수일수 없습니다.")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @NotNull(message = "일일 참여 제한 횟수 은 필수 입니다.")
    private Integer dayParticipationLimit;


    /**
     * 광고 시작 일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "광고 시작 일자 은 필수 입니다.")
    private LocalDateTime adStartDate;


    /**
     * 광고 종료 일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "광고 종료 일자 은 필수 입니다.")
    private LocalDateTime adEndDate;

    private String ifAdCode;

}
