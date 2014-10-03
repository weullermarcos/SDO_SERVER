package controllers.register;

import models.ItineraryBO;
import play.mvc.With;
import controllers.CRUD;
import controllers.CRUD.For;
import controllers.Secure;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:48:07
 */
@With(Secure.class)
@For(ItineraryBO.class)
public class ItineraryController extends CRUD {
}
