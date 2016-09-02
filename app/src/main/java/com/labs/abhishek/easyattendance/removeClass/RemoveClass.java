package com.labs.abhishek.easyattendance.removeClass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.dbConnection.ClassTableDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand on 1/9/16.
 */
public class RemoveClass extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerClassMenu;
    Button bRemoveClass;
    String classToRemove;
    ClassTableDBHelper classTableDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_class);
        spinnerClassMenu = (Spinner) findViewById(R.id.spinnerClassMenu);
        bRemoveClass = (Button) findViewById(R.id.bRemoveClass);
        classTableDBHelper = new ClassTableDBHelper(this);
        loadSpinnerList();
        spinnerClassMenu.setOnItemSelectedListener(this);
        bRemoveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSelectedClass(classTableDBHelper);
            }
        });
    }

    public void removeSelectedClass(ClassTableDBHelper classTableDBHelper) {
        classTableDBHelper.removeClass(classToRemove);
        Toast.makeText(RemoveClass.this, "Successfully removed: " + classToRemove, Toast.LENGTH_LONG).show();
        loadSpinnerList();
    }

    public void loadSpinnerList() {
        List<String> classes = new ArrayList<String>();
        classes = (List<String>) classTableDBHelper.getAllClasses();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classes);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerClassMenu.setAdapter(dataAdapter);
        spinnerClassMenu.setSelection(1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        classToRemove = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}