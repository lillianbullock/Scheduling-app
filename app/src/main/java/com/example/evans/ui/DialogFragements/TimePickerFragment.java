package com.example.evans.ui.DialogFragements;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import org.joda.time.LocalTime;

import java.util.Calendar;

/**
 * { @link DialogFragment } extension that creates a UI way for the user to pick a time
 * Displays a clock and sets a {@link org.joda.time.LocalTime}
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

        // Set the time
        LocalTime selectedTime = new LocalTime(hour, minute);

        // call the caller's onDateSet to do with the time what it wants to do. It's not our business
        // we did our job by getting the date for them
        if (_caller != null) {
            _caller.onTimeSet(selectedTime);
        } else {
            throw new ClassCastException("Null reference for calling fragment: Use setTargetFragment in the calling fragment");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            _caller = (OnTimeSetListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "The Calling activity did not implement ReceiveDateValueListener");
            throw new ClassCastException(activity.toString() + " must implement OnTimeSetListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface OnTimeSetListener {
        void onTimeSet(LocalTime time);
    }
}
