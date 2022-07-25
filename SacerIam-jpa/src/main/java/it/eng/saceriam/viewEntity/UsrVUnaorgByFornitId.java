package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVUnaorgByFornitId implements Serializable {

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.idOrganizIamStrut);
        hash = 53 * hash + Objects.hashCode(this.idUserIamCor);
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
        final UsrVUnaorgByFornitId other = (UsrVUnaorgByFornitId) obj;
        if (!Objects.equals(this.idOrganizIamStrut, other.idOrganizIamStrut)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamCor, other.idUserIamCor)) {
            return false;
        }
        return true;
    }
}
