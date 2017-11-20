package com.example.evans.data;

/**
 * This exception will be thrown if an invalid customer is passed to a class/activity/fragment
 */

public class InvalidCustomerException extends Exception {

    public InvalidCustomerException(String message) {
        super(message);
    }
}


