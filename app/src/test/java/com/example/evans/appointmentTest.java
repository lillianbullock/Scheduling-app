package com.example.evans;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Tests for the appointment class
 * Tests the default constructior
 * Created by Brooke Nelson on 10/31/2017.
 */

public class appointmentTest {

    @Test
    public void testCons() throws Exception{
        String title = "Hair cut";
        LocalDateTime dateTime = LocalDateTime.now();
        Customer customer = new Customer();
        Boolean due = false;
        Service service = new Service();
        Boolean succeed = true;
        Boolean hasPaid = true;

        Appointment appointment = new Appointment(title, dateTime, customer, due, service,
                succeed, hasPaid);

        //Just in case i feel like we need to check the Default to make sure it doesn't have weird values.
        Appointment appointmentNew = new Appointment();
        assertNull(appointmentNew);

        //First we must check to see if the Appointment itself was not created correctly
        assertNotNull(appointment);

        //Then we need to check the Values that were placed were also correct
        assertEquals("Title value for appointment not assigned properly", "Hair Cut", appointment.getTitle());
        assertEquals("Date value for appointment not assigned properly", LocalDateTime.now() , appointment.getDate());

        assertNotNull(customer);
        assertNotNull(service);

        assertFalse(appointment.isDue());
        assertTrue(appointment.isHasPaid());
        assertTrue(appointment.isSucceed());
    }

}
