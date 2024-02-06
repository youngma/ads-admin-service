package com.ads.main.repository.querydsl;

import com.ads.main.vo.admin.report.req.RptSearchVo;
import com.ads.main.vo.admin.report.resp.RptQuizRawVo;
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

                                rptQuizRawEntity.adCost,
                                rptQuizRawEntity.partnerCommission,
                                rptQuizRawEntity.userCommission,
                                rptQuizRawEntity.adReword,
                                rptQuizRawEntity.postBackStatus,
                                rptQuizRawEntity.postBackResult

                        )
                )
                .from(rptQuizRawEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizRawEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .innerJoin(partnerAdGroupEntity).on(rptQuizRawEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .orderBy(rptQuizRawEntity.requestAt.desc())
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


    public List<RptQuizRawVo> searchRptQuizUserDailyExcel(RptSearchVo searchVo) {

        StringTemplate requestAtFormatter = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,rptQuizRawEntity.requestAt
                ,("%Y%m%d")
        );

        return jpaQueryFactory.select(
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

                                rptQuizRawEntity.adCost,
                                rptQuizRawEntity.partnerCommission,
                                rptQuizRawEntity.userCommission,
                                rptQuizRawEntity.adReword,
                                rptQuizRawEntity.postBackStatus,
                                rptQuizRawEntity.postBackResult

                        )
                )
                .from(rptQuizRawEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizRawEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .innerJoin(partnerAdGroupEntity).on(rptQuizRawEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .orderBy(rptQuizRawEntity.requestAt.desc())
                .fetch();
    }

    public RptQuizRawVo searchRptQuizUserDailySummery(RptSearchVo searchVo) {

        StringTemplate requestAtFormatter = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,rptQuizRawEntity.requestAt
                ,("%Y%m%d")
        );
        return jpaQueryFactory.select(
                        Projections.bean(RptQuizRawVo.class,

                                adCampaignMasterEntity.advertiserEntity.advertiserSeq.countDistinct().as("advertiserCount"),
                                partnerAdGroupEntity.partnerEntity.partnerSeq.countDistinct().as("partnerCount"),
                                rptQuizRawEntity.campaignCode.countDistinct().as("campaignCount"),
                                rptQuizRawEntity.groupCode.countDistinct().as("adGroupCount"),
                                rptQuizRawEntity.userKey.countDistinct().as("userCount"),

                                rptQuizRawEntity.reqCnt.sum().as("reqCnt"),
                                rptQuizRawEntity.impressionCnt.sum().as("impressionCnt"),
                                rptQuizRawEntity.detailCnt.sum().as("detailCnt"),
                                rptQuizRawEntity.answerCnt.sum().as("answerCnt"),
                                rptQuizRawEntity.hintCnt.sum().as("hintCnt"),
                                rptQuizRawEntity.clickCnt.sum().as("clickCnt"),

                                rptQuizRawEntity.partnerCommission.sum().as("partnerCommission"),
                                rptQuizRawEntity.userCommission.sum().as("userCommission"),
                                rptQuizRawEntity.adReword.sum().as("adReword")

                        )
                )
                .from(rptQuizRawEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizRawEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .innerJoin(partnerAdGroupEntity).on(rptQuizRawEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .fetchFirst();

    }
}
