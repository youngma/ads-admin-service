package com.ads.main.entity;

import com.ads.main.core.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 파일 관리
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "FILES")
public class FilesEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 파일 순번
     */
    @Id
    @Column(name = "FILE_SEQ", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileSeq;

    /**
     * 파일타입 명
     */
    @Column(name = "FILE_TYPE")
    private String fileType;

    /**
     * 파일 원본명
     */
    @Column(name = "ORIGIN_NAME")
    private String originName;

    /**
     * 파일 명
     */
    @Column(name = "FILE_NAME")
    private String fileName;

}
