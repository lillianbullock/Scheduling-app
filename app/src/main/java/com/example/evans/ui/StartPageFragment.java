package com.example.evans.ui;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evans.R;
import com.example.evans.data.Appointment;
import com.example.evans.data.Goal;


/**
 * {@link Fragment} subclass to display on the main screen.
 */
public class StartPageFragment extends Fragment {

    public StartPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_page, container, false);
    }

    

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface InteractWithStartPageFragment{
        void onClickGoalsSeeMore();
        void onClickAppointmentsSeeMore();
        void onClickAppointment(Appointment appointment);
        void onClickGoal(Goal goal);
    }
}
