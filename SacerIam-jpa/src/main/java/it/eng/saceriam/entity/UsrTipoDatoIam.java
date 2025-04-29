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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the USR_TIPO_DATO_IAM database table.
 */
@Entity
@Table(name = "USR_TIPO_DATO_IAM")
public class UsrTipoDatoIam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idTipoDatoIam;

    private String dsTipoDato;

    private BigDecimal idTipoDatoApplic;

    private String nmTipoDato;

    private List<UsrAbilDati> usrAbilDatis = new ArrayList<>();

    private List<UsrDichAbilDati> usrDichAbilDatis = new ArrayList<>();

    private AplClasseTipoDato aplClasseTipoDato;

    private UsrOrganizIam usrOrganizIam;

    public UsrTipoDatoIam() {
    }

    @Id
    @Column(name = "ID_TIPO_DATO_IAM")
    @GenericGenerator(name = "SUSR_TIPO_DATO_IAM_ID_TIPO_DATO_IAM_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SUSR_TIPO_DATO_IAM"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUSR_TIPO_DATO_IAM_ID_TIPO_DATO_IAM_GENERATOR")
    public Long getIdTipoDatoIam() {
	return this.idTipoDatoIam;
    }

    public void setIdTipoDatoIam(Long idTipoDatoIam) {
	this.idTipoDatoIam = idTipoDatoIam;
    }

    @Column(name = "DS_TIPO_DATO")
    public String getDsTipoDato() {
	return this.dsTipoDato;
    }

    public void setDsTipoDato(String dsTipoDato) {
	this.dsTipoDato = dsTipoDato;
    }

    @Column(name = "ID_TIPO_DATO_APPLIC")
    public BigDecimal getIdTipoDatoApplic() {
	return this.idTipoDatoApplic;
    }

    public void setIdTipoDatoApplic(BigDecimal idTipoDatoApplic) {
	this.idTipoDatoApplic = idTipoDatoApplic;
    }

    @Column(name = "NM_TIPO_DATO")
    public String getNmTipoDato() {
	return this.nmTipoDato;
    }

    public void setNmTipoDato(String nmTipoDato) {
	this.nmTipoDato = nmTipoDato;
    }

    // bi-directional many-to-one association to UsrDichAbilDati
    @OneToMany(mappedBy = "usrTipoDatoIam")
    public List<UsrDichAbilDati> getUsrDichAbilDatis() {
	return this.usrDichAbilDatis;
    }

    public void setUsrDichAbilDatis(List<UsrDichAbilDati> usrDichAbilDatis) {
	this.usrDichAbilDatis = usrDichAbilDatis;
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

    // bi-directional many-to-one association to UsrAbilDati
    @OneToMany(mappedBy = "usrTipoDatoIam")
    public List<UsrAbilDati> getUsrAbilDatis() {
	return this.usrAbilDatis;
    }

    public void setUsrAbilDatis(List<UsrAbilDati> usrAbilDatis) {
	this.usrAbilDatis = usrAbilDatis;
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
}
