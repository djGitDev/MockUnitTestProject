package ca.uqam.mgl7230.tp1.adapter.persist;

import ca.uqam.mgl7230.tp1.model.passenger.Passenger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileWriter;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SavePassengerInFlightTest {
    private static final String VALID_FLIGHT_NUMBER = "UQAM001";
    private static final String PASSPORT = "123456789";
    private static final String NAME = "John Doe";

    @InjectMocks
    private SavePassengerInFlight savePassengerInFlight;

    @Mock
    Passenger passenger;
    @Mock
    FileWriter fileWriter;

    @Test
    void testSavePassengerInFlight() throws IOException {
        // Given
        given(fileWriter.append(anyString())).willReturn(fileWriter);
        given(passenger.getName()).willReturn(NAME);
        given(passenger.getPassport()).willReturn(PASSPORT);
        // When
        savePassengerInFlight.save(fileWriter, passenger, VALID_FLIGHT_NUMBER);
        // Then
        verify(fileWriter, times(12)).append(anyString());
    }

}