package com.example.evans;

import com.example.evans.data.Goal;
import com.example.evans.data.TimePeriod;

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
        assertEquals("default goal repeat cycle", defaultGoal.getRepeatCycle(), null);
        assertEquals("default goal done", defaultGoal.isDone(), null);
        assertEquals("default goal repeat boolean", defaultGoal.isRepeat(), false);

        // first non-default constructor
        String title1 = "Some Goal we set";
        String description1 = "This goal we want to do";
        LocalDate dueDate1 = new LocalDate(2018, 1, 5);
        LocalDate startDate1 = new LocalDate(2017, 7, 11);
        TimePeriod repeatCycle1 = TimePeriod.Month;

        Goal nonDef1Goal = new Goal(title1, description1, dueDate1, startDate1, repeatCycle1);
        assertEquals("non-default goal id", nonDef1Goal.getId(), "");
        assertEquals("non-default goal title", nonDef1Goal.getTitle(), "Some Goal we set");
        assertEquals("non-default goal description", nonDef1Goal.getDescription(), "This goal we want to do");
        assertEquals("non-default goal start date", nonDef1Goal.getStartDateObject(), new LocalDate(2017, 7, 11));
        assertEquals("non-default goal due date", nonDef1Goal.getDueDateObject(), new LocalDate(2018, 1, 5));
        assertEquals("non-default goal repeat cycle", nonDef1Goal.getRepeatCycle(), TimePeriod.Month);
        assertEquals("non-default goal done", nonDef1Goal.isDone(), false);
        assertEquals("non-default goal repeat boolean", nonDef1Goal.isRepeat(), true);

        // non-default constructor that doesn't set the repeat cycle
        String title2 = "Some Goal";
        String description2 = "goal we want to do";
        LocalDate dueDate2 = new LocalDate(1908, 2, 5);
        LocalDate startDate2 = new LocalDate(1907, 7, 22);

        Goal nonDef2Goal = new Goal(title2, description2, dueDate2, startDate2);
        assertEquals("non-default goal id", nonDef2Goal.getId(), "");
        assertEquals("non-default goal title", nonDef2Goal.getTitle(), "Some Goal");
        assertEquals("non-default goal description", nonDef2Goal.getDescription(), "goal we want to do");
        assertEquals("non-default goal start date", nonDef2Goal.getStartDateObject(), new LocalDate(1907, 7, 22));
        assertEquals("non-default goal due date", nonDef2Goal.getDueDateObject(), new LocalDate(1908, 2, 5));
        assertEquals("non-default goal repeat cycle", nonDef2Goal.getRepeatCycle(), null);
        assertEquals("non-default goal done", nonDef2Goal.isDone(), false);
        assertEquals("non-default goal repeat boolean", nonDef2Goal.isRepeat(), false);

        LocalDate currentDate = LocalDate.now();
        // isPastDue checking
        assertEquals("year is before", nonDef2Goal.isPastDue(), true);
        nonDef2Goal.setDueDateObject(currentDate.minusMonths(1));
        assertEquals("year is same, month is before", nonDef2Goal.isPastDue(), true);
        nonDef2Goal.setDueDateObject(currentDate.minusDays(2));
        assertEquals("year and month are the same, day is before", nonDef2Goal.isPastDue(), true);

        assertEquals("year is after", nonDef1Goal.isPastDue(), false);
        nonDef1Goal.setDueDateObject(currentDate.plusMonths(1));
        assertEquals("year is same, month is after", nonDef1Goal.isPastDue(), false);
        nonDef1Goal.setDueDateObject(currentDate.plusDays(2));
        assertEquals("year and month are the same, day is after", nonDef1Goal.isPastDue(), false);

        // testing compareTo
        assertEquals("should be greater", 1, nonDef1Goal.compareTo(nonDef2Goal));
        assertEquals("should be less", -1, nonDef2Goal.compareTo(nonDef1Goal));
    }
}