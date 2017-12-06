package com.example.evans.ui.Adapters;

import android.content.Context;
import android.util.Log;
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

    private ArrayList<Goal> _goalList = new ArrayList<>();

    // use of the viewHolder allows faster loading because the views
    // don't need to be collected for each item in the list view
    static class ViewHolder {
        TextView title;
        TextView dueDate;
        CheckBox complete;
    }

    public GoalAdapter(Context context, int textViewResourceId, ArrayList<Goal> objects) {
        super(context, textViewResourceId, objects);
        _goalList = objects;
    }

    public GoalAdapter(Context context, int textViewResourceId){
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder _viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.goal_adapter, parent, false);

            // well set up the ViewHolder
            _viewHolder = new ViewHolder();
            _viewHolder.title = convertView.findViewById(R.id.goal_adapter_title);
            _viewHolder.dueDate = convertView.findViewById(R.id.goal_adapter_due_date);
            _viewHolder.complete = convertView.findViewById(R.id.goal_adapter_complete);
        } else {
            _viewHolder = (ViewHolder) convertView.getTag();
        }

        if(_goalList.get(position) != null && _viewHolder != null) {

            _viewHolder.title.setText(_goalList.get(position).getTitle());
            _viewHolder.dueDate.setText(_goalList.get(position).getDueDate());

            // we sometimes set this to null, and then it breaks the program
            if (_goalList.get(position).isDone() != null) {
                _viewHolder.complete.setChecked(_goalList.get(position).isDone());
            }
        }
        return convertView;
    }
}
