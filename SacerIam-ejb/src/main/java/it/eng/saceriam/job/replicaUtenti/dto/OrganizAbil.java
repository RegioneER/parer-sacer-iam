package it.eng.saceriam.job.replicaUtenti.dto;

/**
 *
 * @author Gilioli_P
 */
public class OrganizAbil {

    private Integer idOrganizApplicAbil;
    private ListaServiziAutor listaServiziAutor;
    private ListaTipiDatoAbil listaTipiDatoAbil;

    public Integer getIdOrganizApplicAbil() {
        return idOrganizApplicAbil;
    }

    public void setIdOrganizApplicAbil(Integer idOrganizApplicAbil) {
        this.idOrganizApplicAbil = idOrganizApplicAbil;
    }

    public ListaServiziAutor getListaServiziAutor() {
        return listaServiziAutor;
    }

    public void setListaServiziAutor(ListaServiziAutor listaServiziAutor) {
        this.listaServiziAutor = listaServiziAutor;
    }

    public ListaTipiDatoAbil getListaTipiDatoAbil() {
        return listaTipiDatoAbil;
    }

    public void setListaTipiDatoAbil(ListaTipiDatoAbil listaTipiDatoAbil) {
        this.listaTipiDatoAbil = listaTipiDatoAbil;
    }
}
