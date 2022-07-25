package it.eng.saceriam.ws.allineaRuolo.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Bonora_L
 */
public class ListaApplic implements Iterable<Applic> {

    private List<Applic> applic;

    /**
     * @return the applic
     */
    public List<Applic> getApplic() {
        if (applic == null) {
            applic = new ArrayList<>();
        }
        return applic;
    }

    /**
     * @param applic
     *            the applic to set
     */
    public void setApplic(List<Applic> applic) {
        this.applic = applic;
    }

    @Override
    public Iterator<Applic> iterator() {
        return applic.iterator();
    }

    @Override
    public String toString() {
        return "{" + "applic=" + applic + "}";
    }

}
