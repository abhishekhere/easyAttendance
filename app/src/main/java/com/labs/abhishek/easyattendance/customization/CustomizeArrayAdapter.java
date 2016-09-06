package com.labs.abhishek.easyattendance.customization;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by anand on 5/9/16.
 */
public class CustomizeArrayAdapter extends ArrayAdapter<String> {
    private int[] colors = new int[]{Color.parseColor("#F8DE7E"), Color.parseColor("#E3A857")};

    public CustomizeArrayAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }
}
