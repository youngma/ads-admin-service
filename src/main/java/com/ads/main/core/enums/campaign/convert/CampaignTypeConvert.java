package com.ads.main.core.enums.campaign.convert;

import com.ads.main.core.enums.campaign.CampaignType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class CampaignTypeConvert implements AttributeConverter<CampaignType, String> {
    @Override
    public String convertToDatabaseColumn(CampaignType attribute) {
        return attribute.getCode();
    }

    @Override
    public CampaignType convertToEntityAttribute(String dbData) {
        return CampaignType.of(dbData);
    }
}
