package cz.uhk.fim.sportstracker.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
        SQLiteDatabase database = getReadableDatabase();
        String [] projection = {ActivityTable._ID ,ActivityTable.COLUMN_USER_ID,ActivityTable.COLUMN_DATE};
        String selection = ActivityTable._ID + "= ?";
        String [] selectionArgs = {String.valueOf(activityId)};

        Cursor cursor = database.query(ActivityTable.TABLE_NAME, projection, selection, selectionArgs, null, null, ActivityTable.COLUMN_DATE);
        Activity a = new Activity();
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex((ActivityTable._ID)));
            String dateString = cursor.getString(cursor.getColumnIndex((ActivityTable.COLUMN_DATE)));
            Date date = null;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Position> positionList = getActivityPositions(activityId);
            a = new Activity(id, positionList, date);
        }

        return a;
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
    public List<Position> getActivityPositions(int activityId) {
        SQLiteDatabase database = getReadableDatabase();
        String [] projection = {PositionTable._ID ,PositionTable.COLUMN_LAT, PositionTable.COLUMN_LNG, PositionTable.COLUMN_DATE, PositionTable.COLUMN_ACTIVITY_ID};
        String selection = PositionTable.COLUMN_ACTIVITY_ID + "= ?";
        String [] selectionArgs = {String.valueOf(activityId)};

        Cursor cursor = database.query(PositionTable.TABLE_NAME, projection, selection, selectionArgs, null, null, PositionTable.COLUMN_DATE);
        List<Position> positionList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex((PositionTable._ID)));
            double lat = cursor.getInt(cursor.getColumnIndex((PositionTable.COLUMN_LAT)));
            double lng = cursor.getInt(cursor.getColumnIndex((PositionTable.COLUMN_LNG)));
            String dateString = cursor.getString(cursor.getColumnIndex((PositionTable.COLUMN_DATE)));
            Date date = null;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            positionList.add(new Position(id,lat,lng,date));
        }

        return positionList;
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
    public List<Activity> getUserActivities(int userId) {
        return null;
    }
}
