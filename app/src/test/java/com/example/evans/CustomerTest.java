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
        assertEquals("default Customer id", defaultCustomer.getId(), "");
        assertEquals("default Customer name", defaultCustomer.getName(), "");
        assertEquals("default Customer email", defaultCustomer.getEmail(), "");
        assertEquals("default Customer phone", defaultCustomer.getPhone(), "");
        assertEquals("default Customer date", defaultCustomer.getDateAddedObject(), LocalDate.now());

        // non-default constructor
        String id = "customerTest";
        String name = "John Doe";
        String email = "email@byui.edu";
        String phone = "111-111-1111";
        LocalDate dateAdded = new LocalDate(1999, 6, 20);

        Customer nonDefCustomer = new Customer(id, name, email, phone, dateAdded);
        assertEquals("non-default Customer id", nonDefCustomer.getId(), "customerTest");
        assertEquals("non-default Customer name", nonDefCustomer.getName(), "John Doe");
        assertEquals("non-default Customer email", nonDefCustomer.getEmail(), "email@byui.edu");
        assertEquals("non-default Customer phone", nonDefCustomer.getPhone(), "111-111-1111");
        assertEquals("non-default Customer date", nonDefCustomer.getDateAddedObject(), new LocalDate(1999, 6, 20));

        // test compareTo
        assertTrue("customer compareTo should be positive", 0 < nonDefCustomer.compareTo(defaultCustomer));
        assertEquals("customer compareTo should be same", 0, nonDefCustomer.compareTo(nonDefCustomer));
    }
}
