package com.magenta.Entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        this.distanceBetween = calculateDistance(city1, city2);
    }

    public Distance(City city1, City city2, Long[] ids, List<City> list, List<City> list2) {
        this.fromCity = city1.getName();
        this.toCity = city2.getName();
        this.distanceBetween = calculateDistanceMatrix(city1, city2, ids, list, list2);

    }



    public Double calculateDistance(City city1, City city2){
        double bd = city1.getLatitude();
        double bsh = city1.getLongitude();
        double dd = city2.getLatitude();
        double dsh = city2.getLongitude();

        double gip = Math.abs(bd - dd) * 111.1;
        double codd = Math.cos(Math.toRadians(dd));
        double cobd = Math.cos(Math.toRadians(bd));

        double up = 111.3 * codd;
        double down = 111.3 * cobd;

        double ss = Math.abs(bsh - dsh);
        double bc = ss * up;
        double add = ss * down;

        double mal = Math.abs((bc - add)*0.5);
        double bh =   Math.sqrt(Math.pow(gip,2) - Math.pow(mal,2));
        double hd = add - mal;
        double pom =  Math.pow(bh,2) +  Math.pow(hd, 2);
        double distance = Math.sqrt(pom);

        return distance;
    }


    public List<City> formCollection(Long[] ids, List<City> cities1,List<City> cities2) {

        cities2.clear();
        for (int i = 0; i < ids.length; i++) {
            if (cities1.get(i).getId() == ids[i]){
                cities2.add(cities1.get(i));
            }
        }
        return cities2;
    }


    public List<Double> calculateFromStartToOther(List<City> cities){
        List<Double> distance1 = new ArrayList<>();

        Double[] dis = new Double[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            double bd = cities.get(0).getLatitude();
            double bsh = cities.get(0).getLongitude();
            double dd = cities.get(i).getLatitude();
            double dsh = cities.get(i).getLongitude();

            double gip = Math.abs(bd - dd) * 111.1;
            double codd = Math.cos(Math.toRadians(dd));
            double cobd = Math.cos(Math.toRadians(bd));

            double up = 111.3 * codd;
            double down = 111.3 * cobd;

            double ss = Math.abs(bsh - dsh);
            double bc = ss * up;
            double add = ss * down;

            double mal = Math.abs((bc - add) * 0.5);
            double bh = Math.sqrt(Math.pow(gip, 2) - Math.pow(mal, 2));
            double hd = add - mal;
            double pom = Math.pow(bh, 2) + Math.pow(hd, 2);
            double distance = Math.sqrt(pom);
            dis[i] = distance;
        }

        for (int i = 1; i < cities.size(); i++) {
            distance1.add(dis[i]);
        }

        return distance1;
    }

    public List<Double> calculateBetweenOther(List<City> cities){
        List<Double> list = new ArrayList<>();

        Double[] dis = new Double[cities.size()];  //дистанция от первого города до всех остальных
        Double[] dis2 = new Double[cities.size()];  //сумма дистанций первого города до всех остальных
        double sum = 0;


        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                    double bd = cities.get(i).getLatitude();
                    double bsh = cities.get(i).getLongitude();
                    double dd = cities.get(j).getLatitude();
                    double dsh = cities.get(j).getLongitude();

                    double gip = Math.abs(bd - dd) * 111.1;
                    double codd = Math.cos(Math.toRadians(dd));
                    double cobd = Math.cos(Math.toRadians(bd));

                    double up = 111.3 * codd;
                    double down = 111.3 * cobd;

                    double ss = Math.abs(bsh - dsh);
                    double bc = ss * up;
                    double add = ss * down;

                    double mal = Math.abs((bc - add) * 0.5);
                    double bh = Math.sqrt(Math.pow(gip, 2) - Math.pow(mal, 2));
                    double hd = add - mal;
                    double pom = Math.pow(bh, 2) + Math.pow(hd, 2);
                    double distance = Math.sqrt(pom);
                    dis[j] = distance;

            }
            for (int j = 0; j < dis.length; j++) {
                sum += dis[j];
            }
            dis2[i] = sum;
            sum = 0;

        }

        for (int i = 0; i < dis2.length; i++) {
            list.add(i,dis2[i]);
        }

            return list;
        }


    Double summary(List<Double> distanceToOther,List<Double> distanceFromOtherToSecond,List<Double> distanceBetweenOther){
        List<Double> list = new ArrayList<>();
        Double sum = Double.valueOf(0);

        for (int i = 0; i < distanceToOther.size(); i++) {
            sum = distanceToOther.get(i) + distanceFromOtherToSecond.get(i) + distanceBetweenOther.get(i);
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
            double bd = cities.get(i).getLatitude();
            double bsh = cities.get(i).getLongitude();
            double dd = cities.get(0).getLatitude();
            double dsh = cities.get(0).getLongitude();

            double gip = Math.abs(bd - dd) * 111.1;
            double codd = Math.cos(Math.toRadians(dd));
            double cobd = Math.cos(Math.toRadians(bd));

            double up = 111.3 * codd;
            double down = 111.3 * cobd;

            double ss = Math.abs(bsh - dsh);
            double bc = ss * up;
            double add = ss * down;

            double mal = Math.abs((bc - add) * 0.5);
            double bh = Math.sqrt(Math.pow(gip, 2) - Math.pow(mal, 2));
            double hd = add - mal;
            double pom = Math.pow(bh, 2) + Math.pow(hd, 2);
            double distance = Math.sqrt(pom);
            dis[i] = distance;
        }

        for (int i = 1; i < cities.size(); i++) {
            distance1.add(dis[i]);
        }

        return distance1;
    }


    public Double calculateDistanceMatrix(City city1, City city2, Long[] ids, List<City> list, List<City> list2) {
        List<City> cities;
        cities = formCollection(ids, list, list2);
        cities.remove(cities.get(0));
        cities.remove(cities.get(0));
        cities.add(0, city1);
        List<Double> distanceToOther = calculateFromStartToOther(cities);


        List<City> cities2;
        cities2 = formCollection(ids, list, list2);
        cities2.remove(cities2.get(0));
        cities2.remove(cities2.get(0));
        cities2.add(0, city2);
        List<Double> distanceFromOtherToSecond = calculateFromOtherToEnd(cities2);

        List<City> city  = formCollection(ids,list,list2);
        city.remove(city.get(0));
        city.remove(city.get(0));
        List<Double> distanceBetweenOther = calculateBetweenOther(city);




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
