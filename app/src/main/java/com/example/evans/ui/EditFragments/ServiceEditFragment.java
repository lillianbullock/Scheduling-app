package com.example.evans.ui.EditFragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.evans.R;
import com.example.evans.data.Service;
import com.example.evans.ui.KeyboardControl;


/**
 * {@link Fragment} subclass to edit service data.
 */
public class ServiceEditFragment extends Fragment {

    private EditText _title;
    private EditText _description;
    private EditText _price;

    private Service _selectedService;

    private Button _saveBtn;
    private Button _cancelBtn;

    // for closing the keyboard
    private static final int DONE = EditorInfo.IME_ACTION_DONE;

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

        _saveBtn = rootView.findViewById(R.id.btn_edit_bar_save);
        _cancelBtn = rootView.findViewById(R.id.btn_edit_bar_cancel);


        initializeServiceDetails();

        // Set the click lister
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                KeyboardControl.closeKeyboard(getActivity());
                Service service = createService();

                if (service != null) {
                    _hostActivity.onServiceEditFinish(service);
                } else {
                    Snackbar.make(getActivity().findViewById(R.id.content_frame),
                            "Invalid service: Title and price cannot be empty", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        // onCancel
        _cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardControl.closeKeyboard(getActivity());
                _hostActivity.onServiceCancel();
            }
        });

        return rootView;
    }


    private void closeKeyboard() {

        try {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // Do nothing
        }
    }

    private Service createService() {

        if (_title.getText().toString().isEmpty() || _price.getText().toString().isEmpty()) {
            return null;
        }

        Service newService = null;

        String title = _title.getText().toString();
        String description = _description.getText().toString();
        double price = convertPriceStringToDouble(_price.getText().toString());

        newService = new Service(title, description, price);

        return newService;

    }

    /**
     *  deals with if they put a dollar sign or words in the price text box
     */
    double convertPriceStringToDouble(String priceStr) {
        return Double.parseDouble(priceStr.replaceAll("[^0-9.]", ""));
    }

    /**
     * to initialize the customer details for edit
     */
    private void initializeServiceDetails() {

        if (_selectedService != null) {
            _title.setText(_selectedService.getTitle());
            _price.setText(_selectedService.getPrice().toString());
            _description.setText(_selectedService.getDescription());
        }
    }


    /**
     * when an existing goal is called to edit
     * @param service passes to set up existing
     */
    public void setExistingService(Service service){
        _selectedService = service;
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
     * Override onAttach to make sure that the container activity has implemented the callback we specified in
     * our interface
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // make sure that the container class implemented our interface. If it did then it can be casted
        // if not then we know it did not therefore throw an error
        try {
            _hostActivity = (ServiceEditFragment.OnSubmitServiceEdit) activity;
        } catch (ClassCastException e) {
            //they refused to honor the contract!!
            throw new ClassCastException(activity.toString() + " must implement OnSubmitServiceEdit");
        }
    }



    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface OnSubmitServiceEdit {
        void onServiceEditFinish (Service service);
        void onServiceCancel();
        void hideActionbar();
        void showActionbar();
    }

}
