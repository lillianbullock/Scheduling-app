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
import com.example.evans.data.Appointment;
import com.example.evans.data.Service;
import com.example.evans.ui.Adapters.AppointmentAdapter;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentsListFragment extends Fragment {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements
    private AppointmentListFragmentListener _hostListener;


    public AppointmentsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_appointments_list, container, false);

        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_add_btn_appointment);

        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateAppointment();
            }
        });

        //setting arrayAdapter
        ListView simpleList;
        ArrayList<Appointment> appointmentList = new ArrayList<>();

        Service s1 = new Service("Service1", "", 2.00);
        Service s2 = new Service("Service2", "", 3.00);

        Appointment test1 = new Appointment("testName", LocalDate.now(), LocalTime.now(), "0",
                s1);
        appointmentList.add(test1);

        Appointment test2 = new Appointment("testName2", LocalDate.now(), LocalTime.now(), "0",
                s2);
        appointmentList.add(test2);

        //TODO put this back when app actually gets data from database (and take out dummy data above)
        //appointmentList = (ArrayList) _hostListener.getAppointmentList();

        super.onCreate(savedInstanceState);

        simpleList = (ListView) _rootView.findViewById(R.id.appointment_list);

        AppointmentAdapter adapter = new AppointmentAdapter(getActivity(), R.layout.customer_adapter, appointmentList);
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
            _hostListener = (AppointmentListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithAppointmentFragmentListener");
        }
    }

    /**
     *OnCreateAppointment()
     */
    public void onCreateAppointment() { _hostListener.onAddAppointment(); }


    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface AppointmentListFragmentListener {
        void onClickAppointment(Appointment appointment);
        void onAddAppointment();
        List<Appointment> getAppointmentList();
    }



}
