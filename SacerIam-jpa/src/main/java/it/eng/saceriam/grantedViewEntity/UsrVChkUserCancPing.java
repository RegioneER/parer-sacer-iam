package it.eng.saceriam.grantedViewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_CHK_USER_CANC_PING database table.
 *
 */
@Entity
@Table(schema = "SACER_PING", name = "USR_V_CHK_USER_CANC_PING")
@NamedQuery(name = "UsrVChkUserCancPing.findAll", query = "SELECT u FROM UsrVChkUserCancPing u")
public class UsrVChkUserCancPing implements Serializable {

    private static final long serialVersionUID = 1L;
    private String flCancOk;
    private BigDecimal idUserIam;

    public UsrVChkUserCancPing() {
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
