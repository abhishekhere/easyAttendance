package com.labs.abhishek.easyattendance.fetchClassList;

import android.app.Activity;

import com.labs.abhishek.easyattendance.dbConnection.ClassTableDBHelper;

import java.util.List;

/**
 * Created by anand on 30/8/16.
 */
public class FetchClassList {
    List<String> classList;

    public List<String> fetchAllTheClasses(ClassTableDBHelper classTableDBHelper){

        classList = (List<String>) classTableDBHelper.getAllClasses();
        return classList;
    }

}
