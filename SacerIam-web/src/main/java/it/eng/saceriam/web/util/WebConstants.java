/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.web.util;

/**
 *
 * @author Iacolucci_M
 */
public class WebConstants {

    public static final String MIME_TYPE_MICROSOFT_WORD = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String MIME_TYPE_GENERIC = "application/octet-stream";

    // Nome parametri Dettaglio fatture
    public static final String PARAMETER_FATTURE = "ListaFatture";

    public enum DOWNLOAD_ATTRS {

        DOWNLOAD_ACTION, DOWNLOAD_FILENAME, DOWNLOAD_FILEPATH, DOWNLOAD_DELETEFILE, DOWNLOAD_CONTENTTYPE
    }

    public enum TipoAmbitoTerritoriale {

        FORMA_ASSOCIATA("FORMA_ASSOCIATA"), PROVINCIA("PROVINCIA"), REGIONE_STATO("REGIONE/STATO");

        private String nome;

        public String getNome() {
            return nome;
        }

        private TipoAmbitoTerritoriale(String nome) {
            this.nome = nome;
        }
    }

}
