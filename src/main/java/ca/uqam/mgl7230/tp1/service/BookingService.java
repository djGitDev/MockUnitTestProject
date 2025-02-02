package ca.uqam.mgl7230.tp1.service;

import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.passenger.Passenger;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerKeyConstants;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

public class BookingService {

    private static final String SUCCESS_BOOKING = "Passenger added successfully";
    private static final String FULL_FLIGHT ="Flight is Full...";
    private static final String FULL_FIRST_CLASS = "First Class is Full. Trying Business...";
    private static final String FULL_BUSINESS_CLASS = "Business Class is Full. Trying Economy...";
    private static final String FULL_ECONOMIC_CLASS = "Economy is Full. Try another type...";
    private static final String UNKNOWN_CLASS ="Unknown passenger class...";

    private static final Logger logger = Logger.getLogger(BookingService.class.getName());


    private FlightPassengerService flightPassengerService;
    private PassengerService passengerService;


    public BookingService(FlightPassengerService flightPassengerService,
                          PassengerService passengerService) {
        this.flightPassengerService = flightPassengerService;
        this.passengerService = passengerService;
    }


    public void book(Passenger passenger, FlightInformation flightInformation) {
        Map<PassengerKeyConstants, Object> passengerDataMap = getPassengerKeyConstantsObjectMap(passenger);
        if (isSeatsAvailable()) {
            switch (passenger.getType()) {
                case FIRST_CLASS:
                    handleFirstClassPassenger(passengerDataMap, passenger, flightInformation);
                    break;
                case BUSINESS_CLASS:
                    handleBusinessClassPassenger(passengerDataMap, passenger, flightInformation);
                    break;
                case ECONOMY_CLASS:
                    handleEconomicClassPassenger(passenger);
                    break;
                default:
                    logger.warning(UNKNOWN_CLASS);
                    break;
            }
        } else {
            logger.warning(FULL_FLIGHT);
        }
    }

    void handleFirstClassPassenger(Map<PassengerKeyConstants, Object> passengerDataMap, Passenger passenger, FlightInformation flightInformation) {
        if (isFirstClassSeatsNotAvailable()) {
            logger.warning(FULL_FIRST_CLASS);
            passenger = changeToBusinessClass(passengerDataMap, flightInformation);
            handleBusinessClassPassenger(passengerDataMap,passenger,flightInformation);
        }else{
            flightPassengerService.addPassenger(passenger);
            logger.info(SUCCESS_BOOKING);
        }
    }

    void handleBusinessClassPassenger(Map<PassengerKeyConstants, Object> passengerDataMap, Passenger passenger, FlightInformation flightInformation) {
        if (isBusinessClassSeatsNotAvailable()) {
            logger.warning(FULL_BUSINESS_CLASS);
            passenger = changeToEconomicClass(passengerDataMap, flightInformation);
            handleEconomicClassPassenger(passenger);
        }else{
            flightPassengerService.addPassenger(passenger);
            logger.info(SUCCESS_BOOKING);
        }
    }

    void handleEconomicClassPassenger(Passenger passenger) {
        if (isEconomicClassSeatsNotAvailable()) {
            logger.warning(FULL_ECONOMIC_CLASS);
        }else{
            flightPassengerService.addPassenger(passenger);
            logger.info(SUCCESS_BOOKING);
        }
    }


    Passenger changeToBusinessClass(Map<PassengerKeyConstants, Object> passengerDataMap,FlightInformation flightInformation ) {
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_CLASS, PassengerClass.BUSINESS_CLASS);
        return passengerService.createPassenger(flightInformation, passengerDataMap);
    }

    Passenger changeToEconomicClass(Map<PassengerKeyConstants, Object> passengerDataMap,FlightInformation flightInformation ) {
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_CLASS, PassengerClass.ECONOMY_CLASS);
        return passengerService.createPassenger(flightInformation, passengerDataMap);
    }

    private boolean isSeatsAvailable() {
        return flightPassengerService.numberOfTotalSeatsAvailable() != 0;
    }
    private boolean isFirstClassSeatsNotAvailable() {
        return flightPassengerService.numberOfFirstClassSeatsAvailable() == 0;
    }
    private boolean isBusinessClassSeatsNotAvailable() {
        return flightPassengerService.numberOfBusinessClassSeatsAvailable() == 0;
    }
    private boolean isEconomicClassSeatsNotAvailable() {
        return flightPassengerService.numberOfEconomyClassSeatsAvailable() == 0;
    }


    private Map<PassengerKeyConstants, Object> getPassengerKeyConstantsObjectMap(Passenger passenger) {
        Map<PassengerKeyConstants, Object> passengerDataMap = new EnumMap<>(PassengerKeyConstants.class);
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_PASSPORT, passenger.getPassport());
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_NAME, passenger.getName());
        passengerDataMap.put(PassengerKeyConstants.PASSENGER_AGE, passenger.getAge());
        return passengerDataMap;
    }
}
