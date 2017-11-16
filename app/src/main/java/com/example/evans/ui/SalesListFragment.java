package com.example.evans.ui;

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

/**
 * Created by Brooke Nelson on 11/15/2017.
 */

public class SalesListFragment extends Fragment {

    View _rootView;
  //  InteractionwithSalesFragmentListener _hostActivityListener;

    public SalesListFragment(){
        //Required empty public construction
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup contain,
                             Bundle savedInstanceState){

        //Set rootView to layout
        _rootView = inflater.inflate(R.layout.fragment_sales_list, contain, false);
/*
        //setting arrayAdapter
        ListView simpleList;
        ArrayList<Sale> saleList = new ArrayList<>();

        super.onCreate(savedInstanceState);

        Sale test = new Sale();
        test.getService().setTitle("NewSale");
        saleList.add(test);

        Sale test2 = new Sale();
        test2.getService().setTitle("newSale");
        saleList.add(test2);

        simpleList = (ListView) _rootView.findViewById(R.id.sales_list);

        SalesAdapter adapter = new SalesAdapter(getActivity(), R.layout.sales_adapter, saleList);
        simpleList.setAdapter(adapter);
*/
        return _rootView;
    }

}
