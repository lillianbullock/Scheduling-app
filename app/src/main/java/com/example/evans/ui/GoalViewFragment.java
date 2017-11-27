package com.example.evans.ui;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Goal;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoalViewFragment extends Fragment {

    private Goal _goal;
    private InteractionWithGoalViewFragmentListener _hostListener;

    public GoalViewFragment() {
        // Required empty public constructor
    }

    public void setGoal (Goal goal) { _goal = goal; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goal_view,container, false);

        TextView name = (TextView) view.findViewById(R.id.txt_goal_view_name);
        TextView detail = (TextView) view.findViewById(R.id.txt_goal_detail);
        TextView startD = (TextView) view.findViewById(R.id.txt_goal_view_startD);
        TextView endD = (TextView) view.findViewById(R.id.txt_goal_view_endD);

        name.setText(_goal.getTitle());
        detail.setText(_goal.getDescription());

       //TODO FIGURE OUT HOW TO HANDLE THESE
        // startD.getText(_goal.getStartDate());
       // endD.getText(_goal.getDueDate());


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //check for implementation by trying to cast to an instance of the interface
        try {
            _hostListener = (InteractionWithGoalViewFragmentListener) activity;
        } catch (ClassCastException e) {
            // if fails, interface wasn't implemented
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithGoalViewFragmentListener");
        }
    }

    public interface InteractionWithGoalViewFragmentListener{
        Goal getViewGoal();
    }

}
