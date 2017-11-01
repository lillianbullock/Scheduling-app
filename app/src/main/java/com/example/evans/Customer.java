package com.example.evans;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Brooke Nelson on 10/30/2017.
 * This is the Customer class:
 *  It will handle and work with the customer information
 *
 */

public class Customer {
    String _id;
    String _name;
    String _email;
    String _phone;

    //Not really sure about the constructor or this one there were alot of option
    LocalDate _dateAdded;
    //CHANGE TO APPOINTMENT LIST WHEN WE DO APPOINTMENT CLASS
    Appointment _appointments;

    Customer(){
        _id = "";
        _name = "";
        _email = "";
        _phone = "";
        _dateAdded = LocalDate.of(int date; int date1; int date2);

        _appointments = new Appointment();
    }

    public LocalDate getDateAdded() {
        return _dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this._dateAdded = dateAdded;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getId() {
        return _id;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public String getEmail() {
        return _email;
    }

    public void setPhone(String phone) {
        this._phone = phone;
    }

    public String getPhone() {
        return _phone;
    }


}
