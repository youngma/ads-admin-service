package com.ads.main.core.enums.inquiry.convert;

import com.ads.main.core.enums.inquiry.InquiryType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class InquiryTypeConvert implements AttributeConverter<InquiryType, String> {
    @Override
    public String convertToDatabaseColumn(InquiryType attribute) {
        return attribute.getCode();
    }

    @Override
    public InquiryType convertToEntityAttribute(String dbData) {
        return InquiryType.of(dbData);
    }
}
