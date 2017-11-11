package com.example.evans.ui;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Goal;
import com.example.evans.data.TimePeriod;


import org.joda.time.LocalDateTime;

import java.time.format.DateTimeFormatter;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GoalEditFragment extends Fragment {

    OnSubmitGoalEdit _hostActivity;

    public GoalEditFragment() {
        //Goal Fragment
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goal_edit, container, false);

        return inflater.inflate(R.layout.fragment_goal_edit, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void goalActivitySave(View view){
        TextView etxtGoalName   = (TextView) view.findViewById(R.id.etxt_GoalName);
        TextView etxtGoalStartD = (TextClock) view.findViewById(R.id.etxt_StartDate);
        TextView etxtGoalEndD   = (TextClock) view.findViewById(R.id.etxt_EndDate);
        TextView etxtGoalRepeat = (TextView) view.findViewById(R.id.etxt_repeatTime);
        TextView etxtDescription = (TextView) view.findViewById(R.id.etxt_DGoal);

        String title = etxtGoalName.getText().toString();
        String startDate = etxtGoalStartD.getText().toString();
        String dueDate = etxtGoalEndD.getText().toString();
        String repeatCycleText = etxtGoalRepeat.getText().toString();
        String description = etxtDescription.getText().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime sDate = LocalDateTime.parse(startDate);
        LocalDateTime dDate = LocalDateTime.parse(dueDate);
        TimePeriod repeatCycle = TimePeriod.Month;

        //TODO: repeatCYcle is a TIME PERIOD not sure how to handle this
        Goal goal = new Goal(title, description, dDate, sDate, repeatCycle);

        _hostActivity.onGoalEditFinish(goal);

    }

    public interface OnSubmitGoalEdit {
        void onGoalEditFinish (Goal goal);
    }


    /**
     * Override onAttach to make sure that the container activity has implemented the callback we specified in
     * our interface
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        // make sure that the container class implemented our interface. If it did then it can be casted
        // if not then we know it did not therefore throw an error
        try {
            _hostActivity = (OnSubmitGoalEdit) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSubmitGoalEdit");
        }

    }

}
