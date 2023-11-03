package com.magenta.controller;

import com.magenta.Entity.City;
import com.magenta.dto.CityRequest;
import com.magenta.dto.CityResponse;
import com.magenta.dto.DistanceResponse;
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
    public int createCity(@RequestBody CityRequest cityRequest){
        return cityService.createCity(cityRequest.getName(),
                cityRequest.getLatitude(), cityRequest.getLongitude());
    }

    @GetMapping("/cities/{id}")
    public CityResponse getCity(@PathVariable Long id){
        return new CityResponse(cityService.getCityById(id));
    }

    @GetMapping("/cities")
    public List<CityResponse> getAllCities(){
        return cityService.getAll().stream().map(CityResponse::new)
                .collect(Collectors.toList());
    }


    @PostMapping("/distances/Crowflight")
    public Long createDistance(@RequestParam Long[] myParams){
        CityResponse city1 = new CityResponse(cityService.getCityById(myParams[0]));
        CityResponse city2 = new CityResponse(cityService.getCityById(myParams[1]));
        return distanceService.createDistanceCrowFlight(city1, city2);
    }

    @GetMapping("/distances/{id}")
    public DistanceResponse getDistance(@PathVariable Long id){
        return new DistanceResponse(distanceService.getDistanceById(id));
    }

    @GetMapping("/distances")
    public List<DistanceResponse> getAll(){
        return distanceService.getAll().stream().map(DistanceResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/distances/all")
    public Long testing(@RequestParam Long[] ids){
        //СНАЧАЛА НАДО ВВЕСТИ ИНДЕКСЫ ГОРОДОВ ОТПРАВЛЕНИЯ И НАЗНАЧЕНИЯ!!!
        CityResponse city1 = new CityResponse(cityService.getCityById(ids[0]));
        CityResponse city2 = new CityResponse(cityService.getCityById(ids[1]));
        List<City> cities = cityService.getAll();
        List<City> cities2 = cityService.getAll();
        return distanceService.createDistanceMatrix(city1,city2, ids, cities, cities2);
    }



}
