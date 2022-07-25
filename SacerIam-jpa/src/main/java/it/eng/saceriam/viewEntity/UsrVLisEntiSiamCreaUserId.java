package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVLisEntiSiamCreaUserId implements Serializable {

    private BigDecimal idEnteSiam;

    @Column(name = "ID_ENTE_SIAM")
    public BigDecimal getIdEnteSiam() {
        return idEnteSiam;
    }

    public void setIdEnteSiam(BigDecimal idEnteSiam) {
        this.idEnteSiam = idEnteSiam;
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
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.idEnteSiam);
        hash = 71 * hash + Objects.hashCode(this.idUserIamCor);
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
        final UsrVLisEntiSiamCreaUserId other = (UsrVLisEntiSiamCreaUserId) obj;
        if (!Objects.equals(this.idEnteSiam, other.idEnteSiam)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamCor, other.idUserIamCor)) {
            return false;
        }
        return true;
    }
}
