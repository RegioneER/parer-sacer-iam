package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class OrgVRicFattureId implements Serializable {

    private BigDecimal idFatturaEnte;

    @Column(name = "ID_FATTURA_ENTE")
    public BigDecimal getIdFatturaEnte() {
        return idFatturaEnte;
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
        this.idFatturaEnte = idFatturaEnte;
    }

    private BigDecimal idServizioFattura;

    @Column(name = "ID_SERVIZIO_FATTURA")
    public BigDecimal getIdServizioFattura() {
        return idServizioFattura;
    }

    public void setIdServizioFattura(BigDecimal idServizioFattura) {
        this.idServizioFattura = idServizioFattura;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.idFatturaEnte);
        hash = 79 * hash + Objects.hashCode(this.idServizioFattura);
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
        final OrgVRicFattureId other = (OrgVRicFattureId) obj;
        if (!Objects.equals(this.idFatturaEnte, other.idFatturaEnte)) {
            return false;
        }
        if (!Objects.equals(this.idServizioFattura, other.idServizioFattura)) {
            return false;
        }
        return true;
    }
}
