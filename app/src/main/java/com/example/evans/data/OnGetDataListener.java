package com.example.evans.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * This is an interface to implement when making a firebase call since
 * it runs asynchronously
 */

public interface OnGetDataListener {

    static String SERVICES = "Services";
    static String CUSTOMERS = "Customers";
    static String APPOINTMENTS = "Appointments";
    static String GOALS = "Goals";
    static String SALES = "Sales";
    static String EXPENSES = "Expenses";

    public void onDataLoadStarted();
    public void onDataLoadSucceed(DataSnapshot dataSnapshot);
    public void onDataLoadFailed(DatabaseError databaseError);
}
