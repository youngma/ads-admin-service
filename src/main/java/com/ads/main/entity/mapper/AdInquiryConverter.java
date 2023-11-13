package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.core.enums.inquiry.InquiryStatus;
import com.ads.main.core.enums.inquiry.InquiryType;
import com.ads.main.entity.AdInquiryEntity;
import com.ads.main.vo.inquiry.resp.AdInquiryVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdInquiryConverter extends GenericMapper<AdInquiryVo, AdInquiryEntity> {

    @Mapping(target = "groupName", ignore = true)
    @Mapping(target = "campaignName", ignore = true)
    @Mapping(target = "businessName", ignore = true)
    @Mapping(target = "advertiserName", ignore = true)
    @Mapping(target = "statusName", ignore = true)
    @Mapping(target = "inquiryTypeName", ignore = true)
    @Override
    AdInquiryVo toDto(AdInquiryEntity e);

    @Override
    AdInquiryEntity toEntity(AdInquiryVo d);

    @Override
    List<AdInquiryEntity> toEntities(List<AdInquiryVo> d);

    @Override
    List<AdInquiryVo> toDtoList(List<AdInquiryEntity> e);


    @Named("statusToEnum")
    default InquiryStatus statusToEnum(String source) {
        return InquiryStatus.of(source);
    }

    @Named("statusToValue")
    default String statusToValue(InquiryStatus inquiryStatus) {
        return inquiryStatus.getCode();
    }

    @Named("inquiryTypeToEnum")
    default InquiryType inquiryTypeToEnum(String source) {
        return InquiryType.of(source);
    }

    @Named("inquiryTypeToValue")
    default String inquiryTypeToValue(InquiryType inquiryType) {
        return inquiryType.getCode();
    }
}
