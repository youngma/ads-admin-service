package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.RptQuizDailyEntity;
import com.ads.main.vo.admin.report.resp.RptQuizDailyVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RptQuizDailyConverter extends GenericMapper<RptQuizDailyVo, RptQuizDailyEntity> {

    @Override
    RptQuizDailyVo toDto(RptQuizDailyEntity e);

    @Override
    RptQuizDailyEntity toEntity(RptQuizDailyVo d);

    @Override
    List<RptQuizDailyVo> toDtoList(List<RptQuizDailyEntity> e);

    @Override
    List<RptQuizDailyEntity> toEntities(List<RptQuizDailyVo> d);

}
