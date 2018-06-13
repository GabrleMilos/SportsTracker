package cz.uhk.fim.sportstracker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.uhk.fim.sportstracker.Database.DatabaseHelper;
import cz.uhk.fim.sportstracker.Models.Activity;
import cz.uhk.fim.sportstracker.Models.Position;
import cz.uhk.fim.sportstracker.Models.User;
import cz.uhk.fim.sportstracker.TestData.TestData;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @BindView(R.id.recyclerMain)
    RecyclerView recyclerView;
    @BindView(R.id.btnNewActivity)
    FloatingActionButton btnNewActivity;

    RecyclerView.LayoutManager layoutManager;

    ActivityAdapter activityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        databaseHelper = new DatabaseHelper(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//                insertTestData();
//                readTestData();
        User u = databaseHelper.getUser("gabrlmi1");
        activityAdapter = new ActivityAdapter(databaseHelper.getUserActivities(u.getId()));
        recyclerView.setAdapter(activityAdapter);




        btnNewActivity.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, NewActivity.class);
                        startActivityForResult(intent, 10);
                    }
                });
    }


    private void insertTestData() {
        try {
        User u = new User("gabrlmi1", "gabrlmi1", 92.5, 194.0, new Date(), "male");
        Position p1 = new Position(50.289324, 16.152527, format.parse("2016-08-12 19:00:00"));
        Position p2 = new Position(50.290599, 16.151604, format.parse("2016-08-12 19:01:00"));
        Position p3 = new Position(50.290654, 16.151604, format.parse("2016-08-12 19:02:00"));
        Position p4 = new Position(50.293834, 16.150381, format.parse("2016-08-12 19:03:00"));
        Position p5 = new Position(50.293011, 16.153964, format.parse("2016-08-12 19:04:00"));
        Position p6 = new Position(50.291037, 16.157805, format.parse("2016-08-12 19:05:00"));
        Position p7 = new Position(50.290215, 16.155853, format.parse("2016-08-12 19:06:00"));
        Position p8 = new Position(50.288254, 16.153986, format.parse("2016-08-12 19:07:00"));
        Position p9 = new Position(50.289392, 16.152484, format.parse("2016-08-12 19:08:00"));

        List<Position> positionList1 = new ArrayList<>();
        positionList1.add(p1);
        positionList1.add(p2);
        positionList1.add(p3);
        positionList1.add(p4);
        positionList1.add(p5);
        positionList1.add(p6);
        positionList1.add(p7);
        positionList1.add(p8);
        positionList1.add(p9);


        List<Position> positionList2 = new ArrayList<>();
        positionList2.add(p1);
        positionList2.add(p2);
        positionList2.add(p3);

        Activity a1 = new Activity(positionList1, format.parse("2016-08-12 19:00:00"));
        Activity a2 = new Activity(positionList2, format.parse("2016-08-12 19:00:00"));


        if (databaseHelper.insertUser(u)) {
            Log.i("User", "inserted");
        }
        ;
        if (databaseHelper.insertActivity(a1, 1)) {
            Log.i("Activity1", "inserted");
        }
        ;

        if (databaseHelper.insertActivity(a2, 1)) {
            Log.i("Activity2", "inserted");
        }
        ;
    } catch (ParseException e) {
        e.printStackTrace();
    }

    }
}
