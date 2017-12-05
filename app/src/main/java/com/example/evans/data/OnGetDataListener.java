package com.example.evans.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * This is an interface to implement when making a firebase call since
 * it runs asynchronously
 */

public interface OnGetDataListener {
    //strings are all static
    String SERVICES = "Services";
    String CUSTOMERS = "Customers";
    String APPOINTMENTS = "Appointments";
    String GOALS = "Goals";
    String SALES = "Sales";
    String EXPENSES = "Expenses";

    //interface functions are public
    void onDataLoadStarted();
    void onDataLoadSucceed(DataSnapshot dataSnapshot);
    void onDataLoadFailed(DatabaseError databaseError);
}
