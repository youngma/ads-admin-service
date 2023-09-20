package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import com.ads.main.entity.AdCampaignMasterEntity;
import com.ads.main.entity.AdvertiserAccountEntity;
import com.ads.main.entity.AdvertiserUserEntity;
import com.ads.main.entity.FilesEntity;
import com.ads.main.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 광고주
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ADVERTISER")
@Slf4j
public class AdvertiserEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 광고주 순번
     */
    @Id
    @Column(name = "ADVERTISER_SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advertiserSeq;

    /**
     * 광고주 명
     */
    @Column(name = "BUSINESS_NAME", nullable = false)
    private String businessName;

    /**
     * 사업자 번호
     */
    @Column(name = "BUSINESS_NUMBER", nullable = false)
    private String businessNumber;


    /**
     * 사업자 등록증 파일
     */
//    private String businessRegistrationFile;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BUSINESS_REGISTRATION_FILE", referencedColumnName = "FILE_SEQ")
    private FilesEntity filesEntity;


    /**
     * 사업자 전화 번호
     */
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    /**
     * 사업자 이메일
     */
    @Column(name = "EMAIL", nullable = false)
    private String email;


    /**
     * 세금 계산서 발행 이메일
     */
    @Column(name = "TAX_BILL_EMAIL", nullable = false)
    private String taxBillEmail;


    /**
     * 광고주 명
     */
    @Column(name = "ADVERTISER_NAME", nullable = false)
    private String advertiserName;


    @OneToMany(mappedBy = "advertiserEntity")
    @ToString.Exclude
    private List<AdvertiserUserEntity> advertiserUserEntities = new ArrayList<>();


    @OneToMany(mappedBy = "advertiserEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<AdvertiserAccountEntity> advertiserAccountEntities = new ArrayList<>();


    @OneToMany(mappedBy = "advertiserEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<AdCampaignMasterEntity> adCampaignMasterEntities = new ArrayList<>();


    public void addUser(UserEntity userEntity) {
        AdvertiserUserEntity advertiserUserEntity = new AdvertiserUserEntity();

        advertiserUserEntity.setAdvertiserEntity(this);
        advertiserUserEntity.setUserEntity(userEntity);

        this.advertiserUserEntities.add(advertiserUserEntity);
    }

    public void addAccount(AdvertiserAccountEntity advertiserAccountEntity) {
        advertiserAccountEntity.setAdvertiserEntity(this);
        this.advertiserAccountEntities.add(advertiserAccountEntity);
    }

    public AdCampaignMasterEntity addCampaign(AdCampaignMasterEntity adCampaignEntity) {
        adCampaignEntity.setAdvertiserEntity(this);
        this.adCampaignMasterEntities.add(adCampaignEntity);
        return adCampaignEntity;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AdvertiserEntity that = (AdvertiserEntity) o;
        return getAdvertiserSeq() != null && Objects.equals(getAdvertiserSeq(), that.getAdvertiserSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
