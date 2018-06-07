package cz.uhk.fim.sportstracker.Database;

import android.content.ContentValues;
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
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(UserTable.SQL_QUERY_DELETE);
        sqLiteDatabase.execSQL(ActivityTable.SQL_QUERY_DELETE);
        sqLiteDatabase.execSQL(PositionTable.SQL_QUERY_DELETE);

        sqLiteDatabase.execSQL(UserTable.SQL_QUERY_CREATE);
        sqLiteDatabase.execSQL(ActivityTable.SQL_QUERY_CREATE);
        sqLiteDatabase.execSQL(PositionTable.SQL_QUERY_CREATE);
    }


    public List<User> getAllUsers(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(UserTable.TABLE_NAME, null, null, null, null, null, null);
        List<User> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(UserTable.COLUMN_ID));
            String login2 = cursor.getString(cursor.getColumnIndex((UserTable.COLUMN_LOGIN)));
            String password = cursor.getString(cursor.getColumnIndex((UserTable.COLUMN_PASSWORD)));
            double weight = cursor.getDouble(cursor.getColumnIndex((UserTable.COLUMN_WEIGHT)));
            double height = cursor.getDouble(cursor.getColumnIndex((UserTable.COLUMN_HEIGHT)));
            String gender = cursor.getString(cursor.getColumnIndex((UserTable.COLUMN_GENDER)));

            String dateString = cursor.getString(cursor.getColumnIndex((UserTable.COLUMN_BORN)));
            Date date = null;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            list.add(new User(id,login2,password,weight,height,date,gender));
        }
        return  list;
    }

    @Override
    public Activity getActivity(int activityId) {
        SQLiteDatabase database = getReadableDatabase();
        String [] projection = {ActivityTable._ID ,ActivityTable.COLUMN_USER_ID,ActivityTable.COLUMN_DATE};
        String selection = ActivityTable._ID + " = ?";
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
    public boolean insertActivity(Activity activity, int userId) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues activityValues = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(activity.getDate());
        activityValues.put(ActivityTable.COLUMN_DATE, date);
        activityValues.put(ActivityTable.COLUMN_USER_ID, userId);

        long id = database.insert(ActivityTable.TABLE_NAME,null,activityValues);

        for (Position p: activity.getPositionList()) {
            insertPosition(p, id);
        }

        return id > 0;
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
        String [] projection = null;
        String selection = PositionTable.COLUMN_ACTIVITY_ID + " = ?";
        String [] selectionArgs = {String.valueOf(activityId)};

        Cursor cursor = database.query(PositionTable.TABLE_NAME, projection, selection, selectionArgs, null, null, PositionTable.COLUMN_DATE);
        List<Position> positionList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex((PositionTable.COLUMN_ID)));
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
    public boolean insertPosition(Position position, long activityId) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues positionValues = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(position.getDate());

        positionValues.put(PositionTable.COLUMN_ACTIVITY_ID, activityId);
        positionValues.put(PositionTable.COLUMN_DATE,date);
        positionValues.put(PositionTable.COLUMN_LAT, position.getLat());
        positionValues.put(PositionTable.COLUMN_LNG, position.getLng());

        long id = database.insert(PositionTable.TABLE_NAME,null,positionValues);

        return id > 0;
    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public User getUser(String login) {
        SQLiteDatabase database = getReadableDatabase();
        String selection = UserTable.COLUMN_LOGIN + " LIKE ?";
        String [] selectionArgs = {login};

        Cursor cursor = database.query(UserTable.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex((UserTable._ID)));
            String login2 = cursor.getString(cursor.getColumnIndex((UserTable.COLUMN_LOGIN)));
            String password = cursor.getString(cursor.getColumnIndex((UserTable.COLUMN_PASSWORD)));
            double weight = cursor.getDouble(cursor.getColumnIndex((UserTable.COLUMN_WEIGHT)));
            double height = cursor.getDouble(cursor.getColumnIndex((UserTable.COLUMN_HEIGHT)));
            String gender = cursor.getString(cursor.getColumnIndex((UserTable.COLUMN_GENDER)));

            String dateString = cursor.getString(cursor.getColumnIndex((UserTable.COLUMN_BORN)));
            Date date = null;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new User(1,login,password,weight,height,date,gender);
        }

        return  null;
    }

    @Override
    public boolean insertUser(User user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues userValues = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(user.getBorn());

        userValues.put(UserTable.COLUMN_BORN, date);
        userValues.put(UserTable.COLUMN_GENDER, user.getGender());
        userValues.put(UserTable.COLUMN_HEIGHT, user.getHeight());
        userValues.put(UserTable.COLUMN_LOGIN, user.getLogin());
        userValues.put(UserTable.COLUMN_PASSWORD, user.getPassword());
        userValues.put(UserTable.COLUMN_WEIGHT, user.getWeight());


        long id = database.insert(UserTable.TABLE_NAME,null,userValues);

        return id > 0;
    }

    @Override
    public List<Activity> getUserActivities(int userId) {
        SQLiteDatabase database = getReadableDatabase();
        String [] projection = null;
        String selection = ActivityTable.COLUMN_USER_ID + " = ?";
        String [] selectionArgs = {String.valueOf(userId)};

//        Cursor cursor = database.query(ActivityTable.TABLE_NAME, projection, selection, selectionArgs, null, null, ActivityTable.COLUMN_DATE);
        Cursor cursor = database.rawQuery("SELECT * FROM " + ActivityTable.TABLE_NAME,null);
        List<Activity> activityList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex((ActivityTable.COLUMN_ID)));

            String dateString = cursor.getString(cursor.getColumnIndex((ActivityTable.COLUMN_DATE)));
            Date date = null;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            List<Position> positionList = getActivityPositions(1);


            activityList.add(new Activity(1,positionList, date));
        }


        return activityList;
    }
}
