package com.example.evans;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 *
 */
public class MainControllerTest {

    MainController mainController = new MainController();


    @Before
    public void initInputs() {
        LocalDateTime date1 = LocalDateTime.of(2017, 11, 11, 11, 11);
        LocalDateTime date2 = LocalDateTime.of(2017, 12, 22, 22, 22);
        LocalDateTime date3 = LocalDateTime.of(2017, 10, 23, 23, 23);
        LocalDateTime date4 = LocalDateTime.of(2018, 1, 22, 22, 22);

        Goal goal1 = new Goal("GoalTitle1", "GoalDescription1", date2, date1, null);
        Goal goal2 = new Goal("GoalTitle2", "GoalDescription2", date3, date2, null);


    }


    @Test
    public void test() throws Exception {

    }
}
