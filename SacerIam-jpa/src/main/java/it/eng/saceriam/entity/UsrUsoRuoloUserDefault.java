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
 * The persistent class for the USR_USO_RUOLO_USER_DEFAULT database table.
 */
@Entity
@Table(name = "USR_USO_RUOLO_USER_DEFAULT")
public class UsrUsoRuoloUserDefault implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idUsoRuoloUserDefault;

    private PrfRuolo prfRuolo;

    private UsrUsoUserApplic usrUsoUserApplic;

    public UsrUsoRuoloUserDefault() {
    }

    @Id
    @Column(name = "ID_USO_RUOLO_USER_DEFAULT")
    @GenericGenerator(name = "SUSR_USO_RUOLO_USER_DEFAULT_ID_USO_RUOLO_USER_DEFAULT_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SUSR_USO_RUOLO_USER_DEFAULT"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUSR_USO_RUOLO_USER_DEFAULT_ID_USO_RUOLO_USER_DEFAULT_GENERATOR")
    public Long getIdUsoRuoloUserDefault() {
        return this.idUsoRuoloUserDefault;
    }

    public void setIdUsoRuoloUserDefault(Long idUsoRuoloUserDefault) {
        this.idUsoRuoloUserDefault = idUsoRuoloUserDefault;
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
