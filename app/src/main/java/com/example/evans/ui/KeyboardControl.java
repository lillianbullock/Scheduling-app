package com.example.evans.ui;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Provides a static method to close the keyboard
 */

public class KeyboardControl {


    /**
     * Close the keyboard
     * @param activity: THe activity that's hosting the keyboard
     */
    public static void closeKeyboard(Activity activity) {

        try {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // Do nothing
        }

    }

    private KeyboardControl(){

    }


}
