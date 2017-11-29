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
 * Created by brooke on 11/14/17.
 */

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    ArrayList<Expense> expenseList = new ArrayList<>();

    public ExpenseAdapter(Context context, int textViewResourceId, ArrayList<Expense> objects) {
        super(context, textViewResourceId, objects);
        expenseList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.expense_adapter, null);


        TextView title = (TextView) view.findViewById(R.id.array_adapter_expense_title);
        TextView price = (TextView) view.findViewById(R.id.array_adapter_expense_price);
        TextView dateTime = (TextView) view.findViewById(R.id.array_adapter_expense_date);

        title.setText(expenseList.get(position).getName());
        price.setText(expenseList.get(position).getPrice().toString());
        dateTime.setText(expenseList.get(position).getDate().toString());

        return view;
    }
}
