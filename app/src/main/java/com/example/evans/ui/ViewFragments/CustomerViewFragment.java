package com.example.evans.ui.ViewFragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
    private FloatingActionButton _setAppointmentBtn;
    private Button _backBtn;
    private Button _editCustomerBtn;

    private InteractionWithCustomerViewFragmentListener _hostActivity;

    public CustomerViewFragment() {
        // Required empty public constructor
    }

    /**
     * sets the customer within the class
     * @param customer
     */
    public void setCustomer(Customer customer) { _customer = customer; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_view, container, false);

        //_customer = new Customer("0", "Customer1", "email1", "000 000 0000", new LocalDateTime());

        // collects the views to display the data
        TextView name = view.findViewById(R.id.txt_view_name);
        TextView email = view.findViewById(R.id.txt_view_email);
        TextView phone = view.findViewById(R.id.txt_view_phone);
        _setAppointmentBtn = view.findViewById(R.id.customer_add_appointment_btn);
        _backBtn = view.findViewById(R.id.btn_view_bar_back);
        _editCustomerBtn = view.findViewById(R.id.btn_view_bar_edit);


        //sets views to the customer data
        name.setText(_customer.getName());
        email.setText(_customer.getEmail());
        phone.setText(_customer.getPhone());

        // Take Customer that we have and Connect it to the edit for appointmentFragment
        _setAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onSetAppointmentForCustomer(_customer);
            }
        });

        _setAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_customer != null){
                    _hostActivity.onEditCustomer(_customer);
                } else {
                    Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: Invalid customer", Snackbar.LENGTH_LONG).show();

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

    @Override
    public void onStop() {
        super.onStop();
        _hostActivity.showActionbar();
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
        void showActionbar();

        Customer getViewCustomer();
        void onSetAppointmentForCustomer(Customer customer);
        void onEditCustomer(Customer customer);
    }
}
