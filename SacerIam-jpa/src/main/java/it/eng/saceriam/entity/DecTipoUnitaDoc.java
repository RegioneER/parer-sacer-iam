package it.eng.saceriam.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlID;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

/**
 * The persistent class for the DEC_TIPO_UNITA_DOC database table.
 *
 */
@Entity
@Cacheable(true)
@Table(name = "DEC_TIPO_UNITA_DOC", schema = "SACER")
public class DecTipoUnitaDoc implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTipoUnitaDoc;
    private String cdSerie;
    private String cdSerieDaCreare;
    private String dsSerieDaCreare;
    private String dsTipoSerieDaCreare;
    private String dsTipoUnitaDoc;
    // private Date dtFirstVers;
    private Date dtIstituz;
    private Date dtSoppres;
    // private String flCreaTipoSerieStandard;
    // private String flForzaCollegamento;
    private String nmTipoSerieDaCreare;
    private String nmTipoUnitaDoc;
    private String tiSaveFile;
    // private List<AroUnitaDoc> aroUnitaDocs = new ArrayList<>();
    // private List<DecAttribDatiSpec> decAttribDatiSpecs = new ArrayList<>();
    // private List<DecCriterioFiltroMultiplo> decCriterioFiltroMultiplos = new ArrayList<>();
    // private List<DecTipoSerieUd> decTipoSerieUds = new ArrayList<>();
    // private List<DecTipoStrutUnitaDoc> decTipoStrutUnitaDocs = new ArrayList<>();
    // private DecCategTipoUnitaDoc decCategTipoUnitaDoc;
    private OrgStrut orgStrut;
    // private List<DecTipoUnitaDocAmmesso> decTipoUnitaDocAmmessos = new ArrayList<>();
    // private List<DecXsdDatiSpec> decXsdDatiSpecs = new ArrayList<>();
    // private List<MonContaUdDocComp> monContaUdDocComps = new ArrayList<>();
    // private List<OrgRegolaValSubStrut> orgRegolaValSubStruts = new ArrayList<>();
    // private List<DecModelloTipoSerie> decModelloTipoSeries = new ArrayList<>();
    // private DecModelloTipoSerie decModelloTipoSerie;
    // private AplSistemaVersante aplSistemaVersante;
    private OrgTipoServizio orgTipoServizio;

    public DecTipoUnitaDoc() {
    }

    @Id
    // @SequenceGenerator(name = "DEC_TIPO_UNITA_DOC_IDTIPOUNITADOC_GENERATOR", sequenceName = "SDEC_TIPO_UNITA_DOC",
    // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEC_TIPO_UNITA_DOC_IDTIPOUNITADOC_GENERATOR")
    @Column(name = "ID_TIPO_UNITA_DOC")
    @XmlID
    public Long getIdTipoUnitaDoc() {
        return this.idTipoUnitaDoc;
    }

    public void setIdTipoUnitaDoc(Long idTipoUnitaDoc) {
        this.idTipoUnitaDoc = idTipoUnitaDoc;
    }

    @Column(name = "CD_SERIE")
    public String getCdSerie() {
        return this.cdSerie;
    }

    public void setCdSerie(String cdSerie) {
        this.cdSerie = cdSerie;
    }

    @Column(name = "CD_SERIE_DA_CREARE")
    public String getCdSerieDaCreare() {
        return this.cdSerieDaCreare;
    }

    public void setCdSerieDaCreare(String cdSerieDaCreare) {
        this.cdSerieDaCreare = cdSerieDaCreare;
    }

    @Column(name = "DS_SERIE_DA_CREARE")
    public String getDsSerieDaCreare() {
        return this.dsSerieDaCreare;
    }

    public void setDsSerieDaCreare(String dsSerieDaCreare) {
        this.dsSerieDaCreare = dsSerieDaCreare;
    }

    @Column(name = "DS_TIPO_SERIE_DA_CREARE")
    public String getDsTipoSerieDaCreare() {
        return this.dsTipoSerieDaCreare;
    }

    public void setDsTipoSerieDaCreare(String dsTipoSerieDaCreare) {
        this.dsTipoSerieDaCreare = dsTipoSerieDaCreare;
    }

    @Column(name = "DS_TIPO_UNITA_DOC")
    public String getDsTipoUnitaDoc() {
        return this.dsTipoUnitaDoc;
    }

    public void setDsTipoUnitaDoc(String dsTipoUnitaDoc) {
        this.dsTipoUnitaDoc = dsTipoUnitaDoc;
    }

    // @XmlTransient
    // @Temporal(TemporalType.TIMESTAMP)
    // @Column(name = "DT_FIRST_VERS")
    // public Date getDtFirstVers() {
    // return this.dtFirstVers;
    // }
    //
    // public void setDtFirstVers(Date dtFirstVers) {
    // this.dtFirstVers = dtFirstVers;
    // }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ISTITUZ")
    public Date getDtIstituz() {
        return this.dtIstituz;
    }

    public void setDtIstituz(Date dtIstituz) {
        this.dtIstituz = dtIstituz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SOPPRES")
    public Date getDtSoppres() {
        return this.dtSoppres;
    }

    public void setDtSoppres(Date dtSoppres) {
        this.dtSoppres = dtSoppres;
    }

    @Column(name = "NM_TIPO_SERIE_DA_CREARE")
    public String getNmTipoSerieDaCreare() {
        return this.nmTipoSerieDaCreare;
    }

    public void setNmTipoSerieDaCreare(String nmTipoSerieDaCreare) {
        this.nmTipoSerieDaCreare = nmTipoSerieDaCreare;
    }

    @Column(name = "NM_TIPO_UNITA_DOC")
    public String getNmTipoUnitaDoc() {
        return this.nmTipoUnitaDoc;
    }

    public void setNmTipoUnitaDoc(String nmTipoUnitaDoc) {
        this.nmTipoUnitaDoc = nmTipoUnitaDoc;
    }

    @Column(name = "TI_SAVE_FILE")
    public String getTiSaveFile() {
        return this.tiSaveFile;
    }

    public void setTiSaveFile(String tiSaveFile) {
        this.tiSaveFile = tiSaveFile;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_STRUT")
    @XmlInverseReference(mappedBy = "decTipoUnitaDocs")
    public OrgStrut getOrgStrut() {
        return this.orgStrut;
    }

    public void setOrgStrut(OrgStrut orgStrut) {
        this.orgStrut = orgStrut;
    }

    // bi-directional many-to-one association to OrgTipoServizio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_SERVIZIO")
    public OrgTipoServizio getOrgTipoServizio() {
        return this.orgTipoServizio;
    }

    public void setOrgTipoServizio(OrgTipoServizio orgTipoServizio) {
        this.orgTipoServizio = orgTipoServizio;
    }

}
