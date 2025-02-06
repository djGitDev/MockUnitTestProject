package ca.uqam.mgl7230.tp1.service;

import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalog;
import ca.uqam.mgl7230.tp1.adapter.plane.PlaneCatalog;
import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.passenger.Passenger;
import ca.uqam.mgl7230.tp1.model.plane.PlaneType;

import java.util.logging.Logger;


public class FlightPassengerService {

    private static final Logger logger = Logger.getLogger(FlightPassengerService.class.getName());
    private static final String PASSENGER_NOT_SPECIFIED = "Passenger Type not specified";


    private int totalAmountFirstClassSeats;
    private int totalAmountBusinessClassSeats;
    private int totalAmountEconomyClassSeats;

    public FlightPassengerService(PlaneCatalog planeCatalog, FlightCatalog flightCatalog, String flightNumber) {
        FlightInformation flightInformation = flightCatalog.getFlightInformation(flightNumber);
        PlaneType planeType = flightInformation.getPlaneType();
        this.totalAmountFirstClassSeats = planeCatalog.getNumberSeatsFirstClass(planeType);
        this.totalAmountBusinessClassSeats = planeCatalog.getNumberSeatsBusinessClass(planeType);
        this.totalAmountEconomyClassSeats = planeCatalog.getNumberSeatsEconomyClass(planeType);
    }

    public void addPassenger(Passenger passenger) {
        switch (passenger.getType()) {
            case FIRST_CLASS ->
                totalAmountFirstClassSeats = (totalAmountFirstClassSeats != 0)
                        ? totalAmountFirstClassSeats - 1 : totalAmountFirstClassSeats;
            case BUSINESS_CLASS ->
                totalAmountBusinessClassSeats = (totalAmountBusinessClassSeats != 0)
                        ? totalAmountBusinessClassSeats - 1 : totalAmountBusinessClassSeats;
            case ECONOMY_CLASS ->
                totalAmountEconomyClassSeats = (totalAmountEconomyClassSeats != 0)
                        ? totalAmountEconomyClassSeats - 1 : totalAmountEconomyClassSeats;
            default -> logger.warning(PASSENGER_NOT_SPECIFIED);
        }
    }

    public int numberOfFirstClassSeatsAvailable() {
        return totalAmountFirstClassSeats;
    }

    public int numberOfBusinessClassSeatsAvailable() {
        return totalAmountBusinessClassSeats;
    }

    public int numberOfEconomyClassSeatsAvailable() {
        return totalAmountEconomyClassSeats;
    }

    public int numberOfTotalSeatsAvailable() {
        return totalAmountFirstClassSeats + totalAmountBusinessClassSeats + totalAmountEconomyClassSeats;
    }

}
