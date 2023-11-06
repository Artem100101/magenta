package com.magenta.entity;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "distance")
public class Distance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "fromCity")
    private String fromCity;
    @Column(name = "toCity")
    private String toCity;
    @Column(name = "distance")
    private Double distanceBetween;
    @Column(name = "method")
    private String method;

    public Distance() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public Distance(City city1, City city2) {
        this.fromCity = city1.getName();
        this.toCity = city2.getName();
        this.distanceBetween = crow(city1, city2);
    }

    public Distance(City city1, City city2, String[] names, List<City> list) {
        this.fromCity = city1.getName();
        this.toCity = city2.getName();
        this.distanceBetween = calculateDistanceMatrix(city1, city2, names, list);

    }


    public Double crow(City city, City city1){
        double dist = org.apache.lucene.util.SloppyMath.haversinMeters(city.getLatitude()
                , city.getLongitude(), city1.getLatitude(), city1.getLongitude());
        return dist/1000;
    }


    public List<City> findByName(List<City> cities1, String[] name) {
        List<City> cities2 = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            for (City city  : cities1) {
                if (city.getName().equals(name[i])){
                    cities2.add(i,city);
                }
            }
        }
        return cities2;
    }


    public List<Double> calculateFromStartToOther(List<City> cities){
        List<Double> distance1 = new ArrayList<>();

        Double[] dis = new Double[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            double distance = org.apache.lucene.util.SloppyMath.haversinMeters(cities.get(0).getLatitude()
                    , cities.get(0).getLongitude(), cities.get(i).getLatitude(), cities.get(i).getLongitude());
            dis[i] = distance/1000;
        }

        for (int i = 1; i < cities.size(); i++) {
            distance1.add(dis[i]);
        }

        return distance1;
    }

    public static int getFactorial(int f) {
        int result = 1;
        for (int i = 1; i <= f; i++) {
            result = result * i;
        }
        return result;
    }

    public Double calculateBetweenOther(List<City> cities){

        Double[] dis2 = new Double[cities.size() * 2];
        int size = cities.size();
        int variants = getFactorial(size);
        Double[] dis = new Double[variants * 2];
        int k = 0;

        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                double distance = org.apache.lucene.util.
                        SloppyMath.haversinMeters(cities.get(i).getLatitude()
                        , cities.get(i).getLongitude(), cities.get(j).getLatitude(),
                                cities.get(j).getLongitude());
                    dis[k] = distance/1000;
                    k++;

            }
            dis2 = Arrays.copyOf(dis, dis.length);

        }
        List<Double> list = new ArrayList<>();

        for (int i = 0; i < dis2.length; i++) {
            list.add(i, dis2[i]);
        }


        Set<Double> setTo = new HashSet<>(list);
        list.clear();
        list.addAll(setTo);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == 0){
                list.remove(list.get(i));
            }
        }

        Double sum1 = Double.valueOf(0);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null){
                sum1 += list.get(i);
            }

        }
        return sum1;
        }


    Double summary(List<Double> distanceToOther,List<Double> distanceFromOtherToSecond,Double distanceBetweenOther){
        List<Double> list = new ArrayList<>();

        Double sum = Double.valueOf(0);

        for (int i = 0; i < distanceToOther.size(); i++) {
            sum = distanceToOther.get(i) + distanceFromOtherToSecond.get(i) + distanceBetweenOther;
            list.add(i,sum);
        }

        Double min = list.get(0);

        for (Double i: list) {
            if(i < min)
                min = i;

        }

        return min;
    }

    public List<Double> calculateFromOtherToEnd(List<City> cities){
        List<Double> distance1 = new ArrayList<>();

        Double[] dis = new Double[cities.size()];
        for (int i = 1; i < cities.size(); i++) {
            double distance = org.apache.lucene.util.SloppyMath.haversinMeters(cities.get(i).getLatitude()
                    , cities.get(i).getLongitude(), cities.get(0).getLatitude(), cities.get(0).getLongitude());
            dis[i] = distance/1000;
        }

        for (int i = 1; i < cities.size(); i++) {
            distance1.add(dis[i]);
        }

        return distance1;
    }


    public Double calculateDistanceMatrix(City city1, City city2, String[] names, List<City> list) {
        List<City> cities;
        cities = findByName(list,names);
        cities.add(0, city1);
        List<Double> distanceToOther = calculateFromStartToOther(cities);

        List<City> cities2;
        cities2 = findByName(list,names);
        cities2.add(0,city2);
        List<Double> distanceFromOtherToSecond = calculateFromOtherToEnd(cities2);

        List<City> city  = findByName(list,names);

        Double distanceBetweenOther = calculateBetweenOther(city);

        return summary(distanceToOther,distanceFromOtherToSecond, distanceBetweenOther);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public double getDistance() {
        return distanceBetween;
    }

    public void setDistance(double distance) {
        this.distanceBetween = distance;
    }
}
