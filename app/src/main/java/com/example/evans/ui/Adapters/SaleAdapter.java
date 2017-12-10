package com.example.evans.ui.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Sale;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Locale;

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

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.sale_adapter, parent, false);

            _viewHolder = new SaleViewHolder();
            _viewHolder.service = convertView.findViewById(R.id.sales_adapter_title);
            _viewHolder.price = convertView.findViewById(R.id.sales_adapter_price);
            _viewHolder.date = convertView.findViewById(R.id.sales_adapter_date);

            convertView.setTag(_viewHolder);

        }else{
            _viewHolder = (SaleViewHolder) convertView.getTag();
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd, MMMM yyyy");
        if(_salesList.get(position) != null) {
            _viewHolder.service.setText(_salesList.get(position).getService().getTitle());
            _viewHolder.price.setText(String.format(Locale.US,"%1.2f", _salesList.get(position).getPrice()));
            _viewHolder.date.setText(formatter.print(_salesList.get(position).getDateObject()));
        }

        return convertView;
    }
}
