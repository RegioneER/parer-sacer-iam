package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_AMB_SACER_XENTE database table.
 */
@Entity
@Table(name = "USR_V_ABIL_AMB_SACER_XENTE")
@NamedQuery(name = "UsrVAbilAmbSacerXente.findAll", query = "SELECT u FROM UsrVAbilAmbSacerXente u")
public class UsrVAbilAmbSacerXente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsOrganiz;

    private String nmApplic;

    private String nmOrganiz;

    public UsrVAbilAmbSacerXente() {
    }

    @Column(name = "DS_ORGANIZ")
    public String getDsOrganiz() {
        return this.dsOrganiz;
    }

    public void setDsOrganiz(String dsOrganiz) {
        this.dsOrganiz = dsOrganiz;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_ORGANIZ")
    public String getNmOrganiz() {
        return this.nmOrganiz;
    }

    public void setNmOrganiz(String nmOrganiz) {
        this.nmOrganiz = nmOrganiz;
    }

    private UsrVAbilAmbSacerXenteId usrVAbilAmbSacerXenteId;

    @EmbeddedId()
    public UsrVAbilAmbSacerXenteId getUsrVAbilAmbSacerXenteId() {
        return usrVAbilAmbSacerXenteId;
    }

    public void setUsrVAbilAmbSacerXenteId(UsrVAbilAmbSacerXenteId usrVAbilAmbSacerXenteId) {
        this.usrVAbilAmbSacerXenteId = usrVAbilAmbSacerXenteId;
    }
}
