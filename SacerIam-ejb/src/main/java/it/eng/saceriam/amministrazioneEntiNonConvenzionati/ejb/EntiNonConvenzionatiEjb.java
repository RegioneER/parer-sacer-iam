package it.eng.saceriam.amministrazioneEntiNonConvenzionati.ejb;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.ejb.util.ObjectsToLogBefore;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.amministrazioneEntiConvenzionati.helper.EntiConvenzionatiHelper;
import it.eng.saceriam.amministrazioneEntiNonConvenzionati.dto.EnteNonConvenzionatoBean;
import it.eng.saceriam.amministrazioneEntiNonConvenzionati.helper.EntiNonConvenzionatiHelper;
import it.eng.saceriam.entity.AplSistemaVersArkRif;
import it.eng.saceriam.entity.AplSistemaVersante;
import it.eng.saceriam.entity.OrgAccordoVigil;
import it.eng.saceriam.entity.OrgAmbitoTerrit;
import it.eng.saceriam.entity.OrgCategEnte;
import it.eng.saceriam.entity.OrgEnteArkRif;
import it.eng.saceriam.entity.OrgEnteConvenzOrg;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.OrgEnteUserRif;
import it.eng.saceriam.entity.OrgSuptEsternoEnteConvenz;
import it.eng.saceriam.entity.OrgVigilEnteProdut;
import it.eng.saceriam.entity.UsrAbilEnteSiam;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.entity.constraint.ConstOrgAmbitoTerrit;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstUsrStatoUser;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.tablebean.AplSistemaVersanteRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplSistemaVersanteTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAccordoVigilRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAccordoVigilTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbitoTerritRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbitoTerritTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgCategEnteTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteArkRifRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteArkRifTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteConvenzOrgRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteConvenzOrgTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteUserRifRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteUserRifTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSuptEsternoEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSuptEsternoEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgVigilEnteProdutRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgVigilEnteProdutTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUserTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVEnteConvenzByOrganizTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteNonConvenzRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteNonConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilAmbEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilAmbEnteXenteTableBean;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.viewEntity.OrgVRicEnteConvenz;
import it.eng.saceriam.viewEntity.OrgVRicEnteNonConvenz;
import it.eng.saceriam.viewEntity.UsrVAbilAmbEnteConvenz;
import it.eng.saceriam.viewEntity.UsrVAbilAmbEnteXente;
import it.eng.saceriam.web.helper.AmministrazioneUtentiHelper;
import it.eng.saceriam.web.helper.SistemiVersantiHelper;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.Transform;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DiLorenzo_F
 */
@Stateless
@LocalBean
@Interceptors({ it.eng.saceriam.aop.TransactionInterceptor.class })
public class EntiNonConvenzionatiEjb {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntiNonConvenzionatiEjb.class);

    @EJB
    EntiNonConvenzionatiHelper helper;
    @EJB
    AmministrazioneUtentiHelper utentiHelper;
    @EJB
    UserHelper userHelper;
    @EJB
    ParamHelper paramHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;
    @EJB
    EntiConvenzionatiEjb entiComnvenzionatiEjb;
    @EJB
    EntiConvenzionatiHelper ecHelper;
    @EJB
    SistemiVersantiHelper svHelper;
    @Resource
    private SessionContext ctx;

    /**
     * Ritorna il tableBean contenente gli ambiti territoriali dato il tipo, in ordine gerarchico
     *
     * @param tiAmbitoTerrit
     *            tipo ambiente
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAmbitoTerritTableBean getOrgAmbitoTerritTableBean(String tiAmbitoTerrit) throws ParerUserError {
        OrgAmbitoTerritTableBean ambitoTerritTableBean = new OrgAmbitoTerritTableBean();
        List<OrgAmbitoTerrit> ambitoTerrits = helper.retrieveOrgAmbitoTerrit(tiAmbitoTerrit);
        if (!ambitoTerrits.isEmpty()) {
            try {
                ambitoTerritTableBean = (OrgAmbitoTerritTableBean) Transform.entities2TableBean(ambitoTerrits);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero degli ambiti territoriali " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero della lista di ambiti territoriali");
            }
        }

        return ambitoTerritTableBean;
    }

    /**
     * Ritorna il tableBean contenente gli ambiti territoriali figli dell'ambito territoriale 'padre' dato come
     * parametro
     *
     * @param idAmbitoTerrits
     *            lista id ambito territoriale
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAmbitoTerritTableBean getOrgAmbitoTerritTableBean(List<BigDecimal> idAmbitoTerrits)
            throws ParerUserError {
        OrgAmbitoTerritTableBean ambitoTerritTableBean = new OrgAmbitoTerritTableBean();
        List<OrgAmbitoTerrit> ambitoTerrits = helper.retrieveOrgAmbitoTerritChildList(idAmbitoTerrits);
        if (!ambitoTerrits.isEmpty()) {
            try {
                ambitoTerritTableBean = (OrgAmbitoTerritTableBean) Transform.entities2TableBean(ambitoTerrits);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero degli ambiti territoriali " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero della lista di ambiti territoriali");
            }
        }

        return ambitoTerritTableBean;
    }

    /**
     * Ritorna il rowBean contenente l'ambito territoriale padre dell'ambito territoriale 'figlio' dato come parametro
     *
     * @param idAmbitoTerrit
     *            id ambito terrotoriale
     *
     * @return il rowBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAmbitoTerritRowBean getOrgAmbitoTerritParentRowBean(BigDecimal idAmbitoTerrit) throws ParerUserError {
        OrgAmbitoTerritRowBean ambitoTerritRowBean = new OrgAmbitoTerritRowBean();
        OrgAmbitoTerrit ambitoTerrit = helper.retrieveOrgAmbitoTerritParent(idAmbitoTerrit);
        if (ambitoTerrit != null) {
            try {
                ambitoTerritRowBean = (OrgAmbitoTerritRowBean) Transform.entity2RowBean(ambitoTerrit);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dell'ambito territoriale padre "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero dell'ambito territoriale padre");
            }
        }
        return ambitoTerritRowBean;
    }

    public OrgCategEnteTableBean getOrgCategEnteTableBean() throws ParerUserError {
        OrgCategEnteTableBean categEnteTableBean = new OrgCategEnteTableBean();
        List<OrgCategEnte> categEntes = helper.retrieveOrgCategEnteList();
        if (!categEntes.isEmpty()) {
            try {
                categEnteTableBean = (OrgCategEnteTableBean) Transform.entities2TableBean(categEntes);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero delle categorie ente " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero della lista di categorie ente");
            }
        }

        return categEnteTableBean;
    }

    public OrgVRicEnteNonConvenzTableBean getOrgVRicEnteNonConvenzTableBean(String nmEnteSiam, BigDecimal idUserIamCor,
            // BigDecimal idAmbienteEnteConvenz,
            String flEnteCessato, List<BigDecimal> idArchivista, String noArchivista, String tiEnteNonConvenz)
            throws ParerUserError {
        OrgVRicEnteNonConvenzTableBean orgEnteNonConvenzTableBean = new OrgVRicEnteNonConvenzTableBean();
        Date dataOdierna = new Date();
        List<OrgVRicEnteNonConvenz> enteNonConvenzs = helper.retrieveOrgEnteNonConvenzList(nmEnteSiam, idUserIamCor, /*
                                                                                                                      * idAmbienteEnteConvenz,
                                                                                                                      */
                flEnteCessato, idArchivista, noArchivista, dataOdierna, tiEnteNonConvenz);
        if (!enteNonConvenzs.isEmpty()) {
            try {
                orgEnteNonConvenzTableBean = customEntities2EnteNonConvenzTableBean(enteNonConvenzs);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero degli enti non convenzionati "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista di enti non convenzionati");
            }
        }

        return orgEnteNonConvenzTableBean;
    }

    /**
     * Metodo di insert di un nuovo ente non convenzionato
     *
     * @param param
     *            parametri per il logging
     * @param creazioneBean
     *            bean {@link EnteNonConvenzionatoBean}
     * @param idUserIamCor
     *            id user IAM
     *
     * @return id del nuovo ente
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveEnteNonConvenzionato(LogParam param, BigDecimal idUserIamCor,
            EnteNonConvenzionatoBean creazioneBean) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio dell'ente non convenzionato");
        // Controllo esistenza denominazione
        OrgEnteSiam enteSiam = ecHelper.getOrgEnteConvenz(creazioneBean.getNm_ente_convenz());
        if (enteSiam != null) {
            if (enteSiam.getTiEnte().name().equals("CONVENZIONATO")) {
                throw new ParerUserError("Ente gi\u00E0 censito nel sistema come convenzionato");
            } else {
                throw new ParerUserError("Ente gi\u00E0 censito nel sistema come non convenzionato");
            }
        }
        Long idEnteSiam = null;
        try {
            OrgEnteSiam ente = new OrgEnteSiam();
            ente.setTiEnte(ConstOrgEnteSiam.TiEnteSiam.NON_CONVENZIONATO);
            OrgEnteSiam enteConvenzCreazione = helper
                    .getOrgEnteConvenzByTiEnteConvenz(ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE);
            ente.setOrgEnteSiamByIdEnteConvenzCreazione(enteConvenzCreazione);
            setDatiEnteNonConvenzionato(ente, creazioneBean);

            helper.insertEntity(ente, true);

            // Annulla il valore di default di PRODUTTORE
            ente.setTiEnteConvenz(null);

            // Aggiunta abilitazioni ente non convenzionato per gli utenti interessati (utenti che appartengono all’ente
            // di tipo AMMINISTRATORE e agli enti di tipo CONSERVATORE)
            List<UsrUser> utenti = utentiHelper.retrieveUsrUserEnteSiamAppart(null,
                    Collections.singletonMap("IN", Arrays.asList(ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE,
                            ConstOrgEnteSiam.TiEnteConvenz.CONSERVATORE, ConstOrgEnteSiam.TiEnteConvenz.GESTORE)),
                    Collections.singletonMap("NOT IN", Arrays.asList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
                    Collections.singletonMap("IN", Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name())));

            UsrAbilEnteSiam abilEnteSiam = new UsrAbilEnteSiam();
            UsrUser userIamCor = helper.findById(UsrUser.class, idUserIamCor);
            abilEnteSiam.setUsrUser(userIamCor);
            abilEnteSiam.setOrgEnteSiam(ente);
            abilEnteSiam.setFlAbilAutomatica("1");
            String tiEnteConvenzStr = (userIamCor.getOrgEnteSiam().getTiEnteConvenz()
                    .equals(ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE))
                            ? ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE.name().toLowerCase()
                            : ConstOrgEnteSiam.TiEnteConvenz.CONSERVATORE.name().toLowerCase();
            abilEnteSiam.setDsCausaleAbil("Appartenenza ad ente " + tiEnteConvenzStr);
            helper.insertEntity(abilEnteSiam, true);

            for (UsrUser utente : utenti) {
                userHelper.aggiungiAbilEnteNoconv(idUserIamCor.longValue(), utente.getIdUserIam());
            }

            LOGGER.debug("Salvataggio dell'ente non convenzionato completato");
            idEnteSiam = ente.getIdEnteSiam();

            String nmTipoOggetto = (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name()
                    .equals(creazioneBean.getTi_ente_non_convenz()))
                            ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                            : (ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.name()
                                    .equals(creazioneBean.getTi_ente_non_convenz()))
                                            ? SacerLogConstants.TIPO_OGGETTO_ORGANO_VIGILANZA
                                            : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE;
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), nmTipoOggetto, new BigDecimal(idEnteSiam),
                    param.getNomePagina());
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio dell'ente non convenzionato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'ente non convenzionato");
        }
        return idEnteSiam;
    }

    /**
     * Metodo di update di un ente non convenzionato
     *
     * @param param
     *            parametri per il logging
     * @param idUserIamCor
     *            id user IAM
     * @param idEnteNonConvenzionato
     *            id ente non convenzionato
     * @param creazioneBean
     *            bean {@link EnteNonConvenzionatoBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveEnteNonConvenzionato(LogParam param, BigDecimal idUserIamCor, BigDecimal idEnteNonConvenzionato,
            EnteNonConvenzionatoBean creazioneBean) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio delle modifiche sull'ente non convenzionato");
        // Controllo esistenza denominazione
        OrgEnteSiam enteSiam = ecHelper.getOrgEnteConvenz(creazioneBean.getNm_ente_convenz());
        if (enteSiam != null && idEnteNonConvenzionato.longValue() != enteSiam.getIdEnteSiam()) {
            if (enteSiam.getTiEnte().name().equals("CONVENZIONATO")) {
                throw new ParerUserError("Ente gi\u00E0 censito nel sistema come convenzionato");
            } else {
                throw new ParerUserError("Ente gi\u00E0 censito nel sistema come non convenzionato");
            }
        }
        OrgEnteSiam ente = helper.findById(OrgEnteSiam.class, idEnteNonConvenzionato);

        try {
            BigDecimal idEnteSiamProdutCorrispSaved = null;
            if (ente.getOrgEnteSiamByIdEnteProdutCorrisp() != null) {
                idEnteSiamProdutCorrispSaved = BigDecimal
                        .valueOf(ente.getOrgEnteSiamByIdEnteProdutCorrisp().getIdEnteSiam());
            }

            setDatiEnteNonConvenzionato(ente, creazioneBean);

            List<UsrUser> utenti = utentiHelper.retrieveUsrUserEnteSiamAppart(idEnteNonConvenzionato, null,
                    Collections.singletonMap("NOT IN", Arrays.asList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
                    Collections.singletonMap("IN", Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name())));

            if (idEnteSiamProdutCorrispSaved != null
                    && !idEnteSiamProdutCorrispSaved.equals(creazioneBean.getId_ente_siam_produt_corrisp())) {
                for (UsrUser utente : utenti) {
                    // 1. RIMUOVE ABIL
                    userHelper.eliminaAbilEntiConv(utente.getIdUserIam(), idEnteSiamProdutCorrispSaved.longValue());
                    // 2. AGGIUNGE ABIL
                    userHelper.aggiungiAbilEnteCorrisp(idUserIamCor.longValue(), utente.getIdUserIam());
                }
            }

            if (idEnteSiamProdutCorrispSaved == null && creazioneBean.getId_ente_siam_produt_corrisp() != null) {
                // 1. AGGIUNGE ABIL
                for (UsrUser utente : utenti) {
                    userHelper.aggiungiAbilEnteCorrisp(idUserIamCor.longValue(), utente.getIdUserIam());
                }
            }

            helper.getEntityManager().flush();

            LOGGER.debug("Salvataggio dell'ente non convenzionato completato");

            String nmTipoOggetto = (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name()
                    .equals(creazioneBean.getTi_ente_non_convenz()))
                            ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                            : (ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.name()
                                    .equals(creazioneBean.getTi_ente_non_convenz()))
                                            ? SacerLogConstants.TIPO_OGGETTO_ORGANO_VIGILANZA
                                            : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE;
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), nmTipoOggetto, idEnteNonConvenzionato,
                    param.getNomePagina());

        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio dell'ente non convenzionato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'ente non convenzionato");
        }
    }

    private void setDatiEnteNonConvenzionato(OrgEnteSiam ente, EnteNonConvenzionatoBean creazioneBean) {
        // Imposta tutti i campi dell'ente
        ente.setNmEnteSiam(creazioneBean.getNm_ente_convenz());
        ente.setDtIniVal(creazioneBean.getDt_ini_val());
        ente.setDtCessazione(creazioneBean.getDt_cessazione());
        ente.setDsViaSedeLegale(creazioneBean.getDs_via_sede_legale());
        ente.setDsNote(creazioneBean.getDs_note());
        ente.setCdCapSedeLegale(creazioneBean.getCd_cap_sede_legale());
        ente.setDsCittaSedeLegale(creazioneBean.getDs_citta_sede_legale());
        ente.setCdNazioneSedeLegale(creazioneBean.getCd_nazione_sede_legale());
        ente.setIdProvSedeLegale(creazioneBean.getId_prov_sede_legale());
        ente.setCdFisc(creazioneBean.getCd_fisc());
        ente.setTiEnteNonConvenz(ConstOrgEnteSiam.TiEnteNonConvenz.valueOf(creazioneBean.getTi_ente_non_convenz()));
        OrgEnteSiam enteSiamByIdEnteProdutCorrisp = null;
        if (creazioneBean.getId_ente_siam_produt_corrisp() != null) {
            enteSiamByIdEnteProdutCorrisp = helper.findById(OrgEnteSiam.class,
                    creazioneBean.getId_ente_siam_produt_corrisp());
        }
        ente.setOrgEnteSiamByIdEnteProdutCorrisp(enteSiamByIdEnteProdutCorrisp);
        // Per impostare l'ambito territoriale parto dalla forma associata e poi lo popolo col primo non nullo
        BigDecimal idAmbitoTerrit;
        if (creazioneBean.getCd_ambito_territ_forma_associata() != null) {
            idAmbitoTerrit = creazioneBean.getCd_ambito_territ_forma_associata();
        } else if (creazioneBean.getCd_ambito_territ_provincia() != null) {
            idAmbitoTerrit = creazioneBean.getCd_ambito_territ_provincia();
        } else {
            idAmbitoTerrit = creazioneBean.getCd_ambito_territ_regione();
        }
        ente.setIdAmbitoTerrit(idAmbitoTerrit);
    }

    /**
     * Ritorna il rowBean relativo all'entity orgEnteSiam dato il suo id
     *
     * @param idEnteSiam
     *            id ente IAM
     *
     * @return row bean {@link OrgEnteSiamRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteSiamRowBean getOrgEnteSiamRowBean(BigDecimal idEnteSiam) throws ParerUserError {
        OrgEnteSiam ente = helper.findById(OrgEnteSiam.class, idEnteSiam);
        OrgEnteSiamRowBean row = null;
        try {
            row = (OrgEnteSiamRowBean) Transform.entity2RowBean(ente);
            if (ente.getOrgEnteSiamByIdEnteProdutCorrisp() != null) {
                row.setBigDecimal("id_ambiente_ente_convenz", BigDecimal.valueOf(ente
                        .getOrgEnteSiamByIdEnteProdutCorrisp().getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz()));
                row.setString("nm_ambiente_ente_convenz", ente.getOrgEnteSiamByIdEnteProdutCorrisp()
                        .getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz());
                row.setBigDecimal("id_ente_siam_produt_corrisp",
                        BigDecimal.valueOf(ente.getOrgEnteSiamByIdEnteProdutCorrisp().getIdEnteSiam()));
                row.setString("nm_ente_siam_produt_corrisp",
                        ente.getOrgEnteSiamByIdEnteProdutCorrisp().getNmEnteSiam());
            }
            if (ente.getIdAmbitoTerrit() != null) {
                // Setto i campi dell'ambito territoriale
                OrgAmbitoTerrit ambito = helper.findById(OrgAmbitoTerrit.class, ente.getIdAmbitoTerrit());
                if (ambito.getTiAmbitoTerrit().equals(ConstOrgAmbitoTerrit.TiAmbitoTerrit.FORMA_ASSOCIATA.toString())) {
                    final OrgAmbitoTerrit orgAmbitoTerritProvincia = ambito.getOrgAmbitoTerrit();
                    final OrgAmbitoTerrit orgAmbitoTerritRegione = orgAmbitoTerritProvincia.getOrgAmbitoTerrit();
                    row.setBigDecimal("cd_ambito_territ_forma_associata", ente.getIdAmbitoTerrit());
                    row.setBigDecimal("cd_ambito_territ_provincia",
                            new BigDecimal(orgAmbitoTerritProvincia.getIdAmbitoTerrit()));
                    row.setBigDecimal("cd_ambito_territ_regione",
                            new BigDecimal(orgAmbitoTerritRegione.getIdAmbitoTerrit()));
                } else if (ambito.getTiAmbitoTerrit()
                        .equals(ConstOrgAmbitoTerrit.TiAmbitoTerrit.PROVINCIA.toString())) {
                    final OrgAmbitoTerrit orgAmbitoTerritRegione = ambito.getOrgAmbitoTerrit();
                    row.setBigDecimal("cd_ambito_territ_provincia", ente.getIdAmbitoTerrit());
                    row.setBigDecimal("cd_ambito_territ_regione",
                            new BigDecimal(orgAmbitoTerritRegione.getIdAmbitoTerrit()));
                } else {
                    // REGIONE/STATO
                    row.setBigDecimal("cd_ambito_territ_regione", ente.getIdAmbitoTerrit());
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero dell'ente non convenzionato "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Ritorna il tableBean contenente gli enti non convenzionati tranne quello passato in input
     *
     * @param idEnteNonConvezDaEscludere
     *            id ente non convenzionato da escludere
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteSiamTableBean getOrgEnteSiamTableBean(BigDecimal idEnteNonConvezDaEscludere) throws ParerUserError {
        OrgEnteSiamTableBean enteNonConvenzTableBean = new OrgEnteSiamTableBean();
        List<OrgEnteSiam> enteNonConvenzList = helper.getOrgEnteNonConvenzList(idEnteNonConvezDaEscludere);
        if (!enteNonConvenzList.isEmpty()) {
            try {
                enteNonConvenzTableBean = (OrgEnteSiamTableBean) Transform.entities2TableBean(enteNonConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero degli enti non convenzionati "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista degli enti non convenzionati");
            }
        }
        return enteNonConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente gli enti produttori riferiti all'ente passato in input
     *
     * @param idEnteFornitEst
     *            id ente fornitore esterno
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgSuptEsternoEnteConvenzTableBean getOrgEntiSupportatiTableBean(BigDecimal idEnteFornitEst)
            throws ParerUserError {
        OrgSuptEsternoEnteConvenzTableBean suptExtEnteConvenzTableBean = new OrgSuptEsternoEnteConvenzTableBean();
        List<OrgSuptEsternoEnteConvenz> suptExtEnteConvenzList = helper
                .getOrgSuptEsternoEnteConvenzList(idEnteFornitEst);
        if (!suptExtEnteConvenzList.isEmpty()) {
            try {
                for (OrgSuptEsternoEnteConvenz suptExtEnteConvenz : suptExtEnteConvenzList) {
                    OrgSuptEsternoEnteConvenzRowBean suptExtEnteConvenzRowBean = new OrgSuptEsternoEnteConvenzRowBean();
                    suptExtEnteConvenzRowBean = (OrgSuptEsternoEnteConvenzRowBean) Transform
                            .entity2RowBean(suptExtEnteConvenz);
                    if (suptExtEnteConvenz.getOrgEnteSiamByIdEnteProdut().getOrgAmbienteEnteConvenz() != null) {
                        suptExtEnteConvenzRowBean.setString("nm_ambiente_ente_convenz", suptExtEnteConvenz
                                .getOrgEnteSiamByIdEnteProdut().getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz());
                    }
                    suptExtEnteConvenzRowBean.setString("nm_ente_siam",
                            suptExtEnteConvenz.getOrgEnteSiamByIdEnteProdut().getNmEnteSiam());
                    suptExtEnteConvenzTableBean.add(suptExtEnteConvenzRowBean);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero degli enti produttori " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista degli enti produttori");
            }
        }
        return suptExtEnteConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente per l'organo di vigilanza dato in input la lista di enti conservatori
     *
     * @param idEnteOrganoVigil
     *            id ente organo vigilanza
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAccordoVigilTableBean getOrgAccordoVigilTableBean(BigDecimal idEnteOrganoVigil) throws ParerUserError {
        OrgAccordoVigilTableBean table = new OrgAccordoVigilTableBean();
        List<OrgAccordoVigil> list = helper.retrieveOrgAccordoVigil(idEnteOrganoVigil);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgAccordoVigil accordoVigil : list) {
                    OrgAccordoVigilRowBean accordoVigilRowBean = new OrgAccordoVigilRowBean();
                    accordoVigilRowBean = (OrgAccordoVigilRowBean) Transform.entity2RowBean(accordoVigil);
                    accordoVigilRowBean.setString("nm_ente_siam",
                            accordoVigil.getOrgEnteSiamByIdEnteConserv().getNmEnteSiam());
                    table.add(accordoVigilRowBean);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista di accordi di vigilanza dell'ente conservatore "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il tableBean contenente gli utenti (di eventuale tipo e stato passati in ingresso) che hanno ente non
     * convenzionato di appartenenza = ente corrente
     *
     * @param idEnteNonConvenz
     *            id ente non convenzionato
     * @param tiStatoUser
     *            stato degli utenti
     * @param tipoUser
     *            tipo di utenti
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public UsrUserTableBean getUsrUserEnteNoconvenzTableBean(BigDecimal idEnteNonConvenz, List<String> tiStatoUser,
            List<String> tipoUser) throws ParerUserError {
        UsrUserTableBean table = new UsrUserTableBean();
        List<UsrUser> list = utentiHelper.retrieveUsrUserEnteSiamAppart(idEnteNonConvenz, tiStatoUser, tipoUser);
        if (list != null && !list.isEmpty()) {
            try {
                table = (UsrUserTableBean) Transform.entities2TableBean(list);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista di utenti appartenenti all'ente non convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    public void checkAppartUtenteOrganizEnteConvenzCorrisp(BigDecimal idEnteNonConvenz,
            BigDecimal idEnteSiamProdutCorrisp) throws ParerUserError {
        List<BigDecimal> organizIamList = utentiHelper.retrieveIdOrganizIamUserEnteSiamAppartList(idEnteNonConvenz,
                Arrays.asList(ConstUsrStatoUser.TiStatotUser.CESSATO.name()),
                Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name()));
        for (BigDecimal organizIam : organizIamList) {
            if (helper.existsEnteConvenzOrgEnteCorrisp(organizIam, idEnteSiamProdutCorrisp)) {
                throw new ParerUserError(
                        "Esiste almeno un utente appartenente all'ente non convenzionato ed abilitato a una delle organizzazioni associate all'ente convenzionato corrispondente che si intende modificare: modificare le abilitazioni");
            }
        }
    }

    /**
     * Ritorna il tableBean contenente per l'ente non convenzionato dato in input la lista di utenti archivisti
     *
     * @param idEnteNonConvenz
     *            id ente non convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteArkRifTableBean getOrgEnteArkRifTableBean(BigDecimal idEnteNonConvenz) throws ParerUserError {
        OrgEnteArkRifTableBean table = new OrgEnteArkRifTableBean();
        List<OrgEnteArkRif> list = helper.retrieveOrgEnteArkRif(idEnteNonConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgEnteArkRif enteArkRif : list) {
                    OrgEnteArkRifRowBean row = new OrgEnteArkRifRowBean();
                    row.setBigDecimal("id_user_iam", new BigDecimal(enteArkRif.getUsrUser().getIdUserIam()));
                    row.setString("nm_cognome_user", enteArkRif.getUsrUser().getNmCognomeUser());
                    row.setString("nm_nome_user", enteArkRif.getUsrUser().getNmNomeUser());
                    row.setString("nm_userid", enteArkRif.getUsrUser().getNmUserid());
                    row.setString("tipo_user", enteArkRif.getUsrUser().getTipoUser());
                    row.setString("fl_referente", enteArkRif.getFlReferente());
                    row.setString("dl_note", enteArkRif.getDlNote());
                    row.setBigDecimal("id_ente_ark_rif", new BigDecimal(enteArkRif.getIdEnteArkRif()));
                    table.add(row);
                }
            } catch (IllegalArgumentException ex) {
                String msg = "Errore durante il recupero della lista di utenti archivisti dell'ente non convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il rowBean relativo all'entity orgEnteArkRif dato il suo id
     *
     * @param idEnteArkRif
     *            id ente archiviato
     *
     * @return row bean {@link OrgEnteArkRifRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteArkRifRowBean getOrgEnteArkRifRowBean(BigDecimal idEnteArkRif) throws ParerUserError {
        OrgEnteArkRif enteArkRif = helper.findById(OrgEnteArkRif.class, idEnteArkRif);
        OrgEnteArkRifRowBean row = null;
        try {
            row = (OrgEnteArkRifRowBean) Transform.entity2RowBean(enteArkRif);
            row.setString("nm_userid", enteArkRif.getUsrUser().getNmUserid());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero dell'archivista di riferimento "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Ritorna il rowBean relativo all'entity orgEnteUserRif dato il suo id
     *
     * @param idEnteUserRif
     *            id ente/user
     *
     * @return row bean OrgEnteUserRifRowBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteUserRifRowBean getOrgEnteUserRifRowBean(BigDecimal idEnteUserRif) throws ParerUserError {
        OrgEnteUserRif enteUserRif = helper.findById(OrgEnteUserRif.class, idEnteUserRif);
        OrgEnteUserRifRowBean row = null;
        try {
            row = (OrgEnteUserRifRowBean) Transform.entity2RowBean(enteUserRif);
            row.setString("nm_userid", enteUserRif.getUsrUser().getNmUserid());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero del referente ente " + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Ritorna il tableBean contenente per l'ente non convenzionato dato in input la lista di utenti referenti
     *
     * @param idEnteNonConvenz
     *            id ente non convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteUserRifTableBean getOrgEnteUserRifTableBean(BigDecimal idEnteNonConvenz) throws ParerUserError {
        OrgEnteUserRifTableBean table = new OrgEnteUserRifTableBean();
        List<OrgEnteUserRif> list = helper.retrieveOrgEnteUserRif(idEnteNonConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                // table = (OrgEnteUserRifTableBean)Transform.entities2TableBean(list);
                for (OrgEnteUserRif enteUserRif : list) {
                    OrgEnteUserRifRowBean row = new OrgEnteUserRifRowBean();
                    row.setBigDecimal("id_user_iam", new BigDecimal(enteUserRif.getUsrUser().getIdUserIam()));
                    row.setString("nm_cognome_user", enteUserRif.getUsrUser().getNmCognomeUser());
                    row.setString("nm_nome_user", enteUserRif.getUsrUser().getNmNomeUser());
                    row.setString("nm_userid", enteUserRif.getUsrUser().getNmUserid());
                    row.setString("ds_email", enteUserRif.getUsrUser().getDsEmail());
                    row.setString("tipo_user", enteUserRif.getUsrUser().getTipoUser());
                    row.setString("dl_note", enteUserRif.getDlNote());
                    row.setBigDecimal("id_ente_user_rif", new BigDecimal(enteUserRif.getIdEnteUserRif()));
                    table.add(row);
                }
            } catch (IllegalArgumentException ex) {
                String msg = "Errore durante il recupero della lista di utenti referenti dell'ente non convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il tableBean contenente i versatori associati al fornitore esterno passato in input
     *
     * @param idEnteFornitEst
     *            id ente fornitore esterno
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteConvenzOrgTableBean getOrgEnteConvenzOrgTableBean(BigDecimal idEnteFornitEst) throws ParerUserError {
        OrgEnteConvenzOrgTableBean enteConvenzOrgTableBean = new OrgEnteConvenzOrgTableBean();
        List<OrgEnteConvenzOrg> enteConvenzOrgList = helper.getOrgEnteConvenzOrgList(idEnteFornitEst);
        if (!enteConvenzOrgList.isEmpty()) {
            try {
                for (OrgEnteConvenzOrg enteConvenzOrg : enteConvenzOrgList) {
                    OrgEnteConvenzOrgRowBean enteConvenzOrgRowBean = new OrgEnteConvenzOrgRowBean();
                    enteConvenzOrgRowBean = (OrgEnteConvenzOrgRowBean) Transform.entity2RowBean(enteConvenzOrg);
                    enteConvenzOrgRowBean.setString("nm_vers_ping", enteConvenzOrg.getOrgEnteSiam().getNmEnteSiam());
                    enteConvenzOrgRowBean.setString("nm_organiz", enteConvenzOrg.getUsrOrganizIam().getNmOrganiz());
                    enteConvenzOrgTableBean.add(enteConvenzOrgRowBean);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero dei versatori associati " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero della lista dei versatori associati");
            }
        }
        return enteConvenzOrgTableBean;
    }

    /**
     * Ritorna il tableBean con tutti i sistemi versanti dipendendi dal dal fornitore esterno passato in input
     *
     * @param idEnteFornitEst
     *            id ente fornitore esterno
     *
     * @return table bean {@link AplSistemaVersanteTableBean}
     */
    public AplSistemaVersanteTableBean getAplSistemaVersanteTableBean(BigDecimal idEnteFornitEst) {
        AplSistemaVersanteTableBean sistemaVersanteTB = new AplSistemaVersanteTableBean();
        List<AplSistemaVersante> sistemaVersanteList = helper.getAplSistemaVersanteById(idEnteFornitEst);
        try {
            for (AplSistemaVersante sistemaVersante : sistemaVersanteList) {
                AplSistemaVersanteRowBean rb = new AplSistemaVersanteRowBean();
                rb = (AplSistemaVersanteRowBean) Transform.entity2RowBean(sistemaVersante);

                /* MEV 27055 - ARCHIVISTI DI RIFERIMENTO */
                List<AplSistemaVersArkRif> archivisti = svHelper
                        .getAplSistemaVersArkRifList(BigDecimal.valueOf(sistemaVersante.getIdSistemaVersante()));
                if (archivisti != null && !archivisti.isEmpty()) {
                    if (archivisti.size() > 10) {
                        rb.setString("archivista", "Sistema con più di 10 archivisti definiti");
                    } else {
                        String archivistiStr = "";
                        for (AplSistemaVersArkRif archivista : archivisti) {
                            archivistiStr = archivistiStr + archivista.getUsrUser().getNmUserid() + ";";
                        }
                        rb.setString("archivista", archivistiStr);
                    }
                } else {
                    rb.setString("archivista", "Nessun archivista definito");
                }
                sistemaVersanteTB.add(rb);
            }

        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException
                | NoSuchMethodException | InvocationTargetException e) {
            LOGGER.error(e.getMessage());
        }
        return sistemaVersanteTB;
    }

    /**
     * Restituisce il rowbean contenente il dettaglio di un ente supportato
     *
     * @param idSuptEstEnteConvenz
     *            l'id dell'ente supportato da recuperare su DB
     *
     * @return il rowbean contenente il dettaglio ente supportato
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgSuptEsternoEnteConvenzRowBean getOrgSuptEsternoEnteConvenzRowBean(BigDecimal idSuptEstEnteConvenz)
            throws ParerUserError {
        OrgSuptEsternoEnteConvenz enteSupt = helper.findById(OrgSuptEsternoEnteConvenz.class, idSuptEstEnteConvenz);
        OrgSuptEsternoEnteConvenzRowBean row = null;
        try {
            row = (OrgSuptEsternoEnteConvenzRowBean) Transform.entity2RowBean(enteSupt);
            row.setTimestamp("dt_ini_val_supt", new Timestamp(enteSupt.getDtIniVal().getTime()));
            row.setTimestamp("dt_fin_val_supt", new Timestamp(enteSupt.getDtFinVal().getTime()));
            if (enteSupt.getOrgEnteSiamByIdEnteProdut().getOrgAmbienteEnteConvenz() != null) {
                row.setBigDecimal("id_ambiente_ente_convenz", BigDecimal.valueOf(enteSupt.getOrgEnteSiamByIdEnteProdut()
                        .getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz()));
                row.setString("nm_ambiente_ente_convenz",
                        enteSupt.getOrgEnteSiamByIdEnteProdut().getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz());
            }
            row.setBigDecimal("id_ente_convenz",
                    BigDecimal.valueOf(enteSupt.getOrgEnteSiamByIdEnteProdut().getIdEnteSiam()));
            row.setString("nm_ente_convenz", enteSupt.getOrgEnteSiamByIdEnteProdut().getNmEnteSiam());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero dell'ente supportato " + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Metodo di eliminazione di un ente supportato
     *
     * @param param
     *            parametri per il logging
     * @param idSuptEstEnteConvenz
     *            id ente
     * @param tipoEnte
     *            tipo ente
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgSuptEsternoEnteConvenz(LogParam param, BigDecimal idSuptEstEnteConvenz, String tipoEnte)
            throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'ente supportato");
        try {
            OrgSuptEsternoEnteConvenz suptEstEnteConvenz = helper.findById(OrgSuptEsternoEnteConvenz.class,
                    idSuptEstEnteConvenz);

            BigDecimal idEnteFornitEst = BigDecimal
                    .valueOf(suptEstEnteConvenz.getOrgEnteSiamByIdEnteFornitEst().getIdEnteSiam());
            BigDecimal idEnteConvenzSupt = BigDecimal
                    .valueOf(suptEstEnteConvenz.getOrgEnteSiamByIdEnteProdut().getIdEnteSiam());

            // Determino la lista degli oggetti PRIMA della modifica
            List<ObjectsToLogBefore> listaOggettiDaLoggarePrima = sacerLogEjb.logBefore(
                    param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(),
                    tipoEnte.equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())
                            ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                            : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE,
                    new BigDecimal(suptEstEnteConvenz.getOrgEnteSiamByIdEnteFornitEst().getIdEnteSiam()),
                    param.getNomePagina());

            helper.removeEntity(suptEstEnteConvenz, true);

            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(),
                    tipoEnte.equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())
                            ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                            : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE,
                    new BigDecimal(suptEstEnteConvenz.getOrgEnteSiamByIdEnteFornitEst().getIdEnteSiam()),
                    param.getNomePagina());

            if (helper.countEnteSuptPerEnteFornit(idEnteFornitEst, idEnteConvenzSupt) == 0) {
                List<UsrUser> utenti = utentiHelper.retrieveUsrUserEnteSiamAppart(idEnteConvenzSupt,
                        Collections.singletonMap("NOT IN",
                                Arrays.asList(ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE)),
                        Collections.singletonMap("NOT IN",
                                Arrays.asList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
                        Collections.singletonMap("IN", Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name())));

                for (UsrUser utente : utenti) {
                    userHelper.eliminaAbilEntiConv(utente.getIdUserIam(), idEnteFornitEst.longValue());
                }

                // determino utenti PERSONA_FISICA con stato diverso da cessato
                // e flag “Allinea abilitazioni alle organizzazioni e agli enti supportati in automatico”
                // che appartengono all’ente non convenzionato
                List<UsrUser> utentiSup = utentiHelper.retrieveUsrUserEnteSup(idEnteFornitEst,
                        Collections.singletonMap("IN",
                                Arrays.asList(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO)),
                        Collections.singletonMap("NOT IN",
                                Arrays.asList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
                        Collections.singletonMap("IN", Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name())));

                for (UsrUser utente : utentiSup) {
                    /*
                     * Il sistema elimina l’abilitazione all'utente all’ente siam che non è più supportato dal fornitore
                     */
                    userHelper.eliminaAbilEnteSupest(utente.getIdUserIam(), idEnteConvenzSupt.longValue());
                    /*
                     * Il sistema elimina le abilitazioni alle organizzazioni (a cui l’utente corrente e’ abilitato)
                     * appartenenti agli enti supportati
                     */
                    userHelper.eliminaAbilOrganizSupest(utente.getIdUserIam());
                    /*
                     * Il sistema elimina le abilitazioni ai tipi dato (a cui l’utente corrente e’ abilitato) delle
                     * organizzazioni appartenenti agli enti vigilati
                     */
                    userHelper.eliminaAbilTipiDatoSupest(utente.getIdUserIam());
                }

                // Determino la lista degli oggetti DOPO la modifica
                List<ObjectsToLogBefore> listaOggettiDaLoggareDopo = sacerLogEjb.logBefore(
                        param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                        param.getNomeAzione(),
                        tipoEnte.equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())
                                ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                                : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE,
                        new BigDecimal(suptEstEnteConvenz.getOrgEnteSiamByIdEnteFornitEst().getIdEnteSiam()),
                        param.getNomePagina());

                if (!listaOggettiDaLoggarePrima.isEmpty() && !listaOggettiDaLoggareDopo.isEmpty()) {
                    // Calcolo la differenza tra listaOggettiDaLoggarePrima e listaOggettiDaLoggareDopo:
                    List<BigDecimal> listaIdOggetti = listaOggettiDaLoggarePrima.get(0).getIdOggetto().stream()
                            .filter(u1 -> listaOggettiDaLoggareDopo.get(0).getIdOggetto().stream()
                                    .noneMatch(u2 -> u2.equals(u1)))
                            .collect(Collectors.toList());
                    // Aggiorno la lista degli oggetti nella listaOggettiDaLoggareDopo da passare alla logAfter
                    listaOggettiDaLoggareDopo.get(0).setIdOggetto(listaIdOggetti);
                }

                sacerLogEjb.logAfter(param.getTransactionLogContext(), param.getNomeApplicazione(),
                        param.getNomeUtente(), param.getNomeAzione(), listaOggettiDaLoggareDopo, param.getNomePagina());
            }
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'ente supportato ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Restituisce il rowbean contenente il dettaglio di un accordo di vigilanza
     *
     * @param idAccordoVigil
     *            l'id dell'accordo di vigilanza da recuperare su DB @return, il rowbean contenente il dettaglio accordo
     *            di vigilanza
     *
     * @return row bean {@link OrgAccordoVigilRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAccordoVigilRowBean getOrgAccordoVigilRowBean(BigDecimal idAccordoVigil) throws ParerUserError {
        OrgAccordoVigil accordoVigil = helper.findById(OrgAccordoVigil.class, idAccordoVigil);
        OrgAccordoVigilRowBean row = null;
        try {
            row = (OrgAccordoVigilRowBean) Transform.entity2RowBean(accordoVigil);
            if (accordoVigil.getOrgEnteSiamByIdEnteOrganoVigil().getTiEnteNonConvenz() != null) {
                row.setString("ti_ente_non_convenz",
                        accordoVigil.getOrgEnteSiamByIdEnteOrganoVigil().getTiEnteNonConvenz().name());
            }
            row.setString("nm_ente_siam", accordoVigil.getOrgEnteSiamByIdEnteOrganoVigil().getNmEnteSiam());
            row.setTimestamp("dt_ini_val_ente_siam",
                    new Timestamp(accordoVigil.getOrgEnteSiamByIdEnteOrganoVigil().getDtIniVal().getTime()));
            if (accordoVigil.getOrgEnteSiamByIdEnteOrganoVigil().getDtCessazione() != null) {
                row.setTimestamp("dt_cessazione_ente_siam",
                        new Timestamp(accordoVigil.getOrgEnteSiamByIdEnteOrganoVigil().getDtCessazione().getTime()));
            }
            if (accordoVigil.getOrgEnteSiamByIdEnteOrganoVigil().getOrgEnteSiamByIdEnteProdutCorrisp() != null) {
                row.setString("nm_ente_siam_produt_corrisp", accordoVigil.getOrgEnteSiamByIdEnteOrganoVigil()
                        .getOrgEnteSiamByIdEnteProdutCorrisp().getNmEnteSiam());
                if (accordoVigil.getOrgEnteSiamByIdEnteOrganoVigil().getOrgEnteSiamByIdEnteProdutCorrisp()
                        .getOrgAmbienteEnteConvenz() != null) {
                    row.setString("nm_ambiente_ente_convenz",
                            accordoVigil.getOrgEnteSiamByIdEnteOrganoVigil().getOrgEnteSiamByIdEnteProdutCorrisp()
                                    .getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz());
                }
            }
            row.setString("nm_ente_conserv", accordoVigil.getOrgEnteSiamByIdEnteConserv().getNmEnteSiam());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero dell'accordo di vigilanza "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Restituisce il tablebean rappresentante la lista degli enti vigilati.
     *
     * @param idAccordoVigil
     *            id accordo di vigilanza
     *
     * @return il tablebean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgVigilEnteProdutTableBean getOrgVigilEnteProdutTableBean(BigDecimal idAccordoVigil) throws ParerUserError {
        OrgVigilEnteProdutTableBean vigilEnteProdutTableBean = new OrgVigilEnteProdutTableBean();
        List<OrgVigilEnteProdut> vigilEnteProdutList = helper.retrieveOrgVigilEnteProdut(idAccordoVigil);
        try {
            if (!vigilEnteProdutList.isEmpty()) {
                for (OrgVigilEnteProdut vigilEnteProdut : vigilEnteProdutList) {
                    OrgVigilEnteProdutRowBean vigilEnteProdutRowBean = (OrgVigilEnteProdutRowBean) Transform
                            .entity2RowBean(vigilEnteProdut);
                    if (vigilEnteProdut.getOrgEnteSiam().getOrgAmbienteEnteConvenz() != null) {
                        vigilEnteProdutRowBean.setString("nm_ambiente_ente_convenz", vigilEnteProdut.getOrgEnteSiam()
                                .getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz());
                    }
                    vigilEnteProdutRowBean.setString("nm_ente_siam", vigilEnteProdut.getOrgEnteSiam().getNmEnteSiam());
                    vigilEnteProdutTableBean.add(vigilEnteProdutRowBean);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Errore durante il recupero degli enti vigilati per l'accordo avente id " + idAccordoVigil
                    + " " + ExceptionUtils.getRootCauseMessage(e), e);
            throw new ParerUserError(
                    "Errore durante il recupero degli enti vigilati per l'accordo avente id " + idAccordoVigil);
        }
        return vigilEnteProdutTableBean;
    }

    public boolean isUtenteArchivistaInEnteConvenz(BigDecimal idEnteConvenz, BigDecimal idUserIam) {
        return helper.isUtenteArchivistaInEnteConvenz(idEnteConvenz, idUserIam);
    }

    public boolean isReferenteEnteInEnteConvenz(BigDecimal idEnteConvenz, BigDecimal idUserIam) {
        return helper.isReferenteEnteInEnteConvenz(idEnteConvenz, idUserIam);
    }

    public void checkReferenteEnteCessato(BigDecimal idUserIam) throws ParerUserError {
        if (helper.isReferenteEnteCessato(idUserIam)) {
            throw new ParerUserError("Attenzione: utente cessato, impossibile visualizzare il dettaglio");
        }
    }

    public boolean checkUserAbilEnteConvenz(BigDecimal idUserIam, BigDecimal idEnteSiam) throws ParerUserError {
        return userHelper.isAbilEntiConv(idUserIam.longValue(), idEnteSiam.longValue());
    }

    /**
     * Metodo di eliminazione di un utente archivista
     *
     * @param param
     *            parametri per il logging
     * @param idEnteArkRif
     *            id ente archiviazione
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgEnteArkRif(LogParam param, BigDecimal idEnteArkRif) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'utente archivista dall'ente non convenzionato");
        try {
            OrgEnteArkRif enteArkRif = helper.findById(OrgEnteArkRif.class, idEnteArkRif);
            OrgEnteSiam enteSiam = helper.findByIdWithLock(OrgEnteSiam.class,
                    enteArkRif.getOrgEnteSiam().getIdEnteSiam());
            helper.removeEntity(enteArkRif, true);
            String nmTipoOggetto = (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO
                    .equals(enteSiam.getTiEnteNonConvenz()))
                            ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                            : (ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA
                                    .equals(enteSiam.getTiEnteNonConvenz()))
                                            ? SacerLogConstants.TIPO_OGGETTO_ORGANO_VIGILANZA
                                            : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE;
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), nmTipoOggetto,
                    new BigDecimal(enteArkRif.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'utente archivista dall'ente non convenzionato ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    public String getNmEnteSiam(BigDecimal idEnteSiam) {
        return (helper.findById(OrgEnteSiam.class, idEnteSiam)).getNmEnteSiam();
    }

    public UsrUserTableBean getUtentiArchivisti() throws ParerUserError {
        UsrUserTableBean usrUserTableBean = new UsrUserTableBean();
        List<UsrUser> utentiArchivistiList = helper.retrieveUtentiArchivisti();
        if (!utentiArchivistiList.isEmpty()) {
            try {
                usrUserTableBean = (UsrUserTableBean) Transform.entities2TableBean(utentiArchivistiList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero degli utenti archivisti " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero degli utenti archivisti");
            }
        }
        return usrUserTableBean;
    }

    public UsrVAbilAmbEnteConvenzTableBean getUsrVAbilAmbEnteConvenzTableBean(BigDecimal idUserIam) {
        UsrVAbilAmbEnteConvenzTableBean abilAmbEnteConvenzTableBean = new UsrVAbilAmbEnteConvenzTableBean();
        List<UsrVAbilAmbEnteConvenz> abilAmbEnteConvenzList = helper.retrieveAmbientiEntiConvenzAbilitati(idUserIam);
        if (!abilAmbEnteConvenzList.isEmpty()) {
            try {
                abilAmbEnteConvenzTableBean = (UsrVAbilAmbEnteConvenzTableBean) Transform
                        .entities2TableBean(abilAmbEnteConvenzList);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return abilAmbEnteConvenzTableBean;
    }

    /**
     * Recupera gli ambienti ente a cui l'utente passato in ingresso è abilitato (anche quelli che non sono ancora padri
     * di alcun ente)
     *
     * @param idUserIam
     *            id user IAM
     *
     * @return table bean {@link UsrVAbilAmbEnteXenteTableBean}
     */
    public UsrVAbilAmbEnteXenteTableBean getUsrVAbilAmbEnteXEnteTableBean(BigDecimal idUserIam) {
        UsrVAbilAmbEnteXenteTableBean abilAmbEnteXenteTableBean = new UsrVAbilAmbEnteXenteTableBean();
        List<UsrVAbilAmbEnteXente> abilAmbEnteXenteList = helper.retrieveAmbientiEntiXenteAbilitati(idUserIam);
        if (!abilAmbEnteXenteList.isEmpty()) {
            try {
                abilAmbEnteXenteTableBean = (UsrVAbilAmbEnteXenteTableBean) Transform
                        .entities2TableBean(abilAmbEnteXenteList);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return abilAmbEnteXenteTableBean;
    }

    public UsrVAbilAmbEnteXenteTableBean getUsrVAbilAmbEnteXEnteValidTableBean(BigDecimal idUserIam) {
        UsrVAbilAmbEnteXenteTableBean abilAmbEnteXEnteTableBean = new UsrVAbilAmbEnteXenteTableBean();
        List<UsrVAbilAmbEnteXente> abilAmbEnteXEnteList = helper.retrieveAbilAmbEnteXEnteValidAmbienti(idUserIam);
        if (!abilAmbEnteXEnteList.isEmpty()) {
            try {
                abilAmbEnteXEnteTableBean = (UsrVAbilAmbEnteXenteTableBean) Transform
                        .entities2TableBean(abilAmbEnteXEnteList);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return abilAmbEnteXEnteTableBean;
    }

    /**
     * Recupera la lista dei nomi degli enti convenzionati cui l’utente è abilitato con accordo valido dell'ambiente
     * scelto
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idAmbienteEnteConvenz
     *            id ambiente convenzionato
     *
     * @return table bean {@link OrgVRicEnteConvenzTableBean}
     */
    public OrgVRicEnteConvenzTableBean getOrgVRicEnteConvenzAbilAccordoValidoTableBean(BigDecimal idUserIamCor,
            BigDecimal idAmbienteEnteConvenz) {
        OrgVRicEnteConvenzTableBean ricEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        List<OrgVRicEnteConvenz> ricEnteConvenzList = helper.retrieveEntiConvenzAbilitatiAccordoValido(idUserIamCor,
                idAmbienteEnteConvenz);
        if (!ricEnteConvenzList.isEmpty()) {
            try {
                ricEnteConvenzTableBean = (OrgVRicEnteConvenzTableBean) Transform
                        .entities2TableBean(ricEnteConvenzList);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return ricEnteConvenzTableBean;
    }

    @Deprecated
    public OrgVRicEnteNonConvenzTableBean getOrgVRicEnteNonConvenzTableBean(BigDecimal idAmbienteEnteConvenz)
            throws ParerUserError {
        OrgVRicEnteNonConvenzTableBean ricEnteNonConvenzTableBean = new OrgVRicEnteNonConvenzTableBean();
        List<OrgVRicEnteNonConvenz> ricEnteNonConvenzList = helper
                .retrieveOrgVRicEnteNonConvenzByAmbiente(idAmbienteEnteConvenz);
        if (!ricEnteNonConvenzList.isEmpty()) {
            try {
                ricEnteNonConvenzTableBean = customEntities2EnteNonConvenzTableBean(ricEnteNonConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero degli enti non convenzionati "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista di enti non convenzionati");
            }
        }
        return ricEnteNonConvenzTableBean;
    }

    /**
     * Recupera la lista dei nomi degli enti non convenzionati cui l’utente è abilitato
     *
     * @param idUserIam
     *            id user IAM
     *
     * @return table bean {@link OrgVRicEnteNonConvenzTableBean}
     */
    @Deprecated
    public OrgVRicEnteNonConvenzTableBean getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal idUserIam) {
        OrgVRicEnteNonConvenzTableBean ricEnteNonConvenzTableBean = new OrgVRicEnteNonConvenzTableBean();
        List<OrgVRicEnteNonConvenz> ricEnteNonConvenzList = helper.retrieveEntiNonConvenzAbilitati(idUserIam);
        if (!ricEnteNonConvenzList.isEmpty()) {
            try {
                ricEnteNonConvenzTableBean = (OrgVRicEnteNonConvenzTableBean) Transform
                        .entities2TableBean(ricEnteNonConvenzList);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return ricEnteNonConvenzTableBean;
    }

    private OrgVRicEnteNonConvenzTableBean customEntities2EnteNonConvenzTableBean(
            List<OrgVRicEnteNonConvenz> ricEnteNonConvenzList) throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        OrgVRicEnteNonConvenzTableBean ricEnteNonConvenzTableBean = new OrgVRicEnteNonConvenzTableBean();
        for (OrgVRicEnteNonConvenz ente : ricEnteNonConvenzList) {
            OrgVRicEnteNonConvenzRowBean row = (OrgVRicEnteNonConvenzRowBean) Transform.entity2RowBean(ente);
            if (ente.getDtCessazione() != null && ente.getDtCessazione().before(new Date())) {
                row.setString("cessato", "1");
            } else {
                row.setString("cessato", "0");
            }

            /* MEV 25804 - ARCHIVISTI DI RIFERIMENTO */
            List<OrgEnteArkRif> archivisti = ecHelper.getOrgEnteArkRifList(ente.getIdEnteSiam());
            if (archivisti != null && !archivisti.isEmpty()) {
                String archivistiStr = "";
                for (OrgEnteArkRif archivista : archivisti) {
                    archivistiStr = archivistiStr + archivista.getUsrUser().getNmUserid() + ";";
                }
                row.setString("archivista", archivistiStr);
            }

            ricEnteNonConvenzTableBean.add(row);
        }
        return ricEnteNonConvenzTableBean;
    }

    /**
     * Metodo di eliminazione di un utente referente dell'ente
     *
     * @param param
     *            parametri per il logging
     * @param idEnteUserRif
     *            id ente/utente
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgEnteUserRif(LogParam param, BigDecimal idEnteUserRif) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'utente referente dall'ente non convenzionato");
        try {
            OrgEnteUserRif enteUserRif = helper.findById(OrgEnteUserRif.class, idEnteUserRif);
            OrgEnteSiam enteSiam = helper.findByIdWithLock(OrgEnteSiam.class,
                    enteUserRif.getOrgEnteSiam().getIdEnteSiam());
            helper.removeEntity(enteUserRif, true);
            String nmTipoOggetto = (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO
                    .equals(enteSiam.getTiEnteNonConvenz()))
                            ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                            : (ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA
                                    .equals(enteSiam.getTiEnteNonConvenz()))
                                            ? SacerLogConstants.TIPO_OGGETTO_ORGANO_VIGILANZA
                                            : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE;
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), nmTipoOggetto,
                    new BigDecimal(enteUserRif.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'utente referente dall'ente non convenzionato ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo centralizzato per verificare che l'intervallo di validità di un'entità figlia sia coerente con
     * l'intervallo di validità dell'entità padre
     *
     * @param dtIniValPadre
     *            data inizio validata padre
     * @param dtFinValPadre
     *            data fine validata padre
     * @param dtIniValFiglio
     *            data inizio validata figlio
     * @param dtFinValFiglio
     *            data fine validata padre
     * @param nmPadre
     *            nome padre
     * @param nmFiglio
     *            nome figlio
     *
     * @throws ParerUserError
     *             errore generico
     */
    public void checkInclusioneFiglio(Date dtIniValPadre, Date dtFinValPadre, Date dtIniValFiglio, Date dtFinValFiglio,
            String nmPadre, String nmFiglio) throws ParerUserError {
        // Se l'intevallo del figlio non è incluso totalmente nell'intervallo del padre
        if (dtIniValPadre.after(dtIniValFiglio) || dtFinValPadre.before(dtFinValFiglio)) {
            throw new ParerUserError("La validità di " + nmFiglio + " non e' inclusa nella validità di " + nmPadre);
        }
    }

    public void checkInclusionePadreFigli(Date dtIniValPadre, Date dtFinValPadre, String nmPadre,
            Map<String, Date[]> mappaFigli) throws ParerUserError {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (Map.Entry<String, Date[]> entry : mappaFigli.entrySet()) {
            String nmFiglio = entry.getKey();
            Date[] intervalloDateFiglio = entry.getValue();
            // Se l'intevallo del figlio non è incluso totalmente nell'intervallo del padre
            if (dtIniValPadre.after(intervalloDateFiglio[0]) || dtFinValPadre.before(intervalloDateFiglio[1])) {
                sb.append(sep).append(nmFiglio);
                sep = ", ";
            }
        }
        if (sb.length() > 0) {
            throw new ParerUserError(
                    "La validità di " + sb.toString() + " non e' inclusa nella validità di " + nmPadre);
        }
    }

    public void checkEnteSuptPerEnteFornit(BigDecimal idEnteNonConvenz, BigDecimal idEnteConvenz, Date dtIniValSupt,
            Date dtFinValSupt, BigDecimal idSuptEstEnteConvenzDaEscludere) throws ParerUserError {
        if (helper.existsEnteSuptPerEnteFornit(idEnteNonConvenz, idEnteConvenz, dtIniValSupt, dtFinValSupt,
                idSuptEstEnteConvenzDaEscludere)) {
            throw new ParerUserError(
                    "Esiste già un supporto tra ente convenzionato e fornitore il cui periodo di validità si sovrappone a quello indicato");
        }
    }

    /**
     * Metodo di inserimento di un ente supportato
     *
     * @param param
     *            parametri per il logging
     * @param idUserIamCor
     *            id user IAM
     * @param idEnteFornitEst
     *            id ente fornitore esterno
     * @param idEnteProdut
     *            id ente produttore
     * @param dtIniVal
     *            data inizio validita
     * @param dtFinVal
     *            data fine validita
     * @param tipoEnte
     *            tipo ente
     *
     * @return pk
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long insertOrgSuptEsternoEnteConvenz(LogParam param, long idUserIamCor, BigDecimal idEnteFornitEst,
            BigDecimal idEnteProdut, Date dtIniVal, Date dtFinVal, String tipoEnte) throws ParerUserError {

        OrgSuptEsternoEnteConvenz suptEsternoEnteConvenz = new OrgSuptEsternoEnteConvenz();

        OrgEnteSiam enteFornitEst = helper.findByIdWithLock(OrgEnteSiam.class, idEnteFornitEst);
        suptEsternoEnteConvenz.setOrgEnteSiamByIdEnteFornitEst(enteFornitEst);

        enteFornitEst.getOrgSuptEsternoEnteConvenzByIdEnteFornitEsts().add(suptEsternoEnteConvenz);

        OrgEnteSiam enteProdut = helper.findByIdWithLock(OrgEnteSiam.class, idEnteProdut);
        suptEsternoEnteConvenz.setOrgEnteSiamByIdEnteProdut(enteProdut);

        enteProdut.getOrgSuptEsternoEnteConvenzByIdEnteProduts().add(suptEsternoEnteConvenz);

        suptEsternoEnteConvenz.setDtIniVal(dtIniVal);
        suptEsternoEnteConvenz.setDtFinVal(dtFinVal);

        helper.insertEntity(suptEsternoEnteConvenz, true);

        // Determino la lista degli oggetti PRIMA della modifica
        List<ObjectsToLogBefore> listaOggettiDaLoggarePrima = sacerLogEjb.logBefore(param.getTransactionLogContext(),
                param.getNomeApplicazione(), param.getNomeUtente(), param.getNomeAzione(),
                tipoEnte.equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())
                        ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                        : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE,
                new BigDecimal(suptEsternoEnteConvenz.getOrgEnteSiamByIdEnteFornitEst().getIdEnteSiam()),
                param.getNomePagina());

        // determino utenti PERSONA_FISICA che appartengono ad enti produttori (diversi da amministratore) supportati da
        // fornitore
        List<UsrUser> utenti = utentiHelper.retrieveUsrUserEnteSiamAppart(idEnteProdut,
                Collections.singletonMap("NOT IN", Arrays.asList(ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE)),
                Collections.singletonMap("NOT IN", Arrays.asList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
                Collections.singletonMap("IN", Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name())));

        for (UsrUser utente : utenti) {
            if (!userHelper.isAbilEntiConv(utente.getIdUserIam(), idEnteFornitEst.longValue())) {
                userHelper.aggiungiAbilEnteFornit(idUserIamCor, utente.getIdUserIam());
            }
        }

        // determino utenti PERSONA_FISICA con stato diverso da cessato
        // e flag “Allinea abilitazioni alle organizzazioni e agli enti supportati in automatico”
        // che appartengono all’ente non convenzionato
        List<UsrUser> utentiSup = utentiHelper.retrieveUsrUserEnteSup(idEnteFornitEst,
                Collections.singletonMap("IN", Arrays.asList(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO)),
                Collections.singletonMap("NOT IN", Arrays.asList(ConstUsrStatoUser.TiStatotUser.CESSATO.name())),
                Collections.singletonMap("IN", Arrays.asList(ApplEnum.TipoUser.PERSONA_FISICA.name())));

        for (UsrUser utente : utentiSup) {
            // Inserisco l'abilitazione a ente siam
            if (!userHelper.isAbilEntiConv(utente.getIdUserIam(), idEnteProdut.longValue())) {
                userHelper.aggiungiAbilEntiSuptFornit(idUserIamCor, utente.getIdUserIam(), idEnteFornitEst.longValue(),
                        idEnteProdut.longValue());
            }
            // Per ogni utente associato alla propria applicazione (SACER, PING...)
            for (UsrUsoUserApplic usoUserApplic : utente.getUsrUsoUserApplics()) {
                // Ricavo la lista delle strutture/versatori
                OrgVEnteConvenzByOrganizTableBean tb = entiComnvenzionatiEjb
                        .getOrgVEnteConvenzByOrganizTableBean(BigDecimal.valueOf(enteProdut.getIdEnteSiam()), true);
                // Controllo se la struttura/versatore per l'utente è già abilitata
                for (int i = 0; i < tb.size(); i++) {
                    BigDecimal idOrganizIam = tb.getRow(i).getIdOrganizIam();
                    // Controllo se la struttura/versatore (idOrganizIam) per l'utente è già abilitata
                    if (!userHelper.isAbilEntiConvOrg(usoUserApplic.getIdUsoUserApplic(), idOrganizIam.longValue())) {

                        userHelper.aggiungiAbilOrganizFornit(idUserIamCor, utente.getIdUserIam());

                        userHelper.aggiungiAbilDatiFornit(idUserIamCor, utente.getIdUserIam());

                    }

                }

            }

        }

        // Determino la lista degli oggetti DOPO la modifica
        List<ObjectsToLogBefore> listaOggettiDaLoggareDopo = sacerLogEjb.logBefore(param.getTransactionLogContext(),
                param.getNomeApplicazione(), param.getNomeUtente(), param.getNomeAzione(),
                tipoEnte.equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())
                        ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                        : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE,
                new BigDecimal(suptEsternoEnteConvenz.getOrgEnteSiamByIdEnteFornitEst().getIdEnteSiam()),
                param.getNomePagina());

        if (!listaOggettiDaLoggarePrima.isEmpty() && !listaOggettiDaLoggareDopo.isEmpty()) {
            // Calcolo la differenza tra listaOggettiDaLoggarePrima e listaOggettiDaLoggareDopo:
            List<BigDecimal> listaIdOggetti = listaOggettiDaLoggareDopo.get(0).getIdOggetto().stream().filter(
                    u2 -> listaOggettiDaLoggarePrima.get(0).getIdOggetto().stream().noneMatch(u1 -> u1.equals(u2)))
                    .collect(Collectors.toList());
            // Aggiorno la lista degli oggetti nella listaOggettiDaLoggareDopo da passare alla logAfter
            listaOggettiDaLoggareDopo.get(0).setIdOggetto(listaIdOggetti);
        }

        LOGGER.debug("Salvataggio dell'ente supportato completato");

        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(),
                tipoEnte.equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())
                        ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                        : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE,
                idEnteFornitEst, param.getNomePagina());
        sacerLogEjb.logAfter(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                param.getNomeAzione(), listaOggettiDaLoggareDopo, param.getNomePagina());

        return suptEsternoEnteConvenz.getIdSuptEstEnteConvenz();
    }

    /**
     * Metodo di update di un assoziazione tra ente supportato e fornitore
     *
     * @param param
     *            parametri per il logging
     * @param idEnteFornitEst
     *            id ente fornitore esterno
     * @param idSuptEstEnteConvenz
     *            id ente convenzionato
     * @param dtIniVal
     *            data inizio validita
     * @param dtFinVal
     *            data fine validita
     * @param tipoEnte
     *            tipo ente
     *
     * @return pk
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long updateOrgSuptEsternoEnteConvenz(LogParam param, BigDecimal idSuptEstEnteConvenz,
            BigDecimal idEnteFornitEst, Date dtIniVal, Date dtFinVal, String tipoEnte) throws ParerUserError {

        OrgSuptEsternoEnteConvenz suptEsternoEnteConvenz = helper.findById(OrgSuptEsternoEnteConvenz.class,
                idSuptEstEnteConvenz);
        suptEsternoEnteConvenz.setDtIniVal(dtIniVal);
        suptEsternoEnteConvenz.setDtFinVal(dtFinVal);

        // Persisto le modifiche
        helper.mergeEntity(suptEsternoEnteConvenz);
        helper.getEntityManager().flush();

        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(),
                tipoEnte.equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.name())
                        ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                        : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE,
                idEnteFornitEst, param.getNomePagina());

        return suptEsternoEnteConvenz.getIdSuptEstEnteConvenz();
    }

    public Map<String, String> getUsrOrganizIamMap(BigDecimal idOrganizApplic) {
        String[] ids = helper.getStrutUnitaDocAccordoOrganizMap(idOrganizApplic);
        UsrOrganizIam ambiente = helper.findById(UsrOrganizIam.class, new Long(ids[0]));
        UsrOrganizIam ente = helper.findById(UsrOrganizIam.class, new Long(ids[1]));
        UsrOrganizIam struttura = helper.findById(UsrOrganizIam.class, new Long(ids[2]));
        Map<String, String> org = new HashMap<>();
        org.put("AMBIENTE", ambiente.getNmOrganiz());
        org.put("ENTE", ente.getNmOrganiz());
        org.put("STRUTTURA", struttura.getNmOrganiz());
        return org;
    }
}
