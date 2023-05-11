package com.ybcharlog.api.Common.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ybcharlog.api.Common.service.JsonService;
import com.ybcharlog.api.domain.user.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Converter
public class UserRoleConverter implements AttributeConverter<List<UserRole>, String> {

    private final JsonService jsonService;

    @Override
    public String convertToDatabaseColumn(List<UserRole> attribute) {
        return jsonService.objectToString(attribute);
    }

    @Override
    public List<UserRole> convertToEntityAttribute(String dbData) {
        return jsonService.stringToObject(dbData, new TypeReference<>() {
        });
    }
}
