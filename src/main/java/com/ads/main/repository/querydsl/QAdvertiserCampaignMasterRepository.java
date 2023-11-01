package com.ads.main.repository.querydsl;

import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.entity.AdCampaignMasterEntity;
import com.ads.main.vo.advertiser.campaign.req.AdCampaignSearchVo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.ads.main.entity.QAdCampaignMasterEntity.adCampaignMasterEntity;
import static com.ads.main.entity.QAdSmartStoreEntity.adSmartStoreEntity;

@Repository
@RequiredArgsConstructor
public class QAdvertiserCampaignMasterRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public Page<AdCampaignMasterEntity> findAdCampaigns(AdCampaignSearchVo searchVo, Pageable pageable) {

        List<AdCampaignMasterEntity> adCampaignEntities =  jpaQueryFactory.select(
                        adCampaignMasterEntity
                )
                .from(adCampaignMasterEntity)
                .leftJoin(adCampaignMasterEntity.adSmartStoreEntity).fetchJoin()
                .leftJoin(adCampaignMasterEntity.advertiserEntity).fetchJoin()

                .leftJoin(adCampaignMasterEntity.advertiserEntity.filesEntity).fetchJoin()
                .leftJoin(adCampaignMasterEntity.adQuizEntity).fetchJoin()

                .leftJoin(adCampaignMasterEntity.adQuizEntity.mainImage).fetchJoin()
                .leftJoin(adCampaignMasterEntity.adQuizEntity.detailImage1).fetchJoin()
                .leftJoin(adCampaignMasterEntity.adQuizEntity.detailImage2).fetchJoin()

                .where(searchVo.where())
                .orderBy(adCampaignMasterEntity.seq.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetch();

        Long count = jpaQueryFactory.select(adCampaignMasterEntity.count())
                .from(adCampaignMasterEntity)
                .leftJoin(adCampaignMasterEntity.adSmartStoreEntity, adSmartStoreEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(adCampaignEntities, pageable, Objects.requireNonNullElse(count, 0L));
    }

    public Optional<AdCampaignMasterEntity> findAdCampaignMaster(Long advertiserSeq, Long AdCampaignMasterSeq) {

        return jpaQueryFactory.select(
                        adCampaignMasterEntity
                )
                .from(adCampaignMasterEntity)
                .leftJoin(adCampaignMasterEntity.adSmartStoreEntity, adSmartStoreEntity).fetchJoin()
                .where(
                        adCampaignMasterEntity.advertiserEntity.advertiserSeq.eq(advertiserSeq)
                        .and(adCampaignMasterEntity.seq.eq(AdCampaignMasterSeq))
                )
                .orderBy(adCampaignMasterEntity.seq.desc())
                .stream().findFirst();
    }

    public Long findCountByCampaignType(CampaignType campaignType) {

        return jpaQueryFactory.select(adCampaignMasterEntity.count())
                .from(adCampaignMasterEntity)
                .where(
                        adCampaignMasterEntity.campaignStatus.eq(CampaignStatus.Approval)
                                .and(adCampaignMasterEntity.campaignType.eq(campaignType))
                                .and(adCampaignMasterEntity.adStartDate.before(LocalDateTime.now()))
                                .and(adCampaignMasterEntity.adEndDate.after(LocalDateTime.now()))
                )
                .fetchOne();
    }

    public List<AdCampaignMasterEntity> findAllByCampaignType(CampaignType campaignType, Pageable pageable) {

        return jpaQueryFactory.select(
                    adCampaignMasterEntity
                )
                .from(adCampaignMasterEntity)
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .leftJoin(adCampaignMasterEntity.adSmartStoreEntity).fetchJoin()
                .leftJoin(adCampaignMasterEntity.adSmartStoreEntity.image).fetchJoin()
                .leftJoin(adCampaignMasterEntity.adQuizEntity).fetchJoin()
                .leftJoin(adCampaignMasterEntity.adQuizEntity.mainImage).fetchJoin()
                .leftJoin(adCampaignMasterEntity.adQuizEntity.detailImage1).fetchJoin()
                .leftJoin(adCampaignMasterEntity.adQuizEntity.detailImage2).fetchJoin()

                .where(
                        adCampaignMasterEntity.campaignStatus.eq(CampaignStatus.Approval)
                        .and(adCampaignMasterEntity.exposureStatus.eq(true))
                        .and(adCampaignMasterEntity.campaignType.eq(campaignType))
                        .and(adCampaignMasterEntity.adStartDate.before(LocalDateTime.now()))
                        .and(adCampaignMasterEntity.adEndDate.after(LocalDateTime.now()))
                )
                .orderBy(adCampaignMasterEntity.seq.desc())

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                .fetch();
    }


}
