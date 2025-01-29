package ca.uqam.mgl7230.tp1.service;

import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.passenger.*;
import ca.uqam.mgl7230.tp1.model.passenger.Passenger;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerKeyConstants;

import java.util.HashMap;
import java.util.Map;

public class BookingService {

    private FlightPassengerService flightPassengerService;
    private PassengerService passengerService;

    public BookingService(FlightPassengerService flightPassengerService,
                          PassengerService passengerService) {
        this.flightPassengerService = flightPassengerService;
        this.passengerService = passengerService;
    }

    public void book(Passenger passenger, FlightInformation flightInformation) {
        Map<PassengerKeyConstants, Object> passengerDataMap = getPassengerKeyConstantsObjectMap(passenger);
        if (flightPassengerService.numberOfTotalSeatsAvailable() != 0) {
            if (PassengerClass.FIRST_CLASS.name().equalsIgnoreCase(passenger.getType().name())) {
                if (flightPassengerService.numberOfFirstClassSeatsAvailable() == 0) {
                    System.out.println("First Class is Full. Trying Business...");
                    passengerDataMap.put(PassengerKeyConstants.PASSENGER_CLASS, PassengerClass.BUSINESS_CLASS);
                    passenger = passengerService.createPassenger(flightInformation, passengerDataMap);
                }
            }
            if (PassengerClass.BUSINESS_CLASS.name().equalsIgnoreCase(passenger.getType().name())) {
                if (flightPassengerService.numberOfBusinessClassSeatsAvailable() == 0) {
                    System.out.println("Business Class is Full. Trying Economy...");
                    passengerDataMap.put(PassengerKeyConstants.PASSENGER_CLASS, PassengerClass.ECONOMY_CLASS);
                }
            }
            if (PassengerClass.ECONOMY_CLASS.name().equalsIgnoreCase(passenger.getType().name())) {
                if (flightPassengerService.numberOfEconomyClassSeatsAvailable() == 0) {
                    System.out.println("Economy is Full. Try another type...");
                    return;
                }
            }
            flightPassengerService.addPassenger(passenger);
            System.out.println("Passenger added successfully");
        } else {
            System.out.println("Flight is Full...");
        }
    }

    private Map<PassengerKeyConstants, Object> getPassengerKeyConstantsObjectMap(Passenger passenger) {
        Map<PassengerKeyConstants, Object> passengerDataMap = new HashMap<>();
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_PASSPORT, passenger.getPassport());
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_NAME, passenger.getName());
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_AGE, passenger.getAge());
        return passengerDataMap;
    }
}
