package com.ads.main.vo.admin.partner.user;

import com.ads.main.vo.admin.user.UserModifyVo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class PartnerUserModifyVo extends UserModifyVo implements Serializable {

    private Long partnerSeq;

}
