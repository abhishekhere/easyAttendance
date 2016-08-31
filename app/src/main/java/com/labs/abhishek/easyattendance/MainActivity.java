package com.labs.abhishek.easyattendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Intent navigationPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToNavigationPage();
    }

    private void goToNavigationPage(){
        navigationPage = new Intent(this,NavigationPage.class);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(navigationPage);
            }
        });
        thread.start();
    }
}
