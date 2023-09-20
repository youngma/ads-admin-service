package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.enums.user.convert.UserStatusConvert;
import com.ads.main.core.security.config.dto.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * 사용자 Entity
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "USER")
@DynamicUpdate
public class UserEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 사용자 SEQ
     */
    @Id
    @Column(name = "USER_SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    /**
     * 사용자 아이디
     */
    @Column(name = "USER_ID", nullable = false)
    private String userId;

    /**
     * 사용자 명
     */
    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    /**
     * 사용자 권한(파트너, 관리자, 매체)
     */
    @Column(name = "USER_ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    /**
     * 사용자 비밀번호
     */
    @Column(name = "USER_PASSWORD", nullable = false)
    private String userPassword;

    /**
     * 사용자 전화 번호
     */
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    /**
     * 유저상태(활성, 휴면, 탈퇴)
     */
    @Column(name = "USER_STATUS", nullable = false)
    @Convert(converter = UserStatusConvert.class)
    private UserStatus userStatus;

    public void enable() {
        this.userStatus = UserStatus.Enable;
    }

    public void disable() {
        this.userStatus = UserStatus.Disable;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserEntity that = (UserEntity) o;
        return getUserSeq() != null && Objects.equals(getUserSeq(), that.getUserSeq());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    protected boolean canEqual(Object other) {
        return other instanceof UserEntity;
    }


    public boolean comparePassword(String password) {
        return this.userPassword.equals(password);
    }
}
