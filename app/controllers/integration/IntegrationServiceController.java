package controllers.integration;

import java.util.Date;
import java.util.List;

import models.BaseModel;
import models.BusBO;
import models.PositionBO;
import play.Play;
import play.data.binding.As;
import play.mvc.Controller;

public class IntegrationServiceController extends Controller {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Methods - public static
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void saveJsonPosition() {
        final String jsonString = request.params.get("body", String.class);
        final PositionBO jsonObject = PositionBO.fromJson(jsonString, PositionBO.class);
        savePosition(jsonObject);
        ok();
    }

    public static void savePosition(final Double latitude, final Double longitude, final Date date, final Short speed, final String busLicensePlate) {
        final PositionBO object = new PositionBO();
        object.setLatitude(latitude);
        object.setLongitude(longitude);
        object.setDate(date);
        object.setSpeed(speed);
        object.setBusLicensePlate(busLicensePlate);
        savePosition(object);
        ok();
    }

    public static void findPositionByItineraryId(final String lineItinerary) {
        if (lineItinerary == null || lineItinerary.trim().length() == 0) {
            badRequest();
        }
        final List<BusBO> lstObjects = BusBO.findPositionByItineraryId(lineItinerary);
        renderJSON(BaseModel.toJson(lstObjects));
    }

    public static void findPositionByBusId(final String licensePlate) {
        if (licensePlate == null || licensePlate.trim().length() == 0) {
            badRequest();
        }
        final BusBO object = BusBO.findById(licensePlate);
        final List<PositionBO> lstPositions = object.getLstPositions();
        renderJSON(BaseModel.toJson(lstPositions));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Methods - private static
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static void savePosition(final PositionBO object) {
        if (object.getLatitude() == null || object.getLongitude() == null || object.getDate() == null || object.getBusLicensePlate() == null || object.getBusLicensePlate().length() != 8) {
            badRequest();
        }
        BusBO bus = BusBO.findById(object.getBusLicensePlate());
        if (bus == null) {
            bus = new BusBO();
            bus.setLicensePlate(object.getBusLicensePlate());
            bus.save();
        }
        object.setBus(bus);
        object.save();
    }
}
