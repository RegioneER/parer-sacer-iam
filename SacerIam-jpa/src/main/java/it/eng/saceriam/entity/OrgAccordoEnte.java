package it.eng.saceriam.entity;

import it.eng.saceriam.entity.constraint.ConstOrgAccordoEnte.TiScopoAccordo;
import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_ACCORDO_ENTE database table.
 *
 */
@Entity
@Table(name = "ORG_ACCORDO_ENTE")
@NamedQuery(name = "OrgAccordoEnte.findAll", query = "SELECT o FROM OrgAccordoEnte o")
public class OrgAccordoEnte implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAccordoEnte;
    private BigDecimal aaDetermina;
    private BigDecimal aaRepertorio;
    private String cdCapitolo;
    private String cdCig;
    private String cdCoge;
    private String cdCup;
    private String cdDdt;
    private String cdKeyDetermina;
    private String cdKeyRepertorio;
    private String cdOda;
    private String cdRegistroDetermina;
    private String cdRegistroRepertorio;
    private String cdRifContab;
    private String cdUfe;
    private String dsAttoAccordo;
    private String dsFirmatarioEnte;
    private Date dtDetermina;
    private Date dtRichModuloInfo;
    private String dsNoteAccordo;
    private String dsUfe;
    private Date dtAttoAccordo;
    private Date dtDecAccordo;
    private Date dtDecAccordoInfo;
    private Date dtRegAccordo;
    private Date dtScadAccordo;
    private Date dtFineValidAccordo;
    private String flPagamento;
    private String flRecesso;
    private String flAccordoChiuso;
    private BigDecimal niAbitanti;
    private TiScopoAccordo tiScopoAccordo;
    private String dsNotaRecesso;
    private String dsNotaFatturazione;
    private String cdClienteFatturazione;
    private OrgCdIva orgCdIva;
    private OrgClasseEnteConvenz orgClasseEnteConvenz;
    private OrgEnteSiam orgEnteSiam;
    private OrgEnteSiam orgEnteSiamByIdEnteConvenzAmministratore;
    private OrgEnteSiam orgEnteSiamByIdEnteConvenzConserv;
    private OrgEnteSiam orgEnteSiamByIdEnteConvenzGestore;
    private OrgTariffario orgTariffario;
    private OrgTipoAccordo orgTipoAccordo;
    private List<OrgServizioErog> orgServizioErogs = new ArrayList<>();
    private List<OrgModuloInfoAccordo> orgModuloInfoAccordos = new ArrayList<>();
    private List<OrgGestioneAccordo> orgGestioneAccordos = new ArrayList<>();
    private List<OrgTariffaAccordo> orgTariffaAccordos = new ArrayList<>();
    private List<OrgAaAccordo> orgAaAccordos = new ArrayList<>();
    private BigDecimal niCluster;
    private BigDecimal niFasciaManuale;
    private BigDecimal niFasciaStandard;
    private BigDecimal niRefertiManuale;
    private BigDecimal niRefertiStandard;
    private BigDecimal niTipoUdManuale;
    private BigDecimal imAttivDocAmm;
    private BigDecimal imAttivDocSani;
    private BigDecimal niStudioDicomPrev;
    private BigDecimal niTipoUdStandard;
    private BigDecimal niStudioDicom;
    private String dsNotaAttivazione;
    private String dsNoteEnteAccordo;
    private String nmEnte;
    private String nmStrut;

    public OrgAccordoEnte() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_ACCORDO_ENTE") // @SequenceGenerator(name =
                                                                       // "ORG_ACCORDO_ENTE_IDACCORDOENTE_GENERATOR",
                                                                       // sequenceName = "SORG_ACCORDO_ENTE",
                                                                       // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_ACCORDO_ENTE_IDACCORDOENTE_GENERATOR")
    @Column(name = "ID_ACCORDO_ENTE")
    public Long getIdAccordoEnte() {
        return this.idAccordoEnte;
    }

    public void setIdAccordoEnte(Long idAccordoEnte) {
        this.idAccordoEnte = idAccordoEnte;
    }

    @Column(name = "AA_DETERMINA")
    public BigDecimal getAaDetermina() {
        return this.aaDetermina;
    }

    public void setAaDetermina(BigDecimal aaDetermina) {
        this.aaDetermina = aaDetermina;
    }

    @Column(name = "AA_REPERTORIO")
    public BigDecimal getAaRepertorio() {
        return this.aaRepertorio;
    }

    public void setAaRepertorio(BigDecimal aaRepertorio) {
        this.aaRepertorio = aaRepertorio;
    }

    @Column(name = "CD_CAPITOLO")
    public String getCdCapitolo() {
        return this.cdCapitolo;
    }

    public void setCdCapitolo(String cdCapitolo) {
        this.cdCapitolo = cdCapitolo;
    }

    @Column(name = "CD_CIG")
    public String getCdCig() {
        return this.cdCig;
    }

    public void setCdCig(String cdCig) {
        this.cdCig = cdCig;
    }

    @Column(name = "CD_COGE")
    public String getCdCoge() {
        return this.cdCoge;
    }

    public void setCdCoge(String cdCoge) {
        this.cdCoge = cdCoge;
    }

    @Column(name = "CD_CUP")
    public String getCdCup() {
        return this.cdCup;
    }

    public void setCdCup(String cdCup) {
        this.cdCup = cdCup;
    }

    @Column(name = "CD_DDT")
    public String getCdDdt() {
        return cdDdt;
    }

    public void setCdDdt(String cdDdt) {
        this.cdDdt = cdDdt;
    }

    @Column(name = "CD_ODA")
    public String getCdOda() {
        return cdOda;
    }

    public void setCdOda(String cdOda) {
        this.cdOda = cdOda;
    }

    @Column(name = "CD_KEY_DETERMINA")
    public String getCdKeyDetermina() {
        return cdKeyDetermina;
    }

    public void setCdKeyDetermina(String cdKeyDetermina) {
        this.cdKeyDetermina = cdKeyDetermina;
    }

    @Column(name = "CD_KEY_REPERTORIO")
    public String getCdKeyRepertorio() {
        return this.cdKeyRepertorio;
    }

    public void setCdKeyRepertorio(String cdKeyRepertorio) {
        this.cdKeyRepertorio = cdKeyRepertorio;
    }

    @Column(name = "CD_REGISTRO_DETERMINA")
    public String getCdRegistroDetermina() {
        return cdRegistroDetermina;
    }

    public void setCdRegistroDetermina(String cdRegistroDetermina) {
        this.cdRegistroDetermina = cdRegistroDetermina;
    }

    @Column(name = "CD_REGISTRO_REPERTORIO")
    public String getCdRegistroRepertorio() {
        return this.cdRegistroRepertorio;
    }

    public void setCdRegistroRepertorio(String cdRegistroRepertorio) {
        this.cdRegistroRepertorio = cdRegistroRepertorio;
    }

    @Column(name = "CD_RIF_CONTAB")
    public String getCdRifContab() {
        return this.cdRifContab;
    }

    public void setCdRifContab(String cdRifContab) {
        this.cdRifContab = cdRifContab;
    }

    @Column(name = "CD_UFE")
    public String getCdUfe() {
        return this.cdUfe;
    }

    public void setCdUfe(String cdUfe) {
        this.cdUfe = cdUfe;
    }

    @Column(name = "DS_ATTO_ACCORDO")
    public String getDsAttoAccordo() {
        return this.dsAttoAccordo;
    }

    public void setDsAttoAccordo(String dsAttoAccordo) {
        this.dsAttoAccordo = dsAttoAccordo;
    }

    @Column(name = "DS_FIRMATARIO_ENTE")
    public String getDsFirmatarioEnte() {
        return this.dsFirmatarioEnte;
    }

    public void setDsFirmatarioEnte(String dsFirmatarioEnte) {
        this.dsFirmatarioEnte = dsFirmatarioEnte;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_DETERMINA")
    public Date getDtDetermina() {
        return dtDetermina;
    }

    public void setDtDetermina(Date dtDetermina) {
        this.dtDetermina = dtDetermina;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_RICH_MODULO_INFO")
    public Date getDtRichModuloInfo() {
        return this.dtRichModuloInfo;
    }

    public void setDtRichModuloInfo(Date dtRichModuloInfo) {
        this.dtRichModuloInfo = dtRichModuloInfo;
    }

    @Column(name = "DS_NOTE_ACCORDO")
    public String getDsNoteAccordo() {
        return dsNoteAccordo;
    }

    public void setDsNoteAccordo(String dsNoteAccordo) {
        this.dsNoteAccordo = dsNoteAccordo;
    }

    @Column(name = "DS_UFE")
    public String getDsUfe() {
        return this.dsUfe;
    }

    public void setDsUfe(String dsUfe) {
        this.dsUfe = dsUfe;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ATTO_ACCORDO")
    public Date getDtAttoAccordo() {
        return this.dtAttoAccordo;
    }

    public void setDtAttoAccordo(Date dtAttoAccordo) {
        this.dtAttoAccordo = dtAttoAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_ACCORDO")
    public Date getDtRegAccordo() {
        return this.dtRegAccordo;
    }

    public void setDtRegAccordo(Date dtRegAccordo) {
        this.dtRegAccordo = dtRegAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_DEC_ACCORDO")
    public Date getDtDecAccordo() {
        return dtDecAccordo;
    }

    public void setDtDecAccordo(Date dtDecAccordo) {
        this.dtDecAccordo = dtDecAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_DEC_ACCORDO_INFO")
    public Date getDtDecAccordoInfo() {
        return dtDecAccordoInfo;
    }

    public void setDtDecAccordoInfo(Date dtDecAccordoInfo) {
        this.dtDecAccordoInfo = dtDecAccordoInfo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SCAD_ACCORDO")
    public Date getDtScadAccordo() {
        return this.dtScadAccordo;
    }

    public void setDtScadAccordo(Date dtScadAccordo) {
        this.dtScadAccordo = dtScadAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VALID_ACCORDO")
    public Date getDtFineValidAccordo() {
        return this.dtFineValidAccordo;
    }

    public void setDtFineValidAccordo(Date dtFineValidAccordo) {
        this.dtFineValidAccordo = dtFineValidAccordo;
    }

    @Column(name = "FL_PAGAMENTO", columnDefinition = "char(1)")
    public String getFlPagamento() {
        return this.flPagamento;
    }

    public void setFlPagamento(String flPagamento) {
        this.flPagamento = flPagamento;
    }

    @Column(name = "FL_RECESSO", columnDefinition = "char(1)")
    public String getFlRecesso() {
        return this.flRecesso;
    }

    public void setFlRecesso(String flRecesso) {
        this.flRecesso = flRecesso;
    }

    @Column(name = "FL_ACCORDO_CHIUSO", columnDefinition = "char(1)")
    public String getFlAccordoChiuso() {
        return this.flAccordoChiuso;
    }

    public void setFlAccordoChiuso(String flAccordoChiuso) {
        this.flAccordoChiuso = flAccordoChiuso;
    }

    @Column(name = "NI_ABITANTI")
    public BigDecimal getNiAbitanti() {
        return this.niAbitanti;
    }

    public void setNiAbitanti(BigDecimal niAbitanti) {
        this.niAbitanti = niAbitanti;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TI_SCOPO_ACCORDO")
    public TiScopoAccordo getTiScopoAccordo() {
        return this.tiScopoAccordo;
    }

    public void setTiScopoAccordo(TiScopoAccordo tiScopoAccordo) {
        this.tiScopoAccordo = tiScopoAccordo;
    }

    @Column(name = "DS_NOTA_RECESSO")
    public String getDsNotaRecesso() {
        return this.dsNotaRecesso;
    }

    public void setDsNotaRecesso(String dsNotaRecesso) {
        this.dsNotaRecesso = dsNotaRecesso;
    }

    @Column(name = "DS_NOTA_FATTURAZIONE")
    public String getDsNotaFatturazione() {
        return this.dsNotaFatturazione;
    }

    public void setDsNotaFatturazione(String dsNotaFatturazione) {
        this.dsNotaFatturazione = dsNotaFatturazione;
    }

    @Column(name = "CD_CLIENTE_FATTURAZIONE")
    public String getCdClienteFatturazione() {
        return this.cdClienteFatturazione;
    }

    public void setCdClienteFatturazione(String cdClienteFatturazione) {
        this.cdClienteFatturazione = cdClienteFatturazione;
    }

    // bi-directional many-to-one association to OrgCdIva
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CD_IVA")
    public OrgCdIva getOrgCdIva() {
        return this.orgCdIva;
    }

    public void setOrgCdIva(OrgCdIva orgCdIva) {
        this.orgCdIva = orgCdIva;
    }

    // bi-directional many-to-one association to OrgClasseEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLASSE_ENTE_CONVENZ")
    public OrgClasseEnteConvenz getOrgClasseEnteConvenz() {
        return this.orgClasseEnteConvenz;
    }

    public void setOrgClasseEnteConvenz(OrgClasseEnteConvenz orgClasseEnteConvenz) {
        this.orgClasseEnteConvenz = orgClasseEnteConvenz;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ")
    public OrgEnteSiam getOrgEnteSiam() {
        return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ_AMMINISTRATORE")
    public OrgEnteSiam getOrgEnteSiamByIdEnteConvenzAmministratore() {
        return this.orgEnteSiamByIdEnteConvenzAmministratore;
    }

    public void setOrgEnteSiamByIdEnteConvenzAmministratore(OrgEnteSiam orgEnteSiamByIdEnteConvenzAmministratore) {
        this.orgEnteSiamByIdEnteConvenzAmministratore = orgEnteSiamByIdEnteConvenzAmministratore;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ_CONSERV")
    public OrgEnteSiam getOrgEnteSiamByIdEnteConvenzConserv() {
        return this.orgEnteSiamByIdEnteConvenzConserv;
    }

    public void setOrgEnteSiamByIdEnteConvenzConserv(OrgEnteSiam orgEnteSiamByIdEnteConvenzConserv) {
        this.orgEnteSiamByIdEnteConvenzConserv = orgEnteSiamByIdEnteConvenzConserv;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ_GESTORE")
    public OrgEnteSiam getOrgEnteSiamByIdEnteConvenzGestore() {
        return this.orgEnteSiamByIdEnteConvenzGestore;
    }

    public void setOrgEnteSiamByIdEnteConvenzGestore(OrgEnteSiam orgEnteSiamByIdEnteConvenzGestore) {
        this.orgEnteSiamByIdEnteConvenzGestore = orgEnteSiamByIdEnteConvenzGestore;
    }

    // bi-directional many-to-one association to OrgTariffario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TARIFFARIO")
    public OrgTariffario getOrgTariffario() {
        return this.orgTariffario;
    }

    public void setOrgTariffario(OrgTariffario orgTariffario) {
        this.orgTariffario = orgTariffario;
    }

    // bi-directional many-to-one association to OrgTipoAccordo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_ACCORDO")
    public OrgTipoAccordo getOrgTipoAccordo() {
        return this.orgTipoAccordo;
    }

    public void setOrgTipoAccordo(OrgTipoAccordo orgTipoAccordo) {
        this.orgTipoAccordo = orgTipoAccordo;
    }

    // bi-directional many-to-one association to OrgServizioErog
    @OneToMany(mappedBy = "orgAccordoEnte", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    public List<OrgServizioErog> getOrgServizioErogs() {
        return this.orgServizioErogs;
    }

    public void setOrgServizioErogs(List<OrgServizioErog> orgServizioErogs) {
        this.orgServizioErogs = orgServizioErogs;
    }

    public OrgServizioErog addOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().add(orgServizioErog);
        orgServizioErog.setOrgAccordoEnte(this);

        return orgServizioErog;
    }

    public OrgServizioErog removeOrgServizioErog(OrgServizioErog orgServizioErog) {
        getOrgServizioErogs().remove(orgServizioErog);
        orgServizioErog.setOrgAccordoEnte(null);

        return orgServizioErog;
    }

    // bi-directional many-to-one association to OrgModuloInfoAccordo
    @OneToMany(mappedBy = "orgAccordoEnte", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    public List<OrgModuloInfoAccordo> getOrgModuloInfoAccordos() {
        return this.orgModuloInfoAccordos;
    }

    public void setOrgModuloInfoAccordos(List<OrgModuloInfoAccordo> orgModuloInfoAccordos) {
        this.orgModuloInfoAccordos = orgModuloInfoAccordos;
    }

    public OrgModuloInfoAccordo addOrgModuloInfoAccordo(OrgModuloInfoAccordo orgModuloInfoAccordo) {
        getOrgModuloInfoAccordos().add(orgModuloInfoAccordo);
        orgModuloInfoAccordo.setOrgAccordoEnte(this);

        return orgModuloInfoAccordo;
    }

    public OrgModuloInfoAccordo removeOrgModuloInfoAccordo(OrgModuloInfoAccordo orgModuloInfoAccordo) {
        getOrgModuloInfoAccordos().remove(orgModuloInfoAccordo);
        orgModuloInfoAccordo.setOrgAccordoEnte(null);

        return orgModuloInfoAccordo;
    }

    // bi-directional many-to-one association to OrgGestioneAccordo
    @OneToMany(mappedBy = "orgAccordoEnte", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    public List<OrgGestioneAccordo> getOrgGestioneAccordos() {
        return this.orgGestioneAccordos;
    }

    public void setOrgGestioneAccordos(List<OrgGestioneAccordo> orgGestioneAccordos) {
        this.orgGestioneAccordos = orgGestioneAccordos;
    }

    public OrgGestioneAccordo addOrgGestioneAccordo(OrgGestioneAccordo orgGestioneAccordo) {
        getOrgGestioneAccordos().add(orgGestioneAccordo);
        orgGestioneAccordo.setOrgAccordoEnte(this);

        return orgGestioneAccordo;
    }

    public OrgGestioneAccordo removeOrgGestioneAccordo(OrgGestioneAccordo orgGestioneAccordo) {
        getOrgGestioneAccordos().remove(orgGestioneAccordo);
        orgGestioneAccordo.setOrgAccordoEnte(null);

        return orgGestioneAccordo;
    }

    // bi-directional many-to-one association to OrgTariffaAccordo
    @OneToMany(mappedBy = "orgAccordoEnte")
    public List<OrgTariffaAccordo> getOrgTariffaAccordos() {
        return this.orgTariffaAccordos;
    }

    public void setOrgTariffaAccordos(List<OrgTariffaAccordo> orgTariffaAccordos) {
        this.orgTariffaAccordos = orgTariffaAccordos;
    }

    // bi-directional many-to-one association to OrgAaAccordo
    @OneToMany(mappedBy = "orgAccordoEnte")
    public List<OrgAaAccordo> getOrgAaAccordos() {
        return this.orgAaAccordos;
    }

    public void setOrgAaAccordos(List<OrgAaAccordo> orgAaAccordos) {
        this.orgAaAccordos = orgAaAccordos;
    }

    public OrgAaAccordo addOrgAaAccordo(OrgAaAccordo orgAaAccordo) {
        getOrgAaAccordos().add(orgAaAccordo);
        orgAaAccordo.setOrgAccordoEnte(this);

        return orgAaAccordo;
    }

    public OrgAaAccordo removeOrgAaAccordo(OrgAaAccordo orgAaAccordo) {
        getOrgAaAccordos().remove(orgAaAccordo);
        orgAaAccordo.setOrgAccordoEnte(null);

        return orgAaAccordo;
    }

    @Column(name = "NI_CLUSTER")
    public BigDecimal getNiCluster() {
        return this.niCluster;
    }

    public void setNiCluster(BigDecimal niCluster) {
        this.niCluster = niCluster;
    }

    @Column(name = "NI_FASCIA_MANUALE")
    public BigDecimal getNiFasciaManuale() {
        return this.niFasciaManuale;
    }

    public void setNiFasciaManuale(BigDecimal niFasciaManuale) {
        this.niFasciaManuale = niFasciaManuale;
    }

    @Column(name = "NI_FASCIA_STANDARD")
    public BigDecimal getNiFasciaStandard() {
        return this.niFasciaStandard;
    }

    public void setNiFasciaStandard(BigDecimal niFasciaStandard) {
        this.niFasciaStandard = niFasciaStandard;
    }

    @Column(name = "NI_REFERTI_MANUALE")
    public BigDecimal getNiRefertiManuale() {
        return this.niRefertiManuale;
    }

    public void setNiRefertiManuale(BigDecimal niRefertiManuale) {
        this.niRefertiManuale = niRefertiManuale;
    }

    @Column(name = "NI_REFERTI_STANDARD")
    public BigDecimal getNiRefertiStandard() {
        return this.niRefertiStandard;
    }

    public void setNiRefertiStandard(BigDecimal niRefertiStandard) {
        this.niRefertiStandard = niRefertiStandard;
    }

    @Column(name = "NI_TIPO_UD_MANUALE")
    public BigDecimal getNiTipoUdManuale() {
        return this.niTipoUdManuale;
    }

    public void setNiTipoUdManuale(BigDecimal niTipoUdManuale) {
        this.niTipoUdManuale = niTipoUdManuale;
    }

    @Column(name = "NI_TIPO_UD_STANDARD")
    public BigDecimal getNiTipoUdStandard() {
        return this.niTipoUdStandard;
    }

    public void setNiTipoUdStandard(BigDecimal niTipoUdStandard) {
        this.niTipoUdStandard = niTipoUdStandard;
    }

    @Column(name = "NI_STUDIO_DICOM")
    public BigDecimal getNiStudioDicom() {
        return this.niStudioDicom;
    }

    public void setNiStudioDicom(BigDecimal niStudioDicom) {
        this.niStudioDicom = niStudioDicom;
    }

    @Column(name = "DS_NOTA_ATTIVAZIONE")
    public String getDsNotaAttivazione() {
        return this.dsNotaAttivazione;
    }

    public void setDsNotaAttivazione(String dsNotaAttivazione) {
        this.dsNotaAttivazione = dsNotaAttivazione;
    }

    @Column(name = "DS_NOTE_ENTE_ACCORDO")
    public String getDsNoteEnteAccordo() {
        return this.dsNoteEnteAccordo;
    }

    public void setDsNoteEnteAccordo(String dsNoteEnteAccordo) {
        this.dsNoteEnteAccordo = dsNoteEnteAccordo;
    }

    @Column(name = "NM_ENTE")
    public String getNmEnte() {
        return nmEnte;
    }

    public void setNmEnte(String nmEnte) {
        this.nmEnte = nmEnte;
    }

    @Column(name = "NM_STRUT")
    public String getNmStrut() {
        return nmStrut;
    }

    public void setNmStrut(String nmStrut) {
        this.nmStrut = nmStrut;
    }

    @Column(name = "IM_ATTIV_DOC_AMM")
    public BigDecimal getImAttivDocAmm() {
        return imAttivDocAmm;
    }

    public void setImAttivDocAmm(BigDecimal imAttivDocAmm) {
        this.imAttivDocAmm = imAttivDocAmm;
    }

    @Column(name = "IM_ATTIV_DOC_SANI")
    public BigDecimal getImAttivDocSani() {
        return imAttivDocSani;
    }

    public void setImAttivDocSani(BigDecimal imAttivDocSani) {
        this.imAttivDocSani = imAttivDocSani;
    }

    @Column(name = "NI_STUDIO_DICOM_PREV")
    public BigDecimal getNiStudioDicomPrev() {
        return niStudioDicomPrev;
    }

    public void setNiStudioDicomPrev(BigDecimal niStudioDicomPrev) {
        this.niStudioDicomPrev = niStudioDicomPrev;
    }

    @PrePersist
    void preInsert() {
        if (this.tiScopoAccordo == null) {
            this.tiScopoAccordo = TiScopoAccordo.CONSERVAZIONE;
        }
        if (this.dsAttoAccordo == null) {
            this.dsAttoAccordo = "Da definire";
        }
        if (this.flAccordoChiuso == null) {
            this.flAccordoChiuso = "0";
        }
    }

    @PreUpdate
    void preUpdate() {
        if (this.dsAttoAccordo == null) {
            this.dsAttoAccordo = "Da definire";
        }
    }
}
