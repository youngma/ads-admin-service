package com.ads.main.core.enums.advertiser.convert;

import com.ads.main.core.enums.advertiser.AdGroupStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class AdGroupStatusConvert implements AttributeConverter<AdGroupStatus, String> {
    @Override
    public String convertToDatabaseColumn(AdGroupStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public AdGroupStatus convertToEntityAttribute(String dbData) {
        return AdGroupStatus.of(dbData);
    }
}
