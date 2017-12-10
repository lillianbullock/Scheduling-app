package com.example.evans.ui;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.evans.R;
import com.example.evans.data.Appointment;
import com.example.evans.data.FirebaseManager;
import com.example.evans.data.Goal;
import com.example.evans.data.OnGetDataListener;
import com.example.evans.ui.Adapters.AppointmentAdapter;
import com.example.evans.ui.Adapters.GoalAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;


/**
 * {@link Fragment} subclass to display on the main screen.
 */
public class StartPageFragment extends Fragment implements OnGetDataListener {

    private StartPageFragmentListener _hostActivity;
    private View _rootView;

    private FloatingActionButton _appointmentSeeMoreBttn;
    private FloatingActionButton _goalSeeMoreBttn;
    private ProgressBar _goalProgressBar;
    private ProgressBar _appointmentProgressBar;

    private ListView _appointmentListViewStartPage;
    private ListView _goalListViewStartPage;

    private AppointmentAdapter _appointmentAdapter;
    private GoalAdapter _goalAdapter;

    private ArrayList<Appointment> _appointment = new ArrayList<>();
    private ArrayList<Goal> _goals = new ArrayList<>();

    private OnGetDataListener _onGetDataListener;

    public StartPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_start_page, container, false);

        _appointmentSeeMoreBttn = _rootView.findViewById(R.id.btn_appointment_see_more);
        _goalSeeMoreBttn = _rootView.findViewById(R.id.btn_goal_see_more);

        _appointmentListViewStartPage = _rootView.findViewById(R.id.appointment_list_view_start_page);
        _goalListViewStartPage = _rootView.findViewById(R.id.goal_list_view_start_page);

        _appointmentAdapter = new AppointmentAdapter(getActivity(), R.layout.appointment_adapter, _appointment);
        _goalAdapter = new GoalAdapter(getActivity(), R.layout.goal_adapter, _goals);

        _goalProgressBar = _rootView.findViewById(R.id.goal_start_page_progress_bar);
        _appointmentProgressBar = _rootView.findViewById(R.id.appointment_start_page_progress_bar);

        loadData();

       _appointmentSeeMoreBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onClickAppointmentsSeeMore();
            }
        });

        _goalSeeMoreBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _hostActivity.onClickGoalsSeeMore();
            }
        });


        return _rootView;
    }

    private void loadData(){
        FirebaseManager firebaseManager = new FirebaseManager();
        firebaseManager.getUnFinishedLimitGoals(10, this);


        firebaseManager.getAppointmentWithLimit(5, new OnGetDataListener() {
            @Override
            public void onDataLoadStarted() {
                _appointmentProgressBar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onDataLoadSucceed(DataSnapshot dataSnapshot) {
                _appointment.clear();

                for (DataSnapshot child: dataSnapshot.getChildren()){
                    _appointment.add(child.getValue(Appointment.class));
                }

                AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getActivity(), R.layout.appointment_adapter, _appointment);
                _appointmentListViewStartPage.setAdapter(appointmentAdapter);
                _appointmentProgressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onDataLoadFailed(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDataLoadStarted() {
        _goalProgressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void onDataLoadSucceed(DataSnapshot data) {

        _goals.clear();

        for (DataSnapshot child: data.getChildren()){
            _goals.add(child.getValue(Goal.class));
        }

        _goalAdapter.addAll(_goals);

        _goalProgressBar.setVisibility(ProgressBar.INVISIBLE);

        GoalAdapter goalAdapter = new GoalAdapter(getActivity(), R.layout.goal_adapter, _goals);
        _goalListViewStartPage.setAdapter(goalAdapter);

    }

    @Override
    public void onDataLoadFailed(DatabaseError databaseError) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            _hostActivity = (StartPageFragmentListener) context;
        } catch (ClassCastException e){
            /* they refused to honor the contract!!*/
            throw new ClassCastException(context.toString() + " must implement StartPageFragmentListener");
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        final String TITLE = "Evans";

        _hostActivity.showActionbar();
        _hostActivity.setAppbarTitle(TITLE);
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface StartPageFragmentListener {
        void showActionbar();
        void hideActionbar();
        void setAppbarTitle(String title);
        void onClickGoalsSeeMore();
        void onClickAppointmentsSeeMore();
        void onClickAppointment(Appointment appointment);
        void onClickGoal(Goal goal);
    }
}
