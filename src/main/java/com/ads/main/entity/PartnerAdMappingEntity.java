package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 매체사 지면별 광고 맵핑
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "PARTNER_AD_MAPPING")
public class PartnerAdMappingEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 매체사 지면별 광고 맵핑 순번
     */
    @Id
    @Column(name = "SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    /**
     * 지면 순번
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_SEQ", nullable = false)
    @ToString.Exclude
    private PartnerAdGroupEntity partnerAdGroupEntity;

    /**
     * 캠페인 순번
     */
    @Column(name = "CAMPAIGN_SEQ", nullable = false)
    private Long campaignSeq;

//
//    public static PartnerAdMappingEntity init(Long groupSeq, Long campaignSeq) {
//
//        PartnerAdMappingEntity partnerAdMappingEntity = new PartnerAdMappingEntity();
//
//        partnerAdMappingEntity.setGroupSeq(groupSeq);
//        partnerAdMappingEntity.setCampaignSeq(campaignSeq);
//
//        return partnerAdMappingEntity;
//    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PartnerAdMappingEntity that = (PartnerAdMappingEntity) o;
        return getSeq() != null && Objects.equals(getSeq(), that.getSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
