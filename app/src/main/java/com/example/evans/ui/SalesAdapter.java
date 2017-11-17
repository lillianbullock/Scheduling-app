package com.example.evans.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Sale;

import java.util.ArrayList;

/**
 * Created by Brooke Nelson on 11/15/2017.
 */

public class SalesAdapter extends ArrayAdapter<Sale> {

    ArrayList<Sale> SalesArrayList = new ArrayList<>();

    public SalesAdapter(Context context, int textViewResourceId, ArrayList<Sale> objects) {
        super(context, textViewResourceId, objects);

        //Set arrayList to something that is not null
        SalesArrayList = objects;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.sales_adapter, null);

        TextView textView = (TextView) view.findViewById(R.id.salesAdapter);

        textView.setText(SalesArrayList.get(position).getService().getTitle());

        return view;
    }
}
