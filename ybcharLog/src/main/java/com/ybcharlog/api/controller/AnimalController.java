package com.ybcharlog.api.controller;

// type -> cat -> CatService
// type -> dog -> DogService

// cat -> 냐용
// dog -> 멍멍

import com.ybcharlog.api.domain.AnimalType;
import com.ybcharlog.api.service.animal.AnimalService;
import com.ybcharlog.api.service.animal.CatService;
import com.ybcharlog.api.service.animal.DogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AnimalController {

    private final List<AnimalService> animalServices;

    @GetMapping("/sound")
    public String sound(@RequestParam String animalType) {
        log.info("animalServices = {}", animalServices);

        AnimalService animalService1 = animalServices.stream()
                .filter(animalService -> animalService.getType() == AnimalType.valueOf(animalType))
                .findAny()
                .orElseThrow(RuntimeException::new);

        return animalService1.getSound();
//        if (animalType.equals("CAT")) {
//            return new CatService().getSound();
//        } else if (animalType.equals("DOG")){
//            return new DogService().getSound();
//        } else {
//            return "모르는 동물";
//        }
    }
}
