package it.eng.saceriam.web.util;

public class ApplEnum {

    public enum ScopoDichAbilStrut {

        TUTTI_AMBIENTI, UN_AMBIENTE, UN_ENTE, UNA_STRUTTURA, STRUTTURA_DEFAULT
    }

    public enum ScopoDichAbilDati {

        TUTTI_AMBIENTI, UN_AMBIENTE, UN_ENTE, UNA_STRUTTURA, UNA_ABILITAZIONE
    }

    public enum ScopoDichAbilEnteConvenz {

        ALL_AMBIENTI, UN_AMBIENTE, UN_ENTE;

        public static ScopoDichAbilEnteConvenz[] getEnums(ScopoDichAbilEnteConvenz... vals) {
            return vals;
        }

        public static ScopoDichAbilEnteConvenz[] getComboScopiNonAllOrg(String tipoEnte) {

            switch (tipoEnte) {
            case "AMMINISTRATORE":
            default:
                return getEnums(ALL_AMBIENTI, UN_AMBIENTE, UN_ENTE);
            case "CONSERVATORE":
            case "GESTORE":
                return getEnums(UN_AMBIENTE, UN_ENTE);
            case "PRODUTTORE":
                return getEnums(UN_ENTE);
            }
        }
    }

    public enum TiScopoDichAbilOrganiz {
        ALL_ORG, ALL_ORG_CHILD, UNA_ORG, ORG_DEFAULT;

        public static TiScopoDichAbilOrganiz[] getEnums(TiScopoDichAbilOrganiz... vals) {
            return vals;
        }

        public static TiScopoDichAbilOrganiz[] getComboScopiNonAllOrg() {
            return getEnums(ALL_ORG_CHILD, UNA_ORG, ORG_DEFAULT);
        }

    }

    public enum TiScopoDichAbilDati {
        ALL_ORG, ALL_ORG_CHILD, UNA_ORG, UN_TIPO_DATO;

        public static TiScopoDichAbilDati[] getEnums(TiScopoDichAbilDati... vals) {
            return vals;
        }

        public static TiScopoDichAbilDati[] getComboScopiNonAllOrg() {
            return getEnums(ALL_ORG_CHILD, UNA_ORG, UN_TIPO_DATO);
        }
    }

    public enum TipoDichAbilDati {

        TIPO_UD, REGISTRO_UD
    }

    public enum TipoFiltroMultiploCriteriRaggr {

        RANGE_REGISTRO_UNI_DOC, REGISTRO_UNI_DOC, TIPO_DOC, TIPO_ESITO_VERIF_FIRME, TIPO_UNI_DOC, SISTEMA_MIGRAZ
    }

    public enum TiOperReplic {

        INS, MOD, CANC
    }

    public enum TiStatoReplic {

        DA_REPLICARE, REPLICA_IN_ERRORE, REPLICA_IN_TIMEOUT, REPLICA_NON_POSSIBILE, REPLICA_OK, REPLICA_IN_CORSO
    }

    public enum TiStatoReplicForSched {

        REPLICA_IN_ERRORE, REPLICA_IN_TIMEOUT, REPLICA_NON_POSSIBILE
    }

    public enum NmJob {

        REPLICA_UTENTI
    }

    public enum ComboFlag {

        SI("1"), NO("0");

        private String value;

        private ComboFlag(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum TipoRuolo {

        DEF, DICH
    }

    public enum TipoUser {

        PERSONA_FISICA, AUTOMA, NON_DI_SISTEMA, amministrazione, conservazione, gestione, produzione, supporto_esterno,
        vigilanza;

        public static TipoUser[] getEnums(TipoUser... vals) {
            return vals;
        }

        public static TipoUser[] getComboTipiTotali() {
            return getEnums(PERSONA_FISICA, AUTOMA, NON_DI_SISTEMA);
        }

        public static TipoUser[] getComboRicercaUtenti() {
            return getEnums(PERSONA_FISICA, AUTOMA, NON_DI_SISTEMA);
        }

        public static TipoUser[] getComboTipiPerRuoli() {
            return getEnums(PERSONA_FISICA, AUTOMA);
        }

        public static TipoUser[] getComboTipiCategoriaPerRuoli() {
            return getEnums(amministrazione, conservazione, gestione, produzione, supporto_esterno, vigilanza);
        }

        public static TipoUser[] getComboTipiNonDiSistema() {
            return getEnums(NON_DI_SISTEMA);
        }

    }

    public enum TipoAuth {

        AUTH_CERT;

        public static TipoAuth[] getEnums(TipoAuth... vals) {
            return vals;
        }

        public static TipoAuth[] getComboTipiAuth() {
            return getEnums(AUTH_CERT);
        }

    }

}
