package controllers.integration;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import models.BaseModel;
import models.BusBO;
import models.ItineraryBO;
import models.PositionBO;
import models.PositionBO.PositionSense;
import play.Play;
import play.data.binding.As;
import play.db.jpa.JPABase;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.results.BadRequest;
import play.mvc.results.Result;

public class IntegrationServiceController extends Controller {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Methods - public static
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void saveJsonPosition() {
        final String jsonString = request.params.get("body", String.class);
        final PositionBO jsonObject = PositionBO.fromJson(jsonString, PositionBO.class);
        validateRequest(jsonObject);
        jsonObject.setDate(DateUtils.addHours(jsonObject.getDate(), -3));
        jsonObject.setPositionItinerary(jsonObject.getBus().getItinerary().getRouteNumber());
        jsonObject.merge()._save();
        ok();
    }

    public static void savePosition(final Double latitude, final Double longitude, final Date date, final Short speed, final PositionSense positionSense, String licensePlate, Long busNumber,
            Short capacity, String routeNumber, String startPoint, String endPoint) {
        final PositionBO object = new PositionBO();
        object.setLatitude(latitude);
        object.setLongitude(longitude);
        object.setDate(date);
        object.setSpeed(speed);
        object.setPositionSense(positionSense);
        object.setBus(new BusBO(licensePlate, busNumber, capacity, new ItineraryBO(routeNumber, startPoint, endPoint)));
        validateRequest(object);
        object.setDate(DateUtils.addHours(date, -3));
        object.setPositionItinerary(object.getBus().getItinerary().getRouteNumber());
        object.merge()._save();
        ok();
    }

    public static void findItineraries() {
        List<ItineraryBO> lstItinerary = ItineraryBO.findAll();
        renderJSON(BaseModel.toJson(lstItinerary));
    }
    
    public static void findItinerary(final String itineraryLine) {
        ItineraryBO object = ItineraryBO.findById(itineraryLine);
        renderJSON(object.toJson());
    }

    public static void findPositionByItineraryId(final String itineraryLine) {
        if (itineraryLine == null || itineraryLine.trim().length() == 0) {
            badRequest();
        }
        final List<PositionBO> lstObjects = PositionBO.findPositionByItineraryId(itineraryLine);
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
    private static void validateRequest(final PositionBO object) {
        validation.valid(object);
        validation.valid(object.getBus());
        validation.valid(object.getBus().getItinerary());
        if (validation.hasErrors()) {
            final String errorMessage = validation.errorsMap().toString();
            throw new Result(errorMessage) {
                @Override
                public void apply(Request request, Response response) {
                    response.status = Http.StatusCode.BAD_REQUEST;
                    response.setHeader("Errors", errorMessage);
                }
            };
        }
    }
}
