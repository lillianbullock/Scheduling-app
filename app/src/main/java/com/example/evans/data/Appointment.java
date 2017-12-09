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
    private String _customerName;
    private LocalDate _date;
    private LocalTime _time;
    private String _customerId;
    private Service _service;
    private Boolean _due;
    private Boolean _attended;
    
    public Appointment() {
        _id = null;
        _customerName = "";
        _date = LocalDate.now();
        _time = LocalTime.now();
        _customerId = null;
        _service = null;
        _due = false;
        _attended = false;
    }

    public Appointment(String customerName, LocalDate date, LocalTime time, String customerId, Boolean due,
                Service service, Boolean attended) {
        this._id = null;
        this._customerName = customerName;
        this._date = date;
        this._time = time;
        this._customerId = customerId;
        this._due = due;
        this._service = service;
        this._attended = attended;
    }

    public Appointment(String customerName, LocalDate date, LocalTime time, String customerId, Service service) {
        this._id = null;
        this._customerName = customerName;
        this._date = date;
        this._time = time;
        this._customerId = customerId;
        this._service = service;
        this._due = false;
        this._attended = false;
    }


    public String getId() { return _id; }
    public void setId(String id) { _id = id;}

    public String getCustomerName() { return _customerName; }
    public void setCustomerName(String customerName) { this._customerName = customerName; }

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
