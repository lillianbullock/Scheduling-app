package com.example.evans.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.evans.R;
import com.example.evans.data.Service;

import java.util.ArrayList;
import java.util.Locale;

/**
 * {@link ArrayAdapter<>} extension that displays the service data in a list view
 */
public class ServiceAdapter extends ArrayAdapter<Service> {

    private ArrayList<Service> _serviceList = new ArrayList<>();
    private  ViewHolder _viewHolder;
    private String currency;

    // use of the viewHolder allows faster loading because the views
    // don't need to be collected for each item in the list view
    static class ViewHolder {
        TextView title;
        TextView price;
    }

    public ServiceAdapter(Context context, int textViewResourceId, ArrayList<Service> objects) {
        super(context, textViewResourceId, objects);
        _serviceList = objects;
        currency = context.getResources().getString(R.string.currency);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.service_adapter, parent, false);

            // set up the ViewHolder
            _viewHolder = new ViewHolder();
            _viewHolder.title = convertView.findViewById(R.id.service_adapter_name);
            _viewHolder.price = convertView.findViewById(R.id.service_adapter_price);

            // save the holder with the view
            convertView.setTag(_viewHolder);
        } else {
            // there's an existing view so avoid calling findViewById
            // just use the viewHolder
            _viewHolder = (ViewHolder) convertView.getTag();
        }

        // set the values for the views if the current item in our list of services
        // is not null
        if (_serviceList.get(position) != null) {
            _viewHolder.title.setText(_serviceList.get(position).getTitle());

            // just formatting the price to look good
            String stringPrice = String.format(Locale.US,"%1.2f", _serviceList.get(position).getPrice());
            String formattedPrice = currency + stringPrice;

            _viewHolder.price.setText(formattedPrice);
        }

        return convertView;
    }
}
