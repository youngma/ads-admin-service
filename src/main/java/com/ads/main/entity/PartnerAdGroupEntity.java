package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import com.ads.main.core.enums.advertiser.AdGroupStatus;
import com.ads.main.core.enums.advertiser.convert.AdGroupStatusConvert;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.core.enums.campaign.convert.CampaignTypeConvert;
import com.ads.main.entity.FilesEntity;
import com.ads.main.entity.PartnerEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * 매체사 광고 그룹
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "PARTNER_AD_GROUP")
@DynamicUpdate
public class PartnerAdGroupEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 광고 그룹 순번
     */
    @Id
    @Column(name = "GROUP_SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupSeq;

    /**
     * 매체사 순번
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTNER_SEQ")
    @ToString.Exclude
    private PartnerEntity partnerEntity;

    /**
     * 서비스 타입
     */

    @Convert(converter = CampaignTypeConvert.class)
    @Column(name = "AD_TYPE", nullable = false)
    private CampaignType adType;


    /**
     * 광고 그룹 코드
     */
    @Column(name = "GROUP_CODE", nullable = false)
    private String groupCode;


    /**
     * 광고 그룹 명
     */
    @Column(name = "GROUP_NAME", nullable = false)
    private String groupName;

    /**
     * 서비스 로고 파일
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LOGO_FILE", referencedColumnName = "FILE_SEQ")
    private com.ads.main.entity.FilesEntity logoFileEntity;

    /**
     * 포인트 로고 파일
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "POINT_ICON_FILE", referencedColumnName = "FILE_SEQ")
    private FilesEntity pointIconFileEntity;

    /**
     * 포인트 명
     */
    @Column(name = "POINT_NAME", nullable = false)
    private String pointName;

    /**
     * 서비스 콜백 URL
     */
    @Column(name = "CALL_BACK_URL", nullable = false)
    private String callBackUrl;

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

    /**
     * 포인트 교환 비율
     */
    @Column(name = "REWORD_RATE", nullable = false)
    private Integer rewordRate;

    /**
     * 서비스 상태
     */
    @Convert(converter = AdGroupStatusConvert.class)
    @Column(name = "GROUP_STATUS", nullable = false)
    private AdGroupStatus groupStatus;

    @OneToMany(mappedBy = "partnerAdGroupEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PartnerAdMappingEntity> partnerAdMappingEntities = new ArrayList<>();

    public List<PartnerAdMappingEntity> mappingAds(HashSet<Long> campaignSeqList) {

        campaignSeqList.forEach(campaign -> {
            PartnerAdMappingEntity partnerAdMappingEntity = new PartnerAdMappingEntity();
            partnerAdMappingEntity.setPartnerAdGroupEntity(this);
            partnerAdMappingEntity.setCampaignSeq(campaign);
            partnerAdMappingEntities.add(partnerAdMappingEntity);
        });

        return this.partnerAdMappingEntities;
    }


    public void approval() {
        this.groupStatus = AdGroupStatus.Approval;
        this.approvalAt = LocalDateTime.now();
    }

    public void hold(String message) {
        this.groupStatus = AdGroupStatus.Hold;
        this.holdAt = LocalDateTime.now();
        this.holdMessage = message;
    }

    public void reject(String message) {
        this.groupStatus = AdGroupStatus.Reject;
        this.rejectAt = LocalDateTime.now();
        this.rejectMessage = message;
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


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PartnerAdGroupEntity that = (PartnerAdGroupEntity) o;
        return getGroupSeq() != null && Objects.equals(getGroupSeq(), that.getGroupSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
