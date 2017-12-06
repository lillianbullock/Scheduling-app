package com.example.evans.ui.ListFragments;

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
import com.example.evans.ui.Adapters.SaleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Fragment} subclass that lists all relevant appointments
 * uses the {@link SaleAdapter} to display each item.
 */
public class SaleListFragment extends Fragment {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;
    private SaleListFragmentListener _hostActivityListener;

    public SaleListFragment(){
        //Required empty public construction
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup contain,
                             Bundle savedInstanceState){

        //Set rootView to layout
        _rootView = inflater.inflate(R.layout.fragment_sale_list, contain, false);

        _addFloatingBtn = _rootView.findViewById(R.id.floating_sale_btn);

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
        test2.getService().setTitle("Here is a new Sale");
        saleList.add(test2);

        //TODO ADD IN WITH FIREBASE
        //saleList = (ArrayList) _hostActivityListener.getSale();

        simpleList = _rootView.findViewById(R.id.sale_list);

        SaleAdapter adapter = new SaleAdapter(getActivity(), R.layout.sale_adapter, saleList);
        simpleList.setAdapter(adapter);

        return _rootView;
    }

    /**
     * For now we just want to let the host activity tak care of it by calling it's
     * onAddCustomer method it better had implemented our interface
     */
    public void onCreateSale() {
        _hostActivityListener.onAddSale();
    }

    /**
     * Ensures parent activity has implemented the InteractionWithSaleListFragment interface
     * @param activity: the host activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            _hostActivityListener = (SaleListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "SaleListFragmentListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface SaleListFragmentListener {
        void onAddSale();
        void onClickSale(Sale sale);
    }
}
