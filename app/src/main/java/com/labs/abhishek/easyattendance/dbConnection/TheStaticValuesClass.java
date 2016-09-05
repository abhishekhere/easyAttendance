package com.labs.abhishek.easyattendance.dbConnection;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by anand on 3/9/16.
 */
public class TheStaticValuesClass {

    public static String CLASS_NAME;

    public static String MEMBERS_DB_NAME;
    public static String MEMBERS_TABLE_NAME;
    public static Map<Integer, Integer> membersColumn;
    public static ArrayList<String> membersList;

    public static String ATTENDANCE_DB_NAME;
    public static String ATTENDANCE_TABLE_NAME;

    public TheStaticValuesClass(String className) {
        CLASS_NAME = className;
        updateOtherMemberVariables();
        updateOtherAttendanceVariables();
    }

    public TheStaticValuesClass() {

    }

    private void updateOtherMemberVariables() {
        MEMBERS_DB_NAME = CLASS_NAME + "Members.db";
        MEMBERS_TABLE_NAME = CLASS_NAME + "Members";
    }

    private void updateOtherAttendanceVariables() {
        ATTENDANCE_DB_NAME = CLASS_NAME + "Attendance.db";
        ATTENDANCE_TABLE_NAME = CLASS_NAME + "Attendance";
    }
}
