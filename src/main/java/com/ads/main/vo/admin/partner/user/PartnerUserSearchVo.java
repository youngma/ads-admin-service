package com.ads.main.vo.admin.partner.user;

import com.querydsl.core.BooleanBuilder;
import com.ads.main.core.enums.user.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.ads.main.entity.QPartnerUserEntity.partnerUserEntity;


@Getter
@Setter
@Builder
public class PartnerUserSearchVo {

    /**
     * 광고주 순번
     */
    private Long partnerSeq;

    /**
     * 사용자 아이디
     */
    protected String userId;

    /**
     * 사용자 명
     */
    protected String userName;

    protected UserStatus userStatus;


    public BooleanBuilder where() {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(partnerUserEntity.partnerEntity.partnerSeq.eq(partnerSeq));

        if (userId != null && !userId.isBlank()) {
            builder.and(partnerUserEntity.userEntity.userId.contains(userId));
        }

        if (userName != null && !userName.isBlank()) {
            builder.and(partnerUserEntity.userEntity.userName.contains(userName));
        }

        if (userStatus != null) {
            builder.and(partnerUserEntity.userEntity.userStatus.eq(userStatus));
        }

        return builder;
    }
}
