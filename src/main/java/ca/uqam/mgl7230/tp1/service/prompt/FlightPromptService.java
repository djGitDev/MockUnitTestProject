package ca.uqam.mgl7230.tp1.service.prompt;

import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;

import java.util.Scanner;
import java.util.logging.Logger;

public class FlightPromptService {

    private static final Logger logger = Logger.getLogger(FlightPromptService.class.getName());
    private static final String TYPE_FLIGHT_NUMBER = "Enter flight number: ";
    private static final String NO_FLIGHT_MATCH = "No flight found with this code, try again...";

    private FlightCatalog flightCatalog;

    public FlightPromptService(FlightCatalog flightCatalog) {
        this.flightCatalog = flightCatalog;
    }

    public FlightInformation getFlightInformation(Scanner scanner) {
        logger.info(TYPE_FLIGHT_NUMBER);
        String flightNumber = scanner.nextLine();
        FlightInformation flight = flightCatalog.getFlightInformation(flightNumber);
        if (flight == null) {
            logger.warning(NO_FLIGHT_MATCH);
        }
        return flight;
    }
}
