package com.example.evans.ui;


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

import org.joda.time.LocalDate;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseListFragment extends Fragment {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements
    private InteractionWithExpenseListFragmentListener _hostActivityListener;


    public ExpenseListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_expense_list, container, false);

        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_add_btn_expense);

        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateExpense();
            }
        });

        //setting arrayAdapter
        ListView simpleList;
        ArrayList<Expense> expenseList = new ArrayList<>();

        Expense expense1 = new Expense("new name", 15.00, LocalDate.now());
        expenseList.add(expense1);

        super.onCreate(savedInstanceState);

        //TODO put this back when app actually gets data from database (and take out dummy data above)
        //expenseList = (ArrayList) _hostActivityListener.getExpense();

        simpleList = (ListView) _rootView.findViewById(R.id.expense_list);

        ExpenseAdapter adapter = new ExpenseAdapter(getActivity(), R.layout.expense_adapter, expenseList);
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
            _hostActivityListener = (InteractionWithExpenseListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithExpenseListFragmentListener");
        }
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface InteractionWithExpenseListFragmentListener{
        void onClickExpense(Expense expense);
        void onAddExpense();
    }

    /**
     * For now we just want to let the host activity tak care of it by calling it's
     * onAddCustomer method it better had implemented our interface
     */
    public void onCreateExpense() {
        _hostActivityListener.onAddExpense();
    }


}
