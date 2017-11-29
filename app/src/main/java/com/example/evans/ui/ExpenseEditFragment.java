package com.example.evans.ui;

import android.app.Activity;
import android.app.DialogFragment;
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
import com.example.evans.data.Expense;

import org.joda.time.LocalDate;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseEditFragment extends Fragment {

    private EditText _name;
    private EditText _price;
    private EditText _date;
    private static final String TAG = "ExpenseEditFragment";

    private Button _saveBtn;
    private Button _cancelBtn;

    private LocalDate _setDate;

    InteractionWithExpenseFragmentListener _hostActivity;

    public ExpenseEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_expense_edit, container, false);

        _name  = rootView.findViewById(R.id.etxt_expense_name);
        _price = rootView.findViewById(R.id.etxt_expense_price);
        _date = rootView.findViewById(R.id.etxt_expense_date);

        _saveBtn = rootView.findViewById(R.id.btn_edit_bar_save);
        _cancelBtn = rootView.findViewById(R.id.btn_edit_bar_cancel);

        // Create a expense and let the host activity know that a request
        // was made to create an appointment with the expense
        _date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.setTargetFragment(ExpenseEditFragment.this, 0);
                dateFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        // Create a expense on save click and return it to the host activity
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KeyboardControl.closeKeyboard(getActivity());
                Expense expense = createExpense();

                if (expense != null) {
                    _hostActivity.onExpenseEditFinish(expense);
                }
            }
        });

        // Cancel button click
        _cancelBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                KeyboardControl.closeKeyboard(getActivity());
                _hostActivity.onCancel();
            }
        });

        return rootView;
    }

    private Expense createExpense() {

        Expense newExpense = null;

        String name = _name.getText().toString();
        String price = _price.getText().toString();
        LocalDate setDate = LocalDate.now();

        //TODO validate data before saving

        // give a default value of N/A if the phone number field is empty
        // it's not a required field
        /*if (phone.isEmpty()) {
            Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: Invalid phone", Snackbar.LENGTH_LONG).show();
        }


        if (!name.isEmpty()) {
            // Email isn't required but if it's not empty then check to make sure it's a valid email
            if (email.isEmpty() || (!email.isEmpty() && isValidEmail(email))) {
                newExpense = new Expense(name, phone, setDate);
            } else {
                Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: Invalid email", Snackbar.LENGTH_LONG).show();
            }

        } else {
            Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: Name cannot be empty", Snackbar.LENGTH_LONG).show();
        }*/

        return newExpense;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        _hostActivity.hideActionbar();
    }


    @Override
    public void onStop() {
        super.onStop();
        _hostActivity.showActionbar();
    }*/

    /**
     * Ensures parent activity has implemented the InteractionWithCustomerViewFragment interface
     * @param activity: the host activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //check for implementation by trying to cast to an instance of the interface
        try {
            _hostActivity = (InteractionWithExpenseFragmentListener) activity;
        } catch (ClassCastException e) {
            // if fails, interface wasn't implemented
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithFinancialFragmentListener");
        }
    }

    //TODO implement in parent

    /**
     * interface to be implemented by parent activity to allow communication
     */
    public interface InteractionWithExpenseFragmentListener {
            void onExpenseEditFinish (Expense expense);
            void onCancel();
            //void hideActionbar();
            //void showActionbar();

    }

}
