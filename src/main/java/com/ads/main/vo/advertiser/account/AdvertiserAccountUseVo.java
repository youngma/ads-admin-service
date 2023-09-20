package com.ads.main.vo.advertiser.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class AdvertiserAccountUseVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 광고주 순번
     */
    private Long advertiserSeq;

    /**
     * 순번
     */
    @NotNull(message = "seq can not null")
    private Long seq;

}
