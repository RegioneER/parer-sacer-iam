package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVCheckDichAbilEnteId implements Serializable {

    private BigDecimal idDichAbilEnteAggiunta;

    @Column(name = "ID_DICH_ABIL_ENTE_AGGIUNTA")
    public BigDecimal getIdDichAbilEnteAggiunta() {
        return idDichAbilEnteAggiunta;
    }

    public void setIdDichAbilEnteAggiunta(BigDecimal idDichAbilEnteAggiunta) {
        this.idDichAbilEnteAggiunta = idDichAbilEnteAggiunta;
    }

    private BigDecimal idEnteConvenz;

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    private BigDecimal idUserIamCorrente;

    @Column(name = "ID_USER_IAM_CORRENTE")
    public BigDecimal getIdUserIamCorrente() {
        return idUserIamCorrente;
    }

    public void setIdUserIamCorrente(BigDecimal idUserIamCorrente) {
        this.idUserIamCorrente = idUserIamCorrente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.idDichAbilEnteAggiunta);
        hash = 17 * hash + Objects.hashCode(this.idEnteConvenz);
        hash = 17 * hash + Objects.hashCode(this.idUserIamCorrente);
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
        final UsrVCheckDichAbilEnteId other = (UsrVCheckDichAbilEnteId) obj;
        if (!Objects.equals(this.idDichAbilEnteAggiunta, other.idDichAbilEnteAggiunta)) {
            return false;
        }
        if (!Objects.equals(this.idEnteConvenz, other.idEnteConvenz)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamCorrente, other.idUserIamCorrente)) {
            return false;
        }
        return true;
    }
}
