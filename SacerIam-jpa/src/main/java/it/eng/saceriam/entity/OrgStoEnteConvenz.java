package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ORG_STO_ENTE_CONVENZ database table.
 *
 */
@Entity
@Table(name = "ORG_STO_ENTE_CONVENZ")
@NamedQuery(name = "OrgStoEnteConvenz.findAll", query = "SELECT o FROM OrgStoEnteConvenz o")
public class OrgStoEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idStoEnteConvenz;
    private String cdCapSedeLegale;
    private String cdEnteConvenz;
    private String cdFisc;
    private String cdNazioneSedeLegale;
    private String dlNote;
    private String dsCittaSedeLegale;
    private String dsViaSedeLegale;
    private Date dtFineVal;
    private Date dtIniVal;
    private java.math.BigDecimal idAmbitoTerrit;
    private java.math.BigDecimal idCategEnte;
    private java.math.BigDecimal idProvSedeLegale;
    private String nmEnteConvenz;
    private String tiCdEnteConvenz;
    private OrgEnteSiam orgEnteSiam;

    public OrgStoEnteConvenz() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_STO_ENTE_CONVENZ") // @SequenceGenerator(name =
                                                                           // "ORG_STO_ENTE_CONVENZ_IDSTOENTECONVENZ_GENERATOR",
                                                                           // sequenceName = "SORG_STO_ENTE_CONVENZ",
                                                                           // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_STO_ENTE_CONVENZ_IDSTOENTECONVENZ_GENERATOR")
    @Column(name = "ID_STO_ENTE_CONVENZ")
    public Long getIdStoEnteConvenz() {
        return this.idStoEnteConvenz;
    }

    public void setIdStoEnteConvenz(Long idStoEnteConvenz) {
        this.idStoEnteConvenz = idStoEnteConvenz;
    }

    @Column(name = "CD_CAP_SEDE_LEGALE")
    public String getCdCapSedeLegale() {
        return this.cdCapSedeLegale;
    }

    public void setCdCapSedeLegale(String cdCapSedeLegale) {
        this.cdCapSedeLegale = cdCapSedeLegale;
    }

    @Column(name = "CD_ENTE_CONVENZ")
    public String getCdEnteConvenz() {
        return this.cdEnteConvenz;
    }

    public void setCdEnteConvenz(String cdEnteConvenz) {
        this.cdEnteConvenz = cdEnteConvenz;
    }

    @Column(name = "CD_FISC")
    public String getCdFisc() {
        return this.cdFisc;
    }

    public void setCdFisc(String cdFisc) {
        this.cdFisc = cdFisc;
    }

    @Column(name = "CD_NAZIONE_SEDE_LEGALE")
    public String getCdNazioneSedeLegale() {
        return this.cdNazioneSedeLegale;
    }

    public void setCdNazioneSedeLegale(String cdNazioneSedeLegale) {
        this.cdNazioneSedeLegale = cdNazioneSedeLegale;
    }

    @Column(name = "DL_NOTE")
    public String getDlNote() {
        return this.dlNote;
    }

    public void setDlNote(String dlNote) {
        this.dlNote = dlNote;
    }

    @Column(name = "DS_CITTA_SEDE_LEGALE")
    public String getDsCittaSedeLegale() {
        return this.dsCittaSedeLegale;
    }

    public void setDsCittaSedeLegale(String dsCittaSedeLegale) {
        this.dsCittaSedeLegale = dsCittaSedeLegale;
    }

    @Column(name = "DS_VIA_SEDE_LEGALE")
    public String getDsViaSedeLegale() {
        return this.dsViaSedeLegale;
    }

    public void setDsViaSedeLegale(String dsViaSedeLegale) {
        this.dsViaSedeLegale = dsViaSedeLegale;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Column(name = "ID_AMBITO_TERRIT")
    public BigDecimal getIdAmbitoTerrit() {
        return this.idAmbitoTerrit;
    }

    public void setIdAmbitoTerrit(BigDecimal idAmbitoTerrit) {
        this.idAmbitoTerrit = idAmbitoTerrit;
    }

    @Column(name = "ID_CATEG_ENTE")
    public BigDecimal getIdCategEnte() {
        return this.idCategEnte;
    }

    public void setIdCategEnte(BigDecimal idCategEnte) {
        this.idCategEnte = idCategEnte;
    }

    @Column(name = "ID_PROV_SEDE_LEGALE")
    public BigDecimal getIdProvSedeLegale() {
        return this.idProvSedeLegale;
    }

    public void setIdProvSedeLegale(BigDecimal idProvSedeLegale) {
        this.idProvSedeLegale = idProvSedeLegale;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
        return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "TI_CD_ENTE_CONVENZ")
    public String getTiCdEnteConvenz() {
        return this.tiCdEnteConvenz;
    }

    public void setTiCdEnteConvenz(String tiCdEnteConvenz) {
        this.tiCdEnteConvenz = tiCdEnteConvenz;
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

}
