package com.example.evans.data;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import org.joda.time.LocalDate;


/**
 * Sale class:
 * This is the Sale model. It models each sale that was made in the college.
 * It should mostly be getters and setters
 *
 * Version: 0.0.1
 */

public class Sale implements Comparable, Financial{
    private Service _service;
    private Double _price;
    private LocalDate _date;
    private Customer _customer;

    public Sale(){
        _service = new Service();
        _price = 0.0;
        _date = LocalDate.now();
        _customer = new Customer();
    }

    public Sale(Service service, Double price, LocalDate date,
         Customer customer){
        _service = service;
        _price = price;
        _date = date;
        _customer = customer;
    }

    public Sale(Service service, Double price, LocalDate dateTime){
        _service = service;
        _price = price;
        _date = dateTime;
    }


    public Service getService() { return _service; }
    public void setService(Service service) { this._service = service; }

    public Double getPrice() { return _price; }
    public void setPrice(Double price) { this._price = price; }

    public LocalDate getDate() { return _date; }
    public void setDate(LocalDate dateTime) { this._date = dateTime; }

    public Customer getCustomer() { return _customer; }
    public void setCustomer(Customer customer) { this._customer = customer; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(@NonNull Object o) {
        Sale sale1 = (Sale) o;
        if(this._date.isAfter(sale1._date))
            return 1;
        else if(this._date.isBefore(sale1._date))
            return -1;
        else
            return 0;
    }

    @Override
    public double getReport() {
        return _price;
    }
}
