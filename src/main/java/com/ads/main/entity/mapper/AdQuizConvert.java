package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.AdQuizEntity;
import com.ads.main.entity.AdSmartStoreEntity;
import com.ads.main.vo.admin.advertiser.campaign.req.AdQuizModifyVo;
import com.ads.main.vo.admin.advertiser.campaign.req.AdQuizRegisterVo;
import com.ads.main.vo.admin.advertiser.campaign.req.AdSmartStoreRegisterVo;
import com.ads.main.vo.admin.advertiser.campaign.resp.AdQuizVo;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
            FilesConverter.class
        }
)
public interface AdQuizConvert extends GenericMapper<AdQuizVo, AdQuizEntity>  {

    @Mapping(target = "adCampaignMasterVo", ignore = true)
    @Override
    AdQuizVo toDto(AdQuizEntity e);

    @Mapping(target = "adCampaignMasterEntity", ignore = true)
    @Override
    AdQuizEntity toEntity(AdQuizVo d);

    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "adCampaignMasterEntity", ignore = true)
    AdQuizEntity toEntity(AdQuizRegisterVo d);

    @Override
    List<AdQuizVo> toDtoList(List<AdQuizEntity> e);

    @Override
    List<AdQuizEntity> toEntities(List<AdQuizVo> d);

    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "adCampaignMasterEntity", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdSmartStoreRegisterVo adSmartStoreRegisterVo, @MappingTarget AdSmartStoreEntity partnerAdGroupEntity);

    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "adCampaignMasterEntity", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdQuizModifyVo quiz, @MappingTarget AdQuizEntity adQuizEntity);
}
