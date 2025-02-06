package ca.uqam.mgl7230.tp1;

import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalogImpl;
import ca.uqam.mgl7230.tp1.adapter.persist.SavePassengerInFlight;
import ca.uqam.mgl7230.tp1.adapter.plane.PlaneCatalog;
import ca.uqam.mgl7230.tp1.adapter.plane.PlaneCatalogImpl;
import ca.uqam.mgl7230.tp1.service.FlightPassengerService;
import ca.uqam.mgl7230.tp1.service.prompt.FlightPromptService;
import ca.uqam.mgl7230.tp1.utils.DistanceCalculator;

import java.io.FileWriter;
import java.util.Scanner;

public class Initializer {



    public static Initialize initialize(FileWriter file) {
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        Scanner scanner = new Scanner(System.in);
        PlaneCatalog planeCatalog = new PlaneCatalogImpl();
        FlightCatalog flightCatalog = new FlightCatalogImpl();
        SavePassengerInFlight savePassengerInFlight = new SavePassengerInFlight();
        FlightPromptService flightPromptService = new FlightPromptService(flightCatalog);
        String flightNumber = flightPromptService.getFlightInformation(scanner).getFlightNumber();
        FlightPassengerService flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog, flightNumber);
        return new Initialize(distanceCalculator, scanner, flightCatalog, file, savePassengerInFlight, flightNumber, flightPassengerService);
    }

    record Initialize(DistanceCalculator distanceCalculator, Scanner scanner, FlightCatalog flightCatalog, FileWriter file, SavePassengerInFlight savePassengerInFlight, String flightNumber, FlightPassengerService flightPassengerService) {
    }
}
