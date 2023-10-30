package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.RptQuizRawEntity;
import com.ads.main.vo.report.resp.RptQuizRawVo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RptQuizRawConverter extends GenericMapper<RptQuizRawVo, RptQuizRawEntity> {

    @Override
    RptQuizRawVo toDto(RptQuizRawEntity e);

    @Override
    RptQuizRawEntity toEntity(RptQuizRawVo d);

    @Override
    List<RptQuizRawVo> toDtoList(List<RptQuizRawEntity> e);

    @Override
    List<RptQuizRawEntity> toEntities(List<RptQuizRawVo> d);

}
