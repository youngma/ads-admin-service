package com.ads.main.repository.querydsl;


import com.ads.main.vo.admin.report.req.RptSearchVo;
import com.ads.main.vo.admin.report.resp.RptQuizAdminDailyVo;
import com.ads.main.vo.admin.report.resp.RptQuizAdvertiserDailyVo;
import com.ads.main.vo.admin.report.resp.RptQuizPartnerDailyVo;
import com.ads.main.vo.admin.report.resp.RptQuizXCodeVo;
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
import static com.ads.main.entity.QPartnerAdGroupEntity.partnerAdGroupEntity;
import static com.ads.main.entity.QRptQuizAdminDailyEntity.rptQuizAdminDailyEntity;
import static com.ads.main.entity.QRptQuizAdvertiserDailyEntity.rptQuizAdvertiserDailyEntity;
import static com.ads.main.entity.QRptQuizPartnerDailyEntity.rptQuizPartnerDailyEntity;
import static com.ads.main.entity.QRptQuizXCodeEntity.rptQuizXCodeEntity;

@Repository
@RequiredArgsConstructor
public class QRptQuizReportRepository {


    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    public Page<RptQuizAdvertiserDailyVo> searchRptQuizAdvertiserDaily(RptSearchVo searchVo, Pageable pageable) {

        List<RptQuizAdvertiserDailyVo> reports = jpaQueryFactory.select(
                        Projections.bean(RptQuizAdvertiserDailyVo.class,
                                rptQuizAdvertiserDailyEntity.rptDate,
                                adCampaignMasterEntity.advertiserEntity.advertiserSeq,
                                adCampaignMasterEntity.advertiserEntity.advertiserName,
                                adCampaignMasterEntity.advertiserEntity.businessName,
                                adCampaignMasterEntity.adPrice.as("originAdCost"),
                                rptQuizAdvertiserDailyEntity.campaignCode,
                                adCampaignMasterEntity.campaignName,
                                rptQuizAdvertiserDailyEntity.reqCnt,
                                rptQuizAdvertiserDailyEntity.impressionCnt,
                                rptQuizAdvertiserDailyEntity.answerCnt,
                                rptQuizAdvertiserDailyEntity.hintCnt,
                                rptQuizAdvertiserDailyEntity.clickCnt,
                                rptQuizAdvertiserDailyEntity.adCost
                        )
                )
                .from(rptQuizAdvertiserDailyEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizAdvertiserDailyEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .where(searchVo.where())
                .orderBy(rptQuizAdvertiserDailyEntity.rptDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(rptQuizAdvertiserDailyEntity.count())
                .from(rptQuizAdvertiserDailyEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizAdvertiserDailyEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(reports, pageable, Objects.requireNonNullElse(count, 0L));
    }

    public Page<RptQuizPartnerDailyVo> searchRptQuizPartnerDaily(RptSearchVo searchVo, Pageable pageable) {

        List<RptQuizPartnerDailyVo> reports = jpaQueryFactory.select(
                        Projections.bean(RptQuizPartnerDailyVo.class,
                                rptQuizPartnerDailyEntity.rptDate,
                                rptQuizPartnerDailyEntity.groupCode,
                                partnerAdGroupEntity.groupName,
                                partnerAdGroupEntity.partnerEntity.partnerSeq,
                                partnerAdGroupEntity.partnerEntity.businessName,
                                rptQuizPartnerDailyEntity.reqCnt,
                                rptQuizPartnerDailyEntity.impressionCnt,
                                rptQuizPartnerDailyEntity.answerCnt,
                                rptQuizPartnerDailyEntity.hintCnt,
                                rptQuizPartnerDailyEntity.clickCnt,
                                rptQuizPartnerDailyEntity.adCost,
                                rptQuizPartnerDailyEntity.partnerCommission,
                                rptQuizPartnerDailyEntity.userCommission
                        )
                )
                .from(rptQuizPartnerDailyEntity)
                .innerJoin(partnerAdGroupEntity).on(partnerAdGroupEntity.groupCode.eq(rptQuizPartnerDailyEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .orderBy(rptQuizPartnerDailyEntity.rptDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(rptQuizPartnerDailyEntity.count())
                .from(rptQuizPartnerDailyEntity)
                .innerJoin(partnerAdGroupEntity).on(partnerAdGroupEntity.groupCode.eq(rptQuizPartnerDailyEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(reports, pageable, Objects.requireNonNullElse(count, 0L));
    }

    public Page<RptQuizAdminDailyVo> searchRptQuizAdminDaily(RptSearchVo searchVo, Pageable pageable) {

        List<RptQuizAdminDailyVo> reports = jpaQueryFactory.select(
                        Projections.bean(RptQuizAdminDailyVo.class,
                                rptQuizAdminDailyEntity.rptDate,
                                rptQuizAdminDailyEntity.advertiserCnt,
                                rptQuizAdminDailyEntity.campaignCnt,
                                rptQuizAdminDailyEntity.partnerCnt,
                                rptQuizAdminDailyEntity.adGroupCnt,
                                rptQuizAdminDailyEntity.reqCnt,
                                rptQuizAdminDailyEntity.impressionCnt,
                                rptQuizAdminDailyEntity.answerCnt,
                                rptQuizAdminDailyEntity.hintCnt,
                                rptQuizAdminDailyEntity.clickCnt,
                                rptQuizAdminDailyEntity.adCost,
                                rptQuizAdminDailyEntity.partnerCommission,
                                rptQuizAdminDailyEntity.userCommission
                        )
                )
                .from(rptQuizAdminDailyEntity)
                .where(searchVo.where())
                .orderBy(rptQuizAdminDailyEntity.rptDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(rptQuizAdminDailyEntity.count())
                .from(rptQuizAdminDailyEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(reports, pageable, Objects.requireNonNullElse(count, 0L));
    }

    public Page<RptQuizXCodeVo> searchRptQuizXCodeDaily(RptSearchVo searchVo, Pageable pageable) {

        List<RptQuizXCodeVo> reports = jpaQueryFactory.select(
                        Projections.bean(RptQuizXCodeVo.class,
                                rptQuizXCodeEntity.rptDate,
                                adCampaignMasterEntity.advertiserEntity.businessName.as("advertiserName"),
                                adCampaignMasterEntity.campaignName.as("campaignName"),
                                adCampaignMasterEntity.adPrice.as("originAdCost"),
                                partnerAdGroupEntity.partnerEntity.businessName.as("partnerName"),
                                partnerAdGroupEntity.groupName.as("groupName"),
                                rptQuizXCodeEntity.campaignCode,
                                rptQuizXCodeEntity.groupCode,
                                rptQuizXCodeEntity.reqCnt,
                                rptQuizXCodeEntity.impressionCnt,
                                rptQuizXCodeEntity.detailCnt,
                                rptQuizXCodeEntity.answerCnt,
                                rptQuizXCodeEntity.hintCnt,
                                rptQuizXCodeEntity.clickCnt,
                                rptQuizXCodeEntity.adCost,
                                rptQuizXCodeEntity.partnerCommission,
                                rptQuizXCodeEntity.userCommission,
                                rptQuizXCodeEntity.adReword
                        )
                )
                .from(rptQuizXCodeEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizXCodeEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .innerJoin(partnerAdGroupEntity).on(rptQuizXCodeEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .orderBy(rptQuizXCodeEntity.rptDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(rptQuizXCodeEntity.count())
                .from(rptQuizXCodeEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizXCodeEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .innerJoin(partnerAdGroupEntity).on(rptQuizXCodeEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .fetchOne();

        return new PageImpl<>(reports, pageable, Objects.requireNonNullElse(count, 0L));
    }


    public List<RptQuizAdvertiserDailyVo> searchRptQuizAdvertiserDailyExcel(RptSearchVo searchVo) {

        return jpaQueryFactory.select(
                        Projections.bean(RptQuizAdvertiserDailyVo.class,
                                rptQuizAdvertiserDailyEntity.rptDate,
                                adCampaignMasterEntity.advertiserEntity.advertiserSeq,
                                adCampaignMasterEntity.advertiserEntity.advertiserName,
                                adCampaignMasterEntity.advertiserEntity.businessName,
                                adCampaignMasterEntity.adPrice.as("originAdCost"),
                                rptQuizAdvertiserDailyEntity.campaignCode,
                                adCampaignMasterEntity.campaignName,
                                rptQuizAdvertiserDailyEntity.reqCnt,
                                rptQuizAdvertiserDailyEntity.impressionCnt,
                                rptQuizAdvertiserDailyEntity.answerCnt,
                                rptQuizAdvertiserDailyEntity.hintCnt,
                                rptQuizAdvertiserDailyEntity.clickCnt,
                                rptQuizAdvertiserDailyEntity.adCost
                        )
                )
                .from(rptQuizAdvertiserDailyEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizAdvertiserDailyEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .where(searchVo.where())
                .orderBy(rptQuizAdvertiserDailyEntity.rptDate.desc())
                .fetch();
    }

    public List<RptQuizPartnerDailyVo> searchRptQuizPartnerDailyExcel(RptSearchVo searchVo) {
       return jpaQueryFactory.select(
                        Projections.bean(RptQuizPartnerDailyVo.class,
                                rptQuizPartnerDailyEntity.rptDate,
                                rptQuizPartnerDailyEntity.groupCode,
                                partnerAdGroupEntity.groupName,
                                partnerAdGroupEntity.partnerEntity.partnerSeq,
                                partnerAdGroupEntity.partnerEntity.businessName,
                                rptQuizPartnerDailyEntity.reqCnt,
                                rptQuizPartnerDailyEntity.impressionCnt,
                                rptQuizPartnerDailyEntity.answerCnt,
                                rptQuizPartnerDailyEntity.hintCnt,
                                rptQuizPartnerDailyEntity.clickCnt,
                                rptQuizPartnerDailyEntity.adCost,
                                rptQuizPartnerDailyEntity.partnerCommission,
                                rptQuizPartnerDailyEntity.userCommission
                        )
                )
                .from(rptQuizPartnerDailyEntity)
                .innerJoin(partnerAdGroupEntity).on(partnerAdGroupEntity.groupCode.eq(rptQuizPartnerDailyEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .orderBy(rptQuizPartnerDailyEntity.rptDate.desc())
                .fetch();
    }

    public List<RptQuizAdminDailyVo> searchRptQuizAdminDailyExcel(RptSearchVo searchVo) {
        return jpaQueryFactory.select(
                        Projections.bean(RptQuizAdminDailyVo.class,
                                rptQuizAdminDailyEntity.rptDate,
                                rptQuizAdminDailyEntity.advertiserCnt,
                                rptQuizAdminDailyEntity.campaignCnt,
                                rptQuizAdminDailyEntity.partnerCnt,
                                rptQuizAdminDailyEntity.adGroupCnt,
                                rptQuizAdminDailyEntity.reqCnt,
                                rptQuizAdminDailyEntity.impressionCnt,
                                rptQuizAdminDailyEntity.answerCnt,
                                rptQuizAdminDailyEntity.hintCnt,
                                rptQuizAdminDailyEntity.clickCnt,
                                rptQuizAdminDailyEntity.adCost,
                                rptQuizAdminDailyEntity.partnerCommission,
                                rptQuizAdminDailyEntity.userCommission
                        )
                )
                .from(rptQuizAdminDailyEntity)
                .where(searchVo.where())
                .orderBy(rptQuizAdminDailyEntity.rptDate.desc())
                .fetch();
    }

    public List<RptQuizXCodeVo> searchRptQuizXCodeDailyExcel(RptSearchVo searchVo) {

        return jpaQueryFactory.select(
                        Projections.bean(RptQuizXCodeVo.class,
                                rptQuizXCodeEntity.rptDate,
                                adCampaignMasterEntity.advertiserEntity.businessName.as("advertiserName"),
                                adCampaignMasterEntity.campaignName.as("campaignName"),
                                adCampaignMasterEntity.adPrice.as("originAdCost"),
                                partnerAdGroupEntity.partnerEntity.businessName.as("partnerName"),
                                partnerAdGroupEntity.groupName.as("groupName"),
                                rptQuizXCodeEntity.groupCode,
                                rptQuizXCodeEntity.reqCnt,
                                rptQuizXCodeEntity.impressionCnt,
                                rptQuizXCodeEntity.detailCnt,
                                rptQuizXCodeEntity.answerCnt,
                                rptQuizXCodeEntity.hintCnt,
                                rptQuizXCodeEntity.clickCnt,
                                rptQuizXCodeEntity.adCost,
                                rptQuizXCodeEntity.partnerCommission,
                                rptQuizXCodeEntity.userCommission,
                                rptQuizXCodeEntity.adReword
                        )
                )
                .from(rptQuizXCodeEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizXCodeEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .innerJoin(partnerAdGroupEntity).on(rptQuizXCodeEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .orderBy(rptQuizXCodeEntity.rptDate.desc())
                .fetch();
    }


    public RptQuizAdvertiserDailyVo searchRptQuizAdvertiserSummary(RptSearchVo searchVo) {
      return jpaQueryFactory.select(
                      Projections.bean(RptQuizAdvertiserDailyVo.class,
                              adCampaignMasterEntity.advertiserEntity.advertiserSeq.countDistinct().as("advertiserCount"),
                              adCampaignMasterEntity.campaignCode.countDistinct().as("campaignCount"),
                              rptQuizAdvertiserDailyEntity.reqCnt.sum().as("reqCnt"),
                              rptQuizAdvertiserDailyEntity.impressionCnt.sum().as("impressionCnt"),
                              rptQuizAdvertiserDailyEntity.answerCnt.sum().as("answerCnt"),
                              rptQuizAdvertiserDailyEntity.hintCnt.sum().as("hintCnt"),
                              rptQuizAdvertiserDailyEntity.clickCnt.sum().as("clickCnt"),
                              rptQuizAdvertiserDailyEntity.adCost.sum().as("adCost")
                      )
              )
              .from(rptQuizAdvertiserDailyEntity)
              .innerJoin(adCampaignMasterEntity).on(rptQuizAdvertiserDailyEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
              .innerJoin(adCampaignMasterEntity.advertiserEntity)
              .where(searchVo.where())
              .fetchFirst();
    }

    public RptQuizPartnerDailyVo searchRptQuizPartnerDailySummary(RptSearchVo searchVo) {

        return jpaQueryFactory.select(
                        Projections.bean(RptQuizPartnerDailyVo.class,
                                rptQuizPartnerDailyEntity.groupCode.countDistinct().as("adGroupCount"),
                                partnerAdGroupEntity.partnerEntity.partnerSeq.countDistinct().as("partnerCount"),
                                rptQuizPartnerDailyEntity.reqCnt.sum().as("reqCnt"),
                                rptQuizPartnerDailyEntity.impressionCnt.sum().as("impressionCnt"),
                                rptQuizPartnerDailyEntity.answerCnt.sum().as("answerCnt"),
                                rptQuizPartnerDailyEntity.hintCnt.sum().as("hintCnt"),
                                rptQuizPartnerDailyEntity.adCost.sum().as("adCost"),
                                rptQuizPartnerDailyEntity.partnerCommission.sum().as("partnerCommission"),
                                rptQuizPartnerDailyEntity.userCommission.sum().as("userCommission")
                        )
                )
                .from(rptQuizPartnerDailyEntity)
                .innerJoin(partnerAdGroupEntity).on(partnerAdGroupEntity.groupCode.eq(rptQuizPartnerDailyEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .fetchFirst();
    }

    public RptQuizAdminDailyVo searchRptQuizAdminDailySummary(RptSearchVo searchVo) {

        return jpaQueryFactory.select(
                        Projections.bean(RptQuizAdminDailyVo.class,
                                rptQuizAdminDailyEntity.reqCnt.sum().as("reqCnt"),
                                rptQuizAdminDailyEntity.impressionCnt.sum().as("impressionCnt"),
                                rptQuizAdminDailyEntity.answerCnt.sum().as("answerCnt"),
                                rptQuizAdminDailyEntity.hintCnt.sum().as("hintCnt"),
                                rptQuizAdminDailyEntity.clickCnt.sum().as("clickCnt"),
                                rptQuizAdminDailyEntity.partnerCommission.sum().as("partnerCommission"),
                                rptQuizAdminDailyEntity.userCommission.sum().as("userCommission"),
                                rptQuizAdminDailyEntity.adCost.sum().as("adCost"),
                                rptQuizAdminDailyEntity.partnerCommission.sum().as("partnerCommission"),
                                rptQuizAdminDailyEntity.userCommission.sum().as("userCommission")
                        )
                )
                .from(rptQuizAdminDailyEntity)
                .where(searchVo.where())
                .fetchFirst();

    }

    public RptQuizXCodeVo searchRptQuizXCodeDailySummary(RptSearchVo searchVo) {

        return jpaQueryFactory.select(
                Projections.bean(RptQuizXCodeVo.class,
                                rptQuizXCodeEntity.reqCnt.sum().as("reqCnt"),
                                rptQuizXCodeEntity.impressionCnt.sum().as("impressionCnt"),
                                rptQuizXCodeEntity.detailCnt.sum().as("detailCnt"),
                                rptQuizXCodeEntity.answerCnt.sum().as("answerCnt"),
                                rptQuizXCodeEntity.hintCnt.sum().as("hintCnt"),
                                rptQuizXCodeEntity.clickCnt.sum().as("clickCnt"),
                                rptQuizXCodeEntity.adCost.sum().as("adCost"),
                                rptQuizXCodeEntity.partnerCommission.sum().as("partnerCommission"),
                                rptQuizXCodeEntity.userCommission.sum().as("userCommission"),
                                rptQuizXCodeEntity.adReword.sum().as("adReword")
                        )
                )
                .from(rptQuizXCodeEntity)
                .innerJoin(adCampaignMasterEntity).on(rptQuizXCodeEntity.campaignCode.eq(adCampaignMasterEntity.campaignCode))
                .innerJoin(adCampaignMasterEntity.advertiserEntity)
                .innerJoin(partnerAdGroupEntity).on(rptQuizXCodeEntity.groupCode.eq(partnerAdGroupEntity.groupCode))
                .innerJoin(partnerAdGroupEntity.partnerEntity)
                .where(searchVo.where())
                .fetchFirst();

    }

}
