package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.RptSinkTimeEntity;
import com.ads.main.vo.admin.report.RptSinkTimeVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RptSinkTimeConvert extends GenericMapper<RptSinkTimeVo, RptSinkTimeEntity> {

    @Override
    RptSinkTimeVo toDto(RptSinkTimeEntity e);

    @Override
    RptSinkTimeEntity toEntity(RptSinkTimeVo d);

}
