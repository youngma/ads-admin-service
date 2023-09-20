package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import com.ads.main.entity.FilesEntity;
import com.ads.main.entity.PartnerAccountEntity;
import com.ads.main.entity.PartnerAdGroupEntity;
import com.ads.main.entity.PartnerUserEntity;
import com.ads.main.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 매체사
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "PARTNER")
@DynamicUpdate
public class PartnerEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 매체사 순번
     */
    @Id
    @Column(name = "PARTNER_SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partnerSeq;

    /**
     * 사업잠 명
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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
    @Column(name = "TAX_BILL_EMAIL")
    private String taxBillEmail;


    @OneToMany(mappedBy = "partnerEntity")
    @ToString.Exclude
    private List<PartnerUserEntity> partnerUserEntities = new ArrayList<>();


    @OneToMany(mappedBy = "partnerEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PartnerAccountEntity> partnerAccountEntities = new ArrayList<>();


    @OneToMany(mappedBy = "partnerEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<PartnerAdGroupEntity> partnerAdGroupEntities = new ArrayList<>();



    public com.ads.main.entity.UserEntity addUser(UserEntity userEntity) {
        PartnerUserEntity partnerUserEntity = new PartnerUserEntity();

        partnerUserEntity.setPartnerEntity(this);
        partnerUserEntity.setUserEntity(userEntity);

        this.partnerUserEntities.add(partnerUserEntity);

        return userEntity;
    }

    public PartnerAccountEntity addAccount(PartnerAccountEntity partnerAccountEntity) {
        partnerAccountEntity.setPartnerEntity(this);
        this.partnerAccountEntities.add(partnerAccountEntity);

        return partnerAccountEntity;
    }


    public PartnerAdGroupEntity addAdGroup(PartnerAdGroupEntity partnerAdGroupEntity) {
        partnerAdGroupEntity.setPartnerEntity(this);

        partnerAdGroupEntity.approval();
        this.partnerAdGroupEntities.add(partnerAdGroupEntity);

        return partnerAdGroupEntity;
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PartnerEntity that = (PartnerEntity) o;
        return getPartnerSeq() != null && Objects.equals(getPartnerSeq(), that.getPartnerSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
