package com.example.evans;

import com.example.evans.data.Goal;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Goal class
 * Tests the default and two non-default constructors.
 * Tests isPastDue and compareTo functions as well
 */
public class GoalTest {
    @Test
    public void test() throws Exception {
        // default constructor
        Goal defaultGoal = new Goal();
        assertEquals("default goal id", defaultGoal.getId(), "");
        assertEquals("default goal title", defaultGoal.getTitle(), "");
        assertEquals("default goal description", defaultGoal.getDescription(), "");
        assertEquals("default goal start date", defaultGoal.getStartDateObject(), LocalDate.now());
        assertEquals("default goal due date", defaultGoal.getDueDateObject(), LocalDate.now());
        assertEquals("default goal done", defaultGoal.isDone(), false);

        // non-default constructor
        String title1 = "Some Goal we set";
        String description1 = "This goal we want to do";
        LocalDate dueDate1 = new LocalDate(2018, 1, 5);
        LocalDate startDate1 = new LocalDate(2017, 7, 11);

        Goal nonDef1Goal = new Goal(title1, description1, dueDate1, startDate1);
        assertEquals("non-default goal id", "", nonDef1Goal.getId());
        assertEquals("non-default goal title", "Some Goal we set", nonDef1Goal.getTitle());
        assertEquals("non-default goal description", "This goal we want to do", nonDef1Goal.getDescription());
        assertEquals("non-default goal start date", new LocalDate(2017, 7, 11), nonDef1Goal.getStartDateObject());
        assertEquals("non-default goal due date", new LocalDate(2018, 1, 5), nonDef1Goal.getDueDateObject());
        assertEquals("non-default goal done", false, nonDef1Goal.isDone());
        
        LocalDate currentDate = LocalDate.now();
        // isPastDue checking
        defaultGoal.setDueDateObject(currentDate.minusYears(12));
        assertEquals("year is before", true, defaultGoal.isPastDue());
        defaultGoal.setDueDateObject(currentDate.minusMonths(1));
        assertEquals("year is same, month is before", true, defaultGoal.isPastDue());
        defaultGoal.setDueDateObject(currentDate.minusDays(2));
        assertEquals("year and month are the same, day is before", true, defaultGoal.isPastDue());

        assertEquals("year is after", false, nonDef1Goal.isPastDue());
        nonDef1Goal.setDueDateObject(currentDate.plusMonths(1));
        assertEquals("year is same, month is after", false, nonDef1Goal.isPastDue());
        nonDef1Goal.setDueDateObject(currentDate.plusDays(2));
        assertEquals("year and month are the same, day is after", false, nonDef1Goal.isPastDue());

        // testing compareTo
        assertTrue("should be positive (greater)",0 < nonDef1Goal.compareTo(defaultGoal));
        assertTrue("should be less",0 > defaultGoal.compareTo(nonDef1Goal));

        //takes the date from one, and sets the other's date to that
        String holdDueDate = nonDef1Goal.getDueDate();
        defaultGoal.setDueDate(holdDueDate);
        //then makes sure they're the same
        assertEquals("string date getters and setters", nonDef1Goal.getDueDateObject(), defaultGoal.getDueDateObject());

        //takes the date from one, and sets the other's date to that
        String holdStartDate = nonDef1Goal.getStartDate();
        defaultGoal.setStartDate(holdStartDate);
        //then makes sure they're the same
        assertEquals("string date getters and setters", nonDef1Goal.getStartDateObject(), defaultGoal.getStartDateObject());

        nonDef1Goal.setId("idOfObject");
        defaultGoal.setId("idOfObject2");
        //they have different id's
        assertFalse("equals should return false", nonDef1Goal.equals(defaultGoal));
        assertFalse("different id should return different hash", nonDef1Goal.hashCode() == defaultGoal.hashCode());

        defaultGoal.setId("idOfObject");
        //they now have the same id
        assertTrue("equals should return true", nonDef1Goal.equals(defaultGoal));
        assertTrue("same id should return the same hash", nonDef1Goal.hashCode() == defaultGoal.hashCode());
    }
}