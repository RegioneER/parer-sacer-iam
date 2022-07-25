package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class AplVLisOrganizUsoSisVersId implements Serializable {

    private BigDecimal idOrganizIam;

    @Column(name = "ID_ORGANIZ_IAM")
    public BigDecimal getIdOrganizIam() {
        return idOrganizIam;
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        this.idOrganizIam = idOrganizIam;
    }

    private BigDecimal idSistemaVersante;

    @Column(name = "ID_SISTEMA_VERSANTE")
    public BigDecimal getIdSistemaVersante() {
        return idSistemaVersante;
    }

    public void setIdSistemaVersante(BigDecimal idSistemaVersante) {
        this.idSistemaVersante = idSistemaVersante;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.idOrganizIam);
        hash = 37 * hash + Objects.hashCode(this.idSistemaVersante);
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
        final AplVLisOrganizUsoSisVersId other = (AplVLisOrganizUsoSisVersId) obj;
        if (!Objects.equals(this.idOrganizIam, other.idOrganizIam)) {
            return false;
        }
        if (!Objects.equals(this.idSistemaVersante, other.idSistemaVersante)) {
            return false;
        }
        return true;
    }
}
