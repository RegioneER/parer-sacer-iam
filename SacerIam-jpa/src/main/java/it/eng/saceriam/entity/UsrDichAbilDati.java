package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the USR_DICH_ABIL_DATI database table.
 * 
 */
@Entity
@Table(name = "USR_DICH_ABIL_DATI")
@NamedQuery(name = "UsrDichAbilDati.findAll", query = "SELECT u FROM UsrDichAbilDati u")
public class UsrDichAbilDati implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idDichAbilDati;
    private String dsCausaleDich;
    private String tiScopoDichAbilDati;
    private List<UsrAbilDati> usrAbilDatis = new ArrayList<>();
    private AplClasseTipoDato aplClasseTipoDato;
    private OrgAppartCollegEnti orgAppartCollegEnti;
    private OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenz;
    private OrgVigilEnteProdut orgVigilEnteProdut;
    private UsrOrganizIam usrOrganizIam;
    private UsrTipoDatoIam usrTipoDatoIam;
    private UsrUsoUserApplic usrUsoUserApplic;

    public UsrDichAbilDati() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_DICH_ABIL_DATI") // @SequenceGenerator(name =
                                                                         // "USR_DICH_ABIL_DATI_IDDICHABILDATI_GENERATOR",
                                                                         // sequenceName = "SUSR_DICH_ABIL_DATI",
                                                                         // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_DICH_ABIL_DATI_IDDICHABILDATI_GENERATOR")
    @Column(name = "ID_DICH_ABIL_DATI")
    public Long getIdDichAbilDati() {
        return this.idDichAbilDati;
    }

    public void setIdDichAbilDati(Long idDichAbilDati) {
        this.idDichAbilDati = idDichAbilDati;
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "TI_SCOPO_DICH_ABIL_DATI")
    public String getTiScopoDichAbilDati() {
        return this.tiScopoDichAbilDati;
    }

    public void setTiScopoDichAbilDati(String tiScopoDichAbilDati) {
        this.tiScopoDichAbilDati = tiScopoDichAbilDati;
    }

    // bi-directional many-to-one association to UsrAbilDati
    @OneToMany(mappedBy = "usrDichAbilDati")
    public List<UsrAbilDati> getUsrAbilDatis() {
        return this.usrAbilDatis;
    }

    public void setUsrAbilDatis(List<UsrAbilDati> usrAbilDatis) {
        this.usrAbilDatis = usrAbilDatis;
    }

    public UsrAbilDati addUsrAbilDati(UsrAbilDati usrAbilDati) {
        getUsrAbilDatis().add(usrAbilDati);
        usrAbilDati.setUsrDichAbilDati(this);

        return usrAbilDati;
    }

    public UsrAbilDati removeUsrAbilDati(UsrAbilDati usrAbilDati) {
        getUsrAbilDatis().remove(usrAbilDati);
        usrAbilDati.setUsrDichAbilDati(null);

        return usrAbilDati;
    }

    // bi-directional many-to-one association to AplClasseTipoDato
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLASSE_TIPO_DATO")
    public AplClasseTipoDato getAplClasseTipoDato() {
        return this.aplClasseTipoDato;
    }

    public void setAplClasseTipoDato(AplClasseTipoDato aplClasseTipoDato) {
        this.aplClasseTipoDato = aplClasseTipoDato;
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

    // bi-directional many-to-one association to OrgVigilEnteProdut
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VIGIL_ENTE_PRODUT")
    public OrgVigilEnteProdut getOrgVigilEnteProdut() {
        return this.orgVigilEnteProdut;
    }

    public void setOrgVigilEnteProdut(OrgVigilEnteProdut orgVigilEnteProdut) {
        this.orgVigilEnteProdut = orgVigilEnteProdut;
    }

    // bi-directional many-to-one association to UsrOrganizIam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGANIZ_IAM")
    public UsrOrganizIam getUsrOrganizIam() {
        return this.usrOrganizIam;
    }

    public void setUsrOrganizIam(UsrOrganizIam usrOrganizIam) {
        this.usrOrganizIam = usrOrganizIam;
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

}