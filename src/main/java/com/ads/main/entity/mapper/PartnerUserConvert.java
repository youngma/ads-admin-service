package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.PartnerUserEntity;
import com.ads.main.entity.mapper.PartnerConvert;
import com.ads.main.entity.mapper.UserConverter;
import com.ads.main.vo.partner.user.PartnerUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring"
        ,uses = { UserConverter.class, PartnerConvert.class }

)
public interface PartnerUserConvert extends GenericMapper<PartnerUserVo, PartnerUserEntity> {
    @Mapping(target = "user", source = "userEntity")
    @Mapping(target = "partner", source = "partnerEntity")
    @Override
    PartnerUserVo toDto(PartnerUserEntity e);

    @Mapping(target = "userEntity", source = "user")
    @Mapping(target = "partnerEntity", source = "partner")
    @Override
    PartnerUserEntity toEntity(PartnerUserVo d);

    @Override
    List<PartnerUserVo> toDtoList(List<PartnerUserEntity> e);

    @Override
    List<PartnerUserEntity> toEntities(List<PartnerUserVo> d);
}
