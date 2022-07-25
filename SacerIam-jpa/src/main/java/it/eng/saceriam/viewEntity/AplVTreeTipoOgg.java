package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the APL_V_TREE_TIPO_OGG database table.
 *
 */
@Entity
@Table(name = "APL_V_TREE_TIPO_OGG")
@NamedQuery(name = "AplVTreeTipoOgg.findAll", query = "SELECT a FROM AplVTreeTipoOgg a")
public class AplVTreeTipoOgg implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dlCompositoTipoOggetto;
    private String dlPathIdTipoOggetto;
    private BigDecimal idApplic;
    private BigDecimal idTipoOggetto;
    private BigDecimal idTipoOggettoPadre;
    private String nmApplic;
    private String nmTipoOggetto;

    public AplVTreeTipoOgg() {
    }

    @Column(name = "DL_COMPOSITO_TIPO_OGGETTO")
    public String getDlCompositoTipoOggetto() {
        return this.dlCompositoTipoOggetto;
    }

    public void setDlCompositoTipoOggetto(String dlCompositoTipoOggetto) {
        this.dlCompositoTipoOggetto = dlCompositoTipoOggetto;
    }

    @Column(name = "DL_PATH_ID_TIPO_OGGETTO")
    public String getDlPathIdTipoOggetto() {
        return this.dlPathIdTipoOggetto;
    }

    public void setDlPathIdTipoOggetto(String dlPathIdTipoOggetto) {
        this.dlPathIdTipoOggetto = dlPathIdTipoOggetto;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Id
    @Column(name = "ID_TIPO_OGGETTO")
    public BigDecimal getIdTipoOggetto() {
        return this.idTipoOggetto;
    }

    public void setIdTipoOggetto(BigDecimal idTipoOggetto) {
        this.idTipoOggetto = idTipoOggetto;
    }

    @Column(name = "ID_TIPO_OGGETTO_PADRE")
    public BigDecimal getIdTipoOggettoPadre() {
        return this.idTipoOggettoPadre;
    }

    public void setIdTipoOggettoPadre(BigDecimal idTipoOggettoPadre) {
        this.idTipoOggettoPadre = idTipoOggettoPadre;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_TIPO_OGGETTO")
    public String getNmTipoOggetto() {
        return this.nmTipoOggetto;
    }

    public void setNmTipoOggetto(String nmTipoOggetto) {
        this.nmTipoOggetto = nmTipoOggetto;
    }

}
