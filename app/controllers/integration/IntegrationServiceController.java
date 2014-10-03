package controllers.integration;

import java.util.List;

import models.BaseModel;
import models.BusBO;
import models.PositionBO;
import play.mvc.Controller;

public class IntegrationServiceController extends Controller {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Methods - public static
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void savePosition() {
        final String jsonString = request.params.get("body", String.class);
        final PositionBO jsonObject = PositionBO.fromJson(jsonString, PositionBO.class);
        if (jsonObject.getLatitude() == null || jsonObject.getLongitude() == null || jsonObject.getDate() == null || jsonObject.getBusLicensePlate() == null
                || jsonObject.getBusLicensePlate().length() != 8) {
            badRequest();
        }
        BusBO bus = BusBO.findById(jsonObject.getBusLicensePlate());
        if (bus == null) {
            bus = new BusBO();
            bus.setLicensePlate(jsonObject.getBusLicensePlate());
            bus.save();
        }
        jsonObject.setBus(bus);
        jsonObject.save();
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
}
