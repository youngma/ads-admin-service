package com.ads.main.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ads.main.entity.PartnerEntity;
import com.ads.main.vo.admin.partner.PartnerSearchVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ads.main.entity.QPartnerEntity.partnerEntity;


@Repository
@RequiredArgsConstructor
public class QPartnerRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;


    public Page<PartnerEntity> searchPartners(PartnerSearchVo searchVo, Pageable pageable) {

        List<PartnerEntity> users = jpaQueryFactory
                .selectFrom(partnerEntity)
                .innerJoin(partnerEntity.filesEntity).fetchJoin()
                .where(searchVo.where())
                .orderBy(partnerEntity.partnerSeq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(partnerEntity.count())
                .from(partnerEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(users, pageable, Objects.requireNonNullElse(count, 0L));
    }
}
