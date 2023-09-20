package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.entity.FilesEntity;
import com.ads.main.vo.FilesVo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FilesConverter extends GenericMapper<FilesVo, FilesEntity> {

    @Mapping(target = "newFile", ignore = true)
    @Override
    FilesVo toDto(FilesEntity e);

    @Override
    FilesEntity toEntity(FilesVo d);

    @Override
    List<FilesVo> toDtoList(List<FilesEntity> e);

    @Override
    List<FilesEntity> toEntities(List<FilesVo> d);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(FilesVo dto, @MappingTarget FilesEntity entity);
}
