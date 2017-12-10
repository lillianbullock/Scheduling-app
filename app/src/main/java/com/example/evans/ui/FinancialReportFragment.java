package com.example.evans.ui;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Locale;

import com.example.evans.data.Appointment;
import com.example.evans.data.MainController;
import com.example.evans.data.OnGetDataListener;
import com.example.evans.data.Sale;
import com.example.evans.data.Expense;
import com.example.evans.ui.DialogFragements.DatePickerFragment;
import com.example.evans.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

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

    private MainController _mainController;
    private ReportFragmentListener _hostActivity;

    private double _profitTotal;
    private double _costTotal;
    private LocalDate _selectedStartDate;
    private LocalDate _selectedEndDate;
    private static final String TITLE = "Financial Report";

    private EditText _currentDateEdit;
    private char _current;

    private DateTimeFormatter _formatter;

    public FinancialReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_financial_report, container, false);

        _profit         = rootView.findViewById(R.id.etxt_fin_rep_profit);
        _cost           = rootView.findViewById(R.id.etxt_fin_rep_cost);
        _net            = rootView.findViewById(R.id.etxt_fin_rep_net);
        _startDate      = rootView.findViewById(R.id.etxt_fin_rep_start);
        _endDate        = rootView.findViewById(R.id.etxt_fin_rep_end);
        _bttnCalculate  = rootView.findViewById(R.id.bttn_fin_rep_cancel);
        _mainController = MainController.getInstance();

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
                if (_selectedStartDate.isBefore(_selectedEndDate)){

                    loadExpenses();
                    loadSales();
                    loadAppointments();

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

                    _profit.setText(String.format(Locale.US,"$%1.2f", _profitTotal));
                    _cost.setText(String.format(Locale.US,"$%1.2f", _costTotal));
                    _net.setText(String.format(Locale.US,"$%1.2f", netProfit));

                    /*_profit.setText(Double.toString(_profitTotal));
                    _cost.setText(Double.toString(_costTotal));
                    _net.setText(Double.toString(netProfit));*/

                } else {
                    Snackbar.make(getActivity().findViewById(R.id.content_frame), "ERROR: End date cannot be before the begin date", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }

    private void loadSales() {

        _mainController.getSalesBetween(_selectedStartDate, _selectedEndDate, new OnGetDataListener() {
            @Override
            public void onDataLoadStarted() {

            }

            @Override
            public void onDataLoadSucceed(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    _sales.add(child.getValue(Sale.class));
                }
            }

            @Override
            public void onDataLoadFailed(DatabaseError databaseError) {
                Snackbar.make(getActivity().findViewById(R.id.content_frame),
                        "ERROR: Unable to load data. Please check your network connection",
                        Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    private void loadAppointments() {

        _mainController.getSalesBetween(_selectedStartDate, _selectedEndDate, new OnGetDataListener() {
            @Override
            public void onDataLoadStarted() {

            }

            @Override
            public void onDataLoadSucceed(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    _appointments.add(child.getValue(Appointment.class));
                }
            }

            @Override
            public void onDataLoadFailed(DatabaseError databaseError) {
                Snackbar.make(getActivity().findViewById(R.id.content_frame),
                        "ERROR: Unable to load data. Please check your network connection",
                        Snackbar.LENGTH_SHORT).show();

            }
        });

    }


    private void loadExpenses() {

        _mainController.getExpensesBetween(_selectedStartDate, _selectedEndDate, new OnGetDataListener() {
            @Override
            public void onDataLoadStarted() {

            }

            @Override
            public void onDataLoadSucceed(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    _expenses.add(child.getValue(Expense.class));
                }
            }

            @Override
            public void onDataLoadFailed(DatabaseError databaseError) {
                Snackbar.make(getActivity().findViewById(R.id.content_frame),
                        "ERROR: Unable to load data. Please check your network connection",
                        Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        _hostActivity.showActionbar();
        _hostActivity.setAppbarTitle(TITLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            _hostActivity = (ReportFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ReportFragmentListener");
        }
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

    public interface ReportFragmentListener {
        void showActionbar();
        void setAppbarTitle(String title);
    }
}
