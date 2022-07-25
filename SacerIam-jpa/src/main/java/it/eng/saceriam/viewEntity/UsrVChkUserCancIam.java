package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_CHK_USER_CANC_IAM database table.
 *
 */
@Entity
@Table(name = "USR_V_CHK_USER_CANC_IAM")
@NamedQuery(name = "UsrVChkUserCancIam.findAll", query = "SELECT u FROM UsrVChkUserCancIam u")
public class UsrVChkUserCancIam implements Serializable {

    private static final long serialVersionUID = 1L;
    private String flCancOk;
    private BigDecimal idUserIam;

    public UsrVChkUserCancIam() {
    }

    @Column(name = "FL_CANC_OK", columnDefinition = "char(1)")
    public String getFlCancOk() {
        return this.flCancOk;
    }

    public void setFlCancOk(String flCancOk) {
        this.flCancOk = flCancOk;
    }

    @Id
    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
        return this.idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
        this.idUserIam = idUserIam;
    }

}
