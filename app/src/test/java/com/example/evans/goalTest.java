package com.example.evans;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the Goal class
 * Tests the default constructor
 * Created by Brooke Nelson on 11/2/2017.
 */

public class goalTest {

    /*@Test
    public void testCons() throws Exception{
        String title = "Some Goal we set";
        String description = "This goal we want to do";
        LocalDateTime dueDate = LocalDateTime.now();
        Boolean isRepeat = true;
        LocalDateTime startDate = LocalDateTime.of(2017, 7, 11, 9, 30);
        TimePeriod repeatCycle = TimePeriod.Month;

        Goal goal = new Goal(title, description, dueDate, startDate, repeatCycle);

        //Just in case i feel like we need to check the Default to make sure it doesn't have weird values.
        Goal goal1 = new Goal();
        assertNull(goal1);

        //First we must check to see if the Appointment itself was not created correctly
        assertNotNull(goal);

        //Then we need to check the Values that were placed were also correct
        assertEquals("These two things don't match", "Some Goal we set", goal.getTitle());
        assertEquals("These two things don't match", "This goal we want to do", goal.getDescription());

        assertEquals(LocalDateTime.now(), goal.getDueDate());
        assertEquals(LocalDateTime.of(2017, 7, 11, 9, 30), goal.getStartDate());
        assertEquals(TimePeriod.Month, goal.getRepeatCycle());
    }*/
}
