package com.labs.abhishek.easyattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.labs.abhishek.easyattendance.createClasses.CreateAClass;
import com.labs.abhishek.easyattendance.fetchClassList.ClassList;
import com.labs.abhishek.easyattendance.removeClass.RemoveClass;
import com.labs.abhishek.easyattendance.statsHomePage.StatsHomePage;

/**
 * Created by anand on 30/8/16.
 */
public class NavigationPage extends AppCompatActivity {

    Button browseClasses, stats, createClasses, bRemoveAClass;
    Intent intentCreateClass, intentBrowseClasses, intentRemoveClass, intentStatsHomePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_page);
        browseClasses = (Button)findViewById(R.id.bBrowseClasses);
        stats = (Button)findViewById(R.id.bStats);
        createClasses = (Button)findViewById(R.id.bGoToCreateClass);
        bRemoveAClass = (Button) findViewById(R.id.bRemoveAClass);


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

        bRemoveAClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRemoveClass();
            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToStatsHomePage();
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

    private void goToRemoveClass() {
        intentRemoveClass = new Intent(this, RemoveClass.class);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(intentRemoveClass);
            }
        });
        thread.start();
    }

    private void goToStatsHomePage() {
        intentStatsHomePage = new Intent(this, StatsHomePage.class);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(intentStatsHomePage);
            }
        });
        thread.start();
    }
}
