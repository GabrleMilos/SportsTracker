package cz.uhk.fim.sportstracker.Database;

import android.provider.BaseColumns;

public class PositionTable implements BaseColumns {
    public  static final String TABLE_NAME = "positions";
    public  static final String COLUMN_DATE = "date";
    public  static final String COLUMN_LAT = "lat";
    public  static final String COLUMN_LNG = "lng";
    public  static final String COLUMN_ACTIVITY_ID = "activityid";
    public  static final String ACTIVITY_REFERENCE = "activity";



    public static final String SQL_QUERY_CREATE =
            "CREATE TABLE " + TABLE_NAME
                    +"("
                    + _ID + "INTEGER PRIMARY KEY, "
                    + COLUMN_DATE + " DATE, "
                    + COLUMN_LAT + " REAL, "
                    + COLUMN_LNG + " REAL"
                    + "FOREIGN KEY (+"+ COLUMN_ACTIVITY_ID +") REFERENCES " + ACTIVITY_REFERENCE + "(id)"
                    +")";

    public static final String SQL_QUERY_DELETE =
            "DELETE TABLE IF EXISTS " + TABLE_NAME;


}
