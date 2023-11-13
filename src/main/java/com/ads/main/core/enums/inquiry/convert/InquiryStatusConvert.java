package com.ads.main.core.enums.inquiry.convert;

import com.ads.main.core.enums.inquiry.InquiryStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class InquiryStatusConvert implements AttributeConverter<InquiryStatus, String> {
    @Override
    public String convertToDatabaseColumn(InquiryStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public InquiryStatus convertToEntityAttribute(String dbData) {
        return InquiryStatus.of(dbData);
    }
}
