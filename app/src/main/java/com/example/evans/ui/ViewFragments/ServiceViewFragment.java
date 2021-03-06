package com.example.evans.ui.ViewFragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Service;

import java.util.Locale;

public class ServiceViewFragment extends Fragment {

    private Service _service;
    private Button _editServiceBtn;
    private Button _backBtn;
    private ServiceListFragmentListener _hostListener;


    public ServiceViewFragment() {
        // Required empty public constructor
    }

    public void setService (Service service) { _service = service; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_view,container, false);

        TextView name = view.findViewById(R.id.txt_service_view_name);
        TextView detail =  view.findViewById(R.id.txt_service_description);
        TextView price = view.findViewById(R.id.txt_service_price);

        _editServiceBtn = view.findViewById(R.id.btn_view_bar_edit);
        _backBtn = view.findViewById(R.id.btn_view_bar_back);


        name.setText(_service.getTitle());
        detail.setText(_service.getDescription());

        price.setText(String.format(Locale.US,"$%1.2f", _service.getPrice()));

        _editServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostListener.onEditService(_service);
            }
        });

        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostListener.onBackPressed();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        _hostListener.hideActionbar();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //check for implementation by trying to cast to an instance of the interface
        try {
            _hostListener = (ServiceListFragmentListener) activity;
        } catch (ClassCastException e) {
            // if fails, interface wasn't implemented
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithGoalViewFragmentListener");
        }
    }

    public interface ServiceListFragmentListener {
        void onEditService(Service service);
        void hideActionbar();
        void onBackPressed();
    }
}
