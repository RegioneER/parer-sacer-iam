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
package it.eng.saceriam.web.helper;

import static it.eng.ArquillianUtils.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class SistemiVersantiHelperTest {

    @EJB
    private SistemiVersantiHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        final JavaArchive archive = getSacerIamArchive(SistemiVersantiHelper.class)
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity")
                .addClass(SistemiVersantiHelperTest.class).addPackages(true, "it.eng.saceriam.util", "org.joda.time");
        return archive;
    }

    @Test
    public void getAplVRicSistemaVersanteList_SistemiVersantiHelperFiltriSistemiVersantiPlain_queryIsOk() {
        long idUserIam = aLong();
        String denominazione = aString();
        String descrizione = aString();
        String produttore = aString();
        String versione = aString();
        BigDecimal idOrganizApplic = aBigDecimal();
        String flCessato = aFlag();
        String flIntegrazione = aFlag();
        List<BigDecimal> idArchivista = aListOfBigDecimal(2);
        String noArchivista = aString();
        String flAssociaPersonaFisica = aFlag();
        SistemiVersantiHelper.FiltriSistemiVersantiPlain filtri = new SistemiVersantiHelper.FiltriSistemiVersantiPlain(
                idUserIam, denominazione, descrizione, produttore, versione, idOrganizApplic, flCessato, flIntegrazione,
                idArchivista, noArchivista, flAssociaPersonaFisica);

        helper.getAplVRicSistemaVersanteList(filtri);
        assertTrue(true);
    }

    @Test
    public void getAplVRicSistemaVersante_1_queryIsOk() {
        BigDecimal idSistemaVersante = aBigDecimal();
        BigDecimal idUserIam = aBigDecimal();
        BigDecimal idOrganizIam = aBigDecimal();

        helper.getAplVRicSistemaVersante(idSistemaVersante, idUserIam, idOrganizIam);
        assertTrue(true);
    }

    @Test
    public void getAplVRicSistemaVersante_BigDecimal_2_queryIsOk() {
        BigDecimal idSistemaVersante = aBigDecimal();
        long idUserIam = aLong();

        helper.getAplVRicSistemaVersante(idSistemaVersante, idUserIam);
        assertTrue(true);
    }

    @Test
    public void canDeleteSistemaVersante_queryIsOk() {
        long idSistemaVersante = aLong();

        helper.canDeleteSistemaVersante(idSistemaVersante);
        assertTrue(true);
    }

    @Test
    public void getAplSistemaVersanteByName_queryIsOk() {
        String denominazione = aString();

        helper.getAplSistemaVersanteByName(denominazione);
        assertTrue(true);
    }

    @Test
    public void getAplSistemaVersanteValidiList_queryIsOk() {
        String tipoUser = aString();
        BigDecimal idEnteUtente = aBigDecimal();

        helper.getAplSistemaVersanteValidiList(tipoUser, idEnteUtente);
        tipoUser = "AUTOMA";
        helper.getAplSistemaVersanteValidiList(tipoUser, idEnteUtente);

        tipoUser = "PERSONA_FISICA";
        helper.getAplSistemaVersanteValidiList(tipoUser, idEnteUtente);

        assertTrue(true);
    }

    @Test
    public void getAplSistemaVersanteList_queryIsOk() {

        helper.getAplSistemaVersanteList();
        assertTrue(true);
    }

    @Test
    public void checkSistemaVersantePerAutoma_queryIsOk() {
        BigDecimal idSistemaVersante = aBigDecimal();
        BigDecimal idEnteUtente = aBigDecimal();

        helper.checkSistemaVersantePerAutoma(idSistemaVersante, idEnteUtente);
        assertTrue(true);
    }

    @Test
    public void getOrganizUltimoLivelloSacerSistemaVersante_queryIsOk() {
        BigDecimal idSistemaVersante = aBigDecimal();

        helper.getOrganizUltimoLivelloSacerSistemaVersante(idSistemaVersante);
        assertTrue(true);
    }

    @Test
    public void getOrganizUltimoLivelloSacer_queryIsOk() {

        helper.getOrganizUltimoLivelloSacer();
        assertTrue(true);
    }

    @Test
    public void getAplSistemaVersArkRifList_queryIsOk() {
        BigDecimal idSistemaVersante = aBigDecimal();

        helper.getAplSistemaVersArkRifList(idSistemaVersante);
        assertTrue(true);
    }

    @Test
    public void getAplSistemaVersanteUserRefList_queryIsOk() {
        BigDecimal idSistemaVersante = aBigDecimal();

        helper.getAplSistemaVersanteUserRefList(idSistemaVersante);
        assertTrue(true);
    }

    @Test
    public void isUtenteArchivistaInSistemaVersante_queryIsOk() {
        BigDecimal idSistemaVersante = aBigDecimal();
        BigDecimal idUserIam = aBigDecimal();

        helper.isUtenteArchivistaInSistemaVersante(idSistemaVersante, idUserIam);
        assertTrue(true);
    }

    @Test
    public void isReferenteDittaProduttriceInSistemaVersante_queryIsOk() {
        BigDecimal idSistemaVersante = aBigDecimal();
        BigDecimal idUserIam = aBigDecimal();

        helper.isReferenteDittaProduttriceInSistemaVersante(idSistemaVersante, idUserIam);
        assertTrue(true);
    }

    @Test
    public void getAplVLisOrganizUsoSisVers_queryIsOk() {
        BigDecimal idSistemaVersante = aBigDecimal();

        helper.getAplVLisOrganizUsoSisVers(idSistemaVersante);
        assertTrue(true);
    }

    @Test
    public void existsUtentiAssociatiSistemaVersante_queryIsOk() {
        BigDecimal idSistemaVersante = aBigDecimal();
        String tipoUser = aString();

        helper.existsUtentiAssociatiSistemaVersante(idSistemaVersante, tipoUser);
        assertTrue(true);
    }

    @Test
    @Ignore("da lanciare a mano, fa un a insert")
    public void insertAplSistemaVersante_queryIsOk() {
        String denominazione = "TEST_STRING";
        String descrizione = "TEST_STRING";
        String versione = "TEST_STRING";
        BigDecimal idEnteSiam = BigDecimal.ZERO;
        String dsEmail = "TEST_STRING";
        String flPec = "TEST_STRING";
        String flIntegrazione = aFlag();
        String flAssociaPersonaFisica = aFlag();
        Date dtIniVal = todayTs();
        Date dtFineVal = tomorrowTs();
        String dsNote = "TEST_STRING";

        helper.insertAplSistemaVersante(denominazione, descrizione, versione, idEnteSiam, dsEmail, flPec,
                flIntegrazione, flAssociaPersonaFisica, dtIniVal, dtFineVal, dsNote);
        assertTrue(true);
    }
}
