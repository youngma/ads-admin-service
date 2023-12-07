package com.ads.main.entity.mapper;

import com.ads.main.core.config.convert.GenericMapper;
import com.ads.main.core.enums.user.UserStatus;
import com.ads.main.entity.UserEntity;
import com.ads.main.vo.admin.user.UserModifyVo;
import com.ads.main.vo.admin.user.UserVo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter extends GenericMapper<UserVo, UserEntity> {

    @Mapping(target = "userStatusName", ignore = true)
    @Mapping(target = "userStatus", source = "userStatus", qualifiedByName = "userStatusToValue")
    @Override
    UserVo toDto(UserEntity e);

    @Mapping(target = "userStatus", source = "userStatus", qualifiedByName = "userStatusToEnum")
    @Override
    UserEntity toEntity(UserVo d);

    @Override
    List<UserVo> toDtoList(List<UserEntity> e);

    @Override
    List<UserEntity> toEntities(List<UserVo> d);

    @Mapping(target = "userRole", ignore = true)
    @Mapping(target = "userPassword", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "updatedId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "insertedId", ignore = true)
    @Mapping(target = "insertedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserModifyVo dto, @MappingTarget UserEntity entity);

    @Named("userStatusToEnum")
    default UserStatus userStatusToEnum(String userStatus) {
        return UserStatus.of(userStatus);
    }
    @Named("userStatusToValue")
    default String userStatusToValue(UserStatus userStatus) {
        return userStatus.getCode();
    }

}
