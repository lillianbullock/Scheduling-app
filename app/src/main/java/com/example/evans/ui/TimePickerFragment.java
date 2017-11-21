package com.example.evans.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import org.joda.time.LocalTime;

import java.util.Calendar;

/**
 * Timepicker for picking time
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG ="TimePickerFragment";
    private OnTimeSetListener _caller;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // initialize to the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // USE Calendar.Hour if this doesn't work
        int minute = calendar.get(Calendar.MINUTE);
        int seconds = 0;

        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

        _caller = (OnTimeSetListener) getTargetFragment();

        /*try {
            _caller = (OnTimeSetListener) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "The calling fragment did not implement OnDateSetListener");
        }*/

        // Set the time
        LocalTime selectedTime = new LocalTime(hour, minute);


        // call the caller's onDateSet to do with the time what it wants to do. It's not our business
        // we did our job by getting the date for them
        if (_caller != null) {
            _caller.onTimeSet(selectedTime);
        } else {
            throw new ClassCastException("Null reference for calling fragement: Use setTargetFragment in the calling fragment");
        }
    }

    public interface OnTimeSetListener {
        void onTimeSet(LocalTime time);
    }
}
