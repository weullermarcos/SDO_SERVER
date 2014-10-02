package models;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import models.PositionBO.JpaEventListener;

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
    @Column(name = "latitude", precision = 9, scale = 6)
    private Double latitude;
    @Column(name = "longitude", precision = 9, scale = 6)
    private Double longitude;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private Short speed;
    @JsonExclude
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ONIBUS_ID")
    private BusBO bus;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data Access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static long countByBusId(final String busId) {
        return count("bus.licensePlate = ?1", busId);
    }

    public static PositionBO findFirtPositionByBusId(final String busId) {
        return find("bus.licensePlate = ?1 ORDER BY date ASC", busId).first();
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

    public Short getSpeed() {
        return this.speed;
    }

    public void setSpeed(final Short velocidade) {
        this.speed = velocidade;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(final Date data) {
        this.date = data;
    }

    public BusBO getBus() {
        return this.bus;
    }

    public void setBus(final BusBO onibus) {
        this.bus = onibus;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see java.lang.Comparable#compareTo(java.lang.Object)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public int compareTo(final BaseModel o) {
        return this.getDate().compareTo(((PositionBO) o).getDate());
    }

    /**
     * @author jgomes - Jefferson Chaves Gomes | 15/09/2014 - 18:04:35
     */
    public static class JpaEventListener {

        private static final int POSITION_LIMIT = 10;

        @PrePersist
        public void limitPositions(final PositionBO object) {
            final long count = PositionBO.countByBusId(object.getBus().getLicensePlate());
            if (count > POSITION_LIMIT) {
                BusBO.deleteFirstPositioByBusId(object.getBus().getLicensePlate());
            }
        }
    }
}
