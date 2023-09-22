package com.ads.main.vo.advertiser.campaign.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.vo.advertiser.AdvertiserVo;
import com.ads.main.vo.advertiser.campaign.resp.AdSmartStoreVo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class AdCampaignMasterVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 순번
     */
    private Long seq;


    /**
     * 광고주 순번
     */
    private AdvertiserVo advertiser; // advertiserSeq;


    /**
     * 캠페인 명
     */
    private String campaignName;


    /**
     * 캠페인 타입
     */
    private String campaignType;
    private String campaignTypeName;

    public String getCampaignTypeName() {
        return CampaignType.of(this.campaignType).getName();
    }


    /**
     * 캠페인 코드
     */
    private String campaignCode;


    /**
     * 캠페인 설명
     */
    private String campaignDesc;


    /**
     * 총 참여 가능 인원 횟수
     */
    private Integer totalParticipationLimit;


    /**
     * 일일 참여제한 횟수
     */
    private Integer dayParticipationLimit;


    /**
     * 광고 시작 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime adStartDate;


    /**
     * 광고 종료 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime adEndDate;


    /**
     * 캠페인 상태
     */
    private String campaignStatus;
    private String campaignStatusName;
    public String getCampaignStatusName() {
        return CampaignStatus.of(this.campaignStatus).getName();
    }

    /**
     * 등록일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertedAt;


    /**
     * 등록자
     */
    private String insertedId;


    /**
     * 수정일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    /**
     * 수정자
     */
    private String updatedId;


    /**
     * 요청 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestAt;

    /**
     * 승인 처리 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvalAt;

    /**
     * 보류 처리 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime holdAt;

    /**
     * 거절 처리 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rejectAt;

    /**
     * 보류 메시지
     */
    private String holdMessage;

    /**
     * 거절 메시지
     */
    private String rejectMessage;


    private AdSmartStoreVo smartStore;
    private AdQuizVo quiz;
}
