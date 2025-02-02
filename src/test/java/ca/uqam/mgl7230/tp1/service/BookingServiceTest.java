package ca.uqam.mgl7230.tp1.service;

import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.passenger.Passenger;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerKeyConstants;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static ca.uqam.mgl7230.tp1.model.passenger.PassengerClass.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Spy
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private FlightPassengerService flightPassengerService;
    @Mock
    private PassengerService passengerService;
    @Mock
    private Passenger passenger;
    @Mock
    private FlightInformation flightInformation;
    @Mock
    Map<PassengerKeyConstants, Object> passengerDataMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void bookFlightFirstClassWithAvailabilities() {
        // Given
        given(passenger.getType()).willReturn(FIRST_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(9);
        // When
        bookingService.book(passenger, flightInformation);
        // Then
        verify(bookingService).handleFirstClassPassenger(anyMap(), eq(passenger), eq(flightInformation));
        verify(bookingService, times(0)).handleBusinessClassPassenger(anyMap(), eq(passenger), eq(flightInformation));
        verify(bookingService, times(0)).handleEconomicClassPassenger(passenger);

    }

    @Test
    void bookFlightBusinessClassWithAvailabilities() {
        // Given
        given(passenger.getType()).willReturn(BUSINESS_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(3);
        // When
        bookingService.book(passenger, flightInformation);
        // Then
        verify(bookingService, times(0)).handleFirstClassPassenger(anyMap(), eq(passenger), eq(flightInformation));
        verify(bookingService).handleBusinessClassPassenger(anyMap(), eq(passenger), eq(flightInformation));
        verify(bookingService, times(0)).handleEconomicClassPassenger(passenger);

    }

    @Test
    void bookFlightEconomicClassWithAvailabilities() {
        // Given
        given(passenger.getType()).willReturn(ECONOMY_CLASS);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(9);
        // When
        bookingService.book(passenger, flightInformation);
        // Then
        verify(bookingService, times(0)).handleFirstClassPassenger(anyMap(), eq(passenger), eq(flightInformation));
        verify(bookingService, times(0)).handleBusinessClassPassenger(anyMap(), eq(passenger), eq(flightInformation));
        verify(bookingService).handleEconomicClassPassenger(passenger);
    }

    @Test
    void bookFlightAnyClassWithoutAvailabilities() {
        // Given
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(0);
        // When
        bookingService.book(passenger, flightInformation);
        // Then
        verify(bookingService, times(0)).handleFirstClassPassenger(anyMap(), eq(passenger), eq(flightInformation));
        verify(bookingService, times(0)).handleBusinessClassPassenger(anyMap(), eq(passenger), eq(flightInformation));
        verify(bookingService, times(0)).handleEconomicClassPassenger(passenger);
    }



    @Test
    void handleFirstClassPassengerWithAvailabilities(){
        //Given
        given(flightPassengerService.numberOfFirstClassSeatsAvailable()).willReturn(2);
        //When
        bookingService.handleFirstClassPassenger(passengerDataMap, passenger, flightInformation);
        //Then
        verify(flightPassengerService).addPassenger(passenger);
        verify(bookingService, times(0)).changeToBusinessClass(passengerDataMap,flightInformation);
        verify(bookingService, times(0)).handleBusinessClassPassenger(passengerDataMap, passenger, flightInformation);

    }

    @Test
    void handleFirstClassPassengerWithoutAvailabilities(){
        //Given
        given(flightPassengerService.numberOfFirstClassSeatsAvailable()).willReturn(0);
        given(bookingService.changeToBusinessClass(passengerDataMap,flightInformation)).willReturn(passenger);
        //When
        bookingService.handleFirstClassPassenger(passengerDataMap, passenger, flightInformation);
        //Then
        verify(flightPassengerService, times(0)).addPassenger(passenger);
        verify(bookingService).changeToBusinessClass(passengerDataMap,flightInformation);
        verify(bookingService).handleBusinessClassPassenger(passengerDataMap, passenger, flightInformation);
    }

    @Test
    void handleBusinessClassPassengerWithAvailabilities(){
        //Given
        given(flightPassengerService.numberOfBusinessClassSeatsAvailable()).willReturn(8);
        //When
        bookingService.handleBusinessClassPassenger(passengerDataMap, passenger, flightInformation);
        //Then
        verify(flightPassengerService).addPassenger(passenger);
        verify(bookingService, times(0)).changeToEconomicClass(passengerDataMap,flightInformation);
        verify(bookingService, times(0)).handleEconomicClassPassenger(passenger);

    }

    @Test
    void handleBusinessClassPassengerWithoutAvailabilities(){
        //Given
        given(flightPassengerService.numberOfBusinessClassSeatsAvailable()).willReturn(0);
        given(bookingService.changeToEconomicClass(passengerDataMap,flightInformation)).willReturn(passenger);
        //When
        bookingService.handleBusinessClassPassenger(passengerDataMap, passenger, flightInformation);
        //Then
        verify(flightPassengerService, times(0)).addPassenger(passenger);
        verify(bookingService).changeToEconomicClass(passengerDataMap,flightInformation);
        verify(bookingService).handleEconomicClassPassenger( passenger);
    }

    @Test
    void handleEconomicClassPassengerWithAvailabilities(){
        //Given
        given(flightPassengerService.numberOfEconomyClassSeatsAvailable()).willReturn(8);
        //When
        bookingService.handleEconomicClassPassenger(passenger);
        //Then
        verify(flightPassengerService).addPassenger(passenger);
    }

    @Test
    void handleEconomicClassPassengerWithoutAvailabilities(){
        //Given
        given(flightPassengerService.numberOfEconomyClassSeatsAvailable()).willReturn(0);
        //When
        bookingService.handleEconomicClassPassenger(passenger);
        //Then
        verify(flightPassengerService, times(0)).addPassenger(passenger);
    }

    @Test
    void changeToBusinessClass() {
        //when
        bookingService.changeToBusinessClass(passengerDataMap,flightInformation);
        //then
        verify(passengerDataMap).put(PassengerKeyConstants.PASSENGER_CLASS, PassengerClass.BUSINESS_CLASS);
        verify(passengerService).createPassenger(flightInformation, passengerDataMap);
    }

    @Test
    void changeToEconomicClass() {
        //when
        bookingService.changeToEconomicClass(passengerDataMap,flightInformation);
        //then
        verify(passengerDataMap).put(PassengerKeyConstants.PASSENGER_CLASS, PassengerClass.ECONOMY_CLASS);
        verify(passengerService).createPassenger(flightInformation, passengerDataMap);
    }
}