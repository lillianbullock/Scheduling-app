package com.example.evans.ui.ViewFragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Customer;

/**
 * {@link Fragment} subclass to view the customer data.
 */
public class CustomerViewFragment extends Fragment {

    private Customer _customer;
    private FloatingActionButton _setAppointmentBttn;
    private Button _backBttn;
    private Button _editCustomerBttn;
    private Button _seeAllAppointmentsBttn;

    private InteractionWithCustomerViewFragmentListener _hostActivity;

    public CustomerViewFragment() {
        // Required empty public constructor
    }

    /**
     * sets the customer within the class
     * @param customer customer to be displayed
     */
    public void setCustomer(Customer customer) { _customer = customer; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_view, container, false);

        // collects the views to display the data
        TextView name = view.findViewById(R.id.txt_view_name);
        TextView email = view.findViewById(R.id.txt_view_email);
        TextView phone = view.findViewById(R.id.txt_view_phone);
        _setAppointmentBttn = view.findViewById(R.id.customer_add_appointment_btn);
        _backBttn = view.findViewById(R.id.btn_view_bar_back);
        _editCustomerBttn = view.findViewById(R.id.btn_view_bar_edit);
        _seeAllAppointmentsBttn = view.findViewById(R.id.bttn_see_all_appt);


        //sets views to the customer data
        name.setText(_customer.getName());
        email.setText(_customer.getEmail());
        phone.setText(_customer.getPhone());

        // Take Customer that we have and Connect it to the edit for appointmentFragment
        _setAppointmentBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onSetAppointmentForCustomer(_customer);
            }
        });

        _editCustomerBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onEditCustomer(_customer);
            }
        });

        _seeAllAppointmentsBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onListAppointments(_customer);
            }
        });

        _backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onBackPressed();
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
     * Ensures parent activity has implemented the InteractionWithCustomerViewFragment interface
     * @param activity: the host activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //check for implementation by trying to cast to an instance of the interface
        try {
            _hostActivity = (InteractionWithCustomerViewFragmentListener) activity;
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
    public interface InteractionWithCustomerViewFragmentListener{
        void hideActionbar();
        void onBackPressed();

        void onSetAppointmentForCustomer(Customer customer);
        void onEditCustomer(Customer customer);
        void onListAppointments(Customer customer);
    }
}
