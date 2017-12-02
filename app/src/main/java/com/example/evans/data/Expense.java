package com.example.evans.data;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import org.joda.time.LocalDate;

/**
 * Created by Brooke Nelson on 10/30/2017.
 * Expense class:
 *  This class will control the Expenses of
 *      the Program.
 *
 */

public class Expense implements Comparable, Financial {
    private String _id;
    private String _name;
    private Double _price;
    private LocalDate _date;

    public Expense(){
        _name = "";
        _price = 0.0;
        _date = LocalDate.now();
    }

    public Expense(String name, Double price, LocalDate date){
        _name = name;
        _price = price;
        _date = date;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public Double getPrice() {
        return _price;
    }
    public void setPrice(Double price) {
        this._price = price;
    }


    public void setDate(String dateString) { this._date = LocalDate.parse(dateString); }

    @Exclude
    public void setDate(LocalDate date) { this._date = date; }

    public String getDate() { return _date.toString(); }

    @Exclude
    public LocalDate getSaleDate() { return _date; }

    public String getId() { return _id; }
    public void setId(String id) { _id = id;}

    @Override
    public int compareTo(@NonNull Object o) {
        Expense expense1 = (Expense) o;
        if(this._date.isAfter(expense1._date))
            return 1;
        else if(this._date.isBefore(expense1._date))
            return -1;
        else
            return 0;
    }

    @Override
    public double getReport() {
        return (-1 * _price);
    }
}
