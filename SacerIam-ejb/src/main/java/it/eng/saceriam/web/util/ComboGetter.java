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

package it.eng.saceriam.web.util;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import it.eng.parer.sacerlog.entity.constraint.ConstLogEventoLoginUser;
import it.eng.saceriam.entity.constraint.ConstOrgAccordoEnte;
import it.eng.saceriam.entity.constraint.ConstOrgCollegEntiConvenz;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstOrgGestioneAccordo;
import it.eng.saceriam.entity.constraint.ConstOrgStatoFatturaEnte;
import it.eng.saceriam.entity.constraint.ConstOrgTariffa;
import it.eng.saceriam.entity.constraint.ConstOrgTipoServizio;
import it.eng.saceriam.entity.constraint.ConstPrfRuolo;
import it.eng.saceriam.entity.constraint.ConstUsrAppartUserRich;
import it.eng.saceriam.entity.constraint.ConstUsrRichGestUser;
import it.eng.saceriam.entity.constraint.ConstUsrStatoUser;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.base.table.BaseTable;
import it.eng.spagoLite.db.decodemap.DecodeMapIF;
import it.eng.spagoLite.db.oracle.decode.DecodeMap;

/**
 *
 * @author Gilioli_P
 */
public class ComboGetter {

    public static final String CAMPO_REGISTRO_AG = "registro_ag";
    public static final String CAMPO_TI_GESTIONE_PARAM = "ti_gestione_param";
    public static final String CAMPO_TIPOLOGIA = "tipologia";
    public static final String CAMPO_VALORE = "valore";
    public static final String CAMPO_FLAG = "flag";
    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_ANNO = "anno";

    /*
     * GESTIONE DECODEMAP GENERICHE
     */
    @SafeVarargs
    public static <T extends Enum<?>> DecodeMap getMappaSortedGenericEnum(String key, T... enumerator) {
        BaseTable bt = new BaseTable();
        DecodeMap mappa = new DecodeMap();
        for (T mod : sortEnum(enumerator)) {
            bt.add(createKeyValueBaseRow(key, mod.name()));
        }
        mappa.populatedMap(bt, key, key);
        return mappa;
    }

    @SafeVarargs
    public static <T extends Enum<?>> DecodeMap getMappaOrdinalGenericEnum(String key, T... enumerator) {
        BaseTable bt = new BaseTable();
        DecodeMap mappa = new DecodeMap();
        for (T mod : enumerator) {
            bt.add(createKeyValueBaseRow(key, mod.name()));
        }
        mappa.populatedMap(bt, key, key);
        return mappa;
    }

    private static BaseRow createKeyValueBaseRow(String key, String value) {

        BaseRow br = new BaseRow();
        br.setString(key, value);
        return br;
    }

    private static BaseRow createKeyValueBaseRow(String key, String value, String key2, String value2) {

        BaseRow br = new BaseRow();
        br.setString(key, value);
        br.setString(key2, value2);
        return br;
    }

    /*
     * GESTIONE TIPO OPERAZIONE
     */
    public static DecodeMapIF getMappaTiOperReplic() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_oper_replic";
        for (ApplEnum.TiOperReplic oper : sortEnum(ApplEnum.TiOperReplic.values())) {
            bt.add(createKeyValueBaseRow(key, oper.name()));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    /*
     * GESTIONE TIPO STATO REPLICA
     */
    public static DecodeMapIF getMappaTiStatoReplic() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_stato_replic";
        for (ApplEnum.TiStatoReplic stato : sortEnum(ApplEnum.TiStatoReplic.values())) {
            bt.add(createKeyValueBaseRow(key, stato.name()));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    /*
     * GESTIONE TIPO STATO REPLICA "LIMITATO"
     */
    public static DecodeMapIF getMappaTiStatoReplicForSched() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_stato_replic";
        for (ApplEnum.TiStatoReplicForSched stato : sortEnum(ApplEnum.TiStatoReplicForSched.values())) {
            bt.add(createKeyValueBaseRow(key, stato.name()));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    /*
     * GESTIONE SCOPO DICH ABIL ORGANIZ
     */
    public static DecodeMapIF getMappaDichOrganiz(boolean isEnteAmministratore) {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaDich = new DecodeMap();
        String key = "dich";
        if (isEnteAmministratore) {
            for (ApplEnum.TiScopoDichAbilOrganiz abil : sortEnum(ApplEnum.TiScopoDichAbilOrganiz.values())) {
                bt.add(createKeyValueBaseRow(key, abil.name()));
            }
        } else {
            for (ApplEnum.TiScopoDichAbilOrganiz abil : sortEnum(
                    ApplEnum.TiScopoDichAbilOrganiz.getComboScopiNonAllOrg())) {
                bt.add(createKeyValueBaseRow(key, abil.name()));
            }
        }
        mappaDich.populatedMap(bt, key, key);
        return mappaDich;
    }

    /*
     * GESTIONE SCOPO DICH ABIL TIPI DATO
     */
    public static DecodeMapIF getMappaDichDato(boolean isEnteAmministratore) {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaDich = new DecodeMap();
        String key = "dich";
        if (isEnteAmministratore) {
            for (ApplEnum.TiScopoDichAbilDati abil : sortEnum(ApplEnum.TiScopoDichAbilDati.values())) {
                bt.add(createKeyValueBaseRow(key, abil.name()));
            }
        } else {
            for (ApplEnum.TiScopoDichAbilDati abil : sortEnum(ApplEnum.TiScopoDichAbilDati.getComboScopiNonAllOrg())) {
                bt.add(createKeyValueBaseRow(key, abil.name()));
            }
        }
        mappaDich.populatedMap(bt, key, key);
        return mappaDich;
    }

    /*
     * GESTIONE SCOPO DICH ABIL ENTE CONVENZ
     */
    public static DecodeMapIF getMappaDichAbilEnteConvenz(String tipoEnte) {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaDich = new DecodeMap();
        String key = "dich";
        for (ApplEnum.ScopoDichAbilEnteConvenz abil : sortEnum(
                ApplEnum.ScopoDichAbilEnteConvenz.getComboScopiNonAllOrg(tipoEnte))) {
            bt.add(createKeyValueBaseRow(key, abil.name()));
        }
        mappaDich.populatedMap(bt, key, key);
        return mappaDich;
    }

    /*
     * GESTIONE DECODEMAP GENERICHE
     */
    public static DecodeMapIF getMappaGenericFlagSiNo() {
        BaseTable bt = new BaseTable();
        BaseRow br = new BaseRow();
        BaseRow br1 = new BaseRow();
        // Imposto i valori della combo INDICATORE
        DecodeMap mappaIndicatore = new DecodeMap();
        br.setString("flag", ApplEnum.ComboFlag.SI.name());
        br.setString("valore", ApplEnum.ComboFlag.SI.getValue());
        bt.add(br);
        br1.setString("flag", ApplEnum.ComboFlag.NO.name());
        br1.setString("valore", ApplEnum.ComboFlag.NO.getValue());
        bt.add(br1);
        mappaIndicatore.populatedMap(bt, "valore", "flag");
        return mappaIndicatore;
    }

    public static DecodeMapIF getMappaGenericFlagNo() {
        BaseTable bt = new BaseTable();
        BaseRow br = new BaseRow();
        // Imposto i valori della combo INDICATORE
        DecodeMap mappaIndicatore = new DecodeMap();
        br.setString("flag", ApplEnum.ComboFlag.NO.name());
        br.setString("valore", ApplEnum.ComboFlag.NO.getValue());
        bt.add(br);
        mappaIndicatore.populatedMap(bt, "valore", "flag");
        return mappaIndicatore;
    }

    public static DecodeMapIF getMappaTipoUser(String tipoUserAdmin) {
        BaseTable bt = new BaseTable();
        DecodeMap mappaTipoUser = new DecodeMap();
        String key = "tipo_user";
        BaseRow br = new BaseRow();
        br.setString(key, ApplEnum.TipoUser.PERSONA_FISICA.name());
        bt.add(br);
        // }
        mappaTipoUser.populatedMap(bt, key, key);
        return mappaTipoUser;
    }

    public static DecodeMapIF getMappaTiStatoUser() {
        BaseTable bt = new BaseTable();
        DecodeMap mappaTiStatoUser = new DecodeMap();
        String key = "ti_stato_user";
        String key2 = "desc_ti_stato_user";
        /* Imposto i valori della combo */
        for (ConstUsrStatoUser.TiStatotUser tiStatoUser : sortEnum(
                ConstUsrStatoUser.TiStatotUser.getComboFiltriRicercaUtente())) {
            bt.add(createKeyValueBaseRow(key, tiStatoUser.name(), key2, tiStatoUser.getDescrizione()));
        }
        mappaTiStatoUser.populatedMap(bt, key2, key2);
        return mappaTiStatoUser;
    }

    public static DecodeMapIF getMappaTiStatoUserPerRicerca() {
        BaseTable bt = new BaseTable();
        BaseRow br = new BaseRow();
        BaseRow br1 = new BaseRow();
        BaseRow br2 = new BaseRow();
        DecodeMap mappaTiStatoUser = new DecodeMap();
        String key = "ti_stato_user";
        /* Imposto i valori della combo */
        br.setString("ti_stato_user", ConstUsrStatoUser.TiStatotUser.ATTIVO.name());
        br1.setString("ti_stato_user", ConstUsrStatoUser.TiStatotUser.CESSATO.name());
        br2.setString("ti_stato_user", ConstUsrStatoUser.TiStatotUser.DISATTIVO.name());
        bt.add(br);
        bt.add(br1);
        bt.add(br2);

        mappaTiStatoUser.populatedMap(bt, key, key);
        return mappaTiStatoUser;
    }

    public static DecodeMapIF getMappaTiStatoAccordo() {
        BaseTable bt = new BaseTable();
        BaseRow br = new BaseRow();
        BaseRow br1 = new BaseRow();
        BaseRow br2 = new BaseRow();
        BaseRow br3 = new BaseRow();
        BaseRow br4 = new BaseRow();
        BaseRow br5 = new BaseRow();
        DecodeMap mappaTiStatoAccordo = new DecodeMap();
        String key = "ti_stato_accordo";
        /* Imposto i valori della combo */
        br.setString(key, "Accordo valido");
        br1.setString(key, "Accordo valido in corso rinnovo");
        br2.setString(key, "Accordo non valido per recesso");
        br3.setString(key, "Accordo non valido per scadenza");
        br4.setString(key, "Accordo in corso di stipula");
        br5.setString(key, "Accordo non valido");
        bt.add(br);
        bt.add(br1);
        bt.add(br2);
        bt.add(br3);
        bt.add(br4);
        bt.add(br5);

        mappaTiStatoAccordo.populatedMap(bt, key, key);
        return mappaTiStatoAccordo;
    }

    public static DecodeMapIF getMappaCdAlgoTariffario() {
        BaseTable bt = new BaseTable();
        BaseRow br = new BaseRow();
        BaseRow br1 = new BaseRow();
        DecodeMap mappaAlgo = new DecodeMap();
        String key = "cd_algo_tariffario";
        /* Imposto i valori della combo */
        br.setString(key, "CLASSE_ENTE");
        br1.setString(key, "NO_CLASSE_ENTE");
        bt.add(br);
        bt.add(br1);

        mappaAlgo.populatedMap(bt, key, key);
        return mappaAlgo;
    }

    public static DecodeMapIF getMappaTipoTrasmissione() {
        BaseTable bt = new BaseTable();
        DecodeMap mappaTipoTrasmissione = new DecodeMap();
        String key = "tipo_trasmissione";
        /* Imposto i valori della combo */
        for (ConstOrgGestioneAccordo.TipoTrasmissione tipoTrasmissione : sortEnum(
                ConstOrgGestioneAccordo.TipoTrasmissione.values())) {
            bt.add(createKeyValueBaseRow(key, tipoTrasmissione.name()));
        }
        mappaTipoTrasmissione.populatedMap(bt, key, key);
        return mappaTipoTrasmissione;
    }

    public static DecodeMapIF getMappaTiClasseTipoServizio() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTiClasse = new DecodeMap();
        String key = "ti_classe_tipo_servizio";
        for (ConstOrgTipoServizio.TiClasseTipoServizio classe : sortEnum(
                ConstOrgTipoServizio.TiClasseTipoServizio.values())) {
            bt.add(createKeyValueBaseRow(key, classe.name()));
        }
        mappaTiClasse.populatedMap(bt, key, key);
        return mappaTiClasse;
    }

    public static DecodeMapIF getMappaTipoFatturazione() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_classe_tipo_servizio";
        for (ConstOrgTipoServizio.TipoFatturazione tipo : sortEnum(ConstOrgTipoServizio.TipoFatturazione.values())) {
            bt.add(createKeyValueBaseRow(key, tipo.name()));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTipoTariffa() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "tipo_tariffa";
        for (ConstOrgTariffa.TipoTariffa tipo : sortEnum(ConstOrgTariffa.TipoTariffa.values())) {
            bt.add(createKeyValueBaseRow(key, tipo.name()));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTipoTariffaSoloValoreFisso() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "tipo_tariffa";
        for (ConstOrgTariffa.TipoTariffa tipo : sortEnum(ConstOrgTariffa.TipoTariffa.getTipoTariffaSoloValoreFisso())) {
            bt.add(createKeyValueBaseRow(key, tipo.name()));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTiColleg() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_colleg";
        for (ConstOrgCollegEntiConvenz.TiColleg tipo : sortEnum(ConstOrgCollegEntiConvenz.TiColleg.values())) {
            bt.add(createKeyValueBaseRow(key, tipo.name()));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTiStatoRichGestUser() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_cd_ente_convenz";
        for (String tipo : ConstOrgEnteSiam.tiCdEnteConvenz) {
            bt.add(createKeyValueBaseRow(key, tipo));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTiScopoAccordo() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTiScopoAccordo = new DecodeMap();
        String key = "ti_scopo_accordo";
        for (ConstOrgAccordoEnte.TiScopoAccordo scopo : sortEnum(ConstOrgAccordoEnte.TiScopoAccordo.values())) {
            bt.add(createKeyValueBaseRow(key, scopo.name()));
        }
        mappaTiScopoAccordo.populatedMap(bt, key, key);
        return mappaTiScopoAccordo;
    }

    public static DecodeMapIF getMappaTiEnteNonConvenz() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_ente_non_convenz";
        ConstOrgEnteSiam.TiEnteNonConvenz tipo = ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA;
        ConstOrgEnteSiam.TiEnteNonConvenz tipo2 = ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO;
        ConstOrgEnteSiam.TiEnteNonConvenz tipo3 = ConstOrgEnteSiam.TiEnteNonConvenz.SOGGETTO_ATTUATORE;
        bt.add(createKeyValueBaseRow(key, tipo.name()));
        bt.add(createKeyValueBaseRow(key, tipo2.name()));
        bt.add(createKeyValueBaseRow(key, tipo3.name()));
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTiStatoFatturaEnte() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaStato = new DecodeMap();
        String key = "ti_stato_fattura_ente";
        for (ConstOrgStatoFatturaEnte.TiStatoFatturaEnte stato : sortEnum(
                ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.values())) {
            bt.add(createKeyValueBaseRow(key, stato.getDescrizione()));
        }
        mappaStato.populatedMap(bt, key, key);
        return mappaStato;
    }

    public static DecodeMapIF getTiRuolo() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipoRuolo = new DecodeMap();
        String key = "ti_ruolo";
        for (ConstPrfRuolo.TiRuolo tipoRuolo : sortEnum(ConstPrfRuolo.TiRuolo.values())) {
            bt.add(createKeyValueBaseRow(key, tipoRuolo.name()));
        }
        mappaTipoRuolo.populatedMap(bt, key, key);
        return mappaTipoRuolo;
    }

    public static DecodeMapIF getTiCategRuolo() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaCategRuolo = new DecodeMap();
        String key = "ti_categ_ruolo";
        for (ConstPrfRuolo.TiCategRuolo categRuolo : sortEnum(ConstPrfRuolo.TiCategRuolo.values())) {
            bt.add(createKeyValueBaseRow(key, categRuolo.name()));
        }
        mappaCategRuolo.populatedMap(bt, key, key);
        return mappaCategRuolo;
    }

    public static DecodeMapIF getTiStatoRichGestUser() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaStatoRich = new DecodeMap();
        String key = "ti_stato_rich_gest_user";
        for (ConstUsrRichGestUser.TiStatoRichGestUser tipoStato : sortEnum(
                ConstUsrRichGestUser.TiStatoRichGestUser.values())) {
            bt.add(createKeyValueBaseRow(key, tipoStato.name()));
        }
        mappaStatoRich.populatedMap(bt, key, key);
        return mappaStatoRich;
    }

    public static DecodeMapIF getMappaTiRichGestUser() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_rich_gest_user";
        for (ConstUsrRichGestUser.TiRichGestUser tipo : sortEnum(ConstUsrRichGestUser.TiRichGestUser.values())) {
            bt.add(createKeyValueBaseRow(key, tipo.getDescrizione()));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTiRichGestUserRichConfUtenti() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_rich_gest_user";
        ConstUsrRichGestUser.TiRichGestUser tipo = ConstUsrRichGestUser.TiRichGestUser.COMUNICAZIONE_ONLINE;
        bt.add(createKeyValueBaseRow(key, tipo.getDescrizione()));
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTiRichGestUserRicRichieste() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_rich_gest_user";
        ConstUsrRichGestUser.TiRichGestUser tipo = ConstUsrRichGestUser.TiRichGestUser.COMUNICAZIONE_PROTOCOLLATA;
        ConstUsrRichGestUser.TiRichGestUser tipo2 = ConstUsrRichGestUser.TiRichGestUser.EMAIL;
        bt.add(createKeyValueBaseRow(key, tipo.getDescrizione()));
        bt.add(createKeyValueBaseRow(key, tipo2.getDescrizione()));
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTiAzioneRich(String tipoEnte) {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "codice_ti_azione_rich";
        String key2 = "ti_azione_rich";
        ConstUsrAppartUserRich.TiAzioneRich[] tipiAzione = null;
        if (tipoEnte.equals("NON_CONVENZIONATO")) {
            tipiAzione = ConstUsrAppartUserRich.TiAzioneRich.getListaEnumNonConvenz();
        } else {
            tipiAzione = ConstUsrAppartUserRich.TiAzioneRich.getListaEnumConvenz();
        }
        for (ConstUsrAppartUserRich.TiAzioneRich tipo : sortEnum(tipiAzione)) {
            bt.add(createKeyValueBaseRow(key, tipo.name(), key2, tipo.getDescrizione()));
        }
        mappaTipo.populatedMap(bt, key2, key2);
        return mappaTipo;
    }

    public static DecodeMapIF getTiScopoRuolo(String scopoDichAbilOrganiz) {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_scopo_ruolo";
        bt.add(createKeyValueBaseRow(key, ActionEnums.ScopoDichAbilOrganiz.UNA_ORG.name()));
        if (!scopoDichAbilOrganiz.equals(ActionEnums.ScopoDichAbilOrganiz.UNA_ORG.name())) {
            bt.add(createKeyValueBaseRow(key, ActionEnums.ScopoDichAbilOrganiz.ALL_ORG_CHILD.name()));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTipoEvento() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipoEvento = new DecodeMap();
        String key = "tipoEvento";
        for (ConstLogEventoLoginUser.TipoEvento tipoEvento : sortEnum(ConstLogEventoLoginUser.TipoEvento.values())) {
            bt.add(createKeyValueBaseRow(key, tipoEvento.name()));
        }
        mappaTipoEvento.populatedMap(bt, key, key);
        return mappaTipoEvento;
    }

    public static DecodeMapIF getMappaTiGestioneParam() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTiGestioneParam = new DecodeMap();
        BaseRow r1 = new BaseRow();
        r1.setString("ti_gestione_param", "amministrazione");
        BaseRow r2 = new BaseRow();
        r2.setString("ti_gestione_param", "conservazione");
        BaseRow r3 = new BaseRow();
        r3.setString("ti_gestione_param", "gestione");
        bt.add(r1);
        bt.add(r2);
        bt.add(r3);
        mappaTiGestioneParam.populatedMap(bt, "ti_gestione_param", "ti_gestione_param");
        return mappaTiGestioneParam;
    }

    public static DecodeMapIF getMappaTiCategRuolo() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTipo = new DecodeMap();
        String key = "ti_categ_ruolo";
        for (ApplEnum.TipoUser categ : sortEnum(ApplEnum.TipoUser.getComboTipiCategoriaPerRuoli())) {
            bt.add(createKeyValueBaseRow(key, categ.name()));
        }
        mappaTipo.populatedMap(bt, key, key);
        return mappaTipo;
    }

    public static DecodeMapIF getMappaTiModParam() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTiModPagam = new DecodeMap();
        BaseRow r1 = new BaseRow();
        r1.setString("ti_mod_pagam", "conto tesoreria unica");
        BaseRow r2 = new BaseRow();
        r2.setString("ti_mod_pagam", "conto IBAN");
        bt.add(r1);
        bt.add(r2);
        mappaTiModPagam.populatedMap(bt, "ti_mod_pagam", "ti_mod_pagam");
        return mappaTiModPagam;
    }

    public static DecodeMapIF getMappaQualificaUser() {
        BaseTable bt = new BaseTable();
        /* Imposto i valori della combo */
        DecodeMap mappaTiModPagam = new DecodeMap();
        BaseRow r1 = new BaseRow();
        r1.setString("qualifica_user", "Referente dell'ente");
        BaseRow r2 = new BaseRow();
        r2.setString("qualifica_user", "Responsabile della conservazione");
        bt.add(r1);
        bt.add(r2);
        mappaTiModPagam.populatedMap(bt, "qualifica_user", "qualifica_user");
        return mappaTiModPagam;
    }

    // MEV26588
    public static DecodeMapIF getTiValoreParamApplicCombo() {
        BaseTable bt = new BaseTable();
        BaseRow br = new BaseRow();
        BaseRow br1 = new BaseRow();

        DecodeMap mappaTipiValori = new DecodeMap();
        br.setString(CAMPO_NOME, Constants.ComboValueParamentersType.STRINGA.name());
        br.setString(CAMPO_VALORE, Constants.ComboValueParamentersType.STRINGA.name());
        bt.add(br);
        br1.setString(CAMPO_NOME, Constants.ComboValueParamentersType.PASSWORD.name());
        br1.setString(CAMPO_VALORE, Constants.ComboValueParamentersType.PASSWORD.name());
        bt.add(br1);
        mappaTipiValori.populatedMap(bt, CAMPO_VALORE, CAMPO_NOME);
        return mappaTipiValori;
    }

    public static DecodeMap getMappaTiStatoJob() {
        BaseTable bt = new BaseTable();
        BaseRow br = new BaseRow();
        BaseRow br1 = new BaseRow();
        BaseRow br2 = new BaseRow();
        DecodeMap mappaStatoAgg = new DecodeMap();
        br.setString("ti_stato_job", "ATTIVO");
        bt.add(br);
        br1.setString("ti_stato_job", "DISATTIVO");
        bt.add(br1);
        br2.setString("ti_stato_job", "IN_ESECUZIONE");
        bt.add(br2);
        mappaStatoAgg.populatedMap(bt, "ti_stato_job", "ti_stato_job");
        return mappaStatoAgg;
    }

    private static <T extends Enum<?>> Collection<T> sortEnum(T[] enumValues) {
        SortedMap<String, T> map = new TreeMap<>();
        for (T val : enumValues) {
            map.put(val.name(), val);
        }
        return map.values();
    }

}
