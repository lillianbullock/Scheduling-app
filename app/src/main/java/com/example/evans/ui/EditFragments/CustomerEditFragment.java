package com.example.evans.ui.EditFragments;

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

import com.example.evans.R;
import com.example.evans.data.Customer;
import com.example.evans.ui.KeyboardControl;

import org.joda.time.LocalDate;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerEditFragment extends Fragment {

    private EditText _name;
    private EditText _phone;
    private EditText _email;
    private static final String TAG = "CustomerEditFragment";

    //private Button _setAppointmentBtn;
    private Button _saveBtn;
    private Button _cancelBtn;


    // define a new instance of OnSubmitCustomerEdit that would hold an instance of the host activity and will
    // be able to call the methods that we've demanded to be created
    private OnSubmitCustomerEdit _hostActivity;


    public CustomerEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_customer_edit, container, false);

        _name  = rootView.findViewById(R.id.etxt_name);
        _phone = rootView.findViewById(R.id.etxt_phone);
        _email = rootView.findViewById(R.id.etxt_email);

        //_setAppointmentBtn = rootView.findViewById(R.id.btn_set_appt);
        _saveBtn = rootView.findViewById(R.id.btn_edit_bar_save);
        _cancelBtn = rootView.findViewById(R.id.btn_edit_bar_cancel);



        // Create a customer and let the host activity know that a request
        // was made to create an appointment with the customer
        /*_setAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KeyboardControl.closeKeyboard(getActivity());
                Customer customer = createCustomer();

                if (customer != null) {
                    _hostActivity.onCustomerEditFinish(customer);
                }
            }
        });*/

        // Create a customer on save click and return it to the host activity
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KeyboardControl.closeKeyboard(getActivity());
                Customer customer = createCustomer();

                if (customer != null) {
                    _hostActivity.onCustomerEditFinish(customer);
                }

            }
        });

        // Cancel button click
        _cancelBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                KeyboardControl.closeKeyboard(getActivity());
                _hostActivity.onCancelCustomerEdit();
            }
        });

        return rootView;
    }

    private Customer createCustomer() {

        Customer newCustomer = null;

        String id = "";
        String name = _name.getText().toString();
        String phone = _phone.getText().toString();
        String email = _email.getText().toString();
        LocalDate currentDate = LocalDate.now();

        // give a default value of N/A if the phone number field is empty
        // it's not a required field
        if (phone.isEmpty()) {
            phone = "N/A";
        }


        if (!name.isEmpty()) {
            // Email isn't required but if it's not empty then check to make sure it's a valid email
            if (email.isEmpty() || (!email.isEmpty() && isValidEmail(email))) {
                newCustomer = new Customer(id, name, email, phone, currentDate);
            } else {
                Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: Invalid email", Snackbar.LENGTH_LONG).show();
            }

        } else {
                Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: Name cannot be empty", Snackbar.LENGTH_LONG).show();
        }

        return newCustomer;
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
    private boolean isValidEmail(String email) {

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
        //void onAddAppointmentClickForCustomer(Customer customer);
        void onCancelCustomerEdit();
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
