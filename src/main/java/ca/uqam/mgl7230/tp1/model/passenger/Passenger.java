package ca.uqam.mgl7230.tp1.model.passenger;

import java.util.Objects;

public abstract class Passenger {

    private String passport;
    private String name;
    private int age;
    private PassengerClass type;
    private int millagePoints;

    public Passenger(String passport, String name, int age, int millagePoints) {
        this.passport = passport;
        this.name = name;
        this.age = age;
        this.millagePoints = millagePoints;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Passenger passenger = (Passenger) obj;
        return age == passenger.age &&
                millagePoints == passenger.millagePoints &&
                Objects.equals(passport, passenger.passport) &&
                Objects.equals(name, passenger.name) &&
                type == passenger.type;
    }

    public abstract PassengerClass getType();

    public String getPassport() {
        return passport;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getMillagePoints() {
        return millagePoints;
    }
}
