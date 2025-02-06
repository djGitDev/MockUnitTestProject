package ca.uqam.mgl7230.tp1;

import ca.uqam.mgl7230.tp1.service.BookingService;
import ca.uqam.mgl7230.tp1.service.PassengerService;
import ca.uqam.mgl7230.tp1.service.prompt.PassengerPromptService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class Application
{

    private static final Logger logger = Logger.getLogger(Application.class.getName());
    private static final String FILE_NAME = "passengerData.csv";
    private static final String FILE_ERROR = "Failed to create file 'passengerData.csv' during initialization. Please check file permissions and disk space.";
    private static final String START = "Service started...";
    private static final String SUCCES = "Init succes...";

    public static void main(String[] args) throws IOException {
        try (FileWriter file = new FileWriter(FILE_NAME)) {
            file.flush();
            logger.info(START);
            Initializer.Initialize initializer = Initializer.initialize(file);
            PassengerService passengerService = new PassengerService(initializer.distanceCalculator());
            PassengerPromptService passengerPromptService = new PassengerPromptService();
            BookingService bookingService = new BookingService(initializer.flightPassengerService(), passengerService);
            logger.info(SUCCES);
            Runner.runApp(initializer, passengerService, passengerPromptService, bookingService);
        } catch (IOException e) {
            throw new IOException(FILE_ERROR, e);
        }
    }

}
