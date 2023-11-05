package com.magenta.service;

import com.magenta.entity.City;
import com.magenta.exception.CityNotFoundException;
import org.springframework.stereotype.Service;
import com.magenta.repository.CityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public int createCity(String name, double latitude,double longitude){
        City city = new City(name, latitude, longitude);
        return cityRepository.save(city).getId();
    }

    public City getCityById(Long id){
        return cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("can`t find city with id: " + id));
    }

    public City getCityByName(String name){
        List<City> allCities = new ArrayList<>();
        List<City> allCities2 = new ArrayList<>();
        allCities = cityRepository.findAll();
        for (int i = 0; i < allCities.size(); i++) {
            if (allCities.get(i).getName().equals(name)){
                allCities2.add(0, allCities.get(i));
            }
        }
        return allCities2.get(0);
    }

    public List<City> getAll(){
        return cityRepository.findAll();
    }

}
