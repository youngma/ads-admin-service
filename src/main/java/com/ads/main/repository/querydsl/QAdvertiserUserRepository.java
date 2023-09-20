package com.ads.main.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ads.main.entity.UserEntity;
import com.ads.main.vo.advertiser.user.AdvertiserUserSearchVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.ads.main.entity.QAdvertiserEntity.advertiserEntity;
import static com.ads.main.entity.QAdvertiserUserEntity.advertiserUserEntity;
import static com.ads.main.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class QAdvertiserUserRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<UserEntity> findAdvertiserUsersByAdvertiserSeqAndUserId(long advertiserSeq, String userId) {
        return Optional.ofNullable(jpaQueryFactory.select(
                    userEntity
                )
                .from(advertiserUserEntity)
                .innerJoin(userEntity)
                .on(userEntity.userSeq.eq(advertiserUserEntity.userEntity.userSeq))
                .where(advertiserEntity.advertiserSeq.eq(advertiserSeq))
                .where(userEntity.userId.eq(userId))
                .fetchOne());
    }

    public Page<UserEntity> findAdvertiserUsers(AdvertiserUserSearchVo searchVo, Pageable pageable) {

        List<UserEntity> users =
                jpaQueryFactory.select(
                    advertiserUserEntity.userEntity
                )
                .from(advertiserUserEntity)
                .where(searchVo.where())
                .orderBy(advertiserEntity.advertiserSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(
                    advertiserUserEntity.userEntity.count()
                )
                .from(advertiserUserEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(users, pageable, Objects.requireNonNullElse(count, 0L));
    }
}
