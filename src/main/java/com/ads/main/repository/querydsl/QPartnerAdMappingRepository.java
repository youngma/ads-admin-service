package com.ads.main.repository.querydsl;

import com.ads.main.vo.admin.partner.ad.PartnerAdMappingVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ads.main.entity.QPartnerAdMappingEntity.partnerAdMappingEntity;
@Repository
@RequiredArgsConstructor
public class QPartnerAdMappingRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;


    public List<PartnerAdMappingVo> searchMappingAds(Long groupSeq, Pageable pageable) {
        List<PartnerAdMappingVo> list = jpaQueryFactory.select(
                        Projections.bean(PartnerAdMappingVo.class,
                                partnerAdMappingEntity.seq,
                                partnerAdMappingEntity.campaignSeq,
                                partnerAdMappingEntity.partnerAdGroupEntity.groupSeq
                        )
                )
                .from(partnerAdMappingEntity)
                .where(partnerAdMappingEntity.partnerAdGroupEntity.groupSeq.eq(groupSeq))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(partnerAdMappingEntity.count())
                .from(partnerAdMappingEntity)
                .where(partnerAdMappingEntity.partnerAdGroupEntity.groupSeq.eq(groupSeq))
                .fetchOne();

        return list;
    }
}
