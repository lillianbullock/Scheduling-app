package com.example.evans.data;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import org.joda.time.LocalDate;


/**
 * Class to handle sale information
 * Models each sale that was made in the college.
 */
public class Sale implements Comparable, Financial{
    private String _id;
    private Service _service;
    private Double _price;
    private LocalDate _date;
    private String  _customerId;

    public Sale(){
        _service = new Service();
        _price = 0.0;
        _date = LocalDate.now();
        _customerId = "";
    }

    public Sale(Service service, Double price, LocalDate date,
         String customerId){
        _service = service;
        _price = price;
        _date = date;
        _customerId = customerId;
    }

    public Sale(Service service, Double price, LocalDate dateTime){
        _service = service;
        _price = price;
        _date = dateTime;
    }

    public String getId() { return _id; }
    public void setId(String id) { _id = id;}

    public Service getService() { return _service; }
    public void setService(Service service) { this._service = service; }

    public Double getPrice() { return _price; }
    public void setPrice(Double price) { this._price = price; }

    public String getDate() { return _date.toString(); }
    public void setDate(String dateString) { this._date = LocalDate.parse(dateString); }
    @Exclude public LocalDate getDateObject() { return _date; }
    @Exclude public void setDateObject(LocalDate dateTime) { this._date = dateTime; }

    public String getCustomerId() { return _customerId; }
    public void setCustomerId(String customerId) { this._customerId = customerId; }

    @Override
    public int compareTo(@NonNull Object o) {
        Sale sale1 = (Sale) o;
        if (this._date.isAfter(sale1._date))
            return 1;

        if (this._date.isBefore(sale1._date))
            return -1;

        return 0;
    }

    @Override
    public double getReport() {
        return _price;
    }
}
