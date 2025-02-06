package ca.uqam.mgl7230.tp1.service.prompt;

import ca.uqam.mgl7230.tp1.exception.PassengerTypeNotFoundException;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerClass;
import ca.uqam.mgl7230.tp1.model.passenger.PassengerKeyConstants;

import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class PassengerPromptService {

    private static final Logger logger = Logger.getLogger(PassengerPromptService.class.getName());

    private static final String ENTER_PASSPORT = "Enter Passenger passport: ";
    private static final String ENTER_NAME = "Enter Passenger name: ";
    private static final String ENTER_AGE = "Enter Passenger age: ";
    private static final String ENTER_CLASS = "Enter a passenger type: write first for First Class, " +
            "business for Business Class or " +
            "economy for Economy Class";

    public Map<PassengerKeyConstants, Object> getPassengerData(Scanner scanner) {
        logger.info(ENTER_PASSPORT);
        String passengerPassport = scanner.nextLine();
        logger.info(ENTER_NAME);
        String passengerName = scanner.nextLine();
        logger.info(ENTER_AGE);
        int passengerAge = Integer.parseInt(scanner.nextLine());
        logger.info(ENTER_CLASS);
        String passengerType = scanner.nextLine();

        PassengerClass passengerClass = getPassengerClass(passengerType);

        return Map.of(PassengerKeyConstants.PASSENGER_PASSPORT, passengerPassport,
                PassengerKeyConstants.PASSENGER_NAME, passengerName,
                PassengerKeyConstants.PASSENGER_AGE, passengerAge,
                PassengerKeyConstants.PASSENGER_CLASS, passengerClass);
    }

    private static PassengerClass getPassengerClass(String passengerType) {
        switch (passengerType) {
            case "first" -> {
                return PassengerClass.FIRST_CLASS;
            }
            case "business" -> {
                return PassengerClass.BUSINESS_CLASS;
            }
            case "economy" -> {
                return PassengerClass.ECONOMY_CLASS;
            }
            default -> throw new PassengerTypeNotFoundException();
        }
    }
}