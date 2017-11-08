package com.example.evans.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.evans.R;
import com.example.evans.data.Customer;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerEditFragment extends Fragment {

    private EditText _name;
    private EditText _phone;
    private EditText _email;
    private EditText _otherInformation;

    private Button _setAppointmenrBtn;
    private Button _saveBtn;


    // define a new instance of OnSubmitCustomerEdit that would hold an instance of the host activity and will
    // be able to call the methods that we've demanded to be created
    OnSubmitCustomerEdit _hostActivity;


    public CustomerEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_customer_edit, container, false);

        _name = (EditText) rootView.findViewById(R.id.etxt_customer_name);
        _phone = (EditText) rootView.findViewById(R.id.etxt_customer_phone);
        _email = (EditText) rootView.findViewById(R.id.etxt_customer_email);
        _otherInformation = (EditText) rootView.findViewById(R.id.etxt_other_notes);

        _setAppointmenrBtn = rootView.findViewById(R.id.btn_set_appt);
        _saveBtn = rootView.findViewById(R.id.btn_save_edit);


        // Set the click lister for both buttons
        _setAppointmenrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSetAppointmentClick();
            }
        });

        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveCustomerClick();
            }
        });


        return rootView;
    }


    /**
     * Construct a customer and pass it to the host activity by calling it's
     * onCustomerEditFinish function
     */
    public void onSaveCustomerClick() {

        // TODO - Implement
    }

    /**
     * Construct a new customer and pass it to the host activity by calling it's
     * onAddAppointmentClick function. The host activity should know waht to do.
     * We're assuming that it will call another fragment to add an appointment
     * with the customer data received.
     */
    public void onSetAppointmentClick() {

        // TODO - Implement
    }



    /**
     * Declare an interface that the activate that creates this fragment must implemnent. This interface will
     * handle when a new customer has been added
     */
    public interface OnSubmitCustomerEdit {

        void onCustomerEditFinish (Customer customer);
        void onAddAppointmentClick(Customer customer);

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
            _hostActivity = (OnSubmitCustomerEdit) context;
        } catch (ClassCastException e) {
            /* they refused to honor the contract!!*/
            throw new ClassCastException(context.toString() + " must implement OnSubmitCustomerEdit");
        }

    }

}
