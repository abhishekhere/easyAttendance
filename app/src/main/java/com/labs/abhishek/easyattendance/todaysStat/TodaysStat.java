package com.labs.abhishek.easyattendance.todaysStat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.customization.CustomizeArrayAdapter;
import com.labs.abhishek.easyattendance.dateHelper.GetTodaysDate;
import com.labs.abhishek.easyattendance.dbConnection.ClassAttendanceTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.ClassMembersTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.ClassTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.TheStaticValuesClass;
import com.labs.abhishek.easyattendance.dtoClasses.TodaysAttendanceStatDTO;
import com.labs.abhishek.easyattendance.fetchClassList.FetchClassList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anand on 4/9/16.
 */
public class TodaysStat extends AppCompatActivity {

    ListView lvStatsClassList;
    HashMap<String, String> classVsClassWithAttendance;
    Map<String, Map<String, String>> classVsMembersVsAttendanceStatusMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todays_stat);

        ClassTableDBHelper classTableDBHelper = new ClassTableDBHelper(this);
        List<String> classList = new FetchClassList().fetchAllTheClasses(classTableDBHelper);
        List<String> updatedClassList = updateClassListWithAttendanceRecord(classList);
        ArrayAdapter adapter = new CustomizeArrayAdapter(this, R.layout.activity_listview, (String[]) updatedClassList.toArray(new String[updatedClassList.size()]));
        lvStatsClassList = (ListView) findViewById(R.id.lvStatsClassList);
        lvStatsClassList.setAdapter(adapter);

        lvStatsClassList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String className = (String) lvStatsClassList.getItemAtPosition(position);
                postClassSelection(className);
            }
        });
    }

    private List<String> updateClassListWithAttendanceRecord(List<String> classList) {
        classVsClassWithAttendance = new HashMap<String, String>();
        classVsMembersVsAttendanceStatusMap = new HashMap<String, Map<String, String>>();
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
            String attendanceResult = "Something went wrong";
            TodaysAttendanceStatDTO todaysAttendanceStatDTOObj = new TodaysAttendanceStatDTO();
            todaysAttendanceStatDTOObj = classAttendanceTableDBHelper.getPresentPercentStat(todaysDate);
            if (todaysAttendanceStatDTOObj != null) {
                attendanceResult = todaysAttendanceStatDTOObj.getResponse();
                classVsMembersVsAttendanceStatusMap.put(className, todaysAttendanceStatDTOObj.getMembersNameVsAttendanceValueMap());
            }
            String updatedClassName = className + " (" + attendanceResult + ")";
            updatedClassList.add(updatedClassName);
            classVsClassWithAttendance.put(className, updatedClassName);
            classAttendanceTableDBHelper = null;
        }
        return updatedClassList;
    }

    public void postClassSelection(String className) {
        String[] classNameSplittedArray = new String[2];
        classNameSplittedArray = className.split("\\(");
        String actualClassName = classNameSplittedArray[0].trim();
        final Intent intentMembersAttendanceStat = new Intent(this, TodaysStatMembers.class);
        if (classVsMembersVsAttendanceStatusMap.get(actualClassName).size() > 0) {
            intentMembersAttendanceStat.putExtra("membersAttendanceRecord", (Serializable) classVsMembersVsAttendanceStatusMap.get(actualClassName));
            Thread threadShowMemberWiseAttendance = new Thread(new Runnable() {
                @Override
                public void run() {
                    startActivity(intentMembersAttendanceStat);
                }
            });
            threadShowMemberWiseAttendance.start();
        }
    }

}
