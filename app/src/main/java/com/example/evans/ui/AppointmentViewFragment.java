package com.example.evans.ui;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
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
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentViewFragment extends Fragment {

    private Appointment _appointment;
    private Customer _customer;
    private InteractionWithAppointmentViewFragmentListener _hostListener;

    public void setObjects(Appointment appointment, Customer relatedCustomer) {
        _appointment = appointment;
        _customer = relatedCustomer;
    }


    public AppointmentViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment_view, null);

        if (_customer == null) {
            _customer = new Customer("0", "Customer1", "email1", "000 000 0000", new LocalDate());
        }
        if (_appointment == null) {
            Service dummyService = new Service("Service1", "", 2.00);
            _appointment = new Appointment("Appointment1", new LocalDate(), new LocalTime(), "0", dummyService);
        }
        TextView name = (TextView) view.findViewById(R.id.txt_apptv_customer_name);
        TextView email = (TextView) view.findViewById(R.id.txt_apptv_customer_email);
        TextView phone = (TextView) view.findViewById(R.id.txt_apptv_customer_phone);
        TextView service = (TextView) view.findViewById(R.id.txt_appointment_view_service);
        TextView price = (TextView) view.findViewById(R.id.txt_appointment_view_price);
        TextView date = (TextView) view.findViewById(R.id.txt_appointment_view_date);
        TextView time = (TextView) view.findViewById(R.id.txt_appointment_view_time);
        CheckBox showedUp = (CheckBox) view.findViewById(R.id.chk_showed_up);

        name.setText(_customer.getName());
        email.setText(_customer.getEmail());
        phone.setText(_customer.getPhone());
        service.setText(_appointment.getService().getTitle());
        price.setText("$" + Double.toString(_appointment.getService().getPrice()));
        // TODO implement date & time formatting strings
        date.setText(_appointment.getDate().toString());
        time.setText(_appointment.getDate().toString());
        // TODO implement this properly
        if (_appointment.isSucceed() != null)
            showedUp.setChecked(_appointment.isSucceed());

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * We want to make sure that the activity that uses this fragment
     * has implemented our InteractionWithCustomerViewFragment interface. We
     * check for this by trying to cast the activity to an instance of
     * InteractionWithCustomerViewFragment, if it fails then that means that the
     * interface wasn't implemented. We have to say something about that!
     * @param activity: the host activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            _hostListener = (InteractionWithAppointmentViewFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithCustomerViewFragmentListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface InteractionWithAppointmentViewFragmentListener{
        //Appointment getViewAppointment();
    }
}
