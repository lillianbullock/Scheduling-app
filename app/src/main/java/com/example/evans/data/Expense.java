package com.example.evans.data;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import org.joda.time.LocalDate;

/**
 * Class to handle expense information
 */
public class Expense implements Comparable, Financial {
    private String _id;
    private String _name;
    private Double _price;
    private LocalDate _date;

    public Expense(){
        _id = "";
        _name = "";
        _price = 0.0;
        _date = LocalDate.now();
    }

    public Expense(String name, Double price, LocalDate date){
        _id = "";
        _name = name;
        _price = price;
        _date = date;
    }

    public String getId() { return _id; }
    public void setId(String id) { _id = id;}

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

    public String getDate() { return _date.toString(); }
    public void setDate(String dateString) { this._date = LocalDate.parse(dateString); }
    @Exclude
    public LocalDate getDateObject() { return _date; }
    @Exclude
    public void setDateObject(LocalDate date) { this._date = date; }

    @Override
    public int compareTo(@NonNull Object o) {
        Expense expense1 = (Expense) o;
        if (this._date.isAfter(expense1._date))
            return 1;

        if (this._date.isBefore(expense1._date))
            return -1;

        return 0;
    }

    @Override
    public double getReport() { return _price; }

    @Override
    public boolean equals(Object obj) {

        if (obj == this){
            return true;
        }

        if (obj == null || !(obj instanceof Expense)){
            return false;
        }

        // typecast obj to Expense
        Expense expense = (Expense) obj;
        return this._id.equals(expense._id);
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }
}