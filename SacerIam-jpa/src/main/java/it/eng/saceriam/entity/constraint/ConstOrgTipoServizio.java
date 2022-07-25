/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.entity.constraint;

/**
 *
 * @author Gilioli_P
 */
public class ConstOrgTipoServizio {
    public enum TiClasseTipoServizio {
        ALTRO, ATTIVAZIONE_SISTEMA_VERSANTE, CONSERVAZIONE, ATTIVAZIONE_TIPO_UD, ALTRO_ANNUALITA
    }

    public enum TipoFatturazione {
        UNA_TANTUM, ANNUALE
    }

}
