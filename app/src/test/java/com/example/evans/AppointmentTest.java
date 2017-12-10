package com.example.evans;

import com.example.evans.data.Appointment;
import com.example.evans.data.Service;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the appointment class
 * Tests the 3 constructors as well as the compareTo and getReport
 */

public class AppointmentTest {
    @Test
    public void test() throws Exception {

        // default constructor
        Appointment defaultAppt = new Appointment();
        assertEquals("default appointment id", null, defaultAppt.getId());
        assertEquals("default appointment title", "", defaultAppt.getCustomerName());
        assertEquals("default appointment date", LocalDate.now(), defaultAppt.getDateObject());
        // this runs a bit later than the constructor and so gets a different time
        //assertEquals("default appointment time", defaultAppt.getTimeObject(), LocalTime.now());
        assertEquals("default appointment customer id", null, defaultAppt.getCustomerId());
        assertEquals("default appointment due", false, defaultAppt.isDue());
        assertEquals("default appointment service", null, defaultAppt.getService());
        assertEquals("default appointment attended", false, defaultAppt.isAttended());

        // first non default constructor
        String name1 = "Hair cut";
        LocalDate date1 = new LocalDate(2345, 2, 5);
        LocalTime time1 = new LocalTime(12, 32, 12);
        String id1 = "customerID1";
        Boolean due1 = false;
        Service service1 = new Service();
        service1.setTitle("service1");
        service1.setPrice(2.50);
        Boolean attended1 = true;

        Appointment nonDef1Appt = new Appointment(name1, date1, time1, id1, due1, service1, attended1);
        assertEquals("appointment constructor1 id", null, nonDef1Appt.getId());
        assertEquals("appointment constructor1 title", "Hair cut", nonDef1Appt.getCustomerName());
        assertEquals("appointment constructor1 date", new LocalDate(2345, 2, 5), nonDef1Appt.getDateObject());
        assertEquals("appointment constructor1 time", new LocalTime(12, 32, 12), nonDef1Appt.getTimeObject());
        assertEquals("appointment constructor1 customer id", "customerID1", nonDef1Appt.getCustomerId());
        assertEquals("appointment constructor1 due", due1, nonDef1Appt.isDue());
        assertEquals("appointment constructor1 service", "service1", nonDef1Appt.getService().getTitle());
        assertEquals("appointment constructor1 attended", attended1, nonDef1Appt.isAttended());

        // second non default constructor
        String name2 = "John Doe";
        LocalDate date2 = new LocalDate(1998, 4, 6);
        LocalTime time2 = new LocalTime(13, 12, 43);
        String id2 = "customerID2";
        Service service2 = new Service();
        service2.setTitle("service2");
        service2.setPrice(3.00);

        Appointment nonDef2Appt = new Appointment(name2, date2, time2, id2, service2);
        assertEquals("appointment constructor2 id", null, nonDef2Appt.getId());
        assertEquals("appointment constructor2 title", "John Doe", nonDef2Appt.getCustomerName());
        assertEquals("appointment constructor2 date", new LocalDate(1998, 4, 6), nonDef2Appt.getDateObject());
        assertEquals("appointment constructor2 time", new LocalTime(13, 12, 43), nonDef2Appt.getTimeObject());
        assertEquals("appointment constructor2 customer id", "customerID2", nonDef2Appt.getCustomerId());
        assertEquals("appointment constructor2 due", false, nonDef2Appt.isDue());
        assertEquals("appointment constructor2 service", "service2", nonDef2Appt.getService().getTitle());
        assertEquals("appointment constructor2 attended", false, nonDef2Appt.isAttended());

        //testing compareTo
        assertTrue("appointment compareTo should return negative", 0 > nonDef2Appt.compareTo(nonDef1Appt));
        assertEquals("appointment compareTo should return 0", 0, nonDef2Appt.compareTo(nonDef2Appt));

        //testing getReport
        assertEquals("appointment getReport #1", 2.50, nonDef1Appt.getReport(), 0);
        assertEquals("appointment getReport #2", 3.00, nonDef2Appt.getReport(), 0);

        //takes the date from one, and sets the other's date to that
        String holdDate = nonDef1Appt.getDate();
        nonDef2Appt.setDate(holdDate);
        //then makes sure they're the same
        assertEquals("string date getters and setters", nonDef1Appt.getDateObject(), nonDef2Appt.getDateObject());

        //takes the time from one, and sets the other's time to that
        String holdTime = nonDef1Appt.getTime();
        nonDef2Appt.setTime(holdTime);
        //then makes sure they're the same
        assertEquals("string date getters and setters", nonDef1Appt.getTimeObject(), nonDef2Appt.getTimeObject());

        nonDef1Appt.setId("idOfObject");
        nonDef2Appt.setId("idOfObject2");
        //they have different id's
        assertFalse("equals should return false", nonDef1Appt.equals(nonDef2Appt));
        assertFalse("different id should return different hash", nonDef1Appt.hashCode() == nonDef2Appt.hashCode());

        nonDef2Appt.setId("idOfObject");
        //they now have the same id
        assertTrue("equals should return true", nonDef1Appt.equals(nonDef2Appt));
        assertTrue("same id should return the same hash", nonDef1Appt.hashCode() == nonDef2Appt.hashCode());
    }
}
