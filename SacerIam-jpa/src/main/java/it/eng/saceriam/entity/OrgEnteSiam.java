/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteConvenz;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteNonConvenz;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteSiam;

/**
 * The persistent class for the ORG_ENTE_SIAM database table.
 */
@Entity
@Table(name = "ORG_ENTE_SIAM")
public class OrgEnteSiam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEnteSiam;

    private String cdCapSedeLegale;

    private String cdEnteConvenz;

    private String cdFisc;

    private String cdNazioneSedeLegale;
    private String cdUfe;
    private String dsCittaSedeLegale;

    private String dsNote;
    private String dsUfe;
    private String dsViaSedeLegale;

    private Date dtCessazione;

    private Date dtIniVal;

    private Date dtRichModuloInfo;
    private Date dtIniValAppartAmbiente;
    private Date dtFinValAppartAmbiente;
    private BigDecimal idAmbitoTerrit;

    private BigDecimal idCategEnte;

    private BigDecimal idProvSedeLegale;

    private String nmEnteSiam;

    private String tiCdEnteConvenz;
    private String tiModPagam;
    private TiEnteSiam tiEnte;

    private TiEnteConvenz tiEnteConvenz;

    private TiEnteNonConvenz tiEnteNonConvenz;

    private List<OrgAccordoEnte> orgAccordoEntes = new ArrayList<>();

    private List<OrgAccordoEnte> orgAccordoEnteByIdEnteConvenzAmministratores = new ArrayList<>();

    private List<OrgAccordoEnte> orgAccordoEnteByIdEnteConvenzConservs = new ArrayList<>();

    private List<OrgAccordoEnte> orgAccordoEnteByIdEnteConvenzGestores = new ArrayList<>();

    private List<OrgEnteArkRif> orgEnteArkRifs = new ArrayList<>();
    private OrgCdIva orgCdIva;
    private OrgEnteSiam orgEnteSiamByIdEnteConvenzNuovo;

    private OrgEnteSiam orgEnteSiamByIdEnteConvenzCreazione;

    private OrgEnteSiam orgEnteSiamByIdEnteProdutCorrisp;

    private OrgAmbienteEnteConvenz orgAmbienteEnteConvenz;

    private List<OrgEnteSiam> orgEnteSiamByIdEnteConvenzNuovos = new ArrayList<>();

    private List<OrgEnteSiam> orgEnteSiamByIdEnteConvenzCreaziones = new ArrayList<>();

    private List<OrgEnteSiam> orgEnteSiamByIdEnteProdutCorrisps = new ArrayList<>();

    private List<OrgEnteConvenzOrg> orgEnteConvenzOrgs = new ArrayList<>();

    private List<OrgEnteUserRif> orgEnteUserRifs = new ArrayList<>();

    private List<OrgFatturaEnte> orgFatturaEntes = new ArrayList<>();

    private List<OrgStoEnteConvenz> orgStoEnteConvenzs = new ArrayList<>();

    private List<UsrAbilEnteSiam> usrAbilEnteSiams = new ArrayList<>();

    private List<UsrDichAbilEnteConvenz> usrDichAbilEnteConvenzs = new ArrayList<>();

    private List<UsrRichGestUser> usrRichGestUsers = new ArrayList<>();

    private List<UsrUser> usrUsers = new ArrayList<>();

    private List<IamValoreParamApplic> iamValoreParamApplics = new ArrayList<>();

    private List<OrgAmbienteEnteConvenz> orgAmbienteEnteConvenzByIdEnteConservs = new ArrayList<>();

    private List<OrgAmbienteEnteConvenz> orgAmbienteEnteConvenzByIdEnteGestores = new ArrayList<>();

    private List<OrgSuptEsternoEnteConvenz> orgSuptEsternoEnteConvenzByIdEnteFornitEsts = new ArrayList<>();

    private List<OrgSuptEsternoEnteConvenz> orgSuptEsternoEnteConvenzByIdEnteProduts = new ArrayList<>();

    private List<OrgAccordoVigil> orgAccordoVigilByIdEnteConservs = new ArrayList<>();

    private List<OrgAccordoVigil> orgAccordoVigilByIdEnteOrganoVigils = new ArrayList<>();

    private List<OrgVigilEnteProdut> orgVigilEnteProduts = new ArrayList<>();

    private List<AplSistemaVersante> aplSistemaVersantes = new ArrayList<>();

    private List<OrgCollegEntiConvenz> orgCollegEntiConvenzs = new ArrayList<>();

    private List<OrgAppartCollegEnti> orgAppartCollegEntis = new ArrayList<>();

    private List<OrgModuloInfoAccordo> orgModuloInfoAccordos;

    private List<OrgDiscipStrut> orgDiscipStruts;
    private List<OrgStoEnteAmbienteConvenz> orgStoEnteAmbienteConvenzs;

    public OrgEnteSiam() {
    }

    @Id
    @Column(name = "ID_ENTE_SIAM")
    @GenericGenerator(name = "SORG_ENTE_SIAM_ID_ENTE_SIAM_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_ENTE_SIAM"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_ENTE_SIAM_ID_ENTE_SIAM_GENERATOR")
    public Long getIdEnteSiam() {
	return this.idEnteSiam;
    }

    public void setIdEnteSiam(Long idEnteSiam) {
	this.idEnteSiam = idEnteSiam;
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

    @Column(name = "DS_CITTA_SEDE_LEGALE")
    public String getDsCittaSedeLegale() {
	return this.dsCittaSedeLegale;
    }

    public void setDsCittaSedeLegale(String dsCittaSedeLegale) {
	this.dsCittaSedeLegale = dsCittaSedeLegale;
    }

    @Column(name = "DS_NOTE")
    public String getDsNote() {
	return this.dsNote;
    }

    public void setDsNote(String dsNote) {
	this.dsNote = dsNote;
    }

    @Column(name = "DS_VIA_SEDE_LEGALE")
    public String getDsViaSedeLegale() {
	return this.dsViaSedeLegale;
    }

    public void setDsViaSedeLegale(String dsViaSedeLegale) {
	this.dsViaSedeLegale = dsViaSedeLegale;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_CESSAZIONE")
    public Date getDtCessazione() {
	return this.dtCessazione;
    }

    public void setDtCessazione(Date dtCessazione) {
	this.dtCessazione = dtCessazione;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
	return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
	this.dtIniVal = dtIniVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_RICH_MODULO_INFO")
    public Date getDtRichModuloInfo() {
	return this.dtRichModuloInfo;
    }

    public void setDtRichModuloInfo(Date dtRichModuloInfo) {
	this.dtRichModuloInfo = dtRichModuloInfo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL_APPART_AMBIENTE")
    public Date getDtIniValAppartAmbiente() {
	return this.dtIniValAppartAmbiente;
    }

    public void setDtIniValAppartAmbiente(Date dtIniValAppartAmbiente) {
	this.dtIniValAppartAmbiente = dtIniValAppartAmbiente;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_VAL_APPART_AMBIENTE")
    public Date getDtFinValAppartAmbiente() {
	return this.dtFinValAppartAmbiente;
    }

    public void setDtFinValAppartAmbiente(Date dtFinValAppartAmbiente) {
	this.dtFinValAppartAmbiente = dtFinValAppartAmbiente;
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

    @Column(name = "NM_ENTE_SIAM")
    public String getNmEnteSiam() {
	return this.nmEnteSiam;
    }

    public void setNmEnteSiam(String nmEnteSiam) {
	this.nmEnteSiam = nmEnteSiam;
    }

    @Column(name = "TI_CD_ENTE_CONVENZ")
    public String getTiCdEnteConvenz() {
	return this.tiCdEnteConvenz;
    }

    public void setTiCdEnteConvenz(String tiCdEnteConvenz) {
	this.tiCdEnteConvenz = tiCdEnteConvenz;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TI_ENTE", columnDefinition = "CHAR(18)")
    public TiEnteSiam getTiEnte() {
	return this.tiEnte;
    }

    public void setTiEnte(TiEnteSiam tiEnte) {
	this.tiEnte = tiEnte;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TI_ENTE_CONVENZ")
    public TiEnteConvenz getTiEnteConvenz() {
	return this.tiEnteConvenz;
    }

    public void setTiEnteConvenz(TiEnteConvenz tiEnteConvenz) {
	this.tiEnteConvenz = tiEnteConvenz;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TI_ENTE_NON_CONVENZ")
    public TiEnteNonConvenz getTiEnteNonConvenz() {
	return this.tiEnteNonConvenz;
    }

    public void setTiEnteNonConvenz(TiEnteNonConvenz tiEnteNonConvenz) {
	this.tiEnteNonConvenz = tiEnteNonConvenz;
    }

    @Column(name = "CD_UFE")
    public String getCdUfe() {
	return this.cdUfe;
    }

    public void setCdUfe(String cdUfe) {
	this.cdUfe = cdUfe;
    }

    @Column(name = "DS_UFE")
    public String getDsUfe() {
	return this.dsUfe;
    }

    public void setDsUfe(String dsUfe) {
	this.dsUfe = dsUfe;
    }

    @Column(name = "TI_MOD_PAGAM")
    public String getTiModPagam() {
	return tiModPagam;
    }

    public void setTiModPagam(String tiModPagam) {
	this.tiModPagam = tiModPagam;
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

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<OrgAccordoEnte> getOrgAccordoEntes() {
	return this.orgAccordoEntes;
    }

    public void setOrgAccordoEntes(List<OrgAccordoEnte> orgAccordoEntes) {
	this.orgAccordoEntes = orgAccordoEntes;
    }

    public OrgAccordoEnte addOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
	getOrgAccordoEntes().add(orgAccordoEnte);
	orgAccordoEnte.setOrgEnteSiam(this);
	return orgAccordoEnte;
    }

    public OrgAccordoEnte removeOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
	getOrgAccordoEntes().remove(orgAccordoEnte);
	orgAccordoEnte.setOrgEnteSiam(null);
	return orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteConvenzAmministratore")
    public List<OrgAccordoEnte> getOrgAccordoEnteByIdEnteConvenzAmministratores() {
	return this.orgAccordoEnteByIdEnteConvenzAmministratores;
    }

    public void setOrgAccordoEnteByIdEnteConvenzAmministratores(
	    List<OrgAccordoEnte> orgAccordoEnteByIdEnteConvenzAmministratores) {
	this.orgAccordoEnteByIdEnteConvenzAmministratores = orgAccordoEnteByIdEnteConvenzAmministratores;
    }

    public OrgAccordoEnte addOrgAccordoEnteByIdEnteConvenzAmministratore(
	    OrgAccordoEnte orgAccordoEnteByIdEnteConvenzAmministratore) {
	getOrgAccordoEnteByIdEnteConvenzAmministratores()
		.add(orgAccordoEnteByIdEnteConvenzAmministratore);
	orgAccordoEnteByIdEnteConvenzAmministratore.setOrgEnteSiam(this);
	return orgAccordoEnteByIdEnteConvenzAmministratore;
    }

    public OrgAccordoEnte removeOrgAccordoEnteByIdEnteConvenzAmministratore(
	    OrgAccordoEnte orgAccordoEnteByIdEnteConvenzAmministratore) {
	getOrgAccordoEnteByIdEnteConvenzAmministratores()
		.remove(orgAccordoEnteByIdEnteConvenzAmministratore);
	orgAccordoEnteByIdEnteConvenzAmministratore.setOrgEnteSiam(null);
	return orgAccordoEnteByIdEnteConvenzAmministratore;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteConvenzConserv")
    public List<OrgAccordoEnte> getOrgAccordoEnteByIdEnteConvenzConservs() {
	return this.orgAccordoEnteByIdEnteConvenzConservs;
    }

    public void setOrgAccordoEnteByIdEnteConvenzConservs(
	    List<OrgAccordoEnte> orgAccordoEnteByIdEnteConvenzConservs) {
	this.orgAccordoEnteByIdEnteConvenzConservs = orgAccordoEnteByIdEnteConvenzConservs;
    }

    public OrgAccordoEnte addOrgAccordoEnteByIdEnteConvenzConserv(
	    OrgAccordoEnte orgAccordoEnteByIdEnteConvenzConserv) {
	getOrgAccordoEnteByIdEnteConvenzConservs().add(orgAccordoEnteByIdEnteConvenzConserv);
	orgAccordoEnteByIdEnteConvenzConserv.setOrgEnteSiam(this);
	return orgAccordoEnteByIdEnteConvenzConserv;
    }

    public OrgAccordoEnte removeOrgAccordoEnteByIdEnteConvenzConserv(
	    OrgAccordoEnte orgAccordoEnteByIdEnteConvenzConserv) {
	getOrgAccordoEnteByIdEnteConvenzConservs().remove(orgAccordoEnteByIdEnteConvenzConserv);
	orgAccordoEnteByIdEnteConvenzConserv.setOrgEnteSiam(null);
	return orgAccordoEnteByIdEnteConvenzConserv;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteConvenzGestore")
    public List<OrgAccordoEnte> getOrgAccordoEnteByIdEnteConvenzGestores() {
	return this.orgAccordoEnteByIdEnteConvenzGestores;
    }

    public void setOrgAccordoEnteByIdEnteConvenzGestores(
	    List<OrgAccordoEnte> orgAccordoEnteByIdEnteConvenzGestores) {
	this.orgAccordoEnteByIdEnteConvenzGestores = orgAccordoEnteByIdEnteConvenzGestores;
    }

    public OrgAccordoEnte addOrgAccordoEnteByIdEnteConvenzGestore(
	    OrgAccordoEnte orgAccordoEnteByIdEnteConvenzGestore) {
	getOrgAccordoEnteByIdEnteConvenzGestores().add(orgAccordoEnteByIdEnteConvenzGestore);
	orgAccordoEnteByIdEnteConvenzGestore.setOrgEnteSiam(this);
	return orgAccordoEnteByIdEnteConvenzGestore;
    }

    public OrgAccordoEnte removeOrgAccordoEnteByIdEnteConvenzGestore(
	    OrgAccordoEnte orgAccordoEnteByIdEnteConvenzGestore) {
	getOrgAccordoEnteByIdEnteConvenzGestores().remove(orgAccordoEnteByIdEnteConvenzGestore);
	orgAccordoEnteByIdEnteConvenzGestore.setOrgEnteSiam(null);
	return orgAccordoEnteByIdEnteConvenzGestore;
    }

    // bi-directional many-to-one association to OrgEnteArkRif
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<OrgEnteArkRif> getOrgEnteArkRifs() {
	return this.orgEnteArkRifs;
    }

    public void setOrgEnteArkRifs(List<OrgEnteArkRif> orgEnteArkRifs) {
	this.orgEnteArkRifs = orgEnteArkRifs;
    }

    public OrgEnteArkRif addOrgEnteArkRif(OrgEnteArkRif orgEnteArkRif) {
	getOrgEnteArkRifs().add(orgEnteArkRif);
	orgEnteArkRif.setOrgEnteSiam(this);
	return orgEnteArkRif;
    }

    public OrgEnteArkRif removeOrgEnteArkRif(OrgEnteArkRif orgEnteArkRif) {
	getOrgEnteArkRifs().remove(orgEnteArkRif);
	orgEnteArkRif.setOrgEnteSiam(null);
	return orgEnteArkRif;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ_NUOVO")
    public OrgEnteSiam getOrgEnteSiamByIdEnteConvenzNuovo() {
	return this.orgEnteSiamByIdEnteConvenzNuovo;
    }

    public void setOrgEnteSiamByIdEnteConvenzNuovo(OrgEnteSiam orgEnteSiamByIdEnteConvenzNuovo) {
	this.orgEnteSiamByIdEnteConvenzNuovo = orgEnteSiamByIdEnteConvenzNuovo;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteConvenzNuovo")
    public List<OrgEnteSiam> getOrgEnteSiamByIdEnteConvenzNuovos() {
	return this.orgEnteSiamByIdEnteConvenzNuovos;
    }

    public void setOrgEnteSiamByIdEnteConvenzNuovos(
	    List<OrgEnteSiam> orgEnteSiamByIdEnteConvenzNuovos) {
	this.orgEnteSiamByIdEnteConvenzNuovos = orgEnteSiamByIdEnteConvenzNuovos;
    }

    public OrgEnteSiam addOrgEnteSiamByIdEnteConvenzNuovo(
	    OrgEnteSiam orgEnteSiamByIdEnteConvenzNuovo) {
	getOrgEnteSiamByIdEnteConvenzNuovos().add(orgEnteSiamByIdEnteConvenzNuovo);
	orgEnteSiamByIdEnteConvenzNuovo.setOrgEnteSiamByIdEnteConvenzNuovo(this);
	return orgEnteSiamByIdEnteConvenzNuovo;
    }

    public OrgEnteSiam removeOrgEnteSiamByIdEnteConvenzNuovo(
	    OrgEnteSiam orgEnteSiamByIdEnteConvenzNuovo) {
	getOrgEnteSiamByIdEnteConvenzNuovos().remove(orgEnteSiamByIdEnteConvenzNuovo);
	orgEnteSiamByIdEnteConvenzNuovo.setOrgEnteSiamByIdEnteConvenzNuovo(null);
	return orgEnteSiamByIdEnteConvenzNuovo;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ_CREAZIONE")
    public OrgEnteSiam getOrgEnteSiamByIdEnteConvenzCreazione() {
	return this.orgEnteSiamByIdEnteConvenzCreazione;
    }

    public void setOrgEnteSiamByIdEnteConvenzCreazione(
	    OrgEnteSiam orgEnteSiamByIdEnteConvenzCreazione) {
	this.orgEnteSiamByIdEnteConvenzCreazione = orgEnteSiamByIdEnteConvenzCreazione;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteConvenzCreazione")
    public List<OrgEnteSiam> getOrgEnteSiamByIdEnteConvenzCreaziones() {
	return this.orgEnteSiamByIdEnteConvenzCreaziones;
    }

    public void setOrgEnteSiamByIdEnteConvenzCreaziones(
	    List<OrgEnteSiam> orgEnteSiamByIdEnteConvenzCreaziones) {
	this.orgEnteSiamByIdEnteConvenzCreaziones = orgEnteSiamByIdEnteConvenzCreaziones;
    }

    public OrgEnteSiam addOrgEnteSiamByIdEnteConvenzCreazione(
	    OrgEnteSiam orgEnteSiamByIdEnteConvenzCreazione) {
	getOrgEnteSiamByIdEnteConvenzCreaziones().add(orgEnteSiamByIdEnteConvenzCreazione);
	orgEnteSiamByIdEnteConvenzCreazione.setOrgEnteSiamByIdEnteConvenzCreazione(this);
	return orgEnteSiamByIdEnteConvenzCreazione;
    }

    public OrgEnteSiam removeOrgEnteSiamByIdEnteConvenzCreazione(
	    OrgEnteSiam orgEnteSiamByIdEnteConvenzCreazione) {
	getOrgEnteSiamByIdEnteConvenzCreaziones().remove(orgEnteSiamByIdEnteConvenzCreazione);
	orgEnteSiamByIdEnteConvenzCreazione.setOrgEnteSiamByIdEnteConvenzCreazione(null);
	return orgEnteSiamByIdEnteConvenzCreazione;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_PRODUT_CORRISP")
    public OrgEnteSiam getOrgEnteSiamByIdEnteProdutCorrisp() {
	return this.orgEnteSiamByIdEnteProdutCorrisp;
    }

    public void setOrgEnteSiamByIdEnteProdutCorrisp(OrgEnteSiam orgEnteSiamByIdEnteProdutCorrisp) {
	this.orgEnteSiamByIdEnteProdutCorrisp = orgEnteSiamByIdEnteProdutCorrisp;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteProdutCorrisp")
    public List<OrgEnteSiam> getOrgEnteSiamByIdEnteProdutCorrisps() {
	return this.orgEnteSiamByIdEnteProdutCorrisps;
    }

    public void setOrgEnteSiamByIdEnteProdutCorrisps(
	    List<OrgEnteSiam> orgEnteSiamByIdEnteProdutCorrisps) {
	this.orgEnteSiamByIdEnteProdutCorrisps = orgEnteSiamByIdEnteProdutCorrisps;
    }

    public OrgEnteSiam addOrgEnteSiamByIdEnteProdutCorrisp(
	    OrgEnteSiam orgEnteSiamByIdEnteProdutCorrisp) {
	getOrgEnteSiamByIdEnteProdutCorrisps().add(orgEnteSiamByIdEnteProdutCorrisp);
	orgEnteSiamByIdEnteProdutCorrisp.setOrgEnteSiamByIdEnteProdutCorrisp(this);
	return orgEnteSiamByIdEnteProdutCorrisp;
    }

    public OrgEnteSiam removeOrgEnteSiamByIdEnteProdutCorrisp(
	    OrgEnteSiam orgEnteSiamByIdEnteProdutCorrisp) {
	getOrgEnteSiamByIdEnteProdutCorrisps().remove(orgEnteSiamByIdEnteProdutCorrisp);
	orgEnteSiamByIdEnteProdutCorrisp.setOrgEnteSiamByIdEnteProdutCorrisp(null);
	return orgEnteSiamByIdEnteProdutCorrisp;
    }

    // bi-directional many-to-one association to OrgEnteConvenzOrg
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<OrgEnteConvenzOrg> getOrgEnteConvenzOrgs() {
	return this.orgEnteConvenzOrgs;
    }

    public void setOrgEnteConvenzOrgs(List<OrgEnteConvenzOrg> orgEnteConvenzOrgs) {
	this.orgEnteConvenzOrgs = orgEnteConvenzOrgs;
    }

    public OrgEnteConvenzOrg addOrgEnteConvenzOrg(OrgEnteConvenzOrg orgEnteConvenzOrg) {
	getOrgEnteConvenzOrgs().add(orgEnteConvenzOrg);
	orgEnteConvenzOrg.setOrgEnteSiam(this);
	return orgEnteConvenzOrg;
    }

    public OrgEnteConvenzOrg removeOrgEnteConvenzOrg(OrgEnteConvenzOrg orgEnteConvenzOrg) {
	getOrgEnteConvenzOrgs().remove(orgEnteConvenzOrg);
	orgEnteConvenzOrg.setOrgEnteSiam(null);
	return orgEnteConvenzOrg;
    }

    // bi-directional many-to-one association to OrgEnteUserRif
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<OrgEnteUserRif> getOrgEnteUserRifs() {
	return this.orgEnteUserRifs;
    }

    public void setOrgEnteUserRifs(List<OrgEnteUserRif> orgEnteUserRifs) {
	this.orgEnteUserRifs = orgEnteUserRifs;
    }

    // bi-directional many-to-one association to OrgAmbienteEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public OrgAmbienteEnteConvenz getOrgAmbienteEnteConvenz() {
	return this.orgAmbienteEnteConvenz;
    }

    public void setOrgAmbienteEnteConvenz(OrgAmbienteEnteConvenz orgAmbienteEnteConvenz) {
	this.orgAmbienteEnteConvenz = orgAmbienteEnteConvenz;
    }

    // bi-directional many-to-one association to OrgFatturaEnte
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<OrgFatturaEnte> getOrgFatturaEntes() {
	return this.orgFatturaEntes;
    }

    public void setOrgFatturaEntes(List<OrgFatturaEnte> orgFatturaEntes) {
	this.orgFatturaEntes = orgFatturaEntes;
    }

    public OrgFatturaEnte addOrgFatturaEnte(OrgFatturaEnte orgFatturaEnte) {
	getOrgFatturaEntes().add(orgFatturaEnte);
	orgFatturaEnte.setOrgEnteSiam(this);
	return orgFatturaEnte;
    }

    public OrgFatturaEnte removeOrgFatturaEnte(OrgFatturaEnte orgFatturaEnte) {
	getOrgFatturaEntes().remove(orgFatturaEnte);
	orgFatturaEnte.setOrgEnteSiam(null);
	return orgFatturaEnte;
    }

    // bi-directional many-to-one association to OrgStoEnteConvenz
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<OrgStoEnteConvenz> getOrgStoEnteConvenzs() {
	return this.orgStoEnteConvenzs;
    }

    public void setOrgStoEnteConvenzs(List<OrgStoEnteConvenz> orgStoEnteConvenzs) {
	this.orgStoEnteConvenzs = orgStoEnteConvenzs;
    }

    public OrgStoEnteConvenz addOrgStoEnteConvenz(OrgStoEnteConvenz orgStoEnteConvenz) {
	getOrgStoEnteConvenzs().add(orgStoEnteConvenz);
	orgStoEnteConvenz.setOrgEnteSiam(this);
	return orgStoEnteConvenz;
    }

    public OrgStoEnteConvenz removeOrgStoEnteConvenz(OrgStoEnteConvenz orgStoEnteConvenz) {
	getOrgStoEnteConvenzs().remove(orgStoEnteConvenz);
	orgStoEnteConvenz.setOrgEnteSiam(null);
	return orgStoEnteConvenz;
    }

    // bi-directional many-to-one association to UsrAbilEnteSiam
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<UsrAbilEnteSiam> getUsrAbilEnteSiams() {
	return this.usrAbilEnteSiams;
    }

    public void setUsrAbilEnteSiams(List<UsrAbilEnteSiam> usrAbilEnteSiams) {
	this.usrAbilEnteSiams = usrAbilEnteSiams;
    }

    // bi-directional many-to-one association to UsrDichAbilEnteConvenz
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<UsrDichAbilEnteConvenz> getUsrDichAbilEnteConvenzs() {
	return this.usrDichAbilEnteConvenzs;
    }

    public void setUsrDichAbilEnteConvenzs(List<UsrDichAbilEnteConvenz> usrDichAbilEnteConvenzs) {
	this.usrDichAbilEnteConvenzs = usrDichAbilEnteConvenzs;
    }

    // bi-directional many-to-one association to UsrRichGestUser
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<UsrRichGestUser> getUsrRichGestUsers() {
	return this.usrRichGestUsers;
    }

    public void setUsrRichGestUsers(List<UsrRichGestUser> usrRichGestUsers) {
	this.usrRichGestUsers = usrRichGestUsers;
    }

    // bi-directional many-to-one association to UsrUser
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<UsrUser> getUsrUsers() {
	return this.usrUsers;
    }

    public void setUsrUsers(List<UsrUser> usrUsers) {
	this.usrUsers = usrUsers;
    }

    // bi-directional many-to-one association to IamValoreParamApplic
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<IamValoreParamApplic> getIamValoreParamApplics() {
	return this.iamValoreParamApplics;
    }

    public void setIamValoreParamApplics(List<IamValoreParamApplic> iamValoreParamApplics) {
	this.iamValoreParamApplics = iamValoreParamApplics;
    }

    public IamValoreParamApplic addIamValoreParamApplic(IamValoreParamApplic iamValoreParamApplic) {
	getIamValoreParamApplics().add(iamValoreParamApplic);
	iamValoreParamApplic.setOrgEnteSiam(this);
	return iamValoreParamApplic;
    }

    public IamValoreParamApplic removeIamValoreParamApplic(
	    IamValoreParamApplic iamValoreParamApplic) {
	getIamValoreParamApplics().remove(iamValoreParamApplic);
	iamValoreParamApplic.setOrgEnteSiam(null);
	return iamValoreParamApplic;
    }

    // bi-directional many-to-one association to OrgAmbienteEnteConvenz
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteConserv")
    public List<OrgAmbienteEnteConvenz> getOrgAmbienteEnteConvenzByIdEnteConservs() {
	return this.orgAmbienteEnteConvenzByIdEnteConservs;
    }

    public void setOrgAmbienteEnteConvenzByIdEnteConservs(
	    List<OrgAmbienteEnteConvenz> orgAmbienteEnteConvenzByIdEnteConservs) {
	this.orgAmbienteEnteConvenzByIdEnteConservs = orgAmbienteEnteConvenzByIdEnteConservs;
    }

    public OrgAmbienteEnteConvenz addOrgAmbienteEnteConvenzByIdEnteConserv(
	    OrgAmbienteEnteConvenz orgAmbienteEnteConvenzByIdEnteConserv) {
	getOrgAmbienteEnteConvenzByIdEnteConservs().add(orgAmbienteEnteConvenzByIdEnteConserv);
	orgAmbienteEnteConvenzByIdEnteConserv.setOrgEnteSiamByIdEnteConserv(this);
	return orgAmbienteEnteConvenzByIdEnteConserv;
    }

    public OrgAmbienteEnteConvenz removeOrgAmbienteEnteConvenzByIdEnteConserv(
	    OrgAmbienteEnteConvenz orgAmbienteEnteConvenzByIdEnteConserv) {
	getOrgAmbienteEnteConvenzByIdEnteConservs().remove(orgAmbienteEnteConvenzByIdEnteConserv);
	orgAmbienteEnteConvenzByIdEnteConserv.setOrgEnteSiamByIdEnteConserv(null);
	return orgAmbienteEnteConvenzByIdEnteConserv;
    }

    // bi-directional many-to-one association to OrgAmbienteEnteConvenz
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteGestore")
    public List<OrgAmbienteEnteConvenz> getOrgAmbienteEnteConvenzByIdEnteGestores() {
	return this.orgAmbienteEnteConvenzByIdEnteGestores;
    }

    public void setOrgAmbienteEnteConvenzByIdEnteGestores(
	    List<OrgAmbienteEnteConvenz> orgAmbienteEnteConvenzByIdEnteGestores) {
	this.orgAmbienteEnteConvenzByIdEnteGestores = orgAmbienteEnteConvenzByIdEnteGestores;
    }

    public OrgAmbienteEnteConvenz addOrgAmbienteEnteConvenzByIdEnteGestore(
	    OrgAmbienteEnteConvenz orgAmbienteEnteConvenzByIdEnteGestore) {
	getOrgAmbienteEnteConvenzByIdEnteGestores().add(orgAmbienteEnteConvenzByIdEnteGestore);
	orgAmbienteEnteConvenzByIdEnteGestore.setOrgEnteSiamByIdEnteGestore(this);
	return orgAmbienteEnteConvenzByIdEnteGestore;
    }

    public OrgAmbienteEnteConvenz removeOrgAmbienteEnteConvenzByIdEnteGestore(
	    OrgAmbienteEnteConvenz orgAmbienteEnteConvenzByIdEnteGestore) {
	getOrgAmbienteEnteConvenzByIdEnteGestores().remove(orgAmbienteEnteConvenzByIdEnteGestore);
	orgAmbienteEnteConvenzByIdEnteGestore.setOrgEnteSiamByIdEnteGestore(null);
	return orgAmbienteEnteConvenzByIdEnteGestore;
    }

    // bi-directional many-to-one association to OrgSuptEsternoEnteConvenz
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteFornitEst")
    public List<OrgSuptEsternoEnteConvenz> getOrgSuptEsternoEnteConvenzByIdEnteFornitEsts() {
	return this.orgSuptEsternoEnteConvenzByIdEnteFornitEsts;
    }

    public void setOrgSuptEsternoEnteConvenzByIdEnteFornitEsts(
	    List<OrgSuptEsternoEnteConvenz> orgSuptEsternoEnteConvenzByIdEnteFornitEsts) {
	this.orgSuptEsternoEnteConvenzByIdEnteFornitEsts = orgSuptEsternoEnteConvenzByIdEnteFornitEsts;
    }

    public OrgSuptEsternoEnteConvenz addOrgSuptEsternoEnteConvenzByIdEnteFornitEst(
	    OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenzByIdEnteFornitEst) {
	getOrgSuptEsternoEnteConvenzByIdEnteFornitEsts()
		.add(orgSuptEsternoEnteConvenzByIdEnteFornitEst);
	orgSuptEsternoEnteConvenzByIdEnteFornitEst.setOrgEnteSiamByIdEnteFornitEst(this);
	return orgSuptEsternoEnteConvenzByIdEnteFornitEst;
    }

    public OrgSuptEsternoEnteConvenz removeOrgSuptEsternoEnteConvenzByIdEnteFornitEst(
	    OrgSuptEsternoEnteConvenz orgSuptEsternoEnteByIdEnteFornitEst) {
	getOrgSuptEsternoEnteConvenzByIdEnteFornitEsts()
		.remove(orgSuptEsternoEnteByIdEnteFornitEst);
	orgSuptEsternoEnteByIdEnteFornitEst.setOrgEnteSiamByIdEnteFornitEst(null);
	return orgSuptEsternoEnteByIdEnteFornitEst;
    }

    // bi-directional many-to-one association to OrgSuptEsternoEnteConvenz
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteProdut")
    public List<OrgSuptEsternoEnteConvenz> getOrgSuptEsternoEnteConvenzByIdEnteProduts() {
	return this.orgSuptEsternoEnteConvenzByIdEnteProduts;
    }

    public void setOrgSuptEsternoEnteConvenzByIdEnteProduts(
	    List<OrgSuptEsternoEnteConvenz> orgSuptEsternoEnteConvenzByIdEnteProduts) {
	this.orgSuptEsternoEnteConvenzByIdEnteProduts = orgSuptEsternoEnteConvenzByIdEnteProduts;
    }

    public OrgSuptEsternoEnteConvenz addOrgSuptEsternoEnteConvenzByIdEnteProdut(
	    OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenzByIdEnteProdut) {
	getOrgSuptEsternoEnteConvenzByIdEnteProduts().add(orgSuptEsternoEnteConvenzByIdEnteProdut);
	orgSuptEsternoEnteConvenzByIdEnteProdut.setOrgEnteSiamByIdEnteProdut(this);
	return orgSuptEsternoEnteConvenzByIdEnteProdut;
    }

    public OrgSuptEsternoEnteConvenz removeOrgSuptEsternoEnteConvenzByIdEnteProdut(
	    OrgSuptEsternoEnteConvenz orgSuptEsternoEnteByIdEnteProdut) {
	getOrgSuptEsternoEnteConvenzByIdEnteProduts().remove(orgSuptEsternoEnteByIdEnteProdut);
	orgSuptEsternoEnteByIdEnteProdut.setOrgEnteSiamByIdEnteProdut(null);
	return orgSuptEsternoEnteByIdEnteProdut;
    }

    // bi-directional many-to-one association to OrgAccordoVigil
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteConserv")
    public List<OrgAccordoVigil> getOrgAccordoVigilByIdEnteConservs() {
	return this.orgAccordoVigilByIdEnteConservs;
    }

    public void setOrgAccordoVigilByIdEnteConservs(
	    List<OrgAccordoVigil> orgAccordoVigilByIdEnteConservs) {
	this.orgAccordoVigilByIdEnteConservs = orgAccordoVigilByIdEnteConservs;
    }

    public OrgAccordoVigil addOrgAccordoVigilByIdEnteConserv(
	    OrgAccordoVigil orgAccordoVigilByIdEnteConserv) {
	getOrgAccordoVigilByIdEnteConservs().add(orgAccordoVigilByIdEnteConserv);
	orgAccordoVigilByIdEnteConserv.setOrgEnteSiamByIdEnteConserv(this);
	return orgAccordoVigilByIdEnteConserv;
    }

    public OrgAccordoVigil removeOrgAccordoVigilByIdEnteConserv(
	    OrgAccordoVigil orgAccordoVigilByIdEnteConserv) {
	getOrgAccordoVigilByIdEnteConservs().remove(orgAccordoVigilByIdEnteConserv);
	orgAccordoVigilByIdEnteConserv.setOrgEnteSiamByIdEnteConserv(null);
	return orgAccordoVigilByIdEnteConserv;
    }

    // bi-directional many-to-one association to OrgAccordoVigil
    @OneToMany(mappedBy = "orgEnteSiamByIdEnteOrganoVigil")
    public List<OrgAccordoVigil> getOrgAccordoVigilByIdEnteOrganoVigils() {
	return this.orgAccordoVigilByIdEnteOrganoVigils;
    }

    public void setOrgAccordoVigilByIdEnteOrganoVigils(
	    List<OrgAccordoVigil> orgAccordoVigilByIdEnteOrganoVigils) {
	this.orgAccordoVigilByIdEnteOrganoVigils = orgAccordoVigilByIdEnteOrganoVigils;
    }

    public OrgAccordoVigil addOrgAccordoVigilByIdEnteOrganoVigil(
	    OrgAccordoVigil orgAccordoVigilByIdEnteOrganoVigil) {
	getOrgAccordoVigilByIdEnteOrganoVigils().add(orgAccordoVigilByIdEnteOrganoVigil);
	orgAccordoVigilByIdEnteOrganoVigil.setOrgEnteSiamByIdEnteOrganoVigil(this);
	return orgAccordoVigilByIdEnteOrganoVigil;
    }

    public OrgAccordoVigil removeOrgAccordoVigilByIdEnteOrganoVigil(
	    OrgAccordoVigil orgAccordoVigilByIdEnteOrganoVigil) {
	getOrgAccordoVigilByIdEnteOrganoVigils().remove(orgAccordoVigilByIdEnteOrganoVigil);
	orgAccordoVigilByIdEnteOrganoVigil.setOrgEnteSiamByIdEnteOrganoVigil(null);
	return orgAccordoVigilByIdEnteOrganoVigil;
    }

    // bi-directional many-to-one association to OrgVigilEnteProdut
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<OrgVigilEnteProdut> getOrgVigilEnteProduts() {
	return this.orgVigilEnteProduts;
    }

    public void setOrgVigilEnteProduts(List<OrgVigilEnteProdut> orgVigilEnteProduts) {
	this.orgVigilEnteProduts = orgVigilEnteProduts;
    }

    public OrgVigilEnteProdut addOrgVigilEnteProdut(OrgVigilEnteProdut orgVigilEnteProdut) {
	getOrgVigilEnteProduts().add(orgVigilEnteProdut);
	orgVigilEnteProdut.setOrgEnteSiam(this);
	return orgVigilEnteProdut;
    }

    public OrgVigilEnteProdut removeOrgVigilEnteProdut(OrgVigilEnteProdut orgVigilEnteProdut) {
	getOrgVigilEnteProduts().remove(orgVigilEnteProdut);
	orgVigilEnteProdut.setOrgEnteSiam(null);
	return orgVigilEnteProdut;
    }

    // bi-directional many-to-one association to AplSistemaVersante
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<AplSistemaVersante> getAplSistemaVersantes() {
	return this.aplSistemaVersantes;
    }

    public void setAplSistemaVersantes(List<AplSistemaVersante> aplSistemaVersantes) {
	this.aplSistemaVersantes = aplSistemaVersantes;
    }

    public AplSistemaVersante addAplSistemaVersante(AplSistemaVersante aplSistemaVersante) {
	getAplSistemaVersantes().add(aplSistemaVersante);
	aplSistemaVersante.setOrgEnteSiam(this);
	return aplSistemaVersante;
    }

    public AplSistemaVersante removeAplSistemaVersante(AplSistemaVersante aplSistemaVersante) {
	getAplSistemaVersantes().remove(aplSistemaVersante);
	aplSistemaVersante.setOrgEnteSiam(null);
	return aplSistemaVersante;
    }

    // bi-directional many-to-one association to OrgCollegEntiConvenz
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<OrgCollegEntiConvenz> getOrgCollegEntiConvenzs() {
	return this.orgCollegEntiConvenzs;
    }

    public void setOrgCollegEntiConvenzs(List<OrgCollegEntiConvenz> orgCollegEntiConvenzs) {
	this.orgCollegEntiConvenzs = orgCollegEntiConvenzs;
    }

    public OrgCollegEntiConvenz addOrgCollegEntiConvenz(OrgCollegEntiConvenz orgCollegEntiConvenz) {
	getOrgCollegEntiConvenzs().add(orgCollegEntiConvenz);
	orgCollegEntiConvenz.setOrgEnteSiam(this);
	return orgCollegEntiConvenz;
    }

    public OrgCollegEntiConvenz removeOrgCollegEntiConvenz(
	    OrgCollegEntiConvenz orgCollegEntiConvenz) {
	getOrgCollegEntiConvenzs().remove(orgCollegEntiConvenz);
	orgCollegEntiConvenz.setOrgEnteSiam(null);
	return orgCollegEntiConvenz;
    }

    // bi-directional many-to-one association to OrgAppartCollegEnti
    @OneToMany(mappedBy = "orgEnteSiam")
    public List<OrgAppartCollegEnti> getOrgAppartCollegEntis() {
	return this.orgAppartCollegEntis;
    }

    public void setOrgAppartCollegEntis(List<OrgAppartCollegEnti> orgAppartCollegEntis) {
	this.orgAppartCollegEntis = orgAppartCollegEntis;
    }

    public OrgAppartCollegEnti addOrgAppartCollegEnti(OrgAppartCollegEnti orgAppartCollegEnti) {
	getOrgAppartCollegEntis().add(orgAppartCollegEnti);
	orgAppartCollegEnti.setOrgEnteSiam(this);
	return orgAppartCollegEnti;
    }

    public OrgAppartCollegEnti removeOrgAppartCollegEnti(OrgAppartCollegEnti orgAppartCollegEnti) {
	getOrgAppartCollegEntis().remove(orgAppartCollegEnti);
	orgAppartCollegEnti.setOrgEnteSiam(null);
	return orgAppartCollegEnti;
    }

    // bi-directional many-to-one association to OrgAppartCollegEnti
    @OneToMany(mappedBy = "orgEnteConvenz", cascade = {
	    CascadeType.PERSIST, CascadeType.REMOVE })
    public List<OrgModuloInfoAccordo> getOrgModuloInfoAccordos() {
	return this.orgModuloInfoAccordos;
    }

    public void setOrgModuloInfoAccordos(List<OrgModuloInfoAccordo> orgModuloInfoAccordos) {
	this.orgModuloInfoAccordos = orgModuloInfoAccordos;
    }

    public OrgModuloInfoAccordo addOrgModuloInfoAccordo(OrgModuloInfoAccordo orgModuloInfoAccordo) {
	getOrgModuloInfoAccordos().add(orgModuloInfoAccordo);
	orgModuloInfoAccordo.setOrgEnteConvenz(this);
	return orgModuloInfoAccordo;
    }

    public OrgModuloInfoAccordo removeOrgModuloInfoAccordo(
	    OrgModuloInfoAccordo orgModuloInfoAccordo) {
	getOrgModuloInfoAccordos().remove(orgModuloInfoAccordo);
	orgModuloInfoAccordo.setOrgEnteConvenz(null);
	return orgModuloInfoAccordo;
    }

    // bi-directional many-to-one association to OrgDiscipStrut
    @OneToMany(mappedBy = "orgEnteConvenz", cascade = {
	    CascadeType.PERSIST, CascadeType.REMOVE })
    public List<OrgDiscipStrut> getOrgDiscipStruts() {
	return this.orgDiscipStruts;
    }

    public void setOrgDiscipStruts(List<OrgDiscipStrut> orgDiscipStruts) {
	this.orgDiscipStruts = orgDiscipStruts;
    }

    public OrgDiscipStrut addOrgDiscipStrut(OrgDiscipStrut orgDiscipStrut) {
	getOrgDiscipStruts().add(orgDiscipStrut);
	orgDiscipStrut.setOrgEnteConvenz(this);
	return orgDiscipStrut;
    }

    public OrgDiscipStrut removeOrgDiscipStrut(OrgDiscipStrut orgDiscipStrut) {
	getOrgDiscipStruts().remove(orgDiscipStrut);
	orgDiscipStrut.setOrgEnteConvenz(null);
	return orgDiscipStrut;
    }

    // bi-directional many-to-one association to OrgStoEnteAmbienteConvenz
    @OneToMany(mappedBy = "orgEnteConvenz")
    public List<OrgStoEnteAmbienteConvenz> getOrgStoEnteAmbienteConvenzs() {
	return this.orgStoEnteAmbienteConvenzs;
    }

    public void setOrgStoEnteAmbienteConvenzs(
	    List<OrgStoEnteAmbienteConvenz> orgStoEnteAmbienteConvenzs) {
	this.orgStoEnteAmbienteConvenzs = orgStoEnteAmbienteConvenzs;
    }

    @PrePersist
    void preInsert() {
	if (this.tiEnte == null) {
	    this.tiEnte = TiEnteSiam.CONVENZIONATO;
	}
	if (this.tiEnteConvenz == null) {
	    this.tiEnteConvenz = TiEnteConvenz.PRODUTTORE;
	}
    }
}
