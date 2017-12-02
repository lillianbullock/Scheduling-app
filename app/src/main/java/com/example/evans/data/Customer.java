package com.example.evans.data;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.google.firebase.database.Exclude;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * Class to handle customer information
 */
public class Customer implements Comparable{
    private String _id;
    private String _name;
    private String _email;
    private String _phone;
    private LocalDate _dateAdded;

    public Customer(){
        _id = "";
        _name = "";
        _email = "";
        _phone = "";
        _dateAdded = LocalDate.now();
    }

    public Customer(String id, String name, String email, String phone, LocalDate dateAdded){
        _id = id;
        _name = name;
        _email = email;
        _phone = phone;
        _dateAdded = dateAdded;
    }

    /**
     * This is the same as getDateAdded but it returns a date not a string.
     * This is for us to use in normal code but should be ignored by firebase
     * @return
     */
    @Exclude
    public LocalDate getDateAddedDate() { return _dateAdded; }


    /**
     * This is the same as setDateAdded but it receives a date not a string.
     * This is for us to use in normal code but should be ignored by firebase
     * @return
     */
    @Exclude
    public void setDateAddedDate(LocalDate dateAdded) { this._dateAdded = dateAdded; }

    public String getDateAdded() {
        return _dateAdded.toString();
    }

    public  void setDateAdded(String dateString) {
        this._dateAdded = LocalDate.parse(dateString);
    }

    public void setId(String id) { this._id = id; }
    public String getId() { return _id; }

    public void setName(String name) { this._name = name; }
    public String getName() { return _name; }

    public void setEmail(String email) { this._email = email; }
    public String getEmail() { return _email; }

    public void setPhone(String phone) { this._phone = phone; }
    public String getPhone() { return _phone; }

    public List findAppointments(){
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(@NonNull Object o) {
        Customer customer1 = (Customer) o;

        return this._name.compareTo(customer1._name);
    }
}
