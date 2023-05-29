package com.ybcharlog.api.controller;

// type -> cat -> CatService
// type -> dog -> DogService

// cat -> 냐용
// dog -> 멍멍

import com.ybcharlog.api.domain.AnimalType;
import com.ybcharlog.api.service.animal.AnimalService;
import com.ybcharlog.api.service.animal.AnimalServiceFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/*
    1. component list 주입
    2. Map (Key: beanName, value: service)
    3. enum
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AnimalController {

    private final List<AnimalService> animalServices;
    private final AnimalServiceFinder animalServiceFinder;

    // HashMap 주입
    private final Map<String, AnimalService> animalServiceMap;


    @GetMapping("/sound")
    public String sound(@RequestParam String animalType) {
//        log.info("animalServices = {}", animalServices);
//        log.info("animalServices = {}, keys = {}", animalServiceMap, animalServiceMap.keySet());
        // CAT -> catService
        // DOG -> dogService
//        AnimalService service = animalServiceMap.get(animalType.toLowerCase() + "Service");

//        AnimalService service = animalServices.stream()
//                .filter(animalService -> animalService.getType() == AnimalType.valueOf(animalType))
//                .findAny()
//                .orElseThrow(RuntimeException::new);
//        AnimalService service = animalServiceFinder.find(animalType);
//        return service.getSound();
//        if (animalType.equals("CAT")) {
//            return new CatService().getSound();
//        } else if (animalType.equals("DOG")){
//            return new DogService().getSound();
//        } else {
//            return "모르는 동물";
//        }
        AnimalType animalTypeEnum = AnimalType.valueOf(animalType);
        AnimalService service = animalTypeEnum.create();
        return service.getSound();
        // null 체크는 알아서 하십쇼
    }
}
