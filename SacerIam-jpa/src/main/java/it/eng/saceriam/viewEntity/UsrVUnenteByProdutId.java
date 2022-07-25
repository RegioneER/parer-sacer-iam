package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVUnenteByProdutId implements Serializable {

    private BigDecimal idEnteConvenz;

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
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
        hash = 79 * hash + Objects.hashCode(this.idEnteConvenz);
        hash = 79 * hash + Objects.hashCode(this.idUserIamCor);
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
        final UsrVUnenteByProdutId other = (UsrVUnenteByProdutId) obj;
        if (!Objects.equals(this.idEnteConvenz, other.idEnteConvenz)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamCor, other.idUserIamCor)) {
            return false;
        }
        return true;
    }
}
