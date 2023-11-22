package com.ads.main.repository.querydsl;

import com.ads.main.vo.report.req.RptSearchVo;
import com.ads.main.vo.report.resp.RptQuizAdvertiserDailyVo;
import com.ads.main.vo.report.resp.RptQuizRawVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ads.main.entity.QAdCampaignMasterEntity.adCampaignMasterEntity;
import static com.ads.main.entity.QPartnerAdGroupEntity.partnerAdGroupEntity;
import static com.ads.main.entity.QRptQuizAdvertiserDailyEntity.rptQuizAdvertiserDailyEntity;
import static com.ads.main.entity.QRptQuizRawEntity.rptQuizRawEntity;

@Repository
@RequiredArgsConstructor
public class QRptQuizRawRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public Page<RptQuizRawVo> searchRptQuizUserDaily(RptSearchVo searchVo, Pageable pageable) {

        StringTemplate requestAtFormatter = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,rptQuizRawEntity.requestAt
                ,("%Y%m%d")
        );

        List<RptQuizRawVo> reports = jpaQueryFactory.select(
                        Projections.bean(RptQuizRawVo.class,

                                requestAtFormatter.as("rptDate"),
                                rptQuizRawEntity.requestId,
                                adCampaignMasterEntity.advertiserEntity.businessName.as("advertiserName"),
                                partnerAdGroupEntity.partnerEntity.businessName.as("partnerName"),
                                rptQuizRawEntity.campaignCode,
                                adCampaignMasterEntity.campaignName,
                                rptQuizRawEntity.groupCode,
                                partnerAdGroupEntity.groupName,
                                rptQuizRawEntity.userKey,
                                rptQuizRawEntity.reqCnt,
                                rptQuizRawEntity.impressionCnt,
                                rptQuizRawEntity.detailCnt,
                                rptQuizRawEntity.answerCnt,
                                rptQuizRawEntity.hintCnt,
                                rptQuizRawEntity.clickCnt,

                                rptQuizRawEntity.requestAt,
                                rptQuizRawEntity.impressionAt,
                                rptQuizRawEntity.hintAt,
                                rptQuizRawEntity.detailAt,
                                rptQuizRawEntity.answerAt,
                                rptQuizRawEntity.clickAt,

                                rptQuizRawEntity.partnerCommission,
                                rptQuizRawEntity.userCommission,
                                rptQuizRawEntity.adReword

                        )
                )
                .from(rptQuizRawEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizRawEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .innerJoin(partnerAdGroupEntity).on(rptQuizRawEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .orderBy(rptQuizRawEntity.requestAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(rptQuizRawEntity.count())
                .from(rptQuizRawEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizRawEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .innerJoin(partnerAdGroupEntity).on(rptQuizRawEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(reports, pageable, Objects.requireNonNullElse(count, 0L));
    }
}
