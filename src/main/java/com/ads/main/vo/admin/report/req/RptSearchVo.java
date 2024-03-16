package com.ads.main.vo.admin.report.req;

import com.ads.main.core.security.config.dto.Role;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

import static com.ads.main.entity.QAdCampaignMasterEntity.adCampaignMasterEntity;
import static com.ads.main.entity.QPartnerAdGroupEntity.partnerAdGroupEntity;
import static com.ads.main.entity.QRptQuizAdminDailyEntity.rptQuizAdminDailyEntity;
import static com.ads.main.entity.QRptQuizAdvertiserDailyEntity.rptQuizAdvertiserDailyEntity;
import static com.ads.main.entity.QRptQuizPartnerDailyEntity.rptQuizPartnerDailyEntity;
import static com.ads.main.entity.QRptQuizRawEntity.rptQuizRawEntity;
import static com.ads.main.entity.QRptQuizXCodeEntity.rptQuizXCodeEntity;


@Data
@Builder
public class RptSearchVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Role role;

    private String searchStartDt;
    private String searchEndDt;


    private HashSet<Long> advertiserSeq;
    private String campaignCode;
    private String campaignName;

    private HashSet<Long> partnerSeq;
    private String groupName;
    private String groupCode;

    private String userKey;

    public BooleanBuilder where() {
        BooleanBuilder builder = new BooleanBuilder();

        switch (this.getRole()) {
            case ADMIN -> {
                builder
                    .and(rptQuizAdminDailyEntity.rptDate.between(searchStartDt, searchEndDt))
                ;
            }
            case PARTNER -> {
                builder
                        .and(rptQuizPartnerDailyEntity.rptDate.between(searchStartDt, searchEndDt));

                if(partnerSeq != null && !partnerSeq.isEmpty()) {
                    builder
                            .and(partnerAdGroupEntity.partnerEntity.partnerSeq.in(partnerSeq));
                }

                if (groupName != null&& !groupName.isBlank()) {
                    builder.and(partnerAdGroupEntity.groupName.eq(groupName));
                }

                if (groupCode != null&& !groupCode.isBlank()) {
                    builder.and(partnerAdGroupEntity.groupCode.contains(groupCode));
                }

                ;
            }
            case ADVERTISER -> {
                builder
                        .and(rptQuizAdvertiserDailyEntity.rptDate.between(searchStartDt, searchEndDt));

                if(advertiserSeq != null && !advertiserSeq.isEmpty()) {
                    builder
                            .and(adCampaignMasterEntity.advertiserEntity.advertiserSeq.in(advertiserSeq));
                }

                if (campaignCode != null&& !campaignCode.isBlank()) {
                    builder.and(adCampaignMasterEntity.campaignCode.eq(campaignCode));
                }

                if (campaignName != null&& !campaignName.isBlank()) {
                    builder.and(adCampaignMasterEntity.campaignName.contains(campaignName));
                }
                ;
            }
            case USER -> {

                StringTemplate requestAtFormatter = Expressions.stringTemplate(
                        "DATE_FORMAT({0}, {1})"
                        ,rptQuizRawEntity.requestAt
                        ,("%Y%m%d")
                );

                builder.and(requestAtFormatter.between(searchStartDt, searchEndDt));

                if (userKey != null && !userKey.isBlank()) {
                    builder.and(rptQuizRawEntity.userKey.eq(userKey));
                }

                if (campaignCode != null&& !campaignCode.isBlank()) {
                    builder.and(adCampaignMasterEntity.campaignCode.eq(campaignCode));
                }

                if (campaignName != null&& !campaignName.isBlank()) {
                    builder.and(adCampaignMasterEntity.campaignName.contains(campaignName));
                }
            }

            case X_CODE -> {

                builder.and(rptQuizXCodeEntity.rptDate.between(searchStartDt, searchEndDt));

                if (campaignCode != null&& !campaignCode.isBlank()) {
                    builder.and(adCampaignMasterEntity.campaignCode.eq(campaignCode));
                }

                if (campaignName != null&& !campaignName.isBlank()) {
                    builder.and(adCampaignMasterEntity.campaignName.contains(campaignName));
                }

                if (groupCode != null&& !groupCode.isBlank()) {
                    builder.and(partnerAdGroupEntity.groupCode.eq(groupCode));
                }

                if (groupName != null&& !groupName.isBlank()) {
                    builder.and(partnerAdGroupEntity.groupName.contains(groupName));
                }

            }
        };

        return builder;
    }


}
