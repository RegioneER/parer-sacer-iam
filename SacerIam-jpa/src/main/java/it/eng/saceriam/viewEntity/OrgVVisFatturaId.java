package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class OrgVVisFatturaId implements Serializable {

    private BigDecimal idFatturaEnte;

    @Column(name = "ID_FATTURA_ENTE")
    public BigDecimal getIdFatturaEnte() {
        return this.idFatturaEnte;
    }

    public void setIdFatturaEnte(BigDecimal idFatturaEnte) {
        this.idFatturaEnte = idFatturaEnte;
    }

    private BigDecimal idStatoCalc;

    @Column(name = "ID_STATO_CALC")
    public BigDecimal getIdStatoCalc() {
        return idStatoCalc;
    }

    public void setIdStatoCalc(BigDecimal idStatoCalc) {
        this.idStatoCalc = idStatoCalc;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.idFatturaEnte);
        hash = 41 * hash + Objects.hashCode(this.idStatoCalc);
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
        final OrgVVisFatturaId other = (OrgVVisFatturaId) obj;
        if (!Objects.equals(this.idFatturaEnte, other.idFatturaEnte)) {
            return false;
        }
        if (!Objects.equals(this.idStatoCalc, other.idStatoCalc)) {
            return false;
        }
        return true;
    }

}
