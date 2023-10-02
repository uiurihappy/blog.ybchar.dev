package com.ybcharlog.api.service.animal;

import com.ybcharlog.api.domain.AnimalType;
import org.springframework.stereotype.Component;

public class DogService implements AnimalService {

    @Override
    public String getSound() {
        return "멍멍";
    }

//    @Override
//    public AnimalType getType() {
//        return AnimalType.DOG;
//    }
}
