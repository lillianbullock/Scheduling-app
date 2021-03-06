package com.example.evans.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import com.example.evans.R;
import com.example.evans.data.Expense;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * {@link ArrayAdapter<>} extension that displays the expense data in a list view
 */
public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private ArrayList<Expense> _expenseList = new ArrayList<>();

    public ExpenseAdapter(Context context, int textViewResourceId, ArrayList<Expense> objects) {
        super(context, textViewResourceId, objects);
        _expenseList = objects;
    }

    static class ViewHolder {
        TextView title;
        TextView price;
        TextView date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expense_adapter, parent, false);

            // Initialize the views
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.expense_adapter_title);
            viewHolder.price = convertView.findViewById(R.id.expense_adapter_price);
            viewHolder.date = convertView.findViewById(R.id.expense_adapter_date);

            // save the holder with the view
            convertView.setTag(viewHolder);

        } else {
            // load the views
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set the values for the views if the current item in our list of services
        // is not null
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd, MMMM yyyy");

        if (_expenseList.get(position) != null) {
            viewHolder.title.setText(_expenseList.get(position).getName());
            viewHolder.price.setText(String.format(Locale.US,"%1.2f", _expenseList.get(position).getPrice()));
            viewHolder.date.setText(formatter.print(_expenseList.get(position).getDateObject()));
        }

        return convertView;
    }
}
