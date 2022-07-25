package it.eng.saceriam.job.replicaUtenti.dto;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Gilioli_P
 */
public class ListaOrganizAbil implements Iterable<OrganizAbil> {

    private List<OrganizAbil> organizAbil;

    public List<OrganizAbil> getOrganizAbil() {
        return organizAbil;
    }

    public void setOrganizAbil(List<OrganizAbil> organizAbil) {
        this.organizAbil = organizAbil;
    }

    @Override
    public Iterator<OrganizAbil> iterator() {
        return organizAbil.iterator();
    }
}
