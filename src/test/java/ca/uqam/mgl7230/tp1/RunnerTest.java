package ca.uqam.mgl7230.tp1;

import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp1.adapter.persist.SavePassengerInFlight;
import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.flight.FlightStatus;
import ca.uqam.mgl7230.tp1.model.passenger.Passenger;
import ca.uqam.mgl7230.tp1.service.BookingService;
import ca.uqam.mgl7230.tp1.service.FlightPassengerService;
import ca.uqam.mgl7230.tp1.service.PassengerService;
import ca.uqam.mgl7230.tp1.service.prompt.PassengerPromptService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RunnerTest {

    private static final String PASSPORT_NUMBER1 = "123456";
    private static final String PASSPORT_NUMBER2 = "465456";
    private static final String VALID_FLIGHT_NUMBER = "UQAM001";
    private static final String CONTINUE = "oui";
    private static final String EXIT = "no";

    Runner app;

    @Mock
    Initializer.Initialize initializer;
    @Mock
    PassengerService passengerService;
    @Mock
    PassengerPromptService passengerPromptService;
    @Mock
    Scanner scanner;
    @Mock
    FlightCatalog flightCatalog;
    @Mock
    FlightInformation flightInformation;
    @Mock
    BookingService bookingService;
    @Mock
    Passenger passenger;
    @Mock
    FileWriter file;
    @Mock
    SavePassengerInFlight savePassengerInFlight;
    @Mock
    FlightPassengerService flightPassengerService;

    @BeforeEach
    void setUp() {
        given(initializer.scanner()).willReturn(scanner);
        given(initializer.file()).willReturn(file);
        given(passengerService.createPassenger(any(),any())).willReturn(passenger);
        given(initializer.flightCatalog()).willReturn(flightCatalog);
        given(flightCatalog.getFlightInformation(VALID_FLIGHT_NUMBER)).willReturn(flightInformation);
        given(initializer.savePassengerInFlight()).willReturn(savePassengerInFlight);
        given(initializer.flightPassengerService()).willReturn(flightPassengerService);
        given(initializer.flightNumber()).willReturn(VALID_FLIGHT_NUMBER);
    }

    @AfterEach
    void tearDown() throws IOException {
        verify(scanner).close();
        verify(file).close();
    }



    @Test
    void runAppCloseRessourcesAfterAddingOnePassengerAndExit() throws IOException {
        // Given
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(10);
        given(passenger.getPassport()).willReturn(PASSPORT_NUMBER1);
        given(scanner.nextLine()).willReturn(EXIT);
        // When
        Runner.runApp(initializer, passengerService, passengerPromptService,bookingService);
        //Then
        verify(savePassengerInFlight).save(eq(file),any(),anyString());

    }

    @Test
    void runAppCloseRessourcesAfterAddingTwoPassengerAndExit() throws IOException {
        // Given
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(10);
        given(flightInformation.getFlightStatus()).willReturn(FlightStatus.OPEN);
        given(passenger.getPassport()).willReturn(PASSPORT_NUMBER1,PASSPORT_NUMBER2);
        given(scanner.nextLine()).willReturn(CONTINUE,EXIT);
        // When
        Runner.runApp(initializer, passengerService, passengerPromptService,bookingService);
        //Then
        verify(bookingService, times(2)).book(any(),any());
        verify(savePassengerInFlight, times(2)).save(eq(file),any(),anyString());

    }

    @Test
    void samePassengerBookTwiceInSameFlight() throws IOException {
        // Given
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(10);
        given(flightInformation.getFlightStatus()).willReturn(FlightStatus.OPEN);
        given(passenger.getPassport()).willReturn(PASSPORT_NUMBER1);
        given(scanner.nextLine()).willReturn(CONTINUE,EXIT);
        // When
        Runner.runApp(initializer, passengerService, passengerPromptService,bookingService);
        //Then
        verify(bookingService).book(any(),any());
        verify(savePassengerInFlight).save(eq(file),any(),anyString());

    }

    @Test
    void exitAppWhenFlightIsFull() throws IOException {
        // Given
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(0);
        given(flightInformation.getFlightStatus()).willReturn(FlightStatus.FULL);
        given(flightPassengerService.numberOfTotalSeatsAvailable()).willReturn(0);
        given(passenger.getPassport()).willReturn(PASSPORT_NUMBER1,PASSPORT_NUMBER2);
        given(scanner.nextLine()).willReturn(CONTINUE);
        // When
        Runner.runApp(initializer, passengerService, passengerPromptService,bookingService);
        //Then
        verify(flightInformation).setFlightStatus(FlightStatus.FULL);
        verify(bookingService).book(any(),any());
        verify(savePassengerInFlight).save(eq(file),any(),anyString());
    }

}




