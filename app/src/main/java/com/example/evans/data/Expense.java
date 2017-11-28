package com.example.evans.data;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import org.joda.time.LocalDate;

/**
 * Created by Brooke Nelson on 10/30/2017.
 * Expense class:
 *  This class will control the Expenses of
 *      the Program.
 *
 */

public class Expense implements Comparable, Financial {
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

    public void setDate(LocalDate date) { this._date = date; }
    public LocalDate getDate() { return _date; }

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
