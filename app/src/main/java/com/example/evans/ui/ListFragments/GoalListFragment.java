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
import com.example.evans.data.Goal;
import com.example.evans.data.OnGetDataListener;
import com.example.evans.ui.Adapters.GoalAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Fragment} subclass that lists all relevant appointments
 * uses the {@link GoalAdapter} to display each item.
 */
public class GoalListFragment  extends Fragment implements OnGetDataListener {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements
    private GoalsListFragmentListener _hostActivity;
    private ArrayList<Goal> _goals = new ArrayList<>();
    ListView _goalListView;
    private ProgressBar _progressBar;
    private GoalAdapter _goalArrayAdapter;
    private static final String TITLE = "Goals";


    public GoalListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_goal_list, container, false);

        _addFloatingBtn = _rootView.findViewById(R.id.floating_btn);
        _progressBar = _rootView.findViewById(R.id.goals_list_progressbar);
        _goalListView = _rootView.findViewById(R.id.goal_list);
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


        loadGoals();

        return _rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        _hostActivity.setAppbarTitle(TITLE);
        _hostActivity.showActionbar();
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

        _goals.clear();

        for (DataSnapshot child: data.getChildren()){
            Goal goal = child.getValue(Goal.class);
            _goals.add(child.getValue(Goal.class));
        }


        _progressBar.setVisibility(ProgressBar.INVISIBLE);


        GoalAdapter goalAdapter = new GoalAdapter(getActivity(), R.layout.goal_adapter, _goals);
        _goalListView.setAdapter(goalAdapter);

    }

    @Override
    public void onDataLoadFailed(DatabaseError databaseError) {

    }

    /**
     * Ensures parent activity has implemented the InteractionWithGoalListFragment interface
     * @param activity: the host activity
     */
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
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface GoalsListFragmentListener {
        void viewWithGoal(Goal goal);
        void onClickAddGoal();
        void showActionbar();
        void setAppbarTitle(String title);
    }

    public void onCreateGoal() {
        _hostActivity.onClickAddGoal();
    }
}