package com.example.evans.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Goal;

import java.util.ArrayList;

/**
 * {@link ArrayAdapter<>} extension that displays the goal data in a list view
 */
public class GoalAdapter extends ArrayAdapter<Goal> {

    ArrayList<Goal> _goalList = new ArrayList<>();

    public GoalAdapter(Context context, int textViewResourceId, ArrayList<Goal> objects) {
        super(context, textViewResourceId, objects);
        _goalList = objects;
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

        TextView title = (TextView) view.findViewById(R.id.goal_adapter_title);
        TextView dueDate = (TextView) view.findViewById(R.id.goal_adapter_due_date);
        CheckBox complete = (CheckBox) view.findViewById(R.id.goal_adapter_complete);

        title.setText(_goalList.get(position).getTitle());
        dueDate.setText(_goalList.get(position).getDueDate());
        //TODO put dateTime in a better format
        if (_goalList.get(position).isDone() != null)
            complete.setChecked(_goalList.get(position).isDone());

        return view;

    }
}
