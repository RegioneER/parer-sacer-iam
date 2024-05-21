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

package it.eng.saceriam.informazioni.noteRilascio.ejb;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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
import it.eng.saceriam.informazioni.noteRilascio.helper.NoteRilascioHelper;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplNotaRilascioRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplNotaRilascioTableBean;
import it.eng.saceriam.web.util.Transform;
import it.eng.spagoCore.error.EMFError;

/**
 * Ejb note di rilascio di SacerIam
 *
 * @author DiLorenzo_F
 */
@Stateless
@LocalBean
public class NoteRilascioEjb {

    public NoteRilascioEjb() {
    }

    @EJB
    private NoteRilascioHelper noteRilascioHelper;

    private static final Logger log = LoggerFactory.getLogger(NoteRilascioEjb.class);

    public AplNotaRilascioTableBean getAplNoteRilascioTableBean(String nmApplic) throws EMFError {
        AplNotaRilascioTableBean noteRilascioTableBean = new AplNotaRilascioTableBean();
        long idApplic = noteRilascioHelper.getAplApplic(nmApplic).getIdApplic();
        List<AplNotaRilascio> list = noteRilascioHelper.getAplNoteRilascioList(BigDecimal.valueOf(idApplic));
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

    public AplApplicRowBean getAplApplicRowBean(BigDecimal idApplic) {
        AplApplicRowBean applicRowBean = new AplApplicRowBean();
        AplApplic applic = noteRilascioHelper.getAplApplicById(idApplic);
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
        List<AplNotaRilascio> noteRilascioPrecList = noteRilascioHelper.getAplNoteRilascioPrecList(idApplic,
                idNotaRilascio, dtVersione);
        try {
            if (noteRilascioPrecList != null && !noteRilascioPrecList.isEmpty()) {
                noteRilascioPrecTableBean = (AplNotaRilascioTableBean) Transform
                        .entities2TableBean(noteRilascioPrecList);
                // for (SIAplNotaRilascio notaRilascio : noteRilascioPrecList) {
                // SIAplNotaRilascioRowBean row = new SIAplNotaRilascioRowBean();
                // row = (SIAplNotaRilascioRowBean) Transform.entity2RowBean(notaRilascio);
                // row.setString("nm_applic", notaRilascio.getSiAplApplic().getNmApplic());
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
            AplNotaRilascio notaRilascio = noteRilascioHelper.getAplNotaRilascioById(idNotaRilascio);
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
