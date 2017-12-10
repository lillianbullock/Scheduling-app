package com.example.evans.ui.ViewFragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.Appointment;
import com.example.evans.data.Customer;
import com.example.evans.data.MainController;
import com.example.evans.data.Service;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * {@link Fragment} subclass to view appointment data.
 */
public class AppointmentViewFragment extends Fragment {

    private Appointment _appointment;
    private Customer _customer;

    private Button _backAppointmentBtn;
    private Button _editAppointmentBtn;
    private CheckBox _checkAppointmentBox;

    private InteractionWithAppointmentViewFragmentListener _hostActivity;
    private MainController _mainController;

    private static final String TAG = "AppointmentView";

    public AppointmentViewFragment() {
        // Required empty public constructor
    }

    /**
     * sets the customer and appointment in the class
     * @param relatedCustomer : customer to be displayed
     */
    public void setRelatedCustomer(Customer relatedCustomer) {
        _customer = relatedCustomer;
    }

    public void setAppointment(Appointment appointment){
        if (appointment != null) {
            _appointment = appointment;
        } else {
            Log.e(TAG, "Invalid appointment passed to AppointmentView");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment_view, container, false);
        _mainController = MainController.getInstance();

        _customer = _mainController.getCustomerById(_appointment.getCustomerId());

        // collects the views that need to be changed to display stuff
        TextView name = view.findViewById(R.id.txt_appt_view_customer_name);
        TextView email = view.findViewById(R.id.txt_appt_view_customer_email);
        TextView phone = view.findViewById(R.id.txt_appt_view_customer_phone);
        TextView service = view.findViewById(R.id.txt_appointment_view_service);
        TextView price = view.findViewById(R.id.txt_appointment_view_price);
        TextView date = view.findViewById(R.id.txt_appointment_view_date);
        TextView time = view.findViewById(R.id.txt_appointment_view_time);

        _checkAppointmentBox = view.findViewById(R.id.chk_showed_up);
        _backAppointmentBtn = view.findViewById(R.id.btn_view_bar_back);
        _editAppointmentBtn = view.findViewById(R.id.btn_view_bar_edit);

        if (_customer == null) {
            _customer = new Customer("0", "Customer1", "email1", "000 000 0000", new LocalDate());
        }
        if (_appointment == null) {
            Service dummyService = new Service("Service1", "", 2.00);
            _appointment = new Appointment("Appointment1", new LocalDate(), new LocalTime(), "0", dummyService);
        }

        // sets the views with the data from passed classes
        name.setText(_customer.getName());
        email.setText(_customer.getEmail());
        phone.setText(_customer.getPhone());

        price.setText(String.format(Locale.US,"$%1.2f", _appointment.getService().getPrice()));

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd, MMMM yyyy");
        date.setText(formatter.print(_appointment.getDateObject()));

        DateTimeFormatter timeFormatter = DateTimeFormat.shortTime();
        time.setText(timeFormatter.print(_appointment.getTimeObject()));

        if (_appointment.isAttended() != false)
            _checkAppointmentBox.setChecked(_appointment.isAttended());


        if (_appointment != null){
            service.setText(_appointment.getService().getTitle());
        }

        _backAppointmentBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onAppointmentBackPressed(_appointment);
            }
        }));

        _editAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onEditAppointment(_appointment);
            }
        });

        _checkAppointmentBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_checkAppointmentBox.isChecked()) {
                    _appointment.setAttended(true);
                } else {
                    _appointment.setAttended(false);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        _hostActivity.hideActionbar();
    }

    /**
     * Ensures parent activity has implemented the InteractionWithAppointmentViewFragment interface.
     *
     * @param activity: the host activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //check for implementation by trying to cast to an instance of the interface
        try {
            _hostActivity = (InteractionWithAppointmentViewFragmentListener) activity;
        } catch (ClassCastException e) {
            // if fails, interface wasn't implemented
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithCustomerViewFragmentListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface InteractionWithAppointmentViewFragmentListener{
        void hideActionbar();
        void showActionbar();
        void onAppointmentBackPressed(Appointment anew);
        void onEditAppointment(Appointment appointment);
    }
}
