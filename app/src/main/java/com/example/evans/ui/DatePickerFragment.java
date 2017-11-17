package com.example.evans.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;

import org.joda.time.LocalDate;

import java.util.Calendar;

/**
 * For picking a date
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    RecieveDateValueListener _caller;
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
        LocalDate selectedDate = new LocalDate(year, month, day);
        _caller.setDate(selectedDate);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            _caller = (RecieveDateValueListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "The Calling activity did not implement RecieveDateValueListener");
            throw new ClassCastException(context.toString() + " must implement RecieveDateValueListener");
        }
    }

    public interface RecieveDateValueListener {
        void setDate(LocalDate date);
    }
}
