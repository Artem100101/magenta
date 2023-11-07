package com.magenta.controller;

import com.magenta.dto.*;
import com.magenta.entity.City;
import com.magenta.service.CityService;
import com.magenta.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {

    private final CityService cityService;
    private final DistanceService distanceService;

    @Autowired
    public Controller(CityService cityService, DistanceService distanceService) {
        this.cityService = cityService;
        this.distanceService = distanceService;
    }

    @PostMapping("/cities")
    //    С ПОМОЩЬЮ ДАННОГО ЗАПРОСА СОЗДАЕТСЯ ОБЪЕКТ В ТАБЛИЦЕ CITY
    //    ВХОДНЫЕ ПАРАМЕТРЫ ПЕРЕДАЮТСЯ С ПОМОЩЬЮ POSTMAN С ИСПОЛЬЗОВАНИЕМ МЕТОДА POST.
    //    НЕОБХОДИМО НАЖАТЬ НА ВКЛАДКУ Body И ПЕРЕДАВАТЬ ПАРАМЕТРЫ В ФОРМАТЕ JSON СЛЕДУЮЩЕМ ВИДЕ
    //    ПРИМЕР:
    //    {
    //    "name": "Samara",
    //    "latitude" : 53.12,
    //    "longitude": 50.06
    //      }
    public int createCity(@RequestBody CityRequest cityRequest){
        return cityService.createCity(cityRequest.getName(),
                cityRequest.getLatitude(), cityRequest.getLongitude());
    }

    @GetMapping("/cities/{id}")
    //    С ПОМОЩЬЮ ДАННОГО ЗАПРОСА МОЖНО ПОЛУЧИТЬ ПО ID КОНКРЕТНЫЙ ГОРОД
    public CityResponse getCity(@PathVariable Long id){
        return new CityResponse(cityService.getCityById(id));
    }

    @GetMapping("/cities")
    //    С ПОМОЩЬЮ ДАННОГО ЗАПРОСА МОЖНО ПОЛУЧИТЬ СПИСОК ВСЕХ ГОРОДОВ ИЗ ТАБЛИЦЫ CITY
    public List<CityResponse> getAllCities(){
        return cityService.getAll().stream().map(CityResponse::new)
                .collect(Collectors.toList());
    }



    @PostMapping("/distances/crowFlight")
    //    С ПОМОЩЬЮ ДАННОГО ЗАПРОСА РАССЧИТЫВАЕТСЯ РАССТОЯНИЕ МЕЖДУ ГОРОДАМИ С ИСПОЛЬЗОВАНИЕМ МЕТОДА crowFlight
    //    ВХОДНЫЕ ПАРАМЕТРЫ ПЕРЕДАЮТСЯ С ПОМОЩЬЮ POSTMAN С ИСПОЛЬЗОВАНИЕМ МЕТОДА POST.
    //    НЕОБХОДИМО НАЖАТЬ НА ВКЛАДКУ PARAMS И ПЕРЕДАВАТЬ ПАРАМЕТРЫ В СЛЕДУЮЩЕМ ВИДЕ
    //    ПРИМЕР:
    //    Key                       Value
    //    -----------------------------------
    //    fromCity                  Samara
    //    toCity                    Moscow
    //    Затем нажимаем Send и получаем результат в виде дистанции в километрах
    public Double createDistanceCrow(@RequestParam String fromCity,@RequestParam String toCity){
        CityResponse city1 = new CityResponse(cityService.getCityByName(fromCity));
        CityResponse city2 = new CityResponse(cityService.getCityByName(toCity));
        return distanceService.createDistanceCrowFlight(city1, city2);
    }

    @GetMapping("/distances/{id}")
    //    С ПОМОЩЬЮ ДАННОГО ЗАПРОСА МОЖНО ПОЛУЧИТЬ ПО ID КОНКРЕТНУЮ ДИСТАНЦИЮ
    public DistanceResponse getDistance(@PathVariable Long id){
        return new DistanceResponse(distanceService.getDistanceById(id));
    }

    @GetMapping("/distances")
    //    С ПОМОЩЬЮ ДАННОГО ЗАПРОСА МОЖНО ПОЛУЧИТЬ СПИСОК ВСЕХ ДИСТАНЦИЙ ИЗ ТАБЛИЦЫ DISTANCE
    public List<DistanceResponse> getAll(){
        return distanceService.getAll().stream().map(DistanceResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/distances/matrix")
    //    С ПОМОЩЬЮ ДАННОГО ЗАПРОСА РАССЧИТЫВАЕТСЯ РАССТОЯНИЕ МЕЖДУ ГОРОДАМИ С ИСПОЛЬЗОВАНИЕМ МЕТОДА Matrix
    //    ВХОДНЫЕ ПАРАМЕТРЫ ПЕРЕДАЮТСЯ С ПОМОЩЬЮ POSTMAN С ИСПОЛЬЗОВАНИЕМ МЕТОДА POST.
    //    НЕОБХОДИМО НАЖАТЬ НА ВКЛАДКУ PARAMS И ПЕРЕДАВАТЬ ПАРАМЕТРЫ В СЛЕДУЮЩЕМ ВИДЕ
    //    ПРИМЕР:
    //    Key                       Value
    //    -----------------------------------
    //    fromCity                  Samara
    //    toCity                    Moscow
    //    intermediateСities        Saratov
    //    intermediateСities        Saransk
    //    И так далее, затем нажимаем Send и получаем результат в виде дистанции в километрах
    public Double calculateMatrix(@RequestParam String fromCity,@RequestParam String toCity,@RequestParam String[] intermediateСities){
        CityResponse city1 = new CityResponse(cityService.getCityByName(fromCity));
        CityResponse city2 = new CityResponse(cityService.getCityByName(toCity));
        List<City> cities = cityService.getAll();
        return distanceService.createDistanceMatrix(city1,city2, intermediateСities, cities);
    }



}
