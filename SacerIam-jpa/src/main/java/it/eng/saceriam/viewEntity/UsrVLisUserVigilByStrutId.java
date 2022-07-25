package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVLisUserVigilByStrutId implements Serializable {

    private BigDecimal idOrganizIam;

    @Column(name = "ID_ORGANIZ_IAM")
    public BigDecimal getIdOrganizIam() {
        return idOrganizIam;
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        this.idOrganizIam = idOrganizIam;
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
        hash = 59 * hash + Objects.hashCode(this.idOrganizIam);
        hash = 59 * hash + Objects.hashCode(this.idUserIam);
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
        final UsrVLisUserVigilByStrutId other = (UsrVLisUserVigilByStrutId) obj;
        if (!Objects.equals(this.idOrganizIam, other.idOrganizIam)) {
            return false;
        }
        if (!Objects.equals(this.idUserIam, other.idUserIam)) {
            return false;
        }
        return true;
    }
}
