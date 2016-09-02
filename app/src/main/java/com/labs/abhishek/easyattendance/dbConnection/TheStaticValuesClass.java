package com.labs.abhishek.easyattendance.dbConnection;

/**
 * Created by anand on 3/9/16.
 */
public class TheStaticValuesClass {

    public static String CLASS_NAME;
    public static String MEMBERS_DB_NAME;
    public static String MEMBERS_TABLE_NAME;

    public TheStaticValuesClass(String className) {
        CLASS_NAME = className;
        updateOtherMemberVariables();
    }

    public TheStaticValuesClass() {

    }

    private void updateOtherMemberVariables() {
        MEMBERS_DB_NAME = CLASS_NAME + "Members.db";
        MEMBERS_TABLE_NAME = CLASS_NAME + "Members";
    }

}
