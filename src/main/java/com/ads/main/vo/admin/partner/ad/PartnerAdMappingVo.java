package com.ads.main.vo.admin.partner.ad;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class PartnerAdMappingVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 매체사 지면별 광고 맵핑 순번
     */
    @NotNull(message = "seq can not null")
    private Long seq;


    /**
     * 지면 순번
     */
    @NotNull(message = "groupSeq can not null")
    private Long groupSeq;


    /**
     * 캠페인 순번
     */
    @NotNull(message = "campaignSeq can not null")
    private Long campaignSeq;

    /**
     * 등록 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertedAt;


    /**
     * 등록자
     */
    private String insertedId;


    /**
     * 수정 일자
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    /**
     * 수정자
     */
    private String updatedId;

}
