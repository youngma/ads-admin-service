package com.ads.main.core.security.config.dto;

import com.ads.main.vo.admin.advertiser.AdvertiserVo;
import com.ads.main.vo.admin.partner.PartnerVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.token.Sha512DigestUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;


@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userSeq;
    private String userId;
    private String password;
    private String phoneNumber;
    private String userName;

    private Long advertiserSeq;
    private String advertiserName;
    private Long partnerSeq;
    private String partnerName;

    public LoginInfo(Long userSeq, String userId, String password, String phoneNumber, String userName) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }

    public void setPartner(PartnerVo partnerVo) {
        this.partnerName = partnerVo.getBusinessName();
        this.partnerSeq = partnerVo.getPartnerSeq();
    }

    public void setAdvertiser(AdvertiserVo advertiserVo) {
        this.partnerName = advertiserVo.getBusinessName();
        this.advertiserSeq = advertiserVo.getAdvertiserSeq();
    }
}
