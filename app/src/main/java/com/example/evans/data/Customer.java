package com.example.evans.data;

import android.support.annotation.NonNull;

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
     * @return date the customer was added
     */
    @Exclude
    public LocalDate getDateAddedObject() { return _dateAdded; }
    /**
     * This is the same as setDateAdded but it receives a date not a string.
     * This is for us to use in normal code but should be ignored by firebase
     */
    @Exclude
    public void setDateAddedObject(LocalDate dateAdded) { this._dateAdded = dateAdded; }

    public String getDateAdded() { return _dateAdded.toString(); }
    public  void setDateAdded(String dateString) { this._dateAdded = LocalDate.parse(dateString); }

    public void setId(String id) { this._id = id; }
    public String getId() { return _id; }

    public void setName(String name) { this._name = name; }
    public String getName() { return _name; }

    public void setEmail(String email) { this._email = email; }
    public String getEmail() { return _email; }

    public void setPhone(String phone) { this._phone = phone; }
    public String getPhone() { return _phone; }

    @Override
    public int compareTo(@NonNull Object o) {
        Customer customer1 = (Customer) o;

        int comp = this._name.compareTo(customer1._name);
        // if name is same compare dateAdded
        if (comp == 0) {
            this._dateAdded.compareTo(customer1.getDateAddedObject());
        }
        return comp;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this){
            return true;
        }

        if (obj == null || !(obj instanceof Customer)){
            return false;
        }

        // typecast obj to Customer
        Customer customer = (Customer) obj;

        return this._id.equals(customer._id);
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }
}
