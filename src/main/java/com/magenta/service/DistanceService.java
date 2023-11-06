package com.magenta.service;

import com.magenta.entity.City;
import com.magenta.entity.Distance;
import com.magenta.dto.CityResponse;
import com.magenta.exception.DistanceNotFoundException;
import com.magenta.repository.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistanceService {

    private final DistanceRepository distanceRepository;
    private final CityService cityService;

    @Autowired
    public DistanceService(DistanceRepository distanceRepository, CityService cityService, CityService cityService1) {
        this.distanceRepository = distanceRepository;
        this.cityService = cityService1;
    }

    public Double createDistanceCrowFlight(CityResponse city1, CityResponse city2) {
        Distance distance = new Distance(city1, city2);
        distance.setMethod("CrowFlight");
        return distanceRepository.save(distance).getDistance();
    }


    public Double createDistanceMatrix(City city1, City city2, String[] names, List<City> cities) {
        Distance distance = new Distance(city1, city2, names,cities);
        distance.setMethod("Matrix");
        return distanceRepository.save(distance).getDistance();
    }

    public Distance getDistanceById(Long id) {
        return distanceRepository.findById(id)
                .orElseThrow(() -> new DistanceNotFoundException("can`t find distance with id: " + id));
    }


    public List<Distance> getAll() {
        return distanceRepository.findAll();
    }

    }
