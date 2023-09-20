package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.AdCampaignEntity;
import com.ads.main.vo.advertiser.campaign.req.AdCampaignRegVo;
import com.ads.main.vo.advertiser.campaign.resp.AdCampaignVo;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring"
)
public interface AdCampaignConvert extends GenericMapper<AdCampaignVo, AdCampaignEntity> {

    @Mapping(target = "advertiserSeq", source = "advertiserEntity.advertiserSeq")
    @Mapping(target = "paymentTermsName", ignore = true)
    @Mapping(target = "campaignTypeName", ignore = true)
    @Mapping(target = "campaignStatusNm", ignore = true)
    @Override
    AdCampaignVo toDto(AdCampaignEntity e);

    @Mapping(target = "advertiserEntity", ignore = true)
    @Override
    AdCampaignEntity toEntity(AdCampaignVo d);
    @Mapping(target = "campaignCode", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "advertiserEntity", ignore = true)
    @Mapping(target = "campaignStatus", ignore = true)
    AdCampaignEntity toEntity(AdCampaignRegVo d);
    @Override
    List<AdCampaignVo> toDtoList(List<AdCampaignEntity> e);

    @Override
    List<AdCampaignEntity> toEntities(List<AdCampaignVo> d);

    @Mapping(target = "advertiserEntity", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdCampaignVo adCampaignVo, @MappingTarget AdCampaignEntity adCampaignEntity);

    @Mapping(target = "campaignCode", ignore = true)
    @Mapping(target = "advertiserEntity", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "campaignStatus", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdCampaignRegVo adCampaignRegVo, @MappingTarget AdCampaignEntity adCampaignEntity);


}
