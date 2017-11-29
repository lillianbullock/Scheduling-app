package com.example.evans.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.evans.R;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import com.example.evans.data.Appointment;
import com.example.evans.data.Sale;
import com.example.evans.data.Expense;


/**
 * {@link Fragment} subclass that creates a financial report.
 */
public class FinancialReportFragment extends Fragment
        implements DatePickerFragment.OnDateSetListener {

    private static final String TAG = "FinancialReportFragment";

    private EditText _profit;
    private EditText _cost;
    private EditText _net;
    private EditText _startDate;
    private EditText _endDate;
    private Button   _bttnCalculate;

    private List<Expense> _expenses;
    private List<Sale> _sales;
    private List<Appointment> _appointments;

    private double _profitTotal;
    private double _costTotal;
    private LocalDate _selectedStartDate;
    private LocalDate _selectedEndDate;

    private EditText _currentDateEdit;
    private char _current;

    private DateTimeFormatter _formatter;
    private InteractionWithFinancialReportFragmentListener _hostActivityListener;

    public FinancialReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_financial_report, container, false);

        _profit         = (EditText) rootView.findViewById(R.id.etxt_fin_rep_profit);
        _cost           = (EditText) rootView.findViewById(R.id.etxt_fin_rep_cost);
        _net            = (EditText) rootView.findViewById(R.id.etxt_fin_rep_net);
        _startDate      = (EditText) rootView.findViewById(R.id.etxt_fin_rep_start);
        _endDate        = (EditText) rootView.findViewById(R.id.etxt_fin_rep_end);
        _bttnCalculate  = (Button)   rootView.findViewById(R.id.bttn_fin_rep_cancel);

        _formatter = DateTimeFormat.forPattern("dd, MMMM yyyy");

        // On click listener for start date
        _startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                _current = 'a';

                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.setTargetFragment(FinancialReportFragment.this, 0);
                dateFragment.show(getFragmentManager(), "DatePicker");

                _currentDateEdit = _startDate;
            }
        });

        _endDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                _current = 'b';

                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.setTargetFragment(FinancialReportFragment.this, 0);
                dateFragment.show(getFragmentManager(), "DatePicker");

                _currentDateEdit = _endDate;
            }
        });

        // onClick Listener for cancel
        _bttnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO check if start date is before end date

                _expenses = _hostActivityListener.getExpenses(_selectedStartDate, _selectedEndDate);
                _sales = _hostActivityListener.getSales(_selectedStartDate, _selectedEndDate);
                _appointments = _hostActivityListener.getAppointments(_selectedStartDate, _selectedEndDate);

                _costTotal = 0.0;
                _profitTotal = 0.0;

                if (_expenses != null) {
                    for (Expense element : _expenses) {
                        _costTotal += element.getReport();
                    }
                } else {
                    Log.w(TAG, "expenses list returned from database is null");
                }

                if (_sales != null) {
                    for (Sale element : _sales) {
                        _profitTotal += element.getReport();
                    }
                } else {
                    Log.w(TAG, "sales list returned from database is null");
                }

                if (_appointments != null) {
                    for (Appointment element : _appointments) {
                        _profitTotal += element.getReport();
                    }
                } else {
                    Log.w(TAG, "appointment list returned from database is null");
                }

                double netProfit = _profitTotal - _costTotal;

                _profit.setText(Double.toString(_profitTotal));
                _cost.setText(Double.toString(_costTotal));
                _net.setText(Double.toString(netProfit));
            }
        });

        return rootView;
    }

    @Override
    public void onDateSet(LocalDate date) {
        _currentDateEdit.setText(_formatter.print(date));

        if(_current == 'a') {
            _selectedStartDate = date;
        }

        if(_current == 'b'){
            _selectedEndDate = date;
        }
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
            _hostActivityListener = (InteractionWithFinancialReportFragmentListener) activity;
        } catch (ClassCastException e) {
            // if fails, interface wasn't implemented
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithFinancialFragmentListener");
        }
    }

    /**
     * interface to be implemented by parent activity to allow communication
     */
    public interface InteractionWithFinancialReportFragmentListener {
        List<Expense> getExpenses(LocalDate beginDate, LocalDate endDate);
        List<Sale> getSales(LocalDate beginDate, LocalDate endDate);
        List<Appointment> getAppointments(LocalDate beginDate, LocalDate endDate);
    }
}
