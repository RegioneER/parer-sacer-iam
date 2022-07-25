package it.eng.saceriam.ws.allineaRuolo.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Bonora_L
 */
public class ListaDichAutor implements Iterable<DichAutor> {

    private List<DichAutor> dichAutor;

    public List<DichAutor> getDichAutor() {
        if (dichAutor == null) {
            dichAutor = new ArrayList<>();
        }
        return dichAutor;
    }

    public void setDichAutor(List<DichAutor> dichAutor) {
        this.dichAutor = dichAutor;
    }

    @Override
    public Iterator<DichAutor> iterator() {
        return dichAutor.iterator();
    }

    @Override
    public String toString() {
        return "" + dichAutor;
    }

}
