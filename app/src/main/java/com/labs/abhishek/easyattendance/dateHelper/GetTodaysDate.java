package com.labs.abhishek.easyattendance.dateHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by anand on 4/9/16.
 */
public class GetTodaysDate {

    public String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todaysDate = dateFormat.format(calendar.getTime());
        return todaysDate;
    }
}
