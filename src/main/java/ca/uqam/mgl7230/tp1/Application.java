package ca.uqam.mgl7230.tp1;

import ca.uqam.mgl7230.tp1.service.BookingService;
import ca.uqam.mgl7230.tp1.service.PassengerService;
import ca.uqam.mgl7230.tp1.service.prompt.PassengerPromptService;

import java.io.IOException;
import java.util.logging.Logger;

public class Application
{

    private static final Logger logger = Logger.getLogger(Application.class.getName());
    private static final String FILE_NAME = "passengerData.csv";
    private static final String START = "Service started...";
    private static final String SUCCES = "Init succes...";

    public static void main(String[] args) throws IOException {

        logger.info(START);
        Initializer.Initialize initializer = Initializer.initialize(FILE_NAME);
        PassengerService passengerService = new PassengerService(initializer.distanceCalculator());
        PassengerPromptService passengerPromptService = new PassengerPromptService();
        BookingService bookingService = new BookingService(initializer.flightPassengerService(), passengerService);
        logger.info(SUCCES);
        Runner.runApp(initializer,passengerService,passengerPromptService,bookingService);
    }
}
