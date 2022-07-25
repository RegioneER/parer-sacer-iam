package it.eng.saceriam.web.helper;

import it.eng.saceriam.entity.AplApplic;
import it.eng.spagoCore.error.EMFError;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class ComboHelper Contiene i metodi, per la gestione della persistenza su DB per le
 * operazioni CRUD su oggetti di
 */
@Stateless
@LocalBean
public class ComboHelper {

    /*
     * Default constructor.
     */
    public ComboHelper() {
    }

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager em;

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base alle abilitazioni dell'utente
     * "amministratore" (quello corrente)
     *
     * @deprecated il parametro idUserAdmin non viene mai usato, richiamare il metodo senza questo parametro
     * 
     * @param idUserIamCorrente
     *            id user IAM
     * @param isUserAdmin
     *            id user Admin
     * 
     * @return la lista delle applicazioni
     */
    public List<AplApplic> getAplApplicAbilitateList(long idUserIamCorrente, boolean isUserAdmin) {
        return getAplApplicAbilitateList(idUserIamCorrente);
    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base alle abilitazioni dell'utente
     * "amministratore" (quello corrente)
     *
     * @param idUserIamCorrente
     *            id user IAM
     * 
     * @return la lista delle applicazioni
     */
    public List<AplApplic> getAplApplicAbilitateList(long idUserIamCorrente) {
        String queryStr = "SELECT usoApplic.aplApplic " + "FROM UsrUsoUserApplic usoApplic "
                + "WHERE usoApplic.usrUser.idUserIam = :idUserIamCorrente ";

        queryStr += "ORDER BY usoApplic.aplApplic.nmApplic ";
        Query query = em.createQuery(queryStr);
        query.setParameter("idUserIamCorrente", idUserIamCorrente);
        List<AplApplic> list = query.getResultList();
        return list;
    }

    public List<AplApplic> getAplApplicAbilitateRicercaReplicheList(long idUserIamCorrente) {
        String queryStr = "SELECT usoApplic.aplApplic " + "FROM UsrUsoUserApplic usoApplic "
                + "WHERE usoApplic.usrUser.idUserIam = :idUserIamCorrente "
                + "AND usoApplic.aplApplic.nmApplic NOT LIKE :nmApplic " + "ORDER BY usoApplic.aplApplic.nmApplic ";
        Query query = em.createQuery(queryStr);
        query.setParameter("idUserIamCorrente", idUserIamCorrente);
        query.setParameter("nmApplic", "DPI_%");
        List<AplApplic> list = query.getResultList();
        return list;
    }

    public String getFederationMetadata() {
        String queryStr = "SELECT asm.blSamlMetadati " + "FROM AplSamlMetadati asm ";
        Query query = em.createQuery(queryStr);
        return (String) query.getSingleResult();

    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base all'ente a cui appartiene l'utente
     *
     * @deprecated il parametro isEnteFornitoreEsterno non è mai usato
     * 
     * @param idUserIamCorrente
     *            id user IAM
     * @param isAppartEnteConvenzAdmin
     *            id ente di appartenenza
     * @param isEnteOrganoVigilanza
     *            true se ente organo vigilanza
     * @param isEnteFornitoreEsterno
     *            true se ente fornitore esterno
     * 
     * @return la lista delle applicazioni
     *
     */
    @Deprecated
    public List<AplApplic> getAplApplicAbilitateUtenteList(long idUserIamCorrente, boolean isAppartEnteConvenzAdmin,
            boolean isEnteOrganoVigilanza, boolean isEnteFornitoreEsterno) {
        return getAplApplicAbilitateUtenteList(idUserIamCorrente, isAppartEnteConvenzAdmin, isEnteOrganoVigilanza);
    }

    /**
     * Metodo che ritorna la lista delle applicazioni ordinate per nome in base all'ente a cui appartiene l'utente
     *
     * @param idUserIamCorrente
     *            id user IAM
     * @param isAppartEnteConvenzAdmin
     *            id ente di appartenenza
     * @param isEnteOrganoVigilanza
     *            true se ente organo vigilanza
     * 
     * @return la lista delle applicazioni
     *
     */
    public List<AplApplic> getAplApplicAbilitateUtenteList(long idUserIamCorrente, boolean isAppartEnteConvenzAdmin,
            boolean isEnteOrganoVigilanza) {

        String queryStr = "SELECT usoApplic.aplApplic " + "FROM UsrUsoUserApplic usoApplic "
                + "WHERE usoApplic.usrUser.idUserIam = :idUserIamCorrente ";

        if (!isAppartEnteConvenzAdmin) {
            queryStr += "AND usoApplic.aplApplic.nmApplic NOT IN ('TPI') ";
        }

        if (isEnteOrganoVigilanza) {
            queryStr += "AND usoApplic.aplApplic.nmApplic IN ('SACER') ";
        }

        queryStr += "ORDER BY usoApplic.aplApplic.nmApplic ";
        Query query = em.createQuery(queryStr);
        query.setParameter("idUserIamCorrente", idUserIamCorrente);
        List<AplApplic> list = query.getResultList();
        return list;
    }

}
