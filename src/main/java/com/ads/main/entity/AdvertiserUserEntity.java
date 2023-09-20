package com.ads.main.entity;

import com.ads.main.entity.AdvertiserEntity;
import com.ads.main.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 광고주_사용자
 */
@Getter
@Setter
@ToString()
@RequiredArgsConstructor
@Entity
@Table(name = "ADVERTISER_USER")
public class AdvertiserUserEntity implements Serializable {

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
    @JoinColumn(name = "ADVERTISER_SEQ", nullable = false)
    @ToString.Exclude
    private AdvertiserEntity advertiserEntity;

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
        AdvertiserUserEntity that = (AdvertiserUserEntity) o;
        return getSeq() != null && Objects.equals(getSeq(), that.getSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
