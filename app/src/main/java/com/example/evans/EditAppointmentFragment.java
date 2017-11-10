package com.example.evans;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evans.data.Appointment;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditAppointmentFragment extends Fragment {

    AppointmentEditFragmentListener _hostActivityListener;


    public EditAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            _hostActivityListener = (AppointmentEditFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "AppointmentEditFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_appointment2, container, false);
    }


    public interface AppointmentEditFragmentListener {

        void onAppointmentFinishEdit(Appointment appointment);
        void onAppointmentEditCancel();
    }

}
