package com.labs.abhishek.easyattendance.statsHomePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.todaysStat.TodaysStat;

/**
 * Created by anand on 4/9/16.
 */
public class StatsHomePage extends AppCompatActivity {

    Button bTodaysStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_homepage);
        bTodaysStat = (Button) findViewById(R.id.bTodayStat);

        bTodaysStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTodaysStat();
            }
        });
    }

    private void goToTodaysStat() {
        final Intent intentTodaysStat = new Intent(this, TodaysStat.class);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(intentTodaysStat);
            }
        });
        thread.start();
    }
}
