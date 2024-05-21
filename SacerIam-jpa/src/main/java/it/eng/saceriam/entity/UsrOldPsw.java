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
import java.math.BigDecimal;

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
 * The persistent class for the USR_USER database table.
 */
@Entity
@Table(name = "USR_OLD_PSW")
@NamedQuery(name = "UsrOldPsw.findAll", query = "SELECT u FROM UsrOldPsw u")
public class UsrOldPsw implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idOldPsw;

    private BigDecimal pgOldPsw;

    private String cdPsw;

    private String cdSalt;

    private UsrUser usrUser;

    public UsrOldPsw() {
    }

    @Id
    @Column(name = "ID_OLD_PSW")
    @GenericGenerator(name = "SUSR_OLD_PSW_ID_OLD_PSW_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SUSR_OLD_PSW"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUSR_OLD_PSW_ID_OLD_PSW_GENERATOR")
    public Long getIdOldPsw() {
        return this.idOldPsw;
    }

    public void setIdOldPsw(Long idOldPsw) {
        this.idOldPsw = idOldPsw;
    }

    @Column(name = "PG_OLD_PSW")
    public BigDecimal getPgOldPsw() {
        return this.pgOldPsw;
    }

    public void setPgOldPsw(BigDecimal pgOldPsw) {
        this.pgOldPsw = pgOldPsw;
    }

    @Column(name = "CD_PSW")
    public String getCdPsw() {
        return this.cdPsw;
    }

    public void setCdPsw(String cdPsw) {
        this.cdPsw = cdPsw;
    }

    @Column(name = "CD_SALT")
    public String getCdSalt() {
        return this.cdSalt;
    }

    public void setCdSalt(String cdSalt) {
        this.cdSalt = cdSalt;
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
