package com.example.evans.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.evans.R;
import com.example.evans.data.Customer;

/**
 * Created by brooke on 11/14/17.
 */

public class CustomerAdapter extends ArrayAdapter<Customer> {

    ArrayList<Customer> customerList = new ArrayList<>();

    public CustomerAdapter(Context context, int textViewResourceId, ArrayList<Customer> objects) {
        super(context, textViewResourceId, objects);
        customerList = objects;
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

        TextView textView = (TextView) view.findViewById(R.id.textView);

        textView.setText(customerList.get(position).getName());
        return view;

    }
}
