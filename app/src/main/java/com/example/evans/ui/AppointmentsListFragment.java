package com.example.evans.ui;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.evans.R;
import com.example.evans.data.Appointment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentsListFragment extends Fragment {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements
    private InteractionWithAppointmentFragmentListener _hostListener;


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
            _hostListener = (InteractionWithAppointmentFragmentListener) activity;
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
    public interface InteractionWithAppointmentFragmentListener{
        void onClickAppointment(Appointment appointment);
        void onAddAppointment();
    }



}
