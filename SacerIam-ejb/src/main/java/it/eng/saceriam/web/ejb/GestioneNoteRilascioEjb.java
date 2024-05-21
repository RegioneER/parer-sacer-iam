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

package it.eng.saceriam.web.ejb;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplNotaRilascio;
import it.eng.saceriam.exception.IncoherenceException;
import it.eng.saceriam.slite.gen.form.GestioneNoteRilascioForm;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplNotaRilascioRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplNotaRilascioTableBean;
import it.eng.saceriam.web.helper.GestioneNoteRilascioHelper;
import it.eng.saceriam.web.util.Transform;
import it.eng.spagoCore.error.EMFError;

/**
 * Ejb Gestione Note di rilascio di SacerIam
 *
 * @author DiLorenzo_F
 */
@Stateless
@LocalBean
public class GestioneNoteRilascioEjb {

    public GestioneNoteRilascioEjb() {
    }

    @EJB
    private GestioneNoteRilascioHelper gestioneNoteRilascioHelper;
    private static final Logger log = LoggerFactory.getLogger(GestioneNoteRilascioEjb.class);

    public AplNotaRilascioTableBean getAplNoteRilascioTableBean(
            GestioneNoteRilascioForm.FiltriNoteRilascio filtriNoteRilascio) throws EMFError {
        AplNotaRilascioTableBean noteRilascioTableBean = new AplNotaRilascioTableBean();
        List<AplNotaRilascio> list = gestioneNoteRilascioHelper.getAplNoteRilascioList(filtriNoteRilascio);
        try {
            if (!list.isEmpty()) {
                for (AplNotaRilascio notaRilascio : list) {
                    AplNotaRilascioRowBean row = new AplNotaRilascioRowBean();
                    row = (AplNotaRilascioRowBean) Transform.entity2RowBean(notaRilascio);
                    row.setString("nm_applic", notaRilascio.getAplApplic().getNmApplic());
                    noteRilascioTableBean.add(row);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return noteRilascioTableBean;
    }

    public long insertAplNoteRilascio(AplNotaRilascioRowBean noteRilascioRowBean)
            throws IncoherenceException, EMFError {
        AplNotaRilascio notaRilascio = new AplNotaRilascio();
        AplApplic applic = gestioneNoteRilascioHelper.getAplApplic(noteRilascioRowBean.getIdApplic().longValue());
        try {
            notaRilascio = (AplNotaRilascio) Transform.rowBean2Entity(noteRilascioRowBean);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        gestioneNoteRilascioHelper.insert(notaRilascio);

        if (applic.getAplNotaRilascios() == null) {
            applic.setAplNotaRilascios(new ArrayList<AplNotaRilascio>());
        }
        applic.getAplNotaRilascios().add(notaRilascio);

        return notaRilascio.getIdNotaRilascio();
    }

    public void updateAplNoteRilascio(BigDecimal idNotaRilascio, AplNotaRilascioRowBean noteRilascioRowBean)
            throws IncoherenceException, EMFError {
        AplNotaRilascio notaRilascio = new AplNotaRilascio();
        try {
            notaRilascio = (AplNotaRilascio) Transform.rowBean2Entity(noteRilascioRowBean);
            notaRilascio.setIdNotaRilascio(idNotaRilascio.longValue());
        } catch (Exception e) {
            throw new EMFError(EMFError.ERROR, e);
        }

        gestioneNoteRilascioHelper.update(notaRilascio);
    }

    public void deleteNoteRilascio(AplNotaRilascioRowBean noteRilascioRowBean) throws IncoherenceException {
        AplNotaRilascio notaRilascio = gestioneNoteRilascioHelper
                .getAplNotaRilascioById(noteRilascioRowBean.getIdNotaRilascio());
        if (notaRilascio != null) {
            gestioneNoteRilascioHelper.remove(notaRilascio);
        }
    }

    public AplApplicRowBean getAplApplicRowBean(BigDecimal idApplic) {
        AplApplicRowBean applicRowBean = new AplApplicRowBean();
        AplApplic applic = gestioneNoteRilascioHelper.getAplApplicById(idApplic);
        try {
            if (applic != null) {
                applicRowBean = (AplApplicRowBean) Transform.entity2RowBean(applic);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicRowBean;
    }

    public AplNotaRilascioTableBean getAplNoteRilascioPrecTableBean(BigDecimal idApplic, BigDecimal idNotaRilascio,
            Date dtVersione) throws EMFError {
        AplNotaRilascioTableBean noteRilascioPrecTableBean = new AplNotaRilascioTableBean();
        List<AplNotaRilascio> noteRilascioPrecList = gestioneNoteRilascioHelper.getAplNoteRilascioPrecList(idApplic,
                idNotaRilascio, dtVersione);
        try {
            if (noteRilascioPrecList != null && !noteRilascioPrecList.isEmpty()) {
                noteRilascioPrecTableBean = (AplNotaRilascioTableBean) Transform
                        .entities2TableBean(noteRilascioPrecList);
                // for (AplNotaRilascio notaRilascio : noteRilascioPrecList) {
                // AplNotaRilascioRowBean row = new AplNotaRilascioRowBean();
                // row = (AplNotaRilascioRowBean) Transform.entity2RowBean(notaRilascio);
                // row.setString("nm_applic", notaRilascio.getAplApplic().getNmApplic());
                // noteRilascioPrecTableBean.add(row);
                // }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return noteRilascioPrecTableBean;
    }

    public AplNotaRilascioRowBean getAplNotaRilascioRowBean(BigDecimal idNotaRilascio) throws EMFError {
        AplNotaRilascioRowBean notaRilascioRowBean = new AplNotaRilascioRowBean();
        if (idNotaRilascio != null) {
            AplNotaRilascio notaRilascio = gestioneNoteRilascioHelper.getAplNotaRilascioById(idNotaRilascio);
            if (notaRilascio != null) {
                try {
                    notaRilascioRowBean = (AplNotaRilascioRowBean) Transform.entity2RowBean(notaRilascio);
                    notaRilascioRowBean.setString("nm_applic", notaRilascio.getAplApplic().getNmApplic());
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                        | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    log.error("Errore durante il recupero della nota rilascio " + ExceptionUtils.getRootCauseMessage(e),
                            e);
                }
            }
        }
        return notaRilascioRowBean;
    }
}
