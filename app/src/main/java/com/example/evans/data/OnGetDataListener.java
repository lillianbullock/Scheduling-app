package com.example.evans.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * This is an interface to implement when making a firebase call since
 * it runs asynchronously
 */

public interface OnGetDataListener {
    //interface functions are public
    void onDataLoadStarted();
    void onDataLoadSucceed(DataSnapshot dataSnapshot);
    void onDataLoadFailed(DatabaseError databaseError);
}
