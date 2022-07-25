package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_ENTE_TO_ADD database table.
 */
@Entity
@Table(name = "USR_V_ABIL_ENTE_TO_ADD")
@NamedQuery(name = "UsrVAbilEnteToAdd.findAll", query = "SELECT u FROM UsrVAbilEnteToAdd u")
public class UsrVAbilEnteToAdd implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idDichAbilEnteConvenz;

    public UsrVAbilEnteToAdd() {
    }

    @Column(name = "ID_DICH_ABIL_ENTE_CONVENZ")
    public BigDecimal getIdDichAbilEnteConvenz() {
        return this.idDichAbilEnteConvenz;
    }

    public void setIdDichAbilEnteConvenz(BigDecimal idDichAbilEnteConvenz) {
        this.idDichAbilEnteConvenz = idDichAbilEnteConvenz;
    }

    private UsrVAbilEnteToAddId usrVAbilEnteToAddId;

    @EmbeddedId()
    public UsrVAbilEnteToAddId getUsrVAbilEnteToAddId() {
        return usrVAbilEnteToAddId;
    }

    public void setUsrVAbilEnteToAddId(UsrVAbilEnteToAddId usrVAbilEnteToAddId) {
        this.usrVAbilEnteToAddId = usrVAbilEnteToAddId;
    }
}
