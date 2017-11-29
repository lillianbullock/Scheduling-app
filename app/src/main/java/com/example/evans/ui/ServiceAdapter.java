package com.example.evans.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Service;

import java.util.ArrayList;

/**
 * {@link ArrayAdapter<>} extension that displays the service data in a list view
 */
public class ServiceAdapter extends ArrayAdapter<Service> {

    private ArrayList<Service> _serviceList = new ArrayList<>();

    public ServiceAdapter(Context context, int textViewResourceId, ArrayList<Service> objects) {
        super(context, textViewResourceId, objects);
        _serviceList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.service_adapter, null);

        TextView title = (TextView) view.findViewById(R.id.service_adapter_name);
        TextView price = (TextView) view.findViewById(R.id.service_adapter_price);

        title.setText(_serviceList.get(position).getTitle());
        price.setText(Double.toString(_serviceList.get(position).getPrice()));

        return view;
    }
}
