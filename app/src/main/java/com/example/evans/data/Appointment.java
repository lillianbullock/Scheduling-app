package com.example.evans.data;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Class to handle appointment information
 */
public class Appointment implements Comparable, Financial {
    private String _id;
    private String _title;
    private LocalDate _date;
    private LocalTime _time;
    private String _customerId;
    private Boolean _due;
    private Service _service;
    private Boolean _attended;
    
    public Appointment() {
        _title = "";
        _date = LocalDate.now();
        _time = LocalTime.now();
        _customerId = null;
        _due = false;
        _service = null;
        _attended = false;
    }

    public Appointment(String title, LocalDate date, String customerId, Boolean due,
                Service service, Boolean attended) {
        this._title = title;
        this._date = date;
        this._customerId = customerId;
        this._due = due;
        this._service = service;
        this._attended = attended;
    }

    public Appointment(String title, LocalDate date, LocalTime time, String customerId, Service service) {
        this._title = title;
        this._date = date;
        this._customerId = customerId;
        this._service = service;
        this._time = time;
    }

    public String getId() { return _id; }
    public void setId(String id) { _id = id;}

    public String getTitle() { return _title; }
    public void setTitle(String title) { this._title = title; }

    @Exclude //firebase has issues with these, and so exclude them
    public LocalDate getDateObject() {return _date;}
    @Exclude
    public void setDateObject(LocalDate date) {_date = date;}
    public String getDate() { return _date.toString(); }
    public void setDate(String dateString) { this._date = LocalDate.parse(dateString); }

    @Exclude
    public LocalTime getTimeObject() {return _time;}
    @Exclude
    void setTimeObject(LocalTime time) {_time = time;}
    public String getTime() { return _time.toString(); }
    public void setTime(String timeString) { this._time = LocalTime.parse(timeString); }

    public String getCustomerId() { return _customerId; }
    public void setCustomerId(String customerId) { this._customerId = customerId; }

    public Boolean isDue() { return _due; }
    public void setDue(Boolean due) { this._due = due; }

    public Service getService() { return _service; }
    public void setService(Service service) { this._service = service; }

    public Boolean isAttended() { return _attended; }
    public void setAttended(Boolean attended) { this._attended = attended; }

    @Override
    public int compareTo(@NonNull Object o) {
        Appointment appointment2 = (Appointment) o;
        if (this._date.isAfter(appointment2._date))
            return 1;

        if (this._date.isBefore(appointment2._date))
            return -1;

        return 0;
    }

    @Override
    public double getReport() {
        return (_service.getPrice());
    }
}
