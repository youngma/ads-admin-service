package com.ads.main.vo.admin.advertiser.campaign.req;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class AdCampaignMasterModifyVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 캠페인 순번
     */
    @NotNull(message = "캠페인 순번은 필수 입니다.")
    private Long seq; // seq;


    /**
     * 광고주 순번
     */
    @NotNull(message = "광고주 순번은 필수 입니다.")
    private Long advertiserSeq; // advertiserSeq;


    /**
     * 캠페인 명
     */
    @NotBlank(message = "캠페인 명 은 필수 입니다.")
    private String campaignName;


    /**
     * 캠페인 타입
     */
    @NotBlank(message = "캠페인 타입 은 필수 입니다.")
    private String campaignType;


    /**
     * 캠페인 설명
     */
    @NotBlank(message = "캠페인 설명 은 필수 입니다.")
    private String campaignDesc;


    /**
     * 총 참여 가능 인원 횟수
     */
    @Min(value = 0, message = "총 제한 인원이 음수 일 수 없습니다.")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @NotNull(message = "총 참여 제한 횟수 은 필수 입니다.")
    private Integer totalParticipationLimit;


    /**
     * 일일 참여 제한 횟수
     */
    @Min(value = 0, message = "제한 인원이 음수 일 수 없습니다.")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @NotNull(message = "일일 참여 제한 횟수 은 필수 입니다.")
    private Integer dayParticipationLimit;


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

    /**
     * 수수로 비율
     */
    private BigDecimal commissionRate;

    /**
     * 수수로 비율(사용자)
     */
    private BigDecimal userCommissionRate;

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


    /**
     * 광고주 연동 코드
     */
    private String ifAdCode;


    private AdSmartStoreModifyVo smartStore;
    private AdQuizModifyVo quiz;



    public void validCheck() {

//        if (campaignType.equals(CampaignType.Type1.getCode())) {
//            if (smartStore == null) {
//                throw AD_SMART_AD_VALID_ERROR.throwErrors();
//            }
//        }
    }
}
