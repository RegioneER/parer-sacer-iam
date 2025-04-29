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
import java.sql.Timestamp;

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
 * The persistent class for the USR_STATO_USER database table.
 */
@Entity
@Table(name = "USR_STATO_USER")
public class UsrStatoUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idStatoUser;

    private String tiStatoUser;

    private Timestamp tsStato;

    private NtfNotifica ntfNotifica;

    private UsrRichGestUser usrRichGestUser;

    private UsrUser usrUser;

    public UsrStatoUser() {
    }

    @Id
    @Column(name = "ID_STATO_USER")
    @GenericGenerator(name = "SUSR_STATO_USER_ID_STATO_USER_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SUSR_STATO_USER"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUSR_STATO_USER_ID_STATO_USER_GENERATOR")
    public Long getIdStatoUser() {
	return this.idStatoUser;
    }

    public void setIdStatoUser(Long idStatoUser) {
	this.idStatoUser = idStatoUser;
    }

    @Column(name = "TI_STATO_USER")
    public String getTiStatoUser() {
	return this.tiStatoUser;
    }

    public void setTiStatoUser(String tiStatoUser) {
	this.tiStatoUser = tiStatoUser;
    }

    @Column(name = "TS_STATO")
    public Timestamp getTsStato() {
	return this.tsStato;
    }

    public void setTsStato(Timestamp tsStato) {
	this.tsStato = tsStato;
    }

    // bi-directional many-to-one association to NtfNotifica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICA")
    public NtfNotifica getNtfNotifica() {
	return this.ntfNotifica;
    }

    public void setNtfNotifica(NtfNotifica ntfNotifica) {
	this.ntfNotifica = ntfNotifica;
    }

    // bi-directional many-to-one association to UsrRichGestUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RICH_GEST_USER")
    public UsrRichGestUser getUsrRichGestUser() {
	return this.usrRichGestUser;
    }

    public void setUsrRichGestUser(UsrRichGestUser usrRichGestUser) {
	this.usrRichGestUser = usrRichGestUser;
    }

    // bi-directional many-to-one association to UsrUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_IAM", nullable = false)
    public UsrUser getUsrUser() {
	return this.usrUser;
    }

    public void setUsrUser(UsrUser usrUser) {
	this.usrUser = usrUser;
    }
}
