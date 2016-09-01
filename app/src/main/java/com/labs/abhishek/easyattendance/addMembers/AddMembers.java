package com.labs.abhishek.easyattendance.addMembers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.labs.abhishek.easyattendance.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anand on 1/9/16.
 */
public class AddMembers extends AppCompatActivity {

    EditText etRoll, etFirstName, etMiddleName, etLastName;
    Button bAddMore, bDone;
    Map<Integer, String[]> membersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_members);

        membersList = new HashMap<Integer, String[]>();
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
    }

    private void addMemberToMap() {
        Integer rollNumber = 0;
        String[] name = new String[3];
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
        if (!(membersList.containsValue(name))) {
            membersList.put(rollNumber, name);
        } else {
            //TODO Alert Dialog
        }
    }
}
