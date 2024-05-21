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

package it.eng.saceriam.ws.allineaRuolo.dto;

import java.io.Serializable;

/**
 *
 * @author Bonora_L
 */
public class Applic implements Serializable {

    private String nmApplic;
    private String cdVersioneComp;
    private ListaDichAutor listaDichAutor;

    /**
     * Get the value of nmApplic
     *
     * @return the value of nmApplic
     */
    public String getNmApplic() {
        return nmApplic;
    }

    /**
     * Set the value of nmApplic
     *
     * @param nmApplic
     *            new value of nmApplic
     */
    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    /**
     * @return the cdVersioneComp
     */
    public String getCdVersioneComp() {
        return cdVersioneComp;
    }

    /**
     * @param cdVersioneComp
     *            the cdVersioneComp to set
     */
    public void setCdVersioneComp(String cdVersioneComp) {
        this.cdVersioneComp = cdVersioneComp;
    }

    /**
     * @return the listaDichAutor
     */
    public ListaDichAutor getListaDichAutor() {
        return listaDichAutor;
    }

    /**
     * @param listaDichAutor
     *            the listaDichAutor to set
     */
    public void setListaDichAutor(ListaDichAutor listaDichAutor) {
        this.listaDichAutor = listaDichAutor;
    }

    @Override
    public String toString() {
        return "{" + "nmApplic=" + nmApplic + ", cdVersioneComp= " + cdVersioneComp + ", listaDichAutor="
                + listaDichAutor + "}";
    }

}
