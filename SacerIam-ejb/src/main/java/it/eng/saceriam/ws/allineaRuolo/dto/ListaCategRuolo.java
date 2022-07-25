package it.eng.saceriam.ws.allineaRuolo.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Bonora_L
 */
public class ListaCategRuolo implements Iterable<String> {

    private List<String> tiCategRuolo;

    /**
     * @return the tiCategRuolo
     */
    public List<String> getTiCategRuolo() {
        if (tiCategRuolo == null) {
            tiCategRuolo = new ArrayList<>();
        }
        return tiCategRuolo;
    }

    /**
     * @param tiCategRuolo
     *            the categRuolo to set
     */
    public void setTiCategRuolo(List<String> tiCategRuolo) {
        this.tiCategRuolo = tiCategRuolo;
    }

    @Override
    public Iterator<String> iterator() {
        return tiCategRuolo.iterator();
    }

    @Override
    public String toString() {
        return "ListaCategRuolo{" + "tiCategRuolo=" + tiCategRuolo + '}';
    }

}
