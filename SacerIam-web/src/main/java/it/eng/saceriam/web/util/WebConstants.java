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

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package it.eng.saceriam.web.util;

/**
 *
 * @author Iacolucci_M
 */
public class WebConstants {

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String MIME_TYPE_MICROSOFT_WORD = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String MIME_TYPE_GENERIC = "application/octet-stream";

    // Nome parametri Dettaglio fatture
    public static final String PARAMETER_FATTURE = "ListaFatture";

    public enum DOWNLOAD_ATTRS {

	DOWNLOAD_ACTION, DOWNLOAD_FILENAME, DOWNLOAD_FILEPATH, DOWNLOAD_DELETEFILE,
	DOWNLOAD_CONTENTTYPE
    }

    public enum TipoAmbitoTerritoriale {

	FORMA_ASSOCIATA("FORMA_ASSOCIATA"), PROVINCIA("PROVINCIA"), REGIONE_STATO("REGIONE/STATO");

	private String nome;

	public String getNome() {
	    return nome;
	}

	private TipoAmbitoTerritoriale(String nome) {
	    this.nome = nome;
	}
    }

}
