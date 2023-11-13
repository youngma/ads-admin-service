package com.ads.main.vo.advertiser.user;

import com.ads.main.core.enums.user.UserStatus;
import com.querydsl.core.BooleanBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.ads.main.entity.QAdvertiserUserEntity.advertiserUserEntity;


@Getter
@Setter
@Builder
public class AdvertiserUserSearchVo {

    /**
     * 광고주 순번
     */
    private Long advertiserSeq;

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

        builder.and(advertiserUserEntity.advertiserEntity.advertiserSeq.eq(advertiserSeq));

        if (userId != null && !userId.isBlank()) {
            builder.and(advertiserUserEntity.userEntity.userId.contains(userId));
        }

        if (userName != null && !userName.isBlank()) {
            builder.and(advertiserUserEntity.userEntity.userName.contains(userName));
        }

        if (userStatus != null) {
            builder.and(advertiserUserEntity.userEntity.userStatus.eq(userStatus));
        }

        return builder;
    }
}
