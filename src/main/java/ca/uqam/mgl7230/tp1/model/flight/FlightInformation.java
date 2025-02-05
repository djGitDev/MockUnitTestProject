package ca.uqam.mgl7230.tp1.model.flight;

import ca.uqam.mgl7230.tp1.model.plane.PlaneType;

public class FlightInformation {

    private String flightNumber;
    private Double latSource;
    private Double lonSource;
    private Double latDestination;
    private Double lonDestination;
    private PlaneType planeType;
    private FlightStatus flightStatus;

    public FlightInformation(String flightNumber, Double latSource, Double lonSource, Double latDestination, Double lonDestination, PlaneType planeType) {
        this.flightNumber = flightNumber;
        this.latSource = latSource;
        this.lonSource = lonSource;
        this.latDestination = latDestination;
        this.lonDestination = lonDestination;
        this.planeType = planeType;
        flightStatus = FlightStatus.OPEN;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Double getLatSource() {
        return latSource;
    }

    public Double getLonSource() {
        return lonSource;
    }

    public Double getLatDestination() {
        return latDestination;
    }

    public Double getLonDestination() {
        return lonDestination;
    }

    public PlaneType getPlaneType() {
        return planeType;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }
    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }
}
