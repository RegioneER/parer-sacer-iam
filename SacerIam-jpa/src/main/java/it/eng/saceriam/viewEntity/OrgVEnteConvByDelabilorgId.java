package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class OrgVEnteConvByDelabilorgId implements Serializable {

    private BigDecimal idDichAbilOrganiz;

    @Column(name = "ID_DICH_ABIL_ORGANIZ")
    public BigDecimal getIdDichAbilOrganiz() {
        return idDichAbilOrganiz;
    }

    public void setIdDichAbilOrganiz(BigDecimal idDichAbilOrganiz) {
        this.idDichAbilOrganiz = idDichAbilOrganiz;
    }

    private BigDecimal idEnteConvenz;

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.idDichAbilOrganiz);
        hash = 17 * hash + Objects.hashCode(this.idEnteConvenz);
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
        final OrgVEnteConvByDelabilorgId other = (OrgVEnteConvByDelabilorgId) obj;
        if (!Objects.equals(this.idDichAbilOrganiz, other.idDichAbilOrganiz)) {
            return false;
        }
        if (!Objects.equals(this.idEnteConvenz, other.idEnteConvenz)) {
            return false;
        }
        return true;
    }
}
