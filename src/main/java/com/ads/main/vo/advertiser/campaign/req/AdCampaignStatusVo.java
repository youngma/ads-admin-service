package com.ads.main.vo.advertiser.campaign.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class AdCampaignStatusVo implements Serializable {
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
     * 처리 메시지
     */
    private String message;

}
