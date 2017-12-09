package com.example.evans;


import com.example.evans.data.Service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Service class
 * Tests the default and non-default constructors
 * Tests compareTo
 */
public class ServiceTest {
    @Test
    public void test() throws Exception {
        Service defaultService = new Service();
        assertEquals("default Service id", "", defaultService.getId());
        assertEquals("default Service title", "", defaultService.getTitle());
        assertEquals("default service descrption", "", defaultService.getDescription());
        assertEquals("default Service price", 0.0, defaultService.getPrice(), 0.00);

        // first non-default constructor
        String title = "Service 1";
        String description = "description";
        Double price = 3.00;

        Service nonDefService = new Service(title, description, price);
        assertEquals("non-default service id", "", nonDefService.getId());
        assertEquals("non-default Service title", "Service 1", nonDefService.getTitle());
        assertEquals("non-default service description", "description", nonDefService.getDescription());
        assertEquals("non-default service price", 3.00, nonDefService.getPrice(), 0.0);

        // testing compareTo
        assertTrue("should return a negative number", 0 > defaultService.compareTo(nonDefService));
        assertTrue("should return a positive number", 0 < nonDefService.compareTo(defaultService));
    }
}

