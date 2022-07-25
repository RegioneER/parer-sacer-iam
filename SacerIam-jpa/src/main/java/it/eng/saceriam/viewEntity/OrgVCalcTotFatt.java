package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the ORG_V_CALC_TOT_FATT database table.
 *
 */
@Entity
@Table(name = "ORG_V_CALC_TOT_FATT")
@NamedQuery(name = "OrgVCalcTotFatt.findAll", query = "SELECT o FROM OrgVCalcTotFatt o")
public class OrgVCalcTotFatt implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal idFatturaEnte;
    private BigDecimal imTotDaPagare;
    private BigDecimal imTotFattura;
    private BigDecimal imTotIva;

    public OrgVCalcTotFatt() {
    }

    @Id
    @Column(name = "ID_FATTURA_ENTE")
    public BigDecimal getIdFatturaEnte() {
        return this.idFatturaEnte;
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
        this.idFatturaEnte = idFatturaEnte;
    }

    @Column(name = "IM_TOT_DA_PAGARE")
    public BigDecimal getImTotDaPagare() {
        return this.imTotDaPagare;
    }

    public void setImTotDaPagare(BigDecimal imTotDaPagare) {
        this.imTotDaPagare = imTotDaPagare;
    }

    @Column(name = "IM_TOT_FATTURA")
    public BigDecimal getImTotFattura() {
        return this.imTotFattura;
    }

    public void setImTotFattura(BigDecimal imTotFattura) {
        this.imTotFattura = imTotFattura;
    }

    @Column(name = "IM_TOT_IVA")
    public BigDecimal getImTotIva() {
        return this.imTotIva;
    }

    public void setImTotIva(BigDecimal imTotIva) {
        this.imTotIva = imTotIva;
    }

}
