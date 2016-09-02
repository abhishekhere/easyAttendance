package com.labs.abhishek.easyattendance.addMembers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.dbConnection.ClassMembersTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.TheStaticValuesClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anand on 1/9/16.
 */
public class AddMembers extends AppCompatActivity {

    EditText etRoll, etFirstName, etMiddleName, etLastName;
    Button bAddMore, bDone;
    Map<Integer, String> membersList;
    Map<Integer, String[]> membersListToTransport;
    boolean addDuplicate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_members);

        membersList = new HashMap<Integer, String>();
        membersListToTransport = new HashMap<Integer, String[]>();
        etRoll = (EditText) findViewById(R.id.etRollNumber);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etMiddleName = (EditText) findViewById(R.id.etMiddleName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        bAddMore = (Button) findViewById(R.id.bAddMore);
        bDone = (Button) findViewById(R.id.bDone);

        bAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMemberToMap();
            }
        });
        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertListIntoDB();
            }
        });
    }

    private void addMemberToMap() {
        Integer rollNumber = 0;
        String[] name = new String[3];
        String full_name = "//";
        if (etRoll != null && etRoll.getText() != null) {
            rollNumber = Integer.valueOf(etRoll.getText().toString());
        }
        if (etFirstName != null && etFirstName.getText() != null) {
            name[0] = etFirstName.getText().toString();
        }
        if (etMiddleName != null && etMiddleName.getText() != null) {
            name[1] = etMiddleName.getText().toString();
        }
        if (etLastName != null && etLastName.getText() != null) {
            name[2] = etLastName.getText().toString();
        }
        full_name = name[0] + "/" + name[1] + "/" + name[2];

        if (!(membersList.containsValue(full_name))) {
            membersList.put(rollNumber, full_name);
            membersListToTransport.put(rollNumber, name);
            Toast.makeText(AddMembers.this, "Added", Toast.LENGTH_LONG).show();
            clearET();
        } else {
            boolean response = showAlertDialog();
            if (response) {
                membersList.put(rollNumber, full_name);
                membersListToTransport.put(rollNumber, name);
                Toast.makeText(AddMembers.this, "Added", Toast.LENGTH_LONG).show();
            }
            clearET();
        }
    }

    private void clearET() {
        etRoll.setText("");
        etFirstName.setText("");
        etMiddleName.setText("");
        etLastName.setText("");
    }

    private boolean showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("You have already added this name");

        alertDialogBuilder.setPositiveButton("I Know", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                addDuplicate = true;
            }
        });

        alertDialogBuilder.setNegativeButton("Oops", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddMembers.this, "Didn't Add", Toast.LENGTH_LONG).show();
                addDuplicate = false;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return addDuplicate;
    }

    private void insertListIntoDB() {
        String currentClassName = "";
        Bundle extras = getIntent().getExtras();
        if (membersListToTransport != null && extras != null) {
            currentClassName = extras.getString("currentClassName");
            new TheStaticValuesClass(currentClassName);
            boolean result = new ClassMembersTableDBHelper(this).insertMembers(membersListToTransport);
            if (result) {
                Toast.makeText(AddMembers.this, "Successfully Recorded", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(AddMembers.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }
}
