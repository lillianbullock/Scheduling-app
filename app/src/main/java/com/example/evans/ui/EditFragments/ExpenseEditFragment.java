package com.example.evans.ui.EditFragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.evans.R;
import com.example.evans.data.Expense;
import com.example.evans.ui.DialogFragements.DatePickerFragment;
import com.example.evans.ui.KeyboardControl;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseEditFragment extends Fragment
        implements DatePickerFragment.OnDateSetListener{

    private EditText _name;
    private EditText _price;
    private EditText _date;
    private static final String TAG = "ExpenseEditFragment";

    private Button _saveBtn;
    private Button _cancelBtn;

    private LocalDate _setDate;

    private InteractionWithExpenseEditFragmentListener _hostActivity;

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

        // Create a expense and let the host activity know
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
                _hostActivity.onExpenseCancel();
            }
        });

        return rootView;
    }

    private Expense createExpense() {

        Expense newExpense = null;

        String name = _name.getText().toString();
        Double price = Double.parseDouble(_price.getText().toString());
        LocalDate setDate = _setDate;

        //TODO is there a better way to validate these?
        if (_name.toString().isEmpty() || _price.toString().isEmpty() || _date.toString().isEmpty()) {
            Snackbar.make(getActivity().findViewById(R.id.content_frame),
                    "ERROR: Please fill in all fields", Snackbar.LENGTH_LONG).show();
        } else{
            newExpense = new Expense(name, price, setDate);
        }

        return newExpense;
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
            _hostActivity = (InteractionWithExpenseEditFragmentListener) activity;
        } catch (ClassCastException e) {
            // if fails, interface wasn't implemented
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithFinancialFragmentListener");
        }
    }

    @Override
    public void onDateSet(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd, MMMM yyyy");
        _setDate = date;
        _date.setText(formatter.print(date));
    }

    /**
     * interface to be implemented by parent activity to allow communication
     */
    public interface InteractionWithExpenseEditFragmentListener {
            void onExpenseEditFinish (Expense expense);
            void onExpenseCancel();
            void hideActionbar();
            void showActionbar();

    }

}
