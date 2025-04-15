/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.entity.constraint;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Bonora_L
 */
public class ConstPrfDichAutor {

    public enum TiDichAutor {
	ENTRY_MENU("M"), AZIONE("A"), SERVIZIO_WEB("S"), PAGINA("P");

	private final String value;

	private TiDichAutor(String value) {
	    this.value = value;
	}

	public String getValue() {
	    return this.value;
	}

	public static TiDichAutor getTiDichAutor(String value) {
	    for (TiDichAutor dich : TiDichAutor.values()) {
		if (StringUtils.equalsIgnoreCase(value, dich.getValue())) {
		    return dich;
		}
	    }
	    return null;
	}
    }

    public enum TiScopoDichAutor {

	ALL_ABILITAZIONI, ALL_ABILITAZIONI_CHILD, UNA_ABILITAZIONE
    }
}
