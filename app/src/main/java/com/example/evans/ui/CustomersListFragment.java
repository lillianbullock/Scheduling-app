package com.example.evans.ui;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.Customer;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomersListFragment extends Fragment {

    FloatingActionButton _addFloatingBtn;
    View _rootView;  // how we can get access to view elements
    InteractionWithCustomerFragmentListener _hostActivityListener;


    public CustomersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_customers_list, container, false);

        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_add_btn);

        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateCustomer();
            }
        });

        return _rootView;
    }

    /**
     * We want to make sure that the activity that uses this fragment
     * has implemented our InteractionWithCustomerFragment interface. We
     * check for this by trying to cast the activity to an instance of
     * InteractionWithCustomerFragment, if it fails then that means that the
     * interface wasn't implemented. We have to say something about that!
     * @param activity: the host activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            _hostActivityListener = (InteractionWithCustomerFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithCustomerFragmentListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface InteractionWithCustomerFragmentListener{
        void onClickCustomer(Customer customer);
        void onAddCustomer();
    }


    /**
     * For now we just want to let the host activity tak care of it by calling it's
     * onAddCustomer method it better had implemented our interface
     */
    public void onCreateCustomer() {
        Toast.makeText(getActivity(), "You tried to add a new customer", Toast.LENGTH_SHORT).show();
        _hostActivityListener.onAddCustomer();
    }




}
