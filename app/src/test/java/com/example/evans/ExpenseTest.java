package com.example.evans;

import com.example.evans.data.Expense;

import org.joda.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the Expense class
 * Tests the default and 2 non-default constructors
 * Tests compareTo and getReport
 */
public class ExpenseTest {
    // default constructor
    @Test
    public void test() throws Exception {
        Expense defaultExpense = new Expense();
        assertEquals("default expense id", defaultExpense.getId(), "");
        assertEquals("default expense service", defaultExpense.getName(), "");
        assertEquals("default expense price", defaultExpense.getPrice(), 0.00, 0.0);
        assertEquals("default expense date", defaultExpense.getDateObject(), LocalDate.now());

        // first non-default constructor
        String name1 = "Expense 1";
        Double price1 = 5.00;
        LocalDate date1 = new LocalDate(2018, 3, 11);

        Expense nonDef1Expense = new Expense(name1, price1, date1);
        assertEquals("non-default expense id", "", nonDef1Expense.getId());
        assertEquals("non-default expense name", "Expense 1", nonDef1Expense.getName());
        assertEquals("non-default expense price", 5.00, nonDef1Expense.getPrice(), 0.0);
        assertEquals("non-default expense date", new LocalDate(2018, 3, 11), nonDef1Expense.getDateObject());

        // second non-default constructor
        String name2 = "Service 2";
        Double price2 = 6.70;
        LocalDate date2 = new LocalDate(4242, 5, 22);

        Expense nonDef2Expense = new Expense(name2, price2, date2);
        assertEquals("non-default 2 expense id", "", nonDef2Expense.getId());
        assertEquals("non-default 2 expense service", "Service 2", nonDef2Expense.getName());
        assertEquals("non-default 2 expense price", 6.70, nonDef2Expense.getPrice(), 0.0);
        assertEquals("non-default 2 expense date", new LocalDate(4242, 5, 22), nonDef2Expense.getDateObject());

        // testing compareTo
        assertTrue("should be greater",0 < nonDef2Expense.compareTo(nonDef1Expense));
        assertTrue("should be less",0 > nonDef1Expense.compareTo(nonDef2Expense));

        //testing getReport (financial interface)
        assertEquals("get report first expense", nonDef1Expense.getReport(), 5.00, 0.0);
        assertEquals("get report second expense", nonDef2Expense.getReport(), 6.70, 0.0);

        //takes the date from one, and sets the other's date to that
        String holdDate = nonDef1Expense.getDate();
        nonDef2Expense.setDate(holdDate);
        //then makes sure they're the same
        assertEquals("string date getters and setters", nonDef1Expense.getDateObject(), nonDef2Expense.getDateObject());

    }
}
