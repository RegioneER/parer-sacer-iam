package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Gilioli_P
 */
public class ListaTipiDato implements Iterable<TipoDato> {
    private List<TipoDato> tipoDato = new ArrayList<>();

    public List<TipoDato> getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(List<TipoDato> tipoDato) {
        this.tipoDato = tipoDato;
    }

    @Override
    public Iterator<TipoDato> iterator() {
        return tipoDato.iterator();
    }
}
