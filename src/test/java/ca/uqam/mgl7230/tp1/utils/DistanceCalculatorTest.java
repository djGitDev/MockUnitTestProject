package ca.uqam.mgl7230.tp1.utils;

import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DistanceCalculatorTest {

    private static final double MONTREAL_LAT = 45.5017;
    private static final double MONTREAL_LON = -73.5673;
    private static final double NEW_YORK_LAT = 40.7128;
    private static final double NEW_YORK_LON = -74.0060;
    private static final double SYDNEY_LAT = -33.8688;
    private static final double SYDNEY_LON = 151.2093;


    @InjectMocks
    private DistanceCalculator distanceCalculator;

    @Mock
    FlightInformation flightInformation;


    @BeforeEach
    void setUp() {
        //given
        given(flightInformation.getLatSource()).willReturn(MONTREAL_LAT);
        given(flightInformation.getLonSource()).willReturn(MONTREAL_LON);
    }

    @Test
    void calculateDistanceBetweenSamePointTest() {
        //given

        given(flightInformation.getLatDestination()).willReturn(MONTREAL_LAT);
        given(flightInformation.getLonDestination()).willReturn(MONTREAL_LON);
        //when
        int actualDistance = distanceCalculator.calculate(flightInformation);
        //then
        assertThat(actualDistance).isZero();
    }

    @Test
    void calculateShortDistanceTest() {

        //given
        given(flightInformation.getLatDestination()).willReturn(NEW_YORK_LAT);
        given(flightInformation.getLonDestination()).willReturn(NEW_YORK_LON);
        //when
        int actualDistance = distanceCalculator.calculate(flightInformation);
        //then
        assertThat(actualDistance).isCloseTo(540, within(10));
    }

    @Test
    void calculateLongDistanceTest() {
        //given
        given(flightInformation.getLatDestination()).willReturn(SYDNEY_LAT);
        given(flightInformation.getLonDestination()).willReturn(SYDNEY_LON);
        //when
        int actualDistance = distanceCalculator.calculate(flightInformation);
        //then
        assertThat(actualDistance).isCloseTo(16000, within(300));
    }
}