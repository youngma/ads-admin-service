package com.ads.main.vo.report.req;

import com.ads.main.core.security.config.dto.Role;
import com.ads.main.entity.QRptQuizRawEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.ads.main.entity.QAdCampaignMasterEntity.adCampaignMasterEntity;
import static com.ads.main.entity.QRptQuizAdminDailyEntity.rptQuizAdminDailyEntity;
import static com.ads.main.entity.QRptQuizAdvertiserDailyEntity.rptQuizAdvertiserDailyEntity;
import static com.ads.main.entity.QRptQuizPartnerDailyEntity.rptQuizPartnerDailyEntity;
import static com.ads.main.entity.QRptQuizRawEntity.rptQuizRawEntity;


@Data
@Builder
public class RptSearchVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Role role;

    private String searchStartDt;
    private String searchEndDt;


    private long advertiserSeq;
    private String campaignCode;
    private String campaignName;

    private long partnerSeq;
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
                        .and(rptQuizPartnerDailyEntity.rptDate.between(searchStartDt, searchEndDt))
                ;
            }
            case ADVERTISER -> {
                builder
                        .and(rptQuizAdvertiserDailyEntity.rptDate.between(searchStartDt, searchEndDt))
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
        };

        return builder;
    }


}
