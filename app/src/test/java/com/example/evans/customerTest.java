package com.example.evans;

import com.example.evans.data.Customer;

import org.junit.Test;

import org.joda.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Tests for the customer class
 * Tests the 2 constructors as well as the compareTo
 */

public class customerTest {
    @Test
    public void testCons() throws Exception {
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
        assertEquals("customer compareTo should be less", nonDefCustomer.compareTo(defaultCustomer), -1);
        assertEquals("customer compareTo should be same", nonDefCustomer.compareTo(nonDefCustomer), 0);


        /* TODO test this
    @Override
    public int compareTo(@NonNull Object o) {
        //Customer customer1 = (Customer) o;

        return this._name.compareTo(((Customer) o)._name);
    }
*/
    }
}
