package com.labs.abhishek.easyattendance.createClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.labs.abhishek.easyattendance.NavigationPage;
import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.addMembers.AddMembers;
import com.labs.abhishek.easyattendance.dbConnection.ClassTableDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand on 30/8/16.
 */
public class CreateAClass extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button bcreateAClass, bGoToAddMembersInstead, bAddMembers, bBackToDashboard;
    EditText etClassName;
    TextView tvCeilingMessage;
    Spinner spinnerClassMenu;
    String className;
    ClassTableDBHelper classTableDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_class);
        tvCeilingMessage = (TextView) findViewById(R.id.tvCeilingMessage);
        bcreateAClass = (Button) findViewById(R.id.bAddClass);
        bGoToAddMembersInstead = (Button) findViewById(R.id.bGoToAddMembersInstead);
        bAddMembers = (Button) findViewById(R.id.bAddMembers);
        bBackToDashboard = (Button) findViewById(R.id.bBackToDashboard);
        etClassName = (EditText) findViewById(R.id.etClassToBeAdded);
        spinnerClassMenu = (Spinner) findViewById(R.id.spinnerClassMenu);
        classTableDBHelper = new ClassTableDBHelper(this);

        bcreateAClass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                insertClassIntoDB();
            }
        });

        bBackToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashboard();
            }
        });

        bGoToAddMembersInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postSuccessfulAdditionOfClass();
            }
        });

        bAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddMembers();
            }
        });

        spinnerClassMenu.setOnItemSelectedListener(this);
    }

    private void insertClassIntoDB() {
        Boolean result = false;
        try {
            className = etClassName.getText().toString();
            result = classTableDBHelper.insertClass(className);
        }
        catch (Exception e){
            Log.e("## Error ","while inserting the class");
        }
        if(result){
            Log.i("## Success"," in inserting class");
            postSuccessfulAdditionOfClass();
        } else {
            bcreateAClass.setText("Something went wrong : Try Again");
        }
    }

    private void goToDashboard() {
        final Intent intentDashBoard = new Intent(this, NavigationPage.class);
        Thread threadGoToDashboard = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(intentDashBoard);
            }
        });
        threadGoToDashboard.start();
    }

    private void goToAddMembers() {
        final Intent intentAddMembers = new Intent(this, AddMembers.class);
        intentAddMembers.putExtra("currentClassName", className);
        Thread threadGoToAddMembers = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(intentAddMembers);
            }
        });
        threadGoToAddMembers.start();
    }

    private void postSuccessfulAdditionOfClass() {
        List<String> classes = new ArrayList<String>();
        classes = (List<String>) classTableDBHelper.getAllClasses();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classes);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerClassMenu.setAdapter(dataAdapter);
        if (className != null && !className.equals("")) {
            int defaultClassPosition = dataAdapter.getPosition(className);
            spinnerClassMenu.setSelection(defaultClassPosition);
            tvCeilingMessage.setText("Add members to " + className);
        } else {
            tvCeilingMessage.setText("Add members here");
        }
        //spinnerClassMenu.set
        spinnerClassMenu.setVisibility(View.VISIBLE);
        bAddMembers.setVisibility(View.VISIBLE);
        bBackToDashboard.setVisibility(View.VISIBLE);
        etClassName.setVisibility(View.INVISIBLE);
        bcreateAClass.setVisibility(View.INVISIBLE);
        bGoToAddMembersInstead.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        className = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
