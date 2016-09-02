package com.labs.abhishek.easyattendance.dbConnection;

/**
 * Created by anand on 2/9/16.
 */
public class ClassAttendanceTableDBHelper {//} extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ClassAttendance.db";
    public static final String TABLE_NAME = "Attendance";
    public static final String PRIMERY_COLUMN = "Date";
/*
    public ClassAttendanceTableDBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + CLASSES_TABLE_NAME + " ("+CLASSES_COLUMN_ID+" integer primary key, "+CLASSES_COLUMN_CLASS_NAME+" text)";
        db.execSQL(query);
    }*/
}
