package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.entity.AdCampaignMasterEntity;
import com.ads.main.entity.AdSmartStoreEntity;
import com.ads.main.entity.PartnerAdGroupEntity;
import com.ads.main.entity.mapper.AdSmartStoreConvert;
import com.ads.main.entity.mapper.AdvertiserConvert;
import com.ads.main.entity.mapper.FilesConverter;
import com.ads.main.vo.advertiser.campaign.req.AdCampaignMasterModifyVo;
import com.ads.main.vo.advertiser.campaign.req.AdCampaignMasterRegisterVo;
import com.ads.main.vo.advertiser.campaign.resp.AdCampaignMasterVo;
import com.ads.main.vo.advertiser.campaign.resp.AdSmartStoreVo;
import com.ads.main.vo.partner.adGroup.PartnerAdGroupModifyVo;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
            AdSmartStoreConvert.class, AdQuizConvert.class,
            FilesConverter.class,
            AdvertiserConvert.class
        }
)
public interface AdCampaignMasterConvert extends GenericMapper<AdCampaignMasterVo, AdCampaignMasterEntity>  {

    @Mapping(target = "exposureStatusName", ignore = true)
    @Mapping(target = "campaignTypeName", ignore = true)
    @Mapping(target = "campaignStatusName", ignore = true)
    @Mapping(target = "advertiser", source = "advertiserEntity")
    @Mapping(target = "smartStore", source = "adSmartStoreEntity")
    @Mapping(target = "quiz", source = "adQuizEntity")
    @Mapping(target = "campaignType", source = "campaignType", qualifiedByName = "campaignTypeToValue")
    @Mapping(target = "campaignStatus", source = "campaignStatus", qualifiedByName = "campaignStatusToValue")
    @Override
    AdCampaignMasterVo toDto(AdCampaignMasterEntity e);

    @Mapping(target = "adQuizEntity", ignore = true)
    @Mapping(target = "advertiserEntity", ignore = true)
    @Mapping(target = "adSmartStoreEntity", ignore = true)
    @Mapping(target = "campaignType", source = "campaignType", qualifiedByName = "campaignTypeToEnum")
    @Mapping(target = "campaignStatus", source = "campaignStatus", qualifiedByName = "campaignStatusToEnum")
    @Override
    AdCampaignMasterEntity toEntity(AdCampaignMasterVo d);


    @Mapping(target = "exposureStatus", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "requestAt", ignore = true)
    @Mapping(target = "rejectMessage", ignore = true)
    @Mapping(target = "rejectAt", ignore = true)
    @Mapping(target = "holdMessage", ignore = true)
    @Mapping(target = "holdAt", ignore = true)
    @Mapping(target = "campaignStatus", ignore = true)
    @Mapping(target = "campaignCode", ignore = true)
    @Mapping(target = "approvalAt", ignore = true)
    @Mapping(target = "advertiserEntity", ignore = true)
    @Mapping(target = "adSmartStoreEntity", source = "smartStore")
    @Mapping(target = "adQuizEntity", source = "quiz")
    @Mapping(target = "campaignType", source = "campaignType", qualifiedByName = "campaignTypeToEnum")
    AdCampaignMasterEntity toEntity(AdCampaignMasterRegisterVo d);

    @Override
    List<AdCampaignMasterVo> toDtoList(List<AdCampaignMasterEntity> e);

    @Override
    List<AdCampaignMasterEntity> toEntities(List<AdCampaignMasterVo> d);

    @Mapping(target = "exposureStatus", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "requestAt", ignore = true)
    @Mapping(target = "rejectMessage", ignore = true)
    @Mapping(target = "rejectAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "holdMessage", ignore = true)
    @Mapping(target = "holdAt", ignore = true)
    @Mapping(target = "campaignStatus", ignore = true)
    @Mapping(target = "campaignCode", ignore = true)
    @Mapping(target = "approvalAt", ignore = true)
    @Mapping(target = "advertiserEntity", ignore = true)
    @Mapping(target = "adSmartStoreEntity", ignore = true)
    @Mapping(target = "adQuizEntity", ignore = true)
    @Mapping(target = "campaignType", source = "campaignType", qualifiedByName = "campaignTypeToEnum")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdCampaignMasterModifyVo adCampaignMasterModifyVo, @MappingTarget AdCampaignMasterEntity adCampaignMasterEntity);



    @Named("campaignTypeToEnum")
    default CampaignType campaignTypeToEnum(String source) {
        return CampaignType.of(source);
    }

    @Named("campaignTypeToValue")
    default String campaignTypeToValue(CampaignType campaignType) {
        return campaignType.getCode();
    }

    @Named("campaignStatusToEnum")
    default CampaignStatus campaignStatusToEnum(String source) {
        return CampaignStatus.of(source);
    }

    @Named("campaignStatusToValue")
    default String campaignStatusToValue(CampaignStatus campaignStatus) {
        return campaignStatus.getCode();
    }
}
