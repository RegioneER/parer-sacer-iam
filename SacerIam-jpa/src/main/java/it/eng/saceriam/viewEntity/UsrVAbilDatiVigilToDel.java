package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_DATI_VIGIL_TO_DEL database table.
 * 
 */
@Entity
@Table(name = "USR_V_ABIL_DATI_VIGIL_TO_DEL")
@NamedQuery(name = "UsrVAbilDatiVigilToDel.findAll", query = "SELECT u FROM UsrVAbilDatiVigilToDel u")
public class UsrVAbilDatiVigilToDel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dsCausaleAbil;
    private String flAbilAutomatica;
    private BigDecimal idAbilDati;
    private BigDecimal idAppartCollegEnti;
    private BigDecimal idApplic;
    private BigDecimal idDichAbilDati;
    private BigDecimal idSuptEstEnteConvenz;
    private BigDecimal idTipoDatoIam;
    private BigDecimal idUserIam;
    private BigDecimal idUsoUserApplic;
    private BigDecimal idVigilEnteProdut;

    public UsrVAbilDatiVigilToDel() {
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

    @Id
    @Column(name = "ID_ABIL_DATI")
    public BigDecimal getIdAbilDati() {
        return this.idAbilDati;
    }

    public void setIdAbilDati(BigDecimal idAbilDati) {
        this.idAbilDati = idAbilDati;
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

    @Column(name = "ID_TIPO_DATO_IAM")
    public BigDecimal getIdTipoDatoIam() {
        return this.idTipoDatoIam;
    }

    public void setIdTipoDatoIam(BigDecimal idTipoDatoIam) {
        this.idTipoDatoIam = idTipoDatoIam;
    }

    @Column(name = "ID_USER_IAM")
    public BigDecimal getIdUserIam() {
        return this.idUserIam;
    }

    public void setIdUserIam(BigDecimal idUserIam) {
        this.idUserIam = idUserIam;
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

}