package com.example.evans;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Brooke Nelson on 11/2/2017.
 */

public class customerTest {

    @Test
    public void testCons() throws Exception{
        String id = "customerTest";
        String name = "John Doe";
        String email = "email@byui.edu";
        String phone = "490-123-1111";
        LocalDateTime dateAdded = LocalDateTime.now();
        Appointment appointment = new Appointment();

        Customer customer = new Customer(id, name, email, phone, dateAdded, appointment);

        //Just in case i feel like we need to check the Default to make sure it doesn't have weird values.
        Customer customer1 = new Customer();
        assertNull(customer1);

        //First we must check to see if the Appointment itself was not created correctly
        assertNotNull(customer);

        //Then we need to check the Values that were placed were also correct
        assertEquals("Getting Id is not right", "customerTest", customer.getId());
        assertEquals("Getting the Name", "John Doe", customer.getName());
        assertEquals("Getting the Date", LocalDateTime.now() , customer.getDateAdded());

        assertNotNull(customer);
        assertNotNull(appointment);

        assertFalse(appointment.isDue());
        assertTrue(appointment.isHasPaid());
        assertTrue(appointment.isSucceed());
    }
}
