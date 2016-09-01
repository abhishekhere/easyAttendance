package com.labs.abhishek.easyattendance.dbConnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by anand on 30/8/16.
 */
public class ClassTableDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ClassesList.db";
    public static final String CLASSES_TABLE_NAME = "classes";
    public static final String CLASSES_COLUMN_ID = "id";
    public static final String CLASSES_COLUMN_CLASS_NAME = "className";

    public ClassTableDBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String query = "create table " + CLASSES_TABLE_NAME + " ("+CLASSES_COLUMN_ID+" integer primary key, "+CLASSES_COLUMN_CLASS_NAME+" text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+CLASSES_TABLE_NAME);
        onCreate(db);
    }


    public boolean insertClass(String className)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLASSES_COLUMN_CLASS_NAME, className);
        db.insert(CLASSES_TABLE_NAME, null, contentValues);
        return true;
    }

    public void removeClass(String className) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + CLASSES_TABLE_NAME + " where " + CLASSES_COLUMN_CLASS_NAME + "='" + className + "'");
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CLASSES_TABLE_NAME+" where id="+id+"", null );
        return res;
    }

    public ArrayList<String> getAllClasses()
    {
        ArrayList<String> class_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CLASSES_TABLE_NAME, null );
        res.moveToFirst();
        String value;
        while(res.isAfterLast() == false){
            value = res.getString(res.getColumnIndex(CLASSES_COLUMN_CLASS_NAME)).toString();
            class_list.add(value);
            res.moveToNext();
        }
        return class_list;
    }

    public void truncateTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + CLASSES_TABLE_NAME);
    }
}
