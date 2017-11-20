package com.example.evans.ui;



import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.example.evans.R;
import com.example.evans.data.Appointment;
import com.example.evans.data.Customer;
import com.example.evans.data.Service;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



/**
 * This fragment will be loaded when the user tries to create a new appointment
 * or edit an existing appointment
 */
public class AppointmentEditFragment extends Fragment
        implements DatePickerFragment.RecieveDateValueListener{

    private EditText _name;
    private EditText _email;
    private EditText _phone;
    private String   _customerId;
    private EditText _date;
    private EditText _time;
    private Spinner _serviceSpinner;
    private EditText _servicePrice;
    private EditText _notes;
    private Button _btnSave;
    private Button _btnCancel;
    private Map<String, Service> _servicesMap;
    private Service _selectedService;
    private LocalDate _selectedDate;
    private LocalTime _selectedTime;



    private static final String TAG  = "AppointmentEditFragment";
    private static final int DATE_DIALOG = 1;
    private static final int TIME_DIALOG = 2;

    OnSubmitAppointment _hostActivity;

    public AppointmentEditFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_appointment_edit, container, false);

        _name           = (EditText) rootView.findViewById(R.id.etxt_customer_name);
        _phone          = (EditText) rootView.findViewById(R.id.etxt_customer_phone);
        _email          = (EditText) rootView.findViewById(R.id.etxt_customer_email);
        _date           = (EditText) rootView.findViewById(R.id.etxt_appointment_date);
        _time           = (EditText) rootView.findViewById(R.id.etxt_appointment_time);
        _serviceSpinner = (Spinner) rootView.findViewById(R.id.spinner_sales_type);
        _servicePrice   = (EditText) rootView.findViewById(R.id.etxt_price);
        _notes          = (EditText) rootView.findViewById(R.id.etxt_other_notes);
        _btnSave        = (Button) rootView.findViewById(R.id.btn_edit_bar_save);
        _btnCancel      = (Button) rootView.findViewById(R.id.btn_edit_bar_cancel);
        _servicesMap    = new HashMap<String, Service>();
        _servicesMap    = _hostActivity.getServices();




        // Set up the spinner for services list
        setupServicesSpinner();

        // Initialize customer details
        initializeCustomerDetails();


        // Onclick listener for the save button
        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment newAppointment = createAppointment();

                if (newAppointment != null){
                    _hostActivity.onAppointmentEditFinish(newAppointment);
                }
            }
        });

        // onClick Listener for cancel
        _btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onCancelAppointmentEdit();
            }
        });

        // On click listener for date
        _date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        // onClick listener for time
        _time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getFragmentManager(), "DatePicker");
            }
        });



        /* Listen for when an item is selected and set the price accordingly */
        _serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Service currentService = _servicesMap.get(adapterView.getSelectedItem().toString());
                _selectedService = currentService;
                Double price = currentService.getPrice();
                String localePrice =  String.format(Locale.US,"%1.2f", price);
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
    public void setDate(LocalDate date) {
        _selectedDate = date;
        _date.setText(date.toString());
    }

    /**
     * Call the host activity's getCustomerForAppointment and use the customer details
     * to initialize the customer details for the appointment
     */
    private void initializeCustomerDetails() {

        Customer customer = _hostActivity.getCustomerForAppointment();

        if (customer != null) {
            _name.setText(customer.getName());
            _email.setText(customer.getEmail());
            _phone.setText(customer.getPhone());
            _customerId = customer.getId();
        }
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


    /**
     * Create an appointment
     * @return newly created appointment
     */
    private Appointment createAppointment() {

        Appointment appointment = null;

        // these two fields should be required
        if (_name == null)  {return null;}
        if (_date == null)  {return null;}
        if (_selectedService != null) {
            Log.e(TAG, "Selected service was null");
            return null;
        }

        String title = _name.getText().toString();
        String phone = _phone.getText().toString();
        String email = _email.getText().toString();
        LocalDateTime date = LocalDateTime.now();
        String notes;

        // this should be optional
        if (_notes != null) {
            notes  = _notes.getText().toString();
        }

        if (!title.isEmpty() && _selectedService != null) {
            appointment = new Appointment(title, date, _customerId, _selectedService);

        }

        return appointment;
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
        Customer getCustomerForAppointment();
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
            Log.e(TAG, "The Host activity did not implement OnSubmitAppointment");
            throw new ClassCastException(context.toString() + " must implement OnSubmitAppoinment");
        }

    }

}
