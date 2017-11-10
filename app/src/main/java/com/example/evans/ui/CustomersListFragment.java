package com.example.evans.ui;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.evans.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomersListFragment extends Fragment {

    FloatingActionButton _addFloatingBtn;
    View _rootView;  // how we can get access to view elements


    public CustomersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_customers_list, container, false);

        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_add_btn);

        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateCustomer(container);
            }
        });

        return _rootView;
    }




    /**
     * Interface that should be implemented by the container the activity that
     * creates this fragment. This method should be invoked when the user clicks on the plus button
     */
    public interface CustomerChangeOperation {
        public void createCustomer();
        public void onClickCustomer();
    }

    public void onCreateCustomer(ViewGroup parentActivity) {
        Toast.makeText(getActivity(), "You tried to add a new customer", Toast.LENGTH_SHORT).show();
    }




}
