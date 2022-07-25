package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVAbilOrgVigilToAddId implements Serializable {

    private BigDecimal idOrganizIamStrut;

    @Column(name = "ID_ORGANIZ_IAM_STRUT")
    public BigDecimal getIdOrganizIamStrut() {
        return idOrganizIamStrut;
    }

    public void setIdOrganizIamStrut(BigDecimal idOrganizIamStrut) {
        this.idOrganizIamStrut = idOrganizIamStrut;
    }

    private BigDecimal idUserIamCor;

    @Column(name = "ID_USER_IAM_COR")
    public BigDecimal getIdUserIamCor() {
        return idUserIamCor;
    }

    public void setIdUserIamCor(BigDecimal idUserIamCor) {
        this.idUserIamCor = idUserIamCor;
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
        hash = 71 * hash + Objects.hashCode(this.idOrganizIamStrut);
        hash = 71 * hash + Objects.hashCode(this.idUserIamCor);
        hash = 71 * hash + Objects.hashCode(this.idUserIamGestito);
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
        final UsrVAbilOrgVigilToAddId other = (UsrVAbilOrgVigilToAddId) obj;
        if (!Objects.equals(this.idOrganizIamStrut, other.idOrganizIamStrut)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamCor, other.idUserIamCor)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamGestito, other.idUserIamGestito)) {
            return false;
        }
        return true;
    }
}
