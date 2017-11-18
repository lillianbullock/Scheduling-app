package com.example.evans.ui;


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
 * A simple {@link Fragment} subclass.
 */
public class CustomerViewFragment extends Fragment {

    Customer _customer;

    public CustomerViewFragment() {
        // Required empty public constructor
    }

    public void setCustomer(Customer customer) {
        _customer = customer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_view, null);

        //_customer = new Customer("0", "Customer1", "email1", "000 000 0000", new LocalDateTime());

        TextView name = (TextView) view.findViewById(R.id.txt_view_name);
        TextView email = (TextView) view.findViewById(R.id.txt_view_email);
        TextView phone = (TextView) view.findViewById(R.id.txt_view_phone);

        name.setText(_customer.getName());
        email.setText(_customer.getEmail());
        phone.setText(_customer.getPhone());

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface InteractionWithCustomerViewFragmentListener{
        Customer getViewCustomer();
    }



}
