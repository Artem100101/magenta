package com.magenta.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "distance")
public class Distance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
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
    public Double crow(City city, City city1) {
        double dist = org.apache.lucene.util.SloppyMath.haversinMeters(city.getLatitude()
                , city.getLongitude(), city1.getLatitude(), city1.getLongitude());
        return dist / 1000;
    }


    public List<City> findByName(List<City> cities1, String[] name) {
        List<City> cities2 = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            for (City city : cities1) {
                if (city.getName().equals(name[i])) {
                    cities2.add(i, city);
                }
            }
        }
        return cities2;
    }

    public List<Double> calculateFromStartToOther(List<City> cities) {
        List<Double> distance1 = new ArrayList<>();

        Double[] dis = new Double[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            double distance = org.apache.lucene.util.SloppyMath.haversinMeters(cities.get(0).getLatitude()
                    , cities.get(0).getLongitude(), cities.get(i).getLatitude(), cities.get(i).getLongitude());
            dis[i] = distance / 1000;
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

    public Double calculateBetweenOther(List<City> cities) {
        Double[] dis2 = new Double[cities.size() * 2];
        int size = cities.size();
        int sizeOfFirst = cities.size() - 1;
        int variants = getFactorial(size);
        for (int i = 0; i < 1; i++) {

        }
        Double[] dis = new Double[variants * 2];
        Double[][] dis3 = new Double[variants * 2][variants * 2];
        int k = 0;

        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                double distance = org.apache.lucene.util.
                        SloppyMath.haversinMeters(cities.get(i).getLatitude()
                                , cities.get(i).getLongitude(), cities.get(j).getLatitude(),
                                cities.get(j).getLongitude());
                if (distance / 1000 != 0) {
                    dis[k] = distance / 1000;
                    k++;
                }
                dis3[i][j] = distance / 1000;


            }
            dis2 = Arrays.copyOf(dis, dis.length);

        }


        List<Double> list = new ArrayList<>();
        for (int i = 0; i < dis2.length; i++) {
            if (dis2[i] != null) {
                list.add(dis2[i]);
            }
        }

        Set<Double> setTo = new HashSet<>(list);
        list.clear();
        list.addAll(setTo);


        List<Double> list1 = new ArrayList<>();

        int kb = 0;

        Double distance = Double.valueOf(0);
        List<Double> disp = new ArrayList<>();
//        for (int i = 0; i < variants; i++) {
            for (int j = 0; j < cities.size(); j++) {
                for (int l = 0; l < cities.size(); l++) {
                    distance +=list.get(l);
                }

                distance = distance - list.get(j);
                list1.add(distance);
                distance = Double.valueOf(0);
            }

//        }
        Set<Double> setTy = new HashSet<>(list1);
        list1.clear();
        list1.addAll(setTy);



        Double min2 = list1.get(0);
        for (Double aDouble : list1) {
            if (aDouble < min2) {
                min2 = aDouble;
            }
        }

        return min2;
    }

    public List<Double> calculateFromStartToSum(List<Double> list, Double minBetween) {
        List<Double> distance1 = new ArrayList<>();
        Double sum = Double.valueOf(0);

        for (int i = 0; i < list.size(); i++) {
            sum = list.get(i) + minBetween;
            distance1.add(i, sum);
            sum = Double.valueOf(0);
        }

        return distance1;
    }

    public Double calculateFromStartToMin(List<Double> list, Double minBetween) {
        List<Double> distance1 = new ArrayList<>();
        Double razn = Double.valueOf(0);

        for (int i = 0; i < list.size(); i++) {
            razn = list.get(i) - minBetween;
            distance1.add(i, razn);
            razn = Double.valueOf(0);
        }

        Double min = distance1.get(0);
        for (Double i : distance1) {
            if (i < min)
                min = i;

        }

        return min;
    }

    public Double calculateLast(Double check, Double minBetween) {
        Double sum = Double.valueOf(0);
        sum = check + minBetween;

        return sum;
    }


    Double summary(Double distanceToOther, List<Double> distanceFromOtherToSecond) {
        List<Double> list = new ArrayList<>();

        Double sum = Double.valueOf(0);

        for (int i = 0; i < distanceFromOtherToSecond.size(); i++) {
                sum = distanceToOther + distanceFromOtherToSecond.get(i);
                list.add(i, sum);
                sum = Double.valueOf(0);

        }

        Double min = list.get(0);
        for (Double i : list) {
            if (i < min)
                min = i;

        }

        return min;
    }


    public List<Double> calculateFromOtherToEnd(List<City> cities) {
        List<Double> distance1 = new ArrayList<>();

        Double[] dis = new Double[cities.size()];
        for (int i = 1; i < cities.size(); i++) {
            double distance = org.apache.lucene.util.SloppyMath.haversinMeters(cities.get(i).getLatitude()
                    , cities.get(i).getLongitude(), cities.get(0).getLatitude(), cities.get(0).getLongitude());
            dis[i] = distance / 1000;
        }

        for (int i = 1; i < cities.size(); i++) {
            distance1.add(dis[i]);
        }

        return distance1;
    }


    public Double calculateDistanceMatrix(City city1, City city2, String[] names, List<City> list) {
        List<City> city = findByName(list, names);
        Double distanceBetweenOther = calculateBetweenOther(city);

        List<City> cities;
        cities = findByName(list, names);
        cities.add(0, city1);
        List<Double> distanceToOther = calculateFromStartToOther(cities);
        List<Double> distanceToOtherLast = calculateFromStartToSum(distanceToOther, distanceBetweenOther);
        Double distanceToOtherLastMin = calculateFromStartToMin(distanceToOtherLast, distanceBetweenOther);
        Double last = calculateLast(distanceToOtherLastMin, distanceBetweenOther);

        List<City> cities2;
        cities2 = findByName(list, names);
        cities2.add(0, city2);
        List<Double> distanceFromOtherToSecond = calculateFromOtherToEnd(cities2);

        return summary(last, distanceFromOtherToSecond);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromCity() {
        return fromCity;
    }

    public City setFromCity(String fromCity) {
        this.fromCity = fromCity;
        return null;
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