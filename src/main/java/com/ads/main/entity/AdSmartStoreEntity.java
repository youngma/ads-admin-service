package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import com.ads.main.core.enums.campaign.PaymentTerms;
import com.ads.main.core.enums.campaign.convert.PaymentTermsConvert;
import com.ads.main.entity.AdCampaignMasterEntity;
import com.ads.main.entity.FilesEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 스마트 스토어_광고_마스터
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "AD_SMART_STORE")
public class AdSmartStoreEntity extends BaseEntity implements Serializable {

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
     * 캠페인 순번
     */
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "AD_CAMPAIGN_SEQ", referencedColumnName = "SEQ")
    @ToString.Exclude
    private AdCampaignMasterEntity adCampaignMasterEntity;

    /**
     * 참여 방법
     */
    @Column(name = "USE_HOW")
    private String useHow;

    /**
     * 이미지
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE", referencedColumnName = "FILE_SEQ")
    private FilesEntity image;

    /**
     * 랜딩 URL(PC)
     */
    @Column(name = "TARGET_URL_PC")
    private String targetUrlPc;

    /**
     * 랜딩 URL(MOBILE)
     */
    @Column(name = "TARGET_URL_MOBILE")
    private String targetUrlMobile;

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
     * 총 예산
     */
    @Column(name = "TOTAL_BUDGET")
    private BigDecimal totalBudget;

    /**
     * 광고 단가
     */
    @Column(name = "AD_PRICE")
    private BigDecimal adPrice;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AdSmartStoreEntity that = (AdSmartStoreEntity) o;
        return getSeq() != null && Objects.equals(getSeq(), that.getSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
