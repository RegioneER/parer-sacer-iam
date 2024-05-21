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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the APL_SISTEMA_VERS_ARK_RIF database table.
 */
@Entity
@Table(name = "APL_SISTEMA_VERS_ARK_RIF")
@NamedQuery(name = "AplSistemaVersArkRif.findAll", query = "SELECT a FROM AplSistemaVersArkRif a")
public class AplSistemaVersArkRif implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idSistemaVersArkRif;

    private AplSistemaVersante aplSistemaVersante;

    private UsrUser usrUser;

    public AplSistemaVersArkRif() {
    }

    @Id
    @Column(name = "ID_SISTEMA_VERS_ARK_RIF")
    @GenericGenerator(name = "SAPL_SISTEMA_VERS_ARK_RIF_ID_SISTEMA_VERS_ARK_RIF_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_SISTEMA_VERS_ARK_RIF"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_SISTEMA_VERS_ARK_RIF_ID_SISTEMA_VERS_ARK_RIF_GENERATOR")
    public Long getIdSistemaVersArkRif() {
        return this.idSistemaVersArkRif;
    }

    public void setIdSistemaVersArkRif(Long idSistemaVersArkRif) {
        this.idSistemaVersArkRif = idSistemaVersArkRif;
    }

    // bi-directional many-to-one association to AplSistemaVersante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SISTEMA_VERSANTE")
    public AplSistemaVersante getAplSistemaVersante() {
        return this.aplSistemaVersante;
    }

    public void setAplSistemaVersante(AplSistemaVersante aplSistemaVersante) {
        this.aplSistemaVersante = aplSistemaVersante;
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
}
