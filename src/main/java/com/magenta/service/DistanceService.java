package com.magenta.service;

import com.magenta.Entity.City;
import com.magenta.Entity.Distance;
import com.magenta.dto.CityResponse;
import com.magenta.dto.DistanceResponse;
import com.magenta.exception.DistanceNotFoundException;
import com.magenta.repository.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistanceService {

    private final DistanceRepository distanceRepository;
//    private final CityService cityService;

    @Autowired
    public DistanceService(DistanceRepository distanceRepository, CityService cityService) {
        this.distanceRepository = distanceRepository;
    }

    public Long createDistanceCrowFlight(CityResponse city1, CityResponse city2) {
        Distance distance = new Distance(city1, city2);
        distance.setMethod("CrowFlight");
        return distanceRepository.save(distance).getId();
    }

    public Long createDistanceMatrix(City city1, City city2, Long[] ids, List<City> cities,List<City> cities2) {
        Distance distance = new Distance(city1, city2, ids,cities,cities2);
        distance.setMethod("Matrix");
        return distanceRepository.save(distance).getId();
    }

    public Distance getDistanceById(Long id) {
        return distanceRepository.findById(id)
                .orElseThrow(() -> new DistanceNotFoundException("can`t find distance with id: " + id));
    }


    public List<Distance> getAll() {
        return distanceRepository.findAll();
    }



//        public List<City> formCollection(Long[] ids) {
//            List<City> dist = null;
//            List<City> cities = null;
//            cities = cityService.getAll();
//            for (int i = 0; i < ids.length; i++) {
//                if (cities.get(i).getId() == ids[i]){
//                    dist.add(cities.get(i));
//                }
//            }
//            return dist;
//        }
//
//        public List<Double> calculateM(List<City> cities){
//            List<Double> distance1 = null;
//            for (int i = 1; i < cities.size(); i++) {
//                double bd = cities.get(0).getLatitude();
//                double bsh = cities.get(0).getLongitude();
//                double dd = cities.get(i).getLatitude();
//                double dsh = cities.get(i).getLongitude();
//
//                double gip = Math.abs(bd - dd) * 111.1;
//                double codd = Math.cos(Math.toRadians(dd));
//                double cobd = Math.cos(Math.toRadians(bd));
//
//                double up = 111.3 * codd;
//                double down = 111.3 * cobd;
//
//                double ss = Math.abs(bsh - dsh);
//                double bc = ss * up;
//                double add = ss * down;
//
//                double mal = Math.abs((bc - add) * 0.5);
//                double bh = Math.sqrt(Math.pow(gip, 2) - Math.pow(mal, 2));
//                double hd = add - mal;
//                double pom = Math.pow(bh, 2) + Math.pow(hd, 2);
//                double distance = Math.sqrt(pom);
//                distance1.add(distance);
//            }
//            return distance1;
//        }
//
//        public List<Double> calculateOther(List<City> cities){
//            List<Double> distance1 = null;
//            for (int i = 1; i < cities.size(); i++) {
//                for (int j = 0; j < cities.size(); j++) {
//                    double bd = cities.get(j).getLatitude();
//                    double bsh = cities.get(j).getLongitude();
//                    double dd = cities.get(i).getLatitude();
//                    double dsh = cities.get(i).getLongitude();
//
//                    double gip = Math.abs(bd - dd) * 111.1;
//                    double codd = Math.cos(Math.toRadians(dd));
//                    double cobd = Math.cos(Math.toRadians(bd));
//
//                    double up = 111.3 * codd;
//                    double down = 111.3 * cobd;
//
//                    double ss = Math.abs(bsh - dsh);
//                    double bc = ss * up;
//                    double add = ss * down;
//
//                    double mal = Math.abs((bc - add) * 0.5);
//                    double bh = Math.sqrt(Math.pow(gip, 2) - Math.pow(mal, 2));
//                    double hd = add - mal;
//                    double pom = Math.pow(bh, 2) + Math.pow(hd, 2);
//                    double distance = Math.sqrt(pom);
//                    distance1.add(distance);
//                    cities.remove(j);
//                }
//
//            }
//            return distance1;
//        }
//
//        Double summary(List<Double> list1,List<Double> list2,List<Double> list3){
//            Double sum = null;
//            List<Double> listSum = null;
//            for (int i = 0; i < list1.size(); i++) {
//                sum = list1.get(i) + list2.get(i) + list3.get(i);
//                listSum.add(i,sum);
//            }
//
//            Double min = listSum.get(0);
//            for (Double num : listSum) {
//                if (num < min) {
//                    min = num;
//                }
//            }
//            return min;
//        }
//
//        public Double calculateDistanceMatrix (CityResponse city1, CityResponse city2 , Long[] ids) {
//            List<City> cities;
//            cities = formCollection(ids);
//            cities.remove(city1);
//            cities.remove(city1);
//            cities.add(0, city1);
//            cities.add(1, city2);
//            List <Double> distanceToOther = calculateM(cities);
//
//
//            List<City> cities2;
//            cities2 = formCollection(ids);
//            cities2.remove(city1);
//            cities2.remove(city2);
//            cities2.add(0, city2);
//            List <Double> distanceFromOtherToSecond = calculateM(cities2);
//
//            List<City> city  = formCollection(ids);
//            city.remove(city1);
//            city.remove(city2);
//            List <Double> distanceFromOtherToOther = calculateOther(city);
//
//
//            return summary(distanceToOther, distanceFromOtherToSecond, distanceFromOtherToOther);
//        }

    }
