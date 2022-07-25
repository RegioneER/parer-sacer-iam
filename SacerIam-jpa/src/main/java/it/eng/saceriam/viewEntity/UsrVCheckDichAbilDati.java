package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_CHECK_DICH_ABIL_DATI database table.
 */
@Entity
@Table(name = "USR_V_CHECK_DICH_ABIL_DATI")
public class UsrVCheckDichAbilDati implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dlCompositoOrganiz;

    private String nmClasseTipoDato;

    private String nmTipoDato;

    public UsrVCheckDichAbilDati() {
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Column(name = "NM_CLASSE_TIPO_DATO")
    public String getNmClasseTipoDato() {
        return this.nmClasseTipoDato;
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
        this.nmClasseTipoDato = nmClasseTipoDato;
    }

    @Column(name = "NM_TIPO_DATO")
    public String getNmTipoDato() {
        return this.nmTipoDato;
    }

    public void setNmTipoDato(String nmTipoDato) {
        this.nmTipoDato = nmTipoDato;
    }

    private UsrVCheckDichAbilDatiId usrVCheckDichAbilDatiId;

    @EmbeddedId()
    public UsrVCheckDichAbilDatiId getUsrVCheckDichAbilDatiId() {
        return usrVCheckDichAbilDatiId;
    }

    public void setUsrVCheckDichAbilDatiId(UsrVCheckDichAbilDatiId usrVCheckDichAbilDatiId) {
        this.usrVCheckDichAbilDatiId = usrVCheckDichAbilDatiId;
    }
}
