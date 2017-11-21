package com.example.evans.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.evans.R;
import com.example.evans.data.Sale;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Sales List Fragment
 * {@link Fragment} subclass
 */

public class SalesListFragment extends Fragment {

    FloatingActionButton _addFloatingBtn;
    View _rootView;
   SalesListFragmentListener _hostActivityListener;

    public SalesListFragment(){
        //Required empty public construction
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup contain,
                             Bundle savedInstanceState){

        //Set rootView to layout
        _rootView = inflater.inflate(R.layout.fragment_sales_list, contain, false);

        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_sale_btn);

        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateSale();
            }
        });

        //setting arrayAdapter
        ListView simpleList;
        ArrayList<Sale> saleList = new ArrayList<>();

        super.onCreate(savedInstanceState);

        Sale test = new Sale();
        test.getService().setTitle("Sale made a First sale");
        saleList.add(test);

        Sale test2 = new Sale();
        test2.getService().setTitle("Here is a new Sales");
        saleList.add(test2);

        //TODO ADD IN WITH FIREBASE
        //saleList = (ArrayList) _hostActivityListener.getSale();

        simpleList = (ListView) _rootView.findViewById(R.id.sales_list);

        SalesAdapter adapter = new SalesAdapter(getActivity(), R.layout.sales_adapter, saleList);
        simpleList.setAdapter(adapter);

        return _rootView;
    }


    /**
     * Sales Attach, want to make sure that the activity that uses this fragment
     * has implemented our SalesListFragmentListener interface
     * @param activity: the host activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            _hostActivityListener = (SalesListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "SalesListFragmentListener");
        }
    }

    /**
     * For now we just want to let the host activity tak care of it by calling it's
     * onAddCustomer method it better had implemented our interface
     */
    public void onCreateSale() {
        _hostActivityListener.onAddSale();
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface SalesListFragmentListener {
        List<Sale> getSale();
        void onAddSale();
        void onClickSale(Sale sale);
    }


}
