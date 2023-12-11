package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.PartnerAdMappingEntity;
import com.ads.main.vo.admin.partner.ad.PartnerAdMappingVo;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring"
)
public interface PartnerAdMappingConvert extends GenericMapper<PartnerAdMappingVo, PartnerAdMappingEntity> {

    @Mapping(target = "groupSeq", ignore = true)
    @Override
    PartnerAdMappingVo toDto(PartnerAdMappingEntity e);

    @Mapping(target = "partnerAdGroupEntity", ignore = true)
    @Override
    PartnerAdMappingEntity toEntity(PartnerAdMappingVo d);

    @Override
    List<PartnerAdMappingVo> toDtoList(List<PartnerAdMappingEntity> e);

    @Override
    List<PartnerAdMappingEntity> toEntities(List<PartnerAdMappingVo> d);


}
