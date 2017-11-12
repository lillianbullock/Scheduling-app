package com.example.evans.ui;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.Appointment;
import com.example.evans.data.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * This fragment will be loaded when the user tries to create a new appointment
 * or edit an existing appointment
 */
public class EditAppointmentFragment extends Fragment {

    private EditText _name;
    private EditText _email;
    private EditText _phone;
    private EditText _date;
    private Spinner _serviceSpinner;
    private EditText _servicePrice;
    private EditText _notes;

    OnSubmitAppointment _hostActivity;

    public EditAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_edit_appointment, container, false);

        _name = (EditText) rootView.findViewById(R.id.etxt_customer_name);
        _phone = (EditText) rootView.findViewById(R.id.etxt_customer_phone);
        _email = (EditText) rootView.findViewById(R.id.etxt_customer_email);
        _date = (EditText) rootView.findViewById(R.id.etxt_appointment_date);
        _serviceSpinner = (Spinner) rootView.findViewById(R.id.spinner_service_type);
        _servicePrice = (EditText) rootView.findViewById(R.id.etxt_price);
        _notes = (EditText) rootView.findViewById(R.id.etxt_other_notes);


        setupServicesSpinner();


        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * One job: Populate our services spinner from the data we have in MainController
     */
    private void setupServicesSpinner() {

        String [] values = {"Men Hair", "Color", "Shampoo", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        _serviceSpinner.setAdapter(adapter);

        _serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                _servicePrice.setText("$12.00");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                _servicePrice.setText("$0.00");
            }
        });

        /* It's crashing when it calls getServicesList()
        List<String> servicesNames = new ArrayList<>(_hostActivity.getServicesList().keySet());

        servicesNames.addAll( _hostActivity.getServicesList().keySet());


        *//*for (String title : _hostActivity.getServicesList().keySet()){
            servicesNames.add(title);
        } replaced with the code above for now*//*


        // set up the array adapter
        ArrayAdapter<String> servicesSpinnerAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                servicesNames);

        // Specify that the adapter will have a drop down resource. Pass the android resource layout type
        servicesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attach the adapter
        _serviceSpinner.setAdapter(servicesSpinnerAdapter);*/



    }

    private void createAppointment() {
        String name = _name.getText().toString();
        String phone = _phone.getText().toString();
        String email = _email.getText().toString();
        String date = _date.getText().toString();
        String service = "Dummy text for now";
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
     * Declare an interface that the activate that creates this fragment must implemnent. This interface will
     * handle when a new appointment has been added
     */
    public interface OnSubmitAppointment {
        void onAppointmentEditFinish (Appointment appointment);
        Map<String, Service> getServicesList();
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
            throw new ClassCastException(context.toString() + " must implement OnSubmitAppoinment");
        }

    }

}
