package controllers.register;

import play.mvc.With;
import models.PositionBO;
import controllers.CRUD;
import controllers.Secure;
import controllers.CRUD.For;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:50:14
 */
@With(Secure.class)
@For(PositionBO.class)
public class PositionController extends CRUD {
}
