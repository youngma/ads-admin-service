package com.ads.main.repository.querydsl;

import com.ads.main.vo.admin.partner.PartnerVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ads.main.entity.UserEntity;
import com.ads.main.vo.admin.partner.user.PartnerUserSearchVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.spel.ast.Projection;
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

    public Optional<UserEntity> findPartnerUser(long partnerSeq, String userId) {
                return Optional.ofNullable(jpaQueryFactory.select(
                    userEntity
                )
                .from(partnerUserEntity)
                .innerJoin(userEntity)
                .on(userEntity.userSeq.eq(partnerUserEntity.userEntity.userSeq))
                .where(partnerEntity.partnerSeq.eq(partnerSeq).and(userEntity.userId.eq(userId)))
                .fetchOne());
    }


    public PartnerVo findPartnerByUserSeq(Long userSeq) {
        return jpaQueryFactory.select(
                    Projections.bean(
                        PartnerVo.class,
                        partnerEntity.partnerSeq,
                        partnerEntity.businessName
                    )
                )
                .from(partnerUserEntity)
                .join(partnerEntity).on(partnerUserEntity.partnerEntity.partnerSeq.eq(partnerEntity.partnerSeq))
                .where(partnerUserEntity.userEntity.userSeq.eq(userSeq))
                .fetchFirst();
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
