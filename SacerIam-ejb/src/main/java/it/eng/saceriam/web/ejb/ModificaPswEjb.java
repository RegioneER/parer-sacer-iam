package it.eng.saceriam.web.ejb;

import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.saceriam.web.helper.UserHelper;
import it.eng.saceriam.web.util.ApplEnum;
import it.eng.spagoCore.error.EMFError;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Ejb Modifica Password di SacerIam
 *
 * @author Gilioli_P
 */
@Stateless
@LocalBean
public class ModificaPswEjb {

    public ModificaPswEjb() {
    }

    @EJB
    private UserHelper userHelper;

    public Set<BigDecimal> getIdApplicSet(Long idUserIam) throws EMFError {
        Set<BigDecimal> idApplicSet = new HashSet();
        List<UsrUsoUserApplic> applicList = userHelper.getUsrUsoUserApplic(new BigDecimal(idUserIam));
        for (UsrUsoUserApplic applic : applicList) {
            idApplicSet.add(new BigDecimal(applic.getAplApplic().getIdApplic()));
        }
        return idApplicSet;
    }

    public void registraLogUserDaReplic(String username) throws Exception {
        userHelper.registraLogUserDaReplic(username, ApplEnum.TiOperReplic.MOD);
    }

}
