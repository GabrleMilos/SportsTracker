package cz.uhk.fim.sportstracker.Models;

import java.util.Date;
import java.util.List;

public class Activity {
    private int id;
    private List<Position> positionList;
    private double distance;
    private Date date;

    public Activity(int id, List<Position> positionList, double distance, Date date) {
        this.id = id;
        this.positionList = positionList;
        this.distance = distance;
        this.date = date;
    }

    public Activity(List<Position> positionList, double distance, Date date) {
        this.positionList = positionList;
        this.distance = distance;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
