package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_APPLIC database table.
 *
 */
@Entity
@Table(name = "APL_APPLIC")
@NamedQuery(name = "AplApplic.findAll", query = "SELECT a FROM AplApplic a")
public class AplApplic implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idApplic;
    private String cdPswReplicaUser;
    private String cdVersioneComp;
    private String dsApplic;
    private String dsUrlReplicaUser;
    private String nmApplic;
    private String nmUserReplicaUser;
    private List<AplClasseTipoDato> aplClasseTipoDatos = new ArrayList<>();
    private List<AplCompSw> aplCompSws = new ArrayList<>();
    private List<AplEntryMenu> aplEntryMenus = new ArrayList<>();
    private List<AplHelpOnLine> aplHelpOnLines = new ArrayList<>();
    private List<AplNewsApplic> aplNewsApplics = new ArrayList<>();
    private List<AplPaginaWeb> aplPaginaWebs = new ArrayList<>();
    private List<AplServizioWeb> aplServizioWebs = new ArrayList<>();
    private List<AplTipoEvento> aplTipoEventos = new ArrayList<>();
    private List<AplTipoOggetto> aplTipoOggettos = new ArrayList<>();
    private List<AplTipoOrganiz> aplTipoOrganizs = new ArrayList<>();
    private List<LogUserDaReplic> logUserDaReplics = new ArrayList<>();
    private List<PrfUsoRuoloApplic> prfUsoRuoloApplics = new ArrayList<>();
    private List<UsrOrganizIam> usrOrganizIams = new ArrayList<>();
    private List<UsrUsoUserApplic> usrUsoUserApplics = new ArrayList<>();
    private List<SLLogLoginUser> sLLogLoginUsers = new ArrayList<>();
    private List<AplNotaRilascio> aplNotaRilascios = new ArrayList<>();

    public AplApplic() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_APPLIC") // @SequenceGenerator(name =
                                                                 // "APL_APPLIC_IDAPPLIC_GENERATOR", sequenceName =
                                                                 // "SAPL_APPLIC", allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_APPLIC_IDAPPLIC_GENERATOR")
    @Column(name = "ID_APPLIC")
    public Long getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(Long idApplic) {
        this.idApplic = idApplic;
    }

    @Column(name = "CD_PSW_REPLICA_USER")
    public String getCdPswReplicaUser() {
        return this.cdPswReplicaUser;
    }

    public void setCdPswReplicaUser(String cdPswReplicaUser) {
        this.cdPswReplicaUser = cdPswReplicaUser;
    }

    @Column(name = "CD_VERSIONE_COMP")
    public String getCdVersioneComp() {
        return cdVersioneComp;
    }

    public void setCdVersioneComp(String cdVersioneComp) {
        this.cdVersioneComp = cdVersioneComp;
    }

    @Column(name = "DS_APPLIC")
    public String getDsApplic() {
        return this.dsApplic;
    }

    public void setDsApplic(String dsApplic) {
        this.dsApplic = dsApplic;
    }

    @Column(name = "DS_URL_REPLICA_USER")
    public String getDsUrlReplicaUser() {
        return this.dsUrlReplicaUser;
    }

    public void setDsUrlReplicaUser(String dsUrlReplicaUser) {
        this.dsUrlReplicaUser = dsUrlReplicaUser;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_USER_REPLICA_USER")
    public String getNmUserReplicaUser() {
        return this.nmUserReplicaUser;
    }

    public void setNmUserReplicaUser(String nmUserReplicaUser) {
        this.nmUserReplicaUser = nmUserReplicaUser;
    }

    // bi-directional many-to-one association to AplClasseTipoDato
    @OneToMany(mappedBy = "aplApplic")
    public List<AplClasseTipoDato> getAplClasseTipoDatos() {
        return this.aplClasseTipoDatos;
    }

    public void setAplClasseTipoDatos(List<AplClasseTipoDato> aplClasseTipoDatos) {
        this.aplClasseTipoDatos = aplClasseTipoDatos;
    }

    // bi-directional many-to-one association to AplCompSw
    @OneToMany(mappedBy = "aplApplic")
    public List<AplCompSw> getAplCompSws() {
        return this.aplCompSws;
    }

    public void setAplCompSws(List<AplCompSw> aplCompSws) {
        this.aplCompSws = aplCompSws;
    }

    // bi-directional many-to-one association to AplEntryMenu
    @OneToMany(mappedBy = "aplApplic")
    public List<AplEntryMenu> getAplEntryMenus() {
        return this.aplEntryMenus;
    }

    public void setAplEntryMenus(List<AplEntryMenu> aplEntryMenus) {
        this.aplEntryMenus = aplEntryMenus;
    }

    // bi-directional many-to-one association to AplHelpOnLine
    @OneToMany(mappedBy = "aplApplic")
    public List<AplHelpOnLine> getAplHelpOnLines() {
        return this.aplHelpOnLines;
    }

    public void setAplHelpOnLines(List<AplHelpOnLine> aplHelpOnLines) {
        this.aplHelpOnLines = aplHelpOnLines;
    }

    // bi-directional many-to-one association to AplNewsApplic
    @OneToMany(mappedBy = "aplApplic")
    public List<AplNewsApplic> getAplNewsApplics() {
        return this.aplNewsApplics;
    }

    public void setAplNewsApplics(List<AplNewsApplic> aplNewsApplics) {
        this.aplNewsApplics = aplNewsApplics;
    }

    // bi-directional many-to-one association to AplPaginaWeb
    @OneToMany(mappedBy = "aplApplic")
    public List<AplPaginaWeb> getAplPaginaWebs() {
        return this.aplPaginaWebs;
    }

    public void setAplPaginaWebs(List<AplPaginaWeb> aplPaginaWebs) {
        this.aplPaginaWebs = aplPaginaWebs;
    }

    // bi-directional many-to-one association to AplServizioWeb
    @OneToMany(mappedBy = "aplApplic")
    public List<AplServizioWeb> getAplServizioWebs() {
        return this.aplServizioWebs;
    }

    public void setAplServizioWebs(List<AplServizioWeb> aplServizioWebs) {
        this.aplServizioWebs = aplServizioWebs;
    }

    // bi-directional many-to-one association to AplTipoEvento
    @OneToMany(mappedBy = "aplApplic")
    public List<AplTipoEvento> getAplTipoEventos() {
        return this.aplTipoEventos;
    }

    public void setAplTipoEventos(List<AplTipoEvento> aplTipoEventos) {
        this.aplTipoEventos = aplTipoEventos;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @OneToMany(mappedBy = "aplApplic")
    public List<AplTipoOggetto> getAplTipoOggettos() {
        return this.aplTipoOggettos;
    }

    public void setAplTipoOggettos(List<AplTipoOggetto> aplTipoOggettos) {
        this.aplTipoOggettos = aplTipoOggettos;
    }

    public AplTipoOggetto addAplTipoOggetto(AplTipoOggetto aplTipoOggetto) {
        getAplTipoOggettos().add(aplTipoOggetto);
        aplTipoOggetto.setAplApplic(this);

        return aplTipoOggetto;
    }

    public AplTipoOggetto removeAplTipoOggetto(AplTipoOggetto aplTipoOggetto) {
        getAplTipoOggettos().remove(aplTipoOggetto);
        aplTipoOggetto.setAplApplic(null);

        return aplTipoOggetto;
    }

    // bi-directional many-to-one association to AplTipoOrganiz
    @OneToMany(mappedBy = "aplApplic")
    public List<AplTipoOrganiz> getAplTipoOrganizs() {
        return this.aplTipoOrganizs;
    }

    public void setAplTipoOrganizs(List<AplTipoOrganiz> aplTipoOrganizs) {
        this.aplTipoOrganizs = aplTipoOrganizs;
    }

    // bi-directional many-to-one association to LogUserDaReplic
    @OneToMany(mappedBy = "aplApplic")
    public List<LogUserDaReplic> getLogUserDaReplics() {
        return this.logUserDaReplics;
    }

    public void setLogUserDaReplics(List<LogUserDaReplic> logUserDaReplics) {
        this.logUserDaReplics = logUserDaReplics;
    }

    // bi-directional many-to-one association to PrfUsoRuoloApplic
    @OneToMany(mappedBy = "aplApplic")
    public List<PrfUsoRuoloApplic> getPrfUsoRuoloApplics() {
        return this.prfUsoRuoloApplics;
    }

    public void setPrfUsoRuoloApplics(List<PrfUsoRuoloApplic> prfUsoRuoloApplics) {
        this.prfUsoRuoloApplics = prfUsoRuoloApplics;
    }

    // bi-directional many-to-one association to UsrOrganizIam
    @OneToMany(mappedBy = "aplApplic")
    public List<UsrOrganizIam> getUsrOrganizIams() {
        return this.usrOrganizIams;
    }

    public void setUsrOrganizIams(List<UsrOrganizIam> usrOrganizIams) {
        this.usrOrganizIams = usrOrganizIams;
    }

    // bi-directional many-to-one association to UsrUsoUserApplic
    @OneToMany(mappedBy = "aplApplic")
    public List<UsrUsoUserApplic> getUsrUsoUserApplics() {
        return this.usrUsoUserApplics;
    }

    public void setUsrUsoUserApplics(List<UsrUsoUserApplic> usrUsoUserApplics) {
        this.usrUsoUserApplics = usrUsoUserApplics;
    }

    @OneToMany(mappedBy = "aplApplic")
    public List<SLLogLoginUser> getsLLogLoginUsers() {
        return sLLogLoginUsers;
    }

    public void setsLLogLoginUsers(List<SLLogLoginUser> sLLogLoginUsers) {
        this.sLLogLoginUsers = sLLogLoginUsers;
    }

    // bi-directional many-to-one association to AplNotaRilascio
    @OneToMany(mappedBy = "aplApplic")
    public List<AplNotaRilascio> getAplNotaRilascios() {
        return this.aplNotaRilascios;
    }

    public void setAplNotaRilascios(List<AplNotaRilascio> aplNotaRilascios) {
        this.aplNotaRilascios = aplNotaRilascios;
    }

}
