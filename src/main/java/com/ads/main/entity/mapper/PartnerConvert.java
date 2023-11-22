package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.PartnerEntity;
import com.ads.main.entity.mapper.FilesConverter;
import com.ads.main.vo.partner.PartnerBusinessModifyVo;
import com.ads.main.vo.partner.PartnerModifyVo;
import com.ads.main.vo.partner.PartnerVo;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring"
        ,uses = { FilesConverter.class }
)
public interface PartnerConvert extends GenericMapper<PartnerVo, PartnerEntity> {

    @Mapping(target = "file", source = "filesEntity")
    @Override
    PartnerVo toDto(PartnerEntity e);

    @Mapping(target = "partnerAdGroupEntities", ignore = true)
    @Mapping(target = "partnerAccountEntities", ignore = true)
    @Mapping(target = "partnerUserEntities", ignore = true)
    @Mapping(target = "filesEntity", source = "file")
    @Override
    PartnerEntity toEntity(PartnerVo d);

    @Override
    List<PartnerVo> toDtoList(List<PartnerEntity> e);

    @Override
    List<PartnerEntity> toEntities(List<PartnerVo> d);

    @Mapping(target = "partnerAdGroupEntities", ignore = true)
    @Mapping(target = "partnerAccountEntities", ignore = true)
    @Mapping(target = "partnerUserEntities", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "filesEntity", ignore = true)
    @Mapping(target = "businessNumber", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PartnerModifyVo partnerVo, @MappingTarget PartnerEntity advertiserEntity);

    @Mapping(target = "partnerAdGroupEntities", ignore = true)
    @Mapping(target = "partnerAccountEntities", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "taxBillEmail", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "partnerUserEntities", ignore = true)
    @Mapping(target = "filesEntity", source = "file")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PartnerBusinessModifyVo modifyVo, @MappingTarget PartnerEntity advertiserEntity);
}
