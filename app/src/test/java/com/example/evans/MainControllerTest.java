package com.example.evans;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Brooke Nelson on 11/3/2017.
 */

public class MainControllerTest {

    MainController mainController = new MainController();


    @Before
    public void initInputs() {
        Goal goal = new Goal();
        goal.setTitle("goal one");
        
    }


    @Test
    public void test() throws Exception {

    }
}
