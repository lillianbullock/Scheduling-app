package com.example.evans.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.Service;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceEditFragment extends Fragment {

    private EditText _title;
    private EditText _description;
    private EditText _price;

    private Button _saveBtn;


    // define a new instance of OnSubmitServiceEdit that would hold an instance of the host activity and will
    // be able to call the methods that we've demanded to be created
    ServiceEditFragment.OnSubmitServiceEdit _hostActivity;


    public ServiceEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_service_edit, container, false);

        _title = rootView.findViewById(R.id.etxt_service_title);
        _description = rootView.findViewById(R.id.etxt_service_description);
        _price = rootView.findViewById(R.id.etxt_service_price);

        _saveBtn = rootView.findViewById(R.id.btn_service_edit_save);

        // Set the click lister
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createService();
            }
        });


        return rootView;
    }

    private void createService() {
        // TODO Check for errors and prompt user appropriately

        String title = _title.getText().toString();
        String description = _description.getText().toString();
        String priceStr = _price.getText().toString();

        double price = convertPriceStringToDouble(priceStr);

        // TODO check if redundant service
        Service newService = new Service(title, description, price);

        _hostActivity.onServiceEditFinish(newService);

    }

    /**
     *  deals with if they put a dollar sign or words in the price text box
     */
    double convertPriceStringToDouble(String priceStr) {
        return Double.parseDouble(priceStr.replaceAll("[^0-9.]", ""));
    }

    /**
     * Construct a service and pass it to the host activity by calling it's
     * onServiceEditFinish function
     */
    public void onSaveServiceClick() {

        // TODO - Implement
    }

    /**
     * Declare an interface that the activate that creates this fragment must implemnent. This interface will
     * handle when a new service has been added
     */
    public interface OnSubmitServiceEdit {
        void onServiceEditFinish (Service service);
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
            _hostActivity = (ServiceEditFragment.OnSubmitServiceEdit) context;
        } catch (ClassCastException e) {
            //they refused to honor the contract!!
            throw new ClassCastException(context.toString() + " must implement OnSubmitServiceEdit");
        }

    }

}
