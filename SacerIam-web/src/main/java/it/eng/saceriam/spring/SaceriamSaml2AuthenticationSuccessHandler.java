package it.eng.saceriam.spring;

import it.eng.spagoLite.spring.CustomSaml2AuthenticationSuccessHandler;
import it.eng.saceriam.entity.UsrUser;
import it.eng.saceriam.web.helper.UserHelper;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.springframework.stereotype.Component;

/**
 *
 * @author Marco Iacolucci
 */
@Component
public class SaceriamSaml2AuthenticationSuccessHandler extends CustomSaml2AuthenticationSuccessHandler {

    @EJB(mappedName = "java:app/SacerIam-ejb/UserHelper")
    private UserHelper userHelper;

    @Override
    protected List<UtenteDb> findUtentiPerCodiceFiscale(String codiceFiscale) {
        ArrayList<UtenteDb> al = new ArrayList<>();
        List<UsrUser> l = userHelper.findByCodiceFiscale(codiceFiscale);
        l.stream().map(usrUser -> {
            UtenteDb u = new UtenteDb();
            u.setId(usrUser.getIdUserIam());
            u.setCodiceFiscale(usrUser.getCdFisc());
            u.setDataScadenzaPassword(u.getDataScadenzaPassword());
            u.setUsername(usrUser.getNmUserid());
            return u;
        }).forEachOrdered(al::add);
        return al;
    }

    @Override
    protected UtenteDb getUtentePerUsername(String username) {
        UsrUser usrUser = userHelper.findUserByName(username);
        UtenteDb u = new UtenteDb();
        u.setId(usrUser.getIdUserIam());
        u.setCodiceFiscale(usrUser.getCdFisc());
        u.setDataScadenzaPassword(u.getDataScadenzaPassword());
        u.setUsername(usrUser.getNmUserid());
        return u;
    }

    @Override
    protected List<UtenteDb> findUtentiPerUsernameCaseInsensitive(String username) {
        List<UtenteDb> al = new ArrayList<>();
        List<UsrUser> l = userHelper.findUtentiPerUsernameCaseInsensitive(username);
        l.stream().map(usrUser -> {
            UtenteDb u = new UtenteDb();
            u.setId(usrUser.getIdUserIam());
            u.setCodiceFiscale(usrUser.getCdFisc());
            u.setDataScadenzaPassword(u.getDataScadenzaPassword());
            u.setUsername(usrUser.getNmUserid());
            return u;
        }).forEachOrdered(al::add);
        return al;
    }

}
