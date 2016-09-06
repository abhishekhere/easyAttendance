package com.labs.abhishek.easyattendance.todaysStat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.customization.CustomizationStaticValuesClass;
import com.labs.abhishek.easyattendance.customization.CustomizeArrayAdapterTodaysAttendanceMembers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anand on 5/9/16.
 */
public class TodaysStatMembers extends AppCompatActivity implements Serializable {

    ListView lvStatsMembersList;
    List<String> membersNameList;
    HashMap<String, String> attendanceRecordMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todays_stat_members);
        lvStatsMembersList = (ListView) findViewById(R.id.lvStatsMembersList);
        membersNameList = new ArrayList<String>();
        attendanceRecordMap = new HashMap<String, String>();

        Intent intent = getIntent();
        attendanceRecordMap = (HashMap<String, String>) intent.getSerializableExtra("membersAttendanceRecord");
        new CustomizationStaticValuesClass().membersNameVsAttendanceRecordMap = attendanceRecordMap;
        for (Map.Entry entry : attendanceRecordMap.entrySet()) {
            membersNameList.add(entry.getKey().toString());
        }
        Collections.sort(membersNameList);
        ArrayAdapter adapter = new CustomizeArrayAdapterTodaysAttendanceMembers(this, R.layout.activity_listview, (String[]) membersNameList.toArray(new String[membersNameList.size()]));
        //new ArrayAdapter<String>(this, R.layout.activity_listview, (String[]) classList.toArray(new String[classList.size()]));
        lvStatsMembersList.setAdapter(adapter);

    }
}