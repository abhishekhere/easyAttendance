package com.labs.abhishek.easyattendance.fetchClassList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.dateHelper.GetTodaysDate;
import com.labs.abhishek.easyattendance.dbConnection.ClassAttendanceTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.ClassTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.TheStaticValuesClass;
import com.labs.abhishek.easyattendance.fetchMembersList.MembersList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand on 30/8/16.
 */
public class ClassList extends Activity {
    List<String> classList = new ArrayList<String>();
    ListView listView;
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list);

        ClassTableDBHelper classTableDBHelper = new ClassTableDBHelper(this);
        classList = new FetchClassList().fetchAllTheClasses(classTableDBHelper);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, (String[]) classList.toArray(new String[classList.size()]));
        listView = (ListView) findViewById(R.id.lvClassList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                className = (String) listView.getItemAtPosition(position);
                parent.getChildAt(position).setBackgroundColor(Color.parseColor("#ccccff"));
                postClickActivity();
            }
        });
    }

    private void moveToMembersListPage() {
        new TheStaticValuesClass(className);
        final Intent membersIntent = new Intent(this, MembersList.class);
        membersIntent.putExtra("CLASS_NAME", className);
        if (className != null && className != "") {
            new TheStaticValuesClass(className);
        }
        Thread threadGoToMembersList = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(membersIntent);
            }
        });
        threadGoToMembersList.start();
    }

    private void postClickActivity() {
        new TheStaticValuesClass(className);
        ClassAttendanceTableDBHelper classAttendanceTableDBHelper = new ClassAttendanceTableDBHelper(this);
        String date = new GetTodaysDate().getTodayDate();
        boolean isRecordPresent = classAttendanceTableDBHelper.isRecordPresentForDate(date);
        try {
            if (isRecordPresent) {
                showAlertDialog();
            } else {
                moveToMembersListPage();
            }
        } catch (NullPointerException e) {
            moveToMembersListPage();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Attendance recorded for the day");

        alertDialogBuilder.setPositiveButton("Browse Members", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                moveToMembersListPage();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeOptionsMenu();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
