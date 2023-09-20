package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import com.ads.main.core.enums.common.Bank;
import com.ads.main.core.enums.common.convert.BankConvert;
import com.ads.main.entity.FilesEntity;
import com.ads.main.entity.PartnerEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 매체사_계좌정보
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "PARTNER_ACCOUNT")
@DynamicUpdate
public class PartnerAccountEntity extends BaseEntity implements Serializable {

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
     * 매체사 순번
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTNER_SEQ")
    @ToString.Exclude
    private PartnerEntity partnerEntity;

    /**
     * 은행 코드
     */
    @Column(name = "BANK_CODE")
    @Convert(converter = BankConvert.class)
    private Bank bankCode;


    /**
     * 계좌 번호
     */
    @Column(name = "BANK_ACCOUNT")
    private String bankAccount;

    /**
     * 예금주
     */
    @Column(name = "ACCOUNT_HOLDER")
    private String accountHolder;

    /**
     * 사용 여부
     */
    @Column(name = "ACCOUNT_USE")
    private Boolean accountUse;

    public void used() {
        this.accountUse = true;
    }

    public void unused() {
        this.accountUse = false;

    }

    /**
     * 계좌 사본 파일
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_FILE", referencedColumnName = "FILE_SEQ")
    private FilesEntity filesEntity;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PartnerAccountEntity that = (PartnerAccountEntity) o;
        return getSeq() != null && Objects.equals(getSeq(), that.getSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
