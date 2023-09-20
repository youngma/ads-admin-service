package com.ads.main.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class FilesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    private Boolean newFile = false;

    /**
     * 파일 순번
     */
    @NotNull(message = "fileSeq can not null")
    private Long fileSeq;


    /**
     * 파일 타입
     */
    private String fileType;


    /**
     * 파일 원본명
     */
    @NotBlank(message = "사업자 등록증 파일 원본 명은 필수 입니다.")
    private String originName;


    /**
     * 파일 명
     */
    @NotBlank(message = "사업자 등록증 파일은 필수 입니다.")
    private String fileName;


    /**
     * 등록 일자
     */
    private Date insertedAt;


    /**
     * 등록자
     */
    private String insertedId;


    /**
     * 수정 일자
     */
    private Date updatedAt;


    /**
     * 수정자
     */
    private String updatedId;

}
