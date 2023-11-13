package com.ads.main.repository.querydsl;


import com.ads.main.vo.inquiry.req.AdInquirySearchVo;
import com.ads.main.vo.inquiry.resp.AdInquiryVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ads.main.entity.QAdCampaignMasterEntity.adCampaignMasterEntity;
import static com.ads.main.entity.QAdInquiryEntity.adInquiryEntity;
import static com.ads.main.entity.QPartnerAdGroupEntity.partnerAdGroupEntity;

@Repository
@RequiredArgsConstructor
public class QAdInquiryRepository {


    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    public Page<AdInquiryVo> searchAdInquiryList(AdInquirySearchVo searchVo, Pageable pageable) {

        List<AdInquiryVo> reports = jpaQueryFactory.select(
                        Projections.bean(AdInquiryVo.class,
                                adCampaignMasterEntity.advertiserEntity.businessName.nullif("-").as("advertiserName"),
                                adCampaignMasterEntity.campaignName.nullif("-").as("campaignName"),
                                adCampaignMasterEntity.campaignCode.nullif("-").as("campaignCode"),
                                partnerAdGroupEntity.partnerEntity.businessName.nullif("-").as("businessName"),
                                partnerAdGroupEntity.groupName.nullif("-").as("groupName"),
                                adInquiryEntity.seq.as("seq"),
                                adInquiryEntity.inquiryType.as("inquiryType"),
                                adInquiryEntity.quizTitle.as("quizTitle"),
                                adInquiryEntity.answer.as("answer"),
                                adInquiryEntity.title.as("title"),
                                adInquiryEntity.status.as("status"),
                                adInquiryEntity.requestAt.as("requestAt"),
                                adInquiryEntity.answerAt.as("answerAt"),
                                adInquiryEntity.phone.as("phone"),
                                adInquiryEntity.insertedId.as("insertedId")
                        )
                )
                .from(adInquiryEntity)
                .leftJoin(adCampaignMasterEntity).on(adInquiryEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .leftJoin(adCampaignMasterEntity.advertiserEntity)
                .leftJoin(partnerAdGroupEntity).on(adInquiryEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .leftJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .orderBy(adInquiryEntity.requestAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(adInquiryEntity.count())
                .from(adInquiryEntity)
                .leftJoin(adCampaignMasterEntity).on(adInquiryEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .leftJoin(adCampaignMasterEntity.advertiserEntity)
                .leftJoin(partnerAdGroupEntity).on(adInquiryEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .leftJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(reports, pageable, Objects.requireNonNullElse(count, 0L));
    }

}
