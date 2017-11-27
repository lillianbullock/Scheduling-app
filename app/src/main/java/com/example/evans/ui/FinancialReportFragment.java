package com.example.evans.ui;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;

import com.example.evans.R;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinancialReportFragment extends Fragment
        implements DatePickerFragment.OnDateSetListener {

    private EditText _profit;
    private EditText _cost;
    private EditText _net;
    private EditText _startDate;
    private EditText _endDate;
    private Button _bttnCalculate;

    private EditText _currentDateEdit;

    private DateTimeFormatter _formatter;


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
        _bttnCalculate  = (Button) rootView.findViewById(R.id.bttn_fin_rep_cancel);

        _formatter = DateTimeFormat.forPattern("dd, MMMM yyyy");

        // On click listener for date
        _startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.setTargetFragment(FinancialReportFragment.this, 0);
                dateFragment.show(getFragmentManager(), "DatePicker");

                _currentDateEdit = _startDate;
            }
        });

        _endDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
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
                // todo calculate the report and display

                _profit.setText("Profit calculation");
                _cost.setText("Cost calculation");
                _net.setText("Net calculation");
            }
        });

        return rootView;
    }

    @Override
    public void onDateSet(LocalDate date) {
        _currentDateEdit.setText(_formatter.print(date));
    }

    @Override
    public void setDate(LocalDate date) {

    }
}
