package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVCheckDichAbilOrganizId implements Serializable {

    private BigDecimal idDichAbilOrganizAggiunta;

    @Column(name = "ID_DICH_ABIL_ORGANIZ_AGGIUNTA")
    public BigDecimal getIdDichAbilOrganizAggiunta() {
        return idDichAbilOrganizAggiunta;
    }

    public void setIdDichAbilOrganizAggiunta(BigDecimal idDichAbilOrganizAggiunta) {
        this.idDichAbilOrganizAggiunta = idDichAbilOrganizAggiunta;
    }

    private BigDecimal idOrganizIam;

    @Column(name = "ID_ORGANIZ_IAM")
    public BigDecimal getIdOrganizIam() {
        return idOrganizIam;
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        this.idOrganizIam = idOrganizIam;
    }

    private BigDecimal idUserIamCorrente;

    @Column(name = "ID_USER_IAM_CORRENTE")
    public BigDecimal getIdUserIamCorrente() {
        return idUserIamCorrente;
    }

    public void setIdUserIamCorrente(BigDecimal idUserIamCorrente) {
        this.idUserIamCorrente = idUserIamCorrente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idDichAbilOrganizAggiunta);
        hash = 53 * hash + Objects.hashCode(this.idOrganizIam);
        hash = 53 * hash + Objects.hashCode(this.idUserIamCorrente);
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
        final UsrVCheckDichAbilOrganizId other = (UsrVCheckDichAbilOrganizId) obj;
        if (!Objects.equals(this.idDichAbilOrganizAggiunta, other.idDichAbilOrganizAggiunta)) {
            return false;
        }
        if (!Objects.equals(this.idOrganizIam, other.idOrganizIam)) {
            return false;
        }
        if (!Objects.equals(this.idUserIamCorrente, other.idUserIamCorrente)) {
            return false;
        }
        return true;
    }
}
