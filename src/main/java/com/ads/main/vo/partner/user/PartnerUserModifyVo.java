package com.ads.main.vo.partner.user;

import com.ads.main.vo.admin.UserModifyVo;
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
