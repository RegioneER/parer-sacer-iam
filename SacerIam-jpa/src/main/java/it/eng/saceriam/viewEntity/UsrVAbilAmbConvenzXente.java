package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_AMB_CONVENZ_XENTE database table.
 */
@Entity
@Table(name = "USR_V_ABIL_AMB_CONVENZ_XENTE")
@NamedQuery(name = "UsrVAbilAmbConvenzXente.findAll", query = "SELECT u FROM UsrVAbilAmbConvenzXente u")
public class UsrVAbilAmbConvenzXente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String nmAmbienteEnteConvenz;

    private Date dtFineVal;

    private Date dtIniVal;

    private BigDecimal idEnteConserv;

    private BigDecimal idEnteGestore;

    private String nmEnteConserv;

    private String nmEnteGestore;

    public UsrVAbilAmbConvenzXente() {
    }

    @Column(name = "DS_AMBIENTE_ENTE_CONVENZ")
    public String getDsAmbienteEnteConvenz() {
        return this.dsAmbienteEnteConvenz;
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
        this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Column(name = "ID_ENTE_CONSERV")
    public BigDecimal getIdEnteConserv() {
        return this.idEnteConserv;
    }

    public void setIdEnteConserv(BigDecimal idEnteConserv) {
        this.idEnteConserv = idEnteConserv;
    }

    @Column(name = "ID_ENTE_GESTORE")
    public BigDecimal getIdEnteGestore() {
        return this.idEnteGestore;
    }

    public void setIdEnteGestore(BigDecimal idEnteGestore) {
        this.idEnteGestore = idEnteGestore;
    }

    @Column(name = "NM_ENTE_CONSERV")
    public String getNmEnteConserv() {
        return this.nmEnteConserv;
    }

    public void setNmEnteConserv(String nmEnteConserv) {
        this.nmEnteConserv = nmEnteConserv;
    }

    @Column(name = "NM_ENTE_GESTORE")
    public String getNmEnteGestore() {
        return this.nmEnteGestore;
    }

    public void setNmEnteGestore(String nmEnteGestore) {
        this.nmEnteGestore = nmEnteGestore;
    }

    private UsrVAbilAmbConvenzXenteId usrVAbilAmbConvenzXenteId;

    @EmbeddedId()
    public UsrVAbilAmbConvenzXenteId getUsrVAbilAmbConvenzXenteId() {
        return usrVAbilAmbConvenzXenteId;
    }

    public void setUsrVAbilAmbConvenzXenteId(UsrVAbilAmbConvenzXenteId usrVAbilAmbConvenzXenteId) {
        this.usrVAbilAmbConvenzXenteId = usrVAbilAmbConvenzXenteId;
    }
}
