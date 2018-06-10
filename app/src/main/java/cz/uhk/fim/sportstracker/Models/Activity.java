package cz.uhk.fim.sportstracker.Models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Activity {
    private int id;
    private List<Position> positionList;
    private Date date;

    public Activity() {}

    public Activity(int id, List<Position> positionList, Date date) {
        this.id = id;
        this.positionList = positionList;
        this.date = date;
    }

    public Activity(List<Position> positionList, Date date) {
        this.positionList = positionList;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(date);
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public double getDistance(){
        double distance = 0;
        if(positionList.size() >= 2){
            for (int i = 0; i + 1 < positionList.size(); i++) {
                distance += calculateDistance(positionList.get(i).getLat(), positionList.get(i).getLng(),positionList.get(i + 1).getLat(), positionList.get(i).getLng());
            }
        }
        return distance;
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2){
        double PI_RAD = Math.PI / 180.0;
        double phi1 = lat1 * PI_RAD;
        double phi2 = lat2 * PI_RAD;
        double lam1 = lng1 * PI_RAD;
        double lam2 = lng2 * PI_RAD;

        return 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
    }
}
