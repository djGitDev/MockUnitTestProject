package ca.uqam.mgl7230.tp1.service.prompt;

import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FlightPromptServiceTest {

    private static final String VALID_NUMBER = "valid";
    private static final String INVALID_NUMBER = "invalid";


    @InjectMocks
    private FlightPromptService flightPromptService;
    @Mock
    private FlightCatalog flightCatalog;
    @Mock
    private FlightInformation flightInformation;
    @Mock
    private Scanner scanner;

    @Test
    void getFlightInformationWithValidFlightNumber() {
        //given
        given(scanner.nextLine()).willReturn(VALID_NUMBER);
        given(flightCatalog.getFlightInformation(VALID_NUMBER)).willReturn(flightInformation);
        //when
        FlightInformation actualResult = flightPromptService.getFlightInformation(scanner);
        //then
        verify(flightCatalog).getFlightInformation(VALID_NUMBER);  // Vérifie l'appel
        assertThat(actualResult).isNotNull();
    }

    @Test
    void getFlightInformationWithInvalidFlightNumber() {
        //given
        given(scanner.nextLine()).willReturn(INVALID_NUMBER);
        given(flightCatalog.getFlightInformation(INVALID_NUMBER)).willReturn(null);
        //when
        FlightInformation actualResult = flightPromptService.getFlightInformation(scanner);
        //then
        verify(flightCatalog).getFlightInformation(INVALID_NUMBER);  // Vérifie l'appel
        assertThat(actualResult).isNull();
    }
}