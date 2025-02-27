package com.ads.main.vo.admin.advertiser.user;

import com.ads.main.vo.admin.advertiser.AdvertiserVo;
import com.ads.main.vo.admin.user.UserVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Data
public class AdvertiserUserVo extends UserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 순번
     */
    private Long seq;


    /**
     * 광고주 순번
     */
    private AdvertiserVo advertiser;


    /**
     * 사용자 순번
     */
    private UserVo user;

}
