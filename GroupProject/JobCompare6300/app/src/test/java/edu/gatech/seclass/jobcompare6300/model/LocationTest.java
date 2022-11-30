package edu.gatech.seclass.jobcompare6300.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocationTest {

    public Location location;
    @Before
    public void setUp() throws Exception {
            location = new Location("Chicago", State.IL, 200);
            location.setId(501);
    }

    @After
    public void tearDown(){
        location = null;
    }


    @Test
    public void test_getCity() {
        assertEquals("Chicago", location.getCity());
    }

    @Test
    public void test_getState() {
        assertEquals(State.IL, location.getState());
    }

    @Test
    public void test_getLocId() {
        assertEquals(501, location.getId());
    }

    @Test
    public void test_getCostOfLiving() {
        assertEquals(200, location.getCostOfLiving());
    }

    @Test
    public void test_setState() {
        location.setState(State.CO);
        assertEquals(State.CO, location.getState());
    }

    @Test
    public void test_setCity() {
        location.setCity("Schaumberg");
        assertEquals("Schaumberg", location.getCity());
    }

    @Test
    public void test_setCOL() {
        location.setCostOfLiving(50000);
        assertEquals(50000, location.getCostOfLiving());
    }


}