package cz.uhk.fim.sportstracker.Database;

import android.provider.BaseColumns;

public class ActivityTable implements BaseColumns {
    public  static final String TABLE_NAME = "activities";
    public  static final String COLUMN_DATE = "date";
    public  static final String COLUMN_USER_ID = "userid";
    public  static final String USER_REFERENCE = "users";

    public static final String SQL_QUERY_CREATE =
            "CREATE TABLE " + TABLE_NAME
                    +"("
                    + _ID + "INTEGER PRIMARY KEY, "
                    + COLUMN_DATE + " DATE, "
                    + COLUMN_USER_ID + " INTEGER, "
                    + "FOREIGN KEY ("+ COLUMN_USER_ID +") REFERENCES " + USER_REFERENCE + "(id)"
                    +")";

    public static final String SQL_QUERY_DELETE =
            "DELETE TABLE IF EXISTS " + TABLE_NAME;
}
