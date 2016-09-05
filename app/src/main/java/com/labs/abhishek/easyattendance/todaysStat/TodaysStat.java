package com.labs.abhishek.easyattendance.todaysStat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.dateHelper.GetTodaysDate;
import com.labs.abhishek.easyattendance.dbConnection.ClassAttendanceTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.ClassMembersTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.ClassTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.TheStaticValuesClass;
import com.labs.abhishek.easyattendance.fetchClassList.FetchClassList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anand on 4/9/16.
 */
public class TodaysStat extends AppCompatActivity {

    ListView lvStatsClassList;
    Map<String, String> classVsClassWithAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todays_stat);

        ClassTableDBHelper classTableDBHelper = new ClassTableDBHelper(this);
        List<String> classList = new FetchClassList().fetchAllTheClasses(classTableDBHelper);
        List<String> updatedClassList = updateClassListWithAttendanceRecord(classList);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, (String[]) updatedClassList.toArray(new String[updatedClassList.size()]));
        lvStatsClassList = (ListView) findViewById(R.id.lvStatsClassList);
        lvStatsClassList.setAdapter(adapter);
    }

    private List<String> updateClassListWithAttendanceRecord(List<String> classList) {
        classVsClassWithAttendance = new HashMap<String, String>();
        ClassAttendanceTableDBHelper classAttendanceTableDBHelper;
        ClassMembersTableDBHelper classMembersTableDBHelper;
        ArrayList<String> membersList = new ArrayList<String>();
        String todaysDate = new GetTodaysDate().getTodayDate();
        List<String> updatedClassList = new ArrayList<String>();
        for (String className : classList) {
            TheStaticValuesClass theStaticValuesClass = new TheStaticValuesClass(className);
            classMembersTableDBHelper = new ClassMembersTableDBHelper(this);
            membersList = classMembersTableDBHelper.getAllMembers();
            theStaticValuesClass.membersList = membersList;
            classAttendanceTableDBHelper = new ClassAttendanceTableDBHelper(this);
            String attendanceResult = classAttendanceTableDBHelper.getPresentPercentStat(todaysDate);
            String updatedClassName = className + " (" + attendanceResult + ")";
            updatedClassList.add(updatedClassName);
            classVsClassWithAttendance.put(className, updatedClassName);
            classAttendanceTableDBHelper = null;
        }
        return updatedClassList;
    }

}
