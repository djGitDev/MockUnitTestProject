package ca.uqam.mgl7230.tp1.adapter.plane;

import ca.uqam.mgl7230.tp1.model.plane.PlaneInformation;
import ca.uqam.mgl7230.tp1.model.plane.PlaneType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static ca.uqam.mgl7230.tp1.model.plane.PlaneType.BOEING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PlaneCatalogImplTest {

    private static final PlaneInformation plane = new PlaneInformation(PlaneType.BOEING, 2, 5, 12);

    @InjectMocks
    private PlaneCatalogImpl planeCatalog;

    @Mock
    Map<PlaneType, PlaneInformation> planeMap;

    @Test
    void getNumberOfTotalSeats() {
        //given
        given(planeMap.get(BOEING)).willReturn(plane);
        //when
        int actualTotalSeatsNumber = planeCatalog.getNumberOfTotalSeats(BOEING);
        //then
        assertEquals( 19,actualTotalSeatsNumber);
    }
}