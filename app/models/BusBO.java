package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:03:05
 */
@Entity
@Table(name = "TB_ONIBUS")
public class BusBO extends BaseModel {

    private static final long serialVersionUID = 3635118080598839742L;
    @Id
    @Required
    @MinSize(8)
    @MaxSize(8)
    private String licensePlate;
    @Required
    private Long busNumber;
    @Required
    private Short capacity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_INTINERARIO")
    private ItineraryBO itinerary;
    @OneToMany(mappedBy = "bus", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PositionBO> lstPositions;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public BusBO() {
        super();
    }

    public BusBO(final String licensePlate, final Long busNumber, final Short capacity, final ItineraryBO itinerary) {
        super();
        this.licensePlate = licensePlate;
        this.busNumber = busNumber;
        this.capacity = capacity;
        this.itinerary = itinerary;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data Access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static List<BusBO> findPositionByItineraryId(final String itineraryId) {
        return find("itinerary.routeNumber = ?1", itineraryId).fetch();
    }

    public static void deleteFirstPositioByBusId(final String busId) {
        final PositionBO firstPosition = PositionBO.findFirtPositionByBusId(busId);
        firstPosition.delete();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // get/set
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setLicensePlate(final String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getBusNumber() {
        return this.busNumber;
    }

    public void setBusNumber(final Long busNumber) {
        this.busNumber = busNumber;
    }

    public Short getCapacity() {
        return this.capacity;
    }

    public void setCapacity(final Short capacity) {
        this.capacity = capacity;
    }

    public ItineraryBO getItinerary() {
        return this.itinerary;
    }

    public void setItinerary(final ItineraryBO itinerary) {
        this.itinerary = itinerary;
    }

    public List<PositionBO> getLstPositions() {
        return this.lstPositions;
    }

    public void setLstPositions(final List<PositionBO> lstPositions) {
        this.lstPositions = lstPositions;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see java.lang.Comparable#compareTo(java.lang.Object)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public int compareTo(final BaseModel o) {
        return this.getLicensePlate().compareToIgnoreCase(((BusBO) o).getLicensePlate());
    }

    @Override
    public String toString() {
        return this.getLicensePlate();
    }
}
