package com.example.evans;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * Created by Brooke Nelson on 10/30/2017.
 * Service class collects and compare the
 * Services offered and the prices that they correspond to
 */

public class Service implements Comparable {
    private String _title;
    private String _description;
    private Double _price;

    Service(){
        _title = "";
        _description = "";
        _price = 0.0;
    }

    Service(String title, String description, Double price){
        _title = title;
        _description = description;
        _price = price;
    }

    public String getTitle() {
        return _title;
    }
    public void setTitle(String title) {
        this._title = title;
    }

    public void setDescription(String description) {
        this._description = description;
    }
    public String getDescription() {
        return _description;
    }

    public void setPrice(Double price) {
        this._price = price;
    }
    public Double getPrice() {
        return _price;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compareTo(@NonNull Object o) {

        Service service1 = (Service) o;
        return this._title.compareTo(service1._title);

    }
}
