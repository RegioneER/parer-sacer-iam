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
 * The persistent class for the ORG_ENTE_ARK_RIF database table.
 */
@Entity
@Table(name = "ORG_ENTE_ARK_RIF")
public class OrgEnteArkRif implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEnteArkRif;

    private OrgEnteSiam orgEnteSiam;

    private UsrUser usrUser;

    private String flReferente;

    private String dlNote;

    public OrgEnteArkRif() {
    }

    @Id
    @Column(name = "ID_ENTE_ARK_RIF")
    @GenericGenerator(name = "SORG_ENTE_ARK_RIF_ID_ENTE_ARK_RIF_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_ENTE_ARK_RIF"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_ENTE_ARK_RIF_ID_ENTE_ARK_RIF_GENERATOR")
    public Long getIdEnteArkRif() {
        return this.idEnteArkRif;
    }

    public void setIdEnteArkRif(Long idEnteArkRif) {
        this.idEnteArkRif = idEnteArkRif;
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

    // bi-directional many-to-one association to UsrUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_IAM")
    public UsrUser getUsrUser() {
        return this.usrUser;
    }

    public void setUsrUser(UsrUser usrUser) {
        this.usrUser = usrUser;
    }

    @Column(name = "FL_REFERENTE", columnDefinition = "char(1)")
    public String getFlReferente() {
        return flReferente;
    }

    public void setFlReferente(String flReferente) {
        this.flReferente = flReferente;
    }

    @Column(name = "DL_NOTE")
    public String getDlNote() {
        return dlNote;
    }

    public void setDlNote(String dlNote) {
        this.dlNote = dlNote;
    }
}
