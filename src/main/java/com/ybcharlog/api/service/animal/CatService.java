package com.ybcharlog.api.service.animal;

import com.ybcharlog.api.domain.AnimalType;
import org.springframework.stereotype.Component;

public class CatService implements AnimalService {

    @Override
    public String getSound() {
        return "야옹";
    }

//    @Override
//    public AnimalType getType() {
//        return AnimalType.CAT;
//    }
}
