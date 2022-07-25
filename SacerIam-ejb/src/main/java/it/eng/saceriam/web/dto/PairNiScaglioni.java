package it.eng.saceriam.web.dto;

public class PairNiScaglioni<Ini, Fine> {

    private final Ini ini;
    private final Fine fine;

    public PairNiScaglioni(Ini ini, Fine fine) {
        this.ini = ini;
        this.fine = fine;
    }

    public Ini getIni() {
        return ini;
    }

    public Fine getFine() {
        return fine;
    }

    @Override
    public int hashCode() {
        return ini.hashCode() ^ fine.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PairNiScaglioni)) {
            return false;
        }
        PairNiScaglioni pairo = (PairNiScaglioni) o;
        return this.ini.equals(pairo.getIni()) && this.fine.equals(pairo.getFine());
    }

    @Override
    public String toString() {
        return "PairNiScaglioni{" + "ni_ini_scaglione=" + ini + ", ni_fine_scaglione=" + fine + '}';
    }

}
