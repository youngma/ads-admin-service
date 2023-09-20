package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.core.enums.common.Bank;
import com.ads.main.entity.PartnerAccountEntity;
import com.ads.main.entity.mapper.FilesConverter;
import com.ads.main.vo.partner.account.PartnerAccountModifyVo;
import com.ads.main.vo.partner.account.PartnerAccountRegisterVo;
import com.ads.main.vo.partner.account.PartnerAccountVo;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring"
        ,uses = { FilesConverter.class }
)
public interface PartnerAccountConvert extends GenericMapper<PartnerAccountVo, PartnerAccountEntity> {

    @Mapping(target = "file", source = "filesEntity")
    @Mapping(target = "bankCodeName", ignore = true)
    @Mapping(target = "partnerSeq", ignore = true)
    @Mapping(target = "bankCode", source = "bankCode", qualifiedByName = "bankToValue")
    @Override
    PartnerAccountVo toDto(PartnerAccountEntity e);

    @Mapping(target = "filesEntity", source = "file")
    @Mapping(target = "partnerEntity", ignore = true)
    @Mapping(target = "bankCode", source = "bankCode", qualifiedByName = "bankToEnum")
    @Override
    PartnerAccountEntity toEntity(PartnerAccountVo d);

    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "seq", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "accountUse", ignore = true)
    @Mapping(target = "partnerEntity", ignore = true)
    @Mapping(target = "filesEntity", source = "file")
    @Mapping(target = "bankCode", source = "bankCode", qualifiedByName = "bankToEnum")
    PartnerAccountEntity toEntity(PartnerAccountRegisterVo d);

    @Override
    List<PartnerAccountVo> toDtoList(List<PartnerAccountEntity> e);

    @Override
    List<PartnerAccountEntity> toEntities(List<PartnerAccountVo> d);

    @Mapping(target = "partnerEntity", ignore = true)
    @Mapping(target = "filesEntity", source = "file")
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "bankCode", source = "bankCode", qualifiedByName = "bankToEnum")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PartnerAccountModifyVo partnerAccountModifyVo, @MappingTarget PartnerAccountEntity partnerAccountEntity);


    @Named("bankToEnum")
    default Bank bankToEnum(String bank) {
        return Bank.of(bank);
    }

    @Named("bankToValue")
    default String bankToValue(Bank bank) {
        return bank.getCode_3();
    }
}
