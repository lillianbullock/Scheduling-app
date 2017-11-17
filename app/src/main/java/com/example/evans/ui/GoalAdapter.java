package com.example.evans.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Goal;

import java.util.ArrayList;

/**
 * Created by Brooke Nelson on 11/15/2017.
 */

public class GoalAdapter extends ArrayAdapter<Goal> {

    ArrayList<Goal> goalList = new ArrayList<>();

    public GoalAdapter(Context context, int textViewResourceId, ArrayList<Goal> objects) {
        super(context, textViewResourceId, objects);
        goalList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.goal_adapter, null);

        TextView textView = (TextView) view.findViewById(R.id.goal_adapter_view);

        textView.setText(goalList.get(position).getTitle());
        return view;

    }
}
