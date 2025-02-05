package ca.uqam.mgl7230.tp1;

import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.plane.PlaneType;
import ca.uqam.mgl7230.tp1.service.prompt.FlightPromptService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class InitializerTest {

    @Test
    void testInitializeSuccess() throws IOException {
        try (MockedConstruction<FlightPromptService> mockFlightPromptService = Mockito.mockConstruction(FlightPromptService.class,
                (mock, context) -> when(mock.getFlightInformation(any(Scanner.class)))
                        .thenReturn(new FlightInformation("UQAM001", 45.508888, -73.561668, -23.533773, -46.625290, PlaneType.BOEING))
        )) {
            Initializer.Initialize actualResult = Initializer.initialize("passengerData.csv");
            assertNotNull(actualResult);
        }
    }
    @Test
    void testInitializeFail() {
        try (MockedConstruction<FlightPromptService> mockFlightPromptService = Mockito.mockConstruction(FlightPromptService.class,
                (mock, context) -> when(mock.getFlightInformation(any(Scanner.class)))
                        .thenReturn(new FlightInformation("UQAM001", 45.508888, -73.561668, -23.533773, -46.625290, PlaneType.BOEING))
        )) {
            assertThrows(IOException.class, () -> {
                 Initializer.initialize("C:/nonexistent_directory/passengerData.csv");
            });
        }
    }
}