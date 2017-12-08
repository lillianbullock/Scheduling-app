package com.example.evans.ui.ViewFragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Appointment;
import com.example.evans.data.Customer;
import com.example.evans.data.Service;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * {@link Fragment} subclass to view appointment data.
 */
public class AppointmentViewFragment extends Fragment {

    private Appointment _appointment;
    private Customer _customer;
    private InteractionWithAppointmentViewFragmentListener _hostActivity;

    private static final String TAG = "AppointmentView";

    public AppointmentViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment_view, container, false);

        //todo put error messages bc these should not be implemented to null
        if (_customer == null) {
            _customer = new Customer("0", "Customer1", "email1", "000 000 0000", new LocalDate());
        }
        if (_appointment == null) {
            Service dummyService = new Service("Service1", "", 2.00);
            _appointment = new Appointment("Appointment1", new LocalDate(), new LocalTime(), "0", dummyService);
        }

        // collects the views that need to be changed to display stuff
        TextView name = view.findViewById(R.id.txt_appt_view_customer_name);
        TextView email = view.findViewById(R.id.txt_appt_view_customer_email);
        TextView phone = view.findViewById(R.id.txt_appt_view_customer_phone);
        TextView service = view.findViewById(R.id.txt_appointment_view_service);
        TextView price = view.findViewById(R.id.txt_appointment_view_price);
        TextView date = view.findViewById(R.id.txt_appointment_view_date);
        TextView time = view.findViewById(R.id.txt_appointment_view_time);
        CheckBox showedUp = view.findViewById(R.id.chk_showed_up);

        // sets the views with the data from passed classes
        name.setText(_customer.getName());
        email.setText(_customer.getEmail());
        phone.setText(_customer.getPhone());

        if (_appointment != null){
            service.setText(_appointment.getService().getTitle());
        }


        price.setText("$" + Double.toString(_appointment.getService().getPrice()));
        // TODO implement date & time formatting strings
        date.setText(_appointment.getDate().toString());
        time.setText(_appointment.getDate().toString());
        // TODO implement this properly
        if (_appointment.isAttended() != null)
            showedUp.setChecked(_appointment.isAttended());

        return view;
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
        //Appointment getViewAppointment();
    }
}
