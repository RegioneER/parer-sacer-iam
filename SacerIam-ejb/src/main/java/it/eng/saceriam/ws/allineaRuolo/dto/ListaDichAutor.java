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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Bonora_L
 */
public class ListaDichAutor implements Iterable<DichAutor> {

    private List<DichAutor> dichAutor;

    public List<DichAutor> getDichAutor() {
        if (dichAutor == null) {
            dichAutor = new ArrayList<>();
        }
        return dichAutor;
    }

    public void setDichAutor(List<DichAutor> dichAutor) {
        this.dichAutor = dichAutor;
    }

    @Override
    public Iterator<DichAutor> iterator() {
        return dichAutor.iterator();
    }

    @Override
    public String toString() {
        return "" + dichAutor;
    }

}
