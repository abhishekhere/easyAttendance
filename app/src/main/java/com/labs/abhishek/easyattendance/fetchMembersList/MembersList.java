package com.labs.abhishek.easyattendance.fetchMembersList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.dbConnection.ClassAttendanceTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.ClassMembersTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.TheStaticValuesClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anand on 3/9/16.
 */
public class MembersList extends Activity {
    List<String> membersList = new ArrayList<String>();
    Map<Integer, Integer> attendanceRegister = new HashMap<Integer, Integer>();
    ClassMembersTableDBHelper classMembersTableDBHelper;
    ClassAttendanceTableDBHelper classAttendanceTableDBHelper;
    ListView membersListView;
    Button bSubmitAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members_list);

        membersListView = (ListView) findViewById(R.id.lvMembersList);
        bSubmitAttendance = (Button) findViewById(R.id.bSubmitAttendance);

        classAttendanceTableDBHelper = new ClassAttendanceTableDBHelper(this);
        classMembersTableDBHelper = new ClassMembersTableDBHelper(this);
        membersList = classMembersTableDBHelper.getAllMembers();
        if (membersList == null || membersList.isEmpty()) {
            showAlertDialog();
        } else {
            mapMembersListToAttendanceRegister();
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, (String[]) membersList.toArray(new String[membersList.size()]));
        ListView listView = (ListView) findViewById(R.id.lvMembersList);
        listView.setAdapter(adapter);

        membersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedMember = (String) membersListView.getItemAtPosition(position);
                updateAttendanceRegister(selectedMember, parent, position);
            }
        });

        bSubmitAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean attendanceFlag = classAttendanceTableDBHelper.takeAttendance(attendanceRegister);
                if (attendanceFlag) {
                    Toast.makeText(MembersList.this, "Attendance taken", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MembersList.this, "Attendance record exists for today", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void mapMembersListToAttendanceRegister() {
        String roll;
        for (String currentMember : membersList) {
            String[] takeRoll = currentMember.split("\\:");
            roll = takeRoll[0].trim();
            attendanceRegister.put(Integer.valueOf(roll), 1);
        }
        new TheStaticValuesClass().membersColumn = attendanceRegister;
    }

    private void updateAttendanceRegister(String selectedMember, AdapterView<?> parent, int position) {
        String[] takeRoll = selectedMember.split("\\:");
        String roll = takeRoll[0].trim();
        if (attendanceRegister.get(Integer.valueOf(roll)) == 1) {
            attendanceRegister.put(Integer.valueOf(roll), 0);
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#ffb3b3"));
        } else if (attendanceRegister.get(Integer.valueOf(roll)) == 0) {
            attendanceRegister.put(Integer.valueOf(roll), 1);
            parent.getChildAt(position).setBackgroundColor(Color.parseColor("#ccffcc"));
        } else {

        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Looks like this class is empty");

        alertDialogBuilder.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
