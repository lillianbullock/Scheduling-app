package com.example.evans.ui.ViewFragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Goal;


/**
 * {@link Fragment} subclass to view goal data.
 */
public class GoalViewFragment extends Fragment {

    private Goal _goal;
    private InteractionWithGoalViewFragmentListener _hostActivity;
    private Button _editGoalBtn;

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
        TextView startD = (TextView) view.findViewById(R.id.txt_goal_view_start_date);
        TextView endD = (TextView) view.findViewById(R.id.txt_goal_view_end_date);

        _editGoalBtn = (Button) view.findViewById(R.id.btn_edit_goal);

        CheckBox _checkBox = (CheckBox) view.findViewById(R.id.goal_done_box);

        name.setText(_goal.getTitle());
        detail.setText(_goal.getDescription());
        startD.setText(_goal.getStartDate());
        endD.setText(_goal.getDueDate());

        _editGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onEditGoal(_goal);
            }
        });

        _checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCheckBox(_goal);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void onCheckBox(Goal goal){
        goal.setDone(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        _hostActivity.hideActionbar();
    }

    @Override
    public void onStop() {
        super.onStop();
        _hostActivity.showActionbar();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //check for implementation by trying to cast to an instance of the interface
        try {
            _hostActivity = (InteractionWithGoalViewFragmentListener) activity;
        } catch (ClassCastException e) {
            // if fails, interface wasn't implemented
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithGoalViewFragmentListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface InteractionWithGoalViewFragmentListener{
        void hideActionbar();
        void showActionbar();

        //Goal getViewGoal();
        void onEditGoal(Goal goal);
        void viewWithGoal(Goal goal);
    }

}
