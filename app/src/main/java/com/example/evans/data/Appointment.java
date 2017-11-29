package com.example.evans.data;


import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.google.firebase.database.Exclude;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;


/**
 * Holds the information relevant to each appointment
 *
 */

public class Appointment implements Comparable, Financial {
    private String _id;
    private String _title;
    private LocalDate _date;
    private LocalTime _time;
    private String _customerId;
    private Boolean _due;
    private Service _service;
    private Boolean _succeed;


    public Appointment() {
        _title = "";
        _date = LocalDate.now();
        _time = LocalTime.now();
        _customerId = null;
        _due = false;
        _service = null;
        _succeed = false;
    }

    public Appointment(String title, LocalDate date, String customerId, Boolean due,
                Service service, Boolean succeed) {
        this._title = title;
        this._date = date;
        this._customerId = customerId;
        this._due = due;
        this._service = service;
        this._succeed = succeed;
    }

    public Appointment(String title, LocalDate date, LocalTime time, String customerId, Service service) {
        this._title = title;
        this._date = date;
        this._customerId = customerId;
        this._service = service;
        this._time = time;
    }

    public String getTitle() { return _title; }
    public String getCustomerId() { return _customerId; }
    public Boolean isDue() { return _due; }
    public Service getService() { return _service; }
    public Boolean isSucceed() { return _succeed; }
    public String getDate() { return _date.toString(); }
    public String getTime() { return _time.toString(); }

    @Exclude
    public LocalDate getAppointDate() {return _date;}

    @Exclude
    public LocalTime getAppointmentTime() {return _time;}

    public String getId() { return _id; }
    public void setTitle(String title) { this._title = title; }



    public void setId(String id) { _id = id;}
    public void setDate(String dateString) { this._date = LocalDate.parse(dateString); }
    public void setCustomerId(String customerId) { this._customerId = customerId; }
    public void setDue(Boolean due) { this._due = due; }
    public void setService(Service service) { this._service = service; }
    public void setSucceed(Boolean succeed) { this._succeed = succeed; }
    public void setTime(String timeString) { this._time = LocalTime.parse(timeString); }

    @Exclude
    public void setAppointmentDate(LocalDate date) {_date = date;}

    @Exclude void setAppointmentTime(LocalTime time) {_time = time;}


    @Override
    public int compareTo(@NonNull Object o) {
        Appointment appointment2 = (Appointment) o;
        if(this._date.isAfter(appointment2._date))
            return 1;
        else if(this._date.isBefore(appointment2._date))
            return -1;
        else
            return 0;
    }


    @Override
    public double getReport() {
        return (_service.getPrice());
    }
}
