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

package it.eng.saceriam.ws.utils;

/**
 *
 * @author Fioravanti_F
 */
public class Costanti {

    //
    public static final String WS_INSERIMENTO_ORGANIZZAZIONE_VRSN = "1.0";
    public static final String WS_MODIFICA_ORGANIZZAZIONE_VRSN = "1.0";
    public static final String WS_CANCELLA_ORGANIZZAZIONE_VRSN = "1.0";
    public static final String WS_RESTITUZIONE_NEWS_APPLICAZIONE_VRSN = "1.0";
    public static final String WS_STATUS_MONITOR_VRSN = "1.0";
    public static final String WS_APPINFO_VRSN = "1.0";

    //
    public static final String[] WS_INSERIMENTO_ORGANIZZAZIONE_COMP = {
	    "1.0" };
    public static final String[] WS_MODIFICA_ORGANIZZAZIONE_COMP = {
	    "1.0" };
    public static final String[] WS_CANCELLA_ORGANIZZAZIONE_COMP = {
	    "1.0" };
    public static final String[] WS_RESTITUZIONE_NEWS_APPLICAZIONE_COMP = {
	    "1.0" };
    public static final String[] WS_STATUS_MONITOR_COMP = {
	    "1.0" };
    public static final String[] WS_APPINFO_COMP = {
	    "1.0" };

    //
    public static final String WS_INSERIMENTO_ORGANIZZAZIONE = "InserimentoOrganizzione";
    public static final String WS_MODIFICA_ORGANIZZAZIONE = "ModificaOrganizzione";
    public static final String WS_CANCELLA_ORGANIZZAZIONE = "CancellaOrganizzione";
    public static final String WS_RESTITUZIONE_NEWS_APPLICAZIONE = "RestituzioneNewsApplicazione";
    public static final String WS_STATUS_MONITOR_NOME = "StatusMonitor";
    public static final String WS_APPINFO_NOME = "AppInfo";

    public enum AttribDatiSpecDataType {

	ALFANUMERICO, NUMERICO, DATA, DATETIME
    }

    public enum ModificatoriWS {
	// TAG_VERIFICA_FORMATI_OLD,
	// TAG_VERIFICA_FORMATI_1_25,
	// TAG_MIGRAZIONE,
	// TAG_DATISPEC_EXT,
	// TAG_ESTESI_1_3_OUT // ID documento, tag Versatore
    }
}
