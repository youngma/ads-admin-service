package com.ads.main.vo.admin.partner.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class PartnerAccountUseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 광고주 순번
     */
    @NotNull(message = "partnerSeq can not null")
    private Long partnerSeq;

    /**
     * 순번
     */
    @NotNull(message = "seq can not null")
    private Long seq;

}
