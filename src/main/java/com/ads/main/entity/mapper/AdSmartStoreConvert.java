package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.core.enums.campaign.PaymentTerms;
import com.ads.main.entity.AdSmartStoreEntity;
import com.ads.main.vo.admin.advertiser.campaign.req.AdSmartStoreModifyVo;
import com.ads.main.vo.admin.advertiser.campaign.req.AdSmartStoreRegisterVo;
import com.ads.main.vo.admin.advertiser.campaign.resp.AdSmartStoreVo;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
            FilesConverter.class
        }
)
public interface AdSmartStoreConvert extends GenericMapper<AdSmartStoreVo, AdSmartStoreEntity>  {

    @Mapping(target = "adCampaignMasterVo", ignore = true)
    @Mapping(target = "paymentTerms", source = "paymentTerms", qualifiedByName = "paymentTermsToValue")
    @Override
    AdSmartStoreVo toDto(AdSmartStoreEntity e);

    @Mapping(target = "adCampaignMasterEntity", ignore = true)
    @Mapping(target = "paymentTerms", source = "paymentTerms", qualifiedByName = "paymentTermsToEnum")
    @Override
    AdSmartStoreEntity toEntity(AdSmartStoreVo d);

    @Mapping(target = "adCampaignMasterEntity", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "paymentTerms", source = "paymentTerms", qualifiedByName = "paymentTermsToEnum")
    AdSmartStoreEntity toEntity(AdSmartStoreRegisterVo d);

    @Override
    List<AdSmartStoreVo> toDtoList(List<AdSmartStoreEntity> e);

    @Override
    List<AdSmartStoreEntity> toEntities(List<AdSmartStoreVo> d);


    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "adCampaignMasterEntity", ignore = true)
    @Mapping(target = "paymentTerms", source = "paymentTerms", qualifiedByName = "paymentTermsToEnum")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdSmartStoreRegisterVo adSmartStoreRegisterVo, @MappingTarget AdSmartStoreEntity partnerAdGroupEntity);


    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "adCampaignMasterEntity", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "paymentTerms", source = "paymentTerms", qualifiedByName = "paymentTermsToEnum")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdSmartStoreModifyVo adSmartStoreModifyVo, @MappingTarget AdSmartStoreEntity adSmartStoreEntity);


    @Named("paymentTermsToEnum")
    default PaymentTerms paymentTermsToEnum(String source) {
        return PaymentTerms.of(source);
    }

    @Named("paymentTermsToValue")
    default String paymentTermsToValue(PaymentTerms paymentTerms) {
        return paymentTerms.getCode();
    }

}
