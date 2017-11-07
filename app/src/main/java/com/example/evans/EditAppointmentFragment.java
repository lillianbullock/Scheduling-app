package com.example.evans;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * This fragment will be loaded when the user tries to create a new appointment
 * or edit an existing appointment
 */
public class EditAppointmentFragment extends Fragment {


    public EditAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_appointment, container, false);
    }

    // TODO decalre different interfaces for interacting with the activity that this fragment will be loaded on.

}
