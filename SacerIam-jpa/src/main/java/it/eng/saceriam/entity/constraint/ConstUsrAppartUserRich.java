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
public class ConstUsrAppartUserRich {

    public enum TiAppartUserRich {
        UTENTE_CODIFICATO, UTENTE_NON_CODIFICATO
    }

    // public static String[] tiAzioneRich = {"Richiesta di cessazione", "Richiesta di creazione", "Richiesta di
    // modifica abilitazioni", "Richiesta di riattivazione"};
    public enum TiAzioneRich {
        RICHIESTA_CESSAZIONE("Richiesta di cessazione"), RICHIESTA_CREAZIONE("Richiesta di creazione"),
        RICHIESTA_MODIFICA_ABILITAZIONI("Richiesta di modifica abilitazioni"),
        RICHIESTA_RIATTIVZIONE("Richiesta di riattivazione"), RICHIESTA_RESET_PWD("Richiesta di reset password"),
        RICHIESTA_MODIFICA_ENTE_APPARTENENZA("Richiesta di modifica ente di appartenenza"),
        RICHIESTA_MODIFICA_ANAGRAFICA("Richiesta di modifica anagrafica"),
        RICHIESTA_DISATTIVAZIONE("Richiesta di disattivazione");

        private final String descrizione;

        public String getDescrizione() {
            return descrizione;
        }

        private TiAzioneRich(String descrizione) {
            this.descrizione = descrizione;
        }

        public static TiAzioneRich getEnum(String descrizione) {
            TiAzioneRich azione = null;
            for (TiAzioneRich value : values()) {
                if (value.getDescrizione().equalsIgnoreCase(descrizione)) {
                    azione = value;
                }
            }
            if (azione == null) {
                throw new IllegalArgumentException("Descrizione non valida");
            }
            return azione;
        }

        public static TiAzioneRich[] getEnums(TiAzioneRich... vals) {
            return vals;
        }

        public static TiAzioneRich[] getListaEnumNonConvenz() {
            return getEnums(RICHIESTA_CESSAZIONE, RICHIESTA_CREAZIONE, RICHIESTA_RIATTIVZIONE, RICHIESTA_RESET_PWD,
                    RICHIESTA_MODIFICA_ENTE_APPARTENENZA, RICHIESTA_MODIFICA_ANAGRAFICA, RICHIESTA_DISATTIVAZIONE);
        }

        public static TiAzioneRich[] getListaEnumConvenz() {
            return getEnums(RICHIESTA_CESSAZIONE, RICHIESTA_CREAZIONE, RICHIESTA_RIATTIVZIONE, RICHIESTA_RESET_PWD,
                    RICHIESTA_MODIFICA_ENTE_APPARTENENZA, RICHIESTA_MODIFICA_ABILITAZIONI,
                    RICHIESTA_MODIFICA_ANAGRAFICA, RICHIESTA_DISATTIVAZIONE);
        }
    }

}
