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
        assertEquals("default sale id", defaultSale.getId(), "");
        assertEquals("default sale service", defaultSale.getService().getTitle(), "");
        assertEquals("default sale price", defaultSale.getPrice(), 0.00, 0.0);
        assertEquals("default sale date", defaultSale.getDateObject(), LocalDate.now());
        assertEquals("default sale customer id", defaultSale.getCustomerId(), "");

        // first non-default constructor
        Service service1 = new Service();
        service1.setTitle("service1");
        Double price1 = 3.00;
        LocalDate date1 = new LocalDate(2018, 3, 11);
        String customerID1 = "customer id 1";

        Sale nonDef1Sale = new Sale(service1, price1, date1, customerID1);
        assertEquals("non-default sale id", nonDef1Sale.getId(), "");
        assertEquals("non-default sale service", nonDef1Sale.getService().getTitle(), "service1");
        assertEquals("non-default sale price", nonDef1Sale.getPrice(), 3.00, 0.0);
        assertEquals("non-default sale date", nonDef1Sale.getDateObject(), new LocalDate(2018, 3, 11));
        assertEquals("non-default sale customer id", nonDef1Sale.getCustomerId(), "customer id 1");

        // second non-default constructor
        Service service2 = new Service();
        service2.setTitle("service2");
        Double price2 = 5.50;
        LocalDate date2 = new LocalDate(2028, 3, 22);

        Sale nonDef2Sale = new Sale(service2, price2, date2);
        assertEquals("non-default sale id", nonDef2Sale.getId(), "");
        assertEquals("non-default sale service", nonDef2Sale.getService().getTitle(), "service2");
        assertEquals("non-default sale price", nonDef2Sale.getPrice(), 5.50, 0.0);
        assertEquals("non-default sale date", nonDef2Sale.getDateObject(), new LocalDate(2028, 3, 22));
        assertEquals("non-default sale customer id", nonDef2Sale.getCustomerId(), "");

        // testing compareTo
        assertEquals("should be greater", 1, nonDef2Sale.compareTo(nonDef1Sale));
        assertEquals("should be less", -1, nonDef1Sale.compareTo(nonDef2Sale));

        //testing getReport (financial interface)
        assertEquals("get report first sale", nonDef1Sale.getReport(), 3.00, 0.0);
        assertEquals("get report second sale", nonDef2Sale.getReport(), 5.50, 0.0);
    }
}
