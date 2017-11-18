package com.example.evans.ui;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.evans.R;
import com.example.evans.data.Appointment;
import com.example.evans.data.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



/**
 * This fragment will be loaded when the user tries to create a new appointment
 * or edit an existing appointment
 */
public class AppointmentEditFragment extends Fragment {

    private EditText _name;
    private EditText _email;
    private EditText _phone;
    private EditText _date;
    private Spinner _serviceSpinner;
    private EditText _servicePrice;
    private EditText _notes;
    private Button _btnSave;
    private Button _btnCancel;
    private Map<String, Service> _servicesMap;

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
        _serviceSpinner = (Spinner) rootView.findViewById(R.id.spinner_service_type);
        _servicePrice = (EditText) rootView.findViewById(R.id.etxt_price);
        _notes = (EditText) rootView.findViewById(R.id.etxt_appointment_note);
        _btnSave = (Button) rootView.findViewById(R.id.btn_appointment_save);
        _btnCancel = (Button) rootView.findViewById(R.id.btn_appointment_cancel);
        _servicesMap = new HashMap<String, Service>();

        _servicesMap = _hostActivity.getServices();



        // Set up the spinner for services list
        setupServicesSpinner();


        // Onclick listener for the save button
        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAppointment();
            }
        });

        /* Listen for when an item is selected and set the price accordingly */
        _serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Service currentService = _servicesMap.get(adapterView.getSelectedItem().toString());
                Double price = currentService.getPrice();
                String localePrice =  getString(R.string.dollar_sign) +  String.format(Locale.US,"%1.2f", price);
                _servicePrice.setText(localePrice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                _servicePrice.setText("$0.00");
            }
        });


        // Inflate the layout for this fragment
        return rootView;
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
     * One job: Populate our services spinner from the data we have in MainController
     */
    private void setupServicesSpinner() {

        List<String> servicesNames = new ArrayList<>(_hostActivity.getServices().keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_spinner_item,
                servicesNames);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        _serviceSpinner.setAdapter(adapter);

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

        if (!name.isEmpty()) {
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
     * Interface that the activate that creates this fragment must implemnent. This interface will
     * handle when a new appointment has been added
     */
    public interface OnSubmitAppointment {
        void onAppointmentEditFinish (Appointment appointment);
        void onCancelAppointmentEdit();
        Map<String, Service> getServices();
        void hideActionbar();
        void showActionbar();
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
