package com.example.evans.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Interface to implement when making a firebase call since
 * it runs asynchronously
 */

public interface OnGetDataListener {

    void onDataLoadStarted();
    void onDataLoadSucceed(DataSnapshot dataSnapshot);
    void onDataLoadFailed(DatabaseError databaseError);
}
