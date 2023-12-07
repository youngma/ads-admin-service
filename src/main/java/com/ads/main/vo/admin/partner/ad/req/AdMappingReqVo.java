package com.ads.main.vo.admin.partner.ad.req;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;


@Data
@Getter
@Setter
public class AdMappingReqVo  {
    private Long partnerSeq;
    private Long groupSeq;
    HashSet<Long> mappingAds;
}
