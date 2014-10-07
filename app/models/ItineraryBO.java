package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.MaxSize;
import play.data.validation.Required;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:02:52
 */
@Entity
@Table(name = "TB_INTINERARIO")
public class ItineraryBO extends BaseModel {

    private static final long serialVersionUID = -2167369076863305437L;
    @Id
    @Required
    private String routeNumber;
    @Required
    @MaxSize(150)
    private String startPoint;
    @Required
    @MaxSize(150)
    private String endPoint;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public ItineraryBO() {
        super();
    }

    public ItineraryBO(final String routeNumber, final String startPoint, final String endPoint) {
        super();
        this.routeNumber = routeNumber;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data Access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static ItineraryBO findAndSave(final String itineratyId, final String startPoint, final String endPoint) {
        ItineraryBO object = findById(itineratyId);
        if (object == null) {
            object = new ItineraryBO();
        }
        object.setRouteNumber(itineratyId);
        object.setStartPoint(startPoint);
        object.setEndPoint(endPoint);
        object.save();
        return object;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // get/set
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public String getRouteNumber() {
        return this.routeNumber;
    }

    public void setRouteNumber(final String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getStartPoint() {
        return this.startPoint;
    }

    public void setStartPoint(final String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return this.endPoint;
    }

    public void setEndPoint(final String endPoint) {
        this.endPoint = endPoint;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see java.lang.Comparable#compareTo(java.lang.Object)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public int compareTo(final BaseModel o) {
        return this.getRouteNumber().compareToIgnoreCase(((ItineraryBO) o).getRouteNumber());
    }

    @Override
    public String toString() {
        return this.getRouteNumber();
    }
}
