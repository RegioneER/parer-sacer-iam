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
package it.eng.saceriam.ws.rest.dto;

import it.eng.saceriam.ws.dto.IRispostaWS;

/**
 * Questa interfaccia prende il posto di IRispostaWS in SACER. Purtroppo al momento
 * dell'implementazione del primo ws rest in IAM l'interfaccia IRispostaWS era già usata
 * massivamente per i servizi esistenti. Integrare questi metodi in IRispostaWS avrebbe comportato
 * un lavoro di refactoring improbo.
 *
 * @author fioravanti_f
 */
public interface IRispostaRestWS extends IRispostaWS {

    // AvanzamentoWs getAvanzamento(); // inutile
    //
    // void setAvanzamento(AvanzamentoWs avanzamento); // inutile
    //
    void setEsitoWsErrBundle(String errCode, Object... params);

    void setEsitoWsErrBundle(String errCode);

    void setEsitoWsWarnBundle(String errCode, Object... params);

    void setEsitoWsWarnBundle(String errCode);

    void setEsitoWsError(String errCode, String errMessage);

    void setEsitoWsWarning(String errCode, String errMessage);
}
