package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.core.enums.campaign.PaymentTerms;
import com.ads.main.core.enums.campaign.convert.CampaignStatusConvert;
import com.ads.main.core.enums.campaign.convert.CampaignTypeConvert;
import com.ads.main.core.enums.campaign.convert.PaymentTermsConvert;
import com.ads.main.entity.AdvertiserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 광고_캠페인
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "AD_CAMPAIGN")
public class AdCampaignEntity extends BaseEntity implements Serializable {

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
//    @Column(name = "ADVERTISER_SEQ")
//    private Long advertiserSeq;
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
     * 캠페인 설명
     */
    @Column(name = "CAMPAIGN_DESC")
    private String campaignDesc;

    /**
     * 참여 방법
     */
    @Column(name = "USE_HOW")
    private String useHow;

    /**
     * 이미지
     */
    @Column(name = "IMAGE")
    private String image;

    /**
     * 캠페인 코드
     */
    @Column(name = "CAMPAIGN_CODE")
    private String campaignCode;


    /**
     * 캠페인 타입
     */
    @Column(name = "CAMPAIGN_TYPE")
    @Convert(converter = CampaignTypeConvert.class)
    private CampaignType campaignType;


    /**
     * 랜딩 URL
     */
    @Column(name = "TARGET_URL")
    private String targetUrl;

    /**
     * 상품코드
     */
    @Column(name = "GOODS_CODE")
    private String goodsCode;

    /**
     * 지급 조건
     */
    @Column(name = "PAYMENT_TERMS")
    @Convert(converter = PaymentTermsConvert.class)
    private PaymentTerms paymentTerms;

    /**
     * 지급 조건 시간
     */
    @Column(name = "HOLDING_TIME")
    private Integer holdingTime;

    /**
     * 광고 단가
     */
    @Column(name = "AD_PRICE")
    private BigDecimal adPrice;

    /**
     * 총 예산
     */
    @Column(name = "TOTAL_BUDGET")
    private BigDecimal totalBudget;

    /**
     * 일일 참여제한 횟수
     */
    @Column(name = "DAY_PARTICIPATION_LIMIT")
    private Integer dayParticipationLimit;

    /**
     * 광고 시작 일자
     */
    @Column(name = "AD_START_DATE", nullable = false)
    private Date adStartDate;

    /**
     * 광고 종료 일자
     */
    @Column(name = "AD_END_DATE", nullable = false)
    private Date adEndDate;

    /**
     * 캠페인 상태
     */
    @Column(name = "CAMPAIGN_STATUS")
    @Convert(converter = CampaignStatusConvert.class)
    private CampaignStatus campaignStatus;


    public void enable() {
        this.campaignStatus = CampaignStatus.Enable;
    }

    public void disable() {
        this.campaignStatus = CampaignStatus.Disable;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AdCampaignEntity that = (AdCampaignEntity) o;
        return getSeq() != null && Objects.equals(getSeq(), that.getSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
