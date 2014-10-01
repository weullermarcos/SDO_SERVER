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
    private Date data;
    private Short velocidade;
    @JsonExclude
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ONIBUS_ID")
    private BusBO onibus;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data Access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static long countByBusId(final String busId) {
        return count("onibus.placa = ?1", busId);
    }
    public static PositionBO findFirtPositionByBusId(final String busId) {
        return find("onibus.placa = ?1 ORDER BY data ASC", busId).first();
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
    public Short getVelocidade() {
        return this.velocidade;
    }
    public void setVelocidade(final Short velocidade) {
        this.velocidade = velocidade;
    }
    public Date getData() {
        return this.data;
    }
    public void setData(final Date data) {
        this.data = data;
    }
    public BusBO getOnibus() {
        return this.onibus;
    }
    public void setOnibus(final BusBO onibus) {
        this.onibus = onibus;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see java.lang.Comparable#compareTo(java.lang.Object)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public int compareTo(final BaseModel o) {
        return this.getData().compareTo(((PositionBO) o).getData());
    }

    /**
     * @author jgomes - Jefferson Chaves Gomes | 15/09/2014 - 18:04:35
     */
    public static class JpaEventListener {

        private static final int POSITION_LIMIT = 10;

        @PrePersist
        public void limitPositions(final PositionBO object) {
            final long count = PositionBO.countByBusId(object.getOnibus().getPlaca());
            if (count > POSITION_LIMIT) {
                BusBO.deleteFirstPositioByBusId(object.getOnibus().getPlaca());
            }
        }
    }
}
