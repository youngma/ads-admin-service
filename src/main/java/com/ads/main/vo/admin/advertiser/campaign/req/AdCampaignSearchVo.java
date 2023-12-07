package com.ads.main.vo.admin.advertiser.campaign.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.BooleanBuilder;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.core.enums.campaign.PaymentTerms;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import static com.ads.main.entity.QAdCampaignMasterEntity.adCampaignMasterEntity;


@Data
@Builder
public class AdCampaignSearchVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 광고주 순번
     */
    private Set<Long> advertiserSeqList;

    /**
     * 캠페인 명
     */
    private String campaignName;


    /**
     * 캠페인 코드
     */
    private String campaignCode;

    /**
     * 캠페인 타입
     */
    private CampaignType campaignType;

    /**
     * 랜딩 URL
     */
    private String targetUrl;

    /**
     * 상품코드
     */
    private String goodsCode;

    /**
     * 지급 조건
     */
    private PaymentTerms paymentTerms;


    private String exposureStatus;

    /**
     * 광고 시작 일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime adStartDate;

    /**
     * 광고 종료 일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime adEndDate;


    /**
     * 캠페인 조회 조건
     */
    public BooleanBuilder where() {

        BooleanBuilder builder = new BooleanBuilder();

        if (this.advertiserSeqList != null && !this.advertiserSeqList.isEmpty()) {
            builder.and(adCampaignMasterEntity.advertiserEntity.advertiserSeq.in(this.advertiserSeqList));
        }

        if (campaignName != null && !campaignName.isBlank()) {
            builder.and(adCampaignMasterEntity.campaignName.contains(campaignName));
        }

        if (campaignCode != null && !campaignCode.isBlank()) {
            builder.and(adCampaignMasterEntity.campaignCode.eq(campaignCode));
        }

        if (campaignType != null) {
            builder.and(adCampaignMasterEntity.campaignType.eq(campaignType));
        }

        if (exposureStatus != null && !exposureStatus.isBlank()) {
            builder.and(adCampaignMasterEntity.exposureStatus.eq(exposureStatus.equals("1")));
        }

        return builder;
    }
}
