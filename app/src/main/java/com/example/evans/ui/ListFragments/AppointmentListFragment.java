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
import com.example.evans.data.Appointment;
import com.example.evans.data.MainController;
import com.example.evans.data.OnGetDataListener;
import com.example.evans.ui.Adapters.AppointmentAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;


/**
 * {@link Fragment} subclass that lists all relevant appointments
 * uses the {@link AppointmentAdapter} to display each item.
 */
public class AppointmentListFragment extends Fragment implements OnGetDataListener {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements

    private AppointmentListFragmentListener _hostListener;
    private MainController _mainController = MainController.getInstance();


    private static final String TAG = "AppointmentListFragment";
    private final String TITLE = "Appointments";

    private ProgressBar _progressBar;
    private ListView _appointmentListView;
    private ArrayList<Appointment> _appointment = new ArrayList<>();
    private AppointmentAdapter _appointmentAdapter;

    public AppointmentListFragment() { /* Required empty public constructor*/ }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_appointment_list, container, false);
        _addFloatingBtn = _rootView.findViewById(R.id.floating_add_bttn_appointment);
        _progressBar = _rootView.findViewById(R.id.appointment_list_progress_bar);

        _appointmentListView = _rootView.findViewById(R.id.appointment_list);

        _appointmentAdapter = new AppointmentAdapter(getActivity(), R.layout.appointment_adapter, _appointment);

        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateAppointment();
            }
        });

        _appointmentListView.setAdapter(_appointmentAdapter);

        _appointmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Appointment appointment = _appointmentAdapter.getItem(position);
                _hostListener.onClickAppointment(appointment);
            }
        });

        loadAppointment();

        return _rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        _hostListener.showActionbar();
        _hostListener.setAppbarTitle(TITLE);
    }

    private void loadAppointment(){
        _mainController.getAllAppointments(this);
    }

    public void setAppointment(List<Appointment> appointment){ _appointment.addAll(appointment); }

    @Override
    public void onDataLoadStarted() {
        _progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void onDataLoadSucceed(DataSnapshot dataSnapshot) {

        _appointment.clear();

        for(DataSnapshot child: dataSnapshot.getChildren()){
            Appointment appointment = child.getValue(Appointment.class);
            _appointment.add(appointment);
        }

        _progressBar.setVisibility(ProgressBar.INVISIBLE);
        _appointmentListView.setAdapter(_appointmentAdapter);

    }

    @Override
    public void onDataLoadFailed(DatabaseError databaseError) {
        Log.w(TAG, "Unable to load appointments");
    }

    /**
     *OnCreateAppointment()
     */
    public void onCreateAppointment() { _hostListener.onAddAppointment(); }

    /**
     * Ensures parent activity has implemented the InteractionWithAppointmentListFragment interface
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
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface AppointmentListFragmentListener {
        void onClickAppointment(Appointment appointment);
        void onAddAppointment();
        void showActionbar();
        void setAppbarTitle(String title);
    }
}
