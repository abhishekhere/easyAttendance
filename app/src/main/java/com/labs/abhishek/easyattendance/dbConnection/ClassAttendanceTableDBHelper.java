package com.labs.abhishek.easyattendance.dbConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.labs.abhishek.easyattendance.dateHelper.GetTodaysDate;
import com.labs.abhishek.easyattendance.dtoClasses.TodaysAttendanceStatDTO;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by anand on 2/9/16.
 */
public class ClassAttendanceTableDBHelper extends SQLiteOpenHelper {

    TheStaticValuesClass theStaticValuesClass;
    public static String DATABASE_NAME = "";
    public static String TABLE_NAME = "";
    private static final String DATE_COLUMN = "date";
    private static final String PRIMERY_COLUMN = "DaysCount";

    public ClassAttendanceTableDBHelper(Context context) {
        super(context, new TheStaticValuesClass().ATTENDANCE_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        theStaticValuesClass = new TheStaticValuesClass();
        TABLE_NAME = theStaticValuesClass.ATTENDANCE_TABLE_NAME;
        Map<Integer, Integer> membersRollColumn = theStaticValuesClass.membersColumn;

        String query = "create table " + TABLE_NAME + " (" + PRIMERY_COLUMN + " integer primary key," + DATE_COLUMN + " text";
        if (membersRollColumn != null) {
            for (Map.Entry<Integer, Integer> rollList : membersRollColumn.entrySet()) {
                String roll = String.valueOf(rollList.getKey());
                query = query + ",Roll" + roll + " text";
            }
            query = query + ")";
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean takeAttendance(Map<Integer, Integer> attendanceListMap) {
        theStaticValuesClass = new TheStaticValuesClass();
        TABLE_NAME = theStaticValuesClass.ATTENDANCE_TABLE_NAME;
        String date = new GetTodaysDate().getTodayDate();
        if (isRecordPresentForDate(date)) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE_COLUMN, date);

        for (Map.Entry<Integer, Integer> attendanceList : attendanceListMap.entrySet()) {
            String rollNo = String.valueOf(attendanceList.getKey());
            String presentOrAbsent = String.valueOf(attendanceList.getValue());
            contentValues.put("Roll" + rollNo, presentOrAbsent);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        int daysCount = getDaysCount(db) + 1;
        contentValues.put(PRIMERY_COLUMN, daysCount);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean isRecordPresentForDate(String date) {
        String dateChecker;
        theStaticValuesClass = new TheStaticValuesClass();
        TABLE_NAME = theStaticValuesClass.ATTENDANCE_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT " + DATE_COLUMN + " FROM " + TABLE_NAME + " WHERE " + DATE_COLUMN + "='" + date + "'", null);
            cursor.moveToFirst();
            dateChecker = cursor.getString(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (date.equalsIgnoreCase(dateChecker)) {
            return true;
        } else {
            return false;
        }
    }

    private int getDaysCount(SQLiteDatabase db) {
        int returnValue = -1;
        try {
            Cursor cursor = db.rawQuery("SELECT MAX(" + PRIMERY_COLUMN + ") FROM " + TABLE_NAME, null);
            cursor.moveToFirst();
            Integer daysCount = cursor.getInt(0);
            if (daysCount != null) {
                returnValue = daysCount.intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnValue = -1;
        }
        return returnValue;
    }

    public String getPresentPercentStat(String date) {
        String response = "/";
        theStaticValuesClass = new TheStaticValuesClass();
        TABLE_NAME = theStaticValuesClass.ATTENDANCE_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (isRecordPresentForDate(date)) {
                TodaysAttendanceStatDTO todaysAttendanceStatDTO = getPresentVsAbsent(date);
                int[] presentVsAbsent = new int[2];
                presentVsAbsent = todaysAttendanceStatDTO.getPresentVsAbsentArray();
                response = String.valueOf(presentVsAbsent[0]) + "/" + String.valueOf(presentVsAbsent[0] + presentVsAbsent[1]);
            } else {
                response = "Attendance not yet taken";
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "Record not found";
        }
        return response;
    }

    public TodaysAttendanceStatDTO getPresentVsAbsent(String date) {
        int present = 0, absent = 0, invalidData = 0;
        TodaysAttendanceStatDTO todaysAttendanceStatDTO = new TodaysAttendanceStatDTO();
        theStaticValuesClass = new TheStaticValuesClass();
        ArrayList<String> membersList = theStaticValuesClass.membersList;
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        for (String member : membersList) {
            String[] takeRoll = member.split("\\:");
            String roll = takeRoll[0].trim();
            if (res != null) {
                String presentOrAbsent = res.getString(res.getColumnIndex("Roll" + roll)).toString();
                if (presentOrAbsent != null) {
                    todaysAttendanceStatDTO.getRollVsAttendanceValueMap().put(Integer.valueOf(roll), Integer.valueOf(presentOrAbsent));
                    if (presentOrAbsent.equalsIgnoreCase("0")) {
                        absent++;
                    } else if (presentOrAbsent.equalsIgnoreCase("1")) {
                        present++;
                    } else {
                        invalidData++;
                    }
                }
            }
        }
        todaysAttendanceStatDTO.getPresentVsAbsentArray()[0] = present;
        todaysAttendanceStatDTO.getPresentVsAbsentArray()[1] = absent;
        return todaysAttendanceStatDTO;
    }

    public boolean emptyMethod() {
        Log.i("Called empty", "method");
        return true;
    }
}
