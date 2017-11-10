package com.example.evans.ui;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Goal;
import com.example.evans.data.TimePeriod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Goal_Fragment extends Fragment {


    public Goal_Fragment() {
        //Goal Fragment
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_goal_, container, false);

        return inflater.inflate(R.layout.fragment_goal_, container, false);
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
        String repeatCycle = etxtGoalRepeat.getText().toString();
        String description = etxtDescription.getText().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime sDate = LocalDateTime.parse(startDate, formatter);
        LocalDateTime dDate = LocalDateTime.parse(dueDate, formatter);

        //TODO: repeatCYcle is a TIME PERIOD not sure how to handle this
        //Goal goal = new Goal(title, description, dDate, sDate, repeatCycle);

    }

}
