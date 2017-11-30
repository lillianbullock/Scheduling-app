package com.example.evans.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

        _title = (EditText) rootView.findViewById(R.id.etxt_service_title);
        _description = rootView.findViewById(R.id.etxt_service_description);
        _price = rootView.findViewById(R.id.etxt_service_price);

        _saveBtn = rootView.findViewById(R.id.btn_edit_bar_save);
        _cancelBtn = rootView.findViewById(R.id.btn_edit_bar_cancel);





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

    /**
     * Declare an interface that the activate that creates this fragment must implement. This interface will
     * handle when a new service has been added
     */
    public interface OnSubmitServiceEdit {
        void onServiceEditFinish (Service service);
        void onServiceCancel();
        void hideActionbar();
        void showActionbar();
    }

}
