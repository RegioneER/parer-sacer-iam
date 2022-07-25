package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_SISTEMA_VERSANTE database table.
 *
 */
@Entity
@Table(name = "APL_SISTEMA_VERSANTE")
@NamedQuery(name = "AplSistemaVersante.findAll", query = "SELECT a FROM AplSistemaVersante a")
public class AplSistemaVersante implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idSistemaVersante;
    // private BigDecimal cdCapSedeLegale;
    private String cdVersione;
    // private String dsCittaSedeLegale;
    private String dsSistemaVersante;
    // private String dsViaSedeLegale;
    private String dsEmail;
    private String flAssociaPersonaFisica;
    private String flIntegrazione;
    private String flPec;
    private String dsNote;
    private Date dtFineVal;
    private Date dtIniVal;
    // private String nmProduttore;
    private String nmSistemaVersante;
    private OrgEnteSiam orgEnteSiam;
    private List<AplSistemaVersArkRif> aplSistemaVersArkRifs = new ArrayList<>();
    private List<AplSistemaVersanteUserRef> aplSistemaVersanteUserRefs = new ArrayList<>();
    private List<OrgServizioErog> orgServizioErogs = new ArrayList<>();
    private List<UsrUser> usrUsers = new ArrayList<>();

    public AplSistemaVersante() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_SISTEMA_VERSANTE") // @SequenceGenerator(name =
                                                                           // "APL_SISTEMA_VERSANTE_IDSISTEMAVERSANTE_GENERATOR",
                                                                           // sequenceName = "SAPL_SISTEMA_VERSANTE",
                                                                           // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "APL_SISTEMA_VERSANTE_IDSISTEMAVERSANTE_GENERATOR")
    @Column(name = "ID_SISTEMA_VERSANTE")
    public Long getIdSistemaVersante() {
        return this.idSistemaVersante;
    }

    public void setIdSistemaVersante(Long idSistemaVersante) {
        this.idSistemaVersante = idSistemaVersante;
    }

    // @Column(name = "CD_CAP_SEDE_LEGALE")
    // public BigDecimal getCdCapSedeLegale() {
    // return this.cdCapSedeLegale;
    // }
    //
    // public void setCdCapSedeLegale(BigDecimal cdCapSedeLegale) {
    // this.cdCapSedeLegale = cdCapSedeLegale;
    // }

    @Column(name = "CD_VERSIONE")
    public String getCdVersione() {
        return this.cdVersione;
    }

    public void setCdVersione(String cdVersione) {
        this.cdVersione = cdVersione;
    }

    // @Column(name = "DS_CITTA_SEDE_LEGALE")
    // public String getDsCittaSedeLegale() {
    // return this.dsCittaSedeLegale;
    // }
    //
    // public void setDsCittaSedeLegale(String dsCittaSedeLegale) {
    // this.dsCittaSedeLegale = dsCittaSedeLegale;
    // }

    @Column(name = "DS_SISTEMA_VERSANTE")
    public String getDsSistemaVersante() {
        return this.dsSistemaVersante;
    }

    public void setDsSistemaVersante(String dsSistemaVersante) {
        this.dsSistemaVersante = dsSistemaVersante;
    }

    // @Column(name = "DS_VIA_SEDE_LEGALE")
    // public String getDsViaSedeLegale() {
    // return this.dsViaSedeLegale;
    // }
    //
    // public void setDsViaSedeLegale(String dsViaSedeLegale) {
    // this.dsViaSedeLegale = dsViaSedeLegale;
    // }

    @Column(name = "DS_EMAIL")
    public String getDsEmail() {
        return this.dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    @Column(name = "FL_ASSOCIA_PERSONA_FISICA", columnDefinition = "char(1)")
    public String getFlAssociaPersonaFisica() {
        return this.flAssociaPersonaFisica;
    }

    public void setFlAssociaPersonaFisica(String flAssociaPersonaFisica) {
        this.flAssociaPersonaFisica = flAssociaPersonaFisica;
    }

    @Column(name = "FL_INTEGRAZIONE", columnDefinition = "char(1)")
    public String getFlIntegrazione() {
        return this.flIntegrazione;
    }

    public void setFlIntegrazione(String flIntegrazione) {
        this.flIntegrazione = flIntegrazione;
    }

    @Column(name = "FL_PEC", columnDefinition = "char(1)")
    public String getFlPec() {
        return this.flPec;
    }

    public void setFlPec(String flPec) {
        this.flPec = flPec;
    }

    // @Column(name = "NM_PRODUTTORE")
    // public String getNmProduttore() {
    // return this.nmProduttore;
    // }
    //
    // public void setNmProduttore(String nmProduttore) {
    // this.nmProduttore = nmProduttore;
    // }

    @Column(name = "NM_SISTEMA_VERSANTE")
    public String getNmSistemaVersante() {
        return this.nmSistemaVersante;
    }

    public void setNmSistemaVersante(String nmSistemaVersante) {
        this.nmSistemaVersante = nmSistemaVersante;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_SIAM")
    public OrgEnteSiam getOrgEnteSiam() {
        return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to AplSistemaVersArkRif
    @OneToMany(mappedBy = "aplSistemaVersante")
    public List<AplSistemaVersArkRif> getAplSistemaVersArkRifs() {
        return this.aplSistemaVersArkRifs;
    }

    public void setAplSistemaVersArkRifs(List<AplSistemaVersArkRif> aplSistemaVersArkRifs) {
        this.aplSistemaVersArkRifs = aplSistemaVersArkRifs;
    }

    // bi-directional many-to-one association to OrgServizioErog
    @OneToMany(mappedBy = "aplSistemaVersante")
    public List<OrgServizioErog> getOrgServizioErogs() {
        return this.orgServizioErogs;
    }

    public void setOrgServizioErogs(List<OrgServizioErog> orgServizioErogs) {
        this.orgServizioErogs = orgServizioErogs;
    }

    public OrgServizioErog addOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().add(orgServizioErog);
        orgServizioErog.setAplSistemaVersante(this);

        return orgServizioErog;
    }

    public OrgServizioErog removeOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().remove(orgServizioErog);
        orgServizioErog.setAplSistemaVersante(null);

        return orgServizioErog;
    }

    // bi-directional many-to-one association to AplSistemaVersanteUserRef
    @OneToMany(mappedBy = "aplSistemaVersante")
    public List<AplSistemaVersanteUserRef> getAplSistemaVersanteUserRefs() {
        return this.aplSistemaVersanteUserRefs;
    }

    public void setAplSistemaVersanteUserRefs(List<AplSistemaVersanteUserRef> aplSistemaVersanteUserRefs) {
        this.aplSistemaVersanteUserRefs = aplSistemaVersanteUserRefs;
    }

    // bi-directional many-to-one association to UsrUser
    @OneToMany(mappedBy = "aplSistemaVersante")
    public List<UsrUser> getUsrUsers() {
        return this.usrUsers;
    }

    public void setUsrUsers(List<UsrUser> usrUsers) {
        this.usrUsers = usrUsers;
    }

    public UsrUser addUsrUser(UsrUser usrUser) {
        getUsrUsers().add(usrUser);
        usrUser.setAplSistemaVersante(this);

        return usrUser;
    }

    public UsrUser removeUsrUser(UsrUser usrUser) {
        getUsrUsers().remove(usrUser);
        usrUser.setAplSistemaVersante(null);

        return usrUser;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Column(name = "DS_NOTE")
    public String getDsNote() {
        return this.dsNote;
    }

    public void setDsNote(String dsNote) {
        this.dsNote = dsNote;
    }

}
