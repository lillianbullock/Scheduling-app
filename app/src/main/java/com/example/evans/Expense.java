package com.example.evans;

/**
 * Created by Brooke Nelson on 10/30/2017.
 * Expense class:
 *  This class will control the Expenses of
 *      the Program.
 *
 */

public class Expense {
    private String _name;
    private Double _price;

    Expense(){
        _name = "";
        _price = 0.0;
    }

    Expense(String name, Double price){
        _name = name;
        _price = price;
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
}
