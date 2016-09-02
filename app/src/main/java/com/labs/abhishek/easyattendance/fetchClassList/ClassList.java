package com.labs.abhishek.easyattendance.fetchClassList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.dbConnection.ClassTableDBHelper;
import com.labs.abhishek.easyattendance.dbConnection.TheStaticValuesClass;
import com.labs.abhishek.easyattendance.fetchMembersList.MembersList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anand on 30/8/16.
 */
public class ClassList extends Activity {
    List<String> classList = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list);

        ClassTableDBHelper classTableDBHelper = new ClassTableDBHelper(this);
        classList = new FetchClassList().fetchAllTheClasses(classTableDBHelper);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, (String[]) classList.toArray(new String[classList.size()]));
        listView = (ListView) findViewById(R.id.lvClassList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String className = (String) listView.getItemAtPosition(position);
                moveToMembersListPage(className);
            }
        });
    }

    private void moveToMembersListPage(String className) {
        final Intent membersIntent = new Intent(this, MembersList.class);
        if (className != null && className != "") {
            new TheStaticValuesClass(className);
        }
        Thread threadGoToMembersList = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(membersIntent);
            }
        });
        threadGoToMembersList.start();
    }
}
