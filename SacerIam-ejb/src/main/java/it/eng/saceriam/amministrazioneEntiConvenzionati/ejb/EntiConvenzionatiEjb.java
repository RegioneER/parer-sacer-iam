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

package it.eng.saceriam.amministrazioneEntiConvenzionati.ejb;

import java.util.Arrays;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.ejb.util.ObjectsToLogBefore;
import it.eng.parer.sacerlog.entity.LogEventoByScript;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.amministrazioneEntiConvenzionati.dto.EnteConvenzionatoBean;
import it.eng.saceriam.amministrazioneEntiConvenzionati.dto.ServizioFatturatoBean;
import it.eng.saceriam.amministrazioneEntiConvenzionati.dto.StoEnteConvenzionatoBean;
import it.eng.saceriam.amministrazioneEntiConvenzionati.helper.EntiConvenzionatiHelper;
import it.eng.saceriam.amministrazioneEntiNonConvenzionati.ejb.EntiNonConvenzionatiEjb;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.AplApplic;
import it.eng.saceriam.entity.AplAzionePagina;
import it.eng.saceriam.entity.AplSistemaVersante;
import it.eng.saceriam.entity.IamParamApplic;
import it.eng.saceriam.entity.IamValoreParamApplic;
import it.eng.saceriam.entity.OrgAaAccordo;
import it.eng.saceriam.entity.OrgAccordoEnte;
import it.eng.saceriam.entity.OrgAmbienteEnteConvenz;
import it.eng.saceriam.entity.OrgAmbitoTerrit;
import it.eng.saceriam.entity.OrgAppartCollegEnti;
import it.eng.saceriam.entity.OrgCategEnte;
import it.eng.saceriam.entity.OrgCdIva;
import it.eng.saceriam.entity.OrgClasseEnteConvenz;
import it.eng.saceriam.entity.OrgClusterAccordo;
import it.eng.saceriam.entity.OrgCollegEntiConvenz;
import it.eng.saceriam.entity.OrgDiscipStrut;
import it.eng.saceriam.entity.OrgEnteArkRif;
import it.eng.saceriam.entity.OrgEnteConvenzOrg;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.OrgEnteUserRif;
import it.eng.saceriam.entity.OrgFasciaStorageAccordo;
import it.eng.saceriam.entity.OrgFatturaEnte;
import it.eng.saceriam.entity.OrgGestioneAccordo;
import it.eng.saceriam.entity.OrgModifFatturaEnte;
import it.eng.saceriam.entity.OrgModuloInfoAccordo;
import it.eng.saceriam.entity.OrgPagamFatturaEnte;
import it.eng.saceriam.entity.OrgScaglioneTariffa;
import it.eng.saceriam.entity.OrgServizioErog;
import it.eng.saceriam.entity.OrgServizioFattura;
import it.eng.saceriam.entity.OrgSollecitoFatturaEnte;
import it.eng.saceriam.entity.OrgStatoFatturaEnte;
import it.eng.saceriam.entity.OrgStoEnteAmbienteConvenz;
import it.eng.saceriam.entity.OrgStoEnteConvenz;
import it.eng.saceriam.entity.OrgStrut;
import it.eng.saceriam.entity.OrgSuptEsternoEnteConvenz;
import it.eng.saceriam.entity.OrgTariffa;
import it.eng.saceriam.entity.OrgTariffaAaAccordo;
import it.eng.saceriam.entity.OrgTariffaAccordo;
import it.eng.saceriam.entity.OrgTariffario;
import it.eng.saceriam.entity.OrgTipiGestioneAccordo;
import it.eng.saceriam.entity.OrgTipoAccordo;
import it.eng.saceriam.entity.OrgTipoServizio;
import it.eng.saceriam.entity.OrgVigilEnteProdut;
import it.eng.saceriam.entity.UsrAbilEnteSiam;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrStatoUser;
import it.eng.saceriam.entity.UsrTipoDatoIam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.constraint.ConstAplApplic;
import it.eng.saceriam.entity.constraint.ConstIamParamApplic;
import it.eng.saceriam.entity.constraint.ConstOrgAccordoEnte.TiScopoAccordo;
import it.eng.saceriam.entity.constraint.ConstOrgAmbitoTerrit;
import it.eng.saceriam.entity.constraint.ConstOrgCollegEntiConvenz.TiColleg;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteConvenz;
import it.eng.saceriam.entity.constraint.ConstOrgGestioneAccordo.TipoTrasmissione;
import it.eng.saceriam.entity.constraint.ConstOrgStatoFatturaEnte;
import it.eng.saceriam.entity.constraint.ConstOrgTariffa;
import it.eng.saceriam.entity.constraint.ConstOrgTipoServizio;
import it.eng.saceriam.entity.constraint.ConstUsrStatoUser;
import it.eng.saceriam.exception.ParerInternalError;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.gestioneFatturazioneServizi.dto.FatturaBean;
import it.eng.saceriam.grantedViewEntity.DecVLisTiUniDocAms;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm.AccordoDetail;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm.AccordoWizardDetail;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm.TipoServizioAccordoList;
import it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm.TipoServizioAnnualitaAccordoList;
import it.eng.saceriam.slite.gen.tablebean.IamParamApplicRowBean;
import it.eng.saceriam.slite.gen.tablebean.IamParamApplicTableBean;
import it.eng.saceriam.slite.gen.tablebean.IamParamApplicTableDescriptor;
import it.eng.saceriam.slite.gen.tablebean.OrgAaAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAaAccordoTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAccordoEnteRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAccordoEnteTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbienteEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbienteEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbitoTerritRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAmbitoTerritTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAppartCollegEntiRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgAppartCollegEntiTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgCategEnteTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgCdIvaTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgClasseEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgClusterAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgClusterAccordoTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgCollegEntiConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgCollegEntiConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgDiscipStrutRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgDiscipStrutTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteArkRifRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteArkRifTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteConvenzOrgRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteConvenzOrgTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteSiamTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteUserRifRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgEnteUserRifTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgFasciaStorageAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgFasciaStorageAccordoTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgGestioneAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgGestioneAccordoTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgModifFatturaEnteTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgModuloInfoAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgModuloInfoAccordoTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgPagamFatturaEnteRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgPagamFatturaEnteTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgScaglioneTariffaRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgScaglioneTariffaTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgServizioErogRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgServizioErogTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgServizioFatturaRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgServizioFatturaTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSollecitoFatturaEnteRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSollecitoFatturaEnteTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgStoEnteAmbienteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgStoEnteAmbienteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgStoEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgStoEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSuptEsternoEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgSuptEsternoEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTariffaAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTariffaAccordoTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTariffaRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTariffaTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTariffarioRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTariffarioTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTipiGestioneAccordoTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoAccordoRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoAccordoTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoServizioRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgTipoServizioTableBean;
import it.eng.saceriam.slite.gen.tablebean.OrgVigilEnteProdutRowBean;
import it.eng.saceriam.slite.gen.tablebean.OrgVigilEnteProdutTableBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUserRowBean;
import it.eng.saceriam.slite.gen.tablebean.UsrUserTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVCalcTotFattRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVEnteConvenzByOrganizRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVEnteConvenzByOrganizTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVLisServFatturaRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVLisServFatturaTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicAccordoEnteRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicAccordoEnteTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicEnteNonConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicFatturePerAccordoRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicFatturePerAccordoTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicFattureRowBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVRicFattureTableBean;
import it.eng.saceriam.slite.gen.viewbean.OrgVVisFatturaRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilAmbConvenzXenteTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilAmbEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilAmbEnteXenteTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilEnteConvenzRowBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVAbilEnteConvenzTableBean;
import it.eng.saceriam.slite.gen.viewbean.UsrVLisUserEnteConvenzTableBean;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.util.RestTemplateEjb;
import it.eng.saceriam.util.SacerLogConstants;
import it.eng.saceriam.viewEntity.OrgVCalcTotFatt;
import it.eng.saceriam.viewEntity.OrgVCreaFatturaByAnno;
import it.eng.saceriam.viewEntity.OrgVCreaFatturaByFatt;
import it.eng.saceriam.viewEntity.OrgVCreaServErogByAcc;
import it.eng.saceriam.viewEntity.OrgVCreaServFattAnnuale;
import it.eng.saceriam.viewEntity.OrgVCreaServFattUnaPrec;
import it.eng.saceriam.viewEntity.OrgVCreaServFattUnatantum;
import it.eng.saceriam.viewEntity.OrgVEnteConvenzByOrganiz;
import it.eng.saceriam.viewEntity.OrgVLisServFattura;
import it.eng.saceriam.viewEntity.OrgVOccupStorageAccordo;
import it.eng.saceriam.viewEntity.OrgVRicAccordoEnte;
import it.eng.saceriam.viewEntity.OrgVRicEnteConvenz;
import it.eng.saceriam.viewEntity.OrgVRicEnteNonConvenz;
import it.eng.saceriam.viewEntity.OrgVRicFatture;
import it.eng.saceriam.viewEntity.OrgVRicFatturePerAccordo;
import it.eng.saceriam.viewEntity.OrgVVisFattura;
import it.eng.saceriam.viewEntity.UsrVAbilAmbConvenzXente;
import it.eng.saceriam.viewEntity.UsrVAbilAmbEnteConvenz;
import it.eng.saceriam.viewEntity.UsrVAbilAmbEnteXente;
import it.eng.saceriam.viewEntity.UsrVAbilEnteConvenz;
import it.eng.saceriam.viewEntity.UsrVLisUserEnteConvenz;
import it.eng.saceriam.web.dto.EstraiFattureBean;
import it.eng.saceriam.web.dto.PairAuth;
import it.eng.saceriam.web.helper.AmministrazioneUtentiHelper;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ActionEnums;
import it.eng.saceriam.web.util.Transform;
import it.eng.spagoCore.error.EMFError;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.base.table.BaseTable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.TransformerUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.eng.spagoLite.util.Casting.Casting;
import org.springframework.web.client.RestClientException;
import java.math.RoundingMode;

/**
 *
 * @author Bonora_L feat. Gilioli_P feat. DiLorenzo_F
 */
@Stateless
@LocalBean
@Interceptors({ it.eng.saceriam.aop.TransactionInterceptor.class })
public class EntiConvenzionatiEjb {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntiConvenzionatiEjb.class);

    @EJB
    EntiConvenzionatiHelper helper;
    @EJB
    AmministrazioneUtentiHelper utentiHelper;
    @EJB
    EntiNonConvenzionatiEjb encEjb;
    @EJB
    UserHelper userHelper;
    @EJB
    ParamHelper paramHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;
    @EJB
    private RestTemplateEjb restTemplateEjb;
    @Resource
    private SessionContext ctx;

    // @Autowired(required = false)
    // protected RestTemplate restTemplate;

    /**
     * Ritorna il tableBean contenente gli ambiti territoriali dato il tipo, in ordine gerarchico
     *
     * @param tiAmbitoTerrit
     *            tipo ambito territoriale
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
     *            id ambito territoriale
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

    // nmEnteConvenz
    public OrgVRicEnteConvenzTableBean getOrgVRicEnteConvenzTableBean(String nmEnteConvenz, BigDecimal idUserIamCor,
            BigDecimal idAmbienteEnteConvenz, String flEnteAttivo, String flEnteCessato, BigDecimal idCategEnte,
            List<BigDecimal> idAmbitoTerritRegione, List<BigDecimal> idAmbitoTerritProv,
            List<BigDecimal> idAmbitoTerritFormAssoc, List<BigDecimal> idTipoAccordo, Date dtFineValidAccordoDa,
            Date dtFineValidAccordoA, Date dtScadAccordoDa, Date dtScadAccordoA, List<BigDecimal> idArchivista,
            String noArchivista, String flRicev, String flRichModuloInf, String flNonConvenz, String flRecesso,
            String flChiuso, String flEsistonoGestAcc, List<BigDecimal> idTipoGestioneAccordo, String flGestAccNoRisp,
            String tiStatoAccordo, String cdFisc, Date dtDecAccordoDa, Date dtDecAccordoA, Date dtDecAccordoInfoDa,
            Date dtDecAccordoInfoA) throws ParerUserError {
        OrgVRicEnteConvenzTableBean orgEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        List<OrgVRicEnteConvenz> enteConvenzs = helper.retrieveOrgEnteConvenzList(nmEnteConvenz, idUserIamCor,
                idAmbienteEnteConvenz, flEnteAttivo, flEnteCessato, idCategEnte, idAmbitoTerritRegione,
                idAmbitoTerritProv, idAmbitoTerritFormAssoc, idTipoAccordo, dtFineValidAccordoDa, dtFineValidAccordoA,
                dtScadAccordoDa, dtScadAccordoA, idArchivista, noArchivista, flRicev, flRichModuloInf, flNonConvenz,
                flRecesso, flChiuso, flEsistonoGestAcc, idTipoGestioneAccordo, flGestAccNoRisp, tiStatoAccordo, cdFisc,
                dtDecAccordoDa, dtDecAccordoA, dtDecAccordoInfoDa, dtDecAccordoInfoA);
        if (!enteConvenzs.isEmpty()) {
            try {
                orgEnteConvenzTableBean = customEntities2EnteConvenzTableBean(enteConvenzs);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero degli enti convenzionati " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero della lista di enti convenzionati");
            }
        }

        return orgEnteConvenzTableBean;
    }

    /**
     * Metodo di insert di un nuovo ente convenzionato e dell'accordo tramite wizard
     *
     * @param param
     *            parametri per il logging
     * @param creazioneBean
     *            bean {@link EnteConvenzionatoBean}
     * @param parametriAmministrazione
     *            table bean {@link IamParamApplicTableBean}
     * @param parametriConservazione
     *            bean {@link IamParamApplicTableBean}
     * @param parametriGestione
     *            table bean {@link IamParamApplicTableBean}
     * @param accordoWizardDetail
     *            wizard {@link AccordoWizardDetail}
     * @param lista
     *            bean {@link TipoServizioAccordoList}
     *
     * @return id del nuovo ente
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveEnteConvenzionatoAccordoWizard(LogParam param, EnteConvenzionatoBean creazioneBean,
            IamParamApplicTableBean parametriAmministrazione, IamParamApplicTableBean parametriConservazione,
            IamParamApplicTableBean parametriGestione, AccordoWizardDetail accordoWizardDetail,
            TipoServizioAccordoList lista) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio dell'ente convenzionato e dell'accordo");

        Long idEnteConvenz = null;
        try {
            OrgEnteSiam ente = new OrgEnteSiam();
            ente.setNmEnteSiam(creazioneBean.getNm_ente_convenz());
            setDatiEnteConvenzionato(ente, creazioneBean);

            helper.insertEntity(ente, true);

            // Aggiunta abilitazioni ente convenzionato per gli utenti interessati (ALL_AMBIENTI o UN_AMBIENTE riferito
            // all'ambiente scelto in fase di creazione ente)
            List<BigDecimal> utenti = userHelper.getUtentiAbilitatiAmbEnteXnteConvenz(
                    new BigDecimal(ente.getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz()));

            for (BigDecimal utente : utenti) {
                userHelper.aggiungiAbilEnteToAdd(utente.longValue());
            }

            // Gestione parametri
            for (IamParamApplicRowBean paramApplicRowBean : parametriAmministrazione) {
                if (paramApplicRowBean.getString("ds_valore_param_applic_ente_amm") != null
                        && !paramApplicRowBean.getString("ds_valore_param_applic_ente_amm").equals("")) {
                    insertIamValoreParamApplic(null, ente, paramApplicRowBean.getIdParamApplic(), "ENTE",
                            paramApplicRowBean.getString("ds_valore_param_applic_ente_amm"));
                }
            }
            for (IamParamApplicRowBean paramApplicRowBean : parametriConservazione) {
                if (paramApplicRowBean.getString("ds_valore_param_applic_ente_cons") != null
                        && !paramApplicRowBean.getString("ds_valore_param_applic_ente_cons").equals("")) {
                    insertIamValoreParamApplic(null, ente, paramApplicRowBean.getIdParamApplic(), "ENTE",
                            paramApplicRowBean.getString("ds_valore_param_applic_ente_cons"));
                }
            }
            for (IamParamApplicRowBean paramApplicRowBean : parametriGestione) {
                if (paramApplicRowBean.getString("ds_valore_param_applic_ente_gest") != null
                        && !paramApplicRowBean.getString("ds_valore_param_applic_ente_gest").equals("")) {
                    insertIamValoreParamApplic(null, ente, paramApplicRowBean.getIdParamApplic(), "ENTE",
                            paramApplicRowBean.getString("ds_valore_param_applic_ente_gest"));
                }
            }

            idEnteConvenz = ente.getIdEnteSiam();

            OrgAccordoEnte accordoEnte = new OrgAccordoEnte();
            setDatiAccordoWizard(BigDecimal.valueOf(idEnteConvenz), accordoEnte, accordoWizardDetail);
            // Persisto l'accordo con i servizi erogati
            helper.insertEntity(accordoEnte, true);

            insertAnnualitaAutomatiche(accordoEnte);

            insertTipiServizioCompilati(lista, accordoEnte);

            //////////////////////////////////////////
            // Imposto i Servizi Erogati da salvare //
            //////////////////////////////////////////
            calcolaServiziErogatiSuAccordo(accordoEnte);

            LOGGER.debug("Salvataggio dell'ente e dell'accordo completato");
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(idEnteConvenz), param.getNomePagina());
        } catch (ParerUserError ex) {
            throw new ParerUserError(ex.getDescription());
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio dell'ente convenzionato e dell'accordo : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError(
                    "Eccezione imprevista durante il salvataggio dell'ente convenzionato e dell'accordo");
        }
        return idEnteConvenz;
    }

    /**
     * Metodo di insert di un nuovo ente convenzionato
     *
     * @param param
     *            parametri per il loggin
     * @param creazioneBean
     *            bean {@link EnteConvenzionatoBean}
     * @param parametriAmministrazione
     *            table bean {@link IamParamApplicTableBean}
     * @param parametriConservazione
     *            table bean {@link IamParamApplicTableBean}
     * @param parametriGestione
     *            table bean {@link IamParamApplicTableBean}
     *
     * @return id del nuovo ente
     *
     * @throws ParerUserError
     *             errore generico
     */
    // saveEnteConvenzionato
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveEnteConvenzionato(LogParam param, EnteConvenzionatoBean creazioneBean,
            IamParamApplicTableBean parametriAmministrazione, IamParamApplicTableBean parametriConservazione,
            IamParamApplicTableBean parametriGestione) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio dell'ente convenzionato");
        // Controllo esistenza denominazione
        OrgEnteSiam enteSiam = helper.getOrgEnteConvenz(creazioneBean.getNm_ente_convenz());
        if (enteSiam != null) {
            if (enteSiam.getTiEnte().name().equals("CONVENZIONATO")) {
                throw new ParerUserError("Ente gi\u00E0 censito nel sistema come convenzionato");
            } else {
                throw new ParerUserError("Ente gi\u00E0 censito nel sistema come non convenzionato");
            }
        }
        // Controllo esistenza codice e tipo codice ente
        if (helper.getOrgEnteConvenz(creazioneBean.getCd_ente_convenz(),
                creazioneBean.getTi_cd_ente_convenz()) != null) {
            throw new ParerUserError("Ente convenzionato gi\u00E0 censito nel sistema " + "con codice "
                    + creazioneBean.getCd_ente_convenz() + " e tipo codice " + creazioneBean.getTi_cd_ente_convenz());
        }
        Long idEnteConvenz = null;
        try {
            OrgEnteSiam ente = new OrgEnteSiam();
            // ente.setNmEnteSiam(creazioneBean.getNm_ente_convenz());
            setDatiEnteConvenzionato(ente, creazioneBean);

            helper.insertEntity(ente, true);

            // Aggiunta abilitazioni ente convenzionato per gli utenti interessati (ALL_AMBIENTI o UN_AMBIENTE riferito
            // all'ambiente scelto in fase di creazione ente)
            List<BigDecimal> utenti = userHelper.getUtentiAbilitatiAmbEnteXnteConvenz(
                    new BigDecimal(ente.getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz()));

            for (BigDecimal utente : utenti) {
                userHelper.aggiungiAbilEnteToAdd(utente.longValue());
            }

            // Gestione parametri
            for (IamParamApplicRowBean paramApplicRowBean : parametriAmministrazione) {
                if (paramApplicRowBean.getString("ds_valore_param_applic_ente_amm") != null
                        && !paramApplicRowBean.getString("ds_valore_param_applic_ente_amm").equals("")) {
                    insertIamValoreParamApplic(null, ente, paramApplicRowBean.getIdParamApplic(), "ENTE",
                            paramApplicRowBean.getString("ds_valore_param_applic_ente_amm"));
                }
            }
            for (IamParamApplicRowBean paramApplicRowBean : parametriConservazione) {
                if (paramApplicRowBean.getString("ds_valore_param_applic_ente_cons") != null
                        && !paramApplicRowBean.getString("ds_valore_param_applic_ente_cons").equals("")) {
                    insertIamValoreParamApplic(null, ente, paramApplicRowBean.getIdParamApplic(), "ENTE",
                            paramApplicRowBean.getString("ds_valore_param_applic_ente_cons"));
                }
            }
            for (IamParamApplicRowBean paramApplicRowBean : parametriGestione) {
                if (paramApplicRowBean.getString("ds_valore_param_applic_ente_gest") != null
                        && !paramApplicRowBean.getString("ds_valore_param_applic_ente_gest").equals("")) {
                    insertIamValoreParamApplic(null, ente, paramApplicRowBean.getIdParamApplic(), "ENTE",
                            paramApplicRowBean.getString("ds_valore_param_applic_ente_gest"));
                }
            }
            // idEnteConvenz
            LOGGER.debug("Salvataggio dell'ente completato");
            idEnteConvenz = ente.getIdEnteSiam();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(idEnteConvenz), param.getNomePagina());
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio dell'ente convenzionato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'ente convenzionato");
        }
        return idEnteConvenz;
    }

    private void insertIamValoreParamApplic(OrgAmbienteEnteConvenz ambienteEnteConvenz, OrgEnteSiam enteSiam,
            BigDecimal idParamApplic, String tiAppart, String dsValoreParamApplic) {
        IamValoreParamApplic valoreParamApplic = new IamValoreParamApplic();
        valoreParamApplic.setIamParamApplic(helper.findById(IamParamApplic.class, idParamApplic));
        valoreParamApplic.setDsValoreParamApplic(dsValoreParamApplic);
        valoreParamApplic.setTiAppart(tiAppart);
        valoreParamApplic.setOrgEnteSiam(enteSiam);
        valoreParamApplic.setOrgAmbienteEnteConvenz(ambienteEnteConvenz);
        helper.insertEntity(valoreParamApplic, true);
    }// param
     // saveEnteConvenzionato

    /**
     * Metodo di update di un ente convenzionato
     *
     * @param param
     *            parametri per il logging
     * @param idEnteConvenzionato
     *            id ente convenzionato
     * @param creazioneBean
     *            bean {@link EnteConvenzionatoBean}
     * @param parametriAmministrazione
     *            table bean {@link IamParamApplicTableBean}
     * @param parametriConservazione
     *            table bean {@link IamParamApplicTableBean}
     * @param parametriGestione
     *            table bean {@link IamParamApplicTableBean}
     * @param idUserIamCor
     *            id user IAM
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveEnteConvenzionato(LogParam param, BigDecimal idEnteConvenzionato,
            EnteConvenzionatoBean creazioneBean, IamParamApplicTableBean parametriAmministrazione,
            IamParamApplicTableBean parametriConservazione, IamParamApplicTableBean parametriGestione,
            long idUserIamCor) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio delle modifiche sull'ente convenzionato");
        // Controllo esistenza denominazione
        OrgEnteSiam enteSiam = helper.getOrgEnteConvenz(creazioneBean.getNm_ente_convenz());
        if (enteSiam != null && idEnteConvenzionato.longValue() != enteSiam.getIdEnteSiam()) {
            if (enteSiam.getTiEnte().name().equals("CONVENZIONATO")) {
                throw new ParerUserError("Ente gi\u00E0 censito nel sistema come convenzionato");
            } else {
                throw new ParerUserError("Ente gi\u00E0 censito nel sistema come non convenzionato");
            }
        }
        // Controllo esistenza codice e tipo codice ente, ovviamente escludendo il record stesso
        OrgEnteSiam ente = helper.findById(OrgEnteSiam.class, idEnteConvenzionato);
        Date dtIniValOld = ente.getDtIniVal();
        Date dtFineValOld = ente.getDtCessazione();
        BigDecimal idAmbienteEnteConvenz = BigDecimal
                .valueOf(ente.getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz());
        if (helper.getOrgEnteConvenz(creazioneBean.getCd_ente_convenz(), creazioneBean.getTi_cd_ente_convenz()) != null
                && (!ente.getCdEnteConvenz().equals(creazioneBean.getCd_ente_convenz())
                        || !ente.getTiCdEnteConvenz().equals(creazioneBean.getTi_cd_ente_convenz()))) {
            throw new ParerUserError("Ente convenzionato gi\u00E0 censito nel sistema " + "con codice "
                    + creazioneBean.getCd_ente_convenz() + " e tipo codice " + creazioneBean.getTi_cd_ente_convenz());
        }

        // Se ho aggiornato la data di inizio/fine validità , aggiorno la data di inizio/fine validità 
        // delle strutture associate, dei collegamenti e dei supporti esterni
        if ((dtFineValOld != null && dtFineValOld.compareTo(creazioneBean.getDt_cessazione()) != 0)
                || (dtFineValOld == null && creazioneBean.getDt_cessazione() != null)
                || (dtIniValOld != null && dtIniValOld.compareTo(creazioneBean.getDt_ini_val()) != 0)
                || (dtIniValOld == null && creazioneBean.getDt_ini_val() != null)) {
            // Aggiorno la data di inizio / fine validità  delle associazioni struttura / ente
            for (OrgEnteConvenzOrg enteConvenzOrg : ente.getOrgEnteConvenzOrgs()) {
                if (enteConvenzOrg.getDtFineVal().after(creazioneBean.getDt_cessazione())) {
                    if (creazioneBean.getDt_cessazione().before(enteConvenzOrg.getDtIniVal())) {
                        throw new ParerUserError(
                                "Verificare le date di associazione organizzazione/ente prima di procedere con la operazione");
                    }
                    enteConvenzOrg.setDtFineVal(creazioneBean.getDt_cessazione());
                }
                if (enteConvenzOrg.getDtIniVal().before(creazioneBean.getDt_ini_val())) {
                    if (creazioneBean.getDt_ini_val().after(enteConvenzOrg.getDtFineVal())) {
                        throw new ParerUserError(
                                "Verificare le date di associazione organizzazione/ente prima di procedere con la ¢operazione");
                    }
                    enteConvenzOrg.setDtIniVal(creazioneBean.getDt_ini_val());
                }
            }
            // Aggiorno la data di inizio / fine validità  dei collegamenti
            for (OrgAppartCollegEnti appart : ente.getOrgAppartCollegEntis()) {
                if (appart.getDtFinVal().after(creazioneBean.getDt_cessazione())) {
                    if (creazioneBean.getDt_cessazione().before(appart.getDtIniVal())) {
                        throw new ParerUserError(
                                "Verificare le date di associazione del collegamento prima di procedere con la operazione");
                    }
                    appart.setDtFinVal(creazioneBean.getDt_cessazione());
                }
                if (appart.getDtIniVal().before(creazioneBean.getDt_ini_val())) {
                    if (creazioneBean.getDt_ini_val().after(appart.getDtFinVal())) {
                        throw new ParerUserError(
                                "Verificare le date di associazione del collegamento prima di procedere con la operazione");
                    }
                    appart.setDtIniVal(creazioneBean.getDt_ini_val());
                }
            }
            // Aggiorno la data di inizio / fine validitÃƒÆ’Ã‚Â  dei supporti esterni
            for (OrgSuptEsternoEnteConvenz suptEsterno : ente.getOrgSuptEsternoEnteConvenzByIdEnteProduts()) {
                if (suptEsterno.getDtFinVal().after(creazioneBean.getDt_cessazione())) {
                    if (creazioneBean.getDt_cessazione().before(suptEsterno.getDtIniVal())) {
                        throw new ParerUserError(
                                "Verificare le date di associazione del supporto prima di procedere con la operazione");
                    }
                    suptEsterno.setDtFinVal(creazioneBean.getDt_cessazione());
                }
                if (suptEsterno.getDtIniVal().before(creazioneBean.getDt_ini_val())) {
                    if (creazioneBean.getDt_ini_val().after(suptEsterno.getDtFinVal())) {
                        throw new ParerUserError(
                                "Verificare le date di associazione del supporto prima di procedere con la operazione");
                    }
                    suptEsterno.setDtIniVal(creazioneBean.getDt_ini_val());
                }
            }
        }

        try {
            // Se l'ente \u00E8 cessato, aggiorno la data di fine validità  dell'appartenenza dell'ente ad
            // eventuali
            // collegamenti e supporti esterni
            if (creazioneBean.getDt_cessazione().before(new Date())) {
                for (OrgAppartCollegEnti appart : ente.getOrgAppartCollegEntis()) {
                    appart.setDtFinVal(creazioneBean.getDt_cessazione());
                } // suptEsterno
                for (OrgSuptEsternoEnteConvenz suptEsterno : ente.getOrgSuptEsternoEnteConvenzByIdEnteProduts()) {
                    suptEsterno.setDtFinVal(creazioneBean.getDt_cessazione());
                }
            }

            // In caso di CAMBIO AMBIENTE, prima storicizzo se possibile, quindi eseguo
            // l'aggiornamento delle abilitazioni
            if (idAmbienteEnteConvenz != null
                    && idAmbienteEnteConvenz.compareTo(creazioneBean.getId_ambiente_ente_convenz()) != 0) {

                if (creazioneBean.getDt_ini_val_appart_ambiente()
                        .compareTo(creazioneBean.getDt_fin_val_appart_ambiente()) > 0) {
                    throw new ParerUserError(
                            "La data di inizio validità appartenenza ambiente non può essere inferiore a quella di fine validità");
                }

                /*
                 * STORICIZZAZIONE AMBIENTE
                 */
                // Se sono state modificate le date di inizio / fine validità di appartenenza
                // dell'ente convenzionato all’ambiente
                if (creazioneBean.getDt_ini_val_appart_ambiente().compareTo(ente.getDtIniValAppartAmbiente()) != 0
                        || creazioneBean.getDt_fin_val_appart_ambiente()
                                .compareTo(ente.getDtFinValAppartAmbiente()) != 0) {

                    if (isStessoIntervallo(creazioneBean.getDt_ini_val_appart_ambiente(),
                            creazioneBean.getDt_fin_val_appart_ambiente(), ente.getDtIniValAppartAmbiente(),
                            ente.getDtFinValAppartAmbiente())) {
                        throw new ParerUserError(
                                "Le date di inizio e di fine validità di appartenenza all’ambiente si sovrappongono a quelle definite sull’attuale appartenenza");
                    }

                    if (isStoricoPresente(idEnteConvenzionato.longValue(),
                            ente.getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz(),
                            creazioneBean.getDt_ini_val_appart_ambiente(),
                            creazioneBean.getDt_fin_val_appart_ambiente())) {
                        throw new ParerUserError(
                                "Le date di inizio e di fine validità di appartenenza all’ambiente si sovrappongono a quelle definite su una precedente appartenenza");
                    }

                    // MAC 26749 - deve essere possibile introdurre un periodo di appartenenza antecedente a quello
                    // corrente
                    // if (creazioneBean.getDt_ini_val_appart_ambiente().compareTo(ente.getDtIniValAppartAmbiente()) < 0
                    // && creazioneBean.getDt_fin_val_appart_ambiente()
                    // .compareTo(ente.getDtFinValAppartAmbiente()) < 0) {
                    // throw new ParerUserError(
                    // "Le date di inizio e fine validità impostate sull’ambiente sono inferiori a quelle
                    // precedentemente inserite");
                    // }
                }

                if (creazioneBean.getDt_ini_val_appart_ambiente().compareTo(ente.getDtIniValAppartAmbiente()) != 0
                        || creazioneBean.getDt_fin_val_appart_ambiente()
                                .compareTo(ente.getDtFinValAppartAmbiente()) != 0) {
                    OrgStoEnteAmbienteConvenz storicoEnteAmbiente = new OrgStoEnteAmbienteConvenz();
                    storicoEnteAmbiente.setOrgEnteConvenz(ente);
                    storicoEnteAmbiente.setOrgAmbienteEnteConvenz(ente.getOrgAmbienteEnteConvenz());
                    storicoEnteAmbiente.setDtIniVal(creazioneBean.getDt_ini_val_appart_ambiente());
                    storicoEnteAmbiente.setDtFinVal(creazioneBean.getDt_fin_val_appart_ambiente());
                    helper.insertEntity(storicoEnteAmbiente, true);
                }

                List<Object[]> utenti = userHelper.getUtentiDichAbilUnAmbiente(idAmbienteEnteConvenz);

                // Elimino le abilitazioni derivanti dalla dichiarazione
                for (Object[] utenteDich : utenti) {
                    long idUserIam = (Long) utenteDich[0];
                    long idDichAbilOrganiz = (Long) utenteDich[1];
                    userHelper.deleteAbilitazioniUtenteUnAmbiente(idEnteConvenzionato.longValue(), idUserIam,
                            idDichAbilOrganiz);
                }

                // Aggiorno l'ambiente ente convenzionato definito sull'ente
                // produttore dando il valore dell'ambiente
                // scelto in inserimento accordo
                OrgAmbienteEnteConvenz ambienteEnteConvenzNew = (OrgAmbienteEnteConvenz) helper
                        .findById(OrgAmbienteEnteConvenz.class, creazioneBean.getId_ambiente_ente_convenz());
                ente.setOrgAmbienteEnteConvenz(ambienteEnteConvenzNew);

                // Determina gli utenti per cui è definita una dichiarazione di abilitazione relativa
                // all'ambiente dell'ente dopo della modifica
                utenti = userHelper.getUtentiDichAbilUnAmbiente(creazioneBean.getId_ambiente_ente_convenz());

                // Inserisco le dichiarazioni di abilitazione e le abilitazioni enti convenzionati per il nuovo ambiente
                for (Object[] utenteDich : utenti) {
                    long idUserIam = (Long) utenteDich[0];
                    userHelper.aggiungiAbilEnteToAdd(idUserIam);
                }
            } // Se NON ho effettuato un CAMBIO AMBIENTE
            else {
                if (creazioneBean.getDt_ini_val_appart_ambiente()
                        .compareTo(creazioneBean.getDt_fin_val_appart_ambiente()) > 0) {
                    throw new ParerUserError(
                            "La data di inizio validità appartenenza ambiente non può essere inferiore a quella di fine validità");
                }
                // Eseguo i controlli sulle sovrapposizioni di date ed eventuali precedenti appartenenze
                // Se sono state modificate le date di inizio / fine validità di appartenenza
                // dell'ente convenzionato all’ambiente
                if (creazioneBean.getDt_ini_val_appart_ambiente().compareTo(ente.getDtIniValAppartAmbiente()) != 0
                        || creazioneBean.getDt_fin_val_appart_ambiente()
                                .compareTo(ente.getDtFinValAppartAmbiente()) != 0) {
                    // MAC 26749 - NON ho modificato l'ambiente, niente controlli di coerenza sulle date
                    // if (isStessoIntervallo(creazioneBean.getDt_ini_val_appart_ambiente(),
                    // creazioneBean.getDt_fin_val_appart_ambiente(), ente.getDtIniValAppartAmbiente(),
                    // ente.getDtFinValAppartAmbiente())) {
                    // throw new ParerUserError(
                    // "Le date di inizio e di fine validità di appartenenza all’ambiente si sovrappongono a quelle
                    // definite sull’attuale appartenenza");
                    // }
                    //
                    // if (isStoricoPresente(idEnteConvenzionato.longValue(),
                    // ente.getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz(),
                    // creazioneBean.getDt_ini_val_appart_ambiente(),
                    // creazioneBean.getDt_fin_val_appart_ambiente())) {
                    // throw new ParerUserError(
                    // "Le date di inizio e di fine validità di appartenenza all’ambiente si sovrappongono a quelle
                    // definite su una precedente appartenenza");
                    // }
                    //
                    // if (creazioneBean.getDt_ini_val_appart_ambiente().compareTo(ente.getDtIniValAppartAmbiente()) < 0
                    // && creazioneBean.getDt_fin_val_appart_ambiente()
                    // .compareTo(ente.getDtFinValAppartAmbiente()) < 0) {
                    // throw new ParerUserError(
                    // "Le date di inizio e fine validità impostate sull’ambiente sono inferiori a quelle
                    // precedentemente inserite");
                    // }
                }
            }

            setDatiEnteConvenzionato(ente, creazioneBean);

            // Gestione parametri amministrazione
            manageParametriPerEnte(parametriAmministrazione, "ds_valore_param_applic_ente_amm", ente);
            // Gestione parametri conservazione
            manageParametriPerEnte(parametriConservazione, "ds_valore_param_applic_ente_cons", ente);
            // Gestione parametri gestione
            manageParametriPerEnte(parametriGestione, "ds_valore_param_applic_ente_gest", ente);

            helper.getEntityManager().flush();

            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    idEnteConvenzionato, param.getNomePagina());
            List<ObjectsToLogBefore> listaOggettiDaLoggare = sacerLogEjb.logBefore(param.getTransactionLogContext(),
                    param.getNomeApplicazione(), param.getNomeUtente(), param.getNomeAzione(),
                    SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO, idEnteConvenzionato, param.getNomePagina());
            sacerLogEjb.logAfter(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), listaOggettiDaLoggare, param.getNomePagina());
            LOGGER.debug("Salvataggio dell'ente convenzionato completato");
        } catch (ParerUserError ex) {
            throw new ParerUserError(ex.getDescription());
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio dell'ente convenzionato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'ente convenzionato");
        }
    }

    private boolean isStoricoPresente(long idEnteSiam, long idAmbienteEnteConvenzExcluded, Date dtIniValAppartAmbiente,
            Date dtFinValAppartAmbiente) {
        return helper.isStoricoPresente(idEnteSiam, idAmbienteEnteConvenzExcluded, dtIniValAppartAmbiente,
                dtFinValAppartAmbiente);
    }

    private boolean isStessoIntervallo(Date dtIniValAppartAmbiente, Date dtFinValAppartAmbiente,
            Date dtIniValAppartAmbienteDB, Date dtFinValAppartAmbienteDB) {
        return (dtIniValAppartAmbiente.compareTo(dtIniValAppartAmbienteDB) >= 0
                && dtIniValAppartAmbiente.compareTo(dtFinValAppartAmbienteDB) <= 0)
                || (dtFinValAppartAmbiente.compareTo(dtIniValAppartAmbienteDB) >= 0
                        && dtFinValAppartAmbiente.compareTo(dtFinValAppartAmbienteDB) <= 0);
    }

    private void manageParametriPerEnte(IamParamApplicTableBean paramApplicTableBean, String nomeCampoValoreParamApplic,
            OrgEnteSiam enteConvenz) {
        for (IamParamApplicRowBean paramApplicRowBean : paramApplicTableBean) {
            // Cancello il parametro se eliminato
            if (paramApplicRowBean.getBigDecimal("id_valore_param_applic") != null
                    && (paramApplicRowBean.getString(nomeCampoValoreParamApplic) == null
                            || paramApplicRowBean.getString(nomeCampoValoreParamApplic).equals(""))) {
                IamValoreParamApplic parametro = helper.findById(IamValoreParamApplic.class,
                        paramApplicRowBean.getBigDecimal("id_valore_param_applic"));
                helper.removeEntity(parametro, true);
            } // Modifico il parametro se modificato
            else if (paramApplicRowBean.getBigDecimal("id_valore_param_applic") != null
                    && paramApplicRowBean.getString(nomeCampoValoreParamApplic) != null
                    && !paramApplicRowBean.getString(nomeCampoValoreParamApplic).equals("")
                    // MEV26588
                    && !paramApplicRowBean.getString(nomeCampoValoreParamApplic)
                            .equals(it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING)) {
                IamValoreParamApplic parametro = helper.findById(IamValoreParamApplic.class,
                        paramApplicRowBean.getBigDecimal("id_valore_param_applic"));
                parametro.setDsValoreParamApplic(paramApplicRowBean.getString(nomeCampoValoreParamApplic));
            } // Inserisco il parametro se nuovo
            else if (paramApplicRowBean.getBigDecimal("id_valore_param_applic") == null
                    && paramApplicRowBean.getString(nomeCampoValoreParamApplic) != null
                    && !paramApplicRowBean.getString(nomeCampoValoreParamApplic).equals("")) {
                insertIamValoreParamApplic(null, enteConvenz, paramApplicRowBean.getBigDecimal("id_param_applic"),
                        "ENTE", paramApplicRowBean.getString(nomeCampoValoreParamApplic));
            }
        }
    }

    private void manageParametriPerAmbiente(IamParamApplicTableBean paramApplicTableBean,
            String nomeCampoValoreParamApplic, OrgAmbienteEnteConvenz ambienteEnteConvenz) {
        for (IamParamApplicRowBean paramApplicRowBean : paramApplicTableBean) {
            // Cancello il parametro se eliminato
            if (paramApplicRowBean.getBigDecimal("id_valore_param_applic") != null
                    && (paramApplicRowBean.getString(nomeCampoValoreParamApplic) == null
                            || paramApplicRowBean.getString(nomeCampoValoreParamApplic).equals(""))) {
                IamValoreParamApplic parametro = helper.findById(IamValoreParamApplic.class,
                        paramApplicRowBean.getBigDecimal("id_valore_param_applic"));
                helper.removeEntity(parametro, true);
            } // Modifico il parametro se modificato
            else if (paramApplicRowBean.getBigDecimal("id_valore_param_applic") != null
                    && paramApplicRowBean.getString(nomeCampoValoreParamApplic) != null
                    && !paramApplicRowBean.getString(nomeCampoValoreParamApplic).equals("")
                    // MEV26588
                    && !paramApplicRowBean.getString(nomeCampoValoreParamApplic)
                            .equals(it.eng.saceriam.web.util.Constants.OBFUSCATED_STRING)) {
                IamValoreParamApplic parametro = helper.findById(IamValoreParamApplic.class,
                        paramApplicRowBean.getBigDecimal("id_valore_param_applic"));
                parametro.setDsValoreParamApplic(paramApplicRowBean.getString(nomeCampoValoreParamApplic));
            } // Inserisco il parametro se nuovo
            else if (paramApplicRowBean.getBigDecimal("id_valore_param_applic") == null
                    && paramApplicRowBean.getString(nomeCampoValoreParamApplic) != null
                    && !paramApplicRowBean.getString(nomeCampoValoreParamApplic).equals("")) {
                insertIamValoreParamApplic(ambienteEnteConvenz, null,
                        paramApplicRowBean.getBigDecimal("id_param_applic"), "AMBIENTE",
                        paramApplicRowBean.getString(nomeCampoValoreParamApplic));
            }
        }
    }

    private void setDatiEnteConvenzionato(OrgEnteSiam ente, EnteConvenzionatoBean creazioneBean) {
        // Imposta tutti i campi dell'ente
        ente.setNmEnteSiam(creazioneBean.getNm_ente_convenz());
        ente.setCdEnteConvenz(creazioneBean.getCd_ente_convenz());
        ente.setTiCdEnteConvenz(creazioneBean.getTi_cd_ente_convenz());
        ente.setDtIniVal(creazioneBean.getDt_ini_val());
        ente.setDtCessazione(creazioneBean.getDt_cessazione());
        ente.setDtRichModuloInfo(creazioneBean.getDt_rich_modulo_info());
        ente.setDsViaSedeLegale(creazioneBean.getDs_via_sede_legale());
        ente.setCdCapSedeLegale(creazioneBean.getCd_cap_sede_legale());
        ente.setDsCittaSedeLegale(creazioneBean.getDs_citta_sede_legale());
        ente.setCdNazioneSedeLegale(creazioneBean.getCd_nazione_sede_legale());
        ente.setIdProvSedeLegale(creazioneBean.getId_prov_sede_legale());
        ente.setCdFisc(creazioneBean.getCd_fisc());
        ente.setCdUfe(creazioneBean.getCd_ufe());
        ente.setDsUfe(creazioneBean.getDs_ufe());
        ente.setTiModPagam(creazioneBean.getTi_mod_pagam());
        BigDecimal idCdIva;
        if ((idCdIva = creazioneBean.getId_cd_iva()) != null) {
            OrgCdIva cdIva = helper.findById(OrgCdIva.class, idCdIva);
            ente.setOrgCdIva(cdIva);
        } else {
            ente.setOrgCdIva(null);
        }
        ente.setIdCategEnte(creazioneBean.getDs_categ_ente());
        OrgEnteSiam enteSiam = null;
        if (creazioneBean.getId_ente_convenz_nuovo() != null) {
            enteSiam = helper.findById(OrgEnteSiam.class, creazioneBean.getId_ente_convenz_nuovo());
        }
        ente.setOrgEnteSiamByIdEnteConvenzNuovo(enteSiam);
        //
        OrgAmbienteEnteConvenz ambienteEnteConvenz = null;
        if (creazioneBean.getId_ambiente_ente_convenz() != null) {
            ambienteEnteConvenz = helper.findById(OrgAmbienteEnteConvenz.class,
                    creazioneBean.getId_ambiente_ente_convenz());
        }
        ente.setOrgAmbienteEnteConvenz(ambienteEnteConvenz);
        ente.setDtIniValAppartAmbiente(creazioneBean.getDt_ini_val_appart_ambiente());
        ente.setDtFinValAppartAmbiente(creazioneBean.getDt_fin_val_appart_ambiente());
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

        ente.setDsNote(creazioneBean.getDs_note());
    }

    public boolean checkAccordoValido(BigDecimal idEnteConvenz) {
        boolean existsAccordo = false;
        if ((userHelper.getLastAccordoEnte(idEnteConvenz, new Date())) != null) {
            existsAccordo = true;
        }
        return existsAccordo;
    }

    public boolean checkAccordoValido(BigDecimal idEnteConvenz, Date dtVal) {
        boolean existsAccordo = false;
        if ((userHelper.getLastAccordoEnte(idEnteConvenz, dtVal)) != null) {
            existsAccordo = true;
        }
        return existsAccordo;
    }

    public boolean checkAccordoChiuso(BigDecimal idAccordoEnte) {
        boolean accordoChiuso = false;
        if ((helper.findById(OrgAccordoEnte.class, idAccordoEnte)).getFlAccordoChiuso().equals("1")) {
            accordoChiuso = true;
        }
        return accordoChiuso;
    }

    private void setDatiAccordoWizard(BigDecimal idEnteConvenz, OrgAccordoEnte accordo,
            AccordoWizardDetail accordoWizardDetail) throws EMFError {
        // Imposta tutti i campi dell'accordo
        accordo.setCdRegistroRepertorio(accordoWizardDetail.getCd_registro_repertorio().parse());
        accordo.setAaRepertorio(accordoWizardDetail.getAa_repertorio().parse());
        accordo.setCdKeyRepertorio(accordoWizardDetail.getCd_key_repertorio().parse());
        accordo.setCdRegistroDetermina(accordoWizardDetail.getCd_registro_determina().parse());
        accordo.setAaDetermina(accordoWizardDetail.getAa_determina().parse());
        accordo.setCdKeyDetermina(accordoWizardDetail.getCd_key_determina().parse());
        accordo.setDsAttoAccordo(accordoWizardDetail.getDs_atto_accordo().parse());
        accordo.setDtAttoAccordo(accordoWizardDetail.getDt_atto_accordo().parse());
        accordo.setDtDetermina(accordoWizardDetail.getDt_determina().parse());
        accordo.setDtRegAccordo(accordoWizardDetail.getDt_reg_accordo().parse());
        accordo.setDtDecAccordo(accordoWizardDetail.getDt_dec_accordo().parse());
        accordo.setDtScadAccordo(accordoWizardDetail.getDt_scad_accordo().parse());
        accordo.setDtFineValidAccordo(accordoWizardDetail.getDt_fine_valid_accordo().parse());
        accordo.setDsFirmatarioEnte(accordoWizardDetail.getDs_firmatario_ente().parse());
        accordo.setFlPagamento(accordoWizardDetail.getFl_pagamento().parse());
        accordo.setNiAbitanti(accordoWizardDetail.getNi_abitanti().parse());
        accordo.setDsNoteAccordo(accordoWizardDetail.getDs_note_accordo().parse());
        accordo.setCdClienteFatturazione(accordoWizardDetail.getCd_cliente_fatturazione().parse());
        accordo.setCdCig(accordoWizardDetail.getCd_cig().parse());
        accordo.setCdCup(accordoWizardDetail.getCd_cup().parse());
        accordo.setDtDecAccordoInfo(accordoWizardDetail.getDt_dec_accordo_info().parse());
        accordo.setCdRifContab(accordoWizardDetail.getCd_rif_contab().parse());
        accordo.setFlRecesso(accordoWizardDetail.getFl_recesso().parse());
        accordo.setDsNotaRecesso(accordoWizardDetail.getDs_nota_recesso().parse());
        accordo.setDsNotaFatturazione(accordoWizardDetail.getDs_nota_fatturazione().parse());
        BigDecimal idClusterAccordo;
        if ((idClusterAccordo = accordoWizardDetail.getId_cluster_accordo().parse()) != null) {
            OrgClusterAccordo clusterAccordo = helper.findById(OrgClusterAccordo.class, idClusterAccordo);
            accordo.setOrgClusterAccordo(clusterAccordo);
        } else {
            accordo.setOrgClusterAccordo(null);
        }
        BigDecimal idFasciaStorageStandardAccordo;
        if ((idFasciaStorageStandardAccordo = accordoWizardDetail.getId_fascia_storage_standard_accordo()
                .parse()) != null) {
            OrgFasciaStorageAccordo fasciaStorageStandardAccordo = helper.findById(OrgFasciaStorageAccordo.class,
                    idFasciaStorageStandardAccordo);
            accordo.setOrgFasciaStorageStandardAccordo(fasciaStorageStandardAccordo);
        } else {
            accordo.setOrgFasciaStorageStandardAccordo(null);
        }
        BigDecimal idFasciaStorageManualeAccordo;
        if ((idFasciaStorageManualeAccordo = accordoWizardDetail.getId_fascia_storage_manuale_accordo()
                .parse()) != null) {
            OrgFasciaStorageAccordo fasciaStorageManualeAccordo = helper.findById(OrgFasciaStorageAccordo.class,
                    idFasciaStorageManualeAccordo);
            accordo.setOrgFasciaStorageManualeAccordo(fasciaStorageManualeAccordo);
        } else {
            accordo.setOrgFasciaStorageManualeAccordo(null);
        }
        accordo.setNiRefertiManuale(accordoWizardDetail.getNi_referti_manuale().parse());
        accordo.setNiRefertiStandard(accordoWizardDetail.getNi_referti_standard().parse());
        accordo.setNiTipoUdManuale(accordoWizardDetail.getNi_tipo_ud_manuale().parse());
        accordo.setNiTipoUdStandard(accordoWizardDetail.getNi_tipo_ud_standard().parse());
        accordo.setNiStudioDicom(accordoWizardDetail.getNi_studio_dicom().parse());
        accordo.setDsNotaAttivazione(accordoWizardDetail.getDs_nota_attivazione().parse());
        accordo.setNmEnte(accordoWizardDetail.getNm_ente().parse());
        accordo.setNmStrut(accordoWizardDetail.getNm_strut().parse());
        accordo.setDsNoteEnteAccordo(accordoWizardDetail.getDs_note_ente_accordo().parse());
        accordo.setNiStudioDicomPrev(accordoWizardDetail.getNi_studio_dicom_prev().parse());
        accordo.setImAttivDocAmm(accordoWizardDetail.getIm_attiv_doc_amm().parse());
        accordo.setImAttivDocSani(accordoWizardDetail.getIm_attiv_doc_sani().parse());
        accordo.setTiScopoAccordo(TiScopoAccordo.valueOf(accordoWizardDetail.getTi_scopo_accordo().parse()));
        //
        OrgEnteSiam enteSiam = helper.findById(OrgEnteSiam.class, idEnteConvenz);
        accordo.setOrgEnteSiam(enteSiam);
        BigDecimal idTipoAccordo;
        if ((idTipoAccordo = accordoWizardDetail.getId_tipo_accordo().parse()) != null) {
            OrgTipoAccordo tipoAccordo = helper.findById(OrgTipoAccordo.class, idTipoAccordo);
            accordo.setOrgTipoAccordo(tipoAccordo);
        } else {
            accordo.setOrgTipoAccordo(null);
        }
        BigDecimal idTariffario;
        if ((idTariffario = accordoWizardDetail.getId_tariffario().parse()) != null) {
            OrgTariffario tariffario = helper.findById(OrgTariffario.class, idTariffario);
            accordo.setOrgTariffario(tariffario);
        } else {
            accordo.setOrgTariffario(null);
        }
        //
        BigDecimal idClasseEnteConvenz;
        if ((idClasseEnteConvenz = accordoWizardDetail.getId_classe_ente_convenz().parse()) != null) {
            OrgClasseEnteConvenz classeEnteConvenz = helper.findById(OrgClasseEnteConvenz.class, idClasseEnteConvenz);
            accordo.setOrgClasseEnteConvenz(classeEnteConvenz);
        } else {
            accordo.setOrgClasseEnteConvenz(null);
        }
        //
        BigDecimal idEnteConvenzAmministratore;
        if ((idEnteConvenzAmministratore = accordoWizardDetail.getId_ente_convenz_amministratore().parse()) != null) {
            OrgEnteSiam enteSiamByIdEnteConvenzAmministratore = helper.findById(OrgEnteSiam.class,
                    idEnteConvenzAmministratore);
            accordo.setOrgEnteSiamByIdEnteConvenzAmministratore(enteSiamByIdEnteConvenzAmministratore);
        } else {
            // TODO: VERIFICARE
            accordo.setOrgEnteSiamByIdEnteConvenzAmministratore(null);
            // end TODO
        }
        BigDecimal idEnteConvenzConserv;
        if ((idEnteConvenzConserv = accordoWizardDetail.getId_ente_convenz_conserv().parse()) != null) {
            OrgEnteSiam enteSiamByIdEnteConvenzConserv = helper.findById(OrgEnteSiam.class, idEnteConvenzConserv);
            accordo.setOrgEnteSiamByIdEnteConvenzConserv(enteSiamByIdEnteConvenzConserv);
        } else {
            // TODO: VERIFICARE
            accordo.setOrgEnteSiamByIdEnteConvenzConserv(null);
            // end TODO
        }
        BigDecimal idEnteConvenzGestore;
        if ((idEnteConvenzGestore = accordoWizardDetail.getId_ente_convenz_gestore().parse()) != null) {
            OrgEnteSiam enteSiamByIdEnteConvenzGestore = helper.findById(OrgEnteSiam.class, idEnteConvenzGestore);
            accordo.setOrgEnteSiamByIdEnteConvenzGestore(enteSiamByIdEnteConvenzGestore);
        } else {
            // TODO: VERIFICARE
            accordo.setOrgEnteSiamByIdEnteConvenzGestore(null);
            // end TODO
        }
    }

    private void setDatiAccordo(BigDecimal idEnteConvenz, OrgAccordoEnte accordo, AccordoDetail accordoDetail)
            throws EMFError {
        // Imposta tutti i campi dell'accordo
        accordo.setCdRegistroRepertorio(accordoDetail.getCd_registro_repertorio().parse());
        accordo.setAaRepertorio(accordoDetail.getAa_repertorio().parse());
        accordo.setCdKeyRepertorio(accordoDetail.getCd_key_repertorio().parse());
        accordo.setCdRegistroDetermina(accordoDetail.getCd_registro_determina().parse());
        accordo.setAaDetermina(accordoDetail.getAa_determina().parse());
        accordo.setCdKeyDetermina(accordoDetail.getCd_key_determina().parse());
        accordo.setDsAttoAccordo(accordoDetail.getDs_atto_accordo().parse());
        accordo.setDtAttoAccordo(accordoDetail.getDt_atto_accordo().parse());
        accordo.setDtDetermina(accordoDetail.getDt_determina().parse());
        accordo.setDtRegAccordo(accordoDetail.getDt_reg_accordo().parse());
        accordo.setDtDecAccordo(accordoDetail.getDt_dec_accordo().parse());
        accordo.setDtScadAccordo(accordoDetail.getDt_scad_accordo().parse());
        accordo.setDtFineValidAccordo(accordoDetail.getDt_fine_valid_accordo().parse());
        accordo.setDsFirmatarioEnte(accordoDetail.getDs_firmatario_ente().parse());
        accordo.setFlPagamento(accordoDetail.getFl_pagamento().parse());
        accordo.setNiAbitanti(accordoDetail.getNi_abitanti().parse());
        accordo.setDsNoteAccordo(accordoDetail.getDs_note_accordo().parse());
        accordo.setCdClienteFatturazione(accordoDetail.getCd_cliente_fatturazione().parse());
        accordo.setCdCig(accordoDetail.getCd_cig().parse());
        accordo.setCdCup(accordoDetail.getCd_cup().parse());
        accordo.setDtDecAccordoInfo(accordoDetail.getDt_dec_accordo_info().parse());
        accordo.setCdRifContab(accordoDetail.getCd_rif_contab().parse());
        accordo.setCdOda(accordoDetail.getCd_oda().parse());
        accordo.setFlRecesso(accordoDetail.getFl_recesso().parse());
        accordo.setDsNotaRecesso(accordoDetail.getDs_nota_recesso().parse());
        accordo.setDsNotaFatturazione(accordoDetail.getDs_nota_fatturazione().parse());
        BigDecimal idClusterAccordo;
        if ((idClusterAccordo = accordoDetail.getId_cluster_accordo().parse()) != null) {
            OrgClusterAccordo clusterAccordo = helper.findById(OrgClusterAccordo.class, idClusterAccordo);
            accordo.setOrgClusterAccordo(clusterAccordo);
        } else {
            accordo.setOrgClusterAccordo(null);
        }
        BigDecimal idFasciaStorageStandardAccordo;
        if ((idFasciaStorageStandardAccordo = accordoDetail.getId_fascia_storage_standard_accordo().parse()) != null) {
            OrgFasciaStorageAccordo fasciaStorageStandardAccordo = helper.findById(OrgFasciaStorageAccordo.class,
                    idFasciaStorageStandardAccordo);
            accordo.setOrgFasciaStorageStandardAccordo(fasciaStorageStandardAccordo);
        } else {
            accordo.setOrgFasciaStorageStandardAccordo(null);
        }
        BigDecimal idFasciaStorageManualeAccordo;
        if ((idFasciaStorageManualeAccordo = accordoDetail.getId_fascia_storage_manuale_accordo().parse()) != null) {
            OrgFasciaStorageAccordo fasciaStorageManualeAccordo = helper.findById(OrgFasciaStorageAccordo.class,
                    idFasciaStorageManualeAccordo);
            accordo.setOrgFasciaStorageManualeAccordo(fasciaStorageManualeAccordo);
        } else {
            accordo.setOrgFasciaStorageManualeAccordo(null);
        }
        accordo.setNiRefertiManuale(accordoDetail.getNi_referti_manuale().parse());
        accordo.setNiRefertiStandard(accordoDetail.getNi_referti_standard().parse());
        accordo.setNiTipoUdManuale(accordoDetail.getNi_tipo_ud_manuale().parse());
        accordo.setNiTipoUdStandard(accordoDetail.getNi_tipo_ud_standard().parse());
        accordo.setNiStudioDicom(accordoDetail.getNi_studio_dicom().parse());
        accordo.setDsNotaAttivazione(accordoDetail.getDs_nota_attivazione().parse());
        accordo.setDsNoteEnteAccordo(accordoDetail.getDs_note_ente_accordo().parse());
        accordo.setNiStudioDicomPrev(accordoDetail.getNi_studio_dicom_prev().parse());
        // setImAttivDocAmm
        accordo.setImAttivDocAmm(accordoDetail.getIm_attiv_doc_amm().parse());
        accordo.setImAttivDocSani(accordoDetail.getIm_attiv_doc_sani().parse());
        accordo.setNmEnte(accordoDetail.getNm_ente().parse());
        accordo.setNmStrut(accordoDetail.getNm_strut().parse());
        accordo.setTiScopoAccordo(TiScopoAccordo.valueOf(accordoDetail.getTi_scopo_accordo().parse()));
        //
        OrgEnteSiam enteSiam = helper.findById(OrgEnteSiam.class, idEnteConvenz);
        accordo.setOrgEnteSiam(enteSiam);
        OrgTipoAccordo tipoAccordo = helper.findById(OrgTipoAccordo.class, accordoDetail.getId_tipo_accordo().parse());
        accordo.setOrgTipoAccordo(tipoAccordo);
        BigDecimal idTariffario;
        if ((idTariffario = accordoDetail.getId_tariffario().parse()) != null) {
            OrgTariffario tariffario = helper.findById(OrgTariffario.class, idTariffario);
            accordo.setOrgTariffario(tariffario);
        } else {
            accordo.setOrgTariffario(null);
        }
        //
        BigDecimal idClasseEnteConvenz;
        if ((idClasseEnteConvenz = accordoDetail.getId_classe_ente_convenz().parse()) != null) {
            OrgClasseEnteConvenz classeEnteConvenz = helper.findById(OrgClasseEnteConvenz.class, idClasseEnteConvenz);
            accordo.setOrgClasseEnteConvenz(classeEnteConvenz);
        } else {
            accordo.setOrgClasseEnteConvenz(null);
        }
        //
        BigDecimal idEnteConvenzAmministratore;
        if ((idEnteConvenzAmministratore = accordoDetail.getId_ente_convenz_amministratore().parse()) != null) {
            OrgEnteSiam enteSiamByIdEnteConvenzAmministratore = helper.findById(OrgEnteSiam.class,
                    idEnteConvenzAmministratore);
            accordo.setOrgEnteSiamByIdEnteConvenzAmministratore(enteSiamByIdEnteConvenzAmministratore);
        } else {
            // TODO: VERIFICARE
            accordo.setOrgEnteSiamByIdEnteConvenzAmministratore(null);
            // end TODO
        }
        BigDecimal idEnteConvenzConserv;
        if ((idEnteConvenzConserv = accordoDetail.getId_ente_convenz_conserv().parse()) != null) {
            OrgEnteSiam enteSiamByIdEnteConvenzConserv = helper.findById(OrgEnteSiam.class, idEnteConvenzConserv);
            accordo.setOrgEnteSiamByIdEnteConvenzConserv(enteSiamByIdEnteConvenzConserv);
        } else {
            // TODO: VERIFICARE
            accordo.setOrgEnteSiamByIdEnteConvenzConserv(null);
            // end TODO
        }
        BigDecimal idEnteConvenzGestore;
        if ((idEnteConvenzGestore = accordoDetail.getId_ente_convenz_gestore().parse()) != null) {
            OrgEnteSiam enteSiamByIdEnteConvenzGestore = helper.findById(OrgEnteSiam.class, idEnteConvenzGestore);
            accordo.setOrgEnteSiamByIdEnteConvenzGestore(enteSiamByIdEnteConvenzGestore);
        } else {
            // TODO: VERIFICARE
            accordo.setOrgEnteSiamByIdEnteConvenzGestore(null);
            // end TODO
        }
    }

    /**
     * Metodo di eliminazione di un ente convenzionato
     *
     * @param param
     *            parametri per il logging
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgEnteConvenz(LogParam param, BigDecimal idEnteConvenz) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'ente convenzionato " + idEnteConvenz);

        OrgEnteSiam ente = helper.findById(OrgEnteSiam.class, idEnteConvenz);

        // Controlla che sull'ente convenzionato non siano già presenti degli accordi con servizi già inseriti in una
        // fattura
        if (checkEsistenzaServiziInFatturaPerEnteConvenz(idEnteConvenz)) {
            throw new ParerUserError(
                    "Esistono dei servizi riferiti ad accordi dell\u0027ente convenzionato gi\u00E0  inseriti in una fattura. Impossibile procedere con lÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢eliminazione");
        }

        if (helper.checkEsistenzaUtenteEnteAppart(idEnteConvenz)) {
            throw new ParerUserError("Esiste almeno un utente il cui ente convenzionato di appartenenza \u00E8 "
                    + ente.getNmEnteSiam() + ". Eliminazione non consentita");
        }

        if (helper.checkEsistenzaEnteAbilUnEnte(idEnteConvenz)) {
            throw new ParerUserError(
                    "Esiste almeno un utente avente un'abilitazione di tipo UN_ENTE all'ente convenzionato "
                            + ente.getNmEnteSiam() + ". Eliminazione non consentita");
        }

        if (helper.checkEsistenzaRichiesteUtente(idEnteConvenz)) {
            throw new ParerUserError("Esiste almeno una richiesta utente riferita all'ente convenzionato "
                    + ente.getNmEnteSiam() + ". Eliminazione non consentita");
        }

        if (helper.checkEsistenzaAssociazioneStruttureEnte(idEnteConvenz)) {
            throw new ParerUserError("Esiste almeno una struttura associata all'ente convenzionato "
                    + ente.getNmEnteSiam() + ". Eliminazione non consentita");
        }

        if (!ente.getOrgAppartCollegEntis().isEmpty()) {
            for (OrgAppartCollegEnti appart : ente.getOrgAppartCollegEntis()) {
                if (ente.equals(appart.getOrgCollegEntiConvenz().getOrgEnteSiam())) {
                    throw new ParerUserError("Esiste almeno un collegamento in cui l'ente convenzionato "
                            + ente.getNmEnteSiam() + " \u00E8 capofila. Eliminazione non consentita");
                }
            }
        }

        List<ObjectsToLogBefore> listaOggettiDaLoggare = sacerLogEjb.logBefore(param.getTransactionLogContext(),
                param.getNomeApplicazione(), param.getNomeUtente(), param.getNomeAzione(),
                SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO, idEnteConvenz, param.getNomePagina());

        // Controllo l'appartenenza dell'ente passato in input ad almeno un collegamento
        if (!ente.getOrgAppartCollegEntis().isEmpty()) {
            for (OrgAppartCollegEnti appart : ente.getOrgAppartCollegEntis()) {
                // Gestisco rimozione appartenenza o del collegamento nel caso l'ente sia l'unica appartenenza presente
                // nel collegamento
                if (appart.getOrgCollegEntiConvenz().getOrgAppartCollegEntis().size() > 1) {
                    // Rimuovo l'associazione dell'ente al collegamento
                    helper.removeEntity(appart, true);
                } else {
                    // Rimuovo il collegamento
                    helper.removeEntity(appart.getOrgCollegEntiConvenz(), true);
                }
            }
        }

        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                idEnteConvenz, param.getNomePagina());
        helper.removeEntity(ente, true);
        sacerLogEjb.logAfter(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                param.getNomeAzione(), listaOggettiDaLoggare, param.getNomePagina());
    }

    /**
     * Ritorna il rowBean relativo all'entity orgEnteSiam dato il suo id
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return entity bean {@link OrgEnteSiamRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteSiamRowBean getOrgEnteConvenzRowBean(BigDecimal idEnteConvenz) throws ParerUserError {
        OrgEnteSiam ente = helper.findById(OrgEnteSiam.class, idEnteConvenz);
        OrgEnteSiamRowBean row = null;
        try {
            row = (OrgEnteSiamRowBean) Transform.entity2RowBean(ente);
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
            row.setBigDecimal("ds_categ_ente", row.getIdCategEnte());
            OrgAccordoEnte accordo = helper.retrieveOrgAccordoEntePiuRecente(idEnteConvenz);
            if (accordo != null) {
                row.setString("fl_chiuso", accordo.getFlAccordoChiuso());
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero dell'ente convenzionato " + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Ritorna il tableBean contenente gli enti convenzionati tranne quello passato in input
     *
     * @param idEnteConvezDaEscludere
     *            id ente convenzionato da escludere
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteSiamTableBean getOrgEnteConvenzTableBean(BigDecimal idEnteConvezDaEscludere) throws ParerUserError {
        OrgEnteSiamTableBean enteConvenzTableBean = new OrgEnteSiamTableBean();
        List<OrgEnteSiam> enteConvenzList = helper.getOrgEnteConvenzList(idEnteConvezDaEscludere);
        if (!enteConvenzList.isEmpty()) {
            try {
                enteConvenzTableBean = (OrgEnteSiamTableBean) Transform.entities2TableBean(enteConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero degli enti convenzionati " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero della lista degli enti convenzionati");
            }
        }
        return enteConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente gli enti convenzionati del tipo passato in input
     *
     * @param tiEnteConvenz
     *            tipo ente convenzionato
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteSiamTableBean getOrgEnteSiamTableBeanByTiEnteConvenz(ConstOrgEnteSiam.TiEnteConvenz... tiEnteConvenz)
            throws ParerUserError {
        OrgEnteSiamTableBean enteConvenzTableBean = new OrgEnteSiamTableBean();
        List<OrgEnteSiam> enteConvenzList = helper.getOrgEnteConvenzList(tiEnteConvenz);
        if (!enteConvenzList.isEmpty()) {
            try {
                enteConvenzTableBean = (OrgEnteSiamTableBean) Transform.entities2TableBean(enteConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero degli enti convenzionati " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero della lista degli enti convenzionati");
            }
        }
        return enteConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente gli ambienti enti convenzionati del tipo ente passato in input
     *
     * @param tiEnteConvenz
     *            tipo ente convenzionato
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAmbienteEnteConvenzTableBean getOrgAmbienteEnteConvenzTableBeanByTiEnteConvenz(
            ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz) throws ParerUserError {
        OrgAmbienteEnteConvenzTableBean ambienteEnteConvenzTableBean = new OrgAmbienteEnteConvenzTableBean();
        List<OrgAmbienteEnteConvenz> ambientiEntiConvenzList = helper.getOrgAmbienteEnteConvenzList(tiEnteConvenz);
        if (!ambientiEntiConvenzList.isEmpty()) {
            try {
                ambienteEnteConvenzTableBean = (OrgAmbienteEnteConvenzTableBean) Transform
                        .entities2TableBean(ambientiEntiConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero degli ambienti enti convenzionati "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista degli ambienti enti convenzionati");
            }
        }
        return ambienteEnteConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente gli enti convenzionati (e quelli ad esso collegati) dei tipi passati in input cui
     * l'utente corrente \u00E8 abilitato
     *
     * @param idUserIamCor
     *            id user iam
     * @param tiEnteConvenz
     *            tipo ente convenzionato
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteSiamTableBean getOrgEnteSiamCollegUserAbilTableBeanByTiEnteConvenz(BigDecimal idUserIamCor,
            ConstOrgEnteSiam.TiEnteConvenz... tiEnteConvenz) throws ParerUserError {
        OrgEnteSiamTableBean enteConvenzTableBean = new OrgEnteSiamTableBean();
        // Recupero la lista degli enti convenzionati
        List<OrgEnteSiam> enteConvenzList = helper.getOrgEnteConvenzUserAbilList(idUserIamCor, tiEnteConvenz);
        if (enteConvenzList != null && !enteConvenzList.isEmpty()) {
            List<OrgEnteSiam> entiConvenzCollegList = new ArrayList();
            // Recupero per ogni ente convenzionato gli enti ad esso collegati (escluso se stesso)
            for (OrgEnteSiam enteConvenz : enteConvenzList) {
                entiConvenzCollegList.addAll(helper.getOrgEnteConvenzCollegUserAbilList(idUserIamCor,
                        BigDecimal.valueOf(enteConvenz.getIdEnteSiam())));
            }
            // Aggiungo alla lista degli enti convenzionati gli enti ad essi collegati
            if (!entiConvenzCollegList.isEmpty()) {
                enteConvenzList.addAll(entiConvenzCollegList);
            }
            try {
                enteConvenzTableBean = (OrgEnteSiamTableBean) Transform.entities2TableBean(enteConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero degli enti convenzionati " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero della lista degli enti convenzionati");
            }
        }
        return enteConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente gli ambienti degli enti convenzionati (e quelli ad essi collegati) dei tipi ente
     * passato in input cui l'utente corrente \u00E8 abilitato
     *
     * @param idUserIamCor
     *            id user iam
     * @param tiEnteConvenz
     *            tipo ente convenzionato
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAmbienteEnteConvenzTableBean getOrgAmbientiEnteConvenzCollegUserAbilTableBeanByTiEnteConvenz(
            BigDecimal idUserIamCor, ConstOrgEnteSiam.TiEnteConvenz... tiEnteConvenz) throws ParerUserError {
        OrgAmbienteEnteConvenzTableBean ambienteEnteConvenzTableBean = new OrgAmbienteEnteConvenzTableBean();
        // Recupero la lista degli ambienti enti convenzionati
        List<OrgAmbienteEnteConvenz> ambientiEntiConvenzList = helper
                .getOrgAmbienteEnteConvenzUserAbilList(idUserIamCor, tiEnteConvenz);
        if (ambientiEntiConvenzList != null && !ambientiEntiConvenzList.isEmpty()) {
            // Recupero la lista degli enti convenzionati
            List<OrgEnteSiam> enteConvenzList = helper.getOrgEnteConvenzUserAbilList(idUserIamCor, tiEnteConvenz);
            if (enteConvenzList != null && !enteConvenzList.isEmpty()) {
                List<OrgEnteSiam> entiConvenzCollegList = new ArrayList();
                // Recupero per ogni ente convenzionato gli enti ad esso collegati (escluso se stesso)
                for (OrgEnteSiam enteConvenz : enteConvenzList) {
                    entiConvenzCollegList.addAll(helper.getOrgEnteConvenzCollegUserAbilList(idUserIamCor,
                            BigDecimal.valueOf(enteConvenz.getIdEnteSiam())));
                }
                // Aggiungo alla lista degli ambienti enti convenzionati gli ambienti degli enti collegati
                for (OrgEnteSiam enteConvenzColleg : entiConvenzCollegList) {
                    ambientiEntiConvenzList.add(enteConvenzColleg.getOrgAmbienteEnteConvenz());
                }
            }
            try {
                ambienteEnteConvenzTableBean = (OrgAmbienteEnteConvenzTableBean) Transform
                        .entities2TableBean(ambientiEntiConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero degli ambienti enti convenzionati "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista degli ambienti enti convenzionati");
            }
        }
        return ambienteEnteConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente gli enti convenzionati cessati riferiti all'ente passato in input
     *
     * @param idEnteConvez
     *            id ente convezionato
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteSiamTableBean getOrgEnteConvenzCessatiTableBean(BigDecimal idEnteConvez) throws ParerUserError {
        OrgEnteSiamTableBean enteConvenzTableBean = new OrgEnteSiamTableBean();
        List<OrgEnteSiam> enteConvenzList = helper.getOrgEnteConvenzCessatiList(idEnteConvez);
        if (!enteConvenzList.isEmpty()) {
            try {
                for (OrgEnteSiam enteConvenz : enteConvenzList) {
                    OrgEnteSiamRowBean enteConvenzRowBean = new OrgEnteSiamRowBean();
                    enteConvenzRowBean = (OrgEnteSiamRowBean) Transform.entity2RowBean(enteConvenz);
                    enteConvenzRowBean.setBigDecimal("id_ente_convenz", new BigDecimal(enteConvenz.getIdEnteSiam()));
                    enteConvenzRowBean.setString("nm_ente_convenz", enteConvenz.getNmEnteSiam());
                    enteConvenzRowBean.setString("nm_ambiente_ente_convenz",
                            enteConvenz.getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz());
                    enteConvenzTableBean.add(enteConvenzRowBean);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero degli enti convenzionati cessati "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista degli enti convenzionati cessati");
            }
        }
        return enteConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente gli enti convenzionati non cessati figli dell'ambiente passato in ingresso
     *
     * @param idAmbienteEnteConvez
     *            id ambiente/ente convezionato
     * @param idEnteConvenzExcluded
     *            id ente convezionato da ecludere
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteSiamTableBean getOrgEnteConvenzNonCessatiTableBean(BigDecimal idAmbienteEnteConvez,
            BigDecimal idEnteConvenzExcluded) throws ParerUserError {
        OrgEnteSiamTableBean enteConvenzTableBean = new OrgEnteSiamTableBean();
        List<OrgEnteSiam> enteConvenzList = helper.getOrgEnteConvenzNonCessatiList(idAmbienteEnteConvez,
                idEnteConvenzExcluded);
        if (!enteConvenzList.isEmpty()) {
            try {
                enteConvenzTableBean = (OrgEnteSiamTableBean) Transform.entities2TableBean(enteConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero degli enti convenzionati non cessati "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista degli enti convenzionati non cessati");
            }
        }
        return enteConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente, per l'ente convenzionato passsato in input, la lista delle precedenti
     * appartenenze all'ambiente ente convenzionato
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgStoEnteAmbienteConvenzTableBean getOrgStoEnteAmbienteConvenzTableBean(BigDecimal idEnteConvenz)
            throws ParerUserError {
        OrgStoEnteAmbienteConvenzTableBean table = new OrgStoEnteAmbienteConvenzTableBean();
        List<OrgStoEnteAmbienteConvenz> list = helper.retrieveOrgStoEnteAmbienteConvenz(idEnteConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgStoEnteAmbienteConvenz stoEnteAmbiente : list) {
                    OrgStoEnteAmbienteConvenzRowBean stoEnteAmbienteConvenzRowBean = new OrgStoEnteAmbienteConvenzRowBean();
                    stoEnteAmbienteConvenzRowBean = (OrgStoEnteAmbienteConvenzRowBean) Transform
                            .entity2RowBean(stoEnteAmbiente);
                    stoEnteAmbienteConvenzRowBean.setString("nm_ambiente_ente_convenz",
                            stoEnteAmbiente.getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz());
                    table.add(stoEnteAmbienteConvenzRowBean);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista delel precedenti appartenenze all'ambiente dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il tableBean contenente per l'ente convenzionato dato in input la lista di anagrafiche
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgStoEnteConvenzTableBean getOrgStoEnteConvenzTableBean(BigDecimal idEnteConvenz) throws ParerUserError {
        OrgStoEnteConvenzTableBean table = new OrgStoEnteConvenzTableBean();
        List<OrgStoEnteConvenz> list = helper.retrieveOrgStoEnteConvenz(idEnteConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                table = (OrgStoEnteConvenzTableBean) Transform.entities2TableBean(list);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista di anagrafiche dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il tableBean contenente per l'ente convenzionato dato in input la lista di accordi
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAccordoEnteTableBean getOrgAccordoEnteTableBean(BigDecimal idEnteConvenz) throws ParerUserError {
        OrgAccordoEnteTableBean table = new OrgAccordoEnteTableBean();
        List<OrgAccordoEnte> list = helper.retrieveOrgAccordoEnte(idEnteConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgAccordoEnte accordoEnte : list) {
                    OrgAccordoEnteRowBean accordoEnteRowBean = new OrgAccordoEnteRowBean();
                    accordoEnteRowBean = (OrgAccordoEnteRowBean) Transform.entity2RowBean(accordoEnte);
                    if (accordoEnte.getCdRegistroRepertorio() != null && accordoEnte.getAaRepertorio() != null
                            && accordoEnte.getCdKeyRepertorio() != null) {
                        accordoEnteRowBean.setString("chiave", accordoEnte.getCdRegistroRepertorio() + " - "
                                + accordoEnte.getAaRepertorio() + " - " + accordoEnte.getCdKeyRepertorio());
                    }
                    if (accordoEnte.getOrgTipoAccordo() != null) {
                        accordoEnteRowBean.setString("cd_tipo_accordo",
                                accordoEnte.getOrgTipoAccordo().getCdTipoAccordo());
                    }
                    if (accordoEnte.getOrgEnteSiamByIdEnteConvenzConserv() != null) {
                        accordoEnteRowBean.setString("nm_ente_convenz_conserv",
                                accordoEnte.getOrgEnteSiamByIdEnteConvenzConserv().getNmEnteSiam());
                    }
                    if (accordoEnte.getOrgEnteSiamByIdEnteConvenzGestore() != null) {
                        accordoEnteRowBean.setString("nm_ente_convenz_gestore",
                                accordoEnte.getOrgEnteSiamByIdEnteConvenzGestore().getNmEnteSiam());
                    }
                    String tiStatoAccordo = helper.getTiStatoAccordoById(accordoEnteRowBean.getIdAccordoEnte());
                    if (tiStatoAccordo != null) {
                        accordoEnteRowBean.setString("ti_stato_accordo", tiStatoAccordo);
                        if (tiStatoAccordo.equals("Accordo valido")
                                || tiStatoAccordo.equals("Accordo valido in corso rinnovo")) {
                            accordoEnteRowBean.setString("fl_valido", "1");
                        } else {
                            accordoEnteRowBean.setString("fl_valido", "0");
                        }
                    } else {
                        accordoEnteRowBean.setString("fl_valido", "0");
                    }

                    table.add(accordoEnteRowBean);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista di accordi dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna la lista di accordi validi (esclusi quelli dell'ente dato in input) in cui l'ente risulta, in base al
     * tipo dato in input, CONSERVATORE o GESTORE dell'accordo.
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param tiEnteConvenz
     *            tipo ente convenzionato
     *
     * @return la lista degli accordi
     *
     * @throws ParerUserError
     *             errore generico
     */
    public List<OrgAccordoEnte> getOrgAccordoEnteValidListByTipoEnteConvenz(BigDecimal idEnteConvenz,
            String tiEnteConvenz) throws ParerUserError {
        List<OrgAccordoEnte> list = helper.retrieveOrgAccordoEnteValidByTipo(idEnteConvenz, tiEnteConvenz);
        return list;
    }

    /**
     * Ritorna la lista delle organizzazioni che si riferiscono all'ente dato in input.
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return la lista delle organizzazioni
     *
     * @throws ParerUserError
     *             errore generico
     */
    public List<OrgEnteConvenzOrg> getOrgEnteConvenzOrgListByEnteConvenz(BigDecimal idEnteConvenz)
            throws ParerUserError {
        List<OrgEnteConvenzOrg> list = helper.retrieveOrgEnteConvenzOrg(idEnteConvenz);
        return list;
    }

    /**
     * Ritorna il tableBean contenente per l'ente convenzionato dato in input l'accordo piÃƒÆ’Ã‚Â¹ recente
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAccordoEnteTableBean getOrgAccordoEntePiuRecenteTableBean(BigDecimal idEnteConvenz)
            throws ParerUserError {
        OrgAccordoEnteTableBean table = new OrgAccordoEnteTableBean();
        OrgAccordoEnte accordoEnte = helper.retrieveOrgAccordoEntePiuRecente(idEnteConvenz);
        if (accordoEnte != null) {
            try {
                OrgAccordoEnteRowBean accordoEnteRowBean = new OrgAccordoEnteRowBean();
                accordoEnteRowBean = (OrgAccordoEnteRowBean) Transform.entity2RowBean(accordoEnte);
                if (accordoEnte.getOrgTipoAccordo() != null) {
                    accordoEnteRowBean.setString("cd_tipo_accordo", accordoEnte.getOrgTipoAccordo().getCdTipoAccordo());
                }
                if (accordoEnte.getOrgEnteSiamByIdEnteConvenzGestore() != null) {
                    accordoEnteRowBean.setString("nm_ente_convenz_gestore",
                            accordoEnte.getOrgEnteSiamByIdEnteConvenzGestore().getNmEnteSiam());
                }
                if (accordoEnte.getOrgEnteSiamByIdEnteConvenzConserv() != null) {
                    accordoEnteRowBean.setString("nm_ente_convenz_conserv",
                            accordoEnte.getOrgEnteSiamByIdEnteConvenzConserv().getNmEnteSiam());
                }
                if (accordoEnte.getOrgEnteSiamByIdEnteConvenzAmministratore() != null) {
                    accordoEnteRowBean.setString("nm_ente_convenz_amministratore",
                            accordoEnte.getOrgEnteSiamByIdEnteConvenzAmministratore().getNmEnteSiam());
                }
                table.add(accordoEnteRowBean);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero dell'accordo più recente dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Determina l'ente gestore contenente per l'ente conservatore dato in input l'accordo valido alla data
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idEnteConserv
     *            id ente conservatore
     * @param tiEnteConvenz
     *            tipo ente convezionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAccordoEnteTableBean getOrgAccordoEnteGestoreValidoTableBean(BigDecimal idUserIamCor,
            BigDecimal idEnteConserv, TiEnteConvenz... tiEnteConvenz) throws ParerUserError {
        OrgAccordoEnteTableBean table = new OrgAccordoEnteTableBean();
        List<OrgAccordoEnte> list = helper.retrieveOrgAccordoEnteGestore(idUserIamCor, idEnteConserv, true,
                tiEnteConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgAccordoEnte accordoEnte : list) {
                    OrgAccordoEnteRowBean accordoEnteRowBean = new OrgAccordoEnteRowBean();
                    accordoEnteRowBean = (OrgAccordoEnteRowBean) Transform.entity2RowBean(accordoEnte);
                    accordoEnteRowBean.setBigDecimal("id_ente_convenz",
                            BigDecimal.valueOf(accordoEnte.getOrgEnteSiam().getIdEnteSiam()));
                    accordoEnteRowBean.setString("nm_ente_convenz", accordoEnte.getOrgEnteSiam().getNmEnteSiam());
                    table.add(accordoEnteRowBean);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista di accordi dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    public BaseTable getEntiGestoreAbilitatiTableBean(BigDecimal idUserIamCor) {
        BaseTable ricEnteConvenzTableBean = new BaseTable();
        List<OrgVRicEnteConvenz> ricEnteConvenzList = helper.getOrgVRicEnteConvenzList(idUserIamCor, "PRODUTTORE");
        try {
            for (OrgVRicEnteConvenz ricEnteConvenz : ricEnteConvenzList) {
                BaseRow riga = new BaseRow();
                riga.setBigDecimal("id_ente_gestore", ricEnteConvenz.getIdEnteConvenz());
                riga.setString("nm_ente_gestore", ricEnteConvenz.getNmEnteConvenz());
                ricEnteConvenzTableBean.add(riga);
            }
        } catch (Exception e) {
            LOGGER.error("Errore nel recupero degli enti gestori: " + ExceptionUtils.getRootCauseMessage(e), e);
            throw new IllegalStateException("Errore inatteso nel recupero degli enti gestori");
        }
        return ricEnteConvenzTableBean;
    }

    public BaseTable getEntiConservatori(BigDecimal idUserIamCor, BigDecimal idEnteSiamGestore) throws ParerUserError {
        BaseTable tabella = new BaseTable();
        List<OrgEnteSiam> entiConvenzConserv = helper.getEnteConvenzConservList(idUserIamCor, idEnteSiamGestore);
        for (OrgEnteSiam ente : entiConvenzConserv) {
            BaseRow riga = new BaseRow();
            riga.setBigDecimal("id_ente_siam", BigDecimal.valueOf(ente.getIdEnteSiam()));
            riga.setString("nm_ente_siam", ente.getNmEnteSiam());
            tabella.add(riga);
        }
        return tabella;
    }

    /**
     * Ritorna il tableBean contenente per l'ente convenzionato dato in input la lista di strutture versanti
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteConvenzOrgTableBean getOrgEnteConvenzOrgTableBean(BigDecimal idEnteConvenz) throws ParerUserError {
        OrgEnteConvenzOrgTableBean table = new OrgEnteConvenzOrgTableBean();
        List<OrgEnteConvenzOrg> list = helper.retrieveOrgEnteConvenzOrg(idEnteConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgEnteConvenzOrg enteConvenzOrg : list) {
                    OrgEnteConvenzOrgRowBean rowBean = (OrgEnteConvenzOrgRowBean) Transform
                            .entity2RowBean(enteConvenzOrg);
                    UsrOrganizIam organiz = enteConvenzOrg.getUsrOrganizIam();
                    if (organiz.getAplTipoOrganiz().getNmTipoOrganiz().equals("STRUTTURA")) {
                        UsrOrganizIam strutOrganiz = enteConvenzOrg.getUsrOrganizIam();
                        UsrOrganizIam enteOrganiz = strutOrganiz.getUsrOrganizIam();
                        UsrOrganizIam ambienteOrganiz = enteOrganiz.getUsrOrganizIam();
                        rowBean.setBigDecimal("id_organiz_applic", strutOrganiz.getIdOrganizApplic());
                        rowBean.setBigDecimal("id_organiz_iam", BigDecimal.valueOf(strutOrganiz.getIdOrganizIam()));
                        rowBean.setString("nm_organiz_ambiente", enteOrganiz.getUsrOrganizIam().getNmOrganiz());
                        rowBean.setString("nm_organiz_strut",
                                enteOrganiz.getNmOrganiz() + " - " + strutOrganiz.getNmOrganiz());
                        rowBean.setString("nm_organiz_strut_completo", ambienteOrganiz.getNmOrganiz() + " - "
                                + enteOrganiz.getNmOrganiz() + " - " + strutOrganiz.getNmOrganiz());
                        rowBean.setString("ds_organiz", strutOrganiz.getDsOrganiz());
                    } else {
                        UsrOrganizIam versOrganiz = enteConvenzOrg.getUsrOrganizIam();
                        UsrOrganizIam ambienteOrganiz = versOrganiz.getUsrOrganizIam();
                        rowBean.setBigDecimal("id_organiz_applic", versOrganiz.getIdOrganizApplic());
                        rowBean.setBigDecimal("id_organiz_iam", BigDecimal.valueOf(versOrganiz.getIdOrganizIam()));
                        rowBean.setString("nm_organiz_ambiente", ambienteOrganiz.getNmOrganiz());
                        rowBean.setString("nm_organiz_vers", versOrganiz.getNmOrganiz());
                        rowBean.setString("nm_organiz_strut_completo",
                                ambienteOrganiz.getNmOrganiz() + " - " + versOrganiz.getNmOrganiz());
                        rowBean.setString("ds_organiz", versOrganiz.getDsOrganiz());
                    }
                    table.add(rowBean);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista di organizzazioni versanti dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        table.addSortingRule("nm_organiz_strut");
        table.addSortingRule("nm_organiz_vers");
        table.sort();
        return table;
    }

    public OrgVEnteConvenzByOrganizTableBean getOrgVEnteConvenzByOrganizTableBean(BigDecimal idEnteConvenz,
            boolean filterValid) throws ParerUserError {
        OrgVEnteConvenzByOrganizTableBean table = new OrgVEnteConvenzByOrganizTableBean();
        List<OrgVEnteConvenzByOrganiz> list = helper.retrieveOrgVEnteConvenzByOrganiz(idEnteConvenz, filterValid);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgVEnteConvenzByOrganiz organiz : list) {
                    OrgVEnteConvenzByOrganizRowBean rowBean = (OrgVEnteConvenzByOrganizRowBean) Transform
                            .entity2RowBean(organiz);
                    UsrOrganizIam organizIam = helper.findById(UsrOrganizIam.class, organiz.getIdOrganizIam());

                    if (organizIam.getAplTipoOrganiz().getNmTipoOrganiz().equals("STRUTTURA")) {
                        UsrOrganizIam enteOrganiz = organizIam.getUsrOrganizIam();
                        UsrOrganizIam ambienteOrganiz = enteOrganiz.getUsrOrganizIam();
                        rowBean.setString("dl_composito_organiz", ambienteOrganiz.getNmOrganiz() + " - "
                                + enteOrganiz.getNmOrganiz() + " - " + organizIam.getNmOrganiz());
                        if (!isOrganizApplicCessata(organizIam.getIdOrganizApplic(), "STRUTTURA")) {
                            rowBean.setString("url_assoluto", getSacerUrlForStruttureVersanti() + "/detail/strut/"
                                    + organizIam.getIdOrganizApplic());
                        }
                    } else {
                        UsrOrganizIam ambienteOrganiz = organizIam.getUsrOrganizIam();
                        rowBean.setString("dl_composito_organiz",
                                ambienteOrganiz.getNmOrganiz() + " - " + organizIam.getNmOrganiz());
                        if (!isOrganizApplicCessata(organizIam.getIdOrganizApplic(), "VERSATORE")) {
                            rowBean.setString("url_assoluto",
                                    getPreingestUrlForVersatori() + "/detail/vers/" + organizIam.getIdOrganizApplic());
                        }
                    }
                    table.add(rowBean);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista di organizzazioni versanti dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        table.addSortingRule("dl_composito_organiz");
        table.sort();
        return table;
    }

    public boolean isOrganizApplicCessata(BigDecimal idOrganizApplic, String nmTipoOrganiz) {

        boolean risposta = false;

        if (nmTipoOrganiz.equals("STRUTTURA")) {
            OrgStrut strut = helper.findById(OrgStrut.class, idOrganizApplic);
            if (strut != null) {
                return strut.getFlCessato() != null ? strut.getFlCessato().equals("1") : false;
            }
        } else {
            // MEV#27457 - Rendere indipendente SIAM da PING

            // PigVers vers = helper.findById(PigVers.class, idOrganizApplic);
            // if (vers != null) {
            // return vers.getFlCessato() != null ? vers.getFlCessato().equals("1") : false;
            // }
            if (utentiHelper.getAplApplicByName(ConstAplApplic.NmApplic.SACER_PREINGEST.name()) != null) {
                try {
                    risposta = restTemplateEjb.chiamaVersatoreCessato(idOrganizApplic.longValueExact());
                } catch (RestClientException ex) {
                    LOGGER.error("Errore durante l'invocazione del WS Rest 'versatoreCessato'", ex);
                    throw ex;
                }
            }
        }
        return risposta;
    }

    /**
     * Ritorna il tableBean contenente la lista di utenti, dati in input l'ente convenzionato su cui gli utenti, di tipo
     * tipoUser e in stato tiStatoUser, sono abilitati ad operare
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param tiStatoUser
     *            tipo stato user
     * @param tipoUser
     *            tipo user
     * @param idUserIamEsclusi
     *            lista distinta id user iam eclusivi
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public UsrVLisUserEnteConvenzTableBean getUsrVLisUserTableBean(BigDecimal idEnteConvenz, List<String> tiStatoUser,
            List<String> tipoUser, Set<BigDecimal> idUserIamEsclusi) throws ParerUserError {
        UsrVLisUserEnteConvenzTableBean table = new UsrVLisUserEnteConvenzTableBean();
        List<UsrVLisUserEnteConvenz> list = utentiHelper.retrieveUsrVLisUserEnteConvenz(idEnteConvenz, tiStatoUser,
                tipoUser, idUserIamEsclusi);
        if (list != null && !list.isEmpty()) {
            try {
                table = (UsrVLisUserEnteConvenzTableBean) Transform.entities2TableBean(list);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista di utenti dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il tableBean contenente gli utenti (di eventuale tipo e stato passati in ingresso) che hanno ente
     * convenzionato di appartenenza = ente corrente
     *
     * @param idEnteConvenz
     *            id ente convenzionato
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
    public UsrUserTableBean getUsrUserEnteConvenzTableBean(BigDecimal idEnteConvenz, List<String> tiStatoUser,
            List<String> tipoUser) throws ParerUserError {
        UsrUserTableBean table = new UsrUserTableBean();
        List<UsrUser> list = utentiHelper.retrieveUsrUserEnteSiamAppart(idEnteConvenz, tiStatoUser, tipoUser);
        try {
            for (UsrUser user : list) {
                UsrUserRowBean userRowBean = new UsrUserRowBean();
                userRowBean = (UsrUserRowBean) Transform.entity2RowBean(user);
                table.add(userRowBean);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero della lista di utenti appartenenti all'ente convenzionato "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return table;
    }

    /**
     * Ritorna il tableBean contenente per l'ente convenzionato dato in input la lista di utenti archivisti
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteArkRifTableBean getOrgEnteArkRifTableBean(BigDecimal idEnteConvenz) throws ParerUserError {
        OrgEnteArkRifTableBean table = new OrgEnteArkRifTableBean();
        List<OrgEnteArkRif> list = helper.retrieveOrgEnteArkRif(idEnteConvenz);
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
                String msg = "Errore durante il recupero della lista di utenti archivisti dell'ente convenzionato "
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
     *            id user/ente
     *
     * @return row bean {@link OrgEnteUserRifRowBean}
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
     * Ritorna il tableBean contenente per l'ente convenzionato dato in input la lista di utenti referenti
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgEnteUserRifTableBean getOrgEnteUserRifTableBean(BigDecimal idEnteConvenz) throws ParerUserError {
        OrgEnteUserRifTableBean table = new OrgEnteUserRifTableBean();
        List<OrgEnteUserRif> list = helper.retrieveOrgEnteUserRif(idEnteConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgEnteUserRif enteUserRif : list) {
                    OrgEnteUserRifRowBean row = new OrgEnteUserRifRowBean();
                    row.setBigDecimal("id_user_iam", new BigDecimal(enteUserRif.getUsrUser().getIdUserIam()));
                    row.setString("nm_cognome_user", enteUserRif.getUsrUser().getNmCognomeUser());
                    row.setString("nm_nome_user", enteUserRif.getUsrUser().getNmNomeUser());
                    row.setString("nm_userid", enteUserRif.getUsrUser().getNmUserid());
                    row.setString("ds_email", enteUserRif.getUsrUser().getDsEmail());
                    row.setString("tipo_user", enteUserRif.getUsrUser().getTipoUser());
                    row.setString("qualifica_user", enteUserRif.getQualificaUser());
                    row.setString("dl_note", enteUserRif.getDlNote());
                    row.setBigDecimal("id_ente_user_rif", new BigDecimal(enteUserRif.getIdEnteUserRif()));
                    table.add(row);
                }
            } catch (IllegalArgumentException ex) {
                String msg = "Errore durante il recupero della lista di utenti referenti dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il tableBean contenente la lista delle appartenenze ai collegamenti per l'ente convenzionato dato in
     * input
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAppartCollegEntiTableBean getOrgAppartCollegEnteTableBean(BigDecimal idEnteConvenz)
            throws ParerUserError {
        OrgAppartCollegEntiTableBean table = new OrgAppartCollegEntiTableBean();
        List<OrgAppartCollegEnti> list = helper.retrieveOrgAppartCollegEntiByIdEnteConvenz(idEnteConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgAppartCollegEnti appartCollegEnti : list) {
                    OrgAppartCollegEntiRowBean row = (OrgAppartCollegEntiRowBean) Transform
                            .entity2RowBean(appartCollegEnti);
                    row.setString("nm_colleg", appartCollegEnti.getOrgCollegEntiConvenz().getNmColleg());
                    row.setString("ds_colleg", appartCollegEnti.getOrgCollegEntiConvenz().getDsColleg());
                    row.setString("ti_colleg", appartCollegEnti.getOrgCollegEntiConvenz().getTiColleg().name());
                    if (!appartCollegEnti.getOrgCollegEntiConvenz().getOrgAppartCollegEntis().isEmpty()) {

                        if (appartCollegEnti.getOrgCollegEntiConvenz().getOrgAppartCollegEntis().size() <= 10) {
                            Collection<Object> collegamenti = CollectionUtils.collect(
                                    appartCollegEnti.getOrgCollegEntiConvenz().getOrgAppartCollegEntis(),
                                    TransformerUtils.invokerTransformer("getOrgEnteSiam"));
                            CollectionUtils.collect(collegamenti, TransformerUtils.invokerTransformer("getNmEnteSiam"));
                            row.setString("nm_enti_convenz_collegati", StringUtils.join(CollectionUtils
                                    .collect(collegamenti, TransformerUtils.invokerTransformer("getNmEnteSiam")), ","));
                        } else {
                            row.setString("nm_enti_convenz_collegati", "Pi\u00F9 di 10 enti collegati");
                        }
                    } else {
                        row.setString("nm_enti_convenz_collegati", "");
                    }
                    if (appartCollegEnti.getOrgEnteSiam()
                            .equals(appartCollegEnti.getOrgCollegEntiConvenz().getOrgEnteSiam())) {
                        row.setString("fl_capofila", "1");
                    } else {
                        row.setString("fl_capofila", "0");
                    }

                    // MEV 29931
                    // Verifico l'esistenza di utenti che sarebbero coinvolti nella cancellazione del collegamento
                    // per ricavare l'informazione circa le abilitazioni concesse
                    if (existUtentiConAbilitazioniAppartenenzaCollegamento(
                            BigDecimal.valueOf(appartCollegEnti.getIdAppartCollegEnti()))) {
                        row.setString("fl_abilitazioni_concesse", "1");
                    } else {
                        row.setString("fl_abilitazioni_concesse", "0");
                    }

                    table.add(row);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista delle appartenenze ai collegamenti dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il rowBean relativo all'entity orgCollegEntiConvenz dato il suo id
     *
     * @param idCollegEntiConvenz
     *            id enti collegati convenzionati
     *
     * @return row bean {@link OrgCollegEntiConvenzRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgCollegEntiConvenzRowBean getOrgCollegEntiConvenzRowBean(BigDecimal idCollegEntiConvenz)
            throws ParerUserError {
        OrgCollegEntiConvenz collegEntiConvenz = helper.findById(OrgCollegEntiConvenz.class, idCollegEntiConvenz);
        OrgCollegEntiConvenzRowBean row = null;
        try {
            row = (OrgCollegEntiConvenzRowBean) Transform.entity2RowBean(collegEntiConvenz);
            if (collegEntiConvenz.getOrgEnteSiam() != null) {
                row.setBigDecimal("id_ente_convenz_capofila",
                        new BigDecimal(collegEntiConvenz.getOrgEnteSiam().getIdEnteSiam()));
                row.setString("nm_ente_convenz_capofila", collegEntiConvenz.getOrgEnteSiam().getNmEnteSiam());
            }

            if (!collegEntiConvenz.getOrgAppartCollegEntis().isEmpty()) {
                Collection<Object> collegamenti = CollectionUtils.collect(collegEntiConvenz.getOrgAppartCollegEntis(),
                        TransformerUtils.invokerTransformer("getOrgEnteSiam"));
                CollectionUtils.collect(collegamenti, TransformerUtils.invokerTransformer("getNmEnteSiam"));
                row.setString("nm_enti_convenz_collegati", StringUtils.join(
                        CollectionUtils.collect(collegamenti, TransformerUtils.invokerTransformer("getNmEnteSiam")),
                        ","));
                row.setString("nm_enti_convenz_collegati", StringUtils.join(
                        CollectionUtils.collect(collegamenti, TransformerUtils.invokerTransformer("getNmEnteSiam")),
                        ","));
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero del collegamento dell'ente "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Ritorna il tableBean contenente per l'ente convenzionato dato in input la lista dei collegamenti
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgCollegEntiConvenzTableBean getOrgCollegEntiConvenzTableBean(BigDecimal idEnteConvenz)
            throws ParerUserError {
        OrgCollegEntiConvenzTableBean table = new OrgCollegEntiConvenzTableBean();
        List<OrgCollegEntiConvenz> list = helper.retrieveOrgCollegEntiConvenz(idEnteConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgCollegEntiConvenz collegEntiConvenz : list) {
                    OrgCollegEntiConvenzRowBean row = (OrgCollegEntiConvenzRowBean) Transform
                            .entity2RowBean(collegEntiConvenz);
                    if (collegEntiConvenz.getOrgEnteSiam() != null) {
                        row.setBigDecimal("id_ente_convenz_capofila",
                                new BigDecimal(collegEntiConvenz.getOrgEnteSiam().getIdEnteSiam()));
                        row.setString("nm_ente_convenz_capofila", collegEntiConvenz.getOrgEnteSiam().getNmEnteSiam());
                    }

                    if (!collegEntiConvenz.getOrgAppartCollegEntis().isEmpty()) {
                        Collection<Object> collegamenti = CollectionUtils.collect(
                                collegEntiConvenz.getOrgAppartCollegEntis(),
                                TransformerUtils.invokerTransformer("getOrgEnteSiam"));
                        CollectionUtils.collect(collegamenti, TransformerUtils.invokerTransformer("getNmEnteSiam"));
                        row.setString("nm_enti_convenz_collegati", StringUtils.join(CollectionUtils
                                .collect(collegamenti, TransformerUtils.invokerTransformer("getNmEnteSiam")), ","));
                    }

                    table.add(row);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista dei collegamenti dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il tableBean contenente la lista dei collegamenti dati in input i filtri di ricerca
     *
     * @param idUserIamCor
     *            id user IAM
     * @param nmColleg
     *            nome collegamento
     * @param dsColleg
     *            descrizione collegamto
     * @param dtValidDa
     *            data validata da
     * @param dtValidA
     *            data validata a
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgCollegEntiConvenzTableBean getOrgCollegEntiConvenzTableBean(BigDecimal idUserIamCor, String nmColleg,
            String dsColleg, Date dtValidDa, Date dtValidA) throws ParerUserError {
        OrgCollegEntiConvenzTableBean orgCollegEntiConvenzTableBean = new OrgCollegEntiConvenzTableBean();
        List<OrgCollegEntiConvenz> collegEntiConvenzs = helper.retrieveOrgCollegEntiConvenz(idUserIamCor, nmColleg,
                dsColleg, dtValidDa, dtValidA);
        if (!collegEntiConvenzs.isEmpty()) {
            try {
                for (OrgCollegEntiConvenz collegEntiConvenz : collegEntiConvenzs) {
                    OrgCollegEntiConvenzRowBean row = new OrgCollegEntiConvenzRowBean();
                    row = (OrgCollegEntiConvenzRowBean) Transform.entity2RowBean(collegEntiConvenz);
                    if (collegEntiConvenz.getOrgEnteSiam() != null) {
                        row.setBigDecimal("id_ente_convenz_capofila",
                                new BigDecimal(collegEntiConvenz.getOrgEnteSiam().getIdEnteSiam()));
                        row.setString("nm_ente_convenz_capofila", collegEntiConvenz.getOrgEnteSiam().getNmEnteSiam());
                    }
                    if (!collegEntiConvenz.getOrgAppartCollegEntis().isEmpty()) {
                        if (collegEntiConvenz.getOrgAppartCollegEntis().size() <= 10) {
                            Collection<Object> collegamenti = CollectionUtils.collect(
                                    collegEntiConvenz.getOrgAppartCollegEntis(),
                                    TransformerUtils.invokerTransformer("getOrgEnteSiam"));
                            CollectionUtils.collect(collegamenti, TransformerUtils.invokerTransformer("getNmEnteSiam"));
                            row.setString("nm_enti_convenz_collegati", StringUtils.join(CollectionUtils
                                    .collect(collegamenti, TransformerUtils.invokerTransformer("getNmEnteSiam")), ","));
                        } else {
                            row.setString("nm_enti_convenz_collegati", "Pi\u00F9 di 10 enti collegati");
                        }
                    } else {
                        row.setString("nm_enti_convenz_collegati", "");
                    }
                    orgCollegEntiConvenzTableBean.add(row);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero della lista dei collegamenti "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista dei collegamenti");
            }
        }

        return orgCollegEntiConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente la lista dei collegamenti validi alla data corrente
     *
     * @param idUserIamCor
     *            id user IAM
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgCollegEntiConvenzTableBean getOrgCollegEntiConvenzValidTableBean(BigDecimal idUserIamCor)
            throws ParerUserError {
        OrgCollegEntiConvenzTableBean orgCollegEntiConvenzTableBean = new OrgCollegEntiConvenzTableBean();
        List<OrgCollegEntiConvenz> collegEntiConvenzValidList = helper.retrieveOrgCollegEntiConvenzValid(idUserIamCor);
        if (!collegEntiConvenzValidList.isEmpty()) {
            try {
                for (OrgCollegEntiConvenz collegEntiConvenz : collegEntiConvenzValidList) {
                    OrgCollegEntiConvenzRowBean collegEntiConvenzRowBean = (OrgCollegEntiConvenzRowBean) Transform
                            .entity2RowBean(collegEntiConvenz);
                    orgCollegEntiConvenzTableBean.add(collegEntiConvenzRowBean);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero della lista dei collegamenti validi "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista dei collegamenti validi");
            }
        }
        return orgCollegEntiConvenzTableBean;
    }

    public String getSacerUrlForStruttureVersanti() throws ParerUserError {
        AplApplic sacer = utentiHelper.getAplApplicByName(ConstAplApplic.NmApplic.SACER.name());
        String url = sacer.getDsUrlReplicaUser();
        if (StringUtils.isNotBlank(url)) {
            url = StringUtils.substringBeforeLast(url, "/");
        } else {
            throw new ParerUserError("Url a SACER per il caricamento delle strutture assente");
        }
        return url;
    }

    public String getPreingestUrlForVersatori() throws ParerUserError {
        String url = null;
        AplApplic sacer = utentiHelper.getAplApplicByName(ConstAplApplic.NmApplic.SACER_PREINGEST.name());
        if (sacer != null) {
            url = sacer.getDsUrlReplicaUser();
            if (StringUtils.isNotBlank(url)) {
                url = StringUtils.substringBeforeLast(url, "/");
            } else {
                throw new ParerUserError("Url a SACER_PREINGEST per il caricamento del versatore assente");
            }
        } else {
            throw new ParerUserError("SACER_PREINGEST non è configurata nel sistema");
        }
        return url;
    }

    public String getSacerUrlForFatturaEmessa() throws ParerUserError {
        AplApplic sacer = utentiHelper.getAplApplicByName(ConstAplApplic.NmApplic.SACER.name());
        String url = sacer.getDsUrlReplicaUser();
        if (StringUtils.isNotBlank(url)) {
            url = StringUtils.substringBeforeLast(url, "/");
        } else {
            throw new ParerUserError("Url a SACER per il caricamento dell'unit\u00E0  documentaria assente");
        }
        return url;
    }

    public boolean checkEsistenzaServiziInFatturaPerEnteConvenz(BigDecimal idEnteConvenz) {
        return helper.checkEsistenzaServiziInFatturaPerEnteConvenz(idEnteConvenz);
    }

    public boolean checkEsistenzaServiziInFatturaPerAccordo(BigDecimal idAccordoEnte) {
        return helper.checkEsistenzaServiziInFatturaPerAccordo(idAccordoEnte);
    }

    public boolean checkEsistenzaServiziInFattura(BigDecimal idServizioErog) {
        return helper.checkEsistenzaServiziInFattura(idServizioErog);
    }

    public boolean checkEsistenzaServiziErogatiPerEnteConvenz(BigDecimal idEnteConvenz) {
        return helper.checkEsistenzaServiziErogatiPerEnteConvenz(idEnteConvenz);
    }

    public boolean checkEsistenzaAccordiPerTipoAccordo(BigDecimal idTipoAccordo) {
        return helper.checkEsistenzaAccordiPerTipoAccordo(idTipoAccordo);
    }

    public boolean checkEsistenzaAccordiPerTariffario(BigDecimal idTariffario) {
        return helper.checkEsistenzaAccordiPerTariffario(idTariffario);
    }

    public boolean checkEsistenzaAccordiAttivi(BigDecimal idEnteConvenz) {
        return helper.checkEsistenzaAccordiAttivi(idEnteConvenz);
    }

    public boolean checkEsistenzaServiziErogatiPerTipoServizio(BigDecimal idTipoServizio) {
        return helper.checkEsistenzaServiziErogatiPerTipoServizio(idTipoServizio);
    }

    public boolean checkEsistenzaServiziErogatiPerTariffa(BigDecimal idTariffa) {
        return helper.checkEsistenzaServiziErogatiPerTariffa(idTariffa);
    }

    public boolean checkEsistenzaEntePerDenominazione(String nmEnteConvenz) {
        return helper.getOrgEnteConvenz(nmEnteConvenz) != null;
    }

    public boolean checkEsistenzaEntePerCodiceTipoCodice(String cdEnteConvenz, String tiCdEnteConvenz) {
        return helper.getOrgEnteConvenz(cdEnteConvenz, tiCdEnteConvenz) != null;
    }

    public boolean checkEsistenzaAmbienteValidoDtDecDtFineValid(BigDecimal idAmbienteEnteConvenz, Date dtDecAccordo,
            Date dtFineValidAccordo, BigDecimal idAmbienteDaEscludere) {
        return helper.checkEsistenzaAmbienteValidoDtDecDtFineValid(idAmbienteEnteConvenz, dtDecAccordo,
                dtFineValidAccordo, idAmbienteDaEscludere);
    }

    public int countAccordiSuEnteConvenz(BigDecimal idEnteConvenz) {
        return helper.countAccordiSuEnteConvenz(idEnteConvenz);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Long saveAssociazioneEnteStrutturaReq(LogParam param, BigDecimal idEnteConvenz, BigDecimal idOrganizIam,
            Date dtIniVal, Date dtFineVal) throws ParerUserError {
        Long ritorno = executeSaveAssociazioneEnteStruttura(param, idEnteConvenz, idOrganizIam, dtIniVal, dtFineVal);
        sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                param.getNomeAzione(), param.getNomeTipoOggetto(), idEnteConvenz, param.getNomeComponenteSoftware());
        return ritorno;
    }

    private Long executeSaveAssociazioneEnteStruttura(LogParam param, BigDecimal idEnteConvenz, BigDecimal idOrganizIam,
            Date dtIniVal, Date dtFineVal) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio dell'associazione tra l'ente convenzionato e la struttura versante");
        Long idEnteConvenzOrg = null;
        try {
            OrgEnteConvenzOrg ente = new OrgEnteConvenzOrg();

            OrgEnteSiam enteSiam = helper.findById(OrgEnteSiam.class, idEnteConvenz);
            ente.setOrgEnteSiam(enteSiam);

            UsrOrganizIam organizIam = helper.findById(UsrOrganizIam.class, idOrganizIam);
            ente.setUsrOrganizIam(organizIam);

            ente.setDtIniVal(dtIniVal);
            ente.setDtFineVal(dtFineVal);

            if (enteSiam.getOrgEnteConvenzOrgs() == null) {
                enteSiam.setOrgEnteConvenzOrgs(new ArrayList<>());
            }
            enteSiam.getOrgEnteConvenzOrgs().add(ente);

            if (organizIam.getOrgEnteConvenzOrgs() == null) {
                organizIam.setOrgEnteConvenzOrgs(new ArrayList<>());
            }
            organizIam.getOrgEnteConvenzOrgs().add(ente);

            helper.insertEntity(ente, true);

            calcolaServiziErogatiSuUltimoAccordo(idEnteConvenz);
            LOGGER.debug(
                    "Salvataggio dell'associazione ente convenzionato / struttura versante effettuato con successo");
            idEnteConvenzOrg = ente.getIdEnteConvenzOrg();
        } catch (Exception ex) {
            LOGGER.error(
                    "Errore imprevisto durante il salvataggio dell'associazione ente convenzionato / struttura versante: "
                            + ExceptionUtils.getRootCauseMessage(ex),
                    ex);
            throw new ParerUserError(
                    "Eccezione imprevista durante il salvataggio dell'associazione ente convenzionato / struttura versante");
        }
        return idEnteConvenzOrg;
    }

    /**
     * Ritorna il tableBean contenente i tipi accordo
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgTipoAccordoTableBean getOrgTipoAccordoTableBean() throws ParerUserError {
        OrgTipoAccordoTableBean tipoAccordoTableBean = new OrgTipoAccordoTableBean();
        List<OrgTipoAccordo> tipoAccordoList = helper.retrieveOrgTipoAccordo();
        if (!tipoAccordoList.isEmpty()) {
            try {
                tipoAccordoTableBean = (OrgTipoAccordoTableBean) Transform.entities2TableBean(tipoAccordoList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dei tipi accordo " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista di tipi accordo");
            }
        }
        return tipoAccordoTableBean;
    }

    public OrgTipoAccordoTableBean getOrgTipoAccordoNoClasseEnteTableBean() throws ParerUserError {
        OrgTipoAccordoTableBean tipoAccordoTableBean = new OrgTipoAccordoTableBean();
        List<OrgTipoAccordo> tipoAccordoList = helper.retrieveOrgTipoAccordoNoClasseEnte();
        if (!tipoAccordoList.isEmpty()) {
            try {
                tipoAccordoTableBean = (OrgTipoAccordoTableBean) Transform.entities2TableBean(tipoAccordoList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dei tipi accordo " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista di tipi accordo");
            }
        }
        return tipoAccordoTableBean;
    }

    /**
     * Ritorna il tableBean contenente i tipi gestione accordo
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgTipiGestioneAccordoTableBean getOrgTipiGestioneAccordoTableBean() throws ParerUserError {
        OrgTipiGestioneAccordoTableBean tipiGestioneAccordoTableBean = new OrgTipiGestioneAccordoTableBean();
        List<OrgTipiGestioneAccordo> tipiGestioneAccordoList = helper.retrieveOrgTipiGestioneAccordo();
        if (!tipiGestioneAccordoList.isEmpty()) {
            try {
                tipiGestioneAccordoTableBean = (OrgTipiGestioneAccordoTableBean) Transform
                        .entities2TableBean(tipiGestioneAccordoList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero dei tipi gestione accordo " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero della lista di tipi gestione accordo");
            }
        }
        return tipiGestioneAccordoTableBean;
    }

    /**
     * Ritorna il tableBean contenente i codici della classi degli enti convenzionati
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgClasseEnteConvenzTableBean getOrgClasseEnteConvenzTableBean() throws ParerUserError {
        OrgClasseEnteConvenzTableBean classeEnteConvenzTableBean = new OrgClasseEnteConvenzTableBean();
        List<OrgClasseEnteConvenz> classeEnteConvenzList = helper.retrieveOrgClasseEnteConvenz();
        if (!classeEnteConvenzList.isEmpty()) {
            try {
                classeEnteConvenzTableBean = (OrgClasseEnteConvenzTableBean) Transform
                        .entities2TableBean(classeEnteConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero delle classi enti convenzionati "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero delle classi enti convenzionati");
            }
        }
        return classeEnteConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente i tariffari in base al tipo di accordo. La sintassi prevista \u00E8 la seguente:
     * [TipoAccordo]_[DataInizioValidit\u00E0]
     *
     * @param idTipoAccordo
     *            id tipo accordo
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgTariffarioTableBean getOrgTariffarioTableBean(BigDecimal idTipoAccordo) throws ParerUserError {
        OrgTariffarioTableBean tariffarioTableBean = new OrgTariffarioTableBean();
        List<OrgTariffario> tariffarioList = helper.retrieveOrgTariffario(idTipoAccordo);
        if (!tariffarioList.isEmpty()) {
            try {
                for (OrgTariffario tariffario : tariffarioList) {
                    OrgTariffarioRowBean tariffarioRowBean = (OrgTariffarioRowBean) Transform
                            .entity2RowBean(tariffario);
                    tariffarioTableBean.add(tariffarioRowBean);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero dei tariffari " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero dei tariffari");
            }
        }
        return tariffarioTableBean;
    }

    /**
     * Restituisce il rowbean contenente il dettaglio di un accordo ente
     *
     * @param idAccordoEnte
     *            l'id dell'accordo da recuperare su DB @return, il rowbean contenente il dettaglio accordo
     *
     * @return row bean {@link OrgAccordoEnteRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAccordoEnteRowBean getOrgAccordoEnteRowBean(BigDecimal idAccordoEnte) throws ParerUserError {
        OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordoEnte);
        OrgAccordoEnteRowBean row = null;
        try {
            row = (OrgAccordoEnteRowBean) Transform.entity2RowBean(accordoEnte);
            row.setString("nm_ente_convenz_gestore",
                    accordoEnte.getOrgEnteSiamByIdEnteConvenzGestore().getNmEnteSiam());
            row.setString("nm_ente_convenz_conserv",
                    accordoEnte.getOrgEnteSiamByIdEnteConvenzConserv().getNmEnteSiam());
            row.setString("nm_ente_convenz_amministratore",
                    accordoEnte.getOrgEnteSiamByIdEnteConvenzAmministratore().getNmEnteSiam());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero dell'accordo ente " + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Restituisce il rowbean contenente il dettaglio di una gestione accordo
     *
     * @param idGestAccordo
     *            id gestione accordo
     *
     * @return row bean {@link OrgGestioneAccordoRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgGestioneAccordoRowBean getOrgGestioneAccordoRowBean(BigDecimal idGestAccordo) throws ParerUserError {
        OrgGestioneAccordo gestioneAccordo = helper.findById(OrgGestioneAccordo.class, idGestAccordo);
        OrgGestioneAccordoRowBean row = null;
        try {
            row = (OrgGestioneAccordoRowBean) Transform.entity2RowBean(gestioneAccordo);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero della gestione accordo " + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Restituisce il rowbean contenente il dettaglio di un modulo info
     *
     * @param idModulo
     *            id modulo
     *
     * @return row bean {@link OrgModuloInfoAccordoRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgModuloInfoAccordoRowBean getOrgModuloInfoAccordoRowBean(BigDecimal idModulo) throws ParerUserError {
        OrgModuloInfoAccordo moduloInfo = helper.findById(OrgModuloInfoAccordo.class, idModulo);
        OrgModuloInfoAccordoRowBean row = null;
        try {
            row = (OrgModuloInfoAccordoRowBean) Transform.entity2RowBean(moduloInfo);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero del modulo info accordo " + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Restituisce il tablebean rappresentante la lista dei servizi erogati in base ad un accordo.
     *
     * @param idAccordoEnte
     *            id accordo ente
     *
     * @return il tablebean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgServizioErogTableBean getOrgServizioErogTableBean(BigDecimal idAccordoEnte) throws ParerUserError {
        OrgServizioErogTableBean servizioErogTableBean = new OrgServizioErogTableBean();
        List<OrgServizioErog> servizioErogList = helper.retrieveOrgServizioErog(idAccordoEnte);
        if (!servizioErogList.isEmpty()) {
            try {
                for (OrgServizioErog servizioErog : servizioErogList) {
                    OrgServizioErogRowBean servizioErogRowBean = (OrgServizioErogRowBean) Transform
                            .entity2RowBean(servizioErog);
                    servizioErogRowBean.setString("cd_tipo_servizio",
                            servizioErog.getOrgTipoServizio().getCdTipoServizio());
                    servizioErogRowBean.setString("tipo_fatturazione",
                            servizioErog.getOrgTipoServizio().getTipoFatturazione());
                    servizioErogRowBean.setBigDecimal("id_tipo_servizio",
                            new BigDecimal(servizioErog.getOrgTipoServizio().getIdTipoServizio()));
                    servizioErogRowBean.setString("ti_classe_tipo_servizio",
                            servizioErog.getOrgTipoServizio().getTiClasseTipoServizio());
                    servizioErogTableBean.add(servizioErogRowBean);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero dei servizi erogati sull'accordo avente id " + idAccordoEnte
                        + " " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError(
                        "Errore durante il recupero dei servizi erogati sull'accordo avente id " + idAccordoEnte);
            }
        }
        return servizioErogTableBean;
    }

    /**
     * Restituisce il tablebean rappresentante la lista delle gestioni di un accordo.
     *
     * @param idAccordoEnte
     *            id accordo ente
     *
     * @return il tablebean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgGestioneAccordoTableBean getOrgGestioneAccordoTableBean(BigDecimal idAccordoEnte) throws ParerUserError {
        OrgGestioneAccordoTableBean gestioneAccordoTableBean = new OrgGestioneAccordoTableBean();
        if (idAccordoEnte != null) {
            List<OrgGestioneAccordo> gestioneAccordoList = helper.retrieveOrgGestioneAccordo(idAccordoEnte);
            try {
                if (!gestioneAccordoList.isEmpty()) {
                    for (OrgGestioneAccordo gestioneAccordo : gestioneAccordoList) {
                        OrgGestioneAccordoRowBean gestioneAccordoRowBean = (OrgGestioneAccordoRowBean) Transform
                                .entity2RowBean(gestioneAccordo);
                        if (gestioneAccordo.getCdRegistroGestAccordo() != null
                                && gestioneAccordo.getAaGestAccordo() != null
                                && gestioneAccordo.getCdKeyGestAccordo() != null) {
                            gestioneAccordoRowBean.setString("key_id_gest_accordo",
                                    gestioneAccordo.getCdRegistroGestAccordo() + " - "
                                            + gestioneAccordo.getAaGestAccordo() + " - "
                                            + gestioneAccordo.getCdKeyGestAccordo());
                        } else {
                            gestioneAccordoRowBean.setString("key_id_gest_accordo",
                                    gestioneAccordo.getPgGestAccordo().toString());
                        }
                        gestioneAccordoRowBean.setString("tipo_gestione_accordo",
                                gestioneAccordo.getOrgTipiGestioneAccordo().getCdTipoGestioneAccordo());

                        if (gestioneAccordo.getNmFileGestAccordo() == null) {
                            gestioneAccordoRowBean.setString("download_visibile", "0");
                        } else {
                            gestioneAccordoRowBean.setString("download", "download");
                            gestioneAccordoRowBean.setString("download_visibile", "1");
                        }

                        gestioneAccordoTableBean.add(gestioneAccordoRowBean);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero delle gestioni sull'accordo avente id " + idAccordoEnte + " "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError(
                        "Errore durante il recupero delle gestioni sull'accordo avente id " + idAccordoEnte);
            }
        }
        return gestioneAccordoTableBean;
    }

    /**
     * Restituisce il tablebean rappresentante la lista dei moduli informazioni in base ad un accordo.
     *
     * @param idAccordoEnte
     *            id accordo ente
     *
     * @return il tablebean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgModuloInfoAccordoTableBean getOrgModuloInfoAccordoTableBean(BigDecimal idAccordoEnte)
            throws ParerUserError {
        OrgModuloInfoAccordoTableBean orgModuloInfoAccordoTableBean = new OrgModuloInfoAccordoTableBean();
        List<OrgModuloInfoAccordo> moduloInfoList = helper.retrieveOrgModuloInfoAccordo(idAccordoEnte);
        if (!moduloInfoList.isEmpty()) {
            try {
                for (OrgModuloInfoAccordo moduloInfo : moduloInfoList) {
                    OrgModuloInfoAccordoRowBean servizioErogRowBean = (OrgModuloInfoAccordoRowBean) Transform
                            .entity2RowBean(moduloInfo);
                    if (servizioErogRowBean.getCdModuloInfo() == null) {
                        String str = servizioErogRowBean.getCdRegistroModuloInfo() + " - "
                                + servizioErogRowBean.getAaModuloInfo() + " - "
                                + servizioErogRowBean.getCdKeyModuloInfo();
                        servizioErogRowBean.setString("chiave", str);
                    }
                    if (moduloInfo.getNmFileModuloInfo() == null) {
                        servizioErogRowBean.setString("download_visibile", "0");
                    } else {
                        servizioErogRowBean.setString("download", "download");
                        servizioErogRowBean.setString("download_visibile", "1");
                    }
                    orgModuloInfoAccordoTableBean.add(servizioErogRowBean);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero dei moduli info sull'accordo avente id " + idAccordoEnte + " "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError(
                        "Errore durante il recupero dei moduli info sull'accordo avente id " + idAccordoEnte);
            }
        }
        return orgModuloInfoAccordoTableBean;
    }

    /**
     * Restituisce il tablebean rappresentante la lista dei moduli informazioni in base ad un ente convenzionato.
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tablebean
     *
     * @throws ParerUserError
     *             eccezione
     */
    public OrgModuloInfoAccordoTableBean getOrgModuloInfoAccordoByEnteTableBean(BigDecimal idEnteConvenz)
            throws ParerUserError {
        OrgModuloInfoAccordoTableBean orgModuloInfoAccordoTableBean = new OrgModuloInfoAccordoTableBean();
        OrgEnteSiam enteConvenz = helper.findById(OrgEnteSiam.class, idEnteConvenz);
        List<OrgModuloInfoAccordo> moduloInfoList = helper
                .retrieveOrgModuloInfoAccordoByEnte(BigDecimal.valueOf(enteConvenz.getIdEnteSiam()));
        if (!moduloInfoList.isEmpty()) {
            try {
                for (OrgModuloInfoAccordo moduloInfo : moduloInfoList) {
                    OrgModuloInfoAccordoRowBean servizioErogRowBean = (OrgModuloInfoAccordoRowBean) Transform
                            .entity2RowBean(moduloInfo);
                    if (servizioErogRowBean.getCdModuloInfo() == null) {
                        String str = servizioErogRowBean.getCdRegistroModuloInfo() + " - "
                                + servizioErogRowBean.getAaModuloInfo() + " - "
                                + servizioErogRowBean.getCdKeyModuloInfo();
                        servizioErogRowBean.setString("chiave", str);
                    }
                    if (moduloInfo.getNmFileModuloInfo() == null) {
                        servizioErogRowBean.setString("download_visibile", "0");
                    } else {
                        servizioErogRowBean.setString("download", "download");
                        servizioErogRowBean.setString("download_visibile", "1");
                    }
                    orgModuloInfoAccordoTableBean.add(servizioErogRowBean);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero dei moduli info sull'ente convenzionato avente id "
                        + enteConvenz.getIdEnteSiam() + " " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero dei moduli info sull'ente convenzionato avente id "
                        + enteConvenz.getIdEnteSiam());
            }
        }
        return orgModuloInfoAccordoTableBean;
    }

    public OrgServizioErogRowBean getOrgServizioErogRowBean(BigDecimal idServizioErog) throws ParerUserError {
        OrgServizioErog servizioErog = helper.findById(OrgServizioErog.class, idServizioErog);
        OrgServizioErogRowBean row = null;
        try {
            if (servizioErog != null) {
                row = (OrgServizioErogRowBean) Transform.entity2RowBean(servizioErog);
                row.setString("tipo_fatturazione", servizioErog.getOrgTipoServizio().getTipoFatturazione());
                if (servizioErog.getOrgTariffa() != null) {
                    row.setString("nm_tariffa", servizioErog.getOrgTariffa().getNmTariffa());
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero del servizio erogato " + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    /**
     * Ricava un array bidimensionale con gli id/nomi di ambiente/ente/struttura della struttura il cui id \u00E8
     * salvato come nome parametro STRUT_UNITA_DOC_ACCORDO nella tabella IAM_PARAM_APPLIC
     *
     *
     * @param idAmbienteEnteConvenz
     *            id ambiente convenzionato
     * @param idEnteConvenz
     *            id ente convenzionato @return, uno string array contenente 3 elementi, nell'ordine nomeAmbiente,
     *            nomeEnte e nomeStruttura
     *
     * @return matrice ambiente/ente
     *
     * @throws ParerUserError
     *             errore generico
     */
    public String[][] getAmbEnteStrutByIamParamApplic(BigDecimal idAmbienteEnteConvenz, BigDecimal idEnteConvenz)
            throws ParerUserError {
        BigDecimal idOrganizApplic = null;
        // BigDecimal(paramHelper.getValoreParamApplic(ConstIamParamApplic.NmParamApplic.STRUT_UNITA_DOC_ACCORDO.name()));
        try {
            String nmEnteUnitaDocAccordo = paramHelper.getValoreParamApplic(
                    ConstIamParamApplic.NmParamApplic.NM_ENTE_UNITA_DOC_ACCORDO.name(), idAmbienteEnteConvenz, null,
                    Constants.TipoIamVGetValAppart.AMBIENTEENTECONVENZ);
            String nmStrutUnitaDocAccordo = paramHelper.getValoreParamApplic(
                    ConstIamParamApplic.NmParamApplic.NM_STRUT_UNITA_DOC_ACCORDO.name(), idAmbienteEnteConvenz, null,
                    Constants.TipoIamVGetValAppart.AMBIENTEENTECONVENZ);
            idOrganizApplic = utentiHelper.getIdOrganizIamByParam(nmEnteUnitaDocAccordo, nmStrutUnitaDocAccordo);
        } catch (Exception e) {
            throw new ParerUserError(
                    "Attenzione: in base ai parametri ente e struttura definiti sull'ambiente ente convenzionato, non è possibile ricavare un ambiente di SACER e quindi accedere al dettaglio. Assicurarsi che i valori dei parametri siano esatti");
        }
        UsrOrganizIam organizIam = helper.getUsrOrganizIam("SACER", "STRUTTURA", idOrganizApplic);
        String[][] ambienteEnteStruttura = new String[3][3];
        if (organizIam != null) {
            // Ambiente
            ambienteEnteStruttura[0][0] = organizIam.getUsrOrganizIam().getUsrOrganizIam().getIdOrganizApplic()
                    .toString();
            ambienteEnteStruttura[0][1] = organizIam.getUsrOrganizIam().getUsrOrganizIam().getNmOrganiz();
            ambienteEnteStruttura[0][2] = "" + organizIam.getUsrOrganizIam().getUsrOrganizIam().getIdOrganizIam();
            // Ente
            ambienteEnteStruttura[1][0] = organizIam.getUsrOrganizIam().getIdOrganizApplic().toString();
            ambienteEnteStruttura[1][1] = organizIam.getUsrOrganizIam().getNmOrganiz();
            ambienteEnteStruttura[1][2] = "" + organizIam.getUsrOrganizIam().getIdOrganizIam();
            // Struttura
            ambienteEnteStruttura[2][0] = organizIam.getIdOrganizApplic().toString();
            ambienteEnteStruttura[2][1] = organizIam.getNmOrganiz();
            ambienteEnteStruttura[2][2] = "" + organizIam.getIdOrganizIam();
        } else {
            throw new ParerUserError(
                    "Attenzione: in base ai parametri ente e struttura definiti sull'ambiente ente convenzionato, non è possibile ricavare un ambiente di SACER e quindi accedere al dettaglio. Assicurarsi che i valori dei parametri siano esatti");
        }
        return ambienteEnteStruttura;
    }

    public boolean existsTariffaPerTariffarioClasseEnte(BigDecimal idTariffario, BigDecimal idClasseEnteConvenz) {
        boolean exists = false;
        List<OrgTariffa> tariffe = helper.retrieveOrgTariffa(null, idTariffario, idClasseEnteConvenz);
        for (OrgTariffa tariffa : tariffe) {
            if (tariffa.getTipoTariffa().equals(ConstOrgTariffa.TipoTariffa.VALORE_UNITARIO_SCAGLIONI_STORAGE.name())) {
                exists = true;
            }
        }
        return exists;
    }

    /**
     * Metodo di insert di un nuovo accordo
     *
     * @param param
     *            parametri per il logging
     * @param idEnteConvenz
     *            l'ente convenzionato cui fa riferimento l'accordo da salvare
     * @param accordoDetail
     *            i campi di dettaglio da salvare
     * @param idAmbienteEnteConvenzCambio
     *            ambiente scelto per il cambio gestore
     * @param idStruttureVersantiList
     *            le strutture versanti dell'ente convenzionato cui fa riferimento l'accordo da salvare
     * @param idUserIamCor
     *            utente corrente
     * @param tipiServizioCompilati
     *            bean tipo servizi {@link TipoServizioAccordoList}
     * @param nuovoAccordo
     *            flag nuovo accordo
     *
     * @return id del nuovo accordo
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveAccordo(LogParam param, BigDecimal idEnteConvenz, AccordoDetail accordoDetail,
            BigDecimal idAmbienteEnteConvenzCambio, List<BigDecimal> idStruttureVersantiList,
            TipoServizioAccordoList tipiServizioCompilati, long idUserIamCor, boolean nuovoAccordo)
            throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio dell'accordo");
        Date dtDecAccordo;
        Date dtScadAccordo;
        Date dtFineValidAccordo;
        BigDecimal idEnteConvenzGestore;
        BigDecimal idEnteConvenzConserv;
        BigDecimal idClasseEnteConvenz;
        BigDecimal idTariffario;
        BigDecimal niAbitanti;
        try {
            // Ricavo la data di scadenza dell'accordo
            dtScadAccordo = accordoDetail.getDt_scad_accordo().parse();
            // Ricavo la data di inizio validità dell'accordo
            dtDecAccordo = accordoDetail.getDt_dec_accordo().parse();
            // Ricavo la data di fine validita dell'accordo
            dtFineValidAccordo = accordoDetail.getDt_fine_valid_accordo().parse();
            // Ricavo l'ente convenzionato gestore
            idEnteConvenzGestore = accordoDetail.getId_ente_convenz_gestore().parse();
            // Ricavo l'ente convenzionato conservatore
            idEnteConvenzConserv = accordoDetail.getId_ente_convenz_conserv().parse();
            // Ricavo la classe di appartenenza dell'ente
            idClasseEnteConvenz = accordoDetail.getId_classe_ente_convenz().parse();
            // Ricavo il tariffario
            idTariffario = accordoDetail.getId_tariffario().parse();
            // Numero abitanti
            niAbitanti = accordoDetail.getNi_abitanti().parse();
        } catch (EMFError ex) {
            LOGGER.error(
                    "Errore imprevisto durante il salvataggio dell'accordo : " + ExceptionUtils.getRootCauseMessage(ex),
                    ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'accordo");
        }

        if (idEnteConvenzGestore == null) {
            throw new ParerUserError("Sull'ambiente dell'ente convenzionato non \u00E8 definito il gestore.");
        }

        if (idEnteConvenzConserv == null) {
            throw new ParerUserError("Sull'ambiente dell'ente convenzionato non \u00E8 definito il conservatore.");
        }

        if (idTariffario != null && idClasseEnteConvenz != null) {
            List<OrgTariffa> tariffe = helper.retrieveOrgTariffa(null, idTariffario, idClasseEnteConvenz);
            for (OrgTariffa tariffa : tariffe) {
                if (tariffa.getTipoTariffa()
                        .equals(ConstOrgTariffa.TipoTariffa.VALORE_UNITARIO_SCAGLIONI_STORAGE.name())) {
                    if (niAbitanti == null) {
                        throw new ParerUserError("Il campo numero abitanti \u00E8 obbligatorio");
                    }
                }
            }
        }

        // Controllo che sull'ente non esistano altri accordi con data di inizio validità nulla
        if (helper.isDtDecAccordoEmpty(idEnteConvenz)) {
            throw new ParerUserError(
                    "Impossibile inserire un nuovo accordo: esiste gi\u00E0  un accordo con data di inizio validità non valorizzata");
        }
        if ((dtDecAccordo != null && DateUtils.truncate(dtDecAccordo, Calendar.DATE).equals(DateUtil.MAX_DATE))
                || DateUtils.truncate(dtFineValidAccordo, Calendar.DATE).equals(DateUtil.MAX_DATE)) {
            if (helper.isDtDecDtFineValIndefinite(idEnteConvenz)) {
                throw new ParerUserError(
                        "Impossibile inserire un nuovo accordo: esiste gi\u00E0  un accordo con data di inizio validità e/o di fine validità valorizzati con 31/12/2444");
            }
        }

        // In caso di cambio gestore sull'accordo, aggiorno l'accordo precedente definendo data di fine validità pari
        // all'inizio nuovo accordo meno 1 gg
        if (idAmbienteEnteConvenzCambio != null) {
            OrgAccordoEnte lastAccordoEnte = (OrgAccordoEnte) helper.retrieveOrgAccordoEntePiuRecente(idEnteConvenz);
            if (lastAccordoEnte != null) { // dovrebbe essere sempre NON nullo
                Calendar cal = Calendar.getInstance();
                cal.setTime(dtDecAccordo);
                cal.add(Calendar.DATE, -1);
                lastAccordoEnte.setDtFineValidAccordo(cal.getTime());
            }
        }

        // NUOVA GESTIONE (MEV#22367)
        // In caso di nuovo accordo, se il precedente accordo ha data scadenza < data fine validità, il sistema ne
        // imposta la data di fine validità = data di scadenza di tale accordo.
        if (nuovoAccordo) {
            OrgAccordoEnte lastAccordoEnte = (OrgAccordoEnte) helper.retrieveOrgAccordoEntePiuRecente(idEnteConvenz);
            if (lastAccordoEnte != null
                    && lastAccordoEnte.getDtScadAccordo().before(lastAccordoEnte.getDtFineValidAccordo())) {
                lastAccordoEnte.setDtFineValidAccordo(lastAccordoEnte.getDtScadAccordo());
            }
        }
        // end MEV#22367

        // Controllo sovrapposizione periodi
        if (helper.checkEsistenzaAltriAccordiDtDecDtFineValid(idEnteConvenz, dtDecAccordo, dtFineValidAccordo, null)) {
            throw new ParerUserError(
                    "Esiste almeno un accordo il cui intervallo di validit\u00E0  si sovrappone al presente accordo");
        }

        Long idAccordoEnte = null;
        try {

            OrgAccordoEnte accordoEnte = new OrgAccordoEnte();
            setDatiAccordo(idEnteConvenz, accordoEnte, accordoDetail);

            // Persisto l'accordo con i servizi erogati
            helper.insertEntity(accordoEnte, true);

            // In caso di primo accordo sull'ente, modifico i campi di last_accordo dell'ente
            OrgEnteSiam enteConvenz = (OrgEnteSiam) helper.findById(OrgEnteSiam.class, idEnteConvenz);

            insertTipiServizioCompilati(tipiServizioCompilati, accordoEnte);

            insertAnnualitaAutomatiche(accordoEnte);

            //////////////////////////////////////////
            // Imposto i Servizi Erogati da salvare //
            //////////////////////////////////////////
            calcolaServiziErogatiSuAccordo(accordoEnte);

            // In caso di cambio gestore sull'accordo, aggiorno l'ambiente dell'ente ed eseguo l'aggiornamento delle
            // abilitazioni
            if (idAmbienteEnteConvenzCambio != null) {
                BigDecimal idAmbienteEnteConvenzOld = BigDecimal
                        .valueOf(enteConvenz.getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz());

                // Determina gli utenti per cui è definita una dichiarazione di abilitazione relativa all'ambiente
                // con scopo UN_AMBIENTE dell'ente prima della modifica
                List<Object[]> utenti = userHelper.getUtentiDichAbilUnAmbiente(idAmbienteEnteConvenzOld);

                // Elimino le abilitazioni derivanti dalla dichiarazione
                for (Object[] utenteDich : utenti) {
                    long idUserIam = (Long) utenteDich[0];
                    long idDichAbilOrganiz = (Long) utenteDich[1];
                    userHelper.deleteAbilitazioniUtenteUnAmbiente(idEnteConvenz.longValue(), idUserIam,
                            idDichAbilOrganiz);
                }

                // Aggiorno l'ambiente ente convenzionato definito sull'ente produttore dando il valore dell'ambiente
                // scelto in inserimento accordo
                OrgAmbienteEnteConvenz ambienteEnteConvenzNew = (OrgAmbienteEnteConvenz) helper
                        .findById(OrgAmbienteEnteConvenz.class, idAmbienteEnteConvenzCambio);
                enteConvenz.setOrgAmbienteEnteConvenz(ambienteEnteConvenzNew);

                // Determina gli utenti per cui è definita una dichiarazione di abilitazione relativa all'ambiente
                // dell'ente dopo della modifica
                utenti = userHelper.getUtentiDichAbilUnAmbiente(idAmbienteEnteConvenzCambio);

                // Inserisco le dichiarazioni di abilitazione e le abilitazioni enti convenzionati per il nuovo ambiente
                for (Object[] utenteDich : utenti) {
                    long idUserIam = (Long) utenteDich[0];
                    userHelper.aggiungiAbilEnteToAdd(idUserIam);
                }
            }

            LOGGER.debug("Salvataggio dell'accordo completato");
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    idEnteConvenz, param.getNomePagina());
            idAccordoEnte = accordoEnte.getIdAccordoEnte();
        } catch (ParerUserError ex) {
            throw new ParerUserError(ex.getDescription());
        } catch (Exception ex) {
            LOGGER.error(
                    "Errore imprevisto durante il salvataggio dell'accordo : " + ExceptionUtils.getRootCauseMessage(ex),
                    ex);
            throw new ParerUserError("Errore imprevisto durante il salvataggio dell'accordo");
        }
        return idAccordoEnte;
    }

    /**
     * Metodo di update di un accordo
     *
     * @param param
     *            parametri per il logging
     * @param idEnteConvenz
     *            l'ente convenzionato di cui fa parte l'accordo
     * @param idAccordo
     *            l'id dell'accordo che sto modificando
     * @param accordoDetail
     *            i campi di dettaglio con le modifiche
     * @param serviziErogatiPresenti
     *            true se servizi erogati presenti
     * @param tipoServizioAccordoList
     *            bean {@link TipoServizioAccordoList}
     * @param isEditMode
     *            true se modalità modifica attiva
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveAccordo(LogParam param, BigDecimal idEnteConvenz, BigDecimal idAccordo, AccordoDetail accordoDetail,
            boolean serviziErogatiPresenti, TipoServizioAccordoList tipoServizioAccordoList, boolean isEditMode)
            throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio dell'accordo");
        Date dtDecAccordo;
        Date dtScadAccordo;
        Date dtFineValidAccordo;
        BigDecimal idClasseEnteConvenz;
        BigDecimal idTariffario;
        BigDecimal niAbitanti;
        try {
            // Ricavo la data di inizio validità dell'accordo
            dtDecAccordo = accordoDetail.getDt_dec_accordo().parse();
            // Ricavo la data di scadenza dell'accordo
            dtScadAccordo = accordoDetail.getDt_scad_accordo().parse();
            // Ricavo la data di fine validitÃƒÆ’Ã‚Â  dell'accordo
            dtFineValidAccordo = accordoDetail.getDt_fine_valid_accordo().parse();
            // Ricavo la classe di appartenenza dell'ente
            idClasseEnteConvenz = accordoDetail.getId_classe_ente_convenz().parse();
            // Ricavo il tariffario
            idTariffario = accordoDetail.getId_tariffario().parse();
            // Numero abitanti
            niAbitanti = accordoDetail.getNi_abitanti().parse();
        } catch (EMFError ex) {
            LOGGER.error(
                    "Errore imprevisto durante il salvataggio dell'accordo : " + ExceptionUtils.getRootCauseMessage(ex),
                    ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'accordo");
        }

        if (idTariffario != null && idClasseEnteConvenz != null) {
            List<OrgTariffa> tariffe = helper.retrieveOrgTariffa(null, idTariffario, idClasseEnteConvenz);
            for (OrgTariffa tariffa : tariffe) {
                if (tariffa.getTipoTariffa()
                        .equals(ConstOrgTariffa.TipoTariffa.VALORE_UNITARIO_SCAGLIONI_STORAGE.name())) {
                    if (niAbitanti == null) {
                        throw new ParerUserError("Il campo numero abitanti \u00E8 obbligatorio");
                    }
                }
            }
        }

        // Controllo che sull'ente non esistano altri accordi con data di inizio validità nulla
        if (dtDecAccordo == null && helper.isDtDecAccordoEmpty(idEnteConvenz)) {
            throw new ParerUserError(
                    "Impossibile modificare il presente accordo: esiste gi\u00E0  un accordo con data di inizio validità non valorizzata");
        }
        // Controllo sovrapposizione periodi
        if (helper.checkEsistenzaAltriAccordiDtDecDtFineValid(idEnteConvenz, dtDecAccordo, dtFineValidAccordo,
                idAccordo)) {
            throw new ParerUserError(
                    "Esiste almeno un accordo il cui intervallo di validit\u00E0  si sovrappone al presente accordo");
        }

        OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordo);

        // Gestione in modifica dei Tipi servizio
        manageTipiServizioAccordo(accordoEnte, tipoServizioAccordoList, serviziErogatiPresenti, isEditMode);

        try {
            setDatiAccordo(idEnteConvenz, accordoEnte, accordoDetail);
            // Gestione modifica accordo chiuso
            Date dataCorrente = new Date();
            if (accordoEnte.getFlAccordoChiuso().equals("1")) {
                if (accordoEnte.getDtDecAccordo() != null && accordoEnte.getDtFineValidAccordo() != null) {
                    if (!accordoEnte.getDtDecAccordo().after(dataCorrente)
                            && !accordoEnte.getDtFineValidAccordo().before(dataCorrente)) {
                        accordoEnte.setFlAccordoChiuso("0");
                    }
                } else {
                    accordoEnte.setFlAccordoChiuso("0");
                }
            }
            helper.getEntityManager().flush();

            // //////////////////////////////////////////
            // // Imposto i Servizi Erogati da salvare //
            // //////////////////////////////////////////
            // calcolaServiziErogatiSuAccordo(accordoEnte);
            if (accordoEnte.getFlAccordoChiuso().equals("1")) {
                String tiStatoAccordo = helper
                        .getTiStatoAccordoById(BigDecimal.valueOf(accordoEnte.getIdAccordoEnte()));
                if (tiStatoAccordo != null) {
                    if (tiStatoAccordo.equals("Accordo valido")
                            || tiStatoAccordo.equals("Accordo valido in corso rinnovo")) {
                        accordoEnte.setFlAccordoChiuso("0");
                    }
                }
            }

            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    idEnteConvenz, param.getNomePagina());
            LOGGER.debug("Salvataggio dell'accordo completato");
        } catch (Exception ex) {
            LOGGER.error(
                    "Errore imprevisto durante il salvataggio dell'accordo : " + ExceptionUtils.getRootCauseMessage(ex),
                    ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'accordo");
        }
    }

    /**
     * Metodo di chiusura di un accordo
     *
     * @param param
     *            parametri per il logging
     * @param idEnteConvenz
     *            l'ente convenzionato di cui fa parte l'accordo
     * @param idAccordo
     *            l'id dell'accordo che sto chiudendo
     * @param dtFineValidAccordo
     *            la data di chiusura dell'accordo
     * @param dsNoteAccordo
     *            le note dell'accordo
     * @param noteChiusura
     *            le note di chiusura del popup
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void chiusuraAccordo(LogParam param, BigDecimal idEnteConvenz, BigDecimal idAccordo, Date dtFineValidAccordo,
            String dsNoteAccordo, String noteChiusura) throws ParerUserError {
        LOGGER.debug("Eseguo la chiusura dell'accordo");

        OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordo);
        // Gestione data di fine validitÃƒÆ’Ã‚Â 
        accordoEnte.setDtFineValidAccordo(dtFineValidAccordo);
        // Gestione note accordo
        if (StringUtils.isNotBlank(noteChiusura)) {
            String separator = "";
            if (StringUtils.isNotBlank(dsNoteAccordo)) {
                separator = "-------------";
            }
            accordoEnte.setDsNoteAccordo(
                    StringUtils.defaultIfBlank(accordoEnte.getDsNoteAccordo(), "") + separator + noteChiusura);
        }
        // Gestione flag accordo chiuso
        accordoEnte.setFlAccordoChiuso("1");
        try {
            helper.mergeEntity(accordoEnte);

            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    idEnteConvenz, param.getNomePagina());
            LOGGER.debug("Chiusura dell'accordo completata");
        } catch (Exception ex) {
            LOGGER.error(
                    "Errore imprevisto durante la chiusura dell'accordo : " + ExceptionUtils.getRootCauseMessage(ex),
                    ex);
            throw new ParerUserError("Eccezione imprevista durante la chiusura dell'accordo");
        }
    }

    /**
     * Metodo di insert di un servizio erogato
     *
     * @param param
     *            parametri per il logging
     * @param idAccordoEnte
     *            id accordo ente
     * @param annualitaAccordoDetail
     *            bean {@link AnnualitaAccordoDetail}
     * @param tipoServizioAnnualitaAccordoList
     *            bean {@link TipoServizioAnnualitaAccordoList}
     *
     * @return pk
     *
     * @throws ParerUserError
     *             errore generico
     * @throws EMFError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveAnnualitaAccordo(LogParam param, BigDecimal idAccordoEnte,
            AnnualitaAccordoDetail annualitaAccordoDetail,
            TipoServizioAnnualitaAccordoList tipoServizioAnnualitaAccordoList) throws ParerUserError, EMFError {
        LOGGER.debug("Eseguo il salvataggio dell'annualit\u00E0  accordo");

        OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordoEnte);

        // Controlli
        BigDecimal anno = annualitaAccordoDetail.getAa_anno_accordo().parse();

        if (helper.checkEsistenzaAnnualitaPerAccordo(anno, idAccordoEnte)) {
            throw new ParerUserError("Sull'accordo \u00E8 gi\u00E0 presente l'annualit\u00E0  per l'anno indicato");
        }

        // Controllo data futura
        Date dtDecAccordo = accordoEnte.getDtDecAccordo();
        Date dtScadAccordo = accordoEnte.getDtScadAccordo();
        Calendar c = Calendar.getInstance();
        c.setTime(dtDecAccordo);
        int annoDecAccordo = c.get(Calendar.YEAR);
        c.setTime(dtScadAccordo);
        int annoScadAccordo = c.get(Calendar.YEAR);
        if (anno == null || !(annoDecAccordo <= anno.intValue() && anno.intValue() <= annoScadAccordo)) {
            throw new ParerUserError("L'anno non \u00E8 compreso nella validit\u00E0  ");
        }

        Long idAaAccordo = null;
        try {
            OrgAaAccordo annualita = new OrgAaAccordo();

            setDatiAnnualita(accordoEnte, annualita, annualitaAccordoDetail);

            // Persisto l'annualità
            helper.insertEntity(annualita, true);

            for (BaseRow rb : (BaseTable) tipoServizioAnnualitaAccordoList.getTable()) {
                BigDecimal imTariffaAaAccordo = null;
                try {
                    imTariffaAaAccordo = new BigDecimal(rb.getString("im_tariffa_aa_accordo"));

                } catch (NumberFormatException e) {
                    // Mi serve solo per avere un importo giusto
                }
                if (imTariffaAaAccordo != null) {
                    OrgTariffaAaAccordo tariffaAaAccordo = helper.getOrgTariffaAaAccordo(
                            BigDecimal.valueOf(annualita.getIdAaAccordo()), rb.getBigDecimal("id_tipo_servizio"));
                    if (tariffaAaAccordo != null) {
                        tariffaAaAccordo.setImTariffaAaAccordo(imTariffaAaAccordo);
                    } else {
                        OrgTariffaAaAccordo tariffaAaAccordo2 = new OrgTariffaAaAccordo();
                        tariffaAaAccordo2.setImTariffaAaAccordo(imTariffaAaAccordo);
                        tariffaAaAccordo2.setOrgAaAccordo(annualita);
                        tariffaAaAccordo2.setOrgTipoServizio(
                                helper.findById(OrgTipoServizio.class, rb.getBigDecimal("id_tipo_servizio")));
                        if (annualita.getOrgTariffaAaAccordos() == null) {
                            annualita.setOrgTariffaAaAccordos(new ArrayList<>());
                        }
                        annualita.getOrgTariffaAaAccordos().add(tariffaAaAccordo2);
                        helper.getEntityManager().persist(tariffaAaAccordo2);
                        helper.getEntityManager().flush();
                    }
                }
            }

            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(accordoEnte.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
            LOGGER.debug("Salvataggio dell'annualit\u00E0 accordo completato");
            idAaAccordo = annualita.getIdAaAccordo();
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio dell'annualit\u00E0  accordo : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'annualit\u00E0  accordo");
        }
        return idAaAccordo;
    }

    /**
     * Metodo di update di un servizio erogato
     *
     * @param param
     *            parametri per il logging
     * @param idAccordoEnte
     *            id accordo ente
     * @param idAaAccordo
     *            id anno accordo
     * @param annualitaAccordoDetail
     *            bean {@link AnnualitaAccordoDetail}
     * @param tipoServizioAnnualitaAccordoList
     *            bean {@link TipoServizioAnnualitaAccordoList}
     *
     * @throws ParerUserError
     *             errore generico
     * @throws EMFError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveAnnualitaAccordo(LogParam param, BigDecimal idAccordoEnte, BigDecimal idAaAccordo,
            AnnualitaAccordoDetail annualitaAccordoDetail,
            TipoServizioAnnualitaAccordoList tipoServizioAnnualitaAccordoList) throws ParerUserError, EMFError {
        LOGGER.debug("Eseguo il salvataggio dell'annualit\u00E0  accordo");
        OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordoEnte);

        // Controlli
        BigDecimal anno = annualitaAccordoDetail.getAa_anno_accordo().parse();
        OrgAaAccordo annualita = helper.findById(OrgAaAccordo.class, idAaAccordo);

        if (annualita.getAaAnnoAccordo().compareTo(anno) != 0
                && helper.checkEsistenzaAnnualitaPerAccordo(anno, idAccordoEnte)) {
            throw new ParerUserError("Sull'accordo \u00E8 gi\u00E0  presente l'annualit\u00E0  per l'anno indicato");
        }

        // Controllo data futura
        Date dtDecAccordo = accordoEnte.getDtDecAccordo();
        Date dtScadAccordo = accordoEnte.getDtScadAccordo();
        Calendar c = Calendar.getInstance();
        c.setTime(dtDecAccordo);
        int annoDecAccordo = c.get(Calendar.YEAR);
        c.setTime(dtScadAccordo);
        int annoScadAccordo = c.get(Calendar.YEAR);
        if (anno == null || !(annoDecAccordo <= anno.intValue() && anno.intValue() <= annoScadAccordo)) {
            throw new ParerUserError("L'anno non \u00E8 compreso nella validit\u00E0  ");
        }

        // Gestione in modifica dei Tipi servizio
        manageTipiServizioAnnualitaAccordo(annualita, tipoServizioAnnualitaAccordoList);

        try {

            setDatiAnnualita(accordoEnte, annualita, annualitaAccordoDetail);
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(accordoEnte.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
            LOGGER.debug("Salvataggio dell'annualit\u00E0 accordo completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio dell'annualit\u00E0  accordo : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'annualit\u00E0  accordo");
        }
    }

    /**
     * Metodo di insert di un servizio erogato
     *
     * @param param
     *            parametri per il logging
     * @param idAccordoEnte
     *            id accordo ente
     * @param servizioErogatoDetail
     *            bean ServizioErogatoDetail
     *
     * @return pk
     *
     * @throws ParerUserError
     *             errore generico
     * @throws EMFError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveServizioErogato(LogParam param, BigDecimal idAccordoEnte,
            ServizioErogatoDetail servizioErogatoDetail) throws ParerUserError, EMFError {
        LOGGER.debug("Eseguo il salvataggio del servizio erogato");
        String tiClasseTipoServizio = ((OrgTipoServizio) helper.findById(OrgTipoServizio.class,
                servizioErogatoDetail.getId_tipo_servizio().parse())).getTiClasseTipoServizio();
        BigDecimal idSistemaVersante = null;
        Date dtErog = null;

        try {
            idSistemaVersante = servizioErogatoDetail.getId_sistema_versante().parse();
            dtErog = servizioErogatoDetail.getDt_erog().parse();
        } catch (EMFError ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del servizio erogato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del servizio erogato");
        }

        // Controlli
        if (tiClasseTipoServizio
                .equals(ConstOrgTipoServizio.TiClasseTipoServizio.ATTIVAZIONE_SISTEMA_VERSANTE.name())) {
            // Controllo che il sistema versante sia valorizzato
            if (idSistemaVersante == null) {
                throw new ParerUserError(
                        "La classe del tipo servizio \u00E8 ATTIVAZIONE_SISTEMA_VERSANTE: \u00E8 necessario indicare il sistema versante");
            }

            if (helper.checkEsistenzaServiziErogatiPerAccordo(null, idAccordoEnte,
                    servizioErogatoDetail.getId_tipo_servizio().parse(), idSistemaVersante)) {
                throw new ParerUserError(
                        "Sull'accordo esiste gi\u00E0 un servizio erogato con lo stesso tipo servizio e lo stesso sistema versante");
            }

        }
        if (!tiClasseTipoServizio
                .equals(ConstOrgTipoServizio.TiClasseTipoServizio.ATTIVAZIONE_SISTEMA_VERSANTE.name())) {
            // Controllo che il sistema versante NON sia valorizzato
            if (idSistemaVersante != null) {
                throw new ParerUserError(
                        "La classe del tipo servizio \u00E8 CONSERVAZIONE o ALTRO: NON \u00E8 possibile indicare il sistema versante");
            }
        }
        if (tiClasseTipoServizio.equals(ConstOrgTipoServizio.TiClasseTipoServizio.ALTRO.name())
                || tiClasseTipoServizio.equals(ConstOrgTipoServizio.TiClasseTipoServizio.CONSERVAZIONE.name())) {
            if (helper.checkEsistenzaServiziErogatiPerAccordo(null, idAccordoEnte,
                    servizioErogatoDetail.getId_tipo_servizio().parse(), null)) {
                throw new ParerUserError(
                        "Sull'accordo esiste gi\u00E0 un servizio erogato con lo stesso tipo servizio");
            }
        }
        // Controllo obbligatorietÃƒÆ’Ã‚Â  data erogazione
        if (tiClasseTipoServizio.equals(ConstOrgTipoServizio.TiClasseTipoServizio.ALTRO.name())) {
            if (dtErog == null) {
                throw new ParerUserError(
                        "La classe del tipo servizio \u00E8 ALTRO: \u00E8 obbligatorio indicare la data di erogazione");
            }
        }
        // Controllo data futura
        if (dtErog != null && dtErog.after(new Date())) {
            throw new ParerUserError("Non \u00E8 possibile indicare una data di erogazione futura");
        }

        Long idServizioErogato = null;
        try {
            OrgServizioErog servizioErogato = new OrgServizioErog();
            OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordoEnte);
            setDatiServizioErogato(accordoEnte, servizioErogato, servizioErogatoDetail, accordoEnte.getFlPagamento());

            // Persisto il servizio erogato
            helper.insertEntity(servizioErogato, true);
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(accordoEnte.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
            LOGGER.debug("Salvataggio del servizio erogato completato");
            idServizioErogato = servizioErogato.getIdServizioErogato();
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del servizio erogato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del servizio erogato");
        }
        return idServizioErogato;
    }

    /**
     * Metodo di update di un servizio erogato
     *
     * @param param
     *            parametri per logging
     * @param idAccordoEnte
     *            id accordo ente
     * @param idServizioErogato
     *            id servizio erogato
     * @param servizioErogatoDetail
     *            bean {@link ServizioErogatoDetail}
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveServizioErogato(LogParam param, BigDecimal idAccordoEnte, BigDecimal idServizioErogato,
            ServizioErogatoDetail servizioErogatoDetail) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio del servizio erogato");
        BigDecimal idSistemaVersante = null;
        Date dtErog = null;
        String tiClasseTipoServizio = null;
        BigDecimal idTipoServizio = null;
        try {
            idSistemaVersante = servizioErogatoDetail.getId_sistema_versante().parse();
            dtErog = servizioErogatoDetail.getDt_erog().parse();
            tiClasseTipoServizio = ((OrgTipoServizio) helper.findById(OrgTipoServizio.class,
                    servizioErogatoDetail.getId_tipo_servizio().parse())).getTiClasseTipoServizio();
            idTipoServizio = servizioErogatoDetail.getId_tipo_servizio().parse();
        } catch (EMFError ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del servizio erogato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del servizio erogato");
        }
        // Controlli
        if (tiClasseTipoServizio
                .equals(ConstOrgTipoServizio.TiClasseTipoServizio.ATTIVAZIONE_SISTEMA_VERSANTE.name())) {
            if (helper.checkEsistenzaServiziErogatiPerAccordo(idServizioErogato, idAccordoEnte, idTipoServizio,
                    idSistemaVersante)) {
                throw new ParerUserError(
                        "Sull'accordo esiste gi\u00E0 un servizio erogato con lo stesso tipo servizio e lo stesso sistema versante");
            }
        }
        if (tiClasseTipoServizio.equals(ConstOrgTipoServizio.TiClasseTipoServizio.ALTRO.name())
                || tiClasseTipoServizio.equals(ConstOrgTipoServizio.TiClasseTipoServizio.CONSERVAZIONE.name())) {
            if (helper.checkEsistenzaServiziErogatiPerAccordo(idServizioErogato, idAccordoEnte, idTipoServizio, null)) {
                throw new ParerUserError(
                        "Sull'accordo esiste gi\u00E0 un servizio erogato con lo stesso tipo servizio");
            }
        }
        // Controllo obbligatorietà data erogazione
        if (tiClasseTipoServizio.equals(ConstOrgTipoServizio.TiClasseTipoServizio.ALTRO.name())) {
            if (dtErog == null) {
                throw new ParerUserError(
                        "La classe del tipo servizio \u00E8 ALTRO: \u00E8 obbligatorio indicare la data di erogazione");
            }
        }
        // Controllo data futura
        if (dtErog != null && dtErog.after(new Date())) {
            throw new ParerUserError("Non \u00E8 possibile indicare una data di erogazione futura");
        }

        try {
            OrgServizioErog servizioDrogato = helper.findById(OrgServizioErog.class, idServizioErogato);
            OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordoEnte);
            setDatiServizioErogato(accordoEnte, servizioDrogato, servizioErogatoDetail,
                    servizioErogatoDetail.getFl_pagamento().parse());
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(accordoEnte.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
            LOGGER.debug("Salvataggio del servizio erogato completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del servizio erogato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del servizio erogato");
        }
    }

    /**
     * Metodo di salvataggio di una gestione accordo
     *
     * @param param
     *            parametri per il logging
     * @param idAccordoEnte
     *            id accordo ente
     * @param idGestAccordo
     *            id gestione accordo
     * @param cdRegistroGestAccordo
     *            codice registro accordo
     * @param aaGestAccordo
     *            anno gestione accordo
     * @param cdKeyGestAccordo
     *            numero gestione accordo
     * @param dtGestAccordo
     *            data accordo
     * @param dsNotaGestAccordo
     *            nota
     * @param dsGestAccordo
     *            descrizione accordo
     * @param blGestAccordo
     *            blob accordo
     * @param nmFileGestAccordo
     *            nome file
     * @param tipoTrasmissione
     *            tipo trasmissione
     * @param pgGestAccordo
     *            progressivo
     * @param idGestAccordoRisposta
     *            id riposta
     * @param idTipoGestioneAccordo
     *            id tipo gestione accordo
     * @param nmAmbiente
     *            nome ambiente
     * @param nmEnte
     *            nome ente
     * @param nmStrut
     *            nome struttura
     *
     * @return pk
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long saveGestioneAccordo(LogParam param, BigDecimal idAccordoEnte, BigDecimal idGestAccordo,
            String cdRegistroGestAccordo, BigDecimal aaGestAccordo, String cdKeyGestAccordo, Date dtGestAccordo,
            String dsNotaGestAccordo, String dsGestAccordo, byte[] blGestAccordo, String nmFileGestAccordo,
            BigDecimal pgGestAccordo, BigDecimal idTipoGestioneAccordo, TipoTrasmissione tipoTrasmissione,
            BigDecimal idGestAccordoRisposta, String nmAmbiente, String nmEnte, String nmStrut) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio della gestione accordo");

        if (nmFileGestAccordo != null && nmFileGestAccordo.length() > 100) {
            throw new ParerUserError(
                    "Il nome associato al file di gestione accordo supera i 100 caratteri. Rinominare il file con una lunghezza inferiore<br>");
        }

        OrgGestioneAccordo gestioneAccordo = null;
        OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordoEnte);
        boolean esisteGestione = helper.esisteGestionePerChiave(idAccordoEnte, cdRegistroGestAccordo, aaGestAccordo,
                cdKeyGestAccordo);

        // Se sono in inserimento
        if (idGestAccordo == null) {
            if (esisteGestione) {
                throw new ParerUserError(
                        "Esiste gi\u00E0 una gestione accordo con il numero di protocollo inserito<br>");
            }
            gestioneAccordo = new OrgGestioneAccordo();
            accordoEnte.addOrgGestioneAccordo(gestioneAccordo);
        } // Se sono in modifica
        else {
            gestioneAccordo = helper.findById(OrgGestioneAccordo.class, idGestAccordo);
            if ((esisteGestione && (!cdRegistroGestAccordo.equals(gestioneAccordo.getCdRegistroGestAccordo())
                    || !aaGestAccordo.equals(gestioneAccordo.getAaGestAccordo())
                    || !cdKeyGestAccordo.equals(gestioneAccordo.getCdKeyGestAccordo())))) {
                throw new ParerUserError(
                        "Esiste gi\u00E0 una gestione accordo con il numero di protocollo inserito<br>");
            }
        }

        try {
            gestioneAccordo.setCdRegistroGestAccordo(cdRegistroGestAccordo);
            gestioneAccordo.setAaGestAccordo(aaGestAccordo);
            gestioneAccordo.setCdKeyGestAccordo(cdKeyGestAccordo);
            gestioneAccordo.setDtGestAccordo(dtGestAccordo);
            gestioneAccordo.setDsNotaGestAccordo(dsNotaGestAccordo);
            gestioneAccordo.setDsGestAccordo(dsGestAccordo);
            gestioneAccordo.setPgGestAccordo(pgGestAccordo);
            gestioneAccordo.setTipoTrasmissione(tipoTrasmissione);
            gestioneAccordo.setIdGestAccordoRisposta(idGestAccordoRisposta);
            gestioneAccordo.setEnteGestAccordo(nmEnte);
            gestioneAccordo.setStrutturaGestAccordo(nmStrut);
            if (idTipoGestioneAccordo != null) {
                OrgTipiGestioneAccordo tipoGestioneAccordo = helper.getEntityManager()
                        .find(OrgTipiGestioneAccordo.class, idTipoGestioneAccordo.longValue());
                gestioneAccordo.setOrgTipiGestioneAccordo(tipoGestioneAccordo);
            } else {
                gestioneAccordo.setOrgTipiGestioneAccordo(null);
            }

            // se il nome del file della gestione \u00E8 nullo, controllo se esiste il blobbo e non esiste già salvato
            // un blobbo, allora azzeralo sto blobbo!
            if (nmFileGestAccordo == null) {
                if (idGestAccordo == null) {
                    gestioneAccordo.setBlGestAccordo(null);
                } else {
                    OrgGestioneAccordo gestioneAccordoDB = helper.getEntityManager().find(OrgGestioneAccordo.class,
                            idGestAccordo.longValue());
                    if (gestioneAccordoDB.getBlGestAccordo() != null) {
                        nmFileGestAccordo = gestioneAccordoDB.getNmFileGestAccordo();
                    }
                }
            } else {
                gestioneAccordo.setBlGestAccordo(blGestAccordo);
            }
            gestioneAccordo.setNmFileGestAccordo(nmFileGestAccordo);
            //
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(accordoEnte.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
            LOGGER.debug("Salvataggio della gestione accordo completata");
            return gestioneAccordo.getIdGestAccordo();
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio della gestione accordo : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio della gestione accordo");
        }
    }

    /**
     * Metodo di salvataggio di un modulo informazioni in dettaglio ente convenzionato
     *
     * @param param
     *            parametri per il logging
     * @param idAccordoEnte
     *            id accordo
     * @param idModuloInfoAccordo
     *            id modulo
     * @param dtRicev
     *            data ricezione
     * @param cdRegistroModuloInfo
     *            codice registro
     * @param aaModuloInfo
     *            anno modulo
     * @param cdKeyModuloInfo
     *            numero modulo
     * @param cdModuloInfo
     *            codice modulo
     * @param blModuloInfo
     *            blob modulo
     * @param nmFileModuloInfo
     *            nome file
     * @param dsModuloInfo
     *            descrizione modulo
     * @param nmAmbiente
     *            nome ambiente
     * @param nmEnte
     *            nome ente
     * @param nmStrut
     *            nome struttura
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return pk primary key
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long saveModuloInformazioni(LogParam param, BigDecimal idAccordoEnte, BigDecimal idModuloInfoAccordo,
            Date dtRicev, String cdRegistroModuloInfo, BigDecimal aaModuloInfo, String cdKeyModuloInfo,
            String cdModuloInfo, byte[] blModuloInfo, String nmFileModuloInfo, String dsModuloInfo, String nmAmbiente,
            String nmEnte, String nmStrut, BigDecimal idEnteConvenz) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio del modulo informazioni");

        OrgModuloInfoAccordo moduloInfoAccordo = null;
        // OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordoEnte);
        OrgEnteSiam enteConvenz = helper.findById(OrgEnteSiam.class, idEnteConvenz);
        boolean esisteModulo = helper.esisteModelloInfoPerDataIdentificativoEnteConvenz(idEnteConvenz, cdModuloInfo,
                cdRegistroModuloInfo, aaModuloInfo, cdKeyModuloInfo);

        // Se sono in inserimento
        if (idModuloInfoAccordo == null) {
            if (esisteModulo) {
                throw new ParerUserError("Esiste gi\u00E0 un modello informazioni con l'identificativo inserito<br>");
            }
            moduloInfoAccordo = new OrgModuloInfoAccordo();
            enteConvenz.addOrgModuloInfoAccordo(moduloInfoAccordo);
        } // Se sono in modifica
        else {
            moduloInfoAccordo = helper.findById(OrgModuloInfoAccordo.class, idModuloInfoAccordo);
            // Se sto consideranto l'identificativo...
            if (cdModuloInfo != null) {
                // ...controllo non sia già stato inserito, ovviamente escludendo sé stesso
                if (esisteModulo && !cdModuloInfo.equals(moduloInfoAccordo.getCdModuloInfo())) {
                    throw new ParerUserError(
                            "Esiste gi\u00E0 un modello informazioni con l'identificativo inserito<br>");
                }
            } // Se invece considero la terna registro/anno/numero...
            else {
                if (esisteModulo && (!cdRegistroModuloInfo.equals(moduloInfoAccordo.getCdRegistroModuloInfo())
                        || !aaModuloInfo.equals(moduloInfoAccordo.getAaModuloInfo())
                        || !cdKeyModuloInfo.equals(moduloInfoAccordo.getCdKeyModuloInfo()))) {
                    throw new ParerUserError(
                            "Esiste gi\u00E0 un modello informazioni con l'identificativo inserito<br>");
                }
            }
        }

        try {
            moduloInfoAccordo.setAaModuloInfo(aaModuloInfo);
            moduloInfoAccordo.setCdModuloInfo(cdModuloInfo);
            moduloInfoAccordo.setCdKeyModuloInfo(cdKeyModuloInfo);
            moduloInfoAccordo.setCdRegistroModuloInfo(cdRegistroModuloInfo);
            moduloInfoAccordo.setDtRicev(dtRicev);
            moduloInfoAccordo.setDsModuloInfo(dsModuloInfo);
            moduloInfoAccordo.setNmEnte(nmEnte);
            moduloInfoAccordo.setNmStrut(nmStrut);
            if (blModuloInfo != null) {
                moduloInfoAccordo.setBlModuloInfo(blModuloInfo);
                moduloInfoAccordo.setNmFileModuloInfo(nmFileModuloInfo);
            }
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(enteConvenz.getIdEnteSiam()), param.getNomePagina());
            LOGGER.debug("Salvataggio del modulo informazioni completato");
            return moduloInfoAccordo.getIdModuloInfoAccordo();

        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del modulo informazioni : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del modulo informazioni");
        }
    }

    private void setDatiServizioErogato(OrgAccordoEnte accordoEnte, OrgVCreaServErogByAcc creaServErogByAcc) {
        OrgServizioErog servizioErog = new OrgServizioErog();
        servizioErog.setImValoreTariffa(creaServErogByAcc.getImValoreTariffa());
        if (creaServErogByAcc.getIdTariffa() != null) {
            OrgTariffa tariffa = helper.findById(OrgTariffa.class, creaServErogByAcc.getIdTariffa());
            servizioErog.setOrgTariffa(tariffa);
        }
        if (creaServErogByAcc.getIdTariffaAccordo() != null) {
            OrgTariffaAccordo tariffaAcc = helper.findById(OrgTariffaAccordo.class,
                    creaServErogByAcc.getIdTariffaAccordo());
            servizioErog.setOrgTariffaAccordo(tariffaAcc);
        }
        if (creaServErogByAcc.getIdTariffaAaAccordo() != null) {
            OrgTariffaAaAccordo tariffaAa = helper.findById(OrgTariffaAaAccordo.class,
                    creaServErogByAcc.getIdTariffaAaAccordo());
            servizioErog.setOrgTariffaAaAccordo(tariffaAa);
        }

        servizioErog.setFlPagamento(creaServErogByAcc.getFlPagamento());
        AplSistemaVersante sistemaVersante = creaServErogByAcc.getIdSistemaVersante() != null
                ? helper.findById(AplSistemaVersante.class, creaServErogByAcc.getIdSistemaVersante()) : null;

        servizioErog.setAplSistemaVersante(sistemaVersante);
        servizioErog.setDtErog(creaServErogByAcc.getDtErog());

        servizioErog.setOrgTipoServizio(helper.findById(OrgTipoServizio.class, creaServErogByAcc.getIdTipoServizio()));
        servizioErog.setNmServizioErogato(creaServErogByAcc.getOrgVCreaServErogByAccId().getNmServizioErogato());
        servizioErog.setOrgAccordoEnte(accordoEnte);

        if (accordoEnte.getOrgServizioErogs() == null) {
            accordoEnte.setOrgServizioErogs(new ArrayList<>());
        }
        accordoEnte.getOrgServizioErogs().add(servizioErog);
        helper.getEntityManager().persist(servizioErog);
    }

    private void setDatiAnnualita(OrgAccordoEnte accordoEnte, OrgAaAccordo aaAccordo,
            AnnualitaAccordoDetail aaAccordoDetail) throws EMFError {
        aaAccordo.setOrgAccordoEnte(accordoEnte);
        aaAccordo.setAaAnnoAccordo(aaAccordoDetail.getAa_anno_accordo().parse());
        aaAccordo.setCdCapitoloAaAccordo(aaAccordoDetail.getCd_capitolo_aa_accordo().parse());
        aaAccordo.setDsImpegnoAaAccordo(aaAccordoDetail.getDs_impegno_aa_accordo().parse());
        aaAccordo.setCdCigAaAccordo(aaAccordoDetail.getCd_cig_aa_accordo().parse());
        aaAccordo.setCdCupAaAccordo(aaAccordoDetail.getCd_cup_aa_accordo().parse());
        aaAccordo.setCdOdaAaAccordo(aaAccordoDetail.getCd_oda_aa_accordo().parse());
        aaAccordo.setDsAttoAaAccordo(aaAccordoDetail.getDs_atto_aa_accordo().parse());
        aaAccordo.setDsNotaAaAccordo(aaAccordoDetail.getDs_nota_aa_accordo().parse());
        // aaAccordo.setImCanoneAaAccordo(aaAccordoDetail.getI().parse());
        aaAccordo.setMmAaAccordo(aaAccordoDetail.getMm_aa_accordo().parse());

        //
        if (accordoEnte.getOrgAaAccordos() == null) {
            accordoEnte.setOrgAaAccordos(new ArrayList<>());
        }
        accordoEnte.getOrgAaAccordos().add(aaAccordo);
        //

    }

    private void setDatiServizioErogato(OrgAccordoEnte accordoEnte, OrgServizioErog servizioErogato,
            ServizioErogatoDetail servizioErogatoDetail, String flPagamento) throws EMFError {
        servizioErogato.setOrgAccordoEnte(accordoEnte);
        servizioErogato.setNmServizioErogato(servizioErogatoDetail.getNm_servizio_erogato().parse());
        OrgTipoServizio tipoServizio = helper.findById(OrgTipoServizio.class,
                servizioErogatoDetail.getId_tipo_servizio().parse());
        servizioErogato.setOrgTipoServizio(tipoServizio);
        servizioErogato.setFlPagamento(flPagamento);
        // Tariffa
        long idTariffario = accordoEnte.getOrgTariffario().getIdTariffario();
        String nmTariffa = servizioErogatoDetail.getNm_tariffa().parse();
        if (nmTariffa != null) {
            OrgTariffa tariffa = helper.retrieveOrgTariffa(idTariffario, nmTariffa);
            servizioErogato.setOrgTariffa(tariffa);
            if (tariffa.getOrgServizioErogs() == null) {
                tariffa.setOrgServizioErogs(new ArrayList<>());
            }
            tariffa.getOrgServizioErogs().add(servizioErogato);
        }

        servizioErogato.setImValoreTariffa(servizioErogatoDetail.getIm_valore_tariffa().parse());
        servizioErogato.setDtErog(servizioErogatoDetail.getDt_erog().parse());
        servizioErogato.setNtServizioErog(servizioErogatoDetail.getNt_servizio_erog().parse());
        BigDecimal idSistemaVersante;
        if ((idSistemaVersante = servizioErogatoDetail.getId_sistema_versante().parse()) != null) {
            AplSistemaVersante sistemaVersante = helper.findById(AplSistemaVersante.class, idSistemaVersante);
            servizioErogato.setAplSistemaVersante(sistemaVersante);
            if (sistemaVersante.getOrgServizioErogs() == null) {
                sistemaVersante.setOrgServizioErogs(new ArrayList<>());
            }
            sistemaVersante.getOrgServizioErogs().add(servizioErogato);
        }
        //
        if (accordoEnte.getOrgServizioErogs() == null) {
            accordoEnte.setOrgServizioErogs(new ArrayList<>());
        }
        accordoEnte.getOrgServizioErogs().add(servizioErogato);
        //
        if (tipoServizio.getOrgServizioErogs() == null) {
            tipoServizio.setOrgServizioErogs(new ArrayList<>());
        }
        tipoServizio.getOrgServizioErogs().add(servizioErogato);
        //

    }

    /**
     * Recupera i registri riferiti alla struttura passata come parametro
     *
     * @param idOrganizApplic
     *            l'id dell'organizzazione nell'applicazione
     *
     * @return un basetable riferito al tipo dato registro
     *
     * @throws ParerUserError
     *             errore generico
     */
    public BaseTable getRegistriStrutturaTableBean(BigDecimal idOrganizApplic) throws ParerUserError {
        String nmApplic = "SACER";
        String nmTipoOrganiz = "STRUTTURA";
        String nmClasseTipoDato = "REGISTRO";
        List<UsrTipoDatoIam> tipoDatoIamList = helper.getUsrTipoDatoIam(nmApplic, nmTipoOrganiz, idOrganizApplic,
                nmClasseTipoDato);
        BaseTable tipoDatoTable = new BaseTable();
        if (!tipoDatoIamList.isEmpty()) {
            try {
                for (UsrTipoDatoIam tipoDatoIam : tipoDatoIamList) {
                    BaseRow tipoDatoRow = new BaseRow();
                    tipoDatoRow.setBigDecimal("id_registro", tipoDatoIam.getIdTipoDatoApplic());
                    tipoDatoRow.setString("cd_registro", tipoDatoIam.getNmTipoDato());
                    tipoDatoTable.add(tipoDatoRow);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero dei registri " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero dei registri ");
            }
        }
        return tipoDatoTable;
    }

    /**
     * Metodo di eliminazione di un accordo
     *
     * @param param
     *            parametri per il logging
     * @param idAccordo
     *            id accordo
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgAccordoEnte(LogParam param, BigDecimal idAccordo) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'accordo");
        try {
            OrgAccordoEnte accordo = helper.findById(OrgAccordoEnte.class, idAccordo);
            helper.removeEntity(accordo, true);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(accordo.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'accordo ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di eliminazione di una annualità
     *
     * @param param
     *            parametri per il logging
     * @param idAaAccordo
     *            id anno accordo
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgAaAccordo(LogParam param, BigDecimal idAaAccordo) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'annualit\u00E0 ");
        try {
            OrgAaAccordo aaAccordo = helper.findById(OrgAaAccordo.class, idAaAccordo);
            helper.removeEntity(aaAccordo, true);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(aaAccordo.getOrgAccordoEnte().getOrgEnteSiam().getIdEnteSiam()),
                    param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'annualit\u00E0  ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di eliminazione di un servizio erogato
     *
     * @param param
     *            parametri per logging
     * @param idServizioErogato
     *            id servizio erogato
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgServizioErog(LogParam param, BigDecimal idServizioErogato) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del servizio erogato");
        try {
            OrgServizioErog servizioErog = helper.findById(OrgServizioErog.class, idServizioErogato);
            helper.removeEntity(servizioErog, true);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(servizioErog.getOrgAccordoEnte().getOrgEnteSiam().getIdEnteSiam()),
                    param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del servizio erogato ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di eliminazione di una gestione accordo
     *
     * @param param
     *            parametri per il logging
     * @param idGestAccordo
     *            id gestione accordo
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteGestioneAccordo(LogParam param, BigDecimal idGestAccordo) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione della gestione accordo");
        try {
            OrgGestioneAccordo gestioneAccordo = helper.findById(OrgGestioneAccordo.class, idGestAccordo);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(gestioneAccordo.getOrgAccordoEnte().getOrgEnteSiam().getIdEnteSiam()),
                    param.getNomePagina());
            helper.removeEntity(gestioneAccordo, true);
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione della gestione accordo ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di eliminazione di un modulo informazioni
     *
     * @param param
     *            parametri per il logging
     * @param idModulo
     *            id modulo
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteModuloInformazioni(LogParam param, BigDecimal idModulo) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del modulo informazioni");
        try {
            OrgModuloInfoAccordo modulo = helper.findById(OrgModuloInfoAccordo.class, idModulo);
            BigDecimal idEnteConvenz = BigDecimal.valueOf(modulo.getOrgEnteConvenz().getIdEnteSiam());
            helper.removeEntity(modulo, true);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO, idEnteConvenz,
                    param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del modulo informazioni ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di eliminazione di una fattura
     *
     * @param param
     *            parametri per logging
     * @param idFatturaEnte
     *            id fatture
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgFatturaEnte(LogParam param, BigDecimal idFatturaEnte) throws ParerUserError {
        String statoFatturaEnte = helper.getUltimoStatoFatturaEnte(idFatturaEnte.longValue());
        if (!statoFatturaEnte.equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.getDescrizione())) {
            throw new ParerUserError(
                    "Non \u00E8 possibile eliminare la fattura in quanto non si trova in stato CALCOLATA<br>");
        }
        LOGGER.debug("Eseguo l'eliminazione della fattura");
        sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA, idFatturaEnte, param.getNomePagina());
        OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnte);
        helper.removeEntity(fatturaEnte, true);
    }

    public OrgServizioFatturaTableBean getOrgServizioFatturaTableBean(BigDecimal idServizioErogato)
            throws ParerUserError {
        OrgServizioFatturaTableBean servizioFatturaTableBean = new OrgServizioFatturaTableBean();
        List<OrgServizioFattura> servizioFatturaList = helper.retrieveOrgServizioFattura(idServizioErogato.longValue());
        if (!servizioFatturaList.isEmpty()) {
            try {
                for (OrgServizioFattura servizioFattura : servizioFatturaList) {
                    OrgServizioFatturaRowBean servizioFatturaRowBean = (OrgServizioFatturaRowBean) Transform
                            .entity2RowBean(servizioFattura);
                    servizioFatturaRowBean.setBigDecimal("aa_servizio_fattura", servizioFattura.getAaServizioFattura());
                    servizioFatturaRowBean.setBigDecimal("im_servizio_fattura", servizioFattura.getImServizioFattura());
                    servizioFatturaRowBean.setString("cd_fattura", servizioFattura.getOrgFatturaEnte().getCdFattura());
                    Date dtRegStatoFatturaEnteCalcolata = getDtRegStatoFatturaEnte(
                            servizioFattura.getOrgFatturaEnte().getOrgStatoFatturaEntes(),
                            ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.name());
                    servizioFatturaRowBean.setTimestamp("dt_reg_stato_fattura_ente_calcolata",
                            dtRegStatoFatturaEnteCalcolata != null
                                    ? new Timestamp(dtRegStatoFatturaEnteCalcolata.getTime()) : null);
                    servizioFatturaRowBean.setString("cd_emis_fattura",
                            servizioFattura.getOrgFatturaEnte().getCdEmisFattura());
                    Date dtRegStatoFatturaEnteEmessa = getDtRegStatoFatturaEnte(
                            servizioFattura.getOrgFatturaEnte().getOrgStatoFatturaEntes(),
                            ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.name());
                    servizioFatturaRowBean.setTimestamp("dt_reg_stato_fattura_ente_emessa",
                            dtRegStatoFatturaEnteEmessa != null ? new Timestamp((dtRegStatoFatturaEnteEmessa).getTime())
                                    : null);
                    servizioFatturaRowBean.setString("ti_stato_fattura_ente",
                            helper.getUltimoStatoFatturaEnte(servizioFattura.getOrgFatturaEnte().getIdFatturaEnte()));
                    Date dtRegStatoFatturaEnteUltimoStato = getDtRegStatoFatturaEnte(
                            servizioFattura.getOrgFatturaEnte().getOrgStatoFatturaEntes(),
                            servizioFatturaRowBean.getString("ti_stato_fattura_ente"));
                    servizioFatturaRowBean.setTimestamp("dt_reg_stato_fattura_ente_ultimo_stato",
                            dtRegStatoFatturaEnteUltimoStato != null
                                    ? new Timestamp((dtRegStatoFatturaEnteUltimoStato).getTime()) : null);
                    servizioFatturaTableBean.add(servizioFatturaRowBean);
                }
            } catch (Exception e) {
                LOGGER.error(
                        "Errore durante il recupero dei servizi fatturati " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero dei servizi fatturati");
            }
        }
        return servizioFatturaTableBean;
    }

    private Date getDtRegStatoFatturaEnte(List<OrgStatoFatturaEnte> statoFatturaEnteList, String tiStatoFatturaEnte) {
        for (OrgStatoFatturaEnte statoFatturaEnte : statoFatturaEnteList) {
            if (statoFatturaEnte != null && statoFatturaEnte.getTiStatoFatturaEnte().equals(tiStatoFatturaEnte)) {
                return statoFatturaEnte.getDtRegStatoFatturaEnte();
            }
        }
        return null;
    }

    public OrgTipoServizioTableBean getOrgTipoServizioTableBean(BigDecimal idClasseEnteConvenz, BigDecimal idTariffario,
            List<BigDecimal> idTipoServizioEsclusi) throws ParerUserError {
        OrgTipoServizioTableBean tipoServizioTableBean = new OrgTipoServizioTableBean();
        if (idClasseEnteConvenz != null && idTariffario != null) {
            List<OrgTipoServizio> tipoServizioList = helper.retrieveOrgTipoServizio(idClasseEnteConvenz.longValue(),
                    idTariffario.longValue(), idTipoServizioEsclusi);
            if (!tipoServizioList.isEmpty()) {
                try {
                    tipoServizioTableBean = (OrgTipoServizioTableBean) Transform.entities2TableBean(tipoServizioList);
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                        | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    LOGGER.error(
                            "Errore durante il recupero dei tipi servizio " + ExceptionUtils.getRootCauseMessage(e), e);
                    throw new ParerUserError("Errore durante il recupero della lista di tipi servizio");
                }
            }
        }
        return tipoServizioTableBean;
    }

    public OrgTipoServizioRowBean getOrgTipoServizioRowBean(BigDecimal idTipoServizio) {
        OrgTipoServizioRowBean tipoServizioRowBean = new OrgTipoServizioRowBean();
        if (idTipoServizio != null) {
            OrgTipoServizio tipoServizio = helper.findById(OrgTipoServizio.class, idTipoServizio);
            if (tipoServizio != null) {
                try {
                    tipoServizioRowBean = (OrgTipoServizioRowBean) Transform.entity2RowBean(tipoServizio);
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                        | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    LOGGER.error(
                            "Errore durante il recupero del tipo servizio " + ExceptionUtils.getRootCauseMessage(e), e);
                }
            }
        }
        return tipoServizioRowBean;
    }

    public Object[] getNmTariffaEImporto(BigDecimal idTipoServizio, BigDecimal idTariffario,
            BigDecimal idClasseEnteConvenz) throws ParerUserError {
        Object[] nmTariffaEImporto = new Object[2];
        if (idTipoServizio != null && idTariffario != null && idClasseEnteConvenz != null) {
            List<OrgTariffa> tariffaList = helper.retrieveOrgTariffa(idTipoServizio, idTariffario, idClasseEnteConvenz);
            if (!tariffaList.isEmpty()) {
                try {
                    OrgTariffa tariffa = tariffaList.get(0);
                    if (tariffa != null) {
                        nmTariffaEImporto[0] = tariffa.getNmTariffa();
                        if (tariffa.getTipoTariffa().equals(ConstOrgTariffa.TipoTariffa.VALORE_SCAGLIONI_STORAGE.name())
                                || tariffa.getTipoTariffa()
                                        .equals(ConstOrgTariffa.TipoTariffa.VALORE_SCAGLIONI_UD_VERSATE.name())) {
                            nmTariffaEImporto[1] = helper.getImValoreTariffaDaScaglione(tariffa.getIdTariffa());
                        } else {
                            nmTariffaEImporto[1] = tariffa.getImValoreFissoTariffa();
                        }
                    }
                } catch (IllegalArgumentException e) {
                    LOGGER.error("Errore durante il recupero delle Entità rimborso costi "
                            + ExceptionUtils.getRootCauseMessage(e), e);
                    throw new ParerUserError("Errore durante il recupero delle Entità rimborso costi");
                }
            }
        }
        return nmTariffaEImporto;
    }

    public BigDecimal getImportoTariffa(BigDecimal idTariffa) {
        BigDecimal importoTariffa = null;
        OrgTariffa tariffa = helper.findById(OrgTariffa.class, idTariffa);
        if (tariffa != null) {
            if (tariffa.getTipoTariffa().equals(ConstOrgTariffa.TipoTariffa.VALORE_SCAGLIONI_STORAGE.name()) || tariffa
                    .getTipoTariffa().equals(ConstOrgTariffa.TipoTariffa.VALORE_SCAGLIONI_UD_VERSATE.name())) {
                importoTariffa = helper.getImValoreTariffaDaScaglione(tariffa.getIdTariffa());
            } else {
                importoTariffa = tariffa.getImValoreFissoTariffa();
            }
        }
        return importoTariffa;
    }

    public OrgTipoServizioTableBean getOrgTipoServizioTableBean() throws ParerUserError {
        OrgTipoServizioTableBean tipoServizioTableBean = new OrgTipoServizioTableBean();
        List<OrgTipoServizio> tipoServizioList = helper.retrieveOrgTipoServizio();
        if (!tipoServizioList.isEmpty()) {
            try {
                tipoServizioTableBean = (OrgTipoServizioTableBean) Transform.entities2TableBean(tipoServizioList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dei tipi servizio " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero della lista di tipi servizio");
            }
        }
        return tipoServizioTableBean;
    }

    /**
     * Metodo di eliminazione di un'associazione struttura versante - ente convenzionato
     *
     * @param param
     *            parametri per il logging
     * @param idEnteConvenzOrg
     *            id ente convezionato/organizzazione
     * @param idEnteConvenzionato
     *            id ente convenzionato
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgEnteConvenzOrg(LogParam param, BigDecimal idEnteConvenzOrg, BigDecimal idEnteConvenzionato)
            throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'associazione struttura versante - ente convenzionato");
        try {
            OrgEnteConvenzOrg associazione = helper.findById(OrgEnteConvenzOrg.class, idEnteConvenzOrg);
            helper.removeEntity(associazione, true);
            calcolaServiziErogatiSuUltimoAccordo(new BigDecimal(associazione.getOrgEnteSiam().getIdEnteSiam()));
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO, idEnteConvenzionato,
                    param.getNomePagina());
        } catch (ParerUserError e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'associazione struttura versante - ente convenzionato ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    public OrgTipoAccordoTableBean getOrgTipoAccordoTableBean(String cdTipoAccordo, String dsTipoAccordo,
            String flPagamento, String idAttivo) throws ParerUserError {
        OrgTipoAccordoTableBean tipoAccordoTableBean = new OrgTipoAccordoTableBean();
        List<OrgTipoAccordo> tipoAccordoList = helper.retrieveOrgTipoAccordoList(cdTipoAccordo, dsTipoAccordo,
                flPagamento, idAttivo);
        if (!tipoAccordoList.isEmpty()) {
            try {
                tipoAccordoTableBean = (OrgTipoAccordoTableBean) Transform.entities2TableBean(tipoAccordoList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero della lista dei tipi accordo "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista dei tipi accordo");
            }
        }
        return tipoAccordoTableBean;
    }

    public OrgTipoAccordoRowBean getOrgTipoAccordoRowBean(BigDecimal idTipoAccordo) throws ParerUserError {
        OrgTipoAccordoRowBean tipoAccordoRowBean = new OrgTipoAccordoRowBean();
        OrgTipoAccordo tipoAccordo = helper.findById(OrgTipoAccordo.class, idTipoAccordo);
        if (tipoAccordo != null) {
            try {
                tipoAccordoRowBean = (OrgTipoAccordoRowBean) Transform.entity2RowBean(tipoAccordo);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero del tipo accordo " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero del tipo accordo");
            }
        }
        return tipoAccordoRowBean;
    }

    /**
     * Metodo di eliminazione di un tipo accordo
     *
     * @param param
     *            parametri per logging
     * @param idTipoAccordo
     *            id tipo accordo
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgTipoAccordo(LogParam param, BigDecimal idTipoAccordo) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del tipo accordo");
        try {
            OrgTipoAccordo tipoAccordo = helper.findById(OrgTipoAccordo.class, idTipoAccordo);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_ACCORDO,
                    new BigDecimal(tipoAccordo.getIdTipoAccordo()), param.getNomePagina());
            helper.removeEntity(tipoAccordo, true);
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del tipo accordo ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di insert di un nuovo tipo accordo
     *
     * @param param
     *            parametri per logging
     * @param cdTipoAccordo
     *            code tipo accordo
     * @param dsTipoAccordo
     *            descrizione tipo accordo
     * @param flPagamento
     *            flag 1/0 (true/false)
     * @param cdAlgoTariffario
     *            tariffario
     * @param dtIniVal
     *            data inizio validita
     * @param dtFineVal
     *            data fine validita
     *
     * @return id del nuovo ente
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveTipoAccordo(LogParam param, String cdTipoAccordo, String dsTipoAccordo, String flPagamento,
            String cdAlgoTariffario, Date dtIniVal, Date dtFineVal) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio del tipo accordo");
        // Controllo esistenza denominazione
        if (helper.getOrgTipoAccordo(cdTipoAccordo) != null) {
            throw new ParerUserError("Il codice del tipo accordo\u00E8 gi\u00E0  presente nel sistema");
        }
        Long idTipoAccordo = null;
        try {
            OrgTipoAccordo tipoAccordo = new OrgTipoAccordo();
            tipoAccordo.setCdTipoAccordo(cdTipoAccordo);
            tipoAccordo.setDsTipoAccordo(dsTipoAccordo);
            tipoAccordo.setFlPagamento(flPagamento);
            tipoAccordo.setCdAlgoTariffario(cdAlgoTariffario);
            tipoAccordo.setDtIstituz(dtIniVal);
            tipoAccordo.setDtSoppres(dtFineVal);
            helper.insertEntity(tipoAccordo, true);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_ACCORDO,
                    new BigDecimal(tipoAccordo.getIdTipoAccordo()), param.getNomePagina());
            LOGGER.debug("Salvataggio del tipo accordo completato");
            idTipoAccordo = tipoAccordo.getIdTipoAccordo();
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del tipo accordo : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del tipo accordo ");
        }
        return idTipoAccordo;
    }

    /**
     * Metodo di update di un tipo accordo
     *
     * @param param
     *            parametri per logging
     * @param idTipoAccordo
     *            id tipo accordo
     * @param cdTipoAccordo
     *            code tipo accordo
     * @param dsTipoAccordo
     *            descrizione tipo accordo
     * @param flPagamento
     *            flag 1/0 (true/false)
     * @param dtIniVal
     *            data inizio validita
     * @param dtFineVal
     *            data fine validita
     * @param cdAlgoTariffario
     *            algoritmo tariffario
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveTipoAccordo(LogParam param, BigDecimal idTipoAccordo, String cdTipoAccordo, String dsTipoAccordo,
            String flPagamento, String cdAlgoTariffario, Date dtIniVal, Date dtFineVal) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio delle modifiche sul tipo accordo");
        // Controllo esistenza denominazione, se modificata
        OrgTipoAccordo tipoAccordoDaVerificare = helper.getOrgTipoAccordo(cdTipoAccordo);
        if (tipoAccordoDaVerificare != null
                && tipoAccordoDaVerificare.getIdTipoAccordo() != idTipoAccordo.longValue()) {
            throw new ParerUserError("Il codice del tipo accordo \u00E8 gi\u00E0  presente nel sistema");
        }
        try {
            OrgTipoAccordo tipoAccordo = helper.findById(OrgTipoAccordo.class, idTipoAccordo);
            tipoAccordo.setCdTipoAccordo(cdTipoAccordo);
            tipoAccordo.setDsTipoAccordo(dsTipoAccordo);
            tipoAccordo.setFlPagamento(flPagamento);
            tipoAccordo.setCdAlgoTariffario(cdAlgoTariffario);
            tipoAccordo.setDtIstituz(dtIniVal);
            tipoAccordo.setDtSoppres(dtFineVal);
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_ACCORDO,
                    new BigDecimal(tipoAccordo.getIdTipoAccordo()), param.getNomePagina());
            LOGGER.debug("Salvataggio del tipo accordo completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del tipo accordo : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del tipo accordo ");
        }
    }

    /**
     * Ritorna il tableBean contenente i tariffari in base ai filtri di ricerca La sintassi prevista \u00E8 la seguente:
     * [TipoAccordo]_[DataInizioValidit\u00E0]
     *
     * @param isValido
     *            flag 1/0 (true/false)
     * @param cdTipoAccordo
     *            codice tipo accordo
     * @param nomeTariffario
     *            nome tariffario
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgTariffarioTableBean getOrgTariffarioTableBean(String isValido, String cdTipoAccordo,
            String nomeTariffario) throws ParerUserError {
        OrgTariffarioTableBean tariffarioTableBean = new OrgTariffarioTableBean();
        List<OrgTariffario> tariffarioList = helper.retrieveOrgTariffario(isValido, cdTipoAccordo, nomeTariffario);
        if (!tariffarioList.isEmpty()) {
            try {
                for (OrgTariffario tariffario : tariffarioList) {
                    OrgTariffarioRowBean tariffarioRowBean = (OrgTariffarioRowBean) Transform
                            .entity2RowBean(tariffario);
                    String valido = "0";
                    final Date date = new Date();
                    if (tariffario.getDtIniVal().before(date) && (tariffario.getDtFineVal().after(date))) {
                        valido = "1";
                    }
                    tariffarioRowBean.setString("is_valido", valido);
                    tariffarioRowBean.setString("cd_tipo_accordo", tariffario.getOrgTipoAccordo().getCdTipoAccordo());
                    tariffarioTableBean.add(tariffarioRowBean);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero dei tariffari " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero dei tariffari");
            }
        }
        return tariffarioTableBean;
    }

    /**
     * Ritorna il tableBean contenente i tipi accordo attivi
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgTipoAccordoTableBean getOrgTipoAccordoAttiviTableBean() throws ParerUserError {
        OrgTipoAccordoTableBean tipoAccordoTableBean = new OrgTipoAccordoTableBean();
        List<OrgTipoAccordo> tipoAccordoList = helper.retrieveOrgTipoAccordoAttivi();
        if (!tipoAccordoList.isEmpty()) {
            try {
                tipoAccordoTableBean = (OrgTipoAccordoTableBean) Transform.entities2TableBean(tipoAccordoList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dei tipi accordo " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista di tipi accordo");
            }
        }
        return tipoAccordoTableBean;
    }

    public OrgTariffarioRowBean getOrgTariffarioRowBean(BigDecimal idTariffario) throws ParerUserError {
        OrgTariffarioRowBean tariffarioRowBean = new OrgTariffarioRowBean();
        OrgTariffario tariffario = helper.findById(OrgTariffario.class, idTariffario);
        if (tariffario != null) {
            try {
                tariffarioRowBean = (OrgTariffarioRowBean) Transform.entity2RowBean(tariffario);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero del tariffario " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero del tariffario ");
            }
        }
        return tariffarioRowBean;
    }

    /**
     * Ritorna il tableBean contenente le Entità rimborso costi in base al tariffario.
     *
     * @param idTariffario
     *            id tariffario
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgTariffaTableBean getOrgTariffaByTariffarioTableBean(BigDecimal idTariffario) throws ParerUserError {
        OrgTariffaTableBean tariffaTableBean = new OrgTariffaTableBean();
        List<OrgTariffa> tariffaList = helper.retrieveOrgTariffa(idTariffario);
        if (!tariffaList.isEmpty()) {
            try {
                for (OrgTariffa tariffa : tariffaList) {
                    OrgTariffaRowBean row = (OrgTariffaRowBean) Transform.entity2RowBean(tariffa);
                    row.setBigDecimal("id_tipo_servizio",
                            BigDecimal.valueOf(tariffa.getOrgTipoServizio().getIdTipoServizio()));
                    row.setString("cd_tipo_servizio", tariffa.getOrgTipoServizio().getCdTipoServizio());
                    tariffaTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero delle Entità rimborso costi "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero delle Entità rimborso costi");
            }
        }
        return tariffaTableBean;
    }

    /**
     * Metodo di eliminazione di un tariffario
     *
     * @param param
     *            parametri per logging
     * @param idTariffario
     *            id tariffario
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgTariffario(LogParam param, BigDecimal idTariffario) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del tariffario");
        try {
            OrgTariffario tariffario = helper.findById(OrgTariffario.class, idTariffario);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_ACCORDO,
                    new BigDecimal(tariffario.getOrgTipoAccordo().getIdTipoAccordo()), param.getNomePagina());
            helper.removeEntity(tariffario, true);
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del tariffario ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di insert di un nuovo tariffario
     *
     * @param param
     *            parametri per logging
     * @param idTipoAccordo
     *            id tipo accordo
     * @param nmTariffario
     *            nome tariffario
     * @param dtIniVal
     *            data inizio validita
     * @param dtFineVal
     *            data fine validita
     *
     * @return id del nuovo tariffario
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveTariffario(LogParam param, BigDecimal idTipoAccordo, String nmTariffario, Date dtIniVal,
            Date dtFineVal) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio del tariffario");
        // Controllo che la denominazione del tariffario non sia già presente nel sistema
        if (helper.checkEsistenzaTariffario(nmTariffario, idTipoAccordo, null)) {
            throw new ParerUserError("Nel sistema \u00E8 gi\u00E0 presente un tariffario con la stessa denominazione");
        }

        Long idTariffario = null;
        try {
            OrgTariffario tariffario = new OrgTariffario();
            tariffario.setOrgTipoAccordo(helper.findById(OrgTipoAccordo.class, idTipoAccordo));
            tariffario.setNmTariffario(nmTariffario);
            tariffario.setDtIniVal(dtIniVal);
            tariffario.setDtFineVal(dtFineVal);
            helper.insertEntity(tariffario, true);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_ACCORDO, idTipoAccordo,
                    param.getNomePagina());
            LOGGER.debug("Salvataggio del tariffario completato");
            idTariffario = tariffario.getIdTariffario();
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del tariffario : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del tariffario");
        }
        return idTariffario;
    }

    /**
     * Metodo di update di un tariffario
     *
     * @param param
     *            parametri per logging
     * @param idTariffario
     *            id tariffario
     * @param idTipoAccordo
     *            id tipo accordo
     * @param nmTariffario
     *            nome tariffario
     * @param dtIniVal
     *            data inizio validita
     * @param dtFineVal
     *            data fine validita
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveTariffario(LogParam param, BigDecimal idTariffario, BigDecimal idTipoAccordo, String nmTariffario,
            Date dtIniVal, Date dtFineVal) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio delle modifiche sul tariffario");
        // Controllo che la denominazione del tariffario non sia già presente nel sistema
        if (helper.checkEsistenzaTariffario(nmTariffario, idTipoAccordo, idTariffario)) {
            throw new ParerUserError("Nel sistema \u00E8 gi\u00E0 presente un tariffario con la stessa denominazione");
        }

        try {
            OrgTariffario tariffario = helper.findById(OrgTariffario.class, idTariffario);
            tariffario.setNmTariffario(nmTariffario);
            tariffario.setOrgTipoAccordo(helper.findById(OrgTipoAccordo.class, idTipoAccordo));
            tariffario.setDtIniVal(dtIniVal);
            tariffario.setDtFineVal(dtFineVal);
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_ACCORDO, idTipoAccordo,
                    param.getNomePagina());
            LOGGER.debug("Salvataggio del tariffario completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del tariffario : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del tariffario");
        }
    }

    /**
     * Ritorna il tableBean contenente i codici della classi degli enti convenzionati in base ai parametri di ricerca
     * passati in input
     *
     * @param cdClasseEnteConvenz
     *            codice classe ente convenzionato
     * @param dsClasseEnteConvenz
     *            descrizione classe ente convenzionato
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgClasseEnteConvenzTableBean getOrgClasseEnteConvenzTableBean(String cdClasseEnteConvenz,
            String dsClasseEnteConvenz) throws ParerUserError {
        OrgClasseEnteConvenzTableBean classeEnteConvenzTableBean = new OrgClasseEnteConvenzTableBean();
        List<OrgClasseEnteConvenz> classeEnteConvenzList = helper.retrieveOrgClasseEnteConvenz(cdClasseEnteConvenz,
                dsClasseEnteConvenz);
        if (!classeEnteConvenzList.isEmpty()) {
            try {
                classeEnteConvenzTableBean = (OrgClasseEnteConvenzTableBean) Transform
                        .entities2TableBean(classeEnteConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero delle classi enti convenzionati "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero delle classi enti convenzionati");
            }
        }
        return classeEnteConvenzTableBean;
    }

    /**
     * Ritorna il tableBean contenente le Entità rimborso costi in base al tipo servizio
     *
     * @param idTipoServizio
     *            id tipo servizio
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgTariffaTableBean getOrgTariffaByTipoServizioTableBean(BigDecimal idTipoServizio) throws ParerUserError {
        OrgTariffaTableBean tariffaTableBean = new OrgTariffaTableBean();
        List<OrgTariffa> tariffaList = helper.retrieveOrgTariffaByTipoServizio(idTipoServizio);
        if (!tariffaList.isEmpty()) {
            try {
                for (OrgTariffa tariffa : tariffaList) {
                    OrgTariffaRowBean row = (OrgTariffaRowBean) Transform.entity2RowBean(tariffa);
                    row.setString("nm_tariffario", tariffa.getOrgTariffario().getNmTariffario());
                    tariffaTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero delle Entità rimborso costi "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero delle Entità rimborso costi");
            }
        }
        return tariffaTableBean;
    }

    public OrgTariffaAccordoTableBean getOrgTariffaAccordoByTipoServizioTableBean(BigDecimal idTipoServizio)
            throws ParerUserError {
        OrgTariffaAccordoTableBean tariffaTableBean = new OrgTariffaAccordoTableBean();
        List<Object[]> tariffaList = helper.retrieveOrgTariffaAccordoByTipoServizio(idTipoServizio);
        if (!tariffaList.isEmpty()) {
            try {
                for (Object[] tariffa : tariffaList) {
                    OrgTariffaAccordoRowBean row = new OrgTariffaAccordoRowBean();
                    Date dtScad = (Date) tariffa[8];
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String registro = (String) tariffa[5];
                    BigDecimal anno = (BigDecimal) tariffa[6];
                    String numero = (String) tariffa[7];
                    row.setString("accordo", (String) tariffa[0] + " - " + registro + " - " + anno + " - " + numero
                            + " - scadenza: " + df.format(dtScad));
                    row.setBigDecimal("im_tariffa_accordo", (BigDecimal) tariffa[3]);
                    row.setString("cd_tipo_servizio", (String) tariffa[4]);
                    tariffaTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero delle Entità rimborso costi accordo "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero delle Entità rimborso costi accordo");
            }
        }
        return tariffaTableBean;
    }

    public OrgTariffaAccordoTableBean getOrgTariffaAnnualitaByTipoServizioTableBean(BigDecimal idTipoServizio)
            throws ParerUserError {
        OrgTariffaAccordoTableBean tariffaTableBean = new OrgTariffaAccordoTableBean();
        List<Object[]> tariffaList = helper.retrieveOrgTariffaAnnualitaByTipoServizio(idTipoServizio);
        if (!tariffaList.isEmpty()) {
            try {
                for (Object[] tariffa : tariffaList) {
                    OrgTariffaAccordoRowBean row = new OrgTariffaAccordoRowBean();
                    Date dtScad = (Date) tariffa[9];
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String registro = (String) tariffa[6];
                    BigDecimal anno = (BigDecimal) tariffa[7];
                    String numero = (String) tariffa[8];
                    row.setString("accordo", (String) tariffa[0] + " - " + registro + " - " + anno + " - " + numero
                            + " - scadenza: " + df.format(dtScad));
                    row.setBigDecimal("aa_anno_accordo", (BigDecimal) tariffa[3]);
                    row.setBigDecimal("im_tariffa_aa_accordo", (BigDecimal) tariffa[4]);
                    row.setString("cd_tipo_servizio", (String) tariffa[5]);
                    tariffaTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero delle Entità rimborso costi annualit\u00E0  "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero delle Entità rimborso costi annualit\u00E0 ");
            }
        }
        return tariffaTableBean;
    }

    /**
     * Metodo di eliminazione di un tipo servizio
     *
     * @param param
     *            parametri per logging
     * @param idTipoServizio
     *            id tipo servizio
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgTipoServizio(LogParam param, BigDecimal idTipoServizio) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del tipo servizio");
        try {
            OrgTipoServizio tipoServizio = helper.findById(OrgTipoServizio.class, idTipoServizio);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_SERVIZIO,
                    new BigDecimal(tipoServizio.getIdTipoServizio()), param.getNomePagina());
            helper.removeEntity(tipoServizio, true);
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del tipo servizio ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di insert di un nuovo tipo servizio
     *
     * @param param
     *            parametri per logging
     * @param cdTipoServizio
     *            codice tipo servizio
     * @param dsTipoServizio
     *            descrizione tipo servizio
     * @param tiClasseTipoServizio
     *            tipo classe servizio
     * @param tipoFatturazione
     *            tipo fatturazione
     * @param flTariffaAccordo
     *            flag tariffa accordo 1/0 (true/false)
     * @param flTariffaAnnualita
     *            flag tariffa annualita 1/0 (true/false)
     *
     * @return id del nuovo ente
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveTipoServizio(LogParam param, String cdTipoServizio, String dsTipoServizio,
            String tiClasseTipoServizio, String tipoFatturazione, String flTariffaAccordo, String flTariffaAnnualita)
            throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio del tipo servizio");
        // Controllo esistenza denominazione
        if (helper.getOrgTipoServizio(cdTipoServizio) != null) {
            throw new ParerUserError("Codice del tipo servizio gi\u00E0  presente");
        }
        Long idTipoServizio = null;
        try {
            OrgTipoServizio tipoServizio = new OrgTipoServizio();
            tipoServizio.setCdTipoServizio(cdTipoServizio);
            tipoServizio.setDsTipoServizio(dsTipoServizio);
            tipoServizio.setTiClasseTipoServizio(tiClasseTipoServizio);
            tipoServizio.setFlTariffaAccordo(flTariffaAccordo);
            tipoServizio.setFlTariffaAnnualita(flTariffaAnnualita);
            tipoServizio.setTipoFatturazione(tipoFatturazione);
            helper.insertEntity(tipoServizio, true);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_SERVIZIO,
                    new BigDecimal(tipoServizio.getIdTipoServizio()), param.getNomePagina());
            LOGGER.debug("Salvataggio del tipo servizio completato");
            idTipoServizio = tipoServizio.getIdTipoServizio();
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del tipo servizio : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del tipo servizio ");
        }
        return idTipoServizio;
    }

    /**
     * Metodo di update di un tipo accordo
     *
     * @param param
     *            paramtri per logging
     * @param idTipoServizio
     *            id tipo servizio
     * @param cdTipoServizio
     *            codice tipo servizio
     * @param dsTipoServizio
     *            descrizione tipo servizio
     * @param tiClasseTipoServizio
     *            classe tipo servizio
     * @param tipoFatturazione
     *            tipo fatturazione
     * @param flTariffaAccordo
     *            flag tariffa accordo 1/0 (true/false)
     * @param flTariffaAnnualita
     *            flag tariffa annualita 1/0 (true/false)
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveTipoAccordo(LogParam param, BigDecimal idTipoServizio, String cdTipoServizio, String dsTipoServizio,
            String tiClasseTipoServizio, String tipoFatturazione, String flTariffaAccordo, String flTariffaAnnualita)
            throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio delle modifiche sul tipo servizio");
        // Controllo esistenza denominazione, se modificata
        OrgTipoServizio tipoServizioDaVerificare = helper.getOrgTipoServizio(cdTipoServizio);
        if (tipoServizioDaVerificare != null
                && tipoServizioDaVerificare.getIdTipoServizio() != idTipoServizio.longValue()) {
            throw new ParerUserError("Codice del tipo servizio gi\u00E0  presente");
        }

        OrgTipoServizio tipoServizio = helper.findById(OrgTipoServizio.class, idTipoServizio);

        // Check in caso di modifica (inversione) flag rimborso costi su accordo
        if (tipoServizio.getOrgTariffaAccordos() != null && tipoServizio.getOrgTariffaAccordos().size() > 0
                && flTariffaAccordo.equals("0")) {
            throw new ParerUserError(
                    "Impossibile modificare il rimborso costi su accordo in quanto già definiti in alcuni enti <br/>");
        }

        if (tipoServizio.getOrgTariffaAaAccordos() != null && tipoServizio.getOrgTariffaAaAccordos().size() > 0
                && flTariffaAnnualita.equals("0")) {
            throw new ParerUserError(
                    "Impossibile modificare il rimborso costi su annualità in quanto già definiti in alcuni enti <br/>");
        }

        try {
            tipoServizio.setCdTipoServizio(cdTipoServizio);
            tipoServizio.setDsTipoServizio(dsTipoServizio);
            tipoServizio.setTiClasseTipoServizio(tiClasseTipoServizio);
            tipoServizio.setFlTariffaAccordo(flTariffaAccordo);
            tipoServizio.setFlTariffaAnnualita(flTariffaAnnualita);
            tipoServizio.setTipoFatturazione(tipoFatturazione);
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_SERVIZIO,
                    new BigDecimal(tipoServizio.getIdTipoServizio()), param.getNomePagina());
            LOGGER.debug("Salvataggio del tipo servizio completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del tipo servizio : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del tipo servizio ");
        }
    }

    public OrgTipoServizioTableBean getOrgTipoServizioTableBean(String cdTipoServizio, String tiClasseTipoServizio,
            String tipoFatturazione) throws ParerUserError {
        OrgTipoServizioTableBean tipoServizioTableBean = new OrgTipoServizioTableBean();
        List<OrgTipoServizio> tipoServizioList = helper.retrieveOrgTipoServizioList(cdTipoServizio,
                tiClasseTipoServizio, tipoFatturazione);
        if (!tipoServizioList.isEmpty()) {
            try {
                tipoServizioTableBean = (OrgTipoServizioTableBean) Transform.entities2TableBean(tipoServizioList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero della lista dei tipi servizio "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della lista dei tipi servizio");
            }
        }
        return tipoServizioTableBean;
    }

    public OrgTariffaRowBean getOrgTariffaRowBean(BigDecimal idTariffa) throws ParerUserError {
        OrgTariffaRowBean tariffaRowBean = new OrgTariffaRowBean();
        OrgTariffa tariffa = helper.findById(OrgTariffa.class, idTariffa);
        if (tariffa != null) {
            try {
                tariffaRowBean = (OrgTariffaRowBean) Transform.entity2RowBean(tariffa);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero del tariffario " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero del tariffario ");
            }
        }
        return tariffaRowBean;
    }

    /**
     * Ritorna il tableBean contenente gli scaglioni della tariffa in input
     *
     * @param idTariffa
     *            id tariffa
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgScaglioneTariffaTableBean getOrgScaglioneTariffaTableBean(BigDecimal idTariffa) throws ParerUserError {
        OrgScaglioneTariffaTableBean scaglioneTariffaTableBean = new OrgScaglioneTariffaTableBean();
        List<OrgScaglioneTariffa> scaglioneTariffaList = helper.retrieveOrgScaglioneTariffa(idTariffa);
        if (!scaglioneTariffaList.isEmpty()) {
            try {
                scaglioneTariffaTableBean = (OrgScaglioneTariffaTableBean) Transform
                        .entities2TableBean(scaglioneTariffaList);
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero degli scaglioni " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero degli scaglioni");
            }
        }
        return scaglioneTariffaTableBean;
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

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long insertUtenteArchivistaToEnteSiam(LogParam param, BigDecimal idEnteSiam, BigDecimal idUserIam,
            String flReferente, String dlNote) throws ParerUserError {
        UsrUser user = helper.findByIdWithLock(UsrUser.class, idUserIam);

        // Controlli
        if (helper.existsUtenteArchivistaPerEnteSiam(idEnteSiam, idUserIam, null, null)) {
            throw new ParerUserError("Attenzione: utente archivista gi\u00E0  presente");
        }

        if (user.getFlAttivo().equals("0")) {
            throw new ParerUserError("Utente non esistente o non appartenente al dominio per lo username '"
                    + user.getNmUserid() + "' specificato");
        }

        if (flReferente.equals("1") && helper.existsUtenteArchivistaPerEnteSiam(idEnteSiam, null, flReferente, null)) {
            throw new ParerUserError(
                    "Sull'ente \u00E8 già presente un archivista Referente, non \u00E8 possibile indicare un altro referente");
        }

        OrgEnteSiam enteSiam = helper.findByIdWithLock(OrgEnteSiam.class, idEnteSiam);
        OrgEnteArkRif enteArkRif = new OrgEnteArkRif();
        enteArkRif.setOrgEnteSiam(enteSiam);
        enteArkRif.setUsrUser(user);
        enteArkRif.setFlReferente(flReferente);
        enteArkRif.setDlNote(dlNote);
        helper.insertEntity(enteArkRif, true);

        String nmTipoOggetto = SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO;
        if (enteSiam.getTiEnte().equals(ConstOrgEnteSiam.TiEnteSiam.NON_CONVENZIONATO)) {
            nmTipoOggetto = (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.equals(enteSiam.getTiEnteNonConvenz()))
                    ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                    : (ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.equals(enteSiam.getTiEnteNonConvenz()))
                            ? SacerLogConstants.TIPO_OGGETTO_ORGANO_VIGILANZA
                            : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE;
        }

        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), nmTipoOggetto, idEnteSiam, param.getNomePagina());

        return enteArkRif.getIdEnteArkRif();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateUtenteArchivistaToEnteSiam(LogParam param, BigDecimal idEnteArkRif, BigDecimal idEnteSiam,
            String flReferente, String dlNote) throws ParerUserError {
        OrgEnteSiam enteSiam = helper.findByIdWithLock(OrgEnteSiam.class, idEnteSiam);
        OrgEnteArkRif enteArkRif = helper.findById(OrgEnteArkRif.class, idEnteArkRif);

        if (flReferente.equals("1") && helper.existsUtenteArchivistaPerEnteSiam(idEnteSiam, null, flReferente,
                BigDecimal.valueOf(enteArkRif.getUsrUser().getIdUserIam()))) {
            throw new ParerUserError(
                    "Sull'ente \u00E8 giÃƒÆ’Ã‚Â  presente un archivista Referente, non \u00E8 possibile indicare un altro referente");
        }

        enteArkRif.setFlReferente(flReferente);
        enteArkRif.setDlNote(dlNote);
        helper.mergeEntity(enteArkRif);
        helper.getEntityManager().flush();

        String nmTipoOggetto = SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO;
        if (enteSiam.getTiEnte().equals(ConstOrgEnteSiam.TiEnteSiam.NON_CONVENZIONATO)) {
            nmTipoOggetto = (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.equals(enteSiam.getTiEnteNonConvenz()))
                    ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                    : (ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.equals(enteSiam.getTiEnteNonConvenz()))
                            ? SacerLogConstants.TIPO_OGGETTO_ORGANO_VIGILANZA
                            : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE;
        }

        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), nmTipoOggetto, idEnteSiam, param.getNomePagina());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long insertReferenteEnteToEnteSiam(LogParam param, BigDecimal idEnteSiam, BigDecimal idUserIam,
            String qualificaUser, String dlNote) throws ParerUserError {
        UsrUser user = helper.findByIdWithLock(UsrUser.class, idUserIam);

        // Controlli
        if (helper.existsReferenteEntePerEnteSiam(idEnteSiam, idUserIam, qualificaUser)) {
            throw new ParerUserError("Attenzione: referente con la stessa qualifica già presente");
        }
        if (qualificaUser.equals("Responsabile della conservazione")) {
            if (helper.existsResponsabileConservazione(idEnteSiam, idUserIam)) {
                throw new ParerUserError("Attenzione: esiste già un altro utente responsabile della conservazione");
            }
        }
        if (helper.isAutoma(idUserIam)) {
            throw new ParerUserError(
                    "Attenzione: l'utente selezionato è di tipo AUTOMA, deve essere selezionato un utente PERSONA_FISICA o NON_DI_SISTEMA");
        }

        OrgEnteSiam enteSiam = helper.findByIdWithLock(OrgEnteSiam.class, idEnteSiam);
        OrgEnteUserRif enteUserRif = new OrgEnteUserRif();
        enteUserRif.setOrgEnteSiam(enteSiam);
        enteUserRif.setUsrUser(user);
        enteUserRif.setQualificaUser(qualificaUser);
        enteUserRif.setDlNote(dlNote);
        helper.insertEntity(enteUserRif, true);

        String nmTipoOggetto = SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO;
        if (enteSiam.getTiEnte().equals(ConstOrgEnteSiam.TiEnteSiam.NON_CONVENZIONATO)) {
            nmTipoOggetto = (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.equals(enteSiam.getTiEnteNonConvenz()))
                    ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                    : (ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.equals(enteSiam.getTiEnteNonConvenz()))
                            ? SacerLogConstants.TIPO_OGGETTO_ORGANO_VIGILANZA
                            : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE;
        }

        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), nmTipoOggetto, idEnteSiam, param.getNomePagina());

        return enteUserRif.getIdEnteUserRif();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateReferenteEnteToEnteSiam(LogParam param, BigDecimal idEnteSiam, BigDecimal idEnteUserRif,
            String dlNote) throws ParerUserError {
        OrgEnteSiam enteSiam = helper.findByIdWithLock(OrgEnteSiam.class, idEnteSiam);
        OrgEnteUserRif enteUserRif = helper.findById(OrgEnteUserRif.class, idEnteUserRif);
        enteUserRif.setDlNote(dlNote);
        helper.mergeEntity(enteUserRif);
        helper.getEntityManager().flush();

        String nmTipoOggetto = SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO;
        if (enteSiam.getTiEnte().equals(ConstOrgEnteSiam.TiEnteSiam.NON_CONVENZIONATO)) {
            nmTipoOggetto = (ConstOrgEnteSiam.TiEnteNonConvenz.FORNITORE_ESTERNO.equals(enteSiam.getTiEnteNonConvenz()))
                    ? SacerLogConstants.TIPO_OGGETTO_FORNITORE_ESTERNO
                    : (ConstOrgEnteSiam.TiEnteNonConvenz.ORGANO_VIGILANZA.equals(enteSiam.getTiEnteNonConvenz()))
                            ? SacerLogConstants.TIPO_OGGETTO_ORGANO_VIGILANZA
                            : SacerLogConstants.TIPO_OGGETTO_SOGGETTO_ATTUATORE;
        }

        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), nmTipoOggetto, idEnteSiam, param.getNomePagina());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long insertCollegamentoEnteToEnteConvenzionato(LogParam param, BigDecimal idEnteConvenz,
            BigDecimal idEnteConvenzCapofila, String nmColleg, String dsColleg, String tiColleg, Date dtIniVal,
            Date dtFinVal) throws ParerUserError {

        // Controllo esistenza denominazione
        if (helper.getOrgCollegEntiConvenz(nmColleg) != null) {
            throw new ParerUserError("Attenzione: collegamento ente gi\u00E0  presente");
        }

        OrgEnteSiam enteConvenz = helper.findByIdWithLock(OrgEnteSiam.class, idEnteConvenz);
        OrgCollegEntiConvenz collegEntiConvenz = new OrgCollegEntiConvenz();
        if (idEnteConvenzCapofila != null) {
            OrgEnteSiam enteConvenzCapofila = helper.findByIdWithLock(OrgEnteSiam.class, idEnteConvenzCapofila);
            collegEntiConvenz.setOrgEnteSiam(enteConvenzCapofila);
        }
        collegEntiConvenz.setNmColleg(nmColleg);
        collegEntiConvenz.setDsColleg(dsColleg);
        collegEntiConvenz.setTiColleg(TiColleg.valueOf(tiColleg));
        collegEntiConvenz.setDtIniVal(dtIniVal);
        collegEntiConvenz.setDtFinVal(dtFinVal);

        helper.insertEntity(collegEntiConvenz, true);

        enteConvenz.getOrgCollegEntiConvenzs().add(collegEntiConvenz);

        // Inserisco il collegamento nella tabelli degli enti associati
        OrgAppartCollegEnti appartCollegEnte = new OrgAppartCollegEnti();
        appartCollegEnte.setOrgEnteSiam(enteConvenz);
        appartCollegEnte.setOrgCollegEntiConvenz(collegEntiConvenz);
        appartCollegEnte.setDtIniVal(new Date());

        Calendar c = Calendar.getInstance();
        c.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
        appartCollegEnte.setDtFinVal(c.getTime());

        enteConvenz.getOrgAppartCollegEntis().add(appartCollegEnte);

        helper.insertEntity(appartCollegEnte, true);

        collegEntiConvenz.getOrgAppartCollegEntis().add(appartCollegEnte);

        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                idEnteConvenz, param.getNomePagina());

        return collegEntiConvenz.getIdCollegEntiConvenz();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateCollegamentoEnteToEnteConvenzionato(LogParam param, BigDecimal idEnteConvenz,
            BigDecimal idCollegEntiConvenz, BigDecimal idEnteConvenzCapofila, String nmColleg, String dsColleg,
            String tiColleg, Date dtIniVal, Date dtFinVal) throws ParerUserError {
        OrgCollegEntiConvenz collegEntiConvenz = helper.findById(OrgCollegEntiConvenz.class, idCollegEntiConvenz);
        // Controllo che il nome, se modificato, non sia giÃƒÆ’Ã‚Â  presente
        if (!collegEntiConvenz.getNmColleg().equals(nmColleg) && helper.getOrgCollegEntiConvenz(nmColleg) != null) {
            throw new ParerUserError(
                    "Attenzione: nel sistema \u00E8 giÃƒÆ’Ã‚Â  censito un altro collegamento con nome " + nmColleg);
        }

        try {
            if (idEnteConvenzCapofila != null) {
                OrgEnteSiam enteConvenzCapofila = helper.findByIdWithLock(OrgEnteSiam.class, idEnteConvenzCapofila);
                collegEntiConvenz.setOrgEnteSiam(enteConvenzCapofila);
            } else {
                collegEntiConvenz.setOrgEnteSiam(null);
            }
            collegEntiConvenz.setNmColleg(nmColleg);
            collegEntiConvenz.setDsColleg(dsColleg);
            collegEntiConvenz.setTiColleg(TiColleg.valueOf(tiColleg));
            collegEntiConvenz.setDtIniVal(dtIniVal);
            collegEntiConvenz.setDtFinVal(dtFinVal);

            helper.mergeEntity(collegEntiConvenz);
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    idEnteConvenz, param.getNomePagina());
            LOGGER.debug("Salvataggio del collegamento ente convenzionato completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del collegamento ente convenzionato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del collegamento ente convenzionato");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long insertNuovoCollegamentoEnte(LogParam param, BigDecimal idEnteConvenzCapofila, String nmColleg,
            String dsColleg, String tiColleg, Date dtIniVal, Date dtFinVal) throws ParerUserError {

        // Controllo esistenza denominazione
        if (helper.getOrgCollegEntiConvenz(nmColleg) != null) {
            throw new ParerUserError("Attenzione: collegamento ente gi\u00E0  presente");
        }

        OrgCollegEntiConvenz collegEntiConvenz = new OrgCollegEntiConvenz();
        OrgEnteSiam enteConvenzCapofila = null;
        if (idEnteConvenzCapofila != null) {
            enteConvenzCapofila = helper.findByIdWithLock(OrgEnteSiam.class, idEnteConvenzCapofila);
            collegEntiConvenz.setOrgEnteSiam(enteConvenzCapofila);
        }
        collegEntiConvenz.setNmColleg(nmColleg);
        collegEntiConvenz.setDsColleg(dsColleg);
        collegEntiConvenz.setTiColleg(TiColleg.valueOf(tiColleg));
        collegEntiConvenz.setDtIniVal(dtIniVal);
        collegEntiConvenz.setDtFinVal(dtFinVal);

        helper.insertEntity(collegEntiConvenz, true);

        if (idEnteConvenzCapofila != null) {
            enteConvenzCapofila.getOrgCollegEntiConvenzs().add(collegEntiConvenz);

            // Inserisco il collegamento nella tabelli degli enti associati
            OrgAppartCollegEnti appartCollegEnte = new OrgAppartCollegEnti();
            appartCollegEnte.setOrgEnteSiam(enteConvenzCapofila);
            appartCollegEnte.setOrgCollegEntiConvenz(collegEntiConvenz);
            appartCollegEnte.setDtIniVal(dtIniVal);
            appartCollegEnte.setDtFinVal(dtFinVal);

            enteConvenzCapofila.getOrgAppartCollegEntis().add(appartCollegEnte);

            helper.insertEntity(appartCollegEnte, true);

            collegEntiConvenz.getOrgAppartCollegEntis().add(appartCollegEnte);
        }

        return collegEntiConvenz.getIdCollegEntiConvenz();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateCollegamento(LogParam param, BigDecimal idCollegEntiConvenz, BigDecimal idEnteConvenzCapofila,
            String nmColleg, String dsColleg, String tiColleg, Date dtIniVal, Date dtFinVal,
            Set<BigDecimal> appartCollegEntiSet) throws ParerUserError {
        OrgCollegEntiConvenz collegEntiConvenz = helper.findById(OrgCollegEntiConvenz.class, idCollegEntiConvenz);
        // Controllo che il nome, se modificato, non sia giÃƒÆ’Ã‚Â  presente
        if (!collegEntiConvenz.getNmColleg().equals(nmColleg) && helper.getOrgCollegEntiConvenz(nmColleg) != null) {
            throw new ParerUserError(
                    "Attenzione: nel sistema \u00E8 gi\u00E0 censito un altro collegamento con nome " + nmColleg);
        }

        try {
            if (idEnteConvenzCapofila != null) {
                OrgEnteSiam enteConvenzCapofila = helper.findByIdWithLock(OrgEnteSiam.class, idEnteConvenzCapofila);
                collegEntiConvenz.setOrgEnteSiam(enteConvenzCapofila);
            } else {
                collegEntiConvenz.setOrgEnteSiam(null);
            }
            collegEntiConvenz.setNmColleg(nmColleg);
            collegEntiConvenz.setDsColleg(dsColleg);
            collegEntiConvenz.setTiColleg(TiColleg.valueOf(tiColleg));
            collegEntiConvenz.setDtIniVal(dtIniVal);
            collegEntiConvenz.setDtFinVal(dtFinVal);

            helper.mergeEntity(collegEntiConvenz);
            helper.getEntityManager().flush();

            if (!appartCollegEntiSet.isEmpty()) {
                sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                        param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                        appartCollegEntiSet, param.getNomePagina());
            }

            LOGGER.debug("Salvataggio del collegamento ente convenzionato completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del collegamento ente convenzionato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del collegamento ente convenzionato");
        }
    }

    /**
     * Metodo di eliminazione di un utente archivista
     *
     * @param param
     *            parametri per logging
     * @param idEnteArkRif
     *            id ente archiviazione
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgEnteArkRif(LogParam param, BigDecimal idEnteArkRif) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'utente archivista dall'ente convenzionato");
        try {
            OrgEnteArkRif enteArkRif = helper.findById(OrgEnteArkRif.class, idEnteArkRif);
            helper.removeEntity(enteArkRif, true);
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(enteArkRif.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'utente archivista dall'ente convenzionato ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    public boolean isInRegione(BigDecimal idAmbitoTerrit, BigDecimal idAmbienteEnteConvenz, BigDecimal idEnteConvenz) {
        String regioneDefault = paramHelper.getValoreParamApplic(
                ConstIamParamApplic.NmParamApplic.REGIONE_DEFAULT.name(), idAmbienteEnteConvenz, idEnteConvenz,
                Constants.TipoIamVGetValAppart.ENTECONVENZ);
        return regioneDefault.equals(idAmbitoTerrit.toString());
    }

    /**
     * Metodo di insert di una nuova tariffa
     *
     * @param param
     *            parametri per logging
     * @param idTariffario
     *            id tariffario
     * @param tariffaRowBean
     *            row bean {@link OrgTariffaRowBean}
     * @param scaglioneTableBean
     *            table bean {@link OrgScaglioneTariffaTableBean}
     *
     * @return id della nuova tariffa
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveTariffa(LogParam param, BigDecimal idTariffario, OrgTariffaRowBean tariffaRowBean,
            OrgScaglioneTariffaTableBean scaglioneTableBean) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio della tariffa");
        // Controllo esistenza nome tariffa per lo stesso tariffario...
        if (helper.getOrgTariffa(idTariffario, tariffaRowBean.getNmTariffa()) != null) {
            throw new ParerUserError("Nome tariffa gi\u00E0  presente all'interno di questo tariffario");
        }
        // ...e per tipo servizio e classe ente convenzionato
        if (helper.getOrgTariffa(idTariffario, tariffaRowBean.getIdTipoServizio(),
                tariffaRowBean.getIdClasseEnteConvenz(), null) != null) {
            throw new ParerUserError(
                    "Tariffa con tipo servizio e classe ente convenzionato gi\u00E0  presente all'interno di questo tariffario");
        }

        Long idTariffa = null;
        try {
            OrgTariffa tariffa = new OrgTariffa();
            tariffa.setOrgTipoServizio(helper.findById(OrgTipoServizio.class, tariffaRowBean.getIdTipoServizio()));
            if (tariffaRowBean.getIdClasseEnteConvenz() != null) {
                tariffa.setOrgClasseEnteConvenz(
                        helper.findById(OrgClasseEnteConvenz.class, tariffaRowBean.getIdClasseEnteConvenz()));
            }
            tariffa.setNmTariffa(tariffaRowBean.getNmTariffa());
            tariffa.setDsTariffa(tariffaRowBean.getDsTariffa());
            tariffa.setTipoTariffa(tariffaRowBean.getTipoTariffa());
            tariffa.setImValoreFissoTariffa(tariffaRowBean.getImValoreFissoTariffa());
            tariffa.setNiQuantitaUnit(tariffaRowBean.getNiQuantitaUnit());
            OrgTariffario tariffario = helper.findById(OrgTariffario.class, idTariffario);
            tariffa.setOrgTariffario(tariffario);
            if (tariffario.getOrgTariffas() == null) {
                tariffario.setOrgTariffas(new ArrayList<>());
            }
            tariffario.getOrgTariffas().add(tariffa);

            ///////////////////////////
            // Imposto gli scaglioni //
            ///////////////////////////
            insertScaglioni(tariffa, scaglioneTableBean);
            helper.insertEntity(tariffa, true);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_ACCORDO,
                    new BigDecimal(tariffa.getOrgTariffario().getOrgTipoAccordo().getIdTipoAccordo()),
                    param.getNomePagina());
            LOGGER.debug("Salvataggio della tariffa completata");
            idTariffa = tariffa.getIdTariffa();
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio della tariffa : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio della tariffa");
        }
        return idTariffa;
    }

    /**
     * Metodo di update di una tariffa
     *
     * @param param
     *            parametri per logging
     * @param idTariffario
     *            id tariffario
     * @param tariffaNewRowBean
     *            row bean {@link OrgTariffaRowBean}
     * @param scaglioneTableBean
     *            table bean {@link OrgScaglioneTariffaTableBean}
     * @param scaglioniDaEliminare
     *            lista distinta id
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveTariffa(LogParam param, BigDecimal idTariffario, OrgTariffaRowBean tariffaNewRowBean,
            OrgScaglioneTariffaTableBean scaglioneTableBean, Set<BigDecimal> scaglioniDaEliminare)
            throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio delle modifiche sulla tariffa");
        /*
         * Ricavo da DB la tariffa prima delle modifiche (anche se prendo idTariffa da tariffaNewRowBean, in questa fase
         * preliminare ricaverÃƒÆ’Ã‚Â² i vecchi dati persistiti
         */
        OrgTariffa tariffaOld = helper.findById(OrgTariffa.class, tariffaNewRowBean.getIdTariffa());
        // Controllo esistenza nome tariffa per lo stesso tariffario...
        if (helper.getOrgTariffa(idTariffario, tariffaNewRowBean.getNmTariffa()) != null
                && !tariffaNewRowBean.getNmTariffa().equals(tariffaOld.getNmTariffa())) {
            throw new ParerUserError("Nome tariffa gi\u00E0  presente all'interno di questo tariffario");
        }
        // ...e per tipo servizio e classe ente convenzionato
        if (helper.getOrgTariffa(idTariffario, tariffaNewRowBean.getIdTipoServizio(),
                tariffaNewRowBean.getIdClasseEnteConvenz(), tariffaNewRowBean.getIdTariffa()) != null) {
            throw new ParerUserError(
                    "Tariffa con tipo servizio e classe ente convenzionato gi\u00E0  presente all'interno di questo tariffario");
        }

        try {
            OrgTariffa tariffa = helper.findById(OrgTariffa.class, tariffaNewRowBean.getIdTariffa());
            tariffa.setOrgTipoServizio(helper.findById(OrgTipoServizio.class, tariffaNewRowBean.getIdTipoServizio()));
            if (tariffaNewRowBean.getIdClasseEnteConvenz() != null) {
                tariffa.setOrgClasseEnteConvenz(
                        helper.findById(OrgClasseEnteConvenz.class, tariffaNewRowBean.getIdClasseEnteConvenz()));
            } else {
                tariffa.setOrgClasseEnteConvenz(null);
            }
            tariffa.setNmTariffa(tariffaNewRowBean.getNmTariffa());
            tariffa.setDsTariffa(tariffaNewRowBean.getDsTariffa());
            tariffa.setTipoTariffa(tariffaNewRowBean.getTipoTariffa());
            tariffa.setImValoreFissoTariffa(tariffaNewRowBean.getImValoreFissoTariffa());
            tariffa.setNiQuantitaUnit(tariffaNewRowBean.getNiQuantitaUnit());

            manageScaglioni(tariffa, scaglioneTableBean, scaglioniDaEliminare);

            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_ACCORDO,
                    new BigDecimal(tariffa.getOrgTariffario().getOrgTipoAccordo().getIdTipoAccordo()),
                    param.getNomePagina());
            LOGGER.debug("Salvataggio della tariffa completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio della tariffa : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio della tariffa");
        }
    }

    private void insertScaglioni(OrgTariffa tariffa, OrgScaglioneTariffaTableBean scaglioneTariffaTableBean)
            throws ParerUserError {
        for (OrgScaglioneTariffaRowBean scaglioneTariffaRowBean : scaglioneTariffaTableBean) {
            OrgScaglioneTariffa scaglioneTariffa = new OrgScaglioneTariffa();
            scaglioneTariffa.setOrgTariffa(tariffa);
            scaglioneTariffa.setNiIniScaglione(scaglioneTariffaRowBean.getNiIniScaglione());
            scaglioneTariffa.setNiFineScaglione(scaglioneTariffaRowBean.getNiFineScaglione());
            scaglioneTariffa.setImScaglione(scaglioneTariffaRowBean.getImScaglione());
            if (tariffa.getOrgScaglioneTariffas() == null) {
                tariffa.setOrgScaglioneTariffas(new ArrayList<>());
            }
            tariffa.getOrgScaglioneTariffas().add(scaglioneTariffa);
        }
    }

    private void manageScaglioni(OrgTariffa tariffa, OrgScaglioneTariffaTableBean scaglioneTariffaTableBean,
            Set<BigDecimal> scaglioneTariffaDaEliminareList) throws ParerUserError {
        LOGGER.info(EntiConvenzionatiEjb.class.getName() + " Elimino gli scaglioni da eliminare");
        /* Elimino gli scaglioni da eliminare (cancellando il record in OrgScaglioneTariffa) */
        if (!scaglioneTariffaDaEliminareList.isEmpty()) {
            Iterator<BigDecimal> it = scaglioneTariffaDaEliminareList.iterator();
            while (it.hasNext()) {
                BigDecimal idScaglioneTariffaDaEliminare = it.next();
                OrgScaglioneTariffa scaglione = helper.findById(OrgScaglioneTariffa.class,
                        idScaglioneTariffaDaEliminare);
                helper.removeEntity(scaglione, true);
            }
        }

        /* Inserisco/modifico gli altri scaglioni */
        for (OrgScaglioneTariffaRowBean scaglioneTariffaRowBean : scaglioneTariffaTableBean) {
            OrgScaglioneTariffa scaglioneTariffa = new OrgScaglioneTariffa();
            // Se lo scaglione \u00E8 giÃƒÆ’Ã‚Â  presente, lo recupero
            if (scaglioneTariffaRowBean.getIdScaglioneTariffa() != null) {
                scaglioneTariffa = helper.findById(OrgScaglioneTariffa.class,
                        scaglioneTariffaRowBean.getIdScaglioneTariffa());
            }
            // Modifico i campi
            scaglioneTariffa.setNiIniScaglione(scaglioneTariffaRowBean.getNiIniScaglione());
            scaglioneTariffa.setNiFineScaglione(scaglioneTariffaRowBean.getNiFineScaglione());
            scaglioneTariffa.setImScaglione(scaglioneTariffaRowBean.getImScaglione());
            scaglioneTariffa.setOrgTariffa(tariffa);
            if (tariffa.getOrgScaglioneTariffas() == null) {
                tariffa.setOrgScaglioneTariffas(new ArrayList<>());
            }
            tariffa.getOrgScaglioneTariffas().add(scaglioneTariffa);
        }
    }

    /**
     * Metodo di eliminazione di una tariffa
     *
     * @param param
     *            parametri per logging
     * @param idTariffa
     *            id tariffa
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgTariffa(LogParam param, BigDecimal idTariffa) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del tariffa");
        try {
            OrgTariffa tariffa = helper.findById(OrgTariffa.class, idTariffa);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_TIPO_ACCORDO,
                    new BigDecimal(tariffa.getOrgTariffario().getOrgTipoAccordo().getIdTipoAccordo()),
                    param.getNomePagina());
            helper.removeEntity(tariffa, true);
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione della tariffa ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Ritorna il tableBean contenente i codici iva
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgCdIvaTableBean getOrgCdIvaTableBean() throws ParerUserError {
        OrgCdIvaTableBean cdIvaTableBean = new OrgCdIvaTableBean();
        List<OrgCdIva> cdIvaList = helper.retrieveOrgCdIvaList();
        if (!cdIvaList.isEmpty()) {
            try {
                cdIvaTableBean = (OrgCdIvaTableBean) Transform.entities2TableBean(cdIvaList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dei codici IVA " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero dei codici IVA");
            }
        }
        return cdIvaTableBean;
    }

    /**
     * Ritorna il tableBean contenente le fatture in base ai parametri di ricerca
     *
     * @param nmEnteConvenz
     *            nome ente convenzionato
     * @param cdClienteSap
     *            code client sap
     * @param idTipoAccordo
     *            id tipo accordo
     * @param aaFatturaDa
     *            anno fattura da
     * @param idTipoServizio
     *            id tipo servizio
     * @param aaFatturaA
     *            anno fattura a
     * @param pgFatturaEnteDa
     *            progressivo ente da
     * @param pgFatturaEnteA
     *            progressivo ente a
     * @param cdFattura
     *            codice fattura
     * @param dtEmisFatturaDa
     *            data emissione fattura da
     * @param dtEmisFatturaA
     *            data emissione fattura a
     * @param aaEmissFattura
     *            anno emissione fattura
     * @param pgFatturaEmis
     *            progressivo emissione
     * @param cdRegistroEmisFattura
     *            codice registro emissione fattura
     * @param cdEmisFattura
     *            codice emissione fattura
     * @param tiStatoFatturaEnte
     *            tipo stato fattura ente
     *
     * @return il tableBean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgVRicFattureTableBean getOrgVRicFattureTableBean(String nmEnteConvenz, String cdClienteSap,
            BigDecimal idTipoAccordo, BigDecimal idTipoServizio, BigDecimal aaFatturaDa, BigDecimal aaFatturaA,
            BigDecimal pgFatturaEnteDa, BigDecimal pgFatturaEnteA, String cdFattura, String cdRegistroEmisFattura,
            BigDecimal aaEmissFattura, String cdEmisFattura, BigDecimal pgFatturaEmis, Date dtEmisFatturaDa,
            Date dtEmisFatturaA, Set<String> tiStatoFatturaEnte) throws ParerUserError {
        OrgVRicFattureTableBean ricFattureTableBean = new OrgVRicFattureTableBean();

        List<OrgVRicFatture> ricFattureList = helper.retrieveOrgVRicFattureList2(nmEnteConvenz, idTipoAccordo,
                idTipoServizio, aaFatturaDa, aaFatturaA, pgFatturaEnteDa, pgFatturaEnteA, cdFattura,
                cdRegistroEmisFattura, aaEmissFattura, cdEmisFattura, pgFatturaEmis, dtEmisFatturaDa, dtEmisFatturaA,
                tiStatoFatturaEnte);
        if (!ricFattureList.isEmpty()) {
            try {
                for (OrgVRicFatture fattura : ricFattureList) {
                    OrgVRicFattureRowBean ricFatturaRow = (OrgVRicFattureRowBean) Transform.entity2RowBean(fattura);
                    ricFatturaRow.setString("numero_fattura_temp",
                            fattura.getAaFattura() + "/" + fattura.getPgFattura());
                    ricFattureTableBean.add(ricFatturaRow);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero delle fatture " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero delle fatture");
            }
        }
        return ricFattureTableBean;
    }

    public OrgVRicFatturePerAccordoTableBean getOrgVRicFattureTableBean(BigDecimal idAccordoEnte)
            throws ParerUserError {
        OrgVRicFatturePerAccordoTableBean ricFattureTableBean = new OrgVRicFatturePerAccordoTableBean();

        List<OrgVRicFatturePerAccordo> ricFattureList = helper.retrieveOrgVRicFatturePerAccordo(idAccordoEnte);
        if (!ricFattureList.isEmpty()) {
            try {
                for (OrgVRicFatturePerAccordo fattura : ricFattureList) {
                    OrgVRicFatturePerAccordoRowBean ricFatturaRow = (OrgVRicFatturePerAccordoRowBean) Transform
                            .entity2RowBean(fattura);
                    ricFatturaRow.setString("numero_fattura_temp",
                            fattura.getAaFattura() + "/" + fattura.getPgFattura());
                    ricFattureTableBean.add(ricFatturaRow);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero delle fatture " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero delle fatture");
            }
        }
        return ricFattureTableBean;
    }

    public OrgVVisFatturaRowBean getOrgVVisFatturaRowBean(BigDecimal idFatturaEnte) throws ParerUserError {
        OrgVVisFatturaRowBean fatturaEnteRowBean = new OrgVVisFatturaRowBean();
        OrgVVisFattura fatturaEnte = helper.getPrimoOrgVVisFatturaEnte(idFatturaEnte);
        if (fatturaEnte != null) {
            try {
                fatturaEnteRowBean = (OrgVVisFatturaRowBean) Transform.entity2RowBean(fatturaEnte);
                fatturaEnteRowBean.setString("codice_ente",
                        fatturaEnte.getCdEnteConvenz() + " (" + fatturaEnte.getTiCdEnteConvenz() + ")");
                String nmAmbiente = fatturaEnteRowBean.getNmAmbiente();
                String nmEnte = fatturaEnteRowBean.getNmEnte();
                String nmStrut = fatturaEnteRowBean.getNmStrut();
                if (fatturaEnte.getCdRegistroEmisFattura() != null) {
                    fatturaEnteRowBean.setString("chiave_fattura", nmAmbiente + " - " + nmEnte + " - " + nmStrut);
                }
                if (fatturaEnte.getCdRegistroEmisNotaCredito() != null) {
                    fatturaEnteRowBean.setString("chiave_nota_storno", nmAmbiente + " - " + nmEnte + " - " + nmStrut);
                }

                // Setto il campo relativo alle modifiche intervenute
                fatturaEnteRowBean.setString("modifiche_intervenute",
                        helper.checkModificheIntervenuteFattura(idFatturaEnte) ? "1" : "0");

            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero della fattura " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero della fattura ");
            }
        }
        return fatturaEnteRowBean;
    }

    public BaseTable getServiziFatturati(BigDecimal idEnteFattura) throws ParerUserError {
        List<ServizioFatturatoBean> servizioFatturatoBeanList = helper.getServiziFatturati(idEnteFattura);
        BaseTable serviziFatturatiBaseTable = new BaseTable();
        try {
            for (ServizioFatturatoBean servizioFatturatoBean : servizioFatturatoBeanList) {
                BaseRow servizioFatturatoBaseRow = new BaseRow();
                servizioFatturatoBaseRow.setBigDecimal("id_fattura_ente", servizioFatturatoBean.getId_fattura_ente());
                servizioFatturatoBaseRow.setBigDecimal("id_servizio_erogato",
                        servizioFatturatoBean.getId_servizio_erogato());
                servizioFatturatoBaseRow.setString("nm_servizio_erogato",
                        servizioFatturatoBean.getNm_servizio_erogato());
                servizioFatturatoBaseRow.setBigDecimal("aa_servizio_fattura",
                        servizioFatturatoBean.getAa_servizio_fattura());
                servizioFatturatoBaseRow.setString("tipo_fatturazione", servizioFatturatoBean.getTipo_fatturazione());
                servizioFatturatoBaseRow.setString("nm_tariffa", servizioFatturatoBean.getNm_tariffa());
                servizioFatturatoBaseRow.setBigDecimal("im_valore_tariffa",
                        servizioFatturatoBean.getIm_valore_tariffa());
                if (servizioFatturatoBean.getTipo_tariffa() != null) {
                    if (servizioFatturatoBean.getTipo_tariffa()
                            .equals(ConstOrgTariffa.TipoTariffa.VALORE_SCAGLIONI_STORAGE.name())
                            || servizioFatturatoBean.getTipo_tariffa()
                                    .equals(ConstOrgTariffa.TipoTariffa.VALORE_UNITARIO_SCAGLIONI_STORAGE.name())) {
                        servizioFatturatoBaseRow.setBigDecimal("storage_occupato",
                                servizioFatturatoBean.getQt_scaglione_servizio_fattura());
                    } else if (servizioFatturatoBean.getTipo_tariffa()
                            .equals(ConstOrgTariffa.TipoTariffa.VALORE_SCAGLIONI_UD_VERSATE.name())) {
                        servizioFatturatoBaseRow.setBigDecimal("num_ud_versate",
                                servizioFatturatoBean.getQt_scaglione_servizio_fattura());
                    }
                }
                servizioFatturatoBaseRow.setBigDecimal("im_servizio_fattura",
                        servizioFatturatoBean.getIm_servizio_fattura());
                serviziFatturatiBaseTable.add(servizioFatturatoBaseRow);
            }
        } catch (Exception e) {
            LOGGER.error("Errore durante il recupero dei servizi fatturati " + ExceptionUtils.getRootCauseMessage(e),
                    e);
            throw new ParerUserError("Errore durante il recupero dei servizi fatturati ");
        }
        return serviziFatturatiBaseTable;
    }

    public OrgVLisServFatturaTableBean getOrgVLisServFatturaTableBeanByIdFatturaEnte(BigDecimal idFatturaEnte)
            throws ParerUserError {
        List<Object[]> serviziFatturatiList = helper.retrieveOrgVLisServFatturaListByIdFattura(idFatturaEnte);
        OrgVLisServFatturaTableBean serviziFatturatiTableBean = new OrgVLisServFatturaTableBean();
        try {
            for (Object[] servizioFatturatoObj : serviziFatturatiList) {
                OrgVLisServFattura servizioFatturato = (OrgVLisServFattura) servizioFatturatoObj[0];
                OrgVLisServFatturaRowBean servizioFatturatoRowBean = (OrgVLisServFatturaRowBean) Transform
                        .entity2RowBean(servizioFatturato);
                servizioFatturatoRowBean.setString("fl_superamento_scaglione",
                        servizioFatturato.getFlSuperamentoScaglione() != null
                                ? servizioFatturato.getFlSuperamentoScaglione() : "2");
                servizioFatturatoRowBean.setBigDecimal("id_servizio_erogato",
                        new BigDecimal((Long) servizioFatturatoObj[1]));
                servizioFatturatoRowBean.setString("nm_servizio_erogato", (String) servizioFatturatoObj[2]);
                serviziFatturatiTableBean.add(servizioFatturatoRowBean);
            }
        } catch (Exception e) {
            LOGGER.error("Errore durante il recupero dei servizi fatturati " + ExceptionUtils.getRootCauseMessage(e),
                    e);
            throw new ParerUserError("Errore durante il recupero dei servizi fatturati ");
        }
        return serviziFatturatiTableBean;
    }

    public OrgVLisServFatturaTableBean getOrgVLisServFatturaTableBeanByIdServizioErog(BigDecimal idServizioErog)
            throws ParerUserError {
        List<Object[]> serviziFatturatiList = helper.retrieveOrgVLisServFatturaListByIdServizioErog(idServizioErog);
        OrgVLisServFatturaTableBean serviziFatturatiTableBean = new OrgVLisServFatturaTableBean();
        try {
            for (Object[] servizioFatturatoObj : serviziFatturatiList) {
                OrgVLisServFattura servizioFatturato = (OrgVLisServFattura) servizioFatturatoObj[0];
                OrgFatturaEnte fatturaEnte = (OrgFatturaEnte) servizioFatturatoObj[1];
                OrgVLisServFatturaRowBean servizioFatturatoRowBean = (OrgVLisServFatturaRowBean) Transform
                        .entity2RowBean(servizioFatturato);
                servizioFatturatoRowBean.setString("cd_fattura", fatturaEnte.getCdFattura());
                String numeroFatturaEmessa = fatturaEnte.getCdRegistroEmisFattura() != null
                        ? fatturaEnte.getCdRegistroEmisFattura() + " - " + fatturaEnte.getAaEmissFattura() + " - "
                                + fatturaEnte.getCdEmisFattura()
                        : "";
                servizioFatturatoRowBean.setString("numero_fattura_emessa", numeroFatturaEmessa);
                servizioFatturatoRowBean.setTimestamp("dt_emis_fattura", fatturaEnte.getDtEmisFattura() != null
                        ? new Timestamp((fatturaEnte.getDtEmisFattura()).getTime()) : null);
                serviziFatturatiTableBean.add(servizioFatturatoRowBean);
            }
        } catch (Exception e) {
            LOGGER.error("Errore durante il recupero dei servizi fatturati " + ExceptionUtils.getRootCauseMessage(e),
                    e);
            throw new ParerUserError("Errore durante il recupero dei servizi fatturati ");
        }
        return serviziFatturatiTableBean;
    }

    public OrgPagamFatturaEnteTableBean getOrgPagamFatturaEnteTableBean(BigDecimal idFatturaEnte)
            throws ParerUserError {
        List<OrgPagamFatturaEnte> pagamFatturaEnteList = helper.retrieveOrgPagamFatturaEnteList(idFatturaEnte);
        OrgPagamFatturaEnteTableBean pagamFatturaEnteTableBean = new OrgPagamFatturaEnteTableBean();
        try {
            if (!pagamFatturaEnteList.isEmpty()) {
                pagamFatturaEnteTableBean = (OrgPagamFatturaEnteTableBean) Transform
                        .entities2TableBean(pagamFatturaEnteList);
            }
        } catch (Exception e) {
            LOGGER.error(
                    "Errore durante il recupero dei pagamenti fattura ente " + ExceptionUtils.getRootCauseMessage(e),
                    e);
            throw new ParerUserError("Errore durante il recupero dei pagamenti fattura ente ");
        }
        return pagamFatturaEnteTableBean;
    }

    public OrgPagamFatturaEnteRowBean getOrgPagamFatturaEnteRowBean(BigDecimal idPagamFatturaEnte)
            throws ParerUserError {
        OrgPagamFatturaEnte pagamFatturaEnte = helper.findById(OrgPagamFatturaEnte.class, idPagamFatturaEnte);
        OrgPagamFatturaEnteRowBean serviziFatturatiRowBean = new OrgPagamFatturaEnteRowBean();
        try {
            if (pagamFatturaEnte != null) {
                serviziFatturatiRowBean = (OrgPagamFatturaEnteRowBean) Transform.entity2RowBean(pagamFatturaEnte);
            }
        } catch (Exception e) {
            LOGGER.error(
                    "Errore durante il recupero del pagamento fattura ente " + ExceptionUtils.getRootCauseMessage(e),
                    e);
            throw new ParerUserError("Errore durante il recupero del pagamento fattura ente ");
        }
        return serviziFatturatiRowBean;
    }

    public BaseTable getFattureRiemesseBaseTable(BigDecimal idFatturaEnte) throws ParerUserError {
        List<Object[]> fattureRiemesseList = helper.retrieveFattureRiemesseList(idFatturaEnte);
        BaseTable fattureRiemesseBaseTable = new BaseTable();
        try {
            for (Object[] fatturaRiemessa : fattureRiemesseList) {
                BaseRow fatturaRiemessaBaseRow = new BaseRow();
                fatturaRiemessaBaseRow.setString("cd_registro_emis_fattura",
                        fatturaRiemessa[0] != null ? (String) fatturaRiemessa[0] : null);
                fatturaRiemessaBaseRow.setBigDecimal("aa_emis_fattura",
                        fatturaRiemessa[1] != null ? (BigDecimal) fatturaRiemessa[1] : null);
                fatturaRiemessaBaseRow.setString("cd_emis_fattura",
                        fatturaRiemessa[2] != null ? (String) fatturaRiemessa[2] : null);
                fatturaRiemessaBaseRow.setTimestamp("dt_emis_fattura",
                        fatturaRiemessa[3] != null ? new Timestamp(((Date) fatturaRiemessa[3]).getTime()) : null);
                fatturaRiemessaBaseRow.setBigDecimal("im_tot_fattura",
                        fatturaRiemessa[4] != null ? (BigDecimal) fatturaRiemessa[4] : null);
                fatturaRiemessaBaseRow.setBigDecimal("im_pagam",
                        fatturaRiemessa[5] != null ? (BigDecimal) fatturaRiemessa[5] : null);
                fatturaRiemessaBaseRow.setString("ti_stato_fattura_ente",
                        fatturaRiemessa[6] != null ? (String) fatturaRiemessa[6] : null);
                fatturaRiemessaBaseRow.setTimestamp("dt_reg_stato_fattura_ente",
                        fatturaRiemessa[7] != null ? new Timestamp(((Date) fatturaRiemessa[7]).getTime()) : null);
                fatturaRiemessaBaseRow.setBigDecimal("id_fattura_ente", BigDecimal.valueOf((Long) fatturaRiemessa[8]));
                fattureRiemesseBaseTable.add(fatturaRiemessaBaseRow);
            }
        } catch (Exception e) {
            LOGGER.error("Errore durante il recupero delle fatture riemesse " + ExceptionUtils.getRootCauseMessage(e),
                    e);
            throw new ParerUserError("Errore durante il recupero delle fatture riemesse ");
        }
        return fattureRiemesseBaseTable;
    }

    public OrgSollecitoFatturaEnteTableBean getOrgSollecitoFatturaEnteTableBean(BigDecimal idFatturaEnte)
            throws ParerUserError {
        List<OrgSollecitoFatturaEnte> sollecitiList = helper.getOrgSollecitoFatturaEnteList(idFatturaEnte);
        OrgSollecitoFatturaEnteTableBean sollecitiTableBean = new OrgSollecitoFatturaEnteTableBean();
        try {
            for (OrgSollecitoFatturaEnte sollecito : sollecitiList) {
                OrgSollecitoFatturaEnteRowBean rafSollecito = new OrgSollecitoFatturaEnteRowBean();
                rafSollecito = (OrgSollecitoFatturaEnteRowBean) Transform.entity2RowBean(sollecito);
                if (rafSollecito.getCdRegistroSollecito() != null) {
                    rafSollecito.setString("identificativo_sollecito", rafSollecito.getCdRegistroSollecito() + " - "
                            + rafSollecito.getAaVarSollecito() + " - " + rafSollecito.getCdKeyVarSollecito());
                }
                sollecitiTableBean.add(rafSollecito);
            }
        } catch (Exception e) {
            LOGGER.error("Errore durante il recupero dei solleciti " + ExceptionUtils.getRootCauseMessage(e), e);
            throw new ParerUserError("Errore durante il recupero dei solleciti ");
        }
        return sollecitiTableBean;
    }

    public OrgModifFatturaEnteTableBean getOrgModifFatturaEnteTableBean(BigDecimal idFatturaEnte)
            throws ParerUserError {
        List<OrgModifFatturaEnte> modifFatturaEnteList = helper.retrieveOrgModifFatturaEnteList(idFatturaEnte);
        OrgModifFatturaEnteTableBean modifFatturaEnteTableBean = new OrgModifFatturaEnteTableBean();
        try {
            if (!modifFatturaEnteList.isEmpty()) {
                modifFatturaEnteTableBean = (OrgModifFatturaEnteTableBean) Transform
                        .entities2TableBean(modifFatturaEnteList);
            }
        } catch (Exception e) {
            LOGGER.error(
                    "Errore durante il recupero delle modifiche fattura ente " + ExceptionUtils.getRootCauseMessage(e),
                    e);
            throw new ParerUserError("Errore durante il recupero delle modifiche fattura ente ");
        }
        return modifFatturaEnteTableBean;
    }

    /**
     * Restituisce il rowBean (esteso con altri valori) del servizio fatturato
     *
     * @param idServizioFattura
     *            id servizio fattura
     *
     * @return row bean {@link OrgVLisServFatturaRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgVLisServFatturaRowBean getOrgVLisServFatturaRowBean(BigDecimal idServizioFattura) throws ParerUserError {
        OrgVLisServFattura servFattura = helper.findViewById(OrgVLisServFattura.class, idServizioFattura);
        OrgVLisServFatturaRowBean servFatturaRowBean = new OrgVLisServFatturaRowBean();
        try {
            if (servFattura != null) {
                servFatturaRowBean = (OrgVLisServFatturaRowBean) Transform.entity2RowBean(servFattura);
                servFatturaRowBean.setBigDecimal("quantita", BigDecimal.ONE);
                servFatturaRowBean.setString("unita_misura", "PZ");
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error("Errore durante il recupero dei servizi fatturati " + ExceptionUtils.getRootCauseMessage(e),
                    e);
            throw new ParerUserError("Errore durante il recupero dei servizi fatturati ");
        }
        return servFatturaRowBean;
    }

    public String getDsIva(BigDecimal idCdIva) {
        return (helper.findById(OrgCdIva.class, idCdIva)).getDsIva();
    }

    public String getTiEnteSiam(BigDecimal idEnteSiam) {
        return (helper.findById(OrgEnteSiam.class, idEnteSiam)).getTiEnte().name();
    }

    public String getTiStatoAccordo(BigDecimal idAccordoEnte) {
        return (helper.getTiStatoAccordoById(idAccordoEnte));
    }

    /**
     * Restituisce il rowbean contenente il dettaglio dell'accordo valido alla data corrente per l'ente convenzionato
     * dato in input
     *
     * @param idEnteConvenz
     *            l'id dell'accordo da recuperare su DB
     *
     * @return row bean {@link OrgAccordoEnteRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAccordoEnteRowBean getOrgAccordoEnteValidoRowBean(BigDecimal idEnteConvenz) throws ParerUserError {
        OrgAccordoEnteRowBean accordoEnteRowBean = new OrgAccordoEnteRowBean();
        OrgAccordoEnte accordoEnte = helper.retrieveOrgAccordoEnteValido(idEnteConvenz);
        if (accordoEnte != null) {
            try {
                accordoEnteRowBean = (OrgAccordoEnteRowBean) Transform.entity2RowBean(accordoEnte);
                accordoEnteRowBean.setString("nm_ente_convenz_gestore",
                        accordoEnte.getOrgEnteSiamByIdEnteConvenzGestore().getNmEnteSiam());
                accordoEnteRowBean.setString("nm_ente_convenz_conserv",
                        accordoEnte.getOrgEnteSiamByIdEnteConvenzConserv().getNmEnteSiam());
                accordoEnteRowBean.setString("nm_ente_convenz_amministratore",
                        accordoEnte.getOrgEnteSiamByIdEnteConvenzAmministratore().getNmEnteSiam());
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero dell'ente convenzionato gestore"
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return accordoEnteRowBean;
    }

    public OrgEnteSiamRowBean getOrgEnteConvenzGestore(BigDecimal idAmbienteEnteConvenz) throws ParerUserError {
        OrgEnteSiamRowBean enteConvenzGestoreRowBean = null;
        OrgEnteSiam enteConvenzGestore = helper.findById(OrgAmbienteEnteConvenz.class, idAmbienteEnteConvenz)
                .getOrgEnteSiamByIdEnteGestore();
        if (enteConvenzGestore != null) {
            enteConvenzGestoreRowBean = new OrgEnteSiamRowBean();
            try {
                enteConvenzGestoreRowBean = (OrgEnteSiamRowBean) Transform.entity2RowBean(enteConvenzGestore);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero dell'ente convenzionato gestore"
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return enteConvenzGestoreRowBean;
    }

    public OrgEnteSiamRowBean getOrgEnteConvenzConserv(BigDecimal idAmbienteEnteConvenz) throws ParerUserError {
        OrgEnteSiamRowBean enteConvenzConservRowBean = null;
        OrgEnteSiam enteConvenzConserv = helper.findById(OrgAmbienteEnteConvenz.class, idAmbienteEnteConvenz)
                .getOrgEnteSiamByIdEnteConserv();
        if (enteConvenzConserv != null) {
            enteConvenzConservRowBean = new OrgEnteSiamRowBean();
            try {
                enteConvenzConservRowBean = (OrgEnteSiamRowBean) Transform.entity2RowBean(enteConvenzConserv);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero dell'ente convenzionato conservatore"
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return enteConvenzConservRowBean;
    }

    public UsrUserTableBean getUtentiArchivisti() throws ParerUserError {
        UsrUserTableBean usrUserTableBean = new UsrUserTableBean();
        List<UsrUser> utentiArchivistiList = helper.retrieveUtentiArchivistiDaVista();
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

    public OrgStoEnteConvenzRowBean getOrgStoEnteConvenzRowBean(BigDecimal idStoEnteConvenz) throws ParerUserError {
        OrgStoEnteConvenzRowBean stoRowBean = new OrgStoEnteConvenzRowBean();
        OrgStoEnteConvenz sto = helper.findById(OrgStoEnteConvenz.class, idStoEnteConvenz);
        if (sto != null) {
            try {
                stoRowBean = (OrgStoEnteConvenzRowBean) Transform.entity2RowBean(sto);
                if (sto.getIdAmbitoTerrit() != null) {
                    // Setto i campi dell'ambito territoriale
                    OrgAmbitoTerrit ambito = helper.findById(OrgAmbitoTerrit.class, sto.getIdAmbitoTerrit());
                    if (ambito.getTiAmbitoTerrit()
                            .equals(ConstOrgAmbitoTerrit.TiAmbitoTerrit.FORMA_ASSOCIATA.toString())) {
                        final OrgAmbitoTerrit orgAmbitoTerritProvincia = ambito.getOrgAmbitoTerrit();
                        final OrgAmbitoTerrit orgAmbitoTerritRegione = orgAmbitoTerritProvincia.getOrgAmbitoTerrit();
                        stoRowBean.setBigDecimal("cd_ambito_territ_forma_associata", sto.getIdAmbitoTerrit());
                        stoRowBean.setBigDecimal("cd_ambito_territ_provincia",
                                new BigDecimal(orgAmbitoTerritProvincia.getIdAmbitoTerrit()));
                        stoRowBean.setBigDecimal("cd_ambito_territ_regione",
                                new BigDecimal(orgAmbitoTerritRegione.getIdAmbitoTerrit()));
                    } else if (ambito.getTiAmbitoTerrit()
                            .equals(ConstOrgAmbitoTerrit.TiAmbitoTerrit.PROVINCIA.toString())) {
                        final OrgAmbitoTerrit orgAmbitoTerritRegione = ambito.getOrgAmbitoTerrit();
                        stoRowBean.setBigDecimal("cd_ambito_territ_provincia", sto.getIdAmbitoTerrit());
                        stoRowBean.setBigDecimal("cd_ambito_territ_regione",
                                new BigDecimal(orgAmbitoTerritRegione.getIdAmbitoTerrit()));
                    } else {
                        // REGIONE/STATO
                        stoRowBean.setBigDecimal("cd_ambito_territ_regione", sto.getIdAmbitoTerrit());
                    }
                }
                // String u = "";
                stoRowBean.setBigDecimal("ds_categ_ente", stoRowBean.getIdCategEnte());
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero dell'anagrafica dell'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return stoRowBean;
    }

    /**
     * Storicizzo
     *
     * @param param
     *            parametri per il logging
     * @param stoCaBean
     *            bean {@link StoEnteConvenzionatoBean}
     * @param enteBean
     *            bean {@link EnteConvenzionatoBean}
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return id del nuovo ente storicizzato
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveStoEnteConvenzionato(LogParam param, StoEnteConvenzionatoBean stoCaBean,
            EnteConvenzionatoBean enteBean, BigDecimal idEnteConvenz) throws ParerUserError {
        LOGGER.debug("Eseguo la storicizzazione dell'ente convenzionato");

        Long idStoEnteConvenz = null;
        // Modifico l'ente convenzionato
        OrgEnteSiam ente = helper.findById(OrgEnteSiam.class, idEnteConvenz);
        if (helper.getOrgEnteConvenz(enteBean.getCd_ente_convenz(), enteBean.getTi_cd_ente_convenz()) != null
                && (!ente.getCdEnteConvenz().equals(enteBean.getCd_ente_convenz())
                        || !ente.getTiCdEnteConvenz().equals(enteBean.getTi_cd_ente_convenz()))) {
            throw new ParerUserError("Ente convenzionato gi\u00E0 censito nel sistema " + "con codice "
                    + enteBean.getCd_ente_convenz() + " e tipo codice " + enteBean.getTi_cd_ente_convenz());
        }

        try {
            // Storicizzo
            OrgStoEnteConvenz stoEnte = new OrgStoEnteConvenz();
            setDatiStoEnteConvenzionato(stoEnte, stoCaBean, idEnteConvenz);
            helper.insertEntity(stoEnte, false);
            setDatiEnteConvenzionato(ente, enteBean);
            ente.setNmEnteSiam(enteBean.getNm_ente_convenz());
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(ente.getIdEnteSiam()), param.getNomePagina());
            LOGGER.debug("Storicizzazione dell'ente completata");
            idStoEnteConvenz = stoEnte.getIdStoEnteConvenz();
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante la storicizzazione dell'ente convenzionato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante la storicizzazione dell'ente convenzionato");
        }
        return idStoEnteConvenz;
    }

    /**
     * Modifico una storicizzazione
     *
     * @param param
     *            parametri per il logging
     * @param idStoEnteConvenz
     *            id storico ente convenzionato
     * @param stoCaBean
     *            bean {@link StoEnteConvenzionatoBean}
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveModificaStoEnteConvenzionato(LogParam param, BigDecimal idStoEnteConvenz,
            StoEnteConvenzionatoBean stoCaBean, BigDecimal idEnteConvenz) throws ParerUserError {
        LOGGER.debug("Eseguo la modifica di una storicizzazione dell'ente convenzionato");
        try {
            // Recupero da DB la storicizzazione
            OrgStoEnteConvenz stoEnte = helper.findById(OrgStoEnteConvenz.class, idStoEnteConvenz);
            // Modifico i dati
            setDatiStoEnteConvenzionato(stoEnte, stoCaBean, idEnteConvenz);
            // Li persisto
            helper.mergeEntity(stoEnte);
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    idEnteConvenz, param.getNomePagina());
            LOGGER.debug("Modifica storicizzazione dell'ente completata");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante la modifica della storicizzazione dell'ente convenzionato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError(
                    "Eccezione imprevista durante la modifica della storicizzazione dell'ente convenzionato");
        }
    }

    private void setDatiStoEnteConvenzionato(OrgStoEnteConvenz sto, StoEnteConvenzionatoBean stoCaBean,
            BigDecimal idEnteConvenz) {
        // Imposta tutti i campi dell'ente da storicizzare
        sto.setCdEnteConvenz(stoCaBean.getCd_ente_convenz());
        sto.setTiCdEnteConvenz(stoCaBean.getTi_cd_ente_convenz());
        sto.setDtIniVal(stoCaBean.getDt_ini_val());
        sto.setDtFineVal(stoCaBean.getDt_fine_val());
        sto.setDlNote(stoCaBean.getDl_note());
        sto.setDsViaSedeLegale(stoCaBean.getDs_via_sede_legale());
        sto.setCdCapSedeLegale(stoCaBean.getCd_cap_sede_legale());
        sto.setDsCittaSedeLegale(stoCaBean.getDs_citta_sede_legale());
        sto.setCdNazioneSedeLegale(stoCaBean.getCd_nazione_sede_legale());
        sto.setIdProvSedeLegale(stoCaBean.getId_prov_sede_legale());
        sto.setCdFisc(stoCaBean.getCd_fisc());
        sto.setIdCategEnte(stoCaBean.getDs_categ_ente());
        // sto.setIdEnteConvenzNuovo(stoCaBean.getId_ente_convenz_nuovo());
        OrgEnteSiam enteSiam = helper.findById(OrgEnteSiam.class, idEnteConvenz);
        sto.setOrgEnteSiam(enteSiam);
        sto.setNmEnteConvenz(stoCaBean.getNm_ente_convenz());
        // Per impostare l'ambito territoriale parto dalla forma associata e poi lo popolo col primo non nullo
        BigDecimal idAmbitoTerrit;
        if (stoCaBean.getCd_ambito_territ_forma_associata() != null) {
            idAmbitoTerrit = stoCaBean.getCd_ambito_territ_forma_associata();
        } else if (stoCaBean.getCd_ambito_territ_provincia() != null) {
            idAmbitoTerrit = stoCaBean.getCd_ambito_territ_provincia();
        } else {
            idAmbitoTerrit = stoCaBean.getCd_ambito_territ_regione();
        }
        sto.setIdAmbitoTerrit(idAmbitoTerrit);
    }

    public boolean existOtherAnagrafichePerIntervallo(BigDecimal idEnteConvenz, Date dtIniVal, Date dtFineVal,
            BigDecimal idStoEnteConvenzDaEscludere) {
        return helper.existOtherAnagrafichePerIntervallo(idEnteConvenz, dtIniVal, dtFineVal,
                idStoEnteConvenzDaEscludere);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void calcolaServiziErogatiDaDettaglioAccordo(LogParam param, BigDecimal idAccordoEnte)
            throws ParerUserError {
        try {
            OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordoEnte);
            calcolaServiziErogatiSuAccordo(accordoEnte);
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    BigDecimal.valueOf(accordoEnte.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il ricalcolo dei servizi erogati "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il ricalcolo dei servizi erogati");
        }
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

    public UsrVAbilAmbEnteConvenzTableBean getUsrVAbilAmbEnteConvenzByLastAccordoTableBean(BigDecimal idUserIam,
            BigDecimal idEnteConserv) {
        UsrVAbilAmbEnteConvenzTableBean abilAmbEnteConvenzTableBean = new UsrVAbilAmbEnteConvenzTableBean();
        List<UsrVAbilAmbEnteConvenz> abilAmbEnteConvenzList = helper
                .retrieveAbilAmbEntiConvenzValidByIdEnteConserv(idUserIam, idEnteConserv);
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

    public UsrVAbilAmbEnteXenteTableBean getUsrVAbilAmbEnteXEnteByLastAccordoTableBean(BigDecimal idUserIam,
            BigDecimal idEnteConserv, BigDecimal idEnteGestore) {
        UsrVAbilAmbEnteXenteTableBean abilAmbEnteXEnteTableBean = new UsrVAbilAmbEnteXenteTableBean();
        List<UsrVAbilAmbEnteXente> abilAmbEnteXEnteList = helper.retrieveAbilAmbEnteXEnteValidAmbienti(idUserIam,
                idEnteConserv, idEnteGestore);
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

    public BaseTable getAmbientiAbilitatiUnAmbienteTableBean(long idUserIamCor, String tipoEnte, BigDecimal idEnte) {
        BaseTable tabella = new BaseTable();
        List<Object[]> ambientiList = new ArrayList<>();
        ambientiList = helper.getAmbientiAbilitatiUnAmbienteList(idUserIamCor, tipoEnte, idEnte);
        for (Object[] ambiente : ambientiList) {
            try {
                BaseRow riga = new BaseRow();
                riga.setBigDecimal("id_ambiente_ente_convenz", (BigDecimal) ambiente[0]);
                riga.setString("nm_ambiente_ente_convenz", (String) ambiente[1]);
                riga.setString("ds_causale_dich", (String) ambiente[2]);
                tabella.add(riga);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return tabella;
    }

    public BaseTable getEntiAbilitatiUnEnteTableBean(long idUserIamCor, String tipoEnte, BigDecimal idEnte,
            BigDecimal idAmbienteEnteConvenz) {
        BaseTable tabella = new BaseTable();
        List<Object[]> entiList = helper.getEntiAbilitatiUnEnteList(idUserIamCor, tipoEnte, idEnte,
                idAmbienteEnteConvenz);

        for (Object[] ente : entiList) {
            try {
                BaseRow riga = new BaseRow();
                riga.setBigDecimal("id_ente_convenz", (BigDecimal) ente[0]);
                riga.setString("nm_ente_convenz", (String) ente[1]);
                riga.setString("ds_causale_dich", (String) ente[2]);
                if (ente.length > 3) {
                    riga.setBigDecimal("id_ente", (BigDecimal) ente[3]);
                }
                tabella.add(riga);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return tabella;
    }

    public BaseTable getAmbientiEntiAbilitatiUnEnteTableBean(long idUserIamCor, String tipoEnte, BigDecimal idEnte) {
        BaseTable tabella = new BaseTable();
        List<Object[]> ambientiEntiList = helper.getAmbientiEntiAbilitatiUnEnteList(idUserIamCor, tipoEnte, idEnte);

        for (Object[] ente : ambientiEntiList) {
            try {
                BaseRow riga = new BaseRow();
                riga.setBigDecimal("id_ambiente_ente_convenz", (BigDecimal) ente[0]);
                riga.setString("nm_ambiente_ente_convenz", (String) ente[1]);
                tabella.add(riga);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return tabella;
    }

    /**
     * Recupera gli ambienti ente a cui l'utente passato in ingresso \u00E8 abilitato (anche quelli che non sono ancora
     * padri di alcun ente)
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

    /**
     * Recupera gli ambienti ente a cui l'utente passato in ingresso \u00E8 abilitato (anche quelli che non sono ancora
     * padri di alcun ente) e validi alla data
     *
     * @param idUserIam
     *            id user IAM
     *
     * @return table bean {@link UsrVAbilAmbEnteXenteTableBean}
     */
    public UsrVAbilAmbEnteXenteTableBean getUsrVAbilAmbEnteXEnteValidTableBean(BigDecimal idUserIam) {
        UsrVAbilAmbEnteXenteTableBean abilAmbEnteXenteTableBean = new UsrVAbilAmbEnteXenteTableBean();
        List<UsrVAbilAmbEnteXente> abilAmbEnteXenteList = helper.retrieveAmbientiEntiXenteAbilitatiValid(idUserIam);
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

    /**
     * Recupera gli enti convenzionati a cui l'utente passato in ingresso \u00E8 abilitato. Restituisce inoltre un nuovo
     * campo dato dalla combinazione tra nome ambiente ente convenzionato e nome ente convenzionato
     *
     * @param idUserIam
     *            id user IAM
     * @param data
     *            data di riferimento
     *
     * @return table bean {@link UsrVAbilEnteConvenzTableBean}
     */
    public UsrVAbilEnteConvenzTableBean getUsrVAbilEnteConvenzTableBean(BigDecimal idUserIam, Date data) {
        UsrVAbilEnteConvenzTableBean abilEnteConvenzTableBean = new UsrVAbilEnteConvenzTableBean();
        List<UsrVAbilEnteConvenz> abilEnteConvenzList = helper.retrieveEntiConvenzAbilitati(idUserIam, data);
        for (UsrVAbilEnteConvenz abilEnteConvenz : abilEnteConvenzList) {
            try {
                UsrVAbilEnteConvenzRowBean abilEnteConvenzRowBean = (UsrVAbilEnteConvenzRowBean) Transform
                        .entity2RowBean(abilEnteConvenz);
                abilEnteConvenzRowBean.setString("nmAmbienteEnteCongiunti",
                        abilEnteConvenz.getNmAmbienteEnteConvenz() + " / " + abilEnteConvenz.getNmEnteConvenz());
                abilEnteConvenzTableBean.add(abilEnteConvenzRowBean);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return abilEnteConvenzTableBean;
    }

    /**
     * Recupera gli enti convenzionati di un determinato ambiente a cui l'utente passato in ingresso \u00E8 abilitato
     *
     * @param idUserIam
     *            id user IAM
     * @param idAmbienteEnteConvenz
     *            id ambiente convenzionato
     * @param data
     *            data di riferimento
     *
     * @return table bean {@link UsrVAbilEnteConvenzTableBean}
     */
    public UsrVAbilEnteConvenzTableBean getUsrVAbilEnteConvenzTableBeanByUtenteAmbiente(BigDecimal idUserIam,
            BigDecimal idAmbienteEnteConvenz, Date data) {
        UsrVAbilEnteConvenzTableBean abilEnteConvenzTableBean = new UsrVAbilEnteConvenzTableBean();
        List<UsrVAbilEnteConvenz> abilEnteConvenzList = helper.retrieveEntiConvenzAbilitatiByUtenteAmbiente(idUserIam,
                idAmbienteEnteConvenz, data);
        if (!abilEnteConvenzList.isEmpty()) {
            try {
                abilEnteConvenzTableBean = (UsrVAbilEnteConvenzTableBean) Transform
                        .entities2TableBean(abilEnteConvenzList);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return abilEnteConvenzTableBean;
    }

    public boolean checkDichAutorEnteConvenzSetForChilds(BigDecimal idAmbienteEnteConvenz,
            Set<PairAuth> scopoEnteConvenzSet) {
        boolean result = false;
        OrgAmbienteEnteConvenz ambienteEnteConvenz = helper.findById(OrgAmbienteEnteConvenz.class,
                idAmbienteEnteConvenz);
        for (OrgEnteSiam ente : ambienteEnteConvenz.getOrgEnteSiams()) {
            if (scopoEnteConvenzSet
                    .contains(new PairAuth(ActionEnums.ScopoDichAbilEnteConvenz.UN_ENTE.name(),
                            new PairAuth(idAmbienteEnteConvenz, new BigDecimal(ente.getIdEnteSiam()))))
                    || scopoEnteConvenzSet.contains(new PairAuth(ActionEnums.ScopoDichAbilEnteConvenz.UN_ENTE.name(),
                            new PairAuth(BigDecimal.ZERO, new BigDecimal(ente.getIdEnteSiam()))))) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean checkUtenteAbilChiusuraAccordo(BigDecimal idUserIamCor, BigDecimal idAccordoEnte,
            TiEnteConvenz tiEnteConvenz) {
        boolean result = false;
        OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordoEnte);
        if (accordoEnte != null) {
            switch (tiEnteConvenz) {
            case CONSERVATORE:
                if (helper.checkAppartenenzaUtenteEnte(idUserIamCor, BigDecimal
                        .valueOf(accordoEnte.getOrgEnteSiamByIdEnteConvenzAmministratore().getIdEnteSiam()))) {
                    result = true;
                }
                break;
            case GESTORE:
                if (helper.checkAppartenenzaUtenteEnte(idUserIamCor,
                        BigDecimal.valueOf(accordoEnte.getOrgEnteSiamByIdEnteConvenzAmministratore().getIdEnteSiam()))
                        || helper.checkAppartenenzaUtenteEnte(idUserIamCor, BigDecimal
                                .valueOf(accordoEnte.getOrgEnteSiamByIdEnteConvenzConserv().getIdEnteSiam()))) {
                    result = true;
                }
                break;
            case PRODUTTORE:
                if (helper.checkAppartenenzaUtenteEnte(idUserIamCor,
                        BigDecimal.valueOf(accordoEnte.getOrgEnteSiamByIdEnteConvenzAmministratore().getIdEnteSiam()))
                        || helper.checkAppartenenzaUtenteEnte(idUserIamCor,
                                BigDecimal.valueOf(accordoEnte.getOrgEnteSiamByIdEnteConvenzConserv().getIdEnteSiam()))
                        || helper.checkAppartenenzaUtenteEnte(idUserIamCor, BigDecimal
                                .valueOf(accordoEnte.getOrgEnteSiamByIdEnteConvenzGestore().getIdEnteSiam()))) {
                    result = true;
                }
                break;
            default:
                break;
            }
        }
        return result;
    }

    public boolean checkUtenteAbilLastAccordo(BigDecimal idUserIamCor, BigDecimal idEnteConvenz) {
        boolean result = false;
        OrgAccordoEnte accordoEnte = helper.retrieveOrgAccordoEntePiuRecente(idEnteConvenz);
        if (accordoEnte != null) {
            if (helper.checkEsistenzaUtenteAbilitatoXEnte(idUserIamCor,
                    BigDecimal.valueOf(accordoEnte.getOrgEnteSiamByIdEnteConvenzConserv().getIdEnteSiam()))
                    || helper.checkEsistenzaUtenteAbilitatoXEnte(idUserIamCor, BigDecimal
                            .valueOf(accordoEnte.getOrgEnteSiamByIdEnteConvenzAmministratore().getIdEnteSiam()))) {
                result = true;
            }
        }
        return result;
    }

    public boolean checkUtenteArkAbilEntiConvenzColleg(BigDecimal idUserIamCor, BigDecimal idUserIamArk) {
        boolean result = false;
        // Recupero la lista degli enti convenzionati di tipo CONSERVATORE e AMMINISTRATORE cui l'utente corrente
        // \u00E8
        // abilitato
        List<OrgEnteSiam> enteConvenzList = helper.getOrgEnteConvenzUserAbilList(idUserIamCor,
                ConstOrgEnteSiam.TiEnteConvenz.CONSERVATORE, ConstOrgEnteSiam.TiEnteConvenz.AMMINISTRATORE);
        if (!enteConvenzList.isEmpty()) {
            List<OrgEnteSiam> collegEntiConvenzList = new ArrayList();
            // Recupero per ogni ente convenzionato gli enti ad esso collegati (escluso se stesso)
            for (OrgEnteSiam enteConvenz : enteConvenzList) {
                collegEntiConvenzList.addAll(helper.getOrgEnteConvenzCollegUserAbilList(idUserIamCor,
                        BigDecimal.valueOf(enteConvenz.getIdEnteSiam())));
            }
            // Aggiungo alla lista degli enti convenzionati gli enti ad essi collegati
            if (!collegEntiConvenzList.isEmpty()) {
                enteConvenzList.addAll(collegEntiConvenzList);
            }
        }
        UsrUser userIamArk = helper.findById(UsrUser.class, idUserIamArk);
        for (OrgEnteSiam enteConvenz : enteConvenzList) {
            if (userIamArk.getOrgEnteSiam().getIdEnteSiam() == enteConvenz.getIdEnteSiam()) {
                result = true;
                break;
            } else {
                result = false;
            }

        }
        return result;
    }

    public OrgAmbienteEnteConvenzRowBean getOrgAmbienteEnteConvenzByEnteConvenz(BigDecimal idEnteConvenz) {
        OrgAmbienteEnteConvenzRowBean ambienteEnteConvenzRowBean = new OrgAmbienteEnteConvenzRowBean();
        try {
            OrgAmbienteEnteConvenz ambienteEnteConvenz = helper
                    .retrieveOrgAmbienteEnteConvenzByEnteConvenz(idEnteConvenz);
            if (ambienteEnteConvenz != null) {
                ambienteEnteConvenzRowBean = (OrgAmbienteEnteConvenzRowBean) Transform
                        .entity2RowBean(ambienteEnteConvenz);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error("Errore durante il recupero dell'ambiente di un ente convenzionato "
                    + ExceptionUtils.getRootCauseMessage(e), e);
        }
        return ambienteEnteConvenzRowBean;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void calcolaServiziErogatiSuAccordo(OrgAccordoEnte accordoEnte) {
        boolean serviziErogatiModificati = false;
        if (accordoEnte != null) {
            // Calcolo i servizi da inserire, cominciando con l'interrogare la vista OrgVCreaServErogByAcc
            List<OrgVCreaServErogByAcc> listaServiziErogatiVISTA = helper
                    .getServiziErogatiSuAccordo(accordoEnte.getIdAccordoEnte());
            if (!listaServiziErogatiVISTA.isEmpty()) {
                // Se sull'accordo non erano gi\u00E0  definiti dei servizi erogati, li inserisco a raffica...
                if ((accordoEnte.getOrgServizioErogs() == null)
                        || (accordoEnte.getOrgServizioErogs() != null && accordoEnte.getOrgServizioErogs().isEmpty())) {
                    for (OrgVCreaServErogByAcc servizioErogatoVISTA : listaServiziErogatiVISTA) {
                        setDatiServizioErogato(accordoEnte, servizioErogatoVISTA);
                        serviziErogatiModificati = true;
                    }
                } // ...altrimenti confronto
                else {
                    // Confrontro i record presenti nella vista per vedere se erano gi\u00E0  stati inseriti o meno
                    for (OrgVCreaServErogByAcc servizioErogatoVISTA : listaServiziErogatiVISTA) {

                        // Ricavo tipo servizio riferito al record della vista per i controlli
                        OrgTipoServizio tipoServizioVISTA = helper.findById(OrgTipoServizio.class,
                                servizioErogatoVISTA.getIdTipoServizio());

                        boolean trovato = false;

                        // Scorro i servizi erogati gi\u00E0 presenti...
                        for (OrgServizioErog servizioErog : accordoEnte.getOrgServizioErogs()) {
                            // Se sono di tipo conservazione o altro controllo solo il tipo servizio...
                            if (tipoServizioVISTA.getTiClasseTipoServizio()
                                    .equals(ConstOrgTipoServizio.TiClasseTipoServizio.CONSERVAZIONE.name())
                                    || tipoServizioVISTA.getTiClasseTipoServizio()
                                            .equals(ConstOrgTipoServizio.TiClasseTipoServizio.ALTRO.name())) {
                                if (servizioErogatoVISTA.getIdTipoServizio()
                                        .longValue() == (servizioErog.getOrgTipoServizio().getIdTipoServizio())) {
                                    trovato = true;
                                    break;
                                }
                            } // Se sono di tipo attivazione sistema versante controllo anche il sistema versante
                            else if (tipoServizioVISTA.getTiClasseTipoServizio().equals(
                                    ConstOrgTipoServizio.TiClasseTipoServizio.ATTIVAZIONE_SISTEMA_VERSANTE.name())) {
                                // Ricavo sistema versante riferito al record della vista per i controlli
                                AplSistemaVersante sistemaVersanteVISTA = helper.findById(AplSistemaVersante.class,
                                        servizioErogatoVISTA.getIdSistemaVersante());
                                if (servizioErogatoVISTA.getIdTipoServizio()
                                        .longValue() == (servizioErog.getOrgTipoServizio().getIdTipoServizio())
                                        && sistemaVersanteVISTA.getIdSistemaVersante() == (servizioErog
                                                .getAplSistemaVersante().getIdSistemaVersante())) {
                                    trovato = true;
                                    break;
                                }
                            } // Se sono di tipo attivazione tipo ud
                            else if (tipoServizioVISTA.getTiClasseTipoServizio()
                                    .equals(ConstOrgTipoServizio.TiClasseTipoServizio.ATTIVAZIONE_TIPO_UD.name())
                                    || tipoServizioVISTA.getTiClasseTipoServizio()
                                            .equals(ConstOrgTipoServizio.TiClasseTipoServizio.ALTRO_ANNUALITA.name())) {
                                if (servizioErogatoVISTA.getIdTipoServizio()
                                        .longValue() == (servizioErog.getOrgTipoServizio().getIdTipoServizio())) {
                                    trovato = true;
                                    break;
                                }
                            } else {
                                trovato = true;
                            }
                        }
                        // Se non era gi\u00E0  presente, lo inserisco...
                        if (!trovato) {
                            setDatiServizioErogato(accordoEnte, servizioErogatoVISTA);
                            serviziErogatiModificati = true;
                        }
                    }

                    // Riscorro e ora confronto i record che erano già  presenti e vedo quali
                    // non erano stati restituiti dalla vista
                    // Uso servizioErogIterator per poter cancellare durante il ciclo
                    Iterator<OrgServizioErog> servizioErogIterator = accordoEnte.getOrgServizioErogs().iterator();
                    while (servizioErogIterator.hasNext()) {
                        OrgServizioErog elemento = servizioErogIterator.next();
                        boolean trovato = false;

                        for (OrgVCreaServErogByAcc servizioErogatoSuAccordo : listaServiziErogatiVISTA) {

                            // Ricavo tipo servizio riferito al record della vista per i controlli
                            OrgTipoServizio tipoServizioVISTA = helper.findById(OrgTipoServizio.class,
                                    servizioErogatoSuAccordo.getIdTipoServizio());

                            // Se sono di tipo conservazione o altro controllo solo il tipo servizio...
                            if (tipoServizioVISTA.getTiClasseTipoServizio()
                                    .equals(ConstOrgTipoServizio.TiClasseTipoServizio.CONSERVAZIONE.name())
                                    || tipoServizioVISTA.getTiClasseTipoServizio()
                                            .equals(ConstOrgTipoServizio.TiClasseTipoServizio.ALTRO.name())) {

                                if (servizioErogatoSuAccordo.getIdTipoServizio()
                                        .longValue() == (elemento.getOrgTipoServizio().getIdTipoServizio())) {
                                    trovato = true;
                                    break;
                                }
                            } // Se sono di tipo attivazione sistema versante controllo anche il sistema versante
                            else if (tipoServizioVISTA.getTiClasseTipoServizio().equals(
                                    ConstOrgTipoServizio.TiClasseTipoServizio.ATTIVAZIONE_SISTEMA_VERSANTE.name())) {
                                AplSistemaVersante sistemaVersanteVISTA = helper.findById(AplSistemaVersante.class,
                                        servizioErogatoSuAccordo.getIdSistemaVersante());
                                if (servizioErogatoSuAccordo.getIdTipoServizio()
                                        .longValue() == (elemento.getOrgTipoServizio().getIdTipoServizio())
                                        && sistemaVersanteVISTA.getIdSistemaVersante() == (elemento
                                                .getAplSistemaVersante().getIdSistemaVersante())) {
                                    trovato = true;
                                    break;
                                }
                            } // Se sono di tipo attivazione tipo ud
                            else if (tipoServizioVISTA.getTiClasseTipoServizio()
                                    .equals(ConstOrgTipoServizio.TiClasseTipoServizio.ATTIVAZIONE_TIPO_UD.name())
                                    || tipoServizioVISTA.getTiClasseTipoServizio()
                                            .equals(ConstOrgTipoServizio.TiClasseTipoServizio.ALTRO_ANNUALITA.name())) {
                                if (servizioErogatoSuAccordo.getIdTipoServizio()
                                        .longValue() == (elemento.getOrgTipoServizio().getIdTipoServizio())) {
                                    trovato = true;
                                    break;
                                }
                            } else {
                                trovato = true;
                            }
                        }

                        // Se non l'ho trovato (ciè era in lista ma non era presente tra quelli restituiti
                        // dalla vista...), controllo se il servizio erogato era stato fatturato
                        // in una fattura con stato diverso da CALCOLATA e in caso non sia fatturato lo rimuovo
                        if (!trovato) {
                            if (elemento.getDtErog() == null
                                    || (elemento.getDtErog() != null && !checkEsistenzaServiziInFattura(
                                            BigDecimal.valueOf(elemento.getIdServizioErogato())))) {
                                servizioErogIterator.remove();
                                helper.getEntityManager().remove(elemento);
                                serviziErogatiModificati = true;
                            }
                        }
                    }
                }
            }

            // Se \u00E8 presente un accordo precedente a quello corrente e la data corrente
            // \u00E8 successiva alla scadenza di tale accordo, si devono eliminare i servizi erogati
            // di tipo ATTIVAZIONE_SISTEMA_VERSANTE o ATTIVAZIONE_TIPO_UD di tale accordo,
            // con data erogazione nulla
            OrgEnteSiam enteSiam = helper.findById(OrgEnteSiam.class, accordoEnte.getOrgEnteSiam().getIdEnteSiam());
            for (OrgAccordoEnte acc : enteSiam.getOrgAccordoEntes()) {
                if (acc.getIdAccordoEnte() != accordoEnte.getIdAccordoEnte()
                        && acc.getDtDecAccordo().before(accordoEnte.getDtDecAccordo())
                        && acc.getDtScadAccordo().before(new Date())) {
                    Iterator<OrgServizioErog> servizioErogIterator = acc.getOrgServizioErogs().iterator();
                    while (servizioErogIterator.hasNext()) {
                        OrgServizioErog elemento = servizioErogIterator.next();
                        if ((elemento.getOrgTipoServizio().getTiClasseTipoServizio()
                                .equals("ATTIVAZIONE_SISTEMA_VERSANTE")
                                || elemento.getOrgTipoServizio().getTiClasseTipoServizio()
                                        .equals("ATTIVAZIONE_TIPO_UD"))
                                && elemento.getDtErog() == null) {
                            servizioErogIterator.remove();
                            helper.getEntityManager().remove(elemento);
                            serviziErogatiModificati = true;
                        }
                    }
                }
            }

            // Verifico se per l'ente convenzionato dell'accordo esistono fatture in stato CALCOLATA
            BigDecimal idEnteConvenz = BigDecimal.valueOf(accordoEnte.getOrgEnteSiam().getIdEnteSiam());
            List<OrgFatturaEnte> fatturaEnteList = helper.getFattureEnteConvenzionato(idEnteConvenz,
                    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.getDescrizione());

            // Per ogni fattura ricavata in stato CALCOLATA,
            if (serviziErogatiModificati) {
                for (OrgFatturaEnte fatturaEnte : fatturaEnteList) {
                    // Determino l'anno dei servizi da fatturare
                    BigDecimal annoFattServizi = helper.getAnnoServiziDaFatturare(fatturaEnte.getIdFatturaEnte());

                    // Elimino i servizi fatturati della fattura
                    for (OrgServizioFattura servizioFattura : fatturaEnte.getOrgServizioFatturas()) {
                        helper.getEntityManager().remove(servizioFattura);
                    }
                    fatturaEnte.getOrgServizioFatturas().clear();

                    // Ricalcolo dei servizi fatturati e dei totali da inserire alla fattura
                    ctx.getBusinessObject(EntiConvenzionatiEjb.class).creaServiziFatturatiERicalcolaTotali(fatturaEnte,
                            BigDecimal.valueOf(accordoEnte.getIdAccordoEnte()), annoFattServizi);
                }
            }
        }
    }

    public void calcolaServiziErogatiSuUltimoAccordo(BigDecimal idEnteConvenz) throws ParerUserError {
        OrgAccordoEnte accordoEnte = userHelper.getLastAccordoEnte(idEnteConvenz, new Date());
        calcolaServiziErogatiSuAccordo(accordoEnte);
    }

    public UsrVAbilAmbConvenzXenteTableBean getUsrVAbilAmbConvenzXenteTableBean(BigDecimal idUserIam,
            String nmAmbienteEnteConvenz, String dsAmbienteEnteConvenz) {
        UsrVAbilAmbConvenzXenteTableBean abilAmbConvenzXenteTableBean = new UsrVAbilAmbConvenzXenteTableBean();
        List<UsrVAbilAmbConvenzXente> abilAmbEnteConvenzList = helper.retrieveAmbientiEntiConvenzAbilitati(idUserIam,
                nmAmbienteEnteConvenz, dsAmbienteEnteConvenz);
        if (!abilAmbEnteConvenzList.isEmpty()) {
            try {
                abilAmbConvenzXenteTableBean = (UsrVAbilAmbConvenzXenteTableBean) Transform
                        .entities2TableBean(abilAmbEnteConvenzList);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return abilAmbConvenzXenteTableBean;
    }

    public OrgVRicEnteConvenzTableBean getOrgVRicEnteConvenzTableBean(BigDecimal idAmbienteEnteConvenz) {
        OrgVRicEnteConvenzTableBean ricEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        List<OrgVRicEnteConvenz> ricEnteConvenzList = helper
                .retrieveOrgVRicEnteConvenzByAmbiente(idAmbienteEnteConvenz);
        if (!ricEnteConvenzList.isEmpty()) {
            try {
                ricEnteConvenzTableBean = customEntities2EnteConvenzTableBean(ricEnteConvenzList);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error(
                        "Errore durante il recupero degli enti convenzionati " + ExceptionUtils.getRootCauseMessage(e),
                        e);
            }
        }
        return ricEnteConvenzTableBean;
    }

    /**
     * Recupera la lista dei nomi degli enti convenzionati cui l'utente è abilitato
     *
     * @param idUserIamCor
     *            id user IAM
     *
     * @return table bean {@link OrgVRicEnteConvenzTableBean}
     */
    public OrgVRicEnteConvenzTableBean getOrgVRicEnteConvenzAbilTableBean(BigDecimal idUserIamCor) {
        OrgVRicEnteConvenzTableBean ricEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        List<OrgVRicEnteConvenz> ricEnteConvenzList = helper.retrieveEntiConvenzAbilitati(idUserIamCor);
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

    /**
     * Recupera la lista dei nomi degli enti convenzionati di tipo AMMINISTRATORE cui lÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢utente \u00E8
     * abilitato
     *
     * @deprecated
     *
     * @param idUserIamCor
     *            id user IAM
     *
     * @return table bean {@link OrgVRicEnteConvenzTableBean}
     */
    @Deprecated
    public OrgVRicEnteConvenzTableBean getOrgVRicEnteConvenzAbilSoloAmmTableBean(BigDecimal idUserIamCor) {
        OrgVRicEnteConvenzTableBean ricEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        List<OrgVRicEnteConvenz> ricEnteConvenzList = helper.retrieveEntiConvenzAbilitatiSoloAmm(idUserIamCor);
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

    /**
     * Recupera la lista dei nomi degli enti convenzionati cui l'utente è abilitato
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idAmbienteEnteConvenz
     *            id ambiente/ente convezionato
     * @param tiEnteConvenz
     *            tipo ente convenzione {@link TiEnteConvenz}
     *
     * @return table bean {@link OrgVRicEnteConvenzTableBean}
     */
    public OrgVRicEnteConvenzTableBean getOrgVRicEnteConvenzAbilTableBean(BigDecimal idUserIamCor,
            List<BigDecimal> idAmbienteEnteConvenz, ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz) {
        OrgVRicEnteConvenzTableBean ricEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        List<OrgVRicEnteConvenz> ricEnteConvenzList = helper.retrieveEntiConvenzAbilitatiAmbiente(idUserIamCor,
                idAmbienteEnteConvenz, tiEnteConvenz);
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

    /*
     * Recupera la lista dei nomi degli enti convenzionati cui lÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢utente \u00E8 abilitato
     *
     * @param idUserIamCor
     * 
     * @param idAmbienteEnteConvenz
     * 
     * @return
     */
    public OrgVRicEnteConvenzTableBean getOrgVRicEnteConvenzAbilTableBean(BigDecimal idUserIamCor,
            BigDecimal idAmbienteEnteConvenz, ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz) {
        OrgVRicEnteConvenzTableBean ricEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        List<BigDecimal> idAmbienteEnteConvenzList = new ArrayList<>();
        idAmbienteEnteConvenzList.add(idAmbienteEnteConvenz);
        List<OrgVRicEnteConvenz> ricEnteConvenzList = helper.retrieveEntiConvenzAbilitatiAmbiente(idUserIamCor,
                idAmbienteEnteConvenzList, tiEnteConvenz);
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

    /**
     * Recupera la lista dei nomi degli enti convenzionati e degli enti ad essi collegati cui lÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢utente
     * \u00E8 abilitato
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idAmbienteEnteConvenz
     *            lista id ambiente/ente convenzionato
     * @param tiEnteConvenz
     *            table bean {@link OrgVRicEnteConvenzTableBean}
     *
     * @return table bean {@link OrgVRicEnteConvenzTableBean}
     */
    public OrgVRicEnteConvenzTableBean getOrgVRicEnteConvenzCollegAbilTableBean(BigDecimal idUserIamCor,
            List<BigDecimal> idAmbienteEnteConvenz, ConstOrgEnteSiam.TiEnteConvenz... tiEnteConvenz) {
        OrgVRicEnteConvenzTableBean ricEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        List<OrgVRicEnteConvenz> ricEnteConvenzList = helper.retrieveEntiConvenzCollegAbilitatiAmbiente(idUserIamCor,
                idAmbienteEnteConvenz, tiEnteConvenz);
        if (ricEnteConvenzList != null && !ricEnteConvenzList.isEmpty()) {
            List<OrgEnteSiam> entiConvenzCollegList = new ArrayList();
            // Recupero per ogni ente convenzionato gli enti ad esso collegati (escluso se stesso)
            for (OrgVRicEnteConvenz ricEnteConvenz : ricEnteConvenzList) {
                entiConvenzCollegList.addAll(
                        helper.getOrgEnteConvenzCollegUserAbilList(idUserIamCor, ricEnteConvenz.getIdEnteConvenz()));
            }
            // Aggiungo alla lista degli enti convenzionati gli enti ad essi collegati
            for (OrgEnteSiam enteConvenzColleg : entiConvenzCollegList) {
                ricEnteConvenzList.add(helper.findViewById(OrgVRicEnteConvenz.class,
                        BigDecimal.valueOf(enteConvenzColleg.getIdEnteSiam())));
            }
            try {
                ricEnteConvenzTableBean = (OrgVRicEnteConvenzTableBean) Transform
                        .entities2TableBean(ricEnteConvenzList);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return ricEnteConvenzTableBean;
    }

    /**
     * Recupera la lista dei nomi degli enti convenzionati cui lÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢utente \u00E8 abilitato con accordo
     * valido all'ambiente scelto
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idAmbienteEnteConvenz
     *            id ambiente/ente convenzionato
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

    /**
     * Recupera la lista dei nomi degli enti NON convenzionati cui lÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢utente \u00E8 abilitato
     *
     * @param idUserIam
     *            id user IAM
     * @param tiEnteNonConvenz
     *            tipo ente convenzionato
     *
     * @return table bean {@link OrgVRicEnteNonConvenzTableBean}
     */
    public OrgVRicEnteNonConvenzTableBean getOrgVRicEnteNonConvenzAbilTableBean(BigDecimal idUserIam,
            String tiEnteNonConvenz) {
        OrgVRicEnteNonConvenzTableBean ricEnteNonConvenzTableBean = new OrgVRicEnteNonConvenzTableBean();
        List<OrgVRicEnteNonConvenz> ricEnteNonConvenzList = helper.retrieveEntiNonConvenzAbilitati(idUserIam,
                tiEnteNonConvenz);
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

    public OrgVRicEnteNonConvenzTableBean getOrgVRicEnteNonConvenzAbilListTableBean(BigDecimal idUserIam,
            List<String> tiEnteNonConvenz) {
        OrgVRicEnteNonConvenzTableBean ricEnteNonConvenzTableBean = new OrgVRicEnteNonConvenzTableBean();
        List<OrgVRicEnteNonConvenz> ricEnteNonConvenzList = helper.retrieveEntiNonConvenzAbilitati(idUserIam,
                tiEnteNonConvenz);
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

    /**
     * Recupera la lista dei nomi degli enti NON convenzionati cui l'utente è abilitato
     *
     * @param idUserIam
     *            id user IAM
     * @param tiEnteNonConvenz
     *            tipo ente convenzionato
     *
     * @return table bean {@link OrgVRicEnteNonConvenzTableBean}
     */
    public OrgVRicEnteNonConvenzTableBean getOrgVRicEnteNonConvenzExcludeAbilTableBean(BigDecimal idUserIam,
            String tiEnteNonConvenz) {
        OrgVRicEnteNonConvenzTableBean ricEnteNonConvenzTableBean = new OrgVRicEnteNonConvenzTableBean();
        List<OrgVRicEnteNonConvenz> ricEnteNonConvenzList = helper.retrieveEntiNonConvenzAbilitati(idUserIam,
                tiEnteNonConvenz);
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

    /**
     * Recupera la lista dei nomi degli enti NON convenzionati cui lÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢utente \u00E8 abilitato
     *
     * @param idUserIam
     *            id user IAM
     * @param tiEnteNonConvenz
     *            tipo ente convenzionato
     *
     * @return table bean {@link OrgVRicEnteNonConvenzTableBean}
     */
    public OrgVRicEnteNonConvenzTableBean getOrgVRicEnteNonConvenzAbilAccordoValidoTableBean(BigDecimal idUserIam,
            String tiEnteNonConvenz) {
        OrgVRicEnteNonConvenzTableBean ricEnteNonConvenzTableBean = new OrgVRicEnteNonConvenzTableBean();
        List<OrgVRicEnteNonConvenz> ricEnteNonConvenzList = helper.retrieveEntiNonConvenzAbilitati(idUserIam,
                tiEnteNonConvenz);
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

    /**
     * Recupera la lista dei nomi degli enti convenzionati cui l'utente è abilitato con accordo valido dell'ente scelto,
     * id_ente_gestore uguale e id_ente_conserv uguali a quello definito sull'accordo dell'ente corrente
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param idAmbienteEnteConvenz
     *            id ambietne convenzionato
     *
     * @return table bean {@link OrgVRicEnteConvenzTableBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgVRicEnteConvenzTableBean getOrgVRicEnteConvenzAbilXEnteCapofilaTableBean(BigDecimal idUserIamCor,
            BigDecimal idEnteConvenz, BigDecimal idAmbienteEnteConvenz) throws ParerUserError {
        OrgVRicEnteConvenzTableBean ricEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        List<OrgVRicEnteConvenz> ricEnteConvenzList = helper.retrieveEntiConvenzAbilitatiXEnteCapofila(idUserIamCor,
                idEnteConvenz, idAmbienteEnteConvenz);
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

    /**
     * Recupera la lista dei nomi degli enti convenzionati cui lÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢utente \u00E8 abilitato
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idAmbienteEnteConvenz
     *            id ambiente convenzionato
     *
     * @return table bean {@link OrgVRicEnteConvenzTableBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgVRicEnteConvenzTableBean getOrgVRicEnteConvenzAbilXEnteCapofilaTableBean(BigDecimal idUserIamCor,
            BigDecimal idAmbienteEnteConvenz) throws ParerUserError {
        OrgVRicEnteConvenzTableBean ricEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        List<OrgVRicEnteConvenz> ricEnteConvenzList = helper.retrieveEntiConvenzAbilitatiXEnteCapofila(idUserIamCor,
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

    private OrgVRicEnteConvenzTableBean customEntities2EnteConvenzTableBean(List<OrgVRicEnteConvenz> ricEnteConvenzList)
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        OrgVRicEnteConvenzTableBean ricEnteConvenzTableBean = new OrgVRicEnteConvenzTableBean();
        for (OrgVRicEnteConvenz ente : ricEnteConvenzList) {
            OrgVRicEnteConvenzRowBean row = (OrgVRicEnteConvenzRowBean) Transform.entity2RowBean(ente);
            if (ente.getIdAmbitoTerrit() != null) {
                // Setto i campi dell'ambito territoriale
                OrgAmbitoTerrit ambito = helper.findById(OrgAmbitoTerrit.class, ente.getIdAmbitoTerrit());
                if (ambito.getTiAmbitoTerrit().equals(ConstOrgAmbitoTerrit.TiAmbitoTerrit.FORMA_ASSOCIATA.toString())) {
                    final OrgAmbitoTerrit orgAmbitoTerritProvincia = ambito.getOrgAmbitoTerrit();
                    final OrgAmbitoTerrit orgAmbitoTerritRegione = orgAmbitoTerritProvincia.getOrgAmbitoTerrit();
                    row.setString("cd_ambito_territ_forma_associata", ambito.getCdAmbitoTerrit());
                    row.setString("cd_ambito_territ_provincia", orgAmbitoTerritProvincia.getCdAmbitoTerrit());
                    row.setString("cd_ambito_territ_regione", orgAmbitoTerritRegione.getCdAmbitoTerrit());
                } else if (ambito.getTiAmbitoTerrit()
                        .equals(ConstOrgAmbitoTerrit.TiAmbitoTerrit.PROVINCIA.toString())) {
                    final OrgAmbitoTerrit orgAmbitoTerritRegione = ambito.getOrgAmbitoTerrit();
                    row.setString("cd_ambito_territ_provincia", ambito.getCdAmbitoTerrit());
                    row.setString("cd_ambito_territ_regione", orgAmbitoTerritRegione.getCdAmbitoTerrit());
                } else {
                    // REGIONE/STATO
                    row.setString("cd_ambito_territ_regione", ambito.getCdAmbitoTerrit());
                }
            }
            if (ente.getIdCategEnte() != null) {
                // Setto i campi della categoria ente
                OrgCategEnte categ = helper.findById(OrgCategEnte.class, ente.getIdCategEnte());
                row.setString("ds_categ_ente", categ.getDsCategEnte());
            }
            row.setString("attivo", ente.getEnteAttivo() != null ? ente.getEnteAttivo().toString() : null);
            if (ente.getDtCessazione() != null) {
                if (ente.getDtCessazione().before(new Date())) {
                    row.setString("cessato", "1");
                } else {
                    row.setString("cessato", "0");
                }
            }

            /* MEV 24397 - ARCHIVISTI DI RIFERIMENTO */
            List<OrgEnteArkRif> archivisti = helper.getOrgEnteArkRifList(ente.getIdEnteConvenz());
            if (archivisti != null && !archivisti.isEmpty()) {
                String archivistiStr = "";
                for (OrgEnteArkRif archivista : archivisti) {
                    archivistiStr = archivistiStr + archivista.getUsrUser().getNmUserid() + ";";
                }
                row.setString("archivista", archivistiStr);
            }

            ricEnteConvenzTableBean.add(row);
        }
        return ricEnteConvenzTableBean;
    }

    public OrgAmbienteEnteConvenzRowBean getOrgAmbienteEnteConvenzRowBean(BigDecimal idAmbienteEnteConvenz) {
        OrgAmbienteEnteConvenzRowBean ambienteEnteConvenzRowBean = new OrgAmbienteEnteConvenzRowBean();
        try {
            OrgAmbienteEnteConvenz ambienteEnteConvenz = helper.findById(OrgAmbienteEnteConvenz.class,
                    idAmbienteEnteConvenz);
            ambienteEnteConvenzRowBean = (OrgAmbienteEnteConvenzRowBean) Transform.entity2RowBean(ambienteEnteConvenz);
            if (ambienteEnteConvenz.getOrgEnteSiamByIdEnteConserv() != null) {
                ambienteEnteConvenzRowBean.setBigDecimal("id_ente_conserv",
                        BigDecimal.valueOf(ambienteEnteConvenz.getOrgEnteSiamByIdEnteConserv().getIdEnteSiam()));
                ambienteEnteConvenzRowBean.setString("nm_ente_conserv",
                        ambienteEnteConvenz.getOrgEnteSiamByIdEnteConserv().getNmEnteSiam());
            }
            if (ambienteEnteConvenz.getOrgEnteSiamByIdEnteGestore() != null) {
                ambienteEnteConvenzRowBean.setBigDecimal("id_ente_gestore",
                        BigDecimal.valueOf(ambienteEnteConvenz.getOrgEnteSiamByIdEnteGestore().getIdEnteSiam()));
                ambienteEnteConvenzRowBean.setString("nm_ente_gestore",
                        ambienteEnteConvenz.getOrgEnteSiamByIdEnteGestore().getNmEnteSiam());
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error("Errore durante il recupero dell'ambiente di un ente convenzionato "
                    + ExceptionUtils.getRootCauseMessage(e), e);
        }
        return ambienteEnteConvenzRowBean;
    }

    /**
     * Metodo di insert di un nuovo ambiente ente convenzionato
     *
     * @param param
     *            parametri per il logging
     * @param nmAmbienteEnteConvenz
     *            nome ambiente/ente convenzionato
     * @param dsAmbienteEnteConvenz
     *            descrizione ambiente/ente convenzionato
     * @param dtIniVal
     *            data inizio validita
     * @param dtFineVal
     *            data fine validita
     * @param idEnteConserv
     *            id ente
     * @param idEnteGestore
     *            id ente/gestore
     * @param parametriAmministrazione
     *            bean {@link IamParamApplicTableBean}
     * @param parametriConservazione
     *            bean {@link IamParamApplicTableBean}
     * @param parametriGestione
     *            bean {@link IamParamApplicTableBean}
     * @param dsNote
     *            nota
     *
     * @return id del nuovo ambiente ente
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveAmbienteEnteConvenzionato(LogParam param, String nmAmbienteEnteConvenz,
            String dsAmbienteEnteConvenz, String dsNote, Date dtIniVal, Date dtFineVal, BigDecimal idEnteConserv,
            BigDecimal idEnteGestore, IamParamApplicTableBean parametriAmministrazione,
            IamParamApplicTableBean parametriConservazione, IamParamApplicTableBean parametriGestione)
            throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio dell'ambiente ente convenzionato");
        // Controllo esistenza denominazione
        if (helper.getOrgAmbienteEnteConvenz(nmAmbienteEnteConvenz) != null) {
            throw new ParerUserError("Nome ambiente gi\u00E0 censito nel sistema");
        }
        Long idAmbienteEnteConvenz = null;
        try {
            OrgAmbienteEnteConvenz ambienteEnteConvenz = new OrgAmbienteEnteConvenz();
            ambienteEnteConvenz.setNmAmbienteEnteConvenz(nmAmbienteEnteConvenz);
            ambienteEnteConvenz.setDsAmbienteEnteConvenz(dsAmbienteEnteConvenz);
            ambienteEnteConvenz.setDsNote(dsNote);
            ambienteEnteConvenz.setDtIniVal(dtIniVal);
            ambienteEnteConvenz.setDtFineVal(dtFineVal);
            if (idEnteConserv != null) {
                OrgEnteSiam enteConserv = helper.findById(OrgEnteSiam.class, idEnteConserv);
                ambienteEnteConvenz.setOrgEnteSiamByIdEnteConserv(enteConserv);

                enteConserv.addOrgAmbienteEnteConvenzByIdEnteConserv(ambienteEnteConvenz);
            } else {
                ambienteEnteConvenz.setOrgEnteSiamByIdEnteConserv(null);
            }
            if (idEnteGestore != null) {
                OrgEnteSiam enteGestore = helper.findById(OrgEnteSiam.class, idEnteGestore);
                ambienteEnteConvenz.setOrgEnteSiamByIdEnteGestore(enteGestore);

                enteGestore.addOrgAmbienteEnteConvenzByIdEnteGestore(ambienteEnteConvenz);
            } else {
                ambienteEnteConvenz.setOrgEnteSiamByIdEnteGestore(null);
            }
            helper.insertEntity(ambienteEnteConvenz, true);

            // Gestione parametri
            for (IamParamApplicRowBean paramApplicRowBean : parametriAmministrazione) {
                if (paramApplicRowBean.getString("ds_valore_param_applic_ambiente_amm") != null
                        && !paramApplicRowBean.getString("ds_valore_param_applic_ambiente_amm").equals("")) {
                    insertIamValoreParamApplic(ambienteEnteConvenz, null, paramApplicRowBean.getIdParamApplic(),
                            "AMBIENTE", paramApplicRowBean.getString("ds_valore_param_applic_ambiente_amm"));
                }
            }
            for (IamParamApplicRowBean paramApplicRowBean : parametriConservazione) {
                if (paramApplicRowBean.getString("ds_valore_param_applic_ambiente_cons") != null
                        && !paramApplicRowBean.getString("ds_valore_param_applic_ambiente_cons").equals("")) {
                    insertIamValoreParamApplic(ambienteEnteConvenz, null, paramApplicRowBean.getIdParamApplic(),
                            "AMBIENTE", paramApplicRowBean.getString("ds_valore_param_applic_ambiente_cons"));
                }
            }
            for (IamParamApplicRowBean paramApplicRowBean : parametriGestione) {
                if (paramApplicRowBean.getString("ds_valore_param_applic_ambiente_gest") != null
                        && !paramApplicRowBean.getString("ds_valore_param_applic_ambiente_gest").equals("")) {
                    insertIamValoreParamApplic(ambienteEnteConvenz, null, paramApplicRowBean.getIdParamApplic(),
                            "AMBIENTE", paramApplicRowBean.getString("ds_valore_param_applic_ambiente_gest"));
                }
            }

            LOGGER.debug("Salvataggio dell'ambiente ente convenzionato completato");
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_AMB_ENTE_CONVENZ,
                    new BigDecimal(ambienteEnteConvenz.getIdAmbienteEnteConvenz()), param.getNomePagina());
            idAmbienteEnteConvenz = ambienteEnteConvenz.getIdAmbienteEnteConvenz();
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio dell'ambiente ente convenzionato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'ambiente ente convenzionato");
        }
        return idAmbienteEnteConvenz;
    }

    /**
     * Metodo di update di un ambiente ente convenzionato
     *
     * @param param
     *            parametri per il logging
     * @param idAmbienteEnteConvenzionato
     *            id ambiente/ente convenzionato
     * @param nmAmbienteEnteConvenz
     *            nome ambiente/ente convenzionato
     * @param dsAmbienteEnteConvenz
     *            descrizione ambiente/ente convenzionato
     * @param dsNote
     *            nota
     * @param dtIniVal
     *            data inizio validita
     * @param dtFineVal
     *            data fine validita
     * @param idEnteConserv
     *            id ente
     * @param parametriAmministrazione
     *            bean {@link IamParamApplicTableBean}
     * @param idEnteGestore
     *            id ente gestore
     * @param parametriConservazione
     *            bean {@link IamParamApplicTableBean}
     * @param parametriGestione
     *            bean {@link IamParamApplicTableBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveAmbienteEnteConvenzionato(LogParam param, BigDecimal idAmbienteEnteConvenzionato,
            String nmAmbienteEnteConvenz, String dsAmbienteEnteConvenz, String dsNote, Date dtIniVal, Date dtFineVal,
            BigDecimal idEnteConserv, BigDecimal idEnteGestore, IamParamApplicTableBean parametriAmministrazione,
            IamParamApplicTableBean parametriConservazione, IamParamApplicTableBean parametriGestione)
            throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio delle modifiche sull'ambiente ente convenzionato");
        OrgAmbienteEnteConvenz ambienteEnteConvenz = helper.findById(OrgAmbienteEnteConvenz.class,
                idAmbienteEnteConvenzionato);
        // Controllo che il nome, se modificato, non sia già presente
        if (!ambienteEnteConvenz.getNmAmbienteEnteConvenz().equals(nmAmbienteEnteConvenz)
                && helper.getOrgAmbienteEnteConvenz(nmAmbienteEnteConvenz) != null) {
            throw new ParerUserError("Attenzione: nel sistema \u00E8 giÃƒÆ’Ã‚Â  censito un altro ambiente con nome "
                    + nmAmbienteEnteConvenz);
        }
        try {
            ambienteEnteConvenz.setNmAmbienteEnteConvenz(nmAmbienteEnteConvenz);
            ambienteEnteConvenz.setDsAmbienteEnteConvenz(dsAmbienteEnteConvenz);
            ambienteEnteConvenz.setDsNote(dsNote);
            ambienteEnteConvenz.setDtIniVal(dtIniVal);
            ambienteEnteConvenz.setDtFineVal(dtFineVal);
            if (idEnteConserv != null) {
                OrgEnteSiam enteConserv = helper.findById(OrgEnteSiam.class, idEnteConserv);
                ambienteEnteConvenz.setOrgEnteSiamByIdEnteConserv(enteConserv);

                enteConserv.addOrgAmbienteEnteConvenzByIdEnteConserv(ambienteEnteConvenz);
            } else {
                ambienteEnteConvenz.setOrgEnteSiamByIdEnteConserv(null);
            }
            if (idEnteGestore != null) {
                OrgEnteSiam enteGestore = helper.findById(OrgEnteSiam.class, idEnteGestore);
                ambienteEnteConvenz.setOrgEnteSiamByIdEnteGestore(enteGestore);

                enteGestore.addOrgAmbienteEnteConvenzByIdEnteGestore(ambienteEnteConvenz);
            } else {
                ambienteEnteConvenz.setOrgEnteSiamByIdEnteGestore(null);
            }
            helper.getEntityManager().flush();

            // Gestione parametri amministrazione
            manageParametriPerAmbiente(parametriAmministrazione, "ds_valore_param_applic_ambiente_amm",
                    ambienteEnteConvenz);
            // Gestione parametri conservazione
            manageParametriPerAmbiente(parametriConservazione, "ds_valore_param_applic_ambiente_cons",
                    ambienteEnteConvenz);
            // Gestione parametri gestione
            manageParametriPerAmbiente(parametriGestione, "ds_valore_param_applic_ambiente_gest", ambienteEnteConvenz);

            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_AMB_ENTE_CONVENZ,
                    idAmbienteEnteConvenzionato, param.getNomePagina());
            LOGGER.debug("Salvataggio dell'ambiente ente convenzionato completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio dell'ambiente ente convenzionato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'ambiente ente convenzionato");
        }
    }

    public boolean existsEntiValidiAmbienteTableBean(BigDecimal idAmbienteEnteConvenz) {
        List<OrgEnteSiam> list = helper.getEntiValidiAmbiente(idAmbienteEnteConvenz);
        return !list.isEmpty();
    }

    public boolean existsUtentiAttiviAbilitatiAdAmbienteTableBean(BigDecimal idAmbienteEnteConvenz) {
        List<UsrUser> list = helper.getUtentiAttiviAbilitatiAdAmbiente(idAmbienteEnteConvenz);
        return !list.isEmpty();
    }

    public boolean existsUtentiAttiviAbilitatiAdAmbienteTableBean2(BigDecimal idAmbienteEnteConvenz) {
        List<UsrUser> list = helper.getUtentiAttiviAbilitatiAdAmbiente2(idAmbienteEnteConvenz);
        return !list.isEmpty();
    }

    /**
     * Metodo di eliminazione di un ambiente ente convenzionato
     *
     * @param param
     *            parametri per il logging
     * @param idAmbienteEnteConvenz
     *            id ambiente/ente convenzionato
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgAmbienteEnteConvenz(LogParam param, BigDecimal idAmbienteEnteConvenz) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'ambiente ente convenzionato " + idAmbienteEnteConvenz);
        OrgAmbienteEnteConvenz ambienteEnteConvenz = helper.findById(OrgAmbienteEnteConvenz.class,
                idAmbienteEnteConvenz);
        /* Controlla se sono presenti degli enti correlati */
        if (ambienteEnteConvenz.getOrgEnteSiams() != null && !ambienteEnteConvenz.getOrgEnteSiams().isEmpty()) {
            throw new ParerUserError("L'ambiente contiene degli enti convenzionati: eliminazione non consentita");
        }
        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_AMB_ENTE_CONVENZ,
                new BigDecimal(ambienteEnteConvenz.getIdAmbienteEnteConvenz()), param.getNomePagina());
        helper.removeEntity(ambienteEnteConvenz, true);
        LOGGER.debug("Eliminazione dell'ambiente ente convenzionato eseguita con successo");
    }

    /**
     * Metodo di insert di un nuovo incasso
     *
     * @param param
     *            parametri per il logging
     * @param idFatturaEnte
     *            id fattura ente
     * @param pagamFatturaEnteRowBean
     *            row bean {@link OrgPagamFatturaEnteRowBean}
     * @param tiStatoFatturaEnte
     *            tipo stato fatture ente
     *
     * @return id del nuovo incasso
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long insertIncasso(LogParam param, BigDecimal idFatturaEnte,
            OrgPagamFatturaEnteRowBean pagamFatturaEnteRowBean,
            ConstOrgStatoFatturaEnte.TiStatoFatturaEnte tiStatoFatturaEnte) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio dell'incasso");

        // Recupero la fattura
        OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnte);

        // Recupero lo stato fattura corrente
        OrgStatoFatturaEnte statoFatturaEnteCorrente = helper.findById(OrgStatoFatturaEnte.class,
                fatturaEnte.getIdStatoFatturaEnteCor());

        // Recupero l'ultimo progressivo incasso disponibile per la fattura in questione
        BigDecimal lastProgr = helper.getLastProgressivoIncasso(idFatturaEnte);
        lastProgr = lastProgr.add(BigDecimal.ONE);

        Long idPagamFatturaEnte = null;
        try {
            OrgPagamFatturaEnte pagamFatturaEnte = new OrgPagamFatturaEnte();

            pagamFatturaEnte.setOrgFatturaEnte(fatturaEnte);
            pagamFatturaEnte.setPgPagam(lastProgr);
            pagamFatturaEnte.setCdProvvPagam(pagamFatturaEnteRowBean.getCdProvvPagam());
            pagamFatturaEnte.setCdRevPagam(pagamFatturaEnteRowBean.getCdRevPagam());
            pagamFatturaEnte.setDtPagam(pagamFatturaEnteRowBean.getDtPagam());
            pagamFatturaEnte.setImPagam(pagamFatturaEnteRowBean.getImPagam());
            pagamFatturaEnte.setNtPagam(pagamFatturaEnteRowBean.getNtPagam());
            // TODO:controlla non sia nullo
            fatturaEnte.getOrgPagamFatturaEntes().add(pagamFatturaEnte);
            // Inserisco un nuovo stato corrente della fattura, IN CASO SIA CAMBIATO
            if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte().equals(tiStatoFatturaEnte.getDescrizione())) {
                OrgStatoFatturaEnte statoFatturaEnte = new OrgStatoFatturaEnte();
                statoFatturaEnte.setDtRegStatoFatturaEnte(new Date());
                statoFatturaEnte.setOrgFatturaEnte(fatturaEnte);
                statoFatturaEnte.setTiStatoFatturaEnte(tiStatoFatturaEnte.getDescrizione());
                fatturaEnte.getOrgStatoFatturaEntes().add(statoFatturaEnte);
                helper.insertEntity(statoFatturaEnte, true);
                // Setto l'id dello stato fattura corrente nella tabella delle fatture
                fatturaEnte.setIdStatoFatturaEnteCor(new BigDecimal(statoFatturaEnte.getIdStatoFatturaEnte()));
            }
            helper.insertEntity(pagamFatturaEnte, true);
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA,
                    new BigDecimal(pagamFatturaEnte.getOrgFatturaEnte().getIdFatturaEnte()), param.getNomePagina());
            LOGGER.debug("Salvataggio dell'incasso completato");
            idPagamFatturaEnte = pagamFatturaEnte.getIdPagamFatturaEnte();
        } catch (Exception ex) {
            LOGGER.error(
                    "Errore imprevisto durante il salvataggio dell'incasso : " + ExceptionUtils.getRootCauseMessage(ex),
                    ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'incasso");
        }
        return idPagamFatturaEnte;
    }

    /**
     * Metodo di update di un incasso
     *
     * @param param
     *            parametri per il logging
     * @param idFatturaEnte
     *            id fattura ente
     * @param pagamFatturaEnteRowBean
     *            row bean {@link OrgPagamFatturaEnteRowBean}
     * @param tiStatoFatturaEnte
     *            tipo stato fattura ente
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateIncasso(LogParam param, BigDecimal idFatturaEnte,
            OrgPagamFatturaEnteRowBean pagamFatturaEnteRowBean,
            ConstOrgStatoFatturaEnte.TiStatoFatturaEnte tiStatoFatturaEnte) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio dell'incasso");
        try {
            // Recupero la fattura
            OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnte);

            // Recupero lop stato fattura corrente
            OrgStatoFatturaEnte statoFatturaEnteCorrente = helper.findById(OrgStatoFatturaEnte.class,
                    fatturaEnte.getIdStatoFatturaEnteCor());

            OrgPagamFatturaEnte pagamFatturaEnte = helper.findById(OrgPagamFatturaEnte.class,
                    pagamFatturaEnteRowBean.getIdPagamFatturaEnte());
            pagamFatturaEnte.setCdProvvPagam(pagamFatturaEnteRowBean.getCdProvvPagam());
            pagamFatturaEnte.setCdRevPagam(pagamFatturaEnteRowBean.getCdRevPagam());
            pagamFatturaEnte.setDtPagam(pagamFatturaEnteRowBean.getDtPagam());
            pagamFatturaEnte.setImPagam(pagamFatturaEnteRowBean.getImPagam());
            pagamFatturaEnte.setNtPagam(pagamFatturaEnteRowBean.getNtPagam());
            // Inserisco un nuovo stato corrente della fattura, IN CASO SIA CAMBIATO
            if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte().equals(tiStatoFatturaEnte.getDescrizione())) {
                OrgStatoFatturaEnte statoFatturaEnte = new OrgStatoFatturaEnte();
                statoFatturaEnte.setDtRegStatoFatturaEnte(new Date());
                statoFatturaEnte.setOrgFatturaEnte(fatturaEnte);
                statoFatturaEnte.setTiStatoFatturaEnte(tiStatoFatturaEnte.getDescrizione());
                fatturaEnte.getOrgStatoFatturaEntes().add(statoFatturaEnte);
                helper.insertEntity(statoFatturaEnte, true);
                // Setto l'id dello stato fattura corrente nella tabella delle fatture
                fatturaEnte.setIdStatoFatturaEnteCor(new BigDecimal(statoFatturaEnte.getIdStatoFatturaEnte()));
            }
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA, idFatturaEnte,
                    param.getNomePagina());
            LOGGER.debug("Salvataggio dell'incasso completato");
        } catch (Exception ex) {
            LOGGER.error(
                    "Errore imprevisto durante il salvataggio dell'incasso : " + ExceptionUtils.getRootCauseMessage(ex),
                    ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio dell'incasso");
        }
    }

    /**
     * Metodo di eliminazione di un incasso
     *
     * @param param
     *            parametri per il logging
     * @param idFatturaEnte
     *            id fattura ente
     * @param idPagamFatturaEnte
     *            id pagamento
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgPagamFatturaEnte(LogParam param, BigDecimal idFatturaEnte, BigDecimal idPagamFatturaEnte)
            throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'incasso");
        try {
            // Recupero la fattura
            OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnte);

            OrgPagamFatturaEnte pagamFatturaEnte = helper.findById(OrgPagamFatturaEnte.class, idPagamFatturaEnte);
            helper.removeEntity(pagamFatturaEnte, true);
            // Aggiorno lo stato della fattura in base ai nuovi totali
            OrgVVisFattura visFattura = helper.getPrimoOrgVVisFatturaEnte(idFatturaEnte);
            BigDecimal importoPagamento = visFattura.getImTotDaPagare() != null ? visFattura.getImTotDaPagare()
                    : BigDecimal.ZERO;
            BigDecimal importoIncassato = visFattura.getImTotPagam() != null ? visFattura.getImTotPagam()
                    : BigDecimal.ZERO;
            // Recupero lop stato fattura corrente
            OrgStatoFatturaEnte statoFatturaEnteCorrente = helper.findById(OrgStatoFatturaEnte.class,
                    fatturaEnte.getIdStatoFatturaEnteCor());

            if (importoIncassato.compareTo(importoPagamento) > 0) {
                // Significa che sono in stato PAGATA, e rimango tale. Non faccio nulla, ma riporto per chiarezza
            } else if (importoIncassato.compareTo(BigDecimal.ZERO) == 0) {
                if (statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                        .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione())) {
                    // Se la fattura ha stato SOLLECITATA, rimane tale. Non faccio nulla, ma riporto per chiarezza
                } else {
                    if (visFattura.getDtScadFattura() != null) {
                        // Not before significa after estremo incluso
                        if (!visFattura.getDtScadFattura().before(new Date())) {
                            if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                                    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())) {
                                insertStatoFatturaEnte(fatturaEnte,
                                        ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione());
                            }
                        } else {
                            if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                                    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())) {
                                insertStatoFatturaEnte(fatturaEnte,
                                        ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione());
                            }
                        }
                    } else {
                        if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                                .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())) {
                            insertStatoFatturaEnte(fatturaEnte,
                                    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione());
                        }
                    }
                }
            } else if (importoIncassato.compareTo(BigDecimal.ZERO) != 0
                    && importoIncassato.compareTo(importoPagamento) < 0) {
                if (statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                        .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione())) {
                    // Se la fattura ha stato SOLLECITATA, rimane tale. Non faccio nulla, ma riporto per chiarezza
                } else {
                    if (visFattura.getDtScadFattura() != null) {
                        // Not before significa after estremo incluso
                        if (!visFattura.getDtScadFattura().before(new Date())) {
                            if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte().equals(
                                    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione())) {
                                insertStatoFatturaEnte(fatturaEnte,
                                        ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE
                                                .getDescrizione());
                            }
                        } else {
                            if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                                    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())) {
                                insertStatoFatturaEnte(fatturaEnte,
                                        ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione());
                            }
                        }
                    } else {
                        if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte().equals(
                                ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione())) {
                            insertStatoFatturaEnte(fatturaEnte,
                                    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione());
                        }
                    }
                }
            }
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA, idFatturaEnte,
                    param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'incasso "
                    + ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError("Eccezione imprevista nell'eliminazione dell'incasso");
        }
    }

    /**
     * Metodo di update di una fattura
     *
     * @param param
     *            parametri per il logging
     * @param tiStatoFatturaEnteNew
     *            tipo stato fattura
     * @param dataModifica
     *            data modifica
     * @param fatturaBean
     *            bean {@link FatturaBean}
     * @param noteModifica
     *            note
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveFattura(LogParam param, FatturaBean fatturaBean, String tiStatoFatturaEnteNew, Date dataModifica,
            String noteModifica) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio delle modifiche sulla fattura");
        OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, fatturaBean.getIdFatturaEnte());
        try {
            // Eseguo l'update della fattura
            setDatiFattura(fatturaEnte, fatturaBean);
            // Eventuale registrazione delle modifiche
            if (dataModifica != null) {
                OrgModifFatturaEnte modifFatturaEnte = new OrgModifFatturaEnte();
                modifFatturaEnte.setDtModifFatturaEnte(dataModifica);
                modifFatturaEnte.setNtModifFatturaEnte(noteModifica);
                modifFatturaEnte.setOrgFatturaEnte(fatturaEnte);
                helper.insertEntity(modifFatturaEnte, true);
                if (fatturaEnte.getOrgModifFatturaEntes() == null) {
                    fatturaEnte.setOrgModifFatturaEntes(new ArrayList());
                }
                fatturaEnte.getOrgModifFatturaEntes().add(modifFatturaEnte);
            }
            // Eventuale cambio di stato
            if (tiStatoFatturaEnteNew != null) {
                OrgStatoFatturaEnte statoFatturaEnte = new OrgStatoFatturaEnte();
                statoFatturaEnte.setDtRegStatoFatturaEnte(new Date());
                statoFatturaEnte.setOrgFatturaEnte(fatturaEnte);
                statoFatturaEnte.setTiStatoFatturaEnte(tiStatoFatturaEnteNew);
                fatturaEnte.getOrgStatoFatturaEntes().add(statoFatturaEnte);
                helper.insertEntity(statoFatturaEnte, true);
                // Setto l'id dello stato fattura corrente nella tabella delle fatture
                fatturaEnte.setIdStatoFatturaEnteCor(new BigDecimal(statoFatturaEnte.getIdStatoFatturaEnte()));
            }

            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA,
                    fatturaBean.getIdFatturaEnte(), param.getNomePagina());
            LOGGER.debug("Salvataggio della fattura completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio della fattura : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio della fattura");
        }
    }

    private void setDatiFattura(OrgFatturaEnte fatturaEnte, FatturaBean fatturaBean) {
        // Imposta i campi della fattura modificabili
        fatturaEnte.setAaFattura(fatturaBean.getAaFattura());
        fatturaEnte.setPgFattura(fatturaBean.getPgFattura());
        fatturaEnte.setCdFattura(fatturaBean.getCdFattura());
        fatturaEnte.setNmEnte(fatturaBean.getNmEnte());
        fatturaEnte.setNmStrut(fatturaBean.getNmStrut());
        fatturaEnte.setCdRegistroEmisFattura(fatturaBean.getCdRegistroEmisFattura());
        fatturaEnte.setAaEmissFattura(fatturaBean.getAaEmissFattura());
        fatturaEnte.setCdEmisFattura(fatturaBean.getCdEmisFattura());
        fatturaEnte.setDtEmisFattura(fatturaBean.getDtEmisFattura());
        fatturaEnte.setDtScadFattura(fatturaBean.getDtScadFattura());
        fatturaEnte.setCdFatturaSap(fatturaBean.getCdFatturaSap());
        fatturaEnte.setNtEmisFattura(fatturaBean.getNtEmisFattura());
        fatturaEnte.setImTotFattura(fatturaBean.getImTotFattura());
        fatturaEnte.setImTotDaPagare(fatturaBean.getImTotDaPagare());
        fatturaEnte.setImTotIva(fatturaBean.getImTotIva());
        fatturaEnte.setCdRegistroEmisNotaCredito(fatturaBean.getCdRegistroEmisNotaCredito());
        fatturaEnte.setAaEmissNotaCredito(fatturaBean.getAaEmissNotaCredito());
        fatturaEnte.setCdEmisNotaCredito(fatturaBean.getCdEmisNotaCredito());
        fatturaEnte.setDtEmisNotaCredito(fatturaBean.getDtEmisNotaCredito());
        fatturaEnte.setFlDaRiemis(fatturaBean.getFlDaRiemis());
        fatturaEnte.setNtEmisNotaCredito(fatturaBean.getNtEmisNotaCredito());
    }

    public boolean isUnitaDocVersataInSacer(BigDecimal idStrut, String cdRegistroKeyUnitaDoc, BigDecimal aaKeyUnitaDoc,
            String cdKeyUnitaDoc) throws ParerUserError {
        return helper.isUnitaDocVersataInSacer(idStrut, cdRegistroKeyUnitaDoc, aaKeyUnitaDoc, cdKeyUnitaDoc);
    }

    /**
     * Recupera da SACER i registri connessi al tipo ud passato in input
     *
     * @param idTipoUnitaDoc
     *            id tipo unita doc
     *
     * @return il tableBean dei registri
     *
     * @throws ParerUserError
     *             errore generico
     */
    public BaseTable getRegistriConnessiTableBean(BigDecimal idTipoUnitaDoc) throws ParerUserError {
        BaseTable registriConnessiTableBean = new BaseTable();
        List<DecVLisTiUniDocAms> registriConnessiList = helper.retrieveRegistriConnessiList(idTipoUnitaDoc);
        try {
            for (DecVLisTiUniDocAms registroConnesso : registriConnessiList) {
                BaseRow registroConnessoRowBean = new BaseRow();
                registroConnessoRowBean.setBigDecimal("id_registro_unita_doc",
                        registroConnesso.getIdRegistroUnitaDoc());
                registroConnessoRowBean.setString("cd_registro_unita_doc", registroConnesso.getCdRegistroUnitaDoc());
                registriConnessiTableBean.add(registroConnessoRowBean);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("Errore durante il recupero dei registri connessi al tipo ud "
                    + ExceptionUtils.getRootCauseMessage(e), e);
            throw new ParerUserError("Errore durante il recupero dei registri connessi al tipo ud");
        }
        return registriConnessiTableBean;
    }

    /**
     * Metodo di eliminazione di un utente referente dell'ente
     *
     * @param param
     *            parametri per il logging
     * @param idEnteUserRif
     *            id riferimento utente
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgEnteUserRif(LogParam param, BigDecimal idEnteUserRif) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'utente referente dall'ente convenzionato");
        try {
            OrgEnteUserRif enteUserRif = helper.findById(OrgEnteUserRif.class, idEnteUserRif);
            helper.removeEntity(enteUserRif, true);
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    new BigDecimal(enteUserRif.getOrgEnteSiam().getIdEnteSiam()), param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'utente referente dall'ente convenzionato ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di eliminazione di un collegamento dell'ente
     *
     * @param param
     *            parametri per il logging
     * @param idCollegEntiConvenz
     *            id collegamenti enti convenzionati
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param appartCollegEntiSet
     *            lista distinta enti collegati
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgCollegEntiConvenz(LogParam param, BigDecimal idCollegEntiConvenz, BigDecimal idEnteConvenz,
            Set<BigDecimal> appartCollegEntiSet) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del collegamento dall'ente convenzionato");
        try {
            OrgCollegEntiConvenz collegEntiConvenz = helper.findById(OrgCollegEntiConvenz.class, idCollegEntiConvenz);
            // List<ObjectsToLogBefore> listaOggettiDaLoggare = sacerLogEjb.logBefore(param.getTransactionLogContext(),
            // param.getNomeApplicazione(), param.getNomeUtente(), param.getNomeAzione(),
            // SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO, idEnteConvenz, param.getNomePagina());

            helper.removeEntity(collegEntiConvenz, true);
            if (!appartCollegEntiSet.isEmpty()) {
                /* TODO QUA VA LENTO */
                sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                        param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                        appartCollegEntiSet, param.getNomePagina());
            }
            // sacerLogEjb.logAfter(param.getTransactionLogContext(), param.getNomeApplicazione(),
            // param.getNomeUtente(),
            // param.getNomeAzione(), listaOggettiDaLoggare, param.getNomePagina());

        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del collegamento dall'ente convenzionato ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di eliminazione di un collegamento
     *
     * @param param
     *            parametri per logging
     * @param idCollegEntiConvenz
     *            id collegamento enti convenzionati
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgCollegEntiConvenz(LogParam param, BigDecimal idCollegEntiConvenz) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del collegamento");
        try {
            OrgCollegEntiConvenz collegEntiConvenz = helper.findById(OrgCollegEntiConvenz.class, idCollegEntiConvenz);
            // List<ObjectsToLogBefore> listaOggettiDaLoggare = sacerLogEjb.logBefore(param.getTransactionLogContext(),
            // param.getNomeApplicazione(), param.getNomeUtente(), param.getNomeAzione(),
            // SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO, idEnteConvenz, param.getNomePagina());
            helper.removeEntity(collegEntiConvenz, true);
            // sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
            // param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
            // idEnteConvenz, param.getNomePagina());
            // sacerLogEjb.logAfter(param.getTransactionLogContext(), param.getNomeApplicazione(),
            // param.getNomeUtente(), param.getNomeAzione(), listaOggettiDaLoggare, param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del collegamento ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di eliminazione di un'associazione tra ente produttore e collegamento
     *
     * @param param
     *            paramentri per il logging
     * @param idAppartCollegEnti
     *            id collegamento enti convenzionati
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param idUserIamCor
     *            id dell'utente corrente che effettua la cancellazione
     *
     * @throws ParerUserError
     *             errore generico
     */
    public void deleteOrgAppartCollegEnti(LogParam param, BigDecimal idAppartCollegEnti, BigDecimal idEnteConvenz,
            long idUserIamCor) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione dell'associazione tra ente e collegamento");
        try {
            OrgAppartCollegEnti appartCollegEnti = helper.findById(OrgAppartCollegEnti.class, idAppartCollegEnti);
            if (appartCollegEnti.getOrgEnteSiam().equals(appartCollegEnti.getOrgCollegEntiConvenz().getOrgEnteSiam())) {
                appartCollegEnti.getOrgCollegEntiConvenz().setOrgEnteSiam(null);
            }

            /* GESTIONE LOGGING COLLEGAMENTI IN MANIERA ASINCRONA */
            List<ObjectsToLogBefore> listaOggettiDaLoggare = sacerLogEjb.logBefore(param.getTransactionLogContext(),
                    param.getNomeApplicazione(), param.getNomeUtente(), param.getNomeAzione(),
                    SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    BigDecimal.valueOf(appartCollegEnti.getOrgCollegEntiConvenz().getIdCollegEntiConvenz()),
                    param.getNomePagina());

            Object[] objArr = helper.getDatiLogByScript();

            writeLogEventoByScriptEnteConvenz(objArr, listaOggettiDaLoggare, param, idUserIamCor, idEnteConvenz);

            helper.removeEntity(appartCollegEnti, true);

            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    idEnteConvenz, param.getNomePagina());

        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione dell'associazione tra ente e collegamento ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    /**
     * Metodo di inserimento di un'associazione tra ente e collegamento
     *
     * @param param
     *            parametri per il logging
     * @param idUserIamCor
     *            id user IAM
     * @param idCollegEntiConvenz
     *            id enti convenzionati
     * @param idEnteConvenzAppart
     *            id ente appartenenza convenzionato
     * @param dtIniAppart
     *            data inizio appartenenza
     * @param dtFinAppart
     *            data fine appartenenza
     *
     * @return pk
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long insertOrgAppartCollegEnti(LogParam param, long idUserIamCor, BigDecimal idCollegEntiConvenz,
            BigDecimal idEnteConvenzAppart, Date dtIniAppart, Date dtFinAppart) throws ParerUserError {

        // Controllo esistenza associazione
        if (helper.existsEnteCollegatoPerCollegamento(idCollegEntiConvenz, idEnteConvenzAppart)) {
            throw new ParerUserError("Attenzione: associazione ente già presente");
        }

        OrgCollegEntiConvenz collegEntiConvenz = helper.findByIdWithLock(OrgCollegEntiConvenz.class,
                idCollegEntiConvenz);
        OrgEnteSiam enteConvenz = helper.findByIdWithLock(OrgEnteSiam.class, idEnteConvenzAppart);

        OrgAppartCollegEnti appartCollegEnti = new OrgAppartCollegEnti();
        appartCollegEnti.setOrgCollegEntiConvenz(collegEntiConvenz);
        appartCollegEnti.setOrgEnteSiam(enteConvenz);
        appartCollegEnti.setDtIniVal(dtIniAppart);
        appartCollegEnti.setDtFinVal(dtFinAppart);

        helper.insertEntity(appartCollegEnti, true);

        enteConvenz.getOrgAppartCollegEntis().add(appartCollegEnti);

        collegEntiConvenz.getOrgAppartCollegEntis().add(appartCollegEnti);

        // Aggiunta delle abilitazioni agli enti convenzionati collegati all'ente a cui appartiene
        // l'utente (se non già presenti) a cui l'amministratore è abilitato
        userHelper.aggiungiAbilEnteColleg(idUserIamCor, idCollegEntiConvenz.longValue(),
                idEnteConvenzAppart.longValue());

        /* GESTIONE LOGGING COLLEGAMENTI IN MANIERA ASINCRONA */
        // Vengono ricavati gli enti convenzionati presenti nel collegamento tramite la query in APL_QUERY_TIPO_OGGETTO
        // di tipo SELEZIONE_OGG_BEFORE
        List<ObjectsToLogBefore> listaOggettiDaLoggare = sacerLogEjb.logBefore(param.getTransactionLogContext(),
                param.getNomeApplicazione(), param.getNomeUtente(), param.getNomeAzione(),
                SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO, idCollegEntiConvenz, param.getNomePagina());

        Object[] objArr = helper.getDatiLogByScript();

        writeLogEventoByScriptEnteConvenz(objArr, listaOggettiDaLoggare, param, idUserIamCor, idEnteConvenzAppart);

        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                idEnteConvenzAppart, param.getNomePagina());

        return appartCollegEnti.getIdAppartCollegEnti();
    }

    /**
     * Scrive in LOG_EVENTO_BY_SCRIPT gli enti convenzionati su cui effettuare logging asincrono
     *
     * @param objArr
     *            array con le informazioni relative ad azione e tipo oggetto
     * @param listaOggettiDaLoggare
     *            elenco enti convenzionati da loggare
     * @param param
     * @param idUserIamCor
     *            utente corrente che effettua l'operazione e per il quale verrà ricavato il Ruolo agente da mostrare in
     *            Log eventi
     * @param idEnteConvenzAppart
     *            ente da escludere dal logging asincrono in quanto già registrato online
     *
     * @throws ParerUserError
     */
    private void writeLogEventoByScriptEnteConvenz(Object[] objArr, List<ObjectsToLogBefore> listaOggettiDaLoggare,
            LogParam param, long idUserIamCor, BigDecimal idEnteConvenzAppart) throws ParerUserError {
        if (objArr != null) {
            UsrUser utente = helper.findById(UsrUser.class, idUserIamCor);
            AplAzionePagina azione = null;
            try {
                azione = userHelper.getAplAzionePagina(param.getNomeAzione(), param.getNomePagina(),
                        param.getNomeApplicazione());
            } catch (Exception e) {
                throw new ParerUserError(e.getMessage());
            }

            for (ObjectsToLogBefore obj : listaOggettiDaLoggare) {
                for (BigDecimal idObj : obj.getIdOggetto()) {
                    if (idObj.compareTo(idEnteConvenzAppart) != 0) {
                        LogEventoByScript log1 = new LogEventoByScript();
                        log1.setDsMotivoScript("LOG ASINCRONO");
                        log1.setDtRegEvento(new Timestamp(new Date().getTime()));
                        log1.setIdAgente(BigDecimal.valueOf(utente.getLogAgente().getIdAgente()));
                        log1.setIdApplic((BigDecimal) objArr[3]);
                        // devo mettere idazioncompsw comunque perchè il campo è not null
                        log1.setIdAzioneCompSw((BigDecimal) objArr[2]);
                        log1.setIdAzionePagina(new BigDecimal(azione.getIdAzionePagina()));
                        log1.setIdOggetto(idObj);
                        log1.setIdTipoOggetto((BigDecimal) objArr[0]);
                        log1.setTiRuoloAgenteEvento("executing program");
                        log1.setTiRuoloOggettoEvento("outcome");
                        log1.setIdTransazione(param.getTransactionLogContext().getTransactionId());
                        helper.getEntityManager().persist(log1);
                    }
                }
            }
        }
    }

    /**
     * Metodo di update di un assoziazione tra ente e collegamento
     *
     * @param param
     *            parametri per il logging
     * @param idAppartCollegEnti
     *            id appartenenza enti collegati
     * @param idEnteConvenzAppart
     *            id enti convenzionati
     * @param dtIniAppart
     *            data inizio appartenenza
     * @param dtFinAppart
     *            data fine appartenenza
     *
     * @return pk
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long updateAppartenenzaCollegamento(LogParam param, BigDecimal idAppartCollegEnti,
            BigDecimal idEnteConvenzAppart, Date dtIniAppart, Date dtFinAppart) throws ParerUserError {

        OrgAppartCollegEnti appartCollegEnti = helper.findById(OrgAppartCollegEnti.class, idAppartCollegEnti);
        appartCollegEnti.setDtIniVal(dtIniAppart);
        appartCollegEnti.setDtFinVal(dtFinAppart);

        // Persisto le modifiche
        helper.mergeEntity(appartCollegEnti);
        helper.getEntityManager().flush();

        List<ObjectsToLogBefore> listaOggettiDaLoggare = sacerLogEjb.logBefore(param.getTransactionLogContext(),
                param.getNomeApplicazione(), param.getNomeUtente(), param.getNomeAzione(),
                SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO, idEnteConvenzAppart, param.getNomePagina());
        sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                idEnteConvenzAppart, param.getNomePagina());
        sacerLogEjb.logAfter(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                param.getNomeAzione(), listaOggettiDaLoggare, param.getNomePagina());

        return appartCollegEnti.getIdAppartCollegEnti();
    }

    /**
     * Metodo di update di un servizio fatturato
     *
     * @param param
     *            parametri per il logging
     * @param idFatturaEnte
     *            id fattura/ente
     * @param idServizioFattura
     *            id servizio fattura/ente
     * @param nmServizioFattura
     *            nome servizio fattura
     * @param cdIva
     *            codice iva
     * @param imNetto
     *            netto
     * @param imIva
     *            iva
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateServizioFatturato(LogParam param, BigDecimal idFatturaEnte, BigDecimal idServizioFattura,
            String nmServizioFattura, BigDecimal imNetto, String cdIva, BigDecimal imIva) throws ParerUserError {
        LOGGER.debug("Eseguo la modifica del servizio fatturato");
        try {
            // Recupero il servizio fatturato
            OrgServizioFattura servizioFattura = helper.findById(OrgServizioFattura.class, idServizioFattura);
            // Modifico i campi
            servizioFattura.setNmServizioFattura(nmServizioFattura);
            servizioFattura.setImServizioFattura(imNetto);
            servizioFattura.setImIva(imIva);
            OrgCdIva cdIvaEntity = helper.getOrgCdIvaByCdIva(cdIva);
            servizioFattura.setOrgCdIva(cdIvaEntity);
            // Persisto le modifiche
            helper.getEntityManager().flush();
            // Ricalcolo i totali tramite vista
            OrgVCalcTotFatt calcTotFatt = helper.findViewById(OrgVCalcTotFatt.class, idFatturaEnte);
            OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnte);
            fatturaEnte.setImTotFattura(calcTotFatt.getImTotFattura());
            fatturaEnte.setImTotDaPagare(calcTotFatt.getImTotDaPagare());
            fatturaEnte.setImTotIva(calcTotFatt.getImTotIva());
            // Persisto le modifiche
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA, idFatturaEnte,
                    param.getNomePagina());
            LOGGER.debug("Modifica del servizio fatturato completata con successo");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del servizio fatturato : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del servizio fatturato");
        }
    }

    public Object[] getFileGestioneAccordo(BigDecimal idGestAccordo) {
        OrgGestioneAccordo gestioneAccordo = helper.findById(OrgGestioneAccordo.class, idGestAccordo);
        Object[] acc = new Object[2];
        acc[0] = gestioneAccordo.getNmFileGestAccordo();
        acc[1] = gestioneAccordo.getBlGestAccordo();
        return acc;
    }

    public boolean existsStatoFattura(BigDecimal idFatturaEnte, String tiStatoFatturaEnte) {
        List<OrgStatoFatturaEnte> statoFatturaEnteList = helper.getOrgStatoFatturaEnte(idFatturaEnte,
                tiStatoFatturaEnte);
        return !statoFatturaEnteList.isEmpty();
    }

    /**
     * Metodo di eliminazione di un servizio fatturato
     *
     * @param param
     *            parametri per il logging
     * @param idFatturaEnte
     *            id fattura/ente
     * @param idServizioFattura
     *            id servizio fattura
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgServizioFattura(LogParam param, BigDecimal idFatturaEnte, BigDecimal idServizioFattura)
            throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del servizio fatturato");
        try {
            // Cancello il servizio fatturato
            OrgServizioFattura servizioFattura = helper.findById(OrgServizioFattura.class, idServizioFattura);
            helper.removeEntity(servizioFattura, true);
            // Aggiorno gli importi della fattura
            OrgVCalcTotFatt calcTotFatt = helper.findViewById(OrgVCalcTotFatt.class, idFatturaEnte);
            OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnte);
            fatturaEnte.setImTotFattura(
                    calcTotFatt.getImTotFattura() != null ? calcTotFatt.getImTotFattura() : BigDecimal.ZERO);
            fatturaEnte.setImTotDaPagare(
                    calcTotFatt.getImTotDaPagare() != null ? calcTotFatt.getImTotDaPagare() : BigDecimal.ZERO);
            fatturaEnte.setImTotIva(calcTotFatt.getImTotIva() != null ? calcTotFatt.getImTotIva() : BigDecimal.ZERO);
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del servizio fatturato ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    public int calcolaFattureProvvisorie(LogParam param, BigDecimal idEnteConvenz, BigDecimal annoTestata,
            BigDecimal annoFattServizi, String nmAmbiente, String nmEnte, String nmStrut) {
        List<OrgVCreaFatturaByAnno> creaFatturaByAnnoList = helper.getOrgVCreaFatturaByAnno(idEnteConvenz, annoTestata,
                annoFattServizi);
        Long idFatturaProvvisoria = null;
        int numFattCalcolate = 0;

        for (OrgVCreaFatturaByAnno creaFatturaByAnno : creaFatturaByAnnoList) {
            OrgEnteSiam enteSiam = helper.findById(OrgEnteSiam.class, idEnteConvenz);

            // Eseguo il salvataggio della fattura
            BigDecimal progr = helper.getPgFattura(idEnteConvenz, annoTestata);
            OrgFatturaEnte fatturaProvvisoria = new OrgFatturaEnte();
            fatturaProvvisoria.setOrgEnteSiam(enteSiam);
            enteSiam.addOrgFatturaEnte(fatturaProvvisoria);
            fatturaProvvisoria.setAaFattura(creaFatturaByAnno.getOrgVCreaFatturaByAnnoId().getAaFattura());
            fatturaProvvisoria.setPgFattura(progr);
            fatturaProvvisoria.setImTotFattura(creaFatturaByAnno.getImTotFattura());
            fatturaProvvisoria.setImTotDaPagare(creaFatturaByAnno.getImTotDaPagare());
            fatturaProvvisoria.setImTotIva(creaFatturaByAnno.getImTotIva());
            fatturaProvvisoria.setNmEnte(nmEnte);
            fatturaProvvisoria.setNmStrut(nmStrut);
            fatturaProvvisoria.setCdFattura(enteSiam.getCdEnteConvenz() + "/" + annoTestata + "/" + progr);
            // Setto la nuova fattura a stato CALCOLATA
            OrgStatoFatturaEnte statoFatturaEnteNuovaEmissione = new OrgStatoFatturaEnte();
            statoFatturaEnteNuovaEmissione.setDtRegStatoFatturaEnte(new Date());
            statoFatturaEnteNuovaEmissione.setOrgFatturaEnte(fatturaProvvisoria);
            statoFatturaEnteNuovaEmissione
                    .setTiStatoFatturaEnte(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.name());
            fatturaProvvisoria.setOrgStatoFatturaEntes(new ArrayList<OrgStatoFatturaEnte>());
            fatturaProvvisoria.addOrgStatoFatturaEnte(statoFatturaEnteNuovaEmissione);
            //
            helper.getEntityManager().persist(fatturaProvvisoria);
            helper.getEntityManager().flush();
            //
            fatturaProvvisoria.setIdStatoFatturaEnteCor(
                    BigDecimal.valueOf(statoFatturaEnteNuovaEmissione.getIdStatoFatturaEnte()));

            // Per la nuova fattura creo o servizi fatturati e ricalcolo i totali
            idFatturaProvvisoria = ctx.getBusinessObject(EntiConvenzionatiEjb.class)
                    .creaServiziFatturatiERicalcolaTotali(fatturaProvvisoria,
                            creaFatturaByAnno.getOrgVCreaFatturaByAnnoId().getIdAccordoEnte(), annoFattServizi);

            if (idFatturaProvvisoria != null) {
                numFattCalcolate++;
                sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                        param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA,
                        new BigDecimal(fatturaProvvisoria.getIdFatturaEnte()), param.getNomePagina());
            }
        }
        return numFattCalcolate;
    }

    // Punti da d) ad f) di Calcolo Fattura Provvisoria
    public Long creaServiziFatturatiERicalcolaTotali(OrgFatturaEnte fatturaProvvisoria, BigDecimal idAccordoEnte,
            BigDecimal annoFattServizi) {
        int serviziFatturatiCreati = 0;

        Long idFatturaProvvisoria = fatturaProvvisoria.getIdFatturaEnte();

        // Creazione servizi fatturati
        serviziFatturatiCreati = ctx.getBusinessObject(EntiConvenzionatiEjb.class)
                .creaServiziFatturati(fatturaProvvisoria.getIdFatturaEnte(), idAccordoEnte, annoFattServizi);

        // Se non sono stati creati servizi, fatturati, cancello la fattura
        if (serviziFatturatiCreati == 0) {
            helper.removeEntity(fatturaProvvisoria, true);
            idFatturaProvvisoria = null;
        } // se invece almeno uno \u00E8 stato creato
        else {
            // Ricalcolo i totali
            ctx.getBusinessObject(EntiConvenzionatiEjb.class).ricalcolaTotali(idFatturaProvvisoria);
        }

        return idFatturaProvvisoria;
    }

    public int creaServiziFatturati(long idFatturaEnte, BigDecimal idAccordoEnte, BigDecimal annoFattServizi) {
        int serviziFatturatiCreati = 0;
        // Creazione dei servizi fatturati, attraverso l'id della nuova fattura creata con ORG_V_CREA_SERV_FATT_ANNUALE
        serviziFatturatiCreati += creaServiziFatturaAnnuale(BigDecimal.valueOf(idFatturaEnte), idAccordoEnte,
                annoFattServizi);

        // Creazione dei servizi fatturati, attraverso l'id della nuova fattura creata con
        // ORG_V_CREA_SERV_FATT_UNATANTUM
        serviziFatturatiCreati += creaServiziFatturaUnaTantum(BigDecimal.valueOf(idFatturaEnte), idAccordoEnte,
                annoFattServizi);

        // Creazione dei servizi fatturati, attraverso l'id della nuova fattura creata con ORG_V_CREA_SERV_FATT_UNA_PREC
        serviziFatturatiCreati += creaServiziFatturaUnaPrec(BigDecimal.valueOf(idFatturaEnte), idAccordoEnte,
                annoFattServizi);

        return serviziFatturatiCreati;
    }

    private int creaServiziFatturaAnnuale(BigDecimal idFatturaEnte, BigDecimal idAccordoEnte,
            BigDecimal annoFattServizi) {
        List<OrgVCreaServFattAnnuale> creaServFattAnnualeList = helper.getOrgVCreaServFattAnnualeCustom(idFatturaEnte,
                idAccordoEnte, annoFattServizi);
        int numServiziCreati = 0;
        // Per ogni servizio non presente in fatture precedenti
        for (OrgVCreaServFattAnnuale creaServFattAnnuale : creaServFattAnnualeList) {
            if (creaServFattAnnuale.getImServizioFattura() != null
                    && creaServFattAnnuale.getImServizioFattura().longValue() != 0L) {
                String qt = creaServFattAnnuale.getQtUnitServizioFattura() != null
                        ? creaServFattAnnuale.getQtUnitServizioFattura().toString() : null;
                BigDecimal qtScaglioneServizioFattura = creaServFattAnnuale.getQtScaglioneServizioFattura() != null
                        ? creaServFattAnnuale.getQtScaglioneServizioFattura() : BigDecimal.ZERO;
                insertOrgServizioFattura(qt, qtScaglioneServizioFattura, creaServFattAnnuale.getNtServizioFattura(),
                        creaServFattAnnuale.getNmServizioFattura(), creaServFattAnnuale.getImServizioFattura(),
                        creaServFattAnnuale.getOrgVCreaServFattAnnualeId().getIdServizioErogato(), idFatturaEnte,
                        creaServFattAnnuale.getIdCdIva(), creaServFattAnnuale.getDtErog(),
                        creaServFattAnnuale.getOrgVCreaServFattAnnualeId().getAaServizioFattura(),
                        creaServFattAnnuale.getImIva());
                numServiziCreati++;
            }
        }
        return numServiziCreati;
    }

    private int creaServiziFatturaUnaTantum(BigDecimal idFatturaEnte, BigDecimal idAccordoEnte,
            BigDecimal annoFattServizi) {
        List<OrgVCreaServFattUnatantum> creaServFattUnatantumList = helper
                .getOrgVCreaServFattUnatantumCustom(idFatturaEnte, idAccordoEnte, annoFattServizi);
        int numServiziCreati = 0;
        // Per ogni servizio non presente in fatture precedenti
        for (OrgVCreaServFattUnatantum creaServFattUnatantum : creaServFattUnatantumList) {
            // Se l'importo \u00E8 diverso da 0 o null inserisco il servizio
            if (creaServFattUnatantum.getImServizioFattura() != null
                    && creaServFattUnatantum.getImServizioFattura().longValue() != 0L) {
                BigDecimal qtScaglioneServizioFattura = creaServFattUnatantum.getQtScaglioneServizioFattura() != null
                        ? creaServFattUnatantum.getQtScaglioneServizioFattura() : BigDecimal.ZERO;
                insertOrgServizioFattura(creaServFattUnatantum.getQtUnitServizioFattura(), qtScaglioneServizioFattura,
                        creaServFattUnatantum.getNtServizioFattura(), creaServFattUnatantum.getNmServizioFattura(),
                        creaServFattUnatantum.getImServizioFattura(),
                        creaServFattUnatantum.getOrgVCreaServFattUnatantumId().getIdServizioErogato(), idFatturaEnte,
                        creaServFattUnatantum.getIdCdIva(), creaServFattUnatantum.getDtErog(),
                        creaServFattUnatantum.getOrgVCreaServFattUnatantumId().getAaServizioFattura(),
                        creaServFattUnatantum.getImIva());
                numServiziCreati++;
            }
        }
        return numServiziCreati;
    }

    private int creaServiziFatturaUnaPrec(BigDecimal idFatturaEnte, BigDecimal idAccordoEnte,
            BigDecimal annoFattServizi) {
        List<OrgVCreaServFattUnaPrec> creaServFattUnaPrecList = helper.getOrgVCreaServFattUnaPrecCustom(idFatturaEnte,
                idAccordoEnte, annoFattServizi);
        int numServiziCreati = 0;
        // Per ogni servizio non presente in fatture precedenti
        for (OrgVCreaServFattUnaPrec creaServFattUnaPrec : creaServFattUnaPrecList) {
            // Se l'importo \u00E8 diverso da 0 o null inserisco il servizio
            if (creaServFattUnaPrec.getImServizioFattura() != null
                    && creaServFattUnaPrec.getImServizioFattura().longValue() != 0L) {
                BigDecimal qtScaglioneServizioFattura = creaServFattUnaPrec.getQtScaglioneServizioFattura() != null
                        ? creaServFattUnaPrec.getQtScaglioneServizioFattura() : BigDecimal.ZERO;
                insertOrgServizioFattura(creaServFattUnaPrec.getQtUnitServizioFattura(), qtScaglioneServizioFattura,
                        creaServFattUnaPrec.getNtServizioFattura(), creaServFattUnaPrec.getNmServizioFattura(),
                        creaServFattUnaPrec.getImServizioFattura(),
                        creaServFattUnaPrec.getOrgVCreaServFattUnaPrecId().getIdServizioErogato(), idFatturaEnte,
                        creaServFattUnaPrec.getIdCdIva(), creaServFattUnaPrec.getDtErog(),
                        creaServFattUnaPrec.getOrgVCreaServFattUnaPrecId().getAaServizioFatturaPrec(),
                        creaServFattUnaPrec.getImIva());
                numServiziCreati++;
            }
        }
        return numServiziCreati;
    }

    private void insertOrgServizioFattura(String qtUnitServizioFattura, BigDecimal qtScaglioneServizioFattura,
            String ntServizioFattura, String nmServizioFattura, BigDecimal imServizioFattura,
            BigDecimal idServizioErogato, BigDecimal idFatturaEnte, BigDecimal idCdIva, Date dtErog,
            BigDecimal aaServizioFattura, BigDecimal imIva) {
        OrgServizioFattura servizioFattura = new OrgServizioFattura();
        servizioFattura
                .setQtUnitServizioFattura(qtUnitServizioFattura != null ? new BigDecimal(qtUnitServizioFattura) : null);
        servizioFattura.setQtScaglioneServizioFattura(qtScaglioneServizioFattura);
        servizioFattura.setNtServizioFattura(ntServizioFattura);
        servizioFattura.setNmServizioFattura(nmServizioFattura);
        servizioFattura.setImServizioFattura(imServizioFattura);
        servizioFattura.setImIva(imIva);
        servizioFattura.setDtErog(dtErog);
        servizioFattura.setAaServizioFattura(aaServizioFattura);
        // Relazione con OrgServizioErog
        OrgServizioErog servizioErog = helper.findById(OrgServizioErog.class, idServizioErogato);
        servizioFattura.setOrgServizioErog(servizioErog);
        if (servizioErog.getOrgServizioFatturas() == null) {
            servizioErog.setOrgServizioFatturas(new ArrayList<OrgServizioFattura>());
        }
        servizioErog.addOrgServizioFattura(servizioFattura);
        // Relazione con OrgFatturaEnte (provvisoria)
        OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnte);
        servizioFattura.setOrgFatturaEnte(fatturaEnte);
        if (fatturaEnte.getOrgServizioFatturas() == null) {
            fatturaEnte.setOrgServizioFatturas(new ArrayList<OrgServizioFattura>());
        }
        fatturaEnte.addOrgServizioFattura(servizioFattura);
        // Relazione con OrgCdIva
        if (idCdIva != null) {
            OrgCdIva cdIva = helper.findById(OrgCdIva.class, idCdIva);
            servizioFattura.setOrgCdIva(cdIva);
            if (cdIva.getOrgServizioFatturas() == null) {
                cdIva.setOrgServizioFatturas(new ArrayList<OrgServizioFattura>());
            }
            cdIva.getOrgServizioFatturas().add(servizioFattura);
        }

        helper.getEntityManager().persist(servizioFattura);
        helper.getEntityManager().flush();
    }

    public int riemettiFattureStornate(LogParam param, BigDecimal idEnteConvenz, BigDecimal annoTestata) {

        // Calcolo le fatture in stato STORNATA e che hanno il flag fl_da_riem = 1 dell'ente convenzionato
        List<Long> idFattureStornateDaRiemettereList = getFattureStornateDaRiemettereList(idEnteConvenz);
        int numFattureCalcolate = 0;

        // Per ogni fattura stornata
        for (Long idFatturaStornataDaRiemettere : idFattureStornateDaRiemettereList) {
            Long idFatturaProvvisoria = riemettiFatturaStornata(param, idEnteConvenz, idFatturaStornataDaRiemettere,
                    annoTestata);
            if (idFatturaProvvisoria != null) {
                numFattureCalcolate++;
            }
        }
        return numFattureCalcolate;
    }

    public Long riemettiFatturaStornata(LogParam param, BigDecimal idEnteConvenz, Long idFatturaStornataDaRiemettere,
            BigDecimal annoTestata) {
        Long idFatturaProvvisoria = null;
        OrgVCreaFatturaByFatt creaFatturaByFatt = helper.getOrgVCreaFatturaByFatt(idFatturaStornataDaRiemettere,
                annoTestata);

        if (creaFatturaByFatt != null) {
            OrgEnteSiam enteSiam = helper.findById(OrgEnteSiam.class, idEnteConvenz);

            // Eseguo il salvataggio della fattura
            OrgFatturaEnte fatturaProvvisoria = new OrgFatturaEnte();
            fatturaProvvisoria.setOrgEnteSiam(enteSiam);
            enteSiam.addOrgFatturaEnte(fatturaProvvisoria);
            fatturaProvvisoria.setAaFattura(creaFatturaByFatt.getOrgVCreaFatturaByFattId().getAaFattura());
            fatturaProvvisoria.setPgFattura(creaFatturaByFatt.getPgFattura());
            fatturaProvvisoria.setImTotFattura(creaFatturaByFatt.getImTotFattura());
            fatturaProvvisoria.setImTotDaPagare(creaFatturaByFatt.getImTotDaPagare());
            fatturaProvvisoria.setImTotIva(creaFatturaByFatt.getImTotIva());
            fatturaProvvisoria.setCdFattura(creaFatturaByFatt.getCdFattura());

            // Fattura stornata
            OrgFatturaEnte fatturaStornata = helper.findById(OrgFatturaEnte.class,
                    creaFatturaByFatt.getOrgVCreaFatturaByFattId().getIdFatturaEnteDaRicalc());
            fatturaProvvisoria.setOrgFatturaEnte(fatturaStornata);

            // Setto la nuova fattura a stato CALCOLATA
            OrgStatoFatturaEnte statoFatturaEnteNuovaEmissione = new OrgStatoFatturaEnte();
            statoFatturaEnteNuovaEmissione.setDtRegStatoFatturaEnte(new Date());
            statoFatturaEnteNuovaEmissione.setOrgFatturaEnte(fatturaProvvisoria);
            statoFatturaEnteNuovaEmissione
                    .setTiStatoFatturaEnte(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.CALCOLATA.name());
            fatturaProvvisoria.setOrgStatoFatturaEntes(new ArrayList<OrgStatoFatturaEnte>());
            fatturaProvvisoria.addOrgStatoFatturaEnte(statoFatturaEnteNuovaEmissione);
            //
            helper.getEntityManager().persist(fatturaProvvisoria);
            helper.getEntityManager().flush();
            //
            fatturaProvvisoria.setIdStatoFatturaEnteCor(
                    BigDecimal.valueOf(statoFatturaEnteNuovaEmissione.getIdStatoFatturaEnte()));

            // Determino l'anno dei servizi da fatturare
            BigDecimal annoFattServizi = helper.getAnnoServiziDaFatturare(idFatturaStornataDaRiemettere);

            // Determino l'accordo
            BigDecimal idAccordoEnte = null;
            for (OrgServizioFattura servizioFattura : fatturaStornata.getOrgServizioFatturas()) {
                idAccordoEnte = BigDecimal
                        .valueOf(servizioFattura.getOrgServizioErog().getOrgAccordoEnte().getIdAccordoEnte());
            }

            // Per la nuova fattura creo o servizi fatturati e ricalcolo i totali
            idFatturaProvvisoria = ctx.getBusinessObject(EntiConvenzionatiEjb.class)
                    .creaServiziFatturatiERicalcolaTotali(fatturaProvvisoria, idAccordoEnte, annoFattServizi);

            if (idFatturaProvvisoria != null) {
                sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                        param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA,
                        new BigDecimal(fatturaProvvisoria.getIdFatturaEnte()), param.getNomePagina());
            }
        }
        return idFatturaProvvisoria;
    }

    public void ricalcolaTotali(long idFatturaEnte) {
        OrgFatturaEnte fatturaProvvisoria = helper.findById(OrgFatturaEnte.class, idFatturaEnte);
        // Ricalcolo i totali tramite vista
        OrgVCalcTotFatt calcTotFatt = helper.findViewById(OrgVCalcTotFatt.class,
                BigDecimal.valueOf(fatturaProvvisoria.getIdFatturaEnte()));
        fatturaProvvisoria.setImTotFattura(
                calcTotFatt.getImTotFattura() != null ? calcTotFatt.getImTotFattura() : BigDecimal.ZERO);
        fatturaProvvisoria.setImTotDaPagare(
                calcTotFatt.getImTotDaPagare() != null ? calcTotFatt.getImTotDaPagare() : BigDecimal.ZERO);
        fatturaProvvisoria.setImTotIva(calcTotFatt.getImTotIva() != null ? calcTotFatt.getImTotIva() : BigDecimal.ZERO);
        // Persisto le modifiche
        helper.getEntityManager().flush();
    }

    public List<Long> getFattureStornateDaRiemettereList(BigDecimal idEnteConvenz) {
        return helper.getIdFattureStornateDaRiemettereList(idEnteConvenz);
    }

    public OrgSollecitoFatturaEnteRowBean getOrgSollecitoFatturaEnteRowBean(BigDecimal idSollecitoFatturaEnte)
            throws ParerUserError {
        OrgSollecitoFatturaEnte sollecitoFatturaEnte = helper.findById(OrgSollecitoFatturaEnte.class,
                idSollecitoFatturaEnte);
        OrgSollecitoFatturaEnteRowBean sollecitoFatturaEnteRowBean = new OrgSollecitoFatturaEnteRowBean();
        try {
            if (sollecitoFatturaEnte != null) {
                sollecitoFatturaEnteRowBean = (OrgSollecitoFatturaEnteRowBean) Transform
                        .entity2RowBean(sollecitoFatturaEnte);
            }
        } catch (Exception e) {
            LOGGER.error(
                    "Errore durante il recupero del sollecito fattura ente " + ExceptionUtils.getRootCauseMessage(e),
                    e);
            throw new ParerUserError("Errore durante il recupero del sollecito fattura ente ");
        }
        return sollecitoFatturaEnteRowBean;
    }

    /**
     * Metodo di insert di un nuovo sollecito fattura ente
     *
     * @param param
     *            parametri per il logging
     * @param idFatturaEnte
     *            id fattura/ente
     * @param dtSollecito
     *            data sollecito
     * @param cdRegistroSollecito
     *            registro sollecito
     * @param cdKeyVarSollecito
     *            chiave sollecito
     * @param aaVarSollecito
     *            anno sollecito
     * @param dlSollecito
     *            descrizione
     *
     * @return id del nuovo record riferito al sollecito
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveSollecito(LogParam param, BigDecimal idFatturaEnte, String cdRegistroSollecito,
            BigDecimal aaVarSollecito, String cdKeyVarSollecito, Date dtSollecito, String dlSollecito)
            throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio del sollecito fattura");

        /* Ricavo da DB il sollecito */
        OrgSollecitoFatturaEnte sollecitoDB = helper.getOrgSollecitoFatturaEnte(idFatturaEnte, cdRegistroSollecito,
                aaVarSollecito, cdKeyVarSollecito);
        if (sollecitoDB != null) {
            throw new ParerUserError("Registro, anno e numero sollecito gi\u00E0  presenti per questa fattura");
        }

        Long idSollecitoFatturaEnte = null;
        try {
            OrgSollecitoFatturaEnte sollecito = new OrgSollecitoFatturaEnte();
            sollecito.setCdRegistroSollecito(cdRegistroSollecito);
            sollecito.setAaVarSollecito(aaVarSollecito);
            sollecito.setCdKeyVarSollecito(cdKeyVarSollecito);
            sollecito.setDtSollecito(dtSollecito);
            sollecito.setDlSollecito(dlSollecito);
            OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnte);
            sollecito.setOrgFatturaEnte(fatturaEnte);
            if (fatturaEnte.getOrgSollecitoFatturaEntes() == null) {
                fatturaEnte.setOrgSollecitoFatturaEntes(new ArrayList<OrgSollecitoFatturaEnte>());
            }
            fatturaEnte.getOrgSollecitoFatturaEntes().add(sollecito);

            ////////////////////////////////////////////
            // Inserisco lo stato fattura sollecitata //
            ////////////////////////////////////////////
            insertStatoFatturaEnte(fatturaEnte,
                    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione());
            helper.insertEntity(sollecito, true);
            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA, idFatturaEnte,
                    param.getNomePagina());
            LOGGER.debug("Salvataggio del sollecito completato");
            idSollecitoFatturaEnte = sollecito.getIdSollecitoFatturaEnte();
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del sollecito fattura ente : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del sollecito fattura ente");
        }
        return idSollecitoFatturaEnte;
    }

    /**
     * Metodo di update di un sollecito
     *
     * @param param
     *            parametri per il logging
     * @param idSollecitoFatturaEnte
     *            id sollecito fattura
     * @param dtSollecito
     *            data sollecito
     * @param idFatturaEnte
     *            id fattura/ente
     * @param cdRegistroSollecito
     *            registro sollecito
     * @param cdKeyVarSollecito
     *            numero sollecito
     * @param aaVarSollecito
     *            anno sollecito
     * @param dlSollecito
     *            descrizione
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveSollecito(LogParam param, BigDecimal idSollecitoFatturaEnte, BigDecimal idFatturaEnte,
            String cdRegistroSollecito, BigDecimal aaVarSollecito, String cdKeyVarSollecito, Date dtSollecito,
            String dlSollecito) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio delle modifiche sul sollecito");
        /* Ricavo da DB il sollecito per controllo su esistenza per stessa fattura */
        OrgSollecitoFatturaEnte sollecitoDB = helper.getOrgSollecitoFatturaEnte(idFatturaEnte, cdRegistroSollecito,
                aaVarSollecito, cdKeyVarSollecito);
        if (sollecitoDB != null && sollecitoDB.getIdSollecitoFatturaEnte() != idSollecitoFatturaEnte.longValue()) {
            throw new ParerUserError("Registro, anno e numero sollecito gi\u00E0  presenti per questa fattura");
        }

        try {
            sollecitoDB.setCdRegistroSollecito(cdRegistroSollecito);
            sollecitoDB.setAaVarSollecito(aaVarSollecito);
            sollecitoDB.setCdKeyVarSollecito(cdKeyVarSollecito);
            sollecitoDB.setDtSollecito(dtSollecito);
            sollecitoDB.setDlSollecito(dlSollecito);

            helper.getEntityManager().flush();
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA, idFatturaEnte,
                    param.getNomePagina());
            LOGGER.debug("Salvataggio del sollecito fattura ente completato");
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del sollecito fattura ente : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del sollecito fattura ente");
        }
    }

    public void insertStatoFatturaEnte(OrgFatturaEnte fatturaEnte, String tiStatoFatturaEnte) {
        OrgStatoFatturaEnte statoFatturaEnte = new OrgStatoFatturaEnte();
        statoFatturaEnte.setDtRegStatoFatturaEnte(new Date());
        statoFatturaEnte.setOrgFatturaEnte(fatturaEnte);
        statoFatturaEnte.setTiStatoFatturaEnte(tiStatoFatturaEnte);
        fatturaEnte.getOrgStatoFatturaEntes().add(statoFatturaEnte);
        helper.insertEntity(statoFatturaEnte, true);
        // Setto l'id dello stato fattura corrente nella tabella delle fatture
        fatturaEnte.setIdStatoFatturaEnteCor(new BigDecimal(statoFatturaEnte.getIdStatoFatturaEnte()));
    }

    /**
     * Metodo di eliminazione di un sollecito
     *
     * @param param
     *            parametri per il logging
     * @param idFatturaEnte
     *            id fattura/ente
     * @param idSollecitoFatturaEnte
     *            id sollecito fattura/ente
     * @param numRecordTabella
     *            numero record
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgSollecitoFatturaEnte(LogParam param, BigDecimal idFatturaEnte,
            BigDecimal idSollecitoFatturaEnte, int numRecordTabella) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del sollecito");
        try {
            // Recupero la fattura
            OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnte);

            // Recupero e rimuovo il sollecito
            OrgSollecitoFatturaEnte sollecitoFatturaEnte = helper.findById(OrgSollecitoFatturaEnte.class,
                    idSollecitoFatturaEnte);
            helper.removeEntity(sollecitoFatturaEnte, true);

            // Aggiorno lo STATO della fattura in base ai nuovi totali
            OrgVVisFattura visFattura = helper.getPrimoOrgVVisFatturaEnte(idFatturaEnte);
            BigDecimal importoPagamento = visFattura.getImTotDaPagare() != null ? visFattura.getImTotDaPagare()
                    : BigDecimal.ZERO;
            BigDecimal importoIncassato = visFattura.getImTotPagam() != null ? visFattura.getImTotPagam()
                    : BigDecimal.ZERO;

            // Recupero lo stato fattura corrente
            OrgStatoFatturaEnte statoFatturaEnteCorrente = helper.findById(OrgStatoFatturaEnte.class,
                    fatturaEnte.getIdStatoFatturaEnteCor());

            // Caso PAGATA, sia quando importoIncassato = importoPagamento, sia quando importoIncassato >
            // importoPagamento (dopo alert a video)
            if (importoIncassato.compareTo(importoPagamento) >= 0) {
                // Inserisco lo stato PAGATA se ovviamente non lo era giÃƒÆ’Ã‚Â  prima
                if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                        .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione())) {
                    insertStatoFatturaEnte(fatturaEnte,
                            ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA.getDescrizione());
                }
            } else if (importoIncassato.compareTo(importoPagamento) < 0) {

                // // Se lo stato \u00E8 SOLLECITATA, rimane SOLLECITATA
                // if
                // (statoFatturaEnteCorrente.getTiStatoFatturaEnte().equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione()))
                // {
                // // Rimane sollecitata
                // } else {
                // if (visFattura.getDtScadFattura() != null) {
                // // Not before significa after estremo incluso
                // if (!visFattura.getDtScadFattura().before(new Date())) {
                // if
                // (!statoFatturaEnteCorrente.getTiStatoFatturaEnte().equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione()))
                // {
                // insertStatoFatturaEnte(fatturaEnte,
                // ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione());
                // }
                // } else {
                // if
                // (!statoFatturaEnteCorrente.getTiStatoFatturaEnte().equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione()))
                // {
                // insertStatoFatturaEnte(fatturaEnte,
                // ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione());
                // }
                // }
                // } else {
                // if
                // (!statoFatturaEnteCorrente.getTiStatoFatturaEnte().equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione()))
                // {
                // insertStatoFatturaEnte(fatturaEnte,
                // ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione());
                // }
                // }
                // }
                // Se la fattura ha almeno un sollecito oltre a quello oggetto di eliminazione
                if (numRecordTabella > 1) {
                    // Rimane sollecitata
                } // Se la fattura non ha altri solleciti, oltre a quello oggetto di eliminazione
                else if (numRecordTabella == 1) {
                    if (visFattura.getDtScadFattura() != null) {
                        // Not before significa after estremo incluso
                        if (!visFattura.getDtScadFattura().before(new Date())) {
                            if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte().equals(
                                    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione())) {
                                insertStatoFatturaEnte(fatturaEnte,
                                        ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE
                                                .getDescrizione());
                            }
                        } else {
                            if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                                    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())) {
                                insertStatoFatturaEnte(fatturaEnte,
                                        ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione());
                            }
                        }
                    } else {
                        if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte().equals(
                                ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione())) {
                            insertStatoFatturaEnte(fatturaEnte,
                                    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione());
                        }
                    }
                }

            } // Se l'importo incassato \u00E8 uguale a 0
            else if (importoIncassato.compareTo(BigDecimal.ZERO) == 0) {
                // Se la fattura ha almeno un sollecito oltre a quello oggetto di eliminazione
                if (numRecordTabella > 1) {
                    // Non faccio nulla ma lascio per chiarezza
                } // Se la fattura non ha altri solleciti, oltre a quello oggetto di eliminazione
                else if (numRecordTabella == 1) {
                    if (visFattura.getDtScadFattura() != null) {
                        // Not before significa after estremo incluso
                        if (!visFattura.getDtScadFattura().before(new Date())) {
                            if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                                    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())) {
                                insertStatoFatturaEnte(fatturaEnte,
                                        ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione());
                            }
                        } else {
                            if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                                    .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione())) {
                                insertStatoFatturaEnte(fatturaEnte,
                                        ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione());
                            }
                        }
                    } else {
                        if (!statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                                .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione())) {
                            insertStatoFatturaEnte(fatturaEnte,
                                    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione());
                        }
                    }
                }
            }
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_FATTURA, idFatturaEnte,
                    param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del sollecito "
                    + ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError("Eccezione imprevista nell'eliminazione del sollecito");
        }
    }

    public boolean checkImportiPerSollecito(BigDecimal idFatturaEnte) {
        OrgVVisFattura visFattura = helper.getPrimoOrgVVisFatturaEnte(idFatturaEnte);
        BigDecimal importoPagamento = visFattura.getImTotDaPagare() != null ? visFattura.getImTotDaPagare()
                : BigDecimal.ZERO;
        BigDecimal importoIncassato = visFattura.getImTotPagam() != null ? visFattura.getImTotPagam() : BigDecimal.ZERO;
        return importoIncassato.compareTo(importoPagamento) > 0;
    }

    public ConstOrgStatoFatturaEnte.TiStatoFatturaEnte getStatoFatturaDaAssegnareUpdate(BigDecimal idFatturaEnte) {
        // Recupero la fattura
        OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnte);

        // Recupero lop stato fattura corrente
        OrgStatoFatturaEnte statoFatturaEnteCorrente = helper.findById(OrgStatoFatturaEnte.class,
                fatturaEnte.getIdStatoFatturaEnteCor());

        if (statoFatturaEnteCorrente.getTiStatoFatturaEnte()
                .equals(ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA.getDescrizione())) {
            return ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.SOLLECITATA;
        } else {
            if (fatturaEnte.getDtScadFattura() != null) {
                // Not before significa after estremo incluso
                if (!fatturaEnte.getDtScadFattura().before(new Date())) {
                    return ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE;
                } else {
                    return ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA;
                }
            } else {
                return ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE;
            }
        }
    }

    public OrgVCalcTotFattRowBean getOrgVCalcTotFattRowBean(BigDecimal idFatturaEnte) throws ParerUserError {
        OrgVCalcTotFatt calcTotFatt = helper.findViewById(OrgVCalcTotFatt.class, idFatturaEnte);
        OrgVCalcTotFattRowBean calcTotFattRowBean = new OrgVCalcTotFattRowBean();
        if (calcTotFatt != null) {
            try {
                calcTotFattRowBean = (OrgVCalcTotFattRowBean) Transform.entity2RowBean(calcTotFatt);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dei totali fattura " + ExceptionUtils.getRootCauseMessage(e),
                        e);
                throw new ParerUserError("Errore durante il recupero dei totali fattura");
            }
        }
        return calcTotFattRowBean;
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

    public Map<String, String> getUsrOrganizIamMapByDlPath(String dlPath) {
        Map<String, String> org = new HashMap<>();
        String[] ids = StringUtils.split(dlPath, "/");
        UsrOrganizIam ambiente = helper.findById(UsrOrganizIam.class, new Long(ids[0]));
        UsrOrganizIam ente = helper.findById(UsrOrganizIam.class, new Long(ids[1]));
        UsrOrganizIam struttura = helper.findById(UsrOrganizIam.class, new Long(ids[2]));
        org.put("AMBIENTE", ambiente.getNmOrganiz());
        org.put("ENTE", ente.getNmOrganiz());
        org.put("STRUTTURA", struttura.getNmOrganiz());
        return org;
    }

    /**
     * Restituisce il rowbean contenente il dettaglio di un disciplinare tecnico
     *
     * @param idDiscipStrut
     *            id disciplinale
     *
     * @return row bean {@link OrgDiscipStrutRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgDiscipStrutRowBean getOrgDiscipStrutRowBean(BigDecimal idDiscipStrut) throws ParerUserError {
        OrgDiscipStrut discipStrut = helper.findById(OrgDiscipStrut.class, idDiscipStrut);
        OrgDiscipStrutRowBean row = null;
        try {
            row = (OrgDiscipStrutRowBean) Transform.entity2RowBean(discipStrut);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero del disciplinare tecnico "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    public boolean existsUDInSacer(BigDecimal idOrganizApplic, String registro, BigDecimal anno, String numero) {
        return helper.existsUDInSacer(idOrganizApplic, registro, anno, numero);
    }

    /**
     * Metodo di insert di un nuovo disciplinare tecnico
     *
     * @param param
     *            parametri per il logging
     * @param aaDiscipStrut
     *            anno disciplinale
     * @param blDiscipStrut
     *            blob disciplinale
     * @param cdKeyDiscipStrut
     *            numero disciplinale
     * @param cdRegistroDiscipStrut
     *            registro disciplinale
     * @param dsDiscipStrut
     *            descrizione disciplinale
     * @param idOrganizIam
     *            id organizzazione IAM
     * @param dsNoteDiscipStrut
     *            note
     * @param flInseritoManualmente
     *            flag 1/0 (true/false)
     * @param dtDiscipStrut
     *            data disciplinale
     * @param idAccordoEnte
     *            id accordo ente
     * @param nmAmbiente
     *            nome ambiente
     * @param nmEnte
     *            nome ente
     * @param nmStrut
     *            nome struttura
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return id del nuovo ente
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long saveDisciplinareTecnico(LogParam param, BigDecimal aaDiscipStrut, byte[] blDiscipStrut,
            String cdKeyDiscipStrut, String cdRegistroDiscipStrut, String dsDiscipStrut, String dsNoteDiscipStrut,
            Date dtDiscipStrut, String flInseritoManualmente, BigDecimal idAccordoEnte, BigDecimal idOrganizIam,
            String nmAmbiente, String nmEnte, String nmStrut, BigDecimal idEnteConvenz) throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio del disciplinare tecnico");

        // Controlla se \u00E8 già stato inserito un disciplinare nella stessa data per la stessa organizzazione
        if (helper.existsDisciplinareByEnte(idEnteConvenz, idOrganizIam, dtDiscipStrut)) {
            throw new ParerUserError(
                    "Esiste gi\u00E0 un disciplinare tecnico inserito manualmente per organizzazione versante e data");
        }

        Long idDiscipStrut = null;
        try {
            OrgDiscipStrut discipStrut = new OrgDiscipStrut();
            discipStrut.setAaDiscipStrut(aaDiscipStrut);
            if (blDiscipStrut != null) {
                discipStrut.setBlDiscipStrut(blDiscipStrut);
            }
            discipStrut.setCdKeyDiscipStrut(cdKeyDiscipStrut);
            discipStrut.setCdRegistroDiscipStrut(cdRegistroDiscipStrut);
            discipStrut.setDsDiscipStrut(dsDiscipStrut);
            discipStrut.setDsNoteDiscipStrut(dsNoteDiscipStrut);
            discipStrut.setEnteDiscipStrut(nmEnte);
            discipStrut.setStrutturaDiscipStrut(nmStrut);
            discipStrut.setDtDiscipStrut(dtDiscipStrut);
            discipStrut.setFlInseritoManualmente(flInseritoManualmente);
            OrgEnteSiam enteConvenz = helper.findById(OrgEnteSiam.class, idEnteConvenz);
            discipStrut.setOrgEnteConvenz(enteConvenz);
            discipStrut.setUsrOrganizIam(helper.findById(UsrOrganizIam.class, idOrganizIam));
            helper.insertEntity(discipStrut, true);
            LOGGER.debug("Salvataggio del disciplinare tecnico eseguito con successo");
            idDiscipStrut = discipStrut.getIdDiscipStrut();
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    idEnteConvenz, param.getNomePagina());
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del disciplinare tecnico : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del disciplinare tecnico");
        }
        return idDiscipStrut;
    }

    /**
     * Metodo di update di un nuovo disciplinare tecnico
     *
     * @param param
     *            parametri per il logging
     * @param idDiscipStrut
     *            id disciplinale
     * @param aaDiscipStrut
     *            anno disciplinale
     * @param blDiscipStrut
     *            blob disciplinale
     * @param cdKeyDiscipStrut
     *            numero disciplinale
     * @param cdRegistroDiscipStrut
     *            registro disciplinale
     * @param dsDiscipStrut
     *            descrizione disciplinale
     * @param idOrganizIam
     *            id organizzazione IAM
     * @param dsNoteDiscipStrut
     *            descrizione note disciplinale
     * @param flInseritoManualmente
     *            flag 1/0 (true/false)
     * @param dtDiscipStrut
     *            data disciplinale
     * @param idAccordoEnte
     *            id accordo ente
     * @param nmAmbiente
     *            nome ambiente
     * @param nmEnte
     *            nome ente
     * @param nmStrut
     *            nome struttura
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveDisciplinareTecnico(LogParam param, BigDecimal idDiscipStrut, BigDecimal aaDiscipStrut,
            byte[] blDiscipStrut, String cdKeyDiscipStrut, String cdRegistroDiscipStrut, String dsDiscipStrut,
            String dsNoteDiscipStrut, Date dtDiscipStrut, String flInseritoManualmente, BigDecimal idAccordoEnte,
            BigDecimal idOrganizIam, String nmAmbiente, String nmEnte, String nmStrut, BigDecimal idEnteConvenz)
            throws ParerUserError {
        LOGGER.debug("Eseguo il salvataggio del disciplinare tecnico");

        OrgDiscipStrut discipStrut = helper.findById(OrgDiscipStrut.class, idDiscipStrut);

        OrgDiscipStrut discipStrutDB = helper.getDisciplinareByEnteOrganizData(idEnteConvenz, idOrganizIam,
                dtDiscipStrut);

        if (discipStrutDB != null && discipStrutDB.getIdDiscipStrut() != idDiscipStrut.longValue()
                && flInseritoManualmente.equals("1")) {
            throw new ParerUserError(
                    "Esiste gi\u00E0 un disciplinare tecnico inserito manualmente per organizzazione versante e data");
        }

        try {
            discipStrut.setAaDiscipStrut(aaDiscipStrut);
            if (blDiscipStrut != null) {
                discipStrut.setBlDiscipStrut(blDiscipStrut);
            }
            discipStrut.setCdKeyDiscipStrut(cdKeyDiscipStrut);
            discipStrut.setCdRegistroDiscipStrut(cdRegistroDiscipStrut);
            discipStrut.setDsDiscipStrut(dsDiscipStrut);
            discipStrut.setDsNoteDiscipStrut(dsNoteDiscipStrut);
            if (flInseritoManualmente.equals("1")) {
                discipStrut.setDtDiscipStrut(dtDiscipStrut);
            }
            discipStrut.setFlInseritoManualmente(flInseritoManualmente);
            discipStrut.setEnteDiscipStrut(nmEnte);
            discipStrut.setStrutturaDiscipStrut(nmStrut);
            discipStrut.setOrgEnteConvenz(helper.findById(OrgEnteSiam.class, idEnteConvenz));
            discipStrut.setUsrOrganizIam(helper.findById(UsrOrganizIam.class, idOrganizIam));
            // helper.insertEntity(discipStrut, true);
            LOGGER.debug("Salvataggio del disciplinare tecnico eseguito con successo");
            sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                    param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                    idEnteConvenz, param.getNomePagina());
        } catch (Exception ex) {
            LOGGER.error("Errore imprevisto durante il salvataggio del disciplinare tecnico : "
                    + ExceptionUtils.getRootCauseMessage(ex), ex);
            throw new ParerUserError("Eccezione imprevista durante il salvataggio del disciplinare tecnico");
        }
    }

    /**
     * Restituisce il tablebean rappresentante la lista dei disciplinari tecnici in base ad un accordo.
     *
     * @param idAccordoEnte
     *            id accordo ente
     *
     * @return il tablebean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgDiscipStrutTableBean getOrgDiscipStrutTableBean(BigDecimal idAccordoEnte) throws ParerUserError {
        OrgDiscipStrutTableBean discipStrutTableBean = new OrgDiscipStrutTableBean();
        List<OrgDiscipStrut> discipStrutList = helper.getOrgDiscipStrutList(idAccordoEnte);
        if (!discipStrutList.isEmpty()) {
            try {
                for (OrgDiscipStrut discipStrut : discipStrutList) {
                    OrgDiscipStrutRowBean discipStrutRowBean = new OrgDiscipStrutRowBean();
                    discipStrutRowBean.setString("nm_organiz_iam", discipStrut.getUsrOrganizIam().getNmOrganiz());
                    discipStrutRowBean.setBigDecimal("id_discip_strut",
                            BigDecimal.valueOf(discipStrut.getIdDiscipStrut()));
                    discipStrutRowBean.setTimestamp("dt_discip_strut",
                            new Timestamp(discipStrut.getDtDiscipStrut().getTime()));
                    discipStrutRowBean.setString("fl_inserito_manualmente", discipStrut.getFlInseritoManualmente());
                    discipStrutTableBean.add(discipStrutRowBean);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero dei disciplinari tecnici sull'accordo avente id "
                        + idAccordoEnte + " " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError(
                        "Errore durante il recupero dei disciplinari tecnici sull'accordo avente id " + idAccordoEnte);
            }
        }
        return discipStrutTableBean;
    }

    /**
     * Restituisce il tablebean rappresentante la lista dei disciplinari tecnici in base ad un ente.
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tablebean
     *
     * @throws ParerUserError
     *             in caso di eccezione
     */
    public OrgDiscipStrutTableBean getOrgDiscipStrutByEnteTableBean(BigDecimal idEnteConvenz) throws ParerUserError {
        OrgDiscipStrutTableBean discipStrutTableBean = new OrgDiscipStrutTableBean();
        OrgEnteSiam enteConvenz = helper.findById(OrgEnteSiam.class, idEnteConvenz);
        List<OrgDiscipStrut> discipStrutList = helper
                .getOrgDiscipStrutListByEnte(BigDecimal.valueOf(enteConvenz.getIdEnteSiam()));
        if (!discipStrutList.isEmpty()) {
            try {
                for (OrgDiscipStrut discipStrut : discipStrutList) {
                    OrgDiscipStrutRowBean discipStrutRowBean = new OrgDiscipStrutRowBean();
                    discipStrutRowBean.setString("nm_organiz_iam", discipStrut.getUsrOrganizIam().getNmOrganiz());
                    discipStrutRowBean.setBigDecimal("id_discip_strut",
                            BigDecimal.valueOf(discipStrut.getIdDiscipStrut()));
                    discipStrutRowBean.setTimestamp("dt_discip_strut",
                            new Timestamp(discipStrut.getDtDiscipStrut().getTime()));
                    discipStrutRowBean.setString("fl_inserito_manualmente", discipStrut.getFlInseritoManualmente());
                    discipStrutRowBean.setString("ds_discip_strut", discipStrut.getDsDiscipStrut());
                    discipStrutRowBean.setString("ds_note_discip_strut", discipStrut.getDsNoteDiscipStrut());
                    discipStrutTableBean.add(discipStrutRowBean);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero dei disciplinari tecnici sull'ente convenzionato avente id "
                        + enteConvenz.getIdEnteSiam() + " " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError(
                        "Errore durante il recupero dei disciplinari tecnici sull'ente convenzionato avente id "
                                + enteConvenz.getIdEnteSiam());
            }
        }
        return discipStrutTableBean;
    }

    /**
     * Restituisce il tablebean rappresentante la lista degli enti associati al collegamento.
     *
     * @param idCollegEntiConvenz
     *            id ente convenzionato
     *
     * @return il tablebean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAppartCollegEntiTableBean getOrgAppartCollegEntiTableBean(BigDecimal idCollegEntiConvenz)
            throws ParerUserError {
        OrgAppartCollegEntiTableBean appartCollegEntiTableBean = new OrgAppartCollegEntiTableBean();
        List<OrgAppartCollegEnti> appartCollegEntiList = helper.getOrgAppartCollegEntiList(idCollegEntiConvenz);
        if (!appartCollegEntiList.isEmpty()) {
            try {
                for (OrgAppartCollegEnti appartCollegEnti : appartCollegEntiList) {
                    OrgAppartCollegEntiRowBean appartCollegEntiRowBean = new OrgAppartCollegEntiRowBean();
                    appartCollegEntiRowBean.setBigDecimal("id_appart_colleg_enti",
                            BigDecimal.valueOf(appartCollegEnti.getIdAppartCollegEnti()));
                    appartCollegEntiRowBean.setBigDecimal("id_ente_convenz",
                            BigDecimal.valueOf(appartCollegEnti.getOrgEnteSiam().getIdEnteSiam()));
                    if (appartCollegEnti.getOrgEnteSiam().getOrgAmbienteEnteConvenz() != null) {
                        appartCollegEntiRowBean.setString("nm_ambiente_ente_convenz", appartCollegEnti.getOrgEnteSiam()
                                .getOrgAmbienteEnteConvenz().getNmAmbienteEnteConvenz());
                    }
                    appartCollegEntiRowBean.setString("nm_ente_convenz",
                            appartCollegEnti.getOrgEnteSiam().getNmEnteSiam());
                    appartCollegEntiRowBean.setTimestamp("dt_ini_val",
                            new Timestamp(appartCollegEnti.getDtIniVal().getTime()));
                    appartCollegEntiRowBean.setTimestamp("dt_fin_val",
                            new Timestamp(appartCollegEnti.getDtFinVal().getTime()));
                    if (appartCollegEnti.getOrgEnteSiam()
                            .equals(appartCollegEnti.getOrgCollegEntiConvenz().getOrgEnteSiam())) {
                        appartCollegEntiRowBean.setString("fl_capofila", "1");
                    } else {
                        appartCollegEntiRowBean.setString("fl_capofila", "0");
                    }
                    appartCollegEntiTableBean.add(appartCollegEntiRowBean);
                }
            } catch (Exception e) {
                LOGGER.error("Errore durante il recupero degli enti associati al collegamento avente id "
                        + idCollegEntiConvenz + " " + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero degli enti associati al collegamento avente id "
                        + idCollegEntiConvenz);
            }
        }
        return appartCollegEntiTableBean;
    }

    /**
     * Ritorna il rowBean relativo all'entity orgAppartCollegEnti dato il suo id
     *
     * @param idAppartCollegEnti
     *            id ente convenizonato
     *
     * @return row bean {@link OrgAppartCollegEntiRowBean}
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgAppartCollegEntiRowBean getOrgAppartCollegEntiRowBean(BigDecimal idAppartCollegEnti)
            throws ParerUserError {
        OrgAppartCollegEnti appartCollegEnti = helper.findById(OrgAppartCollegEnti.class, idAppartCollegEnti);
        OrgAppartCollegEntiRowBean row = null;
        try {
            row = (OrgAppartCollegEntiRowBean) Transform.entity2RowBean(appartCollegEnti);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero dell'appartenenza del collegamento "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(msg);
        }
        return row;
    }

    public List<OrgAppartCollegEnti> getOrgAppartCollegEntiList(BigDecimal idCollegEntiConvenz) throws ParerUserError {
        List<OrgAppartCollegEnti> appartCollegEntiList = helper.getOrgAppartCollegEntiList(idCollegEntiConvenz);
        return appartCollegEntiList;
    }

    /**
     * Metodo di eliminazione di un disciplinare tecnico
     *
     * @param param
     *            parametri per il logging
     * @param idDiscipStrut
     *            id disciplinare
     *
     * @throws ParerUserError
     *             errore generico
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteOrgDiscipStrut(LogParam param, BigDecimal idDiscipStrut) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del disciplinare tecnico");
        try {
            OrgDiscipStrut discipStrut = helper.findById(OrgDiscipStrut.class, idDiscipStrut);
            BigDecimal idEnteConvenz = BigDecimal.valueOf(discipStrut.getOrgEnteConvenz().getIdEnteSiam());
            helper.removeEntity(discipStrut, true);
            sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
                    param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO, idEnteConvenz,
                    param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del disciplinare tecnico ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    public void deleteFileDiscip(BigDecimal idDiscipStrut) throws ParerUserError {
        LOGGER.debug("Eseguo l'eliminazione del file del disciplinare tecnico");
        try {
            OrgDiscipStrut discipStrut = helper.findById(OrgDiscipStrut.class, idDiscipStrut);
            discipStrut.setBlDiscipStrut(null);
            // sacerLogEjb.log(param.getTransactionLogContext(), param.getNomeApplicazione(), param.getNomeUtente(),
            // param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO, new
            // BigDecimal(servizioErog.getOrgAccordoEnte().getOrgEnteConvenz().getIdEnteConvenz()),
            // param.getNomePagina());
        } catch (Exception e) {
            String messaggio = "Eccezione imprevista nell'eliminazione del file del disciplinare tecnico ";
            messaggio += ExceptionUtils.getRootCauseMessage(e);
            LOGGER.error(messaggio, e);
            throw new ParerUserError(messaggio);
        }
    }

    // BigDecimal
    public boolean existsFileDiscip(BigDecimal idDiscipStrut) throws ParerUserError {
        OrgDiscipStrut discipStrut = helper.findById(OrgDiscipStrut.class, idDiscipStrut);
        return discipStrut.getBlDiscipStrut() != null;
    }

    public Object[] getFileDisciplinareTecnico(BigDecimal idDiscipStrut) {
        OrgDiscipStrut discipStrut = helper.findById(OrgDiscipStrut.class, idDiscipStrut);// OrgDiscipStrut
        Object[] dt = new Object[2];
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String data = formatter.format(discipStrut.getDtDiscipStrut());// data
        // MEV #16785
        // dt[0] = data + "_" + discipStrut.getIdDiscipStrut() + ".pdf";
        data = data.replace(":", "_");
        dt[0] = "disciplinare_" + discipStrut.getUsrOrganizIam().getUsrOrganizIam().getNmOrganiz() + "_"
                + discipStrut.getUsrOrganizIam().getNmOrganiz() + "_" + data + ".pdf";
        dt[1] = discipStrut.getBlDiscipStrut();
        return dt;
    }

    public int getNumMaxGestAccordoEnte(BigDecimal idAccordoEnte) {
        return helper.getNumMaxGestAccordoEnte(idAccordoEnte);
    }

    /**
     * Restituisce un array di object con i tablebean dei parametri
     *
     * @param idAmbienteEnteConvenz
     *            id ambiente/ente convenzionato
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tablebean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public Object[] getIamParamApplicEnte(BigDecimal idAmbienteEnteConvenz, BigDecimal idEnteConvenz)
            throws ParerUserError {
        Object[] parametriObj = new Object[3];
        IamParamApplicTableBean paramApplicAmministrazioneTableBean = new IamParamApplicTableBean();
        IamParamApplicTableBean paramApplicGestioneTableBean = new IamParamApplicTableBean();
        IamParamApplicTableBean paramApplicConservazioneTableBean = new IamParamApplicTableBean();
        // Ricavo la lista dei parametri definiti per l'ENTE
        List<IamParamApplic> paramApplicList = helper.getIamParamApplicListEnte();
        if (!paramApplicList.isEmpty()) {
            try {
                // Per ogni parametro, popolo i valori su applicazione, ambiente ed ente ricavandoli da
                // IAM_VALORE_PARAM_APPLIC
                for (IamParamApplic paramApplic : paramApplicList) {
                    IamParamApplicRowBean paramApplicRowBean = new IamParamApplicRowBean();
                    paramApplicRowBean = (IamParamApplicRowBean) Transform.entity2RowBean(paramApplic);
                    populateParamApplicRowBean(paramApplicRowBean, idAmbienteEnteConvenz, idEnteConvenz,
                            paramApplic.getTiGestioneParam());
                    switch (paramApplic.getTiGestioneParam()) {
                    case "amministrazione":
                        paramApplicAmministrazioneTableBean.add(paramApplicRowBean);
                        break;
                    case "gestione":
                        paramApplicGestioneTableBean.add(paramApplicRowBean);
                        break;
                    case "conservazione":
                        paramApplicConservazioneTableBean.add(paramApplicRowBean);
                        break;
                    default:
                        break;
                    }
                }

            } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException
                    | NoSuchMethodException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dei parametri sull'ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero dei parametri sull'ente convenzionato");
            }
        }
        parametriObj[0] = paramApplicAmministrazioneTableBean;
        parametriObj[1] = paramApplicGestioneTableBean;
        parametriObj[2] = paramApplicConservazioneTableBean;
        return parametriObj;
    }

    /**
     * Restituisce un array di object con i tablebean dei parametri
     *
     * @param idAmbienteEnteConvenz
     *            id ambiente/ente convenzionato
     *
     * @return il tablebean
     *
     * @throws ParerUserError
     *             errore generico
     */
    public Object[] getIamParamApplicAmbiente(BigDecimal idAmbienteEnteConvenz) throws ParerUserError {
        Object[] parametriObj = new Object[3];
        IamParamApplicTableBean paramApplicAmministrazioneTableBean = new IamParamApplicTableBean();
        IamParamApplicTableBean paramApplicGestioneTableBean = new IamParamApplicTableBean();
        IamParamApplicTableBean paramApplicConservazioneTableBean = new IamParamApplicTableBean();
        // Ricavo la lista dei parametri definiti per l'AMBIENTE
        List<IamParamApplic> paramApplicList = helper.getIamParamApplicListAmbiente();
        if (!paramApplicList.isEmpty()) {
            try {
                // Per ogni parametro, popolo i valori su applicazione ed ambiente ricavandoli da
                // IAM_VALORE_PARAM_APPLIC
                for (IamParamApplic paramApplic : paramApplicList) {
                    IamParamApplicRowBean paramApplicRowBean = new IamParamApplicRowBean();
                    paramApplicRowBean = (IamParamApplicRowBean) Transform.entity2RowBean(paramApplic);
                    populateParamApplicAmbienteRowBean(paramApplicRowBean, idAmbienteEnteConvenz,
                            paramApplic.getTiGestioneParam());
                    switch (paramApplic.getTiGestioneParam()) {
                    case "amministrazione":
                        paramApplicAmministrazioneTableBean.add(paramApplicRowBean);
                        break;
                    case "gestione":
                        paramApplicGestioneTableBean.add(paramApplicRowBean);
                        break;
                    case "conservazione":
                        paramApplicConservazioneTableBean.add(paramApplicRowBean);
                        break;
                    default:
                        break;
                    }
                }
            } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException
                    | NoSuchMethodException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dei parametri sull'ambiente ente convenzionato "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError("Errore durante il recupero dei parametri sull'ambiente ente convenzionato");
            }
        }
        parametriObj[0] = paramApplicAmministrazioneTableBean;
        parametriObj[1] = paramApplicGestioneTableBean;
        parametriObj[2] = paramApplicConservazioneTableBean;
        return parametriObj;
    }

    private void populateParamApplicRowBean(IamParamApplicRowBean paramApplicRowBean, BigDecimal idAmbienteEnteConvenz,
            BigDecimal idEnteConvenz, String tiGestioneParam) {
        String nomeCampoEnte = tiGestioneParam.equals("amministrazione") ? "ds_valore_param_applic_ente_amm"
                : tiGestioneParam.equals("gestione") ? "ds_valore_param_applic_ente_gest"
                        : "ds_valore_param_applic_ente_cons";

        // Determino i valori su applicazione, ambiente ed ente
        IamValoreParamApplic valoreParamApplicApplic = helper
                .getIamValoreParamApplic(paramApplicRowBean.getIdParamApplic(), "APPLIC", null, null);
        if (valoreParamApplicApplic != null) {
            paramApplicRowBean.setString("ds_valore_param_applic_applic",
                    valoreParamApplicApplic.getDsValoreParamApplic());
        }

        if (idAmbienteEnteConvenz != null) {
            IamValoreParamApplic valoreParamApplicAmbiente = helper.getIamValoreParamApplic(
                    paramApplicRowBean.getIdParamApplic(), "AMBIENTE", idAmbienteEnteConvenz, null);
            if (valoreParamApplicAmbiente != null) {
                paramApplicRowBean.setString("ds_valore_param_applic_ambiente",
                        valoreParamApplicAmbiente.getDsValoreParamApplic());
            }
        }

        if (idEnteConvenz != null) {
            IamValoreParamApplic valoreParamApplicEnte = helper
                    .getIamValoreParamApplic(paramApplicRowBean.getIdParamApplic(), "ENTE", null, idEnteConvenz);
            if (valoreParamApplicEnte != null) {
                paramApplicRowBean.setString(nomeCampoEnte, valoreParamApplicEnte.getDsValoreParamApplic());
                paramApplicRowBean.setBigDecimal("id_valore_param_applic",
                        BigDecimal.valueOf(valoreParamApplicEnte.getIdValoreParamApplic()));
            }
        }
    }

    private void populateParamApplicAmbienteRowBean(IamParamApplicRowBean paramApplicRowBean,
            BigDecimal idAmbienteEnteConvenz, String tiGestioneParam) {
        String nomeCampoAmbiente = tiGestioneParam.equals("amministrazione") ? "ds_valore_param_applic_ambiente_amm"
                : tiGestioneParam.equals("gestione") ? "ds_valore_param_applic_ambiente_gest"
                        : "ds_valore_param_applic_ambiente_cons";

        // Determino i valori su applicazione ed ambiente
        IamValoreParamApplic valoreParamApplicApplic = helper
                .getIamValoreParamApplic(paramApplicRowBean.getIdParamApplic(), "APPLIC", null, null);
        if (valoreParamApplicApplic != null) {
            paramApplicRowBean.setString("ds_valore_param_applic_applic",
                    valoreParamApplicApplic.getDsValoreParamApplic());
        }

        if (idAmbienteEnteConvenz != null) {
            IamValoreParamApplic valoreParamApplicAmbiente = helper.getIamValoreParamApplic(
                    paramApplicRowBean.getIdParamApplic(), "AMBIENTE", idAmbienteEnteConvenz, null);
            if (valoreParamApplicAmbiente != null) {
                paramApplicRowBean.setString(nomeCampoAmbiente, valoreParamApplicAmbiente.getDsValoreParamApplic());
                paramApplicRowBean.setBigDecimal("id_valore_param_applic",
                        BigDecimal.valueOf(valoreParamApplicAmbiente.getIdValoreParamApplic()));
            }
        }
    }

    public BaseTable getTiParamApplicBaseTable() {
        BaseTable table = new BaseTable();
        List<String> tiParamApplicList = helper.getTiParamApplic();
        if (tiParamApplicList != null && !tiParamApplicList.isEmpty()) {
            try {
                for (String row : tiParamApplicList) {
                    BaseRowInterface r = new BaseRow();
                    r.setString(IamParamApplicTableDescriptor.COL_TI_PARAM_APPLIC, row);
                    table.add(r);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return table;
    }

    public BaseTable getTiGestioneParamBaseTable() {
        BaseTable table = new BaseTable();
        List<String> tiGestioneParamList = helper.getTiGestioneParam();
        if (tiGestioneParamList != null && !tiGestioneParamList.isEmpty()) {
            try {
                for (String row : tiGestioneParamList) {
                    BaseRowInterface r = new BaseRow();
                    r.setString(IamParamApplicTableDescriptor.COL_TI_GESTIONE_PARAM, row);
                    table.add(r);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return table;
    }

    public IamParamApplicTableBean getIamParamApplicTableBean(String tiParamApplic, String tiGestioneParam,
            String flAppartApplic, String flAppartAmbiente, String flApparteEnte) {
        IamParamApplicTableBean paramApplicTableBean = new IamParamApplicTableBean();
        List<IamParamApplic> paramApplicList = helper.getIamParamApplicList(tiParamApplic, tiGestioneParam,
                flAppartApplic, flAppartAmbiente, flApparteEnte);

        try {
            if (paramApplicList != null && !paramApplicList.isEmpty()) {
                for (IamParamApplic paramApplic : paramApplicList) {
                    IamParamApplicRowBean paramApplicRowBean = (IamParamApplicRowBean) Transform
                            .entity2RowBean(paramApplic);
                    paramApplicRowBean.setString("ds_valore_param_applic", "");
                    for (IamValoreParamApplic valoreParamApplic : paramApplic.getIamValoreParamApplics()) {
                        if (valoreParamApplic.getTiAppart().equals("APPLIC")) {
                            paramApplicRowBean.setString("ds_valore_param_applic",
                                    valoreParamApplic.getDsValoreParamApplic());
                        }
                    }
                    paramApplicTableBean.add(paramApplicRowBean);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return paramApplicTableBean;
    }

    /**
     * Esegue il salvataggio del rowBean del parametro di configurazione
     *
     * @param row
     *            il rowBean da salvare su DB
     *
     * @return true in mancanza di eccezioni
     */
    public boolean saveConfiguration(IamParamApplicRowBean row) {
        boolean result = false;
        IamParamApplic config;
        boolean newRow;

        try {

            if (row.getIdParamApplic() != null) {
                config = helper.findById(IamParamApplic.class, row.getIdParamApplic().longValue());
                newRow = false;
            } else {
                config = new IamParamApplic();
                newRow = true;
                // // TODO DA TOGLIERE
                // config.setDsValoreParamApplic("valore fittizio");
            }

            config.setTiValoreParamApplic(row.getTiValoreParamApplic());
            config.setTiParamApplic(row.getTiParamApplic());
            config.setTiGestioneParam(row.getTiGestioneParam());
            config.setNmParamApplic(row.getNmParamApplic());
            config.setDsListaValoriAmmessi(row.getDsListaValoriAmmessi());
            config.setDsParamApplic(row.getDsParamApplic());
            config.setFlAppartApplic(row.getFlAppartApplic());
            config.setFlAppartAmbiente(row.getFlAppartAmbiente());
            config.setFlApparteEnte(row.getFlApparteEnte());

            if (newRow) {
                helper.getEntityManager().persist(config);
                row.setIdParamApplic(BigDecimal.valueOf(config.getIdParamApplic()));
            }
            result = true;
            helper.getEntityManager().flush();

            // GESTIONE DS_VALORE_PARAM_APPLIC
            // Se \u00E8 una nuova riga di IamParamApplic, nel caso sia stato inserito il valore, vai a persisterlo
            if (newRow) {
                if (row.getString("ds_valore_param_applic") != null
                        && !row.getString("ds_valore_param_applic").equals("")) {
                    IamValoreParamApplic valore = new IamValoreParamApplic();
                    valore.setIamParamApplic(config);
                    valore.setTiAppart("APPLIC");
                    valore.setOrgAmbienteEnteConvenz(null);
                    valore.setOrgEnteSiam(null);
                    valore.setDsValoreParamApplic(row.getString("ds_valore_param_applic"));
                    helper.getEntityManager().persist(valore);
                }
            } else {
                // Se invece la riga di IamParamApplic giÃƒÆ’Ã‚Â  esisteva:
                // Se c'\u00E8 un valore parametro di tipo APPLIC, modificalo
                IamValoreParamApplic valoreParamApplic = helper.getIamValoreParamApplic(config.getIdParamApplic(),
                        "APPLIC");
                if (valoreParamApplic != null) {
                    if (row.getString("ds_valore_param_applic") != null
                            && !row.getString("ds_valore_param_applic").equals("")) {
                        valoreParamApplic.setDsValoreParamApplic(row.getString("ds_valore_param_applic"));
                    } else {
                        helper.removeEntity(valoreParamApplic, true);
                    }
                } else {
                    if (row.getString("ds_valore_param_applic") != null
                            && !row.getString("ds_valore_param_applic").equals("")) {
                        IamValoreParamApplic valore = new IamValoreParamApplic();
                        valore.setIamParamApplic(config);
                        valore.setTiAppart("APPLIC");
                        valore.setOrgAmbienteEnteConvenz(null);
                        valore.setOrgEnteSiam(null);
                        valore.setDsValoreParamApplic(row.getString("ds_valore_param_applic"));
                        helper.getEntityManager().persist(valore);
                    }
                }
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * Rimuove la riga di parametri definita nel rowBean
     *
     * @param row
     *            il parametro da eliminare
     *
     * @return true se eliminato con successo
     */
    public boolean deleteIamParamApplicRowBean(IamParamApplicRowBean row) {
        IamParamApplic config;
        boolean result = false;
        try {
            config = (IamParamApplic) helper.findById(IamParamApplic.class, row.getIdParamApplic().longValue());
            // Rimuovo il record
            helper.getEntityManager().remove(config);
            helper.getEntityManager().flush();
            result = true;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return result;
    }

    public boolean checkParamApplic(String nmParamApplic, BigDecimal idParamApplic) {
        return helper.existsIamParamApplic(nmParamApplic, idParamApplic);
    }

    /**
     * Rimuove la riga di parametri definita nel rowBean
     *
     * @param param
     *            parametri per il logging
     * @param idValoreParamApplic
     *            id valore paramtro applicativo
     *
     * @return true se eliminato con successo
     */
    public boolean deleteParametro(LogParam param, BigDecimal idValoreParamApplic) {
        IamValoreParamApplic parametro;
        Long idEnteConvenz = null;
        boolean result = false;
        try {
            parametro = (IamValoreParamApplic) helper.findById(IamValoreParamApplic.class, idValoreParamApplic);
            if (parametro.getOrgEnteSiam() != null) {
                idEnteConvenz = parametro.getOrgEnteSiam().getIdEnteSiam();
            }
            // Rimuovo il record
            helper.getEntityManager().remove(parametro);
            helper.getEntityManager().flush();
            if (param != null && idEnteConvenz != null) {
                sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                        param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_ENTE_CONVENZIONATO,
                        new BigDecimal(idEnteConvenz), param.getNomePagina());
            }
            result = true;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return result;
    }

    /**
     * Rimuove la riga di parametri d'ambiente definita nel rowBean
     *
     * @param param
     *            parametri per il logging
     * @param idValoreParamApplic
     *            id valore parametro
     *
     * @return true se eliminato con successo
     */
    public boolean deleteParametroAmb(LogParam param, BigDecimal idValoreParamApplic) {
        IamValoreParamApplic parametro;
        Long idAmbienteEnteConvenz = null;
        boolean result = false;
        try {
            parametro = (IamValoreParamApplic) helper.findById(IamValoreParamApplic.class, idValoreParamApplic);
            if (parametro.getOrgAmbienteEnteConvenz() != null) {
                idAmbienteEnteConvenz = parametro.getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz();
            }
            // Rimuovo il record
            helper.getEntityManager().remove(parametro);
            helper.getEntityManager().flush();
            if (param != null && idAmbienteEnteConvenz != null) {
                sacerLogEjb.log(param.getTransactionLogContext(), paramHelper.getParamApplicApplicationName(),
                        param.getNomeUtente(), param.getNomeAzione(), SacerLogConstants.TIPO_OGGETTO_AMB_ENTE_CONVENZ,
                        new BigDecimal(idAmbienteEnteConvenz), param.getNomePagina());
            }
            result = true;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return result;
    }

    /**
     * Ritorna il tableBean contenente per la lista dei fornitori esterni associati all'ente convenzionato passato in
     * ingresso
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param tiEnteNonConvenz
     *            tipo ente non convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgSuptEsternoEnteConvenzTableBean getOrgSuptEsternoEnteConvenzTableBean(BigDecimal idEnteConvenz,
            String tiEnteNonConvenz) throws ParerUserError {
        OrgSuptEsternoEnteConvenzTableBean table = new OrgSuptEsternoEnteConvenzTableBean();
        List<OrgSuptEsternoEnteConvenz> list = helper.retrieveOrgSuptEsternoEnteConvenz(idEnteConvenz,
                tiEnteNonConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgSuptEsternoEnteConvenz suptEsternoEnteConvenz : list) {
                    OrgSuptEsternoEnteConvenzRowBean row = new OrgSuptEsternoEnteConvenzRowBean();
                    row = (OrgSuptEsternoEnteConvenzRowBean) Transform.entity2RowBean(suptEsternoEnteConvenz);
                    row.setBigDecimal("id_ente_siam", BigDecimal
                            .valueOf(suptEsternoEnteConvenz.getOrgEnteSiamByIdEnteFornitEst().getIdEnteSiam()));
                    row.setString("nm_ente_siam",
                            suptEsternoEnteConvenz.getOrgEnteSiamByIdEnteFornitEst().getNmEnteSiam());

                    // MEV 29931
                    // Verifico l'esistenza di utenti che sarebbero coinvolti nella cancellazione del fornitore esterno
                    // che offre supporto per ricavare l'informazione circa le abilitazioni concesse
                    if (encEjb.existUsrUserEnteSiamSupt(
                            BigDecimal.valueOf(suptEsternoEnteConvenz.getIdSuptEstEnteConvenz()))) {
                        row.setString("fl_abilitazioni_concesse", "1");
                    } else {
                        row.setString("fl_abilitazioni_concesse", "0");
                    }

                    table.add(row);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista dei fornitori esterni"
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    /**
     * Ritorna il tableBean contenente per la lista degli organi di vigilanza associati all'ente convenzionato passato
     * in ingresso
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return il tableBean della lista
     *
     * @throws ParerUserError
     *             errore generico
     */
    public OrgVigilEnteProdutTableBean getOrgVigilEnteProdutTableBean(BigDecimal idEnteConvenz) throws ParerUserError {
        OrgVigilEnteProdutTableBean table = new OrgVigilEnteProdutTableBean();
        List<OrgVigilEnteProdut> list = helper.retrieveOrgVigilEnteProdut(idEnteConvenz);
        if (list != null && !list.isEmpty()) {
            try {
                for (OrgVigilEnteProdut orgVigilEnteProdut : list) {
                    OrgVigilEnteProdutRowBean row = new OrgVigilEnteProdutRowBean();
                    row = (OrgVigilEnteProdutRowBean) Transform.entity2RowBean(orgVigilEnteProdut);
                    row.setBigDecimal("id_ente_siam", BigDecimal.valueOf(orgVigilEnteProdut.getOrgAccordoVigil()
                            .getOrgEnteSiamByIdEnteOrganoVigil().getIdEnteSiam()));
                    row.setString("nm_ente_siam", orgVigilEnteProdut.getOrgAccordoVigil()
                            .getOrgEnteSiamByIdEnteOrganoVigil().getNmEnteSiam());
                    table.add(row);
                }
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException ex) {
                String msg = "Errore durante il recupero della lista degli organi di vigilanza"
                        + ExceptionUtils.getRootCauseMessage(ex);
                LOGGER.error(msg, ex);
                throw new ParerUserError(msg);
            }
        }
        return table;
    }

    public BaseTable getAmbientiDaUsrVLisEntiSiamCreaUserTableBean(BigDecimal idRichGestUser, long idUserIamCor) {
        BaseTable ambientiAppartenenzaUtenteTableBean = new BaseTable();
        List<Object[]> ambientiAppartenenzaUtenteList = helper.getAmbietiDaUsrVLisEntiSiamCreaUserList(idRichGestUser,
                idUserIamCor);
        if (!ambientiAppartenenzaUtenteList.isEmpty()) {
            try {
                for (Object[] ambienteObj : ambientiAppartenenzaUtenteList) {
                    BaseRow row = new BaseRow();
                    row.setBigDecimal("id_ambiente_ente_convenz", (BigDecimal) ambienteObj[0]);
                    row.setString("nm_ambiente_ente_convenz", (String) ambienteObj[1]);
                    ambientiAppartenenzaUtenteTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return ambientiAppartenenzaUtenteTableBean;
    }

    public BaseTable getAmbientiDaUsrVLisEntiSiamAppEnteTableBean(long idUserIamCor) {
        BaseTable ambientiAppartenenzaUtenteTableBean = new BaseTable();
        List<Object[]> ambientiAppartenenzaUtenteList = helper.getAmbietiDaUsrVLisEntiSiamAppEnteList(idUserIamCor);
        if (!ambientiAppartenenzaUtenteList.isEmpty()) {
            try {
                for (Object[] ambienteObj : ambientiAppartenenzaUtenteList) {
                    BaseRow row = new BaseRow();
                    row.setBigDecimal("id_ambiente_ente_convenz", (BigDecimal) ambienteObj[0]);
                    row.setString("nm_ambiente_ente_convenz", (String) ambienteObj[1]);
                    ambientiAppartenenzaUtenteTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return ambientiAppartenenzaUtenteTableBean;
    }

    public BaseTable getAmbientiDaUsrVLisEntiSiamPerAzioTableBean(BigDecimal idRichGestUser, long idUserIamCor) {
        BaseTable ambientiAppartenenzaUtenteTableBean = new BaseTable();
        List<Object[]> ambientiAppartenenzaUtenteList = helper.getAmbietiDaUsrVLisEntiSiamPerAzioList(idRichGestUser,
                idUserIamCor);
        if (!ambientiAppartenenzaUtenteList.isEmpty()) {
            try {
                for (Object[] ambienteObj : ambientiAppartenenzaUtenteList) {
                    BaseRow row = new BaseRow();
                    row.setBigDecimal("id_ambiente_ente_convenz", (BigDecimal) ambienteObj[0]);
                    row.setString("nm_ambiente_ente_convenz", (String) ambienteObj[1]);
                    ambientiAppartenenzaUtenteTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return ambientiAppartenenzaUtenteTableBean;
    }

    public BaseTable getEntiDaUsrVLisEntiSiamCreaUserTableBean(BigDecimal idRichGestUser, long idUserIamCor,
            BigDecimal idAmbienteEnteConvenz) {
        BaseTable entiAppartenenzaUtenteTableBean = new BaseTable();
        List<Object[]> entiAppartenenzaUtenteList = helper.getEntiDaUsrVLisEntiSiamCreaUserList(idRichGestUser,
                idUserIamCor, idAmbienteEnteConvenz);
        if (!entiAppartenenzaUtenteList.isEmpty()) {
            try {
                for (Object[] enteObj : entiAppartenenzaUtenteList) {
                    BaseRow row = new BaseRow();
                    row.setBigDecimal("id_ente_siam", (BigDecimal) enteObj[0]);
                    row.setString("nm_ente_siam", (String) enteObj[1]);
                    entiAppartenenzaUtenteTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return entiAppartenenzaUtenteTableBean;
    }

    public BaseTable getEntiDaUsrVLisEntiSiamPerAzioTableBean(BigDecimal idRichGestUser, long idUserIamCor,
            BigDecimal idAmbienteEnteConvenz) {
        BaseTable entiAppartenenzaUtenteTableBean = new BaseTable();
        List<Object[]> entiAppartenenzaUtenteList = helper.getEntiDaUsrVLisEntiSiamPerAzioList(idRichGestUser,
                idUserIamCor, idAmbienteEnteConvenz);
        if (!entiAppartenenzaUtenteList.isEmpty()) {
            try {
                for (Object[] enteObj : entiAppartenenzaUtenteList) {
                    BaseRow row = new BaseRow();
                    row.setBigDecimal("id_ente_siam", (BigDecimal) enteObj[0]);
                    row.setString("nm_ente_siam", (String) enteObj[1]);
                    entiAppartenenzaUtenteTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return entiAppartenenzaUtenteTableBean;
    }

    public BaseTable getEntiDaUsrVLisEntiSiamAppEnteTableBean(long idUserIamCor, BigDecimal idAmbienteEnteConvenz) {
        BaseTable entiAppartenenzaUtenteTableBean = new BaseTable();
        List<Object[]> entiAppartenenzaUtenteList = helper.getEntiDaUsrVLisEntiSiamAppEnteList(idUserIamCor,
                idAmbienteEnteConvenz);
        if (!entiAppartenenzaUtenteList.isEmpty()) {
            try {
                for (Object[] enteObj : entiAppartenenzaUtenteList) {
                    BaseRow row = new BaseRow();
                    row.setBigDecimal("id_ente_siam", (BigDecimal) enteObj[0]);
                    row.setString("nm_ente_siam", (String) enteObj[1]);
                    entiAppartenenzaUtenteTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return entiAppartenenzaUtenteTableBean;
    }

    public BaseTable getAmbientiDaUsrVLisEntiModifAppEnteTableBean(BigDecimal idRichGestUser, long idUserIamCor,
            BigDecimal idUserIamModif) {
        BaseTable ambientiAppartenenzaUtenteTableBean = new BaseTable();
        List<Object[]> ambientiAppartenenzaUtenteList = helper.getAmbientiDaUsrVLisEntiModifAppEnteList(idRichGestUser,
                idUserIamCor, idUserIamModif);
        if (!ambientiAppartenenzaUtenteList.isEmpty()) {
            try {
                for (Object[] ambienteObj : ambientiAppartenenzaUtenteList) {
                    BaseRow row = new BaseRow();
                    if (ambienteObj[0] != null) {
                        row.setBigDecimal("id_ambiente_ente_convenz", (BigDecimal) ambienteObj[0]);
                        row.setString("nm_ambiente_ente_convenz", (String) ambienteObj[1]);
                        ambientiAppartenenzaUtenteTableBean.add(row);
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return ambientiAppartenenzaUtenteTableBean;
    }

    public BaseTable getEntiDaUsrVLisEntiModifAppEnteTableBean(BigDecimal idRichGestUser, long idUserIamCor,
            BigDecimal idUserIamModif, BigDecimal idAmbienteEnteConvenz) {
        BaseTable entiAppartenenzaUtenteTableBean = new BaseTable();
        List<Object[]> entiAppartenenzaUtenteList = helper.getEntiDaUsrVLisEntiModifAppEnteList(idRichGestUser,
                idUserIamCor, idUserIamModif, idAmbienteEnteConvenz);
        if (!entiAppartenenzaUtenteList.isEmpty()) {
            try {
                for (Object[] ambienteObj : entiAppartenenzaUtenteList) {
                    BaseRow row = new BaseRow();
                    row.setBigDecimal("id_ente_siam", (BigDecimal) ambienteObj[0]);
                    row.setString("nm_ente_siam", (String) ambienteObj[1]);
                    entiAppartenenzaUtenteTableBean.add(row);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return entiAppartenenzaUtenteTableBean;
    }

    /**
     * Metodo centralizzato per verificare che l'intervallo di validitÃƒÆ’Ã‚Â  di un'entitÃƒÆ’Ã‚Â  figlia sia coerente
     * con l'intervallo di validità dell'entità padre
     *
     * @param dtIniValPadre
     *            data inizio validita padre
     * @param dtFinValPadre
     *            data fine validita padre
     * @param dtIniValFiglio
     *            data inizio validita figlio
     * @param dtFinValFiglio
     *            data fine validita figlio
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
        // Se l'intervallo del figlio non \u00E8 incluso totalmente nell'intervallo del padre
        if (dtIniValPadre.after(dtIniValFiglio) || dtFinValPadre.before(dtFinValFiglio)) {
            throw new ParerUserError(
                    "La validit\u00E0  di " + nmFiglio + " non e' inclusa nella validit\u00E0  di " + nmPadre);
        }
    }

    /**
     * Metodo centralizzato per verificare che l'intervallo di validitÃƒÆ’Ã‚Â  di un accordo di un'entitÃƒÆ’Ã‚Â  figlia
     * sia coerente con l'intervallo di validitÃƒÆ’Ã‚Â  di un accordo dell'entitÃƒÆ’Ã‚Â  padre
     *
     * @param idEnteConvenzPadre
     *            id ente convenzionato padre
     * @param dtDecAccFiglio
     *            data decorrenza
     * @param dtFinValAccFiglio
     *            data fine validita
     * @param nmFiglio
     *            nome figlio
     *
     * @throws ParerUserError
     *             errore generico
     */
    public void checkInclusioneAccordoFiglio(BigDecimal idEnteConvenzPadre, Date dtDecAccFiglio, Date dtFinValAccFiglio,
            String nmFiglio) throws ParerUserError {
        // Se l'intervallo di validià dell'accordo del figlio non \u00E8 incluso totalmente nell'intervallo di
        // validità dell'accordo del padre
        String nmEnteConvenzPadre = helper.findById(OrgEnteSiam.class, idEnteConvenzPadre).getNmEnteSiam();
        Date dtDecAccordoMinPadre = helper.getDataDecorrenzaMinima(idEnteConvenzPadre);
        Date dtFineValidAccordoPadre = helper.getDataFineValiditaMassima(idEnteConvenzPadre);
        if ((dtDecAccordoMinPadre != null && dtDecAccordoMinPadre.after(dtDecAccFiglio))
                || (dtFineValidAccordoPadre != null && dtFineValidAccordoPadre.before(dtFinValAccFiglio))) {
            throw new ParerUserError("La validit\u00E0 dell'accordo di " + nmFiglio
                    + " non e' inclusa nella validit\u00E0  dell'accordo di " + nmEnteConvenzPadre);
        }
    }

    public void checkInclusionePadreFigli(Date dtIniValPadre, Date dtFinValPadre, String nmPadre,
            Map<String, Date[]> mappaFigli) throws ParerUserError {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (Map.Entry<String, Date[]> entry : mappaFigli.entrySet()) {
            String nmFiglio = entry.getKey();
            Date[] intervalloDateFiglio = entry.getValue();
            // Se l'intervallo del figlio non \u00E8 incluso totalmente nell'intervallo del padre
            if (dtIniValPadre.after(intervalloDateFiglio[0]) || dtFinValPadre.before(intervalloDateFiglio[1])) {
                sb.append(sep).append(nmFiglio);
                sep = ", ";
            }
        }
        if (sb.length() > 0) {
            throw new ParerUserError(
                    "La validit\u00E0  di " + sb.toString() + " non e' inclusa nella validit\u00E0  di " + nmPadre);
        }
    }

    public Long getIdTipoUnitaDoc(String nmEnte, String nmStrut, String nmTipoUd) {
        BigDecimal idStrut = utentiHelper.getIdOrganizIamByParam(nmEnte, nmStrut);
        return helper.getIdTipoUnitaDoc(idStrut, nmTipoUd);
    }

    public BigDecimal getIdStrut(String nmEnte, String nmStrut) {
        return utentiHelper.getIdOrganizIamByParam(nmEnte, nmStrut);
    }

    public BaseTable getTipiServizioAssociatiTariffarioPerAccordoDaCreare(BigDecimal idTariffario) {
        BaseTable table = new BaseTable();
        List<Object[]> tipiServizio = helper.getTipiServizioAssociatiTariffarioPerAccordoDaCreare(idTariffario);
        for (Object[] tipoServizio : tipiServizio) {
            BaseRow row = new BaseRow();
            row.setBigDecimal("id_tipo_servizio", BigDecimal.valueOf((Long) tipoServizio[0]));
            row.setString("cd_tipo_servizio", (String) tipoServizio[1]);
            table.add(row);
        }
        return table;
    }

    public BaseTable getTipiServizioAssociatiTariffarioPerAnnualitaDaCreare(BigDecimal idTariffario) {
        BaseTable table = new BaseTable();
        List<Object[]> tipiServizio = helper.getTipiServizioAssociatiTariffarioPerAnnualitaDaCreare(idTariffario);
        for (Object[] tipoServizio : tipiServizio) {
            BaseRow row = new BaseRow();
            row.setBigDecimal("id_tipo_servizio", BigDecimal.valueOf((Long) tipoServizio[0]));
            row.setString("cd_tipo_servizio", (String) tipoServizio[1]);
            table.add(row);
        }
        return table;
    }

    public BaseTable getTipiServizioImporto(BigDecimal idTariffario, BigDecimal idAccordoEnte) {
        BaseTable table = new BaseTable();
        List<Object[]> tipiServizio = helper.getTipiServizioDelTariffarioDelTipoAccordoGiaCreato(idTariffario,
                idAccordoEnte);
        // Creazione decimalFormatter: gestione via codice e non tramite framework per gestione dei campi editabili
        // delle liste editabili
        DecimalFormat decimalFormatter;
        DecimalFormatSymbols itSymbols = new DecimalFormatSymbols(Locale.ITALIAN);
        itSymbols.setDecimalSeparator(',');
        itSymbols.setGroupingSeparator('.');
        decimalFormatter = new DecimalFormat("#.######", itSymbols);
        decimalFormatter.setGroupingUsed(true);
        decimalFormatter.setGroupingSize(3);
        for (Object[] tipoServizio : tipiServizio) {
            BaseRow row = new BaseRow();
            row.setBigDecimal("id_tipo_servizio", BigDecimal.valueOf((Long) tipoServizio[0]));
            row.setString("cd_tipo_servizio", (String) tipoServizio[1]);
            row.setBigDecimal("id_tariffa_accordo", BigDecimal.valueOf((Long) tipoServizio[3]));
            if (row.getBigDecimal("id_tariffa_accordo").compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal imTariffaAccordo = (BigDecimal) tipoServizio[2];
                BigDecimal imponibile = ((BigDecimal) tipoServizio[2]);
                BigDecimal iva = imponibile.multiply(new BigDecimal(22).divide(new BigDecimal(100)));
                BigDecimal ivato = imponibile.add(iva);
                BigDecimal ivatoArrotondato = ivato.setScale(0, RoundingMode.DOWN);
                row.setBigDecimal("im_tariffa_accordo_ivato", ivatoArrotondato);
                // Formatto "all'italiana" il campo stringa im_tariffa_accordo recuperato come numerico da DB
                String imTariffaAccordoString = decimalFormatter.format(imTariffaAccordo);
                row.setString("im_tariffa_accordo", imTariffaAccordoString);
            }

            table.add(row);
        }
        return table;
    }

    public BaseTable getTipiServizioAnnualitaImporto(BigDecimal idTariffario, BigDecimal idAaAccordo) {
        BaseTable table = new BaseTable();
        List<Object[]> tipiServizio = helper.getTipiServizioDelTariffarioDelTipoAccordoAnnualitaGiaCreato(idTariffario,
                idAaAccordo);
        for (Object[] tipoServizio : tipiServizio) {
            BaseRow row = new BaseRow();
            row.setBigDecimal("id_tipo_servizio", BigDecimal.valueOf((Long) tipoServizio[0]));
            row.setString("cd_tipo_servizio", (String) tipoServizio[1]);
            row.setBigDecimal("id_tariffa_aa_accordo", BigDecimal.valueOf((Long) tipoServizio[3]));
            if (row.getBigDecimal("id_tariffa_aa_accordo").compareTo(BigDecimal.ZERO) != 0) {
                row.setBigDecimal("im_tariffa_aa_accordo", (BigDecimal) tipoServizio[2]);
            }
            table.add(row);
        }
        return table;
    }

    public OrgAaAccordoTableBean getOrgAaAccordoEnteTableBean(BigDecimal idTariffario, BigDecimal idAccordoEnte) {
        OrgAaAccordoTableBean table = new OrgAaAccordoTableBean();
        List<OrgAaAccordo> annualita = helper.getOrgAaAccordoList(idAccordoEnte);
        if (!annualita.isEmpty()) {
            try {
                for (OrgAaAccordo aaAccordo : annualita) {
                    OrgAaAccordoRowBean row = new OrgAaAccordoRowBean();
                    row = (OrgAaAccordoRowBean) Transform.entity2RowBean(aaAccordo);
                    row.setBigDecimal("tot_rimb_costi",
                            getTotRimbCosti(idTariffario, BigDecimal.valueOf(aaAccordo.getIdAaAccordo())));
                    table.add(row);
                }
                // table = (OrgAaAccordoTableBean) Transform.entities2TableBean(annualita);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero delle annualitÃ  sull'accordo "
                        + ExceptionUtils.getRootCauseMessage(e), e);
            }
        }
        return table;
    }

    public BigDecimal getTotRimbCosti(BigDecimal idTariffario, BigDecimal idAaAccordo) {
        List<Object[]> tipiServizio = helper.getTipiServizioDelTariffarioDelTipoAccordoAnnualitaGiaCreato(idTariffario,
                idAaAccordo);
        BigDecimal tot = null;
        for (Object[] tipoServizio : tipiServizio) {

            if ((BigDecimal) tipoServizio[2] != null
                    && ((BigDecimal) tipoServizio[2]).compareTo(BigDecimal.ZERO) != 0) {
                if (tot == null) {
                    tot = BigDecimal.ZERO;
                }
                tot = tot.add((BigDecimal) tipoServizio[2]);
            }
        }
        return tot;
    }

    public OrgAaAccordoRowBean getOrgAaAccordoRowBean(BigDecimal idAaAccordo) {
        OrgAaAccordoRowBean aaAccordoRowBean = new OrgAaAccordoRowBean();
        OrgAaAccordo aaAccordo = helper.findById(OrgAaAccordo.class, idAaAccordo);
        if (aaAccordo != null) {
            try {
                aaAccordoRowBean = (OrgAaAccordoRowBean) Transform.entity2RowBean(aaAccordo);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dell'annualit\u00E0  accordo "
                        + ExceptionUtils.getRootCauseMessage(e), e);
            }
        }
        return aaAccordoRowBean;
    }

    public boolean checkInsertAnnualita(String flPagamento, BigDecimal idTipoAccordo, Date dtScadAccordo) {

        if (idTipoAccordo != null) {
            OrgTipoAccordo tipoAccordo = helper.findById(OrgTipoAccordo.class, idTipoAccordo);
            if (flPagamento != null && flPagamento.equals("1")
                    && tipoAccordo.getCdAlgoTariffario().equals("NO_CLASSE_ENTE") && dtScadAccordo != null) {
                return true;
            }
        }
        return false;
    }

    private void insertAnnualitaAutomatiche(OrgAccordoEnte accordoEnte) {
        // MEV #21030
        if (accordoEnte.getFlPagamento().equals("1")
                && accordoEnte.getOrgTipoAccordo().getCdAlgoTariffario().equals("NO_CLASSE_ENTE")
                && accordoEnte.getDtFineValidAccordo() != null) {
            // Creo le annualità
            Calendar c = Calendar.getInstance();
            c.setTime(accordoEnte.getDtDecAccordo());
            int annoDecorrenza = c.get(Calendar.YEAR);
            int meseDecorrenza = c.get(Calendar.MONTH);
            c.setTime(accordoEnte.getDtFineValidAccordo());
            int annoScadenza = c.get(Calendar.YEAR);
            int meseScadenza = c.get(Calendar.MONTH);
            // Salvo in ORG_AA_ACCORDO
            int conteggioAnni = 0;
            boolean isUltimoAnno = false;
            for (int i = annoDecorrenza; i <= annoScadenza; i++) {
                conteggioAnni++;
                if (i == annoScadenza) {
                    isUltimoAnno = true;
                }
                // if (conteggioAnni <= 5) {
                OrgAaAccordo aaAccordo = new OrgAaAccordo();
                aaAccordo.setAaAnnoAccordo(BigDecimal.valueOf(i));
                if (conteggioAnni == 1) {
                    aaAccordo.setMmAaAccordo(BigDecimal.valueOf(12 - meseDecorrenza - 1));
                } else if (!isUltimoAnno) {
                    aaAccordo.setMmAaAccordo(BigDecimal.valueOf(12));
                } else {
                    aaAccordo.setMmAaAccordo(BigDecimal.valueOf(meseScadenza + 1));
                }
                if (accordoEnte.getOrgAaAccordos() == null) {
                    accordoEnte.setOrgAaAccordos(new ArrayList<>());
                }
                accordoEnte.getOrgAaAccordos().add(aaAccordo);
                aaAccordo.setOrgAccordoEnte(accordoEnte);
                helper.getEntityManager().persist(aaAccordo);
                helper.getEntityManager().flush();

                // }
            }
        }
    }

    private void insertTipiServizioCompilati(TipoServizioAccordoList tipiServizioCompilati, OrgAccordoEnte accordoEnte)
            throws ParerUserError {
        for (BaseRow rb : (BaseTable) tipiServizioCompilati.getTable()) {
            BigDecimal imTariffaAccordoBD;
            try {
                imTariffaAccordoBD = Casting.parseDecimal(rb.getString("im_tariffa_accordo"));
            } catch (EMFError ex) {
                throw new ParerUserError("Formato numerico tariffa accordo non valido (valore errato = "
                        + rb.getString("im_tariffa_accordo") + ")");
            }
            insertTipoServizioCompilato(accordoEnte, imTariffaAccordoBD, rb.getBigDecimal("id_tipo_servizio"));
        }
    }

    private void insertTipoServizioCompilato(OrgAccordoEnte accordoEnte, BigDecimal imTariffaAccordo,
            BigDecimal idTipoServizio) {
        if (imTariffaAccordo != null && !imTariffaAccordo.equals("")) {
            OrgTariffaAccordo tariffaAccordo = new OrgTariffaAccordo();
            tariffaAccordo.setImTariffaAccordo(imTariffaAccordo);
            tariffaAccordo.setOrgAccordoEnte(accordoEnte);
            tariffaAccordo.setOrgTipoServizio(helper.findById(OrgTipoServizio.class, idTipoServizio));
            helper.getEntityManager().persist(tariffaAccordo);
            helper.getEntityManager().flush();
        }
    }

    private void insertTipoServizioCompilatoAnnualita(OrgAaAccordo aaAccordo, String imTariffaAaAccordo,
            BigDecimal idTipoServizio) {
        if (imTariffaAaAccordo != null && !imTariffaAaAccordo.equals("")) {
            OrgTariffaAaAccordo tariffaAaAccordo = new OrgTariffaAaAccordo();
            tariffaAaAccordo.setImTariffaAaAccordo(new BigDecimal(imTariffaAaAccordo));
            tariffaAaAccordo.setOrgAaAccordo(aaAccordo);
            tariffaAaAccordo.setOrgTipoServizio(helper.findById(OrgTipoServizio.class, idTipoServizio));
            helper.getEntityManager().persist(tariffaAaAccordo);
            helper.getEntityManager().flush();
        }
    }

    private void manageTipiServizioAccordo(OrgAccordoEnte accordoEnte, TipoServizioAccordoList tipoServizioAccordoList,
            boolean serviziErogatiPresenti, boolean isEditMode) throws ParerUserError {
        if (isEditMode) {
            // Ricavo le Entità rimborso costi accordo salvate sul DB
            List<OrgTariffaAccordo> tabellaDB = helper
                    .getOrgTariffaAccordo(BigDecimal.valueOf(accordoEnte.getIdAccordoEnte()));

            // Controllo i tipi servizio per i quali non è possibile effettuare modifiche
            if (serviziErogatiPresenti) {
                Set<String> tipiServizioSet = new HashSet<>();
                tipiServizioSet.add("Attivazione documentazione amministrativa");
                tipiServizioSet.add("Attivazione documentazione sanitaria");
                tipiServizioSet.add("Canone documentazione amministrativa");
                tipiServizioSet.add("Canone documentazione sanitaria");
                tipiServizioSet.add("Canone studi diagnostici");
                for (BaseRow rb : (BaseTable) tipoServizioAccordoList.getTable()) {
                    String imTariffaAccordoString = rb.getString("im_tariffa_accordo");
                    BigDecimal imTariffaAccordo = BigDecimal.ZERO;
                    try {
                        if (imTariffaAccordoString != null && !imTariffaAccordoString.equals("")) {
                            // imTariffaAccordo = new BigDecimal(rb.getString("im_tariffa_accordo"));
                            try {
                                imTariffaAccordo = Casting.parseDecimal(rb.getString("im_tariffa_accordo"));
                            } catch (EMFError ex) {
                                throw new ParerUserError("Formato numerico tariffa accordo non valido (valore errato = "
                                        + rb.getString("im_tariffa_accordo") + ")");
                            }
                        }
                    } catch (NumberFormatException e) {
                        // Solo per mantenere l'importo tariffa a 0
                    }
                    // Eseguo il controllo sulla modificabilità dei tipi servizio
                    if (tipiServizioSet.contains(rb.getString("cd_tipo_servizio"))) {
                        for (OrgTariffaAccordo rb2 : tabellaDB) {
                            if (rb2.getOrgTipoServizio().getCdTipoServizio().equals(rb.getString("cd_tipo_servizio"))
                                    && BigDecimal.valueOf(rb2.getIdTariffaAccordo()).compareTo(imTariffaAccordo) != 0) {
                                throw new ParerUserError("Attenzione: non è possibile modificare l'importo per: "
                                        + rb2.getOrgTipoServizio().getCdTipoServizio());
                            }
                        }
                    }
                }
            }

            /* GESTIONE TIPI SERVIZIO: INSERIMENTO/MODIFICA/CANCELLAZIONE */
            for (BaseRow rb : (BaseTable) tipoServizioAccordoList.getTable()) {
                boolean trovato = false;
                for (OrgTariffaAccordo tariffaAccordo : tabellaDB) {
                    if (rb.getBigDecimal("id_tariffa_accordo")
                            .compareTo(BigDecimal.valueOf(tariffaAccordo.getIdTariffaAccordo())) == 0) {
                        // Modifica o cancella l'importo SENZA AVER CAMBIATO LA LISTA DEI TIPI SERVIZI
                        // a seguito di un cambiamento del tariffario, (ma solo l'importo)
                        String imTariffaAccordoString = rb.getString("im_tariffa_accordo");
                        BigDecimal imTariffaAccordo = BigDecimal.ZERO;
                        if (imTariffaAccordoString != null && !imTariffaAccordoString.equals("")) {
                            try {
                                imTariffaAccordo = Casting.parseDecimal(rb.getString("im_tariffa_accordo"));
                            } catch (EMFError ex) {
                                throw new ParerUserError("Formato numerico tariffa accordo non valido (valore errato = "
                                        + rb.getString("im_tariffa_accordo") + ")");
                            }
                        }

                        if (imTariffaAccordo.compareTo(BigDecimal.ZERO) != 0) {
                            OrgTariffaAccordo ta = helper.findById(OrgTariffaAccordo.class,
                                    rb.getBigDecimal("id_tariffa_accordo"));
                            try {
                                imTariffaAccordo = Casting.parseDecimal(rb.getString("im_tariffa_accordo"));

                                int parteIntera = imTariffaAccordo.intValue();
                                if (String.valueOf(parteIntera).length() < 9) {
                                    ta.setImTariffaAccordo(imTariffaAccordo);
                                } else {
                                    throw new EMFError("errore", "errore");
                                }
                            } catch (EMFError ex) {
                                throw new ParerUserError(
                                        "Formato numerico tariffa accordo non valido o lunghezza della parte intera del numero superiore al limite consentito");
                            }
                            // ta.setImTariffaAccordo(new BigDecimal(rb.getString("im_tariffa_accordo")));
                            trovato = true;
                        } else {
                            // Cancellazione
                            OrgTariffaAccordo ta = helper.findById(OrgTariffaAccordo.class,
                                    tariffaAccordo.getIdTariffaAccordo());
                            trovato = true;
                            helper.getEntityManager().remove(ta);
                            helper.getEntityManager().flush();
                        }
                    }
                }

                // Non avendo trovato corrispondenze con gli ID, significa che sto trattando nuovi Tipi servizio
                // quindi ne inserisco i valori
                if (!trovato) {
                    BigDecimal imTariffaAccordoBD;
                    try {
                        imTariffaAccordoBD = Casting.parseDecimal(rb.getString("im_tariffa_accordo"));
                    } catch (EMFError ex) {
                        throw new ParerUserError("Formato numerico tariffa accordo non valido (valore errato = "
                                + rb.getString("im_tariffa_accordo") + ")");
                    }
                    // Inserimento nuovo elemento
                    insertTipoServizioCompilato(accordoEnte, imTariffaAccordoBD, rb.getBigDecimal("id_tipo_servizio"));
                }
            }

            // Cancello i valori non più presenti
            for (OrgTariffaAccordo tariffaAccordo : tabellaDB) {
                boolean trovato = false;

                for (BaseRow rb2 : (BaseTable) tipoServizioAccordoList.getTable()) {
                    if (rb2.getBigDecimal("id_tariffa_accordo")
                            .compareTo(BigDecimal.valueOf(tariffaAccordo.getIdTariffaAccordo())) == 0) {
                        trovato = true;
                    }
                }
                if (!trovato) {
                    // Cancellazione
                    OrgTariffaAccordo ta = helper.findById(OrgTariffaAccordo.class,
                            tariffaAccordo.getIdTariffaAccordo());
                    helper.getEntityManager().remove(ta);
                    helper.getEntityManager().flush();
                }
            }

        }
    }

    private void manageTipiServizioAnnualitaAccordo(OrgAaAccordo aaAccordo,
            TipoServizioAnnualitaAccordoList tipoServizioAnnualitaAccordoList) throws ParerUserError {
        // Ricavo le Entità rimborso costi annualita accordo salvate sul DB
        List<OrgTariffaAaAccordo> tabellaDB = helper
                .getOrgTariffaAaAccordo(BigDecimal.valueOf(aaAccordo.getIdAaAccordo()));
        // imTariffaAaAccordoString
        /* GESTIONE TIPI SERVIZIO: INSERIMENTO/MODIFICA/CANCELLAZIONE */
        for (BaseRow rb : (BaseTable) tipoServizioAnnualitaAccordoList.getTable()) {
            boolean trovato = false;
            for (OrgTariffaAaAccordo tariffaAaAccordo : tabellaDB) {
                if (rb.getBigDecimal("id_tariffa_aa_accordo")
                        .compareTo(BigDecimal.valueOf(tariffaAaAccordo.getIdTariffaAaAccordo())) == 0) {
                    // Modifica o cancella l'importo SENZA AVER CAMBIATO LA LISTA DEI TIPI SERVIZI
                    // a seguito di un cambiamento del tariffario, (ma solo l'importo)
                    String imTariffaAaAccordoString = rb.getString("im_tariffa_aa_accordo");
                    BigDecimal imTariffaAaAccordo = BigDecimal.ZERO;
                    if (imTariffaAaAccordoString != null && !imTariffaAaAccordoString.equals("")) {
                        imTariffaAaAccordo = new BigDecimal(rb.getString("im_tariffa_aa_accordo"));
                    }

                    if (imTariffaAaAccordo.compareTo(BigDecimal.ZERO) != 0) {
                        OrgTariffaAaAccordo ta = helper.findById(OrgTariffaAaAccordo.class,
                                rb.getBigDecimal("id_tariffa_aa_accordo"));
                        ta.setImTariffaAaAccordo(new BigDecimal(rb.getString("im_tariffa_aa_accordo")));
                        trovato = true;
                    } else {
                        // Cancellazione
                        OrgTariffaAaAccordo ta = helper.findById(OrgTariffaAaAccordo.class,
                                tariffaAaAccordo.getIdTariffaAaAccordo());
                        trovato = true;
                        helper.getEntityManager().remove(ta);
                        helper.getEntityManager().flush();
                    }
                }
            }

            // Non avendo trovato corrispondenze con gli ID, significa che sto trattando nuovi Tipi servizio
            // quindi ne inserisco i valori
            if (!trovato) {
                // Inserimento nuovo elemento
                insertTipoServizioCompilatoAnnualita(aaAccordo, rb.getString("im_tariffa_aa_accordo"),
                        rb.getBigDecimal("id_tipo_servizio"));
            }
        }

        // Cancello i valori non più presenti
        for (OrgTariffaAaAccordo tariffaAaAccordo : tabellaDB) {
            boolean trovato = false;

            for (BaseRow rb2 : (BaseTable) tipoServizioAnnualitaAccordoList.getTable()) {
                if (rb2.getBigDecimal("id_tariffa_aa_accordo")
                        .compareTo(BigDecimal.valueOf(tariffaAaAccordo.getIdTariffaAaAccordo())) == 0) {
                    trovato = true;
                }
            }
            if (!trovato) {
                // Cancellazione
                OrgTariffaAaAccordo ta = helper.findById(OrgTariffaAaAccordo.class,
                        tariffaAaAccordo.getIdTariffaAaAccordo());
                helper.getEntityManager().remove(ta);
                helper.getEntityManager().flush();
            }
        }

    }

    public boolean existsDateSovrappostePrecAnagrafiche(BigDecimal idEnteConvenz, Date dtIniVal, Date dtFineVal) {
        OrgEnteSiam enteConvenz = helper.findById(OrgEnteSiam.class, idEnteConvenz);
        for (OrgStoEnteConvenz stoEnteConvenz : enteConvenz.getOrgStoEnteConvenzs()) {
            if ((stoEnteConvenz.getDtIniVal().compareTo(dtIniVal) <= 0
                    && stoEnteConvenz.getDtFineVal().compareTo(dtIniVal) >= 0)
                    || (stoEnteConvenz.getDtIniVal().compareTo(dtFineVal) <= 0
                            && stoEnteConvenz.getDtFineVal().compareTo(dtFineVal) >= 0)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Controllo che le date di validità di una Anagrafica siano contenute nella validità dell'ente convenzionato
     *
     * @param dtIniValEnte
     *            data inizio validita ente
     * @param dtFineValEnte
     *            data fine validita ente
     * @param dtIniVal
     *            data inizio validita anagrafica
     * @param dtFineVal
     *            data fine validita anagrafica
     *
     * @return true se le date dell'Anagrafica non rientrano nel range di validità dell'ente convenzionato
     */
    public boolean isDateAnagraficaOutOfRange(Date dtIniValEnte, Date dtFineValEnte, Date dtIniVal, Date dtFineVal) {
        return dtIniValEnte.compareTo(dtIniVal) > 0 || dtFineValEnte.compareTo(dtIniVal) < 0
                || dtIniValEnte.compareTo(dtFineVal) > 0 || dtFineValEnte.compareTo(dtFineVal) < 0;
    }

    /**
     * Ricavo la minima data di inizio validità e la massima data di fine validità di eventuali Anagrafiche presenti
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return un array contenente gli estremi delle date delle eventuali Anagrafiche
     */
    public Date[] getExtremeDatesAnagrafiche(BigDecimal idEnteConvenz) {
        Date[] extremeDatesAnagrafiche = new Date[2];
        extremeDatesAnagrafiche[0] = helper.getMinDateAnagrafica(idEnteConvenz);
        extremeDatesAnagrafiche[1] = helper.getMaxDateAnagrafica(idEnteConvenz);
        return extremeDatesAnagrafiche;
    }

    /**
     * Controlla che le data di inizio validità di una nuova Anagrafica che si sta inserendo siano contigua alla fine di
     * una eventuale anagrafica precedente
     *
     * @param dtIniValNuovaAnagrafica
     *            data inizio validita nuova anagrafica
     * @param idEnteConvenz
     *            identificativo ente convenzionato
     * @param idAnagraficaToExclude
     *            anagrafica da escludere
     *
     * @return true se la data di inizio nuova anagrafica è impostata sul giorno successivo all'ultima anagrafica
     *         inserita
     */
    public boolean isDateAnagraficaContinue(Date dtIniValNuovaAnagrafica, BigDecimal idEnteConvenz,
            BigDecimal idAnagraficaToExclude) {
        OrgStoEnteConvenz ultimaAnagrafica = helper.getOrgStoEnteConvenz(idEnteConvenz, idAnagraficaToExclude);
        if (ultimaAnagrafica != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(ultimaAnagrafica.getDtFineVal());
            c.add(Calendar.DATE, 1);
            Date dtIniValNuovaAnagraficaVerifica = c.getTime();
            return dtIniValNuovaAnagrafica.compareTo(dtIniValNuovaAnagraficaVerifica) == 0;
        }
        return true;
    }

    /**
     * Ritorna un rowBean rappresentante l'ente siam in base al nome passato in ingresso
     *
     * @param nmEnteSiam
     *            il nome dell'ente siam
     *
     * @return il rowBean
     */
    public OrgEnteSiamRowBean getOrgEnteSiamByName(String nmEnteSiam) {
        OrgEnteSiamRowBean enteSiamRowBean = new OrgEnteSiamRowBean();
        OrgEnteSiam enteSiam = helper.getOrgEnteConvenz(nmEnteSiam);
        if (enteSiam != null) {
            try {
                enteSiamRowBean = (OrgEnteSiamRowBean) Transform.entity2RowBean(enteSiam);
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero dell'ente siam " + ExceptionUtils.getRootCauseMessage(e), e);
            }
        }
        return enteSiamRowBean;
    }

    public int getNumStoricizzazioni(BigDecimal idEnteSiam) {
        OrgEnteSiam enteSiam = helper.findById(OrgEnteSiam.class, idEnteSiam);
        return enteSiam.getOrgStoEnteAmbienteConvenzs().size();
    }

    public OrgVRicAccordoEnteTableBean getOrgVRicAccordoEnteTableBean(BigDecimal idUserIamCor,
            BigDecimal idAmbienteEnteConvenz, String nmEnteConvenz, BigDecimal idAccordoEnte,
            String cdRegistroRepertorio, BigDecimal aaRepertorio, String cdKeyRepertorio,
            List<BigDecimal> idTipoAccordo, Date dtDecAccordoDa, Date dtDecAccordoA, Date dtFineValidAccordoDa,
            Date dtFineValidAccordoA, Date dtDecAccordoInfoDa, Date dtDecAccordoInfoA, Date dtScadAccordoDa,
            Date dtScadAccordoA, String flRecesso, List<BigDecimal> idTipoGestioneAccordo,
            String flEsisteNotaFatturazione, String flEsistonoSeAnnuali, Date saeDa, Date saeA, String flEsistonoSeUt,
            Date sueDa, Date sueA, String flFasciaManuale) throws ParerUserError {
        OrgVRicAccordoEnteTableBean ricAccordiEnteTableBean = new OrgVRicAccordoEnteTableBean();
        List<OrgVRicAccordoEnte> ricAccordiEnteList = helper.retrieveOrgVRicAccordoEnteList(idUserIamCor,
                idAmbienteEnteConvenz, nmEnteConvenz, idAccordoEnte, cdRegistroRepertorio, aaRepertorio,
                cdKeyRepertorio, idTipoAccordo, dtDecAccordoDa, dtDecAccordoA, dtFineValidAccordoDa,
                dtFineValidAccordoA, dtDecAccordoInfoDa, dtDecAccordoInfoA, dtScadAccordoDa, dtScadAccordoA, flRecesso,
                idTipoGestioneAccordo, flEsisteNotaFatturazione, flEsistonoSeAnnuali, saeDa, saeA, flEsistonoSeUt,
                sueDa, sueA, flFasciaManuale);
        if (!ricAccordiEnteList.isEmpty()) {
            try {
                // Se ho impostato il filtro sui tipi gestione accordo devo "scremare" i risultati
                if (!idTipoGestioneAccordo.isEmpty()) {
                    int i = -1;
                    // Scorro tutti i record restituiti dalla ricerca
                    for (OrgVRicAccordoEnte ricAccordoEnte : ricAccordiEnteList) {
                        i++;
                        boolean trovato = false;
                        if (ricAccordoEnte.getIdTipoGestioneAccordo() != null) {
                            // Per ognuno estraggo dalla colonna dei tipi gestine accordo gli eventuali id
                            String[] idsTipoGestioneAccordoByDB = ricAccordoEnte.getIdTipoGestioneAccordo().split(";");
                            for (BigDecimal idTipoGestFiltroAcc : idTipoGestioneAccordo) {
                                String idTipoGestAccFiltroStr = idTipoGestFiltroAcc.toString();

                                // Confronto gli eventuali id con quelli dei filtri
                                for (String idTipoGestioneAccordoByDB : idsTipoGestioneAccordoByDB) {
                                    // Se non è presente l'id del filtro, elimino il record
                                    if (idTipoGestioneAccordoByDB.equals(idTipoGestAccFiltroStr)) {
                                        trovato = true;
                                    }
                                }
                            }
                            if (trovato) {
                                OrgVRicAccordoEnteRowBean ricAccordiEnteRowBean = (OrgVRicAccordoEnteRowBean) Transform
                                        .entity2RowBean(ricAccordoEnte);
                                ricAccordiEnteTableBean.add(ricAccordiEnteRowBean);
                            }
                        }
                    }
                } else {
                    ricAccordiEnteTableBean = (OrgVRicAccordoEnteTableBean) Transform
                            .entities2TableBean(ricAccordiEnteList);
                }

            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.error("Errore durante il recupero degli accordi degli enti convenzionati "
                        + ExceptionUtils.getRootCauseMessage(e), e);
                throw new ParerUserError(
                        "Errore durante il recupero della lista degli accordi degli enti convenzionati");
            }
        }
        return ricAccordiEnteTableBean;
    }

    /**
     * Ritorna il rowBean relativo all'entity orgEnteSiam dato in input l'id di un suo accordo
     *
     * @param idAccordoEnte
     *            id di un accordo dell'ente convenzionato cercato
     *
     * @return row bean {@link OrgEnteSiamRowBean}
     *
     * @throws it.eng.saceriam.exception.ParerInternalError
     *             errore interno
     */
    public OrgEnteSiamRowBean getOrgEnteSiamRowBeanByAccordo(BigDecimal idAccordoEnte) throws ParerInternalError {
        OrgAccordoEnte accordoEnte = helper.findById(OrgAccordoEnte.class, idAccordoEnte);
        OrgEnteSiamRowBean row = null;
        try {
            if (accordoEnte != null) {
                row = (OrgEnteSiamRowBean) Transform.entity2RowBean(accordoEnte.getOrgEnteSiam());
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            String msg = "Errore durante il recupero dell'ente convenzionato da accordo "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerInternalError(msg);
        }
        return row;
    }

    public BaseTable getAnnualitaSenzaAtto() {
        List<Object[]> lista = helper.getAnnualitaSenzaAtto();
        BaseTable tabella = new BaseTable();
        for (Object[] obj : lista) {
            BaseRow riga = new BaseRow();
            riga.setString("ente", (String) obj[0]);
            riga.setBigDecimal("annualita", (BigDecimal) obj[1]);
            riga.setString("rpi", (String) obj[2]);
            riga.setString("data_registrazione", (String) obj[3]);
            riga.setString("cd_tipo_accordo", (String) obj[4]);
            tabella.add(riga);
        }
        return tabella;
    }

    /**
     * Cambia lo stato di una fattura in EMESSA, inserendo dei dati standard
     *
     * @param idFattureEnteDaElaborare
     *            la lista delle fatture per le quali effettuare il cambio di stato
     */
    public void cambiaStatoFatture(List<BigDecimal> idFattureEnteDaElaborare) {
        Calendar dataOdiernaCal = Calendar.getInstance();
        Date dataOdierna = dataOdiernaCal.getTime();
        int anno = dataOdiernaCal.get(Calendar.YEAR);
        Calendar data2444Cal = Calendar.getInstance();
        data2444Cal.set(2444, Calendar.DECEMBER, 31, 0, 0, 0);
        Date data2444 = data2444Cal.getTime();

        for (BigDecimal idFatturaEnteDaElaborare : idFattureEnteDaElaborare) {
            OrgFatturaEnte fatturaEnte = helper.findById(OrgFatturaEnte.class, idFatturaEnteDaElaborare);
            // Inserisco lo stato EMESSA in ORG_STATO_FATTURA_ENTE
            OrgStatoFatturaEnte statoFatturaEnte = creaStatoFattura(dataOdierna, fatturaEnte,
                    ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA);
            helper.getEntityManager().persist(statoFatturaEnte);
            // Aggiorno i campi di ORG_FATTURA_ENTE
            aggiornaFattura(fatturaEnte, anno, statoFatturaEnte.getIdStatoFatturaEnte(), dataOdierna, data2444);
        }
    }

    /**
     * Crea un oggetto rappresentante lo stato della fattura
     *
     * @param dataOdierna
     *            data di registrazione fattura
     * @param fatturaEnte
     *            la fattura per la quale si crea lo stato
     * @param tiStatoFatturaEnte
     *            il tipo di stato assunto
     *
     * @return entity {@link OrgStatoFatturaEnte}
     */
    public OrgStatoFatturaEnte creaStatoFattura(Date dataOdierna, OrgFatturaEnte fatturaEnte,
            ConstOrgStatoFatturaEnte.TiStatoFatturaEnte tiStatoFatturaEnte) {
        OrgStatoFatturaEnte statoFatturaEnte = new OrgStatoFatturaEnte();
        statoFatturaEnte.setDtRegStatoFatturaEnte(dataOdierna);
        statoFatturaEnte.setOrgFatturaEnte(fatturaEnte);
        statoFatturaEnte.setTiStatoFatturaEnte(tiStatoFatturaEnte.getDescrizione());
        return statoFatturaEnte;
    }

    /**
     * Aggiorna l'entity della fattura con dati coerenti allo stato EMESSA
     *
     * @param fatturaEnte
     *            la fattura da modificare
     * @param anno
     *            anno di emissione della fattura
     * @param idStatoFatturaEnte
     *            stato attuale della fattura
     * @param dtEmisFattura
     *            data di emissione fattura
     * @param dtScadFattura
     *            data di scadenza della fattura
     */
    public void aggiornaFattura(OrgFatturaEnte fatturaEnte, int anno, long idStatoFatturaEnte, Date dtEmisFattura,
            Date dtScadFattura) {
        fatturaEnte.setCdRegistroEmisFattura("DA_SCRIPT");
        fatturaEnte.setAaEmissFattura(new BigDecimal(anno));
        fatturaEnte.setCdEmisFattura(fatturaEnte.getCdFattura());
        fatturaEnte.setIdStatoFatturaEnteCor(BigDecimal.valueOf(idStatoFatturaEnte));
        fatturaEnte.setDtEmisFattura(dtEmisFattura);
        fatturaEnte.setNtEmisFattura(
                "Aggiornamento stato a EMESSA per gestione esterna delle fasi successive alla assunzione dello stato");
        fatturaEnte.setDtScadFattura(dtScadFattura);
        fatturaEnte.setCdFatturaSap(fatturaEnte.getCdFattura());
    }

    public BaseTable getReportFattureCalcolate() {
        List<Object[]> reportList = helper.getReportFattureCalcolate();
        return createBaseTableEstraiFatture(reportList);
    }

    public BaseTable getReportFattureCalcolateDaRicerca(String nmEnteConvenz, String cdClienteSap,
            BigDecimal idTipoAccordo, BigDecimal idTipoServizio, BigDecimal aaFatturaDa, BigDecimal aaFatturaA,
            BigDecimal pgFatturaEnteDa, BigDecimal pgFatturaEnteA, String cdFattura, String cdRegistroEmisFattura,
            BigDecimal aaEmissFattura, String cdEmisFattura, BigDecimal pgFatturaEmis, Date dtEmisFatturaDa,
            Date dtEmisFatturaA, Set<String> tiStatoFatturaEnte) {

        List<Object[]> reportList = helper.getReportFattureCalcolateDaRicerca(nmEnteConvenz, cdClienteSap,
                idTipoAccordo, idTipoServizio, aaFatturaDa, aaFatturaA, pgFatturaEnteDa, pgFatturaEnteA, cdFattura,
                cdRegistroEmisFattura, aaEmissFattura, cdEmisFattura, pgFatturaEmis, dtEmisFatturaDa, dtEmisFatturaA,
                tiStatoFatturaEnte);

        return createBaseTableEstraiFatture(reportList);
    }

    private BaseTable createBaseTableEstraiFatture(List<Object[]> reportList) {
        BaseTable table = new BaseTable();
        Set<EstraiFattureBean> fattureSet = new TreeSet<>();
        for (Object[] report : reportList) {
            EstraiFattureBean fattureBean = new EstraiFattureBean();
            fattureBean.setNm_ente_convenz((String) report[0]);
            fattureBean.setCd_fisc((String) report[1]);
            fattureBean.setNm_servizio_erogato_completo((String) report[2]);
            fattureBean.setAa_erogazione((BigDecimal) report[3]);
            String attoImpegno = (String) report[4] != null ? (String) report[4] : "";
            fattureBean.setDs_atto_aa_accordo(attoImpegno);
            String ufe = (String) report[5] != null ? (String) report[5] : "";
            fattureBean.setCd_ufe(ufe);
            String cig = (String) report[6] != null ? (String) report[6] : "";
            fattureBean.setCd_cig(cig);
            fattureBean.setIm_servizio_fattura((BigDecimal) report[7]);
            fattureBean.setIm_totale((BigDecimal) report[8]);
            fattureBean.setCd_iva((String) report[9]);
            String tesoreria = (String) report[10] != null ? (String) report[10] : "";
            fattureBean.setConto_tesoreria_unica(tesoreria);
            String iban = (String) report[11] != null ? (String) report[11] : "";
            fattureBean.setConto_iban(iban);
            fattureSet.add(fattureBean);
        }

        for (EstraiFattureBean fattureBean : fattureSet) {
            BaseRow row = new BaseRow();
            row.setString("nm_ente_convenz", fattureBean.getNm_ente_convenz());
            row.setString("cd_fisc", fattureBean.getCd_fisc());
            row.setString("nm_servizio_erogato_completo", fattureBean.getNm_servizio_erogato_completo());
            row.setBigDecimal("aa_erogazione", fattureBean.getAa_erogazione());
            row.setString("ds_atto_aa_accordo", fattureBean.getDs_atto_aa_accordo());
            row.setString("cd_ufe", fattureBean.getCd_ufe());
            row.setString("cd_cig", fattureBean.getCd_cig());
            row.setBigDecimal("im_servizio_fattura", fattureBean.getIm_servizio_fattura());
            row.setBigDecimal("im_totale", fattureBean.getIm_totale());
            row.setString("cd_iva", fattureBean.getCd_iva());
            row.setString("conto_tesoreria_unica", fattureBean.getConto_tesoreria_unica());
            row.setString("conto_IBAN", fattureBean.getConto_iban());
            table.add(row);
        }
        return table;

    }

    public boolean isNmFileModuloInfoPresente(BigDecimal idModuloInfo) {
        OrgModuloInfoAccordo moduloInfoAccordo = helper.findById(OrgModuloInfoAccordo.class, idModuloInfo);
        if (moduloInfoAccordo != null && moduloInfoAccordo.getNmFileModuloInfo() != null) {
            return true;
        } else {
            return false;
        }

    }

    //
    public String getFileTypeByTika(InputStream istream) {
        final Tika tika = new Tika();
        String fileType = "";
        try {
            fileType = tika.detect(istream);
        } catch (IOException e) {
            LOGGER.error("getFileTypeByTika - Errore durante la determinazione del mime type da InputStream "
                    + e.getMessage(), e);
        }
        return fileType;
    }

    /**
     * Recupera il tablebean dei cluster presenti su DB
     *
     * @return il tablebean contenente i cluster e relativa fascia associabili ad un accordo
     */
    public OrgClusterAccordoTableBean getOrgClusterAccordoTableBean() {
        OrgClusterAccordoTableBean table = new OrgClusterAccordoTableBean();
        List<Object[]> clusterAccordoList = helper.getOrgClusterAccordoList();

        try {
            clusterAccordoList.stream().map(clusterAccordo -> {
                OrgClusterAccordoRowBean row = new OrgClusterAccordoRowBean();
                row.setBigDecimal("id_cluster_accordo", BigDecimal.valueOf((Long) clusterAccordo[0]));
                row.setString("ds_cluster_accordo", (String) clusterAccordo[1]);
                return row;
            }).forEachOrdered(row -> {
                table.add(row);
            });
        } catch (Exception e) {
            LOGGER.error("Errore durante il recupero dei cluster sull'accordo " + ExceptionUtils.getRootCauseMessage(e),
                    e);
        }

        return table;
    }

    /**
     * Recupera il tablebean delle fasce storage presenti su DB
     *
     * @return il tablebean contenente le fasce storage
     */
    public OrgFasciaStorageAccordoTableBean getOrgFasciaStorageAccordoTableBean() {
        OrgFasciaStorageAccordoTableBean table = new OrgFasciaStorageAccordoTableBean();
        List<Object[]> fasciaStorageAccordoList = helper.getOrgFasciaStorageAccordoList();
        DecimalFormat df = new DecimalFormat("#,###.00");
        Locale currentLocale = Locale.getDefault();
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(currentLocale));

        try {
            fasciaStorageAccordoList.stream().map(fasciaStorageAccordo -> {
                OrgFasciaStorageAccordoRowBean row = new OrgFasciaStorageAccordoRowBean();
                row.setBigDecimal("id_fascia_storage_accordo", BigDecimal.valueOf((Long) fasciaStorageAccordo[0]));
                // Formatto la descrizione inserendo anche i punti e virgola separatori
                BigDecimal fasciaDa = (BigDecimal) fasciaStorageAccordo[2];
                BigDecimal fasciaA = (BigDecimal) fasciaStorageAccordo[3];
                String fasciaDaFormattata = df.format(fasciaDa.longValue());
                fasciaDaFormattata = fasciaDaFormattata.equals(",00") ? "0,00" : fasciaDaFormattata;
                String fasciaAFormattata = df.format(fasciaA.longValue());
                row.setString("ds_fascia_storage_accordo",
                        (String) fasciaStorageAccordo[1] + " - da " + fasciaDaFormattata + " a " + fasciaAFormattata);
                return row;
            }).forEachOrdered(row -> {
                table.add(row);
            });
        } catch (Exception e) {
            LOGGER.error("Errore durante il recupero delle fasce storage sull'accordo "
                    + ExceptionUtils.getRootCauseMessage(e), e);
        }

        return table;
    }

    public BaseRow getOccupStorageAccordo(BigDecimal idAccordoEnte, BigDecimal idFasciaDaAccordo)
            throws ParerUserError {
        BaseRow row = null;
        try {
            // Recupero il parametro sui tipi accordo da passare alla query //bdcar
            String tipiAccordo = paramHelper.getValoreParamApplic(
                    ConstIamParamApplic.NmParamApplic.TIPI_ACCORDO_CALC_STORAGE_PROD.name(), null, null,
                    Constants.TipoIamVGetValAppart.APPLIC);

            List<String> tipiAccordoList = Arrays.asList(tipiAccordo.split("\\|"));

            if (tipiAccordoList != null && !tipiAccordoList.isEmpty()) {
                OrgVOccupStorageAccordo occupStorageAccordo = helper.getOrgVOccupStorageAccordo(idAccordoEnte,
                        tipiAccordoList);

                if (occupStorageAccordo != null) {
                    row = new BaseRow();
                    row.setBigDecimal("storage_occupato", occupStorageAccordo.getDimBytesMediaAnno());
                    // Calcolo il valore della data di "arrivo"
                    int months = occupStorageAccordo.getMesiValidita().intValue();
                    int years = months / 12; // 1
                    Calendar c = Calendar.getInstance();
                    c.setTime(occupStorageAccordo.getDtDecAccordo());
                    c.add(Calendar.YEAR, years);
                    row.setTimestamp("dato_calcolato_dal",
                            new Timestamp(occupStorageAccordo.getDtDecAccordo().getTime()));
                    row.setTimestamp("dato_calcolato_al",
                            new Timestamp(occupStorageAccordo.getDtFineAccordo().getTime()));
                    // Imposto le fasce
                    row.setString("fascia_da_occupazione", occupStorageAccordo.getFasciaOccupazione());
                    row.setString("fascia_da_accordo", occupStorageAccordo.getFasciaAccordo());
                    // Creo un parametro per "colorare" il campo in caso non sia allineato con la fascia impostata
                    if (occupStorageAccordo.getNiFasciaAccordoA() != null) {
                        if (occupStorageAccordo.getNiFasciaAccordoA()
                                .compareTo(occupStorageAccordo.getNiFasciaOccupazioneA()) < 0) {
                            row.setString("colore_fascia", "coral");
                        } else {
                            row.setString("colore_fascia", "lightgreen");
                        }
                    }
                }

            }
        } catch (Exception ex) {
            String msg = "Errore durante il recupero dei valori degli storage occupati "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(ex.getMessage());
        }
        return row;
    }

    public Object[] getOccupStorageGestore(Date dtDecAccordoCorrente, BigDecimal idEnteConvenzGestore)
            throws ParerUserError {
        Object[] result = null;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Object[] occupStorageGestore = helper.getOccupStorageGestore(df.format(dtDecAccordoCorrente),
                    idEnteConvenzGestore);
            if (occupStorageGestore != null) {
                BigDecimal mesiValidita = (BigDecimal) occupStorageGestore[0];
                BigDecimal dimBytesMediaAnno = ((BigDecimal) occupStorageGestore[1]);
                dimBytesMediaAnno = dimBytesMediaAnno != null ? dimBytesMediaAnno : BigDecimal.ZERO;
                Calendar cal = Calendar.getInstance();
                cal.setTime(dtDecAccordoCorrente);
                int numMesiDaAggiungere = mesiValidita.intValue() - (mesiValidita.intValue() % 12);
                cal.add(Calendar.MONTH, numMesiDaAggiungere);
                cal.add(Calendar.DATE, -1);
                Date dtFineAccordo = cal.getTime();
                result = new Object[2];
                result[0] = dtFineAccordo;
                result[1] = dimBytesMediaAnno;
            }
        } catch (Exception ex) {
            String msg = "Errore durante il recupero dei valori degli storage occupati "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(ex.getMessage());
        }
        return result;
    }

    public Object[] getFasciaDaOccupazione(BigDecimal dimBytesMediaAnno) {
        return helper.getFasciaDaOccupazione(dimBytesMediaAnno);
    }

    public BaseTable getReportStorageExtraRer() throws ParerUserError {
        BaseTable tabella = new BaseTable();
        try {
            // Recupero il parametro con i tipi accordo da considerare
            String tipiAccordo = paramHelper.getValoreParamApplic(
                    ConstIamParamApplic.NmParamApplic.TIPI_ACCORDO_CALC_STORAGE_PROD_FATT.name(), null, null,
                    Constants.TipoIamVGetValAppart.APPLIC);

            List<String> tipiAccordoList = Arrays.asList(tipiAccordo.split("\\|"));

            if (tipiAccordoList != null && !tipiAccordoList.isEmpty()) {

                List<Object[]> lista = helper.getReportStorageExtraRer(tipiAccordoList);

                for (Object[] obj : lista) {
                    BaseRow riga = new BaseRow();
                    riga.setString("ambiente_ente_convenzionato", (String) obj[0]);
                    riga.setBigDecimal("storage_occupato", (BigDecimal) obj[1]);
                    riga.setString("dato_calcolato_dal", (String) obj[2]);
                    riga.setString("dato_calcolato_al", (String) obj[3]);
                    riga.setString("fascia_da_accordo", (String) obj[4]);
                    riga.setString("fascia_da_occupazione", (String) obj[5]);
                    if (((BigDecimal) obj[6]).compareTo(BigDecimal.ONE) == 0) {
                        riga.setString("fl_sforo", "SI");
                    } else {
                        riga.setString("fl_sforo", "NO");
                    }
                    tabella.add(riga);
                }
            }
        } catch (Exception ex) {
            String msg = "Errore durante il recupero dei valori degli storage occupati "
                    + ExceptionUtils.getRootCauseMessage(ex);
            LOGGER.error(msg, ex);
            throw new ParerUserError(ex.getMessage());
        }
        return tabella;
    }

    public List<String> getUtentiConAbilitazioniEntiCollegToDel2(BigDecimal idAppartCollegEnti) {
        SortedSet<String> listaUtenti = new TreeSet<>();
        List<String> utentiCoinvolti = userHelper.getUsrVAbilEnteCollegToDel2(idAppartCollegEnti);
        for (String nmUserid : utentiCoinvolti) {
            UsrUser utente = utentiHelper.getUsrUserByNmUserid(nmUserid);
            UsrStatoUser statoUser = helper.findById(UsrStatoUser.class, utente.getIdStatoUserCor());
            if (!statoUser.getTiStatoUser().equals(ConstUsrStatoUser.TiStatotUser.CESSATO.name())) {
                listaUtenti.add(utente.getNmUserid());
            }
        }
        return new ArrayList<>(listaUtenti);
    }

    public SortedSet<String> getUtentiConAbilitazioniCollegamento(BigDecimal idCollegEntiConvenz) {
        OrgCollegEntiConvenz collegEntiConvenz = helper.findById(OrgCollegEntiConvenz.class, idCollegEntiConvenz);
        SortedSet<String> listaUtenti = new TreeSet<>();

        // Recupero l'elenco degli utenti coinvolti nelle abilitazioni agli enti
        List<OrgAppartCollegEnti> appartCollegEntiList = collegEntiConvenz.getOrgAppartCollegEntis();
        for (OrgAppartCollegEnti appartCollegEnti : appartCollegEntiList) {
            for (UsrAbilEnteSiam abilEnteSiam : appartCollegEnti.getUsrAbilEnteSiams()) {
                UsrUser utente = utentiHelper.getUsrUserByNmUserid(abilEnteSiam.getUsrUser().getNmUserid());
                UsrStatoUser statoUser = helper.findById(UsrStatoUser.class, utente.getIdStatoUserCor());
                if (!statoUser.getTiStatoUser().equals(ConstUsrStatoUser.TiStatotUser.CESSATO.name())) {
                    listaUtenti.add(utente.getNmUserid());
                }
            }
        }
        return listaUtenti;
    }

    public SortedSet<String> getUtentiConAbilitazioniAppartenenzaCollegamento(BigDecimal idAppartCollegEntiConvenz) {
        OrgAppartCollegEnti appartCollegEnti = helper.findById(OrgAppartCollegEnti.class, idAppartCollegEntiConvenz);
        SortedSet<String> listaUtenti = new TreeSet<>();

        // Attenzione: recupero solo gli utenti NON CESSATI (al momento dunque non è stata modificata la vista che
        // recupera le stesse informazioni (USR_V_ABIL_ENTE_COLLEG_TO_DEL)
        for (UsrAbilEnteSiam abilEnteSiam : appartCollegEnti.getUsrAbilEnteSiams()) {
            BigDecimal idStatoUserCor = abilEnteSiam.getUsrUser().getIdStatoUserCor();
            UsrStatoUser statoUser = helper.findById(UsrStatoUser.class, idStatoUserCor);
            if (!statoUser.getTiStatoUser().equals(ConstUsrStatoUser.TiStatotUser.CESSATO.name())) {
                listaUtenti.add(abilEnteSiam.getUsrUser().getNmUserid());
            }
        }
        return listaUtenti;
    }

    public boolean existUtentiConAbilitazioniAppartenenzaCollegamento(BigDecimal idAppartCollegEntiConvenz) {
        OrgAppartCollegEnti appartCollegEnti = helper.findById(OrgAppartCollegEnti.class, idAppartCollegEntiConvenz);

        for (UsrAbilEnteSiam abilEnteSiam : appartCollegEnti.getUsrAbilEnteSiams()) {
            BigDecimal idStatoUserCor = abilEnteSiam.getUsrUser().getIdStatoUserCor();
            UsrStatoUser statoUser = helper.findById(UsrStatoUser.class, idStatoUserCor);
            if (!statoUser.getTiStatoUser().equals(ConstUsrStatoUser.TiStatotUser.CESSATO.name())) {
                return true;
            }
        }
        return false;
    }
}
