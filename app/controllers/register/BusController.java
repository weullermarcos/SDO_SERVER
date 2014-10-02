package controllers.register;

import play.mvc.With;
import models.BusBO;
import controllers.CRUD;
import controllers.Secure;
import controllers.CRUD.For;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:50:06
 */
@With(Secure.class)
@For(BusBO.class)
public class BusController extends CRUD {
}
