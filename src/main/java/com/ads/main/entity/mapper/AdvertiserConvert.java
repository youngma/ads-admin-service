package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.AdvertiserEntity;
import com.ads.main.vo.admin.advertiser.AdvertiserBusinessModifyVo;
import com.ads.main.vo.admin.advertiser.AdvertiserModifyVo;
import com.ads.main.vo.admin.advertiser.AdvertiserVo;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring"
        ,uses = { AdvertiserUserConvert.class, FilesConverter.class }
)
public interface AdvertiserConvert extends GenericMapper<AdvertiserVo, AdvertiserEntity> {

    @Mapping(target = "file", source = "filesEntity")
    @Override
    AdvertiserVo toDto(AdvertiserEntity e);

    @Override
    @Mapping(target = "adCampaignMasterEntities", ignore = true)
    @Mapping(target = "advertiserUserEntities", ignore = true)
    @Mapping(target = "advertiserAccountEntities", ignore = true)
    @Mapping(target = "filesEntity", source = "file")
    AdvertiserEntity toEntity(AdvertiserVo d);

    @Override
    List<AdvertiserVo> toDtoList(List<AdvertiserEntity> e);

    @Override
    List<AdvertiserEntity> toEntities(List<AdvertiserVo> d);

    @Mapping(target = "adCampaignMasterEntities", ignore = true)
    @Mapping(target = "filesEntity", ignore = true)
    @Mapping(target = "advertiserAccountEntities", ignore = true)
    @Mapping(target = "advertiserUserEntities", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdvertiserVo advertiserVo, @MappingTarget AdvertiserEntity advertiserEntity);

    @Mapping(target = "adCampaignMasterEntities", ignore = true)
    @Mapping(target = "filesEntity", ignore = true)
    @Mapping(target = "advertiserAccountEntities", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "businessNumber", ignore = true)
    @Mapping(target = "advertiserUserEntities", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdvertiserModifyVo dto, @MappingTarget AdvertiserEntity entity);


    @Mapping(target = "adCampaignMasterEntities", ignore = true)
    @Mapping(target = "taxBillEmail", ignore = true)
    @Mapping(target = "advertiserAccountEntities", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "advertiserUserEntities", ignore = true)
    @Mapping(target = "advertiserName", ignore = true)
    @Mapping(target = "filesEntity", source = "file")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdvertiserBusinessModifyVo dto, @MappingTarget AdvertiserEntity entity);

}
