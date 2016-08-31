package com.labs.abhishek.easyattendance.fetchClassList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.labs.abhishek.easyattendance.R;
import com.labs.abhishek.easyattendance.dbConnection.ClassTableDBHelper;

import java.util.List;

/**
 * Created by anand on 30/8/16.
 */
public class ClassList extends Activity {
        List<String> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list);

        ClassTableDBHelper classTableDBHelper = new ClassTableDBHelper(this);
        classList = new FetchClassList().fetchAllTheClasses(classTableDBHelper);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, (String[]) classList.toArray());
        ListView listView = (ListView) findViewById(R.id.lvClassList);
        listView.setAdapter(adapter);
    }
}
