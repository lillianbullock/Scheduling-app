package com.example.evans.ui;

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
class AppointmentAdapter extends ArrayAdapter<Appointment> {

    private ArrayList<Appointment> _appointmentList = new ArrayList<>();

    public AppointmentAdapter(Context context, int textViewResourceId, ArrayList<Appointment> objects) {
        super(context, textViewResourceId, objects);
        _appointmentList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.appointment_adapter, null);

        TextView title = (TextView) view.findViewById(R.id.appointment_adapter_title);
        TextView service = (TextView) view.findViewById(R.id.appointment_adapter_service);
        TextView dateTime = (TextView) view.findViewById(R.id.appointment_adapter_date_time);

        title.setText(_appointmentList.get(position).getTitle());
        service.setText(_appointmentList.get(position).getService().getTitle());
        //TODO put dateTime in a better format
        dateTime.setText(_appointmentList.get(position).getDate().toString());

        return view;
    }
}
