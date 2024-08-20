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
package it.eng.saceriam.amministrazioneEntiConvenzionati.helper;

import it.eng.saceriam.entity.*;
import it.eng.saceriam.entity.constraint.*;
import it.eng.saceriam.helper.GenericHelper;
import it.eng.saceriam.web.util.ApplEnum;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static it.eng.ArquillianUtils.getSacerIamArchive;
import static org.junit.Assert.*;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class MappingCascadeTest {
    public static final String JUNIT_ARQUILLIAN = "JUNIT - ARQUILLIAN";
    @EJB
    private GenericHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        final JavaArchive archive = getSacerIamArchive(EntiConvenzionatiHelper.class)
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity", "oracle.jdbc")
                .addClasses(it.eng.saceriam.amministrazioneEntiConvenzionati.dto.ServizioFatturatoBean.class,
                        it.eng.saceriam.grantedViewEntity.DecVLisTiUniDocAms.class, MappingCascadeTest.class,
                        ApplEnum.class);
        return archive;
    }

    @Test
    public void inserisciOrgAaAccordo_nonInserisceAutomaticamenteFigli() {
        Long idAccordo = null;
        Long idTariffa = null;
        Long idServizio = null;
        try {
            OrgAaAccordo accordo = new OrgAaAccordo();
            final OrgAccordoEnte orgAccordoEnte = new OrgAccordoEnte();
            orgAccordoEnte.setIdAccordoEnte(2763L);
            accordo.setDsNotaAaAccordo("Inserito da un test automatico");
            accordo.setOrgAccordoEnte(orgAccordoEnte);
            accordo.setAaAnnoAccordo(BigDecimal.valueOf(1985));
            accordo.setMmAaAccordo(BigDecimal.valueOf(5));

            List<OrgTariffaAaAccordo> tariffe = new ArrayList<>();
            OrgTariffaAaAccordo tariffa = new OrgTariffaAaAccordo();
            tariffa.setOrgAaAccordo(accordo);
            OrgTipoServizio tipoServizio = new OrgTipoServizio();
            tipoServizio.setIdTipoServizio(241L);
            tariffa.setOrgTipoServizio(tipoServizio);
            tariffa.setImTariffaAaAccordo(BigDecimal.valueOf(1985));

            OrgServizioErog servizioErog = new OrgServizioErog();
            OrgAccordoEnte accordoEnte = new OrgAccordoEnte();
            accordoEnte.setIdAccordoEnte(2983L);
            servizioErog.setOrgAccordoEnte(accordoEnte);
            servizioErog.setNmServizioErogato("Inserito da test automatico");
            servizioErog.setOrgTipoServizio(tipoServizio);
            servizioErog.setFlPagamento("0");
            servizioErog.setImValoreTariffa(BigDecimal.valueOf(1985));
            servizioErog.setOrgTariffaAaAccordo(tariffa);
            tariffa.setOrgServizioErogs(new ArrayList<>());
            tariffa.getOrgServizioErogs().add(servizioErog);

            tariffe.add(tariffa);
            accordo.setOrgTariffaAaAccordos(tariffe);

            helper.insertEntity(accordo, true);
            // non deve aver inserito org_tariffa_aa_accordo, la cascade non prevede persist
            idAccordo = accordo.getIdAaAccordo();
            idTariffa = accordo.getOrgTariffaAaAccordos().get(0).getIdTariffaAaAccordo();
            idServizio = accordo.getOrgTariffaAaAccordos().get(0).getOrgServizioErogs().get(0).getIdServizioErogato();
            assertNotNull(idAccordo);
            assertNull(idTariffa);
            assertNull(idServizio);
        } finally {
            delete(OrgAaAccordo.class, idAccordo);
            delete(OrgTariffaAaAccordo.class, idTariffa);
            delete(OrgServizioErog.class, idServizio);
        }
    }

    // @Test
    /*
     * INSERT INTO ORG_AA_ACCORDO (ID_AA_ACCORDO, ID_ACCORDO_ENTE, AA_ANNO_ACCORDO, MM_AA_ACCORDO) VALUES (-9, 2763,
     * 3020, 1);
     *
     * INSERT INTO ORG_TARIFFA_AA_ACCORDO (ID_TARIFFA_AA_ACCORDO,ID_AA_ACCORDO,ID_TIPO_SERVIZIO,IM_TARIFFA_AA_ACCORDO)
     * VALUES (-91,-9,241,1985);
     *
     * INSERT into ORG_SERVIZIO_EROG
     * (ID_SERVIZIO_EROGATO,ID_ACCORDO_ENTE,NM_SERVIZIO_EROGATO,ID_TIPO_SERVIZIO,FL_PAGAMENTO,IM_VALORE_TARIFFA,
     * ID_TARIFFA_AA_ACCORDO) VALUES (-911,2763,'test automatico', 241,'0',1900,-91);
     */
    /*
     * public void cancellaOrgAaAccordo_rimuoveCascataFigli(){ helper.removeById(OrgAaAccordo.class,Long.valueOf(-9)); }
     */

    @Test
    public void orgAccordoEnte_persistCascade() {
        Long idAccordo = null;
        Long idServizio = null;
        Long idModuloInfo = null;
        Long idGestione = null;
        try {
            OrgAccordoEnte accordoEnte = new OrgAccordoEnte();
            accordoEnte.setOrgEnteSiam(anEnteSiam());
            accordoEnte.setFlPagamento("0");
            accordoEnte.setFlRecesso("0");
            accordoEnte.setDsAttoAccordo("Da definire");
            accordoEnte.setDtAttoAccordo(new Date());
            accordoEnte.setTiScopoAccordo(ConstOrgAccordoEnte.TiScopoAccordo.GESTIONE);
            accordoEnte.setFlAccordoChiuso("1");
            accordoEnte.setDsFirmatarioEnte(JUNIT_ARQUILLIAN);
            OrgEnteSiam enteSiamAmministratore = anOrgEnteSiam();
            accordoEnte.setOrgEnteSiamByIdEnteConvenzAmministratore(enteSiamAmministratore);
            OrgEnteSiam enteSiamConserv = anOrgEnteSiam();
            accordoEnte.setOrgEnteSiamByIdEnteConvenzConserv(enteSiamConserv);
            OrgEnteSiam enteSiamConvenzGestore = new OrgEnteSiam();
            enteSiamConvenzGestore.setIdEnteSiam(Long.valueOf(4713621));
            accordoEnte.setOrgEnteSiamByIdEnteConvenzGestore(enteSiamConvenzGestore);
            accordoEnte.setDtFineValidAccordo(new Date());

            OrgServizioErog servizioErog = new OrgServizioErog();
            servizioErog.setOrgAccordoEnte(accordoEnte);
            servizioErog.setNmServizioErogato("Inserito da test automatici");
            OrgTipoServizio tipoServizio = new OrgTipoServizio();
            tipoServizio.setIdTipoServizio(241L);
            servizioErog.setOrgTipoServizio(tipoServizio);
            servizioErog.setFlPagamento("0");
            servizioErog.setImValoreTariffa(BigDecimal.valueOf(1985));

            accordoEnte.setOrgServizioErogs(new ArrayList<>());
            accordoEnte.getOrgServizioErogs().add(servizioErog);

            OrgModuloInfoAccordo moduloInfoAccordo = new OrgModuloInfoAccordo();
            moduloInfoAccordo.setOrgAccordoEnte(accordoEnte);
            moduloInfoAccordo.setDtRicev(new Date());

            accordoEnte.setOrgModuloInfoAccordos(new ArrayList<>());
            accordoEnte.getOrgModuloInfoAccordos().add(moduloInfoAccordo);

            OrgGestioneAccordo gestioneAccordo = new OrgGestioneAccordo();
            gestioneAccordo.setOrgAccordoEnte(accordoEnte);
            gestioneAccordo.setDtGestAccordo(new Date());
            gestioneAccordo.setPgGestAccordo(BigDecimal.valueOf(1985));
            gestioneAccordo.setDsGestAccordo("Inserito da test automatici");
            OrgTipiGestioneAccordo tipiGestioneAccordo = new OrgTipiGestioneAccordo();
            tipiGestioneAccordo.setIdTipoGestioneAccordo(1L);
            gestioneAccordo.setOrgTipiGestioneAccordo(tipiGestioneAccordo);
            gestioneAccordo.setTipoTrasmissione(ConstOrgGestioneAccordo.TipoTrasmissione.COMUNICAZIONE_PROTOCOLLATA);

            accordoEnte.setOrgGestioneAccordos(new ArrayList<>());
            accordoEnte.getOrgGestioneAccordos().add(gestioneAccordo);

            helper.insertEntity(accordoEnte, true);
            idAccordo = accordoEnte.getIdAccordoEnte();
            idServizio = accordoEnte.getOrgServizioErogs().get(0).getIdServizioErogato();
            idModuloInfo = accordoEnte.getOrgModuloInfoAccordos().get(0).getIdModuloInfoAccordo();
            idGestione = accordoEnte.getOrgGestioneAccordos().get(0).getIdGestAccordo();
            assertNotNull(idAccordo);
            assertNotNull(idServizio);
            assertNotNull(idModuloInfo);
            assertNotNull(idGestione);
        } finally {
            delete(OrgServizioErog.class, idServizio);
            delete(OrgModuloInfoAccordo.class, idModuloInfo);
            delete(OrgGestioneAccordo.class, idGestione);
            delete(OrgAccordoEnte.class, idAccordo);
        }
    }

    private OrgEnteSiam anOrgEnteSiam() {
        OrgEnteSiam enteSiamAmministratore = new OrgEnteSiam();
        enteSiamAmministratore.setIdEnteSiam(Long.valueOf(316984));
        return enteSiamAmministratore;
    }

    @Test
    public void orgAccordoEnte_removeCascade() {
        Long idAccordo = null;
        Long idServizio = null;
        Long idModuloInfo = null;
        Long idGestione = null;
        try {
            OrgAccordoEnte accordoEnte = new OrgAccordoEnte();
            accordoEnte.setOrgEnteSiam(anEnteSiam());
            accordoEnte.setFlPagamento("0");
            accordoEnte.setFlRecesso("0");
            accordoEnte.setDsAttoAccordo("Da definire");
            accordoEnte.setDtAttoAccordo(new Date());
            accordoEnte.setTiScopoAccordo(ConstOrgAccordoEnte.TiScopoAccordo.GESTIONE);
            accordoEnte.setFlAccordoChiuso("1");
            accordoEnte.setDsFirmatarioEnte(JUNIT_ARQUILLIAN);
            accordoEnte.setOrgEnteSiamByIdEnteConvenzAmministratore(anOrgEnteSiam());
            accordoEnte.setOrgEnteSiamByIdEnteConvenzConserv(anOrgEnteSiam());
            OrgEnteSiam enteSiamConvenzGestore = new OrgEnteSiam();
            enteSiamConvenzGestore.setIdEnteSiam(Long.valueOf(4713621));
            accordoEnte.setOrgEnteSiamByIdEnteConvenzGestore(enteSiamConvenzGestore);
            accordoEnte.setDtFineValidAccordo(new Date());

            OrgServizioErog servizioErog = new OrgServizioErog();
            servizioErog.setOrgAccordoEnte(accordoEnte);
            servizioErog.setNmServizioErogato("Inserito da test automatici");
            OrgTipoServizio tipoServizio = new OrgTipoServizio();
            tipoServizio.setIdTipoServizio(241L);
            servizioErog.setOrgTipoServizio(tipoServizio);
            servizioErog.setFlPagamento("0");
            servizioErog.setImValoreTariffa(BigDecimal.valueOf(1985));

            accordoEnte.setOrgServizioErogs(new ArrayList<>());
            accordoEnte.getOrgServizioErogs().add(servizioErog);

            OrgModuloInfoAccordo moduloInfoAccordo = new OrgModuloInfoAccordo();
            moduloInfoAccordo.setOrgAccordoEnte(accordoEnte);
            moduloInfoAccordo.setDtRicev(new Date());

            accordoEnte.setOrgModuloInfoAccordos(new ArrayList<>());
            accordoEnte.getOrgModuloInfoAccordos().add(moduloInfoAccordo);

            OrgGestioneAccordo gestioneAccordo = new OrgGestioneAccordo();
            gestioneAccordo.setOrgAccordoEnte(accordoEnte);
            gestioneAccordo.setDtGestAccordo(new Date());
            gestioneAccordo.setPgGestAccordo(BigDecimal.valueOf(1985));
            gestioneAccordo.setDsGestAccordo("Inserito da test automatici");
            OrgTipiGestioneAccordo tipiGestioneAccordo = new OrgTipiGestioneAccordo();
            tipiGestioneAccordo.setIdTipoGestioneAccordo(1L);
            gestioneAccordo.setOrgTipiGestioneAccordo(tipiGestioneAccordo);
            gestioneAccordo.setTipoTrasmissione(ConstOrgGestioneAccordo.TipoTrasmissione.COMUNICAZIONE_PROTOCOLLATA);

            accordoEnte.setOrgGestioneAccordos(new ArrayList<>());
            accordoEnte.getOrgGestioneAccordos().add(gestioneAccordo);

            helper.insertEntity(accordoEnte, true);
            idAccordo = accordoEnte.getIdAccordoEnte();
            idServizio = accordoEnte.getOrgServizioErogs().get(0).getIdServizioErogato();
            idModuloInfo = accordoEnte.getOrgModuloInfoAccordos().get(0).getIdModuloInfoAccordo();
            idGestione = accordoEnte.getOrgGestioneAccordos().get(0).getIdGestAccordo();
            helper.removeById(OrgAccordoEnte.class, idAccordo);
            assertNull(helper.findById(OrgAccordoEnte.class, idAccordo));
            assertNull(helper.findById(OrgServizioErog.class, idServizio));
            assertNull(helper.findById(OrgGestioneAccordo.class, idGestione));
            assertNull(helper.findById(OrgModuloInfoAccordo.class, idModuloInfo));
        } finally {
            delete(OrgServizioErog.class, idServizio);
            delete(OrgModuloInfoAccordo.class, idModuloInfo);
            delete(OrgGestioneAccordo.class, idGestione);
            delete(OrgAccordoEnte.class, idAccordo);
        }
    }

    private OrgEnteSiam anEnteSiam() {
        OrgEnteSiam enteSiam = new OrgEnteSiam();
        enteSiam.setIdEnteSiam(Long.valueOf(781));
        return enteSiam;
    }

    @Test
    public void orgFatturaEnte_persistCascade() {
        Long idFattura = null;
        Long idStato = null;
        Long idPagamento = null;
        Long idSollecito = null;
        try {
            OrgFatturaEnte fatturaEnte = new OrgFatturaEnte();
            fatturaEnte.setOrgEnteSiam(anEnteSiam());
            fatturaEnte.setAaFattura(BigDecimal.valueOf(2021));
            fatturaEnte.setPgFattura(BigDecimal.valueOf(1985));
            fatturaEnte.setImTotFattura(BigDecimal.valueOf(1985));
            fatturaEnte.setImTotIva(BigDecimal.valueOf(1985));

            OrgStatoFatturaEnte statoFatturaEnte = new OrgStatoFatturaEnte();
            statoFatturaEnte.setOrgFatturaEnte(fatturaEnte);
            statoFatturaEnte.setTiStatoFatturaEnte(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.name());
            statoFatturaEnte.setDtRegStatoFatturaEnte(new Date());
            fatturaEnte.setOrgStatoFatturaEntes(new ArrayList<>());
            fatturaEnte.getOrgStatoFatturaEntes().add(statoFatturaEnte);

            OrgPagamFatturaEnte pagamFatturaEnte = new OrgPagamFatturaEnte();
            pagamFatturaEnte.setOrgFatturaEnte(fatturaEnte);
            pagamFatturaEnte.setPgPagam(BigDecimal.valueOf(1985));
            pagamFatturaEnte.setDtPagam(new Date());
            pagamFatturaEnte.setImPagam(BigDecimal.valueOf(1985));
            fatturaEnte.setOrgPagamFatturaEntes(new ArrayList<>());
            fatturaEnte.getOrgPagamFatturaEntes().add(pagamFatturaEnte);

            OrgSollecitoFatturaEnte sollecitoFatturaEnte = new OrgSollecitoFatturaEnte();
            sollecitoFatturaEnte.setAaVarSollecito(BigDecimal.valueOf(1985));
            sollecitoFatturaEnte.setCdKeyVarSollecito(JUNIT_ARQUILLIAN);
            sollecitoFatturaEnte.setCdRegistroSollecito("1985");
            sollecitoFatturaEnte.setDlSollecito(JUNIT_ARQUILLIAN);
            sollecitoFatturaEnte.setDtSollecito(new Date());
            sollecitoFatturaEnte.setOrgFatturaEnte(fatturaEnte);
            fatturaEnte.setOrgSollecitoFatturaEntes(new ArrayList<>());
            fatturaEnte.getOrgSollecitoFatturaEntes().add(sollecitoFatturaEnte);

            helper.insertEntity(fatturaEnte, true);
            idFattura = fatturaEnte.getIdFatturaEnte();
            idStato = fatturaEnte.getOrgStatoFatturaEntes().get(0).getIdStatoFatturaEnte();
            idSollecito = fatturaEnte.getOrgSollecitoFatturaEntes().get(0).getIdSollecitoFatturaEnte();
            idPagamento = fatturaEnte.getOrgPagamFatturaEntes().get(0).getIdPagamFatturaEnte();

            assertNotNull(idFattura);
            assertNotNull(idStato);
            assertNotNull(idSollecito);
            assertNotNull(idPagamento);
        } finally {
            delete(OrgStatoFatturaEnte.class, idStato);
            delete(OrgPagamFatturaEnte.class, idPagamento);
            delete(OrgSollecitoFatturaEnte.class, idSollecito);
            delete(OrgFatturaEnte.class, idFattura);
        }

    }

    @Test
    public void orgStatoFatturaEnte_cascadePersist() {
        Long idStato = null;
        Long idFattura = null;
        try {
            OrgFatturaEnte fatturaEnte = new OrgFatturaEnte();
            fatturaEnte.setOrgEnteSiam(anEnteSiam());
            fatturaEnte.setAaFattura(BigDecimal.valueOf(2021));
            fatturaEnte.setPgFattura(BigDecimal.valueOf(1985));
            fatturaEnte.setImTotFattura(BigDecimal.valueOf(1985));
            fatturaEnte.setImTotIva(BigDecimal.valueOf(1985));

            OrgStatoFatturaEnte statoFatturaEnte = new OrgStatoFatturaEnte();
            statoFatturaEnte.setOrgFatturaEnte(fatturaEnte);
            statoFatturaEnte.setTiStatoFatturaEnte(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.name());
            statoFatturaEnte.setDtRegStatoFatturaEnte(new Date());
            fatturaEnte.setOrgStatoFatturaEntes(new ArrayList<>());
            fatturaEnte.getOrgStatoFatturaEntes().add(statoFatturaEnte);

            helper.insertEntity(statoFatturaEnte, true);
            idStato = statoFatturaEnte.getIdStatoFatturaEnte();
            idFattura = statoFatturaEnte.getOrgFatturaEnte().getIdFatturaEnte();
            assertNotNull(idStato);
            assertNotNull(idFattura);
        } finally {
            delete(OrgStatoFatturaEnte.class, idStato);
            delete(OrgFatturaEnte.class, idFattura);
        }
    }

    @Test
    public void orgStatoFatturaEnte_NOcascadeRemove() {
        Long idStato = null;
        Long idFattura = null;
        try {
            OrgFatturaEnte fatturaEnte = new OrgFatturaEnte();
            fatturaEnte.setOrgEnteSiam(anEnteSiam());
            fatturaEnte.setAaFattura(BigDecimal.valueOf(2021));
            fatturaEnte.setPgFattura(BigDecimal.valueOf(1985));
            fatturaEnte.setImTotFattura(BigDecimal.valueOf(1985));
            fatturaEnte.setImTotIva(BigDecimal.valueOf(1985));

            OrgStatoFatturaEnte statoFatturaEnte = new OrgStatoFatturaEnte();
            statoFatturaEnte.setOrgFatturaEnte(fatturaEnte);
            statoFatturaEnte.setTiStatoFatturaEnte(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.name());
            statoFatturaEnte.setDtRegStatoFatturaEnte(new Date());
            fatturaEnte.setOrgStatoFatturaEntes(new ArrayList<>());
            fatturaEnte.getOrgStatoFatturaEntes().add(statoFatturaEnte);

            helper.insertEntity(statoFatturaEnte, true);
            idStato = statoFatturaEnte.getIdStatoFatturaEnte();
            idFattura = statoFatturaEnte.getOrgFatturaEnte().getIdFatturaEnte();
            helper.removeById(OrgStatoFatturaEnte.class, idStato);
            // OrgFatturaEnte non dev'essere stata cancellata
            assertNull(helper.findById(OrgStatoFatturaEnte.class, idStato));
            assertNotNull(helper.findById(OrgFatturaEnte.class, idFattura));
        } finally {
            delete(OrgStatoFatturaEnte.class, idStato);
            delete(OrgFatturaEnte.class, idFattura);
        }
    }

    @Test
    public void orgTariffa_cascadePersist() {
        Long idTariffa = null;
        Long idScaglione = null;
        try {
            OrgTariffa tariffa = new OrgTariffa();
            tariffa.setNmTariffa(JUNIT_ARQUILLIAN);
            tariffa.setDsTariffa(JUNIT_ARQUILLIAN);

            OrgTariffario tariffario = new OrgTariffario();
            tariffario.setIdTariffario(142L);
            tariffa.setOrgTariffario(tariffario);

            OrgTipoServizio tipoServizio = new OrgTipoServizio();
            tipoServizio.setIdTipoServizio(201L);
            tariffa.setOrgTipoServizio(tipoServizio);

            tariffa.setTipoTariffa(ConstOrgTariffa.TipoTariffa.VALORE_SCAGLIONI_STORAGE.name());

            OrgScaglioneTariffa scaglioneTariffa = new OrgScaglioneTariffa();
            scaglioneTariffa.setImScaglione(BigDecimal.valueOf(1985));
            scaglioneTariffa.setNiFineScaglione(BigDecimal.valueOf(1985));
            scaglioneTariffa.setNiIniScaglione(BigDecimal.valueOf(1985));
            scaglioneTariffa.setOrgTariffa(tariffa);
            tariffa.setOrgScaglioneTariffas(new ArrayList<>());
            tariffa.getOrgScaglioneTariffas().add(scaglioneTariffa);

            helper.insertEntity(tariffa, true);
            idTariffa = tariffa.getIdTariffa();
            idScaglione = tariffa.getOrgScaglioneTariffas().get(0).getIdScaglioneTariffa();
            assertNotNull(idTariffa);
            assertNotNull(idScaglione);
        } finally {
            delete(OrgScaglioneTariffa.class, idScaglione);
            delete(OrgTariffa.class, idTariffa);
        }
    }

    @Test
    public void orgTariffa_cascadeRemove() {
        Long idTariffa = null;
        Long idScaglione = null;
        try {
            OrgTariffa tariffa = new OrgTariffa();
            tariffa.setNmTariffa(JUNIT_ARQUILLIAN);
            tariffa.setDsTariffa(JUNIT_ARQUILLIAN);

            OrgTariffario tariffario = new OrgTariffario();
            tariffario.setIdTariffario(142L);
            tariffa.setOrgTariffario(tariffario);

            OrgTipoServizio tipoServizio = new OrgTipoServizio();
            tipoServizio.setIdTipoServizio(201L);
            tariffa.setOrgTipoServizio(tipoServizio);

            tariffa.setTipoTariffa(ConstOrgTariffa.TipoTariffa.VALORE_SCAGLIONI_STORAGE.name());

            OrgScaglioneTariffa scaglioneTariffa = new OrgScaglioneTariffa();
            scaglioneTariffa.setImScaglione(BigDecimal.valueOf(1985));
            scaglioneTariffa.setNiFineScaglione(BigDecimal.valueOf(1985));
            scaglioneTariffa.setNiIniScaglione(BigDecimal.valueOf(1985));
            scaglioneTariffa.setOrgTariffa(tariffa);
            tariffa.setOrgScaglioneTariffas(new ArrayList<>());
            tariffa.getOrgScaglioneTariffas().add(scaglioneTariffa);

            helper.insertEntity(tariffa, true);
            idTariffa = tariffa.getIdTariffa();
            idScaglione = tariffa.getOrgScaglioneTariffas().get(0).getIdScaglioneTariffa();
            helper.removeById(OrgTariffa.class, idTariffa);
            assertNull(helper.findById(OrgTariffa.class, idTariffa));
            assertNull(helper.findById(OrgScaglioneTariffa.class, idScaglione));
        } finally {
            delete(OrgScaglioneTariffa.class, idScaglione);
            delete(OrgTariffa.class, idTariffa);
        }
    }

    @Test
    public void orgTariffaAaAccordo_cascadeRemove() {
        Long idServizio = null;
        Long idTariffaAaAccordo = null;
        try {
            OrgTipoServizio tipoServizio = new OrgTipoServizio();
            tipoServizio.setIdTipoServizio(201L);

            OrgTariffaAaAccordo tariffaAaAccordo = new OrgTariffaAaAccordo();
            tariffaAaAccordo.setImTariffaAaAccordo(BigDecimal.valueOf(1985));
            tariffaAaAccordo.setOrgTipoServizio(tipoServizio);

            OrgAaAccordo orgAaAccordo = new OrgAaAccordo();
            orgAaAccordo.setIdAaAccordo(61L);
            tariffaAaAccordo.setOrgAaAccordo(orgAaAccordo);
            helper.insertEntity(tariffaAaAccordo, true);
            idTariffaAaAccordo = tariffaAaAccordo.getIdTariffaAaAccordo();
            assertNotNull(idTariffaAaAccordo);

            OrgServizioErog servizioErog = new OrgServizioErog();
            servizioErog.setNmServizioErogato(JUNIT_ARQUILLIAN);
            servizioErog.setFlPagamento("0");
            servizioErog.setImValoreTariffa(BigDecimal.valueOf(1985));
            servizioErog.setOrgTipoServizio(tipoServizio);

            OrgAccordoEnte accordoEnte = new OrgAccordoEnte();
            accordoEnte.setIdAccordoEnte(601L);
            servizioErog.setOrgAccordoEnte(accordoEnte);

            servizioErog.setOrgTariffaAaAccordo(tariffaAaAccordo);

            helper.insertEntity(servizioErog, true);
            idServizio = servizioErog.getIdServizioErogato();
            assertNotNull(idServizio);

            helper.removeById(OrgTariffaAaAccordo.class, idTariffaAaAccordo);
            assertNull(helper.findById(OrgTariffaAaAccordo.class, idTariffaAaAccordo));
            assertNull(helper.findById(OrgServizioErog.class, idServizio));

        } finally {
            delete(OrgServizioErog.class, idServizio);
            delete(OrgTariffaAaAccordo.class, idTariffaAaAccordo);
        }
    }

    @Test
    public void orgTariffaAaAccordo_noPersistCascadeDiOrgServizioErog() {
        // testare che la insert della OrgTariffaAaAccordo con una lista di OrgServizioErog non faccia la persiste in
        // automatico dei figli
        Long idServizio = null;
        Long idTariffaAaAccordo = null;
        try {
            OrgTipoServizio tipoServizio = new OrgTipoServizio();
            tipoServizio.setIdTipoServizio(201L);

            OrgTariffaAaAccordo tariffaAaAccordo = new OrgTariffaAaAccordo();
            tariffaAaAccordo.setImTariffaAaAccordo(BigDecimal.valueOf(1985));
            tariffaAaAccordo.setOrgTipoServizio(tipoServizio);

            OrgAaAccordo orgAaAccordo = new OrgAaAccordo();
            orgAaAccordo.setIdAaAccordo(61L);
            tariffaAaAccordo.setOrgAaAccordo(orgAaAccordo);

            OrgServizioErog servizioErog = new OrgServizioErog();
            servizioErog.setNmServizioErogato(JUNIT_ARQUILLIAN);
            servizioErog.setFlPagamento("0");
            servizioErog.setImValoreTariffa(BigDecimal.valueOf(1985));
            servizioErog.setOrgTipoServizio(tipoServizio);

            OrgAccordoEnte accordoEnte = new OrgAccordoEnte();
            accordoEnte.setIdAccordoEnte(601L);
            servizioErog.setOrgAccordoEnte(accordoEnte);

            servizioErog.setOrgTariffaAaAccordo(tariffaAaAccordo);

            tariffaAaAccordo.setOrgServizioErogs(new ArrayList<>());
            tariffaAaAccordo.getOrgServizioErogs().add(servizioErog);
            helper.insertEntity(tariffaAaAccordo, true);
            idTariffaAaAccordo = tariffaAaAccordo.getIdTariffaAaAccordo();
            assertNotNull(idTariffaAaAccordo);
            idServizio = tariffaAaAccordo.getOrgServizioErogs().get(0).getIdServizioErogato();
            assertNull(idServizio);

        } finally {
            delete(OrgServizioErog.class, idServizio);
            delete(OrgTariffaAaAccordo.class, idTariffaAaAccordo);
        }
    }

    @Test
    public void orgTipiGestioneAccordo_persistCascade() {
        Long idTipoGestioneAccordo = null;
        Long idGestAccordo = null;
        try {
            OrgTipiGestioneAccordo tipiGestioneAccordo = new OrgTipiGestioneAccordo();

            tipiGestioneAccordo.setCdTipoGestioneAccordo(JUNIT_ARQUILLIAN);

            OrgGestioneAccordo gestioneAccordo = new OrgGestioneAccordo();
            final OrgAccordoEnte orgAccordoEnte = new OrgAccordoEnte();
            orgAccordoEnte.setIdAccordoEnte(881L);
            gestioneAccordo.setOrgAccordoEnte(orgAccordoEnte);
            gestioneAccordo.setDtGestAccordo(new Date());
            gestioneAccordo.setDsGestAccordo(JUNIT_ARQUILLIAN);
            gestioneAccordo.setPgGestAccordo(BigDecimal.valueOf(1985));
            gestioneAccordo.setOrgTipiGestioneAccordo(tipiGestioneAccordo);
            gestioneAccordo.setTipoTrasmissione(ConstOrgGestioneAccordo.TipoTrasmissione.EMAIL);
            tipiGestioneAccordo.setOrgGestioneAccordos(new ArrayList<>());
            tipiGestioneAccordo.getOrgGestioneAccordos().add(gestioneAccordo);

            helper.insertEntity(tipiGestioneAccordo, true);
            idTipoGestioneAccordo = tipiGestioneAccordo.getIdTipoGestioneAccordo();
            idGestAccordo = tipiGestioneAccordo.getOrgGestioneAccordos().get(0).getIdGestAccordo();
            assertNotNull(idTipoGestioneAccordo);
            assertNotNull(idGestAccordo);
        } finally {
            delete(OrgGestioneAccordo.class, idGestAccordo);
            delete(OrgTipiGestioneAccordo.class, idTipoGestioneAccordo);
        }
    }

    @Test
    public void orgTipiGestioneAccordo_removeCascade() {
        Long idTipoGestioneAccordo = null;
        Long idGestAccordo = null;
        try {
            OrgTipiGestioneAccordo tipiGestioneAccordo = new OrgTipiGestioneAccordo();

            tipiGestioneAccordo.setCdTipoGestioneAccordo(JUNIT_ARQUILLIAN);

            OrgGestioneAccordo gestioneAccordo = new OrgGestioneAccordo();
            final OrgAccordoEnte orgAccordoEnte = new OrgAccordoEnte();
            orgAccordoEnte.setIdAccordoEnte(881L);
            gestioneAccordo.setOrgAccordoEnte(orgAccordoEnte);
            gestioneAccordo.setDtGestAccordo(new Date());
            gestioneAccordo.setDsGestAccordo(JUNIT_ARQUILLIAN);
            gestioneAccordo.setPgGestAccordo(BigDecimal.valueOf(1985));
            gestioneAccordo.setOrgTipiGestioneAccordo(tipiGestioneAccordo);
            gestioneAccordo.setTipoTrasmissione(ConstOrgGestioneAccordo.TipoTrasmissione.EMAIL);
            tipiGestioneAccordo.setOrgGestioneAccordos(new ArrayList<>());
            tipiGestioneAccordo.getOrgGestioneAccordos().add(gestioneAccordo);

            helper.insertEntity(tipiGestioneAccordo, true);
            idTipoGestioneAccordo = tipiGestioneAccordo.getIdTipoGestioneAccordo();
            idGestAccordo = tipiGestioneAccordo.getOrgGestioneAccordos().get(0).getIdGestAccordo();

            helper.removeById(OrgTipiGestioneAccordo.class, idTipoGestioneAccordo);
            assertNull(helper.findById(OrgGestioneAccordo.class, idGestAccordo));
            assertNull(helper.findById(OrgTipiGestioneAccordo.class, idTipoGestioneAccordo));
        } finally {
            delete(OrgGestioneAccordo.class, idGestAccordo);
            delete(OrgTipiGestioneAccordo.class, idTipoGestioneAccordo);
        }
    }

    @Test
    public void prfRuolo_persistCascadePrfRuoloCategoria() {
        Long idRuolo = null;
        Long idRuoloCategoria = null;
        try {
            PrfRuolo ruolo = new PrfRuolo();
            ruolo.setNmRuolo(JUNIT_ARQUILLIAN);
            ruolo.setDsRuolo(JUNIT_ARQUILLIAN);
            ruolo.setTiRuolo(ConstPrfRuolo.TiRuolo.AUTOMA.name());
            ruolo.setTiStatoRichAllineaRuoli_1(ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE.name());
            ruolo.setTiStatoRichAllineaRuoli_2(ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE.name());
            ruolo.setFlAllineamentoInCorso("0");

            PrfRuoloCategoria ruoloCategoria = new PrfRuoloCategoria();
            ruoloCategoria.setPrfRuolo(ruolo);
            ruoloCategoria.setTiCategRuolo(ConstPrfRuolo.TiCategRuolo.conservazione.name());
            ruolo.setPrfRuoloCategorias(new ArrayList<>());
            ruolo.getPrfRuoloCategorias().add(ruoloCategoria);

            helper.insertEntity(ruolo, true);
            idRuolo = ruolo.getIdRuolo();
            idRuoloCategoria = ruolo.getPrfRuoloCategorias().get(0).getIdRuoloCategoria();
            assertNotNull(idRuolo);
            assertNotNull(idRuoloCategoria);
        } finally {
            delete(PrfRuolo.class, idRuolo);
            delete(PrfRuoloCategoria.class, idRuoloCategoria);
        }
    }

    @Test
    public void prfRuolo_mergeCascadePrfRuoloCategoria() {
        Long idRuolo = null;
        Long idRuoloCategoria = null;
        try {
            final String nmRuolo = JUNIT_ARQUILLIAN;
            PrfRuolo ruolo = new PrfRuolo();
            ruolo.setNmRuolo(nmRuolo);
            ruolo.setDsRuolo(JUNIT_ARQUILLIAN);
            ruolo.setTiRuolo(ConstPrfRuolo.TiRuolo.AUTOMA.name());
            ruolo.setTiStatoRichAllineaRuoli_1(ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE.name());
            ruolo.setTiStatoRichAllineaRuoli_2(ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE.name());
            ruolo.setFlAllineamentoInCorso("0");

            final String categRuolo = ConstPrfRuolo.TiCategRuolo.conservazione.name();
            PrfRuoloCategoria ruoloCategoria = new PrfRuoloCategoria();
            ruoloCategoria.setPrfRuolo(ruolo);
            ruoloCategoria.setTiCategRuolo(categRuolo);
            ruolo.setPrfRuoloCategorias(new ArrayList<>());
            ruolo.getPrfRuoloCategorias().add(ruoloCategoria);

            helper.insertEntity(ruolo, true);
            idRuolo = ruolo.getIdRuolo();
            idRuoloCategoria = ruolo.getPrfRuoloCategorias().get(0).getIdRuoloCategoria();

            PrfRuolo ruoloConCategorie = helper.findByIdForceLazyField(PrfRuolo.class, idRuolo, (PrfRuolo r) -> r
                    .getPrfRuoloCategorias().stream().forEach(c -> System.out.println(c.getTiCategRuolo())));
            assertEquals(nmRuolo, ruoloConCategorie.getNmRuolo());
            assertEquals(categRuolo, ruoloConCategorie.getPrfRuoloCategorias().get(0).getTiCategRuolo());
            final String nuovoNmRuolo = "ARQUILLIAN CAMBIATO PER MERGE";
            ruoloConCategorie.setNmRuolo(nuovoNmRuolo);
            final String nuovaCategRuolo = ConstPrfRuolo.TiCategRuolo.gestione.name();
            ruoloConCategorie.getPrfRuoloCategorias().get(0).setTiCategRuolo(nuovaCategRuolo);

            helper.mergeEntity(ruoloConCategorie);

            PrfRuolo ruoloAfterMerge = helper.findByIdForceLazyField(PrfRuolo.class, idRuolo, (PrfRuolo r) -> r
                    .getPrfRuoloCategorias().stream().forEach(c -> System.out.println(c.getTiCategRuolo())));
            assertEquals(nuovoNmRuolo, ruoloAfterMerge.getNmRuolo());
            assertEquals(nuovaCategRuolo, ruoloAfterMerge.getPrfRuoloCategorias().get(0).getTiCategRuolo());

        } finally {
            delete(PrfRuolo.class, idRuolo);
            delete(PrfRuoloCategoria.class, idRuoloCategoria);
        }
    }

    @Test
    public void prfRuolo_persistCascadePrfUsoRuoloApplic() {
        Long idRuolo = null;
        Long idUsoRuoloApplic = null;
        Long idDichAutor = null;
        try {
            PrfRuolo ruolo = new PrfRuolo();
            ruolo.setNmRuolo(JUNIT_ARQUILLIAN);
            ruolo.setDsRuolo(JUNIT_ARQUILLIAN);
            ruolo.setTiRuolo(ConstPrfRuolo.TiRuolo.AUTOMA.name());
            ruolo.setTiStatoRichAllineaRuoli_1(ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE.name());
            ruolo.setTiStatoRichAllineaRuoli_2(ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE.name());
            ruolo.setFlAllineamentoInCorso("0");

            PrfUsoRuoloApplic usoRuoloApplic = new PrfUsoRuoloApplic();
            usoRuoloApplic.setPrfRuolo(ruolo);
            final AplApplic aplApplic = anAplApplic();
            usoRuoloApplic.setAplApplic(aplApplic);
            PrfDichAutor dichAutor = new PrfDichAutor();
            dichAutor.setPrfUsoRuoloApplic(usoRuoloApplic);
            dichAutor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
            dichAutor.setTiScopoDichAutor(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name());
            usoRuoloApplic.setPrfDichAutors(new ArrayList<>());
            usoRuoloApplic.getPrfDichAutors().add(dichAutor);

            ruolo.setPrfUsoRuoloApplics(new ArrayList<>());
            ruolo.getPrfUsoRuoloApplics().add(usoRuoloApplic);

            helper.insertEntity(ruolo, true);
            idRuolo = ruolo.getIdRuolo();
            idUsoRuoloApplic = ruolo.getPrfUsoRuoloApplics().get(0).getIdUsoRuoloApplic();
            idDichAutor = ruolo.getPrfUsoRuoloApplics().get(0).getPrfDichAutors().get(0).getIdDichAutor();
            assertNotNull(idRuolo);
            assertNotNull(idUsoRuoloApplic);
            assertNotNull(idDichAutor);
        } finally {
            delete(PrfDichAutor.class, idDichAutor);
            delete(PrfUsoRuoloApplic.class, idUsoRuoloApplic);
            delete(PrfRuolo.class, idRuolo);
        }
    }

    private AplApplic anAplApplic() {
        final AplApplic aplApplic = new AplApplic();
        aplApplic.setIdApplic(321L);
        return aplApplic;
    }

    @Test
    public void prfRuolo_mergeCascadePrfUsoRuoloApplic() {
        Long idRuolo = null;
        Long idUsoRuoloApplic = null;
        Long idDichAutor = null;
        try {
            PrfRuolo ruolo = new PrfRuolo();
            ruolo.setNmRuolo(JUNIT_ARQUILLIAN);
            ruolo.setDsRuolo(JUNIT_ARQUILLIAN);
            ruolo.setTiRuolo(ConstPrfRuolo.TiRuolo.AUTOMA.name());
            ruolo.setTiStatoRichAllineaRuoli_1(ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE.name());
            ruolo.setTiStatoRichAllineaRuoli_2(ConstPrfRuolo.TiStatoRichAllineaRuoli.DA_ALLINEARE.name());
            ruolo.setFlAllineamentoInCorso("0");

            PrfUsoRuoloApplic usoRuoloApplic = new PrfUsoRuoloApplic();
            usoRuoloApplic.setPrfRuolo(ruolo);
            usoRuoloApplic.setAplApplic(anAplApplic());
            PrfDichAutor dichAutor = new PrfDichAutor();
            dichAutor.setPrfUsoRuoloApplic(usoRuoloApplic);
            dichAutor.setTiDichAutor(ConstPrfDichAutor.TiDichAutor.SERVIZIO_WEB.name());
            dichAutor.setTiScopoDichAutor(ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name());
            usoRuoloApplic.setPrfDichAutors(new ArrayList<>());
            usoRuoloApplic.getPrfDichAutors().add(dichAutor);

            ruolo.setPrfUsoRuoloApplics(new ArrayList<>());
            ruolo.getPrfUsoRuoloApplics().add(usoRuoloApplic);

            helper.insertEntity(ruolo, true);
            idRuolo = ruolo.getIdRuolo();
            idUsoRuoloApplic = ruolo.getPrfUsoRuoloApplics().get(0).getIdUsoRuoloApplic();
            idDichAutor = ruolo.getPrfUsoRuoloApplics().get(0).getPrfDichAutors().get(0).getIdDichAutor();

            PrfRuolo ruoloReloaded = helper.findByIdForceLazyField(PrfRuolo.class, idRuolo,
                    (PrfRuolo r) -> r.getPrfUsoRuoloApplics().stream().forEach(
                            u -> u.getPrfDichAutors().stream().forEach(d -> System.out.println(d.getTiDichAutor()))));

            final String nmRuolo = "ARQUILIAN MERGE";
            ruoloReloaded.setNmRuolo(nmRuolo);
            final String tiDichAutor = ConstPrfDichAutor.TiDichAutor.AZIONE.name();
            ruoloReloaded.getPrfUsoRuoloApplics().get(0).getPrfDichAutors().get(0).setTiDichAutor(tiDichAutor);

            helper.mergeEntity(ruoloReloaded);

            PrfRuolo ruoloReloadedAgain = helper.findByIdForceLazyField(PrfRuolo.class, idRuolo,
                    (PrfRuolo r) -> r.getPrfUsoRuoloApplics().stream().forEach(
                            u -> u.getPrfDichAutors().stream().forEach(d -> System.out.println(d.getTiDichAutor()))));
            assertEquals(nmRuolo, ruoloReloadedAgain.getNmRuolo());
            assertEquals(tiDichAutor,
                    ruoloReloadedAgain.getPrfUsoRuoloApplics().get(0).getPrfDichAutors().get(0).getTiDichAutor());

        } finally {
            delete(PrfDichAutor.class, idDichAutor);
            delete(PrfUsoRuoloApplic.class, idUsoRuoloApplic);
            delete(PrfRuolo.class, idRuolo);
        }
    }

    @Test
    public void usrDichAbilOrganiz() {
        Long idDichAbilOrganiz = null;
        Long idUsoRuoloDich = null;
        try {
            UsrDichAbilOrganiz dichAbilOrganiz = new UsrDichAbilOrganiz();
            dichAbilOrganiz.setTiScopoDichAbilOrganiz(ApplEnum.TiScopoDichAbilOrganiz.UNA_ORG.name());
            UsrUsoUserApplic usrUsoUserApplic = anUsrUsoUserApplic();
            dichAbilOrganiz.setUsrUsoUserApplic(usrUsoUserApplic);

            UsrUsoRuoloDich usrUsoRuoloDich = new UsrUsoRuoloDich();
            usrUsoRuoloDich.setUsrDichAbilOrganiz(dichAbilOrganiz);
            usrUsoRuoloDich.setPrfRuolo(aPrfRuolo());
            usrUsoRuoloDich.setTiScopoRuolo("UNA_ORG");
            final UsrOrganizIam usrOrganizIam = new UsrOrganizIam();
            usrOrganizIam.setIdOrganizIam(1483L);
            usrUsoRuoloDich.setUsrOrganizIam(usrOrganizIam);

            dichAbilOrganiz.setUsrUsoRuoloDiches(new ArrayList<>());
            dichAbilOrganiz.getUsrUsoRuoloDiches().add(usrUsoRuoloDich);

            helper.insertEntity(dichAbilOrganiz, true);
            idDichAbilOrganiz = dichAbilOrganiz.getIdDichAbilOrganiz();
            idUsoRuoloDich = dichAbilOrganiz.getUsrUsoRuoloDiches().get(0).getIdUsoRuoloDich();
            assertNotNull(idDichAbilOrganiz);
            assertNotNull(idUsoRuoloDich);
        } finally {
            delete(UsrUsoRuoloDich.class, idUsoRuoloDich);
            delete(UsrDichAbilOrganiz.class, idDichAbilOrganiz);
        }
    }

    private PrfRuolo aPrfRuolo() {
        PrfRuolo ruolo = new PrfRuolo();
        ruolo.setIdRuolo(925L);
        return ruolo;
    }

    private UsrUsoUserApplic anUsrUsoUserApplic() {
        UsrUsoUserApplic usrUsoUserApplic = new UsrUsoUserApplic();
        usrUsoUserApplic.setIdUsoUserApplic(1581L);
        return usrUsoUserApplic;
    }

    @Test
    public void usrOrganizIam_removeCascadeOrgEnteConvenz() {
        Long idOrganizIam = null;
        Long idEnteConvenzOrg = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();
            helper.insertEntity(usrOrganizIam, true);
            idOrganizIam = usrOrganizIam.getIdOrganizIam();
            assertNotNull(idOrganizIam);

            OrgEnteConvenzOrg orgEnteConvenzOrg = new OrgEnteConvenzOrg();
            orgEnteConvenzOrg.setOrgEnteSiam(anOrgEnteSiam());
            orgEnteConvenzOrg.setUsrOrganizIam(usrOrganizIam);
            orgEnteConvenzOrg.setDtFineVal(new Date());
            orgEnteConvenzOrg.setDtIniVal(new Date());
            helper.insertEntity(orgEnteConvenzOrg, true);
            idEnteConvenzOrg = orgEnteConvenzOrg.getIdEnteConvenzOrg();
            assertNotNull(idEnteConvenzOrg);

            usrOrganizIam.setOrgEnteConvenzOrgs(new ArrayList<>());
            helper.mergeEntity(usrOrganizIam);

            helper.removeById(UsrOrganizIam.class, idOrganizIam);
            assertNull(helper.findById(UsrOrganizIam.class, idOrganizIam));
            assertNull(helper.findById(OrgEnteConvenzOrg.class, idEnteConvenzOrg));
        } finally {
            delete(OrgEnteConvenzOrg.class, idEnteConvenzOrg);
            delete(UsrOrganizIam.class, idOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_persistCascadeUsrDichAbilDati() {
        Long idOrganizIam = null;
        Long idUsrDichAbilDati = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrDichAbilDati usrDichAbilDati = new UsrDichAbilDati();
            usrDichAbilDati.setUsrUsoUserApplic(anUsrUsoUserApplic());
            usrDichAbilDati.setTiScopoDichAbilDati(ApplEnum.TiScopoDichAbilDati.UN_TIPO_DATO.name());
            usrDichAbilDati.setAplClasseTipoDato(anAplClasseTipoDato());
            usrDichAbilDati.setUsrOrganizIam(usrOrganizIam);
            usrOrganizIam.setUsrDichAbilDatis(new ArrayList<>());
            usrOrganizIam.getUsrDichAbilDatis().add(usrDichAbilDati);

            helper.insertEntity(usrOrganizIam, true);
            idOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrDichAbilDati = usrDichAbilDati.getIdDichAbilDati();
            assertNotNull(idOrganizIam);
            assertNotNull(idUsrDichAbilDati);
        } finally {
            delete(UsrDichAbilDati.class, idUsrDichAbilDati);
            delete(UsrOrganizIam.class, idOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_mergeCascadeUsrDichAbilDati() {
        Long idUsrOrganizIam = null;
        Long idUsrDichAbilDati = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrDichAbilDati usrDichAbilDati = new UsrDichAbilDati();
            usrDichAbilDati.setUsrUsoUserApplic(anUsrUsoUserApplic());
            usrDichAbilDati.setTiScopoDichAbilDati(ApplEnum.TiScopoDichAbilDati.UN_TIPO_DATO.name());
            usrDichAbilDati.setAplClasseTipoDato(anAplClasseTipoDato());
            usrDichAbilDati.setUsrOrganizIam(usrOrganizIam);
            usrOrganizIam.setUsrDichAbilDatis(new ArrayList<>());
            usrOrganizIam.getUsrDichAbilDatis().add(usrDichAbilDati);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrDichAbilDati = usrDichAbilDati.getIdDichAbilDati();

            UsrOrganizIam usrOrganizIamReloaded = helper.findByIdForceLazyField(UsrOrganizIam.class, idUsrOrganizIam,
                    u -> System.out.println(u.getUsrDichAbilDatis().get(0).getTiScopoDichAbilDati()));
            String nmOrganizMerge = " CAMBIATO PER MERGE";
            usrOrganizIamReloaded.setNmOrganiz(nmOrganizMerge);
            final String tiScopoDichAbilDatiMerge = ApplEnum.TiScopoDichAbilDati.ALL_ORG_CHILD.name();
            usrOrganizIamReloaded.getUsrDichAbilDatis().get(0).setTiScopoDichAbilDati(tiScopoDichAbilDatiMerge);
            helper.mergeEntity(usrOrganizIamReloaded);

            UsrOrganizIam usrOrganizIamReloadedAgain = helper.findByIdForceLazyField(UsrOrganizIam.class,
                    idUsrOrganizIam, u -> System.out.println(u.getUsrDichAbilDatis().get(0).getTiScopoDichAbilDati()));
            assertEquals(nmOrganizMerge, usrOrganizIamReloadedAgain.getNmOrganiz());
            assertEquals(tiScopoDichAbilDatiMerge,
                    usrOrganizIamReloadedAgain.getUsrDichAbilDatis().get(0).getTiScopoDichAbilDati());

        } finally {
            delete(UsrDichAbilDati.class, idUsrDichAbilDati);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_mergeRemoveUsrDichAbilDati() {
        Long idUsrOrganizIam = null;
        Long idUsrDichAbilDati = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrDichAbilDati usrDichAbilDati = new UsrDichAbilDati();
            usrDichAbilDati.setUsrUsoUserApplic(anUsrUsoUserApplic());
            usrDichAbilDati.setTiScopoDichAbilDati(ApplEnum.TiScopoDichAbilDati.UN_TIPO_DATO.name());
            usrDichAbilDati.setAplClasseTipoDato(anAplClasseTipoDato());
            usrDichAbilDati.setUsrOrganizIam(usrOrganizIam);
            usrOrganizIam.setUsrDichAbilDatis(new ArrayList<>());
            usrOrganizIam.getUsrDichAbilDatis().add(usrDichAbilDati);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrDichAbilDati = usrDichAbilDati.getIdDichAbilDati();

            helper.removeById(UsrOrganizIam.class, idUsrOrganizIam);
            assertNull(helper.findById(UsrOrganizIam.class, idUsrOrganizIam));
            assertNull(helper.findById(UsrDichAbilDati.class, idUsrDichAbilDati));
        } finally {
            delete(UsrDichAbilDati.class, idUsrDichAbilDati);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_persistCascadeUsrDichAbilOrganiz() {
        Long idUsrOrganizIam = null;
        Long idUsrDichAbilOrganiz = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrDichAbilOrganiz usrDichAbilOrganiz = new UsrDichAbilOrganiz();
            usrDichAbilOrganiz.setUsrUsoUserApplic(anUsrUsoUserApplic());
            usrDichAbilOrganiz.setTiScopoDichAbilOrganiz(ApplEnum.TiScopoDichAbilOrganiz.ORG_DEFAULT.name());
            usrDichAbilOrganiz.setUsrOrganizIam(usrOrganizIam);
            usrOrganizIam.setUsrDichAbilOrganizs(new ArrayList<>());
            usrOrganizIam.getUsrDichAbilOrganizs().add(usrDichAbilOrganiz);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrDichAbilOrganiz = usrDichAbilOrganiz.getIdDichAbilOrganiz();
            assertNotNull(idUsrOrganizIam);
            assertNotNull(idUsrDichAbilOrganiz);
        } finally {
            delete(UsrDichAbilOrganiz.class, idUsrDichAbilOrganiz);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_mergeCascadeUsrDichAbilOrganiz() {
        Long idUsrOrganizIam = null;
        Long idUsrDichAbilOrganiz = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrDichAbilOrganiz usrDichAbilOrganiz = new UsrDichAbilOrganiz();
            usrDichAbilOrganiz.setUsrUsoUserApplic(anUsrUsoUserApplic());
            usrDichAbilOrganiz.setTiScopoDichAbilOrganiz(ApplEnum.TiScopoDichAbilOrganiz.ORG_DEFAULT.name());
            usrDichAbilOrganiz.setUsrOrganizIam(usrOrganizIam);
            usrOrganizIam.setUsrDichAbilOrganizs(new ArrayList<>());
            usrOrganizIam.getUsrDichAbilOrganizs().add(usrDichAbilOrganiz);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrDichAbilOrganiz = usrDichAbilOrganiz.getIdDichAbilOrganiz();

            UsrOrganizIam usrOrganizIamReloaded = helper.findByIdForceLazyField(UsrOrganizIam.class, idUsrOrganizIam,
                    u -> u.getUsrDichAbilOrganizs().get(0).getTiScopoDichAbilOrganiz());
            final String nmOrganiz = "NOME MERGE";
            usrOrganizIamReloaded.setNmOrganiz(nmOrganiz);
            final String tiScopoDichAbilOrganiz = ApplEnum.TiScopoDichAbilOrganiz.ALL_ORG.name();
            usrOrganizIamReloaded.getUsrDichAbilOrganizs().get(0).setTiScopoDichAbilOrganiz(tiScopoDichAbilOrganiz);
            helper.mergeEntity(usrOrganizIamReloaded);
            UsrOrganizIam usrOrganizIamReloadedAgain = helper.findByIdForceLazyField(UsrOrganizIam.class,
                    idUsrOrganizIam, u -> u.getUsrDichAbilOrganizs().get(0).getTiScopoDichAbilOrganiz());
            assertEquals(nmOrganiz, usrOrganizIamReloadedAgain.getNmOrganiz());
            assertEquals(tiScopoDichAbilOrganiz,
                    usrOrganizIamReloadedAgain.getUsrDichAbilOrganizs().get(0).getTiScopoDichAbilOrganiz());

        } finally {
            delete(UsrDichAbilOrganiz.class, idUsrDichAbilOrganiz);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_removeCascadeUsrDichAbilOrganiz() {
        Long idUsrOrganizIam = null;
        Long idUsrDichAbilOrganiz = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrDichAbilOrganiz usrDichAbilOrganiz = new UsrDichAbilOrganiz();
            usrDichAbilOrganiz.setUsrUsoUserApplic(anUsrUsoUserApplic());
            usrDichAbilOrganiz.setTiScopoDichAbilOrganiz(ApplEnum.TiScopoDichAbilOrganiz.ORG_DEFAULT.name());
            usrDichAbilOrganiz.setUsrOrganizIam(usrOrganizIam);
            usrOrganizIam.setUsrDichAbilOrganizs(new ArrayList<>());
            usrOrganizIam.getUsrDichAbilOrganizs().add(usrDichAbilOrganiz);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrDichAbilOrganiz = usrDichAbilOrganiz.getIdDichAbilOrganiz();

            helper.removeById(UsrOrganizIam.class, idUsrOrganizIam);
            assertNull(helper.findById(UsrOrganizIam.class, idUsrOrganizIam));
            assertNull(helper.findById(UsrDichAbilOrganiz.class, idUsrDichAbilOrganiz));

        } finally {
            delete(UsrDichAbilOrganiz.class, idUsrDichAbilOrganiz);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_persistCascadeUsrTipoDatoIam() {
        Long idUsrOrganizIam = null;
        Long idUsrTipoDatoIam = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrTipoDatoIam usrTipoDatoIam = new UsrTipoDatoIam();
            usrTipoDatoIam.setUsrOrganizIam(usrOrganizIam);
            usrTipoDatoIam.setNmTipoDato(JUNIT_ARQUILLIAN);
            usrTipoDatoIam.setDsTipoDato(JUNIT_ARQUILLIAN);
            usrTipoDatoIam.setIdTipoDatoApplic(BigDecimal.valueOf(1985));
            usrTipoDatoIam.setAplClasseTipoDato(anAplClasseTipoDato());

            usrOrganizIam.setUsrTipoDatoIams(new ArrayList<>());
            usrOrganizIam.getUsrTipoDatoIams().add(usrTipoDatoIam);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrTipoDatoIam = usrTipoDatoIam.getIdTipoDatoIam();
            assertNotNull(idUsrOrganizIam);
            assertNotNull(idUsrTipoDatoIam);
        } finally {
            delete(UsrDichAbilOrganiz.class, idUsrTipoDatoIam);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_mergeCascadeUsrTipoDatoIam() {
        Long idUsrOrganizIam = null;
        Long idUsrTipoDatoIam = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrTipoDatoIam usrTipoDatoIam = new UsrTipoDatoIam();
            usrTipoDatoIam.setUsrOrganizIam(usrOrganizIam);
            usrTipoDatoIam.setNmTipoDato(JUNIT_ARQUILLIAN);
            usrTipoDatoIam.setDsTipoDato(JUNIT_ARQUILLIAN);
            usrTipoDatoIam.setIdTipoDatoApplic(BigDecimal.valueOf(1985));
            usrTipoDatoIam.setAplClasseTipoDato(anAplClasseTipoDato());

            usrOrganizIam.setUsrTipoDatoIams(new ArrayList<>());
            usrOrganizIam.getUsrTipoDatoIams().add(usrTipoDatoIam);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrTipoDatoIam = usrTipoDatoIam.getIdTipoDatoIam();

            UsrOrganizIam usrOrganizIamReloaded = helper.findByIdForceLazyField(UsrOrganizIam.class, idUsrOrganizIam,
                    u -> u.getUsrTipoDatoIams().get(0).getDsTipoDato());
            final String nmOrganiz = JUNIT_ARQUILLIAN + " MERGED";
            usrOrganizIamReloaded.setNmOrganiz(nmOrganiz);
            final String dsTipoDato = JUNIT_ARQUILLIAN + " MERGED";
            usrOrganizIamReloaded.getUsrTipoDatoIams().get(0).setDsTipoDato(dsTipoDato);
            helper.mergeEntity(usrOrganizIamReloaded);
            assertEquals(nmOrganiz, helper.findById(UsrOrganizIam.class, idUsrOrganizIam).getNmOrganiz());
            assertEquals(dsTipoDato, helper.findById(UsrTipoDatoIam.class, idUsrTipoDatoIam).getDsTipoDato());

        } finally {
            delete(UsrDichAbilOrganiz.class, idUsrTipoDatoIam);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_removeCascadeUsrTipoDatoIam() {
        Long idUsrOrganizIam = null;
        Long idUsrTipoDatoIam = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrTipoDatoIam usrTipoDatoIam = new UsrTipoDatoIam();
            usrTipoDatoIam.setUsrOrganizIam(usrOrganizIam);
            usrTipoDatoIam.setNmTipoDato(JUNIT_ARQUILLIAN);
            usrTipoDatoIam.setDsTipoDato(JUNIT_ARQUILLIAN);
            usrTipoDatoIam.setIdTipoDatoApplic(BigDecimal.valueOf(1985));
            usrTipoDatoIam.setAplClasseTipoDato(anAplClasseTipoDato());

            usrOrganizIam.setUsrTipoDatoIams(new ArrayList<>());
            usrOrganizIam.getUsrTipoDatoIams().add(usrTipoDatoIam);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrTipoDatoIam = usrTipoDatoIam.getIdTipoDatoIam();

            helper.removeById(UsrOrganizIam.class, idUsrOrganizIam);

            assertNull(helper.findById(UsrOrganizIam.class, idUsrOrganizIam));
            assertNull(helper.findById(UsrTipoDatoIam.class, idUsrTipoDatoIam));

        } finally {
            delete(UsrDichAbilOrganiz.class, idUsrTipoDatoIam);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_persistCascadeUsrUsoRuoloDich() {
        Long idUsrOrganizIam = null;
        Long idUsrUsoRuoloDich = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrUsoRuoloDich usrUsoRuoloDich = new UsrUsoRuoloDich();
            final UsrDichAbilOrganiz usrDichAbilOrganiz = anUsrDichAbilOrganiz();
            usrUsoRuoloDich.setUsrDichAbilOrganiz(usrDichAbilOrganiz);
            usrUsoRuoloDich.setPrfRuolo(aPrfRuolo());
            usrUsoRuoloDich.setTiScopoRuolo("UNA_ORG");
            usrUsoRuoloDich.setUsrOrganizIam(usrOrganizIam);

            usrOrganizIam.setUsrUsoRuoloDiches(new ArrayList<>());
            usrOrganizIam.getUsrUsoRuoloDiches().add(usrUsoRuoloDich);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrUsoRuoloDich = usrUsoRuoloDich.getIdUsoRuoloDich();
            assertNotNull(idUsrOrganizIam);
            assertNotNull(idUsrUsoRuoloDich);
        } finally {
            delete(UsrUsoRuoloDich.class, idUsrUsoRuoloDich);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_persistMergeUsrUsoRuoloDich() {
        Long idUsrOrganizIam = null;
        Long idUsrUsoRuoloDich = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrUsoRuoloDich usrUsoRuoloDich = new UsrUsoRuoloDich();
            final UsrDichAbilOrganiz usrDichAbilOrganiz = anUsrDichAbilOrganiz();
            usrUsoRuoloDich.setUsrDichAbilOrganiz(usrDichAbilOrganiz);
            usrUsoRuoloDich.setPrfRuolo(aPrfRuolo());
            usrUsoRuoloDich.setTiScopoRuolo("UNA_ORG");
            usrUsoRuoloDich.setUsrOrganizIam(usrOrganizIam);

            usrOrganizIam.setUsrUsoRuoloDiches(new ArrayList<>());
            usrOrganizIam.getUsrUsoRuoloDiches().add(usrUsoRuoloDich);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrUsoRuoloDich = usrUsoRuoloDich.getIdUsoRuoloDich();

            UsrOrganizIam usrOrganizIamReloaded = helper.findByIdForceLazyField(UsrOrganizIam.class, idUsrOrganizIam,
                    u -> u.getUsrUsoRuoloDiches().get(0).getTiScopoRuolo());
            final String nmOrganiz = JUNIT_ARQUILLIAN + " MERGED";
            usrOrganizIamReloaded.setNmOrganiz(nmOrganiz);
            final String tiScopoRuolo = "ALL_ORG_CHILD";
            usrOrganizIamReloaded.getUsrUsoRuoloDiches().get(0).setTiScopoRuolo(tiScopoRuolo);
            helper.mergeEntity(usrOrganizIamReloaded);
            assertEquals(nmOrganiz, helper.findById(UsrOrganizIam.class, idUsrOrganizIam).getNmOrganiz());
            assertEquals(tiScopoRuolo, helper.findById(UsrUsoRuoloDich.class, idUsrUsoRuoloDich).getTiScopoRuolo());
        } finally {
            delete(UsrUsoRuoloDich.class, idUsrUsoRuoloDich);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrOrganizIam_removeCascadeUsrUsoRuoloDich() {
        Long idUsrOrganizIam = null;
        Long idUsrUsoRuoloDich = null;
        try {
            UsrOrganizIam usrOrganizIam = newUsrOrganizIam();

            UsrUsoRuoloDich usrUsoRuoloDich = new UsrUsoRuoloDich();
            final UsrDichAbilOrganiz usrDichAbilOrganiz = anUsrDichAbilOrganiz();
            usrUsoRuoloDich.setUsrDichAbilOrganiz(usrDichAbilOrganiz);
            usrUsoRuoloDich.setPrfRuolo(aPrfRuolo());
            usrUsoRuoloDich.setTiScopoRuolo("UNA_ORG");
            usrUsoRuoloDich.setUsrOrganizIam(usrOrganizIam);

            usrOrganizIam.setUsrUsoRuoloDiches(new ArrayList<>());
            usrOrganizIam.getUsrUsoRuoloDiches().add(usrUsoRuoloDich);

            helper.insertEntity(usrOrganizIam, true);
            idUsrOrganizIam = usrOrganizIam.getIdOrganizIam();
            idUsrUsoRuoloDich = usrUsoRuoloDich.getIdUsoRuoloDich();
            helper.removeById(UsrOrganizIam.class, idUsrOrganizIam);
            assertNull(helper.findById(UsrOrganizIam.class, idUsrOrganizIam));
            assertNull(helper.findById(UsrUsoRuoloDich.class, idUsrUsoRuoloDich));
        } finally {
            delete(UsrUsoRuoloDich.class, idUsrUsoRuoloDich);
            delete(UsrOrganizIam.class, idUsrOrganizIam);
        }
    }

    @Test
    public void usrRichGestUser_persistCascadeUsrStatoUser() {
        Long idUsrRichGestUser = null;
        Long idUsrStatoUser = null;
        try {
            UsrRichGestUser usrRichGestUser = new UsrRichGestUser();
            usrRichGestUser.setOrgEnteSiam(anOrgEnteSiam());
            usrRichGestUser.setTiRichGestUser(ConstUsrRichGestUser.TiRichGestUser.EMAIL.name());
            usrRichGestUser.setDtRichGestUser(new Date());
            usrRichGestUser.setTiUserRich(ConstUsrRichGestUser.TiUserRich.UTENTE_NO_CODIF.name());
            usrRichGestUser.setTiStatoRichGestUser(ConstUsrRichGestUser.TiStatoRichGestUser.DA_EVADERE.name());

            UsrStatoUser usrStatoUser = new UsrStatoUser();
            usrStatoUser.setUsrUser(aUsrUser());
            usrStatoUser.setTsStato(new Timestamp(new Date().getTime()));
            usrStatoUser.setTiStatoUser(ConstUsrStatoUser.TiStatotUser.DISATTIVO.name());
            usrStatoUser.setUsrRichGestUser(usrRichGestUser);

            usrRichGestUser.setUsrStatoUsers(new ArrayList<>());
            usrRichGestUser.getUsrStatoUsers().add(usrStatoUser);

            helper.insertEntity(usrRichGestUser, true);
            idUsrRichGestUser = usrRichGestUser.getIdRichGestUser();
            idUsrStatoUser = usrStatoUser.getIdStatoUser();
            assertNotNull(idUsrRichGestUser);
            assertNotNull(idUsrStatoUser);

        } finally {
            delete(UsrStatoUser.class, idUsrStatoUser);
            delete(UsrRichGestUser.class, idUsrRichGestUser);
        }
    }

    @Test
    public void usrRichGestUser_noRemoveCascadeUsrStatoUser() {
        Long idUsrRichGestUser = null;
        Long idUsrStatoUser = null;
        try {
            UsrRichGestUser usrRichGestUser = new UsrRichGestUser();
            usrRichGestUser.setOrgEnteSiam(anOrgEnteSiam());
            usrRichGestUser.setTiRichGestUser(ConstUsrRichGestUser.TiRichGestUser.EMAIL.name());
            usrRichGestUser.setDtRichGestUser(new Date());
            usrRichGestUser.setTiUserRich(ConstUsrRichGestUser.TiUserRich.UTENTE_NO_CODIF.name());
            usrRichGestUser.setTiStatoRichGestUser(ConstUsrRichGestUser.TiStatoRichGestUser.DA_EVADERE.name());

            UsrStatoUser usrStatoUser = new UsrStatoUser();
            usrStatoUser.setUsrUser(aUsrUser());
            usrStatoUser.setTsStato(new Timestamp(new Date().getTime()));
            usrStatoUser.setTiStatoUser(ConstUsrStatoUser.TiStatotUser.DISATTIVO.name());
            usrStatoUser.setUsrRichGestUser(usrRichGestUser);

            usrRichGestUser.setUsrStatoUsers(new ArrayList<>());
            usrRichGestUser.getUsrStatoUsers().add(usrStatoUser);

            helper.insertEntity(usrRichGestUser, true);
            idUsrRichGestUser = usrRichGestUser.getIdRichGestUser();
            idUsrStatoUser = usrStatoUser.getIdStatoUser();
            try {
                helper.removeById(UsrRichGestUser.class, idUsrRichGestUser);
            } catch (Throwable t) {
                // essendoci una FK deve dare un errore Oracle
                assertTrue(t.getMessage().contains("ConstraintViolationException"));
            }
        } finally {
            delete(UsrStatoUser.class, idUsrStatoUser);
            delete(UsrRichGestUser.class, idUsrRichGestUser);
        }
    }

    @Test
    public void usrUser_removeCascadeAplSistemaVersanteUserRef() {
        Long idUsrUser = null;
        Long idAplSistemaVersanteUserRef = null;
        try {
            UsrUser usrUser = newUsrUser();
            helper.insertEntity(usrUser, true);
            idUsrUser = usrUser.getIdUserIam();
            assertNotNull(idUsrUser);

            AplSistemaVersanteUserRef aplSistemaVersanteUserRef = new AplSistemaVersanteUserRef();
            aplSistemaVersanteUserRef.setAplSistemaVersante(anAplSistemaVersante());
            aplSistemaVersanteUserRef.setUsrUser(usrUser);
            helper.insertEntity(aplSistemaVersanteUserRef, true);
            idAplSistemaVersanteUserRef = aplSistemaVersanteUserRef.getIdSistemaVersanteUserRef();
            assertNotNull(idAplSistemaVersanteUserRef);

            helper.removeById(UsrUser.class, idUsrUser);
            assertNull(helper.findById(UsrUser.class, idUsrUser));
            assertNull(helper.findById(AplSistemaVersanteUserRef.class, idAplSistemaVersanteUserRef));

        } finally {
            delete(UsrUser.class, idUsrUser);
            delete(AplSistemaVersanteUserRef.class, idAplSistemaVersanteUserRef);
        }
    }

    @Test
    public void usrUser_NOpersistCascadeAplSistemaVersanteUserRef() {
        Long idUsrUser = null;
        Long idAplSistemaVersanteUserRef = null;
        try {
            UsrUser usrUser = newUsrUser();
            idUsrUser = usrUser.getIdUserIam();

            AplSistemaVersanteUserRef aplSistemaVersanteUserRef = new AplSistemaVersanteUserRef();
            aplSistemaVersanteUserRef.setAplSistemaVersante(anAplSistemaVersante());
            aplSistemaVersanteUserRef.setUsrUser(usrUser);

            usrUser.setAplSistemaVersanteUserRefs(new ArrayList<>());
            usrUser.getAplSistemaVersanteUserRefs().add(aplSistemaVersanteUserRef);

            helper.insertEntity(usrUser, true);
            idUsrUser = usrUser.getIdUserIam();
            idAplSistemaVersanteUserRef = aplSistemaVersanteUserRef.getIdSistemaVersanteUserRef();
            assertNotNull(idUsrUser);
            assertNull(idAplSistemaVersanteUserRef);

        } finally {
            delete(UsrUser.class, idUsrUser);
            delete(AplSistemaVersanteUserRef.class, idAplSistemaVersanteUserRef);
        }
    }

    @Test
    public void usrUser_removeCascadeUsrAppartUserRich() {
        Long idUsrUser = null;
        Long idUsrAppartUserRich = null;
        try {
            UsrUser usrUser = newUsrUser();
            helper.insertEntity(usrUser, true);
            idUsrUser = usrUser.getIdUserIam();
            assertNotNull(idUsrUser);

            UsrAppartUserRich usrAppartUserRich = new UsrAppartUserRich();
            usrAppartUserRich.setUsrRichGestUser(aUsrRichGestUser());
            usrAppartUserRich.setTiAppartUserRich(ConstUsrAppartUserRich.TiAppartUserRich.UTENTE_NON_CODIFICATO.name());
            usrAppartUserRich
                    .setTiAzioneRich(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CESSAZIONE.getDescrizione());
            usrAppartUserRich.setFlAzioneRichEvasa("1");
            usrAppartUserRich.setOrgEnteSiam(anEnteSiam());
            usrAppartUserRich.setUsrUser(usrUser);

            helper.insertEntity(usrAppartUserRich, true);
            idUsrAppartUserRich = usrAppartUserRich.getIdAppartUserRich();
            assertNotNull(idUsrAppartUserRich);

            helper.removeById(UsrUser.class, idUsrUser);
            assertNull(helper.findById(UsrUser.class, idUsrUser));
            assertNull(helper.findById(UsrAppartUserRich.class, idUsrAppartUserRich));

        } finally {
            delete(UsrUser.class, idUsrUser);
            delete(UsrAppartUserRich.class, idUsrAppartUserRich);
        }
    }

    // @Test
    // public void usrUser_NOmergeCascadeUsrAppartUserRich() {
    // Long idUsrUser = null;
    // Long idUsrAppartUserRich = null;
    // try {
    // UsrUser usrUser = newUsrUser();
    // helper.insertEntity(usrUser, true);
    // idUsrUser = usrUser.getIdUserIam();
    //
    // UsrAppartUserRich usrAppartUserRich = new UsrAppartUserRich();
    // usrAppartUserRich.setUsrRichGestUser(aUsrRichGestUser());
    // usrAppartUserRich.setTiAppartUserRich(ConstUsrAppartUserRich.TiAppartUserRich.UTENTE_NON_CODIFICATO.name());
    // usrAppartUserRich
    // .setTiAzioneRich(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CESSAZIONE.getDescrizione());
    // usrAppartUserRich.setFlAzioneRichEvasa("1");
    // usrAppartUserRich.setOrgEnteSiam(anEnteSiam());
    // usrAppartUserRich.setUsrUser(usrUser);
    //
    // helper.insertEntity(usrAppartUserRich, true);
    // idUsrAppartUserRich = usrAppartUserRich.getIdAppartUserRich();
    //
    // UsrUser usrUserReloaded = helper.findByIdForceLazyField(UsrUser.class, idUsrUser,
    // u -> u.getUsrAppartUserRiches().get(0).getTiAppartUserRich());
    // final String nmNomeUser = "MERGED";
    // usrUserReloaded.setNmNomeUser(nmNomeUser);
    // final String tiAzioneRich = ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RESET_PWD.getDescrizione();
    // usrUserReloaded.getUsrAppartUserRiches().get(0).setTiAzioneRich(tiAzioneRich);
    // helper.mergeEntity(usrUserReloaded);
    // assertEquals(nmNomeUser, helper.findById(UsrUser.class, idUsrUser).getNmNomeUser());
    // assertNotEquals(tiAzioneRich, helper.findById(UsrAppartUserRich.class, idUsrAppartUserRich));
    //
    // } finally {
    // delete(UsrUser.class, idUsrUser);
    // delete(UsrAppartUserRich.class, idUsrAppartUserRich);
    // }
    // }

    @Test
    public void UsrUser_persistCascdeUsrStatoUser() {
        Long idUsrUser = null;
        Long idUsrStatoUser = null;
        try {
            UsrUser usrUser = newUsrUser();

            UsrStatoUser usrStatoUser = new UsrStatoUser();
            usrStatoUser.setUsrUser(usrUser);
            usrStatoUser.setTsStato(new Timestamp(new Date().getTime()));
            usrStatoUser.setTiStatoUser(ConstUsrStatoUser.TiStatotUser.DISATTIVO.name());

            usrUser.setUsrStatoUsers(new ArrayList<>());
            usrUser.getUsrStatoUsers().add(usrStatoUser);

            helper.insertEntity(usrUser, true);
            idUsrUser = usrUser.getIdUserIam();
            idUsrStatoUser = usrStatoUser.getIdStatoUser();
            assertNotNull(idUsrUser);
            assertNotNull(idUsrStatoUser);
        } finally {
            delete(UsrStatoUser.class, idUsrStatoUser);
            delete(UsrUser.class, idUsrUser);
        }
    }

    @Test
    public void UsrUser_persistCascadeUsrUsoUserApplic() {
        Long idUserIam = null;
        Long idUsoUserApplic = null;
        try {
            UsrUser usrUser = newUsrUser();

            UsrUsoUserApplic usrUsoUserApplic = new UsrUsoUserApplic();
            usrUsoUserApplic.setAplApplic(anAplApplic());
            usrUsoUserApplic.setUsrUser(usrUser);

            usrUser.setUsrUsoUserApplics(new ArrayList<>());
            usrUser.getUsrUsoUserApplics().add(usrUsoUserApplic);

            helper.insertEntity(usrUser, true);
            idUserIam = usrUser.getIdUserIam();
            idUsoUserApplic = usrUsoUserApplic.getIdUsoUserApplic();
            assertNotNull(idUserIam);
            assertNotNull(idUsoUserApplic);

        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrUser.class, idUserIam);
        }
    }

    @Test
    public void UsrUser_mergeCascadeUsrUsoUserApplic() {
        Long idUserIam = null;
        Long idUsoUserApplic = null;
        try {
            UsrUser usrUser = newUsrUser();

            UsrUsoUserApplic usrUsoUserApplic = new UsrUsoUserApplic();
            usrUsoUserApplic.setAplApplic(anAplApplic());
            usrUsoUserApplic.setUsrUser(usrUser);

            usrUser.setUsrUsoUserApplics(new ArrayList<>());
            usrUser.getUsrUsoUserApplics().add(usrUsoUserApplic);

            helper.insertEntity(usrUser, true);
            idUserIam = usrUser.getIdUserIam();
            idUsoUserApplic = usrUsoUserApplic.getIdUsoUserApplic();

            UsrUser usrUserReloaded = helper.findByIdForceLazyField(UsrUser.class, idUserIam,
                    u -> u.getUsrUsoUserApplics().get(0).getAplApplic().getIdApplic());
            final AplApplic aplApplic = new AplApplic();
            aplApplic.setIdApplic(341L);
            usrUserReloaded.getUsrUsoUserApplics().get(0).setAplApplic(aplApplic);
            final String nmNomeUser = "MERGED";
            usrUserReloaded.setNmNomeUser(nmNomeUser);
            helper.mergeEntity(usrUserReloaded);
            assertEquals(nmNomeUser, helper.findById(UsrUser.class, idUserIam).getNmNomeUser());
            assertEquals(aplApplic.getIdApplic(),
                    helper.findById(UsrUsoUserApplic.class, idUsoUserApplic).getAplApplic().getIdApplic());

        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrUser.class, idUserIam);
        }
    }

    @Test
    public void UsrUser_persistCascadeUsrIndIpUser() {
        Long idUserIam = null;
        Long idUsrIndIpUser = null;
        try {
            UsrUser usrUser = newUsrUser();

            UsrIndIpUser usrIndIpUser = new UsrIndIpUser();
            usrIndIpUser.setUsrUser(usrUser);
            usrIndIpUser.setCdIndIpUser("127.0.0.1");

            usrUser.setUsrIndIpUsers(new ArrayList<>());
            usrUser.getUsrIndIpUsers().add(usrIndIpUser);

            helper.insertEntity(usrUser, true);
            idUserIam = usrUser.getIdUserIam();
            idUsrIndIpUser = usrIndIpUser.getIdIndIpUser();
            assertNotNull(idUsrIndIpUser);
            assertNotNull(idUserIam);

        } finally {
            delete(UsrIndIpUser.class, idUsrIndIpUser);
            delete(UsrUser.class, idUserIam);
        }
    }

    @Test
    public void UsrUser_mergeCascadeUsrIndIpUser() {
        Long idUserIam = null;
        Long idUsrIndIpUser = null;
        try {
            UsrUser usrUser = newUsrUser();

            UsrIndIpUser usrIndIpUser = new UsrIndIpUser();
            usrIndIpUser.setUsrUser(usrUser);
            usrIndIpUser.setCdIndIpUser("127.0.0.1");

            usrUser.setUsrIndIpUsers(new ArrayList<>());
            usrUser.getUsrIndIpUsers().add(usrIndIpUser);

            helper.insertEntity(usrUser, true);
            idUserIam = usrUser.getIdUserIam();
            idUsrIndIpUser = usrIndIpUser.getIdIndIpUser();

            UsrUser usrUserReloaded = helper.findByIdForceLazyField(UsrUser.class, idUserIam,
                    u -> u.getUsrIndIpUsers().get(0).getCdIndIpUser());

            final String nmNomeUser = "MERGED";
            usrUserReloaded.setNmNomeUser(nmNomeUser);
            final String cdIndIpUser = "255.0.0.0";
            usrUserReloaded.getUsrIndIpUsers().get(0).setCdIndIpUser(cdIndIpUser);
            helper.mergeEntity(usrUserReloaded);
            assertEquals(nmNomeUser, helper.findById(UsrUser.class, idUserIam).getNmNomeUser());
            assertEquals(cdIndIpUser, helper.findById(UsrIndIpUser.class, idUsrIndIpUser).getCdIndIpUser());
        } finally {
            delete(UsrIndIpUser.class, idUsrIndIpUser);
            delete(UsrUser.class, idUserIam);
        }
    }

    @Test
    public void UsrUser_persistCascadeUsrDichAbilEnteConvenz() {
        Long idUserIam = null;
        Long idUsrDichAbilEnteConvenz = null;
        try {
            UsrUser usrUser = newUsrUser();

            UsrDichAbilEnteConvenz dichAbilEnteConvenz = new UsrDichAbilEnteConvenz();
            dichAbilEnteConvenz.setUsrUser(usrUser);
            dichAbilEnteConvenz.setTiScopoDichAbilEnte("UN_ENTE");

            usrUser.setUsrDichAbilEnteConvenzs(new ArrayList<>());
            usrUser.getUsrDichAbilEnteConvenzs().add(dichAbilEnteConvenz);

            helper.insertEntity(usrUser, true);
            idUserIam = usrUser.getIdUserIam();
            idUsrDichAbilEnteConvenz = dichAbilEnteConvenz.getIdDichAbilEnteConvenz();
            assertNotNull(idUsrDichAbilEnteConvenz);
            assertNotNull(idUserIam);

        } finally {
            delete(UsrDichAbilEnteConvenz.class, idUsrDichAbilEnteConvenz);
            delete(UsrUser.class, idUserIam);
        }
    }

    @Test
    public void UsrUser_mergeCascadeUsrDichAbilEnteConvenz() {
        Long idUserIam = null;
        Long idUsrDichAbilEnteConvenz = null;
        try {
            UsrUser usrUser = newUsrUser();

            UsrDichAbilEnteConvenz dichAbilEnteConvenz = new UsrDichAbilEnteConvenz();
            dichAbilEnteConvenz.setUsrUser(usrUser);
            dichAbilEnteConvenz.setTiScopoDichAbilEnte("UN_ENTE");

            usrUser.setUsrDichAbilEnteConvenzs(new ArrayList<>());
            usrUser.getUsrDichAbilEnteConvenzs().add(dichAbilEnteConvenz);

            helper.insertEntity(usrUser, true);
            idUserIam = usrUser.getIdUserIam();
            idUsrDichAbilEnteConvenz = dichAbilEnteConvenz.getIdDichAbilEnteConvenz();

            UsrUser usrUserReloaded = helper.findByIdForceLazyField(UsrUser.class, idUserIam,
                    u -> u.getUsrDichAbilEnteConvenzs().get(0).getTiScopoDichAbilEnte());
            final String nmNomeUser = "merged";
            usrUserReloaded.setNmNomeUser(nmNomeUser);
            final String tiScopoDichAbilEnte = "UN_AMBIENTE";
            usrUserReloaded.getUsrDichAbilEnteConvenzs().get(0).setTiScopoDichAbilEnte(tiScopoDichAbilEnte);
            helper.mergeEntity(usrUserReloaded);
            assertEquals(nmNomeUser, helper.findById(UsrUser.class, idUserIam).getNmNomeUser());
            assertEquals(tiScopoDichAbilEnte,
                    helper.findById(UsrDichAbilEnteConvenz.class, idUsrDichAbilEnteConvenz).getTiScopoDichAbilEnte());

        } finally {
            delete(UsrDichAbilEnteConvenz.class, idUsrDichAbilEnteConvenz);
            delete(UsrUser.class, idUserIam);
        }
    }

    @Test
    public void UsrUser_persistCascadeUsrOldPsw() {
        Long idUser = null;
        Long idOldPsw = null;
        try {
            UsrUser user = newUsrUser();

            UsrOldPsw oldPsw = new UsrOldPsw();
            oldPsw.setCdPsw("VECCHIO PASSWORD");
            oldPsw.setCdSalt(JUNIT_ARQUILLIAN);
            oldPsw.setPgOldPsw(BigDecimal.valueOf(1985));
            oldPsw.setUsrUser(user);

            user.setUsrOldPsws(new ArrayList<>());
            user.getUsrOldPsws().add(oldPsw);

            helper.insertEntity(user, true);
            idUser = user.getIdUserIam();
            idOldPsw = oldPsw.getIdOldPsw();
            assertNotNull(idOldPsw);
            assertNotNull(idUser);

        } finally {
            delete(UsrOldPsw.class, idOldPsw);
            delete(UsrUser.class, idUser);
        }
    }

    @Test
    public void UsrUser_persistRemoveUsrOldPsw() {
        Long idUser = null;
        Long idOldPsw = null;
        try {
            UsrUser user = newUsrUser();

            UsrOldPsw oldPsw = new UsrOldPsw();
            oldPsw.setCdPsw("VECCHIO PASSWORD");
            oldPsw.setCdSalt(JUNIT_ARQUILLIAN);
            oldPsw.setPgOldPsw(BigDecimal.valueOf(1985));
            oldPsw.setUsrUser(user);

            user.setUsrOldPsws(new ArrayList<>());
            user.getUsrOldPsws().add(oldPsw);

            helper.insertEntity(user, true);
            idUser = user.getIdUserIam();
            idOldPsw = oldPsw.getIdOldPsw();

            helper.removeById(UsrUser.class, idUser);
            assertNull(helper.findById(UsrUser.class, idUser));
            assertNull(helper.findById(UsrOldPsw.class, idOldPsw));

        } finally {
            delete(UsrOldPsw.class, idOldPsw);
            delete(UsrUser.class, idUser);
        }
    }

    @Test
    public void UsrUser_mergeCascadeUsrOldPsw() {
        Long idUser = null;
        Long idOldPsw = null;
        try {
            UsrUser user = newUsrUser();

            UsrOldPsw oldPsw = new UsrOldPsw();
            oldPsw.setCdPsw("VECCHIO PASSWORD");
            oldPsw.setCdSalt(JUNIT_ARQUILLIAN);
            oldPsw.setPgOldPsw(BigDecimal.valueOf(1985));
            oldPsw.setUsrUser(user);

            user.setUsrOldPsws(new ArrayList<>());
            user.getUsrOldPsws().add(oldPsw);

            helper.insertEntity(user, true);
            idUser = user.getIdUserIam();
            idOldPsw = oldPsw.getIdOldPsw();

            UsrUser userReloaded = helper.findByIdForceLazyField(UsrUser.class, idUser,
                    u -> u.getUsrOldPsws().get(0).getCdPsw());
            final String cdPsw = "ALTRA PASSWORD";
            userReloaded.getUsrOldPsws().get(0).setCdPsw(cdPsw);
            final String nmNomeUser = "merged";
            userReloaded.setNmNomeUser(nmNomeUser);
            helper.mergeEntity(userReloaded);
            assertEquals(nmNomeUser, helper.findById(UsrUser.class, idUser).getNmNomeUser());
            assertEquals(cdPsw, helper.findById(UsrOldPsw.class, idOldPsw).getCdPsw());
        } finally {
            delete(UsrOldPsw.class, idOldPsw);
            delete(UsrUser.class, idUser);
        }
    }

    @Test
    public void UsrUsoUserApplic_persistCascadeUsrDichAbilDati() {
        Long idUsoUserApplic = null;
        Long idDichAbilDati = null;
        try {
            UsrUsoUserApplic usoUserApplic = new UsrUsoUserApplic();
            usoUserApplic.setUsrUser(aUsrUser());
            usoUserApplic.setAplApplic(anAplApplic());

            UsrDichAbilDati dichAbilDati = new UsrDichAbilDati();
            dichAbilDati.setUsrUsoUserApplic(usoUserApplic);
            dichAbilDati.setTiScopoDichAbilDati(ApplEnum.TiScopoDichAbilDati.UN_TIPO_DATO.name());
            dichAbilDati.setAplClasseTipoDato(anAplClasseTipoDato());

            usoUserApplic.setUsrDichAbilDatis(new ArrayList<>());
            usoUserApplic.getUsrDichAbilDatis().add(dichAbilDati);

            helper.insertEntity(usoUserApplic, true);
            idUsoUserApplic = usoUserApplic.getIdUsoUserApplic();
            idDichAbilDati = dichAbilDati.getIdDichAbilDati();
            assertNotNull(idUsoUserApplic);
            assertNotNull(idDichAbilDati);
        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrDichAbilDati.class, idDichAbilDati);
        }
    }

    @Test
    public void UsrUsoUserApplic_removeCascadeUsrDichAbilDati() {
        Long idUsoUserApplic = null;
        Long idDichAbilDati = null;
        try {
            UsrUsoUserApplic usoUserApplic = new UsrUsoUserApplic();
            usoUserApplic.setUsrUser(aUsrUser());
            usoUserApplic.setAplApplic(anAplApplic());

            UsrDichAbilDati dichAbilDati = new UsrDichAbilDati();
            dichAbilDati.setUsrUsoUserApplic(usoUserApplic);
            dichAbilDati.setTiScopoDichAbilDati(ApplEnum.TiScopoDichAbilDati.UN_TIPO_DATO.name());
            dichAbilDati.setAplClasseTipoDato(anAplClasseTipoDato());

            usoUserApplic.setUsrDichAbilDatis(new ArrayList<>());
            usoUserApplic.getUsrDichAbilDatis().add(dichAbilDati);

            helper.insertEntity(usoUserApplic, true);
            idUsoUserApplic = usoUserApplic.getIdUsoUserApplic();
            idDichAbilDati = dichAbilDati.getIdDichAbilDati();

            helper.removeById(UsrUsoUserApplic.class, idUsoUserApplic);
            assertNull(helper.findById(UsrUsoUserApplic.class, idUsoUserApplic));
            assertNull(helper.findById(UsrDichAbilDati.class, idDichAbilDati));
        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrDichAbilDati.class, idDichAbilDati);
        }
    }

    @Test
    public void UsrUsoUserApplic_mergeCascadeUsrDichAbilDati() {
        Long idUsoUserApplic = null;
        Long idDichAbilDati = null;
        try {
            UsrUsoUserApplic usoUserApplic = new UsrUsoUserApplic();
            usoUserApplic.setUsrUser(aUsrUser());
            usoUserApplic.setAplApplic(anAplApplic());

            UsrDichAbilDati dichAbilDati = new UsrDichAbilDati();
            dichAbilDati.setUsrUsoUserApplic(usoUserApplic);
            dichAbilDati.setTiScopoDichAbilDati(ApplEnum.TiScopoDichAbilDati.UN_TIPO_DATO.name());
            dichAbilDati.setAplClasseTipoDato(anAplClasseTipoDato());

            usoUserApplic.setUsrDichAbilDatis(new ArrayList<>());
            usoUserApplic.getUsrDichAbilDatis().add(dichAbilDati);

            helper.insertEntity(usoUserApplic, true);
            idUsoUserApplic = usoUserApplic.getIdUsoUserApplic();
            idDichAbilDati = dichAbilDati.getIdDichAbilDati();

            UsrUsoUserApplic usoUserApplicReloaded = helper.findByIdForceLazyField(UsrUsoUserApplic.class,
                    idUsoUserApplic, u -> u.getUsrDichAbilDatis().get(0).getTiScopoDichAbilDati());
            final AplApplic aplApplic = new AplApplic();
            aplApplic.setIdApplic(341L);
            usoUserApplicReloaded.setAplApplic(aplApplic);
            final String tiScopoDichAbilDati = ApplEnum.TiScopoDichAbilDati.ALL_ORG_CHILD.name();
            usoUserApplicReloaded.getUsrDichAbilDatis().get(0).setTiScopoDichAbilDati(tiScopoDichAbilDati);
            helper.mergeEntity(usoUserApplicReloaded);
            assertEquals(aplApplic.getIdApplic(),
                    helper.findById(UsrUsoUserApplic.class, idUsoUserApplic).getAplApplic().getIdApplic());
            assertEquals(tiScopoDichAbilDati,
                    helper.findById(UsrDichAbilDati.class, idDichAbilDati).getTiScopoDichAbilDati());
        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrDichAbilDati.class, idDichAbilDati);
        }
    }

    @Test
    public void UsrUsoUserApplic_persistCascadeUsrDichAbilOrganiz() {
        Long idUsoUserApplic = null;
        Long idDichAbilOrganiz = null;
        try {
            UsrUsoUserApplic usoUserApplic = new UsrUsoUserApplic();
            usoUserApplic.setUsrUser(aUsrUser());
            usoUserApplic.setAplApplic(anAplApplic());

            UsrDichAbilOrganiz dichAbilOrganiz = new UsrDichAbilOrganiz();
            dichAbilOrganiz.setUsrUsoUserApplic(usoUserApplic);
            dichAbilOrganiz.setTiScopoDichAbilOrganiz(ApplEnum.TiScopoDichAbilOrganiz.ORG_DEFAULT.name());

            usoUserApplic.setUsrDichAbilOrganizs(new ArrayList<>());
            usoUserApplic.getUsrDichAbilOrganizs().add(dichAbilOrganiz);

            helper.insertEntity(usoUserApplic, true);
            idUsoUserApplic = usoUserApplic.getIdUsoUserApplic();
            idDichAbilOrganiz = dichAbilOrganiz.getIdDichAbilOrganiz();
            assertNotNull(idUsoUserApplic);
            assertNotNull(idDichAbilOrganiz);
        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrDichAbilOrganiz.class, idDichAbilOrganiz);
        }
    }

    @Test
    public void UsrUsoUserApplic_removeCascadeUsrDichAbilOrganiz() {
        Long idUsoUserApplic = null;
        Long idDichAbilOrganiz = null;
        try {
            UsrUsoUserApplic usoUserApplic = new UsrUsoUserApplic();
            usoUserApplic.setUsrUser(aUsrUser());
            usoUserApplic.setAplApplic(anAplApplic());

            UsrDichAbilOrganiz dichAbilOrganiz = new UsrDichAbilOrganiz();
            dichAbilOrganiz.setUsrUsoUserApplic(usoUserApplic);
            dichAbilOrganiz.setTiScopoDichAbilOrganiz(ApplEnum.TiScopoDichAbilOrganiz.ORG_DEFAULT.name());

            usoUserApplic.setUsrDichAbilOrganizs(new ArrayList<>());
            usoUserApplic.getUsrDichAbilOrganizs().add(dichAbilOrganiz);

            helper.insertEntity(usoUserApplic, true);
            idUsoUserApplic = usoUserApplic.getIdUsoUserApplic();
            idDichAbilOrganiz = dichAbilOrganiz.getIdDichAbilOrganiz();
            helper.removeById(UsrUsoUserApplic.class, idUsoUserApplic);
            assertNull(helper.findById(UsrUsoUserApplic.class, idUsoUserApplic));
            assertNull(helper.findById(UsrDichAbilOrganiz.class, idDichAbilOrganiz));
        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrDichAbilOrganiz.class, idDichAbilOrganiz);
        }
    }

    @Test
    public void UsrUsoUserApplic_mergeCascadeUsrDichAbilOrganiz() {
        Long idUsoUserApplic = null;
        Long idDichAbilOrganiz = null;
        try {
            UsrUsoUserApplic usoUserApplic = new UsrUsoUserApplic();
            usoUserApplic.setUsrUser(aUsrUser());
            usoUserApplic.setAplApplic(anAplApplic());

            UsrDichAbilOrganiz dichAbilOrganiz = new UsrDichAbilOrganiz();
            dichAbilOrganiz.setUsrUsoUserApplic(usoUserApplic);
            dichAbilOrganiz.setTiScopoDichAbilOrganiz(ApplEnum.TiScopoDichAbilOrganiz.ORG_DEFAULT.name());

            usoUserApplic.setUsrDichAbilOrganizs(new ArrayList<>());
            usoUserApplic.getUsrDichAbilOrganizs().add(dichAbilOrganiz);

            helper.insertEntity(usoUserApplic, true);
            idUsoUserApplic = usoUserApplic.getIdUsoUserApplic();
            idDichAbilOrganiz = dichAbilOrganiz.getIdDichAbilOrganiz();
            helper.removeById(UsrUsoUserApplic.class, idUsoUserApplic);
            assertNull(helper.findById(UsrUsoUserApplic.class, idUsoUserApplic));
            assertNull(helper.findById(UsrDichAbilOrganiz.class, idDichAbilOrganiz));
        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrDichAbilOrganiz.class, idDichAbilOrganiz);
        }
    }

    @Test
    public void UsrUsoUserApplic_persistCascadeUsrUsoRuoloUserDefault() {
        Long idUsoUserApplic = null;
        Long idUsoRuoloUserDefault = null;
        try {
            UsrUsoUserApplic usoUserApplic = new UsrUsoUserApplic();
            usoUserApplic.setUsrUser(aUsrUser());
            usoUserApplic.setAplApplic(anAplApplic());

            UsrUsoRuoloUserDefault usoRuoloUserDefault = new UsrUsoRuoloUserDefault();
            usoRuoloUserDefault.setPrfRuolo(aPrfRuolo());
            usoRuoloUserDefault.setUsrUsoUserApplic(usoUserApplic);

            usoUserApplic.setUsrUsoRuoloUserDefaults(new ArrayList<>());
            usoUserApplic.getUsrUsoRuoloUserDefaults().add(usoRuoloUserDefault);

            helper.insertEntity(usoUserApplic, true);
            idUsoUserApplic = usoUserApplic.getIdUsoUserApplic();
            idUsoRuoloUserDefault = usoRuoloUserDefault.getIdUsoRuoloUserDefault();
            assertNotNull(idUsoUserApplic);
            assertNotNull(idUsoRuoloUserDefault);
        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrUsoRuoloUserDefault.class, idUsoRuoloUserDefault);
        }
    }

    @Test
    public void UsrUsoUserApplic_removeCascadeUsrUsoRuoloUserDefault() {
        Long idUsoUserApplic = null;
        Long idUsoRuoloUserDefault = null;
        try {
            UsrUsoUserApplic usoUserApplic = new UsrUsoUserApplic();
            usoUserApplic.setUsrUser(aUsrUser());
            usoUserApplic.setAplApplic(anAplApplic());

            UsrUsoRuoloUserDefault usoRuoloUserDefault = new UsrUsoRuoloUserDefault();
            usoRuoloUserDefault.setPrfRuolo(aPrfRuolo());
            usoRuoloUserDefault.setUsrUsoUserApplic(usoUserApplic);

            usoUserApplic.setUsrUsoRuoloUserDefaults(new ArrayList<>());
            usoUserApplic.getUsrUsoRuoloUserDefaults().add(usoRuoloUserDefault);

            helper.insertEntity(usoUserApplic, true);
            idUsoUserApplic = usoUserApplic.getIdUsoUserApplic();
            idUsoRuoloUserDefault = usoRuoloUserDefault.getIdUsoRuoloUserDefault();
            helper.removeById(UsrUsoUserApplic.class, idUsoUserApplic);
            assertNull(helper.findById(UsrUsoUserApplic.class, idUsoUserApplic));
            assertNull(helper.findById(UsrUsoRuoloUserDefault.class, idUsoRuoloUserDefault));
        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrUsoRuoloUserDefault.class, idUsoRuoloUserDefault);
        }
    }

    @Test
    public void UsrUsoUserApplic_mergeCascadeUsrUsoRuoloUserDefault() {
        Long idUsoUserApplic = null;
        Long idUsoRuoloUserDefault = null;
        try {
            UsrUsoUserApplic usoUserApplic = new UsrUsoUserApplic();
            usoUserApplic.setUsrUser(aUsrUser());
            usoUserApplic.setAplApplic(anAplApplic());

            UsrUsoRuoloUserDefault usoRuoloUserDefault = new UsrUsoRuoloUserDefault();
            usoRuoloUserDefault.setPrfRuolo(aPrfRuolo());
            usoRuoloUserDefault.setUsrUsoUserApplic(usoUserApplic);

            usoUserApplic.setUsrUsoRuoloUserDefaults(new ArrayList<>());
            usoUserApplic.getUsrUsoRuoloUserDefaults().add(usoRuoloUserDefault);

            helper.insertEntity(usoUserApplic, true);
            idUsoUserApplic = usoUserApplic.getIdUsoUserApplic();
            idUsoRuoloUserDefault = usoRuoloUserDefault.getIdUsoRuoloUserDefault();

            UsrUsoUserApplic usoUserApplicReloaded = helper.findByIdForceLazyField(UsrUsoUserApplic.class,
                    idUsoUserApplic, u -> u.getUsrUsoRuoloUserDefaults().get(0).getPrfRuolo().getIdRuolo());
            final AplApplic aplApplic = new AplApplic();
            aplApplic.setIdApplic(341L);
            usoUserApplicReloaded.setAplApplic(aplApplic);
            final PrfRuolo ruolo = new PrfRuolo();
            ruolo.setIdRuolo(1482L);
            usoUserApplicReloaded.getUsrUsoRuoloUserDefaults().get(0).setPrfRuolo(ruolo);
            helper.mergeEntity(usoUserApplicReloaded);
            assertEquals(aplApplic.getIdApplic(),
                    helper.findById(UsrUsoUserApplic.class, idUsoUserApplic).getAplApplic().getIdApplic());
            assertEquals(ruolo.getIdRuolo(),
                    helper.findById(UsrUsoRuoloUserDefault.class, idUsoRuoloUserDefault).getPrfRuolo().getIdRuolo());

        } finally {
            delete(UsrUsoUserApplic.class, idUsoUserApplic);
            delete(UsrUsoRuoloUserDefault.class, idUsoRuoloUserDefault);
        }
    }

    private UsrRichGestUser aUsrRichGestUser() {
        final UsrRichGestUser usrRichGestUser = new UsrRichGestUser();
        usrRichGestUser.setIdRichGestUser(82L);
        return usrRichGestUser;
    }

    private AplSistemaVersante anAplSistemaVersante() {
        final AplSistemaVersante sistemaVersante = new AplSistemaVersante();
        sistemaVersante.setIdSistemaVersante(33L);
        return sistemaVersante;
    }

    private UsrUser newUsrUser() {
        UsrUser usrUser = new UsrUser();
        usrUser.setNmUserid(JUNIT_ARQUILLIAN);
        usrUser.setCdPsw(JUNIT_ARQUILLIAN);
        usrUser.setNmNomeUser(JUNIT_ARQUILLIAN);
        usrUser.setNmCognomeUser(JUNIT_ARQUILLIAN);
        usrUser.setFlAttivo("0");
        usrUser.setDtRegPsw(new Date());
        usrUser.setDtScadPsw(new Date());
        usrUser.setDsEmail(JUNIT_ARQUILLIAN);
        usrUser.setFlContrIp("0");
        usrUser.setTipoUser(ApplEnum.TipoUser.AUTOMA.name());
        usrUser.setLogAgente(aLogAgente());
        usrUser.setOrgEnteSiam(anEnteSiam());
        usrUser.setFlRespEnteConvenz("0");
        usrUser.setFlUtenteDitta("0");
        usrUser.setFlUserAdmin("0");
        return usrUser;
    }

    private LogAgente aLogAgente() {
        final LogAgente logAgente = new LogAgente();
        logAgente.setIdAgente(-99L);
        return logAgente;
    }

    private UsrUser aUsrUser() {
        final UsrUser usrUser = new UsrUser();
        usrUser.setIdUserIam(1L);
        return usrUser;
    }

    private UsrDichAbilOrganiz anUsrDichAbilOrganiz() {
        final UsrDichAbilOrganiz abilOrganiz = new UsrDichAbilOrganiz();
        abilOrganiz.setIdDichAbilOrganiz(1681L);
        return abilOrganiz;
    }

    private AplClasseTipoDato anAplClasseTipoDato() {
        AplClasseTipoDato aplClasseTipoDato = new AplClasseTipoDato();
        aplClasseTipoDato.setIdClasseTipoDato(185L);
        return aplClasseTipoDato;
    }

    private UsrOrganizIam newUsrOrganizIam() {
        UsrOrganizIam usrOrganizIam = new UsrOrganizIam();
        usrOrganizIam.setAplApplic(anAplApplic());
        usrOrganizIam.setIdOrganizApplic(BigDecimal.valueOf(1985));
        usrOrganizIam.setAplTipoOrganiz(anAplTipoOrganiz());
        usrOrganizIam.setNmOrganiz(JUNIT_ARQUILLIAN);
        usrOrganizIam.setDsOrganiz(JUNIT_ARQUILLIAN);
        return usrOrganizIam;
    }

    private AplTipoOrganiz anAplTipoOrganiz() {
        AplTipoOrganiz aplTipoOrganiz = new AplTipoOrganiz();
        aplTipoOrganiz.setIdTipoOrganiz(184L);
        return aplTipoOrganiz;
    }

    private void delete(Class clazz, Long id) {
        try {
            if (id != null) {
                System.out.println("*** ROLLBACK " + clazz.getSimpleName() + " BY ID " + id);
                helper.removeById(clazz, id);
            }
        } catch (Exception e) {
            return;
        }
    }
}
