package com.magenta.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    public Double[][] calculateBetweenOther(List<City> cities){
        Double[] dis = new Double[cities.size()];
        Double[][] dis2 = new Double[cities.size()][cities.size()];

        for (int i = 0; i < cities.size()-1; i++) {

            for (int j = 1; j < cities.size(); j++) {
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
                dis[j-1] = distance;
                dis2[i][j-1] = dis[j-1];
            }
        }
        return dis2;
    }

    Double summary(List<Double> distanceToOther,List<Double> distanceFromOtherToSecond,Double[][] distanceFromOtherToOther){

//        Здесь
//                могло
//                бы
//                        быть
//                        ваше
//                                решение :)
//        на этом мои полномочия все(

        double random = 0;

        return random;
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
        Double[][] distanceFromOtherToOther = calculateBetweenOther(city);

        return summary(distanceToOther,distanceFromOtherToSecond, distanceFromOtherToOther);
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
