package com.ads.main.repository.querydsl;


import com.ads.main.entity.QAdvertiserEntity;
import com.ads.main.vo.report.req.RptSearchVo;
import com.ads.main.vo.report.resp.RptQuizAdminDailyVo;
import com.ads.main.vo.report.resp.RptQuizAdvertiserDailyVo;
import com.ads.main.vo.report.resp.RptQuizPartnerDailyVo;
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
import static com.ads.main.entity.QRptQuizAdvertiserDailyEntity.rptQuizAdvertiserDailyEntity;
import static com.ads.main.entity.QRptQuizPartnerDailyEntity.rptQuizPartnerDailyEntity;
import static com.ads.main.entity.QRptQuizAdminDailyEntity.rptQuizAdminDailyEntity;

@Repository
@RequiredArgsConstructor
public class QRptQuizRepository {


    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    public Page<RptQuizAdvertiserDailyVo> searchRptQuizAdvertiserDaily(RptSearchVo searchVo, Pageable pageable) {

        List<RptQuizAdvertiserDailyVo> reports = jpaQueryFactory.select(
                        Projections.bean(RptQuizAdvertiserDailyVo.class,
                                rptQuizAdvertiserDailyEntity.rptDate,
                                adCampaignMasterEntity.advertiserEntity.advertiserSeq,
                                adCampaignMasterEntity.advertiserEntity.advertiserName,
                                adCampaignMasterEntity.advertiserEntity.businessName,
                                rptQuizAdvertiserDailyEntity.campaignCode,
                                adCampaignMasterEntity.campaignName,
                                rptQuizAdvertiserDailyEntity.reqCnt,
                                rptQuizAdvertiserDailyEntity.impressionCnt,
                                rptQuizAdvertiserDailyEntity.answerCnt,
                                rptQuizAdvertiserDailyEntity.hintCnt,
                                rptQuizAdvertiserDailyEntity.clickCnt
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
                                rptQuizPartnerDailyEntity.clickCnt
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
}
