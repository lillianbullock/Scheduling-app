package com.example.evans.data;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import org.joda.time.LocalDateTime;


/**
 * Holds the information relevant to each appointment
 *
 */

public class Appointment implements Comparable {
    private String _title;
    private LocalDateTime _date;
    private String _customerId;
    private Boolean _due;
    private Service _service;
    private Boolean _succeed;
    private Boolean _hasPaid;


    public Appointment() {
        _title = "";
        _date = LocalDateTime.now();
        _customerId = null;
        _due = false;
        _service = null;
        _succeed = false;
        _hasPaid = false;
    }

    public Appointment(String title, LocalDateTime date, String customerId, Boolean due,
                Service service, Boolean succeed, Boolean hasPaid) {
        this._title = title;
        this._date = date;
        this._customerId = customerId;
        this._due = due;
        this._service = service;
        this._succeed = succeed;
        this._hasPaid = hasPaid;
    }

    public Appointment(String title, LocalDateTime date, String customerId, Service service) {
        this._title = title;
        this._date = date;
        this._customerId = customerId;
        this._service = service;
    }

    public String getTitle() { return _title; }
    public LocalDateTime getDate() { return _date; }
    public String getCustomerId() { return _customerId; }
    public Boolean isDue() { return _due; }
    public Service getService() { return _service; }
    public Boolean isSucceed() { return _succeed; }
    public Boolean isHasPaid() { return _hasPaid; }

    public void setTitle(String title) { this._title = title; }
    public void setDate(LocalDateTime date) { this._date = date; }
    public void setCustomerId(String customerId) { this._customerId = customerId; }
    public void setDue(Boolean due) { this._due = due; }
    public void setService(Service service) { this._service = service; }
    public void setSucceed(Boolean succeed) { this._succeed = succeed; }
    public void setHasPaid(Boolean hasPaid) { this._hasPaid = hasPaid; }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
}
