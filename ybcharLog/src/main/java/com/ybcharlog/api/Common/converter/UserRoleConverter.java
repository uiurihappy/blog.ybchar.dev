package com.ybcharlog.api.Common.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ybcharlog.api.Common.service.JsonService;
import com.ybcharlog.api.domain.user.Role;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserRoleConverter implements AttributeConverter<List<Role>, String> {

    private final JsonService jsonService;

    @Override
    public String convertToDatabaseColumn(List<Role> attribute) {
        return jsonService.objectToString(attribute);
    }

    @Override
    public List<Role> convertToEntityAttribute(String dbData) {
        return jsonService.stringToObject(dbData, new TypeReference<>() {
        });
    }
}
