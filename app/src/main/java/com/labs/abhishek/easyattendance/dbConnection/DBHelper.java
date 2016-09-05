package com.labs.abhishek.easyattendance.dbConnection;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by anand on 4/9/16.
 */
public class DBHelper extends Activity {

    ClassMembersTableDBHelper classMembersTableDBHelper;

    ClassAttendanceTableDBHelper classAttendanceTableDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classMembersTableDBHelper = new ClassMembersTableDBHelper(this);
        classAttendanceTableDBHelper = new ClassAttendanceTableDBHelper(this);
    }

    public ClassMembersTableDBHelper getClassMembersTableDBHelperInstance() {
        return classMembersTableDBHelper;
    }

    public ClassAttendanceTableDBHelper getClassAttendanceTableDBHelperInstance() {
        return classAttendanceTableDBHelper;
    }
}
