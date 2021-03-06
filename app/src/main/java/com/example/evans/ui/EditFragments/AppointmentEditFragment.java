package com.example.evans.ui.EditFragments;

import android.app.Activity;
import android.app.DialogFragment;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.evans.R;
import com.example.evans.data.Appointment;
import com.example.evans.data.Customer;
import com.example.evans.data.MainController;
import com.example.evans.data.Service;
import com.example.evans.ui.DialogFragements.DatePickerFragment;
import com.example.evans.ui.DialogFragements.TimePickerFragment;
import com.example.evans.ui.KeyboardControl;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * {@link Fragment} subclass to edit appointment data.
 */
public class AppointmentEditFragment extends Fragment
        implements DatePickerFragment.OnDateSetListener,
                   TimePickerFragment.OnTimeSetListener {

    private View _rootView;

    private EditText _name;
    private EditText _email;
    private EditText _phone;

    private EditText _servicePrice;
    private EditText _date;
    private EditText _time;

    private Spinner _serviceSpinner;
    private ArrayAdapter<String> adapter;

    private Button _btnSave;
    private Button _btnCancel;
    private CheckBox _appointmentCheckBox;

    private Map<String, Service> _servicesMap;

    private LocalDate _selectedDate;
    private LocalTime _selectedTime;

    private Service _selectedService;
    private Customer _selectedCustomer;
    private Appointment _selectedAppointment;

    private static final String TAG  = "AppointmentEditFragment";

    private OnSubmitAppointment _hostActivity;
    private MainController _mainController = MainController.getInstance();

    public AppointmentEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _rootView =inflater.inflate(R.layout.fragment_appointment_edit, container, false);

        _name           = _rootView.findViewById(R.id.etxt_customer_name);
        _phone          = _rootView.findViewById(R.id.etxt_customer_phone);
        _email          = _rootView.findViewById(R.id.etxt_customer_email);
        _date           = _rootView.findViewById(R.id.etxt_appointment_date);
        _time           = _rootView.findViewById(R.id.etxt_appointment_time);
        _serviceSpinner = _rootView.findViewById(R.id.spinner_sales_type);
        _servicePrice   = _rootView.findViewById(R.id.etxt_price);
        _btnSave        = _rootView.findViewById(R.id.btn_edit_bar_save);
        _btnCancel      = _rootView.findViewById(R.id.btn_edit_bar_cancel);
        _appointmentCheckBox = _rootView.findViewById(R.id.chk_showed_up);

        _servicesMap    = _hostActivity.getServices();

        // Set up the spinner for services list
        setupServicesSpinner();

        // Initialize Appointments, Customer, and Service data details
        initializeAppointmentDetails();
        initializeCustomerDetails();
        initializeServiceDetails();

        //Control the Checkbox to say if customer has come or not
        if (_selectedAppointment != null && _selectedAppointment.isAttended()){

            _appointmentCheckBox.setChecked(_selectedAppointment.isAttended());
        }


        // Onclick listener for the save button
        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardControl.closeKeyboard(getActivity());
                Appointment newAppointment = createAppointment();

                if (newAppointment != null){
                    if(newAppointment.getDate() != null && newAppointment.getTime() != null){
                        _hostActivity.onAppointmentEditFinish(_selectedCustomer, _selectedAppointment, newAppointment);
                    } else {
                        Snackbar.make(getActivity().findViewById(R.id.content_frame),
                                "ERROR: Invalid data, Please review your input", Snackbar.LENGTH_LONG).show();
                    }
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
                String currency = getString(R.string.currency);

                Double price = currentService.getPrice();
                String localePrice =  String.format(Locale.US,"%1.2f", price);
                String finalPrice = currency + localePrice;

                // display the price to the user
                _servicePrice.setText(finalPrice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                _servicePrice.setText(getString(R.string.default_money));
            }
        });

        _appointmentCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_appointmentCheckBox.isChecked()){
                    _selectedAppointment.setAttended(true);
                }else{
                    _selectedAppointment.setAttended(false);
                }
            }
        });


        // return the inflated layout for this fragment
        return _rootView;
    }

    /* Initialize existing information
     * for customer and appointments
     */
    private void initializeAppointmentDetails(){
        if(_selectedAppointment != null) {
            onDateSet(_selectedAppointment.getDateObject());
            onTimeSet(_selectedAppointment.getTimeObject());
        }

    }
    private void initializeCustomerDetails() {
        if (_selectedCustomer != null) {
            _name.setText(_selectedCustomer.getName());
            _email.setText(_selectedCustomer.getEmail());
            _phone.setText(_selectedCustomer.getPhone());
        }
    }

    private void initializeServiceDetails(){
        if(_selectedService != null){
            _serviceSpinner.setSelection(adapter.getPosition(_selectedService.getTitle()));
        }
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

        if (_selectedCustomer == null) {
            _selectedCustomer = _mainController.getCustomerByName(title);

            if (_selectedCustomer == null){
                // there's no existing customer, create a new customer
                String id = _mainController.getIdForNewCustomer();
                _selectedCustomer = new Customer(id, title, email, phone, LocalDate.now());
                _mainController.addCustomer(_selectedCustomer);
            }
        }

        if (!title.isEmpty() && _selectedService != null) {
            // Email isn't required but if it's not empty then check to make sure it's a valid email
            if (email.isEmpty() || (!email.isEmpty() && isValidEmail(email))) {
                appointment = new Appointment(title, _selectedDate, _selectedTime, _selectedCustomer.getId(), _selectedService);
            } else {
                Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: Invalid email", Snackbar.LENGTH_LONG).show();
            }

        } else {
            Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: Name cannot be empty", Snackbar.LENGTH_LONG).show();
        }

        return appointment;
    }

    public void setExistingAppointment(Appointment appointment){
        if(appointment != null) {
            _selectedAppointment = appointment;
            _selectedCustomer = _mainController.getCustomerById(_selectedAppointment.getCustomerId());
            setCustomer(_selectedCustomer);
            _selectedService = _selectedAppointment.getService();
            setService(_selectedService);
            _selectedDate = _selectedAppointment.getDateObject();
            _selectedTime = _selectedAppointment.getTimeObject();
        }
    }

    public void setCustomer(Customer customer) {
        if (customer != null) {
            _selectedCustomer = customer;
        }
    }

    public void setService(Service service){
        if(service != null){
            _selectedService = service;
        }
    }

    /**
     * One job:
     * Populate our services spinner from the data
     * we have in MainController
     */
    private void setupServicesSpinner() {
        List<String> servicesNames = new ArrayList<>(_hostActivity.getServices().keySet());
        adapter = new ArrayAdapter<>(
                this.getActivity(),
                android.R.layout.simple_spinner_item,
                servicesNames);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        _serviceSpinner.setAdapter(adapter);
    }


    /*---------- FUNCTIONS TO ERROR CHECK ----------*/
    /**
     *  isValid: Return true is the passed email string matches the specified
     *  regEx pattern, false otherwise
     */
    private boolean isValidEmail(String email) {

        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        return (email.matches(EMAIL_REGEX));
    }


    /**
     * Override onAttach to make sure that the container activity has implemented the callback we specified in
     * our interface
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // make sure that the container class implemented our interface. If it did then it can be casted
        // if not then we know it did not therefore throw an error
        try {
            _hostActivity = (OnSubmitAppointment) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "The Host activity did not implement OnSubmitAppointment");
            throw new ClassCastException(activity.toString() + " must implement OnSubmitAppointment");
        }
    }

    /*---------- DATE AND TIME HANDLE ----------*/
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


    /*---------- INTERFACE ----------*/
    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface OnSubmitAppointment {
        void onAppointmentEditFinish (Customer customer, Appointment oldAppointment, Appointment newAppointment);
        void onAppointmentEditCancel();
        Map<String, Service> getServices();
        void hideActionbar();
        void showActionbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        _hostActivity.hideActionbar();
    }

}
