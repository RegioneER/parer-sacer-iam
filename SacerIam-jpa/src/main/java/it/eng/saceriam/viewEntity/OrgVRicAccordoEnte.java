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

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ORG_V_RIC_ACCORDO_ENTE database table.
 *
 */
@Entity
@Table(name = "ORG_V_RIC_ACCORDO_ENTE")
public class OrgVRicAccordoEnte implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal aaRepertorio;
    private String cdKeyRepertorio;
    private String cdRegistroRepertorio;
    private String cdTipoAccordo;
    private String cdTipoGestioneAccordo;
    private String dsNotaFatturazione;
    private Date dtDecAccordo;
    private Date dtDecAccordoInfo;
    private Date dtFineValidAccordo;
    private Date dtScadAccordo;
    private String flEsisteNotaFatturazione;
    private String flEsistonoGestAcc;
    private String flEsistonoSae;
    private String flEsistonoSue;
    private String flFasciaManuale;
    private String flRecesso;
    private BigDecimal idAccordoEnte;
    private BigDecimal idAmbienteEnteConvenz;
    private BigDecimal idEnteConvenz;
    private BigDecimal idTipoAccordo;
    private String idTipoGestioneAccordo;
    private BigDecimal idUserIamCor;
    private String nmAmbienteEnteConvenz;
    private String nmEnteConvenz;
    private String tiFasciaStandard;
    private String tiFasciaManuale;
    private String tiStatoAccordo;

    public OrgVRicAccordoEnte() {
    }

    public OrgVRicAccordoEnte(BigDecimal idAmbienteEnteConvenz, BigDecimal idEnteConvenz,
	    BigDecimal idAccordoEnte, String nmAmbienteEnteConvenz, String nmEnteConvenz,
	    String cdRegistroRepertorio, BigDecimal aaRepertorio, String cdKeyRepertorio,
	    BigDecimal idTipoAccordo, String cdTipoAccordo, Date dtDecAccordo,
	    Date dtFineValidAccordo, Date dtDecAccordoInfo, Date dtScadAccordo, String flRecesso,
	    String flEsistonoGestAcc, String idTipoGestioneAccordo, String cdTipoGestioneAccordo,
	    String flEsisteNotaFatturazione, String dsNotaFatturazione, String flFasciaManuale,
	    String flEsistonoSae, String flEsistonoSue, String tiFasciaStandard,
	    String tiFasciaManuale, String tiStatoAccordo) {
	this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
	this.idEnteConvenz = idEnteConvenz;
	this.idAccordoEnte = idAccordoEnte;
	this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
	this.nmEnteConvenz = nmEnteConvenz;
	this.cdRegistroRepertorio = cdRegistroRepertorio;
	this.aaRepertorio = aaRepertorio;
	this.cdKeyRepertorio = cdKeyRepertorio;
	this.idTipoAccordo = idTipoAccordo;
	this.cdTipoAccordo = cdTipoAccordo;
	this.dtDecAccordo = dtDecAccordo;
	this.dtFineValidAccordo = dtFineValidAccordo;
	this.dtDecAccordoInfo = dtDecAccordoInfo;
	this.dtScadAccordo = dtScadAccordo;
	this.flRecesso = flRecesso;
	this.cdTipoAccordo = cdTipoAccordo;
	this.dtFineValidAccordo = dtFineValidAccordo;
	this.flEsistonoGestAcc = flEsistonoGestAcc;
	this.idTipoGestioneAccordo = idTipoGestioneAccordo;
	this.cdTipoGestioneAccordo = cdTipoGestioneAccordo;
	this.flEsisteNotaFatturazione = flEsisteNotaFatturazione;
	this.flFasciaManuale = flFasciaManuale;
	this.dsNotaFatturazione = dsNotaFatturazione;
	this.flEsistonoSae = flEsistonoSae;
	this.flEsistonoSue = flEsistonoSue;
	this.tiFasciaStandard = tiFasciaStandard;
	this.tiFasciaManuale = tiFasciaManuale;
	this.tiStatoAccordo = tiStatoAccordo;
    }

    @Column(name = "AA_REPERTORIO")
    public BigDecimal getAaRepertorio() {
	return this.aaRepertorio;
    }

    public void setAaRepertorio(BigDecimal aaRepertorio) {
	this.aaRepertorio = aaRepertorio;
    }

    @Column(name = "CD_KEY_REPERTORIO")
    public String getCdKeyRepertorio() {
	return this.cdKeyRepertorio;
    }

    public void setCdKeyRepertorio(String cdKeyRepertorio) {
	this.cdKeyRepertorio = cdKeyRepertorio;
    }

    @Column(name = "CD_REGISTRO_REPERTORIO")
    public String getCdRegistroRepertorio() {
	return this.cdRegistroRepertorio;
    }

    public void setCdRegistroRepertorio(String cdRegistroRepertorio) {
	this.cdRegistroRepertorio = cdRegistroRepertorio;
    }

    @Column(name = "CD_TIPO_ACCORDO")
    public String getCdTipoAccordo() {
	return this.cdTipoAccordo;
    }

    public void setCdTipoAccordo(String cdTipoAccordo) {
	this.cdTipoAccordo = cdTipoAccordo;
    }

    @Column(name = "CD_TIPO_GESTIONE_ACCORDO")
    public String getCdTipoGestioneAccordo() {
	return this.cdTipoGestioneAccordo;
    }

    public void setCdTipoGestioneAccordo(String cdTipoGestioneAccordo) {
	this.cdTipoGestioneAccordo = cdTipoGestioneAccordo;
    }

    @Column(name = "DS_NOTA_FATTURAZIONE")
    public String getDsNotaFatturazione() {
	return this.dsNotaFatturazione;
    }

    public void setDsNotaFatturazione(String dsNotaFatturazione) {
	this.dsNotaFatturazione = dsNotaFatturazione;
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
    @Column(name = "DT_DEC_ACCORDO_INFO")
    public Date getDtDecAccordoInfo() {
	return this.dtDecAccordoInfo;
    }

    public void setDtDecAccordoInfo(Date dtDecAccordoInfo) {
	this.dtDecAccordoInfo = dtDecAccordoInfo;
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
    @Column(name = "DT_SCAD_ACCORDO")
    public Date getDtScadAccordo() {
	return this.dtScadAccordo;
    }

    public void setDtScadAccordo(Date dtScadAccordo) {
	this.dtScadAccordo = dtScadAccordo;
    }

    @Column(name = "FL_ESISTE_NOTA_FATTURAZIONE", columnDefinition = "char(1)")
    public String getFlEsisteNotaFatturazione() {
	return this.flEsisteNotaFatturazione;
    }

    public void setFlEsisteNotaFatturazione(String flEsisteNotaFatturazione) {
	this.flEsisteNotaFatturazione = flEsisteNotaFatturazione;
    }

    @Column(name = "FL_ESISTONO_GEST_ACC", columnDefinition = "char(1)")
    public String getFlEsistonoGestAcc() {
	return this.flEsistonoGestAcc;
    }

    public void setFlEsistonoGestAcc(String flEsistonoGestAcc) {
	this.flEsistonoGestAcc = flEsistonoGestAcc;
    }

    @Column(name = "FL_ESISTONO_SAE", columnDefinition = "char(1)")
    public String getFlEsistonoSae() {
	return this.flEsistonoSae;
    }

    public void setFlEsistonoSae(String flEsistonoSae) {
	this.flEsistonoSae = flEsistonoSae;
    }

    @Column(name = "FL_ESISTONO_SUE", columnDefinition = "char(1)")
    public String getFlEsistonoSue() {
	return this.flEsistonoSue;
    }

    public void setFlEsistonoSue(String flEsistonoSue) {
	this.flEsistonoSue = flEsistonoSue;
    }

    @Column(name = "FL_FASCIA_MANUALE", columnDefinition = "char(1)")
    public String getFlFasciaManuale() {
	return this.flFasciaManuale;
    }

    public void setFlFasciaManuale(String flFasciaManuale) {
	this.flFasciaManuale = flFasciaManuale;
    }

    @Column(name = "FL_RECESSO", columnDefinition = "char(1)")
    public String getFlRecesso() {
	return this.flRecesso;
    }

    public void setFlRecesso(String flRecesso) {
	this.flRecesso = flRecesso;
    }

    @Id
    @Column(name = "ID_ACCORDO_ENTE")
    public BigDecimal getIdAccordoEnte() {
	return this.idAccordoEnte;
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
	this.idAccordoEnte = idAccordoEnte;
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
	return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
	this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
	return this.idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
	this.idEnteConvenz = idEnteConvenz;
    }

    @Column(name = "ID_TIPO_ACCORDO")
    public BigDecimal getIdTipoAccordo() {
	return this.idTipoAccordo;
    }

    public void setIdTipoAccordo(BigDecimal idTipoAccordo) {
	this.idTipoAccordo = idTipoAccordo;
    }

    @Column(name = "ID_TIPO_GESTIONE_ACCORDO")
    public String getIdTipoGestioneAccordo() {
	return this.idTipoGestioneAccordo;
    }

    public void setIdTipoGestioneAccordo(String idTipoGestioneAccordo) {
	this.idTipoGestioneAccordo = idTipoGestioneAccordo;
    }

    @Id
    @Column(name = "ID_USER_IAM_COR")
    public BigDecimal getIdUserIamCor() {
	return this.idUserIamCor;
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

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
	return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
	this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "TI_FASCIA_STANDARD")
    public String getTiFasciaStandard() {
	return this.tiFasciaStandard;
    }

    public void setTiFasciaStandard(String tiFasciaStandard) {
	this.tiFasciaStandard = tiFasciaStandard;
    }

    @Column(name = "TI_FASCIA_MANUALE")
    public String getTiFasciaManuale() {
	return this.tiFasciaManuale;
    }

    public void setTiFasciaManuale(String tiFasciaManuale) {
	this.tiFasciaManuale = tiFasciaManuale;
    }

    @Column(name = "TI_STATO_ACCORDO")
    public String getTiStatoAccordo() {
	return this.tiStatoAccordo;
    }

    public void setTiStatoAccordo(String tiStatoAccordo) {
	this.tiStatoAccordo = tiStatoAccordo;
    }

}
