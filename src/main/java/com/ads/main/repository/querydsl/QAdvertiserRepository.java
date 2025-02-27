package com.ads.main.repository.querydsl;

import com.ads.main.vo.admin.advertiser.AdvertiserVo;
import com.ads.main.vo.admin.partner.PartnerVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ads.main.entity.AdvertiserEntity;
import com.ads.main.entity.AdvertiserUserEntity;
import com.ads.main.vo.admin.advertiser.AdvertiserSearchVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ads.main.entity.QAdvertiserEntity.advertiserEntity;
import static com.ads.main.entity.QAdvertiserUserEntity.advertiserUserEntity;
import static com.ads.main.entity.QPartnerEntity.partnerEntity;
import static com.ads.main.entity.QPartnerUserEntity.partnerUserEntity;
import static com.ads.main.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class QAdvertiserRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public List<AdvertiserUserEntity> findAdvertiserUsers(Long advertiserSeq) {

        return jpaQueryFactory.select(
                    advertiserUserEntity
                )
                .from(advertiserUserEntity)
                .innerJoin(advertiserUserEntity.advertiserEntity).fetchJoin()
                .innerJoin(advertiserUserEntity.advertiserEntity.filesEntity).fetchJoin()
                .leftJoin(userEntity).fetchJoin()
                .where(advertiserEntity.advertiserSeq.eq(advertiserSeq))
                .fetch();
    }

    public AdvertiserVo findAdvertiserByUserSeq(Long userSeq) {
        return jpaQueryFactory.select(
                        Projections.bean(
                                AdvertiserVo.class,
                                advertiserEntity.advertiserSeq,
                                advertiserEntity.businessName
                        )
                )
                .from(advertiserUserEntity)
                .join(advertiserEntity).on(advertiserUserEntity.advertiserEntity.advertiserSeq.eq(advertiserEntity.advertiserSeq))
                .where(advertiserUserEntity.userEntity.userSeq.eq(userSeq))
                .fetchFirst();
    }

    public Page<AdvertiserEntity> searchAdvertisers(AdvertiserSearchVo searchVo, Pageable pageable) {

        List<AdvertiserEntity> users = jpaQueryFactory
                .selectFrom(advertiserEntity)
                .leftJoin(advertiserEntity.filesEntity).fetchJoin()
                .where(searchVo.where())
                .orderBy(advertiserEntity.advertiserSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(advertiserEntity.count())
                .from(advertiserEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(users, pageable, Objects.requireNonNullElse(count, 0L));
    }
}
