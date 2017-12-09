package com.example.evans.ui.ListFragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.evans.R;
import com.example.evans.data.Expense;
import com.example.evans.data.MainController;
import com.example.evans.data.OnGetDataListener;
import com.example.evans.ui.Adapters.ExpenseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Fragment} subclass that lists all relevant expenses
 * uses the {@link ExpenseAdapter} to display each item.
 */
public class ExpenseListFragment extends Fragment implements OnGetDataListener {

    private FloatingActionButton _addFloatingBtn;
    private ExpenseListFragmentListener _hostActivityListener;
    private String TITLE = "Expenses";
    private View _rootView;

    private ProgressBar _progressBar;
    private ListView _expenseListView;
    private ArrayList<Expense> _expense = new ArrayList<>();
    private ExpenseAdapter _expenseAdapter;
    private MainController _mainController = MainController.getInstance();

    private OnGetDataListener _onGetDataListener;

    public ExpenseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _rootView = inflater.inflate(R.layout.fragment_expense_list, container, false);

        _addFloatingBtn = _rootView.findViewById(R.id.floating_add_btn_expense);
        _progressBar = _rootView.findViewById(R.id.expense_list_progress_bar);
        _expenseListView = _rootView.findViewById(R.id.expense_list);

        _expenseAdapter = new ExpenseAdapter(getActivity(), R.layout.expense_adapter, _expense);

        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateExpense();
            }
        });


        _expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense expense = _expenseAdapter.getItem(position);
                _hostActivityListener.onClickExpense(expense);
            }
        });

        loadExpense();

        return _rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        _hostActivityListener.showActionbar();
        _hostActivityListener.setAppbarTitle(TITLE);
    }

    public void setExpense(List<Expense> expense){ _expense.addAll(expense); }

    private void loadExpense(){

        _mainController.getAllExpenses(this);
    }

    @Override
    public void onDataLoadStarted() {
        _progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void onDataLoadSucceed(DataSnapshot dataSnapshot) {

        _expense.clear();

        for(DataSnapshot child: dataSnapshot.getChildren()){
            _expense.add(child.getValue(Expense.class));
        }

        _progressBar.setVisibility(ProgressBar.INVISIBLE);
        _expenseListView.setAdapter(_expenseAdapter);

    }

    @Override
    public void onDataLoadFailed(DatabaseError databaseError) {
        Log.w("Error in Expense", "Unable to load Expense");
    }

    /**
     * For now we just want to let the host activity tak care of it by calling it's
     * onAddCustomer method it better had implemented our interface
     */
    public void onCreateExpense() {
        _hostActivityListener.onAddExpense();
    }

    /**
     * Ensures parent activity has implemented the InteractionWithExpenseListFragment interface
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
        void showActionbar();
        void setAppbarTitle(String title);
    }
}
