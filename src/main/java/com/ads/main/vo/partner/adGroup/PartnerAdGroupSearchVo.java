package com.ads.main.vo.partner.adGroup;

import com.querydsl.core.BooleanBuilder;
import com.ads.main.core.enums.advertiser.AdGroupStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

import static com.ads.main.entity.QPartnerAdGroupEntity.partnerAdGroupEntity;


@Data
@Builder
public class PartnerAdGroupSearchVo implements Serializable {

    /**
     * 광고 그룹 순번
     */
    private Long groupSeq;


    /**
     * 매체사 순번
     */
    private Set<Long> partnerSeq;


    /**
     * 서비스 타입
     */
    private String adType;


    /**
     * 서비스 코드
     */
    private String groupCode;

    /**
     * 광고 그룹 명
     */
    private String groupName;

    /**
     * 서비스 상태
     */
    private String groupStatus;


    public BooleanBuilder where() {


        BooleanBuilder builder = new BooleanBuilder();
        builder.and(partnerAdGroupEntity.partnerEntity.partnerSeq.in(this.partnerSeq));

        if (this.groupSeq != null) {
            builder.and(partnerAdGroupEntity.groupSeq.eq(this.groupSeq));
        }

        if (this.adType != null && !this.adType.isBlank()) {
            builder.and(partnerAdGroupEntity.adType.eq(CampaignType.of(this.adType)));
        }


        if (this.groupName != null && !this.groupName.isBlank()) {
            builder.and(partnerAdGroupEntity.groupName.eq(this.groupName));
        }

        if (this.groupCode != null && !this.groupCode.isBlank()) {
            builder.and(partnerAdGroupEntity.groupCode.eq(this.groupCode));
        }

        if (this.groupStatus != null && !this.groupStatus.isBlank()) {
            builder.and(partnerAdGroupEntity.groupStatus.eq(AdGroupStatus.of(this.groupStatus)));
        }

        return builder;
    }

}
