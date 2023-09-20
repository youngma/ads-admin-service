package com.ads.main.vo.admin;

import com.querydsl.core.BooleanBuilder;
import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.core.security.config.dto.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

import static com.ads.main.entity.QUserEntity.userEntity;


@Data
@Builder
public class UserSearchVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 사용자 아이디
     */
    protected String userId;


    /**
     * 사용자 명
     */
    @Getter
    protected String userName;


    /**
     * 사용자 권한(파트너, 관리자, 매체)
     */
    protected Role userRole;


    /**
     * 사용자 전화 번호
     */
    protected String phoneNumber;

    protected UserStatus userStatus;


    public BooleanBuilder where() {

        BooleanBuilder builder = new BooleanBuilder();

        if (userId != null && !userId.isBlank()) {
            builder.and(userEntity.userId.contains(userId));
        }

        if (userName != null && !userName.isBlank()) {
            builder.and(userEntity.userName.contains(userName));
        }

        if (phoneNumber != null && !phoneNumber.isBlank()) {
            builder.and(userEntity.phoneNumber.contains(phoneNumber));
        }

        if (userStatus != null) {
            builder.and(userEntity.userStatus.eq(userStatus));
        }

        return builder;
    }
}
