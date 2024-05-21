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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.entity.constraint;

/**
 *
 * @author gilioli_p
 */
public class ConstDecModelloComunic {

    public enum TiComunic {
        ESIBIZIONE, NOTIFICA
    }

    public enum TiOggettoQuery {
        COMP, DOC, RICH_GEST_USER, UNITA_DOC, UTENTE
    }

    public enum TiStatoTrigComunic {
        DA_NOTIFICARE_PASSWORD, DA_NOTIFICARE_USERID, DA_NOTIFICARE_CESSAZIONE, DA_NOTIFICARE_EVASIONE,
        DA_NOTIFICARE_RIATTIVAZIONE
    }
}
