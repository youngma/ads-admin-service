package com.ads.main.vo.admin.partner;


import com.querydsl.core.BooleanBuilder;
import lombok.Builder;

import static com.ads.main.entity.QPartnerEntity.partnerEntity;

@Builder
public class PartnerSearchVo {

    /**
     * 광고주 명
     */
    private String businessName;


    /**
     * 사업자 번호
     */
    private String businessNumber;


    /**
     * 사업자 전화 번호
     */
    private String phoneNumber;


    /**
     * 사업자 이메일
     */
    private String email;

    public BooleanBuilder where() {

        BooleanBuilder builder = new BooleanBuilder();

        if (this.businessName != null && !businessName.isBlank()) {
            builder.and(partnerEntity.businessName.contains(this.businessName));
        }

        if (this.businessNumber != null && !businessNumber.isBlank()) {
            builder.and(partnerEntity.businessNumber.eq(this.businessNumber));
        }

        if (this.phoneNumber != null && !phoneNumber.isBlank()) {
            builder.and(partnerEntity.phoneNumber.contains(this.phoneNumber));
        }

        if (this.email != null && !email.isBlank()) {
            builder.and(partnerEntity.email.eq(this.email));
        }

        return builder;
    }
}
