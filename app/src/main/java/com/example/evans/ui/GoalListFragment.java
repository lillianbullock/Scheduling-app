package com.example.evans.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.evans.R;

/**
 * Created by Brooke Nelson on 11/9/2017.
 */

public class GoalListFragment  extends Fragment {
    FloatingActionButton _addFloatingBtn;
    View _rootView;  // how we can get access to view elements


    public GoalListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_customers_list, container, false);

        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_add_btn);

        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateGoal(container);
            }
        });

        return _rootView;
    }




    /**
     * Interface that should be implemented by the container the activity that
     * creates this fragment. This method should be invoked when the user clicks on the plus button
     */
    public interface GoalChangeOperation {
        public void createGoal();
        public void onClickGoal();
    }

    public void onCreateGoal(ViewGroup parentActivity) {
        /*FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new Goal_Fragment();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        */
        Toast.makeText(getActivity(), "You tried to add a new goal", Toast.LENGTH_SHORT).show();
    }




}
