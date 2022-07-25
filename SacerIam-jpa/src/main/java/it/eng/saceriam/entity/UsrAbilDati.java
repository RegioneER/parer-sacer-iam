package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_ABIL_DATI database table.
 * 
 */
@Entity
@Table(name = "USR_ABIL_DATI")
@NamedQuery(name = "UsrAbilDati.findAll", query = "SELECT u FROM UsrAbilDati u")
public class UsrAbilDati implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idAbilDati;
    private UsrDichAbilDati usrDichAbilDati;
    private UsrTipoDatoIam usrTipoDatoIam;
    private UsrUsoUserApplic usrUsoUserApplic;
    private String dsCausaleAbil;
    private String flAbilAutomatica;
    private OrgAppartCollegEnti orgAppartCollegEnti;
    private OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenz;

    public UsrAbilDati() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_ABIL_DATI") // @SequenceGenerator(name =
                                                                    // "USR_ABIL_DATI_IDABILDATI_GENERATOR",
                                                                    // sequenceName = "SUSR_ABIL_DATI", allocationSize =
                                                                    // 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_ABIL_DATI_IDABILDATI_GENERATOR")
    @Column(name = "ID_ABIL_DATI")
    public Long getIdAbilDati() {
        return this.idAbilDati;
    }

    public void setIdAbilDati(Long idAbilDati) {
        this.idAbilDati = idAbilDati;
    }

    // bi-directional many-to-one association to UsrDichAbilDati
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DICH_ABIL_DATI")
    public UsrDichAbilDati getUsrDichAbilDati() {
        return this.usrDichAbilDati;
    }

    public void setUsrDichAbilDati(UsrDichAbilDati usrDichAbilDati) {
        this.usrDichAbilDati = usrDichAbilDati;
    }

    // bi-directional many-to-one association to UsrTipoDatoIam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_DATO_IAM")
    public UsrTipoDatoIam getUsrTipoDatoIam() {
        return this.usrTipoDatoIam;
    }

    public void setUsrTipoDatoIam(UsrTipoDatoIam usrTipoDatoIam) {
        this.usrTipoDatoIam = usrTipoDatoIam;
    }

    // bi-directional many-to-one association to UsrUsoUserApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USO_USER_APPLIC")
    public UsrUsoUserApplic getUsrUsoUserApplic() {
        return this.usrUsoUserApplic;
    }

    public void setUsrUsoUserApplic(UsrUsoUserApplic usrUsoUserApplic) {
        this.usrUsoUserApplic = usrUsoUserApplic;
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

    // bi-directional many-to-one association to OrgAppartCollegEnti
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPART_COLLEG_ENTI")
    public OrgAppartCollegEnti getOrgAppartCollegEnti() {
        return this.orgAppartCollegEnti;
    }

    public void setOrgAppartCollegEnti(OrgAppartCollegEnti orgAppartCollegEnti) {
        this.orgAppartCollegEnti = orgAppartCollegEnti;
    }

    // bi-directional many-to-one association to OrgSuptEsternoEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public OrgSuptEsternoEnteConvenz getOrgSuptEsternoEnteConvenz() {
        return this.orgSuptEsternoEnteConvenz;
    }

    public void setOrgSuptEsternoEnteConvenz(OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenz) {
        this.orgSuptEsternoEnteConvenz = orgSuptEsternoEnteConvenz;
    }

}