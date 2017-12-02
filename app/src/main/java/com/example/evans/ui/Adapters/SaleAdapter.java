package com.example.evans.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Sale;

import java.util.ArrayList;

/**
 * {@link ArrayAdapter<>} extension that displays the sales data in a list view
 */
public class SaleAdapter extends ArrayAdapter<Sale> {

    private ArrayList<Sale> _salesList = new ArrayList<>();
    private SaleViewHolder _viewHolder;

    // use of the viewHolder allows faster loading because the views
    // don't need to be collected for each item in the list view
    static class SaleViewHolder {
        TextView service;
        TextView price;
        TextView date;
    }

    public SaleAdapter(Context context, int textViewResourceId, ArrayList<Sale> objects) {
        super(context, textViewResourceId, objects);
        //Set arrayList to something that is not null
        _salesList = objects;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.sale_adapter, parent, false);

        _viewHolder = new SaleViewHolder();
        _viewHolder.service = (TextView) convertView.findViewById(R.id.array_adapter_sales_service);
        _viewHolder.price = (TextView) convertView.findViewById(R.id.array_adapter_sales_price);
        _viewHolder.date = (TextView) convertView.findViewById(R.id.array_adapter_sales_date);

        _viewHolder.service.setText(_salesList.get(position).getService().getTitle());
        _viewHolder.price.setText(_salesList.get(position).getPrice().toString());
        _viewHolder.date.setText(_salesList.get(position).getDate().toString());

        return convertView;
    }
}
