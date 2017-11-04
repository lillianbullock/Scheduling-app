package com.example.evans;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalDateTime;

/**
 * Sale class:
 * This is the Sale model. It models each sale that was made in the college.
 * It should mostly be getters and setters
 *
 * Version: 0.0.1
 */

public class Sale {
    private Service _service;
    private Double _price;
    private LocalDateTime _dateTime;
    private Customer _customer;

    @RequiresApi(api = Build.VERSION_CODES.O)
    Sale(){
        _service = new Service();
        _price = 0.0;
        _dateTime = LocalDateTime.now();
        _customer = new Customer();
    }

    Sale(Service service, Double price, LocalDateTime dateTime,
         Customer customer){
        _service = service;
        _price = price;
        _dateTime = dateTime;
        _customer = customer;
    }

    public Service getService() { return _service; }
    public void setService(Service service) { this._service = service; }

    public Double getPrice() { return _price; }
    public void setPrice(Double price) { this._price = price; }

    public LocalDateTime getDateTime() { return _dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this._dateTime = dateTime; }

    public Customer getCustomer() { return _customer; }
    public void setCustomer(Customer customer) { this._customer = customer; }
}
