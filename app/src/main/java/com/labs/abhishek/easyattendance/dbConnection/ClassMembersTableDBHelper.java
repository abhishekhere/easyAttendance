package com.labs.abhishek.easyattendance.dbConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by anand on 2/9/16.
 */
public class ClassMembersTableDBHelper extends SQLiteOpenHelper {

    TheStaticValuesClass theStaticValuesClass;
    public static String DATABASE_NAME = "";
    public static String TABLE_NAME = "";
    //public static Map<Integer,String[]> membersList;
    private static final String PRIMERY_COLUMN = "Roll";
    private static final String FIRST_NAME_COLUMN = "FirstName";
    private static final String MIDDLE_NAME_COLUMN = "MiddleName";
    private static final String LAST_NAME_COLUMN = "LastName";

    public ClassMembersTableDBHelper(Context context) {
        super(context, new TheStaticValuesClass().MEMBERS_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        theStaticValuesClass = new TheStaticValuesClass();
        TABLE_NAME = theStaticValuesClass.MEMBERS_TABLE_NAME;

        String query = "create table " + TABLE_NAME + " (" + PRIMERY_COLUMN + " integer primary key, " + FIRST_NAME_COLUMN + " text," + MIDDLE_NAME_COLUMN + " text," + LAST_NAME_COLUMN + " text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertMembers(Map<Integer, String[]> membersListToTransport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        TABLE_NAME = theStaticValuesClass.MEMBERS_TABLE_NAME;
        for (Map.Entry<Integer, String[]> membersList : membersListToTransport.entrySet()) {
            String roll = String.valueOf(membersList.getKey());
            String[] nameArray = membersList.getValue();
            contentValues.put(PRIMERY_COLUMN, roll);
            contentValues.put(FIRST_NAME_COLUMN, nameArray[0]);
            contentValues.put(MIDDLE_NAME_COLUMN, nameArray[1]);
            contentValues.put(LAST_NAME_COLUMN, nameArray[2]);
            db.insert(TABLE_NAME, null, contentValues);
        }
        return true;
    }

    public ArrayList<String> getAllMembers() {
        ArrayList<String> members_list = new ArrayList<String>();
        theStaticValuesClass = new TheStaticValuesClass();
        TABLE_NAME = theStaticValuesClass.MEMBERS_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            String member, tempValue;
            tempValue = res.getString(res.getColumnIndex(PRIMERY_COLUMN)).toString();
            member = tempValue + " :";
            tempValue = res.getString(res.getColumnIndex(FIRST_NAME_COLUMN)).toString();
            member = member + " " + tempValue.trim();
            tempValue = res.getString(res.getColumnIndex(MIDDLE_NAME_COLUMN)).toString();
            member = member + " " + tempValue.trim();
            tempValue = res.getString(res.getColumnIndex(LAST_NAME_COLUMN)).toString();
            member = member + " " + tempValue.trim();
            members_list.add(member);
            res.moveToNext();
        }
        return members_list;
    }
}
