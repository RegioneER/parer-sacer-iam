package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVListaAbilDatiId implements Serializable {

    private BigDecimal idClasseTipoDato;

    @Column(name = "ID_CLASSE_TIPO_DATO")
    public BigDecimal getIdClasseTipoDato() {
        return idClasseTipoDato;
    }

    public void setIdClasseTipoDato(BigDecimal idClasseTipoDato) {
        this.idClasseTipoDato = idClasseTipoDato;
    }

    private BigDecimal idOrganizApplic;

    @Column(name = "ID_ORGANIZ_APPLIC")
    public BigDecimal getIdOrganizApplic() {
        return idOrganizApplic;
    }

    public void setIdOrganizApplic(BigDecimal idOrganizApplic) {
        this.idOrganizApplic = idOrganizApplic;
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
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idClasseTipoDato);
        hash = 37 * hash + Objects.hashCode(this.idOrganizApplic);
        hash = 37 * hash + Objects.hashCode(this.idUserIam);
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
        final UsrVListaAbilDatiId other = (UsrVListaAbilDatiId) obj;
        if (!Objects.equals(this.idClasseTipoDato, other.idClasseTipoDato)) {
            return false;
        }
        if (!Objects.equals(this.idOrganizApplic, other.idOrganizApplic)) {
            return false;
        }
        if (!Objects.equals(this.idUserIam, other.idUserIam)) {
            return false;
        }
        return true;
    }

}
