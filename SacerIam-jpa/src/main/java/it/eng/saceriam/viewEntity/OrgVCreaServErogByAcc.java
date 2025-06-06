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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the ORG_V_CREA_SERV_EROG_BY_ACC database table.
 */
@Entity
@Table(name = "ORG_V_CREA_SERV_EROG_BY_ACC")
public class OrgVCreaServErogByAcc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date dtErog;

    private String flPagamento;

    private BigDecimal idSistemaVersante;

    private BigDecimal idTariffa;

    private BigDecimal idTariffaAaAccordo;

    private BigDecimal idTariffaAccordo;

    private BigDecimal idTipoServizio;

    private BigDecimal imValoreTariffa;

    private BigDecimal aaAnnoAccordo;

    public OrgVCreaServErogByAcc() {
	// document why this constructor is empty
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EROG")
    public Date getDtErog() {
	return this.dtErog;
    }

    public void setDtErog(Date dtErog) {
	this.dtErog = dtErog;
    }

    @Column(name = "FL_PAGAMENTO", columnDefinition = "char(1)")
    public String getFlPagamento() {
	return this.flPagamento;
    }

    public void setFlPagamento(String flPagamento) {
	this.flPagamento = flPagamento;
    }

    @Column(name = "ID_SISTEMA_VERSANTE")
    public BigDecimal getIdSistemaVersante() {
	return this.idSistemaVersante;
    }

    public void setIdSistemaVersante(BigDecimal idSistemaVersante) {
	this.idSistemaVersante = idSistemaVersante;
    }

    @Column(name = "ID_TARIFFA")
    public BigDecimal getIdTariffa() {
	return this.idTariffa;
    }

    public void setIdTariffa(BigDecimal idTariffa) {
	this.idTariffa = idTariffa;
    }

    @Column(name = "ID_TARIFFA_AA_ACCORDO")
    public BigDecimal getIdTariffaAaAccordo() {
	return this.idTariffaAaAccordo;
    }

    public void setIdTariffaAaAccordo(BigDecimal idTariffaAaAccordo) {
	this.idTariffaAaAccordo = idTariffaAaAccordo;
    }

    @Column(name = "ID_TARIFFA_ACCORDO")
    public BigDecimal getIdTariffaAccordo() {
	return this.idTariffaAccordo;
    }

    public void setIdTariffaAccordo(BigDecimal idTariffaAccordo) {
	this.idTariffaAccordo = idTariffaAccordo;
    }

    @Column(name = "ID_TIPO_SERVIZIO")
    public BigDecimal getIdTipoServizio() {
	return this.idTipoServizio;
    }

    public void setIdTipoServizio(BigDecimal idTipoServizio) {
	this.idTipoServizio = idTipoServizio;
    }

    @Column(name = "IM_VALORE_TARIFFA")
    public BigDecimal getImValoreTariffa() {
	return this.imValoreTariffa;
    }

    public void setImValoreTariffa(BigDecimal imValoreTariffa) {
	this.imValoreTariffa = imValoreTariffa;
    }

    @Column(name = "AA_ANNO_ACCORDO")
    public BigDecimal getAaAnnoAccordo() {
	return this.aaAnnoAccordo;
    }

    public void setAaAnnoAccordo(BigDecimal aaAnnoAccordo) {
	this.aaAnnoAccordo = aaAnnoAccordo;
    }

    private OrgVCreaServErogByAccId orgVCreaServErogByAccId;

    @EmbeddedId()
    public OrgVCreaServErogByAccId getOrgVCreaServErogByAccId() {
	return orgVCreaServErogByAccId;
    }

    public void setOrgVCreaServErogByAccId(OrgVCreaServErogByAccId orgVCreaServErogByAccId) {
	this.orgVCreaServErogByAccId = orgVCreaServErogByAccId;
    }
}
