package ca.uqam.mgl7230.tp1.service.prompt;

import ca.uqam.mgl7230.tp1.exception.PassengerTypeNotFoundException;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerKeyConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Scanner;

import static ca.uqam.mgl7230.tp1.model.passenger.PassengerClass.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PassengerPromptServiceTest {

    private static final String PASSPORT_NUMBER = "123456";
    private static final String NAME = "John";
    private static final String AGE = "40";
    private static final String FIRST_TYPE = "first";
    private static final String BUSINESS_TYPE = "business";
    private static final String ECONOMY_TYPE = "economy";
    private static final String INVALID_TYPE = "invalid";



    private static Map<PassengerKeyConstants, Object> expectedFirstClassPassagerData =
            Map.of
            (   PassengerKeyConstants.PASSENGER_PASSPORT, PASSPORT_NUMBER,
                PassengerKeyConstants.PASSENGER_NAME, NAME,
                PassengerKeyConstants.PASSENGER_AGE, Integer.parseInt(AGE),
                PassengerKeyConstants.PASSENGER_CLASS, FIRST_CLASS
            );
    private static Map<PassengerKeyConstants, Object> expectedBusinessClassPassagerData =
            Map.of
            (   PassengerKeyConstants.PASSENGER_PASSPORT, PASSPORT_NUMBER,
                PassengerKeyConstants.PASSENGER_NAME, NAME,
                PassengerKeyConstants.PASSENGER_AGE, Integer.parseInt(AGE),
                PassengerKeyConstants.PASSENGER_CLASS, BUSINESS_CLASS
            );
    private static Map<PassengerKeyConstants, Object> expectedEconomicClassPassagerData =
            Map.of
            (   PassengerKeyConstants.PASSENGER_PASSPORT, PASSPORT_NUMBER,
                PassengerKeyConstants.PASSENGER_NAME, NAME,
                PassengerKeyConstants.PASSENGER_AGE, Integer.parseInt(AGE),
                PassengerKeyConstants.PASSENGER_CLASS, ECONOMY_CLASS
            );

    @InjectMocks
    private PassengerPromptService passengerPromptService;
    @Mock
    private Scanner scanner;

    @Test
    void getFirstClassPassengerData() {
        // Given
        given(scanner.nextLine()).willReturn(PASSPORT_NUMBER,NAME,AGE,FIRST_TYPE);
        // When
        Map<PassengerKeyConstants, Object> actualpassengerData= passengerPromptService.getPassengerData(scanner);
        // Then
        verify(scanner, times(4)).nextLine();
        assertThat(actualpassengerData).isEqualTo(expectedFirstClassPassagerData);
    }

    @Test
    void getBusinessClassPassengerData() {
        // Given
        given(scanner.nextLine()).willReturn(PASSPORT_NUMBER,NAME,AGE,BUSINESS_TYPE);
        // When
        Map<PassengerKeyConstants, Object> actualpassengerData= passengerPromptService.getPassengerData(scanner);
        // Then
        verify(scanner, times(4)).nextLine();
        assertThat(actualpassengerData).isEqualTo(expectedBusinessClassPassagerData);
    }

    @Test
    void getEconomicClassPassengerData() {
        // Given
        given(scanner.nextLine()).willReturn(PASSPORT_NUMBER,NAME,AGE,ECONOMY_TYPE);
        // When
        Map<PassengerKeyConstants, Object> actualpassengerData= passengerPromptService.getPassengerData(scanner);
        // Then
        verify(scanner, times(4)).nextLine();
        assertThat(actualpassengerData).isEqualTo(expectedEconomicClassPassagerData);
    }

    @Test
    void getExceptionUnknownClassPassengerData() {
        // Given
        given(scanner.nextLine()).willReturn(PASSPORT_NUMBER,NAME,AGE,INVALID_TYPE);
        // When & Then
        assertThrows(PassengerTypeNotFoundException.class, () -> {
            passengerPromptService.getPassengerData(scanner);
        });
    }



}