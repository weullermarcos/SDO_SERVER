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
    private String origin;
    @Required
    @MaxSize(150)
    private String arrival;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public ItineraryBO() {
        super();
    }

    public ItineraryBO(final String busNumber, final String origin, final String arrival) {
        super();
        this.routeNumber = busNumber;
        this.origin = origin;
        this.arrival = arrival;
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

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getArrival() {
        return this.arrival;
    }

    public void setArrival(final String arrival) {
        this.arrival = arrival;
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
