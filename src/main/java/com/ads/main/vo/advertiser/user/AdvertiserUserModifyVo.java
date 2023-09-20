package com.ads.main.vo.advertiser.user;

import com.ads.main.vo.admin.UserModifyVo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class AdvertiserUserModifyVo extends UserModifyVo implements Serializable {

    private Long advertiserSeq;

}
