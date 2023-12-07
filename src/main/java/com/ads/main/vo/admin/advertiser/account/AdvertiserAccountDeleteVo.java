package com.ads.main.vo.admin.advertiser.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class AdvertiserAccountDeleteVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 순번
     */
    @NotNull
    private Long seq;


    /**
     * 광고주 순번
     */
    @NotNull
    private Long advertiserSeq;

}
