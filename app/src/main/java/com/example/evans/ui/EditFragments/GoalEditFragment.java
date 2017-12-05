package com.example.evans.ui.EditFragments;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.evans.R;
import com.example.evans.data.Goal;
import com.example.evans.data.TimePeriod;
import com.example.evans.ui.DialogFragements.DatePickerFragment;
import com.example.evans.ui.KeyboardControl;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;


/**
 * {@link Fragment} subclass to edit goal data.
 */
public class GoalEditFragment extends Fragment
        implements DatePickerFragment.OnDateSetListener {

    private EditText _goalName;
    private EditText _goalStart;
    private EditText _goalEnd;
    private EditText _goalRepeat;
    private EditText _goalDescription;
    private char current;
    private EditText _currentDateEdit;

    private Goal _selectedGoal;

    private LocalDate _selectedStartDate;
    private LocalDate _selectedEndDate;

    private Button _btnSaveGoal;
    private Button _btnCancelGoal;

    private DateTimeFormatter _formatter;

    private OnSubmitGoalEdit _hostActivity;

    public GoalEditFragment() {
        //Goal Fragment
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goal_edit, container, false);

        _formatter = DateTimeFormat.forPattern("dd, MMMM yyyy");

        _goalName = view.findViewById(R.id.etxt_goal_name);
        _goalStart= view.findViewById(R.id.etxt_start_date);
        _goalEnd =  view.findViewById(R.id.etxt_end_date);
        _goalRepeat = view.findViewById(R.id.etxt_repeat_time);
        _goalDescription = view.findViewById(R.id.etxt_goal_details);

        _btnSaveGoal = view.findViewById(R.id.btn_edit_bar_save);
        _btnCancelGoal = view.findViewById(R.id.btn_edit_bar_cancel);

        initializeGoalDetails();

        _btnSaveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                KeyboardControl.closeKeyboard(getActivity());

                Goal newGoal = createGoal();

                if(newGoal != null){
                    _hostActivity.onGoalEditFinish(newGoal);
                } else{
                    Snackbar.make(getActivity().findViewById(R.id.content_frame),
                            "ERROR: Invalid Goal data. Please review your input", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        _btnCancelGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardControl.closeKeyboard(getActivity());
                _hostActivity.onGoalEditCancel();
            }
        });

        // On click listener for date
        _goalStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                current = 'a';
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.setTargetFragment(GoalEditFragment.this, 0);
                dateFragment.show(getFragmentManager(), "DatePicker");

                _currentDateEdit = _goalStart;
            }
        });

        // On click listener for date
        _goalEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                current = 'b';
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.setTargetFragment(GoalEditFragment.this, 0);
                dateFragment.show(getFragmentManager(), "DatePicker");

                _currentDateEdit = _goalEnd;
            }
        });

        return view;
    }

    public Goal createGoal(){

        Goal goal = null;

        if(_goalName == null) { return null; }
        if(_goalEnd == null) {return null; }
        if(_goalStart == null) {return null; }


        String goalName   = _goalName.getText().toString();
        LocalDate goalStart = _selectedStartDate;
        LocalDate goalEnd   = _selectedEndDate;
        String goalRepeat = _goalRepeat.getText().toString();
        String goalDescription = _goalDescription.getText().toString();

        TimePeriod repeatCycle = TimePeriod.Month;

        if(!goalName.isEmpty()) {
            goal = new Goal(goalName, goalDescription, goalEnd, goalStart, repeatCycle);
        }

        return goal;
    }

    @Override
    public void onDateSet(LocalDate date) {
        _currentDateEdit.setText(_formatter.print(date));

       if(current == 'a') {
            _selectedStartDate = date;
       }

       if(current == 'b'){
            _selectedEndDate = date;
       }
    }

    /**
     * to initialize the customer details for edit
     */
    private void initializeGoalDetails() {

        if (_selectedGoal != null) {
            _goalName.setText(_selectedGoal.getTitle());
            _goalDescription.setText(_selectedGoal.getDescription());
            _goalStart.setText(_selectedGoal.getStartDate());
            _goalEnd.setText(_selectedGoal.getDueDate());
        }
    }

    /**
     * when an existing goal is called to edit
     * @param goal passes to set up existing
     */
    public void setExistingGoal(Goal goal){
        _selectedGoal = goal;
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

    /**
     * Override onAttach to make sure that the container activity has implemented the callback we specified in
     * our interface
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // make sure that the container class implemented our interface. If it did then it can be casted
        // if not then we know it did not therefore throw an error
        try {
            _hostActivity = (OnSubmitGoalEdit) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSubmitGoalEdit");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface OnSubmitGoalEdit {
        void onGoalEditFinish (Goal goal);
        List<Goal> getGoal();
        void onGoalEditCancel();
        void hideActionbar();
        void showActionbar();
    }

}
