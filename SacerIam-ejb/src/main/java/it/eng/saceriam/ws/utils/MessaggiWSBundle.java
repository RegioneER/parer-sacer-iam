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

package it.eng.saceriam.ws.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessaggiWSBundle {

    // Definisce le costanti utilizzate per il bundle dei messaggi di errore
    private static final String BUNDLE_NAME = "messaggi_ws";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
    public static final String DEFAULT_LOCALE = "it";
    // COSTANTI DI ERRORE
    // ERRORI IMPREVISTI
    public static final String ERR_666 = "666";
    // ERRORI INSERIMENTO, MODIFICA, CANCELLAZIONE ORGANIZZAZIONE
    public static final String SERVIZI_ORG_001 = "SERVIZI-ORG-001";
    public static final String SERVIZI_ORG_002 = "SERVIZI-ORG-002";
    public static final String SERVIZI_ORG_003 = "SERVIZI-ORG-003";
    public static final String SERVIZI_ORG_004 = "SERVIZI-ORG-004";
    public static final String SERVIZI_ORG_005 = "SERVIZI-ORG-005";
    public static final String SERVIZI_ORG_006 = "SERVIZI-ORG-006";
    public static final String SERVIZI_ORG_007 = "SERVIZI-ORG-007";
    // RECUPERO AUTORIZZAZIONI
    public static final String RECUP_001 = "RECUP-001";
    public static final String RECUP_002 = "RECUP-002";
    public static final String RECUP_003 = "RECUP-003";
    public static final String RECUP_004 = "RECUP-004";
    public static final String RECUP_005 = "RECUP-005";
    public static final String RECUP_006 = "RECUP-006";
    // ERRORI DEI SERVIZI REST
    public static final String ERR_WS_CHECK = "ERR-WS-CHECK";
    public static final String MON_AUTH_001 = "MON-AUTH-001";
    public static final String MON_AUTH_002 = "MON-AUTH-002";
    public static final String MON_AUTH_003 = "MON-AUTH-003";
    public static final String MON_AUTH_004 = "MON-AUTH-004";
    public static final String MON_AUTH_005 = "MON-AUTH-005";
    // ERRORE ALLINEAMENTO RUOLI
    public static final String SERVIZI_RUOLI_001 = "SERVIZI-RUOLI-001";
    public static final String SERVIZI_RUOLI_002 = "SERVIZI-RUOLI-002";
    public static final String SERVIZI_RUOLI_003 = "SERVIZI-RUOLI-003";

    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }

    public static String getString(String key, Object... params) {
        return MessageFormat.format(RESOURCE_BUNDLE.getString(key), params);
    }
}
