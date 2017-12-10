package com.example.evans;

import com.example.evans.data.Sale;
import com.example.evans.data.Service;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Sale class
 * Tests the default and 2 non-default constructors
 * Tests compareTo and getReport
 */
public class SaleTest {
    // default constructor
    @Test
    public void test() throws Exception {
        Sale defaultSale = new Sale();
        assertEquals("default sale id", "", defaultSale.getId());
        assertEquals("default sale service", "", defaultSale.getService().getTitle());
        assertEquals("default sale price", 0.00, defaultSale.getPrice(), 0.0);
        assertEquals("default sale date", LocalDate.now(), defaultSale.getDateObject());
        assertEquals("default sale customer id", "", defaultSale.getCustomerId());

        // first non-default constructor
        Service service1 = new Service();
        service1.setTitle("service1");
        Double price1 = 3.00;
        LocalDate date1 = new LocalDate(2018, 3, 11);
        String customerID1 = "customer id 1";

        Sale nonDef1Sale = new Sale(service1, price1, date1, customerID1);
        assertEquals("non-default sale id", "", nonDef1Sale.getId());
        assertEquals("non-default sale service", "service1", nonDef1Sale.getService().getTitle());
        assertEquals("non-default sale price", 3.00, nonDef1Sale.getPrice(), 0.0);
        assertEquals("non-default sale date", new LocalDate(2018, 3, 11), nonDef1Sale.getDateObject());
        assertEquals("non-default sale customer id", "customer id 1", nonDef1Sale.getCustomerId());

        // second non-default constructor
        Service service2 = new Service();
        service2.setTitle("service2");
        Double price2 = 5.50;
        LocalDate date2 = new LocalDate(2028, 3, 22);

        Sale nonDef2Sale = new Sale(service2, price2, date2);
        assertEquals("non-default sale id", "", nonDef2Sale.getId());
        assertEquals("non-default sale service", "service2", nonDef2Sale.getService().getTitle());
        assertEquals("non-default sale price", 5.50, nonDef2Sale.getPrice(), 0.0);
        assertEquals("non-default sale date", new LocalDate(2028, 3, 22), nonDef2Sale.getDateObject());
        assertEquals("non-default sale customer id", "", nonDef2Sale.getCustomerId());

        // testing compareTo
        assertTrue("should return a positive number", 0 < nonDef2Sale.compareTo(nonDef1Sale));
        assertTrue("should return a negative number", 0 > nonDef1Sale.compareTo(nonDef2Sale));

        //testing getReport (financial interface)
        assertEquals("get report first sale", 3.00, nonDef1Sale.getReport(), 0.0);
        assertEquals("get report second sale", 5.50, nonDef2Sale.getReport(), 0.0);

        //takes the date from one, and sets the other's date to that
        String holdDate = nonDef1Sale.getDate();
        nonDef2Sale.setDate(holdDate);
        //then makes sure they're the same
        assertEquals("string date getters and setters", nonDef1Sale.getDateObject(), nonDef2Sale.getDateObject());

        nonDef1Sale.setId("idOfObject");
        nonDef2Sale.setId("idOfObject2");
        //they have different id's
        assertFalse("equals should return false", nonDef1Sale.equals(nonDef2Sale));
        assertFalse("different id should return different hash", nonDef1Sale.hashCode() == nonDef2Sale.hashCode());

        nonDef2Sale.setId("idOfObject");
        //they now have the same id
        assertTrue("equals should return true", nonDef1Sale.equals(nonDef2Sale));
        assertTrue("same id should return the same hash", nonDef1Sale.hashCode() == nonDef2Sale.hashCode());

    }
}
