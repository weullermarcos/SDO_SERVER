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
        jsonObject.delete();
        System.out.println(jsonObject.toJson());
        ok();
    }
    public static void findBusByItinerary(final String lineItineraty) {
        final List<BusBO> lstObjects = BusBO.findLastsPositionByLineItineraty(lineItineraty);
        renderJSON(BaseModel.toJson(lstObjects));
    }
}
