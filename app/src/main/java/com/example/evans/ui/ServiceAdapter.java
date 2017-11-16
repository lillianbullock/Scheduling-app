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
 * Created by Brooke Nelson on 11/15/2017.
 */

public class ServiceAdapter extends ArrayAdapter<Service> {

    ArrayList<Service> ServiceArrayList = new ArrayList<Service>();

    public ServiceAdapter(Context context, int textViewResourceId, ArrayList<Service> objects) {
        super(context, textViewResourceId, objects);

        //Set arrayList to something that is not null
        ServiceArrayList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.service_adapter, null);

        TextView textView = (TextView) view.findViewById(R.id.service_adapter_list);

        textView.setText(ServiceArrayList.get(position).getTitle());

        return view;
    }
}
