package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class OrgVChkRefEnteId implements Serializable {

    private BigDecimal idEnteRef;

    @Column(name = "ID_ENTE_REF")
    public BigDecimal getIdEnteRef() {
        return idEnteRef;
    }

    public void setIdEnteRef(BigDecimal idEnteRef) {
        this.idEnteRef = idEnteRef;
    }

    private BigDecimal idUserIam;

    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
        return idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
        this.idUserIam = idUserIam;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.idEnteRef);
        hash = 31 * hash + Objects.hashCode(this.idUserIam);
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
        final OrgVChkRefEnteId other = (OrgVChkRefEnteId) obj;
        if (!Objects.equals(this.idEnteRef, other.idEnteRef)) {
            return false;
        }
        if (!Objects.equals(this.idUserIam, other.idUserIam)) {
            return false;
        }
        return true;
    }

}
