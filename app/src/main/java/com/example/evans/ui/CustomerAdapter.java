package com.example.evans.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.evans.R;
import com.example.evans.data.Customer;

/**
 * {@link ArrayAdapter<>} extension that displays the customer data in a list view
 */
public class CustomerAdapter extends ArrayAdapter<Customer> {

    private ArrayList<Customer> _customerList = new ArrayList<>();

    public CustomerAdapter(Context context, int textViewResourceId, ArrayList<Customer> objects) {
        super(context, textViewResourceId, objects);
        _customerList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.customer_adapter, null);

        TextView textView = (TextView) view.findViewById(R.id.name);

        textView.setText(_customerList.get(position).getName());
        return view;

    }
}
