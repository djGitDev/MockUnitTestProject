package ca.uqam.mgl7230.tp1.service;

import ca.uqam.mgl7230.tp1.adapter.flight.FlightCatalogImpl;
import ca.uqam.mgl7230.tp1.adapter.plane.PlaneCatalog;
import ca.uqam.mgl7230.tp1.model.flight.FlightInformation;
import ca.uqam.mgl7230.tp1.model.passenger.*;
import ca.uqam.mgl7230.tp1.model.plane.PlaneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static ca.uqam.mgl7230.tp1.model.passenger.PassengerClass.*;
import static ca.uqam.mgl7230.tp1.model.plane.PlaneType.BOEING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FlightPassengerServiceTest {


    private static final PlaneType PLANE_TYPE = BOEING;
    private static final String FLIGHT_NUMBER = "UQAM001";



    private FlightPassengerService flightPassengerService;

    @Mock
    PlaneCatalog planeCatalog;

    @Mock
    FlightCatalogImpl flightCatalog;

    @Mock
    FlightInformation flightInformation;

    @Mock
    FirstClassPassenger firstClassPassenger;

    @Mock
    BusinessClassPassenger businessClassPassenger;

    @Mock
    EconomyClassPassenger economicClassPassenger;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        given(flightCatalog.getFlightInformation(FLIGHT_NUMBER)).willReturn(flightInformation);
        given(flightInformation.getPlaneType()).willReturn(PLANE_TYPE);
    }

    @Test
    void addFirstClassPassengerWithAllAvailabalities() {
        //given
        given(planeCatalog.getNumberSeatsFirstClass(PLANE_TYPE)).willReturn(12);
        given(planeCatalog.getNumberSeatsBusinessClass(PLANE_TYPE)).willReturn(10);
        given(planeCatalog.getNumberSeatsEconomyClass(PLANE_TYPE)).willReturn(8);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog, FLIGHT_NUMBER);
        given(firstClassPassenger.getType()).willReturn(FIRST_CLASS);

        //when
        flightPassengerService.addPassenger(firstClassPassenger);

        //then
        assertThat(flightPassengerService.numberOfFirstClassSeatsAvailable()).isEqualTo(11);
        assertThat(flightPassengerService.numberOfBusinessClassSeatsAvailable()).isEqualTo(10);
        assertThat(flightPassengerService.numberOfEconomyClassSeatsAvailable()).isEqualTo(8);


    }

    @Test
    void addBusinessClassPassengerWithAllAvailabalities() {

        //given
        given(planeCatalog.getNumberSeatsFirstClass(PLANE_TYPE)).willReturn(17);
        given(planeCatalog.getNumberSeatsBusinessClass(PLANE_TYPE)).willReturn(1);
        given(planeCatalog.getNumberSeatsEconomyClass(PLANE_TYPE)).willReturn(8);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog, FLIGHT_NUMBER);
        given(businessClassPassenger.getType()).willReturn(BUSINESS_CLASS);

        //when
        flightPassengerService.addPassenger(businessClassPassenger);

        //then
        assertThat(flightPassengerService.numberOfFirstClassSeatsAvailable()).isEqualTo(17);
        assertThat(flightPassengerService.numberOfBusinessClassSeatsAvailable()).isZero();
        assertThat(flightPassengerService.numberOfEconomyClassSeatsAvailable()).isEqualTo(8);

    }

    @Test
    void addEconomicClassPassengerWithAllAvailabalities() {
        //given
        given(planeCatalog.getNumberSeatsFirstClass(PLANE_TYPE)).willReturn(5);
        given(planeCatalog.getNumberSeatsBusinessClass(PLANE_TYPE)).willReturn(0);
        given(planeCatalog.getNumberSeatsEconomyClass(PLANE_TYPE)).willReturn(2);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog, FLIGHT_NUMBER);
        given(economicClassPassenger.getType()).willReturn(ECONOMY_CLASS);

        //when
        flightPassengerService.addPassenger(economicClassPassenger);

        //then
        assertThat(flightPassengerService.numberOfFirstClassSeatsAvailable()).isEqualTo(5);
        assertThat(flightPassengerService.numberOfBusinessClassSeatsAvailable()).isZero();
        assertThat(flightPassengerService.numberOfEconomyClassSeatsAvailable()).isEqualTo(1);
    }

    @Test
    void addFirstClassPassengerWithoutAvailabality() {
        //given
        given(planeCatalog.getNumberSeatsFirstClass(PLANE_TYPE)).willReturn(0);
        given(planeCatalog.getNumberSeatsBusinessClass(PLANE_TYPE)).willReturn(10);
        given(planeCatalog.getNumberSeatsEconomyClass(PLANE_TYPE)).willReturn(2);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog, FLIGHT_NUMBER);
        given(firstClassPassenger.getType()).willReturn(FIRST_CLASS);

        //when
        flightPassengerService.addPassenger(firstClassPassenger);

        //then
        assertThat(flightPassengerService.numberOfFirstClassSeatsAvailable()).isZero();
        assertThat(flightPassengerService.numberOfBusinessClassSeatsAvailable()).isEqualTo(10);
        assertThat(flightPassengerService.numberOfEconomyClassSeatsAvailable()).isEqualTo(2);

    }

    @Test
    void numberOfTotalSeatsAvailable() {

        //given
        given(planeCatalog.getNumberSeatsFirstClass(PLANE_TYPE)).willReturn(12);
        given(planeCatalog.getNumberSeatsBusinessClass(PLANE_TYPE)).willReturn(10);
        given(planeCatalog.getNumberSeatsEconomyClass(PLANE_TYPE)).willReturn(8);
        flightPassengerService = new FlightPassengerService(planeCatalog, flightCatalog, FLIGHT_NUMBER);
        //when
        int actualAllSeats = flightPassengerService.numberOfTotalSeatsAvailable();
        //then
        assertThat(actualAllSeats).isEqualTo(30);
    }
}