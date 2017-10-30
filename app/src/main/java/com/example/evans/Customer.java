package com.example.evans;

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

    //CHANGE TO APPOINTMENT LIST WHEN WE DO APPOINTMENT CLASS
    List<String> _appointmentsList;

    Customer(){
        _id = "";
        _name = "";
        _email = "";
        _phone = "";
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
