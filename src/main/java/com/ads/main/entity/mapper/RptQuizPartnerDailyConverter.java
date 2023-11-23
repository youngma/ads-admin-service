package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.RptQuizPartnerDailyEntity;
import com.ads.main.vo.report.resp.RptQuizPartnerDailyVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RptQuizPartnerDailyConverter extends GenericMapper<RptQuizPartnerDailyVo, RptQuizPartnerDailyEntity> {

    @Mapping(target = "partnerSeq", ignore = true)
    @Mapping(target = "partnerCount", ignore = true)
    @Mapping(target = "groupName", ignore = true)
    @Mapping(target = "businessName", ignore = true)
    @Mapping(target = "adGroupCount", ignore = true)
    @Override
    RptQuizPartnerDailyVo toDto(RptQuizPartnerDailyEntity e);

    @Override
    RptQuizPartnerDailyEntity toEntity(RptQuizPartnerDailyVo d);

    @Override
    List<RptQuizPartnerDailyVo> toDtoList(List<RptQuizPartnerDailyEntity> e);

    @Override
    List<RptQuizPartnerDailyEntity> toEntities(List<RptQuizPartnerDailyVo> d);

}
