package com.ads.main.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ads.main.entity.UserEntity;
import com.ads.main.vo.partner.user.PartnerUserSearchVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.ads.main.entity.QPartnerEntity.partnerEntity;
import static com.ads.main.entity.QPartnerUserEntity.partnerUserEntity;
import static com.ads.main.entity.QUserEntity.userEntity;


@Repository
@RequiredArgsConstructor
public class QPartnerUserRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<UserEntity> findPartnerUser(long advertiserSeq, String userId) {
                return Optional.ofNullable(jpaQueryFactory.select(
                    userEntity
                )
                .from(partnerUserEntity)
                .innerJoin(userEntity)
                .on(userEntity.userSeq.eq(partnerUserEntity.userEntity.userSeq))
                .where(partnerEntity.partnerSeq.eq(advertiserSeq).and(userEntity.userId.eq(userId)))
                .fetchOne());
    }

    public Page<UserEntity> findAdvertiserUsers(PartnerUserSearchVo searchVo, Pageable pageable) {

        List<UserEntity> users =
                jpaQueryFactory.select(
                    partnerUserEntity.userEntity
                )
                .from(partnerUserEntity)
                .where(searchVo.where())
                .orderBy(partnerUserEntity.seq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(
                    partnerUserEntity.userEntity.count()
                )
                .from(partnerUserEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(users, pageable, Objects.requireNonNullElse(count, 0L));
    }
}
