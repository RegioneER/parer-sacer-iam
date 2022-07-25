package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVLisAbilOrganizId implements Serializable {

    private BigDecimal idOrganizIam;

    @Column(name = "ID_ORGANIZ_IAM")
    public BigDecimal getIdOrganizIam() {
        return idOrganizIam;
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        this.idOrganizIam = idOrganizIam;
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
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.idOrganizIam);
        hash = 83 * hash + Objects.hashCode(this.idUserIamGestito);
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
        final UsrVLisAbilOrganizId other = (UsrVLisAbilOrganizId) obj;
        if (!Objects.equals(this.idOrganizIam, other.idOrganizIam)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamGestito, other.idUserIamGestito)) {
            return false;
        }
        return true;
    }
}
