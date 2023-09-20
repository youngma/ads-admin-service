package com.ads.main.entity;

import com.ads.main.entity.PartnerEntity;
import com.ads.main.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 매체사_사용자
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "PARTNER_USER")
public class PartnerUserEntity implements Serializable {

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
    @JoinColumn(name = "PARTNER_SEQ", nullable = false)
    @ToString.Exclude
    private PartnerEntity partnerEntity;

    /**
     * 사용자 순번
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_SEQ", nullable = false)
    @ToString.Exclude
    private UserEntity userEntity;
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PartnerUserEntity that = (PartnerUserEntity) o;
        return getSeq() != null && Objects.equals(getSeq(), that.getSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
