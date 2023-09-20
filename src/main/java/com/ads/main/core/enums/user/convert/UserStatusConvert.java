package com.ads.main.core.enums.user.convert;

import com.ads.main.core.enums.user.UserStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class UserStatusConvert implements AttributeConverter<UserStatus, String> {
    @Override
    public String convertToDatabaseColumn(UserStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbData) {
        return UserStatus.of(dbData);
    }
}
