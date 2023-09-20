package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.core.enums.advertiser.AdGroupStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import com.ads.main.entity.PartnerAdGroupEntity;
import com.ads.main.entity.mapper.FilesConverter;
import com.ads.main.vo.partner.adGroup.PartnerAdGroupModifyVo;
import com.ads.main.vo.partner.adGroup.PartnerAdGroupRegisterVo;
import com.ads.main.vo.partner.adGroup.PartnerAdGroupStatusVo;
import com.ads.main.vo.partner.adGroup.PartnerAdGroupVo;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring"
        ,uses = { PartnerConvert.class, FilesConverter.class }
)
public interface PartnerAdGroupConvert extends GenericMapper<PartnerAdGroupVo, PartnerAdGroupEntity> {

    @Mapping(target = "partner", source = "partnerEntity")
    @Mapping(target = "groupStatusName", ignore = true)
    @Mapping(target = "adTypeName", ignore = true)
    @Mapping(target = "pointIconFile", source = "pointIconFileEntity")
    @Mapping(target = "logoFile", source = "logoFileEntity")
    @Mapping(target = "adType", source = "adType", qualifiedByName = "adTypeToValue")
    @Mapping(target = "groupStatus", source = "groupStatus", qualifiedByName = "groupStatusToValue")
    @Override
    PartnerAdGroupVo toDto(PartnerAdGroupEntity e);

    @Mapping(target = "partnerEntity", ignore = true)
    @Mapping(target = "pointIconFileEntity", source = "pointIconFile")
    @Mapping(target = "logoFileEntity", source = "logoFile")
    @Mapping(target = "adType", source = "adType", qualifiedByName = "adTypeToEnum")
    @Override
    PartnerAdGroupEntity toEntity(PartnerAdGroupVo d);

    @Mapping(target = "requestAt", ignore = true)
    @Mapping(target = "rejectMessage", ignore = true)
    @Mapping(target = "rejectAt", ignore = true)
    @Mapping(target = "holdMessage", ignore = true)
    @Mapping(target = "holdAt", ignore = true)
    @Mapping(target = "approvalAt", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "groupStatus", ignore = true)
    @Mapping(target = "groupSeq", ignore = true)
    @Mapping(target = "partnerEntity", ignore = true)
    @Mapping(target = "pointIconFileEntity", source = "pointIconFile")
    @Mapping(target = "logoFileEntity", source = "logoFile")
    @Mapping(target = "adType", source = "adType", qualifiedByName = "adTypeToEnum")
    PartnerAdGroupEntity toEntity(PartnerAdGroupRegisterVo d);
    @Override
    List<PartnerAdGroupVo> toDtoList(List<PartnerAdGroupEntity> e);

    @Override
    List<PartnerAdGroupEntity> toEntities(List<PartnerAdGroupVo> d);

    @Mapping(target = "requestAt", ignore = true)
    @Mapping(target = "rejectMessage", ignore = true)
    @Mapping(target = "rejectAt", ignore = true)
    @Mapping(target = "holdMessage", ignore = true)
    @Mapping(target = "holdAt", ignore = true)
    @Mapping(target = "approvalAt", ignore = true)
    @Mapping(target = "partnerEntity", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "pointIconFileEntity", source = "pointIconFile")
    @Mapping(target = "logoFileEntity", source = "logoFile")
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "groupStatus", ignore = true)
    @Mapping(target = "groupCode", ignore = true)
    @Mapping(target = "adType", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PartnerAdGroupModifyVo partnerAdGroupModifyVo, @MappingTarget PartnerAdGroupEntity partnerAdGroupEntity);


    @Mapping(target = "requestAt", ignore = true)
    @Mapping(target = "rejectMessage", ignore = true)
    @Mapping(target = "rejectAt", ignore = true)
    @Mapping(target = "holdMessage", ignore = true)
    @Mapping(target = "holdAt", ignore = true)
    @Mapping(target = "groupStatus", ignore = true)
    @Mapping(target = "approvalAt", ignore = true)
    @Mapping(target = "partnerEntity", ignore = true)
    @Mapping(target = "groupName", ignore = true)
    @Mapping(target = "rewordRate", ignore = true)
    @Mapping(target = "pointName", ignore = true)
    @Mapping(target = "commissionRate", ignore = true)
    @Mapping(target = "callBackUrl", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "pointIconFileEntity", ignore = true)
    @Mapping(target = "logoFileEntity", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "groupCode", ignore = true)
    @Mapping(target = "adType", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PartnerAdGroupStatusVo partnerAdGroupStatusVo, @MappingTarget PartnerAdGroupEntity partnerAdGroupEntity);



    @Named("adTypeToEnum")
    default CampaignType adTypeToEnum(String source) {
        return CampaignType.of(source);
    }

    @Named("adTypeToValue")
    default String adTypeToValue(CampaignType adType) {
        return adType.getCode();
    }

    @Named("groupStatusToEnum")
    default AdGroupStatus groupStatusToEnum(String source) {
        return AdGroupStatus.of(source);
    }

    @Named("groupStatusToValue")
    default String groupStatusToValue(AdGroupStatus adGroupStatus) {
        return adGroupStatus.getCode();
    }


}
