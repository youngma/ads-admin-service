package com.ads.main.core.enums.common.convert;

import com.ads.main.core.enums.common.Bank;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class BankConvert implements AttributeConverter<Bank, String> {
    @Override
    public String convertToDatabaseColumn(Bank attribute) {
        return attribute.getCode_3();
    }

    @Override
    public Bank convertToEntityAttribute(String dbData) {
        return Bank.of(dbData);
    }
}
