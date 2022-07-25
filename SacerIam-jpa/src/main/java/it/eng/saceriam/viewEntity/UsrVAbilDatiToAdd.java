package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_DATI_TO_ADD database table.
 */
@Entity
@Table(name = "USR_V_ABIL_DATI_TO_ADD")
@NamedQuery(name = "UsrVAbilDatiToAdd.findAll", query = "SELECT u FROM UsrVAbilDatiToAdd u")
public class UsrVAbilDatiToAdd implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dsCausaleAbil;
    private BigDecimal idAppartCollegEnti;
    private BigDecimal idApplic;

    private BigDecimal idClasseTipoDato;

    private BigDecimal idDichAbilDati;
    private BigDecimal idSuptEstEnteConvenz;

    private BigDecimal idUsoUserApplic;
    private BigDecimal idVigilEnteProdut;
    private String nmApplic;

    public UsrVAbilDatiToAdd() {
    }

    @Column(name = "DS_CAUSALE_ABIL")
    public String getDsCausaleAbil() {
        return this.dsCausaleAbil;
    }

    public void setDsCausaleAbil(String dsCausaleAbil) {
        this.dsCausaleAbil = dsCausaleAbil;
    }

    @Column(name = "ID_APPART_COLLEG_ENTI")
    public BigDecimal getIdAppartCollegEnti() {
        return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
        this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "ID_CLASSE_TIPO_DATO")
    public BigDecimal getIdClasseTipoDato() {
        return this.idClasseTipoDato;
    }

    public void setIdClasseTipoDato(BigDecimal idClasseTipoDato) {
        this.idClasseTipoDato = idClasseTipoDato;
    }

    @Column(name = "ID_DICH_ABIL_DATI")
    public BigDecimal getIdDichAbilDati() {
        return this.idDichAbilDati;
    }

    public void setIdDichAbilDati(BigDecimal idDichAbilDati) {
        this.idDichAbilDati = idDichAbilDati;
    }

    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public BigDecimal getIdSuptEstEnteConvenz() {
        return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
        this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
    }

    @Column(name = "ID_USO_USER_APPLIC")
    public BigDecimal getIdUsoUserApplic() {
        return this.idUsoUserApplic;
    }

    public void setIdUsoUserApplic(BigDecimal idUsoUserApplic) {
        this.idUsoUserApplic = idUsoUserApplic;
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

    private UsrVAbilDatiToAddId usrVAbilDatiToAddId;

    @EmbeddedId()
    public UsrVAbilDatiToAddId getUsrVAbilDatiToAddId() {
        return usrVAbilDatiToAddId;
    }

    public void setUsrVAbilDatiToAddId(UsrVAbilDatiToAddId usrVAbilDatiToAddId) {
        this.usrVAbilDatiToAddId = usrVAbilDatiToAddId;
    }
}
