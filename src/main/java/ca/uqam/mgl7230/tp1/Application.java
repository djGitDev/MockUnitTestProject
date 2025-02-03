package ca.uqam.mgl7230.tp1;

import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalogImpl;
import ca.uqam.mgl7230.tp1.adapter.persist.SavePassengerInFlight;
import ca.uqam.mgl7230.tp1.adapter.plane.PlaneCatalog;
import ca.uqam.mgl7230.tp1.adapter.plane.PlaneCatalogImpl;
import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.passenger.Passenger;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerKeyConstants;
import ca.uqam.mgl7230.tp1.service.BookingService;
import ca.uqam.mgl7230.tp1.service.FlightPassengerService;
import ca.uqam.mgl7230.tp1.service.PassengerService;
import ca.uqam.mgl7230.tp1.service.prompt.FlightPromptService;
import ca.uqam.mgl7230.tp1.service.prompt.PassengerPromptService;
import ca.uqam.mgl7230.tp1.utils.DistanceCalculator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws IOException {
        logger.info("Service started...");
        Initialize result = initialize();
        boolean shouldContinue = true;
        while (shouldContinue) {

            PassengerService passengerService = new PassengerService(result.distanceCalculator());
            Passenger newPassenger = createPassenger(result,passengerService);
            bookPassenger(result,newPassenger,passengerService);
            saveResevation(result,newPassenger);
            int seatsAvailable = result.flightPassengerService().numberOfTotalSeatsAvailable();

            if (logger.isLoggable(Level.INFO)) {
                logger.info("Seats available: " + seatsAvailable);
            }
            logger.info("Continue adding passengers to this flight? yes or no");
            String continueChoice = result.scanner().nextLine();
            if ("no".equalsIgnoreCase(continueChoice)) {
                shouldContinue = false;
                result.scanner().close();
                result.file().close();
            }
        }
    }

    private static Passenger createPassenger(Initialize result, PassengerService passengerService) {
        PassengerPromptService passengerPromptService = new PassengerPromptService();
        Map<PassengerKeyConstants, Object> passengerData = passengerPromptService.getPassengerData(result.scanner());
        FlightInformation flightInformation = result.flightCatalog().getFlightInformation(result.flightNumber());
        return passengerService.createPassenger(flightInformation, passengerData);
    }

    private static void bookPassenger(Initialize result, Passenger passenger,PassengerService passengerService) {
        BookingService bookingService = new BookingService(result.flightPassengerService(), passengerService);
        FlightInformation flightInformation = result.flightCatalog().getFlightInformation(result.flightNumber());
        bookingService.book(passenger, flightInformation);
    }

    private static void saveResevation(Initialize result, Passenger passenger) throws IOException {
        result.savePassengerInFlight().save(result.file(), passenger, result.flightNumber());
    }



    private static Initialize initialize() throws IOException {
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        Scanner scanner = new Scanner(System.in);
        PlaneCatalog planeCatalog = new PlaneCatalogImpl();
        FlightCatalog flightCatalog = new FlightCatalogImpl();
        SavePassengerInFlight savePassengerInFlight = new SavePassengerInFlight();
        FlightPromptService flightPromptService = new FlightPromptService(flightCatalog);
        String flightNumber = flightPromptService.getFlightInformation(scanner).getFlightNumber();
        FlightPassengerService flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog, flightNumber);

        try (FileWriter file = new FileWriter("passengerData.csv")) {
            file.flush();
            return new Initialize(distanceCalculator, scanner, flightCatalog, file, savePassengerInFlight, flightNumber, flightPassengerService);
        } catch (IOException e) {
            String errorMessage = "Failed to create file 'passengerData.csv' during initialization. Please check file permissions and disk space.";
            throw new IOException(errorMessage, e);
        }
    }

    private record Initialize(DistanceCalculator distanceCalculator, Scanner scanner, FlightCatalog flightCatalog, FileWriter file, SavePassengerInFlight savePassengerInFlight, String flightNumber, FlightPassengerService flightPassengerService) {
    }
}
