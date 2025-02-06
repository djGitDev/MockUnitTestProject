package ca.uqam.mgl7230.tp1;

import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.flight.FlightStatus;
import ca.uqam.mgl7230.tp1.model.passenger.Passenger;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerKeyConstants;
import ca.uqam.mgl7230.tp1.service.BookingService;
import ca.uqam.mgl7230.tp1.service.PassengerService;
import ca.uqam.mgl7230.tp1.service.prompt.PassengerPromptService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Runner {

    private static final Logger logger = Logger.getLogger(Runner.class.getName());
    private static final String EXIT = "no";
    private static final String ADD_PASSENGER = "Continue adding passengers to this flight? yes or no";
    private static final String SEATS = "Seats available: ";
    private static final String FLIGHT_FULL = "Flight is full ...";

    private Runner(){}

    public static void runApp(Initializer.Initialize initializer,
                              PassengerService passengerService,
                              PassengerPromptService passengerPromptService,
                              BookingService bookingService
    ) throws IOException {

        ArrayList<String> passengersInFlight = new ArrayList<>();
        boolean shouldContinue = true;
        while (shouldContinue) {

            Map<PassengerKeyConstants, Object> passengerData = passengerPromptService.getPassengerData(initializer.scanner());
            FlightInformation flightInformation = initializer.flightCatalog().getFlightInformation(initializer.flightNumber());
            Passenger newPassenger = passengerService.createPassenger(flightInformation, passengerData);
            String passportNumber = newPassenger.getPassport();

            if(!(passengersInFlight.contains(passportNumber))) {
                passengersInFlight.add(passportNumber);
                bookingService.book(newPassenger, flightInformation);
                initializer.savePassengerInFlight().save(initializer.file(), newPassenger, initializer.flightNumber());
            }

            int seatsAvailable = initializer.flightPassengerService().numberOfTotalSeatsAvailable();
            if(seatsAvailable == 0) {
                flightInformation.setFlightStatus(FlightStatus.FULL);
            }
            if (logger.isLoggable(Level.INFO)) {
                logger.info(SEATS + seatsAvailable);
            }
            if (flightInformation.getFlightStatus() == FlightStatus.FULL) {
                logger.info(FLIGHT_FULL);
                shouldContinue = false;
            } else {
                logger.info(ADD_PASSENGER);
                String continueChoice = initializer.scanner().nextLine();
                if (EXIT.equalsIgnoreCase(continueChoice)) {
                    shouldContinue = false;
                }
            }
        }
        closeResources(initializer);
    }

    private static void closeResources(Initializer.Initialize initializer) {
        initializer.scanner().close();
    }

}
