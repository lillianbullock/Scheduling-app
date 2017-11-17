package com.example.evans.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.evans.R;
import com.example.evans.data.Sale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brooke Nelson on 11/15/2017.
 */

public class SalesListFragment extends Fragment {

    View _rootView;
   InteractionWithSalesFragmentListener _hostActivityListener;

    public SalesListFragment(){
        //Required empty public construction
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup contain,
                             Bundle savedInstanceState){

        //Set rootView to layout
        _rootView = inflater.inflate(R.layout.fragment_sales_list, contain, false);

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
     * Sales Attach
     * @param activity: the host activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            _hostActivityListener = (InteractionWithSalesFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithSalesFragmentListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface InteractionWithSalesFragmentListener{
        List<Sale> getSale();
    }


}
