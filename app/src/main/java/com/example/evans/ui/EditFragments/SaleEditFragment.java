package com.example.evans.ui.EditFragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.evans.R;
import com.example.evans.data.Sale;
import com.example.evans.data.Service;
import com.example.evans.ui.DialogFragements.DatePickerFragment;
import com.example.evans.ui.KeyboardControl;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * {@link Fragment} subclass to edit sales data.
 */
public class SaleEditFragment extends Fragment
        implements DatePickerFragment.OnDateSetListener {

    private OnSubmitSaleEdit _hostActivity;

    private EditText _date;
    private Spinner _serviceSpinner;
    private EditText _servicePrice;

    private Button _btnSave;
    private Button _btnCancel;

    private Map<String, Service> _servicesMap;

    private Service _selectedService;
    private LocalDate _selectedDate;
    private Double _price;

    private static final String TAG = "SaleEditFragment";

    public SaleEditFragment() {
        // Required empty public constructor
    }


    /**
     * SaleEditFragment
     * This createView will handle the calling and view of Sale Edit Fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sale_edit, container, false);

        _date           = (EditText) view.findViewById(R.id.etxt_sale_date);
        _serviceSpinner = (Spinner) view.findViewById(R.id.spinner_sale_type);
        _servicePrice   = (EditText) view.findViewById(R.id.etxt_sale_price);
        _btnSave        = (Button) view.findViewById(R.id.btn_edit_bar_save);
        _btnCancel      = (Button) view.findViewById(R.id.btn_edit_bar_cancel);
        _servicesMap    = new HashMap<>();
        _servicesMap    = _hostActivity.getServices();





        // Set up the spinner for services list
        setupServicesSpinner();

        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KeyboardControl.closeKeyboard(getActivity());
                Sale newSale = createSale();

                if (newSale != null){
                    _hostActivity.onSaleEditFinish(newSale);
                } else {
                    Snackbar.make(getActivity().findViewById(R.id.content_frame),
                            "ERROR: Invalid Sale data. Please review your input", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        // onClick Listener for cancel
        _btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardControl.closeKeyboard(getActivity());
               _hostActivity.onSaleCancel();

            }
        });

        // On click listener for date
        _date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.setTargetFragment(SaleEditFragment.this, 0);
                dateFragment.show(getFragmentManager(), "DatePicker");
            }
        });


        // Listen for when an item is selected and set the price accordingly
        _serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Service currentService = _servicesMap.get(adapterView.getSelectedItem().toString());
                _selectedService = currentService;
                Double price = currentService.getPrice();
                String localePrice =  String.format(Locale.US,"%1.2f", price);
                _servicePrice.setText(localePrice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                _servicePrice.setText("$0.00");
            }
        });

        return view;
    }

    @Override
    public void onDateSet(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd, MMMM yyyy");
        _selectedDate = date;
        _date.setText(formatter.print(date));
    }

    private Sale createSale(){
        Sale sale = null;

        if(_selectedService == null) {return null;}
        if(_selectedDate == null) {return null;}
        if(_servicePrice == null) {return null;}

        String date = _date.getText().toString();

        _price = Double.parseDouble(_servicePrice.getText().toString());

        if(_selectedService != null && _date != null){
           sale = new Sale(_selectedService, _price , _selectedDate);
        }

        return sale;
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
     * Populate our services spinner from the data we have in MainController
     */
    private void setupServicesSpinner() {

        List<String> servicesNames = new ArrayList<>(_hostActivity.getServices().keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>( this.getActivity(),
                android.R.layout.simple_spinner_item, servicesNames);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        _serviceSpinner.setAdapter(adapter);
    }

    /**
     * Override onAttach to make sure that the container activity has implemented the callback we specified in
     * our interface
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // make sure that the container class implemented our interface. If it did then it can be casted
        // if not then we know it did not therefore throw an error
        try {
            _hostActivity = (OnSubmitSaleEdit) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "We have a problem in our Sale Edit Fragment");
            throw new ClassCastException(activity.toString() + " must implement OnSubmitSaleEdit");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface OnSubmitSaleEdit {
        void onSaleEditFinish (Sale sale);
        void onSaleCancel();
        Map<String, Service> getServices();
        void hideActionbar();
        void showActionbar();
    }

}
