package com.ads.main.repository.querydsl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.entity.UserEntity;
import com.ads.main.vo.admin.user.UserSearchVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ads.main.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class QUserRepository {


    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;


    public void updateUserStatus(List<Long> userIds, UserStatus userStatus) {

        jpaQueryFactory.update(userEntity)
                .set(userEntity.userStatus, userStatus)
                .where(userEntity.userSeq.in(userIds))
                .execute();

        entityManager.clear();
        entityManager.close();
    }


    public Page<UserEntity> searchUsers(UserSearchVo searchVo, Pageable pageable) {

        List<UserEntity> users = jpaQueryFactory.selectFrom(userEntity)
                .where(searchVo.where())
                .orderBy(userEntity.userSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(userEntity.count())
                .from(userEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(users, pageable, Objects.requireNonNullElse(count, 0L));
    }
}
