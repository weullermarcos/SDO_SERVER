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
import play.data.validation.Required;

/**
 * @author jgomes - Jefferson Chaves Gomes | 11/09/2014 - 11:03:05
 */
@Entity
@Table(name = "TB_ONIBUS")
public class BusBO extends BaseModel {

    private static final long serialVersionUID = 3635118080598839742L;
    @Id
    @MaxSize(7)
    private String placa;
    @Required
    private Long numero;
    private Short capacidadePassageiros;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_INTINERARIO")
    private ItineraryBO intinerario;
    @OneToMany(mappedBy = "onibus", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PositionBO> lstPosicoes;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public BusBO() {
        super();
    }
    public BusBO(final String placa, final Long numero, final Short capacidadePassageiros, final ItineraryBO intinerario) {
        super();
        this.placa = placa;
        this.numero = numero;
        this.capacidadePassageiros = capacidadePassageiros;
        this.intinerario = intinerario;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data Access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static List<BusBO> findLastsPositionByLineItineraty(final String lineItineraty) {
        return find("intinerario.linha = ?1", lineItineraty).fetch();
    }
    public static void deleteFirstPositioByBusId(final String busId) {
        final PositionBO firstPosition = PositionBO.findFirtPositionByBusId(busId);
        firstPosition.delete();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // get/set
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public String getPlaca() {
        return this.placa;
    }
    public void setPlaca(final String placa) {
        this.placa = placa;
    }
    public Long getNumero() {
        return this.numero;
    }
    public void setNumero(final Long numero) {
        this.numero = numero;
    }
    public Short getCapacidadePassageiros() {
        return this.capacidadePassageiros;
    }
    public void setCapacidadePassageiros(final Short capacidadePassageiros) {
        this.capacidadePassageiros = capacidadePassageiros;
    }
    public ItineraryBO getIntinerario() {
        return this.intinerario;
    }
    public void setIntinerario(final ItineraryBO intinerario) {
        this.intinerario = intinerario;
    }
    public List<PositionBO> getLstPosicoes() {
        return this.lstPosicoes;
    }
    public void setLstPosicoes(final List<PositionBO> lstPosicoes) {
        this.lstPosicoes = lstPosicoes;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see java.lang.Comparable#compareTo(java.lang.Object)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public int compareTo(final BaseModel o) {
        return this.getPlaca().compareToIgnoreCase(((BusBO) o).getPlaca());
    }
}
