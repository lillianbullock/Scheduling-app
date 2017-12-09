package com.example.evans.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.evans.R;
import com.example.evans.data.Appointment;

/**
 * {@link ArrayAdapter<>} extension that displays the appointment data in a list view
 */
public class AppointmentAdapter extends ArrayAdapter<Appointment> {

    private ArrayList<Appointment> _appointmentList = new ArrayList<>();


    public AppointmentAdapter(Context context, int textViewResourceId, ArrayList<Appointment> objects) {
        super(context, textViewResourceId, objects);
        _appointmentList = objects;
    }

    // use of the viewHolder allows faster loading because the views
    // don't need to be collected for each item in the list view
    static class ViewHolder {
        TextView title;
        TextView service;
        TextView dateTime;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder _viewHolder;

        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.appointment_adapter, parent, false);

            // well set up the ViewHolder
            _viewHolder = new ViewHolder();
            _viewHolder.title = convertView.findViewById(R.id.appointment_adapter_title);
            _viewHolder.service = convertView.findViewById(R.id.appointment_adapter_service);
            _viewHolder.dateTime = convertView.findViewById(R.id.appointment_adapter_date_time);

            // store the holder with the view.
            convertView.setTag(_viewHolder);

        }else{
            // we've just avoided calling findViewById() on resource every time
            // just use the viewHolder
            _viewHolder = (ViewHolder) convertView.getTag();
        }

        // assign values if the object is not null
        if (_appointmentList.get(position) != null && _viewHolder != null) {


            Appointment currentAppointment = _appointmentList.get(position);

            _viewHolder.title.setText(currentAppointment.getCustomerName());
            _viewHolder.service.setText(currentAppointment.getService().getTitle());
            _viewHolder.dateTime.setText(currentAppointment.getDate());


        }
        return convertView;
    }
}
