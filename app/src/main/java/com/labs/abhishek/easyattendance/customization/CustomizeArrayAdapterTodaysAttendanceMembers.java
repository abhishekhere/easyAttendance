package com.labs.abhishek.easyattendance.customization;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by anand on 5/9/16.
 */
public class CustomizeArrayAdapterTodaysAttendanceMembers extends ArrayAdapter<String> {

    private int[] colors = new int[]{Color.parseColor("#ccffcc"), Color.parseColor("#ffb3b3"), Color.parseColor("#F5F5F5")};

    public CustomizeArrayAdapterTodaysAttendanceMembers(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colorPos = 2;
        String memberName = getItem(position);
        String presentOrAbsent = new CustomizationStaticValuesClass().membersNameVsAttendanceRecordMap.get(memberName);
        if (presentOrAbsent.trim().equals("0")) {
            colorPos = 1;
        } else if (presentOrAbsent.trim().equals("1")) {
            colorPos = 0;
        } else {
            colorPos = 2;
        }
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }

}
