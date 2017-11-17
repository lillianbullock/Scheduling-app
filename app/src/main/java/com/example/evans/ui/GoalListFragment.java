package com.example.evans.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.Goal;

import org.joda.time.LocalDateTime;

import java.util.ArrayList;

/**
 * Created by Brooke Nelson on 11/9/2017
 */

public class GoalListFragment  extends Fragment {
    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements
    private InteractionWithGoalsListFragmentListener _hostActivity;


    public GoalListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_goal_list, container, false);

        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_btn);

        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateGoal();
            }
        });

        //setting arrayAdapter
        ArrayList<Goal> goalArrayList = new ArrayList<>();

        super.onCreate(savedInstanceState);

        ListView goalList = (ListView) _rootView.findViewById(R.id.goal_list);

        Goal goal = new Goal("Two", "Do two things", LocalDateTime.now(), LocalDateTime.now());
        ArrayList<Goal> newGoals = new ArrayList<>();
        newGoals.add(goal);

        GoalAdapter goalArrayAdapter = new GoalAdapter(getActivity(), R.layout.goal_adapter, newGoals);

        goalList.setAdapter(goalArrayAdapter);

        return _rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            _hostActivity = (InteractionWithGoalsListFragmentListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithGoalsListFragmentListener");
        }
    }

    public void onCreateGoal() {
        _hostActivity.onClickAddGoal();
    }


    /**
     * Interface that should be implemented by the container the activity that
     * creates this fragment. This method should be invoked when the user clicks on the plus button */
    public interface InteractionWithGoalsListFragmentListener {
        void onClickGoal();
        void onClickAddGoal();
    }

}