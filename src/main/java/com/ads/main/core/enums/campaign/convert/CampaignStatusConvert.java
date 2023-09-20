package com.ads.main.core.enums.campaign.convert;

import com.ads.main.core.enums.campaign.CampaignStatus;
import com.ads.main.core.enums.campaign.CampaignType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class CampaignStatusConvert implements AttributeConverter<CampaignStatus, String> {
    @Override
    public String convertToDatabaseColumn(CampaignStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public CampaignStatus convertToEntityAttribute(String dbData) {
        return CampaignStatus.of(dbData);
    }
}
