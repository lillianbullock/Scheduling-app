package com.example.evans;

import java.time.LocalDateTime;

/**
 * Created by Brooke Nelson on 10/30/2017.
 * This is the Customer class:
 *  It will handle and work with the customer information
 *
 */

public class Customer {
    private String _id;
    private String _name;
    private String _email;
    private String _phone;

    //Not really sure about the constructor or this one there were alot of option
    private LocalDateTime _dateAdded;
    //CHANGE TO APPOINTMENT LIST WHEN WE DO APPOINTMENT CLASS
    private Appointment _appointments;

    Customer(){
        _id = "";
        _name = "";
        _email = "";
        _phone = "";

        _appointments = null;
        _dateAdded = null;
    }

    public LocalDateTime getDateAdded() {
        return _dateAdded;
    }
    public void setId(String id) {
        this._id = id;
    }
    public void setName(String name) {
        this._name = name;
    }
    public void setEmail(String email) {
        this._email = email;
    }
    public void setPhone(String phone) {
        this._phone = phone;
    }


    public void setDateAdded(LocalDateTime dateAdded) {
        this._dateAdded = dateAdded;
    }
    public String getId() {
        return _id;
    }
    public String getName() {
        return _name;
    }
    public String getEmail() {
        return _email;
    }
    public String getPhone() {
        return _phone;
    }
}
