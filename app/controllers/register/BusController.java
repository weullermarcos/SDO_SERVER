package controllers.register;

import models.BusBO;
import play.mvc.With;
import controllers.CRUD;
import controllers.CRUD.For;
import controllers.Secure;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:50:06
 */
@With(Secure.class)
@For(BusBO.class)
public class BusController extends CRUD {
}
