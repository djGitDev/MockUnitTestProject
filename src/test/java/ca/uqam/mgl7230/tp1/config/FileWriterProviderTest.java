package ca.uqam.mgl7230.tp1.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class FileWriterProviderTest {

    private FileWriterProvider fileProvider;

    @BeforeEach
    void setUp() {
        fileProvider = new FileWriterProvider();
    }

    @Test
    void testInitialize_Success()  {
        FileWriter actualResult = fileProvider.createFile("passengerData.csv");
        assertNotNull(actualResult);
    }

    @Test
    void initWritter_Fail() {
        FileWriter actualResult =   fileProvider.createFile("C:/nonexistent_directory/passengerData.csv");
        assertNull(actualResult);
    }

}