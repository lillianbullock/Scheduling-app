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
    private ViewHolder _viewHolder;

    public CustomerAdapter(Context context, int textViewResourceId, ArrayList<Customer> objects) {
        super(context, textViewResourceId, objects);
        _customerList = objects;
    }

    // use of the viewHolder allows faster loading because the views
    // don't need to be collected for each item in the list view
    static class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customer_adapter, parent, false);

            _viewHolder = new ViewHolder();
            _viewHolder.name = (TextView) convertView.findViewById(R.id.customer_adapter_name);

            // store the holder with the view.
            convertView.setTag(_viewHolder);

        }else{
            // we've just avoided calling findViewById() on resource every time
            // just use the viewHolder
            _viewHolder = (ViewHolder) convertView.getTag();
        }

        // if object not null, assigns values
        if (_customerList.get(position) != null) {
            _viewHolder.name.setText(_customerList.get(position).getName());
        }
        return convertView;

    }
}
