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



    public static void runApp(Initializer.Initialize initializer,
                              PassengerService passengerService,
                              PassengerPromptService passengerPromptService,
                              BookingService bookingService
    ) throws IOException {

        ArrayList<String> PassengersInFlight = new ArrayList();
        boolean shouldContinue = true;
        while (shouldContinue) {

            Map<PassengerKeyConstants, Object> passengerData = passengerPromptService.getPassengerData(initializer.scanner());
            FlightInformation flightInformation = initializer.flightCatalog().getFlightInformation(initializer.flightNumber());
            Passenger newPassenger = passengerService.createPassenger(flightInformation, passengerData);
            String passportNumber = newPassenger.getPassport();

            if(!(PassengersInFlight.contains(passportNumber))) {
                PassengersInFlight.add(passportNumber);
                bookingService.book(newPassenger, flightInformation);
                initializer.savePassengerInFlight().save(initializer.file(), newPassenger, initializer.flightNumber());
            }

            int seatsAvailable = initializer.flightPassengerService().numberOfTotalSeatsAvailable();
            if(seatsAvailable == 0) {
                flightInformation.setFlightStatus(FlightStatus.FULL);
            }

            if (logger.isLoggable(Level.INFO)) {
                logger.info("Seats available: " + seatsAvailable);
            }
            logger.info("Continue adding passengers to this flight? yes or no");
            String continueChoice = initializer.scanner().nextLine();
            if (EXIT.equalsIgnoreCase(continueChoice) || flightInformation.getFlightStatus() == FlightStatus.FULL) {
                shouldContinue = false;
                initializer.scanner().close();
                initializer.file().close();
            }
        }
    }

}
