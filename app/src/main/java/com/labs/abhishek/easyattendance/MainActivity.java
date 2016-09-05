package com.labs.abhishek.easyattendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
                navigationPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(navigationPage);
            }
        });
        try {
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog();
        }
        finish();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Opps! Something is wrong");

        alertDialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
