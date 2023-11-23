package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.RptQuizAdvertiserDailyEntity;
import com.ads.main.vo.report.resp.RptQuizAdvertiserDailyVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RptQuizAdvertiserDailyConverter extends GenericMapper<RptQuizAdvertiserDailyVo, RptQuizAdvertiserDailyEntity> {

    @Mapping(target = "campaignCount", ignore = true)
    @Mapping(target = "advertiserCount", ignore = true)
    @Mapping(target = "campaignName", ignore = true)
    @Mapping(target = "businessName", ignore = true)
    @Mapping(target = "advertiserSeq", ignore = true)
    @Mapping(target = "advertiserName", ignore = true)
    @Override
    RptQuizAdvertiserDailyVo toDto(RptQuizAdvertiserDailyEntity e);

    @Override
    RptQuizAdvertiserDailyEntity toEntity(RptQuizAdvertiserDailyVo d);

    @Override
    List<RptQuizAdvertiserDailyVo> toDtoList(List<RptQuizAdvertiserDailyEntity> e);

    @Override
    List<RptQuizAdvertiserDailyEntity> toEntities(List<RptQuizAdvertiserDailyVo> d);

}
