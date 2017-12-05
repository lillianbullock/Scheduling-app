package com.example.evans.ui.EditFragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
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
import com.example.evans.data.InvalidCustomerException;
import com.example.evans.data.MainController;
import com.example.evans.data.OnGetDataListener;
import com.example.evans.data.Service;
import com.example.evans.ui.DialogFragements.DatePickerFragment;
import com.example.evans.ui.DialogFragements.TimePickerFragment;
import com.example.evans.ui.KeyboardControl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This fragment will be loaded when the user tries to create a new appointment
 * or edit an existing appointment
 */
public class AppointmentEditFragment extends Fragment
        implements DatePickerFragment.OnDateSetListener,
                   TimePickerFragment.OnTimeSetListener {

    private EditText _name;
    private EditText _email;

    private EditText _phone;
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
    private Customer _selectedCustomer;

    private static final String TAG  = "AppointmentEditFragment";
    private static final int DATE_DIALOG = 1;
    private static final int TIME_DIALOG = 2;

    private OnSubmitAppointment _hostActivity;
    private MainController _maincontroller;

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
        _notes          = (EditText) rootView.findViewById(R.id.etxt_appointment_note);
        _btnSave        = (Button) rootView.findViewById(R.id.btn_edit_bar_save);
        _btnCancel      = (Button) rootView.findViewById(R.id.btn_edit_bar_cancel);
        _maincontroller = new MainController();

        _servicesMap    = _hostActivity.getServices();

        // Set up the spinner for services list
        setupServicesSpinner();

        // Initialize customer details
        initializeCustomerDetails();


        // Onclick listener for the save button
        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KeyboardControl.closeKeyboard(getActivity());
                Appointment newAppointment = createAppointment();

                if (newAppointment != null){
                    _hostActivity.onAppointmentEditFinish(_selectedCustomer, newAppointment);
                } else {
                    Snackbar.make(getActivity().findViewById(R.id.content_frame),
                            "ERROR: Invalid customer data. Please review your input", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        // onClick Listener for cancel
        _btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardControl.closeKeyboard(getActivity());
                _hostActivity.onAppointmentEditCancel();
            }
        });

        // On click listener for date
        _date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.setTargetFragment(AppointmentEditFragment.this, 0);
                dateFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        // onClick listener for time
        _time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.setTargetFragment(AppointmentEditFragment.this, 0);
                timeFragment.show(getFragmentManager(), "DatePicker");
            }
        });



        /* Listen for when an item is selected and set the price accordingly */
        _serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                // set the selected service so we can use it later
                Service currentService = _servicesMap.get(adapterView.getSelectedItem().toString());
                _selectedService = currentService;

                Double price = currentService.getPrice();
                String localePrice =  String.format(Locale.US,"%1.2f", price);

                // display the price to the user
                _servicePrice.setText(localePrice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                _servicePrice.setText("$0.00");
            }
        });


        // return the inflated layout for this fragment
        return rootView;
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
        if (_selectedService == null) {
            Log.e(TAG, "Selected service was null");
            return null;
        }

        String title = _name.getText().toString();
        String phone = _phone.getText().toString();
        String email = _email.getText().toString();
        String notes;

        //
        if (_selectedCustomer == null) {
            _selectedCustomer = _maincontroller.getCustomerWithName(title);

            if (_selectedCustomer == null){
                String id = _maincontroller.getIdForNewCustomer();
                // there's no existing customer, create one then
                _selectedCustomer = new Customer(id, title, email, phone, LocalDate.now());
                _maincontroller.addCustomer(_selectedCustomer);
            }
        }

        // this should be optional
        if (_notes != null) {
            notes  = _notes.getText().toString();
        }

        if (!title.isEmpty() && _selectedService != null) {
            appointment = new Appointment(title, _selectedDate, _selectedTime, _selectedCustomer.getId(), _selectedService);
        }

        // return appointment;
        return appointment;


    }



    @Override
    public void onDateSet(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd, MMMM yyyy");
        _selectedDate = date;
        _date.setText(formatter.print(date));
    }

    @Override
    public void onTimeSet(LocalTime time) {
        DateTimeFormatter timeFormatter = DateTimeFormat.shortTime();
        _selectedTime = time;
        _time.setText(timeFormatter.print(time));
    }


    /**
     * Call the host activity's getCustomerForAppointment and use the customer details
     * to initialize the customer details for the appointment
     */
    private void initializeCustomerDetails() {


        if (_selectedCustomer != null) {
            _name.setText(_selectedCustomer.getName());
            _email.setText(_selectedCustomer.getEmail());
            _phone.setText(_selectedCustomer.getPhone());
        }
    }

    public void setCustomer(Customer customer) {
        if (customer != null) {
            _selectedCustomer = customer;
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

        List<Service> newTest = new ArrayList<>();

        for(Service service: _hostActivity.getServices().values()){

        }

        List<String> servicesNames = new ArrayList<>(_hostActivity.getServices().keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getActivity(),
                android.R.layout.simple_spinner_item,
                servicesNames);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        _serviceSpinner.setAdapter(adapter);

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
            throw new ClassCastException(context.toString() + " must implement OnSubmitAppointment");
        }

    }

    /**
     * Interface that the activate that creates this fragment must implement. This interface will
     * handle when a new appointment has been added
     */
    public interface OnSubmitAppointment {
        void onAppointmentEditFinish (Customer customer, Appointment appointment);
        void onAppointmentEditCancel();
        Map<String, Service> getServices();
        void hideActionbar();
        void showActionbar();
    }


}
