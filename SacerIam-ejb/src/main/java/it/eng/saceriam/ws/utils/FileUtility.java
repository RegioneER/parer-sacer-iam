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
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package it.eng.saceriam.ws.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fioravanti_F
 */
public class FileUtility {

    private static final Logger log = LoggerFactory.getLogger(FileUtility.class);

    public static byte[] getByteDaFile(File file) {
	FileInputStream fis = null;
	byte[] fileBArray = null;
	try {
	    fileBArray = new byte[(int) file.length()];
	    fis = new FileInputStream(file);
	    fis.read(fileBArray);
	} catch (IOException ex) {
	    log.error("Eccezione nella lettura dei dati binari del file " + ex);
	} finally {
	    try {
		fis.close();
	    } catch (IOException ex) {
		log.error("Eccezione nella lettura dei dati binari del file " + ex);
	    }
	}

	return fileBArray;
    }
}
