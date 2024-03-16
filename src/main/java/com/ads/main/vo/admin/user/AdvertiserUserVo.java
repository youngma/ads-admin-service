package com.ads.main.vo.admin.user;

import com.ads.main.vo.admin.advertiser.AdvertiserVo;
import com.ads.main.vo.admin.partner.PartnerVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Data
public class AdvertiserUserVo extends UserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private AdvertiserVo advertiserVo;

}
