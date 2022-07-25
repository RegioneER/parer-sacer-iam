package it.eng.saceriam.web.ejb;

import it.eng.parer.idpjaas.logutils.LogDto;
import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplAzionePagina;
import it.eng.saceriam.entity.AplClasseTipoDato;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.AplPaginaWeb;
import it.eng.saceriam.entity.AplServizioWeb;
import it.eng.saceriam.entity.AplSistemaVersante;
import it.eng.saceriam.entity.LogJob;
import it.eng.saceriam.entity.NtfNotifica;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.PrfDichAutor;
import it.eng.saceriam.entity.PrfRuolo;
import it.eng.saceriam.entity.PrfUsoRuoloApplic;
import it.eng.saceriam.entity.UsrAppartUserRich;
import it.eng.saceriam.entity.UsrDichAbilDati;
import it.eng.saceriam.entity.UsrDichAbilEnteConvenz;
import it.eng.saceriam.entity.UsrDichAbilOrganiz;
import it.eng.saceriam.entity.UsrIndIpUser;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrRichGestUser;
import it.eng.saceriam.entity.UsrStatoUser;
import it.eng.saceriam.entity.UsrTipoDatoIam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.UsrUsoRuoloUserDefault;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstUsrAppartUserRich;
import it.eng.saceriam.entity.constraint.ConstUsrRichGestUser;
import it.eng.saceriam.entity.constraint.ConstUsrStatoUser;
import it.eng.saceriam.exception.ParerInternalError;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.grantedEntity.LogEventoLoginUser;
import it.eng.saceriam.grantedEntity.constraint.ConstLogEventoLoginUser;
import it.eng.saceriam.grantedViewEntity.LogVRicAccessi;
import it.eng.saceriam.grantedViewEntity.UsrVChkUserCancPing;
import it.eng.saceriam.grantedViewEntity.UsrVChkUserCancSacer;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.job.ejb.JobLogger;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm.FiltriReplica;
import it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm.FiltriUtenti;
import it.eng.saceriam.slite.gen.tablebean.AplApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.AplApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplAzionePaginaTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplClasseTipoDatoTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplEntryMenuTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplPaginaWebTableBean;
import it.eng.saceriam.slite.gen.tablebean.AplServizioWebTableBean;
import it.eng.saceriam.slite.gen.tablebean.LogEventoLoginUserTableBean;
import it.eng.saceriam.slite.gen.tablebean.PrfDichAutorRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfRuoloTableBean;
import it.eng.saceriam.slite.gen.tablebean.PrfUsoRuoloApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.PrfUsoRuoloApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrAppartUserRichRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrAppartUserRichTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilDatiRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilDatiTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilOrganizRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrDichAbilOrganizTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrIndIpUserRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrIndIpUserTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrOrganizIamRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrOrganizIamTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrRichGestUserRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrTipoDatoIamTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUserRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoRuoloUserDefaultRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoRuoloUserDefaultTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoUserApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUsoUserApplicTableBean;
import it.eng.saceriam.slite.gen.viewbean.LogVRicAccessiTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisDichAutorTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilDatiTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilOrganizNolastLivTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilOrganizRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilOrganizTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisAbilEnteTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisAbilOrganizTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisEntiSiamAppEnteRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisEntiSiamAppEnteTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisEntiSiamPerAzioRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisEntiSiamPerAzioTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisSchedRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisSchedTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisStatoUserTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserReplicRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserReplicTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUsoRuoloDichTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVRicRichiesteRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVRicRichiesteTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVTreeOrganizIamRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVTreeOrganizIamTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVVisDichAbilRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVVisLastRichGestUserRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVVisLastSchedRowBean;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.viewEntity.OrgVChkRefEnte;
import it.eng.saceriam.viewEntity.PrfVLisDichAutor;
import it.eng.saceriam.viewEntity.UsrVAbilDati;
import it.eng.saceriam.viewEntity.UsrVAbilOrganiz;
import it.eng.saceriam.viewEntity.UsrVAbilOrganizNolastLiv;
import it.eng.saceriam.viewEntity.UsrVChkUserCancIam;
import it.eng.saceriam.viewEntity.UsrVLisAbilEnte;
import it.eng.saceriam.viewEntity.UsrVLisAbilOrganiz;
import it.eng.saceriam.viewEntity.UsrVLisEntiSiamAppEnte;
import it.eng.saceriam.viewEntity.UsrVLisEntiSiamPerAzio;
import it.eng.saceriam.viewEntity.UsrVLisSched;
import it.eng.saceriam.viewEntity.UsrVLisStatoUser;
import it.eng.saceriam.viewEntity.UsrVLisUser;
import it.eng.saceriam.viewEntity.UsrVLisUserReplic;
import it.eng.saceriam.viewEntity.UsrVLisUsoRuoloDich;
import it.eng.saceriam.viewEntity.UsrVRicRichieste;
import it.eng.saceriam.viewEntity.UsrVTreeOrganizIam;
import it.eng.saceriam.viewEntity.UsrVVisDichAbil;
import it.eng.saceriam.viewEntity.UsrVVisLastRichGestUser;
import it.eng.saceriam.viewEntity.UsrVVisLastSched;
import it.eng.saceriam.web.dto.PairAuth;
import it.eng.saceriam.web.helper.AmministrazioneUtentiHelper;
import it.eng.saceriam.web.helper.SistemiVersantiHelper;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ActionEnums;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.saceriam.web.util.Transform;
import it.eng.saceriam.ws.ejb.WsIdpLogger;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.base.table.BaseTable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ejb Amministrazione Utenti di SacerIam
 *
 * @author Gilioli_P
 */
@Stateless
@LocalBean
public class AmministrazioneUtentiEjb {

    public AmministrazioneUtentiEjb() {
    }

    @EJB
    private AmministrazioneUtentiHelper amministrazioneUtentiHelper;
    @EJB
    private UserHelper userHelper;
    @EJB
    private AuthEjb authEjb;
    @EJB
    private JobLogger jobLogger;
    @EJB
    private ParamHelper paramHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;
    @EJB
    private WsIdpLogger idpLogger;
    @Resource
    private SessionContext ctx;
    @EJB
    private SistemiVersantiHelper sistemiVersantiHelper;
    @EJB
    private EntiConvenzionatiEjb entiConvenzionatiEjb;

    private static final Logger log = LoggerFactory.getLogger(AmministrazioneUtentiEjb.class);

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome
     *
     * @return il table bean delle applicazioni
     *
     * @throws EMFError
     *             errore generico
     */
    public AplApplicTableBean getAplApplicTableBean() throws EMFError {
        AplApplicTableBean applicTableBean = new AplApplicTableBean();
        List<AplApplic> applicList = amministrazioneUtentiHelper.getAplApplicList();
        try {
            if (applicList != null && !applicList.isEmpty()) {
                applicTableBean = (AplApplicTableBean) Transform.entities2TableBean(applicList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicTableBean;
    }

    public PrfRuoloTableBean getPrfRuoliTableBean(BigDecimal idApplic) throws EMFError {
        PrfRuoloTableBean ruoliTB = new PrfRuoloTableBean();
        List<PrfRuolo> ruoliList = amministrazioneUtentiHelper.getPrfRuoli(idApplic);

        try {
            if (ruoliList != null && !ruoliList.isEmpty()) {
                ruoliTB = (PrfRuoloTableBean) Transform.entities2TableBean(ruoliList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliTB;
    }

    public PrfRuoloTableBean getPrfRuoliUtenteTableBean(BigDecimal idApplic, String tipoEnte, String tipoUtente)
            throws EMFError {
        PrfRuoloTableBean ruoliTB = new PrfRuoloTableBean();
        List<PrfRuolo> ruoliList = amministrazioneUtentiHelper.getPrfRuoliUtente(idApplic, tipoEnte, tipoUtente);

        try {
            if (ruoliList != null && !ruoliList.isEmpty()) {
                ruoliTB = (PrfRuoloTableBean) Transform.entities2TableBean(ruoliList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliTB;
    }

    public PrfRuoloTableBean getPrfRuoliTableBean(String nmApplic) throws EMFError {
        PrfRuoloTableBean ruoliTB = new PrfRuoloTableBean();
        List<PrfRuolo> ruoliList = amministrazioneUtentiHelper.getPrfRuoli(nmApplic);

        try {
            if (ruoliList != null && !ruoliList.isEmpty()) {
                ruoliTB = (PrfRuoloTableBean) Transform.entities2TableBean(ruoliList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliTB;
    }

    public PrfRuoloTableBean getPrfRuoliTableBean(String nmApplic, Set<BigDecimal> idRuoliExcluded) throws EMFError {
        PrfRuoloTableBean ruoliTB = new PrfRuoloTableBean();
        PrfRuoloRowBean ruoloRB = new PrfRuoloRowBean();
        List<PrfRuolo> ruoliList = amministrazioneUtentiHelper.getPrfRuoli(nmApplic);
        try {
            if (ruoliList != null && !ruoliList.isEmpty()) {
                for (PrfRuolo ruolo : ruoliList) {
                    if (!idRuoliExcluded.contains(BigDecimal.valueOf(ruolo.getIdRuolo()))) {
                        ruoloRB = (PrfRuoloRowBean) Transform.entity2RowBean(ruolo);
                        ruoliTB.add(ruoloRB);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliTB;
    }

    public PrfRuoloTableBean getRuoliSpecificiTableBean(String nmApplic, long idUserIam) throws EMFError {
        PrfRuoloTableBean ruoliTB = new PrfRuoloTableBean();
        List<PrfRuolo> ruoliList = amministrazioneUtentiHelper.getPrfRuoli(nmApplic);
        List<PrfRuolo> ruoliDefaultList = amministrazioneUtentiHelper.getRuoliDefaultUtenteApplic(nmApplic, idUserIam);
        try {
            if (ruoliList != null && !ruoliList.isEmpty()) {
                ruoliList.removeAll(ruoliDefaultList);
                ruoliTB = (PrfRuoloTableBean) Transform.entities2TableBean(ruoliList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliTB;
    }

    public PrfRuoloTableBean getPrfRuoliTableBean() throws EMFError {
        PrfRuoloTableBean ruoliTB = new PrfRuoloTableBean();
        List<PrfRuolo> ruoliList = amministrazioneUtentiHelper.getPrfRuoli();
        try {
            if (ruoliList != null && !ruoliList.isEmpty()) {
                ruoliTB = (PrfRuoloTableBean) Transform.entities2TableBean(ruoliList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliTB;
    }

    /**
     * Ritorna il rowBean di un ruolo dato il suo id
     *
     * @param idRuolo
     *            id ruolo
     *
     * @return il rowBean del ruolo
     */
    public PrfRuoloRowBean getPrfRuoloById(BigDecimal idRuolo) {
        List<PrfRuolo> ruoli = amministrazioneUtentiHelper.getPrfRuoloById(idRuolo);
        PrfRuoloRowBean ruoloRB = new PrfRuoloRowBean();
        if (ruoli != null && !ruoli.isEmpty()) {
            try {
                ruoloRB = (PrfRuoloRowBean) Transform.entity2RowBean(ruoli.get(0));
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
        return ruoloRB;
    }

    /**
     *
     * @param idUsoRuoloSet
     *            lista id ruolo distinti
     * @param tiDichAutor
     *            autore
     * @param idApplic
     *            id applicazione
     *
     * @return table bean {@link PrfVLisDichAutorTableBean}
     *
     * @throws EMFError
     *             errore generico
     */
    public PrfVLisDichAutorTableBean getPrfDichAutorViewBean(Set<BigDecimal> idUsoRuoloSet, String tiDichAutor,
            BigDecimal idApplic) throws EMFError {
        List<PrfVLisDichAutor> dichAutorList = amministrazioneUtentiHelper.getPrfDichAutorList(idUsoRuoloSet,
                tiDichAutor, idApplic);
        PrfVLisDichAutorTableBean dichsTB = new PrfVLisDichAutorTableBean();
        try {
            if (dichAutorList != null && !dichAutorList.isEmpty()) {
                dichsTB = (PrfVLisDichAutorTableBean) Transform.entities2TableBean(dichAutorList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dichsTB;
    }

    public UsrVAbilOrganizTableBean getOrganizAbilitateByUserCorrente(BigDecimal idUserIamCorrente, BigDecimal idApplic)
            throws EMFError {
        List<UsrVAbilOrganiz> organizAbilitateList = amministrazioneUtentiHelper
                .getUsrVAbilOrganizList(idUserIamCorrente, idApplic);
        UsrVAbilOrganizTableBean organizAbilitateTB = new UsrVAbilOrganizTableBean();
        try {
            if (organizAbilitateList != null && !organizAbilitateList.isEmpty()) {
                organizAbilitateTB = (UsrVAbilOrganizTableBean) Transform.entities2TableBean(organizAbilitateList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return organizAbilitateTB;
    }

    public PrfRuoloTableBean getRuoliByApplic(BigDecimal idApplic, ApplEnum.TipoRuolo tipoRuolo) throws EMFError {
        List<PrfRuolo> ruoliList;
        PrfRuoloTableBean ruoliDefaultTB = new PrfRuoloTableBean();
        try {
            if (tipoRuolo.equals(ApplEnum.TipoRuolo.DEF)) {
                ruoliList = amministrazioneUtentiHelper.getRuoliDefaultByApplic(idApplic);
            } else {
                ruoliList = amministrazioneUtentiHelper.getRuoliDichByApplic(idApplic);
            }
            if (ruoliList != null && !ruoliList.isEmpty()) {
                ruoliDefaultTB = (PrfRuoloTableBean) Transform.entities2TableBean(ruoliList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliDefaultTB;
    }

    public UsrVTreeOrganizIamTableBean getUsrVTreeOrganizIamNoLastLevelTableBean(long idUserIam, BigDecimal idApplic)
            throws EMFError {
        List<UsrVTreeOrganizIam> dichAutorList = amministrazioneUtentiHelper
                .getUsrVTreeOrganizIamNoLastLevelList(idUserIam, idApplic);
        UsrVTreeOrganizIamTableBean dichsTB = new UsrVTreeOrganizIamTableBean();
        try {
            if (dichAutorList != null && !dichAutorList.isEmpty()) {
                dichsTB = (UsrVTreeOrganizIamTableBean) Transform.entities2TableBean(dichAutorList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dichsTB;
    }

    public UsrVTreeOrganizIamRowBean getUsrVTreeOrganizIamPerVersatore(BigDecimal idUserIam) throws EMFError {
        UsrVTreeOrganizIam treeOrganizIam = amministrazioneUtentiHelper.getUsrVTreeOrganizIam(idUserIam);
        UsrVTreeOrganizIamRowBean treeOrganizIamRowBean = new UsrVTreeOrganizIamRowBean();
        try {
            treeOrganizIamRowBean = (UsrVTreeOrganizIamRowBean) Transform.entity2RowBean(treeOrganizIam);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return treeOrganizIamRowBean;
    }

    // public UsrVTreeOrganizIamTableBean getUsrVTreeOrganizIamAllOrgChildTableBean(long idUserIamCor, BigDecimal
    // idApplic, String tipoEnte, BigDecimal idEnte) throws EMFError {
    // List<UsrVTreeOrganizIam> dichAutorList =
    // amministrazioneUtentiHelper.getUsrVTreeOrganizIamAllOrgChildList(idUserIamCor, idApplic, tipoEnte, idEnte);
    // UsrVTreeOrganizIamTableBean dichsTB = new UsrVTreeOrganizIamTableBean();
    // try {
    // if (dichAutorList != null && !dichAutorList.isEmpty()) {
    // dichsTB = (UsrVTreeOrganizIamTableBean) Transform.entities2TableBean(dichAutorList);
    // }
    // } catch (Exception e) {
    // log.error(e.getMessage(), e);
    // }
    // return dichsTB;
    // }
    public BaseTable getUsrVTreeOrganizIamAllOrgChildTableBean(long idUserIamCor, BigDecimal idApplic, String tipoEnte,
            BigDecimal idEnte, String flAbilOrganizAutom, String flAbilOrganizEntiAutom, BigDecimal idRichGestUser)
            throws EMFError {
        List<Object[]> dichAutorList = amministrazioneUtentiHelper.getUsrVTreeOrganizIamAllOrgChildList(idUserIamCor,
                idApplic, tipoEnte, idEnte, flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichGestUser);
        BaseTable dichsTB = new BaseTable();
        try {
            for (Object[] o : dichAutorList) {
                BaseRow dichsRB = new BaseRow();
                dichsRB.setBigDecimal("id_organiz_iam", (BigDecimal) o[0]);
                dichsRB.setString("dl_composito_organiz", (String) o[1]);
                dichsRB.setString("tipoEnte", tipoEnte);
                dichsRB.setString("ds_causale_dich", (String) o[2]);
                if (o.length > 3) {
                    dichsRB.setBigDecimal("id_ente", (BigDecimal) o[3]);
                }
                dichsTB.add(dichsRB);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dichsTB;
    }

    // public UsrVTreeOrganizIamTableBean getUsrVTreeOrganizIamUnaOrgTableBean(long idUserIamCor, BigDecimal idApplic,
    // String tipoEnte, BigDecimal idEnte, String flAbilOrganizAutom) throws EMFError {
    // List<UsrVTreeOrganizIam> dichAutorList =
    // amministrazioneUtentiHelper.getUsrVTreeOrganizIamUnaOrgList(idUserIamCor, idApplic, tipoEnte, idEnte,
    // flAbilOrganizAutom);
    // UsrVTreeOrganizIamTableBean dichsTB = new UsrVTreeOrganizIamTableBean();
    // try {
    // if (dichAutorList != null && !dichAutorList.isEmpty()) {
    // dichsTB = (UsrVTreeOrganizIamTableBean) Transform.entities2TableBean(dichAutorList);
    // }
    // } catch (Exception e) {
    // log.error(e.getMessage(), e);
    // }
    // return dichsTB;
    // }
    public BaseTable getUsrVTreeOrganizIamUnaOrgTableBean(long idUserIamCor, BigDecimal idApplic, String tipoEnte,
            BigDecimal idEnte, String flAbilOrganizAutom, String flAbilOrganizEntiAutom, BigDecimal idRichGestUser)
            throws EMFError {
        List<Object[]> dichAutorList = amministrazioneUtentiHelper.getUsrVTreeOrganizIamUnaOrgList(idUserIamCor,
                idApplic, tipoEnte, idEnte, flAbilOrganizAutom, flAbilOrganizEntiAutom, idRichGestUser);
        BaseTable dichsTB = new BaseTable();
        try {
            for (Object[] o : dichAutorList) {
                BaseRow dichsRB = new BaseRow();
                dichsRB.setBigDecimal("id_organiz_iam", (BigDecimal) o[0]);
                dichsRB.setString("dl_composito_organiz", (String) o[1]);
                dichsRB.setString("tipoEnte", tipoEnte);
                dichsRB.setString("ds_causale_dich", (String) o[2]);
                if (o.length > 3) {
                    dichsRB.setBigDecimal("id_ente", (BigDecimal) o[3]);
                }
                dichsTB.add(dichsRB);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dichsTB;
    }

    public UsrVAbilOrganizNolastLivTableBean getUsrVAbilOrganizNolastLivTableBean(long idUserIam, BigDecimal idApplic,
            String dlPathIdOrganizIam) throws EMFError {
        List<UsrVAbilOrganizNolastLiv> abilOrganizNoLastLiv = amministrazioneUtentiHelper
                .getUsrVAbilOrganizNolastLivList(idUserIam, idApplic, dlPathIdOrganizIam);
        UsrVAbilOrganizNolastLivTableBean abilOrganizNolastLivTB = new UsrVAbilOrganizNolastLivTableBean();
        try {
            if (abilOrganizNoLastLiv != null && !abilOrganizNoLastLiv.isEmpty()) {
                abilOrganizNolastLivTB = (UsrVAbilOrganizNolastLivTableBean) Transform
                        .entities2TableBean(abilOrganizNoLastLiv);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return abilOrganizNolastLivTB;
    }

    public UsrVAbilOrganizTableBean getUsrVAbilOrganizLastLevelTableBean(long idUserIam, BigDecimal idApplic)
            throws EMFError {
        List<UsrVAbilOrganiz> dichAutorList = amministrazioneUtentiHelper.getUsrVAbilOrganizLastLevelList(idUserIam,
                idApplic);
        UsrVAbilOrganizTableBean dichsTB = new UsrVAbilOrganizTableBean();
        try {
            if (dichAutorList != null && !dichAutorList.isEmpty()) {
                dichsTB = (UsrVAbilOrganizTableBean) Transform.entities2TableBean(dichAutorList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dichsTB;
    }

    public UsrVAbilOrganizTableBean getOrganizPerRuoliSpecificiUnaOrg(long idUserIam, BigDecimal idApplic,
            String dlPathIdOrganizIam) throws EMFError {
        List<UsrVAbilOrganiz> dichAutorList = amministrazioneUtentiHelper.getUsrVAbilOrganizLastLevelList(idUserIam,
                idApplic, dlPathIdOrganizIam);
        UsrVAbilOrganizTableBean dichsTB = new UsrVAbilOrganizTableBean();
        try {
            if (dichAutorList != null && !dichAutorList.isEmpty()) {
                dichsTB = (UsrVAbilOrganizTableBean) Transform.entities2TableBean(dichAutorList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dichsTB;
    }

    public UsrVTreeOrganizIamTableBean getOrganizIamChilds(BigDecimal idOrganizIam) throws EMFError {
        List<UsrVTreeOrganizIam> dichAutorList = amministrazioneUtentiHelper.getOrganizIamChilds(idOrganizIam);
        UsrVTreeOrganizIamTableBean dichsTB = new UsrVTreeOrganizIamTableBean();
        try {
            if (dichAutorList != null && !dichAutorList.isEmpty()) {
                dichsTB = (UsrVTreeOrganizIamTableBean) Transform.entities2TableBean(dichAutorList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dichsTB;
    }

    /**
     * Metodo che ritorna dato un'organizzazione non di ultimo livello la lista dei suoi figli
     *
     *
     * @param idEntryMenu
     *            id entry menu
     *
     * @return il tableBean contenente i figli dell'organizzazione specificata
     */
    public UsrOrganizIamTableBean getUsrOrganizIamChilds(BigDecimal idEntryMenu) {
        UsrOrganizIamTableBean organizTB = new UsrOrganizIamTableBean();
        List<UsrOrganizIam> organizs = amministrazioneUtentiHelper.getUsrOrganizIamChilds(idEntryMenu);
        try {
            if (organizs != null && !organizs.isEmpty()) {
                organizTB = (UsrOrganizIamTableBean) Transform.entities2TableBean(organizs);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return organizTB;
    }

    /**
     *
     * @param idEntryMenu
     *            id entry menu
     *
     * @return table bean {@link AplEntryMenuTableBean}
     *
     * @throws EMFError
     *             errore generico
     */
    public AplEntryMenuTableBean getEntryMenuParents(BigDecimal idEntryMenu) throws EMFError {
        List<AplEntryMenu> entries = amministrazioneUtentiHelper.getEntryMenuParentsList(idEntryMenu);
        AplEntryMenuTableBean entriesTB = new AplEntryMenuTableBean();
        try {
            if (entries != null && !entries.isEmpty()) {
                entriesTB = (AplEntryMenuTableBean) Transform.entities2TableBean(entries);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entriesTB;
    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base al ruolo
     *
     * @param idUsoRuoloApplic
     *            id ruolo
     *
     * @return il table bean delle applicazioni
     *
     * @throws EMFError
     *             errore generico
     */
    public PrfUsoRuoloApplicTableBean getPrfUsoRuoloApplicTableBean(Set<BigDecimal> idUsoRuoloApplic) throws EMFError {
        PrfUsoRuoloApplicTableBean usoRuoloApplicTableBean = new PrfUsoRuoloApplicTableBean();
        List<PrfUsoRuoloApplic> usoRuoloApplicList = amministrazioneUtentiHelper
                .getPrfUsoRuoloApplicList(idUsoRuoloApplic);
        try {
            for (PrfUsoRuoloApplic usoRuoloApplic : usoRuoloApplicList) {
                PrfUsoRuoloApplicRowBean usoRuoloApplicRowBean = new PrfUsoRuoloApplicRowBean();
                usoRuoloApplicRowBean = (PrfUsoRuoloApplicRowBean) Transform.entity2RowBean(usoRuoloApplic);
                usoRuoloApplicRowBean.setString("nm_applic", usoRuoloApplic.getAplApplic().getNmApplic());
                usoRuoloApplicRowBean.setString("ds_applic", usoRuoloApplic.getAplApplic().getDsApplic());
                usoRuoloApplicTableBean.add(usoRuoloApplicRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return usoRuoloApplicTableBean;
    }

    public AplApplicRowBean getAplApplicRowBean(BigDecimal idApplic) {
        AplApplicRowBean applicRowBean = new AplApplicRowBean();
        AplApplic applic = amministrazioneUtentiHelper.getAplApplicById(idApplic);
        try {
            if (applic != null) {
                applicRowBean = (AplApplicRowBean) Transform.entity2RowBean(applic);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicRowBean;
    }

    public AplApplicRowBean getAplApplicRowBean(String nmApplic) {
        AplApplicRowBean applicRowBean = new AplApplicRowBean();
        AplApplic applic = amministrazioneUtentiHelper.getAplApplicByName(nmApplic);
        try {
            if (applic != null) {
                applicRowBean = (AplApplicRowBean) Transform.entity2RowBean(applic);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return applicRowBean;
    }

    public PrfDichAutorRowBean getPrfDichAutorRowBean(String tiScopoDichAutor, String tiDichAutor, BigDecimal idObject,
            BigDecimal idObject2, BigDecimal idPrfUsoRuoloApplic) {
        PrfDichAutorRowBean rb = null;
        List<PrfDichAutor> dichs = amministrazioneUtentiHelper.getPrfDichAutor(tiScopoDichAutor, tiDichAutor, idObject,
                idObject2, idPrfUsoRuoloApplic);
        try {
            if (dichs != null && !dichs.isEmpty() && dichs.size() == 1) {
                rb = (PrfDichAutorRowBean) Transform.entity2RowBean(dichs.get(0));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rb;
    }

    /**
     * Metodo che ritorna dato un'entry menu la lista dei suoi figli
     *
     * @param idEntryMenu
     *            id entry menu
     *
     * @return il tableBean contenente i figli dell'entry menu specificata
     */
    @Deprecated
    public AplEntryMenuTableBean getEntryMenuChilds(BigDecimal idEntryMenu) {
        AplEntryMenuTableBean entriesTB = new AplEntryMenuTableBean();
        List<AplEntryMenu> entries = amministrazioneUtentiHelper.getEntryMenuChilds(idEntryMenu);
        try {
            if (entries != null && !entries.isEmpty()) {
                entriesTB = (AplEntryMenuTableBean) Transform.entities2TableBean(entries);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entriesTB;
    }

    /**
     * Metodo che ritorna tutte le azioni di una pagina data come parametro
     *
     * @param idPaginaWeb
     *            l'id della pagina web
     *
     * @return il tableBean contenente la lista di azioni
     */
    public AplAzionePaginaTableBean getActionChilds(BigDecimal idPaginaWeb) {
        AplAzionePaginaTableBean actionsTB = new AplAzionePaginaTableBean();
        List<AplAzionePagina> actions = amministrazioneUtentiHelper.getActionChilds(idPaginaWeb);
        try {
            if (actions != null && !actions.isEmpty()) {
                actionsTB = (AplAzionePaginaTableBean) Transform.entities2TableBean(actions);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return actionsTB;
    }

    /**
     * Metodo che ritorna la lista di azioni dato l'idApplicazione
     *
     * @param idApplicazione
     *            id applicazione
     * @param idPaginaWeb
     *            id pagina
     *
     * @return il tableBean della lista di azioni
     */
    public AplAzionePaginaTableBean getActionsList(BigDecimal idApplicazione, BigDecimal idPaginaWeb) {
        AplAzionePaginaTableBean actionsTb = new AplAzionePaginaTableBean();
        List<AplAzionePagina> actions = amministrazioneUtentiHelper.getActionsList(idApplicazione, idPaginaWeb);
        try {
            if (actions != null && !actions.isEmpty()) {
                actionsTb = (AplAzionePaginaTableBean) Transform.entities2TableBean(actions);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return actionsTb;
    }

    @Deprecated
    public AplEntryMenuTableBean getEntryMenuList(BigDecimal idApplicazione, boolean ricercaFoglie) {
        AplEntryMenuTableBean entriesTB = new AplEntryMenuTableBean();
        List<AplEntryMenu> entries = amministrazioneUtentiHelper.getEntryMenuList(idApplicazione, ricercaFoglie);
        try {
            if (entries != null && !entries.isEmpty()) {
                entriesTB = (AplEntryMenuTableBean) Transform.entities2TableBean(entries);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entriesTB;
    }

    public AplPaginaWebTableBean getPagesList(BigDecimal idApplicazione) {
        AplPaginaWebTableBean pagesTb = new AplPaginaWebTableBean();
        List<AplPaginaWeb> pages = amministrazioneUtentiHelper.getPagesList(idApplicazione);
        try {
            if (pages != null && !pages.isEmpty()) {
                pagesTb = (AplPaginaWebTableBean) Transform.entities2TableBean(pages);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return pagesTb;
    }

    public AplServizioWebTableBean getServicesList(BigDecimal idApplicazione) {
        AplServizioWebTableBean servicesTb = new AplServizioWebTableBean();
        List<AplServizioWeb> services = amministrazioneUtentiHelper.getServicesList(idApplicazione);
        try {
            if (services != null && !services.isEmpty()) {
                servicesTb = (AplServizioWebTableBean) Transform.entities2TableBean(services);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return servicesTb;
    }

    public String getAplApplicFromAction(BigDecimal idAzionePagina) {
        AplAzionePagina azione = amministrazioneUtentiHelper.getAplAzionePagina(idAzionePagina);
        return azione.getAplPaginaWeb().getAplApplic().getNmApplic();
    }

    public UsrVLisUserTableBean getUsrVLisUserTableBean(FiltriUtenti filtri, Date[] dateValidate, long idUserIamCorr,
            List<BigDecimal> idEntiConvenzionatiAmministratori) throws EMFError {
        UsrVLisUserTableBean userTB = new UsrVLisUserTableBean();
        try {
            // MAC #20499
            // Devo ricavare i filtri "corretti" per ambiente ed ente di appartenenza
            List<BigDecimal> idAmbienteEnteAppartList = filtri.getId_amb_ente_convenz_appart().parse();
            List<BigDecimal> idEnteAppartList = filtri.getId_ente_convenz_appart().parse();
            // Set<BigDecimal> idAmbienteSet = new HashSet<BigDecimal>(idAmbienteEnteAppartList);
            Set<BigDecimal> idEnteSet = new HashSet<BigDecimal>(idEnteAppartList);
            Set<BigDecimal> idEnteAppartDaPassareAllaQuery = new HashSet<>();
            idEnteAppartDaPassareAllaQuery.addAll(idEnteSet);
            // Se ho selezionato degli ambienti, devo mostrare i risultati solo degli enti abilitati
            if (!idAmbienteEnteAppartList.isEmpty()) {
                // Considero ogni ambiente selezionato
                for (BigDecimal idAmbienteEnteConvenzAppart : idAmbienteEnteAppartList) {
                    // Ricavo tutti gli enti abilitati per l'ambiente considerato
                    OrgVRicEnteConvenzTableBean table = entiConvenzionatiEjb.getOrgVRicEnteConvenzTableBean(null,
                            BigDecimal.valueOf(idUserIamCorr), idAmbienteEnteConvenzAppart, null, null, null,
                            new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), null, null, null, null,
                            new ArrayList(), "0", null, null, null, null, null, null, new ArrayList(), null, null, null,
                            null, null, null);
                    Set<BigDecimal> idEnteAppartAbilitati = new HashSet<>();
                    for (OrgVRicEnteConvenzRowBean row : table) {
                        idEnteAppartAbilitati.add(row.getIdEnteConvenz());
                    }
                    // Se nell'online ho selezionato almeno un ente per quell'ambiente, sono a posto
                    // altrimenti se non ne ho selezionati devo utilizzare tutti e soli gli enti ai quali l'utente
                    // corrente è abilitato
                    boolean almenoUnEnteAmbienteSelezionato = false;
                    for (BigDecimal idEnteAppart : idEnteAppartList) {
                        if (idEnteAppartAbilitati.contains(idEnteAppart)) {
                            almenoUnEnteAmbienteSelezionato = true;
                            break;
                        }
                    }
                    // Se ho selezionato almeno un ente sono a posto, altrimenti aggiunto tutti e soli gli enti
                    // abilitati di quell'ambiente
                    if (!almenoUnEnteAmbienteSelezionato) {
                        idEnteAppartDaPassareAllaQuery.addAll(idEnteAppartAbilitati);
                    }
                }
            }
            List<UsrVLisUser> userList = amministrazioneUtentiHelper.getUsrVLisUserList(filtri,
                    idEnteAppartDaPassareAllaQuery, dateValidate, idUserIamCorr, idEntiConvenzionatiAmministratori);

            for (UsrVLisUser user : userList) {
                UsrVLisUserRowBean userRB = new UsrVLisUserRowBean();
                userRB = (UsrVLisUserRowBean) Transform.entity2RowBean(user);
                String emailSec = userRB.getDsEmailSecondaria() != null ? "; " + userRB.getDsEmailSecondaria() : "";
                userRB.setDsEmail(userRB.getDsEmail() + emailSec);
                userTB.add(userRB);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return userTB;
    }

    /**
     * Ritorna il rowBean dell'utente determinato dal suo id
     *
     * @param idUser
     *            l'id dell'utente
     *
     * @return il rowBean contenente i dati utente
     */
    public UsrUserRowBean getUserRowBean(BigDecimal idUser) {
        UsrUserRowBean utenteRB = new UsrUserRowBean();
        UsrUser utente = amministrazioneUtentiHelper.getUsrUser(idUser);
        try {
            if (utente != null) {
                utenteRB = (UsrUserRowBean) Transform.entity2RowBean(utente);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return utenteRB;
    }

    /**
     * Ritorna il rowBean dell'utente determinato dal suo nmUserId
     *
     * @param nmUserId
     *            user id
     *
     * @return il rowBean contenente i dati utente
     */
    public UsrUserRowBean getUserRowBean(String nmUserId) {
        UsrUserRowBean utenteRB = new UsrUserRowBean();
        UsrUser utente = amministrazioneUtentiHelper.getUsrUserByNmUserid(nmUserId);
        try {
            if (utente != null) {
                utenteRB = (UsrUserRowBean) Transform.entity2RowBean(utente);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return utenteRB;
    }

    public UsrIndIpUserTableBean getUsrIndIpUserTableBean(BigDecimal idUserIam) throws EMFError {
        UsrIndIpUserTableBean uiiaTableBean = new UsrIndIpUserTableBean();
        List<UsrIndIpUser> uiiaList = amministrazioneUtentiHelper.getUsrIndIpUserList(idUserIam);
        try {
            for (UsrIndIpUser uiia : uiiaList) {
                UsrIndIpUserRowBean uiiaRowBean = new UsrIndIpUserRowBean();
                uiiaRowBean = (UsrIndIpUserRowBean) Transform.entity2RowBean(uiia);
                uiiaTableBean.add(uiiaRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return uiiaTableBean;
    }

    public UsrUsoUserApplicTableBean getUsoUsoUserApplicTableBean(BigDecimal idUserIam) throws EMFError {
        UsrUsoUserApplicTableBean uuuaTableBean = new UsrUsoUserApplicTableBean();
        List<UsrUsoUserApplic> uuuaList = amministrazioneUtentiHelper.getUsrUsoUserApplicList(idUserIam);
        try {
            for (UsrUsoUserApplic uuua : uuuaList) {
                UsrUsoUserApplicRowBean uuuaRowBean = new UsrUsoUserApplicRowBean();
                uuuaRowBean.setBigDecimal("id_applic", new BigDecimal(uuua.getAplApplic().getIdApplic()));
                uuuaRowBean.setString("nm_applic", uuua.getAplApplic().getNmApplic());
                uuuaRowBean.setString("ds_applic", uuua.getAplApplic().getDsApplic());
                uuuaRowBean.setBigDecimal("id_uso_user_applic", new BigDecimal(uuua.getIdUsoUserApplic()));
                uuuaTableBean.add(uuuaRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return uuuaTableBean;
    }

    public UsrUsoRuoloUserDefaultTableBean getUsrUsoRuoloUserDefaultTableBean(Set<BigDecimal> uuuaSet) throws EMFError {
        UsrUsoRuoloUserDefaultTableBean uurudTableBean = new UsrUsoRuoloUserDefaultTableBean();
        List<UsrUsoRuoloUserDefault> uurudList = amministrazioneUtentiHelper.getUsrUsoRuoloUserDefaultList(uuuaSet);
        try {
            for (UsrUsoRuoloUserDefault uurud : uurudList) {
                UsrUsoRuoloUserDefaultRowBean uurudRowBean = new UsrUsoRuoloUserDefaultRowBean();
                uurudRowBean = (UsrUsoRuoloUserDefaultRowBean) Transform.entity2RowBean(uurud);
                uurudRowBean.setBigDecimal("id_applic",
                        new BigDecimal(uurud.getUsrUsoUserApplic().getAplApplic().getIdApplic()));
                uurudRowBean.setString("nm_applic", uurud.getUsrUsoUserApplic().getAplApplic().getNmApplic());
                uurudRowBean.setString("nm_ruolo", uurud.getPrfRuolo().getNmRuolo());
                uurudRowBean.setString("ds_ruolo", uurud.getPrfRuolo().getDsRuolo());
                uurudRowBean.setString("ti_ruolo", uurud.getPrfRuolo().getTiRuolo());
                uurudTableBean.add(uurudRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return uurudTableBean;
    }

    public UsrDichAbilOrganizTableBean getUsrDichAbilOrganizTableBean(Set<BigDecimal> uuuaSet) throws EMFError {
        UsrDichAbilOrganizTableBean udaoTableBean = new UsrDichAbilOrganizTableBean();
        List<UsrDichAbilOrganiz> udaoList = amministrazioneUtentiHelper.getUsrDichAbilOrganizList(uuuaSet);
        try {
            for (UsrDichAbilOrganiz udao : udaoList) {
                UsrDichAbilOrganizRowBean udaoRowBean = new UsrDichAbilOrganizRowBean();
                udaoRowBean = (UsrDichAbilOrganizRowBean) Transform.entity2RowBean(udao);
                udaoRowBean.setBigDecimal("id_applic",
                        new BigDecimal(udao.getUsrUsoUserApplic().getAplApplic().getIdApplic()));
                udaoRowBean.setString("nm_applic", udao.getUsrUsoUserApplic().getAplApplic().getNmApplic());
                UsrVTreeOrganizIam tree = amministrazioneUtentiHelper
                        .getUsrVTreeOrganizIam(udaoRowBean.getIdOrganizIam());
                udaoRowBean.setString("dl_composito_organiz", tree.getDlCompositoOrganiz());
                udaoRowBean.setString("dl_path_id_organiz_iam", tree.getDlPathIdOrganizIam());
                String nmRuoloDich;
                if (udao.getUsrUsoRuoloDiches().isEmpty()) {
                    nmRuoloDich = "Nessun ruolo definito";
                } else if (udao.getUsrUsoRuoloDiches().size() == 1) {
                    nmRuoloDich = udao.getUsrUsoRuoloDiches().get(0).getPrfRuolo().getNmRuolo();
                } else {
                    nmRuoloDich = "Autorizzato con più di un ruolo";
                }
                udaoRowBean.setString("nm_ruolo_dich", nmRuoloDich);
                udaoTableBean.add(udaoRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return udaoTableBean;
    }

    public UsrDichAbilDatiTableBean getUsrDichAbilDatiTableBean(Set<BigDecimal> uuuaSet) throws EMFError {
        UsrDichAbilDatiTableBean udadTableBean = new UsrDichAbilDatiTableBean();
        List<UsrDichAbilDati> udadList = amministrazioneUtentiHelper.getUsrDichAbilDatiList(uuuaSet);
        try {
            for (UsrDichAbilDati udad : udadList) {
                UsrDichAbilDatiRowBean udadRowBean = new UsrDichAbilDatiRowBean();
                udadRowBean = (UsrDichAbilDatiRowBean) Transform.entity2RowBean(udad);
                udadRowBean.setBigDecimal("id_applic",
                        new BigDecimal(udad.getUsrUsoUserApplic().getAplApplic().getIdApplic()));
                udadRowBean.setString("nm_applic", udad.getUsrUsoUserApplic().getAplApplic().getNmApplic());
                udadRowBean.setString("nm_classe_tipo_dato", udad.getAplClasseTipoDato().getNmClasseTipoDato());
                if (udad.getUsrOrganizIam() != null) {
                    UsrVTreeOrganizIam tree = amministrazioneUtentiHelper
                            .getUsrVTreeOrganizIam(new BigDecimal(udad.getUsrOrganizIam().getIdOrganizIam()));
                    udadRowBean.setIdOrganizIam(new BigDecimal(udad.getUsrOrganizIam().getIdOrganizIam()));
                    udadRowBean.setString("dl_composito_organiz", tree.getDlCompositoOrganiz());
                }
                if (udad.getUsrTipoDatoIam() != null) {
                    udadRowBean.setString("nm_tipo_dato", udad.getUsrTipoDatoIam().getNmTipoDato());
                }
                udadTableBean.add(udadRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return udadTableBean;
    }

    public UsrDichAbilEnteConvenzTableBean getUsrDichAbilEnteConvenzTableBean(BigDecimal idUserIam) throws EMFError {
        UsrDichAbilEnteConvenzTableBean daecTableBean = new UsrDichAbilEnteConvenzTableBean();
        List<UsrDichAbilEnteConvenz> daecList = amministrazioneUtentiHelper.getUsrDichAbilEnteConvenz(idUserIam);
        try {
            for (UsrDichAbilEnteConvenz daec : daecList) {
                UsrDichAbilEnteConvenzRowBean daecRowBean = new UsrDichAbilEnteConvenzRowBean();
                daecRowBean = (UsrDichAbilEnteConvenzRowBean) Transform.entity2RowBean(daec);
                daecRowBean.setString("nm_ambiente_ente_convenz", daec.getOrgAmbienteEnteConvenz() != null
                        ? daec.getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz() : null);
                daecRowBean.setString("nm_ente_convenz",
                        daec.getOrgEnteSiam() != null ? daec.getOrgEnteSiam().getNmEnteSiam() : null);
                daecTableBean.add(daecRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return daecTableBean;
    }

    public UsrDichAbilEnteConvenzTableBean getUsrDichAbilEnteConvenzUnEnteAttiviTableBean(BigDecimal idEnteConvenz)
            throws EMFError {
        UsrDichAbilEnteConvenzTableBean daecTableBean = new UsrDichAbilEnteConvenzTableBean();
        List<UsrDichAbilEnteConvenz> daecList = amministrazioneUtentiHelper
                .getUsrDichAbilEnteConvenzUnEnteAttivi(idEnteConvenz);
        try {
            for (UsrDichAbilEnteConvenz daec : daecList) {
                UsrDichAbilEnteConvenzRowBean daecRowBean = new UsrDichAbilEnteConvenzRowBean();
                daecRowBean = (UsrDichAbilEnteConvenzRowBean) Transform.entity2RowBean(daec);
                daecTableBean.add(daecRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return daecTableBean;
    }

    public UsrVLisStatoUserTableBean getUsrStatoUserTableBean(BigDecimal idUserIam) throws EMFError {
        UsrVLisStatoUserTableBean statoUserTableBean = new UsrVLisStatoUserTableBean();
        List<UsrVLisStatoUser> statoUserList = amministrazioneUtentiHelper.getUsrVLisStatoUserList(idUserIam);
        try {
            if (statoUserList != null && !statoUserList.isEmpty()) {
                statoUserTableBean = (UsrVLisStatoUserTableBean) Transform.entities2TableBean(statoUserList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return statoUserTableBean;
    }

    public AplClasseTipoDatoTableBean getAplClasseTipoDatoTableBean(BigDecimal idApplic) throws EMFError {
        AplClasseTipoDatoTableBean classeTipoDatoTableBean = new AplClasseTipoDatoTableBean();
        List<AplClasseTipoDato> listaClasse = amministrazioneUtentiHelper.getAplClasseTipoDatoList(idApplic);
        try {
            if (listaClasse != null && !listaClasse.isEmpty()) {
                classeTipoDatoTableBean = (AplClasseTipoDatoTableBean) Transform.entities2TableBean(listaClasse);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return classeTipoDatoTableBean;
    }

    public UsrVAbilDatiTableBean getUsrVAbilDatiTableBean(long idUserIam, String nmClasseTipoDato, BigDecimal idApplic,
            BigDecimal idOrganizIam) {
        UsrVAbilDatiTableBean usrTipoDatoIamTableBean = new UsrVAbilDatiTableBean();
        List<UsrVAbilDati> listaTipoDato = amministrazioneUtentiHelper.getUsrVAbilDatiList(idUserIam, nmClasseTipoDato,
                idApplic, idOrganizIam);
        try {
            if (listaTipoDato != null && !listaTipoDato.isEmpty()) {
                usrTipoDatoIamTableBean = (UsrVAbilDatiTableBean) Transform.entities2TableBean(listaTipoDato);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return usrTipoDatoIamTableBean;
    }

    public UsrTipoDatoIamTableBean getUsrTipoDatoIamTableBean(BigDecimal idApplic, String nmClasseTipoDato,
            BigDecimal idOrganizIam) {
        UsrTipoDatoIamTableBean usrTipoDatoIamTableBean = new UsrTipoDatoIamTableBean();
        List<UsrTipoDatoIam> listaTipoDato = amministrazioneUtentiHelper.getUsrTipoDatoIamList(idApplic,
                nmClasseTipoDato, idOrganizIam);
        try {
            if (listaTipoDato != null && !listaTipoDato.isEmpty()) {
                usrTipoDatoIamTableBean = (UsrTipoDatoIamTableBean) Transform.entities2TableBean(listaTipoDato);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return usrTipoDatoIamTableBean;
    }

    public boolean checkUserExists(String userName) {
        return amministrazioneUtentiHelper.checkUserExists(userName);
    }

    public boolean checkUserExists(String nmUserId, String nmCognomeUser, String nmNomeUser, String cdFisc,
            String dsEmail, String flContrIp) {
        return amministrazioneUtentiHelper.checkUserExists(nmUserId, nmCognomeUser, nmNomeUser, cdFisc, dsEmail,
                flContrIp);
    }

    public boolean hasServicesAuthorization(BigDecimal idRuolo, BigDecimal idApplic) {
        return amministrazioneUtentiHelper.hasServicesAuthorization(idRuolo, idApplic);
    }

    public boolean checkModificheCampiUtente(String nmUserid, String nmCognomeUser, String nmNomeUser, String flAttivo,
            String cdFisc, String dsEmail, String dsEmailSecondaria, String flContrIp, String tipoUser) {
        UsrUser userDB = amministrazioneUtentiHelper.getUsrUserByNmUserid(nmUserid);
        return !StringUtils.equals(userDB.getNmCognomeUser(), nmCognomeUser)
                || !StringUtils.equals(userDB.getNmNomeUser(), nmNomeUser)
                || !StringUtils.equals(userDB.getFlAttivo(), flAttivo)
                || !StringUtils.equals(userDB.getCdFisc(), cdFisc) || !StringUtils.equals(userDB.getDsEmail(), dsEmail)
                || !StringUtils.equals(userDB.getDsEmailSecondaria(), dsEmailSecondaria)
                || !StringUtils.equals(userDB.getFlContrIp(), flContrIp);
        // || !StringUtils.equals(userDB.getTipoUser(), tipoUser);
    }

    public String[] getPadri(BigDecimal idOrganizIam) {
        UsrVTreeOrganizIam user = amministrazioneUtentiHelper.getUsrVTreeOrganizIam(idOrganizIam);
        String[] padri = null;
        if (user != null) {
            padri = user.getDlPathIdOrganizIam().split("/");
        }
        return padri;
    }

    public BigDecimal getPadre(BigDecimal idOrganizIam) {
        UsrOrganizIam organizIam = amministrazioneUtentiHelper.findById(UsrOrganizIam.class, idOrganizIam);
        if (organizIam.getUsrOrganizIam() != null) {
            return BigDecimal.valueOf(organizIam.getUsrOrganizIam().getIdOrganizIam());
        }
        return null;
    }

    /**
     * Ritorna la lista di dichiarazione dei ruoli per le organizzazioni
     *
     * @param idDichAbilOrganiz
     *            l'id della dichiarazione di abilitazione in organizzazione
     *
     * @return la lista dei ruoli per quella dichiarazione
     */
    public PrfRuoloTableBean getRuoliAbilOrganiz(BigDecimal idDichAbilOrganiz) {
        List<Object[]> ruoliList = amministrazioneUtentiHelper.getRuoliAbilOrganizList(idDichAbilOrganiz);
        PrfRuoloTableBean ruoliTB = new PrfRuoloTableBean();
        try {
            if (ruoliList != null && !ruoliList.isEmpty()) {
                PrfRuoloRowBean ruoloRB = new PrfRuoloRowBean();
                for (Object[] ruolo : ruoliList) {
                    ruoloRB = (PrfRuoloRowBean) Transform.entity2RowBean((PrfRuolo) ruolo[0]);
                    ruoloRB.setBigDecimal("id_uso_ruolo_dich", new BigDecimal((Long) ruolo[1]));
                    ruoloRB.setBigDecimal("id_dich_abil_organiz", idDichAbilOrganiz);
                    ruoliTB.add(ruoloRB);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliTB;
    }

    /**
     * Ritorna la lista di dichiarazione dei ruoli specifici relativi alla dichiarazione di abilitazioni alle
     * organizzazioni passata in ingresso
     *
     * @param idDichAbilOrganiz
     *            l'id della dichiarazione di abilitazione in organizzazione
     *
     * @return la lista dei ruoli per quella dichiarazione
     */
    public UsrVLisUsoRuoloDichTableBean getUsrVLisUsoRuoloDichTableBean(BigDecimal idDichAbilOrganiz) {
        List<UsrVLisUsoRuoloDich> ruoliList = amministrazioneUtentiHelper.getUsrVLisUsoRuoloDichList(idDichAbilOrganiz);
        UsrVLisUsoRuoloDichTableBean ruoliTB = new UsrVLisUsoRuoloDichTableBean();
        try {
            if (ruoliList != null && !ruoliList.isEmpty()) {
                ruoliTB = (UsrVLisUsoRuoloDichTableBean) Transform.entities2TableBean(ruoliList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ruoliTB;
    }

    public UsrVVisDichAbilRowBean getUsrVVisDichAbilRowBean(BigDecimal idDichAbilOrg) {
        UsrVVisDichAbilRowBean dichAbilRowBean = new UsrVVisDichAbilRowBean();
        UsrVVisDichAbil dichAbil = amministrazioneUtentiHelper.getUsrVVisDichAbil(idDichAbilOrg);
        try {
            if (dichAbil != null) {
                dichAbilRowBean = (UsrVVisDichAbilRowBean) Transform.entity2RowBean(dichAbil);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dichAbilRowBean;
    }

    public UsrVVisLastSchedRowBean getUsrVVisLastSchedRowBean(ApplEnum.NmJob nmJob) {
        UsrVVisLastSchedRowBean lastSchedRowBean = new UsrVVisLastSchedRowBean();
        UsrVVisLastSched lastSched = amministrazioneUtentiHelper.getUsrVVisLastSched(nmJob);
        try {
            if (lastSched != null) {
                lastSchedRowBean = (UsrVVisLastSchedRowBean) Transform.entity2RowBean(lastSched);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return lastSchedRowBean;
    }

    public int sbloccaReplicheBloccate(ApplEnum.NmJob nmJob, boolean soloSbloccoRepliche) throws ParerInternalError {
        Date fine = DateUtil.getDataInfinita();
        Date inizio = DateUtil.get3MesiAntecedentiDataOdierna();
        Date[] dateValidate = new Date[2];
        dateValidate[0] = inizio;
        dateValidate[1] = fine;
        List<UsrVLisSched> listaSched = amministrazioneUtentiHelper.getUsrVLisSchedList(nmJob, dateValidate,
                soloSbloccoRepliche);
        int replicheAggiornate = 0;
        try {
            for (UsrVLisSched sched : listaSched) {
                if (sched.getTiBlocco() != null) {
                    if (sched.getTiBlocco().equals("SENZA_FINE_SCHEDULAZIONE")) {
                        replicheAggiornate += aggiornaReplicheInCorso(sched.getIdLogJob(), true);
                    } else if (sched.getTiBlocco().equals("ERRORE")) {
                        replicheAggiornate += aggiornaReplicheInCorso(sched.getIdLogJob(), false);
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Errore inaspettato in fase di aggiornamento repliche in corso :"
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerInternalError(ex);

        }
        return replicheAggiornate;
    }

    public UsrVLisSchedTableBean getUsrVLisSchedTableBean(ApplEnum.NmJob nmJob, Date[] dateValidate,
            boolean soloSbloccoRepliche) throws EMFError {
        UsrVLisSchedTableBean schedTableBean = new UsrVLisSchedTableBean();
        List<UsrVLisSched> listaSched = amministrazioneUtentiHelper.getUsrVLisSchedList(nmJob, dateValidate,
                soloSbloccoRepliche);

        try {
            if (listaSched != null && !listaSched.isEmpty()) {
                schedTableBean = (UsrVLisSchedTableBean) Transform.entities2TableBean(listaSched);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        /* Creo un nuovo campo concatenandone altri già esistenti */
        for (int i = 0; i < schedTableBean.size(); i++) {
            UsrVLisSchedRowBean row = schedTableBean.getRow(i);
            if (row.getDtRegLogJobFine() != null) {
                String durata = row.getDurataGg() + "-" + row.getDurataOre() + ":" + row.getDurataMin() + ":"
                        + row.getDurataSec();
                row.setString("durata", durata);
            }
            if (row.getTiBlocco() != null
                    && (row.getDlMsgErr() == null || !row.getDlMsgErr().equals(SBLOCCO_REPLICHE_ERR))) {
                row.setString("fl_async_locked", "1");
                row.setString("cleanAsyncLocked", "Sblocca replica");
            } else {
                row.setString("fl_async_locked", "0");
                row.setString("cleanAsyncLocked", null);
            }
        }
        return schedTableBean;
    }

    public UsrVLisUserReplicTableBean getUsrVLisUserReplicTableBean(FiltriReplica filtri, Date dataValidata,
            boolean isFromDettaglioUtente) throws EMFError {
        UsrVLisUserReplicTableBean replicTableBean = new UsrVLisUserReplicTableBean();
        List<UsrVLisUserReplic> listaSched = amministrazioneUtentiHelper.getUsrVLisUserReplicList(filtri, dataValidata,
                isFromDettaglioUtente);

        try {
            if (listaSched != null && !listaSched.isEmpty()) {
                replicTableBean = (UsrVLisUserReplicTableBean) Transform.entities2TableBean(listaSched);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        /* Creo un nuovo campo concatenandone altri già esistenti */
        for (int i = 0; i < replicTableBean.size(); i++) {
            UsrVLisUserReplicRowBean row = replicTableBean.getRow(i);
            if (row.getCdErr() != null && row.getDsMsgErr() != null && row.getDtErr() != null) {
                String errore = row.getCdErr() + "-" + row.getDsMsgErr() + " del " + row.getDtErr();
                row.setString("errore", errore);
            }
        }

        return replicTableBean;
    }

    public Boolean hasErroriReplica(BigDecimal idUserIam) {
        return amministrazioneUtentiHelper.hasErroriReplica(idUserIam);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void attivaDisattivaUtente(LogParam param, BigDecimal idUserIam, boolean attiva) throws ParerUserError {
        try {
            // Attiva/Disattiva l'utente
            UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, idUserIam);
            user.setFlAttivo(attiva ? "1" : "0");

            /*
             * Se sto riattivando l'utente e la password e' scaduta da piu di tre mesi da oggi l'aggiorno per evitare
             * che venga disattivato nuovamente alla prossima replica
             */
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -3);
            if (attiva && user.getDtScadPsw().before(cal.getTime())) {
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                user.setDtScadPsw(cal.getTime());
                user.setDtRegPsw(Calendar.getInstance().getTime());
            }

            // Se è stato indicato il sistema versante, gestisco l'aggiornamento dei servizi erogati
            if (user.getAplSistemaVersante() != null) {
                authEjb.manageUltimoAccordoEntiConvenzionati(idUserIam);
            }
            /*
             * Codice aggiuntivo per il logging...
             */
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_UTENTE,
                    new BigDecimal(user.getIdUserIam()), param.getNomePagina());

        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nel salvataggio dell'utente ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            log.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String attivaUtente(LogParam param, BigDecimal idUserIam) throws ParerUserError {
        // Controllo che all'utente sia associata una richiesta in stato NON_EVASA in cui sia referenziata una richiesta
        // di riattivazione
        UsrAppartUserRich richiestaDaEvadere = amministrazioneUtentiHelper.getRichiestaDaEvadere(idUserIam,
                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RIATTIVZIONE.getDescrizione());
        if (richiestaDaEvadere == null) {
            throw new ParerUserError(
                    "Attenzione: richiesta di riattivazione non presente, non \u00E8 possibile procedere");
        }
        String resetPwd;
        try {
            // Inserisco un record nella tabella USR_STATO_USER
            UsrStatoUser statoUser = new UsrStatoUser();
            UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, idUserIam);
            statoUser.setUsrUser(user);
            statoUser.setTsStato(new Timestamp(new Date().getTime()));
            statoUser.setTiStatoUser(ConstUsrStatoUser.TiStatotUser.ATTIVO.name());
            statoUser.setUsrRichGestUser(richiestaDaEvadere.getUsrRichGestUser());
            amministrazioneUtentiHelper.insertEntity(statoUser, true);
            user.setIdStatoUserCor(BigDecimal.valueOf(statoUser.getIdStatoUser()));

            user.setFlAttivo("1");

            // Resetto la password (All'interno della logica eseguo il log dell'utente)
            resetPwd = authEjb.resetPwd(param, idUserIam);
            /* registra l'evento di riattivazione nella tabella LOG_EVENTO_LOGIN_USER */
            LogDto logDto = new LogDto();
            logDto.setNmAttore(ConstLogEventoLoginUser.NM_ATTORE_ONLINE);
            logDto.setNmUser(user.getNmUserid());
            // FORSE INUTILE
            // tmpLogDto.setCdIndIpClient(indirizzoIP);
            //
            logDto.setTsEvento(new Date());
            logDto.setTipoEvento(LogDto.TipiEvento.SET_PSW);
            logDto.setDsEvento(ConstLogEventoLoginUser.DS_EVENTO_RIATTIVA_USER);

            idpLogger.scriviLog(logDto);

            /* Per ogni applicazione dell'utente, registra nel log degli utenti da replicare */
            userHelper.registraLogUserDaReplic(user.getNmUserid(), ApplEnum.TiOperReplic.MOD);
            // Setto la richiesta come evasa
            richiestaDaEvadere.setFlAzioneRichEvasa("1");
            amministrazioneUtentiHelper.getEntityManager().flush();
            if (!amministrazioneUtentiHelper.existAzioni(richiestaDaEvadere.getUsrRichGestUser().getIdRichGestUser(),
                    "0")) {
                richiestaDaEvadere.getUsrRichGestUser()
                        .setTiStatoRichGestUser(ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name());
            }
            amministrazioneUtentiHelper.getEntityManager().flush();
            /*
             * Codice aggiuntivo per il logging...
             */
            // sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
            // param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_UTENTE, new
            // BigDecimal(user.getIdUserIam()), param.getNomePagina());
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA,
                    BigDecimal.valueOf(richiestaDaEvadere.getUsrRichGestUser().getIdRichGestUser()),
                    param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nel salvataggio dell'utente ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            log.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
        return resetPwd;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void disattivaUtente(LogParam param, BigDecimal idUserIam) throws ParerUserError {
        // Controllo che all'utente sia associata una richiesta in stato NON_EVASA
        // in cui sia referenziata una richiesta di disattivazione
        UsrAppartUserRich richiestaDaEvadere = amministrazioneUtentiHelper.getRichiestaDaEvadere(idUserIam,
                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_DISATTIVAZIONE.getDescrizione());
        if (richiestaDaEvadere == null) {
            throw new ParerUserError(
                    "Attenzione: richiesta di disattivazione non presente, non \u00E8 possibile procedere");
        }
        try {
            // Inserisco un record nella tabella USR_STATO_USER
            UsrStatoUser statoUser = new UsrStatoUser();
            UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, idUserIam);
            statoUser.setUsrUser(user);
            statoUser.setTsStato(new Timestamp(new Date().getTime()));
            statoUser.setTiStatoUser(ConstUsrStatoUser.TiStatotUser.DISATTIVO.name());
            statoUser.setUsrRichGestUser(richiestaDaEvadere.getUsrRichGestUser());
            amministrazioneUtentiHelper.insertEntity(statoUser, true);
            user.setIdStatoUserCor(BigDecimal.valueOf(statoUser.getIdStatoUser()));

            user.setFlAttivo("0");

            if (!user.getTipoUser().equals("NON_DI_SISTEMA")) {
                /* Per ogni applicazione dell'utente, registra nel log degli utenti da replicare */
                userHelper.registraLogUserDaReplic(user.getNmUserid(), ApplEnum.TiOperReplic.MOD);
            }
            // Setto la richiesta come evasa
            richiestaDaEvadere.setFlAzioneRichEvasa("1");
            amministrazioneUtentiHelper.getEntityManager().flush();
            if (!amministrazioneUtentiHelper.existAzioni(richiestaDaEvadere.getUsrRichGestUser().getIdRichGestUser(),
                    "0")) {
                richiestaDaEvadere.getUsrRichGestUser()
                        .setTiStatoRichGestUser(ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name());
            }
            amministrazioneUtentiHelper.getEntityManager().flush();
            /*
             * Codice aggiuntivo per il logging...
             */

            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA,
                    BigDecimal.valueOf(richiestaDaEvadere.getUsrRichGestUser().getIdRichGestUser()),
                    param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nel salvataggio dell'utente ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            log.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }

    }

    private static final String SBLOCCO_REPLICHE_ERR = "Job terminato forzatamente per sblocco repliche in corso non eseguite entro tempo limite";

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int aggiornaReplicheInCorso(BigDecimal idLogJob, boolean writeLog) {
        int replicheAggiornate = amministrazioneUtentiHelper.aggiornaReplicheInCorso(idLogJob);
        if (writeLog) {
            LogJob logJob = amministrazioneUtentiHelper.findById(LogJob.class, idLogJob);
            jobLogger.writeLog(logJob.getNmJob(), Constants.TipiRegLogJob.ERRORE, SBLOCCO_REPLICHE_ERR);
        }
        return replicheAggiornate;
    }

    /**
     * Verifica se nella lista di abilitazioni organizzazioni sono già presenti dichiarazioni gerarchicamente 'figlie'
     * dell'organizzazione non di ultimo livello passata come parametro
     *
     *
     * @param idOrganizIam
     *            id organizzazione
     * @param applicazione
     *            lista elementi di tipo {@link PairAuth}
     *
     * @return true se la lista contiene già delle dichiarazioni
     */
    public boolean checkDichAutorSetOrganizForChilds(BigDecimal idOrganizIam, Set<PairAuth> applicazione) {
        boolean result = false;
        UsrOrganizIamTableBean figli = getUsrOrganizIamChilds(idOrganizIam);
        if (!figli.isEmpty()) {
            for (UsrOrganizIamRowBean row : figli) {
                if (applicazione.contains(
                        new PairAuth(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG_CHILD.name(), row.getIdOrganizIam()))) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public boolean checkDichAutorSetOrganizUnaOrgForChilds(BigDecimal idOrganizIam, Set<PairAuth> applicazione) {
        boolean result = false;
        UsrOrganizIamTableBean figli = getUsrOrganizIamChilds(idOrganizIam);
        if (!figli.isEmpty()) {
            for (UsrOrganizIamRowBean row : figli) {
                if (applicazione.contains(
                        new PairAuth(ActionEnums.ScopoDichAbilOrganiz.ORG_DEFAULT.name(), row.getIdOrganizIam()))
                        || applicazione.contains(
                                new PairAuth(ActionEnums.ScopoDichAbilOrganiz.UNA_ORG.name(), row.getIdOrganizIam()))) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Verifica se nella lista di abilitazioni organizzazioni sono già presenti dichiarazioni gerarchicamente 'figlie'
     * dell'organizzazione non di ultimo livello passata come parametro
     *
     *
     * @param idOrganizIam
     *            id organizzazione
     * @param idClasseTipoDato
     *            id classe tipo dato
     * @param applicazione
     *            lista elementi di tipo {@link PairAuth}
     *
     * @return true se la lista contiene già delle dichiarazioni
     */
    public boolean checkDichAutorTipiDatoSetOrganizForChilds(BigDecimal idOrganizIam, BigDecimal idClasseTipoDato,
            Set<PairAuth> applicazione) {
        boolean result = false;
        UsrOrganizIamTableBean figli = getUsrOrganizIamChilds(idOrganizIam);
        if (!figli.isEmpty()) {
            for (UsrOrganizIamRowBean row : figli) {
                if (applicazione.contains(new PairAuth(idClasseTipoDato,
                        new PairAuth(ActionEnums.ScopoDichAbilOrganiz.ALL_ORG_CHILD.name(), row.getIdOrganizIam())))) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public boolean checkDichAutorTipiDatoSetOrganizUnaOrgForChilds(BigDecimal idOrganizIam, BigDecimal idClasseTipoDato,
            Set<PairAuth> applicazione) {
        boolean result = false;
        UsrOrganizIamTableBean figli = getUsrOrganizIamChilds(idOrganizIam);
        if (!figli.isEmpty()) {
            for (UsrOrganizIamRowBean row : figli) {
                if (applicazione.contains(new PairAuth(idClasseTipoDato,
                        new PairAuth(ActionEnums.ScopoDichAbilOrganiz.UNA_ORG.name(), row.getIdOrganizIam())))) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public boolean existsDichAnyScopo(Set<PairAuth> scopoSet, BigDecimal idClasseTipoDato) {
        boolean result = false;
        for (PairAuth row : scopoSet) {
            BigDecimal idClasseTipoDatoScopoSet = (BigDecimal) row.getAppl();
            if (idClasseTipoDatoScopoSet.compareTo(idClasseTipoDato) == 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    // public LogEventoLoginUserTableBean getLogEventoLoginUserTableBean(Date dtEventoDa, Date dtEventoA, String
    // nmUserid,
    // String tipoEvento, String inclUtentiAutomi, String inclLoginOK) throws ParerUserError {
    // LogEventoLoginUserTableBean loginEventiTB = new LogEventoLoginUserTableBean();
    // if (inclLoginOK.equals("0") && tipoEvento != null
    // && tipoEvento.equals(ConstLogEventoLoginUser.TipoEvento.LOGIN_OK.name())) {
    // throw new ParerUserError(
    // "Attenzione: è stato selezionato di visualizzare i login di tipo LOGIN_OK e al tempo stesso di escluderli dalla
    // ricerca (flag includi login OK) ");
    // }
    //
    // // Preparo il filtro di ricerca per il tipo evento
    // Set<String> tipiEventoSet = new HashSet<String>();
    // // Se ho selezionato un tipo evento specifico, inserisco quello ed eventualmente il LOGIN_OK
    // if (StringUtils.isNotBlank(tipoEvento)) {
    // tipiEventoSet.add(tipoEvento);
    // if (inclLoginOK.equals("1")) {
    // tipiEventoSet.add(ConstLogEventoLoginUser.TipoEvento.LOGIN_OK.name());
    // }
    // } // Se invece non ne ho selezionato nessuno, prendo tutti i tipi possibili, eventualmente escludendo il
    // // LOGIN_OK
    // else {
    // tipiEventoSet = new HashSet(Arrays.stream(ConstLogEventoLoginUser.TipoEvento.values()).map(t -> t.name())
    // .collect(Collectors.toList()));
    // if (inclLoginOK.equals("0")) {
    // tipiEventoSet.remove(ConstLogEventoLoginUser.TipoEvento.LOGIN_OK);
    // }
    // }
    //
    // List<LogEventoLoginUser> loginEventiList = amministrazioneUtentiHelper
    // .retrieveLogEventoLoginUserList(dtEventoDa, dtEventoA, nmUserid, tipiEventoSet, inclUtentiAutomi);
    // try {
    // if (loginEventiList != null && !loginEventiList.isEmpty()) {
    // loginEventiTB = (LogEventoLoginUserTableBean) Transform.entities2TableBean(loginEventiList);
    // }
    // } catch (Exception e) {
    // log.error(e.getMessage(), e);
    // throw new ParerUserError("Errore inatteso nel caricamento degli accessi");
    // }
    // return loginEventiTB;
    // }

    public LogVRicAccessiTableBean getLogVRicAccessiTableBean(Date dtEventoDa, Date dtEventoA, String nmUserid,
            String tipoEvento, String inclUtentiAutomi, String inclLoginOK) throws ParerUserError {
        LogVRicAccessiTableBean loginEventiTB = new LogVRicAccessiTableBean();
        if (inclLoginOK.equals("0") && tipoEvento != null
                && tipoEvento.equals(ConstLogEventoLoginUser.TipoEvento.LOGIN_OK.name())) {
            throw new ParerUserError(
                    "Attenzione: è stato selezionato di visualizzare i login di tipo LOGIN_OK e al tempo stesso di escluderli dalla ricerca (flag includi login OK) ");
        }

        // Preparo il filtro di ricerca per il tipo evento
        Set<String> tipiEventoSet = new HashSet<String>();
        // Se ho selezionato un tipo evento specifico, inserisco quello ed eventualmente il LOGIN_OK
        if (StringUtils.isNotBlank(tipoEvento)) {
            tipiEventoSet.add(tipoEvento);
            if (inclLoginOK.equals("1")) {
                tipiEventoSet.add(ConstLogEventoLoginUser.TipoEvento.LOGIN_OK.name());
            }
        } // Se invece non ne ho selezionato nessuno, prendo tutti i tipi possibili, eventualmente escludendo il
          // LOGIN_OK
        else {
            tipiEventoSet = new HashSet(Arrays.stream(ConstLogEventoLoginUser.TipoEvento.values()).map(t -> t.name())
                    .collect(Collectors.toList()));
            if (inclLoginOK.equals("0")) {
                tipiEventoSet.remove(ConstLogEventoLoginUser.TipoEvento.LOGIN_OK);
            }
        }

        List<LogVRicAccessi> loginEventiList = amministrazioneUtentiHelper.retrieveLogVRicAccessiList(dtEventoDa,
                dtEventoA, nmUserid, tipiEventoSet, inclUtentiAutomi);
        try {
            if (loginEventiList != null && !loginEventiList.isEmpty()) {
                loginEventiTB = (LogVRicAccessiTableBean) Transform.entities2TableBean(loginEventiList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ParerUserError("Errore inatteso nel caricamento degli accessi");
        }
        return loginEventiTB;
    }

    public UsrVRicRichiesteTableBean getUsrVRicRichiesteTableBean(BigDecimal idUserIamCor, String nmCognomeUserAppRich,
            String nmNomeUserAppRich, String nmUseridAppRich, BigDecimal idAmbienteEnteConvenz, BigDecimal idEnteSiam,
            String nmCognomeUserRich, String nmNomeUserRich, String nmUseridRich, BigDecimal idOrganizIam,
            String cdRegistroRichGestUser, BigDecimal aaRichGestUser, String cdKeyRichGestUser, String cdRichGestUser,
            String tiStatoRichGestUser) {

        UsrVRicRichiesteTableBean ricRichiesteTB = new UsrVRicRichiesteTableBean();
        List<UsrVRicRichieste> ricRichiesteList = amministrazioneUtentiHelper.getUsrVRicRichiesteList(idUserIamCor,
                nmCognomeUserAppRich, nmNomeUserAppRich, nmUseridAppRich, idAmbienteEnteConvenz, idEnteSiam,
                nmCognomeUserRich, nmNomeUserRich, nmUseridRich, idOrganizIam, cdRegistroRichGestUser, aaRichGestUser,
                cdKeyRichGestUser, cdRichGestUser, tiStatoRichGestUser);
        try {
            for (UsrVRicRichieste ricRichieste : ricRichiesteList) {
                UsrVRicRichiesteRowBean ricRichiesteRB = (UsrVRicRichiesteRowBean) Transform
                        .entity2RowBean(ricRichieste);
                String ambienteEnteConvenz = ricRichieste.getNmAmbienteEnteConvenz() != null
                        ? ricRichieste.getNmAmbienteEnteConvenz() + " / " : "";
                ricRichiesteRB.setString("ente_convenzionato", ambienteEnteConvenz + ricRichieste.getNmEnteSiam());

                if (ricRichieste.getCdRegistroRichGestUser() != null) {
                    ricRichiesteRB.setString("identificativo_ud_rich",
                            ricRichieste.getDlCompositoOrganiz() + " - " + ricRichieste.getCdRegistroRichGestUser()
                                    + " - " + ricRichieste.getAaRichGestUser() + " - "
                                    + ricRichieste.getCdKeyRichGestUser());
                }
                ricRichiesteRB.setString("richiedente",
                        ricRichieste.getNmUserRich() != null ? ricRichieste.getNmUserRich()
                                : ricRichieste.getNmCognomeUserRich() + " " + ricRichieste.getNmNomeUserRich());
                ricRichiesteTB.add(ricRichiesteRB);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ricRichiesteTB;
    }

    public UsrRichGestUserRowBean getUsrRichGestUserRowBean(BigDecimal idRichGestUser) {
        UsrRichGestUserRowBean richGestUserRowBean = new UsrRichGestUserRowBean();
        UsrRichGestUser richGestUser = amministrazioneUtentiHelper.findById(UsrRichGestUser.class, idRichGestUser);
        try {
            if (richGestUser != null) {
                richGestUserRowBean = (UsrRichGestUserRowBean) Transform.entity2RowBean(richGestUser);
                if (richGestUser.getCdRegistroRichGestUser() != null) {
                    richGestUserRowBean.setString("identificativo_ud_rich",
                            richGestUserRowBean.getCdRegistroRichGestUser() + " - "
                                    + richGestUserRowBean.getAaRichGestUser() + " - "
                                    + richGestUserRowBean.getCdKeyRichGestUser());
                } else {
                    richGestUserRowBean.setString("identificativo_ud_rich", null);
                }
                if (richGestUserRowBean.getTiUserRich().equals(ConstUsrRichGestUser.TiUserRich.UTENTE_CODIF.name())) {
                    richGestUserRowBean.setString("nm_userid_codificato", richGestUser.getUsrUser().getNmCognomeUser()
                            + " " + richGestUser.getUsrUser().getNmNomeUser());
                } else if (richGestUserRowBean.getTiUserRich()
                        .equals(ConstUsrRichGestUser.TiUserRich.UTENTE_NO_CODIF.name())) {
                    richGestUserRowBean.setString("nm_userid_manuale", richGestUser.getNmUserRich());
                }
                String dlCompositoOrganiz = " ";
                if (richGestUser.getUsrOrganizIam() != null) {
                    final BigDecimal idOrganizIam = BigDecimal
                            .valueOf(richGestUser.getUsrOrganizIam().getIdOrganizIam());
                    richGestUserRowBean.setBigDecimal("id_organiz_iam", idOrganizIam);
                    UsrVTreeOrganizIam organiz = amministrazioneUtentiHelper.findViewById(UsrVTreeOrganizIam.class,
                            idOrganizIam);
                    dlCompositoOrganiz += "(" + organiz.getDlCompositoOrganiz() + ")";
                }
                richGestUserRowBean.setString("numero_protocollo",
                        StringUtils.defaultString(richGestUserRowBean.getString("identificativo_ud_rich"))
                                + dlCompositoOrganiz);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return richGestUserRowBean;
    }

    public UsrAppartUserRichTableBean getUsrAppartUserRichTableBean(BigDecimal idRichGestUser,
            String tiStatoRichGestUser) {
        UsrAppartUserRichTableBean appartUserRichTableBean = new UsrAppartUserRichTableBean();
        List<UsrAppartUserRich> appartUserRichList = amministrazioneUtentiHelper
                .getUsrAppartUserRichList(idRichGestUser);
        try {
            for (UsrAppartUserRich appartUserRich : appartUserRichList) {
                UsrAppartUserRichRowBean appartUserRichRowBean = (UsrAppartUserRichRowBean) Transform
                        .entity2RowBean(appartUserRich);
                // Cognome, nome, userid
                if (appartUserRich.getTiAppartUserRich()
                        .equals(ConstUsrAppartUserRich.TiAppartUserRich.UTENTE_NON_CODIFICATO.name())) {
                    appartUserRichRowBean.setString("nm_cognome_user", appartUserRich.getNmCognomeUser());
                    appartUserRichRowBean.setString("nm_nome_user", appartUserRich.getNmNomeUser());
                    appartUserRichRowBean.setString("nm_userid", appartUserRich.getNmUserid());
                } else if (appartUserRich.getTiAppartUserRich()
                        .equals(ConstUsrAppartUserRich.TiAppartUserRich.UTENTE_CODIFICATO.name())) {
                    appartUserRichRowBean.setString("nm_cognome_user", appartUserRich.getUsrUser().getNmCognomeUser());
                    appartUserRichRowBean.setString("nm_nome_user", appartUserRich.getUsrUser().getNmNomeUser());
                    appartUserRichRowBean.setString("nm_userid", appartUserRich.getUsrUser().getNmUserid());
                }

                if (tiStatoRichGestUser.equals(ConstUsrRichGestUser.TiStatoRichGestUser.DA_EVADERE.name())
                        && appartUserRich.getFlAzioneRichEvasa().equals("0")) {
                    appartUserRichRowBean.setString("is_not_evasa", "1");
                    appartUserRichRowBean.setString("eseguiAzione", "Esegui");
                } else {
                    appartUserRichRowBean.setString("is_not_evasa", "0");
                }

                // Ente user
                if (appartUserRich.getOrgEnteSiam().getOrgAmbienteEnteConvenz() != null) {
                    appartUserRichRowBean.setBigDecimal("id_ambiente_ente_appart_user", BigDecimal.valueOf(
                            appartUserRich.getOrgEnteSiam().getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz()));
                }
                appartUserRichRowBean.setBigDecimal("id_ente_appart_user",
                        BigDecimal.valueOf(appartUserRich.getOrgEnteSiam().getIdEnteSiam()));
                appartUserRichRowBean.setString("nm_ente_appart_user", appartUserRich.getOrgEnteSiam().getNmEnteSiam());
                String nmAmbienteEnteConvenz = "";
                if (appartUserRich.getOrgEnteSiam().getOrgAmbienteEnteConvenz() != null) {
                    nmAmbienteEnteConvenz = appartUserRich.getOrgEnteSiam().getOrgAmbienteEnteConvenz()
                            .getNmAmbienteEnteConvenz();
                }
                appartUserRichRowBean.setString("ambiente_ente_appart_user", nmAmbienteEnteConvenz);
                appartUserRichRowBean.setString("ente_appart_user", appartUserRich.getOrgEnteSiam().getNmEnteSiam());

                appartUserRichTableBean.add(appartUserRichRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return appartUserRichTableBean;
    }

    public UsrAppartUserRichTableBean getUsrAppartUserRichTableBean(BigDecimal idRichGestUser, String tiAzioneRich,
            String flAzioneRichEvasa) {
        UsrAppartUserRichTableBean appartUserRichTableBean = new UsrAppartUserRichTableBean();
        List<UsrAppartUserRich> appartUserRichList = amministrazioneUtentiHelper
                .getUsrAppartUserRichList(idRichGestUser, tiAzioneRich, flAzioneRichEvasa);
        try {
            if (appartUserRichList != null && !appartUserRichList.isEmpty()) {
                appartUserRichTableBean = (UsrAppartUserRichTableBean) Transform.entities2TableBean(appartUserRichList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return appartUserRichTableBean;
    }

    public UsrAppartUserRichRowBean getUsrAppartUserRichRowBean(BigDecimal idAppartUserRich) {
        UsrAppartUserRichRowBean appartUserRichRowBean = new UsrAppartUserRichRowBean();
        UsrAppartUserRich appartUserRich = amministrazioneUtentiHelper.findById(UsrAppartUserRich.class,
                idAppartUserRich);
        try {
            if (appartUserRich != null) {
                appartUserRichRowBean = (UsrAppartUserRichRowBean) Transform.entity2RowBean(appartUserRich);
                if (appartUserRich.getUsrUser() != null) {
                    appartUserRichRowBean.setString("nm_userid_codificato",
                            appartUserRich.getUsrUser().getNmCognomeUser() + " "
                                    + appartUserRich.getUsrUser().getNmNomeUser());
                }
                if (appartUserRich.getOrgEnteSiam().getOrgAmbienteEnteConvenz() != null) {
                    appartUserRichRowBean.setBigDecimal("id_ambiente_ente_user", BigDecimal.valueOf(
                            appartUserRich.getOrgEnteSiam().getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz()));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return appartUserRichRowBean;
    }

    /**
     * Metodo di eliminazione di una richiesta
     *
     * @param param
     *            paramentri per il logging
     * @param idRichGestUser
     *            id richiesta
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteUsrRichGestUser(LogParam param, BigDecimal idRichGestUser) throws ParerUserError {
        // Controllo se vi è almeno una azione con stato evasa
        if (amministrazioneUtentiHelper.existAzioni(idRichGestUser.longValue(), "1")) {
            throw new ParerUserError(
                    "Eliminazione della richiesta non possibile perchè esiste almeno una azione evasa");
        }
        try {
            UsrRichGestUser richGestUser = amministrazioneUtentiHelper.findById(UsrRichGestUser.class, idRichGestUser);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA, idRichGestUser,
                    param.getNomePagina());
            amministrazioneUtentiHelper.removeEntity(richGestUser, true);
        } catch (Exception e) {
            log.error("Errore durante la cancellazione della richiesta " + ExceptionUtils.getRootCauseMessage(e), e);
            throw new ParerUserError("Errore durante la cancellazione della richiesta");
        }
    }

    /**
     * Metodo di insert di una nuova richiesta
     *
     * @param param
     *            parametri per il logging
     * @param richGestUserRowBean
     *            row bean {@link UsrRichGestUserRowBean}
     * @param blob
     *            contenuto richiesta
     *
     * @return id della nuova richiesta
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveRichiesta(LogParam param, UsrRichGestUserRowBean richGestUserRowBean, byte[] blob)
            throws ParerUserError {
        log.debug("Eseguo i controlli sulla richiesta da inserire");
        Long idRichGestUser = null;
        // Controlli inserimento richiesta
        controlliInserimentoRichiestaUtente(richGestUserRowBean, null);

        log.debug("Eseguo il salvataggio della richiesta");
        try {
            UsrRichGestUser richiesta = new UsrRichGestUser();
            setDatiRichiesta(richiesta, richGestUserRowBean, blob);

            amministrazioneUtentiHelper.insertEntity(richiesta, true);

            log.debug("Salvataggio della richiesta completato");
            idRichGestUser = richiesta.getIdRichGestUser();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA,
                    new BigDecimal(idRichGestUser), param.getNomePagina());
        } catch (Exception ex) {
            log.error("Errore imprevisto durante il salvataggio della richiesta : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio della richiesta");
        }
        return idRichGestUser;
    }

    /**
     * Metodo di update di una richiesta
     *
     * @param param
     *            parametri per il logging
     * @param idRichGestUser
     *            id richiesta
     * @param richGestUserRowBean
     *            row bean {@link UsrRichGestUserRowBean}
     * @param blob
     *            contenuto richiesta
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveRichiesta(LogParam param, BigDecimal idRichGestUser, UsrRichGestUserRowBean richGestUserRowBean,
            byte[] blob) throws ParerUserError {
        log.debug("Eseguo i controlli sulla richiesta da modificare");
        // Controlli modifica richiesta
        controlliInserimentoRichiestaUtente(richGestUserRowBean, idRichGestUser);

        log.debug("Eseguo la modifica della richiesta");
        UsrRichGestUser richGestUser = amministrazioneUtentiHelper.findById(UsrRichGestUser.class, idRichGestUser);
        try {
            setDatiRichiesta(richGestUser, richGestUserRowBean, blob);

            amministrazioneUtentiHelper.getEntityManager().flush();

            log.debug("Salvataggio della richiesta completato");
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA,
                    idRichGestUser, param.getNomePagina());
        } catch (Exception ex) {
            log.error("Errore imprevisto durante il salvataggio della richiesta : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio della richiesta");
        }
    }

    private void controlliInserimentoRichiestaUtente(UsrRichGestUserRowBean richGestUserRowBean,
            BigDecimal idRichGestUserExcluded) throws ParerUserError {
        // Controllo che non esista già uno stesso identificativo UD, se presente, diverso da quello che sto modificando
        if (richGestUserRowBean.getCdRegistroRichGestUser() != null) {
            if (amministrazioneUtentiHelper
                    .getUsrRichGestUserByOrganizzazioneAndUd(richGestUserRowBean.getBigDecimal("id_organiz_iam"),
                            richGestUserRowBean.getCdRegistroRichGestUser(), richGestUserRowBean.getAaRichGestUser(),
                            richGestUserRowBean.getCdKeyRichGestUser(), idRichGestUserExcluded)
                    .size() > 0) {
                throw new ParerUserError("Il numero protocollo della richiesta è già associato ad un'altra richiesta");
            }
        }

        // Controllo che non esista già uno stesso identificativo richiesta, se esiste, per uno stesso ente
        // convenzionato
        if (richGestUserRowBean.getCdRichGestUser() != null) {
            if (amministrazioneUtentiHelper.getUsrRichGestUserByRichEnte(richGestUserRowBean.getCdRichGestUser(),
                    richGestUserRowBean.getIdEnteRich(), idRichGestUserExcluded).size() > 0) {
                throw new ParerUserError("L'identificativo della richiesta è già associato ad un'altra richiesta");
            }
        }

        // Se ti_user_rich = UTENTE_CODIF
        if (richGestUserRowBean.getTiUserRich().equals(ConstUsrRichGestUser.TiUserRich.UTENTE_CODIF.name())) {
            UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, richGestUserRowBean.getIdUserIamRich());
            // Controllo che l'utente riferito appartenga allo stesso ente convenzionato a cui appartiene la richiesta
            // oppure appartenga ad un ente convenzionato di tipo amministratore
            if (richGestUserRowBean.getIdEnteRich() != null) {
                if (user.getOrgEnteSiam() == null || (user.getOrgEnteSiam() != null
                        && (user.getOrgEnteSiam().getIdEnteSiam() != richGestUserRowBean.getIdEnteRich().longValue()
                                || !user.getOrgEnteSiam().getTiEnteConvenz().name().equals("AMMINISTRATORE")))) {
                    throw new ParerUserError(
                            "E' necessario scegliere un utente appartenente allo stesso ente convenzionato a cui appartiene la richiesta oppure un utente appartenente ad un ente convenzionato di tipo amministratore");
                }
            }

            // Controllo che l'utente sia di tipo PERSONA_FISICA o NON_DI_SISTEMA
            if (!user.getTipoUser().equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                    && !user.getTipoUser().equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                throw new ParerUserError(
                        "L’utente richiedente deve avere tipologia pari a PERSONA_FISICA o NON_DI_SISTEMA");
            }
        }
    }

    private void setDatiRichiesta(UsrRichGestUser richGestUser, UsrRichGestUserRowBean richGestUserRowBean,
            byte[] blob) {
        // Imposta tutti i campi della richiesta
        richGestUser.setCdRegistroRichGestUser(richGestUserRowBean.getCdRegistroRichGestUser());
        richGestUser.setAaRichGestUser(richGestUserRowBean.getAaRichGestUser());
        richGestUser.setCdKeyRichGestUser(richGestUserRowBean.getCdKeyRichGestUser());
        richGestUser.setCdRichGestUser(richGestUserRowBean.getCdRichGestUser());
        //
        OrgEnteSiam enteSiam = null;
        if (richGestUserRowBean.getIdEnteRich() != null) {
            enteSiam = amministrazioneUtentiHelper.findById(OrgEnteSiam.class, richGestUserRowBean.getIdEnteRich());
        }
        richGestUser.setOrgEnteSiam(enteSiam);
        //
        UsrOrganizIam organizIam = null;
        if (richGestUserRowBean.getIdOrganizIam() != null) {
            organizIam = amministrazioneUtentiHelper.findById(UsrOrganizIam.class,
                    richGestUserRowBean.getIdOrganizIam());
        }
        richGestUser.setUsrOrganizIam(organizIam);
        //
        richGestUser.setTiRichGestUser(richGestUserRowBean.getTiRichGestUser());
        // Aggiungo ore, minuti e secondi alla data
        Calendar dataOdierna = Calendar.getInstance();
        dataOdierna.setTime(richGestUserRowBean.getDtRichGestUser());
        int year = dataOdierna.get(Calendar.YEAR);
        int month = dataOdierna.get(Calendar.MONTH);
        int day = dataOdierna.get(Calendar.DAY_OF_MONTH);
        Calendar dataElaborata = Calendar.getInstance();
        dataElaborata.set(year, month, day);
        richGestUser.setDtRichGestUser(dataElaborata.getTime());
        richGestUser.setNtRichGestUser(richGestUserRowBean.getNtRichGestUser());
        richGestUser.setTiUserRich(richGestUserRowBean.getTiUserRich());
        richGestUser.setDsEmailRich(richGestUserRowBean.getDsEmailRich());
        // Inserisco o l'utente codificato o l'utente manuale
        if (richGestUserRowBean.getIdUserIamRich() != null) {
            UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, richGestUserRowBean.getIdUserIamRich());
            richGestUser.setUsrUser(user);
            richGestUser.setNmUserRich(null);
        } else {
            richGestUser.setNmUserRich(richGestUserRowBean.getString("nm_userid_manuale"));
            richGestUser.setUsrUser(null);
        }
        //
        richGestUser.setTiStatoRichGestUser(richGestUserRowBean.getTiStatoRichGestUser());
        richGestUser.setNmFileRichGestUser(richGestUserRowBean.getNmFileRichGestUser());
        if (blob != null) {
            richGestUser.setBlRichGestUser(blob);
        }
        //
        NtfNotifica notifica = null;
        if (richGestUserRowBean.getIdNotificaRichEvasa() != null) {
            notifica = amministrazioneUtentiHelper.findById(NtfNotifica.class,
                    richGestUserRowBean.getIdNotificaRichEvasa());
        }
        richGestUser.setNtfNotifica(notifica);
    }

    /**
     * Metodo di insert di una nuova azione
     *
     * @param param
     *            parametri per il logging
     * @param idRichGestUser
     *            id richiesta
     * @param tiStatoRichGestUser
     *            tipo stato richiesta
     * @param appartUserRichRowBean
     *            row bean {@link UsrAppartUserRichRowBean}
     *
     * @return id della nuova richiesta
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveAzione(LogParam param, BigDecimal idRichGestUser, String tiStatoRichGestUser,
            UsrAppartUserRichRowBean appartUserRichRowBean) throws ParerUserError {

        log.debug("Eseguo i controlli sull'azione da inserire");
        controlliAzioneUtente(idRichGestUser, appartUserRichRowBean, false);

        log.debug("Eseguo il salvataggio dell'azione");
        Long idAppartUserRich = null;
        try {
            UsrAppartUserRich azione = new UsrAppartUserRich();
            setDatiAzione(idRichGestUser, azione, appartUserRichRowBean);

            amministrazioneUtentiHelper.insertEntity(azione, true);

            // Se la richiesta ha stato EVASA, assume stato DA_EVADERE
            if (tiStatoRichGestUser.equals(ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name())) {
                updateTiStatoRichGestUser(idRichGestUser, ConstUsrRichGestUser.TiStatoRichGestUser.DA_EVADERE);
            }

            log.debug("Salvataggio dell'azione completato");
            idAppartUserRich = azione.getIdAppartUserRich();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA,
                    idRichGestUser, param.getNomePagina());
        } catch (Exception ex) {
            log.error(
                    "Errore imprevisto durante il salvataggio dell'azione : " + ExceptionUtils.getRootCauseMessage(ex),
                    ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'azione");
        }
        return idAppartUserRich;
    }

    /**
     * Metodo di update di un'azione
     *
     * @param param
     *            parametri per il logging
     * @param idRichGestUser
     *            id richiesta
     * @param idAppartUserRich
     *            id appartenenza richiesta
     * @param appartUserRichRowBean
     *            row bean {@link UsrAppartUserRichRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveAzione(LogParam param, BigDecimal idRichGestUser, BigDecimal idAppartUserRich,
            UsrAppartUserRichRowBean appartUserRichRowBean) throws ParerUserError {

        log.debug("Eseguo i controlli sull'azione da modificare");
        controlliAzioneUtente(idRichGestUser, appartUserRichRowBean, true);

        log.debug("Eseguo la modifica dell'azione");
        UsrAppartUserRich appartUserRich = amministrazioneUtentiHelper.findById(UsrAppartUserRich.class,
                idAppartUserRich);
        try {
            setDatiAzione(idRichGestUser, appartUserRich, appartUserRichRowBean);

            amministrazioneUtentiHelper.getEntityManager().flush();

            log.debug("Salvataggio dell'azione completato");
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA,
                    idRichGestUser, param.getNomePagina());
        } catch (Exception ex) {
            log.error(
                    "Errore imprevisto durante il salvataggio dell'azione : " + ExceptionUtils.getRootCauseMessage(ex),
                    ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'azione");
        }
    }

    /**
     * Metodo per controllare le condizioni necessarie all'inserimento/modifica di una azione
     *
     * @param idRichGestUser
     *            id richiesta
     * @param appartUserRichRowBean
     *            row bean {@link UsrAppartUserRichRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    private void controlliAzioneUtente(BigDecimal idRichGestUser, UsrAppartUserRichRowBean appartUserRichRowBean,
            boolean isUpdate) throws ParerUserError {
        // Ricavo i dati della richiesta
        UsrRichGestUser richGestUser = amministrazioneUtentiHelper.findById(UsrRichGestUser.class, idRichGestUser);
        // Ricavo l'enum di tipo azione
        ConstUsrAppartUserRich.TiAzioneRich azioneEnum = ConstUsrAppartUserRich.TiAzioneRich
                .getEnum(appartUserRichRowBean.getTiAzioneRich());
        // Ricavo la lista delle eventuali altre azioni riferite all'utente
        List<UsrAppartUserRich> azioniUtente = null;
        if (isUpdate) {
            azioniUtente = amministrazioneUtentiHelper.getUsrAppartUserRichByUser(idRichGestUser,
                    appartUserRichRowBean.getNmUserid(), appartUserRichRowBean.getIdUserIam(),
                    appartUserRichRowBean.getIdAppartUserRich());
        } else {
            azioniUtente = amministrazioneUtentiHelper.getUsrAppartUserRichByUser(idRichGestUser,
                    appartUserRichRowBean.getNmUserid(), appartUserRichRowBean.getIdUserIam(), null);
        }
        // Nel caso in cui esistano già altre azioni per l'utente che sto inserendo, eseguo dei controlli
        for (UsrAppartUserRich azioneUtente : azioniUtente) {
            ConstUsrAppartUserRich.TiAzioneRich azioneUtenteEnum = ConstUsrAppartUserRich.TiAzioneRich
                    .getEnum(azioneUtente.getTiAzioneRich());
            // Se sto inserendo una richiesta di modifica abilitazioni
            boolean confrontoOK = false;
            switch (azioneEnum) {
            case RICHIESTA_MODIFICA_ABILITAZIONI:
            case RICHIESTA_MODIFICA_ENTE_APPARTENENZA:
                if ((azioneUtenteEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RIATTIVZIONE)
                        || azioneUtenteEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RESET_PWD))) {
                    confrontoOK = true;
                }
                break;
            case RICHIESTA_RIATTIVZIONE:
                if ((azioneUtenteEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ABILITAZIONI)
                        || azioneUtenteEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RESET_PWD)
                        || azioneUtenteEnum
                                .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA))) {
                    confrontoOK = true;
                }
                break;
            case RICHIESTA_RESET_PWD:
                if ((azioneUtenteEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ABILITAZIONI)
                        || azioneUtenteEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RIATTIVZIONE)
                        || azioneUtenteEnum
                                .equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA))) {
                    confrontoOK = true;
                }
                break;
            }

            if (!confrontoOK) {
                throw new ParerUserError("Attenzione: è già stata inserita un'azione per il medesimo utente");
            }
        }

        // Controlli UTENTE_CODIFICATO
        if (appartUserRichRowBean.getIdUserIam() != null) {

            UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, appartUserRichRowBean.getIdUserIam());
            String statoUserCorrente = amministrazioneUtentiHelper
                    .getStatoCorrenteUser(appartUserRichRowBean.getIdUserIam());

            // Controllo ente convenzionato
            // if (azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RESET_PWD)
            // || azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CESSAZIONE)
            // //|| azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ABILITAZIONI)
            // || azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RIATTIVZIONE)) {
            //
            // if (user.getOrgEnteSiam() != null && user.getOrgEnteSiam().getIdEnteSiam() !=
            // richGestUser.getOrgEnteSiam().getIdEnteSiam()) {
            // throw new ParerUserError("Utente non esistente o non appartenente al dominio per lo username
            // "+user.getNmUserid()+" specificato");
            // }
            // }
            // Controllo tipo RICHIESTA DI RESET PASSWORD
            if (azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RESET_PWD)) {
                if (!statoUserCorrente.equals(ConstUsrStatoUser.TiStatotUser.ATTIVO.getDescrizione())
                        || user.getTipoUser().equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                    throw new ParerUserError(
                            "L'azione 'Richiesta di reset password' è possibile solo se l'utente ha stato corrente pari ad ATTIVO e non sia di tipo NON_DI_SISTEMA");
                }
            }

            // Controllo stato RICHIESTA DI CESSAZIONE
            if (azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CESSAZIONE)) {
                if ((!statoUserCorrente.equals(ConstUsrStatoUser.TiStatotUser.ATTIVO.getDescrizione())
                        && !statoUserCorrente.equals(ConstUsrStatoUser.TiStatotUser.DISATTIVO.getDescrizione()))
                        || user.getTipoUser().equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                    throw new ParerUserError(
                            "L'azione 'Richiesta di cessazione' è possibile solo se l'utente ha stato corrente pari ad ATTIVO o DISATTIVO e non sia di tipo NON_DI_SISTEMA");
                }
            }

            // Controllo stato RICHIESTA DI RIATTIVAZIONE
            if (azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RIATTIVZIONE)) {
                if (!statoUserCorrente.equals(ConstUsrStatoUser.TiStatotUser.DISATTIVO.getDescrizione())
                        || user.getTipoUser().equals(ApplEnum.TipoUser.NON_DI_SISTEMA.name())) {
                    throw new ParerUserError(
                            "L'azione 'Richiesta di riattivazione' è possibile solo se l'utente ha stato corrente pari a DISATTIVO e non sia di tipo NON_DI_SISTEMA");
                }
            }

            // Controllo stato RICHIESTA DI MODIFICA ABILITAZIONI
            if (azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ABILITAZIONI)) {
                if (!statoUserCorrente.equals(ConstUsrStatoUser.TiStatotUser.ATTIVO.getDescrizione())
                        && !statoUserCorrente.equals(ConstUsrStatoUser.TiStatotUser.DISATTIVO.getDescrizione())) {
                    throw new ParerUserError(
                            "L'azione 'Richiesta di modifica abilitazioni' è possibile solo se l'utente ha stato corrente pari ad ATTIVO o DISATTIVO");
                }
            }

            // Controllo stato RICHIESTA DI MODIFICA ENTE DI APPARTENENZA
            if (azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_MODIFICA_ENTE_APPARTENENZA)) {
                if (!statoUserCorrente.equals(ConstUsrStatoUser.TiStatotUser.ATTIVO.getDescrizione())
                        && !statoUserCorrente.equals(ConstUsrStatoUser.TiStatotUser.DISATTIVO.getDescrizione())) {
                    throw new ParerUserError(
                            "L'azione 'Richiesta di modifica ente di appartenenza' è possibile solo se l'utente ha stato corrente pari ad ATTIVO o DISATTIVO");
                }
            }
        } // UTENTE NON CODIFICATO
        else {
            // Sicuramente sarà una richiesta di creazione, ma controllo ugualmente...
            if (azioneEnum.equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE)) {
                if (existsUtenteByUseridAndTipo(appartUserRichRowBean.getNmUserid())) {
                    throw new ParerUserError("Lo username dell’utente da creare è già presente nel sistema");
                    // throw new ParerUserError("Utente non esistente o non appartenente al dominio gestito per lo
                    // username "+appartUserRichRowBean.getNmUserid()+" specificato");
                }
            }
        }

        // Controllo esistenza azioni sull'utente in altre richieste diverse da quella corrente
        if (!amministrazioneUtentiHelper.getOtherUsrAppartUserRichByUser(idRichGestUser,
                appartUserRichRowBean.getNmUserid(), appartUserRichRowBean.getIdUserIam()).isEmpty()) {
            throw new ParerUserError(
                    "L'azione non può essere inserita perchè sono presenti azioni precedenti sull'utente non ancora evase");
        }

    }

    private void setDatiAzione(BigDecimal idRichGestUser, UsrAppartUserRich appartUserRich,
            UsrAppartUserRichRowBean appartUserRichRowBean) {
        // Imposta tutti i campi dell'azione
        appartUserRich.setUsrRichGestUser(amministrazioneUtentiHelper.findById(UsrRichGestUser.class, idRichGestUser));
        appartUserRich.setOrgEnteSiam(
                amministrazioneUtentiHelper.findById(OrgEnteSiam.class, appartUserRichRowBean.getIdEnteUser()));
        appartUserRich.setTiAppartUserRich(appartUserRichRowBean.getTiAppartUserRich());
        appartUserRich.setNmNomeUser(appartUserRichRowBean.getNmNomeUser());
        appartUserRich.setNmCognomeUser(appartUserRichRowBean.getNmCognomeUser());
        appartUserRich.setNmUserid(appartUserRichRowBean.getNmUserid());
        // // Assegno null ad fl_user_admin se l'azione è diversa da Richiesta di creazione
        // if
        // (!appartUserRichRowBean.getTiAzioneRich().equals(ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CREAZIONE.getDescrizione()))
        // {
        // appartUserRich.setFlUserAdmin(null);
        // } else {
        // appartUserRich.setFlUserAdmin(appartUserRichRowBean.getFlUserAdmin());
        // }
        if (appartUserRichRowBean.getIdUserIam() != null) {
            appartUserRich.setUsrUser(
                    amministrazioneUtentiHelper.findById(UsrUser.class, appartUserRichRowBean.getIdUserIam()));
            appartUserRich.setTiAppartUserRich(ConstUsrAppartUserRich.TiAppartUserRich.UTENTE_CODIFICATO.name());
        } else {
            appartUserRich.setUsrUser(null);
            appartUserRich.setTiAppartUserRich(ConstUsrAppartUserRich.TiAppartUserRich.UTENTE_NON_CODIFICATO.name());
        }
        appartUserRich.setTiAzioneRich(appartUserRichRowBean.getTiAzioneRich());
        appartUserRich.setFlAzioneRichEvasa(appartUserRichRowBean.getFlAzioneRichEvasa());
    }

    /**
     * Metodo di eliminazione di un'azione
     *
     * @param param
     *            parametri per il logging
     * @param idAppartUserRich
     *            id appartenenza richiesta
     * @param idUserIam
     *            id user IAM
     * @param tiAzioneRich
     *            tipo azione
     * @param flAzioneRichEvasa
     *            flag 1/0 (true/false)
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteUsrAppartUserRich(LogParam param, BigDecimal idAppartUserRich, BigDecimal idUserIam,
            String tiAzioneRich, String flAzioneRichEvasa) throws ParerUserError {
        try {
            UsrAppartUserRich appartUserRich = amministrazioneUtentiHelper.findById(UsrAppartUserRich.class,
                    idAppartUserRich);
            long idRichGestUser = appartUserRich.getUsrRichGestUser().getIdRichGestUser();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA,
                    BigDecimal.valueOf(appartUserRich.getUsrRichGestUser().getIdRichGestUser()), param.getNomePagina());
            amministrazioneUtentiHelper.removeEntity(appartUserRich, true);
            UsrRichGestUser richGestUser = amministrazioneUtentiHelper.findById(UsrRichGestUser.class, idRichGestUser);
            if (richGestUser.getUsrAppartUserRiches() != null && richGestUser.getUsrAppartUserRiches().isEmpty()) {
                richGestUser.setTiStatoRichGestUser(ConstUsrRichGestUser.TiStatoRichGestUser.DA_COMPLETARE.name());
            }
        } catch (Exception e) {
            log.error("Errore durante la cancellazione dell'azione " + ExceptionUtils.getRootCauseMessage(e), e);
            throw new ParerUserError("Errore durante la cancellazione dell'azione");
        }
    }

    public byte[] getBlRichGestUser(BigDecimal idRichGestUser) {
        UsrRichGestUser richGestUser = amministrazioneUtentiHelper.findById(UsrRichGestUser.class, idRichGestUser);
        return richGestUser.getBlRichGestUser();
    }

    public UsrVAbilOrganizTableBean getOrganizAbilitateByParamApplic(BigDecimal idUserIamCorrente,
            BigDecimal idAmbienteEnteConvenz, BigDecimal idEnteConvenz) {
        UsrVAbilOrganizTableBean abilOrganizTB = new UsrVAbilOrganizTableBean();
        // MEV #18372: Ricavo idOrganizApplic dai nuovi parametri NM_ENTE_UNITA_DOC_ACCORDO e NM_STRUT_UNITA_DOC_ACCORDO
        String nmEnteUnitaDocAccordo = paramHelper.getValoreParamApplic(
                ConstIamParamApplic.NmParamApplic.NM_ENTE_UNITA_DOC_ACCORDO.name(), idAmbienteEnteConvenz, null,
                Constants.TipoIamVGetValAppart.AMBIENTEENTECONVENZ);
        String nmStrutUnitaDocAccordo = paramHelper.getValoreParamApplic(
                ConstIamParamApplic.NmParamApplic.NM_STRUT_UNITA_DOC_ACCORDO.name(), idAmbienteEnteConvenz, null,
                Constants.TipoIamVGetValAppart.AMBIENTEENTECONVENZ);
        BigDecimal idOrganizApplic = amministrazioneUtentiHelper.getIdOrganizIamByParam(nmEnteUnitaDocAccordo,
                nmStrutUnitaDocAccordo);
        UsrVAbilOrganiz abilOrganiz = amministrazioneUtentiHelper.getUsrVAbilOrganiz(idUserIamCorrente,
                idOrganizApplic);
        try {
            if (abilOrganiz != null) {
                UsrVAbilOrganizRowBean abilOrganizRB = (UsrVAbilOrganizRowBean) Transform.entity2RowBean(abilOrganiz);
                abilOrganizTB.add(abilOrganizRB);
            } else {
                AplApplic applic = amministrazioneUtentiHelper.getAplApplic("SACER");
                List<UsrVAbilOrganiz> abilOrganizList = amministrazioneUtentiHelper.getUsrVAbilOrganizList(
                        idUserIamCorrente, BigDecimal.valueOf(applic.getIdApplic()), "STRUTTURA", null, false);
                if (!abilOrganizList.isEmpty()) {
                    abilOrganizTB = (UsrVAbilOrganizTableBean) Transform.entities2TableBean(abilOrganizList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return abilOrganizTB;
    }

    public UsrVAbilOrganizTableBean getOrganizAbilitate(BigDecimal idUserIamCorrente) {
        UsrVAbilOrganizTableBean abilOrganizTB = new UsrVAbilOrganizTableBean();
        try {
            // Ricavo le organizzazioni di SACER alle quali l'utente è abilitato
            AplApplic applic = amministrazioneUtentiHelper.getAplApplic("SACER");
            List<UsrVAbilOrganiz> abilOrganizList = amministrazioneUtentiHelper.getUsrVAbilOrganizList(
                    idUserIamCorrente, BigDecimal.valueOf(applic.getIdApplic()), "STRUTTURA", null, false);
            if (!abilOrganizList.isEmpty()) {
                abilOrganizTB = (UsrVAbilOrganizTableBean) Transform.entities2TableBean(abilOrganizList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return abilOrganizTB;
    }

    public UsrVAbilOrganizRowBean getOrganizAbilitataByParamApplic(BigDecimal idUserIamCorrente,
            BigDecimal idAmbienteEnteConvenz) {
        UsrVAbilOrganizRowBean abilOrganizRB = null;
        // MEV #18372: Ricavo idOrganizApplic dai nuovi parametri NM_ENTE_UNITA_DOC_ACCORDO e NM_STRUT_UNITA_DOC_ACCORDO
        String nmEnteUnitaDocAccordo = paramHelper.getValoreParamApplic(
                ConstIamParamApplic.NmParamApplic.NM_ENTE_UNITA_DOC_ACCORDO.name(), idAmbienteEnteConvenz, null,
                Constants.TipoIamVGetValAppart.AMBIENTEENTECONVENZ);
        String nmStrutUnitaDocAccordo = paramHelper.getValoreParamApplic(
                ConstIamParamApplic.NmParamApplic.NM_STRUT_UNITA_DOC_ACCORDO.name(), idAmbienteEnteConvenz, null,
                Constants.TipoIamVGetValAppart.AMBIENTEENTECONVENZ);
        BigDecimal idOrganizApplic = amministrazioneUtentiHelper.getIdOrganizIamByParam(nmEnteUnitaDocAccordo,
                nmStrutUnitaDocAccordo);
        UsrVAbilOrganiz abilOrganiz = amministrazioneUtentiHelper.getUsrVAbilOrganiz(idUserIamCorrente,
                idOrganizApplic);
        try {
            if (abilOrganiz != null) {
                abilOrganizRB = (UsrVAbilOrganizRowBean) Transform.entity2RowBean(abilOrganiz);
            }
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException
                | NoSuchMethodException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }
        return abilOrganizRB;
    }

    public void updateTiStatoRichGestUser(BigDecimal idRichGestUser,
            ConstUsrRichGestUser.TiStatoRichGestUser statoRichGestUser) {
        UsrRichGestUser richGestUser = amministrazioneUtentiHelper.findById(UsrRichGestUser.class, idRichGestUser);
        richGestUser.setTiStatoRichGestUser(statoRichGestUser.name());
    }

    public BigDecimal getSusrRichGestUserCd() {
        return amministrazioneUtentiHelper.getSusrRichGestUserCd();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String resetPassword(LogParam param, BigDecimal idUserIam) throws ParerUserError {
        // UsrAppartUserRich richiestaDaEvadere = amministrazioneUtentiHelper.getRichiestaDaEvadere(idUserIam,
        // ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RESET_PWD.getDescrizione());
        UsrAppartUserRich richiestaDaEvadere = amministrazioneUtentiHelper.getRichiestaDaEseguire(idUserIam,
                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RESET_PWD.getDescrizione());
        if (richiestaDaEvadere == null) {
            // throw new ParerUserError("Attenzione: richiesta di reset password non presente, non \u00E8 possibile
            // procedere");
            throw new ParerUserError(
                    "La richiesta di gestione utenti specificata non presenta una azione di reset password relativa all’utente non evasa, oppure presenta una azione reset password relativa all’utente già evasa ma la richiesta non e’ l’ultima in cui e’ coinvolto l’utente");
        }
        /*
         * Codice aggiuntivo per il logging...
         */
        UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, idUserIam);

        String password = authEjb.resetPwd(param, idUserIam);
        /* registra l'evento di riattivazione nella tabella LOG_EVENTO_LOGIN_USER */
        LogDto logDto = new LogDto();
        logDto.setNmAttore(ConstLogEventoLoginUser.NM_ATTORE_ONLINE);
        logDto.setNmUser(user.getNmUserid());
        // FORSE INUTILE
        // tmpLogDto.setCdIndIpClient(indirizzoIP);
        //
        logDto.setTsEvento(new Date());
        logDto.setTipoEvento(LogDto.TipiEvento.SET_PSW);
        logDto.setDsEvento(ConstLogEventoLoginUser.DS_EVENTO_RESET_PSW);

        idpLogger.scriviLog(logDto);

        /* Per ogni applicazione dell'utente, registra nel log degli utenti da replicare */
        userHelper.registraLogUserDaReplic(user.getNmUserid(), ApplEnum.TiOperReplic.MOD);
        richiestaDaEvadere.setFlAzioneRichEvasa("1");
        amministrazioneUtentiHelper.getEntityManager().flush();
        if (!amministrazioneUtentiHelper.existAzioni(richiestaDaEvadere.getUsrRichGestUser().getIdRichGestUser(),
                "0")) {
            richiestaDaEvadere.getUsrRichGestUser()
                    .setTiStatoRichGestUser(ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name());
        }

        return password;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void savePassword(LogParam param, BigDecimal idUserIam, String psw) throws ParerUserError {
        UsrAppartUserRich richiestaDaEvadere = amministrazioneUtentiHelper.getRichiestaDaEvadere(idUserIam,
                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_RESET_PWD.getDescrizione());
        if (richiestaDaEvadere == null) {
            throw new ParerUserError(
                    "Attenzione: richiesta di reset password non presente, non \u00E8 possibile procedere");
        }
        /*
         * Codice aggiuntivo per il logging...
         */
        UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, idUserIam);

        authEjb.resetPwd(param, idUserIam, psw);
        /* registra l'evento di riattivazione nella tabella LOG_EVENTO_LOGIN_USER */
        LogDto logDto = new LogDto();
        logDto.setNmAttore(ConstLogEventoLoginUser.NM_ATTORE_ONLINE);
        logDto.setNmUser(user.getNmUserid());
        // FORSE INUTILE
        // tmpLogDto.setCdIndIpClient(indirizzoIP);
        //
        logDto.setTsEvento(new Date());
        logDto.setTipoEvento(LogDto.TipiEvento.SET_PSW);
        logDto.setDsEvento(ConstLogEventoLoginUser.DS_EVENTO_INSERT_PSW);

        idpLogger.scriviLog(logDto);
        /* Per ogni applicazione dell'utente, registra nel log degli utenti da replicare */
        userHelper.registraLogUserDaReplic(user.getNmUserid(), ApplEnum.TiOperReplic.MOD);
        richiestaDaEvadere.setFlAzioneRichEvasa("1");
        amministrazioneUtentiHelper.getEntityManager().flush();
        if (!amministrazioneUtentiHelper.existAzioni(richiestaDaEvadere.getUsrRichGestUser().getIdRichGestUser(),
                "0")) {
            richiestaDaEvadere.getUsrRichGestUser()
                    .setTiStatoRichGestUser(ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name());
        }
        amministrazioneUtentiHelper.getEntityManager().flush();
        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA,
                BigDecimal.valueOf(richiestaDaEvadere.getUsrRichGestUser().getIdRichGestUser()), param.getNomePagina());
    }

    public String getStatoCorrenteUser(BigDecimal idUserIam) {
        return amministrazioneUtentiHelper.getStatoCorrenteUser(idUserIam);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cessaUtente(LogParam param, BigDecimal idUserIam) throws ParerUserError {
        UsrAppartUserRich richiestaDaEvadere = amministrazioneUtentiHelper.getRichiestaDaEvadere(idUserIam,
                ConstUsrAppartUserRich.TiAzioneRich.RICHIESTA_CESSAZIONE.getDescrizione());
        if (richiestaDaEvadere == null) {
            throw new ParerUserError(
                    "Attenzione: richiesta di cessazione non presente, non \u00E8 possibile procedere");
        }

        ctx.getBusinessObject(AmministrazioneUtentiEjb.class).cessaUtente(param, idUserIam, richiestaDaEvadere);

        richiestaDaEvadere.setFlAzioneRichEvasa("1");
        amministrazioneUtentiHelper.getEntityManager().flush();
        if (!amministrazioneUtentiHelper.existAzioni(richiestaDaEvadere.getUsrRichGestUser().getIdRichGestUser(),
                "0")) {
            richiestaDaEvadere.getUsrRichGestUser()
                    .setTiStatoRichGestUser(ConstUsrRichGestUser.TiStatoRichGestUser.EVASA.name());
        }
        amministrazioneUtentiHelper.getEntityManager().flush();
        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA,
                BigDecimal.valueOf(richiestaDaEvadere.getUsrRichGestUser().getIdRichGestUser()), param.getNomePagina());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cessaUtente(LogParam param, BigDecimal idUserIam, UsrAppartUserRich richiestaDaEvadere)
            throws ParerUserError {
        UsrVChkUserCancPing cancPing = amministrazioneUtentiHelper.findViewById(UsrVChkUserCancPing.class, idUserIam);
        boolean cancOkPing = (amministrazioneUtentiHelper.getAplApplicByName("SACER_PREINGEST") != null)
                ? (cancPing == null || cancPing.getFlCancOk().equals("1")) : true;
        UsrVChkUserCancSacer cancSacer = amministrazioneUtentiHelper.findViewById(UsrVChkUserCancSacer.class,
                idUserIam);
        boolean cancOkSacer = (cancSacer == null || cancSacer.getFlCancOk().equals("1"));
        UsrVChkUserCancIam cancIam = amministrazioneUtentiHelper.findViewById(UsrVChkUserCancIam.class, idUserIam);
        boolean cancOkIam = (cancIam == null || cancIam.getFlCancOk().equals("1"));
        UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, idUserIam);

        // Se è stato indicato il sistema versante, gestisco l'aggiornamento dei servizi erogati
        if (user.getAplSistemaVersante() != null) {
            authEjb.deleteUserManagingUltimoAccordoEntiConvenzionati(param, idUserIam, cancOkPing, cancOkSacer,
                    cancOkIam, richiestaDaEvadere);
        } else {
            ctx.getBusinessObject(AmministrazioneUtentiEjb.class).deleteOrCessaUtente(param, idUserIam, cancOkPing,
                    cancOkSacer, cancOkIam, richiestaDaEvadere);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteOrCessaUtente(LogParam param, BigDecimal idUserIam, boolean cancOkPing, boolean cancOkSacer,
            boolean cancOkIam, UsrAppartUserRich richiestaDaEvadere) {
        UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, idUserIam);
        boolean deleteUser = false;
        if (!cancOkPing || !cancOkSacer || !cancOkIam || !user.getOrgEnteUserRifs().isEmpty()) {
            // Almeno in uno degli applicativi non è cancellabile, creo lo stato CESSATO
            UsrStatoUser statoUser = new UsrStatoUser();
            statoUser.setUsrUser(user);
            statoUser.setTsStato(new Timestamp(new Date().getTime()));
            statoUser.setTiStatoUser(ConstUsrStatoUser.TiStatotUser.CESSATO.name());
            statoUser.setUsrRichGestUser((richiestaDaEvadere != null ? richiestaDaEvadere.getUsrRichGestUser() : null));
            amministrazioneUtentiHelper.insertEntity(statoUser, true);
            user.setIdStatoUserCor(BigDecimal.valueOf(statoUser.getIdStatoUser()));
            user.setFlAttivo("0");

            /* Per ogni applicazione dell'utente, registra nel log degli utenti da replicare */
            userHelper.registraLogUserDaReplic(user.getNmUserid(), ApplEnum.TiOperReplic.MOD);
        } else {
            /* Per ogni applicazione dell'utente, registra nel log degli utenti da replicare */
            userHelper.registraLogUserDaReplic(user.getNmUserid(), ApplEnum.TiOperReplic.CANC);
            deleteUser = true;
        }
        /*
         * Codice aggiuntivo per il logging...
         */
        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_UTENTE,
                BigDecimal.valueOf(user.getIdUserIam()), param.getNomePagina());

        // Per l'utente cessato il sistema provvede ad eliminare le seguenti informazioni
        // List<UsrUsoUserApplic> uuuaList = amministrazioneUtentiHelper.getUsrUsoUserApplicList(idUserIam);
        userHelper.deleteUsoUserApplic(idUserIam);
        userHelper.deleteDichAbilEnteConvenz(idUserIam);
        userHelper.deleteAbilEnteSiam(idUserIam);
        user.setAplSistemaVersante(null);

        if (deleteUser) {
            /* Elimino l'utente */
            userHelper.deleteUsrUser(user);
        }
    }

    /**
     * @deprecated @param idOrganizIam id organizzazione
     *
     * @param registro
     *            registro
     * @param anno
     *            anno
     * @param numero
     *            numero
     *
     * @return true se esiste l'ud, false altrimenti
     */
    @Deprecated
    public boolean checkEsistenzaUd(BigDecimal idOrganizIam, String registro, BigDecimal anno, String numero) {
        UsrOrganizIam organizIam = amministrazioneUtentiHelper.findById(UsrOrganizIam.class, idOrganizIam);
        return amministrazioneUtentiHelper.checkEsistenzaUd(organizIam.getIdOrganizApplic(), registro, anno, numero);
    }

    public UsrRichGestUserRowBean getUsrRichGestUserRowBean(BigDecimal IdOrganizIamRich, String cdRegistroRich,
            BigDecimal aaRich, String cdKeyRich) {
        UsrRichGestUserRowBean richGestUserRowBean = null;
        List<UsrRichGestUser> usrRichGestUserByOrganizzazioneAndUd = amministrazioneUtentiHelper
                .getUsrRichGestUserByOrganizzazioneAndUd(IdOrganizIamRich, cdRegistroRich, aaRich, cdKeyRich, null);
        try {
            if (!usrRichGestUserByOrganizzazioneAndUd.isEmpty()) {
                final UsrRichGestUser richGestUser = usrRichGestUserByOrganizzazioneAndUd.get(0);
                richGestUserRowBean = (UsrRichGestUserRowBean) Transform.entity2RowBean(richGestUser);
                if (richGestUser.getCdRegistroRichGestUser() != null) {
                    richGestUserRowBean.setString("identificativo_ud_rich",
                            richGestUserRowBean.getCdRegistroRichGestUser() + " - "
                                    + richGestUserRowBean.getAaRichGestUser() + " - "
                                    + richGestUserRowBean.getCdKeyRichGestUser());
                }
                if (richGestUserRowBean.getTiUserRich().equals(ConstUsrRichGestUser.TiUserRich.UTENTE_CODIF.name())) {
                    richGestUserRowBean.setString("nm_userid_codificato", richGestUser.getUsrUser().getNmCognomeUser()
                            + " " + richGestUser.getUsrUser().getNmNomeUser());
                } else if (richGestUserRowBean.getTiUserRich()
                        .equals(ConstUsrRichGestUser.TiUserRich.UTENTE_NO_CODIF.name())) {
                    richGestUserRowBean.setString("nm_userid_manuale", richGestUser.getNmUserRich());
                }
                richGestUserRowBean.setBigDecimal("id_ente_siam",
                        BigDecimal.valueOf(richGestUser.getOrgEnteSiam().getIdEnteSiam()));
                richGestUserRowBean.setString("nm_ente_siam", richGestUser.getOrgEnteSiam().getNmEnteSiam());
                richGestUserRowBean.setBigDecimal("id_organiz_iam",
                        BigDecimal.valueOf(richGestUser.getUsrOrganizIam().getIdOrganizIam()));
                richGestUserRowBean.setString("nm_ente_convenz", richGestUser.getOrgEnteSiam().getNmEnteSiam());
                richGestUserRowBean.setString("nmAmbienteEnteCongiunti",
                        richGestUser.getOrgEnteSiam().getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz() + " / "
                                + richGestUser.getOrgEnteSiam().getNmEnteSiam());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return richGestUserRowBean;
    }

    public UsrRichGestUserRowBean getUsrRichGestUserRowBean(BigDecimal idEnteConvenzRich, String cdRich) {
        UsrRichGestUserRowBean richGestUserRowBean = null;
        List<UsrRichGestUser> usrRichGestUserByRichEnte = amministrazioneUtentiHelper
                .getUsrRichGestUserByRichEnte(cdRich, idEnteConvenzRich, null);
        try {
            if (!usrRichGestUserByRichEnte.isEmpty()) {
                final UsrRichGestUser richGestUser = usrRichGestUserByRichEnte.get(0);
                richGestUserRowBean = (UsrRichGestUserRowBean) Transform.entity2RowBean(richGestUser);
                if (richGestUser.getCdRegistroRichGestUser() != null) {
                    richGestUserRowBean.setString("identificativo_ud_rich",
                            richGestUserRowBean.getCdRegistroRichGestUser() + " - "
                                    + richGestUserRowBean.getAaRichGestUser() + " - "
                                    + richGestUserRowBean.getCdKeyRichGestUser());
                }
                if (richGestUserRowBean.getTiUserRich().equals(ConstUsrRichGestUser.TiUserRich.UTENTE_CODIF.name())) {
                    richGestUserRowBean.setString("nm_userid_codificato", richGestUser.getUsrUser().getNmCognomeUser()
                            + " " + richGestUser.getUsrUser().getNmNomeUser());
                } else if (richGestUserRowBean.getTiUserRich()
                        .equals(ConstUsrRichGestUser.TiUserRich.UTENTE_NO_CODIF.name())) {
                    richGestUserRowBean.setString("nm_userid_manuale", richGestUser.getNmUserRich());
                }
                richGestUserRowBean.setBigDecimal("id_organiz_iam", richGestUser.getUsrOrganizIam() != null
                        ? BigDecimal.valueOf(richGestUser.getUsrOrganizIam().getIdOrganizIam()) : null);
                richGestUserRowBean.setBigDecimal("id_ente_siam",
                        BigDecimal.valueOf(richGestUser.getOrgEnteSiam().getIdEnteSiam()));
                richGestUserRowBean.setString("nm_ente_siam", richGestUser.getOrgEnteSiam().getNmEnteSiam());
                if (richGestUser.getOrgEnteSiam().getOrgAmbienteEnteConvenz() != null) {
                    richGestUserRowBean.setString("nmAmbienteEnteCongiunti",
                            richGestUser.getOrgEnteSiam().getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz() + " / "
                                    + richGestUser.getOrgEnteSiam().getNmEnteSiam());
                    richGestUserRowBean.setString("nmAmbienteEnteConvenz",
                            richGestUser.getOrgEnteSiam().getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return richGestUserRowBean;
    }

    public boolean existsAzioneUtente(BigDecimal idRichGestUser, BigDecimal idAppartUserRich, List<String> tiAzioneRich,
            BigDecimal idUserIam, String flAzioneRichEvasa) {
        return amministrazioneUtentiHelper.existsAzioneUtente(idRichGestUser, idAppartUserRich, tiAzioneRich, idUserIam,
                null, null, null, flAzioneRichEvasa);
    }

    public boolean existsAzioneUtente(BigDecimal idRichGestUser, BigDecimal idAppartUserRich, String tiAzioneRich,
            BigDecimal idUserIam, String flAzioneRichEvasa) {
        List<String> tiAzioneRichList = new ArrayList<>();
        tiAzioneRichList.add(flAzioneRichEvasa);
        return amministrazioneUtentiHelper.existsAzioneUtente(idRichGestUser, idAppartUserRich, tiAzioneRichList,
                idUserIam, null, null, null, flAzioneRichEvasa);
    }

    public boolean existsAzioneUtente(BigDecimal idRichGestUser, List<String> tiAzioneRich, String nmCognomeUser,
            String nmNomeUser, String nmUserid, String flAzioneRichEvasa) {
        return amministrazioneUtentiHelper.existsAzioneUtente(idRichGestUser, null, tiAzioneRich, null, nmCognomeUser,
                nmNomeUser, nmUserid, flAzioneRichEvasa);
    }

    public BigDecimal getAzioneUtente(BigDecimal idRichGestUser, List<String> tiAzioneRich, String nmCognomeUser,
            String nmNomeUser, String nmUserid, String flAzioneRichEvasa) {
        UsrAppartUserRich appartUserRich = amministrazioneUtentiHelper.getAzioneUtente(idRichGestUser, tiAzioneRich,
                null, nmCognomeUser, nmNomeUser, nmUserid, flAzioneRichEvasa);
        if (appartUserRich != null) {
            return BigDecimal.valueOf(appartUserRich.getIdAppartUserRich());
        }
        return null;
    }

    public BigDecimal getAzioneUtente(BigDecimal idRichGestUser, List<String> tiAzioneRich, BigDecimal idUserIam) {
        UsrAppartUserRich appartUserRich = amministrazioneUtentiHelper.getAzioneUtente(idRichGestUser, tiAzioneRich,
                idUserIam);
        if (appartUserRich != null) {
            return BigDecimal.valueOf(appartUserRich.getIdAppartUserRich());
        }
        return null;
    }

    public boolean existsAzioneUtente(BigDecimal idRichGestUser, String tiAzioneRich, String nmCognomeUser,
            String nmNomeUser, String nmUserid, String flAzioneRichEvasa) {
        List<String> tiAzioneRichList = new ArrayList<>();
        tiAzioneRichList.add(tiAzioneRich);
        return amministrazioneUtentiHelper.existsAzioneUtente(idRichGestUser, null, tiAzioneRichList, null,
                nmCognomeUser, nmNomeUser, nmUserid, flAzioneRichEvasa);
    }

    public boolean existsUtenteByUseridAndTipo(String nmUserid, String... tipoUtente) {
        return amministrazioneUtentiHelper.existsUtenteByUseridAndTipo(nmUserid, tipoUtente);
    }

    public boolean isLastRichiestaForUser(BigDecimal idUserIam, BigDecimal idRichGestUser, List<String> tiAzione) {
        List<Long> usrRicGestUserByUser = amministrazioneUtentiHelper.getUsrRicGestUserByUser(idUserIam, tiAzione);
        return usrRicGestUserByUser.get(0) == idRichGestUser.longValue();
    }

    public UsrVVisLastRichGestUserRowBean getUsrVVisLastRichGestUserRowBean(BigDecimal idUserIam)
            throws ParerUserError {
        UsrVVisLastRichGestUserRowBean rowBean = null;
        try {
            UsrVVisLastRichGestUser usrVVisLastRichGestUser = amministrazioneUtentiHelper
                    .getUsrVVisLastRichGestUser(idUserIam);
            if (usrVVisLastRichGestUser != null) {
                rowBean = (UsrVVisLastRichGestUserRowBean) Transform.entity2RowBean(usrVVisLastRichGestUser);
            }
        } catch (ClassNotFoundException | IllegalStateException | IllegalAccessException | IllegalArgumentException
                | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            throw new ParerUserError("Errore inatteso nel caricamento dell'ultima richiesta per l'utente");
        }
        return rowBean;
    }

    public String checkTipoUtenteSistemaVersante(String tipoUser, BigDecimal idSistemaVersante,
            BigDecimal idEnteAppart) {
        String errore = null;
        AplSistemaVersante sistemaVersante = amministrazioneUtentiHelper.findById(AplSistemaVersante.class,
                idSistemaVersante);
        if (tipoUser.equals(ApplEnum.TipoUser.PERSONA_FISICA.name())
                && sistemaVersante.getFlAssociaPersonaFisica().equals("0")) {
            errore = "Il sistema versante non è associabile a una persona fisica";
        } else if (tipoUser.equals(ApplEnum.TipoUser.AUTOMA.name())
                && sistemaVersante.getFlAssociaPersonaFisica().equals("1")) {
            errore = "Il sistema versante non è associabile a un automa";
        } else if (tipoUser.equals(ApplEnum.TipoUser.AUTOMA.name())
                && !sistemiVersantiHelper.checkSistemaVersantePerAutoma(idSistemaVersante, idEnteAppart)) {
            errore = "Il sistema versante deve appartenere all’ente a cui appartiene l’utente oppure ad un ente di tipo fornitore collegato all’ente a cui appartiene l’utente";
        }
        return errore;
    }

    public UsrVLisEntiSiamPerAzioTableBean getUsrVLisEntiSiamPerAzioTableBean(BigDecimal idRichGestUser,
            long idUserIamCor) throws EMFError {
        UsrVLisEntiSiamPerAzioTableBean entiSiamPerAzioTableBean = new UsrVLisEntiSiamPerAzioTableBean();
        List<UsrVLisEntiSiamPerAzio> entiSiamPerAzioList = amministrazioneUtentiHelper
                .getUsrVLisEntiSiamPerAzioList(idRichGestUser, idUserIamCor);
        try {
            for (UsrVLisEntiSiamPerAzio enteSiamPerAzio : entiSiamPerAzioList) {
                UsrVLisEntiSiamPerAzioRowBean entiSiamPerAzioRowBean = new UsrVLisEntiSiamPerAzioRowBean();
                entiSiamPerAzioRowBean = (UsrVLisEntiSiamPerAzioRowBean) Transform.entity2RowBean(enteSiamPerAzio);
                String ambiente = enteSiamPerAzio.getNmAmbienteEnteConvenz() != null
                        ? enteSiamPerAzio.getNmAmbienteEnteConvenz() + " / " : "";
                entiSiamPerAzioRowBean.setString("nome_ambiente_ente", ambiente + enteSiamPerAzio.getNmEnteSiam());
                entiSiamPerAzioTableBean.add(entiSiamPerAzioRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entiSiamPerAzioTableBean;
    }

    public UsrVLisEntiSiamAppEnteTableBean getUsrVLisEntiSiamAppEnteTableBean(long idUserIamCor) throws EMFError {
        UsrVLisEntiSiamAppEnteTableBean entiSiamAppEnteTableBean = new UsrVLisEntiSiamAppEnteTableBean();
        List<UsrVLisEntiSiamAppEnte> entiSiamAppEnteList = amministrazioneUtentiHelper
                .getUsrVLisEntiSiamAppEnteList(idUserIamCor);
        try {
            for (UsrVLisEntiSiamAppEnte enteSiamAppEnte : entiSiamAppEnteList) {
                UsrVLisEntiSiamAppEnteRowBean entiSiamAppEnteRowBean = new UsrVLisEntiSiamAppEnteRowBean();
                entiSiamAppEnteRowBean = (UsrVLisEntiSiamAppEnteRowBean) Transform.entity2RowBean(enteSiamAppEnte);
                String ambiente = enteSiamAppEnte.getNmAmbienteEnteConvenz() != null
                        ? enteSiamAppEnte.getNmAmbienteEnteConvenz() + " / " : "";
                entiSiamAppEnteRowBean.setString("nome_ambiente_ente", ambiente + enteSiamAppEnte.getNmEnteSiam());
                entiSiamAppEnteTableBean.add(entiSiamAppEnteRowBean);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entiSiamAppEnteTableBean;
    }

    public boolean isUtenteAppartEnteSpecificato(BigDecimal idUserIam, BigDecimal idEnteSiam) {
        UsrUser user = amministrazioneUtentiHelper.findById(UsrUser.class, idUserIam);
        OrgEnteSiam enteSiam = amministrazioneUtentiHelper.findById(OrgEnteSiam.class, idEnteSiam);
        return user.getOrgEnteSiam().getIdEnteSiam() == enteSiam.getIdEnteSiam();
    }

    public UsrVLisAbilOrganizTableBean getUsrVLisAbilOrganizTableBean(BigDecimal idUserIam) throws EMFError {
        UsrVLisAbilOrganizTableBean abilOrganizTB = new UsrVLisAbilOrganizTableBean();
        List<UsrVLisAbilOrganiz> abilOrganizList = amministrazioneUtentiHelper.getUsrVLisAbilOrganizList(idUserIam);
        try {
            if (abilOrganizList != null && !abilOrganizList.isEmpty()) {
                abilOrganizTB = (UsrVLisAbilOrganizTableBean) Transform.entities2TableBean(abilOrganizList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return abilOrganizTB;
    }

    public UsrVLisAbilEnteTableBean getUsrVLisAbilEnteTableBean(BigDecimal idUserIam) throws EMFError {
        UsrVLisAbilEnteTableBean abilEnteTB = new UsrVLisAbilEnteTableBean();
        List<UsrVLisAbilEnte> abilEnteList = amministrazioneUtentiHelper.getUsrVLisAbilEnteList(idUserIam);
        try {
            if (abilEnteList != null && !abilEnteList.isEmpty()) {
                abilEnteTB = (UsrVLisAbilEnteTableBean) Transform.entities2TableBean(abilEnteList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return abilEnteTB;
    }

    public boolean isEnteAmministratore(BigDecimal idEnteSiam) throws EMFError {
        OrgEnteSiam enteSiam = amministrazioneUtentiHelper.findById(OrgEnteSiam.class, idEnteSiam);
        return enteSiam.getTiEnteConvenz() != null
                && enteSiam.getTiEnteConvenz().equals(ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE);
    }

    public boolean isEnteOrganoVigilanza(BigDecimal idEnteSiam) throws EMFError {
        OrgEnteSiam enteSiam = amministrazioneUtentiHelper.findById(OrgEnteSiam.class, idEnteSiam);
        return enteSiam.getTiEnteNonConvenz() != null
                && enteSiam.getTiEnteNonConvenz().equals(ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA);
    }

    public boolean isEnteFornitoreEsterno(BigDecimal idEnteSiam) throws EMFError {
        OrgEnteSiam enteSiam = amministrazioneUtentiHelper.findById(OrgEnteSiam.class, idEnteSiam);
        return enteSiam.getTiEnteNonConvenz() != null
                && enteSiam.getTiEnteNonConvenz().equals(ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO);
    }

    public boolean isEnteSoggettoAttuatore(BigDecimal idEnteSiam) throws EMFError {
        OrgEnteSiam enteSiam = amministrazioneUtentiHelper.findById(OrgEnteSiam.class, idEnteSiam);
        return enteSiam.getTiEnteNonConvenz() != null
                && enteSiam.getTiEnteNonConvenz().equals(ConstOrgEnteSiam.TiEnteNonConvenz.SOGGETTO_ATTUATORE);
    }

    public String getTipoEnteConvenzNonConvenz(BigDecimal idEnteSiam) {
        OrgEnteSiam enteSiam = amministrazioneUtentiHelper.findById(OrgEnteSiam.class, idEnteSiam);
        if (enteSiam.getTiEnte().equals(ConstOrgEnteSiam.TiEnteSiam.CONVENZIONATO)) {
            return enteSiam.getTiEnteConvenz().name();
        } else if (enteSiam.getTiEnte().equals(ConstOrgEnteSiam.TiEnteSiam.NON_CONVENZIONATO)) {
            return enteSiam.getTiEnteNonConvenz().name();
        }
        return null;
    }

    public String getTipoEnte(BigDecimal idEnteSiam) {
        OrgEnteSiam enteSiam = amministrazioneUtentiHelper.findById(OrgEnteSiam.class, idEnteSiam);
        return enteSiam.getTiEnte().name();
    }

    // /**
    // * Metodo di eliminazione delle abilitazioni
    // *
    // * @param idUserIam
    // * @param param
    // * @throws ParerUserError
    // */
    // @TransactionAttribute(TransactionAttributeType.REQUIRED)
    // public void deleteUsrDichAbil(BigDecimal idUserIam) throws ParerUserError {
    // try {
    // List<Long> uuuaList = amministrazioneUtentiHelper.getIdUsrUsoUserApplicList(idUserIam);
    // userHelper.deleteDichAbilOrganiz(uuuaList);
    // userHelper.deleteAbilOrganiz(uuuaList);
    // userHelper.deleteDichAbilDati(uuuaList);
    // userHelper.deleteAbilDati(uuuaList);
    // userHelper.deleteDichAbilEnteConvenz(idUserIam);
    // userHelper.deleteAbilEnteSiam(idUserIam);
    // //sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
    // param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_RICHIESTA, idRichGestUser, param.getNomePagina());
    // } catch (Exception e) {
    // log.error("Errore durante la cancellazione delle dichiarazioni e abilitazioni dell'utente" +
    // ExceptionUtils.getRootCauseMessage(e), e);
    // throw new ParerUserError("Errore durante la cancellazione delle dichiarazioni e abilitazioni dell'utente");
    // }
    // }
    public boolean existsRichiestaUtente(BigDecimal idUserIam) {
        return amministrazioneUtentiHelper.existsRichiestaUtente(idUserIam);
    }

    public boolean existsRichiestaUtenteAzione(BigDecimal idUserIam, String azione) {
        return amministrazioneUtentiHelper.existsRichiestaUtenteAzione(idUserIam, azione);
    }

    public boolean existsRichiestaUtenteAzione(BigDecimal idRichGestUser, BigDecimal idUserIam, String azione) {
        return amministrazioneUtentiHelper.existsRichiestaUtenteAzione(idRichGestUser, idUserIam, azione);
    }

    public boolean existsRichiestaUtenteDiversaAzione(BigDecimal idUserIam, String azione) {
        return amministrazioneUtentiHelper.existsRichiestaUtenteDiversaAzione(idUserIam, azione);
    }

    public boolean existsRichiestaUtenteDiversaAzione(BigDecimal idRichGestUser, BigDecimal idUserIam, String azione) {
        return amministrazioneUtentiHelper.existsRichiestaUtenteDiversaAzione(idRichGestUser, idUserIam, azione);
    }

    public boolean isReferenteOk(BigDecimal idEnteSiam, BigDecimal idUserIam) {
        OrgVChkRefEnte record = amministrazioneUtentiHelper.getOrgVChkRefEnte(idEnteSiam, idUserIam);
        return record.getFlRefOk().equals("1");
    }

    public UsrAppartUserRichRowBean getRichiestaUtenteAzioneRowBean(BigDecimal idRichGestUser, BigDecimal idUserIam,
            String azione) {
        UsrAppartUserRichRowBean rb = new UsrAppartUserRichRowBean();
        UsrAppartUserRich azioneRichiesta = amministrazioneUtentiHelper.getRichiestaUtenteAzione(idRichGestUser,
                idUserIam, azione);
        try {
            if (azioneRichiesta != null) {
                rb = (UsrAppartUserRichRowBean) Transform.entity2RowBean(azioneRichiesta);
                if (azioneRichiesta.getOrgEnteSiam().getOrgAmbienteEnteConvenz() != null) {
                    rb.setBigDecimal("id_ambiente_ente_user", BigDecimal.valueOf(
                            azioneRichiesta.getOrgEnteSiam().getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz()));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rb;
    }

    /**
     * Verifico se per le applicazioni SACER o SACER_PING sono stati effettuati versamenti
     *
     * @param nmApplic
     *            può valere SACER o SACER_PREINGEST
     * @param idUsoUserApplic
     *            l'identificativo che mette in relazione l'utente con l'applicazione
     *
     * @return true se per una delle applicazioni in input l'utente ha effettuato versamenti
     */
    public boolean checkExistsVersamenti(String nmApplic, BigDecimal idUsoUserApplic) {
        UsrUsoUserApplic usoUserApplic = amministrazioneUtentiHelper.findById(UsrUsoUserApplic.class, idUsoUserApplic);
        if (usoUserApplic != null) {
            // Controllo SACER
            if (nmApplic.equals("SACER")) {
                return amministrazioneUtentiHelper
                        .checkExistsVersamentiSacer(usoUserApplic.getUsrUser().getIdUserIam());
                // Controllo PING
            } else if (nmApplic.equals("SACER_PREINGEST")) {
                return amministrazioneUtentiHelper.checkExistsVersamentiPing(usoUserApplic.getUsrUser().getIdUserIam());
            }
        }
        return false;
    }

    public String getQualificaUser(long idUserIam) {
        return amministrazioneUtentiHelper.getQualificaUser(idUserIam);
    }

}
