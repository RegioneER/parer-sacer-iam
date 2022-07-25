package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVAllorgchildByProdutId implements Serializable {

    private BigDecimal idOrganizIamEnte;

    @Column(name = "ID_ORGANIZ_IAM_ENTE")
    public BigDecimal getIdOrganizIamEnte() {
        return idOrganizIamEnte;
    }

    public void setIdOrganizIamEnte(BigDecimal idOrganizIamEnte) {
        this.idOrganizIamEnte = idOrganizIamEnte;
    }

    private BigDecimal idUserIamCor;

    @Column(name = "ID_USER_IAM_COR")
    public BigDecimal getIdUserIamCor() {
        return idUserIamCor;
    }

    public void setIdUserIamCor(BigDecimal idUserIamCor) {
        this.idUserIamCor = idUserIamCor;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.idOrganizIamEnte);
        hash = 89 * hash + Objects.hashCode(this.idUserIamCor);
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
        final UsrVAllorgchildByProdutId other = (UsrVAllorgchildByProdutId) obj;
        if (!Objects.equals(this.idOrganizIamEnte, other.idOrganizIamEnte)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamCor, other.idUserIamCor)) {
            return false;
        }
        return true;
    }
}
