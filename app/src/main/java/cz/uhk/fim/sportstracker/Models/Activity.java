package cz.uhk.fim.sportstracker.Models;

import java.util.Date;
import java.util.List;

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

    public void setDate(Date date) {
        this.date = date;
    }
}
