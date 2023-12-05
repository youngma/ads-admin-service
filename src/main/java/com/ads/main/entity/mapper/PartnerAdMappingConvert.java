package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.core.enums.advertiser.AdGroupStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.entity.PartnerAdGroupEntity;
import com.ads.main.entity.PartnerAdMappingEntity;
import com.ads.main.vo.partner.ad.PartnerAdMappingVo;
import com.ads.main.vo.partner.adGroup.PartnerAdGroupModifyVo;
import com.ads.main.vo.partner.adGroup.PartnerAdGroupRegisterVo;
import com.ads.main.vo.partner.adGroup.PartnerAdGroupStatusVo;
import com.ads.main.vo.partner.adGroup.PartnerAdGroupVo;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring"
)
public interface PartnerAdMappingConvert extends GenericMapper<PartnerAdMappingVo, PartnerAdMappingEntity> {

    @Override
    PartnerAdMappingVo toDto(PartnerAdMappingEntity e);

    @Override
    PartnerAdMappingEntity toEntity(PartnerAdMappingVo d);

    @Override
    List<PartnerAdMappingVo> toDtoList(List<PartnerAdMappingEntity> e);

    @Override
    List<PartnerAdMappingEntity> toEntities(List<PartnerAdMappingVo> d);


}
