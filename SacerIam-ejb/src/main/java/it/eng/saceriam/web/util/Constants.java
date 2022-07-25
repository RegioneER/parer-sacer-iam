/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.util;

/**
 *
 * @author Quaranta_M (40M o "FortyEm")
 */
public class Constants {

    public final static int PASSWORD_EXPIRATION_DAYS = 90;
    public final static String SACERIAM = "SACER_IAM";
    // Constants for Transformer
    public final static String ENTITY_PACKAGE_NAME = "it.eng.saceriam.entity";
    public final static String GRANTED_ENTITY_PACKAGE_NAME = "it.eng.saceriam.grantedEntity";
    public final static String ROWBEAN_PACKAGE_NAME = "it.eng.saceriam.slite.gen.tablebean";
    public final static String VIEWROWBEAN_PACKAGE_NAME = "it.eng.saceriam.slite.gen.viewbean";
    public final static String VIEWENTITY_PACKAGE_NAME = "it.eng.saceriam.viewEntity";
    public final static String GRANTED_VIEWENTITY_PACKAGE_NAME = "it.eng.saceriam.grantedViewEntity";

    // Enum per esito
    public enum Esito {

        OK, KO
    }

    public enum EsitoServizio {

        OK, KO, WARN
    }

    // MEV26588
    public enum ComboValueParamentersType {
        STRINGA, PASSWORD;
    }

    public static final String OBFUSCATED_STRING = "********";
}
