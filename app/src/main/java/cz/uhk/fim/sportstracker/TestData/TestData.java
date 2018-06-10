package cz.uhk.fim.sportstracker.TestData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.uhk.fim.sportstracker.Models.Activity;
import cz.uhk.fim.sportstracker.Models.Position;

public class TestData {

    public static List<Activity> GetUserActivities(){
        Position p1 = new Position(50.289700, 16.152545, new Date(2016, 8, 12, 19,0));
        Position p2 = new Position(50.293607, 16.150378, new Date(2016, 8, 12, 19,1));
        Position p3 = new Position(50.292935, 16.154304, new Date(2016, 8, 12, 19,2));
        Position p4 = new Position(50.291167, 16.157845, new Date(2016, 8, 12, 19,3));
        Position p5 = new Position(50.290193, 16.155764, new Date(2016, 8, 12, 19,4));
        List<Position> positionList1 = new ArrayList<>();
        positionList1.add(p1);
        positionList1.add(p2);
        positionList1.add(p3);
        positionList1.add(p4);
        positionList1.add(p5);

        List<Position> positionList2 = new ArrayList<>();
        positionList2.add(p1);
        positionList2.add(p2);
        positionList2.add(p3);

        Activity a1 = new Activity(positionList1, new Date(2016, 8, 12, 19,4));
        Activity a2 = new Activity(positionList2, new Date(2016, 8, 12, 19,4));
        List<Activity> list = new ArrayList<Activity>();
        list.add(a1);
        list.add(a2);
        return list;
    }

    public static Activity GetActivity(){
        return GetUserActivities().get(1);
    }
}
