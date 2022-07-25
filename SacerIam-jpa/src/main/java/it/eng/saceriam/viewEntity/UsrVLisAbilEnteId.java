package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVLisAbilEnteId implements Serializable {

    private BigDecimal idAmbienteEnteConvenz;

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
        return idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    private BigDecimal idEnteSiam;

    @Column(name = "ID_ENTE_SIAM")
    public BigDecimal getIdEnteSiam() {
        return idEnteSiam;
    }

    public void setIdEnteSiam(BigDecimal idEnteSiam) {
        this.idEnteSiam = idEnteSiam;
    }

    private BigDecimal idUserIamGestito;

    @Column(name = "ID_USER_IAM_GESTITO")
    public BigDecimal getIdUserIamGestito() {
        return idUserIamGestito;
    }

    public void setIdUserIamGestito(BigDecimal idUserIamGestito) {
        this.idUserIamGestito = idUserIamGestito;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idAmbienteEnteConvenz);
        hash = 37 * hash + Objects.hashCode(this.idEnteSiam);
        hash = 37 * hash + Objects.hashCode(this.idUserIamGestito);
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
        final UsrVLisAbilEnteId other = (UsrVLisAbilEnteId) obj;
        if (!Objects.equals(this.idAmbienteEnteConvenz, other.idAmbienteEnteConvenz)) {
            return false;
        }
        if (!Objects.equals(this.idEnteSiam, other.idEnteSiam)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamGestito, other.idUserIamGestito)) {
            return false;
        }
        return true;
    }

}
