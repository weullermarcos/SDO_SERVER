package controllers.register;

import java.util.List;

import models.PositionBO;
import play.Play;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;
import controllers.CRUD;
import controllers.CRUD.For;
import controllers.Secure;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:50:14
 */
@With(Secure.class)
@For(PositionBO.class)
public class PositionController extends CRUD {

    public static void list(int page, final String search, final String searchFields, String orderBy, String order) {
        final ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        if (orderBy == null || orderBy.trim().length() == 0) {
            orderBy = "date";
            order = "DESC";
        }
        List<?> objects = null;
        Long count = null;
        Long totalCount = null;
        final String where = (String) request.args.get("where");
        if (search != null && search.trim().length() > 0) {
            objects = PositionBO.findByKeyword(search, orderBy, order, page, getPageSize());
            count = (long) PositionBO.findByKeyword(search, orderBy, order, null, null).size();
            totalCount = type.count(null, null, where);
        } else {
            objects = type.findPage(page, search, searchFields, orderBy, order, where);
            count = type.count(search, searchFields, where);
            totalCount = type.count(null, null, where);
        }
        try {
            render(type, objects, count, totalCount, page, orderBy, order);
        } catch (final TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }

    private static int getPageSize() {
        return Integer.parseInt(Play.configuration.getProperty("crud.pageSize", "30"));
    }
}
