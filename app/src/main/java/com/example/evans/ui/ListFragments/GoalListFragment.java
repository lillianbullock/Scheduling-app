package com.example.evans.ui.ListFragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.FirebaseManager;
import com.example.evans.data.Goal;
import com.example.evans.data.OnGetDataListener;
import com.example.evans.ui.Adapters.GoalAdapter;
import com.example.evans.ui.ViewFragments.GoalViewFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brooke Nelson on 11/9/2017
 */

public class GoalListFragment  extends Fragment implements OnGetDataListener {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements
    private GoalsListFragmentListener _hostActivity;
    private ArrayList<Goal> _goals = new ArrayList<>();
    ListView _goalListView;
    private ProgressBar _progressBar;
    private GoalAdapter _goalArrayAdapter;
    private OnGetDataListener _onGetDataListener;


    public GoalListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_goal_list, container, false);

        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_btn);
        _progressBar = _rootView.findViewById(R.id.goals_list_progressbar);
        _goalListView = (ListView) _rootView.findViewById(R.id.goal_list);
        _goalArrayAdapter = new GoalAdapter(getActivity(), R.layout.goal_adapter, _goals);


        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateGoal();
            }
        });

        _goalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Goal goal = _goalArrayAdapter.getItem(position);
               _hostActivity.viewWithGoal(goal);
            }
        });

        //setting arrayAdapter
        ArrayList<Goal> newGoals = new ArrayList<>();


        loadGoals();

        return _rootView;
    }

    public void setGoals(List<Goal> goals){
        _goals.addAll(goals);
    }


    private void loadGoals(){

        FirebaseManager firebaseManager = new FirebaseManager();
        firebaseManager.getAllGoals(this);

    }


    @Override
    public void onDataLoadStarted() {
        _progressBar.setVisibility(ProgressBar.VISIBLE);

    }

    @Override
    public void onDataLoadSucceed(DataSnapshot data) {

        for (DataSnapshot child: data.getChildren()){
            _goals.add(child.getValue(Goal.class));
        }

        _goalArrayAdapter.addAll(_goals);

        _progressBar.setVisibility(ProgressBar.INVISIBLE);


        GoalAdapter goalAdapter = new GoalAdapter(getActivity(), R.layout.goal_adapter, _goals);
        _goalListView.setAdapter(goalAdapter);

    }

    @Override
    public void onDataLoadFailed(DatabaseError databaseError) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            _hostActivity = (GoalsListFragmentListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement " +
                    "GoalsListFragmentListener");
        }
    }

    /**
     * Interface that should be implemented by the container the activity that
     * creates this fragment. This method should be invoked when the user clicks on the plus button */
    public interface GoalsListFragmentListener {
        void viewWithGoal(Goal goal);
        void onClickAddGoal();
    }

    public void onCreateGoal() {
        _hostActivity.onClickAddGoal();
    }
}