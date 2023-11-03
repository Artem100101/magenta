package com.magenta.service;

import com.magenta.Entity.City;
import com.magenta.dto.CityResponse;
import com.magenta.exception.CityNotFoundException;
import org.springframework.stereotype.Service;
import com.magenta.repository.CityRepository;

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

    public List<City> getAll(){
        return cityRepository.findAll();
    }

}
