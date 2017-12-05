package com.example.evans.ui.ListFragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.evans.R;
import com.example.evans.data.Customer;
import com.example.evans.ui.Adapters.CustomerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerListFragment extends Fragment {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements
    private CustomerListFragmentListener _hostActivityListener;

    public CustomerListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_customer_list, container, false);

        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_add_btn);

        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateCustomer();
            }
        });

        //setting arrayAdapter
        ListView simpleList;
        ArrayList<Customer> customerList = new ArrayList<>();

        super.onCreate(savedInstanceState);

        Customer test1 = new Customer();
        test1.setName("testName");
        customerList.add(test1);

        Customer test2 = new Customer();
        test2.setName("testName2");
        customerList.add(test2);

        //TODO put this back when app actually gets data from database (and take out dummy data above)
        //customerList = (ArrayList) _hostActivityListener.getCustomers();

        simpleList = (ListView) _rootView.findViewById(R.id.customer_list);

        CustomerAdapter adapter = new CustomerAdapter(getActivity(), R.layout.customer_adapter, customerList);
        simpleList.setAdapter(adapter);

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
            _hostActivityListener = (CustomerListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "CustomerListFragmentListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface CustomerListFragmentListener {
        void onClickCustomer(Customer customer);
        void onAddCustomer();
    }



    /**
     * For now we just want to let the host activity tak care of it by calling it's
     * onAddCustomer method it better had implemented our interface
     */
    public void onCreateCustomer() {
        _hostActivityListener.onAddCustomer();
    }

}
