package com.ads.main.core.enums.campaign.convert;

import com.ads.main.core.enums.campaign.PaymentTerms;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class PaymentTermsConvert implements AttributeConverter<PaymentTerms, String> {
    @Override
    public String convertToDatabaseColumn(PaymentTerms attribute) {
        return attribute.getCode();
    }

    @Override
    public PaymentTerms convertToEntityAttribute(String dbData) {
        return PaymentTerms.of(dbData);
    }
}
