package com.ads.main.vo.admin.advertiser.account;

import com.querydsl.core.BooleanBuilder;
import com.ads.main.core.enums.common.Bank;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static com.ads.main.entity.QAdvertiserAccountEntity.advertiserAccountEntity;


@Data
@Builder
public class AdvertiserAccountSearchVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;




    /**
     * 광고주 순번
     */
    private Long advertiserSeq;
//    private AdvertiserVo advertiser;


    /**
     * 은행코드
     */
    private String bankCode;


    /**
     * 계좌번호
     */
    private String bankAccount;


    /**
     * 예금주
     */
    private String accountHolder;


    /**
     * 사용 여부
     */
    private String accountUse;

    public BooleanBuilder where() {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(advertiserAccountEntity.advertiserEntity.advertiserSeq.eq(this.advertiserSeq));

        if (this.bankCode != null && !bankCode.isBlank()) {
            builder.and(advertiserAccountEntity.bankCode.eq(Bank.of(this.bankCode)));
        }

        if (this.bankAccount != null && !bankAccount.isBlank()) {
            builder.and(advertiserAccountEntity.bankAccount.eq(this.bankAccount));
        }

        if (this.accountHolder != null && !accountHolder.isBlank()) {
            builder.and(advertiserAccountEntity.accountHolder.eq(this.accountHolder));
        }

        if ("true".equals(this.accountUse)) {
            builder.and(advertiserAccountEntity.accountUse.eq(true));
        } else if ("false".equals(this.accountUse)){
            builder.and(advertiserAccountEntity.accountUse.eq(false));
        }

        return builder;
    }


}
