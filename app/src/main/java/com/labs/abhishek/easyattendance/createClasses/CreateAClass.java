package com.labs.abhishek.easyattendance.createClasses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.dbConnection.ClassTableDBHelper;

/**
 * Created by anand on 30/8/16.
 */
public class CreateAClass extends AppCompatActivity {

    Button bcreateAClass;
    EditText etClassName;
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_class);
        bcreateAClass = (Button) findViewById(R.id.bAddClass);
        etClassName = (EditText) findViewById(R.id.etClassToBeAdded);

        className = etClassName.getText().toString();
        bcreateAClass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                insertClassIntoDB(className);
            }
        });

    }

    private void insertClassIntoDB(String className){
        ClassTableDBHelper classTableDBHelper = new ClassTableDBHelper(this);
        Boolean result = false;
        try {
            result = classTableDBHelper.insertClass(className);
        }
        catch (Exception e){
            Log.e("## Error ","while inserting the class");
        }
        if(result){
            Log.i("## Success"," in inserting class");
        }
    }
}
