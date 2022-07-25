package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class OrgVCreaServFattUnaPrecId implements Serializable {

    private BigDecimal aaServizioFatturaPrec;

    @Column(name = "AA_SERVIZIO_FATTURA_PREC")
    public BigDecimal getAaServizioFatturaPrec() {
        return aaServizioFatturaPrec;
    }

    public void setAaServizioFatturaPrec(BigDecimal aaServizioFatturaPrec) {
        this.aaServizioFatturaPrec = aaServizioFatturaPrec;
    }

    private BigDecimal idFatturaEnte;

    @Column(name = "ID_FATTURA_ENTE")
    public BigDecimal getIdFatturaEnte() {
        return idFatturaEnte;
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
        this.idFatturaEnte = idFatturaEnte;
    }

    private BigDecimal idServizioErogato;

    @Column(name = "ID_SERVIZIO_EROGATO")
    public BigDecimal getIdServizioErogato() {
        return idServizioErogato;
    }

    public void setIdServizioErogato(BigDecimal idServizioErogato) {
        this.idServizioErogato = idServizioErogato;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.aaServizioFatturaPrec);
        hash = 23 * hash + Objects.hashCode(this.idFatturaEnte);
        hash = 23 * hash + Objects.hashCode(this.idServizioErogato);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrgVCreaServFattUnaPrecId other = (OrgVCreaServFattUnaPrecId) obj;
        if (!Objects.equals(this.aaServizioFatturaPrec, other.aaServizioFatturaPrec)) {
            return false;
        }
        if (!Objects.equals(this.idFatturaEnte, other.idFatturaEnte)) {
            return false;
        }
        if (!Objects.equals(this.idServizioErogato, other.idServizioErogato)) {
            return false;
        }
        return true;
    }
}
