package com.example.evans.ui.ListFragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.evans.R;
import com.example.evans.data.Expense;
import com.example.evans.ui.Adapters.ExpenseAdapter;

import org.joda.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link Fragment} subclass that lists all relevant expenses
 * uses the {@link ExpenseAdapter} to display each item.
 */
public class ExpenseListFragment extends Fragment {

    private FloatingActionButton _addFloatingBtn;
    private ExpenseListFragmentListener _hostActivityListener;


    public ExpenseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View rootView;
        rootView = inflater.inflate(R.layout.fragment_expense_list, container, false);

        ListView simpleList;
        simpleList = (ListView) rootView.findViewById(R.id.expense_list);

        _addFloatingBtn = (FloatingActionButton) rootView.findViewById(R.id.floating_add_btn_expense);
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateExpense();
            }
        });

        //setting arrayAdapter
        ArrayList<Expense> expenseList = new ArrayList<>(_hostActivityListener.getExpenses());


        ExpenseAdapter adapter = new ExpenseAdapter(getActivity(), R.layout.expense_adapter, expenseList);
        simpleList.setAdapter(adapter);

        return rootView;
    }

    /**
     * For now we just want to let the host activity tak care of it by calling it's
     * onAddCustomer method it better had implemented our interface
     */
    public void onCreateExpense() {
        _hostActivityListener.onAddExpense();
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
            _hostActivityListener = (ExpenseListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "ExpenseListFragmentListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface ExpenseListFragmentListener {
        void onClickExpense(Expense expense);
        void onAddExpense();
        List<Expense> getExpenses();
    }
}
