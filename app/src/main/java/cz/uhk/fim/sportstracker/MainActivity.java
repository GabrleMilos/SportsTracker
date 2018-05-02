package cz.uhk.fim.sportstracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.uhk.fim.sportstracker.Database.DatabaseHelper;
import cz.uhk.fim.sportstracker.Models.Activity;
import cz.uhk.fim.sportstracker.Models.Position;
import cz.uhk.fim.sportstracker.Models.User;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

//        insertTestData();
        readTestData();
    }

    private void readTestData() {
//        User u = databaseHelper.getUser("gabrlmi1");

//        Activity a = databaseHelper.getActivity(1);

//        List<Position> p = databaseHelper.getActivityPositions(1);

//        List<User> a = databaseHelper.getAllUsers();
//        for (User u: a) {
//            Toast.makeText(this, u.getLogin(),Toast.LENGTH_LONG).show();
//        }

        List<Activity> a = databaseHelper.getUserActivities(1);
        for (Activity u: a) {
            Toast.makeText(this, u.getDate().toString(),Toast.LENGTH_LONG).show();
        }
//        Toast.makeText(this, "this sucks",Toast.LENGTH_LONG).show();
    }

    private void insertTestData(){

        User u = new User("gabrlmi1", "gabrlmi1",92.5, 194.0, new Date(), "male");
        Position p1 = new Position(95.20,96.5, new Date(2016, 8, 12, 19,0));
        Position p2 = new Position(98,97, new Date(2016, 8, 12, 19,1));
        Position p3 = new Position(95,99, new Date(2016, 8, 12, 19,2));
        Position p4 = new Position(93,96.5, new Date(2016, 8, 12, 19,3));
        Position p5 = new Position(95.20,96.5, new Date(2016, 8, 12, 19,4));
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


        databaseHelper.insertUser(u);
        databaseHelper.insertActivity(a1,1);
        databaseHelper.insertActivity(a2,1);


    }
}
