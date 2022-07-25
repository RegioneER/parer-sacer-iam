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
public class ConstPrfRuolo {

    public enum TiRuolo {
        AUTOMA, PERSONA_FISICA
    }

    public enum TiCategRuolo {
        amministrazione, conservazione, gestione, produzione, vigilanza
    }

    public enum TiStatoRichAllineaRuoli {
        DA_ALLINEARE, ALLINEAMENTO_COMPLETO, ALLINEAMENTO_IN_CORSO, ALLINEAMENTO_IN_ERRORE, ALLINEAMENTO_PARZIALE
    }
}
