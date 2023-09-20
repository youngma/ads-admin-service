package com.ads.main.vo.advertiser.campaign.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ads.main.core.enums.campaign.PaymentTerms;
import com.ads.main.vo.FilesVo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class AdSmartStoreRegisterVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 참여 방법
     */
    @NotBlank(message = "참여 방법 은 필수 입니다.")
    private String useHow;


    /**
     * 이미지
     */
    @NotNull(message = "이미지는 필수 입니다.")
    private FilesVo image;


    /**
     * 랜딩 URL(PC)
     */
    @NotBlank(message = "PC 랜딩 URL 은 필수 입니다.")
    private String targetUrlPc;


    /**
     * 랜딩 URL(MOBILE)
     */
    @NotBlank(message = "모바일 랜딩 URL 은 필수 입니다.")
    private String targetUrlMobile;


    /**
     * 상품 코드
     */
    @NotBlank(message = "상품 코드 은 필수 입니다.")
    private String goodsCode;


    /**
     * 지급 조건
     */
    @NotBlank(message = "지급 조건 은 필수 입니다.")
    private String paymentTerms;

    /**
     * 지급 조건 시간
     */
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Integer holdingTime;


    /**
     * 총 예산
     */
    @Min(value = 1, message = "총 예산 금액의 최소액은 1원 이상 입니다.")
    private BigDecimal totalBudget;


    /**
     * 광고 단가
     */
    @Min(value = 1, message = "광고 단가의 최소 금액은 1원 이상 입니다.")
    private BigDecimal adPrice;


}
