/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.utils;

import it.eng.saceriam.exception.ParerInternalError;
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
