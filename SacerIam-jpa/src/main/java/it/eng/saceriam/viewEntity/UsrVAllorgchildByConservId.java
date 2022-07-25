package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVAllorgchildByConservId implements Serializable {

    private BigDecimal idOrganizIamAmbEnte;

    @Column(name = "ID_ORGANIZ_IAM_AMB_ENTE")
    public BigDecimal getIdOrganizIamAmbEnte() {
        return idOrganizIamAmbEnte;
    }

    public void setIdOrganizIamAmbEnte(BigDecimal idOrganizIamAmbEnte) {
        this.idOrganizIamAmbEnte = idOrganizIamAmbEnte;
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
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idOrganizIamAmbEnte);
        hash = 37 * hash + Objects.hashCode(this.idUserIamCor);
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
        final UsrVAllorgchildByConservId other = (UsrVAllorgchildByConservId) obj;
        if (!Objects.equals(this.idOrganizIamAmbEnte, other.idOrganizIamAmbEnte)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamCor, other.idUserIamCor)) {
            return false;
        }
        return true;
    }
}
