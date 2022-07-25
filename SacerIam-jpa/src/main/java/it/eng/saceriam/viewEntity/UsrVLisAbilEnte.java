package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_LIS_ABIL_ENTE database table.
 */
@Entity
@Table(name = "USR_V_LIS_ABIL_ENTE")
@NamedQuery(name = "UsrVLisAbilEnte.findAll", query = "SELECT u FROM UsrVLisAbilEnte u")
public class UsrVLisAbilEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleAbil;

    private String flAbilAutomatica;

    private BigDecimal idAccordoVigil;

    private BigDecimal idAppartCollegEnti;

    private BigDecimal idEnteUtente;

    private BigDecimal idSuptEstEnteConvenz;

    private String nmCompositoEnte;

    private String tiScopoAbil;

    public UsrVLisAbilEnte() {
    }

    @Column(name = "DS_CAUSALE_ABIL")
    public String getDsCausaleAbil() {
        return this.dsCausaleAbil;
    }

    public void setDsCausaleAbil(String dsCausaleAbil) {
        this.dsCausaleAbil = dsCausaleAbil;
    }

    @Column(name = "FL_ABIL_AUTOMATICA", columnDefinition = "char(1)")
    public String getFlAbilAutomatica() {
        return this.flAbilAutomatica;
    }

    public void setFlAbilAutomatica(String flAbilAutomatica) {
        this.flAbilAutomatica = flAbilAutomatica;
    }

    @Column(name = "ID_ACCORDO_VIGIL")
    public BigDecimal getIdAccordoVigil() {
        return this.idAccordoVigil;
    }

    public void setIdAccordoVigil(BigDecimal idAccordoVigil) {
        this.idAccordoVigil = idAccordoVigil;
    }

    @Column(name = "ID_APPART_COLLEG_ENTI")
    public BigDecimal getIdAppartCollegEnti() {
        return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
        this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Column(name = "ID_ENTE_UTENTE")
    public BigDecimal getIdEnteUtente() {
        return this.idEnteUtente;
    }

    public void setIdEnteUtente(BigDecimal idEnteUtente) {
        this.idEnteUtente = idEnteUtente;
    }

    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public BigDecimal getIdSuptEstEnteConvenz() {
        return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
        this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
    }

    @Column(name = "NM_COMPOSITO_ENTE")
    public String getNmCompositoEnte() {
        return this.nmCompositoEnte;
    }

    public void setNmCompositoEnte(String nmCompositoEnte) {
        this.nmCompositoEnte = nmCompositoEnte;
    }

    @Column(name = "TI_SCOPO_ABIL")
    public String getTiScopoAbil() {
        return this.tiScopoAbil;
    }

    public void setTiScopoAbil(String tiScopoAbil) {
        this.tiScopoAbil = tiScopoAbil;
    }

    private UsrVLisAbilEnteId usrVLisAbilEnteId;

    @EmbeddedId()
    public UsrVLisAbilEnteId getUsrVLisAbilEnteId() {
        return usrVLisAbilEnteId;
    }

    public void setUsrVLisAbilEnteId(UsrVLisAbilEnteId usrVLisAbilEnteId) {
        this.usrVLisAbilEnteId = usrVLisAbilEnteId;
    }
}
