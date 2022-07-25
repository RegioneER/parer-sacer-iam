package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_LIS_ENTI_SIAM_CREA_USER database table.
 */
@Entity
@Table(name = "USR_V_LIS_ENTI_SIAM_CREA_USER")
@NamedQuery(name = "UsrVLisEntiSiamCreaUser.findAll", query = "SELECT u FROM UsrVLisEntiSiamCreaUser u")
public class UsrVLisEntiSiamCreaUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private BigDecimal idAmbienteEnteConvenz;

    private BigDecimal idRichGestUser;

    private String nmAmbienteEnteConvenz;

    private String nmEnteSiam;

    public UsrVLisEntiSiamCreaUser() {
    }

    @Column(name = "DS_AMBIENTE_ENTE_CONVENZ")
    public String getDsAmbienteEnteConvenz() {
        return this.dsAmbienteEnteConvenz;
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
        this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
        return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Column(name = "ID_RICH_GEST_USER")
    public BigDecimal getIdRichGestUser() {
        return this.idRichGestUser;
    }

    public void setIdRichGestUser(BigDecimal idRichGestUser) {
        this.idRichGestUser = idRichGestUser;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "NM_ENTE_SIAM")
    public String getNmEnteSiam() {
        return this.nmEnteSiam;
    }

    public void setNmEnteSiam(String nmEnteSiam) {
        this.nmEnteSiam = nmEnteSiam;
    }

    private UsrVLisEntiSiamCreaUserId usrVLisEntiSiamCreaUserId;

    @EmbeddedId()
    public UsrVLisEntiSiamCreaUserId getUsrVLisEntiSiamCreaUserId() {
        return usrVLisEntiSiamCreaUserId;
    }

    public void setUsrVLisEntiSiamCreaUserId(UsrVLisEntiSiamCreaUserId usrVLisEntiSiamCreaUserId) {
        this.usrVLisEntiSiamCreaUserId = usrVLisEntiSiamCreaUserId;
    }
}
