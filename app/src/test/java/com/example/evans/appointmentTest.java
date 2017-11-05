package com.example.evans;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by Brooke Nelson on 10/31/2017.
 */

public class appointmentTest {

    /*

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
        assertEquals("These two things dont match", "Hair Cut", appointment.getTitle());
        assertEquals("These two things dont match", LocalDateTime.now() , appointment.getDate());

        assertNotNull(customer);
        assertNotNull(service);

        assertFalse(appointment.isDue());
        assertTrue(appointment.isHasPaid());
        assertTrue(appointment.isSucceed());
    }
    */

}
