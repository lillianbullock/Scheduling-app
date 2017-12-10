package com.example.evans.data;

import android.support.annotation.NonNull;

/**
 * Services offered and corresponding prices
 */
public class Service implements Comparable {
    private String _id;
    private String _title;
    private String _description;
    private Double _price;

    public Service(){
        _id = "";
        _title = "";
        _description = "";
        _price = 0.0;
    }

    public Service(String title, String description, Double price){
        _id = "";
        _title = title;
        _description = description;
        _price = price;
    }

    public String getId() { return _id; }
    public void setId(String id) { _id = id;}

    public String getTitle() { return _title; }
    public void setTitle(String title) { this._title = title; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { this._description = description; }

    public Double getPrice() { return _price; }
    public void setPrice(Double price) { this._price = price; }

    @Override
    public int compareTo(@NonNull Object o) {
        return this._title.compareTo(((Service) o)._title);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this){
            return true;
        }

        if (obj == null || !(obj instanceof Service)){
            return false;
        }

        // typecast obj to Service
        Service service = (Service) obj;

        return this._id.equals(service._id);
    }
    @Override
    public int hashCode() {
        return _id.hashCode();
    }

}
