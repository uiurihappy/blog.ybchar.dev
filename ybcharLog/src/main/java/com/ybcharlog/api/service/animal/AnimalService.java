package com.ybcharlog.api.service.animal;

import com.ybcharlog.api.domain.AnimalType;

public interface AnimalService {
    String getSound();

    AnimalType getType();
}
