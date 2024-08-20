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

package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ORG_V_OCCUP_STORAGE_ACCORDO database table.
 *
 */
@Entity
@Table(name = "ORG_V_OCCUP_STORAGE_ACCORDO")
public class OrgVOccupStorageAccordo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ambienteEnte;
    private String cdTipoAccordo;
    private BigDecimal dimBytes;
    private BigDecimal dimBytesMediaAnno;
    private Date dtDecAccordo;
    private Date dtFineAccordo;
    private String fasciaAccordo;
    private String fasciaOccupazione;
    private BigDecimal flSforo;
    private BigDecimal idAccordoEnte;
    private BigDecimal mesiValidita;
    private BigDecimal niFasciaAccordoA;
    private BigDecimal niFasciaOccupazioneA;
    private String nmAmbienteEnteConvenz;
    private String nmEnteSiam;
    private BigDecimal numComp;
    private BigDecimal numDoc;
    private BigDecimal numUd;

    public OrgVOccupStorageAccordo() {
    }

    @Column(name = "AMBIENTE_ENTE")
    public String getAmbienteEnte() {
        return this.ambienteEnte;
    }

    public void setAmbienteEnte(String ambienteEnte) {
        this.ambienteEnte = ambienteEnte;

    }

    @Column(name = "CD_TIPO_ACCORDO")
    public String getCdTipoAccordo() {
        return this.cdTipoAccordo;
    }

    public void setCdTipoAccordo(String cdTipoAccordo) {
        this.cdTipoAccordo = cdTipoAccordo;
    }

    @Column(name = "DIM_BYTES")
    public BigDecimal getDimBytes() {
        return this.dimBytes;
    }

    public void setDimBytes(BigDecimal dimBytes) {
        this.dimBytes = dimBytes;
    }

    @Column(name = "DIM_BYTES_MEDIA_ANNO")
    public BigDecimal getDimBytesMediaAnno() {
        return this.dimBytesMediaAnno;
    }

    public void setDimBytesMediaAnno(BigDecimal dimBytesMediaAnno) {
        this.dimBytesMediaAnno = dimBytesMediaAnno;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_DEC_ACCORDO")
    public Date getDtDecAccordo() {
        return this.dtDecAccordo;
    }

    public void setDtDecAccordo(Date dtDecAccordo) {
        this.dtDecAccordo = dtDecAccordo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_ACCORDO")
    public Date getDtFineAccordo() {
        return this.dtFineAccordo;
    }

    public void setDtFineAccordo(Date dtFineAccordo) {
        this.dtFineAccordo = dtFineAccordo;

    }

    @Column(name = "FASCIA_ACCORDO")
    public String getFasciaAccordo() {
        return this.fasciaAccordo;
    }

    public void setFasciaAccordo(String fasciaAccordo) {
        this.fasciaAccordo = fasciaAccordo;
    }

    @Column(name = "FASCIA_OCCUPAZIONE")
    public String getFasciaOccupazione() {
        return this.fasciaOccupazione;
    }

    public void setFasciaOccupazione(String fasciaOccupazione) {
        this.fasciaOccupazione = fasciaOccupazione;
    }

    @Column(name = "FL_SFORO", columnDefinition = "char(1)")
    public BigDecimal getFlSforo() {
        return this.flSforo;
    }

    public void setFlSforo(BigDecimal flSforo) {
        this.flSforo = flSforo;
    }

    @Id
    @Column(name = "ID_ACCORDO_ENTE")
    public BigDecimal getIdAccordoEnte() {
        return this.idAccordoEnte;
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
        this.idAccordoEnte = idAccordoEnte;
    }

    @Column(name = "MESI_VALIDITA")
    public BigDecimal getMesiValidita() {
        return this.mesiValidita;
    }

    public void setMesiValidita(BigDecimal mesiValidita) {
        this.mesiValidita = mesiValidita;
    }

    @Column(name = "NI_FASCIA_ACCORDO_A")
    public BigDecimal getNiFasciaAccordoA() {
        return this.niFasciaAccordoA;
    }

    public void setNiFasciaAccordoA(BigDecimal niFasciaAccordoA) {
        this.niFasciaAccordoA = niFasciaAccordoA;
    }

    @Column(name = "NI_FASCIA_OCCUPAZIONE_A")
    public BigDecimal getNiFasciaOccupazioneA() {
        return this.niFasciaOccupazioneA;
    }

    public void setNiFasciaOccupazioneA(BigDecimal niFasciaOccupazioneA) {
        this.niFasciaOccupazioneA = niFasciaOccupazioneA;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "NM_ENTE_SIAM")
    public String getNmEnteSiam() {
        return this.nmEnteSiam;
    }

    public void setNmEnteSiam(String nmEnteSiam) {
        this.nmEnteSiam = nmEnteSiam;
    }

    @Column(name = "NUM_COMP")
    public BigDecimal getNumComp() {
        return this.numComp;
    }

    public void setNumComp(BigDecimal numComp) {
        this.numComp = numComp;
    }

    @Column(name = "NUM_DOC")
    public BigDecimal getNumDoc() {
        return this.numDoc;
    }

    public void setNumDoc(BigDecimal numDoc) {
        this.numDoc = numDoc;
    }

    @Column(name = "NUM_UD")
    public BigDecimal getNumUd() {
        return this.numUd;
    }

    public void setNumUd(BigDecimal numUd) {
        this.numUd = numUd;
    }

}
