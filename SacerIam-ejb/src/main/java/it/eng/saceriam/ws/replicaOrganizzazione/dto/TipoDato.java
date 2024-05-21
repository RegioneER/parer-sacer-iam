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

package it.eng.saceriam.ws.replicaOrganizzazione.dto;

/**
 *
 * @author Gilioli_P
 */
public class TipoDato {
    private String nmClasseTipoDato;
    private Integer idTipoDatoApplic;
    private String nmTipoDato;
    private String dsTipoDato;

    public String getNmClasseTipoDato() {
        return nmClasseTipoDato;
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
        this.nmClasseTipoDato = nmClasseTipoDato;
    }

    public Integer getIdTipoDatoApplic() {
        return idTipoDatoApplic;
    }

    public void setIdTipoDatoApplic(Integer idTipoDatoApplic) {
        this.idTipoDatoApplic = idTipoDatoApplic;
    }

    public String getNmTipoDato() {
        return nmTipoDato;
    }

    public void setNmTipoDato(String nmTipoDato) {
        this.nmTipoDato = nmTipoDato;
    }

    public String getDsTipoDato() {
        return dsTipoDato;
    }

    public void setDsTipoDato(String dsTipoDato) {
        this.dsTipoDato = dsTipoDato;
    }
}
