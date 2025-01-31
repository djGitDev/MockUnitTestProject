package ca.uqam.mgl7230.tp1.service;

import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.passenger.*;
import ca.uqam.mgl7230.tp1.utils.DistanceCalculator;

import java.util.Map;
import java.util.logging.Logger;

public class PassengerService {

    private static final Logger logger = Logger.getLogger(PassengerService.class.getName());
    private static final String PASSENGER_NOT_EXIST = "Passenger type not existent, please try again";
    private  DistanceCalculator distanceCalculator;

    public PassengerService(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

//    public Passenger createPassenger(FlightInformation flightInformation,
//                                     Map<PassengerKeyConstants, Object> passengerData) {
//        String passengerPassport = (String) passengerData.get(PassengerKeyConstants.PASSENGER_PASSPORT);
//        String passengerName = (String) passengerData.get(PassengerKeyConstants.PASSENGER_NAME);
//        int passengerAge = (int) passengerData.get(PassengerKeyConstants.PASSENGER_AGE);
//        PassengerClass passengerClass = (PassengerClass) passengerData.get(PassengerKeyConstants.PASSENGER_CLASS);
//
//        Passenger passenger = null;
//        boolean passengerFound = false;
//        while (!passengerFound) {
//            switch (passengerClass) {
//                case FIRST_CLASS -> {
//                    passenger = new FirstClassPassenger(passengerPassport, passengerName, passengerAge,
//                            distanceCalculator.calculate(flightInformation));
//                    passengerFound = true;
//                }
//                case BUSINESS_CLASS -> {
//                    passenger = new BusinessClassPassenger(passengerPassport, passengerName, passengerAge,
//                            distanceCalculator.calculate(flightInformation));
//                    passengerFound = true;
//                }
//                case ECONOMY_CLASS -> {
//                    passenger = new EconomyClassPassenger(passengerPassport, passengerName, passengerAge,
//                            distanceCalculator.calculate(flightInformation));
//                    passengerFound = true;
//                }
//                default -> {
//                    System.out.println(PASSENGER_NOT_EXIST);
//                    return null;
//                }
//
//            }
//        }
//        return passenger;
//    }
    public Passenger createPassenger(FlightInformation flightInformation,
                                 Map<PassengerKeyConstants, Object> passengerData) {

        PassengerClass passengerClass = (PassengerClass) passengerData.get(PassengerKeyConstants.PASSENGER_CLASS);
        if (passengerClass == null) {
            logger.warning(PASSENGER_NOT_EXIST);
            return null;
        }
        String passengerPassport = (String) passengerData.get(PassengerKeyConstants.PASSENGER_PASSPORT);
        String passengerName = (String) passengerData.get(PassengerKeyConstants.PASSENGER_NAME);
        int passengerAge = (int) passengerData.get(PassengerKeyConstants.PASSENGER_AGE);
        int flightDistance = distanceCalculator.calculate(flightInformation);

        return switch (passengerClass) {
            case FIRST_CLASS -> new FirstClassPassenger(passengerPassport, passengerName, passengerAge, flightDistance);
            case BUSINESS_CLASS -> new BusinessClassPassenger(passengerPassport, passengerName, passengerAge, flightDistance);
            case ECONOMY_CLASS -> new EconomyClassPassenger(passengerPassport, passengerName, passengerAge, flightDistance);
        };
    }

}


