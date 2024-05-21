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
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.saceriam.entity.AplSistemaVersArkRif;
import it.eng.saceriam.entity.AplSistemaVersante;
import it.eng.saceriam.entity.AplSistemaVersanteUserRef;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.exception.IncoherenceException;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.slite.gen.form.AmministrazioneSistemiVersantiForm;
import it.eng.saceriam.slite.gen.form.AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti;
import it.eng.saceriam.slite.gen.tablebean.AplSistemaVersArkRifRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplSistemaVersArkRifTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplSistemaVersanteRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplSistemaVersanteTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplSistemaVersanteUserRefTableBean;
import it.eng.saceriam.slite.gen.viewbean.AplVLisOrganizUsoSisVersTableBean;
import it.eng.saceriam.slite.gen.viewbean.AplVRicSistemaVersanteRowBean;
import it.eng.saceriam.slite.gen.viewbean.AplVRicSistemaVersanteTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVTreeOrganizIamTableBean;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.viewEntity.AplVLisOrganizUsoSisVers;
import it.eng.saceriam.viewEntity.AplVRicSistemaVersante;
import it.eng.saceriam.viewEntity.UsrVTreeOrganizIam;
import it.eng.saceriam.web.helper.SistemiVersantiHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.Transform;
import it.eng.spagoCore.error.EMFError;

/**
 *
 * @author Gilioli_P
 */
@Stateless
@LocalBean
public class SistemiVersantiEjb {

    @EJB
    private SistemiVersantiHelper sistemiVersantiHelper;

    private static final Logger log = LoggerFactory.getLogger(SistemiVersantiEjb.class);

    /**
     * Metodo che ritorna la lista dei sistemi versanti in base ai filtri passati in input attraverso la chiamata al
     * relativo metodo dell'ejb helper.
     *
     * @param filtri
     *            bean {@link FiltriSistemiVersanti}
     * @param idUserIam
     *            id user IAM
     *
     * @return il table bean dei sistemi versanti
     *
     * @throws EMFError
     *             errore generico
     */
    public AplVRicSistemaVersanteTableBean getAplVRicSistemaVersanteTableBean(
            AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti filtri, long idUserIam) throws EMFError {
        AplVRicSistemaVersanteTableBean sistemiVersantiTB = new AplVRicSistemaVersanteTableBean();
        List<AplVRicSistemaVersante> listaSistemiVersanti = sistemiVersantiHelper.getAplVRicSistemaVersanteList(filtri,
                idUserIam);
        try {
            if (listaSistemiVersanti != null && !listaSistemiVersanti.isEmpty()) {
                for (AplVRicSistemaVersante ricSistemaVersante : listaSistemiVersanti) {
                    AplVRicSistemaVersanteRowBean sistemiVersantiRB = new AplVRicSistemaVersanteRowBean();
                    sistemiVersantiRB = (AplVRicSistemaVersanteRowBean) Transform.entity2RowBean(ricSistemaVersante);
                    /* MEV 25460 - ARCHIVISTI DI RIFERIMENTO */
                    List<AplSistemaVersArkRif> archivisti = sistemiVersantiHelper.getAplSistemaVersArkRifList(
                            ricSistemaVersante.getAplVRicSistemaVersanteId().getIdSistemaVersante());
                    if (archivisti != null && !archivisti.isEmpty()) {
                        String archivistiStr = "";
                        for (AplSistemaVersArkRif archivista : archivisti) {
                            archivistiStr = archivistiStr + archivista.getUsrUser().getNmUserid() + ";";
                        }
                        sistemiVersantiRB.setString("archivista", archivistiStr);
                    }
                    sistemiVersantiTB.add(sistemiVersantiRB);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return sistemiVersantiTB;
    }

    /**
     * Ritorna il rowBean del sistema versante dato il suo id
     *
     * @param idSistemaVersante
     *            id sistema
     *
     * @return il rowBean contenente i dati del sistema versante
     */
    public AplSistemaVersanteRowBean getAplSistemaVersanteRowBean(BigDecimal idSistemaVersante) {
        AplSistemaVersanteRowBean sistemaVersanteRB = new AplSistemaVersanteRowBean();
        AplSistemaVersante sistemaVersante = sistemiVersantiHelper.findById(AplSistemaVersante.class,
                idSistemaVersante);
        try {
            if (sistemaVersante != null) {
                sistemaVersanteRB = (AplSistemaVersanteRowBean) Transform.entity2RowBean(sistemaVersante);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sistemaVersanteRB;
    }

    /**
     * Ritorna il rowBean del sistema versante dato il suo id
     *
     * @param idSistemaVersante
     *            id sistema
     * @param idUserIam
     *            id user IAM
     *
     * @return il rowBean contenente i dati del sistema versante
     */
    public AplVRicSistemaVersanteRowBean getAplVRicSistemaVersanteRowBean(BigDecimal idSistemaVersante,
            long idUserIam) {
        AplVRicSistemaVersanteRowBean sistemaVersanteRB = new AplVRicSistemaVersanteRowBean();
        AplVRicSistemaVersante sistemaVersante = sistemiVersantiHelper.getAplVRicSistemaVersante(idSistemaVersante,
                idUserIam);
        Date dataOdierna = DateUtil.getDataOdiernaNoTime();
        try {
            if (sistemaVersante != null) {
                sistemaVersanteRB = (AplVRicSistemaVersanteRowBean) Transform.entity2RowBean(sistemaVersante);
                if (sistemaVersante.getDtIniVal().compareTo(dataOdierna) <= 0
                        && sistemaVersante.getDtFineVal().compareTo(dataOdierna) >= 0) {
                    sistemaVersanteRB.setFlCessato("0");
                } else {
                    sistemaVersanteRB.setFlCessato("1");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sistemaVersanteRB;
    }

    /**
     * Ritorna il tableBean con tutti i sistemi versanti dipendendi: - dal tipo user - dall'ente di appartenenza utente
     * - validi alla data odierna
     *
     * @param tipoUser
     *            tipo user
     * @param idEnteUser
     *            id ente
     *
     * @return table bean {@link AplSistemaVersanteTableBean}
     */
    public AplSistemaVersanteTableBean getAplSistemaVersanteValidiTableBean(String tipoUser, BigDecimal idEnteUser) {
        AplSistemaVersanteTableBean sistemaVersanteTB = new AplSistemaVersanteTableBean();
        List<AplSistemaVersante> sistemaVersanteList = sistemiVersantiHelper.getAplSistemaVersanteValidiList(tipoUser,
                idEnteUser);
        try {
            if (!sistemaVersanteList.isEmpty()) {
                sistemaVersanteTB = (AplSistemaVersanteTableBean) Transform.entities2TableBean(sistemaVersanteList);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sistemaVersanteTB;
    }

    /**
     * Ritorna il tableBean con tutti i sistemi versanti
     *
     * @return table bean {@link AplSistemaVersanteTableBean}
     */
    public AplSistemaVersanteTableBean getAplSistemaVersanteTableBean() {
        AplSistemaVersanteTableBean sistemaVersanteTB = new AplSistemaVersanteTableBean();
        List<AplSistemaVersante> sistemaVersanteList = sistemiVersantiHelper.getAplSistemaVersanteList();
        try {
            if (!sistemaVersanteList.isEmpty()) {
                sistemaVersanteTB = (AplSistemaVersanteTableBean) Transform.entities2TableBean(sistemaVersanteList);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sistemaVersanteTB;
    }

    /**
     * Ritorna il rowBean del sistema versante presente in AplVRicSistemaVersante passati in ingresso i campi chiave
     *
     * @param idSistemaVersante
     *            id sistema versante
     * @param idUserIam
     *            id user IAM
     * @param idOrganizIam
     *            id organizzazione
     *
     * @return row bean {@link AplVRicSistemaVersanteRowBean}
     */
    public AplVRicSistemaVersanteRowBean getAplVRicSistemaVersanteRowBean(BigDecimal idSistemaVersante,
            BigDecimal idUserIam, BigDecimal idOrganizIam) {
        AplVRicSistemaVersanteRowBean sistemaVersanteRB = new AplVRicSistemaVersanteRowBean();
        AplVRicSistemaVersante sistemaVersante = sistemiVersantiHelper.getAplVRicSistemaVersante(idSistemaVersante,
                idUserIam, idOrganizIam);
        try {
            if (sistemaVersante != null) {
                sistemaVersanteRB = (AplVRicSistemaVersanteRowBean) Transform.entity2RowBean(sistemaVersante);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sistemaVersanteRB;
    }

    public long insertSistemaVersante(String denominazione, String descrizione, String versione, BigDecimal idEnteSiam,
            String dsEmail, String flPec, String flIntegrazione, String flAssociaPersonaFisica, Date dtIniVal,
            Date dtFineVal, String dsNote) throws IncoherenceException {
        // Verifico se il sistema versante con il nome passato come parametro è già presente su DB
        AplSistemaVersante sistemaVersante = sistemiVersantiHelper.getAplSistemaVersanteByName(denominazione);
        if (sistemaVersante != null) {
            throw new IncoherenceException("Nome sistema versante non univoco</br>");
        }
        return sistemiVersantiHelper.insertAplSistemaVersante(denominazione, descrizione, versione, idEnteSiam, dsEmail,
                flPec, flIntegrazione, flAssociaPersonaFisica, dtIniVal, dtFineVal, dsNote);
    }

    public void updateSistemaVersante(BigDecimal idSistemaVersante, String denominazione, String descrizione,
            String versione, BigDecimal idEnteSiam, String dsEmail, String flPec, String flIntegrazione,
            String flAssociaPersonaFisica, long idUserIam, Date dtIniVal, Date dtFineVal, String dsNote)
            throws IncoherenceException {
        // Verifico se il sistema versante con il nome passato come parametro è già presente su DB
        AplSistemaVersante sistemaVersanteByName = sistemiVersantiHelper.getAplSistemaVersanteByName(denominazione);
        AplSistemaVersante sistemaVersante = sistemiVersantiHelper.findById(AplSistemaVersante.class,
                idSistemaVersante);
        // Se ho cambiato nome e questi è già presente su DB
        if (!sistemaVersante.getNmSistemaVersante().equals(denominazione) && sistemaVersanteByName != null) {
            throw new IncoherenceException("Nome sistema versante non univoco</br>");
        }
        // Controlli di coerenza con utenti che utilizzano il sistema versante associato a persona fisica
        if (sistemaVersante.getFlAssociaPersonaFisica().equals("1") && flAssociaPersonaFisica.equals("0")
                && sistemiVersantiHelper.existsUtentiAssociatiSistemaVersante(idSistemaVersante,
                        ApplEnum.TipoUser.PERSONA_FISICA.name())) {
            throw new IncoherenceException(
                    "Il sistema versante è associato ad almeno un utente di tipo persona fisica. Il flag associabile a persona fisica non può essere modificato</br>");
        }

        if (sistemaVersante.getFlAssociaPersonaFisica().equals("0") && flAssociaPersonaFisica.equals("1")
                && sistemiVersantiHelper.existsUtentiAssociatiSistemaVersante(idSistemaVersante,
                        ApplEnum.TipoUser.AUTOMA.name())) {
            throw new IncoherenceException(
                    "Il sistema versante è associato ad almeno un utente di tipo automa. Il flag associabile a persona fisica non può essere modificato</br>");
        }

        sistemaVersante.setNmSistemaVersante(denominazione);
        sistemaVersante.setDsSistemaVersante(descrizione);
        sistemaVersante.setCdVersione(versione);
        sistemaVersante.setOrgEnteSiam(sistemiVersantiHelper.findById(OrgEnteSiam.class, idEnteSiam));
        sistemaVersante.setDtIniVal(dtIniVal);
        sistemaVersante.setDtFineVal(dtFineVal);
        sistemaVersante.setDsEmail(dsEmail);
        sistemaVersante.setDsNote(dsNote);
        sistemaVersante.setFlPec(flPec);
        sistemaVersante.setFlIntegrazione(flIntegrazione);
        sistemaVersante.setFlAssociaPersonaFisica(flAssociaPersonaFisica);
    }

    /**
     * Controlla se un sistema versante è eliminabile ed in caso lo elimina
     *
     * @param idSistemaVersante
     *            id sistema
     *
     * @return 0 se impossibile eliminare, 1 se ha eliminato
     */
    public int deleteSistemaVersante(BigDecimal idSistemaVersante) {
        int eliminati = 0;
        // Controllo se il sistema versante è eliminabile
        if (sistemiVersantiHelper.canDeleteSistemaVersante(idSistemaVersante.longValue())) {
            sistemiVersantiHelper.deleteAplSistemaVersante(idSistemaVersante.longValue());
            eliminati++;
        }
        return eliminati;
    }

    public AplVLisOrganizUsoSisVersTableBean getAplVLisOrganizUsoSisVersTableBean(BigDecimal idSistemaVersante)
            throws ParerUserError {
        List<AplVLisOrganizUsoSisVers> dlCompositoOrganizList = sistemiVersantiHelper
                .getAplVLisOrganizUsoSisVers(idSistemaVersante);
        AplVLisOrganizUsoSisVersTableBean tabellaOrganiz = new AplVLisOrganizUsoSisVersTableBean();
        try {
            if (!dlCompositoOrganizList.isEmpty()) {
                tabellaOrganiz = (AplVLisOrganizUsoSisVersTableBean) Transform
                        .entities2TableBean(dlCompositoOrganizList);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            throw new ParerUserError("Errore inatteso nel caricamento delle strutture versanti");
        }
        return tabellaOrganiz;
    }

    public UsrVTreeOrganizIamTableBean getOrganizUltimoLivelloSacer() {
        List<UsrVTreeOrganizIam> dlCompositoOrganizList = sistemiVersantiHelper.getOrganizUltimoLivelloSacer();
        UsrVTreeOrganizIamTableBean tabellaOrganiz = new UsrVTreeOrganizIamTableBean();
        try {
            if (!dlCompositoOrganizList.isEmpty()) {
                tabellaOrganiz = (UsrVTreeOrganizIamTableBean) Transform.entities2TableBean(dlCompositoOrganizList);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return tabellaOrganiz;
    }

    /**
     * Ritorna il tableBean contenente, per il sistema versante dato in input, la lista di utenti archivisti
     *
     * @param idSistemaVersante
     *            id sistema versante
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public AplSistemaVersArkRifTableBean getAplSistemaVerArkRifTableBean(BigDecimal idSistemaVersante)
            throws ParerUserError {
        AplSistemaVersArkRifTableBean table = new AplSistemaVersArkRifTableBean();
        List<AplSistemaVersArkRif> list = sistemiVersantiHelper.getAplSistemaVersArkRifList(idSistemaVersante);
        if (list != null && !list.isEmpty()) {
            try {
                for (AplSistemaVersArkRif sistemaVersArkRif : list) {
                    AplSistemaVersArkRifRowBean row = new AplSistemaVersArkRifRowBean();
                    row.setString("nm_ente_siam", sistemaVersArkRif.getUsrUser().getOrgEnteSiam().getNmEnteSiam());
                    row.setString("nm_cognome_user", sistemaVersArkRif.getUsrUser().getNmCognomeUser());
                    row.setString("nm_nome_user", sistemaVersArkRif.getUsrUser().getNmNomeUser());
                    row.setString("nm_userid", sistemaVersArkRif.getUsrUser().getNmUserid());
                    row.setBigDecimal("id_sistema_vers_ark_rif",
                            new BigDecimal(sistemaVersArkRif.getIdSistemaVersArkRif()));
                    table.add(row);
                }
            } catch (IllegalArgumentException ex) {
                String msg = "Errore durante il recupero della lista di utenti archivisti del sistema versante "
                        + ExceptionUtils.getRootCauseMessage(ex);
                log.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il tableBean contenente, per il sistema versante dato in input, la lista dei referenti della ditta
     * produttrice
     *
     * @param idSistemaVersante
     *            id sistema versante
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public AplSistemaVersanteUserRefTableBean getAplSistemaVeranteUserRefTableBean(BigDecimal idSistemaVersante)
            throws ParerUserError {
        AplSistemaVersanteUserRefTableBean table = new AplSistemaVersanteUserRefTableBean();
        List<AplSistemaVersanteUserRef> list = sistemiVersantiHelper
                .getAplSistemaVersanteUserRefList(idSistemaVersante);
        if (list != null && !list.isEmpty()) {
            try {
                for (AplSistemaVersanteUserRef sistemaVersanteUserRef : list) {
                    AplSistemaVersArkRifRowBean row = new AplSistemaVersArkRifRowBean();
                    row.setString("nm_cognome_user", sistemaVersanteUserRef.getUsrUser().getNmCognomeUser());
                    row.setString("nm_nome_user", sistemaVersanteUserRef.getUsrUser().getNmNomeUser());
                    row.setString("nm_userid", sistemaVersanteUserRef.getUsrUser().getNmUserid());
                    row.setString("ds_email", sistemaVersanteUserRef.getUsrUser().getDsEmail());
                    row.setBigDecimal("id_sistema_versante_user_ref",
                            new BigDecimal(sistemaVersanteUserRef.getIdSistemaVersanteUserRef()));
                    table.add(row);
                }
            } catch (IllegalArgumentException ex) {
                String msg = "Errore durante il recupero della lista dei referenti ditta produttrice del sistema versante "
                        + ExceptionUtils.getRootCauseMessage(ex);
                log.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Metodo di eliminazione di un utente archivista dal sistema versante
     *
     * @param idSistemaVersArkRif
     *            id sistema versante archiviazione
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteAplSistemaVersArkRif(BigDecimal idSistemaVersArkRif) throws ParerUserError {
        log.debug("Eseguo l'eliminazione dell'utente archivista dal sistema versante");
        try {
            AplSistemaVersArkRif sistemaVersArkRif = sistemiVersantiHelper.findById(AplSistemaVersArkRif.class,
                    idSistemaVersArkRif);
            sistemiVersantiHelper.removeEntity(sistemaVersArkRif, true);
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'utente archivista dal sistema versante ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            log.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di eliminazione di un referente ditta produttrice del sistema versante
     *
     * @param idSistemaVersanteUserRef
     *            id sistema versante
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteAplSistemaVersanteUserRef(BigDecimal idSistemaVersanteUserRef) throws ParerUserError {
        log.debug("Eseguo l'eliminazione del referente ditta produttrice del sistema versante");
        try {
            AplSistemaVersanteUserRef sistemaVersanteUserRef = sistemiVersantiHelper
                    .findById(AplSistemaVersanteUserRef.class, idSistemaVersanteUserRef);
            sistemiVersantiHelper.removeEntity(sistemaVersanteUserRef, true);
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del referente ditta produttrice del sistema versante ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            log.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    public boolean isUtenteArchivistaInSistemaVersante(BigDecimal idSistemaVersante, BigDecimal idUserIam) {
        return sistemiVersantiHelper.isUtenteArchivistaInSistemaVersante(idSistemaVersante, idUserIam);
    }

    public boolean isReferenteDittaProduttriceInSistemaVersante(BigDecimal idSistemaVersante, BigDecimal idUserIam) {
        return sistemiVersantiHelper.isReferenteDittaProduttriceInSistemaVersante(idSistemaVersante, idUserIam);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addUtenteArchivistaToSistemaVersante(BigDecimal idSistemaVersante, BigDecimal idUserIam)
            throws ParerUserError {
        AplSistemaVersante sistemaVersante = sistemiVersantiHelper.findByIdWithLock(AplSistemaVersante.class,
                idSistemaVersante);
        UsrUser user = sistemiVersantiHelper.findByIdWithLock(UsrUser.class, idUserIam);
        AplSistemaVersArkRif sistemaVersArkRif = new AplSistemaVersArkRif();
        sistemaVersArkRif.setAplSistemaVersante(sistemaVersante);
        sistemaVersArkRif.setUsrUser(user);
        sistemiVersantiHelper.insertEntity(sistemaVersArkRif, true);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addReferenteDittaProduttriceToSistemaVersante(BigDecimal idSistemaVersante, BigDecimal idUserIam)
            throws ParerUserError {
        AplSistemaVersante sistemaVersante = sistemiVersantiHelper.findByIdWithLock(AplSistemaVersante.class,
                idSistemaVersante);
        UsrUser user = sistemiVersantiHelper.findByIdWithLock(UsrUser.class, idUserIam);
        AplSistemaVersanteUserRef sistemaVersUserRef = new AplSistemaVersanteUserRef();
        sistemaVersUserRef.setAplSistemaVersante(sistemaVersante);
        sistemaVersUserRef.setUsrUser(user);
        sistemiVersantiHelper.insertEntity(sistemaVersUserRef, true);
    }

}
