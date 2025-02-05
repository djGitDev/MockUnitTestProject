package ca.uqam.mgl7230.tp1;


import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.plane.PlaneType;
import ca.uqam.mgl7230.tp1.service.prompt.FlightPromptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {

    private Application app;


    @Test
    void testStaticMethodRunAppCall() throws IOException {
        try (
                MockedConstruction<FlightPromptService> mockFlightPromptService = mockConstruction(FlightPromptService.class,
                        (mock, context) -> when(mock.getFlightInformation(any(Scanner.class)))
                                .thenReturn(new FlightInformation("UQAM001", 45.508888, -73.561668, -23.533773, -46.625290, PlaneType.BOEING))
                );

                MockedStatic<Runner> mockedRunner = mockStatic(Runner.class)
        ) {
            app.main(new String[]{});
            mockedRunner.verify(() -> Runner.runApp(any(), any(), any(), any()));
        }
    }
}

