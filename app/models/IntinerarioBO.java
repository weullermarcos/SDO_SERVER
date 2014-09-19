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
public class IntinerarioBO extends BaseModel {

    private static final long serialVersionUID = -2167369076863305437L;
    @Id
    private String linha;
    @Required
    @MaxSize(150)
    private String origem;
    @Required
    @MaxSize(150)
    private String destino;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public IntinerarioBO() {
        super();
    }
    public IntinerarioBO(final String linha, final String origem, final String destino) {
        super();
        this.linha = linha;
        this.origem = origem;
        this.destino = destino;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // get/set
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public String getLinha() {
        return this.linha;
    }
    public void setLinha(final String linha) {
        this.linha = linha;
    }
    public String getOrigem() {
        return this.origem;
    }
    public void setOrigem(final String origem) {
        this.origem = origem;
    }
    public String getDestino() {
        return this.destino;
    }
    public void setDestino(final String destino) {
        this.destino = destino;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see java.lang.Comparable#compareTo(java.lang.Object)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public int compareTo(final BaseModel o) {
        return this.getLinha().compareToIgnoreCase(((IntinerarioBO) o).getLinha());
    }
}
