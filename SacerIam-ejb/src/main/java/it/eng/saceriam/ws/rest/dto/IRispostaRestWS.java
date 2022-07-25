/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.rest.dto;

import it.eng.saceriam.ws.dto.IRispostaWS;

/**
 * Questa interfaccia prende il posto di IRispostaWS in SACER. Purtroppo al momento dell'implementazione del primo ws
 * rest in IAM l'interfaccia IRispostaWS era già usata massivamente per i servizi esistenti. Integrare questi metodi in
 * IRispostaWS avrebbe comportato un lavoro di refactoring improbo.
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
