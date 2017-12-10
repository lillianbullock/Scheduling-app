package com.example.evans;

import com.example.evans.data.Customer;

import org.junit.Test;

import org.joda.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Tests for the customer class
 * Tests the 2 constructors as well as the compareTo
 */
public class CustomerTest {
    @Test
    public void test() throws Exception {
        // default constructor
        Customer defaultCustomer = new Customer();
        assertEquals("default Customer id", "", defaultCustomer.getId());
        assertEquals("default Customer name", "", defaultCustomer.getName());
        assertEquals("default Customer email", "", defaultCustomer.getEmail());
        assertEquals("default Customer phone", "", defaultCustomer.getPhone());
        assertEquals("default Customer date", LocalDate.now(), defaultCustomer.getDateAddedObject());

        // non-default constructor
        String id = "customerTest";
        String name = "John Doe";
        String email = "email@byui.edu";
        String phone = "111-111-1111";
        LocalDate dateAdded = new LocalDate(1999, 6, 20);

        Customer nonDefCustomer = new Customer(id, name, email, phone, dateAdded);
        assertEquals("non-default Customer id", "customerTest", nonDefCustomer.getId());
        assertEquals("non-default Customer name", "John Doe", nonDefCustomer.getName());
        assertEquals("non-default Customer email", "email@byui.edu", nonDefCustomer.getEmail());
        assertEquals("non-default Customer phone", "111-111-1111", nonDefCustomer.getPhone());
        assertEquals("non-default Customer date", new LocalDate(1999, 6, 20), nonDefCustomer.getDateAddedObject());

        // test compareTo
        assertTrue("customer compareTo should be positive (greater)", 0 < nonDefCustomer.compareTo(defaultCustomer));
        assertEquals("customer compareTo should be zero (same)", 0, nonDefCustomer.compareTo(nonDefCustomer));

        //takes the date from one, and sets the other's date to that
        String holdDate = nonDefCustomer.getDateAdded();
        defaultCustomer.setDateAdded(holdDate);
        //then makes sure they're the same
        assertEquals("string date getters and setters", nonDefCustomer.getDateAddedObject(), defaultCustomer.getDateAddedObject());

    }
}
