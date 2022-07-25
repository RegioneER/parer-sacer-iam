package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the ORG_V_RIC_ENTE_CONVENZ database table.
 *
 */
@Entity
@Table(name = "ORG_V_RIC_ENTE_CONVENZ")
@NamedQuery(name = "OrgVRicEnteConvenz.findAll", query = "SELECT a FROM OrgVRicEnteConvenz a")
public class OrgVRicEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;
    private String archivista;
    private String cdEnteConvenz;
    private String cdTipoAccordo;
    private Date dtCessazione;
    private Date dtIniVal;
    private Date dtRichModuloInfo;
    private Date dtDecAccordoInfo;
    private Date dtFineValidAccordo;
    private Date dtDecAccordo;
    private Date dtScadAccordo;
    private BigDecimal enteAttivo;
    private String flEsistonoGestAcc;
    private String flEsistonoModuli;
    private String flGestAccNoRisp;
    private String flNonConvenz;
    private String flRecesso;
    private String flChiuso;
    private BigDecimal idAmbienteEnteConvenz;
    private BigDecimal idAmbitoTerrit;
    private BigDecimal idCategEnte;
    private BigDecimal idEnteConserv;
    private BigDecimal idEnteConvenz;
    private BigDecimal idEnteGestore;
    private BigDecimal idTipoAccordo;
    private BigDecimal idUserIamArk;
    private BigDecimal idUserIamCor;
    private String nmEnteConvenz;
    private String tiEnteConvenz;
    private String tipoGestioneAccordo;
    private String tiStatoAccordo;

    public OrgVRicEnteConvenz() {
    }

    public OrgVRicEnteConvenz(BigDecimal idEnteConvenz, String nmEnteConvenz, BigDecimal idCategEnte,
            BigDecimal idAmbitoTerrit, Date dtIniVal, Date dtCessazione, String flEsistonoModuli, Date dtRichModuloInfo,
            String cdTipoAccordo, String archivista, BigDecimal enteAttivo, String flRecesso, String flChiuso,
            Date dtFineValidAccordo, Date dtScadAccordo, String flEsistonoGestAcc, String cdEnteConvenz,
            Date dtDecAccordo, Date dtDecAccordoInfo) {
        this.idEnteConvenz = idEnteConvenz;
        this.nmEnteConvenz = nmEnteConvenz;
        this.idCategEnte = idCategEnte;
        this.idAmbitoTerrit = idAmbitoTerrit;
        this.dtIniVal = dtIniVal;
        this.flEsistonoModuli = flEsistonoModuli;
        this.dtRichModuloInfo = dtRichModuloInfo;
        this.dtCessazione = dtCessazione;
        this.cdTipoAccordo = cdTipoAccordo;
        this.archivista = archivista;
        this.enteAttivo = enteAttivo;
        this.flRecesso = flRecesso;
        this.flChiuso = flChiuso;
        this.dtFineValidAccordo = dtFineValidAccordo;
        this.dtScadAccordo = dtScadAccordo;
        this.flEsistonoGestAcc = flEsistonoGestAcc;
        this.cdEnteConvenz = cdEnteConvenz;
        this.dtDecAccordo = dtDecAccordo;
        this.dtDecAccordoInfo = dtDecAccordoInfo;
    }

    @Column(name = "ARCHIVISTA")
    public String getArchivista() {
        return this.archivista;
    }

    public void setArchivista(String archivista) {
        this.archivista = archivista;
    }

    @Column(name = "CD_TIPO_ACCORDO")
    public String getCdTipoAccordo() {
        return this.cdTipoAccordo;
    }

    public void setCdTipoAccordo(String cdTipoAccordo) {
        this.cdTipoAccordo = cdTipoAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CESSAZIONE")
    public Date getDtCessazione() {
        return this.dtCessazione;
    }

    public void setDtCessazione(Date dtCessazione) {
        this.dtCessazione = dtCessazione;
    }

    @Column(name = "FL_ESISTONO_MODULI", columnDefinition = "char(1)")
    public String getFlEsistonoModuli() {
        return this.flEsistonoModuli;
    }

    public void setFlEsistonoModuli(String flEsistonoModuli) {
        this.flEsistonoModuli = flEsistonoModuli;
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
    @Column(name = "DT_DEC_ACCORDO")
    public Date getDtDecAccordo() {
        return this.dtDecAccordo;
    }

    public void setDtDecAccordo(Date dtDecAccordo) {
        this.dtDecAccordo = dtDecAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VALID_ACCORDO")
    public Date getDtFineValidAccordo() {
        return this.dtFineValidAccordo;
    }

    public void setDtFineValidAccordo(Date dtFineValidAccordo) {
        this.dtFineValidAccordo = dtFineValidAccordo;
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
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Column(name = "ENTE_ATTIVO", columnDefinition = "char")
    public BigDecimal getEnteAttivo() {
        return this.enteAttivo;
    }

    public void setEnteAttivo(BigDecimal enteAttivo) {
        this.enteAttivo = enteAttivo;
    }

    @Column(name = "FL_ESISTONO_GEST_ACC", columnDefinition = "char(1)")
    public String getFlEsistonoGestAcc() {
        return this.flEsistonoGestAcc;
    }

    public void setFlEsistonoGestAcc(String flEsistonoGestAcc) {
        this.flEsistonoGestAcc = flEsistonoGestAcc;
    }

    @Column(name = "FL_GEST_ACC_NO_RISP", columnDefinition = "char(1)")
    public String getFlGestAccNoRisp() {
        return this.flGestAccNoRisp;
    }

    public void setFlGestAccNoRisp(String flGestAccNoRisp) {
        this.flGestAccNoRisp = flGestAccNoRisp;
    }

    @Column(name = "FL_NON_CONVENZ", columnDefinition = "char(1)")
    public String getFlNonConvenz() {
        return this.flNonConvenz;
    }

    public void setFlNonConvenz(String flNonConvenz) {
        this.flNonConvenz = flNonConvenz;
    }

    @Column(name = "FL_RECESSO", columnDefinition = "char(1)")
    public String getFlRecesso() {
        return this.flRecesso;
    }

    public void setFlRecesso(String flRecesso) {
        this.flRecesso = flRecesso;
    }

    @Column(name = "FL_CHIUSO", columnDefinition = "char(1)")
    public String getFlChiuso() {
        return this.flChiuso;
    }

    public void setFlChiuso(String flChiuso) {
        this.flChiuso = flChiuso;
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
        return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
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

    @Column(name = "ID_ENTE_CONSERV")
    public BigDecimal getIdEnteConserv() {
        return this.idEnteConserv;
    }

    public void setIdEnteConserv(BigDecimal idEnteConserv) {
        this.idEnteConserv = idEnteConserv;
    }

    @Id
    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return this.idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    @Column(name = "ID_ENTE_GESTORE")
    public BigDecimal getIdEnteGestore() {
        return this.idEnteGestore;
    }

    public void setIdEnteGestore(BigDecimal idEnteGestore) {
        this.idEnteGestore = idEnteGestore;
    }

    @Column(name = "ID_TIPO_ACCORDO")
    public BigDecimal getIdTipoAccordo() {
        return this.idTipoAccordo;
    }

    public void setIdTipoAccordo(BigDecimal idTipoAccordo) {
        this.idTipoAccordo = idTipoAccordo;
    }

    @Column(name = "ID_USER_IAM_ARK")
    public BigDecimal getIdUserIamArk() {
        return this.idUserIamArk;
    }

    public void setIdUserIamArk(BigDecimal idUserIamArk) {
        this.idUserIamArk = idUserIamArk;
    }

    @Column(name = "ID_USER_IAM_COR")
    public BigDecimal getIdUserIamCor() {
        return idUserIamCor;
    }

    public void setIdUserIamCor(BigDecimal idUserIamCor) {
        this.idUserIamCor = idUserIamCor;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
        return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "TI_ENTE_CONVENZ")
    public String getTiEnteConvenz() {
        return this.tiEnteConvenz;
    }

    public void setTiEnteConvenz(String tiEnteConvenz) {
        this.tiEnteConvenz = tiEnteConvenz;
    }

    @Column(name = "TIPO_GESTIONE_ACCORDO")
    public String getTipoGestioneAccordo() {
        return this.tipoGestioneAccordo;
    }

    public void setTipoGestioneAccordo(String tipoGestioneAccordo) {
        this.tipoGestioneAccordo = tipoGestioneAccordo;
    }

    @Column(name = "TI_STATO_ACCORDO")
    public String getTiStatoAccordo() {
        return this.tiStatoAccordo;
    }

    public void setTiStatoAccordo(String tiStatoAccordo) {
        this.tiStatoAccordo = tiStatoAccordo;
    }

    @Column(name = "CD_ENTE_CONVENZ")
    public String getCdEnteConvenz() {
        return this.cdEnteConvenz;
    }

    public void setCdEnteConvenz(String cdEnteConvenz) {
        this.cdEnteConvenz = cdEnteConvenz;
    }
}
