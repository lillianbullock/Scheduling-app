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
import com.example.evans.data.Goal;
import com.example.evans.ui.Adapters.GoalAdapter;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brooke Nelson on 11/9/2017
 */

public class GoalListFragment  extends Fragment {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements
    private GoalsListFragmentListener _hostActivity;
    private ArrayList<Goal> _goals = new ArrayList<>();


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
        ArrayList<Goal> newGoals = new ArrayList<>();

        super.onCreate(savedInstanceState);

       /* Goal goal = new Goal("Two", "Do two things", LocalDate.now(), LocalDate.now());
        newGoals.add(goal);

        goal.setDone(true);

        //TODO information back in with database
        //goalList = (ArrayList) _hostActivity.getGoal();*/

        ListView goalList = (ListView) _rootView.findViewById(R.id.goal_list);

        GoalAdapter goalArrayAdapter = new GoalAdapter(getActivity(), R.layout.goal_adapter, _goals);
        goalList.setAdapter(goalArrayAdapter);

        return _rootView;
    }

    public void setGoals(List<Goal> goals){
        _goals.addAll(goals);
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
        void onClickGoal();
        void onClickAddGoal();
        List<Goal> getGoal(int num);
    }

    public void onCreateGoal() {
        _hostActivity.onClickAddGoal();
    }
}