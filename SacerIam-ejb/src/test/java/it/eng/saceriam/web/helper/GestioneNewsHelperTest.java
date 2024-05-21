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

import static it.eng.ArquillianUtils.aBigDecimal;
import static it.eng.ArquillianUtils.aString;
import static it.eng.ArquillianUtils.getSacerIamArchive;
import static it.eng.ArquillianUtils.todayTs;
import static it.eng.ArquillianUtils.tomorrowTs;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.RandomStringUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplNews;
import it.eng.saceriam.entity.AplNewsApplic;

/**
 * @author Manuel Bertuzzi <manuel.bertuzzi@eng.it>
 */
@RunWith(Arquillian.class)
public class GestioneNewsHelperTest {
    @EJB
    private GestioneNewsHelper helper;

    @Deployment
    public static Archive<?> createTestArchive() {
        return getSacerIamArchive(GestioneNewsHelper.class).addClass(RandomStringUtils.class).addPackages(true,
                "it.eng.parer.sacerlog.entity", "it.eng.parer.sacerlog.viewEntity");
    }

    @Test
    public void getAplNewsList_queryIsOk() {
        String dsOggetto = aString();
        Date dataInizio = todayTs();
        Date dataFine = tomorrowTs();
        helper.getAplNewsList(dsOggetto, dataInizio, dataFine);
        assertTrue(true);
    }

    @Test
    public void getAplNewsById_queryIsOk() {
        BigDecimal idNews = aBigDecimal();

        helper.getAplNewsById(idNews);
        assertTrue(true);
    }

    @Test
    public void getAplApplicById_queryIsOk() {
        BigDecimal idApplic = aBigDecimal();

        helper.getAplApplicById(idApplic);
        assertTrue(true);
    }

    @Test
    public void getAplApplicList_0args_queryIsOk() {
        helper.getAplApplicList();
        assertTrue(true);
    }

    @Test
    public void getAplApplicList_BigDecimal_queryIsOk() {
        BigDecimal idNews = aBigDecimal();
        helper.getAplApplicList(idNews);
        assertTrue(true);
    }

    @Test
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Ignore("TEST MANUALE")
    public void creaAggiornaNewsConApplicazioni_verificaCascade() {
        final String oggetto = "News " + RandomStringUtils.random(12, true, true);
        Date dataInizioPub = new Date();
        Date dataFinePub = new Date();
        AplNews news = new AplNews();
        news.setDlTesto("Inserita da test automatici");
        news.setDtIniPubblic(dataInizioPub);
        news.setDtFinPubblic(dataFinePub);
        news.setDsOggetto(oggetto);
        news.setFlPubblicLogin("1");
        news.setFlPubblicHomepage("0");
        news.setAplNewsApplics(new ArrayList<>());
        AplApplic sacerIam = helper.getAplApplicById(BigDecimal.valueOf(321));

        AplNewsApplic applic = new AplNewsApplic();
        applic.setAplNew(news);
        applic.setAplApplic(sacerIam);

        news.getAplNewsApplics().add(applic);
        // PERSIST
        helper.insert(news);
        List<AplApplic> listaApplicazioni = helper.getAplApplicList(BigDecimal.valueOf(news.getIdNews()));
        Assert.assertEquals(1, listaApplicazioni.size());
        Assert.assertEquals(sacerIam.getIdApplic(), listaApplicazioni.get(0).getIdApplic());
        // MERGE
        helper.deleteAplNewsApplicList(news.getIdNews());
        news.setAplNewsApplics(new ArrayList<>());
        AplApplic sacer = helper.getAplApplicById(BigDecimal.valueOf(322));
        AplNewsApplic applic2 = new AplNewsApplic();
        applic2.setAplNew(news);
        applic2.setAplApplic(sacer);
        news.getAplNewsApplics().add(applic2);
        helper.update(news);
        listaApplicazioni = helper.getAplApplicList(BigDecimal.valueOf(news.getIdNews()));
        Assert.assertEquals(1, listaApplicazioni.size());
        Assert.assertEquals(sacer.getIdApplic(), listaApplicazioni.get(0).getIdApplic());
        helper.update(news);

    }
}
