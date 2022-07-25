package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_CREA_ABIL_DATI database table.
 */
@Entity
@Table(name = "USR_V_CREA_ABIL_DATI")
@NamedQuery(name = "UsrVCreaAbilDati.findAll", query = "SELECT u FROM UsrVCreaAbilDati u")
public class UsrVCreaAbilDati implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsCausaleDich;

    private BigDecimal idAppartCollegEnti;

    private BigDecimal idSuptEstEnteConvenz;

    private BigDecimal idVigilEnteProdut;

    private String nmApplic;

    private String nmClasseTipoDato;

    private String nmUserid;

    public UsrVCreaAbilDati() {
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "ID_APPART_COLLEG_ENTI")
    public BigDecimal getIdAppartCollegEnti() {
        return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
        this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public BigDecimal getIdSuptEstEnteConvenz() {
        return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
        this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
    }

    @Column(name = "ID_VIGIL_ENTE_PRODUT")
    public BigDecimal getIdVigilEnteProdut() {
        return this.idVigilEnteProdut;
    }

    public void setIdVigilEnteProdut(BigDecimal idVigilEnteProdut) {
        this.idVigilEnteProdut = idVigilEnteProdut;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_CLASSE_TIPO_DATO")
    public String getNmClasseTipoDato() {
        return this.nmClasseTipoDato;
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
        this.nmClasseTipoDato = nmClasseTipoDato;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    private UsrVCreaAbilDatiId usrVCreaAbilDatiId;

    @EmbeddedId()
    public UsrVCreaAbilDatiId getUsrVCreaAbilDatiId() {
        return usrVCreaAbilDatiId;
    }

    public void setUsrVCreaAbilDatiId(UsrVCreaAbilDatiId usrVCreaAbilDatiId) {
        this.usrVCreaAbilDatiId = usrVCreaAbilDatiId;
    }
}
