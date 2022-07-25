package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_VIS_LAST_RICH_GEST_USER database table.
 *
 */
@Entity
@Table(name = "USR_V_VIS_LAST_RICH_GEST_USER")
@NamedQuery(name = "UsrVVisLastRichGestUser.findAll", query = "SELECT u FROM UsrVVisLastRichGestUser u")
public class UsrVVisLastRichGestUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dsListaAzioni;
    private Date dtRichGestUser;
    private String flAzioniEvase;
    private BigDecimal idRichGestUser;
    private BigDecimal idUserIam;
    private String keyRichGestUser;

    public UsrVVisLastRichGestUser() {
    }

    @Column(name = "DS_LISTA_AZIONI")
    public String getDsListaAzioni() {
        return this.dsListaAzioni;
    }

    public void setDsListaAzioni(String dsListaAzioni) {
        this.dsListaAzioni = dsListaAzioni;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_RICH_GEST_USER")
    public Date getDtRichGestUser() {
        return this.dtRichGestUser;
    }

    public void setDtRichGestUser(Date dtRichGestUser) {
        this.dtRichGestUser = dtRichGestUser;
    }

    @Column(name = "FL_AZIONI_EVASE", columnDefinition = "char(1)")
    public String getFlAzioniEvase() {
        return this.flAzioniEvase;
    }

    public void setFlAzioniEvase(String flAzioniEvase) {
        this.flAzioniEvase = flAzioniEvase;
    }

    @Id
    @Column(name = "ID_RICH_GEST_USER")
    public BigDecimal getIdRichGestUser() {
        return this.idRichGestUser;
    }

    public void setIdRichGestUser(BigDecimal idRichGestUser) {
        this.idRichGestUser = idRichGestUser;
    }

    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
        return this.idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
        this.idUserIam = idUserIam;
    }

    @Column(name = "KEY_RICH_GEST_USER")
    public String getKeyRichGestUser() {
        return this.keyRichGestUser;
    }

    public void setKeyRichGestUser(String keyRichGestUser) {
        this.keyRichGestUser = keyRichGestUser;
    }

}
