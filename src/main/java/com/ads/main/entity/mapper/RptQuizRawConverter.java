package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.RptQuizRawEntity;
import com.ads.main.vo.admin.report.resp.RptQuizRawVo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RptQuizRawConverter extends GenericMapper<RptQuizRawVo, RptQuizRawEntity> {

    @Mapping(target = "userCount", ignore = true)
    @Mapping(target = "partnerCount", ignore = true)
    @Mapping(target = "campaignCount", ignore = true)
    @Mapping(target = "advertiserCount", ignore = true)
    @Mapping(target = "adGroupCount", ignore = true)
    @Mapping(target = "rptDate", ignore = true)
    @Mapping(target = "partnerName", ignore = true)
    @Mapping(target = "groupName", ignore = true)
    @Mapping(target = "campaignName", ignore = true)
    @Mapping(target = "advertiserName", ignore = true)
    @Override
    RptQuizRawVo toDto(RptQuizRawEntity e);

    @Override
    RptQuizRawEntity toEntity(RptQuizRawVo d);

    @Override
    List<RptQuizRawVo> toDtoList(List<RptQuizRawEntity> e);

    @Override
    List<RptQuizRawEntity> toEntities(List<RptQuizRawVo> d);

}
