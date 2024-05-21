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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the APL_NEWS database table.
 */
@Entity
@Table(name = "APL_NEWS")
public class AplNews implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idNews;

    private String dlTesto;

    private String dsOggetto;

    private Date dtFinPubblic;

    private Date dtIniPubblic;

    private String flPubblicHomepage;

    private String flPubblicLogin;

    private List<AplNewsApplic> aplNewsApplics = new ArrayList<>();

    public AplNews() {
    }

    @Id
    @Column(name = "ID_NEWS")
    @GenericGenerator(name = "SAPL_NEWS_ID_NEWS_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_NEWS"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_NEWS_ID_NEWS_GENERATOR")
    public Long getIdNews() {
        return this.idNews;
    }

    public void setIdNews(Long idNews) {
        this.idNews = idNews;
    }

    @Column(name = "DL_TESTO")
    public String getDlTesto() {
        return this.dlTesto;
    }

    public void setDlTesto(String dlTesto) {
        this.dlTesto = dlTesto;
    }

    @Column(name = "DS_OGGETTO")
    public String getDsOggetto() {
        return this.dsOggetto;
    }

    public void setDsOggetto(String dsOggetto) {
        this.dsOggetto = dsOggetto;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_PUBBLIC")
    public Date getDtFinPubblic() {
        return this.dtFinPubblic;
    }

    public void setDtFinPubblic(Date dtFinPubblic) {
        this.dtFinPubblic = dtFinPubblic;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_PUBBLIC")
    public Date getDtIniPubblic() {
        return this.dtIniPubblic;
    }

    public void setDtIniPubblic(Date dtIniPubblic) {
        this.dtIniPubblic = dtIniPubblic;
    }

    @Column(name = "FL_PUBBLIC_HOMEPAGE", columnDefinition = "char(1)")
    public String getFlPubblicHomepage() {
        return this.flPubblicHomepage;
    }

    public void setFlPubblicHomepage(String flPubblicHomepage) {
        this.flPubblicHomepage = flPubblicHomepage;
    }

    @Column(name = "FL_PUBBLIC_LOGIN", columnDefinition = "char(1)")
    public String getFlPubblicLogin() {
        return this.flPubblicLogin;
    }

    public void setFlPubblicLogin(String flPubblicLogin) {
        this.flPubblicLogin = flPubblicLogin;
    }

    // bi-directional many-to-one association to AplNewsApplic
    @OneToMany(mappedBy = "aplNew", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public List<AplNewsApplic> getAplNewsApplics() {
        return this.aplNewsApplics;
    }

    public void setAplNewsApplics(List<AplNewsApplic> aplNewsApplics) {
        this.aplNewsApplics = aplNewsApplics;
    }
}
