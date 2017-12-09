package com.example.evans.ui.ListFragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.evans.R;
import com.example.evans.data.Customer;
import com.example.evans.data.FirebaseManager;
import com.example.evans.data.MainController;
import com.example.evans.data.OnGetDataListener;
import com.example.evans.ui.Adapters.CustomerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;


/**
 * {@link Fragment} subclass that lists all relevant customers
 * uses the {@link CustomerAdapter} to display each item.
 */
public class CustomerListFragment extends Fragment implements OnGetDataListener {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements

    private CustomerListFragmentListener _hostActivityListener;
    private ArrayList<Customer> _customer = new ArrayList<>();
    private ListView _customerListView;
    private ProgressBar _progressBar;
    private CustomerAdapter _customerArrayAdapter;

    private MainController _mainController = MainController.getInstance();

    private OnGetDataListener _onGetDataListener;

    private final String TITLE = "Customers";

    public CustomerListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_customer_list, container, false);
        _progressBar = _rootView.findViewById(R.id.customer_list_progress_bar);
        _addFloatingBtn = _rootView.findViewById(R.id.floating_add_btn);

        _customerListView = _rootView.findViewById(R.id.customer_list);
        _customerArrayAdapter = new CustomerAdapter(getActivity(), R.layout.customer_adapter, _customer);

        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateCustomer();
            }
        });

        _customerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Customer customer = _customerArrayAdapter.getItem(position);
                _hostActivityListener.onClickCustomer(customer);
            }
        });

        loadCustomer();

        return _rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        _hostActivityListener.setAppbarTitle(TITLE);
        _hostActivityListener.showActionbar();
    }


    public void setCustomer(List<Customer> customer){
        _customer.addAll(customer);
    }


    private void loadCustomer(){
        _mainController.getAllCustomers(this);
    }

    @Override
    public void onDataLoadStarted() {
        _progressBar.setVisibility(ProgressBar.VISIBLE);

    }

    @Override
    public void onDataLoadSucceed(DataSnapshot data) {

        _customer.clear();

        for (DataSnapshot child: data.getChildren()){
            _customer.add(child.getValue(Customer.class));
        }

        _progressBar.setVisibility(ProgressBar.INVISIBLE);
        _customerListView.setAdapter(_customerArrayAdapter);

    }

    @Override
    public void onDataLoadFailed(DatabaseError databaseError) {
        Log.w("Error in Customer", "Unable to load Customer");
    }

    /**
     * For now we just want to let the host activity tak care of it by calling it's
     * onAddCustomer method it better had implemented our interface
     */
    public void onCreateCustomer() {
        _hostActivityListener.onAddCustomer();
    }

    /**
     * Ensures parent activity has implemented the InteractionWithCustomerListFragment interface
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
        void showActionbar();
        void setAppbarTitle(String title);
    }
}
