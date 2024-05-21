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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import it.eng.saceriam.entity.constraint.ConstOrgGestioneAccordo.TipoTrasmissione;

/**
 * The persistent class for the ORG_GESTIONE_ACCORDO database table.
 */
@Entity
@Table(name = "ORG_GESTIONE_ACCORDO")
@NamedQuery(name = "OrgGestioneAccordo.findAll", query = "SELECT o FROM OrgGestioneAccordo o")
public class OrgGestioneAccordo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idGestAccordo;

    private BigDecimal aaGestAccordo;

    private byte[] blGestAccordo;

    private String cdKeyGestAccordo;

    private String cdRegistroGestAccordo;

    private String dsNotaGestAccordo;

    private String dsGestAccordo;

    private Date dtGestAccordo;

    private String nmFileGestAccordo;

    private BigDecimal pgGestAccordo;

    private TipoTrasmissione tipoTrasmissione;

    private BigDecimal idGestAccordoRisposta;

    private OrgAccordoEnte orgAccordoEnte;

    private OrgTipiGestioneAccordo orgTipiGestioneAccordo;

    private String enteGestAccordo;

    private String strutturaGestAccordo;

    public OrgGestioneAccordo() {
    }

    @Id
    @Column(name = "ID_GEST_ACCORDO")
    @GenericGenerator(name = "SORG_GESTIONE_ACCORDO_ID_GEST_ACCORDO_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SORG_GESTIONE_ACCORDO"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SORG_GESTIONE_ACCORDO_ID_GEST_ACCORDO_GENERATOR")
    public Long getIdGestAccordo() {
        return this.idGestAccordo;
    }

    public void setIdGestAccordo(Long idGestAccordo) {
        this.idGestAccordo = idGestAccordo;
    }

    @Column(name = "AA_GEST_ACCORDO")
    public BigDecimal getAaGestAccordo() {
        return this.aaGestAccordo;
    }

    public void setAaGestAccordo(BigDecimal aaGestAccordo) {
        this.aaGestAccordo = aaGestAccordo;
    }

    @Lob
    @Column(name = "BL_GEST_ACCORDO")
    public byte[] getBlGestAccordo() {
        return this.blGestAccordo;
    }

    public void setBlGestAccordo(byte[] blGestAccordo) {
        this.blGestAccordo = blGestAccordo;
    }

    @Column(name = "CD_KEY_GEST_ACCORDO")
    public String getCdKeyGestAccordo() {
        return this.cdKeyGestAccordo;
    }

    public void setCdKeyGestAccordo(String cdKeyGestAccordo) {
        this.cdKeyGestAccordo = cdKeyGestAccordo;
    }

    @Column(name = "CD_REGISTRO_GEST_ACCORDO")
    public String getCdRegistroGestAccordo() {
        return this.cdRegistroGestAccordo;
    }

    public void setCdRegistroGestAccordo(String cdRegistroGestAccordo) {
        this.cdRegistroGestAccordo = cdRegistroGestAccordo;
    }

    @Column(name = "DS_NOTA_GEST_ACCORDO")
    public String getDsNotaGestAccordo() {
        return this.dsNotaGestAccordo;
    }

    public void setDsNotaGestAccordo(String dsNotaGestAccordo) {
        this.dsNotaGestAccordo = dsNotaGestAccordo;
    }

    @Column(name = "DS_GEST_ACCORDO")
    public String getDsGestAccordo() {
        return this.dsGestAccordo;
    }

    public void setDsGestAccordo(String dsGestAccordo) {
        this.dsGestAccordo = dsGestAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_GEST_ACCORDO")
    public Date getDtGestAccordo() {
        return this.dtGestAccordo;
    }

    public void setDtGestAccordo(Date dtGestAccordo) {
        this.dtGestAccordo = dtGestAccordo;
    }

    @Column(name = "NM_FILE_GEST_ACCORDO")
    public String getNmFileGestAccordo() {
        return this.nmFileGestAccordo;
    }

    public void setNmFileGestAccordo(String nmFileGestAccordo) {
        this.nmFileGestAccordo = nmFileGestAccordo;
    }

    @Column(name = "PG_GEST_ACCORDO")
    public BigDecimal getPgGestAccordo() {
        return this.pgGestAccordo;
    }

    public void setPgGestAccordo(BigDecimal pgGestAccordo) {
        this.pgGestAccordo = pgGestAccordo;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_TRASMISSIONE")
    public TipoTrasmissione getTipoTrasmissione() {
        return this.tipoTrasmissione;
    }

    public void setTipoTrasmissione(TipoTrasmissione tipoTrasmissione) {
        this.tipoTrasmissione = tipoTrasmissione;
    }

    @Column(name = "ID_GEST_ACCORDO_RISPOSTA")
    public BigDecimal getIdGestAccordoRisposta() {
        return this.idGestAccordoRisposta;
    }

    public void setIdGestAccordoRisposta(BigDecimal idGestAccordoRisposta) {
        this.idGestAccordoRisposta = idGestAccordoRisposta;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACCORDO_ENTE")
    public OrgAccordoEnte getOrgAccordoEnte() {
        return this.orgAccordoEnte;
    }

    public void setOrgAccordoEnte(OrgAccordoEnte orgAccordoEnte) {
        this.orgAccordoEnte = orgAccordoEnte;
    }

    // bi-directional many-to-one association to OrgTipiGestioneAccordo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_GESTIONE_ACCORDO")
    public OrgTipiGestioneAccordo getOrgTipiGestioneAccordo() {
        return this.orgTipiGestioneAccordo;
    }

    public void setOrgTipiGestioneAccordo(OrgTipiGestioneAccordo orgTipiGestioneAccordo) {
        this.orgTipiGestioneAccordo = orgTipiGestioneAccordo;
    }

    @Column(name = "ENTE_GEST_ACCORDO")
    public String getEnteGestAccordo() {
        return enteGestAccordo;
    }

    public void setEnteGestAccordo(String enteGestAccordo) {
        this.enteGestAccordo = enteGestAccordo;
    }

    @Column(name = "STRUTTURA_GEST_ACCORDO")
    public String getStrutturaGestAccordo() {
        return strutturaGestAccordo;
    }

    public void setStrutturaGestAccordo(String strutturaGestAccordo) {
        this.strutturaGestAccordo = strutturaGestAccordo;
    }
}
