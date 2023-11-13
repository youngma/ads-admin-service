package com.ads.main.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "RPT_SINK_TIME")
@EntityListeners(AuditingEntityListener.class)
public class RptSinkTimeEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 스케쥴 명
     */
    @Id
    @Column(name = "SCHEDULE_NAME", nullable = false)
    private String scheduleName;

    /**
     * 마지막 적재 시간
     */
    @Column(name = "LAST_SINK", nullable = false)
    private String lastSink;

    /**
     * 최종 업데이트 시간
     */
    @Column(name = "LAST_UPDATE_TIM", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdateTim;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RptSinkTimeEntity that = (RptSinkTimeEntity) o;
        return getScheduleName() != null && Objects.equals(getScheduleName(), that.getScheduleName());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
