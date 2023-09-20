package com.ads.main.vo.partner.adGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class PartnerAdGroupStatusVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 광고 그룹 순번
     */
    @NotNull(message = "광고 그룹 순번 순번은 필수 입니다.")
    private Long groupSeq;


    /**
     * 매체사 순번
     */
    @NotNull(message = "매체사 순번은 필수 입니다.")
    private Long partnerSeq;


    /**
     * 처리 메시지
     */
    @NotBlank(message = "처리 메시지는 필수 입니다.")
    private String message;

}
