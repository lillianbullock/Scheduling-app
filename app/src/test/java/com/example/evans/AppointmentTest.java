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
        assertEquals("default appointment id", defaultAppt.getId(), null);
        assertEquals("default appointment title", defaultAppt.getTitle(), "");
        assertEquals("default appointment date", defaultAppt.getDateObject(), LocalDate.now());
        // this runs a bit later than the constructor and so gets a different time
        //assertEquals("default appointment time", defaultAppt.getTimeObject(), LocalTime.now());
        assertEquals("default appointment customer id", defaultAppt.getCustomerId(), null);
        assertEquals("default appointment due", defaultAppt.isDue(), false);
        assertEquals("default appointment service", defaultAppt.getService(), null);
        assertEquals("default appointment attended", defaultAppt.isAttended(), false);

        // first non default constructor
        String title1 = "Hair cut";
        LocalDate date1 = new LocalDate(2345, 2, 5);
        LocalTime time1 = new LocalTime(12, 32, 12);
        String id1 = "customerID1";
        Boolean due1 = false;
        Service service1 = new Service();
        service1.setTitle("service1");
        service1.setPrice(2.50);
        Boolean attended1 = true;

        Appointment nonDef1Appt = new Appointment(title1, date1, time1, id1, due1, service1, attended1);
        assertEquals("appointment constructor1 id", nonDef1Appt.getId(), null);
        assertEquals("appointment constructor1 title", nonDef1Appt.getTitle(), "Hair cut");
        assertEquals("appointment constructor1 date", nonDef1Appt.getDateObject(), new LocalDate(2345, 2, 5));
        assertEquals("appointment constructor1 time", nonDef1Appt.getTimeObject(), new LocalTime(12, 32, 12));
        assertEquals("appointment constructor1 customer id", nonDef1Appt.getCustomerId(), "customerID1");
        assertEquals("appointment constructor1 due", nonDef1Appt.isDue(), due1);
        assertEquals("appointment constructor1 service", nonDef1Appt.getService().getTitle(), "service1");
        assertEquals("appointment constructor1 attended", nonDef1Appt.isAttended(), attended1);

        // second non default constructor
        String title2 = "Hair Dye";
        LocalDate date2 = new LocalDate(1998, 4, 6);
        LocalTime time2 = new LocalTime(13, 12, 43);
        String id2 = "customerID2";
        Service service2 = new Service();
        service2.setTitle("service2");
        service2.setPrice(3.00);

        Appointment nonDef2Appt = new Appointment(title2, date2, time2, id2, service2);
        assertEquals("appointment constructor2 id", nonDef2Appt.getId(), null);
        assertEquals("appointment constructor2 title", nonDef2Appt.getTitle(), "Hair Dye");
        assertEquals("appointment constructor2 date", nonDef2Appt.getDateObject(), new LocalDate(1998, 4, 6));
        assertEquals("appointment constructor2 time", nonDef2Appt.getTimeObject(), new LocalTime(13, 12, 43));
        assertEquals("appointment constructor2 customer id", nonDef2Appt.getCustomerId(), "customerID2");
        assertEquals("appointment constructor2 due", nonDef2Appt.isDue(), false);
        assertEquals("appointment constructor2 service", nonDef2Appt.getService().getTitle(), "service2");
        assertEquals("appointment constructor2 attended", nonDef2Appt.isAttended(), false);

        //testing compareTo
        assertEquals("appointment compareTo should be less", nonDef2Appt.compareTo(nonDef1Appt), -1);
        assertEquals("appointment compareTo should be same", nonDef2Appt.compareTo(nonDef2Appt), 0);

        //testing getReport
        assertEquals("appointment getReport #1", nonDef1Appt.getReport(), 2.50, 0);
        assertEquals("appointment getReport #2", nonDef2Appt.getReport(), 3.00, 0);

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

    }
}
