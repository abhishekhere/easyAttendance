package com.labs.abhishek.easyattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;

import com.labs.abhishek.easyattendance.createClasses.CreateAClass;
import com.labs.abhishek.easyattendance.fetchClassList.ClassList;

/**
 * Created by anand on 30/8/16.
 */
public class NavigationPage extends AppCompatActivity {

    Button browseClasses,stats,createClasses;
    Intent intentCreateClass,intentBrowseClasses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_page);
        browseClasses = (Button)findViewById(R.id.bBrowseClasses);
        stats = (Button)findViewById(R.id.bStats);
        createClasses = (Button)findViewById(R.id.bGoToCreateClass);

        createClasses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                goToCreateClass();
            }
        });

        browseClasses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                goToBrowseClass();
            }
        });
    }

    private void goToCreateClass(){
        intentCreateClass = new Intent(this, CreateAClass.class);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(intentCreateClass);
            }
        });
        thread.start();
    }

    private void goToBrowseClass(){
        intentBrowseClasses = new Intent(this, ClassList.class);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(intentBrowseClasses);
            }
        });
        thread.start();
    }

}
