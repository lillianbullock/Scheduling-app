package com.example.evans;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.time.LocalDateTime;

/**
 * Sale class:
 * This is the Sale model. It models each sale that was made in the college.
 * It should mostly be getters and setters
 *
 * Version: 0.0.1
 */

public class Sale implements Comparable{
    private Service _service;
    private Double _price;
    private LocalDateTime _dateTime;
    private Customer _customer;
    private Boolean _isByAppointment;
    private Boolean _isByCustomer;

    @RequiresApi(api = Build.VERSION_CODES.O)
    Sale(){
        _service = new Service();
        _price = 0.0;
        _dateTime = LocalDateTime.now();
        _customer = new Customer();
        _isByAppointment = false;
        _isByCustomer = false;
    }

    Sale(Service service, Double price, LocalDateTime dateTime,
         Customer customer, Boolean isByAppointment, Boolean isByCustomer){
        _service = service;
        _price = price;
        _dateTime = dateTime;
        _customer = customer;
        _isByCustomer = isByCustomer;
        _isByAppointment = isByAppointment;
    }

    public Service getService() { return _service; }
    public void setService(Service service) { this._service = service; }

    public Double getPrice() { return _price; }
    public void setPrice(Double price) { this._price = price; }

    public LocalDateTime getDateTime() { return _dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this._dateTime = dateTime; }

    public void setIsByAppointment(Boolean isByAppointment) { this._isByAppointment = isByAppointment; }
    public Boolean getIsByAppointment() { return _isByAppointment; }

    public void setIsByCustomer(Boolean isByCustomer) { this._isByCustomer = isByCustomer; }
    public Boolean getIsByCustomer() { return _isByCustomer; }

    public Boolean isAppointment(){ return false; }
    public  Boolean byCustomer() { return false; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(@NonNull Object o) {
        Sale sale1 = (Sale) o;
        if(this._dateTime.isAfter(sale1._dateTime))
            return 1;
        else if(this._dateTime.isBefore(sale1._dateTime))
            return -1;
        else
            return 0;
    }
}
