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

package it.eng.saceriam.amministrazioneEntiConvenzionati.helper;

import static it.eng.paginator.util.HibernateUtils.bigDecimalFrom;
import static it.eng.paginator.util.HibernateUtils.longFrom;
import static it.eng.paginator.util.HibernateUtils.longListFrom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import it.eng.saceriam.amministrazioneEntiConvenzionati.dto.ServizioFatturatoBean;
import it.eng.saceriam.entity.DecTipoUnitaDoc;
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
import it.eng.saceriam.entity.OrgCollegEntiConvenz;
import it.eng.saceriam.entity.OrgDiscipStrut;
import it.eng.saceriam.entity.OrgEnteArkRif;
import it.eng.saceriam.entity.OrgEnteConvenzOrg;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.saceriam.entity.OrgEnteUserRif;
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
import it.eng.saceriam.entity.OrgSuptEsternoEnteConvenz;
import it.eng.saceriam.entity.OrgTariffa;
import it.eng.saceriam.entity.OrgTariffaAaAccordo;
import it.eng.saceriam.entity.OrgTariffaAccordo;
import it.eng.saceriam.entity.OrgTariffario;
import it.eng.saceriam.entity.OrgTipiGestioneAccordo;
import it.eng.saceriam.entity.OrgTipoAccordo;
import it.eng.saceriam.entity.OrgTipoServizio;
import it.eng.saceriam.entity.OrgVigilEnteProdut;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrTipoDatoIam;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam;
import it.eng.saceriam.entity.constraint.ConstOrgEnteSiam.TiEnteConvenz;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.grantedViewEntity.DecVLisTiUniDocAms;
import it.eng.saceriam.helper.GenericHelper;
import it.eng.saceriam.util.DateUtil;
import it.eng.saceriam.viewEntity.OrgVCreaFatturaByAnno;
import it.eng.saceriam.viewEntity.OrgVCreaFatturaByFatt;
import it.eng.saceriam.viewEntity.OrgVCreaServErogByAcc;
import it.eng.saceriam.viewEntity.OrgVCreaServFattAnnuale;
import it.eng.saceriam.viewEntity.OrgVCreaServFattUnaPrec;
import it.eng.saceriam.viewEntity.OrgVCreaServFattUnatantum;
import it.eng.saceriam.viewEntity.OrgVEnteConvByDelabilorg;
import it.eng.saceriam.viewEntity.OrgVEnteConvenzByOrganiz;
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
import it.eng.saceriam.viewEntity.UsrVAbilEnteCollegToDel;
import it.eng.saceriam.viewEntity.UsrVAbilEnteConvenz;
import it.eng.saceriam.viewEntity.UsrVTreeOrganizIam;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.stream.Collectors;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.util.Asserts;

/**
 * Session Bean implementation class AmministrazioneHelper Contiene i metodi, per la gestione della persistenza su DB
 * per le operazioni CRUD
 *
 */
@Stateless
@LocalBean
public class EntiConvenzionatiHelper extends GenericHelper {

    public List<OrgAmbitoTerrit> retrieveOrgAmbitoTerrit(String tiAmbitoTerrit) {
        Query query = getEntityManager().createQuery(
                "SELECT a FROM OrgAmbitoTerrit a WHERE a.tiAmbitoTerrit LIKE :tiAmbitoTerrit ORDER BY a.cdAmbitoTerrit");
        query.setParameter("tiAmbitoTerrit", "%" + tiAmbitoTerrit + "%");
        List<OrgAmbitoTerrit> list = query.getResultList();
        return list;
    }

    public List<OrgAmbitoTerrit> retrieveOrgAmbitoTerritChildList(List<BigDecimal> idAmbitoTerritoriale) {
        // TODO: Bisogna chiedere a Sandro di fare un sinonimo per questa tabella, cosÃ¬ non romperÃ  piÃ¹ le palle per
        // mettere sto alias a SACER
        Query query = getEntityManager().createQuery(
                "SELECT a FROM OrgAmbitoTerrit a WHERE a.orgAmbitoTerrit.idAmbitoTerrit IN (:idAmbitoTerritoriale)");
        query.setParameter("idAmbitoTerritoriale", longListFrom(idAmbitoTerritoriale));

        List<OrgAmbitoTerrit> list = query.getResultList();
        return list;
    }

    public List<Long> retrieveIdAmbitoTerritChildList(BigDecimal idAmbitoTerrit) {
        // TODO: Bisogna chiedere a Sandro di fare un sinonimo per questa tabella, cosÃ¬ non romperÃ  piÃ¹ le palle per
        // mettere sto alias a SACER
        String queryStr = "SELECT a.idAmbitoTerrit " + "FROM OrgAmbitoTerrit a "
                + "WHERE a.orgAmbitoTerrit.idAmbitoTerrit = :idAmbitoTerrit";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAmbitoTerrit", longFrom(idAmbitoTerrit));
        List<Long> idAmbitoTerritList = query.getResultList();
        return idAmbitoTerritList;
    }

    public OrgAmbitoTerrit retrieveOrgAmbitoTerritParent(BigDecimal idAmbitoTerrit) {
        // TODO: Bisogna chiedere a Sandro di fare un sinonimo per questa tabella, cosÃ¬ non romperÃ  piÃ¹ le palle per
        // mettere sto alias a SACER
        String queryStr = "SELECT ambitoTerrit.orgAmbitoTerrit FROM OrgAmbitoTerrit ambitoTerrit "
                + "WHERE ambitoTerrit.idAmbitoTerrit = :idAmbitoTerrit ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAmbitoTerrit", longFrom(idAmbitoTerrit));
        List<OrgAmbitoTerrit> ambitoTerritList = query.getResultList();
        if (!ambitoTerritList.isEmpty()) {
            return ambitoTerritList.get(0);
        }
        return null;
    }

    public List<OrgCategEnte> retrieveOrgCategEnteList() {
        // TODO: Bisogna chiedere a Sandro di fare un sinonimo per questa tabella, cosÃ¬ non romperÃ  piÃ¹ le palle per
        // mettere sto alias a SACER
        Query query = getEntityManager().createQuery("SELECT categ FROM OrgCategEnte categ");
        List<OrgCategEnte> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna l'oggetto ente convenzionato se esiste
     *
     * @param nmEnteConvenz
     *            nome ente
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgEnteSiam getOrgEnteConvenz(String nmEnteConvenz) {
        OrgEnteSiam ente = null;
        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            Query query = getEntityManager()
                    .createQuery("SELECT ente FROM OrgEnteSiam ente WHERE ente.nmEnteSiam = :nmEnteConvenz");
            query.setParameter("nmEnteConvenz", nmEnteConvenz);
            List<OrgEnteSiam> list = query.getResultList();
            if (!list.isEmpty()) {
                ente = list.get(0);
            }
        } else {
            throw new IllegalArgumentException("Parametro nmEnteConvenz nullo");
        }
        return ente;
    }

    /**
     * Ritorna l'oggetto ente convenzionato se esiste
     *
     * @param cdEnteConvenz
     *            codice ente
     * @param tiCdEnteConvenz
     *            tipo codice ente
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgEnteSiam getOrgEnteConvenz(String cdEnteConvenz, String tiCdEnteConvenz) {
        OrgEnteSiam ente = null;
        // if (StringUtils.isNotBlank(cdEnteConvenz) && StringUtils.isNotBlank(tiCdEnteConvenz)) {
        Query query = getEntityManager().createQuery("SELECT ente FROM OrgEnteSiam ente "
                + "WHERE ente.cdEnteConvenz = :cdEnteConvenz " + "AND ente.tiCdEnteConvenz = :tiCdEnteConvenz ");
        query.setParameter("cdEnteConvenz", cdEnteConvenz);
        query.setParameter("tiCdEnteConvenz", tiCdEnteConvenz);
        List<OrgEnteSiam> list = query.getResultList();
        if (!list.isEmpty()) {
            ente = list.get(0);
        }

        return ente;
    }

    /**
     * Ritorna la lista degli enti convenzionati escluso quello passato in input
     *
     * @param idEnteConvenzDaEscludere
     *            id ente convenzionato da escludere
     *
     * @return lista elementi di tipo {@link OrgEnteSiam}
     */
    public List<OrgEnteSiam> getOrgEnteConvenzList(BigDecimal idEnteConvenzDaEscludere) {
        String queryStr = "SELECT ente FROM OrgEnteSiam ente ";
        if (idEnteConvenzDaEscludere != null) {
            queryStr = queryStr + "WHERE ente.idEnteSiam != :idEnteConvenzDaEscludere ";
        }
        queryStr = queryStr + "ORDER BY ente.nmEnteSiam";
        Query query = getEntityManager().createQuery(queryStr);
        if (idEnteConvenzDaEscludere != null) {
            query.setParameter("idEnteConvenzDaEscludere", longFrom(idEnteConvenzDaEscludere));
        }
        List<OrgEnteSiam> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista degli enti convenzionati dei tipi passati in input
     *
     * @param tiEnteConvenz
     *            ente convenzionato di tipo {@link TiEnteConvenz}
     *
     * @return lista elementi di tipo {@link OrgEnteSiam}
     */
    public List<OrgEnteSiam> getOrgEnteConvenzList(ConstOrgEnteSiam.TiEnteConvenz... tiEnteConvenz) {
        String queryStr = "SELECT ente FROM OrgEnteSiam ente ";
        if (tiEnteConvenz != null) {
            queryStr = queryStr + "WHERE ente.tiEnteConvenz IN :tiEnteConvenz ";
        }
        queryStr = queryStr + "ORDER BY ente.nmEnteSiam";
        Query query = getEntityManager().createQuery(queryStr);
        if (tiEnteConvenz != null) {
            query.setParameter("tiEnteConvenz", Arrays.asList(tiEnteConvenz));
        }
        List<OrgEnteSiam> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista degli ambienti enti convenzionati in base al tipo ente convenzionato
     *
     * @param tiEnteConvenz
     *            ente convenzionato di tipo {@link TiEnteConvenz}
     *
     * @return lista elementi di tipo {@link OrgAmbienteEnteConvenz}
     */
    public List<OrgAmbienteEnteConvenz> getOrgAmbienteEnteConvenzList(ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz) {
        String queryStr = "SELECT DISTINCT ente.orgAmbienteEnteConvenz FROM OrgEnteSiam ente ";
        if (tiEnteConvenz != null) {
            queryStr = queryStr + "WHERE ente.tiEnteConvenz = :tiEnteConvenz ";
        }
        queryStr = queryStr + "ORDER BY ente.orgAmbienteEnteConvenz.nmAmbienteEnteConvenz";
        Query query = getEntityManager().createQuery(queryStr);
        if (tiEnteConvenz != null) {
            query.setParameter("tiEnteConvenz", tiEnteConvenz);
        }
        List<OrgAmbienteEnteConvenz> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista degli enti convenzionati dei tipi passati in input cui l'utente corrente corrente Ã¨ abilitato
     *
     * @param idUserIamCor
     *            id user IAM
     * @param tiEnteConvenz
     *            ente convenzionato di tipo {@link TiEnteConvenz}
     *
     * @return lista elementi di tipo {@link OrgEnteSiam}
     */
    public List<OrgEnteSiam> getOrgEnteConvenzUserAbilList(BigDecimal idUserIamCor,
            ConstOrgEnteSiam.TiEnteConvenz... tiEnteConvenz) {
        String queryStr = "SELECT ente FROM UsrVAbilEnteConvenz abilEnteConvenz, OrgEnteSiam ente "
                + "WHERE abilEnteConvenz.id.idUserIam = :idUserIamCor "
                + "AND abilEnteConvenz.id.idEnteConvenz = ente.idEnteSiam ";
        if (tiEnteConvenz != null) {
            queryStr = queryStr + "AND ente.tiEnteConvenz IN (:tiEnteConvenz) ";
        }
        queryStr = queryStr + "ORDER BY ente.nmEnteSiam";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIamCor", idUserIamCor);
        if (tiEnteConvenz != null && tiEnteConvenz.length > 0) {
            List<TiEnteConvenz> tipiList = Arrays.asList(tiEnteConvenz);
            query.setParameter("tiEnteConvenz", tipiList);
        }
        List<OrgEnteSiam> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista degli ambienti enti convenzionati in base ai tipi ente convenzionato passati in input cui
     * l'utente corrente è abilitato
     *
     * @param idUserIamCor
     *            id user iam
     * @param tiEnteConvenz
     *            ente convenzionato di tipo {@link TiEnteConvenz}
     *
     * @return lista elementi di tipo {@link OrgAmbienteEnteConvenz}
     */
    public List<OrgAmbienteEnteConvenz> getOrgAmbienteEnteConvenzUserAbilList(BigDecimal idUserIamCor,
            ConstOrgEnteSiam.TiEnteConvenz... tiEnteConvenz) {
        String queryStr = "SELECT DISTINCT ente.orgAmbienteEnteConvenz FROM UsrVAbilEnteConvenz abilEnteConvenz, OrgEnteSiam ente "
                + "WHERE abilEnteConvenz.id.idUserIam = :idUserIamCor "
                + "AND abilEnteConvenz.id.idEnteConvenz = ente.idEnteSiam ";
        if (tiEnteConvenz != null) {
            queryStr = queryStr + "AND ente.tiEnteConvenz IN (:tiEnteConvenz) ";
        }
        queryStr = queryStr + "ORDER BY ente.orgAmbienteEnteConvenz.nmAmbienteEnteConvenz";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIamCor", idUserIamCor);
        if (tiEnteConvenz != null && tiEnteConvenz.length > 0) {
            List<TiEnteConvenz> tipiList = Arrays.asList(tiEnteConvenz);
            query.setParameter("tiEnteConvenz", tipiList);
        }
        List<OrgAmbienteEnteConvenz> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista degli enti convenzionati collegati all'ente passato in input (escluso se stesso) cui l'utente
     * corrente è abilitato
     *
     * @param idUserIamCor
     *            id user IAM
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return lista elementi di tipo {@link OrgEnteSiam}
     */
    public List<OrgEnteSiam> getOrgEnteConvenzCollegUserAbilList(BigDecimal idUserIamCor, BigDecimal idEnteConvenz) {
        String queryStr = "SELECT appart2.orgEnteSiam FROM UsrVAbilEnteConvenz abilEnteConvenz, OrgEnteSiam ente, "
                + "OrgAppartCollegEnti appart1, OrgAppartCollegEnti appart2 "
                + "WHERE abilEnteConvenz.id.idUserIam = :idUserIamCor "
                + "AND abilEnteConvenz.id.idEnteConvenz = ente.idEnteSiam "
                + "AND appart1.orgCollegEntiConvenz = appart2.orgCollegEntiConvenz "
                + "AND appart1.orgEnteSiam.idEnteSiam = ente.idEnteSiam "
                + "AND appart2.orgEnteSiam <> appart1.orgEnteSiam " + "AND ente.idEnteSiam = :idEnteConvenz "
                + "ORDER BY ente.nmEnteSiam";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIamCor", idUserIamCor);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgEnteSiam> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista degli enti convenzionati cessati relativi all'ente passato in input
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return lista elementi di tipo {@link OrgEnteSiam}
     */
    public List<OrgEnteSiam> getOrgEnteConvenzCessatiList(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT ente FROM OrgEnteSiam ente "
                + "WHERE ente.orgEnteSiamByIdEnteConvenzNuovo.idEnteSiam = :idEnteConvenz "
                + "ORDER BY ente.nmEnteSiam ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgEnteSiam> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista degli enti convenzionati non cessati relativi all'ambiente passato in input
     *
     * @param idAmbienteEnteConvenz
     *            id ambiente convenzionato
     * @param idEnteConvenzExcluded
     *            id ente da escludere
     *
     * @return lista elementi di tipo {@link OrgEnteSiam}
     */
    public List<OrgEnteSiam> getOrgEnteConvenzNonCessatiList(BigDecimal idAmbienteEnteConvenz,
            BigDecimal idEnteConvenzExcluded) {
        String queryStr = "SELECT ente FROM OrgEnteSiam ente "
                + "WHERE ente.orgAmbienteEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz "
                + "AND ente.dtCessazione > :dataCorrente ";
        if (idEnteConvenzExcluded != null) {
            queryStr = queryStr + "AND ente.idEnteSiam != :idEnteConvenzExcluded ";
        }
        queryStr = queryStr + "ORDER BY ente.nmEnteSiam ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAmbienteEnteConvenz", longFrom(idAmbienteEnteConvenz));
        query.setParameter("dataCorrente", new Date());
        if (idEnteConvenzExcluded != null) {
            query.setParameter("idEnteConvenzExcluded", longFrom(idEnteConvenzExcluded));
        }
        List<OrgEnteSiam> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista degli utenti appartenenti ad enti (diversi da quello aggiunto al collegamento) coinvolti nel
     * collegamento, per i quali è settato l'indicatore che deve essere abilitato in automatico agli enti produttori
     * appartenenti ai collegamenti (fl_abil_enti_colleg_autom)
     *
     * @param idCollegEntiConvenz
     *            id ente collegamento enti convezionati
     * @param idEnteConvenzExcluded
     *            id ente convenzionato da escludere
     *
     * @return lista elementi di tipo {@link UsrUser}
     */
    public List<UsrUser> getUsrUserCollegList(BigDecimal idCollegEntiConvenz, BigDecimal idEnteConvenzExcluded) {
        String queryStr = "SELECT DISTINCT utente FROM OrgAppartCollegEnti appart, UsrUser utente "
                + "WHERE appart.orgEnteSiam = utente.orgEnteSiam "
                + "AND appart.orgCollegEntiConvenz.idCollegEntiConvenz = :idCollegEntiConvenz "
                + "AND utente.flAbilEntiCollegAutom = '1' ";
        if (idEnteConvenzExcluded != null) {
            queryStr = queryStr + "AND appart.orgEnteSiam.idEnteSiam != :idEnteConvenzExcluded ";
        }
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idCollegEntiConvenz", longFrom(idCollegEntiConvenz));
        if (idEnteConvenzExcluded != null) {
            query.setParameter("idEnteConvenzExcluded", longFrom(idEnteConvenzExcluded));
        }
        List<UsrUser> list = query.getResultList();
        return list;
    }

    public List<OrgEnteArkRif> retrieveOrgEnteArkRif(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT enteArkRif FROM OrgEnteArkRif enteArkRif "
                + "WHERE enteArkRif.orgEnteSiam.idEnteSiam = :idEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return (List<OrgEnteArkRif>) query.getResultList();
    }

    public List<OrgEnteUserRif> retrieveOrgEnteUserRif(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT enteUserRif FROM OrgEnteUserRif enteUserRif "
                + "WHERE enteUserRif.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "ORDER BY enteUserRif.usrUser.nmCognomeUser, enteUserRif.usrUser.nmNomeUser ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return (List<OrgEnteUserRif>) query.getResultList();
    }

    public List<OrgCollegEntiConvenz> retrieveOrgCollegEntiConvenz(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT collegEntiConvenz FROM OrgAppartCollegEnti appartCollegEnti "
                + "JOIN appartCollegEnti.orgCollegEntiConvenz collegEntiConvenz "
                + "WHERE appartCollegEnti.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "ORDER BY collegEntiConvenz.nmColleg ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return (List<OrgCollegEntiConvenz>) query.getResultList();
    }

    public List<OrgCollegEntiConvenz> retrieveOrgCollegEntiConvenz(BigDecimal idUserIamCor, String nmColleg,
            String dsColleg, Date dtValidDa, Date dtValidA) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT collegEntiConvenz FROM OrgCollegEntiConvenz collegEntiConvenz "
                        + "WHERE (EXISTS (SELECT appartCollegEnti FROM OrgAppartCollegEnti appartCollegEnti, UsrAbilEnteSiam userAbil "
                        + "JOIN appartCollegEnti.orgCollegEntiConvenz collegEntiConvenzAbil "
                        + "WHERE collegEntiConvenzAbil.idCollegEntiConvenz = collegEntiConvenz.idCollegEntiConvenz "
                        + "AND appartCollegEnti.orgEnteSiam.idEnteSiam = userAbil.orgEnteSiam.idEnteSiam "
                        + "AND userAbil.usrUser.idUserIam = :idUserIamCor) "
                        + "OR NOT EXISTS (SELECT collegEntiConvenz FROM OrgAppartCollegEnti appartenenzeCollegEnti "
                        + "WHERE appartenenzeCollegEnti.orgCollegEntiConvenz.idCollegEntiConvenz = collegEntiConvenz.idCollegEntiConvenz)) ");
        String clause = " AND ";
        if (StringUtils.isNotBlank(nmColleg)) {
            queryStr.append(clause).append("UPPER(collegEntiConvenz.nmColleg) like :nmColleg");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(dsColleg)) {
            queryStr.append(clause).append("UPPER(collegEntiConvenz.dsColleg) LIKE :dsColleg");
            clause = " AND ";
        }
        if (dtValidDa != null) {
            queryStr.append(clause).append("(collegEntiConvenz.dtIniVal >= :dtValidDa)");
            clause = " AND ";
        }
        if (dtValidA != null) {
            queryStr.append(clause).append("(collegEntiConvenz.dtFinVal <= :dtValidA)");
            clause = " AND ";
        }
        queryStr.append(" ORDER BY collegEntiConvenz.nmColleg ");

        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idUserIamCor", longFrom(idUserIamCor));

        if (StringUtils.isNotBlank(nmColleg)) {
            query.setParameter("nmColleg", "%" + nmColleg.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(dsColleg)) {
            query.setParameter("dsColleg", "%" + dsColleg.toUpperCase() + "%");
        }
        if (dtValidDa != null) {
            query.setParameter("dtValidDa", dtValidDa);
        }
        if (dtValidA != null) {
            query.setParameter("dtValidA", dtValidA);
        }
        return (List<OrgCollegEntiConvenz>) query.getResultList();
    }

    public List<OrgCollegEntiConvenz> retrieveOrgCollegEntiConvenzValid(BigDecimal idUserIamCor) {
        String queryStr = "SELECT DISTINCT collegEntiConvenz FROM OrgCollegEntiConvenz collegEntiConvenz "
                + "WHERE (EXISTS (SELECT appartCollegEnti FROM OrgAppartCollegEnti appartCollegEnti, UsrAbilEnteSiam userAbil "
                + "JOIN appartCollegEnti.orgCollegEntiConvenz collegEntiConvenzAbil "
                + "WHERE collegEntiConvenzAbil.idCollegEntiConvenz = collegEntiConvenz.idCollegEntiConvenz "
                + "AND appartCollegEnti.orgEnteSiam.idEnteSiam = userAbil.orgEnteSiam.idEnteSiam "
                + "AND userAbil.usrUser.idUserIam = :idUserIamCor) "
                + "OR NOT EXISTS (SELECT collegEntiConvenz FROM OrgAppartCollegEnti appartenenzeCollegEnti "
                + "WHERE appartenenzeCollegEnti.orgCollegEntiConvenz.idCollegEntiConvenz = collegEntiConvenz.idCollegEntiConvenz)) "
                + "AND collegEntiConvenz.dtIniVal <= :dataOdierna AND collegEntiConvenz.dtFinVal >= :dataOdierna "
                + "ORDER BY collegEntiConvenz.dtIniVal DESC ";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIamCor", longFrom(idUserIamCor));
        query.setParameter("dataOdierna", new Date());

        List<OrgCollegEntiConvenz> list = query.getResultList();
        return list;
    }

    public List<OrgSuptEsternoEnteConvenz> retrieveOrgSuptEsternoEnteConvenz(BigDecimal idEnteConvenz,
            String tiEnteNonConvenz) {
        String queryStr = "SELECT suptEsternoEnteConvenz FROM OrgSuptEsternoEnteConvenz suptEsternoEnteConvenz "
                + "JOIN suptEsternoEnteConvenz.orgEnteSiamByIdEnteFornitEst enteSiam "
                + "WHERE suptEsternoEnteConvenz.orgEnteSiamByIdEnteProdut.idEnteSiam = :idEnteConvenz "
                + "AND enteSiam.tiEnteNonConvenz = :tiEnteNonConvenz "
                + "ORDER BY suptEsternoEnteConvenz.orgEnteSiamByIdEnteFornitEst.nmEnteSiam ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("tiEnteNonConvenz", ConstOrgEnteSiam.TiEnteNonConvenz.valueOf(tiEnteNonConvenz));
        return (List<OrgSuptEsternoEnteConvenz>) query.getResultList();
    }

    public List<OrgVigilEnteProdut> retrieveOrgVigilEnteProdut(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT orgVigilEnteProdut FROM OrgVigilEnteProdut orgVigilEnteProdut "
                + "WHERE orgVigilEnteProdut.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "ORDER BY orgVigilEnteProdut.orgAccordoVigil.orgEnteSiamByIdEnteOrganoVigil.nmEnteSiam ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return (List<OrgVigilEnteProdut>) query.getResultList();
    }

    public List<OrgVRicEnteConvenz> retrieveOrgEnteConvenzList(String nmEnteConvenz, BigDecimal idUserIamCor,
            BigDecimal idAmbienteEnteConvenz, String flEnteAttivo, String flEnteCessato, BigDecimal idCategEnte,
            List<BigDecimal> idAmbitoTerritRegione, List<BigDecimal> idAmbitoTerritProv,
            List<BigDecimal> idAmbitoTerritFormAssoc, List<BigDecimal> idTipoAccordo, Date dtFineValidAccordoDa,
            Date dtFineValidAccordoA, Date dtScadAccordoDa, Date dtScadAccordoA, List<BigDecimal> idArchivista,
            String noArchivista, String flRicev, String flRichModuloInfo, String flNonConvenz, String flRecesso,
            String flChiuso, String flEsistonoGestAcc, List<BigDecimal> idTipoGestioneAccordo, String flGestAccNoRisp,
            String tiStatoAccordo, String cdFisc, Date dtDecAccordoDa, Date dtDecAccordoA, Date dtDecAccordoInfoDa,
            Date dtDecAccordoInfoA, Date dtIniValDa, Date dtIniValA, Date dtCessazioneDa, Date dtCessazioneA) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT new it.eng.saceriam.viewEntity.OrgVRicEnteConvenz (ente.idEnteConvenz, ente.nmEnteConvenz, "
                        + "ente.idCategEnte, ente.idAmbitoTerrit, ente.dtIniVal, ente.dtCessazione, ente.flEsistonoModuli, ente.dtRichModuloInfo, "
                        + "ente.cdTipoAccordo, ente.archivista, ente.enteAttivo, ente.flRecesso, ente.flChiuso, ente.dtFineValidAccordo, ente.dtScadAccordo, ente.flEsistonoGestAcc, ente.cdEnteConvenz, ente.dtDecAccordo, ente.dtDecAccordoInfo) FROM OrgVRicEnteConvenz ente ");
        String clause = " WHERE ";
        // Ottengo la lista di tutti gli id di ambito territoriale da ricercare, che corrispondono all'intera gerarchia
        // di dati selezionati
        List<BigDecimal> idAmbitoTerritList = getIdAmbitoTerritorialePerRicerca(idAmbitoTerritRegione,
                idAmbitoTerritProv, idAmbitoTerritFormAssoc);
        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            queryStr.append(clause).append("UPPER(ente.nmEnteConvenz) like :nmEnteConvenz");
            clause = " AND ";
        }
        if (idUserIamCor != null) {
            queryStr.append(clause).append("ente.idUserIamCor = :idUserIamCor");
            clause = " AND ";
        }
        if (idAmbienteEnteConvenz != null) {
            queryStr.append(clause).append("ente.idAmbienteEnteConvenz = :idAmbienteEnteConvenz");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(flEnteAttivo)) {
            queryStr.append(clause).append("ente.enteAttivo = :flEnteAttivo");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(flEnteCessato)) {
            if (flEnteCessato.equals("1")) {
                queryStr.append(clause).append("ente.dtCessazione <= :dataOdierna");
            } else {
                queryStr.append(clause).append("ente.dtCessazione > :dataOdierna");
            }
            clause = " AND ";
        }
        if (idCategEnte != null) {
            queryStr.append(clause).append("ente.idCategEnte = :idCategEnte");
            clause = " AND ";
        }
        if (!idAmbitoTerritList.isEmpty()) {
            queryStr.append(clause).append("ente.idAmbitoTerrit IN (:idAmbitoTerritList)");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(flRicev)) {
            queryStr.append(clause).append("ente.flEsistonoModuli = :flEsistonoModuli ");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(flNonConvenz)) {
            queryStr.append(clause).append("ente.flNonConvenz = :flNonConvenz ");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(flRecesso)) {
            queryStr.append(clause).append("ente.flRecesso = :flRecesso");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(flChiuso)) {
            queryStr.append(clause).append("ente.flChiuso = :flChiuso");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(flRichModuloInfo)) {
            if (flRichModuloInfo.equals("1")) {
                queryStr.append(clause).append("ente.dtRichModuloInfo IS NOT NULL ");
            } else {
                queryStr.append(clause).append("ente.dtRichModuloInfo IS NULL ");
            }
            clause = " AND ";
        }
        if (!idTipoAccordo.isEmpty()) {
            queryStr.append(clause).append("ente.idTipoAccordo IN :idTipoAccordo ");
            clause = " AND ";
        }

        if (dtDecAccordoDa != null && dtDecAccordoA != null) {
            queryStr.append(clause).append("(ente.dtDecAccordo BETWEEN :dtDecAccordoDa AND :dtDecAccordoA) ");
            clause = " AND ";
        }

        if (dtFineValidAccordoDa != null && dtFineValidAccordoA != null) {
            queryStr.append(clause)
                    .append("(ente.dtFineValidAccordo BETWEEN :dtFineValidAccordoDa AND :dtFineValidAccordoA) ");
            clause = " AND ";
        }

        if (dtDecAccordoInfoDa != null && dtDecAccordoInfoA != null) {
            queryStr.append(clause)
                    .append("(ente.dtDecAccordoInfo BETWEEN :dtDecAccordoInfoDa AND :dtDecAccordoInfoA) ");
            clause = " AND ";
        }

        if (dtScadAccordoDa != null && dtScadAccordoA != null) {
            queryStr.append(clause).append("(ente.dtScadAccordo BETWEEN :dtScadAccordoDa AND :dtScadAccordoA) ");
            clause = " AND ";
        }

        if (dtIniValDa != null && dtIniValA != null) {
            queryStr.append(clause).append("(ente.dtIniVal BETWEEN :dtIniValDa AND :dtIniValA) ");
            clause = " AND ";
        }

        if (dtCessazioneDa != null && dtCessazioneA != null) {
            queryStr.append(clause).append("(ente.dtCessazione BETWEEN :dtCessazioneDa AND :dtCessazioneA) ");
            clause = " AND ";
        }

        if (!idArchivista.isEmpty()) {
            if (noArchivista.equals("1")) {
                queryStr.append(clause).append("(ente.idUserIamArk IN (:idArchivista) OR ente.idUserIamArk IS NULL) ");
            } else {
                queryStr.append(clause).append("ente.idUserIamArk IN (:idArchivista)");
            }
            clause = " AND ";
        } else {
            // Flag checkato
            if (noArchivista.equals("1")) {
                queryStr.append(clause).append("ente.idUserIamArk IS NULL");
            }
        }

        if (StringUtils.isNotBlank(flEsistonoGestAcc)) {
            queryStr.append(clause).append("ente.flEsistonoGestAcc = :flEsistonoGestAcc");
            clause = " AND ";
        }

        if (!idTipoGestioneAccordo.isEmpty()) {
            queryStr.append(clause).append(
                    "ente.tipoGestioneAccordo IN (SELECT tipiGestAcc.cdTipoGestioneAccordo FROM OrgTipiGestioneAccordo tipiGestAcc WHERE tipiGestAcc.idTipoGestioneAccordo IN (:idTipoGestioneAccordo))");
            clause = " AND ";
        }

        if (StringUtils.isNotBlank(flGestAccNoRisp)) {
            queryStr.append(clause).append("ente.flGestAccNoRisp = :flGestAccNoRisp");
            clause = " AND ";
        }

        if (StringUtils.isNotBlank(tiStatoAccordo)) {
            // MAC#22374
            if (tiStatoAccordo.equals("Accordo valido")) {
                queryStr.append(clause).append("UPPER(ente.tiStatoAccordo) like :tiStatoAccordo");
            } else if (tiStatoAccordo.equals("Accordo non valido")) {
                queryStr.append(clause).append(
                        "(UPPER(ente.tiStatoAccordo) like :tiStatoAccordo OR UPPER(ente.tiStatoAccordo) = 'ACCORDO IN CORSO DI STIPULA')");
            } else {
                queryStr.append(clause).append("UPPER(ente.tiStatoAccordo) = :tiStatoAccordo");
            }
            // end MAC#22374
            clause = " AND ";
        }

        if (StringUtils.isNotBlank(cdFisc)) {
            queryStr.append(clause).append("ente.cdFisc LIKE :cdFisc");
            clause = " AND ";
        }

        queryStr.append(" ORDER BY ente.nmEnteConvenz");
        Query query = getEntityManager().createQuery(queryStr.toString());

        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            query.setParameter("nmEnteConvenz", "%" + nmEnteConvenz.toUpperCase() + "%");
        }
        if (idUserIamCor != null) {
            query.setParameter("idUserIamCor", idUserIamCor);
        }
        if (idAmbienteEnteConvenz != null) {
            query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        }
        if (StringUtils.isNotBlank(flEnteAttivo)) {
            query.setParameter("flEnteAttivo", new BigDecimal(flEnteAttivo));
        }
        if (StringUtils.isNotBlank(flEnteCessato)) {
            query.setParameter("dataOdierna", new Date());
        }
        if (StringUtils.isNotBlank(flRicev)) {
            query.setParameter("flEsistonoModuli", flRicev);
        }
        if (StringUtils.isNotBlank(flNonConvenz)) {
            query.setParameter("flNonConvenz", flNonConvenz);
        }
        if (StringUtils.isNotBlank(flRecesso)) {
            query.setParameter("flRecesso", flRecesso);
        }
        if (StringUtils.isNotBlank(flChiuso)) {
            query.setParameter("flChiuso", flChiuso);
        }
        if (StringUtils.isNotBlank(flEsistonoGestAcc)) {
            query.setParameter("flEsistonoGestAcc", flEsistonoGestAcc);
        }
        if (!idTipoGestioneAccordo.isEmpty()) {
            query.setParameter("idTipoGestioneAccordo", longListFrom(idTipoGestioneAccordo));
        }
        if (StringUtils.isNotBlank(flGestAccNoRisp)) {
            query.setParameter("flGestAccNoRisp", flGestAccNoRisp);
        }
        if (idCategEnte != null) {
            query.setParameter("idCategEnte", idCategEnte);
        }
        if (!idAmbitoTerritList.isEmpty()) {
            query.setParameter("idAmbitoTerritList", idAmbitoTerritList);
        }
        if (!idTipoAccordo.isEmpty()) {
            query.setParameter("idTipoAccordo", idTipoAccordo);
        }
        if (StringUtils.isNotBlank(cdFisc)) {
            query.setParameter("cdFisc", cdFisc);
        }
        if (dtDecAccordoDa != null && dtDecAccordoA != null) {
            query.setParameter("dtDecAccordoDa", dtDecAccordoDa);
            query.setParameter("dtDecAccordoA", dtDecAccordoA);
        }
        if (dtFineValidAccordoDa != null && dtFineValidAccordoA != null) {
            query.setParameter("dtFineValidAccordoDa", dtFineValidAccordoDa);
            query.setParameter("dtFineValidAccordoA", dtFineValidAccordoA);
        }

        if (dtIniValDa != null && dtIniValA != null) {
            query.setParameter("dtIniValDa", dtIniValDa);
            query.setParameter("dtIniValA", dtIniValA);
        }
        if (dtCessazioneDa != null && dtCessazioneA != null) {
            query.setParameter("dtCessazioneDa", dtCessazioneDa);
            query.setParameter("dtCessazioneA", dtCessazioneA);
        }

        if (dtDecAccordoInfoDa != null && dtDecAccordoInfoA != null) {
            query.setParameter("dtDecAccordoInfoDa", dtDecAccordoInfoDa);
            query.setParameter("dtDecAccordoInfoA", dtDecAccordoInfoA);
        }
        if (dtScadAccordoDa != null && dtScadAccordoA != null) {
            query.setParameter("dtScadAccordoDa", dtScadAccordoDa);
            query.setParameter("dtScadAccordoA", dtScadAccordoA);
        }
        if (!idArchivista.isEmpty()) {
            query.setParameter("idArchivista", idArchivista);
        }
        if (StringUtils.isNotBlank(tiStatoAccordo)) {
            // MAC#22374
            if (tiStatoAccordo.equals("Accordo valido") || tiStatoAccordo.equals("Accordo non valido")) {
                query.setParameter("tiStatoAccordo", tiStatoAccordo.toUpperCase() + "%");
            } else {
                query.setParameter("tiStatoAccordo", tiStatoAccordo.toUpperCase());
            }
            // end MAC#22374
        }
        List<OrgVRicEnteConvenz> list = query.getResultList();
        return list;
    }

    private List<BigDecimal> getIdAmbitoTerritorialePerRicerca(List<BigDecimal> regioniList,
            List<BigDecimal> provinceList, List<BigDecimal> formeAssociateList) {
        List<BigDecimal> idAmbitoTerritList = new ArrayList();

        // Metto tutte le forme associate presenti in online tra gli idAmbitoTerrit da ricercare
        if (!formeAssociateList.isEmpty()) {
            idAmbitoTerritList.addAll(formeAssociateList);
        }

        if (!provinceList.isEmpty()) {
            // Controllo se ci sono figli selezionati nell'online: se non ci sono piazzo la provincia e tutti i suoi
            // figli
            // salvati su DB, come ambiti territoriali da ricercare
            for (BigDecimal idProvincia : provinceList) {
                if (!haFigliPresentiInSottoLivelloOnlineList(idProvincia, formeAssociateList)) {
                    idAmbitoTerritList.add(idProvincia);
                    for (Long id : retrieveIdAmbitoTerritChildList(idProvincia)) {
                        idAmbitoTerritList.add(new BigDecimal(id));
                    }
                }
            }
        }

        if (!regioniList.isEmpty()) {
            for (BigDecimal idRegione : regioniList) {
                if (!haFigliPresentiInSottoLivelloOnlineList(idRegione, provinceList)) {
                    idAmbitoTerritList.add(idRegione);
                    List<Long> figliRegione = retrieveIdAmbitoTerritChildList(idRegione);
                    for (Long provincia : figliRegione) {
                        final BigDecimal idProvincia = new BigDecimal(provincia);
                        idAmbitoTerritList.add(idProvincia);
                        for (Long id : retrieveIdAmbitoTerritChildList(idProvincia)) {
                            idAmbitoTerritList.add(new BigDecimal(id));
                        }
                    }
                }
            }
        }
        return idAmbitoTerritList;
    }

    private boolean haFigliPresentiInSottoLivelloOnlineList(BigDecimal idPadre,
            List<BigDecimal> figliQualunquePresentiInOnline) {
        // Se la lista di figli presenti nell'online Ã¨ vuota, Ã¨ ovvio che non avrÃ² figli presenti nell'online
        if (figliQualunquePresentiInOnline.isEmpty()) {
            return false;
        } else {
            Query query = getEntityManager().createQuery(
                    "SELECT COUNT(a) FROM OrgAmbitoTerrit a WHERE a.orgAmbitoTerrit.idAmbitoTerrit = :idPadre AND a.idAmbitoTerrit IN (:figliQualunquePresentiInOnline)");
            query.setParameter("idPadre", longFrom(idPadre));
            query.setParameter("figliQualunquePresentiInOnline", longListFrom(figliQualunquePresentiInOnline));
            return ((Long) query.getSingleResult()) > 0L;
        }
    }

    public boolean esisteGestionePerChiave(BigDecimal idAccordoEnte, String cdRegistroGestAccordo,
            BigDecimal aaGestAccordo, String cdKeyGestAccordo) {
        String queryStr = "SELECT gestioneAccordo FROM OrgGestioneAccordo gestioneAccordo "
                + "WHERE gestioneAccordo.orgAccordoEnte.idAccordoEnte = :idAccordoEnte "
                + "AND gestioneAccordo.cdRegistroGestAccordo = :cdRegistroGestAccordo "
                + "AND gestioneAccordo.aaGestAccordo = :aaGestAccordo "
                + "AND gestioneAccordo.cdKeyGestAccordo = :cdKeyGestAccordo ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAccordoEnte", longFrom(idAccordoEnte));
        query.setParameter("cdRegistroGestAccordo", cdRegistroGestAccordo);
        query.setParameter("aaGestAccordo", aaGestAccordo);
        query.setParameter("cdKeyGestAccordo", cdKeyGestAccordo);
        return !query.getResultList().isEmpty();
    }

    public boolean esisteModelloInfoPerDataIdentificativo(BigDecimal idAccordoEnte, String cdModuloInfo,
            String cdRegistroModuloInfo, BigDecimal aaModuloInfo, String cdKeyModuloInfo) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT a FROM OrgModuloInfoAccordo a " + "WHERE a.orgAccordoEnte.idAccordoEnte = :idAccordoEnte ");

        if (cdModuloInfo != null) {
            queryStr.append("AND a.cdModuloInfo = :cdModuloInfo ");
        } else {
            queryStr.append(
                    "AND a.cdRegistroModuloInfo = :cdRegistroModuloInfo AND a.aaModuloInfo = :aaModuloInfo AND a.cdKeyModuloInfo = :cdKeyModuloInfo ");
        }
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idAccordoEnte", longFrom(idAccordoEnte));
        // query.setParameter("dtRicev", dtRicev);
        if (cdModuloInfo != null) {
            query.setParameter("cdModuloInfo", cdModuloInfo);
        } else {
            query.setParameter("cdRegistroModuloInfo", cdRegistroModuloInfo);
            query.setParameter("aaModuloInfo", aaModuloInfo);
            query.setParameter("cdKeyModuloInfo", cdKeyModuloInfo);
        }

        List l = query.getResultList();
        return l != null && !l.isEmpty();
    }

    public boolean esisteModelloInfoPerDataIdentificativoEnteConvenz(BigDecimal idEnteConvenz, String cdModuloInfo,
            String cdRegistroModuloInfo, BigDecimal aaModuloInfo, String cdKeyModuloInfo) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT a FROM OrgModuloInfoAccordo a " + "WHERE a.orgEnteConvenz.idEnteSiam = :idEnteConvenz ");

        if (cdModuloInfo != null) {
            queryStr.append("AND a.cdModuloInfo = :cdModuloInfo ");
        } else {
            queryStr.append(
                    "AND a.cdRegistroModuloInfo = :cdRegistroModuloInfo AND a.aaModuloInfo = :aaModuloInfo AND a.cdKeyModuloInfo = :cdKeyModuloInfo ");
        }
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
        if (cdModuloInfo != null) {
            query.setParameter("cdModuloInfo", cdModuloInfo);
        } else {
            query.setParameter("cdRegistroModuloInfo", cdRegistroModuloInfo);
            query.setParameter("aaModuloInfo", aaModuloInfo);
            query.setParameter("cdKeyModuloInfo", cdKeyModuloInfo);
        }

        List l = query.getResultList();
        return l != null && !l.isEmpty();
    }

    /**
     * Ritorna la lista delle apparteneenze all'ambiente per l'ente convenzionato passato in input
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return la lista di anagrafiche
     */
    public List<OrgStoEnteAmbienteConvenz> retrieveOrgStoEnteAmbienteConvenz(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT s FROM OrgStoEnteAmbienteConvenz s WHERE s.orgEnteConvenz.idEnteSiam = :idEnteConvenz ORDER BY s.dtIniVal DESC");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgStoEnteAmbienteConvenz> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista di anagrafiche per l'ente convenzionato dato in input
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return la lista di anagrafiche
     */
    public List<OrgStoEnteConvenz> retrieveOrgStoEnteConvenz(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT s FROM OrgStoEnteConvenz s WHERE s.orgEnteSiam.idEnteSiam = :idEnteConvenz ORDER BY s.dtIniVal DESC");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgStoEnteConvenz> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista degli accordi per l'ente gestore dato in input l'ente conservatore
     *
     * @param idUserIamCor
     *            id uesr IAM
     * @param idEnteConserv
     *            id ente conservatore
     * @param isValid
     *            true se valido
     * @param tiEnteConvenz
     *            tipo ente {@link TiEnteConvenz}
     *
     * @return la lista di accordi
     */
    public List<OrgAccordoEnte> retrieveOrgAccordoEnteGestore(BigDecimal idUserIamCor, BigDecimal idEnteConserv,
            boolean isValid, TiEnteConvenz... tiEnteConvenz) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT accordoGestore FROM OrgAccordoEnte accordoGestore, OrgEnteSiam enteGestore, UsrAbilEnteSiam userAbil ");
        String clause = " WHERE ";
        queryStr.append(clause).append("accordoGestore.orgEnteSiam = enteGestore ");
        clause = " AND ";
        queryStr.append(clause).append(
                "(accordoGestore.dtDecAccordo = (SELECT MAX(accordo_max.dtDecAccordo) FROM OrgAccordoEnte accordo_max "
                        + "WHERE accordo_max.orgEnteSiam = enteGestore) OR accordoGestore.dtDecAccordo IS NULL) ");
        queryStr.append(clause).append("EXISTS (SELECT accordo_ente FROM OrgAccordoEnte accordo_ente "
                + "WHERE accordo_ente.orgEnteSiam = enteGestore ");
        if (isValid) {
            queryStr.append(clause).append(
                    "(accordo_ente.dtDecAccordo <= :dataOdierna AND accordo_ente.dtFineValidAccordo >= :dataOdierna) ");
        } else {
            queryStr.append(clause).append(
                    "(accordo_ente.dtDecAccordo > :dataOdierna OR accordo_ente.dtFineValidAccordo < :dataOdierna) ");
        }
        queryStr.append(") ");
        queryStr.append(clause).append("userAbil.orgEnteSiam = enteGestore ");
        queryStr.append(clause).append("enteGestore.tiEnteConvenz IN (:tiEnteConvenz) ");
        queryStr.append(clause).append("userAbil.usrUser.idUserIam = :idUserIamCor ");
        queryStr.append(clause).append("accordoGestore.orgEnteSiamByIdEnteConvenzConserv.idEnteSiam = :idEnteConserv ");

        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("dataOdierna", new Date());
        if (tiEnteConvenz != null && tiEnteConvenz.length > 0) {
            List<TiEnteConvenz> tipiList = Arrays.asList(tiEnteConvenz);
            query.setParameter("tiEnteConvenz", tipiList);
        }
        query.setParameter("idUserIamCor", longFrom(idUserIamCor));
        query.setParameter("idEnteConserv", longFrom(idEnteConserv));

        List<OrgAccordoEnte> list = query.getResultList();
        return list;
    }

    public List<OrgVRicEnteConvenz> getOrgVRicEnteConvenzList(BigDecimal idUserIamCor, String tiEnteConvenz) {
        StringBuilder queryStr = new StringBuilder("SELECT ricEnteConvenz FROM OrgVRicEnteConvenz ricEnteConvenz "
                + "WHERE ricEnteConvenz.idUserIamCor = :idUserIamCor ");
        if (tiEnteConvenz != null) {
            queryStr.append("AND ricEnteConvenz.tiEnteConvenz != :tiEnteConvenz ");
        }
        queryStr.append(
                "AND (ricEnteConvenz.dtDecAccordo <= :dataOdierna AND ricEnteConvenz.dtFineValidAccordo >= :dataOdierna) ");

        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idUserIamCor", idUserIamCor);
        if (tiEnteConvenz != null) {
            query.setParameter("tiEnteConvenz", tiEnteConvenz);
        }
        query.setParameter("dataOdierna", new Date());

        return (List<OrgVRicEnteConvenz>) query.getResultList();
    }

    public List<OrgEnteSiam> getEnteConvenzConservList(BigDecimal idUserIamCor, BigDecimal idEnteSiamGestore) {
        Query query = getEntityManager().createQuery(
                "SELECT DISTINCT enteConvenzConserv FROM OrgVRicEnteConvenz ricEnteConvenz, OrgAccordoEnte accordoEnte "
                        + "JOIN accordoEnte.orgEnteSiam enteConvenz "
                        + "JOIN accordoEnte.orgEnteSiamByIdEnteConvenzGestore enteConvenzGestore "
                        + "JOIN accordoEnte.orgEnteSiamByIdEnteConvenzConserv enteConvenzConserv "
                        + "WHERE ricEnteConvenz.idEnteConvenz = enteConvenz.idEnteSiam "
                        + "AND ricEnteConvenz.idUserIamCor = :idUserIamCor "
                        + "AND enteConvenzGestore.idEnteSiam = :idEnteSiamGestore "
                        + "AND :dtCorrente BETWEEN accordoEnte.dtDecAccordo AND accordoEnte.dtFineValidAccordo ");
        query.setParameter("idUserIamCor", idUserIamCor);
        query.setParameter("idEnteSiamGestore", longFrom(idEnteSiamGestore));
        query.setParameter("dtCorrente", new Date());
        List<OrgEnteSiam> lista = (List<OrgEnteSiam>) query.getResultList();

        return lista;
    }

    /**
     *
     * Metodo che restituisce una lista di enti validi alla data corrente associati all'ambiente
     *
     * @param idAmbienteEnteConvenz
     *            id ambiente/ente convenzionato
     *
     * @return lista di entità  {@link OrgEnteSiam}
     */
    public List<OrgEnteSiam> getEntiValidiAmbiente(BigDecimal idAmbienteEnteConvenz) {
        String queryStr = "SELECT ente FROM OrgEnteSiam ente "
                + "WHERE ente.orgAmbienteEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz "
                + "AND ente.dtIniVal < :dtCorrente AND ente.dtCessazione > :dtCorrente ";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAmbienteEnteConvenz", longFrom(idAmbienteEnteConvenz));
        query.setParameter("dtCorrente", new Date());

        List<OrgEnteSiam> listaEnti = query.getResultList();
        return listaEnti;
    }

    public List<UsrUser> getUtentiAttiviAbilitatiAdAmbiente(BigDecimal idAmbienteEnteConvenz) {
        String queryStr = "SELECT DISTINCT user FROM UsrDichAbilOrganiz dichAbilOrganiz "
                + "JOIN dichAbilOrganiz.usrUsoUserApplic usoUserApplic " + "JOIN usoUserApplic.usrUser user "
                + "JOIN dichAbilOrganiz.usrOrganizIam organizIam " + "JOIN organizIam.aplTipoOrganiz tipoOrganiz "
                + "WHERE dichAbilOrganiz.tiScopoDichAbilOrganiz = 'ALL_ORG_CHILD' "
                + "AND tipoOrganiz.nmTipoOrganiz = 'AMBIENTE' " + "AND user.flAttivo = '1' "
                + "AND usoUserApplic.aplApplic.nmApplic = 'SACER_IAM' "
                + "AND organizIam.idOrganizApplic = :idAmbienteEnteConvenz ";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        List<UsrUser> list = query.getResultList();
        return list;
    }

    /* INIZIO DA TESTARE */
    public List<UsrUser> getUtentiAttiviAbilitatiAdAmbiente2(BigDecimal idAmbienteEnteConvenz) {
        String queryStr = "SELECT DISTINCT user FROM UsrVAbilAmbSacerXente abilAmbSacerXente, UsrUser user "
                + "WHERE user.flAttivo = '1' " + "AND abilAmbSacerXente.nmApplic = 'SACER_IAM' "
                + "AND abilAmbSacerXente.id.idOrganizApplic = :idAmbienteEnteConvenz "
                + "AND user.idUserIam = abilAmbSacerXente.id.idUserIam ";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        List<UsrUser> list = query.getResultList();
        return list;
    }

    public List<UsrUser> getUtentiAttiviAbilitatiAdEnte(BigDecimal idEnteSiam) {
        String queryStr = "SELECT DISTINCT user FROM UsrVAbilEnteSacerXstrut abilEnteSacerXstrut, UsrUser user "
                + "WHERE user.flAttivo = '1' " + "AND abilEnteSacerXstrut.nmApplic = 'SACER_IAM' "
                + "AND abilEnteSacerXstrut.id.idOrganizApplic = :idEnteSiam "
                + "AND user.idUserIam = abilEnteSacerXstrut.id.idUserIam ";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteSiam", idEnteSiam);
        List<UsrUser> list = query.getResultList();
        return list;
    }

    /* FINE DA TESTARE */
    /**
     * Ritorna la lista di accordi per l'ente convenzionato dato in input
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return la lista di accordi
     */
    public List<OrgAccordoEnte> retrieveOrgAccordoEnte(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT s FROM OrgAccordoEnte s WHERE s.orgEnteSiam.idEnteSiam = :idEnteConvenz ORDER BY s.dtDecAccordo DESC");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgAccordoEnte> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista degli accordi validi (esclusi quelli dell'ente dato in input) in cui l'ente risulta, in base al
     * tipo dato in input, CONSERVATORE o GESTORE dell'accordo.
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param tiEnteConvenz
     *            tipo ente convenzionato
     *
     * @return la lista di accordi
     */
    public List<OrgAccordoEnte> retrieveOrgAccordoEnteValidByTipo(BigDecimal idEnteConvenz, String tiEnteConvenz) {
        StringBuilder queryStr = new StringBuilder("SELECT accordo FROM OrgAccordoEnte accordo ");
        String clause = "WHERE ";
        switch (TiEnteConvenz.valueOf(tiEnteConvenz)) {
        // case AMMINISTRATORE:
        // queryStr.append(clause)
        // .append("accordo.orgEnteSiamByIdEnteConvenzAmministratore.idEnteSiam = :idEnteConvenz ");
        // clause = "AND ";
        // break;
        case CONSERVATORE:
            queryStr.append(clause).append("accordo.orgEnteSiamByIdEnteConvenzConserv.idEnteSiam = :idEnteConvenz ");
            clause = "AND ";
            break;
        case GESTORE:
            queryStr.append(clause).append("accordo.orgEnteSiamByIdEnteConvenzGestore.idEnteSiam = :idEnteConvenz ");
            clause = "AND ";
            break;
        default:
            break;
        }
        queryStr.append(clause)
                .append("accordo.dtDecAccordo <= :dataOdierna AND accordo.dtFineValidAccordo >= :dataOdierna "
                        + "AND accordo.orgEnteSiam.idEnteSiam != :idEnteConvenz");
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("dataOdierna", new Date());
        List<OrgAccordoEnte> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna l'accordo più recente per l'ente convenzionato dato in input. Se esiste un accordo con data di decorrenza
     * a NULL, ritorna quest'ultimo.
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return l'accordo più recente oppure l'accordo con la data decorrenza non valorizzata
     */
    public OrgAccordoEnte retrieveOrgAccordoEntePiuRecente(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT s FROM OrgAccordoEnte s "
                + "WHERE s.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "AND ((s.dtDecAccordo = (SELECT MAX(accordo_max.dtDecAccordo) FROM OrgAccordoEnte accordo_max "
                + "WHERE accordo_max.orgEnteSiam.idEnteSiam = s.orgEnteSiam.idEnteSiam "
                + "AND NOT EXISTS (SELECT accordo_null FROM OrgAccordoEnte accordo_null "
                + "WHERE accordo_null.orgEnteSiam.idEnteSiam = s.orgEnteSiam.idEnteSiam "
                + "AND accordo_null.dtDecAccordo IS NULL))) OR s.dtDecAccordo IS NULL) ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgAccordoEnte> accordoList = query.getResultList();
        if (!accordoList.isEmpty() && accordoList.get(0) != null) {
            return accordoList.get(0);
        }
        return null;
    }

    /**
     * Ritorna l'accordo valido alla data corrente per l'ente convenzionato dato in input
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return la lista di accordi
     */
    public OrgAccordoEnte retrieveOrgAccordoEnteValido(BigDecimal idEnteConvenz) {
        Query query = getEntityManager()
                .createQuery("SELECT s FROM OrgAccordoEnte s " + "WHERE s.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                        + "AND s.dtDecAccordo <= :dataOdierna AND s.dtFineValidAccordo >= :dataOdierna ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("dataOdierna", new Date());
        List<OrgAccordoEnte> accordoList = query.getResultList();
        if (!accordoList.isEmpty() && accordoList.get(0) != null) {
            return accordoList.get(0);
        }
        return null;
    }

    /**
     * Ritorna la lista di strutture versanti per l'ente convenzionato dato in input
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return la lista di strutture
     */
    public List<OrgEnteConvenzOrg> retrieveOrgEnteConvenzOrg(BigDecimal idEnteConvenz) {
        Query query = getEntityManager()
                .createQuery("SELECT s FROM OrgEnteConvenzOrg s WHERE s.orgEnteSiam.idEnteSiam = :idEnteConvenz");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgEnteConvenzOrg> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista di organizzazioni per l'ente convenzionato dato in input
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param filterValid
     *            true se filtro valido
     *
     * @return la lista di strutture
     */
    public List<OrgVEnteConvenzByOrganiz> retrieveOrgVEnteConvenzByOrganiz(BigDecimal idEnteConvenz,
            boolean filterValid) {
        StringBuilder queryStr = new StringBuilder("SELECT organiz FROM OrgVEnteConvenzByOrganiz organiz ");
        String whereWord = "WHERE ";
        if (idEnteConvenz != null) {
            queryStr.append(whereWord).append("organiz.idEnteConvenz = :idEnteConvenz ");
            whereWord = "AND ";
        }
        if (filterValid) {
            queryStr.append(whereWord).append("organiz.dtIniVal <= :filterDate AND organiz.dtFineVal >= :filterDate ");
        }
        queryStr.append("ORDER BY organiz.nmApplic ");

        Query query = getEntityManager().createQuery(queryStr.toString());
        if (idEnteConvenz != null) {
            query.setParameter("idEnteConvenz", idEnteConvenz);
        }
        if (filterValid) {
            Date now = Calendar.getInstance().getTime();
            query.setParameter("filterDate", now);
        }

        List<OrgVEnteConvenzByOrganiz> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista di associazioni tra la struttura versante e per l'ente convenzionato dati in input
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param idOrganizIam
     *            id organizzazione IAM
     *
     * @return la lista di strutture
     */
    public List<OrgEnteConvenzOrg> retrieveOrgEnteConvenzOrg(BigDecimal idEnteConvenz, BigDecimal idOrganizIam) {
        Query query = getEntityManager()
                .createQuery("SELECT s FROM OrgEnteConvenzOrg s " + "WHERE s.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                        + "AND s.usrOrganizIam.idOrganizIam = :idOrganizIam " + "ORDER BY s.dtIniVal DESC ");
        query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
        query.setParameter("idOrganizIam", idOrganizIam.longValue());
        List<OrgEnteConvenzOrg> list = query.getResultList();
        return list;
    }

    /**
     * Ritorna la lista di associazioni per la struttura data in input
     *
     * @param idOrganizIam
     *            id organizzazione IAM
     *
     * @return la lista di strutture
     */
    public List<OrgEnteConvenzOrg> retrieveOrgEnteConvenzOrgForOrganiz(BigDecimal idOrganizIam) {
        Query query = getEntityManager().createQuery("SELECT s FROM OrgEnteConvenzOrg s "
                + "WHERE s.usrOrganizIam.idOrganizIam = :idOrganizIam " + "ORDER BY s.dtIniVal DESC ");
        query.setParameter("idOrganizIam", idOrganizIam.longValue());
        List<OrgEnteConvenzOrg> list = query.getResultList();
        return list;
    }

    public void updateDateOrgEnteConvenzOrg(long id, Date dtIniVal, Date dtFineVal) {
        OrgEnteConvenzOrg enteConvenzOrgOld = getEntityManager().find(OrgEnteConvenzOrg.class, id);
        enteConvenzOrgOld.setDtIniVal(dtIniVal);
        enteConvenzOrgOld.setDtFineVal(dtFineVal);
        getEntityManager().flush();
    }

    public void updateDataFineValOrgEnteConvenzOrg(long id, Date dtIniVal) {
        OrgEnteConvenzOrg enteConvenzOrgOld = getEntityManager().find(OrgEnteConvenzOrg.class, id);
        Calendar c = Calendar.getInstance();
        c.setTime(dtIniVal);
        c.add(Calendar.DATE, -1);
        Date dtFineValCalcolataOldEnte = c.getTime();
        enteConvenzOrgOld.setDtFineVal(dtFineValCalcolataOldEnte);
        getEntityManager().flush();
    }

    /**
     * Controlla se esistono dei servizi riferiti ad accordi dellâ€™ente convenzionato giÃ  inseriti in una fattura.
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return true se esistono i servizi
     */
    public boolean checkEsistenzaServiziInFatturaPerEnteConvenz(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT enteSiam FROM OrgEnteSiam enteSiam "
                + "WHERE enteSiam.idEnteSiam = :idEnteConvenz " + "AND EXISTS "
                + "(SELECT servizioFattura FROM OrgServizioFattura servizioFattura "
                + "JOIN servizioFattura.orgServizioErog servizioErog " + "JOIN servizioErog.orgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam = enteSiam) ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgEnteSiam> list = query.getResultList();
        return !list.isEmpty();
    }

    public boolean checkEsistenzaServiziInFatturaPerAccordo(BigDecimal idAccordoEnte) {
        Query query = getEntityManager().createQuery("SELECT accordoEnte FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.idAccordoEnte = :idAccordoEnte "
                + "AND EXISTS (SELECT servizioFattura FROM OrgServizioFattura servizioFattura "
                + "WHERE servizioFattura.orgServizioErog.orgAccordoEnte = accordoEnte) ");
        query.setParameter("idAccordoEnte", longFrom(idAccordoEnte));
        List<OrgAccordoEnte> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Controlla se esistono dei servizi erogati già  inseriti in una fattura in stato calcolata
     *
     * @param idServizioErogato
     *            id servizio erogato
     *
     * @return true se esistono i servizi
     */
    public boolean checkEsistenzaServiziInFattura(BigDecimal idServizioErogato) {
        Query query = getEntityManager()
                .createQuery("SELECT COUNT(servizioFattura) FROM OrgServizioFattura servizioFattura "
                        + "JOIN servizioFattura.orgFatturaEnte fatturaEnte, OrgStatoFatturaEnte statoFatturaEnte "
                        + "WHERE servizioFattura.orgServizioErog.idServizioErogato = :idServizioErogato "
                        + "AND fatturaEnte.idStatoFatturaEnteCor = statoFatturaEnte.idStatoFatturaEnte "
                        + "AND statoFatturaEnte.tiStatoFatturaEnte != 'CALCOLATA' ");
        query.setParameter("idServizioErogato", longFrom(idServizioErogato));
        return (Long) query.getSingleResult() > 0;
    }

    /**
     * Controlla che nel periodo compreso tra data di inizio e di fine validitÃ  la struttura versante non sia giÃ 
     * associata ad un altro ente convenzionato ricercando sulla tabella dell'associazione ovvero OrgEnteConvenzOrg
     *
     * @param idOrganizIam
     *            id organizzazione IAM
     * @param dtIniVal
     *            data inizio validita
     * @param dtFineVal
     *            data fine validita
     * @param idEnteConvenzOrgDaEscludere
     *            eventuale id da non considerare nel controllo, in quanto riferito al record stesso!
     *
     * @return true se esiste già  l'associazione
     */
    public boolean checkEsistenzaAssociazioneEnteConvenzStrutVers(BigDecimal idOrganizIam, Date dtIniVal,
            Date dtFineVal, BigDecimal idEnteConvenzOrgDaEscludere) {
        String queryStr = "SELECT enteConvenzOrg FROM OrgEnteConvenzOrg enteConvenzOrg "
                + "WHERE enteConvenzOrg.usrOrganizIam.idOrganizIam = :idOrganizIam "
                + "AND ((enteConvenzOrg.dtIniVal <= :dtIniVal AND enteConvenzOrg.dtFineVal >= :dtIniVal) "
                + "OR (enteConvenzOrg.dtIniVal <= :dtFineVal AND enteConvenzOrg.dtFineVal >= :dtFineVal)) ";

        if (idEnteConvenzOrgDaEscludere != null) {
            queryStr = queryStr + "AND enteConvenzOrg.idEnteConvenzOrg != :idEnteConvenzOrgDaEscludere ";
        }
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idOrganizIam", longFrom(idOrganizIam));
        query.setParameter("dtIniVal", dtIniVal);
        query.setParameter("dtFineVal", dtFineVal);
        if (idEnteConvenzOrgDaEscludere != null) {
            query.setParameter("idEnteConvenzOrgDaEscludere", longFrom(idEnteConvenzOrgDaEscludere));
        }
        List<OrgEnteConvenzOrg> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Controlla se esiste almeno un utente avente ente convenzionato di appartenenza uguale all'ente passato in
     * ingresso
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return true se esistono degli utenti
     */
    public boolean checkEsistenzaUtenteEnteAppart(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT enteSiam FROM OrgEnteSiam enteSiam " + "WHERE enteSiam.idEnteSiam = :idEnteConvenz "
                        + "AND EXISTS " + "(SELECT user FROM UsrUser user " + "WHERE user.orgEnteSiam = enteSiam) ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgEnteSiam> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Controlla se l'utente dato in input ha ente convenzionato di appartenenza uguale all'ente passato in ingresso
     *
     * @param idUserIam
     *            id user IAM
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return true se utente appartiene all'ente convenzionato
     */
    public boolean checkAppartenenzaUtenteEnte(BigDecimal idUserIam, BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT user FROM UsrUser user "
                + "WHERE user.idUserIam = :idUserIam AND user.orgEnteSiam.idEnteSiam = :idEnteConvenz ");
        query.setParameter("idUserIam", idUserIam.longValue());
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<UsrUser> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Controlla se esiste almeno un utente avente ente convenzionato uguale all'ente passato in ingresso come
     * abilitazione di tipo UN_ENTE
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return true se esistono degli utenti
     */
    public boolean checkEsistenzaEnteAbilUnEnte(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT enteSiam FROM OrgEnteSiam enteSiam " + "WHERE enteSiam.idEnteSiam = :idEnteConvenz "
                        + "AND EXISTS " + "(SELECT dichAbilEnteConvenz FROM UsrDichAbilEnteConvenz dichAbilEnteConvenz "
                        + "WHERE dichAbilEnteConvenz.orgEnteSiam = enteSiam) ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgEnteSiam> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Controlla se esiste almeno una richiesta utente avente ente convenzionato uguale all'ente passato in ingresso
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return true se esistono delle richieste
     */
    public boolean checkEsistenzaRichiesteUtente(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT enteSiam FROM OrgEnteSiam enteSiam " + "WHERE enteSiam.idEnteSiam = :idEnteConvenz "
                        + "AND EXISTS " + "(SELECT richGestUser FROM UsrRichGestUser richGestUser "
                        + "WHERE richGestUser.orgEnteSiam = enteSiam) ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgEnteSiam> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Controlla se esiste almeno una associazione struttura - ente convenzionato (uguale all'ente passato in ingresso)
     * in periodo di validità
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return true se esistono delle strutture
     */
    public boolean checkEsistenzaAssociazioneStruttureEnte(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT enteSiam FROM OrgEnteSiam enteSiam "
                + "WHERE enteSiam.idEnteSiam = :idEnteConvenz " + "AND EXISTS "
                + "(SELECT enteConvenzOrg FROM OrgEnteConvenzOrg enteConvenzOrg "
                + "WHERE enteConvenzOrg.orgEnteSiam = enteSiam "
                + "AND (enteConvenzOrg.dtIniVal <= :dataOdierna AND enteConvenzOrg.dtFineVal >= :dataOdierna)) ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("dataOdierna", new Date());
        List<OrgEnteSiam> list = query.getResultList();
        return !list.isEmpty();
    }

    public List<OrgTipoAccordo> retrieveOrgTipoAccordo() {
        Query query = getEntityManager()
                .createQuery("SELECT tipoAccordo FROM OrgTipoAccordo tipoAccordo ORDER BY tipoAccordo.cdTipoAccordo ");
        List<OrgTipoAccordo> list = query.getResultList();
        return list;
    }

    public List<OrgTipoAccordo> retrieveOrgTipoAccordoNoClasseEnte() {
        Query query = getEntityManager().createQuery("SELECT tipoAccordo FROM OrgTipoAccordo tipoAccordo "
                + "WHERE tipoAccordo.cdAlgoTariffario = 'NO_CLASSE_ENTE' ORDER BY tipoAccordo.cdTipoAccordo ");
        List<OrgTipoAccordo> list = query.getResultList();
        return list;
    }

    public List<OrgTipiGestioneAccordo> retrieveOrgTipiGestioneAccordo() {
        Query query = getEntityManager().createQuery(
                "SELECT tipoGestioneAccordo FROM OrgTipiGestioneAccordo tipoGestioneAccordo ORDER BY tipoGestioneAccordo.cdTipoGestioneAccordo ");
        List<OrgTipiGestioneAccordo> list = query.getResultList();
        return list;
    }

    public List<OrgTariffario> retrieveOrgTariffario(BigDecimal idTipoAccordo) {
        Query query = getEntityManager().createQuery("SELECT tariffario FROM OrgTariffario tariffario "
                + "WHERE tariffario.orgTipoAccordo.idTipoAccordo = :idTipoAccordo ORDER BY tariffario.dtIniVal DESC");
        query.setParameter("idTipoAccordo", idTipoAccordo.longValue());
        List<OrgTariffario> list = query.getResultList();
        return list;
    }

    public List<OrgServizioErog> retrieveOrgServizioErog(BigDecimal idAccordoEnte) {
        Query query = getEntityManager().createQuery("SELECT servizioErog FROM OrgServizioErog servizioErog "
                + "WHERE servizioErog.orgAccordoEnte.idAccordoEnte = :idAccordoEnte "
                + "ORDER BY servizioErog.nmServizioErogato ");
        query.setParameter("idAccordoEnte", idAccordoEnte.longValue());
        List<OrgServizioErog> list = query.getResultList();
        return list;
    }

    public List<OrgModuloInfoAccordo> retrieveOrgModuloInfoAccordo(BigDecimal idAccordoEnte) {
        Query query = getEntityManager().createQuery("SELECT moduloInfo FROM OrgModuloInfoAccordo moduloInfo "
                + "WHERE moduloInfo.orgAccordoEnte.idAccordoEnte = :idAccordoEnte " + "ORDER BY moduloInfo.dtRicev ");
        query.setParameter("idAccordoEnte", idAccordoEnte.longValue());
        List<OrgModuloInfoAccordo> list = query.getResultList();
        return list;
    }

    public List<OrgModuloInfoAccordo> retrieveOrgModuloInfoAccordoByEnte(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT moduloInfo FROM OrgModuloInfoAccordo moduloInfo "
                + "WHERE moduloInfo.orgEnteConvenz.idEnteSiam = :idEnteConvenz " + "ORDER BY moduloInfo.dtRicev ");
        query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
        List<OrgModuloInfoAccordo> list = query.getResultList();
        return list;
    }

    public List<OrgGestioneAccordo> retrieveOrgGestioneAccordo(BigDecimal idAccordoEnte) {
        Query query = getEntityManager().createQuery("SELECT gestioneAccordo FROM OrgGestioneAccordo gestioneAccordo "
                + "WHERE gestioneAccordo.orgAccordoEnte.idAccordoEnte = :idAccordoEnte "
                + "ORDER BY gestioneAccordo.dtGestAccordo DESC ");
        query.setParameter("idAccordoEnte", idAccordoEnte.longValue());
        List<OrgGestioneAccordo> list = query.getResultList();
        return list;
    }

    public UsrOrganizIam getUsrOrganizIam(String nmApplic, String nmTipoOrganiz, BigDecimal idOrganizApplic) {
        Query query = getEntityManager().createQuery("SELECT organizIam FROM UsrOrganizIam organizIam "
                + "JOIN organizIam.aplApplic applic " + "JOIN organizIam.aplTipoOrganiz tipoOrganiz "
                + "WHERE applic.nmApplic = :nmApplic " + "AND tipoOrganiz.nmTipoOrganiz = :nmTipoOrganiz "
                + "AND tipoOrganiz.aplApplic = applic " + "AND organizIam.idOrganizApplic = :idOrganizApplic");
        query.setParameter("nmApplic", nmApplic);
        query.setParameter("nmTipoOrganiz", nmTipoOrganiz);
        query.setParameter("idOrganizApplic", idOrganizApplic);
        List<UsrOrganizIam> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public boolean checkEsistenzaAltriAccordiDtDecDtFineValid(BigDecimal idEnteConvenz, Date dtDecAccordo,
            Date dtFineValidAccordo, BigDecimal idAccordoDaEscludere) {
        String queryStr = "SELECT COUNT(accordoEnte) FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz " + "AND ("
                // Se la data di inizio validità o quella di fine validitÃ  accordo, ricadono all'interno di un
                // intervallo già esistente
                + "(accordoEnte.dtDecAccordo <= :dtDecAccordo AND accordoEnte.dtFineValidAccordo >= :dtDecAccordo) "
                + "OR (accordoEnte.dtDecAccordo <= :dtFineValidAccordo AND accordoEnte.dtFineValidAccordo >= :dtFineValidAccordo) "
                // oppure se l'intevallo del nuovo accordo si sovrappone totalmente ad un intervallo già esistente
                + "OR (accordoEnte.dtDecAccordo >= :dtDecAccordo AND accordoEnte.dtFineValidAccordo <= :dtFineValidAccordo))";
        if (idAccordoDaEscludere != null) {
            queryStr = queryStr + "AND accordoEnte.idAccordoEnte != :idAccordoDaEscludere ";
        }
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("dtDecAccordo", dtDecAccordo);
        query.setParameter("dtFineValidAccordo", dtFineValidAccordo);
        if (idAccordoDaEscludere != null) {
            query.setParameter("idAccordoDaEscludere", longFrom(idAccordoDaEscludere));
        }
        return (Long) query.getSingleResult() != 0;
    }

    public boolean checkEsistenzaAccordoDtDecDtScadNulli(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT COUNT(accordoEnte) FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "AND (accordoEnte.dtDecAccordo IS NULL OR accordoEnte.dtScadAccordo IS NULL) ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return (Long) query.getSingleResult() != 0;
    }

    public boolean isDtDecDtFineValIndefinite(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT accordoEnte FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgAccordoEnte> accordoEnteList = query.getResultList();
        for (OrgAccordoEnte accordoEnte : accordoEnteList) {
            if (DateUtils.truncate(accordoEnte.getDtDecAccordo(), Calendar.DATE).equals(DateUtil.MAX_DATE) || DateUtils
                    .truncate(accordoEnte.getDtFineValidAccordo(), Calendar.DATE).equals(DateUtil.MAX_DATE)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDtDecAccordoEmpty(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT COUNT(accordoEnte) FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "AND accordoEnte.dtDecAccordo IS NULL ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return (Long) query.getSingleResult() != 0;
    }

    public boolean checkEsistenzaAccordoSuDataRegistrazione(BigDecimal idEnteConvenz, Date dtRegAccordo,
            BigDecimal idAccordoDaEscludere) {
        String queryStr = "SELECT COUNT(accordoEnte) FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "AND accordoEnte.dtRegAccordo = :dtRegAccordo ";
        if (idAccordoDaEscludere != null) {
            queryStr = queryStr + "AND accordoEnte.idAccordoEnte != :idAccordoDaEscludere ";
        }
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("dtRegAccordo", dtRegAccordo);
        if (idAccordoDaEscludere != null) {
            query.setParameter("idAccordoDaEscludere", longFrom(idAccordoDaEscludere));
        }
        return (Long) query.getSingleResult() != 0;
    }

    public boolean checkEsistenzaAccordiAttivi(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT 1 FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "AND accordoEnte.dtDecAccordo IS NOT NULL " + "AND accordoEnte.dtFineValidAccordo > :dataOdierna ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("dataOdierna", new Date());
        return !query.getResultList().isEmpty();
    }

    public int countAccordiSuEnteConvenz(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT COUNT(accordoEnte) FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return (int) query.getSingleResult();
    }

    public boolean isDataCessazioneEnteInferioreUgualeDataAccordo(long idEnteSiam, Date dtCessazione) {
        Query query = getEntityManager().createQuery("SELECT accordoEnte FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteSiam "
                + "ORDER BY accordoEnte.dtFineValidAccordo DESC ");
        query.setParameter("idEnteSiam", longFrom(idEnteSiam));
        List<OrgAccordoEnte> accordoEnteList = query.getResultList();
        if (!accordoEnteList.isEmpty()) {
            OrgAccordoEnte accordoEnte = accordoEnteList.get(0);
            if (dtCessazione.compareTo(accordoEnte.getDtFineValidAccordo()) < 0) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEsistenzaUtenteAbilitatoXEnte(BigDecimal idUserIam, BigDecimal idEnteConvenz) {
        Query query = getEntityManager()
                .createQuery("SELECT 1 FROM UsrVAbilEnteConvenz abilEnteConvenz, OrgEnteSiam enteSiam "
                        + "WHERE abilEnteConvenz.id.idUserIam = :idUserIam "
                        + "AND abilEnteConvenz.id.idEnteConvenz = enteSiam.idEnteSiam "
                        + "AND enteSiam.idEnteSiam = :idEnteConvenz ");
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return !query.getResultList().isEmpty();
    }

    public boolean checkEsistenzaAmbienteValidoDtDecDtFineValid(BigDecimal idAmbienteEnteConvenz, Date dtDecAccordo,
            Date dtFineValidAccordo, BigDecimal idAmbienteDaEscludere) {
        String queryStr = "SELECT COUNT(ambienteEnteConvenz) FROM OrgAmbienteEnteConvenz ambienteEnteConvenz "
                + "WHERE ambienteEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz " + "AND ("
                // Se la data di inizio validità o quella di fine validitÃ  accordo, ricadono in all'interno di un
                // intervallo già esistente per l'ambiente
                + "(ambienteEnteConvenz.dtIniVal <= :dtDecAccordo AND ambienteEnteConvenz.dtFineVal >= :dtDecAccordo) "
                + "OR (ambienteEnteConvenz.dtIniVal <= :dtFineValidAccordo AND ambienteEnteConvenz.dtFineVal >= :dtFineValidAccordo) "
                // oppure se l'intevallo del nuovo accordo si sovrappone totalmente ad un intervallo già esistente per
                // l'ambiente
                + "OR(ambienteEnteConvenz.dtIniVal >= :dtDecAccordo AND ambienteEnteConvenz.dtFineVal <= :dtFineValidAccordo))";
        if (idAmbienteDaEscludere != null) {
            queryStr = queryStr + "AND ambienteEnteConvenz.idAmbienteEnteConvenz != :idAmbienteDaEscludere ";
        }
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAmbienteEnteConvenz", longFrom(idAmbienteEnteConvenz));
        query.setParameter("dtDecAccordo", dtDecAccordo);
        query.setParameter("dtFineValidAccordo", dtFineValidAccordo);
        if (idAmbienteDaEscludere != null) {
            query.setParameter("idAmbienteDaEscludere", longFrom(idAmbienteDaEscludere));
        }
        return (Long) query.getSingleResult() != 0;
    }

    public List<UsrTipoDatoIam> getUsrTipoDatoIam(String nmApplic, String nmTipoOrganiz, BigDecimal idOrganizApplic,
            String nmClasseTipoDato) {
        Query query = getEntityManager().createQuery("SELECT tipoDatoIam FROM UsrTipoDatoIam tipoDatoIam "
                + "JOIN tipoDatoIam.usrOrganizIam organizIam " + "JOIN tipoDatoIam.aplClasseTipoDato classeTipoDato "
                + "JOIN organizIam.aplTipoOrganiz tipoOrganiz " + "JOIN tipoOrganiz.aplApplic applic "
                + "WHERE applic.nmApplic = :nmApplic " + "AND tipoOrganiz.nmTipoOrganiz = :nmTipoOrganiz "
                + "AND organizIam.idOrganizApplic = :idOrganizApplic "
                + "AND classeTipoDato.nmClasseTipoDato = :nmClasseTipoDato " + "AND classeTipoDato.aplApplic = applic "
                + "ORDER BY tipoDatoIam.nmTipoDato ");
        query.setParameter("nmApplic", nmApplic);
        query.setParameter("nmTipoOrganiz", nmTipoOrganiz);
        query.setParameter("idOrganizApplic", idOrganizApplic);
        query.setParameter("nmClasseTipoDato", nmClasseTipoDato);
        List<UsrTipoDatoIam> list = query.getResultList();
        return list;
    }

    public List<OrgClasseEnteConvenz> retrieveOrgClasseEnteConvenz() {
        Query query = getEntityManager().createQuery(
                "SELECT classeEnteConvenz FROM OrgClasseEnteConvenz classeEnteConvenz ORDER BY classeEnteConvenz.cdClasseEnteConvenz ");
        List<OrgClasseEnteConvenz> list = query.getResultList();
        return list;
    }

    public List<OrgTariffa> retrieveOrgTariffa(BigDecimal idTipoServizio, BigDecimal idTariffario,
            BigDecimal idClasseEnteConvenz) {
        StringBuilder queryStr = new StringBuilder("SELECT tariffa FROM OrgTariffa tariffa ");
        String whereWord = "WHERE ";
        if (idTipoServizio != null) {
            queryStr.append(whereWord).append("tariffa.orgTipoServizio.idTipoServizio = :idTipoServizio ");
            whereWord = "AND ";
        }
        if (idClasseEnteConvenz != null) {
            queryStr.append(whereWord)
                    .append("tariffa.orgClasseEnteConvenz.idClasseEnteConvenz = :idClasseEnteConvenz ");
            whereWord = "AND ";
        }
        if (idTariffario != null) {
            queryStr.append(whereWord).append("tariffa.orgTariffario.idTariffario = :idTariffario ");
            whereWord = "AND ";
        }
        queryStr.append("ORDER BY tariffa.nmTariffa ");

        Query query = getEntityManager().createQuery(queryStr.toString());
        if (idClasseEnteConvenz != null) {
            query.setParameter("idClasseEnteConvenz", longFrom(idClasseEnteConvenz));
        }
        if (idTariffario != null) {
            query.setParameter("idTariffario", longFrom(idTariffario));
        }
        if (idTipoServizio != null) {
            query.setParameter("idTipoServizio", longFrom(idTipoServizio));
        }
        List<OrgTariffa> list = query.getResultList();
        return list;
    }

    @Deprecated
    public Date getDtFirstVersTipiUdByTipoServizio(long idTipoServizio, List<BigDecimal> idOrganizApplicSelected) {
        StringBuilder queryStr = new StringBuilder("SELECT tipoUnitaDoc.dtFirstVers FROM DecTipoUnitaDoc tipoUnitaDoc "
                + "WHERE tipoUnitaDoc.orgTipoServizio.idTipoServizio = :idTipoServizio ");
        if (idOrganizApplicSelected != null && !idOrganizApplicSelected.isEmpty()) {
            queryStr.append("AND tipoUnitaDoc.orgStrut.idStrut IN (:idOrganizApplicSelected) ");
        }
        queryStr.append("ORDER BY tipoUnitaDoc.dtFirstVers ASC ");
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idTipoServizio", bigDecimalFrom(idTipoServizio));
        if (idOrganizApplicSelected != null && !idOrganizApplicSelected.isEmpty()) {
            query.setParameter("idOrganizApplicSelected", longListFrom(idOrganizApplicSelected));
        }
        List<Date> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @deprecated
     *
     * @param idSistemaVersante
     *            id sistema versante
     * @param idOrganizApplicSelected
     *            id organizzazione selezionata
     *
     * @return data del primo versamento
     */
    @Deprecated
    public Date getDtFirstVersTipiUdBySistemaVersante(long idSistemaVersante,
            List<BigDecimal> idOrganizApplicSelected) {
        StringBuilder queryStr = new StringBuilder("SELECT tipoUnitaDoc.dtFirstVers FROM DecTipoUnitaDoc tipoUnitaDoc "
                + "WHERE tipoUnitaDoc.aplSistemaVersante.idSistemaVersante = :idSistemaVersante ");
        if (idOrganizApplicSelected != null && !idOrganizApplicSelected.isEmpty()) {
            queryStr.append("AND tipoUnitaDoc.orgStrut.idStrut IN (:idOrganizApplicSelected) ");
        }
        queryStr.append("ORDER BY tipoUnitaDoc.dtFirstVers ASC ");
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idSistemaVersante", bigDecimalFrom(idSistemaVersante));
        if (idOrganizApplicSelected != null && !idOrganizApplicSelected.isEmpty()) {
            query.setParameter("idOrganizApplicSelected", longListFrom(idOrganizApplicSelected));
        }
        List<Date> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<OrgServizioFattura> retrieveOrgServizioFattura(long idServizioErogato) {
        Query query = getEntityManager().createQuery("SELECT servizioFattura FROM OrgServizioFattura servizioFattura "
                + "WHERE servizioFattura.orgServizioErog.idServizioErogato = :idServizioErogato ");
        query.setParameter("idServizioErogato", longFrom(idServizioErogato));
        List<OrgServizioFattura> list = query.getResultList();
        return list;
    }

    public String getUltimoStatoFatturaEnte(long idFatturaEnte) {
        Query query = getEntityManager()
                .createQuery("SELECT statoFatturaEnte.tiStatoFatturaEnte FROM OrgStatoFatturaEnte statoFatturaEnte "
                        + "WHERE statoFatturaEnte.orgFatturaEnte.idFatturaEnte = :idFatturaEnte "
                        + "ORDER BY statoFatturaEnte.dtRegStatoFatturaEnte DESC ");
        query.setParameter("idFatturaEnte", longFrom(idFatturaEnte));
        List<String> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public OrgAccordoEnte getLastAccordoEnte(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT accordoEnte FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "ORDER BY accordoEnte.dtRegAccordo DESC ");
        query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
        List<OrgAccordoEnte> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public BigDecimal getImValoreTariffaDaScaglione(long idTariffa) {
        Query query = getEntityManager()
                .createQuery("SELECT scaglioneTariffa.imScaglione FROM OrgScaglioneTariffa scaglioneTariffa "
                        + "WHERE scaglioneTariffa.orgTariffa.idTariffa = :idTariffa "
                        + "ORDER BY scaglioneTariffa.niIniScaglione ASC ");
        query.setParameter("idTariffa", longFrom(idTariffa));
        List<BigDecimal> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<BigDecimal> getIdSistemiVersantiList(long idEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT DISTINCT organizUsoSisVers.id.idSistemaVersante FROM OrgEnteConvenzOrg enteConvenzOrg, AplVLisOrganizUsoSisVers organizUsoSisVers "
                        + "WHERE enteConvenzOrg.usrOrganizIam.idOrganizIam = organizUsoSisVers.id.idOrganizIam "
                        + "AND enteConvenzOrg.orgEnteSiam.idEnteSiam = :idEnteConvenz ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<BigDecimal> list = query.getResultList();
        return list;
    }

    public List<OrgTipoServizio> retrieveOrgTipoServizio(long idClasseEnteConvenz, long idTariffario,
            List<BigDecimal> idTipoServizioEsclusi) {
        StringBuilder queryStr = new StringBuilder("SELECT tipoServizio FROM OrgTipoServizio tipoServizio "
                + "WHERE EXISTS (SELECT tariffa FROM OrgTariffa tariffa "
                + "WHERE tariffa.orgTariffario.idTariffario = :idTariffario "
                + "AND tariffa.orgClasseEnteConvenz.idClasseEnteConvenz = :idClasseEnteConvenz "
                + "AND tariffa.orgTipoServizio = tipoServizio) ");
        if (idTipoServizioEsclusi != null && !idTipoServizioEsclusi.isEmpty()) {
            queryStr.append("AND tipoServizio.idTipoServizio NOT IN (:idTipoServizioEsclusi) ");
        }
        queryStr.append("ORDER BY tipoServizio.cdTipoServizio ");
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idClasseEnteConvenz", longFrom(idClasseEnteConvenz));
        query.setParameter("idTariffario", longFrom(idTariffario));
        if (idTipoServizioEsclusi != null && !idTipoServizioEsclusi.isEmpty()) {
            query.setParameter("idTipoServizioEsclusi", longListFrom(idTipoServizioEsclusi));
        }
        List<OrgTipoServizio> list = query.getResultList();
        return list;
    }

    public boolean checkEsistenzaAnnualitaPerAccordo(BigDecimal aaAnnoAccordo, BigDecimal idAccordoEnte) {
        String queryStr = "SELECT accordoEnte FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.idAccordoEnte = :idAccordoEnte "
                + "AND EXISTS (SELECT annualita FROM OrgAaAccordo annualita "
                + "WHERE annualita.orgAccordoEnte = accordoEnte " + "AND annualita.aaAnnoAccordo = :aaAnnoAccordo) ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAccordoEnte", longFrom(idAccordoEnte));
        query.setParameter("aaAnnoAccordo", aaAnnoAccordo);
        List<OrgAccordoEnte> list = query.getResultList();
        return !list.isEmpty();
    }

    public boolean checkEsistenzaServiziErogatiPerAccordo(BigDecimal idServizioErogato, BigDecimal idAccordoEnte,
            BigDecimal idTipoServizio, BigDecimal idSistemaVersante) {
        StringBuilder queryStr = new StringBuilder("SELECT accordoEnte FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.idAccordoEnte = :idAccordoEnte "
                + "AND EXISTS (SELECT servizioErog FROM OrgServizioErog servizioErog "
                + "WHERE servizioErog.orgAccordoEnte = accordoEnte "
                + "AND servizioErog.orgTipoServizio.idTipoServizio = :idTipoServizio ");
        if (idServizioErogato != null) {
            queryStr.append("AND servizioErog.idServizioErogato != :idServizioErogato ");
        }
        if (idSistemaVersante != null) {
            queryStr.append("AND servizioErog.aplSistemaVersante.idSistemaVersante = :idSistemaVersante ");
        }
        queryStr.append(")");
        Query query = getEntityManager().createQuery(queryStr.toString());

        query.setParameter("idAccordoEnte", longFrom(idAccordoEnte));
        query.setParameter("idTipoServizio", longFrom(idTipoServizio));
        if (idServizioErogato != null) {
            query.setParameter("idServizioErogato", longFrom(idServizioErogato));
        }
        if (idSistemaVersante != null) {
            query.setParameter("idSistemaVersante", longFrom(idSistemaVersante));
        }
        List<OrgAccordoEnte> list = query.getResultList();
        return !list.isEmpty();
    }

    public List<OrgTipoServizio> retrieveOrgTipoServizio() {
        Query query = getEntityManager().createQuery("SELECT tipoServizio FROM OrgTipoServizio tipoServizio ");
        List<OrgTipoServizio> list = query.getResultList();
        return list;
    }

    public OrgTariffa retrieveOrgTariffa(long idTariffario, String nmTariffa) {
        Query query = getEntityManager().createQuery("SELECT tariffa FROM OrgTariffa tariffa "
                + "WHERE tariffa.orgTariffario.idTariffario = :idTariffario " + "AND tariffa.nmTariffa = :nmTariffa ");
        query.setParameter("idTariffario", longFrom(idTariffario));
        query.setParameter("nmTariffa", nmTariffa);
        List<OrgTariffa> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * Controlla se esistono dei servizi riferiti ad accordi dell'ente convenzionato
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     *
     * @return true se esistono i servizi
     */
    public boolean checkEsistenzaServiziErogatiPerEnteConvenz(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT enteSiam FROM OrgEnteSiam enteSiam "
                + "WHERE enteSiam.idEnteSiam = :idEnteConvenz " + "AND EXISTS "
                + "(SELECT servizioErog FROM OrgServizioErog servizioErog "
                + "JOIN servizioErog.orgAccordoEnte accordoEnte " + "WHERE accordoEnte.orgEnteSiam = enteSiam) ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgEnteSiam> list = query.getResultList();
        return !list.isEmpty();
    }

    public List<OrgTipoAccordo> retrieveOrgTipoAccordoList(String cdTipoAccordo, String dsTipoAccordo,
            String flPagamento, String isAttivo) {
        StringBuilder queryStr = new StringBuilder("SELECT tipoAccordo FROM OrgTipoAccordo tipoAccordo ");
        String clause = " WHERE ";
        if (StringUtils.isNotBlank(cdTipoAccordo)) {
            queryStr.append(clause).append("UPPER(tipoAccordo.cdTipoAccordo) LIKE :cdTipoAccordo ");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(dsTipoAccordo)) {
            queryStr.append(clause).append("upper(tipoAccordo.dsTipoAccordo) LIKE :dsTipoAccordo ");
            clause = " AND ";
        }
        if (flPagamento != null) {
            queryStr.append(clause).append("tipoAccordo.flPagamento = :flPagamento ");
            clause = " AND ";
        }
        Date dataOdierna = new Date();
        if (isAttivo != null) {
            if (isAttivo.equals("1")) {
                queryStr.append(clause)
                        .append("(tipoAccordo.dtIstituz <= :dataOdierna AND tipoAccordo.dtSoppres >= :dataOdierna) ");
            } else {
                queryStr.append(clause)
                        .append("(tipoAccordo.dtIstituz > :dataOdierna OR tipoAccordo.dtSoppres < :dataOdierna) ");
            }
            clause = " AND ";
        }
        queryStr.append("ORDER BY tipoAccordo.cdTipoAccordo ");
        Query query = getEntityManager().createQuery(queryStr.toString());

        if (StringUtils.isNotBlank(cdTipoAccordo)) {
            query.setParameter("cdTipoAccordo", "%" + cdTipoAccordo.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(dsTipoAccordo)) {
            query.setParameter("dsTipoAccordo", "%" + dsTipoAccordo.toUpperCase() + "%");
        }
        if (flPagamento != null) {
            query.setParameter("flPagamento", flPagamento);
        }
        if (isAttivo != null) {
            query.setParameter("dataOdierna", dataOdierna);
        }
        List<OrgTipoAccordo> list = query.getResultList();
        return list;
    }

    /**
     * Controlla se esistono accordi per il tipo accordo in input
     *
     * @param idTipoAccordo
     *            tipo accordo
     *
     * @return true se esistono accordi
     */
    public boolean checkEsistenzaAccordiPerTipoAccordo(BigDecimal idTipoAccordo) {
        Query query = getEntityManager().createQuery(
                "SELECT 1 FROM OrgTipoAccordo tipoAccordo " + "WHERE tipoAccordo.idTipoAccordo = :idTipoAccordo "
                        + "AND EXISTS (SELECT accordoEnte FROM OrgAccordoEnte accordoEnte "
                        + "WHERE accordoEnte.orgTipoAccordo = tipoAccordo) ");
        query.setParameter("idTipoAccordo", idTipoAccordo.longValue());
        List<OrgTipoAccordo> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Ritorna l'oggetto tipo accordo se esiste
     *
     * @param cdTipoAccordo
     *            codice tipo accordo
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgTipoAccordo getOrgTipoAccordo(String cdTipoAccordo) {
        OrgTipoAccordo tipoAccordo = null;
        if (StringUtils.isNotBlank(cdTipoAccordo)) {
            Query query = getEntityManager().createQuery(
                    "SELECT tipoAccordo FROM OrgTipoAccordo tipoAccordo WHERE tipoAccordo.cdTipoAccordo = :cdTipoAccordo ");
            query.setParameter("cdTipoAccordo", cdTipoAccordo);
            List<OrgTipoAccordo> list = query.getResultList();
            if (!list.isEmpty()) {
                tipoAccordo = list.get(0);
            }
        } else {
            throw new IllegalArgumentException("Parametro cdTipoAccordo nullo");
        }
        return tipoAccordo;
    }

    public List<OrgTariffario> retrieveOrgTariffario(String isValido, String cdTipoAccordo, String nomeTariffario) {
        StringBuilder queryStr = new StringBuilder("SELECT tariffario FROM OrgTariffario tariffario ");
        String clause = " WHERE ";
        Date dataOdierna = new Date();
        if (isValido != null) {
            if (isValido.equals("1")) {
                queryStr.append(clause)
                        .append("tariffario.dtIniVal <= :dataOdierna AND tariffario.dtFineVal >= :dataOdierna ");
            } else {
                queryStr.append(clause)
                        .append("tariffario.dtIniVal > :dataOdierna OR tariffario.dtFineVal < :dataOdierna ");
            }
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdTipoAccordo)) {
            queryStr.append(clause).append("UPPER(tariffario.orgTipoAccordo.cdTipoAccordo) LIKE :cdTipoAccordo ");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(nomeTariffario)) {
            queryStr.append(clause).append("UPPER(tariffario.nmTariffario) LIKE :nomeTariffario ");
            clause = " AND ";
        }
        queryStr.append("ORDER BY tariffario.dtIniVal DESC ");
        Query query = getEntityManager().createQuery(queryStr.toString());

        if (isValido != null) {
            query.setParameter("dataOdierna", dataOdierna);
        }
        if (StringUtils.isNotBlank(cdTipoAccordo)) {
            query.setParameter("cdTipoAccordo", "%" + cdTipoAccordo.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(nomeTariffario)) {
            query.setParameter("nomeTariffario", "%" + nomeTariffario.toUpperCase() + "%");
        }
        List<OrgTariffario> list = query.getResultList();
        return list;
    }

    public List<OrgTipoAccordo> retrieveOrgTipoAccordoAttivi() {
        Query query = getEntityManager().createQuery("SELECT tipoAccordo FROM OrgTipoAccordo tipoAccordo "
                + "WHERE (tipoAccordo.dtIstituz <= :dataOdierna AND tipoAccordo.dtSoppres > :dataOdierna) "
                + "ORDER BY tipoAccordo.cdTipoAccordo ");
        query.setParameter("dataOdierna", new Date());
        List<OrgTipoAccordo> list = query.getResultList();
        return list;
    }

    public List<OrgTariffa> retrieveOrgTariffa(BigDecimal idTariffario) {
        Query query = getEntityManager().createQuery("SELECT tariffa FROM OrgTariffa tariffa "
                + "WHERE tariffa.orgTariffario.idTariffario = :idTariffario ");
        query.setParameter("idTariffario", idTariffario.longValue());
        List<OrgTariffa> list = query.getResultList();
        return list;
    }

    public List<OrgTariffa> retrieveOrgTariffaByTipoServizio(BigDecimal idTipoServizio) {
        Query query = getEntityManager().createQuery("SELECT tariffa FROM OrgTariffa tariffa "
                + "WHERE tariffa.orgTipoServizio.idTipoServizio = :idTipoServizio ");
        query.setParameter("idTipoServizio", idTipoServizio.longValue());
        List<OrgTariffa> list = query.getResultList();
        return list;
    }

    public List<Object[]> retrieveOrgTariffaAccordoByTipoServizio(BigDecimal idTipoServizio) {
        Query query = getEntityManager()
                .createQuery("SELECT enteSiam.nmEnteSiam, accordoEnte.dtDecAccordo, accordoEnte.dtFineValidAccordo, "
                        + "tariffaAccordo.imTariffaAccordo, tipoServizio.cdTipoServizio, accordoEnte.cdRegistroRepertorio, accordoEnte.aaRepertorio, accordoEnte.cdKeyRepertorio, accordoEnte.dtScadAccordo FROM OrgTariffaAccordo tariffaAccordo "
                        + "JOIN tariffaAccordo.orgAccordoEnte accordoEnte "
                        + "JOIN tariffaAccordo.orgTipoServizio tipoServizio " + "JOIN accordoEnte.orgEnteSiam enteSiam "
                        + "WHERE tipoServizio.idTipoServizio = :idTipoServizio ");
        query.setParameter("idTipoServizio", idTipoServizio.longValue());
        List<Object[]> list = query.getResultList();
        return list;
    }

    public List<Object[]> retrieveOrgTariffaAnnualitaByTipoServizio(BigDecimal idTipoServizio) {
        Query query = getEntityManager()
                .createQuery("SELECT enteSiam.nmEnteSiam, accordoEnte.dtDecAccordo, accordoEnte.dtFineValidAccordo, "
                        + "aaAccordo.aaAnnoAccordo, tariffaAaAccordo.imTariffaAaAccordo, tipoServizio.cdTipoServizio, accordoEnte.cdRegistroRepertorio, accordoEnte.aaRepertorio, accordoEnte.cdKeyRepertorio, accordoEnte.dtScadAccordo "
                        + "FROM OrgTariffaAaAccordo tariffaAaAccordo " + "JOIN tariffaAaAccordo.orgAaAccordo aaAccordo "
                        + "JOIN aaAccordo.orgAccordoEnte accordoEnte "
                        + "JOIN tariffaAaAccordo.orgTipoServizio tipoServizio "
                        + "JOIN accordoEnte.orgEnteSiam enteSiam "
                        + "WHERE tipoServizio.idTipoServizio = :idTipoServizio ");
        query.setParameter("idTipoServizio", idTipoServizio.longValue());
        List<Object[]> list = query.getResultList();
        return list;
    }

    public List<OrgScaglioneTariffa> retrieveOrgScaglioneTariffa(BigDecimal idTariffa) {
        Query query = getEntityManager()
                .createQuery("SELECT scaglioneTariffa FROM OrgScaglioneTariffa scaglioneTariffa "
                        + "WHERE scaglioneTariffa.orgTariffa.idTariffa = :idTariffa ");
        query.setParameter("idTariffa", idTariffa.longValue());
        List<OrgScaglioneTariffa> list = query.getResultList();
        return list;
    }

    /**
     * Controlla se esistono accordi per il tariffario
     *
     * @param idTariffario
     *            id tariffario
     *
     * @return true se esistono accordi
     */
    public boolean checkEsistenzaAccordiPerTariffario(BigDecimal idTariffario) {
        Query query = getEntityManager().createQuery(
                "SELECT 1 FROM OrgTariffario tariffario " + "WHERE tariffario.idTariffario = :idTariffario "
                        + "AND EXISTS (SELECT accordoEnte FROM OrgAccordoEnte accordoEnte "
                        + "WHERE accordoEnte.orgTariffario = tariffario) ");
        query.setParameter("idTariffario", idTariffario.longValue());
        List<OrgTariffario> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Controlla che la denominazione del tariffario non sia già presente nel sistema
     *
     * @param nmTariffario
     *            nome tariffario
     * @param idTipoAccordo
     *            id tipo accordo
     * @param idTariffarioDaEscludere
     *            id tariffario da escludere
     *
     * @return true se corretto
     */
    public boolean checkEsistenzaTariffario(String nmTariffario, BigDecimal idTipoAccordo,
            BigDecimal idTariffarioDaEscludere) {
        String queryStr = "SELECT tariffario FROM OrgTariffario tariffario "
                + "WHERE tariffario.orgTipoAccordo.idTipoAccordo = :idTipoAccordo "
                + "AND tariffario.nmTariffario = :nmTariffario ";
        if (idTariffarioDaEscludere != null) {
            queryStr += "AND tariffario.idTariffario != :idTariffarioDaEscludere ";
        }

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("nmTariffario", nmTariffario);
        query.setParameter("idTipoAccordo", longFrom(idTipoAccordo));

        if (idTariffarioDaEscludere != null) {
            query.setParameter("idTariffarioDaEscludere", longFrom(idTariffarioDaEscludere));
        }

        List<OrgTariffario> list = query.getResultList();
        return !list.isEmpty();
    }

    public List<OrgClasseEnteConvenz> retrieveOrgClasseEnteConvenz(String cdClasseEnteConvenz,
            String dsClasseEnteConvenz) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT classeEnteConvenz FROM OrgClasseEnteConvenz classeEnteConvenz ");
        String clause = " WHERE ";
        if (StringUtils.isNotBlank(cdClasseEnteConvenz)) {
            queryStr.append(clause).append("UPPER(classeEnteConvenz.cdClasseEnteConvenz) LIKE :cdClasseEnteConvenz ");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(dsClasseEnteConvenz)) {
            queryStr.append(clause).append("upper(classeEnteConvenz.dsClasseEnteConvenz) LIKE :dsClasseEnteConvenz ");
            clause = " AND ";
        }
        queryStr.append("ORDER BY classeEnteConvenz.cdClasseEnteConvenz ");
        Query query = getEntityManager().createQuery(queryStr.toString());

        if (StringUtils.isNotBlank(cdClasseEnteConvenz)) {
            query.setParameter("cdClasseEnteConvenz", "%" + cdClasseEnteConvenz.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(dsClasseEnteConvenz)) {
            query.setParameter("dsClasseEnteConvenz", "%" + dsClasseEnteConvenz.toUpperCase() + "%");
        }
        List<OrgClasseEnteConvenz> list = query.getResultList();
        return list;
    }

    /**
     * Controlla se esistono dei servizi erogati di un certo tipo servizio
     *
     * @param idTipoServizio
     *            id tipo servizio
     *
     * @return true se esistono i servizi
     */
    public boolean checkEsistenzaServiziErogatiPerTipoServizio(BigDecimal idTipoServizio) {
        Query query = getEntityManager().createQuery("SELECT tipoServizio FROM OrgTipoServizio tipoServizio "
                + "WHERE tipoServizio.idTipoServizio = :idTipoServizio " + "AND EXISTS "
                + "(SELECT servizioErog FROM OrgServizioErog servizioErog "
                + "WHERE servizioErog.orgTipoServizio = tipoServizio) ");
        query.setParameter("idTipoServizio", longFrom(idTipoServizio));
        List<OrgTipoServizio> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Controlla se esistono dei servizi erogati con una certa tariffa
     *
     * @param idTariffa
     *            id tariffa
     *
     * @return true se esistono i servizi
     */
    public boolean checkEsistenzaServiziErogatiPerTariffa(BigDecimal idTariffa) {
        Query query = getEntityManager()
                .createQuery("SELECT tariffa FROM OrgTariffa tariffa " + "WHERE tariffa.idTariffa = :idTariffa "
                        + "AND EXISTS " + "(SELECT servizioErog FROM OrgServizioErog servizioErog "
                        + "WHERE servizioErog.orgTariffa = tariffa) ");
        query.setParameter("idTariffa", longFrom(idTariffa));
        List<OrgTipoServizio> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Ritorna l'oggetto tipo servizio se esiste
     *
     * @param cdTipoServizio
     *            code tipo servizio
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgTipoServizio getOrgTipoServizio(String cdTipoServizio) {
        OrgTipoServizio tipoServizio = null;
        if (StringUtils.isNotBlank(cdTipoServizio)) {
            Query query = getEntityManager().createQuery(
                    "SELECT tipoServizio FROM OrgTipoServizio tipoServizio WHERE tiposervizio.cdTipoServizio = :cdTipoServizio ");
            query.setParameter("cdTipoServizio", cdTipoServizio);
            List<OrgTipoServizio> list = query.getResultList();
            if (!list.isEmpty()) {
                tipoServizio = list.get(0);
            }
        } else {
            throw new IllegalArgumentException("Parametro cdTipoServizio nullo");
        }
        return tipoServizio;
    }

    public List<OrgTipoServizio> retrieveOrgTipoServizioList(String cdTipoServizio, String tiClasseTipoServizio,
            String tipoFatturazione) {
        StringBuilder queryStr = new StringBuilder("SELECT tipoServizio FROM OrgTipoServizio tipoServizio ");
        String clause = " WHERE ";
        if (StringUtils.isNotBlank(cdTipoServizio)) {
            queryStr.append(clause).append("UPPER(tipoServizio.cdTipoServizio) LIKE :cdTipoServizio ");
            clause = " AND ";
        }
        if (tiClasseTipoServizio != null) {
            queryStr.append(clause).append("tipoServizio.tiClasseTipoServizio = :tiClasseTipoServizio ");
            clause = " AND ";
        }
        if (tipoFatturazione != null) {
            queryStr.append(clause).append("tipoServizio.tipoFatturazione = :tipoFatturazione ");
            clause = " AND ";
        }

        queryStr.append("ORDER BY tipoServizio.cdTipoServizio ");
        Query query = getEntityManager().createQuery(queryStr.toString());

        if (StringUtils.isNotBlank(cdTipoServizio)) {
            query.setParameter("cdTipoServizio", "%" + cdTipoServizio.toUpperCase() + "%");
        }
        if (tiClasseTipoServizio != null) {
            query.setParameter("tiClasseTipoServizio", tiClasseTipoServizio);
        }
        if (tipoFatturazione != null) {
            query.setParameter("tipoFatturazione", tipoFatturazione);
        }
        List<OrgTipoServizio> list = query.getResultList();
        return list;
    }

    public boolean isUtenteArchivistaInEnteConvenz(BigDecimal idEnteConvenz, BigDecimal idUserIam) {
        Query query = getEntityManager().createQuery("SELECT enteArkRif FROM OrgEnteArkRif enteArkRif "
                + "WHERE enteArkRif.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "AND enteArkRif.usrUser.idUserIam = :idUserIam ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("idUserIam", longFrom(idUserIam));
        List<OrgEnteArkRif> list = query.getResultList();
        return !list.isEmpty();
    }

    public boolean isReferenteEnteInEnteConvenz(BigDecimal idEnteConvenz, BigDecimal idUserIam) {
        Query query = getEntityManager().createQuery("SELECT enteUserRif FROM OrgEnteUserRif enteUserRif "
                + "WHERE enteUserRif.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "AND enteUserRif.usrUser.idUserIam = :idUserIam ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("idUserIam", longFrom(idUserIam));
        List<OrgEnteUserRif> list = query.getResultList();
        return !list.isEmpty();
    }

    public boolean isReferenteEnteCessato(BigDecimal idUserIam) {
        Query query = getEntityManager().createQuery("SELECT user FROM UsrStatoUser stato JOIN stato.usrUser user "
                + "WHERE stato.idStatoUser = user.idStatoUserCor AND user.idUserIam = :idUserIam AND stato.tiStatoUser = 'CESSATO'");
        query.setParameter("idUserIam", longFrom(idUserIam));
        List<UsrUser> list = query.getResultList();
        return !list.isEmpty();
    }

    /**
     * Ritorna l'oggetto tariffa se esiste
     *
     * @param idTariffario
     *            id tariffario
     * @param nmTariffa
     *            nome tariffa
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgTariffa getOrgTariffa(BigDecimal idTariffario, String nmTariffa) {
        OrgTariffa tariffa = null;
        if (idTariffario != null && StringUtils.isNotBlank(nmTariffa)) {
            Query query = getEntityManager().createQuery(
                    "SELECT tariffa FROM OrgTariffa tariffa WHERE tariffa.orgTariffario.idTariffario = :idTariffario AND tariffa.nmTariffa = :nmTariffa ");
            query.setParameter("idTariffario", idTariffario.longValue());
            query.setParameter("nmTariffa", nmTariffa);
            List<OrgTariffa> list = query.getResultList();
            if (!list.isEmpty()) {
                tariffa = list.get(0);
            }
        } else {
            throw new IllegalArgumentException("Parametro idTariffario o nmTariffa nullo");
        }
        return tariffa;
    }

    /**
     * Ritorna l'oggetto tariffa se esiste
     *
     * @param idTariffario
     *            id tariffario
     * @param idTipoServizio
     *            id tipo servizio
     * @param idClasseEnteConvenz
     *            id clsse ente convenzionato
     * @param idTariffa
     *            id tariffa
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgTariffa getOrgTariffa(BigDecimal idTariffario, BigDecimal idTipoServizio, BigDecimal idClasseEnteConvenz,
            BigDecimal idTariffa) {
        OrgTariffa tariffa = null;
        if (idTariffario != null && idTipoServizio != null) {
            String queryStr = "SELECT tariffa FROM OrgTariffa tariffa "
                    + "WHERE tariffa.orgTariffario.idTariffario = :idTariffario "
                    + "AND tariffa.orgTipoServizio.idTipoServizio = :idTipoServizio ";
            if (idClasseEnteConvenz != null) {
                queryStr = queryStr + "AND tariffa.orgClasseEnteConvenz.idClasseEnteConvenz = :idClasseEnteConvenz ";
            } else {
                queryStr = queryStr + "AND tariffa.orgClasseEnteConvenz IS NULL ";
            }
            if (idTariffa != null) {
                queryStr = queryStr + "AND tariffa.idTariffa != :idTariffa ";
            }
            Query query = getEntityManager().createQuery(queryStr);
            query.setParameter("idTariffario", idTariffario.longValue());
            query.setParameter("idTipoServizio", idTipoServizio.longValue());
            if (idClasseEnteConvenz != null) {
                query.setParameter("idClasseEnteConvenz", idClasseEnteConvenz.longValue());
            }
            if (idTariffa != null) {
                query.setParameter("idTariffa", idTariffa.longValue());
            }
            List<OrgTariffa> list = query.getResultList();
            if (!list.isEmpty()) {
                tariffa = list.get(0);
            }
        } else {
            throw new IllegalArgumentException("Parametro idTariffario o idTipoServizio nullo");
        }
        return tariffa;
    }

    public List<OrgCdIva> retrieveOrgCdIvaList() {
        Query query = getEntityManager().createQuery("SELECT cdIva FROM OrgCdIva cdIva ");
        List<OrgCdIva> list = query.getResultList();
        return list;
    }

    public List<OrgVRicFatture> retrieveOrgVRicFattureList(String nmEnteConvenz, BigDecimal idTipoAccordo,
            BigDecimal idTipoServizio, BigDecimal aaFatturaDa, BigDecimal aaFatturaA, BigDecimal pgFatturaEnteDa,
            BigDecimal pgFatturaEnteA, String cdFattura, String cdRegistroEmisFattura, BigDecimal aaEmissFattura,
            String cdEmisFattura, BigDecimal pgFatturaEmis, Date dtEmisFatturaDa, Date dtEmisFatturaA,
            Set<String> tiStatoFatturaEnte) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT new it.eng.saceriam.viewEntity.OrgVRicFatture(fatture.cdFattura, "
                        + "fatture.aaEmissFattura, fatture.aaFattura, fatture.cdEmisFattura, fatture.cdRegistroEmisFattura, "
                        + "fatture.cdTipoAccordo, fatture.dtEmisFattura, fatture.dtRegStatoFatturaEnte, fatture.idAccordoEnte, "
                        + "fatture.idEnteConvenz, fatture.id.idFatturaEnte, fatture.idStatoFatturaEnte, fatture.idTipoAccordo, fatture.imTotFattura, "
                        + "fatture.nmEnteConvenz, fatture.pgFattura, fatture.tiStatoFatturaEnte, fatture.imTotPagato) FROM OrgVRicFatture fatture ");
        String clause = " WHERE ";
        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            queryStr.append(clause).append("UPPER(fatture.nmEnteConvenz) LIKE :nmEnteConvenz");
            clause = " AND ";
        }
        if (idTipoAccordo != null) {
            queryStr.append(clause).append("fatture.idTipoAccordo = :idTipoAccordo");
            clause = " AND ";
        }
        if (idTipoServizio != null) {
            queryStr.append(clause).append("fatture.idTipoServizio = :idTipoServizio");
            clause = " AND ";
        }

        if (aaFatturaDa != null && aaFatturaA == null) {
            queryStr.append(clause).append("fatture.aaFattura >= :aaFatturaDa");
            clause = " AND ";
        } else if (aaFatturaDa == null && aaFatturaA != null) {
            queryStr.append(clause).append("fatture.aaFattura <= :aaFatturaA");
            clause = " AND ";
        } else if (aaFatturaDa != null && aaFatturaA != null) {
            queryStr.append(clause).append("(fatture.aaFattura >= :aaFatturaDa AND fatture.aaFattura <= :aaFatturaA)");
            clause = " AND ";
        }

        if (pgFatturaEnteDa != null && pgFatturaEnteA == null) {
            queryStr.append(clause).append("fatture.pgFattura>= :pgFatturaEnteDa");
            clause = " AND ";
        } else if (pgFatturaEnteDa == null && pgFatturaEnteA != null) {
            queryStr.append(clause).append("fatture.pgFattura<= :pgFatturaEnteA");
            clause = " AND ";
        } else if (pgFatturaEnteDa != null && pgFatturaEnteA != null) {
            queryStr.append(clause)
                    .append("(fatture.pgFattura>= :pgFatturaEnteDa AND fatture.pgFattura<= :pgFatturaEnteA)");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdFattura)) {
            queryStr.append(clause).append("fatture.cdFattura = :cdFattura");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdRegistroEmisFattura)) {
            queryStr.append(clause).append("UPPER(fatture.cdRegistroEmisFattura) LIKE :cdRegistroEmisFattura");
            clause = " AND ";
        }
        if (aaEmissFattura != null) {
            queryStr.append(clause).append("fatture.aaEmissFattura = :aaEmissFattura");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdEmisFattura)) {
            queryStr.append(clause).append("UPPER(fatture.cdEmisFattura) LIKE :cdEmisFattura");
            clause = " AND ";
        }
        if (pgFatturaEmis != null) {
            queryStr.append(clause).append("fatture.pgFattura = :pgFatturaEmis");
            clause = " AND ";
        }

        if (dtEmisFatturaDa != null && dtEmisFatturaA == null) {
            queryStr.append(clause).append("fatture.dtEmisFattura >= :dtEmisFatturaDa");
            clause = " AND ";
        } else if (dtEmisFatturaDa == null && dtEmisFatturaA != null) {
            queryStr.append(clause).append("(fatture.dtEmisFattura <= :dtEmisFatturaA)");
            clause = " AND ";
        } else if (dtEmisFatturaDa != null && dtEmisFatturaA != null) {
            queryStr.append(clause).append("(fatture.dtEmisFattura BETWEEN :dtEmisFatturaDa AND :dtEmisFatturaA)");
            clause = " AND ";
        }

        if (!tiStatoFatturaEnte.isEmpty()) {
            queryStr.append(clause).append("fatture.tiStatoFatturaEnte IN (:tiStatoFatturaEnte)");
            clause = " AND ";
        }

        queryStr.append(" ORDER BY fatture.nmEnteConvenz, fatture.id.idFatturaEnte");
        Query query = getEntityManager().createQuery(queryStr.toString());

        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            query.setParameter("nmEnteConvenz", "%" + nmEnteConvenz.toUpperCase() + "%");
        }
        if (idTipoAccordo != null) {
            query.setParameter("idTipoAccordo", idTipoAccordo);
        }
        if (idTipoServizio != null) {
            query.setParameter("idTipoServizio", idTipoServizio);
        }
        if (aaFatturaDa != null) {
            query.setParameter("aaFatturaDa", aaFatturaDa);
        }
        if (aaFatturaA != null) {
            query.setParameter("aaFatturaA", aaFatturaA);
        }
        if (pgFatturaEnteDa != null) {
            query.setParameter("pgFatturaEnteDa", pgFatturaEnteDa);
        }
        if (pgFatturaEnteA != null) {
            query.setParameter("pgFatturaEnteA", pgFatturaEnteA);
        }
        if (StringUtils.isNotBlank(cdFattura)) {
            query.setParameter("cdFattura", cdFattura);
        }
        if (StringUtils.isNotBlank(cdRegistroEmisFattura)) {
            query.setParameter("cdRegistroEmisFattura", "%" + cdRegistroEmisFattura.toUpperCase() + "%");
        }
        if (aaEmissFattura != null) {
            query.setParameter("aaEmissFattura", aaEmissFattura);
        }
        if (StringUtils.isNotBlank(cdEmisFattura)) {
            query.setParameter("cdEmisFattura", "%" + cdEmisFattura.toUpperCase() + "%");
        }
        if (pgFatturaEmis != null) {
            query.setParameter("pgFatturaEmis", pgFatturaEmis);
        }
        if (dtEmisFatturaDa != null) {
            query.setParameter("dtEmisFatturaDa", dtEmisFatturaDa);
        }
        if (dtEmisFatturaA != null) {
            query.setParameter("dtEmisFatturaA", dtEmisFatturaA);
        }
        if (!tiStatoFatturaEnte.isEmpty()) {
            query.setParameter("tiStatoFatturaEnte", tiStatoFatturaEnte);
        }
        List<OrgVRicFatture> list = query.getResultList();
        return list;
    }

    public List<OrgVRicFatture> retrieveOrgVRicFattureList2(String nmEnteConvenz, BigDecimal idTipoAccordo,
            BigDecimal idTipoServizio, BigDecimal aaFatturaDa, BigDecimal aaFatturaA, BigDecimal pgFatturaEnteDa,
            BigDecimal pgFatturaEnteA, String cdFattura, String cdRegistroEmisFattura, BigDecimal aaEmissFattura,
            String cdEmisFattura, BigDecimal pgFatturaEmis, Date dtEmisFatturaDa, Date dtEmisFatturaA,
            Set<String> tiStatoFatturaEnte) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT new it.eng.saceriam.viewEntity.OrgVRicFatture(fatture.idEnteConvenz, "
                        + "fatture.nmEnteConvenz, fatture.cdFattura, fatture.cdEmisFattura, fatture.dtEmisFattura, "
                        + "fatture.tiStatoFatturaEnte, fatture.dtRegStatoFatturaEnte, "
                        + " fatture.id.idFatturaEnte, fatture.imTotFattura, "
                        + " fatture.imTotPagato) FROM OrgVRicFatture fatture ");
        String clause = " WHERE ";
        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            queryStr.append(clause).append("UPPER(fatture.nmEnteConvenz) LIKE :nmEnteConvenz");
            clause = " AND ";
        }
        if (idTipoAccordo != null) {
            queryStr.append(clause).append("fatture.idTipoAccordo = :idTipoAccordo");
            clause = " AND ";
        }
        if (idTipoServizio != null) {
            queryStr.append(clause).append("fatture.idTipoServizio = :idTipoServizio");
            clause = " AND ";
        }

        if (aaFatturaDa != null && aaFatturaA == null) {
            queryStr.append(clause).append("fatture.aaFattura >= :aaFatturaDa");
            clause = " AND ";
        } else if (aaFatturaDa == null && aaFatturaA != null) {
            queryStr.append(clause).append("fatture.aaFattura <= :aaFatturaA");
            clause = " AND ";
        } else if (aaFatturaDa != null && aaFatturaA != null) {
            queryStr.append(clause).append("(fatture.aaFattura >= :aaFatturaDa AND fatture.aaFattura <= :aaFatturaA)");
            clause = " AND ";
        }

        if (pgFatturaEnteDa != null && pgFatturaEnteA == null) {
            queryStr.append(clause).append("fatture.pgFattura>= :pgFatturaEnteDa");
            clause = " AND ";
        } else if (pgFatturaEnteDa == null && pgFatturaEnteA != null) {
            queryStr.append(clause).append("fatture.pgFattura<= :pgFatturaEnteA");
            clause = " AND ";
        } else if (pgFatturaEnteDa != null && pgFatturaEnteA != null) {
            queryStr.append(clause)
                    .append("(fatture.pgFattura>= :pgFatturaEnteDa AND fatture.pgFattura<= :pgFatturaEnteA)");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdFattura)) {
            queryStr.append(clause).append("fatture.cdFattura = :cdFattura");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdRegistroEmisFattura)) {
            queryStr.append(clause).append("UPPER(fatture.cdRegistroEmisFattura) LIKE :cdRegistroEmisFattura");
            clause = " AND ";
        }
        if (aaEmissFattura != null) {
            queryStr.append(clause).append("fatture.aaEmissFattura = :aaEmissFattura");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdEmisFattura)) {
            queryStr.append(clause).append("UPPER(fatture.cdEmisFattura) LIKE :cdEmisFattura");
            clause = " AND ";
        }
        if (pgFatturaEmis != null) {
            queryStr.append(clause).append("fatture.pgFattura = :pgFatturaEmis");
            clause = " AND ";
        }

        if (dtEmisFatturaDa != null && dtEmisFatturaA == null) {
            queryStr.append(clause).append("fatture.dtEmisFattura >= :dtEmisFatturaDa");
            clause = " AND ";
        } else if (dtEmisFatturaDa == null && dtEmisFatturaA != null) {
            queryStr.append(clause).append("(fatture.dtEmisFattura <= :dtEmisFatturaA)");
            clause = " AND ";
        } else if (dtEmisFatturaDa != null && dtEmisFatturaA != null) {
            queryStr.append(clause).append("(fatture.dtEmisFattura BETWEEN :dtEmisFatturaDa AND :dtEmisFatturaA)");
            clause = " AND ";
        }

        if (!tiStatoFatturaEnte.isEmpty()) {
            queryStr.append(clause).append("fatture.tiStatoFatturaEnte IN (:tiStatoFatturaEnte)");
            clause = " AND ";
        }

        queryStr.append(" ORDER BY fatture.nmEnteConvenz, fatture.id.idFatturaEnte");
        Query query = getEntityManager().createQuery(queryStr.toString());

        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            query.setParameter("nmEnteConvenz", "%" + nmEnteConvenz.toUpperCase() + "%");
        }
        if (idTipoAccordo != null) {
            query.setParameter("idTipoAccordo", idTipoAccordo);
        }
        if (idTipoServizio != null) {
            query.setParameter("idTipoServizio", idTipoServizio);
        }
        if (aaFatturaDa != null) {
            query.setParameter("aaFatturaDa", aaFatturaDa);
        }
        if (aaFatturaA != null) {
            query.setParameter("aaFatturaA", aaFatturaA);
        }
        if (pgFatturaEnteDa != null) {
            query.setParameter("pgFatturaEnteDa", pgFatturaEnteDa);
        }
        if (pgFatturaEnteA != null) {
            query.setParameter("pgFatturaEnteA", pgFatturaEnteA);
        }
        if (StringUtils.isNotBlank(cdFattura)) {
            query.setParameter("cdFattura", cdFattura);
        }
        if (StringUtils.isNotBlank(cdRegistroEmisFattura)) {
            query.setParameter("cdRegistroEmisFattura", "%" + cdRegistroEmisFattura.toUpperCase() + "%");
        }
        if (aaEmissFattura != null) {
            query.setParameter("aaEmissFattura", aaEmissFattura);
        }
        if (StringUtils.isNotBlank(cdEmisFattura)) {
            query.setParameter("cdEmisFattura", "%" + cdEmisFattura.toUpperCase() + "%");
        }
        if (pgFatturaEmis != null) {
            query.setParameter("pgFatturaEmis", pgFatturaEmis);
        }
        if (dtEmisFatturaDa != null) {
            query.setParameter("dtEmisFatturaDa", dtEmisFatturaDa);
        }
        if (dtEmisFatturaA != null) {
            query.setParameter("dtEmisFatturaA", dtEmisFatturaA);
        }
        if (!tiStatoFatturaEnte.isEmpty()) {
            query.setParameter("tiStatoFatturaEnte", tiStatoFatturaEnte);
        }
        List<OrgVRicFatture> list = query.getResultList();
        return list;
    }

    public List<OrgVRicFatture> retrieveOrgVRicFattureAccordi(BigDecimal idAccordoEnte) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT new it.eng.saceriam.viewEntity.OrgVRicFatture(fatture.idEnteConvenz, "
                        + "fatture.nmEnteConvenz, fatture.cdFattura, fatture.cdEmisFattura, fatture.dtEmisFattura, "
                        + "fatture.tiStatoFatturaEnte, fatture.dtRegStatoFatturaEnte, "
                        + " fatture.id.idFatturaEnte, fatture.imTotFattura, "
                        + " fatture.imTotPagato) FROM OrgVRicFatture fatture ");
        String clause = " WHERE ";
        if (idAccordoEnte != null) {
            queryStr.append(clause).append("fatture.idAccordoEnte = :idAccordoEnte");
        }

        queryStr.append(" ORDER BY fatture.dtRegStatoFatturaEnte");
        Query query = getEntityManager().createQuery(queryStr.toString());

        if (idAccordoEnte != null) {
            query.setParameter("idAccordoEnte", idAccordoEnte);
        }

        return (List<OrgVRicFatture>) query.getResultList();

    }

    public List<OrgVRicFatturePerAccordo> retrieveOrgVRicFatturePerAccordo(BigDecimal idAccordoEnte) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT new it.eng.saceriam.viewEntity.OrgVRicFatturePerAccordo (fatture.idEnteConvenz, "
                        + "fatture.nmEnteConvenz, fatture.cdFattura, fatture.cdEmisFattura, fatture.dtEmisFattura, "
                        + "fatture.tiStatoFatturaEnte, fatture.dtRegStatoFatturaEnte, "
                        + " fatture.idFatturaEnte, fatture.imTotFattura, "
                        + " fatture.imTotPagato) FROM OrgVRicFatturePerAccordo fatture ");
        String clause = " WHERE ";
        if (idAccordoEnte != null) {
            queryStr.append(clause).append("fatture.idAccordoEnte = :idAccordoEnte");
        }

        queryStr.append(" ORDER BY fatture.dtRegStatoFatturaEnte");
        Query query = getEntityManager().createQuery(queryStr.toString());

        if (idAccordoEnte != null) {
            query.setParameter("idAccordoEnte", idAccordoEnte);
        }

        return (List<OrgVRicFatturePerAccordo>) query.getResultList();

    }

    public List<OrgStatoFatturaEnte> getOrgStatoFatturaEnte(BigDecimal idFatturaEnte, String tiStatoFatturaEnte) {
        if (idFatturaEnte != null && tiStatoFatturaEnte != null) {
            Query q = getEntityManager()
                    .createQuery("SELECT statoFatturaEnte FROM OrgStatoFatturaEnte statoFatturaEnte "
                            + "WHERE statoFatturaEnte.orgFatturaEnte.idFatturaEnte = :idFatturaEnte "
                            + "AND statoFatturaEnte.tiStatoFatturaEnte = :tiStatoFatturaEnte "
                            + "ORDER BY statoFatturaEnte.dtRegStatoFatturaEnte DESC ");
            q.setParameter("idFatturaEnte", idFatturaEnte.longValue());
            q.setParameter("tiStatoFatturaEnte", tiStatoFatturaEnte);
            return (List<OrgStatoFatturaEnte>) q.getResultList();
        }
        return null;
    }

    /**
     * Ritorna la lista dei servizi fatturati di una fattura
     *
     * @param idFatturaEnte
     *            id fattura/ente
     *
     * @return la lista di entity se esistono, oppure <code>null</code>
     */
    public List<ServizioFatturatoBean> getServiziFatturati(BigDecimal idFatturaEnte) {
        List<ServizioFatturatoBean> servizioFatturatoBeanList = null;
        if (idFatturaEnte != null) {
            Query query = getEntityManager().createQuery(
                    "SELECT new it.eng.saceriam.amministrazioneEntiConvenzionati.dto.ServizioFatturatoBean ("
                            + "fatturaEnte.idFatturaEnte, servizioErog.idServizioErogato, servizioErog.nmServizioErogato, servizioFattura.aaServizioFattura, "
                            + "tipoServizio.tipoFatturazione, tariffa.nmTariffa, servizioErog.imValoreTariffa, tipoServizio.cdTipoServizio, sistemaVersante.nmSistemaVersante, "
                            + "servizioErog.flPagamento, servizioErog.dtErog, servizioFattura.qtScaglioneServizioFattura, servizioFattura.imServizioFattura, tariffa.tipoTariffa) "
                            + "FROM OrgServizioFattura servizioFattura JOIN servizioFattura.orgFatturaEnte fatturaEnte "
                            + "JOIN servizioFattura.orgServizioErog servizioErog JOIN servizioErog.orgTipoServizio tipoServizio "
                            + "LEFT JOIN servizioErog.aplSistemaVersante sistemaVersante JOIN servizioErog.orgTariffa tariffa "
                            + "WHERE fatturaEnte.idFatturaEnte = :idFatturaEnte " + "ORDER BY servizioErog.dtErog");
            query.setParameter("idFatturaEnte", idFatturaEnte.longValue());
            servizioFatturatoBeanList = query.getResultList();
        } else {
            throw new IllegalArgumentException("Parametro idFatturaEnte nullo");
        }
        return servizioFatturatoBeanList;
    }

    /**
     * Ritorna un servizio fatturato di una fattura
     *
     * @param idFatturaEnte
     *            id fattura/ente
     * @param idServizioErogato
     *            id servizio erogato
     * @param aaServizioFattura
     *            anno servizio fattura
     *
     * @return la lista di entity se esistono, oppure <code>null</code>
     */
    public OrgServizioFattura getOrgServizioFattura(BigDecimal idFatturaEnte, BigDecimal idServizioErogato,
            BigDecimal aaServizioFattura) {
        List<OrgServizioFattura> servizioFatturatoList = null;
        if (idFatturaEnte != null && idServizioErogato != null && aaServizioFattura != null) {
            Query query = getEntityManager()
                    .createQuery("SELECT servizioFattura FROM OrgServizioFattura servizioFattura "
                            + "WHERE servizioFattura.orgFatturaEnte.idFatturaEnte = :idFatturaEnte "
                            + "AND servizioFattura.orgServizioErog.idServizioErogato = :idServizioErogato "
                            + "AND servizioFattura.aaServizioFattura = :aaServizioFattura ");
            query.setParameter("idFatturaEnte", idFatturaEnte.longValue());
            query.setParameter("idServizioErogato", idServizioErogato.longValue());
            query.setParameter("aaServizioFattura", aaServizioFattura);
            servizioFatturatoList = query.getResultList();
            if (!servizioFatturatoList.isEmpty()) {
                return servizioFatturatoList.get(0);
            }
        } else {
            throw new IllegalArgumentException("Parametro idFatturaEnte o idServizioErogato nullo");
        }
        return null;
    }

    public List<UsrUser> retrieveUtentiArchivisti() {
        String queryStr = "SELECT DISTINCT(enteArkRif.usrUser) FROM OrgEnteArkRif enteArkRif "
                + "ORDER BY enteArkRif.usrUser.nmUserid ";
        Query query = getEntityManager().createQuery(queryStr);
        return (List<UsrUser>) query.getResultList();
    }

    public List<UsrUser> retrieveUtentiArchivistiDaVista() {
        String queryStr = "SELECT DISTINCT(enteArkRif.idUserIamArk) FROM AplVRicSistemaVersante enteArkRif";
        Query query = getEntityManager().createQuery(queryStr);
        List<BigDecimal> listaUtenti = (List<BigDecimal>) query.getResultList();
        String queryStr2 = "SELECT user FROM UsrUser user WHERE user.idUserIam IN (:listaUtenti) ORDER BY user.nmUserid";
        Query query2 = getEntityManager().createQuery(queryStr2);
        query2.setParameter("listaUtenti", longListFrom(listaUtenti));
        return query2.getResultList();

    }

    /**
     * Ritorna l'oggetto ente convenzionato storicizzato se esiste
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param dtIniVal
     *            data inizio validita
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgStoEnteConvenz getOrgStoEnteConvenz(BigDecimal idEnteConvenz, Date dtIniVal) {
        OrgStoEnteConvenz sto = null;
        if (idEnteConvenz != null && dtIniVal != null) {
            Query query = getEntityManager().createQuery("SELECT sto FROM OrgStoEnteConvenz sto "
                    + "WHERE sto.orgEnteSiam.idEnteSiam = :idEnteConvenz " + "AND sto.dtIniVal = :dtIniVal ");
            query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
            query.setParameter("dtIniVal", dtIniVal);
            List<OrgStoEnteConvenz> list = query.getResultList();
            if (!list.isEmpty()) {
                sto = list.get(0);
            }
        } else {
            throw new IllegalArgumentException("Parametro idEnteConvenz e/o dtIniVal nulli");
        }
        return sto;
    }

    public List<OrgVCreaServErogByAcc> getServiziErogatiSuAccordo(long idAccordoEnte) {
        Query query = getEntityManager()
                .createQuery("SELECT creaServErogByAcc FROM OrgVCreaServErogByAcc creaServErogByAcc "
                        + "WHERE creaServErogByAcc.id.idAccordoEnte = :idAccordoEnte ");
        query.setParameter("idAccordoEnte", bigDecimalFrom(idAccordoEnte));
        return (List<OrgVCreaServErogByAcc>) query.getResultList();
    }

    public List<OrgVRicEnteConvenz> retrieveEntiConvenzAbilitati(BigDecimal idUserIamCor) {
        Query query = getEntityManager().createQuery("SELECT ricEnteConvenz FROM OrgVRicEnteConvenz ricEnteConvenz "
                + "WHERE ricEnteConvenz.idUserIamCor = :idUserIamCor "
                + "AND (ricEnteConvenz.dtDecAccordo <= :dataOdierna AND ricEnteConvenz.dtScadAccordo >= :dataOdierna) "
                + "AND ricEnteConvenz.tiEnteConvenz IN ('CONSERVATORE', 'AMMINISTRATORE') "
                + "ORDER BY ricEnteConvenz.nmEnteConvenz ");
        query.setParameter("idUserIamCor", idUserIamCor);
        query.setParameter("dataOdierna", new Date());
        return (List<OrgVRicEnteConvenz>) query.getResultList();
    }

    @Deprecated
    public List<OrgVRicEnteConvenz> retrieveEntiConvenzAbilitatiSoloAmm(BigDecimal idUserIamCor) {
        Query query = getEntityManager().createQuery("SELECT ricEnteConvenz FROM OrgVRicEnteConvenz ricEnteConvenz "
                + "WHERE ricEnteConvenz.idUserIamCor = :idUserIamCor "
                + "AND ricEnteConvenz.tiEnteConvenz = 'AMMINISTRATORE' " + "ORDER BY ricEnteConvenz.nmEnteConvenz ");
        query.setParameter("idUserIamCor", idUserIamCor);
        query.setParameter("dataOdierna", new Date());
        return (List<OrgVRicEnteConvenz>) query.getResultList();
    }

    /**
     * @deprecated
     *
     * @param idUserIamCor
     *            id user iam
     *
     * @return lista {@link OrgAmbienteEnteConvenz}
     */
    @Deprecated
    public List<OrgAmbienteEnteConvenz> retrieveAmbientiEntiConvenzAbilitatiSoloAmm(BigDecimal idUserIamCor) {
        Query query = getEntityManager().createQuery("SELECT DISTINCT ambienteEnteConvenz "
                + "FROM OrgAmbienteEnteConvenz ambienteEnteConvenz, OrgVRicEnteConvenz ricEnteConvenz "
                + "WHERE ricEnteConvenz.idUserIamCor = :idUserIamCor "
                + "AND ricEnteConvenz.idAmbienteEnteConvenz = ambienteEnteConvenz.idAmbienteEnteConvenz "
                + "AND ricEnteConvenz.tiEnteConvenz = 'AMMINISTRATORE' " + "ORDER BY ricEnteConvenz.nmEnteConvenz ");
        query.setParameter("idUserIamCor", idUserIamCor);
        query.setParameter("dataOdierna", new Date());
        return (List<OrgAmbienteEnteConvenz>) query.getResultList();
    }

    public List<OrgVRicEnteConvenz> retrieveEntiConvenzAbilitatiAmbiente(BigDecimal idUserIamCor,
            List<BigDecimal> idAmbienteEnteConvenz, ConstOrgEnteSiam.TiEnteConvenz tiEnteConvenz) {
        String queryStr = "SELECT ricEnteConvenz FROM OrgVRicEnteConvenz ricEnteConvenz "
                + "WHERE ricEnteConvenz.idUserIamCor = :idUserIamCor AND ricEnteConvenz.idAmbienteEnteConvenz IN (:idAmbienteEnteConvenz) ";
        if (tiEnteConvenz != null) {
            queryStr = queryStr + "AND ricEnteConvenz.tiEnteConvenz = :tiEnteConvenz ";
        }
        queryStr = queryStr + "ORDER BY ricEnteConvenz.nmEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIamCor", idUserIamCor);
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        if (tiEnteConvenz != null) {
            query.setParameter("tiEnteConvenz", tiEnteConvenz.toString());
        }
        return (List<OrgVRicEnteConvenz>) query.getResultList();
    }

    public List<OrgVRicEnteConvenz> retrieveEntiConvenzCollegAbilitatiAmbiente(BigDecimal idUserIamCor,
            List<BigDecimal> idAmbienteEnteConvenz, ConstOrgEnteSiam.TiEnteConvenz... tiEnteConvenz) {
        String queryStr = "SELECT ricEnteConvenz FROM OrgVRicEnteConvenz ricEnteConvenz "
                + "WHERE ricEnteConvenz.idUserIamCor = :idUserIamCor AND ricEnteConvenz.idAmbienteEnteConvenz IN (:idAmbienteEnteConvenz) ";
        if (tiEnteConvenz != null) {
            queryStr = queryStr + "AND ricEnteConvenz.tiEnteConvenz IN (:tiEnteConvenz) ";
        }
        queryStr = queryStr + "ORDER BY ricEnteConvenz.nmEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIamCor", idUserIamCor);
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        if (tiEnteConvenz != null && tiEnteConvenz.length > 0) {
            List<TiEnteConvenz> tipiList = Arrays.asList(tiEnteConvenz);
            query.setParameter("tiEnteConvenz", tipiList.stream().map(t -> t.name()).collect(Collectors.toList()));
        }
        return (List<OrgVRicEnteConvenz>) query.getResultList();
    }

    public List<OrgVRicEnteConvenz> retrieveEntiConvenzAbilitatiAccordoValido(BigDecimal idUserIamCor,
            BigDecimal idAmbienteEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT ricEnteConvenz FROM OrgVRicEnteConvenz ricEnteConvenz "
                + "WHERE ricEnteConvenz.idUserIamCor = :idUserIamCor AND ricEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz "
                // MAC#22374
                + "AND EXISTS (SELECT acc FROM OrgAccordoEnte acc WHERE acc.orgEnteSiam.idEnteSiam = ricEnteConvenz.idEnteConvenz "
                + "AND acc.dtDecAccordo <= :dataCorrente AND acc.dtFineValidAccordo > :dataCorrente) "
                // end MAC#22374
                + "ORDER BY ricEnteConvenz.nmEnteConvenz ");
        query.setParameter("idUserIamCor", idUserIamCor);
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        query.setParameter("dataCorrente", new Date());
        return (List<OrgVRicEnteConvenz>) query.getResultList();
    }

    public List<OrgVRicEnteNonConvenz> retrieveEntiNonConvenzAbilitati(BigDecimal idUserIam, String tiEnteNonConvenz) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT ricEnteNonConvenz FROM OrgVRicEnteNonConvenz ricEnteNonConvenz ");
        String whereWord = " WHERE ";
        if (idUserIam != null) {
            queryStr.append(whereWord).append("ricEnteNonConvenz.idUserIamCor = :idUserIam ");
            whereWord = " AND ";
        }
        if (tiEnteNonConvenz != null) {
            queryStr.append(whereWord).append("ricEnteNonConvenz.tiEnteNonConvenz = :tiEnteNonConvenz ");
            whereWord = " AND ";
        }
        queryStr.append("ORDER BY ricEnteNonConvenz.nmEnteSiam");
        Query query = getEntityManager().createQuery(queryStr.toString());
        if (idUserIam != null) {
            query.setParameter("idUserIam", idUserIam);
        }
        if (tiEnteNonConvenz != null) {
            query.setParameter("tiEnteNonConvenz", tiEnteNonConvenz);
        }
        return (List<OrgVRicEnteNonConvenz>) query.getResultList();
    }

    public List<OrgVRicEnteNonConvenz> retrieveEntiNonConvenzAbilitati(BigDecimal idUserIam,
            List<String> tiEnteNonConvenz) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT ricEnteNonConvenz FROM OrgVRicEnteNonConvenz ricEnteNonConvenz ");
        String whereWord = " WHERE ";
        if (idUserIam != null) {
            queryStr.append(whereWord).append("ricEnteNonConvenz.idUserIamCor = :idUserIam ");
            whereWord = " AND ";
        }
        if (tiEnteNonConvenz != null) {
            queryStr.append(whereWord).append("ricEnteNonConvenz.tiEnteNonConvenz IN :tiEnteNonConvenz ");
            whereWord = " AND ";
        }
        queryStr.append("ORDER BY ricEnteNonConvenz.nmEnteSiam");
        Query query = getEntityManager().createQuery(queryStr.toString());
        if (idUserIam != null) {
            query.setParameter("idUserIam", idUserIam);
        }
        if (tiEnteNonConvenz != null) {
            query.setParameter("tiEnteNonConvenz", tiEnteNonConvenz);
        }
        return (List<OrgVRicEnteNonConvenz>) query.getResultList();
    }

    public List<OrgVRicEnteConvenz> retrieveEntiConvenzAbilitatiXEnteCapofila(BigDecimal idUserIamCor,
            BigDecimal idEnteConvenz, BigDecimal idAmbienteEnteConvenz) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT ricEnteConvenz FROM OrgVRicEnteConvenz ricEnteConvenz, OrgAccordoEnte accordoEnte ");
        String clause = " WHERE ";
        if (idUserIamCor != null) {
            queryStr.append(clause).append("ricEnteConvenz.idUserIamCor = :idUserIamCor ");
            clause = " AND ";
        }
        if (idEnteConvenz != null) {
            queryStr.append(clause).append("ricEnteConvenz.idEnteConvenz = :idEnteConvenz ");
            clause = " AND ";
        }
        if (idAmbienteEnteConvenz != null) {
            queryStr.append(clause).append("ricEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
            clause = " AND ";
        }
        queryStr.append(clause)
                .append("ricEnteConvenz.flNonConvenz = '0' "
                        + "AND accordoEnte.orgEnteSiam.idEnteSiam = ricEnteConvenz.idEnteConvenz "
                        + "AND ricEnteConvenz.idEnteConserv = accordoEnte.orgEnteSiamByIdEnteConvenzConserv.idEnteSiam "
                        + "AND ricEnteConvenz.idEnteGestore = accordoEnte.orgEnteSiamByIdEnteConvenzGestore.idEnteSiam "
                        + "ORDER BY ricEnteConvenz.nmEnteConvenz ");

        Query query = getEntityManager().createQuery(queryStr.toString());

        if (idUserIamCor != null) {
            query.setParameter("idUserIamCor", idUserIamCor);
        }
        if (idEnteConvenz != null) {
            query.setParameter("idEnteConvenz", idEnteConvenz);
        }
        if (idAmbienteEnteConvenz != null) {
            query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        }
        return (List<OrgVRicEnteConvenz>) query.getResultList();
    }

    public List<OrgVRicEnteConvenz> retrieveEntiConvenzAbilitatiXEnteCapofila(BigDecimal idUserIamCor,
            BigDecimal idAmbienteEnteConvenz) {
        StringBuilder queryStr = new StringBuilder("SELECT ricEnteConvenz FROM OrgVRicEnteConvenz ricEnteConvenz ");
        String clause = " WHERE ";
        if (idUserIamCor != null) {
            queryStr.append(clause).append("ricEnteConvenz.idUserIamCor = :idUserIamCor ");
            clause = " AND ";
        }
        if (idAmbienteEnteConvenz != null) {
            queryStr.append(clause).append("ricEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
            clause = " AND ";
        }
        // MAC#20840
        // queryStr.append(clause)
        // .append("(ricEnteConvenz.dtIniVal <= :dataOdierna AND ricEnteConvenz.dtCessazione >= :dataOdierna) ");
        // end MAC#20840
        clause = " ORDER BY ";
        queryStr.append(clause).append("ricEnteConvenz.nmEnteConvenz");

        Query query = getEntityManager().createQuery(queryStr.toString());
        if (idUserIamCor != null) {
            query.setParameter("idUserIamCor", idUserIamCor);
        }
        if (idAmbienteEnteConvenz != null) {
            query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        }
        // MAC#20840
        // query.setParameter("dataOdierna", new Date());
        // end MAC#20840
        return (List<OrgVRicEnteConvenz>) query.getResultList();
    }

    public List<UsrVAbilAmbEnteConvenz> retrieveAbilAmbEntiConvenzValidByIdEnteConserv(BigDecimal idUserIam,
            BigDecimal idEnteConserv) {
        Query query = getEntityManager().createQuery("SELECT abilAmbEnteConvenz "
                + "FROM OrgAmbienteEnteConvenz ambienteEnteConvenz, UsrVAbilAmbEnteConvenz abilAmbEnteConvenz "
                + "WHERE abilAmbEnteConvenz.id.idUserIam = :idUserIam "
                + "AND ambienteEnteConvenz.idAmbienteEnteConvenz = abilAmbEnteConvenz.id.idAmbienteEnteConvenz "
                + "AND ambienteEnteConvenz.dtIniVal <= :dataOdierna AND ambienteEnteConvenz.dtFineVal >= :dataOdierna "
                + "AND ambienteEnteConvenz.orgEnteSiamByIdEnteConserv.idEnteSiam = :idEnteConserv "
                + "ORDER BY abilAmbEnteConvenz.nmAmbienteEnteConvenz ");
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("dataOdierna", new Date());
        query.setParameter("idEnteConserv", longFrom(idEnteConserv));
        return (List<UsrVAbilAmbEnteConvenz>) query.getResultList();
    }

    public List<UsrVAbilAmbEnteXente> retrieveAbilAmbEnteXEnteValidAmbienti(BigDecimal idUserIam,
            BigDecimal idEnteConserv, BigDecimal idEnteGestore) {
        Query query = getEntityManager().createQuery("SELECT abilAmbEnteXEnte "
                + "FROM OrgAmbienteEnteConvenz ambienteEnteConvenz, UsrVAbilAmbEnteXente abilAmbEnteXEnte "
                + "WHERE abilAmbEnteXEnte.id.idUserIam = :idUserIam "
                + "AND ambienteEnteConvenz.idAmbienteEnteConvenz = abilAmbEnteXEnte.id.idAmbienteEnteConvenz "
                + "AND ambienteEnteConvenz.dtIniVal <= :dataOdierna AND ambienteEnteConvenz.dtFineVal >= :dataOdierna "
                + "AND ambienteEnteConvenz.orgEnteSiamByIdEnteConserv.idEnteSiam = :idEnteConserv "
                + "AND ambienteEnteConvenz.orgEnteSiamByIdEnteGestore.idEnteSiam = :idEnteGestore "
                + "ORDER BY abilAmbEnteXEnte.nmAmbienteEnteConvenz ");
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("dataOdierna", new Date());
        query.setParameter("idEnteConserv", longFrom(idEnteConserv));
        query.setParameter("idEnteGestore", longFrom(idEnteGestore));
        return (List<UsrVAbilAmbEnteXente>) query.getResultList();
    }

    public List<UsrVAbilAmbEnteConvenz> retrieveAmbientiEntiConvenzAbilitati(BigDecimal idUserIam) {
        Query query = getEntityManager()
                .createQuery("SELECT abilAmbEnteConvenz FROM UsrVAbilAmbEnteConvenz abilAmbEnteConvenz "
                        + "WHERE abilAmbEnteConvenz.id.idUserIam = :idUserIam "
                        + "ORDER BY abilAmbEnteConvenz.nmAmbienteEnteConvenz ");
        query.setParameter("idUserIam", idUserIam);
        return (List<UsrVAbilAmbEnteConvenz>) query.getResultList();
    }

    public List<UsrVAbilAmbEnteXente> retrieveAmbientiEntiXenteAbilitati(BigDecimal idUserIam) {
        Query query = getEntityManager()
                .createQuery("SELECT abilAmbEnteXente FROM UsrVAbilAmbEnteXente abilAmbEnteXente "
                        + "WHERE abilAmbEnteXente.id.idUserIam = :idUserIam "
                        + "ORDER BY abilAmbEnteXente.nmAmbienteEnteConvenz ");
        query.setParameter("idUserIam", idUserIam);
        return (List<UsrVAbilAmbEnteXente>) query.getResultList();
    }

    public List<UsrVAbilAmbEnteXente> retrieveAmbientiEntiXenteAbilitatiValid(BigDecimal idUserIam) {
        Query query = getEntityManager().createQuery("SELECT abilAmbEnteXEnte "
                + "FROM OrgAmbienteEnteConvenz ambienteEnteConvenz, UsrVAbilAmbEnteXente abilAmbEnteXEnte "
                + "WHERE abilAmbEnteXEnte.id.idUserIam = :idUserIam "
                + "AND ambienteEnteConvenz.idAmbienteEnteConvenz = abilAmbEnteXEnte.id.idAmbienteEnteConvenz "
                + "AND ambienteEnteConvenz.dtIniVal <= :dataOdierna AND ambienteEnteConvenz.dtFineVal >= :dataOdierna "
                + "ORDER BY abilAmbEnteXEnte.nmAmbienteEnteConvenz ");
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("dataOdierna", new Date());
        return (List<UsrVAbilAmbEnteXente>) query.getResultList();
    }

    public List<UsrVAbilEnteConvenz> retrieveEntiConvenzAbilitati(BigDecimal idUserIam, Date data) {
        String queryStr = "SELECT abilEnteConvenz FROM UsrVAbilEnteConvenz abilEnteConvenz, OrgEnteSiam enteSiam "
                + "WHERE abilEnteConvenz.id.idUserIam = :idUserIam "
                + "AND abilEnteConvenz.id.idEnteConvenz = enteSiam.idEnteSiam ";

        if (data != null) {
            queryStr = queryStr + "AND enteSiam.dtCessazione > :data ";
        }

        queryStr = queryStr + "ORDER BY abilEnteConvenz.nmEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", idUserIam);
        if (data != null) {
            query.setParameter("data", data);
        }
        return (List<UsrVAbilEnteConvenz>) query.getResultList();
    }

    public List<UsrVAbilEnteConvenz> retrieveEntiConvenzAbilitatiByUtenteAmbiente(BigDecimal idUserIam,
            BigDecimal idAmbienteEnteConvenz, Date data) {
        String queryStr = "SELECT abilEnteConvenz FROM UsrVAbilEnteConvenz abilEnteConvenz, OrgEnteSiam enteSiam "
                + "WHERE abilEnteConvenz.id.idUserIam = :idUserIam "
                + "AND abilEnteConvenz.id.idEnteConvenz = enteSiam.idEnteSiam "
                + "AND abilEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ";

        if (data != null) {
            queryStr = queryStr + "AND enteSiam.dtCessazione > :data ";
        }

        queryStr = queryStr + "ORDER BY abilEnteConvenz.nmEnteConvenz ";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", idUserIam);
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);

        if (data != null) {
            query.setParameter("data", data);
        }

        return (List<UsrVAbilEnteConvenz>) query.getResultList();
    }

    public OrgAmbienteEnteConvenz retrieveOrgAmbienteEnteConvenzByEnteConvenz(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT ambienteEnteConvenz FROM OrgEnteSiam enteSiam "
                + "JOIN enteSiam.orgAmbienteEnteConvenz ambienteEnteConvenz "
                + "WHERE enteSiam.idEnteSiam = :idEnteConvenz ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<OrgAmbienteEnteConvenz> list = (List<OrgAmbienteEnteConvenz>) query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<UsrVAbilAmbConvenzXente> retrieveAmbientiEntiConvenzAbilitati(BigDecimal idUserIam,
            String nmAmbienteEnteConvenz, String dsAmbienteEnteConvenz) {

        StringBuilder queryStr = new StringBuilder(
                "SELECT abilAmbConvenzXEnte FROM UsrVAbilAmbConvenzXente abilAmbConvenzXEnte "
                        + "WHERE abilAmbConvenzXEnte.id.idUserIam = :idUserIam ");

        String clause = " AND ";

        if (StringUtils.isNotBlank(nmAmbienteEnteConvenz)) {
            queryStr.append(clause)
                    .append("UPPER(abilAmbConvenzXEnte.nmAmbienteEnteConvenz) LIKE :nmAmbienteEnteConvenz ");
        }

        if (StringUtils.isNotBlank(dsAmbienteEnteConvenz)) {
            queryStr.append(clause)
                    .append("UPPER(abilAmbConvenzXEnte.dsAmbienteEnteConvenz) LIKE :dsAmbienteEnteConvenz ");
        }

        queryStr.append("ORDER BY abilAmbConvenzXEnte.nmAmbienteEnteConvenz ");

        Query query = getEntityManager().createQuery(queryStr.toString());

        if (StringUtils.isNotBlank(nmAmbienteEnteConvenz)) {
            query.setParameter("nmAmbienteEnteConvenz", "%" + nmAmbienteEnteConvenz.toUpperCase() + "%");
        }
        if (StringUtils.isNotBlank(dsAmbienteEnteConvenz)) {
            query.setParameter("dsAmbienteEnteConvenz", "%" + dsAmbienteEnteConvenz.toUpperCase() + "%");
        }

        query.setParameter("idUserIam", idUserIam);
        return (List<UsrVAbilAmbConvenzXente>) query.getResultList();
    }

    /**
     * Ritorna l'oggetto ambiente ente convenzionato se esiste
     *
     * @param nmAmbienteEnteConvenz
     *            nome ambiente ente
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgAmbienteEnteConvenz getOrgAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        OrgAmbienteEnteConvenz ambienteEnteConvenz = null;
        if (StringUtils.isNotBlank(nmAmbienteEnteConvenz)) {
            Query query = getEntityManager().createQuery(
                    "SELECT ambienteEnteConvenz FROM OrgAmbienteEnteConvenz ambienteEnteConvenz WHERE ambienteEnteConvenz.nmAmbienteEnteConvenz = :nmAmbienteEnteConvenz");
            query.setParameter("nmAmbienteEnteConvenz", nmAmbienteEnteConvenz);
            List<OrgAmbienteEnteConvenz> list = query.getResultList();
            if (!list.isEmpty()) {
                ambienteEnteConvenz = list.get(0);
            }
        } else {
            throw new IllegalArgumentException("Parametro nmAmbienteEnteConvenz nullo");
        }
        return ambienteEnteConvenz;
    }

    /**
     * Ritorna l'oggetto collegamento ente convenzionato se esiste
     *
     * @param nmColleg
     *            nome collegamento ente
     *
     * @return l'entity richiesta se esiste, oppure <code>null</code>
     */
    public OrgCollegEntiConvenz getOrgCollegEntiConvenz(String nmColleg) {
        OrgCollegEntiConvenz collegEntiConvenz = null;
        if (StringUtils.isNotBlank(nmColleg)) {
            Query query = getEntityManager().createQuery(
                    "SELECT collegEntiConvenz FROM OrgCollegEntiConvenz collegEntiConvenz WHERE collegEntiConvenz.nmColleg = :nmColleg");
            query.setParameter("nmColleg", nmColleg);
            List<OrgCollegEntiConvenz> list = query.getResultList();
            if (!list.isEmpty()) {
                collegEntiConvenz = list.get(0);
            }
        } else {
            throw new IllegalArgumentException("Parametro nmColleg nullo");
        }
        return collegEntiConvenz;
    }

    public OrgEnteSiam retrieveEnteCapofila(BigDecimal idEnteConvenzCapofila) {
        OrgEnteSiam enteCapofila = getEntityManager().find(OrgEnteSiam.class, idEnteConvenzCapofila.longValue());
        return enteCapofila;
    }

    /**
     * Ritorna la lista di enti convenzionati per l'ambiente dato in input
     *
     * @param idAmbienteEnteConvenz
     *            id ambiente ente convenzionato
     *
     * @return la lista di enti convenzionati
     */
    public List<OrgVRicEnteConvenz> retrieveOrgVRicEnteConvenzByAmbiente(BigDecimal idAmbienteEnteConvenz) {
        Query query = getEntityManager().createQuery(
                "SELECT DISTINCT new it.eng.saceriam.viewEntity.OrgVRicEnteConvenz (ricEnteConvenz.idEnteConvenz, "
                        + "ricEnteConvenz.nmEnteConvenz, ricEnteConvenz.idCategEnte, ricEnteConvenz.idAmbitoTerrit, "
                        + "ricEnteConvenz.dtIniVal, ricEnteConvenz.dtCessazione, ricEnteConvenz.flEsistonoModuli, "
                        + "ricEnteConvenz.dtRichModuloInfo, ricEnteConvenz.cdTipoAccordo, ricEnteConvenz.archivista, ricEnteConvenz.enteAttivo, "
                        + "ricEnteConvenz.flRecesso, ricEnteConvenz.flChiuso, ricEnteConvenz.dtFineValidAccordo, ricEnteConvenz.dtScadAccordo, ricEnteConvenz.flEsistonoGestAcc, ricEnteConvenz.cdEnteConvenz,"
                        + "ricEnteConvenz.dtDecAccordo,ricEnteConvenz.dtDecAccordoInfo) "
                        + "FROM OrgVRicEnteConvenz ricEnteConvenz WHERE ricEnteconvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz "
                        + "ORDER BY ricEnteConvenz.nmEnteConvenz ");
        query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        return query.getResultList();
    }

    /*
     * NOTA: su richiesta, respinta, di aggiungere il campo nmServizioErogato nella vista, si Ã¨ proceduto al join per
     * recuperare lo stesso
     */
    public List<Object[]> retrieveOrgVLisServFatturaListByIdFattura(BigDecimal idFatturaEnte) {
        String queryStr = "SELECT servFattura, servizioFattura.orgServizioErog.idServizioErogato, servizioFattura.orgServizioErog.nmServizioErogato "
                + "FROM OrgVLisServFattura servFattura, OrgServizioFattura servizioFattura "
                + "WHERE servFattura.idFatturaEnte = :idFatturaEnte "
                + "AND servFattura.idServizioFattura = servizioFattura.idServizioFattura "
                + "ORDER BY servizioFattura.orgServizioErog.dtErog";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idFatturaEnte", idFatturaEnte);
        return (List<Object[]>) query.getResultList();
    }

    public List<Object[]> retrieveOrgVLisServFatturaListByIdServizioErog(BigDecimal idServizioErogato) {
        String queryStr = "SELECT servFattura, fatturaEnte "
                + "FROM OrgVLisServFattura servFattura, OrgServizioFattura servizioFattura "
                + "JOIN servizioFattura.orgServizioErog servizioErog "
                + "JOIN servizioFattura.orgFatturaEnte fatturaEnte "
                + "WHERE servizioErog.idServizioErogato = :idServizioErogato "
                + "AND servFattura.idServizioFattura = servizioFattura.idServizioFattura "
                + "ORDER BY servizioFattura.orgServizioErog.dtErog";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idServizioErogato", idServizioErogato.longValue());
        return (List<Object[]>) query.getResultList();
    }

    public List<OrgPagamFatturaEnte> retrieveOrgPagamFatturaEnteList(BigDecimal idFatturaEnte) {
        String queryStr = "SELECT pagamFatturaEnte FROM OrgPagamFatturaEnte pagamFatturaEnte "
                + "WHERE pagamFatturaEnte.orgFatturaEnte.idFatturaEnte = :idFatturaEnte "
                + "ORDER BY pagamFatturaEnte.dtPagam ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idFatturaEnte", longFrom(idFatturaEnte));
        return (List<OrgPagamFatturaEnte>) query.getResultList();
    }

    public List<Object[]> retrieveFattureRiemesseList(BigDecimal idFatturaEnte) {
        String queryStr = "SELECT DISTINCT fatturaEnte.cdRegistroEmisFattura, fatturaEnte.aaEmissFattura, fatturaEnte.cdEmisFattura, "
                + "fatturaEnte.dtEmisFattura, fatturaEnte.imTotFattura, pagamFatturaEnte.imPagam, "
                + "statoFatturaEntes.tiStatoFatturaEnte, statoFatturaEntes.dtRegStatoFatturaEnte, fatturaEnte.idFatturaEnte,pagamFatturaEnte.dtPagam "
                + "FROM OrgFatturaEnte fatturaEnte " + "LEFT JOIN fatturaEnte.orgPagamFatturaEntes pagamFatturaEnte "
                + "JOIN fatturaEnte.orgStatoFatturaEntes statoFatturaEntes "
                + "WHERE fatturaEnte.orgFatturaEnte.idFatturaEnte = :idFatturaEnte "
                + "AND fatturaEnte.idStatoFatturaEnteCor = statoFatturaEntes.idStatoFatturaEnte "
                // + "AND fatturaEnte.idFatturaEnte = visFattura.idFatturaEnte "
                + "ORDER BY pagamFatturaEnte.dtPagam ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idFatturaEnte", longFrom(idFatturaEnte));
        return (List<Object[]>) query.getResultList();
    }

    public List<OrgSollecitoFatturaEnte> getOrgSollecitoFatturaEnteList(BigDecimal idFatturaEnte) {
        String queryStr = "SELECT sollecitoFatturaEnte FROM OrgSollecitoFatturaEnte sollecitoFatturaEnte "
                + "WHERE sollecitoFatturaEnte.orgFatturaEnte.idFatturaEnte = :idFatturaEnte ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idFatturaEnte", longFrom(idFatturaEnte));
        return (List<OrgSollecitoFatturaEnte>) query.getResultList();
    }

    /**
     * Restituisce il valore dell'ultimo progressivo incasso
     *
     * @param idFatturaEnte
     *            id fattura/ente
     *
     * @return il progressivo versione oppure 0 se questo ancora non esiste
     */
    public BigDecimal getLastProgressivoIncasso(BigDecimal idFatturaEnte) {
        List<BigDecimal> progressiviList;
        String queryStr = "SELECT pagamFatturaEnte.pgPagam FROM OrgPagamFatturaEnte pagamFatturaEnte "
                + "WHERE pagamFatturaEnte.orgFatturaEnte.idFatturaEnte = :idFatturaEnte "
                + "ORDER BY pagamFatturaEnte.pgPagam DESC ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idFatturaEnte", idFatturaEnte.longValue());
        progressiviList = (List<BigDecimal>) query.getResultList();
        if (progressiviList != null && !progressiviList.isEmpty()) {
            return progressiviList.get(0);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public List<OrgModifFatturaEnte> retrieveOrgModifFatturaEnteList(BigDecimal idFatturaEnte) {
        String queryStr = "SELECT modifFatturaEnte FROM OrgModifFatturaEnte modifFatturaEnte "
                + "WHERE modifFatturaEnte.orgFatturaEnte.idFatturaEnte = :idFatturaEnte "
                + "ORDER BY modifFatturaEnte.dtModifFatturaEnte DESC ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idFatturaEnte", longFrom(idFatturaEnte));
        return (List<OrgModifFatturaEnte>) query.getResultList();
    }

    public boolean isUnitaDocVersataInSacer(BigDecimal idStrut, String cdRegistroKeyUnitaDoc, BigDecimal aaKeyUnitaDoc,
            String cdKeyUnitaDoc) {
        String queryStr = "SELECT COUNT(unitaDoc) FROM AroUnitaDoc unitaDoc " + "WHERE unitaDoc.idOrgStrut = :idStrut "
                + "AND unitaDoc.cdRegistroKeyUnitaDoc= :cdRegistroKeyUnitaDoc "
                + "AND unitaDoc.aaKeyUnitaDoc= :aaKeyUnitaDoc " + "AND unitaDoc.cdKeyUnitaDoc= :cdKeyUnitaDoc ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idStrut", idStrut.longValue());
        query.setParameter("cdRegistroKeyUnitaDoc", cdRegistroKeyUnitaDoc);
        query.setParameter("aaKeyUnitaDoc", aaKeyUnitaDoc);
        query.setParameter("cdKeyUnitaDoc", cdKeyUnitaDoc);
        return (Long) query.getSingleResult() > 0L;
    }

    public List<DecVLisTiUniDocAms> retrieveRegistriConnessiList(BigDecimal idTipoUnitaDoc) {
        String queryStr = "SELECT tipoUnitaDocAmmesso FROM DecVLisTiUniDocAms tipoUnitaDocAmmesso "
                + "WHERE tipoUnitaDocAmmesso.idTipoUnitaDoc = :idTipoUnitaDoc "
                + "ORDER BY tipoUnitaDocAmmesso.cdRegistroUnitaDoc ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idTipoUnitaDoc", idTipoUnitaDoc);
        return (List<DecVLisTiUniDocAms>) query.getResultList();
    }

    /**
     * Controlla che nel periodo compreso tra data di inizio e di fine validitÃ  non sia giÃ  presente un'altra
     * anagrafica per l'ente convenzionato, escludendo l'anagrafica corrente
     *
     * @param idEnteConvenz
     *            id ente/convenzionato
     * @param dtIniVal
     *            data inizio validita
     * @param dtFineVal
     *            data fine validita
     * @param idStoEnteConvenzDaEscludere
     *            id stato ente convenzionato da escludere
     *
     * @return true se esiste già  l'anagrafica
     */
    public boolean existOtherAnagrafichePerIntervallo(BigDecimal idEnteConvenz, Date dtIniVal, Date dtFineVal,
            BigDecimal idStoEnteConvenzDaEscludere) {
        String queryStr = "SELECT stoEnteConvenz FROM OrgStoEnteConvenz stoEnteConvenz "
                + "WHERE stoEnteConvenz.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "AND ((stoEnteConvenz.dtIniVal <= :dtIniVal AND stoEnteConvenz.dtFineVal >= :dtIniVal) "
                + "OR (stoEnteConvenz.dtIniVal <= :dtFineVal AND stoEnteConvenz.dtFineVal >= :dtFineVal)) ";

        if (idStoEnteConvenzDaEscludere != null) {
            queryStr = queryStr + "AND stoEnteConvenz.idStoEnteConvenz != :idStoEnteConvenzDaEscludere ";
        }
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("dtIniVal", dtIniVal);
        query.setParameter("dtFineVal", dtFineVal);
        if (idStoEnteConvenzDaEscludere != null) {
            query.setParameter("idStoEnteConvenzDaEscludere", longFrom(idStoEnteConvenzDaEscludere));
        }
        List<OrgStoEnteConvenz> list = query.getResultList();
        return !list.isEmpty();
    }

    public OrgCdIva getOrgCdIvaByCdIva(String cdIva) throws ParerUserError {
        Query query = getEntityManager()
                .createQuery("SELECT orgCdIva FROM OrgCdIva orgCdIva WHERE orgCdIva.cdIva = :cdIva");
        query.setParameter("cdIva", cdIva);
        List<OrgCdIva> list = query.getResultList();
        if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new ParerUserError("Errore nel recupero del codice iva");
        }
    }

    public boolean checkModificheIntervenuteFattura(BigDecimal idFatturaEnte) {
        Query query = getEntityManager()
                .createQuery("SELECT COUNT(modifFatturaEnte) FROM OrgModifFatturaEnte modifFatturaEnte "
                        + "JOIN modifFatturaEnte.orgFatturaEnte fatturaEnte1 WHERE fatturaEnte1.idFatturaEnte = :idFatturaEnte "
                        + "AND modifFatturaEnte.dtModifFatturaEnte > (SELECT MAX(statoFatturaEnte.dtRegStatoFatturaEnte) FROM OrgStatoFatturaEnte statoFatturaEnte "
                        + "JOIN statoFatturaEnte.orgFatturaEnte fatturaEnte2 WHERE fatturaEnte2.idFatturaEnte = :idFatturaEnte) ");
        query.setParameter("idFatturaEnte", longFrom(idFatturaEnte));
        return (Long) query.getSingleResult() > 0;
    }

    public List<OrgVCreaFatturaByAnno> getOrgVCreaFatturaByAnno(BigDecimal idEnteConvenz, BigDecimal aaFattura,
            BigDecimal aaServizioFattura) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT creaFatturaByAnno FROM OrgVCreaFatturaByAnno creaFatturaByAnno "
                        + "WHERE creaFatturaByAnno.id.idEnteConvenz = :idEnteConvenz "
                        + "AND creaFatturaByAnno.id.aaFattura = :aaFattura ");
        if (aaServizioFattura != null) {
            queryStr.append("AND creaFatturaByAnno.id.aaServizioFattura = :aaServizioFattura ");
        }
        queryStr.append("ORDER BY creaFatturaByAnno.dtDecAccordo ");
        Query query = getEntityManager().createQuery(queryStr.toString());
        query.setParameter("idEnteConvenz", idEnteConvenz);
        query.setParameter("aaFattura", aaFattura);
        if (aaServizioFattura != null) {
            query.setParameter("aaServizioFattura", aaServizioFattura);
        }
        return (List<OrgVCreaFatturaByAnno>) query.getResultList();
    }

    /*
     * public List<OrgVCreaServFattByFatt> getOrgVCreaServFattByFatt(BigDecimal idFatturaEnte) { Query query =
     * getEntityManager() .createQuery("SELECT creaServFattByFatt FROM OrgVCreaServFattByFatt creaServFattByFatt " +
     * "WHERE creaServFattByFatt.id.idFatturaEnte = :idFatturaEnte "); query.setParameter("idFatturaEnte",
     * idFatturaEnte); return (List<OrgVCreaServFattByFatt>) query.getResultList(); }
     */
    /**
     * Ritorna l'elenco dei servizi da fatturare per la fattura in ingresso e l'anno specificato, controllando che essi
     * non siano già presenti in fatture precedenti in stato diverso da STORNATA
     *
     * @param idFatturaEnte
     *            id fattura/ente
     * @param idAccordoEnte
     *            id accordo ente
     * @param annoFattServizi
     *            anno fattura/servizi
     *
     * @return lista elementi di tipo {@link OrgVCreaServFattAnnuale}
     */
    public List<OrgVCreaServFattAnnuale> getOrgVCreaServFattAnnualeCustom(BigDecimal idFatturaEnte,
            BigDecimal idAccordoEnte, BigDecimal annoFattServizi) {
        Query query = getEntityManager()
                .createQuery("SELECT creaServFattAnnuale FROM OrgVCreaServFattAnnuale creaServFattAnnuale "
                        + "WHERE creaServFattAnnuale.id.idFatturaEnte = :idFatturaEnte "
                        + "AND creaServFattAnnuale.idAccordoEnte = :idAccordoEnte "
                        + "AND creaServFattAnnuale.id.aaServizioFattura = :annoFattServizi "
                        + "AND NOT EXISTS (SELECT servFattPrec FROM OrgServizioFattura servFattPrec "
                        + "JOIN servFattPrec.orgFatturaEnte fatturaPrec, OrgStatoFatturaEnte statoCor "
                        + "WHERE statoCor.idStatoFatturaEnte = fatturaPrec.idStatoFatturaEnteCor "
                        + "AND statoCor.tiStatoFatturaEnte != 'STORNATA' "
                        + "AND servFattPrec.orgServizioErog.idServizioErogato = creaServFattAnnuale.id.idServizioErogato	"
                        + "AND servFattPrec.aaServizioFattura = creaServFattAnnuale.id.aaServizioFattura) ");
        query.setParameter("idFatturaEnte", idFatturaEnte);
        query.setParameter("idAccordoEnte", idAccordoEnte);
        query.setParameter("annoFattServizi", annoFattServizi);
        return (List<OrgVCreaServFattAnnuale>) query.getResultList();
    }

    /**
     * Ritorna l'elenco dei servizi da fatturare per la fattura in ingresso e l'anno specificato, controllando che essi
     * non siano già  presenti in fatture precedenti in stato diverso da STORNATA
     *
     * @param idFatturaEnte
     *            id fatture/ente
     * @param idAccordoEnte
     *            id accordo
     * @param annoFattServizi
     *            anno fattura servizi
     *
     * @return lista elementi di tipo {@link OrgVCreaServFattUnatantum}
     */
    public List<OrgVCreaServFattUnatantum> getOrgVCreaServFattUnatantumCustom(BigDecimal idFatturaEnte,
            BigDecimal idAccordoEnte, BigDecimal annoFattServizi) {
        Query query = getEntityManager()
                .createQuery("SELECT creaServFattUnatantum FROM OrgVCreaServFattUnatantum creaServFattUnatantum "
                        + "WHERE creaServFattUnatantum.id.idFatturaEnte = :idFatturaEnte "
                        + "AND creaServFattUnatantum.idAccordoEnte = :idAccordoEnte "
                        + "AND creaServFattUnatantum.id.aaServizioFattura = :annoFattServizi "
                        + "AND NOT EXISTS (SELECT servFattPrec FROM OrgServizioFattura servFattPrec "
                        + "JOIN servFattPrec.orgFatturaEnte fatturaPrec, OrgStatoFatturaEnte statoCor "
                        + "WHERE statoCor.idStatoFatturaEnte = fatturaPrec.idStatoFatturaEnteCor "
                        + "AND statoCor.tiStatoFatturaEnte != 'STORNATA' "
                        + "AND servFattPrec.orgServizioErog.idServizioErogato = creaServFattUnatantum.id.idServizioErogato	"
                        + "AND servFattPrec.aaServizioFattura = creaServFattUnatantum.id.aaServizioFattura) ");
        query.setParameter("idFatturaEnte", idFatturaEnte);
        query.setParameter("idAccordoEnte", idAccordoEnte);
        query.setParameter("annoFattServizi", annoFattServizi);
        return (List<OrgVCreaServFattUnatantum>) query.getResultList();
    }

    /**
     * Ritorna l'elenco dei servizi da fatturare per la fattura in ingresso e l'anno specificato, controllando che essi
     * non siano già presenti in fatture precedenti in stato diverso da STORNATA
     *
     * @param idFatturaEnte
     *            id fatture/ente
     * @param idAccordoEnte
     *            id accordo
     * @param annoFattServizi
     *            anno fattura servizi
     *
     * @return lista elementi di tipo {@link OrgVCreaServFattUnaPrec}
     */
    public List<OrgVCreaServFattUnaPrec> getOrgVCreaServFattUnaPrecCustom(BigDecimal idFatturaEnte,
            BigDecimal idAccordoEnte, BigDecimal annoFattServizi) {
        Query query = getEntityManager()
                .createQuery("SELECT creaServFattUnaPrec FROM OrgVCreaServFattUnaPrec creaServFattUnaPrec "
                        + "WHERE creaServFattUnaPrec.id.idFatturaEnte = :idFatturaEnte "
                        + "AND creaServFattUnaPrec.idAccordoEnte = :idAccordoEnte "
                        + "AND creaServFattUnaPrec.aaServizioFattura = :annoFattServizi "
                        + "AND NOT EXISTS (SELECT servFattPrec FROM OrgServizioFattura servFattPrec "
                        + "JOIN servFattPrec.orgFatturaEnte fatturaPrec, OrgStatoFatturaEnte statoCor "
                        + "WHERE statoCor.idStatoFatturaEnte = fatturaPrec.idStatoFatturaEnteCor "
                        + "AND statoCor.tiStatoFatturaEnte != 'STORNATA' "
                        + "AND servFattPrec.orgServizioErog.idServizioErogato = creaServFattUnaPrec.id.idServizioErogato	"
                        + "AND servFattPrec.aaServizioFattura = creaServFattUnaPrec.id.aaServizioFatturaPrec) ");
        query.setParameter("idFatturaEnte", idFatturaEnte);
        query.setParameter("idAccordoEnte", idAccordoEnte);
        query.setParameter("annoFattServizi", annoFattServizi);
        return (List<OrgVCreaServFattUnaPrec>) query.getResultList();
    }

    public OrgVCreaFatturaByFatt getOrgVCreaFatturaByFatt(long idFatturaStornataDaRiemettere, BigDecimal aaFattura) {
        String queryStr = "SELECT creaFatturaByFatt FROM OrgVCreaFatturaByFatt creaFatturaByFatt "
                + "WHERE creaFatturaByFatt.id.idFatturaEnteDaRicalc = :idFatturaStornataDaRiemettere "
                + "AND creaFatturaByFatt.id.aaFattura = :aaFattura ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idFatturaStornataDaRiemettere", bigDecimalFrom(idFatturaStornataDaRiemettere));
        query.setParameter("aaFattura", aaFattura);
        List<OrgVCreaFatturaByFatt> creaFattureByFattList = (List<OrgVCreaFatturaByFatt>) query.getResultList();
        if (!creaFattureByFattList.isEmpty()) {
            return creaFattureByFattList.get(0);
        } else {
            return null;
        }
    }

    public List<Long> getIdFattureStornateDaRiemettereList(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT fatturaEnte.idFatturaEnte FROM OrgFatturaEnte fatturaEnte, OrgStatoFatturaEnte statoFatturaEnte "
                + "WHERE fatturaEnte.idStatoFatturaEnteCor = statoFatturaEnte.idStatoFatturaEnte "
                + "AND statoFatturaEnte.tiStatoFatturaEnte = 'STORNATA' "
                + "AND fatturaEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz " + "AND fatturaEnte.flDaRiemis = '1' ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<Long> fattureStornateDaRiemettereList = (List<Long>) query.getResultList();
        return fattureStornateDaRiemettereList;
    }

    public BigDecimal getAnnoServiziDaFatturare(long idFatturaEnte) {
        String queryStr = "SELECT MAX(servizioFattura.aaServizioFattura) FROM OrgServizioFattura servizioFattura "
                + "JOIN servizioFattura.orgFatturaEnte fatturaEnte "
                + "WHERE fatturaEnte.idFatturaEnte = :idFatturaEnte ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idFatturaEnte", longFrom(idFatturaEnte));
        return (BigDecimal) query.getSingleResult();
    }

    public OrgSollecitoFatturaEnte getOrgSollecitoFatturaEnte(BigDecimal idFatturaEnte, String cdRegistroSollecito,
            BigDecimal aaVarSollecito, String cdKeyVarSollecito) {
        String queryStr = "SELECT sollecitoFatturaEnte FROM OrgSollecitoFatturaEnte sollecitoFatturaEnte "
                + "WHERE sollecitoFatturaEnte.orgFatturaEnte.idFatturaEnte = :idFatturaEnte "
                + "AND sollecitoFatturaEnte.cdRegistroSollecito = :cdRegistroSollecito "
                + "AND sollecitoFatturaEnte.aaVarSollecito = :aaVarSollecito "
                + "AND sollecitoFatturaEnte.cdKeyVarSollecito = :cdKeyVarSollecito ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idFatturaEnte", longFrom(idFatturaEnte));
        query.setParameter("cdRegistroSollecito", cdRegistroSollecito);
        query.setParameter("aaVarSollecito", aaVarSollecito);
        query.setParameter("cdKeyVarSollecito", cdKeyVarSollecito);
        List<OrgSollecitoFatturaEnte> sollecitiList = (List<OrgSollecitoFatturaEnte>) query.getResultList();
        if (!sollecitiList.isEmpty()) {
            return sollecitiList.get(0);
        } else {
            return null;
        }
    }

    public List<OrgFatturaEnte> getOrgFatturaEnteByStatoList(List<String> tiStatoFatturaEnte) {
        Query q = getEntityManager()
                .createQuery("SELECT fatturaEnte FROM OrgStatoFatturaEnte statoFatturaEnte, OrgFatturaEnte fatturaEnte "
                        + "WHERE statoFatturaEnte.tiStatoFatturaEnte IN (:tiStatoFatturaEnte) "
                        + "AND fatturaEnte.idStatoFatturaEnteCor = statoFatturaEnte.idStatoFatturaEnte ");
        q.setParameter("tiStatoFatturaEnte", tiStatoFatturaEnte);
        return (List<OrgFatturaEnte>) q.getResultList();
    }

    /**
     * Recupera le fatture per l'ente convenzionato e lo stato corrente passati in ingresso
     *
     * @param idEnteConvenz
     *            id ente convenzionato
     * @param tiStatoFatturaEnte
     *            tipo stato fattura ente
     *
     * @return la lista delle fatture
     */
    public List<OrgFatturaEnte> getFattureEnteConvenzionato(BigDecimal idEnteConvenz, String tiStatoFatturaEnte) {
        Query query = getEntityManager()
                .createQuery("SELECT fatturaEnte FROM OrgStatoFatturaEnte statoFatturaEnte, OrgFatturaEnte fatturaEnte "
                        + "JOIN fatturaEnte.orgEnteSiam enteSiam " + "WHERE enteSiam.idEnteSiam = :idEnteConvenz "
                        + "AND statoFatturaEnte.tiStatoFatturaEnte = :tiStatoFatturaEnte "
                        + "AND statoFatturaEnte.idStatoFatturaEnte = fatturaEnte.idStatoFatturaEnteCor ");
        query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
        query.setParameter("tiStatoFatturaEnte", tiStatoFatturaEnte);
        List<OrgFatturaEnte> list = query.getResultList();
        return list;
    }

    public List<OrgVEnteConvByDelabilorg> getEntiConvByAbilEliminate(Set<BigDecimal> dichOrganizDeleteList) {
        Query query = getEntityManager()
                .createQuery("SELECT enteConvByDelabilorg FROM OrgVEnteConvByDelabilorg enteConvByDelabilorg "
                        + "WHERE enteConvByDelabilorg.id.idDichAbilOrganiz IN (:dichOrganizDeleteList) ");
        query.setParameter("dichOrganizDeleteList", new ArrayList(dichOrganizDeleteList));
        List<OrgVEnteConvByDelabilorg> list = query.getResultList();
        return list;
    }

    public boolean existsUtenteArchivistaPerEnteSiam(BigDecimal idEnteSiam, BigDecimal idUserIam, String flReferente,
            BigDecimal idUserIamExcluded) {
        String queryStr = "SELECT 1 FROM OrgEnteArkRif enteArkRif "
                + "WHERE enteArkRif.orgEnteSiam.idEnteSiam = :idEnteSiam ";

        if (idUserIam != null) {
            queryStr = queryStr + "AND enteArkRif.usrUser.idUserIam = :idUserIam ";
        }
        if (flReferente != null) {
            queryStr = queryStr + "AND enteArkRif.flReferente = :flReferente ";
        }
        if (idUserIamExcluded != null) {
            queryStr = queryStr + "AND enteArkRif.usrUser.idUserIam != :idUserIamExcluded ";
        }

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteSiam", longFrom(idEnteSiam));
        if (idUserIam != null) {
            query.setParameter("idUserIam", longFrom(idUserIam));
        }
        if (flReferente != null) {
            query.setParameter("flReferente", flReferente);
        }
        if (idUserIamExcluded != null) {
            query.setParameter("idUserIamExcluded", longFrom(idUserIamExcluded));
        }
        query.setMaxResults(1);
        return !query.getResultList().isEmpty();
    }

    public boolean existsReferenteEntePerEnteSiam(BigDecimal idEnteSiam, BigDecimal idUserIam, String qualificaUser) {
        String queryStr = "SELECT 1 FROM OrgEnteUserRif enteUserRif "
                + "WHERE enteUserRif.orgEnteSiam.idEnteSiam = :idEnteSiam "
                + "AND enteUserRif.qualificaUser = :qualificaUser ";

        if (idUserIam != null) {
            queryStr = queryStr + "AND enteUserRif.usrUser.idUserIam = :idUserIam ";
        }

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteSiam", longFrom(idEnteSiam));
        query.setParameter("qualificaUser", qualificaUser);
        if (idUserIam != null) {
            query.setParameter("idUserIam", longFrom(idUserIam));
        }
        query.setMaxResults(1);
        return !query.getResultList().isEmpty();
    }

    public boolean existsResponsabileConservazione(BigDecimal idEnteSiam, BigDecimal idUserIam) {
        String queryStr = "SELECT 1 FROM OrgEnteUserRif enteUserRif "
                + "WHERE enteUserRif.orgEnteSiam.idEnteSiam = :idEnteSiam "
                + "AND enteUserRif.qualificaUser = 'Responsabile della conservazione' ";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteSiam", longFrom(idEnteSiam));
        query.setMaxResults(1);
        return !query.getResultList().isEmpty();
    }

    public boolean isAutoma(BigDecimal idUserIam) {
        String queryStr = "SELECT user FROM UsrUser user " + "WHERE user.tipoUser = 'AUTOMA' "
                + "AND user.idUserIam = :idUserIam ";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idUserIam", longFrom(idUserIam));
        query.setMaxResults(1);
        return !query.getResultList().isEmpty();
    }

    public boolean existsCollegamentoEntePerEnteConvenz(BigDecimal idEnteConvenz, String nmColleg) {
        String queryStr = "SELECT 1 FROM OrgAppartCollegEnti appartCollegEnti JOIN appartCollegEnti.orgCollegEntiConvenz collegEntiConvenz "
                + "WHERE appartCollegEnti.orgEnteSiam.idEnteSiam = :idEnteConvenz ";

        if (StringUtils.isNotBlank(nmColleg)) {
            queryStr = queryStr + "AND collegEntiConvenz.nmColleg = :nmColleg ";
        }

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        if (StringUtils.isNotBlank(nmColleg)) {
            query.setParameter("nmColleg", nmColleg);
        }
        query.setMaxResults(1);
        return !query.getResultList().isEmpty();
    }

    public boolean existsEnteCollegatoPerCollegamento(BigDecimal idCollegEntiConvenz, BigDecimal idEnteConvenz) {
        String queryStr = "SELECT 1 FROM OrgAppartCollegEnti appartCollegEnti "
                + "WHERE appartCollegEnti.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "AND appartCollegEnti.orgCollegEntiConvenz.idCollegEntiConvenz = :idCollegEntiConvenz ";

        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("idCollegEntiConvenz", longFrom(idCollegEntiConvenz));

        query.setMaxResults(1);
        return !query.getResultList().isEmpty();
    }

    public String[] getStrutUnitaDocAccordoOrganizMap(BigDecimal idOrganizApplic) {
        String queryStr = "SELECT treeOrganizIam FROM UsrVTreeOrganizIam treeOrganizIam "
                + "WHERE treeOrganizIam.idOrganizApplic = :idOrganizApplic "
                + "AND treeOrganizIam.nmTipoOrganiz = 'STRUTTURA' ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idOrganizApplic", idOrganizApplic);
        List<UsrVTreeOrganizIam> treeOrganizIamList = (List<UsrVTreeOrganizIam>) query.getResultList();
        if (treeOrganizIamList.isEmpty()) {
            return new String[] {};
        }
        UsrVTreeOrganizIam treeOrganizIam = treeOrganizIamList.get(0);
        String[] parts = StringUtils.split(treeOrganizIam.getDlPathIdOrganizIam(), "/");
        return parts;
    }

    public Date getDataDecorrenzaMinima(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT MIN(accordoEnte.dtDecAccordo) FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<Date> dtDecAccordoList = (List<Date>) query.getResultList();
        if (!dtDecAccordoList.isEmpty() && dtDecAccordoList.get(0) != null) {
            return dtDecAccordoList.get(0);
        }
        return null;
    }

    public Date getDataScadenzaMinima(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT MIN(accordoEnte.dtScadAccordo) FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<Date> dtScadAccordoList = (List<Date>) query.getResultList();
        if (!dtScadAccordoList.isEmpty() && dtScadAccordoList.get(0) != null) {
            return dtScadAccordoList.get(0);
        }
        return null;
    }

    public Date getDataFineValiditaMassima(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT MAX(accordoEnte.dtFineValidAccordo) FROM OrgAccordoEnte accordoEnte "
                + "WHERE accordoEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        List<Date> dtFineValidAccordoList = (List<Date>) query.getResultList();
        if (!dtFineValidAccordoList.isEmpty() && dtFineValidAccordoList.get(0) != null) {
            return dtFineValidAccordoList.get(0);
        }
        return null;
    }

    public boolean existsUDInSacer(BigDecimal idOrganizApplic, String registro, BigDecimal anno, String numero) {
        String queryStr = "SELECT unitaDoc FROM AroUnitaDoc unitaDoc " + "WHERE unitaDoc.idOrgStrut = :idOrganizApplic "
                + "AND unitaDoc.cdRegistroKeyUnitaDoc = :registro " + "AND unitaDoc.aaKeyUnitaDoc = :anno "
                + "AND unitaDoc.cdKeyUnitaDoc = :numero ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idOrganizApplic", longFrom(idOrganizApplic));
        query.setParameter("registro", registro);
        query.setParameter("anno", anno);
        query.setParameter("numero", numero);
        return !query.getResultList().isEmpty();
    }

    public List<OrgDiscipStrut> getOrgDiscipStrutList(BigDecimal idAccordoEnte) {
        String queryStr = "SELECT discipStrut FROM OrgDiscipStrut discipStrut "
                + "WHERE discipStrut.orgAccordoEnte.idAccordoEnte = :idAccordoEnte ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAccordoEnte", idAccordoEnte.longValue());
        return (List<OrgDiscipStrut>) query.getResultList();
    }

    public List<OrgDiscipStrut> getOrgDiscipStrutListByEnte(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT discipStrut FROM OrgDiscipStrut discipStrut "
                + "WHERE discipStrut.orgEnteConvenz.idEnteSiam = :idEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
        return (List<OrgDiscipStrut>) query.getResultList();
    }

    public List<OrgAppartCollegEnti> getOrgAppartCollegEntiList(BigDecimal idCollegEntiConvenz) {
        String queryStr = "SELECT appartCollegEnti FROM OrgAppartCollegEnti appartCollegEnti "
                + " JOIN FETCH appartCollegEnti.orgCollegEntiConvenz " + " JOIN FETCH appartCollegEnti.orgEnteSiam "
                + "WHERE appartCollegEnti.orgCollegEntiConvenz.idCollegEntiConvenz = :idCollegEntiConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idCollegEntiConvenz", idCollegEntiConvenz.longValue());
        return (List<OrgAppartCollegEnti>) query.getResultList();
    }

    public boolean existsDisciplinare(BigDecimal idAccordoEnte, BigDecimal idOrganizIam, Date dtDiscipStrut) {
        String queryStr = "SELECT discipStrut FROM OrgDiscipStrut discipStrut "
                + "WHERE discipStrut.orgAccordoEnte.idAccordoEnte = :idAccordoEnte "
                + "AND discipStrut.usrOrganizIam.idOrganizIam = :idOrganizIam "
                + "AND discipStrut.dtDiscipStrut = :dtDiscipStrut ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAccordoEnte", idAccordoEnte.longValue());
        query.setParameter("idOrganizIam", idOrganizIam.longValue());
        query.setParameter("dtDiscipStrut", dtDiscipStrut);
        return !query.getResultList().isEmpty();
    }

    public boolean existsDisciplinareByEnte(BigDecimal idEnteConvenz, BigDecimal idOrganizIam, Date dtDiscipStrut) {
        String queryStr = "SELECT discipStrut FROM OrgDiscipStrut discipStrut "
                + "WHERE discipStrut.orgEnteConvenz.idEnteSiam = :idEnteConvenz "
                + "AND discipStrut.usrOrganizIam.idOrganizIam = :idOrganizIam "
                + "AND discipStrut.dtDiscipStrut = :dtDiscipStrut ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
        query.setParameter("idOrganizIam", idOrganizIam.longValue());
        query.setParameter("dtDiscipStrut", dtDiscipStrut);
        return !query.getResultList().isEmpty();
    }

    public OrgDiscipStrut getDisciplinareByEnteOrganizData(BigDecimal idEnteConvenz, BigDecimal idOrganizIam,
            Date dtDiscipStrut) {
        String queryStr = "SELECT discipStrut FROM OrgDiscipStrut discipStrut "
                + "WHERE discipStrut.orgEnteConvenz.idEnteSiam = :idEnteConvenz "
                + "AND discipStrut.usrOrganizIam.idOrganizIam = :idOrganizIam "
                + "AND discipStrut.dtDiscipStrut = :dtDiscipStrut ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
        query.setParameter("idOrganizIam", idOrganizIam.longValue());
        query.setParameter("dtDiscipStrut", dtDiscipStrut);
        List<OrgDiscipStrut> lista = (List<OrgDiscipStrut>) query.getResultList();
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public int getNumMaxGestAccordoEnte(BigDecimal idAccordoEnte) {
        String queryStr = "SELECT MAX(gestAccordo.pgGestAccordo) FROM OrgGestioneAccordo gestAccordo "
                + "WHERE gestAccordo.orgAccordoEnte.idAccordoEnte = :idAccordoEnte ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idAccordoEnte", idAccordoEnte.longValue());
        return query.getSingleResult() != null ? ((BigDecimal) query.getSingleResult()).intValue() : 0;
    }

    /**
     * Metodo che ritorna i tipi di parametri di configurazione
     *
     * @return il tablebean contenente la lista di tipi parametri di configurazione
     */
    public List<String> getTiParamApplic() {
        String queryStr = "SELECT DISTINCT config.tiParamApplic FROM IamParamApplic config ORDER BY config.tiParamApplic ";
        Query q = getEntityManager().createQuery(queryStr.toString());
        List<String> params = q.getResultList();
        return params;
    }

    /**
     * Metodo che ritorna i tipi di gestione parametri di configurazione
     *
     * @return il tablebean contenente la lista di tipi di gestione parametri di configurazione
     */
    public List<String> getTiGestioneParam() {
        String queryStr = "SELECT DISTINCT config.tiGestioneParam FROM IamParamApplic config ORDER BY config.tiGestioneParam ";
        Query q = getEntityManager().createQuery(queryStr.toString());
        List<String> params = q.getResultList();
        return params;
    }

    /**
     * Metodo che ritorna i parametri di configurazione
     *
     * @param tiParamApplic
     *            tipo parametro applicativo
     * @param tiGestioneParam
     *            tipo gestione parametro
     * @param flAppartApplic
     *            flag appartenenza applicativo 1/0 (true/false)
     * @param flApparteEnte
     *            flag appartenenza ente 1/0 (true/false)
     * @param flAppartAmbiente
     *            flag appartenenza ambiente 1/0 (true/false)
     *
     * @return lista elementi di tipo {@link IamParamApplic}
     */
    public List<IamParamApplic> getIamParamApplicList(String tiParamApplic, String tiGestioneParam,
            String flAppartApplic, String flAppartAmbiente, String flApparteEnte) {
        StringBuilder queryStr = new StringBuilder("SELECT paramApplic FROM IamParamApplic paramApplic ");
        String whereWord = " WHERE ";
        if (tiParamApplic != null) {
            queryStr.append(whereWord).append("paramApplic.tiParamApplic = :tiParamApplic ");
            whereWord = "AND ";
        }
        if (tiGestioneParam != null) {
            queryStr.append(whereWord).append("paramApplic.tiGestioneParam = :tiGestioneParam ");
            whereWord = "AND ";
        }
        if (flAppartApplic != null) {
            queryStr.append(whereWord).append("paramApplic.flAppartApplic = :flAppartApplic ");
            whereWord = "AND ";
        }
        if (flAppartAmbiente != null) {
            queryStr.append(whereWord).append("paramApplic.flAppartAmbiente = :flAppartAmbiente ");
            whereWord = "AND ";
        }
        if (flApparteEnte != null) {
            queryStr.append(whereWord).append("paramApplic.flApparteEnte = :flApparteEnte ");
            whereWord = "AND ";
        }
        queryStr.append("ORDER BY paramApplic.tiParamApplic, paramApplic.nmParamApplic ");
        Query q = getEntityManager().createQuery(queryStr.toString());
        if (tiParamApplic != null) {
            q.setParameter("tiParamApplic", tiParamApplic);
        }
        if (tiGestioneParam != null) {
            q.setParameter("tiGestioneParam", tiGestioneParam);
        }
        if (flAppartApplic != null) {
            q.setParameter("flAppartApplic", flAppartApplic);
        }
        if (flAppartAmbiente != null) {
            q.setParameter("flAppartAmbiente", flAppartAmbiente);
        }
        if (flApparteEnte != null) {
            q.setParameter("flApparteEnte", flApparteEnte);
        }
        List<IamParamApplic> params = (List<IamParamApplic>) q.getResultList();
        return params;
    }

    public boolean existsIamValoreParamApplic(long idParamApplic, String tiAppart) {
        Query q = getEntityManager().createQuery("SELECT 1 FROM IamValoreParamApplic valoreParamApplic "
                + "WHERE valoreParamApplic.iamParamApplic.idParamApplic = :idParamApplic "
                + "AND valoreParamApplic.tiAppart = :tiAppart ");
        q.setParameter("idParamApplic", longFrom(idParamApplic));
        q.setParameter("tiAppart", tiAppart);
        q.setMaxResults(1);
        return !q.getResultList().isEmpty();
    }

    public IamValoreParamApplic getIamValoreParamApplic(long idParamApplic, String tiAppart) {
        Query q = getEntityManager().createQuery("SELECT valoreParamApplic FROM IamValoreParamApplic valoreParamApplic "
                + "WHERE valoreParamApplic.iamParamApplic.idParamApplic = :idParamApplic "
                + "AND valoreParamApplic.tiAppart = :tiAppart ");
        q.setParameter("idParamApplic", longFrom(idParamApplic));
        q.setParameter("tiAppart", tiAppart);
        List<IamValoreParamApplic> lista = q.getResultList();
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public boolean existsIamParamApplic(String nmParamApplic, BigDecimal idParamApplic) {
        Query q = getEntityManager().createQuery("SELECT paramApplic FROM IamParamApplic paramApplic "
                + "WHERE paramApplic.nmParamApplic = :nmParamApplic "
                + "AND paramApplic.idParamApplic != :idParamApplic ");
        q.setParameter("nmParamApplic", nmParamApplic);
        q.setParameter("idParamApplic", longFrom(idParamApplic));
        return !q.getResultList().isEmpty();
    }

    public List<IamParamApplic> getIamParamApplicListEnte() {
        Query q = getEntityManager().createQuery(
                "SELECT paramApplic FROM IamParamApplic paramApplic " + "WHERE paramApplic.flApparteEnte = '1' "
                        + "ORDER BY paramApplic.tiParamApplic, paramApplic.nmParamApplic");
        List<IamParamApplic> lista = q.getResultList();
        return lista;
    }

    public List<IamParamApplic> getIamParamApplicListAmbiente() {
        Query q = getEntityManager().createQuery(
                "SELECT paramApplic FROM IamParamApplic paramApplic " + "WHERE paramApplic.flAppartAmbiente = '1' "
                        + "ORDER BY paramApplic.tiParamApplic, paramApplic.nmParamApplic");
        List<IamParamApplic> lista = q.getResultList();
        return lista;
    }

    public IamValoreParamApplic getIamValoreParamApplic(BigDecimal idParamApplic, String tiAppart,
            BigDecimal idAmbienteEnteConvenz, BigDecimal idEnteConvenz) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT valoreParamApplic FROM IamValoreParamApplic valoreParamApplic "
                        + "WHERE valoreParamApplic.tiAppart = :tiAppart "
                        + "AND valoreParamApplic.iamParamApplic.idParamApplic = :idParamApplic ");

        if (idAmbienteEnteConvenz != null) {
            queryStr.append(
                    "AND valoreParamApplic.orgAmbienteEnteConvenz.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
        }
        if (idEnteConvenz != null) {
            queryStr.append("AND valoreParamApplic.orgEnteSiam.idEnteSiam = :idEnteConvenz ");
        }

        Query q = getEntityManager().createQuery(queryStr.toString());
        q.setParameter("tiAppart", tiAppart);
        q.setParameter("idParamApplic", longFrom(idParamApplic));
        if (idAmbienteEnteConvenz != null) {
            q.setParameter("idAmbienteEnteConvenz", longFrom(idAmbienteEnteConvenz));
        }
        if (idEnteConvenz != null) {
            q.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        }
        List<IamValoreParamApplic> lista = q.getResultList();
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public List<Object[]> getAmbientiAbilitatiUnAmbienteList(long idUserIamCor, String tipoEnte, BigDecimal idEnte) {
        String nomeVista = "";
        String andClause = "";
        BigDecimal idEntePerQuery = null;
        switch (tipoEnte) {
        case "AMMINISTRATORE":
            nomeVista = "UsrVUnambByAmmin";
            break;
        case "CONSERVATORE":
            nomeVista = "UsrVUnambByConserv";
            andClause = "AND abil.idEnteConserv = :idEnte ";
            idEntePerQuery = idEnte;
            break;
        case "GESTORE":
            nomeVista = "UsrVUnambByGestore";
            andClause = "AND abil.idEnteGestore = :idEnte ";
            idEntePerQuery = idEnte;
            break;
        default:
            break;
        }

        String queryStr = String.format(
                "SELECT abil.id.idAmbienteEnteConvenz, abil.nmAmbienteEnteConvenz, abil.dsCausaleDich FROM %1$s abil "
                        + "WHERE abil.id.idUserIamCor = :idUserIamCor %2$s " + "ORDER BY abil.nmAmbienteEnteConvenz",
                nomeVista, andClause);

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        if (idEntePerQuery != null) {
            q.setParameter("idEnte", idEntePerQuery);
        }
        List<Object[]> ambientiList = q.getResultList();
        return ambientiList;
    }

    public List<Object[]> getEntiAbilitatiUnEnteList(long idUserIamCor, String tipoEnteConvenz, BigDecimal idEnte,
            BigDecimal idAmbienteEnteConvenz) {

        String nomeVista = "";
        String andClause = "";
        String altriEnti = "";
        BigDecimal idEntePerQuery = null;
        switch (tipoEnteConvenz) {
        case "AMMINISTRATORE":
            nomeVista = "UsrVUnenteByAmmin";
            break;
        case "CONSERVATORE":
            nomeVista = "UsrVUnenteByConserv";
            andClause = "AND abil.idEnteConserv = :idEnte ";
            altriEnti = ",abil.idAppartCollegEnti";
            idEntePerQuery = idEnte;
            break;
        case "GESTORE":
            nomeVista = "UsrVUnenteByGestore";
            andClause = "AND abil.idEnteGestore = :idEnte ";
            altriEnti = ",abil.idAppartCollegEnti";
            idEntePerQuery = idEnte;
            break;
        case "PRODUTTORE":
            nomeVista = "UsrVUnenteByProdut";
            andClause = "AND abil.idEnteProdut = :idEnte ";
            altriEnti = ",abil.idAppartCollegEnti";
            idEntePerQuery = idEnte;
            break;
        default:
            break;
        }

        String queryStr = String
                .format("SELECT abil.id.idEnteConvenz, abil.nmEnteConvenz, abil.dsCausaleDich %3$s FROM %1$s abil "
                        + "WHERE abil.id.idUserIamCor = :idUserIamCor "
                        + "AND abil.idAmbienteEnteConvenz = :idAmbienteEnteConvenz %2$s "
                        + "ORDER BY abil.nmEnteConvenz", nomeVista, andClause, altriEnti);

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        q.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        if (idEntePerQuery != null) {
            q.setParameter("idEnte", idEntePerQuery);
        }
        List<Object[]> entiList = q.getResultList();
        return entiList;
    }

    public List<Object[]> getAmbientiEntiAbilitatiUnEnteList(long idUserIamCor, String tipoEnte, BigDecimal idEnte) {
        String nomeVista = "";
        String andClause = "";
        BigDecimal idEntePerQuery = null;
        switch (tipoEnte) {
        case "AMMINISTRATORE":
            nomeVista = "UsrVUnenteByAmmin";
            break;
        case "CONSERVATORE":
            nomeVista = "UsrVUnenteByConserv";
            andClause = "AND abil.idEnteConserv = :idEnte ";
            idEntePerQuery = idEnte;
            break;
        case "GESTORE":
            nomeVista = "UsrVUnenteByGestore";
            andClause = "AND abil.idEnteGestore = :idEnte ";
            idEntePerQuery = idEnte;
            break;
        case "PRODUTTORE":
            nomeVista = "UsrVUnenteByProdut";
            andClause = "AND abil.idEnteProdut = :idEnte ";
            idEntePerQuery = idEnte;
            break;
        default:
            break;
        }

        String queryStr = String.format(
                "SELECT DISTINCT abil.idAmbienteEnteConvenz, abil.nmAmbienteEnteConvenz " + "FROM %1$s abil "
                        + "WHERE abil.id.idUserIamCor = :idUserIamCor %2$s " + "ORDER BY abil.nmAmbienteEnteConvenz",
                nomeVista, andClause);

        Query q = getEntityManager().createQuery(queryStr);
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        if (idEntePerQuery != null) {
            q.setParameter("idEnte", idEntePerQuery);
        }
        List<Object[]> ambientiList = q.getResultList();
        return ambientiList;

    }

    public List<Object[]> getAmbietiDaUsrVLisEntiSiamCreaUserList(BigDecimal idRichGestUser, long idUserIamCor) {
        Query q = getEntityManager().createQuery(
                "SELECT DISTINCT entiSiamCreaUser.idAmbienteEnteConvenz, entiSiamCreaUser.nmAmbienteEnteConvenz "
                        + "FROM UsrVLisEntiSiamCreaUser entiSiamCreaUser "
                        + "WHERE entiSiamCreaUser.idRichGestUser = :idRichGestUser "
                        + "AND entiSiamCreaUser.id.idUserIamCor = :idUserIamCor "
                        + "AND entiSiamCreaUser.idAmbienteEnteConvenz IS NOT NULL "
                        + "ORDER BY entiSiamCreaUser.nmAmbienteEnteConvenz ");
        q.setParameter("idRichGestUser", idRichGestUser);
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        return q.getResultList();
    }

    public List<Object[]> getAmbietiDaUsrVLisEntiSiamAppEnteList(long idUserIamCor) {
        Query q = getEntityManager().createQuery(
                "SELECT DISTINCT entiSiamAppEnte.idAmbienteEnteConvenz, entiSiamAppEnte.nmAmbienteEnteConvenz "
                        + "FROM UsrVLisEntiSiamAppEnte entiSiamAppEnte "
                        + "WHERE entiSiamAppEnte.idUserIamCor = :idUserIamCor "
                        + "AND entiSiamAppEnte.idAmbienteEnteConvenz IS NOT NULL "
                        + "ORDER BY entiSiamAppEnte.nmAmbienteEnteConvenz ");
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        return q.getResultList();
    }

    public List<Object[]> getAmbietiDaUsrVLisEntiSiamPerAzioList(BigDecimal idRichGestUser, long idUserIamCor) {
        Query q = getEntityManager().createQuery(
                "SELECT DISTINCT entiSiamPerAzio.idAmbienteEnteConvenz, entiSiamPerAzio.nmAmbienteEnteConvenz "
                        + "FROM UsrVLisEntiSiamPerAzio entiSiamPerAzio "
                        + "WHERE entiSiamPerAzio.id.idUserIamCor = :idUserIamCor "
                        + "AND entiSiamPerAzio.idRichGestUser = :idRichGestUser "
                        + "AND entiSiamPerAzio.idAmbienteEnteConvenz IS NOT NULL "
                        + "ORDER BY entiSiamPerAzio.nmAmbienteEnteConvenz ");
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        q.setParameter("idRichGestUser", idRichGestUser);
        return q.getResultList();
    }

    public List<Object[]> getEntiDaUsrVLisEntiSiamCreaUserList(BigDecimal idRichGestUser, long idUserIamCor,
            BigDecimal idAmbienteEnteConvenz) {
        StringBuilder queryStr = new StringBuilder("SELECT entiSiamCreaUser.id.idEnteSiam, entiSiamCreaUser.nmEnteSiam "
                + "FROM UsrVLisEntiSiamCreaUser entiSiamCreaUser "
                + "WHERE entiSiamCreaUser.idRichGestUser = :idRichGestUser "
                + "AND entiSiamCreaUser.id.idUserIamCor = :idUserIamCor ");
        if (idAmbienteEnteConvenz != null) {
            queryStr.append("AND entiSiamCreaUser.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
        } else {
            queryStr.append("AND entiSiamCreaUser.idAmbienteEnteConvenz IS NULL ");
        }
        queryStr.append("ORDER BY entiSiamCreaUser.nmEnteSiam ");
        Query q = getEntityManager().createQuery(queryStr.toString());
        q.setParameter("idRichGestUser", idRichGestUser);
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        if (idAmbienteEnteConvenz != null) {
            q.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        }
        return q.getResultList();
    }

    public List<Object[]> getEntiDaUsrVLisEntiSiamPerAzioList(BigDecimal idRichGestUser, long idUserIamCor,
            BigDecimal idAmbienteEnteConvenz) {
        StringBuilder queryStr = new StringBuilder("SELECT entiSiamPerAzio.id.idEnteSiam, entiSiamPerAzio.nmEnteSiam "
                + "FROM UsrVLisEntiSiamPerAzio entiSiamPerAzio "
                + "WHERE entiSiamPerAzio.idRichGestUser = :idRichGestUser "
                + "AND entiSiamPerAzio.id.idUserIamCor = :idUserIamCor ");
        if (idAmbienteEnteConvenz != null) {
            queryStr.append("AND entiSiamPerAzio.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
        } else {
            queryStr.append("AND entiSiamPerAzio.idAmbienteEnteConvenz IS NULL ");
        }
        queryStr.append("ORDER BY entiSiamPerAzio.nmEnteSiam ");
        Query q = getEntityManager().createQuery(queryStr.toString());
        q.setParameter("idRichGestUser", idRichGestUser);
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        if (idAmbienteEnteConvenz != null) {
            q.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        }
        return q.getResultList();
    }

    public List<Object[]> getEntiDaUsrVLisEntiSiamAppEnteList(long idUserIamCor, BigDecimal idAmbienteEnteConvenz) {
        StringBuilder queryStr = new StringBuilder("SELECT entiSiamAppEnte.idEnteSiam, entiSiamAppEnte.nmEnteSiam "
                + "FROM UsrVLisEntiSiamAppEnte entiSiamAppEnte "
                + "WHERE entiSiamAppEnte.idUserIamCor = :idUserIamCor ");
        if (idAmbienteEnteConvenz != null) {
            queryStr.append("AND entiSiamAppEnte.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
        } else {
            queryStr.append("AND entiSiamAppEnte.idAmbienteEnteConvenz IS NULL ");
        }
        queryStr.append("ORDER BY entiSiamAppEnte.nmEnteSiam ");
        Query q = getEntityManager().createQuery(queryStr.toString());
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        if (idAmbienteEnteConvenz != null) {
            q.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        }
        return q.getResultList();
    }

    public List<Object[]> getAmbientiDaUsrVLisEntiModifAppEnteList(BigDecimal idRichGestUser, long idUserIamCor,
            BigDecimal idUserIamModif) {
        Query q = getEntityManager().createQuery(
                "SELECT DISTINCT entiModifAppEnte.idAmbienteEnteConvenz, entiModifAppEnte.nmAmbienteEnteConvenz "
                        + "FROM UsrVLisEntiModifAppEnte entiModifAppEnte "
                        + "WHERE entiModifAppEnte.id.idUserIamCor = :idUserIamCor "
                        + "AND entiModifAppEnte.idRichGestUser = :idRichGestUser "
                        + "AND entiModifAppEnte.idUserIamModif = :idUserIamModif "
                        + "ORDER BY entiModifAppEnte.nmAmbienteEnteConvenz ");
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        q.setParameter("idRichGestUser", idRichGestUser);
        q.setParameter("idUserIamModif", idUserIamModif);
        return q.getResultList();
    }

    public List<Object[]> getEntiDaUsrVLisEntiModifAppEnteList(BigDecimal idRichGestUser, long idUserIamCor,
            BigDecimal idUserIamModif, BigDecimal idAmbienteEnteConvenz) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT entiModifAppEnte.id.idEnteSiam, entiModifAppEnte.nmEnteSiam "
                        + "FROM UsrVLisEntiModifAppEnte entiModifAppEnte "
                        + "WHERE entiModifAppEnte.id.idUserIamCor = :idUserIamCor "
                        + "AND entiModifAppEnte.idRichGestUser = :idRichGestUser "
                        + "AND entiModifAppEnte.idUserIamModif = :idUserIamModif ");
        if (idAmbienteEnteConvenz != null) {
            queryStr.append("AND entiModifAppEnte.idAmbienteEnteConvenz = :idAmbienteEnteConvenz ");
        } else {
            queryStr.append("AND entiModifAppEnte.idAmbienteEnteConvenz IS NULL ");
        }
        queryStr.append("ORDER BY entiModifAppEnte.nmEnteSiam ");
        Query q = getEntityManager().createQuery(queryStr.toString());
        q.setParameter("idUserIamCor", bigDecimalFrom(idUserIamCor));
        q.setParameter("idRichGestUser", idRichGestUser);
        q.setParameter("idUserIamModif", idUserIamModif);
        if (idAmbienteEnteConvenz != null) {
            q.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        }
        return q.getResultList();
    }

    public List<OrgAppartCollegEnti> retrieveOrgAppartCollegEnti(BigDecimal idCollegEntiConvenz) {
        String queryStr = "SELECT appartCollegEnti FROM OrgAppartCollegEnti appartCollegEnti "
                + "WHERE appartCollegEnti.orgCollegEntiConvenz.idCollegEntiConvenz = :idCollegEntiConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idCollegEntiConvenz", longFrom(idCollegEntiConvenz));
        return (List<OrgAppartCollegEnti>) query.getResultList();
    }

    public List<OrgAppartCollegEnti> retrieveOrgAppartCollegEntiByIdEnteConvenz(BigDecimal idEnteConvenz) {
        String queryStr = "SELECT appartCollegEnti FROM OrgAppartCollegEnti appartCollegEnti "
                + "WHERE appartCollegEnti.orgEnteSiam.idEnteSiam = :idEnteConvenz ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return (List<OrgAppartCollegEnti>) query.getResultList();
    }

    public Long getIdTipoUnitaDoc(BigDecimal idStrut, String nmTipoUnitaDoc) {
        String queryStr = "SELECT tipoUnitaDoc FROM DecTipoUnitaDoc tipoUnitaDoc " + "JOIN tipoUnitaDoc.orgStrut strut "
                + "WHERE tipoUnitaDoc.nmTipoUnitaDoc = :nmTipoUnitaDoc " + "AND strut.idStrut = :idStrut ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("nmTipoUnitaDoc", nmTipoUnitaDoc);
        query.setParameter("idStrut", longFrom(idStrut));
        List<DecTipoUnitaDoc> tipoUdList = (List<DecTipoUnitaDoc>) query.getResultList();
        if (!tipoUdList.isEmpty()) {
            return tipoUdList.get(0).getIdTipoUnitaDoc();
        }
        return null;
    }

    /**
     * Ritorna la stringa di stato dell'accordo dato in input
     *
     * @param idAccordoEnte
     *            id accordo ente
     *
     * @return la stringa di stato accordo
     */
    public String getTiStatoAccordoById(BigDecimal idAccordoEnte) {
        Query query = getEntityManager().createQuery("SELECT DISTINCT visStatoAccordo.tiStatoAccordo "
                + "FROM OrgVVisStatoAccordo visStatoAccordo " + "WHERE visStatoAccordo.idAccordoEnte = :idAccordoEnte");
        query.setParameter("idAccordoEnte", idAccordoEnte);
        final List resultList = query.getResultList();
        if (resultList.isEmpty()) {
            throw new NoResultException("Nessun OrgVVisStatoAccordo trovato per idAccordoEnte" + idAccordoEnte);
        }
        String tiStatoAccordo = (String) resultList.get(0);
        return tiStatoAccordo;
    }

    public List<Object[]> getTipiServizioAssociatiTariffarioPerAccordoDaCreare(BigDecimal idTariffario) {
        Query query = getEntityManager().createQuery("SELECT tipoServizio.idTipoServizio, tipoServizio.cdTipoServizio "
                + "FROM OrgTariffa tariffa " + "JOIN tariffa.orgTariffario tariffario "
                + "JOIN tariffa.orgTipoServizio tipoServizio " + "WHERE tariffario.idTariffario = :idTariffario "
                + "AND tipoServizio.flTariffaAccordo = '1' " + "ORDER BY tipoServizio.cdTipoServizio ");
        query.setParameter("idTariffario", longFrom(idTariffario));
        return query.getResultList();
    }

    public List<Object[]> getTipiServizioAssociatiTariffarioPerAnnualitaDaCreare(BigDecimal idTariffario) {
        Query query = getEntityManager().createQuery("SELECT tipoServizio.idTipoServizio, tipoServizio.cdTipoServizio "
                + "FROM OrgTariffa tariffa " + "JOIN tariffa.orgTariffario tariffario "
                + "JOIN tariffa.orgTipoServizio tipoServizio " + "WHERE tariffario.idTariffario = :idTariffario "
                + "AND tipoServizio.flTariffaAnnualita = '1' ORDER BY tipoServizio.cdTipoServizio ");
        query.setParameter("idTariffario", longFrom(idTariffario));
        return query.getResultList();
    }

    public List<Object[]> getTipiServizioDelTariffarioDelTipoAccordoGiaCreato(BigDecimal idTariffario,
            BigDecimal idAccordoEnte) {
        Query query = getEntityManager().createQuery(
                "SELECT tipoServizio.idTipoServizio, tipoServizio.cdTipoServizio, 0, 0 " + "FROM OrgTariffa tariffa "
                        + "JOIN tariffa.orgTariffario tariffario " + "JOIN tariffa.orgTipoServizio tipoServizio "
                        + "WHERE tariffario.idTariffario = :idTariffario " + "AND tipoServizio.flTariffaAccordo = '1' "
                        + "ORDER BY tipoServizio.cdTipoServizio ");
        query.setParameter("idTariffario", longFrom(idTariffario));
        List<Object[]> tipiServizio = query.getResultList();

        List<Object[]> tipiServizioCompilati = new ArrayList<>();
        for (Object[] tipoServizio : tipiServizio) {
            Query query2 = getEntityManager()
                    .createQuery("SELECT tipoServizio.idTipoServizio, tipoServizio.cdTipoServizio, "
                            + "tariffaAccordo.imTariffaAccordo, tariffaAccordo.idTariffaAccordo "
                            + "FROM OrgTariffaAccordo tariffaAccordo "
                            + "JOIN tariffaAccordo.orgTipoServizio tipoServizio "
                            + "JOIN tariffaAccordo.orgAccordoEnte accordoEnte "
                            + "WHERE accordoEnte.idAccordoEnte = :idAccordoEnte "
                            + "AND tipoServizio.idTipoServizio = :idTipoServizio ");
            query2.setParameter("idAccordoEnte", longFrom(idAccordoEnte));
            query2.setParameter("idTipoServizio", (Long) tipoServizio[0]);
            List<Object[]> l = (List<Object[]>) query2.getResultList();
            tipiServizioCompilati.addAll(l);
        }

        for (Object[] tipoServizio : tipiServizio) {
            boolean trovato = false;
            for (Object[] tipoServizioCompilato : tipiServizioCompilati) {
                long ts = (Long) tipoServizio[0];
                long tsc = (Long) tipoServizioCompilato[0];
                if (ts == tsc) {
                    tipoServizio[2] = tipoServizioCompilato[2];
                    tipoServizio[3] = tipoServizioCompilato[3];
                    trovato = true;
                }
            }
            if (!trovato) {
                tipoServizio[3] = 0L;
            }
        }

        return tipiServizio;

    }

    // query.setParameter("idAccordoEnte", idAccordoEnte);
    public List<Object[]> getTipiServizioDelTariffarioDelTipoAccordoAnnualitaGiaCreato(BigDecimal idTariffario,
            BigDecimal idAaAccordo) {
        Query query = getEntityManager().createQuery(
                "SELECT tipoServizio.idTipoServizio, tipoServizio.cdTipoServizio, 0, 0 " + "FROM OrgTariffa tariffa "
                        + "JOIN tariffa.orgTariffario tariffario " + "JOIN tariffa.orgTipoServizio tipoServizio "
                        + "WHERE tariffario.idTariffario = :idTariffario "
                        + "AND tipoServizio.flTariffaAnnualita = '1' " + "ORDER BY tipoServizio.cdTipoServizio ");
        query.setParameter("idTariffario", longFrom(idTariffario));
        List<Object[]> tipiServizio = query.getResultList();

        List<Object[]> tipiServizioCompilati = new ArrayList<>();
        for (Object[] tipoServizio : tipiServizio) {
            Query query2 = getEntityManager()
                    .createQuery("SELECT tipoServizio.idTipoServizio, tipoServizio.cdTipoServizio, "
                            + "tariffaAaAccordo.imTariffaAaAccordo, tariffaAaAccordo.idTariffaAaAccordo "
                            + "FROM OrgTariffaAaAccordo tariffaAaAccordo "
                            + "JOIN tariffaAaAccordo.orgTipoServizio tipoServizio "
                            + "JOIN tariffaAaAccordo.orgAaAccordo aaAccordo "
                            + "WHERE aaAccordo.idAaAccordo = :idAaAccordo "
                            + "AND tipoServizio.idTipoServizio = :idTipoServizio ");
            query2.setParameter("idAaAccordo", longFrom(idAaAccordo));
            query2.setParameter("idTipoServizio", (Long) tipoServizio[0]);
            List<Object[]> l = (List<Object[]>) query2.getResultList();
            tipiServizioCompilati.addAll(l);
        }

        for (Object[] tipoServizio : tipiServizio) {
            // conversione necessaria perché quando nelle query si imposta una costante numerica di default è Integer
            tipoServizio[2] = convertToBigDecimal(tipoServizio[2]);
            tipoServizio[3] = convertToLong(tipoServizio[3]);
            boolean trovato = false;
            for (Object[] tipoServizioCompilato : tipiServizioCompilati) {
                long ts = (Long) tipoServizio[0];
                long tsc = (Long) tipoServizioCompilato[0];
                if (ts == tsc) {
                    tipoServizio[2] = tipoServizioCompilato[2];
                    tipoServizio[3] = tipoServizioCompilato[3];
                    trovato = true;
                }
            }
            if (!trovato) {
                tipoServizio[3] = 0L;
            }
        }

        return tipiServizio;
    }

    private BigDecimal convertToBigDecimal(Object object) {
        Asserts.check(object instanceof Number, "La conversione a BigDecimal è possibile solo per dei Number");
        if (!(object instanceof BigDecimal)) {
            return bigDecimalFrom((Number) object);
        }
        return (BigDecimal) object;
    }

    private Long convertToLong(Object object) {
        Asserts.check(object instanceof Number, "La conversione a Long è possibile solo per dei Number");
        if (!(object instanceof Long)) {
            return longFrom((Number) object);
        }
        return (Long) object;
    }

    public List<Object[]> getTipiServizioDelTariffarioAnnualitaDelTipoAccordo(BigDecimal idTipoAccordo,
            BigDecimal idAaAccordo) {
        Query query = getEntityManager().createQuery("SELECT tipoServizio.idTipoServizio, tipoServizio.cdTipoServizio, "
                + "tariffaAaAccordos.imTariffaAaAccordo "
                + "FROM OrgTariffario tariffario JOIN tariffario.orgTipoAccordo tipoAccordo "
                + "JOIN tariffario.orgTariffas tariffe JOIN tariffe.orgTipoServizio tipoServizio "
                + "JOIN tipoServizio.orgTariffaAaAccordos tariffaAaAccordos "
                + "WHERE tipoAccordo.idTipoAccordo = :idTipoAccordo "
                + "AND tariffaAaAccordos.orgAaAccordo.idAaAccordo = :idAaAccordo "
                + "AND tipoServizio.tiClasseTipoServizio != 'ALTRO' ");
        query.setParameter("idTipoAccordo", longFrom(idTipoAccordo));
        query.setParameter("idAaAccordo", longFrom(idAaAccordo));
        return query.getResultList();
    }

    public List<OrgAaAccordo> getOrgAaAccordoList(BigDecimal idAccordoEnte) {
        Query query = getEntityManager().createQuery(
                "SELECT aaAccordo FROM OrgAaAccordo aaAccordo WHERE aaAccordo.orgAccordoEnte.idAccordoEnte = :idAccordoEnte");
        query.setParameter("idAccordoEnte", longFrom(idAccordoEnte));
        return query.getResultList();
    }

    public OrgTariffaAccordo getOrgTariffaAccordo(BigDecimal idAccordoEnte, BigDecimal idTipoServizio) {
        Query query = getEntityManager().createQuery("SELECT tariffaAccordo FROM OrgTariffaAccordo tariffaAccordo "
                + "WHERE tariffaAccordo.orgAccordoEnte.idAccordoEnte = :idAccordoEnte "
                + "AND tariffaAccordo.orgTipoServizio.idTipoServizio = :idTipoServizio ");
        query.setParameter("idAccordoEnte", longFrom(idAccordoEnte));
        query.setParameter("idTipoServizio", longFrom(idTipoServizio));
        List<OrgTariffaAccordo> tariffaAccordoList = query.getResultList();
        if (!tariffaAccordoList.isEmpty()) {
            return tariffaAccordoList.get(0);
        }
        return null;
    }

    public List<OrgTariffaAccordo> getOrgTariffaAccordo(BigDecimal idAccordoEnte) {
        Query query = getEntityManager().createQuery("SELECT tariffaAccordo FROM OrgTariffaAccordo tariffaAccordo "
                + "WHERE tariffaAccordo.orgAccordoEnte.idAccordoEnte = :idAccordoEnte ");
        query.setParameter("idAccordoEnte", longFrom(idAccordoEnte));

        List<OrgTariffaAccordo> tariffaAccordoList = query.getResultList();

        return tariffaAccordoList;
    }

    public OrgTariffaAaAccordo getOrgTariffaAaAccordo(BigDecimal idAaAccordo, BigDecimal idTipoServizio) {
        Query query = getEntityManager()
                .createQuery("SELECT tariffaAaAccordo FROM OrgTariffaAaAccordo tariffaAaAccordo "
                        + "WHERE tariffaAaAccordo.orgAaAccordo.idAaAccordo = :idAaAccordo "
                        + "AND tariffaAaAccordo.orgTipoServizio.idTipoServizio = :idTipoServizio ");
        query.setParameter("idAaAccordo", longFrom(idAaAccordo));
        query.setParameter("idTipoServizio", longFrom(idTipoServizio));
        List<OrgTariffaAaAccordo> tariffaAaAccordoList = query.getResultList();
        if (!tariffaAaAccordoList.isEmpty()) {
            return tariffaAaAccordoList.get(0);
        }
        return null;
    }

    public List<OrgTariffaAaAccordo> getOrgTariffaAaAccordo(BigDecimal idAaAccordo) {
        Query query = getEntityManager()
                .createQuery("SELECT tariffaAaAccordo FROM OrgTariffaAaAccordo tariffaAaAccordo "
                        + "WHERE tariffaAaAccordo.orgAaAccordo.idAaAccordo = :idAaAccordo ");
        query.setParameter("idAaAccordo", longFrom(idAaAccordo));
        return (List<OrgTariffaAaAccordo>) query.getResultList();
    }

    public BigDecimal getPgFattura(BigDecimal idEnteConvenz, BigDecimal aaFattura) {
        Query query = getEntityManager()
                .createQuery("SELECT MAX(fatturaEnte.pgFattura) FROM OrgFatturaEnte fatturaEnte "
                        + "WHERE fatturaEnte.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                        + "AND fatturaEnte.aaFattura = :aaFattura ");

        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        query.setParameter("aaFattura", aaFattura);

        BigDecimal progr = (BigDecimal) query.getSingleResult();
        if (progr != null) {
            return progr.add(BigDecimal.ONE);
        } else {
            return BigDecimal.ONE;
        }
    }

    public List<OrgEnteArkRif> getOrgEnteArkRifList(BigDecimal idEnteConvenz) {
        Query query = getEntityManager().createQuery("SELECT archivisti FROM OrgEnteArkRif archivisti "
                + "WHERE archivisti.orgEnteSiam.idEnteSiam = :idEnteConvenz "
                + "ORDER BY archivisti.usrUser.nmUserid ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return query.getResultList();
    }

    public Date getMinDateAnagrafica(BigDecimal idEnteConvenz) {
        Query query = getEntityManager()
                .createQuery("SELECT MIN(stoEnteConvenz.dtIniVal) FROM OrgStoEnteConvenz stoEnteConvenz "
                        + "WHERE stoEnteConvenz.orgEnteSiam.idEnteSiam = :idEnteConvenz ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return (Date) query.getSingleResult();
    }

    public Date getMaxDateAnagrafica(BigDecimal idEnteConvenz) {
        Query query = getEntityManager()
                .createQuery("SELECT MAX(stoEnteConvenz.dtFineVal) FROM OrgStoEnteConvenz stoEnteConvenz "
                        + "WHERE stoEnteConvenz.orgEnteSiam.idEnteSiam = :idEnteConvenz ");
        query.setParameter("idEnteConvenz", longFrom(idEnteConvenz));
        return (Date) query.getSingleResult();
    }

    public OrgStoEnteConvenz getOrgStoEnteConvenz(BigDecimal idEnteConvenz, BigDecimal idStoEnteConvenzToExclude) {
        String queryStr = "SELECT sto FROM OrgStoEnteConvenz sto "
                + "WHERE sto.orgEnteSiam.idEnteSiam = :idEnteConvenz ";
        if (idStoEnteConvenzToExclude != null) {
            queryStr += "AND sto.idStoEnteConvenz != :idStoEnteConvenzToExclude ";
        }
        queryStr += "ORDER BY sto.dtFineVal DESC ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
        if (idStoEnteConvenzToExclude != null) {
            query.setParameter("idStoEnteConvenzToExclude", longFrom(idStoEnteConvenzToExclude));
        }
        List<OrgStoEnteConvenz> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public OrgVVisFattura getPrimoOrgVVisFatturaEnte(BigDecimal idFatturaEnte) {
        if (idFatturaEnte == null) {
            throw new IllegalStateException("Impossibile trovare un  OrgVVisFattura con idFatturaEnte null");
        }
        Query query = getEntityManager().createQuery("SELECT orgVVisFattura FROM OrgVVisFattura orgVVisFattura "
                + "WHERE orgVVisFattura.id.idFatturaEnte = :idFatturaEnte " + " ORDER BY orgVVisFattura.dtCreazione");
        query.setParameter("idFatturaEnte", idFatturaEnte);
        List<OrgVVisFattura> fatturaList = query.getResultList();

        if (fatturaList.isEmpty()) {
            throw new IllegalStateException(
                    "Non ci sono record in OrgVVisFattura per idFatturaEnte " + idFatturaEnte.toString());
        }
        return fatturaList.get(0);
    }

    public boolean isStoricoPresente(long idEnteSiam, long idAmbienteEnteConvenzExcluded, Date dtIniValAppartAmbiente,
            Date dtFinValAppartAmbiente) {
        String queryStr = "SELECT storicoEnteAmbiente FROM OrgStoEnteAmbienteConvenz storicoEnteAmbiente "
                + "WHERE storicoEnteAmbiente.orgEnteConvenz.idEnteSiam = :idEnteSiam "
                + "AND storicoEnteAmbiente.orgAmbienteEnteConvenz.idAmbienteEnteConvenz != :idAmbienteEnteConvenzExcluded "
                + "AND ((storicoEnteAmbiente.dtIniVal <= :dtIniValAppartAmbiente AND storicoEnteAmbiente.dtFinVal >= :dtIniValAppartAmbiente) "
                + "OR (storicoEnteAmbiente.dtFinVal <= :dtFinValAppartAmbiente AND storicoEnteAmbiente.dtFinVal >= :dtFinValAppartAmbiente)) ";
        Query query = getEntityManager().createQuery(queryStr);
        query.setParameter("idEnteSiam", idEnteSiam);
        query.setParameter("idAmbienteEnteConvenzExcluded", idAmbienteEnteConvenzExcluded);
        query.setParameter("dtIniValAppartAmbiente", dtIniValAppartAmbiente);
        query.setParameter("dtFinValAppartAmbiente", dtFinValAppartAmbiente);
        return !query.getResultList().isEmpty();
    }

    public boolean checkIntervalloSuStorico(BigDecimal idEnteConvenz, Date dtIniVal, Date dtFinVal) {
        Query query = getEntityManager()
                .createQuery("SELECT storicoEnteAmbiente FROM OrgStoEnteAmbienteConvenz storicoEnteAmbiente "
                        + "WHERE storicoEnteAmbiente.orgEnteConvenz.idEnteSiam = :idEnteConvenz "
                        + "AND ((storicoEnteAmbiente.dtIniVal <= :dtIniVal AND storicoEnteAmbiente.dtFinVal >= :dtIniVal) "
                        + "OR (storicoEnteAmbiente.dtIniVal <= :dtFinVal AND storicoEnteAmbiente.dtFinVal >= :dtFinVal)) ");
        query.setParameter("idEnteConvenz", idEnteConvenz.longValue());
        query.setParameter("dtIniVal", dtIniVal);
        query.setParameter("dtFinVal", dtFinVal);
        List<OrgStoEnteAmbienteConvenz> lista = (List<OrgStoEnteAmbienteConvenz>) query.getResultList();
        return !lista.isEmpty();
    }

    public List<OrgVRicAccordoEnte> retrieveOrgVRicAccordoEnteList(BigDecimal idUserIamCor,
            BigDecimal idAmbienteEnteConvenz, String nmEnteConvenz, BigDecimal idAccordoEnte,
            String cdRegistroRepertorio, BigDecimal aaRepertorio, String cdKeyRepertorio,
            List<BigDecimal> idTipoAccordo, Date dtDecAccordoDa, Date dtDecAccordoA, Date dtFineValidAccordoDa,
            Date dtFineValidAccordoA, Date dtDecAccordoInfoDa, Date dtDecAccordoInfoA, Date dtScadAccordoDa,
            Date dtScadAccordoA, String flRecesso, List<BigDecimal> idTipoGestioneAccordo,
            String flEsisteNotaFatturazione, String flEsistonoSae, Date saeDa, Date saeA, String flEsistonoSue,
            Date sueDa, Date sueA, String flFasciaManuale) {
        StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT new it.eng.saceriam.viewEntity.OrgVRicAccordoEnte (ente.idAmbienteEnteConvenz, ente.idEnteConvenz, ente.idAccordoEnte, "
                        + "ente.nmAmbienteEnteConvenz, ente.nmEnteConvenz, "
                        + "ente.cdRegistroRepertorio, ente.aaRepertorio, ente.cdKeyRepertorio, "
                        + "ente.idTipoAccordo, ente.cdTipoAccordo, ente.dtDecAccordo, ente.dtFineValidAccordo, ente.dtDecAccordoInfo, ente.dtScadAccordo, "
                        + "ente.flRecesso, ente.flEsistonoGestAcc,  ente.idTipoGestioneAccordo, ente.cdTipoGestioneAccordo, ente.flEsisteNotaFatturazione, ente.dsNotaFatturazione, "
                        + "ente.flFasciaManuale, ente.flEsistonoSae, ente.flEsistonoSue, ente.tiFasciaStandard, ente.tiFasciaManuale ) FROM OrgVRicAccordoEnte ente ");

        String clause = " WHERE ";

        if (saeDa != null || sueDa != null) {
            queryStr.append(
                    ", OrgServizioErog servizioErog JOIN servizioErog.orgAccordoEnte accordoEnte WHERE ente.idAccordoEnte = accordoEnte.idAccordoEnte ");
            clause = " AND ";
        }
        if (idUserIamCor != null) {
            queryStr.append(clause).append("ente.idUserIamCor = :idUserIamCor");
            clause = " AND ";
        }
        if (idAmbienteEnteConvenz != null) {
            queryStr.append(clause).append("ente.idAmbienteEnteConvenz = :idAmbienteEnteConvenz");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            queryStr.append(clause).append("UPPER(ente.nmEnteConvenz) LIKE :nmEnteConvenz");
            clause = " AND ";
        }
        if (idAccordoEnte != null) {
            queryStr.append(clause).append("ente.idAccordoEnte = :idAccordoEnte");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdRegistroRepertorio)) {
            queryStr.append(clause).append("UPPER(ente.cdRegistroRepertorio) LIKE :cdRegistroRepertorio");
            clause = " AND ";
        }
        if (aaRepertorio != null) {
            queryStr.append(clause).append("ente.aaRepertorio = :aaRepertorio");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdKeyRepertorio)) {
            queryStr.append(clause).append("ente.cdKeyRepertorio = :cdKeyRepertorio");
            clause = " AND ";
        }
        if (!idTipoAccordo.isEmpty()) {
            queryStr.append(clause).append("ente.idTipoAccordo IN :idTipoAccordo ");
            clause = " AND ";
        }

        if (StringUtils.isNotBlank(flRecesso)) {
            queryStr.append(clause).append("ente.flRecesso = :flRecesso");
            clause = " AND ";
        }

        if (dtDecAccordoDa != null && dtDecAccordoA != null) {
            queryStr.append(clause).append("(ente.dtDecAccordo BETWEEN :dtDecAccordoDa AND :dtDecAccordoA) ");
            clause = " AND ";
        }

        if (dtFineValidAccordoDa != null && dtFineValidAccordoA != null) {
            queryStr.append(clause)
                    .append("(ente.dtFineValidAccordo BETWEEN :dtFineValidAccordoDa AND :dtFineValidAccordoA) ");
            clause = " AND ";
        }

        if (dtDecAccordoInfoDa != null && dtDecAccordoInfoA != null) {
            queryStr.append(clause)
                    .append("(ente.dtDecAccordoInfo BETWEEN :dtDecAccordoInfoDa AND :dtDecAccordoInfoA) ");
            clause = " AND ";
        }

        if (dtScadAccordoDa != null && dtScadAccordoA != null) {
            queryStr.append(clause).append("(ente.dtScadAccordo BETWEEN :dtScadAccordoDa AND :dtScadAccordoA) ");
            clause = " AND ";
        }

        if (StringUtils.isNotBlank(flEsisteNotaFatturazione)) {
            queryStr.append(clause).append("ente.flEsisteNotaFatturazione = :flEsisteNotaFatturazione");
            clause = " AND ";
        }

        if (StringUtils.isNotBlank(flEsistonoSae)) {
            queryStr.append(clause).append("ente.flEsistonoSae = :flEsistonoSae");
            clause = " AND ";
        }

        if (StringUtils.isNotBlank(flEsistonoSue)) {
            queryStr.append(clause).append("ente.flEsistonoSue = :flEsistonoSue");
            clause = " AND ";
        }

        if (saeDa != null && saeA != null) {
            queryStr.append(clause).append("(servizioErog.dtErog BETWEEN :saeDa AND :saeA "
                    + "AND servizioErog.orgTipoServizio.tipoFatturazione = 'ANNUALE') ");
            clause = " AND ";
        }

        if (sueDa != null && sueA != null) {
            queryStr.append(clause).append("(servizioErog.dtErog BETWEEN :sueDa AND :sueA "
                    + "AND servizioErog.orgTipoServizio.tipoFatturazione = 'UNA_TANTUM') ");
            clause = " AND ";
        }

        if (StringUtils.isNotBlank(flFasciaManuale)) {
            queryStr.append(clause).append("ente.flFasciaManuale = :flFasciaManuale ");
            clause = " AND ";
        }

        queryStr.append(" ORDER BY ente.nmEnteConvenz");
        Query query = getEntityManager().createQuery(queryStr.toString());

        if (idUserIamCor != null) {
            query.setParameter("idUserIamCor", idUserIamCor);
        }
        if (idAmbienteEnteConvenz != null) {
            query.setParameter("idAmbienteEnteConvenz", idAmbienteEnteConvenz);
        }
        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            query.setParameter("nmEnteConvenz", "%" + nmEnteConvenz.toUpperCase() + "%");
        }
        if (idAccordoEnte != null) {
            query.setParameter("idAccordoEnte", idAccordoEnte);
        }
        if (StringUtils.isNotBlank(cdRegistroRepertorio)) {
            query.setParameter("cdRegistroRepertorio", "%" + cdRegistroRepertorio.toUpperCase() + "%");
        }
        if (aaRepertorio != null) {
            query.setParameter("aaRepertorio", aaRepertorio);
        }
        if (StringUtils.isNotBlank(cdKeyRepertorio)) {
            query.setParameter("cdKeyRepertorio", cdKeyRepertorio);
        }
        if (!idTipoAccordo.isEmpty()) {
            query.setParameter("idTipoAccordo", idTipoAccordo);
        }

        if (dtDecAccordoDa != null && dtDecAccordoA != null) {
            query.setParameter("dtDecAccordoDa", dtDecAccordoDa);
            query.setParameter("dtDecAccordoA", dtDecAccordoA);
        }
        if (dtFineValidAccordoDa != null && dtFineValidAccordoA != null) {
            query.setParameter("dtFineValidAccordoDa", dtFineValidAccordoDa);
            query.setParameter("dtFineValidAccordoA", dtFineValidAccordoA);
        }
        if (dtDecAccordoInfoDa != null && dtDecAccordoInfoA != null) {
            query.setParameter("dtDecAccordoInfoDa", dtDecAccordoInfoDa);
            query.setParameter("dtDecAccordoInfoA", dtDecAccordoInfoA);
        }
        if (dtScadAccordoDa != null && dtScadAccordoA != null) {
            query.setParameter("dtScadAccordoDa", dtScadAccordoDa);
            query.setParameter("dtScadAccordoA", dtScadAccordoA);
        }

        if (StringUtils.isNotBlank(flRecesso)) {
            query.setParameter("flRecesso", flRecesso);
        }

        if (StringUtils.isNotBlank(flEsisteNotaFatturazione)) {
            query.setParameter("flEsisteNotaFatturazione", flEsisteNotaFatturazione);
        }

        if (StringUtils.isNotBlank(flEsistonoSae)) {
            query.setParameter("flEsistonoSae", flEsistonoSae);
        }
        if (saeDa != null && saeA != null) {
            query.setParameter("saeDa", saeDa);
            query.setParameter("saeA", saeA);
        }

        if (StringUtils.isNotBlank(flEsistonoSue)) {
            query.setParameter("flEsistonoSue", flEsistonoSue);
        }

        if (sueDa != null && sueA != null) {
            query.setParameter("sueDa", sueDa);
            query.setParameter("sueA", sueA);
        }

        if (StringUtils.isNotBlank(flFasciaManuale)) {
            query.setParameter("flFasciaManuale", flFasciaManuale);
        }

        List<OrgVRicAccordoEnte> list = query.getResultList();
        return list;
    }

    public List<Object[]> getAnnualitaSenzaAtto() {
        String queryNativeSQL = "SELECT ENTE.NM_ENTE_SIAM, AA_ACC.AA_ANNO_ACCORDO, ACC.CD_REGISTRO_REPERTORIO||'/'||ACC.AA_REPERTORIO||'/'||ACC.CD_KEY_REPERTORIO RPI, TO_CHAR(ACC.DT_REG_ACCORDO,'DD/MM/YYYY'), TIPO_ACC.CD_TIPO_ACCORDO "
                + "FROM SACER_IAM.ORG_ENTE_SIAM ENTE " + "JOIN SACER_IAM.ORG_ACCORDO_ENTE ACC "
                + "ON (ACC.ID_ENTE_CONVENZ = ENTE.ID_ENTE_SIAM) "
                + "LEFT JOIN SACER_IAM.ORG_CLASSE_ENTE_CONVENZ CLASSE "
                + "ON (CLASSE.ID_CLASSE_ENTE_CONVENZ = ACC.ID_CLASSE_ENTE_CONVENZ) "
                + "JOIN SACER_IAM.ORG_TIPO_ACCORDO TIPO_ACC " + "ON (TIPO_ACC.ID_TIPO_ACCORDO = ACC.ID_TIPO_ACCORDO) "
                + "JOIN SACER_IAM.ORG_AA_ACCORDO AA_ACC " + "ON (AA_ACC.ID_ACCORDO_ENTE = ACC.ID_ACCORDO_ENTE) "
                + "WHERE AA_ACC.DS_ATTO_AA_ACCORDO IS NULL "
                + "AND ACC.DT_REG_ACCORDO = (SELECT MAX(ACCORDO_MAX.DT_REG_ACCORDO) "
                + "FROM SACER_IAM.ORG_ACCORDO_ENTE ACCORDO_MAX "
                + "WHERE ACCORDO_MAX.ID_ENTE_CONVENZ = ENTE.ID_ENTE_SIAM) "
                + "AND TIPO_ACC.FL_PAGAMENTO = '1' AND CD_ALGO_TARIFFARIO = 'NO_CLASSE_ENTE' "
                + "AND AA_ACC.AA_ANNO_ACCORDO >= TO_NUMBER(TO_CHAR(SYSDATE,'YYYY')) "
                + "AND AA_ACC.AA_ANNO_ACCORDO <= TO_NUMBER(TO_CHAR(NVL(ACC.DT_FINE_VALID_ACCORDO, SYSDATE +1),'YYYY')) "
                + "ORDER BY ENTE.NM_ENTE_SIAM, AA_ACC.AA_ANNO_ACCORDO ";

        Query query = getEntityManager().createNativeQuery(queryNativeSQL);
        List<Object[]> lista = (List<Object[]>) query.getResultList();
        return lista;
    }

    public List<Object[]> getReportFattureCalcolate() {
        String queryNativeSQL = "select  " + " ente.nm_ente_siam, " + "ente.CD_FISC, "
                + "serv.nm_servizio_erogato||' - '||serv_fatt.nm_servizio_fattura AS nm_servizio_erogato_completo, "
                + "serv_fatt.aa_servizio_fattura, " + "aa_acc.DS_ATTO_AA_ACCORDO, " + "ente.cd_UFE, "
                + "aa_acc.cd_cig_aa_accordo, " + "round(serv_fatt.im_servizio_fattura, 2), " + "case "
                + "when iva.cd_iva in ('PB', 'HB') " + "then round(serv_fatt.im_servizio_fattura * 1.22, 0) "
                + "else round(serv_fatt.im_servizio_fattura, 0) " + "end im_totale, " + "iva.cd_iva, " + "case "
                + "when ente.TI_MOD_PAGAM = 'conto tesoreria unica' then ente.TI_MOD_PAGAM " + "    else null "
                + "  end conto_tesoreria_unica, " + " case "
                + "    when ente.TI_MOD_PAGAM = 'conto IBAN' then ente.TI_MOD_PAGAM " + "    else null "
                + "  end conto_IBAN  " + "from ORG_ENTE_SIAM ente " + "join org_accordo_ente acc "
                + "on (acc.id_ente_convenz = ente.id_ente_siam) " + "left join org_classe_ente_convenz classe "
                + "on (classe.id_classe_ente_convenz = acc.id_classe_ente_convenz) " + "join org_fattura_ente fatt "
                + "on (fatt.id_ente_convenz = ente.id_ente_siam) " + "join org_servizio_fattura serv_fatt "
                + "on (serv_fatt.id_fattura_ente = fatt.id_fattura_ente) " + "join org_cd_iva iva "
                + "on (iva.id_cd_iva = serv_fatt.id_cd_iva) " + "join org_stato_fattura_ente stato_cor "
                + "on (stato_cor.id_stato_fattura_ente = fatt.id_stato_fattura_ente_cor) "
                + "join org_servizio_erog serv " + "on (serv.id_servizio_erogato = serv_fatt.id_servizio_erogato) "
                + "left join apl_sistema_versante sis_vers "
                + "on (sis_vers.id_sistema_versante  = serv.id_sistema_versante) " + "join org_tipo_servizio ti_serv "
                + " on (ti_serv.id_tipo_servizio = serv.id_tipo_servizio) " + "left join org_aa_accordo aa_acc "
                + "on (aa_acc.id_accordo_ente = acc.id_accordo_ente and aa_acc.aa_anno_accordo = serv_fatt.aa_servizio_fattura) "
                + "where acc.dt_reg_accordo = (select max(accordo_max.dt_reg_accordo) "
                + "from ORG_ACCORDO_ENTE accordo_max " + "where accordo_max.id_ente_convenz = ente.id_ente_siam " + ") "
                + "and exists (select * " + "from org_stato_fattura_ente stato_calc "
                + "where stato_calc.id_fattura_ente = fatt.id_fattura_ente "
                + "and stato_calc.ti_stato_fattura_ente = 'CALCOLATA'  " + ")   "
                + "and stato_cor.ti_stato_fattura_ente = 'CALCOLATA' "
                + "order by ente.nm_ente_siam, fatt.aa_fattura, fatt.pg_fattura, serv.nm_servizio_erogato ";

        Query query = getEntityManager().createNativeQuery(queryNativeSQL);
        List<Object[]> lista = (List<Object[]>) query.getResultList();
        return lista;

    }

    public List<Object[]> getReportFattureCalcolateDaRicerca(String nmEnteConvenz, String cdClienteSap,
            BigDecimal idTipoAccordo, BigDecimal idTipoServizio, BigDecimal aaFatturaDa, BigDecimal aaFatturaA,
            BigDecimal pgFatturaEnteDa, BigDecimal pgFatturaEnteA, String cdFattura, String cdRegistroEmisFattura,
            BigDecimal aaEmissFattura, String cdEmisFattura, BigDecimal pgFatturaEmis, Date dtEmisFatturaDa,
            Date dtEmisFatturaA, Set<String> tiStatoFatturaEnte) {
        StringBuilder queryStr = new StringBuilder("select  " + " ente.nm_ente_siam, " + "ente.CD_FISC, "
                + "serv.nm_servizio_erogato||' - '||serv_fatt.nm_servizio_fattura AS nm_servizio_erogato_completo, "
                + "serv_fatt.aa_servizio_fattura, " + "aa_acc.DS_ATTO_AA_ACCORDO, " + "ente.cd_UFE, "
                + "aa_acc.cd_cig_aa_accordo, " + "round(serv_fatt.im_servizio_fattura, 2), " + "case "
                + "when iva.cd_iva in ('PB', 'HB') " + "then round(serv_fatt.im_servizio_fattura * 1.22, 0) "
                + "else round(serv_fatt.im_servizio_fattura, 0) " + "end im_totale, " + "iva.cd_iva, " + "case "
                + "when ente.TI_MOD_PAGAM = 'conto tesoreria unica' then ente.TI_MOD_PAGAM " + "    else null "
                + "  end conto_tesoreria_unica, " + " case "
                + "    when ente.TI_MOD_PAGAM = 'conto IBAN' then ente.TI_MOD_PAGAM " + "    else null "
                + "  end conto_IBAN  " + "from ORG_ENTE_SIAM ente " + "join org_accordo_ente acc "
                + "on (acc.id_ente_convenz = ente.id_ente_siam) " + "left join org_classe_ente_convenz classe "
                + "on (classe.id_classe_ente_convenz = acc.id_classe_ente_convenz) " + "join org_fattura_ente fatt "
                + "on (fatt.id_ente_convenz = ente.id_ente_siam) " + "join org_servizio_fattura serv_fatt "
                + "on (serv_fatt.id_fattura_ente = fatt.id_fattura_ente) " + "join org_cd_iva iva "
                + "on (iva.id_cd_iva = serv_fatt.id_cd_iva) " + "join org_stato_fattura_ente stato_cor "
                + "on (stato_cor.id_stato_fattura_ente = fatt.id_stato_fattura_ente_cor) "
                + "join org_servizio_erog serv " + "on (serv.id_servizio_erogato = serv_fatt.id_servizio_erogato) "
                + "left join apl_sistema_versante sis_vers "
                + "on (sis_vers.id_sistema_versante  = serv.id_sistema_versante) " + "join org_tipo_servizio ti_serv "
                + " on (ti_serv.id_tipo_servizio = serv.id_tipo_servizio) " + "left join org_aa_accordo aa_acc "
                + "on (aa_acc.id_accordo_ente = acc.id_accordo_ente and aa_acc.aa_anno_accordo = serv_fatt.aa_servizio_fattura) "
                + "where acc.dt_reg_accordo = (select max(accordo_max.dt_reg_accordo) "
                + "from ORG_ACCORDO_ENTE accordo_max " + "where accordo_max.id_ente_convenz = ente.id_ente_siam " + ") "
                + "and exists (select * " + "from org_stato_fattura_ente stato_calc "
                + "where stato_calc.id_fattura_ente = fatt.id_fattura_ente "
                + "and stato_calc.ti_stato_fattura_ente = 'CALCOLATA'  " + ")   "
                + "and stato_cor.ti_stato_fattura_ente = 'CALCOLATA' ");

        String clause = " AND ";
        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            queryStr.append(clause).append("UPPER(ente.nm_Ente_siam) LIKE ?1");
            clause = " AND ";
        }
        if (idTipoAccordo != null) {
            queryStr.append(clause).append("acc.id_Tipo_Accordo = ?3");
            clause = " AND ";
        }
        if (idTipoServizio != null) {
            queryStr.append(clause).append("ti_serv.id_Tipo_Servizio = ?4");
            clause = " AND ";
        }

        if (aaFatturaDa != null && aaFatturaA == null) {
            queryStr.append(clause).append("fatt.aa_Fattura >= ?5");
            clause = " AND ";
        } else if (aaFatturaDa == null && aaFatturaA != null) {
            queryStr.append(clause).append("fatt.aa_Fattura <= ?6");
            clause = " AND ";
        } else if (aaFatturaDa != null && aaFatturaA != null) {
            queryStr.append(clause).append("(fatt.aa_Fattura >= ?5 AND fatt.aa_Fattura <= ?6)");
            clause = " AND ";
        }

        if (pgFatturaEnteDa != null && pgFatturaEnteA == null) {
            queryStr.append(clause).append("fatt.pg_Fattura>= ?7");
            clause = " AND ";
        } else if (pgFatturaEnteDa == null && pgFatturaEnteA != null) {
            queryStr.append(clause).append("fatt.pg_Fattura<= ?8");
            clause = " AND ";
        } else if (pgFatturaEnteDa != null && pgFatturaEnteA != null) {
            queryStr.append(clause).append("(fatt.pg_Fattura>= ?7 AND fatt.pg_Fattura<= ?8)");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdFattura)) {
            queryStr.append(clause).append("fatt.cd_Fattura = ?9");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdRegistroEmisFattura)) {
            queryStr.append(clause).append("UPPER(fatt.cd_Registro_Emis_Fattura) LIKE ?10");
            clause = " AND ";
        }
        if (aaEmissFattura != null) {
            queryStr.append(clause).append("fatt.aa_Emiss_Fattura = ?11");
            clause = " AND ";
        }
        if (StringUtils.isNotBlank(cdEmisFattura)) {
            queryStr.append(clause).append("UPPER(fatt.cd_Emis_Fattura) LIKE ?12");
            clause = " AND ";
        }
        if (pgFatturaEmis != null) {
            queryStr.append(clause).append("fatt.pg_Fattura = ?13");
            clause = " AND ";
        }

        if (dtEmisFatturaDa != null && dtEmisFatturaA == null) {
            queryStr.append(clause).append("fatt.dt_Emis_Fattura >= ?14");
            clause = " AND ";
        } else if (dtEmisFatturaDa == null && dtEmisFatturaA != null) {
            queryStr.append(clause).append("(fatt.dt_Emis_Fattura <= ?15)");
            clause = " AND ";
        } else if (dtEmisFatturaDa != null && dtEmisFatturaA != null) {
            queryStr.append(clause).append("(fatt.dt_Emis_Fattura BETWEEN ?14 AND ?15)");
            clause = " AND ";
        }

        queryStr.append(clause).append("stato_cor.ti_Stato_Fattura_Ente = 'CALCOLATA' ");

        queryStr.append(" order by ente.nm_ente_siam, fatt.aa_fattura, fatt.pg_fattura, serv.nm_servizio_erogato ");

        Query query = getEntityManager().createNativeQuery(queryStr.toString());

        if (StringUtils.isNotBlank(nmEnteConvenz)) {
            query.setParameter(1, "%" + nmEnteConvenz.toUpperCase() + "%");
        }
        if (idTipoAccordo != null) {
            query.setParameter(3, idTipoAccordo);
        }
        if (idTipoServizio != null) {
            query.setParameter(4, idTipoServizio);
        }
        if (aaFatturaDa != null) {
            query.setParameter(5, aaFatturaDa);
        }
        if (aaFatturaA != null) {
            query.setParameter(6, aaFatturaA);
        }
        if (pgFatturaEnteDa != null) {
            query.setParameter(7, pgFatturaEnteDa);
        }
        if (pgFatturaEnteA != null) {
            query.setParameter(8, pgFatturaEnteA);
        }
        if (StringUtils.isNotBlank(cdFattura)) {
            query.setParameter(9, cdFattura);
        }
        if (StringUtils.isNotBlank(cdRegistroEmisFattura)) {
            query.setParameter(10, "%" + cdRegistroEmisFattura.toUpperCase() + "%");
        }
        if (aaEmissFattura != null) {
            query.setParameter(11, aaEmissFattura);
        }
        if (StringUtils.isNotBlank(cdEmisFattura)) {
            query.setParameter(12, "%" + cdEmisFattura.toUpperCase() + "%");
        }
        if (pgFatturaEmis != null) {
            query.setParameter(13, pgFatturaEmis);
        }
        if (dtEmisFatturaDa != null) {
            query.setParameter(14, dtEmisFatturaDa);
        }
        if (dtEmisFatturaA != null) {
            query.setParameter(15, dtEmisFatturaA);
        }

        List<Object[]> lista = (List<Object[]>) query.getResultList();
        return lista;

    }

    public void writeLogEventoByScript() {
        String queryStr = "insert into sacer_log.LOG_EVENTO_BY_SCRIPT " + "( id_evento_by_script, "
                + "  id_tipo_oggetto, " + "  id_oggetto, " + "  dt_reg_evento, " + "  id_agente, "
                + "  id_azione_comp_sw, " + "  ti_ruolo_oggetto_evento, " + "  ti_ruolo_agente_evento, "
                + "  id_applic, " + "  ds_motivo_script) " + "select "
                + "to_number(1000 || to_char (sacer_log.slog_evento_by_script.nextval)), " + "config.id_tipo_oggetto, "
                + "tmp.id_oggetto id_oggetto, " + "sysdate, " + "config.id_agente, " + "config.id_azione_comp_sw, "
                + "'outcome', " + "'executing program', " + "config.id_applic, " + "'Inserimento asincrono' "
                + "from tmp_nuova_richiesta tmp, " + "   (select " + "     ti_ogg.id_tipo_oggetto, "
                + "     comp_sw.id_agente, " + "     azio_sw.id_azione_comp_sw, " + "     apl.id_applic "
                + "    from sacer_iam.apl_applic apl " + "    join sacer_iam.apl_comp_sw comp_sw "
                + "     on (comp_sw.id_applic = apl.id_applic) " + "    join sacer_iam.apl_azione_comp_sw azio_sw "
                + "     on (azio_sw.id_comp_sw = comp_sw.id_comp_sw) " + "    join sacer_iam.apl_tipo_oggetto ti_ogg "
                + "     on (ti_ogg.id_applic = apl.id_applic) " + "    where apl.nm_applic = 'SACER_IAM' "
                + "    and comp_sw.nm_comp_sw = 'SCRIPT_SACER_IAM' "
                + "    and azio_sw.nm_azione_comp_sw = 'Modifica con script' "
                + "    and ti_ogg.nm_tipo_oggetto = 'Ente convenzionato' " + "    ) config ";

        Query query = getEntityManager().createNativeQuery(queryStr);
        query.executeUpdate();
    }

    public Object[] getDatiLogByScript() {
        String queryStr = "select " + "     ti_ogg.id_tipo_oggetto, " + "     comp_sw.id_agente, "
                + "     azio_sw.id_azione_comp_sw, " + "     apl.id_applic " + "    from sacer_iam.apl_applic apl "
                + "    join sacer_iam.apl_comp_sw comp_sw " + "     on (comp_sw.id_applic = apl.id_applic) "
                + "    join sacer_iam.apl_azione_comp_sw azio_sw "
                + "     on (azio_sw.id_comp_sw = comp_sw.id_comp_sw) " + "    join sacer_iam.apl_tipo_oggetto ti_ogg "
                + "     on (ti_ogg.id_applic = apl.id_applic) " + "    where apl.nm_applic = 'SACER_IAM' "
                + "    and comp_sw.nm_comp_sw = 'SCRIPT_SACER_IAM' "
                + "    and azio_sw.nm_azione_comp_sw = 'Modifica con script' "
                + "    and ti_ogg.nm_tipo_oggetto = 'Ente convenzionato' ";
        //
        Query query = getEntityManager().createNativeQuery(queryStr);
        List<Object[]> lista = (List<Object[]>) query.getResultList();
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    /**
     * Recupera l'elenco dei cluster presenti su DB
     * 
     * @return la lista dei cluster e relativa fascia associabili ad un accordo
     */
    public List<Object[]> getOrgClusterAccordoList() {
        Query query = getEntityManager().createQuery(
                "SELECT clusterAccordo.idClusterAccordo, clusterAccordo.cdCluster || ' - ' || clusterAccordo.orgFasciaStorageAccordo.tiFascia as ds_cluster_accordo "
                        + "FROM OrgClusterAccordo clusterAccordo " + "ORDER BY clusterAccordo.idClusterAccordo ");
        return query.getResultList();
    }

    public List<Object[]> getOrgFasciaStorageAccordoList() {
        Query query = getEntityManager().createQuery("SELECT fasciaStorageAccordo.idFasciaStorageAccordo, "
                + "fasciaStorageAccordo.tiFascia, fasciaStorageAccordo.niFasciaDa, fasciaStorageAccordo.niFasciaA "
                + "FROM OrgFasciaStorageAccordo fasciaStorageAccordo ");
        return query.getResultList();
    }

    public Object[] getFasciaDaOccupazione(BigDecimal dimBytesMediaAnno) {
        Query query = getEntityManager()
                .createQuery("SELECT fasciaStorageAccordo.idFasciaStorageAccordo, fasciaStorageAccordo.tiFascia, "//
                        + "fasciaStorageAccordo.niFasciaDa, fasciaStorageAccordo.niFasciaA "
                        + "FROM OrgFasciaStorageAccordo fasciaStorageAccordo "
                        + "WHERE :dimBytesMediaAnno BETWEEN fasciaStorageAccordo.niFasciaDa AND fasciaStorageAccordo.niFasciaA ");//
        query.setParameter("dimBytesMediaAnno", dimBytesMediaAnno);
        List<Object[]> resultList = query.getResultList();
        Object[] fascia = null;//
        if (resultList != null && !resultList.isEmpty()) {
            fascia = new Object[2];
            Object[] result = resultList.get(0);
            // Formatto la descrizione inserendo anche i punti e virgola separatori
            DecimalFormat df = new DecimalFormat("#,###.00");
            Locale currentLocale = Locale.getDefault();
            df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(currentLocale));
            BigDecimal fasciaDa = (BigDecimal) result[2];
            BigDecimal fasciaA = (BigDecimal) result[3];
            String fasciaDaFormattata = df.format(fasciaDa.longValue());
            fasciaDaFormattata = fasciaDaFormattata.equals(",00") ? "0,00" : fasciaDaFormattata;
            String fasciaAFormattata = df.format(fasciaA.longValue());
            fascia[0] = result[0];
            fascia[1] = (String) result[1] + " - da " + fasciaDaFormattata + " a " + fasciaAFormattata;
        }
        return fascia;
    }

    public OrgVOccupStorageAccordo getOrgVOccupStorageAccordo(BigDecimal idAccordoEnte, List<String> cdTipoAccordo) {
        Query query = getEntityManager().createQuery(
                "SELECT a FROM OrgVOccupStorageAccordo a WHERE a.idAccordoEnte = :idAccordoEnte AND a.cdTipoAccordo IN :cdTipoAccordo");
        query.setParameter("idAccordoEnte", idAccordoEnte);
        query.setParameter("cdTipoAccordo", cdTipoAccordo);
        List<OrgVOccupStorageAccordo> list = query.getResultList();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public Object[] getOccupStorageGestore(String dtDecAccordoCorrente, BigDecimal idEnteConvenzGestore) {
        Query query = getEntityManager().createNativeQuery(STORAGE_GESTORE);
        query.setParameter("dtDecAccordoCorrente", dtDecAccordoCorrente);
        query.setParameter("idEnteConvenzGestore", idEnteConvenzGestore);
        List<Object[]> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<Object[]> getReportStorageExtraRer(List<String> cdTipoAccordo) {
        String queryNativeSQL = "SELECT OCCUP.AMBIENTE_ENTE, OCCUP.DIM_BYTES_MEDIA_ANNO, "
                + "TO_CHAR(OCCUP.DT_DEC_ACCORDO,'DD/MM/YYYY'), TO_CHAR(OCCUP.DT_FINE_ACCORDO, 'DD/MM/YYYY'), "
                + "OCCUP.FASCIA_ACCORDO, OCCUP.FASCIA_OCCUPAZIONE, OCCUP.FL_SFORO "
                + "FROM SACER_IAM.ORG_V_OCCUP_STORAGE_ACCORDO OCCUP ";
        if (cdTipoAccordo != null && !cdTipoAccordo.isEmpty()) {
            queryNativeSQL = queryNativeSQL + "WHERE OCCUP.CD_TIPO_ACCORDO IN ?1 ";
        }

        queryNativeSQL = queryNativeSQL + " ORDER BY OCCUP.AMBIENTE_ENTE ";

        Query query = getEntityManager().createNativeQuery(queryNativeSQL);
        if (cdTipoAccordo != null && !cdTipoAccordo.isEmpty()) {
            query.setParameter(1, cdTipoAccordo);
        }
        List<Object[]> lista = (List<Object[]>) query.getResultList();
        return lista;
    }

    public static String STORAGE_GESTORE = "SELECT "
            + " round(months_between(sysdate, to_date(:dtDecAccordoCorrente, 'dd/MM/yyyy'))) AS mesi_validita,   "
            + "    trunc(12 *((SUM(mon.ni_size_vers) + SUM(mon.ni_size_agg) - SUM(mon.ni_size_annul_ud)) / round(months_between(sysdate, to_date(:dtDecAccordoCorrente, 'dd/MM/yyyy'))))) AS dim_bytes_media_anno "
            + "FROM" + "    sacer.mon_conta_ud_doc_comp    mon, " + "    (" + "        SELECT DISTINCT "
            + "            str.id_strut " + "        FROM " + "            sacer_iam.org_accordo_ente acc "
            + "            join sacer_iam.org_ente_siam ente on (acc.id_ente_convenz = ente.id_ente_siam) "
            + "			join sacer_iam.org_ambiente_ente_convenz ambiente on (ambiente.id_ambiente_ente_convenz = ente.id_ambiente_ente_convenz) "
            + "            join sacer_iam.org_tipo_accordo tipo_acc on (tipo_acc.id_tipo_accordo = acc.id_tipo_accordo),            "
            + "            sacer.org_strut               str			" + "        WHERE "
            + "            acc.id_ente_convenz = ente.id_ente_siam            "
            + "            AND nvl(acc.fl_recesso, 0) = 0 "
            + "            AND nvl(acc.dt_dec_accordo, sysdate) <= sysdate			"
            + "            AND str.id_ente_convenz = ente.id_ente_siam "
            + "            and acc.id_ente_convenz_gestore = :idEnteConvenzGestore "
            + "    )                              struts " + "WHERE " + "        mon.id_strut = struts.id_strut "
            + "    AND mon.dt_rif_conta >= to_date(:dtDecAccordoCorrente, 'dd/MM/yyyy') "
            + "    AND to_date(:dtDecAccordoCorrente, 'dd/MM/yyyy')+365<SYSDATE ";

    public List<BigDecimal> getAbilitazioniDaCancellare(List<BigDecimal> idAppartCollegEntiList) {
        Query q = getEntityManager().createQuery(
                "SELECT u FROM UsrVAbilEnteCollegToDel u " + "WHERE u.idAppartCollegEnti IN :idAppartCollegEntiList ");

        q.setParameter("idAppartCollegEntiList", idAppartCollegEntiList);

        List<UsrVAbilEnteCollegToDel> abilEnteCollegToDelList = (List<UsrVAbilEnteCollegToDel>) q.getResultList();
        List<BigDecimal> utenti = new ArrayList<>();

        for (UsrVAbilEnteCollegToDel abilEnteCollegToDel : abilEnteCollegToDelList) {
            utenti.add(abilEnteCollegToDel.getIdUserIam());
        }

        return utenti;
    }

}
