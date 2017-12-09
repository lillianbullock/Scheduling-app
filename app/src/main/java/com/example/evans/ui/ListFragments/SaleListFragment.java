package com.example.evans.ui.ListFragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.evans.R;
import com.example.evans.data.FirebaseManager;
import com.example.evans.data.OnGetDataListener;
import com.example.evans.data.Sale;
import com.example.evans.ui.Adapters.SaleAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Fragment} subclass that lists all relevant appointments
 * uses the {@link SaleAdapter} to display each item.
 */
public class SaleListFragment extends Fragment implements OnGetDataListener {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;
    private SaleListFragmentListener _hostActivityListener;
    private static final String TITLE = "Sales";

    private ProgressBar _progressBar;
    private ListView _saleListView;
    private ArrayList<Sale> _sale = new ArrayList<>();
    private SaleAdapter _saleAdapter;

    private OnGetDataListener _onGetDataListener;

    public SaleListFragment(){
        //Required empty public construction
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup contain,
                             Bundle savedInstanceState){

        //Set rootView to layout
        _rootView = inflater.inflate(R.layout.fragment_sale_list, contain, false);

        _addFloatingBtn = _rootView.findViewById(R.id.floating_sale_btn);
        _progressBar = _rootView.findViewById(R.id.sale_list_progress_bar);
        _saleListView = _rootView.findViewById(R.id.sale_list);

        _saleAdapter = new SaleAdapter(getActivity(), R.layout.sale_adapter, _sale);


        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateSale();
            }
        });

        _saleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sale sale = _saleAdapter.getItem(position);
                _hostActivityListener.onClickSale(sale);
            }
        });

        loadSale();

        return _rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        _hostActivityListener.setAppbarTitle(TITLE);
        _hostActivityListener.showActionbar();
    }

    public void setSale(List<Sale> sale){
        _sale.addAll(sale);
    }

    private void loadSale(){
        FirebaseManager firebaseManager = new FirebaseManager();
        firebaseManager.getAllSales(this);
    }

    @Override
    public void onDataLoadStarted() {
        _progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void onDataLoadSucceed(DataSnapshot dataSnapshot) {

        _sale.clear();


        for(DataSnapshot child: dataSnapshot.getChildren()){
            _sale.add(child.getValue(Sale.class));
        }

        //_saleAdapter.addAll(_sale);
        _progressBar.setVisibility(ProgressBar.INVISIBLE);
        _saleListView.setAdapter(_saleAdapter);

    }

    @Override
    public void onDataLoadFailed(DatabaseError databaseError) {

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
        void showActionbar();
        void setAppbarTitle(String title);
    }
}
