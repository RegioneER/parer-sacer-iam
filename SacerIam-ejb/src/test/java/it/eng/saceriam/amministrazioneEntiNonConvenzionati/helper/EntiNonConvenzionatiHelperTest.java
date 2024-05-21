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
package it.eng.saceriam.amministrazioneEntiNonConvenzionati.helper;

import static it.eng.ArquillianUtils.*;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class EntiNonConvenzionatiHelperTest {

    @EJB
    private EntiNonConvenzionatiHelper helper;

    @Deployment
    public static JavaArchive createTestArchive() {
        return getSacerIamArchive(EntiNonConvenzionatiHelper.class).addClass(EntiNonConvenzionatiHelperTest.class)
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
    }

    @Test
    public void retrieveOrgAmbitoTerritQueryIsOk() {
        String tiAmbitoTerrit = aString();
        helper.retrieveOrgAmbitoTerrit(tiAmbitoTerrit);
        assertTrue(true);
    }

    @Test
    public void getOrgAmbitoTerritByCodeQueryIsOk() {
        String cdAmbitoTerritoriale = aString();
        helper.getOrgAmbitoTerritByCode(cdAmbitoTerritoriale);
        assertTrue(true);
    }

    @Test
    public void retrieveOrgAmbitoTerritChildListQueryIsOk() {
        List<BigDecimal> idAmbitoTerritoriale = aListOfBigDecimal(2);
        helper.retrieveOrgAmbitoTerritChildList(idAmbitoTerritoriale);
        assertTrue(true);
    }

    @Test
    public void retrieveIdAmbitoTerritChildListQueryIsOk() {
        BigDecimal idAmbitoTerrit = aBigDecimal();
        helper.retrieveIdAmbitoTerritChildList(idAmbitoTerrit);
        assertTrue(true);
    }

    @Test
    public void retrieveOrgAmbitoTerritParentQueryIsOk() {
        BigDecimal idAmbitoTerrit = aBigDecimal();
        helper.retrieveOrgAmbitoTerritParent(idAmbitoTerrit);
        assertTrue(true);
    }

    @Test
    public void retrieveOrgCategEnteListQueryIsOk() {

        helper.retrieveOrgCategEnteList();
        assertTrue(true);
    }

    @Test
    public void getOrgEnteSiamQueryIsOk() {
        String nmEnteSiam = aString();

        helper.getOrgEnteSiam(nmEnteSiam);
        assertTrue(true);
    }

    @Test
    public void getOrgEnteConvenzByTiEnteConvenzQueryIsOk() {
        for (ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz : ConstOrgEnteSiam.TiEnteConvenz.values()) {
            helper.getOrgEnteConvenzByTiEnteConvenz(tiEnteConvenz);
            assertTrue(true);
        }
    }

    @Test
    @Ignore("Su SVIL ci sono record di ORG_ENTE_SIAM con  TI_ENTE_NON_CONVENZ = 'SOGGETTO_ATTUATORE'  che da errore, è un work in progress")
    public void getOrgEnteNonConvenzListQueryIsOk() {
        BigDecimal idEnteNonConvenzDaEscludere = aBigDecimal();
        helper.getOrgEnteNonConvenzList(idEnteNonConvenzDaEscludere);
        assertTrue(true);
    }

    @Test
    public void getOrgSuptEsternoEnteConvenzListQueryIsOk() {
        BigDecimal idEnteFornitEst = aBigDecimal();

        helper.getOrgSuptEsternoEnteConvenzList(idEnteFornitEst);
        assertTrue(true);
    }

    @Test
    public void getOrgEnteConvenzOrgListQueryIsOk() {
        BigDecimal idEnteFornitEst = aBigDecimal();

        helper.getOrgEnteConvenzOrgList(idEnteFornitEst);
        assertTrue(true);
    }

    @Test
    public void retrieveOrgEnteArkRifQueryIsOk() {
        BigDecimal idEnteConvenz = aBigDecimal();

        helper.retrieveOrgEnteArkRif(idEnteConvenz);
        assertTrue(true);
    }

    @Test
    public void retrieveOrgEnteUserRifQueryIsOk() {
        BigDecimal idEnteConvenz = aBigDecimal();

        helper.retrieveOrgEnteUserRif(idEnteConvenz);
        assertTrue(true);
    }

    @Test
    public void retrieveOrgEnteNonConvenzListQueryIsOk() {
        String nmEnteSiam = aString();
        BigDecimal idUserIamCor = aBigDecimal();
        String flEnteCessato = aString();
        List<BigDecimal> idArchivista = aListOfBigDecimal(2);
        String noArchivista = aString();
        Date dataOdierna = todayTs();
        String tiEnteNonConvenz = aString();

        helper.retrieveOrgEnteNonConvenzList(nmEnteSiam, idUserIamCor, flEnteCessato, idArchivista, noArchivista,
                dataOdierna, tiEnteNonConvenz);
        assertTrue(true);
    }

    @Test
    public void retrieveOrgAccordoVigilQueryIsOk() {
        BigDecimal idEnteOrganoVigil = aBigDecimal();

        helper.retrieveOrgAccordoVigil(idEnteOrganoVigil);
        assertTrue(true);
    }

    @Test
    public void existsEnteConvenzOrgEnteCorrispQueryIsOk() {
        BigDecimal idOrganizIam = aBigDecimal();
        BigDecimal idEnteSiamProdutCorrisp = aBigDecimal();

        helper.existsEnteConvenzOrgEnteCorrisp(idOrganizIam, idEnteSiamProdutCorrisp);
        assertTrue(true);
    }

    @Test
    public void retrieveOrgVigilEnteProdutQueryIsOk() {
        BigDecimal idAccordoVigil = aBigDecimal();

        helper.retrieveOrgVigilEnteProdut(idAccordoVigil);
        assertTrue(true);
    }

    @Test
    public void getUsrOrganizIamQueryIsOk() {
        String nmApplic = aString();
        String nmTipoOrganiz = aString();
        BigDecimal idOrganizApplic = aBigDecimal();

        helper.getUsrOrganizIam(nmApplic, nmTipoOrganiz, idOrganizApplic);
        assertTrue(true);
    }

    @Test
    public void isUtenteArchivistaInEnteConvenzQueryIsOk() {
        BigDecimal idEnteConvenz = aBigDecimal();
        BigDecimal idUserIam = aBigDecimal();

        helper.isUtenteArchivistaInEnteConvenz(idEnteConvenz, idUserIam);
        assertTrue(true);
    }

    @Test
    public void isReferenteEnteInEnteConvenzQueryIsOk() {
        BigDecimal idEnteConvenz = aBigDecimal();
        BigDecimal idUserIam = aBigDecimal();

        helper.isReferenteEnteInEnteConvenz(idEnteConvenz, idUserIam);
        assertTrue(true);
    }

    @Test
    public void isReferenteEnteCessatoQueryIsOk() {
        BigDecimal idUserIam = aBigDecimal();

        helper.isReferenteEnteCessato(idUserIam);
        assertTrue(true);
    }

    @Test
    public void retrieveUtentiArchivistiQueryIsOk() {

        helper.retrieveUtentiArchivisti();
        assertTrue(true);
    }

    @Test
    public void retrieveAmbientiEntiConvenzAbilitatiQueryIsOk() {
        BigDecimal idUserIam = aBigDecimal();

        helper.retrieveAmbientiEntiConvenzAbilitati(idUserIam);
        assertTrue(true);
    }

    @Test
    public void retrieveAmbientiEntiXenteAbilitatiQueryIsOk() {
        BigDecimal idUserIam = aBigDecimal();

        helper.retrieveAmbientiEntiXenteAbilitati(idUserIam);
        assertTrue(true);
    }

    @Test
    public void retrieveAbilAmbEnteXEnteValidAmbientiQueryIsOk() {
        BigDecimal idUserIam = aBigDecimal();

        helper.retrieveAbilAmbEnteXEnteValidAmbienti(idUserIam);
        assertTrue(true);
    }

    @Test
    public void retrieveEntiConvenzAbilitatiAccordoValidoQueryIsOk() {
        BigDecimal idUserIamCor = aBigDecimal();
        BigDecimal idAmbienteEnteConvenz = aBigDecimal();

        helper.retrieveEntiConvenzAbilitatiAccordoValido(idUserIamCor, idAmbienteEnteConvenz);
        assertTrue(true);
    }

    @Test
    public void existsEnteSuptPerEnteFornitQueryIsOk() {
        BigDecimal idEnteFornitEst = aBigDecimal();
        BigDecimal idEnteProdut = aBigDecimal();
        Date dtIniValSupt = todayTs();
        Date dtFinValSupt = tomorrowTs();
        BigDecimal idSuptEstEnteConvenzDaEscludere = aBigDecimal();

        helper.existsEnteSuptPerEnteFornit(idEnteFornitEst, idEnteProdut, dtIniValSupt, dtFinValSupt,
                idSuptEstEnteConvenzDaEscludere);
        assertTrue(true);
    }

    @Test
    public void countEnteSuptPerEnteFornitQueryIsOk() {
        BigDecimal idEnteFornitEst = aBigDecimal();
        BigDecimal idEnteProdut = aBigDecimal();

        helper.countEnteSuptPerEnteFornit(idEnteFornitEst, idEnteProdut);
        assertTrue(true);
    }

    @Test
    public void getAplSistemaVersanteByIdQueryIsOk() {
        BigDecimal idEnteSiam = aBigDecimal();

        helper.getAplSistemaVersanteById(idEnteSiam);
        assertTrue(true);
    }

    @Test
    public void getStrutUnitaDocAccordoOrganizMapQueryIsOk() {
        BigDecimal idOrganizApplic = BigDecimal.ZERO;
        try {
            helper.getStrutUnitaDocAccordoOrganizMap(idOrganizApplic);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("IndexOutOfBoundsException"));
        }
    }
}
