package com.ybcharlog.api.service.animal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnimalServiceFinder {

    private final List<AnimalService> animalServices;

//    public AnimalService find(String type) {
//        return animalServices.stream()
//                .filter(animalService -> animalService.getType() == AnimalType.valueOf(type))
//                .findAny()
//                .orElseThrow(RuntimeException::new);
//    }

}
