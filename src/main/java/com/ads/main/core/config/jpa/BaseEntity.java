package com.ads.main.core.config.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    /**
     * 등록 일자
     */
    @CreatedDate
    @Column(name = "INSERTED_AT")
    LocalDateTime insertedAt = LocalDateTime.now();

    /**
     * 등록자
     */
    @Column(name = "INSERTED_ID")
    private String insertedId;

    /**
     * 수정 일자
     */
    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    LocalDateTime updatedAt = LocalDateTime.now();


    /**
     * 수정자
     */
    @Column(name = "UPDATED_ID")
    private String updatedId;

}
