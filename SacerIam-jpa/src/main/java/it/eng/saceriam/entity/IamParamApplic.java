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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the IAM_PARAM_APPLIC database table.
 */
@Entity
@Table(name = "IAM_PARAM_APPLIC")
public class IamParamApplic implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idParamApplic;

    private String cdVersioneAppIni;

    private String cdVersioneAppFine;

    private String dsParamApplic;

    private String dsListaValoriAmmessi;

    private String flAppartAmbiente;

    private String flAppartApplic;

    private String flApparteEnte;

    private String nmParamApplic;

    private String tiGestioneParam;

    private String tiParamApplic;
    private String tiValoreParamApplic;
    private List<IamValoreParamApplic> iamValoreParamApplics = new ArrayList<>();

    public IamParamApplic() {
    }

    @Id
    @Column(name = "ID_PARAM_APPLIC")
    @GenericGenerator(name = "SIAM_PARAM_APPLIC_ID_PARAM_APPLIC_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SIAM_PARAM_APPLIC"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIAM_PARAM_APPLIC_ID_PARAM_APPLIC_GENERATOR")
    public Long getIdParamApplic() {
	return this.idParamApplic;
    }

    public void setIdParamApplic(Long idParamApplic) {
	this.idParamApplic = idParamApplic;
    }

    @Column(name = "CD_VERSIONE_APP_INI")
    public String getCdVersioneAppIni() {
	return this.cdVersioneAppIni;
    }

    public void setCdVersioneAppIni(String cdVersioneAppIni) {
	this.cdVersioneAppIni = cdVersioneAppIni;
    }

    @Column(name = "CD_VERSIONE_APP_FINE")
    public String getCdVersioneAppFine() {
	return this.cdVersioneAppFine;
    }

    public void setCdVersioneAppFine(String cdVersioneAppFine) {
	this.cdVersioneAppFine = cdVersioneAppFine;
    }

    @Column(name = "DS_PARAM_APPLIC")
    public String getDsParamApplic() {
	return this.dsParamApplic;
    }

    public void setDsParamApplic(String dsParamApplic) {
	this.dsParamApplic = dsParamApplic;
    }

    @Column(name = "DS_LISTA_VALORI_AMMESSI")
    public String getDsListaValoriAmmessi() {
	return this.dsListaValoriAmmessi;
    }

    public void setDsListaValoriAmmessi(String dsListaValoriAmmessi) {
	this.dsListaValoriAmmessi = dsListaValoriAmmessi;
    }

    @Column(name = "FL_APPART_AMBIENTE", columnDefinition = "char(1)")
    public String getFlAppartAmbiente() {
	return this.flAppartAmbiente;
    }

    public void setFlAppartAmbiente(String flAppartAmbiente) {
	this.flAppartAmbiente = flAppartAmbiente;
    }

    @Column(name = "FL_APPART_APPLIC", columnDefinition = "char(1)")
    public String getFlAppartApplic() {
	return this.flAppartApplic;
    }

    public void setFlAppartApplic(String flAppartApplic) {
	this.flAppartApplic = flAppartApplic;
    }

    @Column(name = "FL_APPARTE_ENTE", columnDefinition = "char(1)")
    public String getFlApparteEnte() {
	return this.flApparteEnte;
    }

    public void setFlApparteEnte(String flApparteEnte) {
	this.flApparteEnte = flApparteEnte;
    }

    @Column(name = "NM_PARAM_APPLIC")
    public String getNmParamApplic() {
	return this.nmParamApplic;
    }

    public void setNmParamApplic(String nmParamApplic) {
	this.nmParamApplic = nmParamApplic;
    }

    @Column(name = "TI_GESTIONE_PARAM")
    public String getTiGestioneParam() {
	return this.tiGestioneParam;
    }

    public void setTiGestioneParam(String tiGestioneParam) {
	this.tiGestioneParam = tiGestioneParam;
    }

    @Column(name = "TI_PARAM_APPLIC")
    public String getTiParamApplic() {
	return this.tiParamApplic;
    }

    public void setTiParamApplic(String tiParamApplic) {
	this.tiParamApplic = tiParamApplic;
    }

    // bi-directional many-to-one association to IamValoreParamApplic
    @OneToMany(mappedBy = "iamParamApplic")
    public List<IamValoreParamApplic> getIamValoreParamApplics() {
	return this.iamValoreParamApplics;
    }

    public void setIamValoreParamApplics(List<IamValoreParamApplic> iamValoreParamApplics) {
	this.iamValoreParamApplics = iamValoreParamApplics;
    }

    public IamValoreParamApplic addIamValoreParamApplic(IamValoreParamApplic iamValoreParamApplic) {
	getIamValoreParamApplics().add(iamValoreParamApplic);
	iamValoreParamApplic.setIamParamApplic(this);
	return iamValoreParamApplic;
    }

    public IamValoreParamApplic removeIamValoreParamApplic(
	    IamValoreParamApplic iamValoreParamApplic) {
	getIamValoreParamApplics().remove(iamValoreParamApplic);
	iamValoreParamApplic.setIamParamApplic(null);
	return iamValoreParamApplic;
    }

    // MEV26588
    @Column(name = "TI_VALORE_PARAM_APPLIC")
    public String getTiValoreParamApplic() {
	return tiValoreParamApplic;
    }

    public void setTiValoreParamApplic(String tiValoreParamApplic) {
	this.tiValoreParamApplic = tiValoreParamApplic;
    }

}
