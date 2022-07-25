package it.eng.saceriam.ws.allineaRuolo.dto;

import java.io.Serializable;

/**
 *
 * @author Bonora_L
 */
public class DichAutor implements Serializable {

    private String tiDichAutor, tiScopoDichAutor, dsPathEntryMenuPadre, dsPathEntryMenuFoglia, nmPaginaWeb,
            nmAzionePagina, nmServizioWeb;

    /**
     * @return the tiDichAutor
     */
    public String getTiDichAutor() {
        return tiDichAutor;
    }

    /**
     * @param tiDichAutor
     *            the tiDichAutor to set
     */
    public void setTiDichAutor(String tiDichAutor) {
        this.tiDichAutor = tiDichAutor;
    }

    /**
     * @return the tiScopoDichAutor
     */
    public String getTiScopoDichAutor() {
        return tiScopoDichAutor;
    }

    /**
     * @param tiScopoDichAutor
     *            the tiScopoDichAutor to set
     */
    public void setTiScopoDichAutor(String tiScopoDichAutor) {
        this.tiScopoDichAutor = tiScopoDichAutor;
    }

    /**
     * @return the dsPathEntryMenuPadre
     */
    public String getDsPathEntryMenuPadre() {
        return dsPathEntryMenuPadre;
    }

    /**
     * @param dsPathEntryMenuPadre
     *            the dsPathEntryMenuPadre to set
     */
    public void setDsPathEntryMenuPadre(String dsPathEntryMenuPadre) {
        this.dsPathEntryMenuPadre = dsPathEntryMenuPadre;
    }

    /**
     * @return the dsPathEntryMenuFoglia
     */
    public String getDsPathEntryMenuFoglia() {
        return dsPathEntryMenuFoglia;
    }

    /**
     * @param dsPathEntryMenuFoglia
     *            the dsPathEntryMenuFoglia to set
     */
    public void setDsPathEntryMenuFoglia(String dsPathEntryMenuFoglia) {
        this.dsPathEntryMenuFoglia = dsPathEntryMenuFoglia;
    }

    /**
     * @return the nmPaginaWeb
     */
    public String getNmPaginaWeb() {
        return nmPaginaWeb;
    }

    /**
     * @param nmPaginaWeb
     *            the nmPaginaWeb to set
     */
    public void setNmPaginaWeb(String nmPaginaWeb) {
        this.nmPaginaWeb = nmPaginaWeb;
    }

    /**
     * @return the nmAzionePagina
     */
    public String getNmAzionePagina() {
        return nmAzionePagina;
    }

    /**
     * @param nmAzionePagina
     *            the nmAzionePagina to set
     */
    public void setNmAzionePagina(String nmAzionePagina) {
        this.nmAzionePagina = nmAzionePagina;
    }

    /**
     * @return the nmServizioWeb
     */
    public String getNmServizioWeb() {
        return nmServizioWeb;
    }

    /**
     * @param nmServizioWeb
     *            the nmServizioWeb to set
     */
    public void setNmServizioWeb(String nmServizioWeb) {
        this.nmServizioWeb = nmServizioWeb;
    }

    @Override
    public String toString() {
        return "{" + "tiDichAutor=" + tiDichAutor + ", tiScopoDichAutor=" + tiScopoDichAutor + ", dsPathEntryMenuPadre="
                + dsPathEntryMenuPadre + ", dsPathEntryMenuFoglia=" + dsPathEntryMenuFoglia + ", nmPaginaWeb="
                + nmPaginaWeb + ", nmAzionePagina=" + nmAzionePagina + ", nmServizioWeb=" + nmServizioWeb + "}";
    }

}
