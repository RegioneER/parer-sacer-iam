package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ORG_V_RIC_ENTE_NON_CONVENZ database table.
 *
 */
@Entity
@Table(name = "ORG_V_RIC_ENTE_NON_CONVENZ")
@NamedQuery(name = "OrgVRicEnteNonConvenz.findAll", query = "SELECT a FROM OrgVRicEnteNonConvenz a")
public class OrgVRicEnteNonConvenz implements Serializable {

    private static final long serialVersionUID = 1L;
    private String archivista;
    private Date dtCessazione;
    private Date dtIniVal;
    private BigDecimal idEnteSiam;
    private BigDecimal idUserIamArk;
    private BigDecimal idUserIamCor;
    private String nmEnteSiam;
    private String nmUseridArk;
    private String tiEnteNonConvenz;

    public OrgVRicEnteNonConvenz() {
    }

    public OrgVRicEnteNonConvenz(BigDecimal idEnteSiam, String nmEnteSiam, Date dtIniVal, Date dtCessazione,
            String archivista, String tiEnteNonConvenz) {
        this.idEnteSiam = idEnteSiam;
        this.nmEnteSiam = nmEnteSiam;
        this.dtIniVal = dtIniVal;
        this.dtCessazione = dtCessazione;
        this.archivista = archivista;
        this.tiEnteNonConvenz = tiEnteNonConvenz;
    }

    @Column(name = "ARCHIVISTA")
    public String getArchivista() {
        return this.archivista;
    }

    public void setArchivista(String archivista) {
        this.archivista = archivista;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CESSAZIONE")
    public Date getDtCessazione() {
        return this.dtCessazione;
    }

    public void setDtCessazione(Date dtCessazione) {
        this.dtCessazione = dtCessazione;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Id
    @Column(name = "ID_ENTE_SIAM")
    public BigDecimal getIdEnteSiam() {
        return this.idEnteSiam;
    }

    public void setIdEnteSiam(BigDecimal idEnteSiam) {
        this.idEnteSiam = idEnteSiam;
    }

    @Column(name = "ID_USER_IAM_ARK")
    public BigDecimal getIdUserIamArk() {
        return this.idUserIamArk;
    }

    public void setIdUserIamArk(BigDecimal idUserIamArk) {
        this.idUserIamArk = idUserIamArk;
    }

    @Column(name = "ID_USER_IAM_COR")
    public BigDecimal getIdUserIamCor() {
        return idUserIamCor;
    }

    public void setIdUserIamCor(BigDecimal idUserIamCor) {
        this.idUserIamCor = idUserIamCor;
    }

    @Column(name = "NM_USERID_ARK")
    public String getNmUseridArk() {
        return this.nmUseridArk;
    }

    public void setNmUseridArk(String nmUseridArk) {
        this.nmUseridArk = nmUseridArk;
    }

    @Column(name = "NM_ENTE_SIAM")
    public String getNmEnteSiam() {
        return this.nmEnteSiam;
    }

    public void setNmEnteSiam(String nmEnteSiam) {
        this.nmEnteSiam = nmEnteSiam;
    }

    @Column(name = "TI_ENTE_NON_CONVENZ")
    public String getTiEnteNonConvenz() {
        return this.tiEnteNonConvenz;
    }

    public void setTiEnteNonConvenz(String tiEnteNonConvenz) {
        this.tiEnteNonConvenz = tiEnteNonConvenz;
    }

}
