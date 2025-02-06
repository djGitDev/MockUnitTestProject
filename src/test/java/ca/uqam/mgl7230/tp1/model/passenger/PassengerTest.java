package ca.uqam.mgl7230.tp1.model.passenger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.uqam.mgl7230.tp1.model.passenger.PassengerClass.*;
import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    private Passenger passenger1;
    private Passenger passenger2;
    private Passenger passenger3;
    private Passenger passenger4;

    @BeforeEach
    void setUp() {
        passenger1 = new BusinessClassPassenger("12345", "Alice", 25, 5000);
        passenger2 = new FirstClassPassenger("12345", "Alice", 25, 5000);
        passenger3 = new EconomyClassPassenger("67890", "Bob", 30, 10000);
        passenger4 = passenger1;
    }

    @Test
    void testEquals() {
        assertEquals(passenger1, passenger1);
        assertEquals(passenger1, passenger2);
        assertNotEquals(passenger1, passenger3);
        assertNotEquals(null,passenger1);
        assertEquals(passenger1, passenger4);
        assertNotEquals("string",passenger1);
    }

    @Test
    void testHashCode() {
        assertEquals(passenger1.hashCode(), passenger2.hashCode());
        assertNotEquals(passenger1.hashCode(), passenger3.hashCode());
    }

    @Test
    void getType() {
        assertEquals(BUSINESS_CLASS, passenger1.getType());
        assertEquals(FIRST_CLASS, passenger2.getType());
        assertEquals(ECONOMY_CLASS, passenger3.getType());
    }

    @Test
    void getPassport() {
        assertEquals("12345", passenger1.getPassport());
        assertEquals("67890", passenger3.getPassport());
    }

    @Test
    void getName() {
        assertEquals("Alice", passenger1.getName());
        assertEquals("Bob", passenger3.getName());
    }

    @Test
    void getAge() {
        assertEquals(25, passenger1.getAge());
        assertEquals(30, passenger3.getAge());
    }

    @Test
    void getMillagePoints() {
        assertEquals(5000, passenger1.getMillagePoints());
        assertEquals(10000, passenger3.getMillagePoints());
    }
}