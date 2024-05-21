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

package it.eng.saceriam.entity.constraint;

/**
 *
 * @author gilioli_p
 */
public class ConstUsrStatoUser {

    public enum TiStatotUser {

        ATTIVO("ATTIVO"), CESSATO("CESSATO"), DA_NOTIFICARE_PASSWORD("DA NOTIFICARE_PASSWORD"),
        DA_NOTIFICARE_USERID("DA NOTIFICARE_USERID"), DA_ATTIVARE("DA_ATTIVARE"),
        DA_NOTIFICARE_CESSAZIONE("DA_NOTIFICARE_CESSAZIONE"),
        DA_NOTIFICARE_RIATTIVAZIONE("DA_NOTIFICARE_RIATTIVAZIONE"), DISATTIVO("DISATTIVO");

        private String descrizione;

        public String getDescrizione() {
            return descrizione;
        }

        private TiStatotUser(String descrizione) {
            this.descrizione = descrizione;
        }

        public static TiStatotUser[] getEnums(TiStatotUser... vals) {
            return vals;
        }

        public static TiStatotUser[] getComboFiltriRicercaUtente() {
            return getEnums(ATTIVO, DISATTIVO);
        }
    }

}
