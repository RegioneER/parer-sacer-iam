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
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the DEC_MODELLO_COMUNIC database table.
 */
@Entity
@Table(name = "DEC_MODELLO_COMUNIC")
public class DecModelloComunic implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idModelloComunic;

    private String blTestoComunic;

    private String cdModelloComunic;

    private String dsModelloComunic;

    private String dsOggettoComunic;

    private Date dtIstituz;

    private Date dtSoppres;

    private String nmMittente;

    private String tiComunic;

    private String tiOggettoQuery;

    private String tiStatoTrigComunic;

    private List<NtfNotifica> ntfNotificas = new ArrayList<>();

    public DecModelloComunic() {
    }

    @Id
    @Column(name = "ID_MODELLO_COMUNIC")
    @GenericGenerator(name = "SDEC_MODELLO_COMUNIC_ID_MODELLO_COMUNIC_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SDEC_MODELLO_COMUNIC"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SDEC_MODELLO_COMUNIC_ID_MODELLO_COMUNIC_GENERATOR")
    public Long getIdModelloComunic() {
        return this.idModelloComunic;
    }

    public void setIdModelloComunic(Long idModelloComunic) {
        this.idModelloComunic = idModelloComunic;
    }

    @Lob
    @Column(name = "BL_TESTO_COMUNIC")
    public String getBlTestoComunic() {
        return this.blTestoComunic;
    }

    public void setBlTestoComunic(String blTestoComunic) {
        this.blTestoComunic = blTestoComunic;
    }

    @Column(name = "CD_MODELLO_COMUNIC")
    public String getCdModelloComunic() {
        return this.cdModelloComunic;
    }

    public void setCdModelloComunic(String cdModelloComunic) {
        this.cdModelloComunic = cdModelloComunic;
    }

    @Column(name = "DS_MODELLO_COMUNIC")
    public String getDsModelloComunic() {
        return this.dsModelloComunic;
    }

    public void setDsModelloComunic(String dsModelloComunic) {
        this.dsModelloComunic = dsModelloComunic;
    }

    @Column(name = "DS_OGGETTO_COMUNIC")
    public String getDsOggettoComunic() {
        return this.dsOggettoComunic;
    }

    public void setDsOggettoComunic(String dsOggettoComunic) {
        this.dsOggettoComunic = dsOggettoComunic;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ISTITUZ")
    public Date getDtIstituz() {
        return this.dtIstituz;
    }

    public void setDtIstituz(Date dtIstituz) {
        this.dtIstituz = dtIstituz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SOPPRES")
    public Date getDtSoppres() {
        return this.dtSoppres;
    }

    public void setDtSoppres(Date dtSoppres) {
        this.dtSoppres = dtSoppres;
    }

    @Column(name = "NM_MITTENTE")
    public String getNmMittente() {
        return this.nmMittente;
    }

    public void setNmMittente(String nmMittente) {
        this.nmMittente = nmMittente;
    }

    @Column(name = "TI_COMUNIC")
    public String getTiComunic() {
        return this.tiComunic;
    }

    public void setTiComunic(String tiComunic) {
        this.tiComunic = tiComunic;
    }

    @Column(name = "TI_OGGETTO_QUERY")
    public String getTiOggettoQuery() {
        return this.tiOggettoQuery;
    }

    public void setTiOggettoQuery(String tiOggettoQuery) {
        this.tiOggettoQuery = tiOggettoQuery;
    }

    @Column(name = "TI_STATO_TRIG_COMUNIC")
    public String getTiStatoTrigComunic() {
        return this.tiStatoTrigComunic;
    }

    public void setTiStatoTrigComunic(String tiStatoTrigComunic) {
        this.tiStatoTrigComunic = tiStatoTrigComunic;
    }

    // bi-directional many-to-one association to NtfNotifica
    @OneToMany(mappedBy = "decModelloComunic")
    public List<NtfNotifica> getNtfNotificas() {
        return this.ntfNotificas;
    }

    public void setNtfNotificas(List<NtfNotifica> ntfNotificas) {
        this.ntfNotificas = ntfNotificas;
    }

    public NtfNotifica addNtfNotifica(NtfNotifica ntfNotifica) {
        getNtfNotificas().add(ntfNotifica);
        ntfNotifica.setDecModelloComunic(this);
        return ntfNotifica;
    }

    public NtfNotifica removeNtfNotifica(NtfNotifica ntfNotifica) {
        getNtfNotificas().remove(ntfNotifica);
        ntfNotifica.setDecModelloComunic(null);
        return ntfNotifica;
    }
}
