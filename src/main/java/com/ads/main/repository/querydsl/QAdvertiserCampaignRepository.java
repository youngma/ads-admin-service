package com.ads.main.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ads.main.entity.AdCampaignEntity;
import com.ads.main.entity.AdvertiserUserEntity;
import com.ads.main.entity.QAdCampaignEntity;
import com.ads.main.vo.advertiser.campaign.req.AdCampaignSearchVo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ads.main.entity.QAdCampaignEntity.adCampaignEntity;
import static com.ads.main.entity.QAdvertiserEntity.advertiserEntity;
import static com.ads.main.entity.QAdvertiserUserEntity.advertiserUserEntity;
import static com.ads.main.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class QAdvertiserCampaignRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public Page<AdCampaignEntity> findAdCampaigns(AdCampaignSearchVo searchVo, Pageable pageable) {

        List<AdCampaignEntity> adCampaignEntities =  jpaQueryFactory.select(
                    adCampaignEntity
                )
                .from(adCampaignEntity)
                .where(searchVo.where())

                .orderBy(adCampaignEntity.seq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetch();

        Long count = jpaQueryFactory.select(adCampaignEntity.count())
                .from(adCampaignEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(adCampaignEntities, pageable, Objects.requireNonNullElse(count, 0L));
    }

//    .where(eqCity(city), eqGu(gu), containName(name));

//    private BooleanExpression eqCity(String city) {
//        if(city == null || city.isEmpty()) {
//            return null;
//        }
//        return cafe.city.eq(city);
//    }
//
//    private BooleanExpression containName(String name) {
//        if(name == null || name.isEmpty()) {
//            return null;
//        }
//        return cafe.name.containsIgnoreCase(name);
//    }
//
//    private BooleanExpression eqGu(String gu) {
//        if(gu == null || gu.isEmpty()) {
//            return null;
//        }
//        return cafe.gu.eq(gu);
//    }
}
