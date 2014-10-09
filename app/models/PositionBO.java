package models;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import models.PositionBO.JpaEventListener;

import org.apache.commons.lang.math.NumberUtils;

import play.data.validation.Required;
import common.annotations.JsonExclude;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:03:14
 */
@Entity
@Table(name = "TB_POSICAO")
@EntityListeners(JpaEventListener.class)
public class PositionBO extends BaseModel {

    private static final long serialVersionUID = -8441987108826483287L;
    @Id
    @GeneratedValue
    private Long id;
    @Required
    @Column(name = "latitude", precision = 9, scale = 6)
    private Double latitude;
    @Required
    @Column(name = "longitude", precision = 9, scale = 6)
    private Double longitude;
    @Required
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Required
    private Short speed;
    private String positionItinerary;
    private PositionSense positionSense;
    @Required
    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
    @JoinColumn(name = "ONIBUS_ID")
    private BusBO bus;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data Access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static long countByBusId(final String busId) {
        return count("bus.licensePlate = ?1", busId);
    }

    public static List<PositionBO> findPositionByItineraryId(final String itineraryId) {
        return find("bus.itinerary.routeNumber = ?1", itineraryId).fetch();
    }
    
    public static PositionBO findFirtPositionByBusId(final String busId) {
        return find("bus.licensePlate = ?1 ORDER BY date ASC", busId).first();
    }

    public static List<PositionBO> findByKeyword(final String keyword, final String orderBy, final String order, final Integer offset, final Integer pageSize) {
        final StringBuilder builder = new StringBuilder();
        if (NumberUtils.isNumber(keyword)) {
            builder.append("latitude = ").append(keyword);
            builder.append(" OR longitude = ").append(keyword);
            builder.append(" OR speed = ").append(keyword);
            builder.append(" OR positionItinerary LIKE ?1");
            builder.append(" OR bus.licensePlate LIKE ?1");
        } else {
            builder.append("bus.licensePlate LIKE ?1");
        }
        builder.append(" ORDER BY ").append(orderBy).append(" ").append(order);
        if (offset == null && pageSize == null) {
            return find(builder.toString(), "%" + keyword.toUpperCase() + "%").fetch();
        }
        return find(builder.toString(), "%" + keyword.toUpperCase() + "%").fetch(offset, pageSize);
    }
    
    public static void deleteFirstPositioByBusId(final String busId) {
        final PositionBO firstPosition = findFirtPositionByBusId(busId);
        firstPosition.delete();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // get/set
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getPositionItinerary() {
        return positionItinerary;
    }

    public void setPositionItinerary(String positionItinerary) {
        this.positionItinerary = positionItinerary;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public Short getSpeed() {
        return this.speed;
    }

    public void setSpeed(final Short speed) {
        this.speed = speed;
    }

    public BusBO getBus() {
        return this.bus;
    }

    public void setBus(final BusBO bus) {
        this.bus = bus;
    }

    public PositionSense getPositionSense() {
        return positionSense;
    }

    public void setPositionSense(PositionSense positionSense) {
        this.positionSense = positionSense;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see java.lang.Comparable#compareTo(java.lang.Object)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public int compareTo(final BaseModel o) {
        return this.getDate().compareTo(((PositionBO) o).getDate());
    }

    /**
     * @author jgomes - Jefferson Chaves Gomes | 17/10/2014 - 02:16:57
     */
    public static enum PositionSense {
        TO_START_POINT,
        TO_END_POINT
    }
    
    /**
     * @author jgomes - Jefferson Chaves Gomes | 15/09/2014 - 18:04:35
     */
    public static class JpaEventListener {

        private static final int POSITION_LIMIT = 10;

        @PrePersist
        public void limitPositions(final PositionBO object) {
            final long count = PositionBO.countByBusId(object.getBus().getLicensePlate());
            if (count >= POSITION_LIMIT) {
                deleteFirstPositioByBusId(object.getBus().getLicensePlate());
            }
        }
    }
}
