package com.ads.main.vo.admin.partner.account;

import com.querydsl.core.BooleanBuilder;
import com.ads.main.core.enums.common.Bank;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static com.ads.main.entity.QPartnerAccountEntity.partnerAccountEntity;


@Data
@Builder
public class PartnerAccountSearchVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;




    /**
     * 매체사 순번
     */
    private Long partnerSeq;
//    private AdvertiserVo advertiser;


    /**
     * 은행 코드
     */
    private String bankCode;


    /**
     * 계좌 번호
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
//
        builder.and(partnerAccountEntity.partnerEntity.partnerSeq.eq(this.partnerSeq));

        if (this.bankCode != null && !bankCode.isBlank()) {
            builder.and(partnerAccountEntity.bankCode.eq(Bank.of(this.bankCode)));
        }

        if (this.bankAccount != null && !bankAccount.isBlank()) {
            builder.and(partnerAccountEntity.bankAccount.eq(this.bankAccount));
        }

        if (this.accountHolder != null && !accountHolder.isBlank()) {
            builder.and(partnerAccountEntity.accountHolder.eq(this.accountHolder));
        }

        if ("true".equals(this.accountUse)) {
            builder.and(partnerAccountEntity.accountUse.eq(true));
        } else if ("false".equals(this.accountUse)){
            builder.and(partnerAccountEntity.accountUse.eq(false));
        }

        return builder;
    }


}
