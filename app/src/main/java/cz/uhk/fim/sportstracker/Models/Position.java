package cz.uhk.fim.sportstracker.Models;

import java.util.Date;

public class Position {

    private int id;
    private double lat;
    private double lng;
    private Date date;

    public Position(int id, double lat, double lng, Date date) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
    }

    public Position(double lat, double lng, Date date) {
        this.lat = lat;
        this.lng = lng;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
