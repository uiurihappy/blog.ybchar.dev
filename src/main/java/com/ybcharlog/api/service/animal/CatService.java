package com.ybcharlog.api.service.animal;

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
