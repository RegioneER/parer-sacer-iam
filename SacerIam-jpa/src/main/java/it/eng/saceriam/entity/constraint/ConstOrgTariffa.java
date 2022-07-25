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
public class ConstOrgTariffa {

    public enum TipoTariffa {
        VALORE_FISSO, VALORE_SCAGLIONI_STORAGE, VALORE_SCAGLIONI_UD_VERSATE, VALORE_UNITARIO_SCAGLIONI_STORAGE;

        public static TipoTariffa[] getEnums(TipoTariffa... vals) {
            return vals;
        }

        public static TipoTariffa[] getTipoTariffaSoloValoreFisso() {
            return getEnums(VALORE_FISSO);
        }

    }

}
