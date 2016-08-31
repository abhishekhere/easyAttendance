package com.labs.abhishek.easyattendance.fetchClassList;

import com.labs.abhishek.easyattendance.dbConnection.ClassTableDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand on 30/8/16.
 */
public class FetchClassList {
    List<String> classesList = new ArrayList<String>();

    public List<String> fetchAllTheClasses(ClassTableDBHelper classTableDBHelper){

        classesList = (List<String>) classTableDBHelper.getAllClasses();
        return classesList;
    }

}
