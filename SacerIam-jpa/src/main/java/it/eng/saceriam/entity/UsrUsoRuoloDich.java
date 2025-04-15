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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the USR_USO_RUOLO_DICH database table.
 */
@Entity
@Table(name = "USR_USO_RUOLO_DICH")
public class UsrUsoRuoloDich implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idUsoRuoloDich;

    private PrfRuolo prfRuolo;

    private UsrDichAbilOrganiz usrDichAbilOrganiz;

    private UsrOrganizIam usrOrganizIam;

    private String tiScopoRuolo;

    public UsrUsoRuoloDich() {
    }

    @Id
    @Column(name = "ID_USO_RUOLO_DICH")
    @GenericGenerator(name = "SUSR_USO_RUOLO_DICH_ID_USO_RUOLO_DICH_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SUSR_USO_RUOLO_DICH"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUSR_USO_RUOLO_DICH_ID_USO_RUOLO_DICH_GENERATOR")
    public Long getIdUsoRuoloDich() {
	return this.idUsoRuoloDich;
    }

    public void setIdUsoRuoloDich(Long idUsoRuoloDich) {
	this.idUsoRuoloDich = idUsoRuoloDich;
    }

    // bi-directional many-to-one association to PrfRuolo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RUOLO")
    public PrfRuolo getPrfRuolo() {
	return this.prfRuolo;
    }

    public void setPrfRuolo(PrfRuolo prfRuolo) {
	this.prfRuolo = prfRuolo;
    }

    // bi-directional many-to-one association to UsrDichAbilOrganiz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DICH_ABIL_ORGANIZ")
    public UsrDichAbilOrganiz getUsrDichAbilOrganiz() {
	return this.usrDichAbilOrganiz;
    }

    public void setUsrDichAbilOrganiz(UsrDichAbilOrganiz usrDichAbilOrganiz) {
	this.usrDichAbilOrganiz = usrDichAbilOrganiz;
    }

    // bi-directional many-to-one association to PrfRuolo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGANIZ_IAM_RUOLO")
    public UsrOrganizIam getUsrOrganizIam() {
	return this.usrOrganizIam;
    }

    public void setUsrOrganizIam(UsrOrganizIam usrOrganizIam) {
	this.usrOrganizIam = usrOrganizIam;
    }

    @Column(name = "TI_SCOPO_RUOLO")
    public String getTiScopoRuolo() {
	return this.tiScopoRuolo;
    }

    public void setTiScopoRuolo(String tiScopoRuolo) {
	this.tiScopoRuolo = tiScopoRuolo;
    }
}
