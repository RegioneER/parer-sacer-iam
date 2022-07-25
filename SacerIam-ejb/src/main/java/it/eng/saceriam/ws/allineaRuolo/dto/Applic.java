package it.eng.saceriam.ws.allineaRuolo.dto;

import java.io.Serializable;

/**
 *
 * @author Bonora_L
 */
public class Applic implements Serializable {

    private String nmApplic;
    private String cdVersioneComp;
    private ListaDichAutor listaDichAutor;

    /**
     * Get the value of nmApplic
     *
     * @return the value of nmApplic
     */
    public String getNmApplic() {
        return nmApplic;
    }

    /**
     * Set the value of nmApplic
     *
     * @param nmApplic
     *            new value of nmApplic
     */
    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    /**
     * @return the cdVersioneComp
     */
    public String getCdVersioneComp() {
        return cdVersioneComp;
    }

    /**
     * @param cdVersioneComp
     *            the cdVersioneComp to set
     */
    public void setCdVersioneComp(String cdVersioneComp) {
        this.cdVersioneComp = cdVersioneComp;
    }

    /**
     * @return the listaDichAutor
     */
    public ListaDichAutor getListaDichAutor() {
        return listaDichAutor;
    }

    /**
     * @param listaDichAutor
     *            the listaDichAutor to set
     */
    public void setListaDichAutor(ListaDichAutor listaDichAutor) {
        this.listaDichAutor = listaDichAutor;
    }

    @Override
    public String toString() {
        return "{" + "nmApplic=" + nmApplic + ", cdVersioneComp= " + cdVersioneComp + ", listaDichAutor="
                + listaDichAutor + "}";
    }

}
