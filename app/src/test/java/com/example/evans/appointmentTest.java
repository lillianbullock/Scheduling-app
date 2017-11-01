package com.example.evans;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Brooke Nelson on 10/31/2017.
 */

public class appointmentTest {

    @Test
    public void testGetters() throws Exception {
        Customer customer = new Customer();
        customer.setName("Brooke times 2");

        assertNotNull(customer);
        assertEquals("getters should return same thing", customer.getName(), "Brooke times 2");
    }

}
