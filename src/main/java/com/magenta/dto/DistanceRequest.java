package com.magenta.dto;

public class DistanceRequest {


    private CityResponse city1;
    private CityResponse city2;

    private String fromCity;
    private String toCity;
    private double distance;


    public DistanceRequest() {
        this.fromCity = city1.getName();
        this.toCity = city2.getName();
        this.distance = calculateDistance(city1, city2);
    }
    public Double calculateDistance(CityResponse city1, CityResponse city2){
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
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
