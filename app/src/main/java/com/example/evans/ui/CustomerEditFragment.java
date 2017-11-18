package com.example.evans.ui;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.Customer;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.time.LocalDateTime;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerEditFragment extends Fragment {

    private EditText _name;
    private EditText _phone;
    private EditText _email;
    private EditText _otherInformation;
    private static final String TAG = "CustomerEditFragment";

    private Button _setAppointmentBtn;
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

        _name = (EditText) rootView.findViewById(R.id.etxt_name);
        _phone = (EditText) rootView.findViewById(R.id.etxt_phone);
        _email = (EditText) rootView.findViewById(R.id.etxt_email);

        _setAppointmentBtn = rootView.findViewById(R.id.btn_set_appt);
        _saveBtn = rootView.findViewById(R.id.btn_save_edit);


        // Create a customer and let the host activity know that a request
        // was made to create an appointment with the customer
        _setAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer customer = createCustomer();

                if (customer != null) {
                    _hostActivity.onCustomerEditFinish(customer);
                }
            }
        });

        // Create a customer on save click and return it to the host activity
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer customer = createCustomer();

                if (customer != null) {
                    _hostActivity.onCustomerEditFinish(customer);
                }

            }
        });

        return rootView;
    }

    private Customer createCustomer() {

        String id = String.valueOf(_hostActivity.getNextCustomerId());
        String name = _name.getText().toString();
        String phone = _phone.getText().toString();
        String email = _email.getText().toString();
        String otherInfo = _otherInformation.getText().toString();
        LocalDate currentDate = LocalDate.now();

        Customer newCustomer = new Customer(id, name, email, phone, currentDate);

        if (!name.isEmpty()) {

            // Email isn't required but if it's not empty then check to make sure it's a valid email
            if (email.isEmpty() || (!email.isEmpty() && isValidEmail(email))) {
                newCustomer = new Customer(id, name, email, phone, currentDate);
                return newCustomer;
            } else {
                Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: Invalid email", Snackbar.LENGTH_LONG).show();
            }

        } else {
                Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: Name cannot be empty", Snackbar.LENGTH_LONG).show();
        }

        // Return null if the customer data wasn't valid
        return null;
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
     *  isValid: Return true is the passed email string matches the specified
     *  regEx pattern, false otherwise
     */
    public static boolean isValidEmail(String email) {

        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        return (email.matches(EMAIL_REGEX));
    }

    /**
     * Construct a new customer and pass it to the host activity by calling it's
     * onAddAppointmentClick function. The host activity should know what to do.
     * We're assuming that it will call another fragment to add an appointment
     * with the customer data received.
     */
    public void onSetAppointmentClick() {

        // TODO - Implement
    }



    /**
     * Declare an interface that the activate that creates this fragment must implement. This interface will
     * handle when a new customer has been added
     */
    public interface OnSubmitCustomerEdit {

        void onCustomerEditFinish (Customer customer);
        void onAddAppointmentClickForCustomer(Customer customer);
        int getNextCustomerId();
        void hideActionbar();
        void showActionbar();

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
            Log.e(TAG, "The host activity did not implement OnSubmitCustomerEdit");
            throw new ClassCastException(context.toString() + " must implement OnSubmitCustomerEdit");
        }

    }

}
