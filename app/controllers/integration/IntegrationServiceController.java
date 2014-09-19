package controllers.integration;

import java.util.List;

import models.BaseModel;
import models.OnibusBO;
import models.PosicaoBO;
import play.mvc.Controller;

public class IntegrationServiceController extends Controller {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Methods - public static
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void savePosition() {
        final String jsonString = request.params.get("body", String.class);
        final PosicaoBO jsonObject = PosicaoBO.fromJson(jsonString, PosicaoBO.class);
        jsonObject.delete();
        System.out.println(jsonObject.toJson());
        ok();
    }
    public static void findBusByItinerary(final String lineItineraty) {
        final List<OnibusBO> lstObjects = OnibusBO.findLastsPositionByLineItineraty(lineItineraty);
        renderJSON(BaseModel.toJson(lstObjects));
    }
}
