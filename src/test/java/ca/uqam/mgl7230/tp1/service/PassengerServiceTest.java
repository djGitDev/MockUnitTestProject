package ca.uqam.mgl7230.tp1.service;

import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.passenger.*;
import ca.uqam.mgl7230.tp1.utils.DistanceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static ca.uqam.mgl7230.tp1.model.passenger.PassengerClass.*;
import static ca.uqam.mgl7230.tp1.model.passenger.PassengerKeyConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {


    private static final String PASSENGER_PASSPORT_EXEMPLE = "15435546";
    private static final String PASSENGER_NAME_EXEMPLE = "Martin Claire";
    private static final int PASSENGER_AGE_EXEMPLE = 48;

    private final Passenger expectedPassengerFirstClass = new FirstClassPassenger(
            PASSENGER_PASSPORT_EXEMPLE, PASSENGER_NAME_EXEMPLE, PASSENGER_AGE_EXEMPLE, 530);
    private final Passenger expectedPassengerBusinessClass = new BusinessClassPassenger(
            PASSENGER_PASSPORT_EXEMPLE, PASSENGER_NAME_EXEMPLE, PASSENGER_AGE_EXEMPLE, 530);
    private final Passenger expectedPassengerEconomyClass = new EconomyClassPassenger(
            PASSENGER_PASSPORT_EXEMPLE, PASSENGER_NAME_EXEMPLE, PASSENGER_AGE_EXEMPLE, 530);

    @InjectMocks
   private PassengerService passengerService;

   @Mock
   private FlightInformation flightInformation;
   @Mock
   private DistanceCalculator distanceCalculator;
   @Mock
   private Map<PassengerKeyConstants, Object> passengerData;

    @BeforeEach
   void setUp() {
       given(passengerData.get(PASSENGER_PASSPORT)).willReturn(PASSENGER_PASSPORT_EXEMPLE);
       given(passengerData.get(PASSENGER_NAME)).willReturn(PASSENGER_NAME_EXEMPLE);
       given(passengerData.get(PASSENGER_AGE)).willReturn(PASSENGER_AGE_EXEMPLE);
   }

    @Test
    void createFirstClassPassenger() {
        //given
        given(passengerData.get(PASSENGER_CLASS)).willReturn(FIRST_CLASS);
        given(distanceCalculator.calculate(flightInformation)).willReturn(530);
        //when
        Passenger actualPassenger = passengerService.createPassenger(flightInformation, passengerData);
        //then
        assertThat(actualPassenger).isEqualTo(expectedPassengerFirstClass);
    }

    @Test
    void createBusinessClassPassenger() {
        //given
        given(passengerData.get(PASSENGER_CLASS)).willReturn(BUSINESS_CLASS);
        given(distanceCalculator.calculate(flightInformation)).willReturn(530);
        //when
        Passenger actualPassenger = passengerService.createPassenger(flightInformation, passengerData);
        //then
        assertThat(actualPassenger).isEqualTo(expectedPassengerBusinessClass);
    }

    @Test
    void createEconomyClassPassenger() {
        //given
        given(passengerData.get(PASSENGER_CLASS)).willReturn(ECONOMY_CLASS);
        given(distanceCalculator.calculate(flightInformation)).willReturn(530);
        //when
        Passenger actualPassenger = passengerService.createPassenger(flightInformation, passengerData);
        //then
        assertThat(actualPassenger).isEqualTo(expectedPassengerEconomyClass);
    }

    @Test
    void notCreatePassengerWithNotAssignedClass() {
        //given
        given(passengerData.get(PASSENGER_CLASS)).willReturn(null);
        //when
        Passenger actualPassenger = passengerService.createPassenger(flightInformation, passengerData);
        //then
        assertThat(actualPassenger).isNull();
        verify(distanceCalculator, times(0)).calculate(flightInformation);
    }
}