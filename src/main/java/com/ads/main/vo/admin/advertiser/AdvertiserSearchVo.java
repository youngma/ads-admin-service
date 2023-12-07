package com.ads.main.vo.admin.advertiser;

import com.querydsl.core.BooleanBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static com.ads.main.entity.QAdvertiserEntity.advertiserEntity;


@Data
@Builder
public class AdvertiserSearchVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


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
            builder.and(advertiserEntity.businessName.contains(this.businessName));
        }

        if (this.businessNumber != null && !businessNumber.isBlank()) {
            builder.and(advertiserEntity.businessNumber.eq(this.businessNumber));
        }

        if (this.phoneNumber != null && !phoneNumber.isBlank()) {
            builder.and(advertiserEntity.phoneNumber.contains(this.phoneNumber));
        }

        if (this.email != null && !email.isBlank()) {
            builder.and(advertiserEntity.email.eq(this.email));
        }

        return builder;
    }

}
