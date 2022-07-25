/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.util;

import it.eng.spagoCore.error.EMFError;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Iacolucci_M
 */
public class Util {

    public static Properties getApplicationProperties() throws EMFError {
        Properties props = new Properties();
        try {
            props.load(Util.class.getClassLoader().getResourceAsStream("/SacerIam.properties"));
        } catch (IOException ex) {
            throw new EMFError(EMFError.BLOCKING, "Errore nel caricamento delle impostazioni per l'upload", ex);
        }
        return props;
    }

}
