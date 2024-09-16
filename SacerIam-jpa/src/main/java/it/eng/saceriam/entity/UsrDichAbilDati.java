/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.entity;

import java.io.Serializable;
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
 * The persistent class for the USR_DICH_ABIL_DATI database table.
 */
@Entity
@Table(name = "USR_DICH_ABIL_DATI")
public class UsrDichAbilDati implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idDichAbilDati;

    private String dsCausaleDich;

    private String tiScopoDichAbilDati;

    private List<UsrAbilDati> usrAbilDatis = new ArrayList<>();

    private AplClasseTipoDato aplClasseTipoDato;

    private OrgAppartCollegEnti orgAppartCollegEnti;

    private OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenz;

    private OrgVigilEnteProdut orgVigilEnteProdut;

    private UsrOrganizIam usrOrganizIam;

    private UsrTipoDatoIam usrTipoDatoIam;

    private UsrUsoUserApplic usrUsoUserApplic;

    public UsrDichAbilDati() {
    }

    @Id
    @Column(name = "ID_DICH_ABIL_DATI")
    @GenericGenerator(name = "SUSR_DICH_ABIL_DATI_ID_DICH_ABIL_DATI_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SUSR_DICH_ABIL_DATI"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUSR_DICH_ABIL_DATI_ID_DICH_ABIL_DATI_GENERATOR")
    public Long getIdDichAbilDati() {
        return this.idDichAbilDati;
    }

    public void setIdDichAbilDati(Long idDichAbilDati) {
        this.idDichAbilDati = idDichAbilDati;
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "TI_SCOPO_DICH_ABIL_DATI")
    public String getTiScopoDichAbilDati() {
        return this.tiScopoDichAbilDati;
    }

    public void setTiScopoDichAbilDati(String tiScopoDichAbilDati) {
        this.tiScopoDichAbilDati = tiScopoDichAbilDati;
    }

    // bi-directional many-to-one association to UsrAbilDati
    @OneToMany(mappedBy = "usrDichAbilDati")
    public List<UsrAbilDati> getUsrAbilDatis() {
        return this.usrAbilDatis;
    }

    public void setUsrAbilDatis(List<UsrAbilDati> usrAbilDatis) {
        this.usrAbilDatis = usrAbilDatis;
    }

    public UsrAbilDati addUsrAbilDati(UsrAbilDati usrAbilDati) {
        getUsrAbilDatis().add(usrAbilDati);
        usrAbilDati.setUsrDichAbilDati(this);
        return usrAbilDati;
    }

    public UsrAbilDati removeUsrAbilDati(UsrAbilDati usrAbilDati) {
        getUsrAbilDatis().remove(usrAbilDati);
        usrAbilDati.setUsrDichAbilDati(null);
        return usrAbilDati;
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

    // bi-directional many-to-one association to OrgAppartCollegEnti
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPART_COLLEG_ENTI")
    public OrgAppartCollegEnti getOrgAppartCollegEnti() {
        return this.orgAppartCollegEnti;
    }

    public void setOrgAppartCollegEnti(OrgAppartCollegEnti orgAppartCollegEnti) {
        this.orgAppartCollegEnti = orgAppartCollegEnti;
    }

    // bi-directional many-to-one association to OrgSuptEsternoEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public OrgSuptEsternoEnteConvenz getOrgSuptEsternoEnteConvenz() {
        return this.orgSuptEsternoEnteConvenz;
    }

    public void setOrgSuptEsternoEnteConvenz(OrgSuptEsternoEnteConvenz orgSuptEsternoEnteConvenz) {
        this.orgSuptEsternoEnteConvenz = orgSuptEsternoEnteConvenz;
    }

    // bi-directional many-to-one association to OrgVigilEnteProdut
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VIGIL_ENTE_PRODUT")
    public OrgVigilEnteProdut getOrgVigilEnteProdut() {
        return this.orgVigilEnteProdut;
    }

    public void setOrgVigilEnteProdut(OrgVigilEnteProdut orgVigilEnteProdut) {
        this.orgVigilEnteProdut = orgVigilEnteProdut;
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

    // bi-directional many-to-one association to UsrTipoDatoIam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_DATO_IAM")
    public UsrTipoDatoIam getUsrTipoDatoIam() {
        return this.usrTipoDatoIam;
    }

    public void setUsrTipoDatoIam(UsrTipoDatoIam usrTipoDatoIam) {
        this.usrTipoDatoIam = usrTipoDatoIam;
    }

    // bi-directional many-to-one association to UsrUsoUserApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USO_USER_APPLIC")
    public UsrUsoUserApplic getUsrUsoUserApplic() {
        return this.usrUsoUserApplic;
    }

    public void setUsrUsoUserApplic(UsrUsoUserApplic usrUsoUserApplic) {
        this.usrUsoUserApplic = usrUsoUserApplic;
    }
}
