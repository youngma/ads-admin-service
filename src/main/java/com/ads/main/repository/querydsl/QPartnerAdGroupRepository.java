package com.ads.main.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ads.main.entity.PartnerAdGroupEntity;
import com.ads.main.vo.partner.adGroup.PartnerAdGroupSearchVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ads.main.entity.QPartnerAdGroupEntity.partnerAdGroupEntity;

@Repository
@RequiredArgsConstructor
public class QPartnerAdGroupRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public Page<PartnerAdGroupEntity> searchAdGroups(PartnerAdGroupSearchVo searchVo, Pageable pageable) {

        List<PartnerAdGroupEntity> accounts = jpaQueryFactory
                .selectFrom(partnerAdGroupEntity)
                .join(partnerAdGroupEntity.logoFileEntity).fetchJoin()
                .join(partnerAdGroupEntity.pointIconFileEntity).fetchJoin()
                .join(partnerAdGroupEntity.partnerEntity).fetchJoin()
                .join(partnerAdGroupEntity.partnerEntity.filesEntity).fetchJoin()
                .where(searchVo.where())
                .orderBy(partnerAdGroupEntity.groupSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(partnerAdGroupEntity.count())
                .from(partnerAdGroupEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(accounts, pageable, Objects.requireNonNullElse(count, 0L));
    }
}
