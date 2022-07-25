package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable()
public class OrgVCreaFatturaByAnnoId implements Serializable {

    private BigDecimal aaFattura;

    @Column(name = "AA_FATTURA")
    public BigDecimal getAaFattura() {
        return aaFattura;
    }

    public void setAaFattura(BigDecimal aaFattura) {
        this.aaFattura = aaFattura;
    }

    private BigDecimal aaServizioFattura;

    @Column(name = "AA_SERVIZIO_FATTURA")
    public BigDecimal getAaServizioFattura() {
        return aaServizioFattura;
    }

    public void setAaServizioFattura(BigDecimal aaServizioFattura) {
        this.aaServizioFattura = aaServizioFattura;
    }

    private BigDecimal idEnteConvenz;

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    private BigDecimal idAccordoEnte;

    @Column(name = "ID_ACCORDO_ENTE")
    public BigDecimal getIdAccordoEnte() {
        return idAccordoEnte;
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
        this.idAccordoEnte = idAccordoEnte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrgVCreaFatturaByAnnoId that = (OrgVCreaFatturaByAnnoId) o;
        return aaFattura.equals(that.aaFattura) && aaServizioFattura.equals(that.aaServizioFattura)
                && idEnteConvenz.equals(that.idEnteConvenz) && idAccordoEnte.equals(that.idAccordoEnte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aaFattura, aaServizioFattura, idEnteConvenz, idAccordoEnte);
    }
}
