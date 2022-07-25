package it.eng.saceriam.job.replicaUtenti.dto;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Gilioli_P
 */
public class ListaServiziAutor implements Iterable<String> {

    private List<String> nmServizioAutor;

    public List<String> getNmServizioAutor() {
        return nmServizioAutor;
    }

    public void setNmServizioAutor(List<String> nmServizioAutor) {
        this.nmServizioAutor = nmServizioAutor;
    }

    @Override
    public Iterator<String> iterator() {
        return nmServizioAutor.iterator();
    }
}
