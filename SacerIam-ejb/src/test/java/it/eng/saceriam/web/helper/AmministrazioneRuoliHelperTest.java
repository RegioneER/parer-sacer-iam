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

package it.eng.saceriam.web.helper;

import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.spagoLite.form.base.BaseElements;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static it.eng.ArquillianUtils.*;
import static org.junit.Assert.*;

/**
 * @author manuel.bertuzzi@eng.it
 */
@RunWith(Arquillian.class)
public class AmministrazioneRuoliHelperTest {
    @EJB
    private AmministrazioneRuoliHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        final JavaArchive archive = getSacerIamArchive(AmministrazioneRuoliHelper.class)
                .addClass(AmministrazioneRuoliHelperTest.class)
                .addPackages(true, "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
        return archive;
    }

    @Test
    public void getPrfVLisRuoloList_queryIsOk() {
        Set<String> nmApplics = aSetOfString(2);
        String tiStatoAllinea1 = aString();
        String tiStatoAllinea2 = aString();
        String nmRuolo = aString();
        String tiRuolo = aString();
        String tiCategRuolo = aString();
        helper.getPrfVLisRuoloList(nmApplics, tiStatoAllinea1, tiStatoAllinea2, nmRuolo, tiRuolo, tiCategRuolo);
        assertTrue(true);
    }

    @Test
    public void getPrfVLisRuolo_queryIsOk() {
        BigDecimal idRuolo = aBigDecimal();
        try {
            helper.getPrfVLisRuolo(idRuolo);
            assertTrue(true);
        } catch (Exception e) {
            assertNoResultException(e);
        }
    }

    @Test
    public void getIdUsoRuoloApplicSet_queryIsOk() {
        BigDecimal idRuolo = aBigDecimal();
        helper.getIdUsoRuoloApplicSet(idRuolo);
        assertTrue(true);
    }

    @Test
    public void getPrfRuoli_queryIsOk() {
        helper.getPrfRuoli();
        assertTrue(true);
    }

    @Test
    public void getAplApplicList_queryIsOk() {
        helper.getAplApplicList();
        assertTrue(true);
    }

    @Test
    public void getPrfUsoRuoloApplicList_queryIsOk() {
        Set<BigDecimal> idUsoRuoloApplic = aSetOfBigDecimal(2);
        helper.getPrfUsoRuoloApplicList(idUsoRuoloApplic);
        assertTrue(true);
    }

    @Test
    public void getPrfVLisDichAutorList_3args_1_queryIsOk() {
        Set<BigDecimal> idUsoRuoloSet = aSetOfBigDecimal(2);
        String tiDichAutor = aString();
        BigDecimal idApplic = aBigDecimal();
        helper.getPrfVLisDichAutorList(idUsoRuoloSet, tiDichAutor, idApplic);
        assertTrue(true);
    }

    @Test
    public void getPrfVLisDichAutorList_3args_2_queryIsOk() {
        BigDecimal idRuolo = aBigDecimal();
        String tiDichAutor = aString();
        BigDecimal idApplic = aBigDecimal();
        helper.getPrfVLisDichAutorList(idRuolo, tiDichAutor, idApplic);
        assertTrue(true);
    }

    @Test
    public void getEntryMenuList2_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        boolean ricercaFoglie = false;
        Set<String> nodiGiaPresenti = aSetOfString(2);
        helper.getEntryMenuList2(idApplic, ricercaFoglie, nodiGiaPresenti);
        assertTrue(true);
    }

    @Test
    public void getEntryMenuList_queryIsOk() {
        helper.getEntryMenuList(BigDecimal.ONE, true);
        assertTrue(true);
        helper.getEntryMenuList(BigDecimal.ONE, false);
        assertTrue(true);
    }

    @Test
    public void getRoleTreeNodesList_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        helper.getRoleTreeNodesList(idApplic, ConstPrfDichAutor.TiDichAutor.AZIONE.name());
        assertTrue(true);
        helper.getRoleTreeNodesList(idApplic, ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name());
        assertTrue(true);
        helper.getRoleTreeNodesList(idApplic, ConstPrfDichAutor.TiDichAutor.PAGINA.name());
        assertTrue(true);
    }

    @Test
    public void getRootEntryMenu_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        helper.getRootEntryMenu(idApplic);
        assertTrue(true);
    }

    @Test
    public void getActionChilds_queryIsOk() {
        BigDecimal idPaginaWeb = aBigDecimal();
        helper.getActionChilds(idPaginaWeb);
        assertTrue(true);
    }

    @Test
    public void getAplAzionePagina_queryIsOk() {
        BigDecimal idAzionePagina = aBigDecimal();
        helper.getAplAzionePagina(idAzionePagina);
        assertTrue(true);
    }

    @Test
    public void getPagesList_queryIsOk() {
        BigDecimal idApplicazione = aBigDecimal();
        Set<String> nodiGiaPresenti = aSetOfString(2);
        helper.getPagesList(idApplicazione, nodiGiaPresenti);
        assertTrue(true);
    }

    @Test
    public void getPagesAzioniList_queryIsOk() {
        BigDecimal idApplicazione = aBigDecimal();
        Set<BigDecimal> azioniGiaPresenti = aSetOfBigDecimal(2);
        helper.getPagesAzioniList(idApplicazione, azioniGiaPresenti);
        assertTrue(true);
    }

    @Test
    public void getActionsList_queryIsOk() {
        BigDecimal idApplicazione = aBigDecimal();
        BigDecimal idPaginaWeb = aBigDecimal();
        helper.getActionsList(idApplicazione, idPaginaWeb);
        assertTrue(true);
    }

    @Test
    public void getActionsPagesList_queryIsOk() {
        BigDecimal idApplicazione = aBigDecimal();
        BigDecimal idPaginaWeb = aBigDecimal();
        Set<BigDecimal> azioniGiaInserite = aSetOfBigDecimal(2);
        helper.getActionsPagesList(idApplicazione, idPaginaWeb, azioniGiaInserite);
        assertTrue(true);
    }

    @Test
    public void getServicesList_queryIsOk() {
        BigDecimal idApplicazione = aBigDecimal();
        Set<String> nodiGiaPresenti = aSetOfString(2);
        helper.getServicesList(idApplicazione, nodiGiaPresenti);
        assertTrue(true);
    }

    @Test
    public void getPrfDichAutor_queryIsOk() {
        BigDecimal idObject = aBigDecimal();
        BigDecimal idObject2 = aBigDecimal();
        BigDecimal idPrfUsoRuoloApplic = aBigDecimal();
        for (ConstPrfDichAutor.TiDichAutor tiDichAutor : ConstPrfDichAutor.TiDichAutor.values()) {
            for (ConstPrfDichAutor.TiScopoDichAutor tiScopoDichAutor : ConstPrfDichAutor.TiScopoDichAutor.values()) {
                helper.getPrfDichAutor(tiScopoDichAutor.name(), tiDichAutor.name(), idObject, idObject2,
                        idPrfUsoRuoloApplic);
                assertTrue(true);
            }
        }
    }

    @Test
    public void getIdUsoRuoloApplic_queryIsOk() {
        BigDecimal idRuolo = aBigDecimal();
        BigDecimal idAppl = aBigDecimal();
        helper.getIdUsoRuoloApplic(idRuolo, idAppl);
        assertTrue(true);
    }

    @Test
    public void getAplApplicById_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        helper.getAplApplicById(idApplic);
        assertTrue(true);
    }

    @Test
    public void getPrfRuolo_queryIsOk() {
        String nmRuolo = aString();
        helper.getPrfRuolo(nmRuolo);
        assertTrue(true);
    }

    @Test
    public void checkIsUniqueRole_queryIsOk() {
        String nmRuoloPreMod = aString();
        String nmRuoloPostMod = aString();
        for (BaseElements.Status status : BaseElements.Status.values()) {
            helper.checkIsUniqueRole(nmRuoloPreMod, nmRuoloPostMod, status);
        }
        assertTrue(true);
    }

    @Test
    public void getUsrUser_queryIsOk() {
        BigDecimal idUser = aBigDecimal();
        helper.getUsrUser(idUser);
        assertTrue(true);
    }

    @Test
    public void getUsrUsoUserApplicList_queryIsOk() {
        BigDecimal idUserIam = aBigDecimal();
        helper.getUsrUsoUserApplicList(idUserIam);
        assertTrue(true);
    }

    @Test
    public void getUsrUsoRuoloUserDefaultList_queryIsOk() {
        BigDecimal idUserIam = aBigDecimal();
        helper.getUsrUsoRuoloUserDefaultList(idUserIam);
        assertTrue(true);
    }

    @Test
    public void getUtentiUsoRuoloUserDefaultList_queryIsOk() {
        BigDecimal idRuolo = aBigDecimal();
        List<String> tiStatoUser = aListOfString(2);
        helper.getUtentiUsoRuoloUserDefaultList(idRuolo, tiStatoUser);
        assertTrue(true);
    }

    @Test
    public void getUtentiUsoRuoloOrganizList_queryIsOk() {
        BigDecimal idRuolo = aBigDecimal();
        List<String> tiStatoUser = aListOfString(2);
        helper.getUtentiUsoRuoloOrganizList(idRuolo, tiStatoUser);
        assertTrue(true);
    }

    @Test
    public void getUsrDichAbilOrganizList_queryIsOk() {
        BigDecimal idUserIam = aBigDecimal();
        helper.getUsrDichAbilOrganizList(idUserIam);
        assertTrue(true);
    }

    @Test
    public void getUsrDichAbilDatiList_queryIsOk() {
        BigDecimal idUserIam = aBigDecimal();
        helper.getUsrDichAbilDatiList(idUserIam);
        assertTrue(true);
    }

    @Test
    public void checkUserExists_queryIsOk() {
        String userName = aString();
        helper.checkUserExists(userName);
        assertTrue(true);
    }

    @Test
    public void countMenusList_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        helper.countMenusList(idApplic);
        assertTrue(true);
    }

    @Test
    public void countPagesList_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        helper.countPagesList(idApplic);
        assertTrue(true);
    }

    @Test
    public void countActionsList_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();
        helper.countActionsList(idApplic);
        assertTrue(true);
    }

    @Test
    public void getPrfUsoRuoloApplic_queryIsOk() {
        long idRuolo = aLong();
        long idAppl = aLong();
        helper.getPrfUsoRuoloApplic(idRuolo, idAppl);
        assertTrue(true);
    }

    @Test
    public void retrievePrfVLisUserDaReplic_queryIsOk() {
        Long idRuolo = aLong();
        helper.retrievePrfVLisUserDaReplic(idRuolo);
        assertTrue(true);
    }

    @Test
    public void retrievePrfVLisDichAllineaToadd_queryIsOk() {
        Long idRuolo = aLong();
        helper.retrievePrfVLisDichAllineaToadd(idRuolo);
        assertTrue(true);
    }

    @Test
    public void retrievePrfVLisDichAutorDaAllin_queryIsOk() {
        BigDecimal idRuolo = aBigDecimal();
        helper.retrievePrfVLisDichAutorDaAllin(idRuolo);
        assertTrue(true);
    }

    @Test
    public void retrievePrfRuoloCategoria_queryIsOk() {
        BigDecimal idRuolo = aBigDecimal();
        helper.retrievePrfRuoloCategoria(idRuolo);
        assertTrue(true);
    }

    @Test
    public void getPrfVChkAllineaRuolo_queryIsOk() {
        Long idRuolo = aLong();
        String nmApplic = aString();
        try {
            helper.getPrfVChkAllineaRuolo(idRuolo, nmApplic);
            assertTrue(true);
        } catch (Exception e) {
            assertNoResultException(e);
        }
    }

    @Test
    public void countPrfAllineaRuolo_queryIsOk() {
        Long idRuolo = aLong();
        helper.countPrfAllineaRuolo(idRuolo);
        assertTrue(true);
    }

    @Test
    public void getPrfRuoloCategoriaList_BigDecimal_queryIsOk() {
        BigDecimal idRuolo = aBigDecimal();
        helper.getPrfRuoloCategoriaList(idRuolo);
        assertTrue(true);
    }

    @Test
    public void getTiCategRuoloList_queryIsOk() {
        BigDecimal idRuolo = aBigDecimal();
        helper.getTiCategRuoloList(idRuolo);
        assertTrue(true);
    }

    @Test
    public void getAbilitazioniEntryMenu4Applic_queryIsOk() {
        BigDecimal idUsoRuoloApplic = BigDecimal.valueOf(-1);
        String nmApplic = "TEST";
        BigDecimal idRuolo = BigDecimal.valueOf(-1);
        helper.getAbilitazioniEntryMenu4Applic(idUsoRuoloApplic, nmApplic, idRuolo);
        assertTrue(true);
    }

    @Test
    public void getEntryMenuChilds_queryIsOk() {
        helper.getEntryMenuChilds(BigDecimal.ONE);
        assertTrue(true);
    }

    @Test
    public void getEntryMenuParentsList_queryIsOk() {
        helper.getEntryMenuParentsList(BigDecimal.ONE);
        assertTrue(true);
    }

}
