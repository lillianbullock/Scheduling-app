package com.example.evans.ui;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.Appointment;


/**
 * This fragment will be loaded when the user tries to create a new appointment
 * or edit an existing appointment
 */
public class AppointmentEditFragment extends Fragment {

    private EditText _name;
    private EditText _email;
    private EditText _phone;
    private EditText _date;
    private EditText _service;
    private EditText _servicePrice;
    private EditText _notes;

    OnSubmitAppointment _hostActivity;

    public AppointmentEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_appointment_edit, container, false);

        _name = (EditText) rootView.findViewById(R.id.etxt_customer_name);
        _phone = (EditText) rootView.findViewById(R.id.etxt_customer_phone);
        _email = (EditText) rootView.findViewById(R.id.etxt_customer_email);
        _date = (EditText) rootView.findViewById(R.id.etxt_appointment_date);
        _service = (EditText) rootView.findViewById(R.id.etxt_service_type);
        _servicePrice = (EditText) rootView.findViewById(R.id.etxt_appointment_price);
        _notes = (EditText) rootView.findViewById(R.id.etxt_appointment_note);

        // Inflate the layout for this fragment
        return rootView;
    }

    private void createAppointment() {
        String name = _name.getText().toString();
        String phone = _phone.getText().toString();
        String email = _email.getText().toString();
        String date = _date.getText().toString();
        String service = _service.getText().toString();
        String servicePrice = _servicePrice.getText().toString();
        String notes = _notes.getText().toString();

        // check for email
        if (!isValidEmail(email)) {
            Toast.makeText(getActivity(), "Invalid email. Please enter a valid email",
                    Toast.LENGTH_SHORT).show();
        }

        if (name != null) {
            // TODO Connect appointment with customers
           // Appointment newAppointment = new Appointment();
           // _hostActivity.onEditAppointmentFinish(newAppointment);
        }

    }


    /**
     *  isValid: Return true is the passed email string matches the specified
     *  regEx pattern, false otherwise
     */
    public static boolean isValidEmail(String email) {

        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        return (email.matches(EMAIL_REGEX));
    }

    /**
     * Declare an interface that the activate that creates this fragment must implement. This interface will
     * handle when a new appointment has been added
     */
    public interface OnSubmitAppointment {
        void onAppointmentEditFinish (Appointment appointment);
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
            _hostActivity = (OnSubmitAppointment) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSubmitAppointment");
        }

    }

}
