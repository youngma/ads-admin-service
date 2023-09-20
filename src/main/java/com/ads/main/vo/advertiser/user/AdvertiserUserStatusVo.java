package com.ads.main.vo.advertiser.user;

import com.ads.main.vo.admin.UserStatusVo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class AdvertiserUserStatusVo extends UserStatusVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long advertiserSeq;

}
