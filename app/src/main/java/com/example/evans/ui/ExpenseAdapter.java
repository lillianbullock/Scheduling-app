package com.example.evans.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.evans.R;
import com.example.evans.data.Expense;

/**
 * {@link ArrayAdapter<>} extension that displays the expense data in a list view
 */
public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private ArrayList<Expense> _expenseList = new ArrayList<>();

    public ExpenseAdapter(Context context, int textViewResourceId, ArrayList<Expense> objects) {
        super(context, textViewResourceId, objects);
        _expenseList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.expense_adapter, parent, false);


        TextView title = (TextView) view.findViewById(R.id.array_adapter_expense_title);
        TextView price = (TextView) view.findViewById(R.id.array_adapter_expense_price);
        TextView dateTime = (TextView) view.findViewById(R.id.array_adapter_expense_date);

        title.setText(_expenseList.get(position).getName());
        price.setText(_expenseList.get(position).getPrice().toString());
        dateTime.setText(_expenseList.get(position).getDate().toString());

        return view;
    }
}
