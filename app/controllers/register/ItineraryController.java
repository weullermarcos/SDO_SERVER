package controllers.register;

import play.mvc.With;
import models.ItineraryBO;
import controllers.CRUD;
import controllers.Secure;
import controllers.CRUD.For;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:48:07
 */
@With(Secure.class)
@For(ItineraryBO.class)
public class ItineraryController extends CRUD {
}
