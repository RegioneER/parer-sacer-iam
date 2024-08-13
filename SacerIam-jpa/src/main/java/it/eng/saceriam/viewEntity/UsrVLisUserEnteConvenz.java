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
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the USR_V_LIS_USER_ENTE_CONVENZ database table.
 */
@Entity
@Table(name = "USR_V_LIS_USER_ENTE_CONVENZ")
public class UsrVLisUserEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dlCompositoOrganiz;

    private String dsListaAzioni;

    private Date dtRichGestUser;

    private String flAzioniEvase;

    private BigDecimal idRichGestUser;

    private BigDecimal idSistemaVersante;

    private String keyRichGestUser;

    private String nmApplic;

    private String nmCognomeUser;

    private String nmNomeUser;

    private String nmRuoloDefault;

    private String nmUserid;

    private String tiEnteConvenzUser;

    private String tiStatoUser;

    private String tipoUser;

    public UsrVLisUserEnteConvenz() {
    }

    public UsrVLisUserEnteConvenz(BigDecimal idEnteConvenz, BigDecimal idUserIam, String nmCognomeUser,
            String nmNomeUser, String nmUserid, String nmApplic, String nmRuoloDefault, String dlCompositoOrganiz,
            String tiStatoUser, String tipoUser, String keyRichGestUser, String dsListaAzioni, Date dtRichGestUser,
            String flAzioniEvase) {
        this.usrVLisUserEnteConvenzId = new UsrVLisUserEnteConvenzId();
        this.usrVLisUserEnteConvenzId.setIdEnteConvenz(idEnteConvenz);
        this.usrVLisUserEnteConvenzId.setIdUserIam(idUserIam);
        this.nmCognomeUser = nmCognomeUser;
        this.nmNomeUser = nmNomeUser;
        this.nmUserid = nmUserid;
        this.nmApplic = nmApplic;
        this.nmRuoloDefault = nmRuoloDefault;
        this.dlCompositoOrganiz = dlCompositoOrganiz;
        this.tiStatoUser = tiStatoUser;
        this.tipoUser = tipoUser;
        this.keyRichGestUser = keyRichGestUser;
        this.dsListaAzioni = dsListaAzioni;
        this.dtRichGestUser = dtRichGestUser;
        this.flAzioniEvase = flAzioniEvase;
    }

    @Column(name = "DL_COMPOSITO_ORGANIZ")
    public String getDlCompositoOrganiz() {
        return this.dlCompositoOrganiz;
    }

    public void setDlCompositoOrganiz(String dlCompositoOrganiz) {
        this.dlCompositoOrganiz = dlCompositoOrganiz;
    }

    @Column(name = "DS_LISTA_AZIONI")
    public String getDsListaAzioni() {
        return this.dsListaAzioni;
    }

    public void setDsListaAzioni(String dsListaAzioni) {
        this.dsListaAzioni = dsListaAzioni;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_RICH_GEST_USER")
    public Date getDtRichGestUser() {
        return this.dtRichGestUser;
    }

    public void setDtRichGestUser(Date dtRichGestUser) {
        this.dtRichGestUser = dtRichGestUser;
    }

    @Column(name = "FL_AZIONI_EVASE", columnDefinition = "char(1)")
    public String getFlAzioniEvase() {
        return this.flAzioniEvase;
    }

    public void setFlAzioniEvase(String flAzioniEvase) {
        this.flAzioniEvase = flAzioniEvase;
    }

    @Column(name = "ID_RICH_GEST_USER")
    public BigDecimal getIdRichGestUser() {
        return this.idRichGestUser;
    }

    public void setIdRichGestUser(BigDecimal idRichGestUser) {
        this.idRichGestUser = idRichGestUser;
    }

    @Column(name = "ID_SISTEMA_VERSANTE")
    public BigDecimal getIdSistemaVersante() {
        return this.idSistemaVersante;
    }

    public void setIdSistemaVersante(BigDecimal idSistemaVersante) {
        this.idSistemaVersante = idSistemaVersante;
    }

    @Column(name = "KEY_RICH_GEST_USER")
    public String getKeyRichGestUser() {
        return this.keyRichGestUser;
    }

    public void setKeyRichGestUser(String keyRichGestUser) {
        this.keyRichGestUser = keyRichGestUser;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_COGNOME_USER")
    public String getNmCognomeUser() {
        return this.nmCognomeUser;
    }

    public void setNmCognomeUser(String nmCognomeUser) {
        this.nmCognomeUser = nmCognomeUser;
    }

    @Column(name = "NM_NOME_USER")
    public String getNmNomeUser() {
        return this.nmNomeUser;
    }

    public void setNmNomeUser(String nmNomeUser) {
        this.nmNomeUser = nmNomeUser;
    }

    @Column(name = "NM_RUOLO_DEFAULT")
    public String getNmRuoloDefault() {
        return this.nmRuoloDefault;
    }

    public void setNmRuoloDefault(String nmRuoloDefault) {
        this.nmRuoloDefault = nmRuoloDefault;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    @Column(name = "TI_ENTE_CONVENZ_USER")
    public String getTiEnteConvenzUser() {
        return this.tiEnteConvenzUser;
    }

    public void setTiEnteConvenzUser(String tiEnteConvenzUser) {
        this.tiEnteConvenzUser = tiEnteConvenzUser;
    }

    @Column(name = "TI_STATO_USER")
    public String getTiStatoUser() {
        return this.tiStatoUser;
    }

    public void setTiStatoUser(String tiStatoUser) {
        this.tiStatoUser = tiStatoUser;
    }

    @Column(name = "TIPO_USER")
    public String getTipoUser() {
        return this.tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    private UsrVLisUserEnteConvenzId usrVLisUserEnteConvenzId;

    @EmbeddedId()
    public UsrVLisUserEnteConvenzId getUsrVLisUserEnteConvenzId() {
        return usrVLisUserEnteConvenzId;
    }

    public void setUsrVLisUserEnteConvenzId(UsrVLisUserEnteConvenzId usrVLisUserEnteConvenzId) {
        this.usrVLisUserEnteConvenzId = usrVLisUserEnteConvenzId;
    }
}
