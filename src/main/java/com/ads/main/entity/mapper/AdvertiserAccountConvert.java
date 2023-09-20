package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.core.enums.common.Bank;
import com.ads.main.entity.AdvertiserAccountEntity;
import com.ads.main.entity.mapper.FilesConverter;
import com.ads.main.vo.advertiser.account.AdvertiserAccountModifyVo;
import com.ads.main.vo.advertiser.account.AdvertiserAccountUseVo;
import com.ads.main.vo.advertiser.account.AdvertiserAccountVo;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring"
        ,uses = { FilesConverter.class }
)
public interface AdvertiserAccountConvert extends GenericMapper<AdvertiserAccountVo, AdvertiserAccountEntity> {

    @Mapping(target = "file", source = "filesEntity")
    @Mapping(target = "advertiserSeq", ignore = true)
    @Mapping(target = "bankCodeName", ignore = true)
    @Mapping(target = "bankCode", source = "bankCode", qualifiedByName = "bankToValue")
    @Override
    AdvertiserAccountVo toDto(AdvertiserAccountEntity e);

    @Mapping(target = "filesEntity", source = "file")
    @Mapping(target = "advertiserEntity", ignore = true)
    @Mapping(target = "bankCode", source = "bankCode", qualifiedByName = "bankToEnum")
    @Override
    AdvertiserAccountEntity toEntity(AdvertiserAccountVo d);

    @Override
    List<AdvertiserAccountVo> toDtoList(List<AdvertiserAccountEntity> e);

    @Override
    List<AdvertiserAccountEntity> toEntities(List<AdvertiserAccountVo> d);


    @Mapping(target = "advertiserEntity", ignore = true)
    @Mapping(target = "filesEntity", source = "file")
    @Mapping(target = "bankCode", source = "bankCode", qualifiedByName = "bankToEnum")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdvertiserAccountVo dto, @MappingTarget AdvertiserAccountEntity entity);


    @Mapping(target = "filesEntity", source = "file")
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @Mapping(target = "advertiserEntity", ignore = true)
    @Mapping(target = "bankCode", source = "bankCode", qualifiedByName = "bankToEnum")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdvertiserAccountModifyVo dto, @MappingTarget AdvertiserAccountEntity entity);


    @Mapping(target = "filesEntity", ignore = true)
    @Mapping(target = "advertiserEntity", ignore = true)
    @Mapping(target = "bankCode", ignore = true)
    @Mapping(target = "bankAccount", ignore = true)
    @Mapping(target = "accountUse", ignore = true)
    @Mapping(target = "accountHolder", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AdvertiserAccountUseVo dto, @MappingTarget AdvertiserAccountEntity entity);


    @Named("bankToEnum")
    default Bank bankToEnum(String bank) {
        return Bank.of(bank);
    }

    @Named("bankToValue")
    default String bankToValue(Bank bank) {
        return bank.getCode_3();
    }

}
