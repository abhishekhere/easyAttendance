package com.labs.abhishek.easyattendance.fetchMembersList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.dbConnection.ClassMembersTableDBHelper;
import com.labs.abhishek.easyattendance.fetchClassList.ClassList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand on 3/9/16.
 */
public class MembersList extends Activity {
    List<String> membersList = new ArrayList<String>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members_list);

        ClassMembersTableDBHelper classMembersTableDBHelper = new ClassMembersTableDBHelper(this);
        membersList = classMembersTableDBHelper.getAllMembers();
        if (membersList == null || membersList.isEmpty()) {
            showAlertDialog();
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, (String[]) membersList.toArray(new String[membersList.size()]));
        ListView listView = (ListView) findViewById(R.id.lvMembersList);
        listView.setAdapter(adapter);
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Looks like this class is empty");

        alertDialogBuilder.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                goToClassListPage();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void goToClassListPage() {
        intent = new Intent(this, ClassList.class);
        Thread goToClassListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        goToClassListThread.start();
        finish();
    }
}
