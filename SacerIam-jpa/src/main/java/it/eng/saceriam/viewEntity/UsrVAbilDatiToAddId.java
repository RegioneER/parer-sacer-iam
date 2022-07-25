package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable()
public class UsrVAbilDatiToAddId implements Serializable {

    private BigDecimal idTipoDatoIam;

    @Column(name = "ID_TIPO_DATO_IAM")
    public BigDecimal getIdTipoDatoIam() {
        return idTipoDatoIam;
    }

    public void setIdTipoDatoIam(BigDecimal idTipoDatoIam) {
        this.idTipoDatoIam = idTipoDatoIam;
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
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.idTipoDatoIam);
        hash = 97 * hash + Objects.hashCode(this.idUserIam);
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
        final UsrVAbilDatiToAddId other = (UsrVAbilDatiToAddId) obj;
        if (!Objects.equals(this.idTipoDatoIam, other.idTipoDatoIam)) {
            return false;
        }
        if (!Objects.equals(this.idUserIam, other.idUserIam)) {
            return false;
        }
        return true;
    }
}
