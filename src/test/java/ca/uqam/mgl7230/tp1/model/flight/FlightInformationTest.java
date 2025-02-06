package ca.uqam.mgl7230.tp1.model.flight;

import ca.uqam.mgl7230.tp1.model.plane.PlaneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FlightInformationTest {

    private FlightInformation flightInformation;

    @BeforeEach
    void setUp() {
        flightInformation = new FlightInformation(
                "UQAM001",
                45.5017,
                -73.5673,
                48.8566,
                2.3522,
                PlaneType.BOEING
        );

    }

    @Test
    void getFlightNumber() {
        assertEquals("UQAM001", flightInformation.getFlightNumber());
    }

    @Test
    void getLatSource() {
        assertEquals(45.5017, flightInformation.getLatSource());
    }

    @Test
    void getLonSource() {
        assertEquals(-73.5673, flightInformation.getLonSource());
    }

    @Test
    void getLatDestination() {
        assertEquals(48.8566, flightInformation.getLatDestination());
    }

    @Test
    void getLonDestination() {
        assertEquals(2.3522, flightInformation.getLonDestination());
    }

    @Test
    void getPlaneType() {
        assertEquals(PlaneType.BOEING, flightInformation.getPlaneType());
    }

    @Test
    void getFlightStatus() {
        assertEquals(FlightStatus.OPEN, flightInformation.getFlightStatus());
    }

    @Test
    void setFlightStatus() {
        flightInformation.setFlightStatus(FlightStatus.FULL);
        assertEquals(FlightStatus.FULL, flightInformation.getFlightStatus());
    }
}
