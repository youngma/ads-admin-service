package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.AdvertiserUserEntity;
import com.ads.main.entity.mapper.AdvertiserConvert;
import com.ads.main.entity.mapper.UserConverter;
import com.ads.main.vo.advertiser.user.AdvertiserUserVo;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring"
        ,uses = { UserConverter.class, AdvertiserConvert.class }

)
public interface AdvertiserUserConvert extends GenericMapper<AdvertiserUserVo, AdvertiserUserEntity> {

    @Mapping(target = "userEntity", source = "user")
    @Mapping(target = "advertiserEntity", source = "advertiser")
    @Override
    AdvertiserUserEntity toEntity(AdvertiserUserVo d);

    @Override
    @Mappings({
        @Mapping(source = "advertiserEntity", target = "advertiser"),
        @Mapping(source = "userEntity", target = "user"),
    })
    AdvertiserUserVo toDto(AdvertiserUserEntity e);

    @Override
    List<AdvertiserUserVo> toDtoList(List<AdvertiserUserEntity> e);

    @Override
    List<AdvertiserUserEntity> toEntities(List<AdvertiserUserVo> d);

     @Mapping(target = "userEntity", source = "user")
     @Mapping(target = "advertiserEntity", source = "advertiser")
     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     void updateFromDto(AdvertiserUserVo advertiserUserVo, @MappingTarget AdvertiserUserEntity advertiserUserEntity);

}
