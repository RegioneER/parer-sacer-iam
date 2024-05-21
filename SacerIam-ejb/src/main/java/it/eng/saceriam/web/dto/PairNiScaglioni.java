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

package it.eng.saceriam.web.dto;

public class PairNiScaglioni<Ini, Fine> {

    private final Ini ini;
    private final Fine fine;

    public PairNiScaglioni(Ini ini, Fine fine) {
        this.ini = ini;
        this.fine = fine;
    }

    public Ini getIni() {
        return ini;
    }

    public Fine getFine() {
        return fine;
    }

    @Override
    public int hashCode() {
        return ini.hashCode() ^ fine.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PairNiScaglioni)) {
            return false;
        }
        PairNiScaglioni pairo = (PairNiScaglioni) o;
        return this.ini.equals(pairo.getIni()) && this.fine.equals(pairo.getFine());
    }

    @Override
    public String toString() {
        return "PairNiScaglioni{" + "ni_ini_scaglione=" + ini + ", ni_fine_scaglione=" + fine + '}';
    }

}
