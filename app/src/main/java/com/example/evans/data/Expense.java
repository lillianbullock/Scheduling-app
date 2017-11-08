package com.example.evans.data;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.time.LocalDateTime;

/**
 * Created by Brooke Nelson on 10/30/2017.
 * Expense class:
 *  This class will control the Expenses of
 *      the Program.
 *
 */

public class Expense implements Comparable {
    private String _name;
    private Double _price;
    private LocalDateTime _date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    Expense(){
        _name = "";
        _price = 0.0;
        _date = LocalDateTime.now();
    }

    Expense(String name, Double price, LocalDateTime date){
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

    public void setDate(LocalDateTime date) { this._date = date; }
    public LocalDateTime getDate() { return _date; }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
}
