package com.example.evans.ui.DialogFragements;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;

import org.joda.time.LocalDate;

import java.util.Calendar;

/**
 * { @link DialogFragment } extension that creates a UI way for the user to pick a date
 * Displays a calendar and sets a {@link org.joda.time.DateTime}
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    OnDateSetListener _caller;
    private static final String TAG = "DatePickerFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // get the current date and set it as the default
        final Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH) + 1;

        // create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        try {
            _caller = (OnDateSetListener) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "The Calling activity did not implement OnDateSetListener");
            throw new ClassCastException(getTargetFragment().toString() + " must implement OnDateSetListener");
        }

        // add one to month since it starts at zero
        LocalDate selectedDate = new LocalDate(year, month + 1, day);

        // call the callee's onDateSet to do with the date whatever it wants to do. It's not our business
        // we did our job by getting the date for them
        _caller.onDateSet(selectedDate);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            _caller = (OnDateSetListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "The Calling activity did not implement ReceiveDateValueListener");
            throw new ClassCastException(activity.toString() + " must implement ReceiveDateValueListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface OnDateSetListener {
        void onDateSet(LocalDate date);
    }
}
