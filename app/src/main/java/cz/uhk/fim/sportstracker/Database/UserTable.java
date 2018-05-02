package cz.uhk.fim.sportstracker.Database;

import android.provider.BaseColumns;

public class UserTable implements BaseColumns {
    public  static final String TABLE_NAME = "user";
    public  static final String COLUMN_LOGIN= "login";
    public  static final String COLUMN_PASSWORD = "password";
    public  static final String COLUMN_WEIGHT = "weight";
    public  static final String COLUMN_HEIGHT = "height";
    public  static final String COLUMN_BORN = "born";
    public  static final String COLUMN_GENDER = "gender";

    public static final String SQL_QUERY_CREATE =
            "CREATE TABLE " + TABLE_NAME
                    +"("
                    + _ID + "INTEGER PRIMARY KEY, "
                    + COLUMN_LOGIN + " TEXT, "
                    + COLUMN_PASSWORD + " TEXT, "
                    + COLUMN_WEIGHT + " REAL, "
                    + COLUMN_HEIGHT + " REAL, "
                    + COLUMN_BORN + " DATE, "
                    + COLUMN_GENDER + " TEXT"
                    +")";

    public static final String SQL_QUERY_DELETE =
            "DELETE TABLE IF EXISTS " + TABLE_NAME;

}
