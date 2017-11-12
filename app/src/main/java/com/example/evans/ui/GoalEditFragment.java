package com.example.evans.ui;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText _goalName;
    private EditText _goalStart;
    private EditText _goalEnd;
    private EditText _goalRepeat;
    private EditText _goalDescription;

    private Button _btnSaveGoal;

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
        _goalName = (EditText) view.findViewById(R.id.etxt_GoalName);
        _goalStart=  (EditText) view.findViewById(R.id.etxt_StartDate);
        _goalEnd =  (EditText) view.findViewById(R.id.etxt_EndDate);
        _goalRepeat =  (EditText) view.findViewById(R.id.etxt_repeatTime);
        _goalDescription =  (EditText) view.findViewById(R.id.etxt_DGoal);

        _btnSaveGoal = view.findViewById(R.id.btn_saveGoal);

        _btnSaveGoal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view){
                goalActivitySave();
            }
        });
        return view;
    }

    public void goalActivitySave(){
        String goalName   = _goalName.getText().toString();
        String goalStart = _goalStart.getText().toString();
        String goalEnd   = _goalEnd.getText().toString();
        String goalRepeat = _goalRepeat.getText().toString();
        String goalDescription = _goalDescription.getText().toString();

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        LocalDateTime sDate = LocalDateTime.now();
        LocalDateTime dDate = LocalDateTime.now();

        TimePeriod repeatCycle = TimePeriod.Month;

        if(goalName != null) {
            //TODO: repeatCYcle is a TIME PERIOD not sure how to handle this also local date and time
            Goal newGoal = new Goal(goalName, goalDescription, dDate, sDate, repeatCycle);
            _hostActivity.onGoalEditFinish(newGoal);
        }
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
