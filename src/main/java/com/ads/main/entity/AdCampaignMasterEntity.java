package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.core.enums.campaign.convert.CampaignStatusConvert;
import com.ads.main.core.enums.campaign.convert.CampaignTypeConvert;
import com.ads.main.entity.AdSmartStoreEntity;
import com.ads.main.entity.AdvertiserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * 광고_캠페인_마스터
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "AD_CAMPAIGN_MASTER")
public class AdCampaignMasterEntity  extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 순번
     */
    @Id
    @Column(name = "SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    /**
     * 광고주 순번
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADVERTISER_SEQ")
    @ToString.Exclude
    private AdvertiserEntity advertiserEntity;



    /**
     * 캠페인 명
     */
    @Column(name = "CAMPAIGN_NAME")
    private String campaignName;

    /**
     * 캠페인 타입
     */
    @Column(name = "CAMPAIGN_TYPE")
    @Convert(converter = CampaignTypeConvert.class)
    private CampaignType campaignType;


    /**
     * 캠페인 코드
     */
    @Column(name = "CAMPAIGN_CODE")
    private String campaignCode;

    /**
     * 캠페인 설명
     */
    @Column(name = "CAMPAIGN_DESC")
    private String campaignDesc;

    /**
     * 총 참여 가능 인원 횟수
     */
    @Column(name = "TOTAL_PARTICIPATION_LIMIT")
    private Integer totalParticipationLimit;

    /**
     * 일일 참여 제한 횟수
     */
    @Column(name = "DAY_PARTICIPATION_LIMIT")
    private Integer dayParticipationLimit;

    /**
     * 광고 시작 일자
     */
    @Column(name = "AD_START_DATE", nullable = false)
    private LocalDateTime adStartDate;

    /**
     * 광고 종료 일자
     */
    @Column(name = "AD_END_DATE", nullable = false)
    private LocalDateTime adEndDate;

    /**
     * 캠페인 상태
     */
    @Column(name = "CAMPAIGN_STATUS")
    @Convert(converter = CampaignStatusConvert.class)
    private CampaignStatus campaignStatus;

    /**
     * 노출 상태
     */
    @Column(name = "EXPOSURE_STATUS")
    private boolean exposureStatus;


    /**
     * 총 예산
     */
    @Column(name = "TOTAL_BUDGET")
    private BigDecimal totalBudget;

    /**
     * 광고 단가
     */
    @Column(name = "AD_PRICE")
    private BigDecimal adPrice;

    /**
     * 광고 연동코드
     */
    @Column(name = "IF_AD_CODE")
    private String ifAdCode;



    public void request() {
        this.campaignStatus = CampaignStatus.Request;
        this.requestAt = LocalDateTime.now();

        if (this.adSmartStoreEntity != null) {
            this.adSmartStoreEntity.setAdCampaignMasterEntity(this);
        }

        if (this.adQuizEntity != null) {
            this.adQuizEntity.setAdCampaignMasterEntity(this);
        }
    }

    public void approval() {
        this.campaignStatus = CampaignStatus.Approval;
        this.approvalAt = LocalDateTime.now();
        exposure();
    }

    public void hold(String message) {
        this.campaignStatus = CampaignStatus.Hold;
        this.holdAt = LocalDateTime.now();
        this.holdMessage = message;
        non_exposure();
    }

    public void reject(String message) {
        this.campaignStatus = CampaignStatus.Reject;
        this.rejectAt = LocalDateTime.now();
        this.rejectMessage = message;
        non_exposure();
    }

    public void exposure() {
        this.exposureStatus = true;
    }

    public void non_exposure() {
        this.exposureStatus = false;
    }

    /**
     * 요청 시간
     */
    @Column(name = "REQUEST_AT", nullable = false)
    @CreatedDate
    private LocalDateTime requestAt;

    /**
     * 승인 처리 시간
     */
    @Column(name = "APPROVAL_AT", nullable = false)
    private LocalDateTime approvalAt;

    /**
     * 보류 처리 시간
     */
    @Column(name = "HOLD_AT", nullable = false)
    private LocalDateTime holdAt;

    /**
     * 거절 처리 시간
     */
    @Column(name = "REJECT_AT", nullable = false)
    private LocalDateTime rejectAt;

    /**
     * 보류 메시지
     */
    @Column(name = "HOLD_MESSAGE", nullable = false)
    private String holdMessage;

    /**
     * 거절 메시지
     */
    @Column(name = "REJECT_MESSAGE", nullable = false)
    private String rejectMessage;


    @OneToOne(mappedBy = "adCampaignMasterEntity", cascade = CascadeType.ALL)
    private AdSmartStoreEntity adSmartStoreEntity;


    @OneToOne(mappedBy = "adCampaignMasterEntity", cascade = CascadeType.ALL)
    private AdQuizEntity adQuizEntity;


    /**
     * 사용자 수수료 비율
     */
    @Column(name = "USER_COMMISSION_RATE", nullable = false)
    private Integer userCommissionRate;

    /**
     * 매체사 수수료 비율
     */
    @Column(name = "COMMISSION_RATE", nullable = false)
    private Integer commissionRate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AdCampaignMasterEntity that = (AdCampaignMasterEntity) o;
        return getSeq() != null && Objects.equals(getSeq(), that.getSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
