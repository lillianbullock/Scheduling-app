package com.example.evans.ui;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Customer;

import org.joda.time.LocalDateTime;


/**
 * {@link Fragment} subclass to view the customer data.
 */
public class CustomerViewFragment extends Fragment {

    private Customer _customer;
    private InteractionWithCustomerViewFragmentListener _hostListener;

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
        TextView name = (TextView) view.findViewById(R.id.txt_view_name);
        TextView email = (TextView) view.findViewById(R.id.txt_view_email);
        TextView phone = (TextView) view.findViewById(R.id.txt_view_phone);

        //sets views to the customer data
        name.setText(_customer.getName());
        email.setText(_customer.getEmail());
        phone.setText(_customer.getPhone());

        return view;
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
            _hostListener = (InteractionWithCustomerViewFragmentListener) activity;
        } catch (ClassCastException e) {
            // if fails, interface wasn't implemented
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithCustomerViewFragmentListener");
        }
    }

    /**
     * interface to be implemented by parent activity to allow communication
     */
    public interface InteractionWithCustomerViewFragmentListener{
        Customer getViewCustomer();
    }
}
