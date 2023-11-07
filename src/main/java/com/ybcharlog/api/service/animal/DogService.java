package com.ybcharlog.api.service.animal;

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
