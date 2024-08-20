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

package it.eng.saceriam.ws.allineaRuolo.ejb;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.ejb.util.PremisEnums;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplAzionePagina;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.AplPaginaWeb;
import it.eng.saceriam.entity.AplServizioWeb;
import it.eng.saceriam.entity.PrfAllineaRuolo;
import it.eng.saceriam.entity.PrfDichAutor;
import it.eng.saceriam.entity.PrfRuolo;
import it.eng.saceriam.entity.PrfRuoloCategoria;
import it.eng.saceriam.entity.PrfUsoRuoloApplic;
import it.eng.saceriam.entity.constraint.ConstPrfRuolo;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.viewEntity.PrfVChkAllineaRuolo;
import it.eng.saceriam.viewEntity.PrfVLisDichAllineaToadd;
import it.eng.saceriam.viewEntity.PrfVLisUserDaReplic;
import it.eng.saceriam.web.ejb.AuthEjb;
import it.eng.saceriam.web.helper.AmministrazioneRuoliHelper;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.ws.allineaRuolo.dto.AllineaRuoloInput;
import it.eng.saceriam.ws.allineaRuolo.dto.AllineaRuoloRisposta;
import it.eng.saceriam.ws.allineaRuolo.dto.Applic;
import it.eng.saceriam.ws.allineaRuolo.dto.DichAutor;
import it.eng.saceriam.ws.allineaRuolo.dto.ListaApplic;
import it.eng.saceriam.ws.allineaRuolo.dto.ListaCategRuolo;
import it.eng.saceriam.ws.dto.RispostaControlli;
import it.eng.saceriam.ws.ejb.ControlliWS;
import it.eng.saceriam.ws.utils.MessaggiWSBundle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bonora_L
 */
@Stateless
@LocalBean
@Interceptors({ it.eng.saceriam.aop.TransactionInterceptor.class })
public class AllineaRuoloEjb {

    private static final Logger logger = LoggerFactory.getLogger(AllineaRuoloEjb.class);

    @Resource
    private SessionContext context;
    @EJB
    AmministrazioneRuoliHelper ammRuoliHelper;
    @EJB
    ControlliWS controlliWs;
    @EJB
    AuthEjb authEjb;
    @EJB
    UserHelper userHelper;
    @EJB(mappedName = "java:app/sacerlog-ejb/SacerLogEjb")
    private SacerLogEjb sacerLogEjb;

    public AllineaRuoloRisposta allineaRuolo(LogParam param, AllineaRuoloInput input) {
        AllineaRuoloRisposta resp = new AllineaRuoloRisposta(input);
        try {
            logger.debug("Aggiornamento del ruolo " + input.getNmRuolo());
            Long idRuolo = context.getBusinessObject(AllineaRuoloEjb.class).updateRuolo(param, input.getNmRuolo(),
                    input.getDsRuolo(), input.getTiRuolo(), input.getListaCategRuolo());
            logger.debug("Inizio allineamento delle applicazioni");
            String cdErr = context.getBusinessObject(AllineaRuoloEjb.class).handleApplics(idRuolo,
                    input.getListaApplic());
            if (StringUtils.isNotBlank(cdErr)) {
                PrfRuolo ruolo = ammRuoliHelper.findById(PrfRuolo.class, idRuolo);
                resp.setCdErr(cdErr);
                if (cdErr.equals(MessaggiWSBundle.SERVIZI_RUOLI_002)) {
                    resp.setDlErr(MessaggiWSBundle.getString(cdErr, ruolo.getDsMsgAllineamentoParz()));
                } else {
                    // MessaggiWSBundle.SERVIZI_RUOLI_003
                    resp.setDlErr(MessaggiWSBundle.getString(cdErr));
                    // Elimina il record relativo al ruolo eventualmente inserito in precedenza
                    ammRuoliHelper.removeEntity(ruolo, true);
                }
            } else {
                resp.setCdEsito(AllineaRuoloRisposta.CdEsito.OK);
            }
        } catch (Exception ex) {
            resp.setCdErr(MessaggiWSBundle.SERVIZI_RUOLI_001);
            resp.setDlErr(MessaggiWSBundle.getString(MessaggiWSBundle.SERVIZI_RUOLI_001,
                    ExceptionUtils.getRootCauseMessage(ex)));
        }
        return resp;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long updateRuolo(LogParam param, String nmRuolo, String dsRuolo, String tiRuolo,
            ListaCategRuolo listaCategRuolo) {
        PrfRuolo ruolo = ammRuoliHelper.getPrfRuolo(nmRuolo);
        boolean insert = (ruolo == null);
        if (ruolo == null) {
            ruolo = new PrfRuolo();
            ruolo.setNmRuolo(nmRuolo);
        }
        ruolo.setTiRuolo(tiRuolo);
        ruolo.setDsRuolo(dsRuolo);
        ruolo.setDsMsgAllineamentoParz(null);
        if (ruolo.getPrfAllineaRuolos() == null) {
            ruolo.setPrfRuoloCategorias(new ArrayList<>());
        }
        // Elimino le categorie non più presenti
        List<Long> daRimuovere = new ArrayList<>();
        for (PrfRuoloCategoria ruoloCategoria : ruolo.getPrfRuoloCategorias()) {
            boolean trovato = false;
            for (String categRuolo : listaCategRuolo.getTiCategRuolo()) {
                if (ruoloCategoria.getTiCategRuolo().equals(categRuolo)) {
                    trovato = true;
                }
            }
            if (!trovato) {
                daRimuovere.add(ruoloCategoria.getIdRuoloCategoria());
            }
        }
        daRimuovere.forEach((idRuoloCategoria) -> {
            ammRuoliHelper.deletePrfRuoloCategoria(idRuoloCategoria);
        });
        // Aggiungo le nuove categorie
        for (String categRuolo : listaCategRuolo.getTiCategRuolo()) {
            boolean trovato = false;
            for (PrfRuoloCategoria ruoloCategoria : ruolo.getPrfRuoloCategorias()) {
                if (ruoloCategoria.getTiCategRuolo().equals(categRuolo)) {
                    trovato = true;
                }
            }
            if (!trovato) {
                PrfRuoloCategoria ruoloCategoria = new PrfRuoloCategoria();
                ruoloCategoria.setPrfRuolo(ruolo);
                ruoloCategoria.setTiCategRuolo(categRuolo);
                ruolo.getPrfRuoloCategorias().add(ruoloCategoria);
            }
        }

        ruolo.setFlAllineamentoInCorso("1");
        ruolo.setTiStatoRichAllineaRuoli_1(ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_COMPLETO.name());
        ruolo.setTiStatoRichAllineaRuoli_2(ConstPrfRuolo.TiStatoRichAllineaRuoli.ALLINEAMENTO_COMPLETO.name());

        if (insert) {
            ammRuoliHelper.insertEntity(ruolo, true);
            param.setNomeAzione(SacerLogConstants.AZ_COMP_SW_ALLINEAMENTO_RUOLO_INSERIMENTO);
        } else {
            param.setNomeAzione(SacerLogConstants.AZ_COMP_SW_ALLINEAMENTO_RUOLO_MODIFICA);
        }
        ammRuoliHelper.getEntityManager().flush();

        sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RUOLO, new BigDecimal(ruolo.getIdRuolo()),
                param.getNomeComponenteSoftware(), PremisEnums.TipoAgenteEvento.EXECUTING_PROGRAM);

        return ruolo.getIdRuolo();
    }

    /**
     * Gestisce l'allineamento delle applicazioni per il sistema
     *
     * @param idRuolo
     *            id ruolo
     * @param applics
     *            lista applicazioni di tipo {@link ListaApplic}
     *
     * @return cdErr da ritornare in {@link AllineaRuoloRisposta}
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String handleApplics(Long idRuolo, ListaApplic applics) {
        String cdErr = null;
        // Elimina i record relativi al ruolo creati in precedenza
        ammRuoliHelper.deletePrfAllineaRuolo(idRuolo, null);

        boolean flAllineamentoParziale = false;
        PrfRuolo ruolo = ammRuoliHelper.findById(PrfRuolo.class, idRuolo);
        PrfUsoRuoloApplic usoRuolo = null;
        if (ruolo.getPrfAllineaRuolos() == null) {
            ruolo.setPrfAllineaRuolos(new ArrayList<PrfAllineaRuolo>());
        }
        for (Applic applic : applics) {
            // Verifica la presenza dell'applicazione
            RispostaControlli verificaApplicazione = controlliWs.verificaApplicazione(applic.getNmApplic());
            if (verificaApplicazione.isrBoolean()) {
                AplApplic applicDb = (AplApplic) verificaApplicazione.getrObject();
                if (applicDb.getCdVersioneComp().equalsIgnoreCase(applic.getCdVersioneComp())) {
                    // Verifica la presenza della relazione Applicazione-Ruolo (PrfUsoRuoloApplic)
                    usoRuolo = ammRuoliHelper.getPrfUsoRuoloApplic(idRuolo, applicDb.getIdApplic());
                    if (usoRuolo == null) {
                        usoRuolo = new PrfUsoRuoloApplic();
                        usoRuolo.setAplApplic(applicDb);
                        usoRuolo.setPrfRuolo(ammRuoliHelper.findById(PrfRuolo.class, idRuolo));

                        ammRuoliHelper.insertEntity(usoRuolo, true);
                    }
                    // Si appoggia sulla tabella PrfAllineaRuolo per ottenere le dichiarazioni da eliminare e creare
                    for (DichAutor dichAutor : applic.getListaDichAutor()) {
                        PrfAllineaRuolo allineaRuolo = new PrfAllineaRuolo();
                        allineaRuolo.setPrfRuolo(ruolo);
                        allineaRuolo.setNmApplic(applic.getNmApplic());
                        allineaRuolo.setNmAzionePagina(dichAutor.getNmAzionePagina());
                        allineaRuolo.setNmPaginaWeb(dichAutor.getNmPaginaWeb());
                        allineaRuolo.setNmServizioWeb(dichAutor.getNmServizioWeb());
                        allineaRuolo.setTiDichAutor(dichAutor.getTiDichAutor());
                        allineaRuolo.setTiScopoDichAutor(dichAutor.getTiScopoDichAutor());
                        allineaRuolo.setDsPathEntryMenuPadre(dichAutor.getDsPathEntryMenuPadre());
                        allineaRuolo.setDsPathEntryMenuFoglia(dichAutor.getDsPathEntryMenuFoglia());
                        ammRuoliHelper.insertEntity(allineaRuolo, true);
                    }
                    // Il flag potrebbe essere di nuovo a true se ci sono incongruenze tra i csv di profilazione delle
                    // due applicazioni
                    PrfVChkAllineaRuolo prfVChkAllineaRuolo = ammRuoliHelper.getPrfVChkAllineaRuolo(idRuolo,
                            applic.getNmApplic());
                    flAllineamentoParziale = prfVChkAllineaRuolo.getFlAllineamentoParziale().equals("1");
                    if (flAllineamentoParziale) {
                        cdErr = MessaggiWSBundle.SERVIZI_RUOLI_002;
                        ammRuoliHelper.deletePrfAllineaRuolo(idRuolo, applic.getNmApplic());
                        String oldMsg = ruolo.getDsMsgAllineamentoParz();
                        ruolo.setDsMsgAllineamentoParz(
                                StringUtils.defaultString(oldMsg) + "L'applicazione " + applic.getNmApplic()
                                        + " nell'ambiente source ha componenti non presenti nell'ambiente target;");
                    }
                } else {
                    flAllineamentoParziale = true;
                    cdErr = MessaggiWSBundle.SERVIZI_RUOLI_002;
                    String oldMsg = ruolo.getDsMsgAllineamentoParz();
                    ruolo.setDsMsgAllineamentoParz(StringUtils.defaultString(oldMsg) + "L'applicazione "
                            + applic.getNmApplic() + " nell'ambiente source ha versione " + applic.getCdVersioneComp()
                            + " mentre in quello target ha versione " + applicDb.getCdVersioneComp() + ";");
                }
            }
        }

        // 13) se per il ruolo corrente la tabella PRF_ALLINEA_RUOLO e’ vuota
        Long countAllineaRuolo = ammRuoliHelper.countPrfAllineaRuolo(idRuolo);
        if (flAllineamentoParziale && countAllineaRuolo == 0L) {
            cdErr = MessaggiWSBundle.SERVIZI_RUOLI_003;
        } else if (usoRuolo != null) {
            List<PrfVLisUserDaReplic> lisUserDaReplic = ammRuoliHelper.retrievePrfVLisUserDaReplic(idRuolo);

            // Elimino le autorizzazioni del ruolo
            ammRuoliHelper.deletePrfDichAutorFromPrfVLisDichAllineaTodel(idRuolo);
            // Creo le autorizzazioni mancanti
            List<PrfVLisDichAllineaToadd> dichToAdd = ammRuoliHelper.retrievePrfVLisDichAllineaToadd(idRuolo);
            for (PrfVLisDichAllineaToadd dich : dichToAdd) {
                PrfDichAutor dichAutor = new PrfDichAutor();
                dichAutor.setPrfUsoRuoloApplic(
                        ammRuoliHelper.findById(PrfUsoRuoloApplic.class, dich.getIdUsoRuoloApplic()));
                dichAutor.setTiDichAutor(dich.getTiDichAutor());
                dichAutor.setTiScopoDichAutor(dich.getTiScopoDichAutor());
                dichAutor.setAplAzionePagina((dich.getIdAzionePagina() != null
                        ? ammRuoliHelper.findById(AplAzionePagina.class, dich.getIdAzionePagina()) : null));
                dichAutor.setAplPaginaWeb((dich.getIdPaginaWeb() != null
                        ? ammRuoliHelper.findById(AplPaginaWeb.class, dich.getIdPaginaWeb()) : null));
                dichAutor.setAplServizioWeb((dich.getIdServizioWeb() != null
                        ? ammRuoliHelper.findById(AplServizioWeb.class, dich.getIdServizioWeb()) : null));
                dichAutor.setAplEntryMenuPadre((dich.getIdEntryMenuPadre() != null
                        ? ammRuoliHelper.findById(AplEntryMenu.class, dich.getIdEntryMenuPadre()) : null));
                dichAutor.setAplEntryMenuFoglia((dich.getIdEntryMenuFoglia() != null
                        ? ammRuoliHelper.findById(AplEntryMenu.class, dich.getIdEntryMenuFoglia()) : null));
                ammRuoliHelper.insertEntity(dichAutor, true);
            }
            // Chiamo la procedura di allineamento autorizzazioni utilizzata anche in modifica di un ruolo
            authEjb.alignAuthsRuoli(BigDecimal.valueOf(idRuolo));
            // Per ogni utente recuperato, creo un record di replica
            for (PrfVLisUserDaReplic userDaReplic : lisUserDaReplic) {
                userHelper.registraLogUserDaReplic(userDaReplic.getPrfVLisUserDaReplicId().getIdUserIam(),
                        userDaReplic.getPrfVLisUserDaReplicId().getIdApplic(), ApplEnum.TiOperReplic.MOD);
            }
        }
        ruolo.setFlAllineamentoInCorso("0");
        return cdErr;
    }

    // public boolean checkToProcedeAlignRuoli(ListaApplic applics, String nmRuolo) {
    // PrfRuolo ruolo = ammRuoliHelper.getPrfRuolo(nmRuolo);
    //
    // if (ruolo != null) {
    // // Elimina i record relativi al ruolo creati in precedenza
    // ammRuoliHelper.deletePrfAllineaRuolo(ruolo.getIdRuolo(), null);
    //
    // boolean flAllineamentoParziale = false;
    // PrfUsoRuoloApplic usoRuolo = null;
    // if (ruolo.getPrfAllineaRuolos() == null) {
    // ruolo.setPrfAllineaRuolos(new ArrayList<PrfAllineaRuolo>());
    // }
    //
    // for (Applic applic : applics) {
    // // Verifica la presenza dell'applicazione
    // RispostaControlli verificaApplicazione = controlliWs.verificaApplicazione(applic.getNmApplic());
    // if (verificaApplicazione.isrBoolean()) {
    // AplApplic applicDb = (AplApplic) verificaApplicazione.getrObject();
    // if (applicDb.getCdVersioneComp().equalsIgnoreCase(applic.getCdVersioneComp())) {
    // // Verifica la presenza della relazione Applicazione-Ruolo (PrfUsoRuoloApplic)
    // usoRuolo = ammRuoliHelper.getPrfUsoRuoloApplic(ruolo.getIdRuolo(), applicDb.getIdApplic());
    // if (usoRuolo == null) {
    // usoRuolo = new PrfUsoRuoloApplic();
    // usoRuolo.setAplApplic(applicDb);
    // usoRuolo.setPrfRuolo(ammRuoliHelper.findById(PrfRuolo.class, ruolo.getIdRuolo()));
    //
    // ammRuoliHelper.insertEntity(usoRuolo, true);
    // }
    // // Si appoggia sulla tabella PrfAllineaRuolo per ottenere le dichiarazioni da eliminare e creare
    // for (DichAutor dichAutor : applic.getListaDichAutor()) {
    // PrfAllineaRuolo allineaRuolo = new PrfAllineaRuolo();
    // allineaRuolo.setPrfRuolo(ruolo);
    // allineaRuolo.setNmApplic(applic.getNmApplic());
    // allineaRuolo.setNmAzionePagina(dichAutor.getNmAzionePagina());
    // allineaRuolo.setNmPaginaWeb(dichAutor.getNmPaginaWeb());
    // allineaRuolo.setNmServizioWeb(dichAutor.getNmServizioWeb());
    // allineaRuolo.setTiDichAutor(dichAutor.getTiDichAutor());
    // allineaRuolo.setTiScopoDichAutor(dichAutor.getTiScopoDichAutor());
    // allineaRuolo.setDsPathEntryMenuPadre(dichAutor.getDsPathEntryMenuPadre());
    // allineaRuolo.setDsPathEntryMenuFoglia(dichAutor.getDsPathEntryMenuFoglia());
    // ammRuoliHelper.insertEntity(allineaRuolo, true);
    // }
    // // Il flag potrebbe essere di nuovo a true se ci sono incongruenze tra i csv di profilazione
    // // delle
    // // due applicazioni
    // PrfVChkAllineaRuolo prfVChkAllineaRuolo = ammRuoliHelper
    // .getPrfVChkAllineaRuolo(ruolo.getIdRuolo(), applic.getNmApplic());
    // flAllineamentoParziale = prfVChkAllineaRuolo.getFlAllineamentoParziale().equals("1");
    // } else {
    // flAllineamentoParziale = true;
    // }
    // }
    // }
    //
    // // 13) se per il ruolo corrente la tabella PRF_ALLINEA_RUOLO e’ vuota
    // Long countAllineaRuolo = ammRuoliHelper.countPrfAllineaRuolo(ruolo.getIdRuolo());
    // if (flAllineamentoParziale && countAllineaRuolo == 0L) {
    // return false;
    // }
    // }
    // return true;
    // }
}
