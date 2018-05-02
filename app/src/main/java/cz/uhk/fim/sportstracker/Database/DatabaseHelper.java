package cz.uhk.fim.sportstracker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collection;

import cz.uhk.fim.sportstracker.Models.Activity;
import cz.uhk.fim.sportstracker.Models.Position;
import cz.uhk.fim.sportstracker.Models.User;

public class DatabaseHelper extends SQLiteOpenHelper implements ActivityHelperInterface, PositionHelperInterface, UserHelperInterface {

    public DatabaseHelper(Context context){
        super(context, "database",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserTable.SQL_QUERY_CREATE);
        sqLiteDatabase.execSQL(ActivityTable.SQL_QUERY_CREATE);
        sqLiteDatabase.execSQL(PositionTable.SQL_QUERY_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public Activity getActivity(int activityId) {
        return null;
    }

    @Override
    public boolean insertActivity(Activity activity) {
        return false;
    }

    @Override
    public boolean deleteActivity(Activity activity) {
        return false;
    }

    @Override
    public boolean deleteActivity(int activityId) {
        return false;
    }

    @Override
    public Collection<Position> getActivityPositions(int activityId) {
        return null;
    }

    @Override
    public Position getPosition(int positionId) {
        return null;
    }

    @Override
    public boolean insertPosition(Position position) {
        return false;
    }

    @Override
    public boolean deletePosition(int positionId) {
        return false;
    }

    @Override
    public boolean deletePosition(Position position) {
        return false;
    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public Collection<Activity> getUserActivities(int userId) {
        return null;
    }
}
