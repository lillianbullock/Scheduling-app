package com.example.evans.ui;

import android.app.DialogFragment;
import android.content.Context;
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

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * SalesEdit Fragment:
 * This Class will control the SalesEdit Fragment and the View information
 * that will be entered in the view from edit Fragment
 * {@link Fragment} subclass
 */
public class SalesEditFragment extends Fragment
        implements DatePickerFragment.OnDateSetListener {

    OnSubmitSalesEdit _hostActivity;

    private EditText _date;
    private Spinner _serviceSpinner;
    private EditText _servicePrice;

    private Button _btnSave;
    private Button _btnCancel;

    private Map<String, Service> _servicesMap;

    private Service _selectedService;
    private LocalDate _selectedDate;
    private Double _price;

    private static final String TAG = "SalesEditFragment";

    public SalesEditFragment() {
        // Required empty public constructor
    }


    /**
     * SalesEditFragment
     * This createView will handle the calling and view of Sales Edit Fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_edit, container, false);

        _date           = (EditText) view.findViewById(R.id.etxt_sale_date);
        _serviceSpinner = (Spinner) view.findViewById(R.id.spinner_sales_type);
        _servicePrice   = (EditText) view.findViewById(R.id.etxt_sale_price);
        _btnSave        = (Button) view.findViewById(R.id.btn_edit_bar_save);
        _btnCancel      = (Button) view.findViewById(R.id.btn_edit_bar_cancel);
        _servicesMap    = new HashMap<String, Service>();
        _servicesMap    = _hostActivity.getServices();





        // Set up the spinner for services list
        setupServicesSpinner();

        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
               // saleActivityCancel();

            }
        });

        // On click listener for date
        _date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                DialogFragment dateFragment = new DatePickerFragment();
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
            _selectedDate = date;
            _date.setText(date.toString());
    }

    @Override
    public void setDate(LocalDate date) {

    }

    private Sale createSale(){
        Sale sale = null;

        if(_selectedService == null) {return null;}
        if(_date == null) {return null;}
        if(_servicePrice == null) {return null;}

        //_price = _servicePrice.getText().;
        _price = 0.0;

        if(_selectedService != null && _date != null){
           // sale = new Sale(_selectedService, _price , _date)
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
     * One job: Populate our services spinner from the data we have in MainController
     */
    private void setupServicesSpinner() {

        List<String> servicesNames = new ArrayList<>(_hostActivity.getServices().keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_spinner_item,
                servicesNames);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        _serviceSpinner.setAdapter(adapter);

    }

    public interface OnSubmitSalesEdit {
        void onSaleEditFinish (Sale sale);
        void onCancel();
        Map<String, Service> getServices();
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
            _hostActivity = (OnSubmitSalesEdit) context;
        } catch (ClassCastException e) {
            Log.e("this is a error", "We have a problem in our Sales Edit Fragment");
            throw new ClassCastException(context.toString() + " must implement OnSubmitSaleEdit");
        }
    }

}
