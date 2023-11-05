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



    @PostMapping("/distances/crowFlight")
    public Double createDistance(@RequestParam String fromCity, String toCity){
        CityResponse city1 = new CityResponse(cityService.getCityByName(fromCity));
        CityResponse city2 = new CityResponse(cityService.getCityByName(toCity));
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

    @PostMapping("/distances/matrix")
    public Double calculateMatrix(@RequestParam String fromCity,@RequestParam String toCity,@RequestParam String[] intermediateСities){
        CityResponse city1 = new CityResponse(cityService.getCityByName(fromCity));
        CityResponse city2 = new CityResponse(cityService.getCityByName(toCity));
        List<City> cities = cityService.getAll();
        return distanceService.createDistanceMatrix(city1,city2, intermediateСities, cities);
    }



}
